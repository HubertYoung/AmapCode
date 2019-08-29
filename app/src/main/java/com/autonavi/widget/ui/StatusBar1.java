package com.autonavi.widget.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.j256.ormlite.stmt.query.SimpleComparison;

public class StatusBar1 extends ViewGroup {
    private static final long DEFAULT_DURATION = 1200;
    private static final long DEFAULT_INIT_DELAY = 1000;
    private static final long DEFAULT_INTERVAL_TIME = 1000;
    public static final int HORIZION_ALIGN_CENTER = 0;
    public static final int HORIZION_ALIGN_LEFT = -1;
    public static final int HORIZION_ALIGN_RIGHT = 1;
    private int mDefaultHeight;
    private TextView mDescTextView;
    private long mDuration;
    private int mHorizonAlign;
    private long mInitDelay;
    private long mIntervalTime;
    private boolean mIsInWindow;
    private Runnable mNextFrameRunnable;
    private PropertyValuesHolder mPropertyValuesHolder;
    /* access modifiers changed from: private */
    public boolean mTwinkle;
    private ObjectAnimator mTwinkleAnimator;

    public StatusBar1(Context context) {
        this(context, null);
    }

    public StatusBar1(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StatusBar1(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mInitDelay = 1000;
        this.mIntervalTime = 1000;
        this.mDuration = DEFAULT_DURATION;
        this.mTwinkle = true;
        this.mHorizonAlign = 0;
        this.mNextFrameRunnable = new Runnable() {
            public final void run() {
                if (StatusBar1.this.mTwinkle) {
                    StatusBar1.this.startAnimator();
                }
            }
        };
        init(context);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        this.mDescTextView.measure(MeasureSpec.makeMeasureSpec((MeasureSpec.getSize(i) - paddingLeft) - paddingRight, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec((MeasureSpec.getSize(i2) - paddingTop) - paddingBottom, Integer.MIN_VALUE));
        setMeasuredDimension(resolveSize(this.mDescTextView.getMeasuredWidth() + paddingLeft + paddingRight, i), resolveSize(this.mDefaultHeight, i2));
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsInWindow = true;
        firstFrame();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        stopAnimator();
        this.mIsInWindow = false;
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = (((((i4 - i2) - paddingTop) - getPaddingBottom()) - this.mDescTextView.getMeasuredHeight()) / 2) + paddingTop;
        if (this.mHorizonAlign == 0) {
            paddingLeft += ((((i3 - i) - paddingLeft) - paddingRight) - this.mDescTextView.getMeasuredWidth()) / 2;
        } else if (this.mHorizonAlign != -1) {
            paddingLeft = ((i3 - i) - paddingRight) - this.mDescTextView.getMeasuredWidth();
        }
        int max = Math.max(paddingLeft, 0);
        int max2 = Math.max(paddingBottom, 0);
        this.mDescTextView.layout(max, max2, this.mDescTextView.getMeasuredWidth() + max, this.mDescTextView.getMeasuredHeight() + max2);
    }

    private void init(Context context) {
        this.mDefaultHeight = context.getResources().getDimensionPixelOffset(R.dimen.status_height);
        this.mDescTextView = new TextView(context);
        this.mDescTextView.setSingleLine();
        this.mDescTextView.setIncludeFontPadding(false);
        addViewInLayout(this.mDescTextView, 0, generateDefaultLayoutParams(), true);
        setupDefaultStyle();
    }

    private void setupDefaultStyle() {
        setBackgroundColor(getResources().getColor(R.color.c_31));
        setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.f_s_13));
        setTextColor(-1);
        this.mDescTextView.getPaint().setFakeBoldText(true);
    }

    public void setTextWithArrow(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            StringBuilder sb = new StringBuilder();
            sb.append(charSequence);
            sb.append(SimpleComparison.GREATER_THAN_OPERATION);
            charSequence = sb.toString();
        }
        this.mDescTextView.setText(charSequence);
    }

    public void setText(CharSequence charSequence) {
        this.mDescTextView.setText(charSequence);
    }

    public void setText(int i) {
        this.mDescTextView.setText(i);
    }

    public void setTextSize(float f) {
        this.mDescTextView.setTextSize(f);
    }

    public void setTextSize(int i, float f) {
        this.mDescTextView.setTextSize(i, f);
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.mDescTextView.setTextColor(colorStateList);
    }

    public void setTextColor(int i) {
        this.mDescTextView.setTextColor(i);
    }

    public void enableTwinkle() {
        if (!this.mTwinkle) {
            this.mTwinkle = true;
            firstFrame();
        }
    }

    public void disableTwinkle() {
        this.mTwinkle = false;
        stopAnimator();
    }

    public TextView getTextView() {
        return this.mDescTextView;
    }

    public boolean isInWindow() {
        return this.mIsInWindow;
    }

    public void setInitDelay(long j) {
        this.mInitDelay = j;
    }

    public void setIntervalTime(long j) {
        this.mIntervalTime = j;
    }

    public void setDuration(long j) {
        this.mDuration = j;
    }

    public void setHorizonAlign(int i) {
        if (i < -1 || i > 1) {
            StringBuilder sb = new StringBuilder("param ");
            sb.append(i);
            sb.append(" not supported");
            throw new IllegalArgumentException(sb.toString());
        }
        this.mHorizonAlign = i;
    }

    /* access modifiers changed from: private */
    public void startAnimator() {
        if (isInWindow()) {
            clearAnimator();
            this.mTwinkleAnimator = ObjectAnimator.ofPropertyValuesHolder(this.mDescTextView, new PropertyValuesHolder[]{buildPropertyValuesHolder()});
            this.mTwinkleAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    StatusBar1.this.nextFrame();
                    StatusBar1.this.clearAnimator();
                }
            });
            this.mTwinkleAnimator.setDuration(this.mDuration);
            this.mTwinkleAnimator.start();
        }
    }

    private void firstFrame() {
        if (isValidState()) {
            if (this.mTwinkleAnimator == null || !this.mTwinkleAnimator.isRunning()) {
                stopAnimator();
                postDelayed(this.mNextFrameRunnable, this.mInitDelay);
            }
        }
    }

    /* access modifiers changed from: private */
    public void nextFrame() {
        if (isValidState()) {
            postDelayed(this.mNextFrameRunnable, this.mIntervalTime);
        }
    }

    private boolean isValidState() {
        return this.mIsInWindow && this.mTwinkle;
    }

    private void stopAnimator() {
        clearAnimator();
        removeCallbacks(this.mNextFrameRunnable);
    }

    /* access modifiers changed from: private */
    public void clearAnimator() {
        if (this.mTwinkleAnimator != null) {
            this.mTwinkleAnimator.cancel();
            this.mTwinkleAnimator = null;
            this.mDescTextView.setAlpha(1.0f);
        }
    }

    private PropertyValuesHolder buildPropertyValuesHolder() {
        if (this.mPropertyValuesHolder != null) {
            return this.mPropertyValuesHolder;
        }
        this.mPropertyValuesHolder = PropertyValuesHolder.ofKeyframe("alpha", new Keyframe[]{Keyframe.ofFloat(0.0f, 1.0f), Keyframe.ofFloat(0.5f, 0.2f), Keyframe.ofFloat(1.0f, 1.0f)});
        return this.mPropertyValuesHolder;
    }
}
