package com.amap.bundle.planhome.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.Iterator;

public class RippleLayout extends RelativeLayout {
    private static final int DEFAULT_DURATION_TIME = 1000;
    private static final int DEFAULT_RADIUS = 160;
    private static final int DEFAULT_RIPPLE_COLOR = Color.rgb(173, 173, 173);
    private static final int DEFAULT_RIPPLE_COUNT = 2;
    private static final float DEFAULT_SCALE = 4.0f;
    private static final int DEFAULT_STROKE_WIDTH = 0;
    ObjectAnimator appearAnimator;
    ObjectAnimator dismissAnimator;
    ObjectAnimator fadedAnimator;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private boolean mIsAnimationRunning;
    /* access modifiers changed from: private */
    public Paint mPaint;
    /* access modifiers changed from: private */
    public AnimatorSet mPointAnimator;
    private int mRippleColor;
    private float mRippleRadius;
    private float mRippleScale;
    private ArrayList<RippleView> mRippleViewList;
    private int mRippleViewNums;
    private LayoutParams mRippleViewParams;
    private int mScaleDuration;
    /* access modifiers changed from: private */
    public float mStrokeWidth;
    ObjectAnimator scaleXAnimator;
    ObjectAnimator scaleYAnimator;

    class RippleView extends View {
        public RippleView(Context context) {
            super(context);
            setVisibility(4);
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            float min = (float) (Math.min(getWidth(), getHeight()) / 2);
            canvas.drawCircle(min, min, min - RippleLayout.this.mStrokeWidth, RippleLayout.this.mPaint);
        }
    }

    public RippleLayout(Context context) {
        this(context, null);
    }

    public RippleLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RippleLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRippleColor = DEFAULT_RIPPLE_COLOR;
        this.mStrokeWidth = 0.0f;
        this.mRippleRadius = 160.0f;
        this.mIsAnimationRunning = false;
        this.mPaint = new Paint();
        this.mRippleViewList = new ArrayList<>();
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (!isInEditMode()) {
            if (attributeSet != null) {
                initTypedArray(context, attributeSet);
            }
            initHandler();
            initPaint();
            initRippleViewLayoutParams(context);
            generateRippleViews();
        }
    }

    private void initTypedArray(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RippleLayout);
        this.mRippleColor = obtainStyledAttributes.getColor(R.styleable.RippleLayout_color, DEFAULT_RIPPLE_COLOR);
        this.mStrokeWidth = obtainStyledAttributes.getDimension(R.styleable.RippleLayout_strokeWidth, 0.0f);
        this.mRippleRadius = obtainStyledAttributes.getDimension(R.styleable.RippleLayout_radius, 160.0f);
        this.mScaleDuration = obtainStyledAttributes.getInt(R.styleable.RippleLayout_scaleDuration, 1000);
        this.mRippleViewNums = obtainStyledAttributes.getInt(R.styleable.RippleLayout_rippleNums, 2);
        this.mRippleScale = obtainStyledAttributes.getFloat(R.styleable.RippleLayout_scale, DEFAULT_SCALE);
        obtainStyledAttributes.recycle();
    }

    private void initHandler() {
        this.mHandler = new Handler(getContext().getMainLooper());
    }

    private void initPaint() {
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mStrokeWidth = 0.0f;
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(this.mRippleColor);
    }

    private void initRippleViewLayoutParams(Context context) {
        int i = (int) ((this.mRippleRadius + this.mStrokeWidth) * 2.0f);
        this.mRippleViewParams = new LayoutParams(i, i);
        this.mRippleViewParams.addRule(11, -1);
        this.mRippleViewParams.addRule(15, -1);
        this.mRippleViewParams.rightMargin = agn.a(context, 3.0f);
        this.mRippleViewParams.topMargin = agn.a(context, 16.0f);
    }

    private void generateRippleViews() {
        for (int i = 0; i < this.mRippleViewNums; i++) {
            RippleView rippleView = new RippleView(getContext());
            addView(rippleView, this.mRippleViewParams);
            this.mRippleViewList.add(rippleView);
            if (i == 0) {
                prepareAlphaAnimator(rippleView);
            } else {
                prepareScaleAnimator(rippleView);
            }
        }
        prepareAnimatorSet();
    }

    private void prepareAnimatorSet() {
        this.mPointAnimator = new AnimatorSet();
        this.mPointAnimator.play(this.appearAnimator).before(this.scaleXAnimator);
        this.mPointAnimator.play(this.scaleXAnimator).with(this.fadedAnimator);
        this.mPointAnimator.play(this.fadedAnimator).with(this.scaleYAnimator);
        this.mPointAnimator.play(this.scaleYAnimator).before(this.dismissAnimator);
        this.mPointAnimator.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                RippleLayout.this.mHandler.post(new Runnable() {
                    public final void run() {
                        RippleLayout.this.mPointAnimator.start();
                    }
                });
            }
        });
    }

    private void prepareAlphaAnimator(RippleView rippleView) {
        this.appearAnimator = ObjectAnimator.ofFloat(rippleView, View.ALPHA, new float[]{0.2f, 1.0f});
        this.appearAnimator.setDuration((long) this.mScaleDuration);
        this.dismissAnimator = ObjectAnimator.ofFloat(rippleView, View.ALPHA, new float[]{1.0f, 0.2f});
        this.dismissAnimator.setDuration((long) this.mScaleDuration);
    }

    private void prepareScaleAnimator(RippleView rippleView) {
        this.scaleXAnimator = ObjectAnimator.ofFloat(rippleView, View.SCALE_X, new float[]{1.0f, this.mRippleScale});
        this.scaleXAnimator.setDuration((long) this.mScaleDuration);
        this.scaleYAnimator = ObjectAnimator.ofFloat(rippleView, View.SCALE_Y, new float[]{1.0f, this.mRippleScale});
        this.scaleYAnimator.setDuration((long) this.mScaleDuration);
        this.fadedAnimator = ObjectAnimator.ofFloat(rippleView, View.ALPHA, new float[]{1.0f, 0.0f});
        this.fadedAnimator.setDuration((long) this.mScaleDuration);
    }

    public void startRippleAnimation() {
        if (!isRippleAnimationRunning()) {
            Iterator<RippleView> it = this.mRippleViewList.iterator();
            while (it.hasNext()) {
                it.next().setVisibility(0);
            }
            this.mPointAnimator.start();
            this.mIsAnimationRunning = true;
        }
    }

    public void stopRippleAnimation() {
        if (isRippleAnimationRunning()) {
            this.mPointAnimator.end();
            this.mIsAnimationRunning = false;
        }
    }

    public boolean isRippleAnimationRunning() {
        return this.mIsAnimationRunning;
    }
}
