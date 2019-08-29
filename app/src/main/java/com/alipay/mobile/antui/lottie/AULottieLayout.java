package com.alipay.mobile.antui.lottie;

import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.alipay.mobile.antui.basic.AUFrameLayout;
import com.alipay.mobile.antui.excutor.AntUIExecutorManager;
import com.alipay.mobile.antui.excutor.LottieViewExecutor;
import org.json.JSONObject;

public class AULottieLayout extends AUFrameLayout {
    private View animationView;
    private LottieViewExecutor lottieViewExecutor;

    public AULottieLayout(Context context) {
        this(context, null);
    }

    public AULottieLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AULottieLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.lottieViewExecutor = AntUIExecutorManager.getInstance().getLottieViewExecutor();
    }

    public void setAnimationSource(JSONObject jsonObject) {
        if (this.animationView != null) {
            removeView(this.animationView);
        }
        this.animationView = this.lottieViewExecutor.createLottieAnimation(getContext(), jsonObject);
        addView(this.animationView, new LayoutParams(-1, -1));
    }

    public void setProgress(float progress) {
        this.lottieViewExecutor.setProgress(this.animationView, progress);
        if (getVisibility() != 0) {
            setVisibility(0);
        }
    }

    public void setMinAndMaxProgress(float min, float max) {
        this.lottieViewExecutor.setMinAndMaxProgress(this.animationView, min, max);
    }

    public void loop(boolean isLoop) {
        this.lottieViewExecutor.loop(this.animationView, isLoop);
    }

    public void playAnimation() {
        this.lottieViewExecutor.playAnimation(this.animationView);
        setVisibility(0);
    }

    public void cancelAnimation() {
        this.lottieViewExecutor.cancelAnimation(this.animationView);
        setVisibility(4);
    }

    public void pauseAnimation() {
        this.lottieViewExecutor.pauseAnimation(this.animationView);
    }

    public void setScale(float scale) {
        this.lottieViewExecutor.setScale(this.animationView, scale);
    }

    public void addAnimatorUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        this.lottieViewExecutor.addAnimatorUpdateListener(this.animationView, animatorUpdateListener);
    }

    public void addAnimatorListener(AnimatorListener listener) {
        this.lottieViewExecutor.addAnimatorListener(this.animationView, listener);
    }

    public void setImageAssetsDelegateFolder(String imageFolder) {
        this.lottieViewExecutor.setImageAssetsDelegateFolder(this.animationView, imageFolder);
    }

    public long getDuration() {
        return this.lottieViewExecutor.getDuration(this.animationView);
    }

    public float getProgress() {
        return this.lottieViewExecutor.getProgress(this.animationView);
    }
}
