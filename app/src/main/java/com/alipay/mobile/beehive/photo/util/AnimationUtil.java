package com.alipay.mobile.beehive.photo.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

public class AnimationUtil implements AnimationListener {
    private Animation animation;
    private OnAnimationEndListener animationEndListener;
    private OnAnimationRepeatListener animationRepeatListener;
    private OnAnimationStartListener animationStartListener;

    public interface OnAnimationEndListener {
        void onAnimationEnd(Animation animation);
    }

    public interface OnAnimationRepeatListener {
        void onAnimationRepeat(Animation animation);
    }

    public interface OnAnimationStartListener {
        void onAnimationStart(Animation animation);
    }

    public AnimationUtil(Context context, int resId) {
        this.animation = AnimationUtils.loadAnimation(context, resId);
        this.animation.setAnimationListener(this);
    }

    public AnimationUtil(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
        this.animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
    }

    public AnimationUtil setStartOffSet(long startOffset) {
        this.animation.setStartOffset(startOffset);
        return this;
    }

    public AnimationUtil setInterpolator(Interpolator i) {
        this.animation.setInterpolator(i);
        return this;
    }

    public AnimationUtil setLinearInterpolator() {
        this.animation.setInterpolator(new LinearInterpolator());
        return this;
    }

    public void startAnimation(View view) {
        if (view.getAnimation() != null) {
            view.getAnimation().cancel();
        }
        view.startAnimation(this.animation);
    }

    public static void startAnimation(int resId, View view) {
        view.setBackgroundResource(resId);
        ((AnimationDrawable) view.getBackground()).start();
    }

    public AnimationUtil setDuration(long durationMillis) {
        this.animation.setDuration(durationMillis);
        return this;
    }

    public AnimationUtil setFillAfter(boolean fillAfter) {
        this.animation.setFillAfter(fillAfter);
        return this;
    }

    public AnimationUtil setOnAnimationEndLinstener(OnAnimationEndListener listener) {
        this.animationEndListener = listener;
        return this;
    }

    public AnimationUtil setOnAnimationStartLinstener(OnAnimationStartListener listener) {
        this.animationStartListener = listener;
        return this;
    }

    public AnimationUtil setOnAnimationRepeatLinstener(OnAnimationRepeatListener listener) {
        this.animationRepeatListener = listener;
        return this;
    }

    public void setAnimationListener(AnimationListener animationListener) {
        this.animation.setAnimationListener(animationListener);
    }

    public void onAnimationStart(Animation animation2) {
        if (this.animationStartListener != null) {
            this.animationStartListener.onAnimationStart(animation2);
        }
    }

    public void onAnimationEnd(Animation animation2) {
        if (this.animationEndListener != null) {
            this.animationEndListener.onAnimationEnd(animation2);
        }
    }

    public void onAnimationRepeat(Animation animation2) {
        if (this.animationRepeatListener != null) {
            this.animationRepeatListener.onAnimationRepeat(animation2);
        }
    }

    public static void fadeInFadeOut(Activity activity) {
        if (activity != null) {
            activity.overridePendingTransition(17432576, 17432577);
        }
    }
}
