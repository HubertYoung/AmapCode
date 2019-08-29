package com.alipay.mobile.antui.excutor;

import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.view.View;
import org.json.JSONObject;

public interface LottieViewExecutor {
    void addAnimatorListener(View view, AnimatorListener animatorListener);

    void addAnimatorUpdateListener(View view, AnimatorUpdateListener animatorUpdateListener);

    void cancelAnimation(View view);

    View createLottieAnimation(Context context, JSONObject jSONObject);

    long getDuration(View view);

    float getProgress(View view);

    void loop(View view, boolean z);

    void pauseAnimation(View view);

    void playAnimation(View view);

    void setImageAssetsDelegateFolder(View view, String str);

    void setMinAndMaxProgress(View view, float f, float f2);

    void setProgress(View view, float f);

    void setScale(View view, float f);
}
