package com.autonavi.widget.ui;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.autonavi.minimap.R;

public class ProgressView extends View {
    private static final int DEFAULT_DURATION = 1000;
    private static final int END_ANGEL = 990;
    private static final int END_SWEEP = 248;
    public static final int PROGRESS_B = 0;
    public static final int PROGRESS_C = 1;
    public static final float ROUND_ANGEL = 360.0f;
    private static final int START_ANGEL = 270;
    private static final int START_SWEEP = 38;
    private static final String TAG = "ProgressView";
    private RectF mBounds;
    private float mCurrentAngel;
    private float mCurrentSweep;
    private float mEndAngel;
    private float mEndSweep;
    private Paint mPaint;
    private int mProgressColor;
    private int mProgressType = 1;
    private float mStartAngel;
    private Property<ProgressView, Float> mStartAngelProperty = new Property<ProgressView, Float>(Float.class, "startAngel") {
        public final /* synthetic */ Object get(Object obj) {
            return Float.valueOf(((ProgressView) obj).getCurrentSweepAngle());
        }

        public final /* synthetic */ void set(Object obj, Object obj2) {
            ((ProgressView) obj).setCurrentStartAngle(((Float) obj2).floatValue());
        }
    };
    private float mStartSweep;
    private float mStrokeWidth;
    private ObjectAnimator mSweepAnimation1;
    private Property<ProgressView, Float> mSweepProperty = new Property<ProgressView, Float>(Float.class, "arc") {
        public final /* synthetic */ Object get(Object obj) {
            return Float.valueOf(((ProgressView) obj).getCurrentSweepAngle());
        }

        public final /* synthetic */ void set(Object obj, Object obj2) {
            ((ProgressView) obj).setCurrentSweepAngle(((Float) obj2).floatValue());
        }
    };

    static class a extends AccelerateDecelerateInterpolator {
        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        public final float getInterpolation(float f) {
            if (((double) f) > 0.5d) {
                return (super.getInterpolation((f * 2.0f) - 1.0f) / 2.0f) + 0.5f;
            }
            return super.getInterpolation(f * 2.0f) / 2.0f;
        }
    }

    public ProgressView(Context context) {
        super(context);
        init(context, null);
    }

    public ProgressView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public ProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ProgressView);
            if (obtainStyledAttributes != null) {
                this.mStartSweep = obtainStyledAttributes.getFloat(R.styleable.ProgressView_startSweep, 38.0f);
                this.mEndSweep = obtainStyledAttributes.getFloat(R.styleable.ProgressView_endSweep, 248.0f);
                this.mStartAngel = obtainStyledAttributes.getFloat(R.styleable.ProgressView_startAngel, 270.0f);
                this.mEndAngel = obtainStyledAttributes.getFloat(R.styleable.ProgressView_endAngel, 990.0f);
                this.mProgressColor = obtainStyledAttributes.getColor(R.styleable.ProgressView_progress_Color, -12417025);
                this.mStrokeWidth = obtainStyledAttributes.getDimension(R.styleable.ProgressView_progress_strokeWidth, getResources().getDimension(R.dimen.progress_stroke_width));
                this.mProgressType = obtainStyledAttributes.getInt(R.styleable.ProgressView_progressType, 1);
                obtainStyledAttributes.recycle();
            }
        }
        this.mPaint = new Paint();
        this.mPaint.setStrokeCap(Cap.SQUARE);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setColor(this.mProgressColor);
        this.mPaint.setStrokeWidth(this.mStrokeWidth);
        this.mBounds = new RectF();
        setUpAnimation();
    }

    private void setUpAnimation() {
        switch (this.mProgressType) {
            case 0:
                setUpProgressBAnimation();
                return;
            case 1:
                setUpProgressCAnimation();
                break;
        }
    }

    private void setUpProgressBAnimation() {
        this.mCurrentSweep = 360.0f;
        this.mSweepAnimation1 = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat(this.mSweepProperty, new float[]{this.mStartSweep, this.mEndSweep, this.mStartSweep}), PropertyValuesHolder.ofFloat(this.mStartAngelProperty, new float[]{this.mStartAngel, this.mEndAngel, this.mEndAngel + 360.0f})});
        this.mSweepAnimation1.setInterpolator(new a(0));
        this.mSweepAnimation1.setDuration(2000);
        this.mSweepAnimation1.setRepeatCount(-1);
    }

    private void setUpProgressCAnimation() {
        this.mSweepAnimation1 = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat(this.mSweepProperty, new float[]{this.mStartSweep, this.mEndSweep, this.mStartSweep}), PropertyValuesHolder.ofFloat(this.mStartAngelProperty, new float[]{this.mStartAngel, this.mEndAngel})});
        this.mSweepAnimation1.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mSweepAnimation1.setDuration(1000);
        this.mSweepAnimation1.setRepeatMode(1);
        this.mSweepAnimation1.setRepeatCount(-1);
    }

    public void startAnimation() {
        if (!this.mSweepAnimation1.isStarted()) {
            this.mSweepAnimation1.start();
        }
    }

    public void setCurrentStartAngle(float f) {
        this.mCurrentAngel = f;
        invalidate();
    }

    public float getCurrentStartAngle() {
        return this.mCurrentAngel;
    }

    public void setCurrentSweepAngle(float f) {
        this.mCurrentSweep = f;
        invalidate();
    }

    public float getCurrentSweepAngle() {
        return this.mCurrentSweep;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isProgressC() && getVisibility() == 0) {
            startAnimation();
        }
    }

    private boolean isProgressC() {
        return this.mProgressType == 1;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 8 || i == 4) {
            stopAnimation();
            return;
        }
        if (getVisibility() == 0 && isProgressC()) {
            startAnimation();
        }
    }

    public void setVisibility(int i) {
        if (getVisibility() != i) {
            super.setVisibility(i);
            if (i == 8 || i == 4) {
                stopAnimation();
            } else if (isProgressC()) {
                startAnimation();
            }
        }
    }

    public void stopAnimation() {
        this.mSweepAnimation1.cancel();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mBounds.left = this.mStrokeWidth;
        this.mBounds.top = this.mStrokeWidth;
        this.mBounds.right = ((float) getWidth()) - this.mStrokeWidth;
        this.mBounds.bottom = ((float) getHeight()) - this.mStrokeWidth;
        canvas.drawArc(this.mBounds, this.mCurrentAngel, this.mCurrentSweep, false, this.mPaint);
    }
}
