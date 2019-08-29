package com.alipay.mobile.beehive.capture.views;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.utils.Logger;

public class CustomRecordImageView extends View {
    private static final long DURATION_MS = 4000;
    private static final long MIN_DURATION = 1000;
    public static final String TAG = "CustomRecordImageView";
    private static final int VALID_TOP_MARGIN = 0;
    private final float cancelRecordDistance;
    private Paint circlePaint;
    private float circleRadius;
    private int colorRecordCancel;
    private int colorRecordNormal;
    private float currentCircleRadius;
    private float currentRingRadius;
    private float currentRingWidth;
    private a currentState;
    private ObjectAnimator mDrive;
    private boolean mInterceptRecordAction;
    private RecordListener mRecordListener;
    private float pivotX;
    private float pivotY;
    private Paint ringPaint;
    private float ringRadius;
    private float ringWidth;
    private long startTime;
    private float targetCircleRadius;
    private float targetRingRadius;
    private float targetRingWidth;

    public interface RecordListener {
        void onRecordCanceled();

        void onRecordFinish(boolean z);

        void onRecordStart();

        void onRecordTimeUnSatisfied();

        void onTouchOutside(boolean z);
    }

    private enum a {
        NORMAL,
        RECORDING,
        CANCELING
    }

    public void setInterceptUserRecordAction(boolean intercept) {
        this.mInterceptRecordAction = intercept;
        Logger.debug(TAG, "Intercept set to \"" + intercept + "\"");
    }

    public CustomRecordImageView(Context context) {
        this(context, null, 0);
    }

    public CustomRecordImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecordImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ringRadius = getResources().getDimension(R.dimen.ring_radius);
        this.ringWidth = getResources().getDimension(R.dimen.ring_width);
        this.circleRadius = getResources().getDimension(R.dimen.circle_radius);
        this.targetRingWidth = getResources().getDimension(R.dimen.target_ring_width);
        this.targetRingRadius = getResources().getDimension(R.dimen.target_ring_radius);
        this.targetCircleRadius = getResources().getDimension(R.dimen.target_circle_radius);
        this.cancelRecordDistance = -getResources().getDimension(R.dimen.cancel_record_distance);
        this.colorRecordNormal = getResources().getColor(R.color.colorRecordNormal);
        this.colorRecordCancel = getResources().getColor(R.color.colorRecordCancel);
        this.ringPaint = new Paint();
        this.circlePaint = new Paint();
        this.currentState = a.NORMAL;
        initPaints();
        initAnimation();
    }

    private void initPaints() {
        this.ringPaint.setColor(this.colorRecordNormal);
        this.ringPaint.setAntiAlias(true);
        this.ringPaint.setStyle(Style.STROKE);
        this.ringPaint.setStrokeWidth(this.ringWidth);
        this.circlePaint.setColor(this.colorRecordNormal);
        this.circlePaint.setAntiAlias(true);
    }

    private void initAnimation() {
        this.mDrive = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("currentRingRadius", new float[]{this.ringRadius, this.targetRingRadius}), PropertyValuesHolder.ofFloat("currentRingWidth", new float[]{this.ringWidth, this.targetRingWidth}), PropertyValuesHolder.ofFloat("currentCircleRadius", new float[]{this.circleRadius, this.targetCircleRadius})});
        this.mDrive.setDuration(DURATION_MS);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.pivotX <= 0.0f) {
            this.pivotX = ((float) getWidth()) / 2.0f;
            this.pivotY = ((float) getHeight()) / 2.0f;
        }
        refreshCirclePaint();
        refreshRingPaint();
        switch (this.currentState) {
            case NORMAL:
                drawNormal(canvas);
                return;
            case RECORDING:
                drawRecording(canvas);
                return;
            case CANCELING:
                drawCanceling(canvas);
                return;
            default:
                return;
        }
    }

    private void drawNormal(Canvas canvas) {
        canvas.drawCircle(this.pivotX, this.pivotY, this.ringRadius, this.ringPaint);
        canvas.drawCircle(this.pivotX, this.pivotY, this.circleRadius, this.circlePaint);
    }

    private void refreshCirclePaint() {
        switch (this.currentState) {
            case NORMAL:
                this.circlePaint.setColor(this.colorRecordNormal);
                return;
            case RECORDING:
                this.circlePaint.setColor(this.colorRecordNormal);
                return;
            case CANCELING:
                this.circlePaint.setColor(this.colorRecordCancel);
                return;
            default:
                return;
        }
    }

    private void refreshRingPaint() {
        switch (this.currentState) {
            case NORMAL:
                this.ringPaint.setColor(this.colorRecordNormal);
                this.ringPaint.setStrokeWidth(this.ringWidth);
                return;
            case RECORDING:
                this.ringPaint.setColor(this.colorRecordNormal);
                this.ringPaint.setStrokeWidth(this.currentRingWidth);
                return;
            case CANCELING:
                this.ringPaint.setColor(this.colorRecordCancel);
                this.ringPaint.setStrokeWidth(this.currentRingWidth);
                return;
            default:
                return;
        }
    }

    private void drawRecording(Canvas canvas) {
        canvas.drawCircle(this.pivotX, this.pivotY, this.currentRingRadius, this.ringPaint);
        canvas.drawCircle(this.pivotX, this.pivotY, this.currentCircleRadius, this.circlePaint);
    }

    private void drawCanceling(Canvas canvas) {
        canvas.drawCircle(this.pivotX, this.pivotY, this.currentRingRadius, this.ringPaint);
        canvas.drawCircle(this.pivotX, this.pivotY, this.currentCircleRadius, this.circlePaint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                if (this.mInterceptRecordAction) {
                    Logger.debug(TAG, "Camera not ready ,intercept user record action.");
                    return false;
                } else if (this.startTime <= 0 || System.currentTimeMillis() - this.startTime >= 500) {
                    updateSafePoinitTime();
                    setCurrentState(a.RECORDING);
                    if (this.mRecordListener != null) {
                        this.mRecordListener.onRecordStart();
                    }
                    return true;
                } else {
                    updateSafePoinitTime();
                    Logger.debug(TAG, "Touch action too fast,action time = " + this.startTime);
                    return false;
                }
            case 1:
            case 3:
                if (this.currentState == a.NORMAL) {
                    Log.d(TAG, "Called finished before");
                } else if (this.currentState == a.RECORDING) {
                    if (System.currentTimeMillis() - this.startTime >= 1000) {
                        if (this.mRecordListener != null) {
                            this.mRecordListener.onRecordFinish(false);
                        }
                    } else if (this.mRecordListener != null) {
                        this.mRecordListener.onRecordTimeUnSatisfied();
                    }
                } else if (this.mRecordListener != null) {
                    this.mRecordListener.onRecordCanceled();
                }
                setCurrentState(a.NORMAL);
                return false;
            case 2:
                if (this.currentState == a.NORMAL) {
                    return false;
                }
                float y = event.getY();
                if (y < this.cancelRecordDistance) {
                    if (this.currentState == a.CANCELING) {
                        return false;
                    }
                    setCurrentState(a.CANCELING);
                    if (this.mRecordListener == null) {
                        return false;
                    }
                    this.mRecordListener.onTouchOutside(true);
                    return false;
                } else if (y <= 0.0f || this.currentState == a.RECORDING) {
                    return false;
                } else {
                    setCurrentState(a.RECORDING);
                    if (this.mRecordListener == null) {
                        return false;
                    }
                    this.mRecordListener.onTouchOutside(false);
                    return false;
                }
            default:
                return false;
        }
    }

    private void updateSafePoinitTime() {
        this.startTime = System.currentTimeMillis();
    }

    public float getCurrentCircleRadius() {
        return this.currentCircleRadius;
    }

    public void setCurrentCircleRadius(float currentCircleRadius2) {
        this.currentCircleRadius = currentCircleRadius2;
        invalidate();
    }

    public float getCurrentRingRadius() {
        return this.currentRingRadius;
    }

    public void setCurrentRingRadius(float currentRingRadius2) {
        this.currentRingRadius = currentRingRadius2;
        invalidate();
    }

    public float getCurrentRingWidth() {
        return this.currentRingWidth;
    }

    public void setCurrentRingWidth(float currentRingWidth2) {
        this.currentRingWidth = currentRingWidth2;
        invalidate();
    }

    private void setCurrentState(a state) {
        switch (state) {
            case NORMAL:
                this.mDrive.cancel();
                break;
            case RECORDING:
                if (this.currentState == a.NORMAL) {
                    this.mDrive.start();
                    break;
                }
                break;
        }
        this.currentState = state;
        invalidate();
    }

    public void setOnRecordListener(RecordListener listener) {
        this.mRecordListener = listener;
    }

    public void performFinishRecord() {
        Logger.debug(TAG, "performFinishRecord called!");
        setCurrentState(a.NORMAL);
        if (this.mRecordListener != null) {
            this.mRecordListener.onRecordFinish(true);
        }
    }

    public void performCancelRecord() {
        Logger.debug(TAG, "performCancelRecord called!");
        setCurrentState(a.NORMAL);
        if (this.mRecordListener != null) {
            this.mRecordListener.onRecordCanceled();
        }
    }
}
