package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout.LayoutParams;
import com.alipay.mobile.antui.R;

public class AUPullLoadingView extends AUAbstractPullLoadingView {
    private AnimationListener mAnimationListener;
    /* access modifiers changed from: private */
    public Animation mCloseAnimation;
    /* access modifiers changed from: private */
    public AUImageView mIndicator;
    /* access modifiers changed from: private */
    public Drawable mIndicatorDownDrawable;
    private AUTextView mIndicatorText;
    /* access modifiers changed from: private */
    public Drawable mIndicatorUpDrawable;
    private AUTextView mLoadingText;
    private View mLoadingView;
    private View mNormalView;
    private Animation mOpenAnimation;
    private AUProgressBar mProgressBar;
    private Drawable mProgressDrawable;

    public AUPullLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs, defStyle);
    }

    public AUPullLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs, 0);
    }

    public AUPullLoadingView(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void initAttrs(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AUPullLoadingView, defStyle, R.style.frameworkPullrefreshOverview);
        this.mIndicatorUpDrawable = a.getDrawable(1);
        this.mIndicatorDownDrawable = a.getDrawable(0);
        this.mProgressDrawable = a.getDrawable(2);
        a.recycle();
    }

    public void init() {
        this.mAnimationListener = new af(this);
        this.mOpenAnimation = new RotateAnimation(0.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        this.mOpenAnimation.setInterpolator(new LinearInterpolator());
        this.mOpenAnimation.setDuration(350);
        this.mOpenAnimation.setAnimationListener(this.mAnimationListener);
        this.mOpenAnimation.setFillAfter(true);
        this.mCloseAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        this.mCloseAnimation.setInterpolator(new LinearInterpolator());
        this.mCloseAnimation.setDuration(350);
        this.mCloseAnimation.setFillAfter(true);
        this.mCloseAnimation.setAnimationListener(this.mAnimationListener);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        if (getId() == -1) {
            throw new RuntimeException("must set id");
        }
        this.mNormalView = findViewById(R.id.framework_pullrefresh_normal);
        this.mLoadingView = findViewById(R.id.framework_pullrefresh_loading);
        this.mProgressBar = (AUProgressBar) findViewById(R.id.framework_pullrefresh_progress);
        this.mIndicator = (AUImageView) findViewById(R.id.framework_pullrefresh_indicator);
        this.mIndicatorText = (AUTextView) findViewById(R.id.pullrefresh_indicator_text);
        this.mLoadingText = (AUTextView) findViewById(R.id.pullrefresh_loading_text);
        if (this.mIndicatorUpDrawable == null) {
            this.mIndicatorUpDrawable = getResources().getDrawable(R.drawable.big_progress_bar);
        }
        if (this.mIndicatorDownDrawable == null) {
            this.mIndicatorDownDrawable = getResources().getDrawable(R.drawable.rotate_process_bar);
        }
        if (this.mIndicatorUpDrawable != null) {
            this.mIndicator.setImageDrawable(this.mIndicatorUpDrawable);
        }
        if (this.mProgressDrawable != null) {
            this.mProgressBar.setIndeterminateDrawable(this.mProgressDrawable);
        }
    }

    public void onOpen() {
        this.mIndicator.clearAnimation();
        this.mIndicator.startAnimation(this.mOpenAnimation);
    }

    public void onOver() {
        this.mIndicator.clearAnimation();
        this.mIndicator.startAnimation(this.mCloseAnimation);
    }

    public void onLoad() {
        this.mNormalView.setVisibility(8);
        this.mLoadingView.setVisibility(0);
    }

    public void onFinish() {
        this.mIndicator.clearAnimation();
        this.mNormalView.setVisibility(0);
        this.mLoadingView.setVisibility(8);
    }

    public View getNormalView() {
        return this.mNormalView;
    }

    public int getOverViewHeight() {
        View v = findViewById(R.id.framework_pullrefresh_loading);
        int height = v.getMeasuredHeight();
        if (height > 0) {
            return height;
        }
        v.measure(0, 0);
        return v.getMeasuredHeight();
    }

    public AUView getLoadingShadowView() {
        return (AUView) findViewById(R.id.framework_pullrefresh_shadow_loading);
    }

    public AUView getNormalShadowView() {
        return (AUView) findViewById(R.id.framework_pullrefresh_shadow_normal);
    }

    public AUImageView getLoadingLogo() {
        return (AUImageView) findViewById(R.id.pull_refresh_logo_loading);
    }

    public AUImageView getNormalLogo() {
        return (AUImageView) findViewById(R.id.pull_refresh_logo_normal);
    }

    public void setProgressDrawable(Drawable drawable) {
        if (this.mProgressBar != null) {
            this.mProgressBar.setIndeterminateDrawable(drawable);
        }
    }

    public void setIndicatorDownDrawable(Drawable mIndicatorDownDrawable2) {
        this.mIndicatorDownDrawable = mIndicatorDownDrawable2;
    }

    public void setIndicatorUpDrawable(Drawable mIndicatorUpDrawable2) {
        this.mIndicatorUpDrawable = mIndicatorUpDrawable2;
        if (this.mIndicator != null) {
            this.mIndicator.setImageDrawable(mIndicatorUpDrawable2);
        }
    }

    public void setLoadingText(String loadingText) {
        if (this.mLoadingText != null && !TextUtils.isEmpty(loadingText)) {
            this.mLoadingText.setVisibility(0);
            this.mLoadingText.setText(loadingText);
            LayoutParams layoutParams = (LayoutParams) this.mProgressBar.getLayoutParams();
            layoutParams.width = 54;
            layoutParams.height = 54;
            this.mProgressBar.setLayoutParams(layoutParams);
        } else if (this.mLoadingText != null) {
            this.mLoadingText.setVisibility(8);
            LayoutParams layoutParams2 = (LayoutParams) this.mProgressBar.getLayoutParams();
            layoutParams2.width = 72;
            layoutParams2.height = 72;
            this.mProgressBar.setLayoutParams(layoutParams2);
        }
    }

    public void setIndicatorText(String indicatorText) {
        if (this.mIndicatorText != null && !TextUtils.isEmpty(indicatorText)) {
            LayoutParams layoutParams = (LayoutParams) this.mIndicator.getLayoutParams();
            layoutParams.width = 54;
            layoutParams.height = 54;
            this.mIndicator.setLayoutParams(layoutParams);
            this.mIndicatorText.setVisibility(0);
            this.mIndicatorText.setText(indicatorText);
        } else if (this.mIndicatorText != null) {
            LayoutParams layoutParams2 = (LayoutParams) this.mIndicator.getLayoutParams();
            layoutParams2.width = 72;
            layoutParams2.height = 72;
            this.mIndicator.setLayoutParams(layoutParams2);
            this.mIndicatorText.setVisibility(8);
        }
    }
}
