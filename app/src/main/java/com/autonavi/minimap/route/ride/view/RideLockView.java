package com.autonavi.minimap.route.ride.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.R;

public class RideLockView extends View {
    private static final String TAG = "RideLockView";
    private float dsBottom;
    private float dsLeft;
    private float dsRight;
    private float dsTop;
    private Paint mArrowInPaint;
    private Paint mArrowMidPaint;
    private Paint mArrowOutPaint;
    private Paint mBkgPaint;
    private float mDensity;
    private Bitmap mDownArrow;
    private Paint mDownArrowPaint;
    private float mDownHeight;
    private RectF mDownRectF;
    private float mDownWidth;
    private Paint mFgPaint;
    private Paint mFlashPaint;
    private int mLength;
    private Bitmap mLockDownBitmap;
    private Bitmap mLockUpBitmap;
    private float mPercent;
    private int mRadius;
    private long mStartTime;
    private boolean mTouched;
    private Bitmap mUpArrow;
    private float mUpHeight;
    private RectF mUpRectF;
    private float mUpWidth;
    private float usBottom;
    private float usLeft;
    private float usRight;
    private float usTop;

    public RideLockView(Context context) {
        super(context);
        init();
    }

    public RideLockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public RideLockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    @SuppressLint({"NewApi"})
    @TargetApi(21)
    public RideLockView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(this.mLength, this.mLength);
    }

    private void init() {
        this.mBkgPaint = new Paint();
        this.mBkgPaint.setAntiAlias(true);
        this.mBkgPaint.setColor(getResources().getColor(R.color.c_27));
        this.mFgPaint = new Paint();
        this.mFgPaint.setAntiAlias(true);
        this.mFgPaint.setColor(getResources().getColor(R.color.c_2));
        this.mFlashPaint = new Paint();
        this.mFlashPaint.setAntiAlias(true);
        this.mFlashPaint.setColor(getResources().getColor(R.color.c_2));
        this.mDownArrowPaint = new Paint();
        this.mArrowOutPaint = new Paint();
        this.mArrowInPaint = new Paint();
        this.mArrowMidPaint = new Paint();
        this.mLockUpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_lockb_upper);
        this.mLockDownBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_lockb_lower);
        this.mUpArrow = BitmapFactory.decodeResource(getResources(), R.drawable.pull_lock_up_arrow);
        this.mDownArrow = BitmapFactory.decodeResource(getResources(), R.drawable.pull_lock_down_arrow);
        this.mLength = (int) (getResources().getDisplayMetrics().density * 132.0f);
        this.mRadius = this.mLength / 4;
        this.mUpWidth = (float) this.mLockUpBitmap.getWidth();
        this.mUpHeight = (float) this.mLockUpBitmap.getHeight();
        this.mDownHeight = (float) this.mLockDownBitmap.getHeight();
        this.mDownWidth = (float) this.mLockDownBitmap.getWidth();
        this.mDensity = getResources().getDisplayMetrics().density;
        this.usLeft = (this.mDensity * 66.0f) - (this.mUpWidth / 3.0f);
        this.usRight = (this.mDensity * 66.0f) + (this.mUpWidth / 3.0f);
        this.usTop = (this.mDensity * 66.0f) - ((this.mUpHeight / 3.0f) * 2.0f);
        this.usBottom = (this.mDensity * 66.0f) + 0.0f;
        this.mUpRectF = new RectF(this.usLeft, this.usTop, this.usRight, this.usBottom);
        this.dsLeft = (this.mDensity * 66.0f) - (this.mDownWidth / 3.0f);
        this.dsRight = (this.mDensity * 66.0f) + (this.mDownWidth / 3.0f);
        this.dsTop = (this.mDensity * 66.0f) - (this.mDownHeight / 3.0f);
        this.dsBottom = (this.mDensity * 66.0f) + (this.mDownHeight / 3.0f);
        this.mDownRectF = new RectF(this.dsLeft, this.dsTop, this.dsRight, this.dsBottom);
    }

    public void setPercent(float f) {
        this.mPercent = f;
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mTouched = true;
            this.mStartTime = SystemClock.uptimeMillis();
            invalidate();
        }
        return super.onTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.drawCircle((float) (this.mRadius * 2), (float) (this.mRadius * 2), (float) this.mRadius, this.mBkgPaint);
        float f = this.mPercent / 3.0f;
        float f2 = ((this.usRight - this.usLeft) * f) / 2.0f;
        float f3 = (((this.usBottom - this.usTop) * f) / 2.0f) * 2.0f;
        this.mUpRectF.set(this.usLeft - f2, this.usTop - f3, this.usRight + f2, this.usBottom);
        canvas.drawBitmap(this.mLockUpBitmap, null, this.mUpRectF, null);
        float f4 = ((this.dsRight - this.dsLeft) * f) / 2.0f;
        this.mDownRectF.set(this.dsLeft - f4, this.dsTop - ((((this.dsBottom - this.dsTop) * f) / 2.0f) * 2.0f), this.dsRight + f4, this.dsBottom);
        canvas.drawBitmap(this.mLockDownBitmap, null, this.mDownRectF, null);
        this.mDownArrowPaint.setAlpha((int) ((1.0f - this.mPercent) * 255.0f));
        canvas.drawBitmap(this.mDownArrow, (this.mDensity * 66.0f) - ((float) (this.mDownArrow.getWidth() / 2)), this.dsBottom + (this.mDensity * 3.0f), this.mDownArrowPaint);
        if (this.mTouched) {
            long uptimeMillis = SystemClock.uptimeMillis();
            if (uptimeMillis - this.mStartTime < 1000) {
                float f5 = ((float) (uptimeMillis - this.mStartTime)) / 1000.0f;
                AMapLog.d(TAG, "p=".concat(String.valueOf(f5)));
                if (f5 > 1.0f) {
                    f5 = 1.0f;
                } else if (f5 < 0.0f) {
                    f5 = 0.0f;
                }
                float f6 = ((float) (uptimeMillis - this.mStartTime)) / 300.0f;
                if (f6 > 1.0f) {
                    f6 = 1.0f;
                } else if (f6 < 0.0f) {
                    f6 = 0.0f;
                }
                this.mFgPaint.setAlpha((int) ((1.0f - f5) * 255.0f));
                canvas.drawCircle(((float) this.mRadius) * 2.0f, ((float) this.mRadius) * 2.0f, (((float) this.mRadius) / 5.0f) * 4.0f, this.mFgPaint);
                this.mFlashPaint.setAlpha((int) ((1.0f - f6) * 255.0f));
                canvas.drawCircle(((float) this.mRadius) * 2.0f, ((float) this.mRadius) * 2.0f, ((float) this.mRadius) * (f6 + 1.0f), this.mFlashPaint);
                invalidate();
            } else {
                this.mTouched = false;
                this.mStartTime = 0;
            }
        }
        if (this.mPercent > 0.95f) {
            if (this.mTouched) {
                long uptimeMillis2 = SystemClock.uptimeMillis();
                if (uptimeMillis2 - this.mStartTime < 7000) {
                    float f7 = (((float) (uptimeMillis2 - this.mStartTime)) % 500.0f) / 500.0f;
                    if (f7 > 1.0f) {
                        f7 = 1.0f;
                    } else if (f7 < 0.0f) {
                        f7 = 0.0f;
                    }
                    int height = this.mUpArrow.getHeight();
                    float width = (float) ((this.mRadius * 2) - (this.mUpArrow.getWidth() / 2));
                    float f8 = (float) height;
                    float f9 = ((this.usTop - f3) - f8) - (this.mDensity * 7.0f);
                    this.mArrowOutPaint.setAlpha((int) ((1.0f - f7) * 128.0f));
                    canvas.drawBitmap(this.mUpArrow, width, f9 - ((2.0f + f7) * f8), this.mArrowOutPaint);
                    this.mArrowMidPaint.setAlpha((int) (255.0f - (128.0f * f7)));
                    canvas.drawBitmap(this.mUpArrow, width, f9 - ((1.0f + f7) * f8), this.mArrowMidPaint);
                    this.mArrowInPaint.setAlpha((int) (255.0f * f7));
                    canvas.drawBitmap(this.mUpArrow, width, f9 - (f8 * f7), this.mArrowInPaint);
                    invalidate();
                }
                return;
            }
            int height2 = this.mUpArrow.getHeight();
            float width2 = (float) ((this.mRadius * 2) - (this.mUpArrow.getWidth() / 2));
            float f10 = this.usTop - f3;
            float f11 = (float) height2;
            float f12 = (f10 - f11) - (this.mDensity * 7.0f);
            this.mArrowOutPaint.setAlpha(128);
            canvas.drawBitmap(this.mUpArrow, width2, f12 - ((float) (height2 * 2)), this.mArrowOutPaint);
            this.mArrowMidPaint.setAlpha(255);
            canvas.drawBitmap(this.mUpArrow, width2, f12 - f11, this.mArrowMidPaint);
        }
    }
}
