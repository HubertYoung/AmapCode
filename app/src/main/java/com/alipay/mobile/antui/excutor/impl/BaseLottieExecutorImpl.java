package com.alipay.mobile.antui.excutor.impl;

import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.excutor.LottieViewExecutor;
import org.json.JSONObject;

public class BaseLottieExecutorImpl implements LottieViewExecutor {
    public View createLottieAnimation(Context context, JSONObject fileObject) {
        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.drawable_default_process));
        return progressBar;
    }

    public void setProgress(View view, float progress) {
        if (view instanceof ProgressBar) {
            ((ProgressBar) view).setProgress((int) progress);
        }
    }

    public void setMinAndMaxProgress(View view, float min, float max) {
    }

    public void loop(View view, boolean isLoop) {
        if (view instanceof ProgressBar) {
            ((ProgressBar) view).setIndeterminate(isLoop);
        }
    }

    public void playAnimation(View view) {
        if (view instanceof ProgressBar) {
            view.setVisibility(0);
        }
    }

    public void cancelAnimation(View view) {
        if (view instanceof ProgressBar) {
            view.setVisibility(8);
        }
    }

    public void pauseAnimation(View view) {
    }

    public void setScale(View view, float scale) {
    }

    public void addAnimatorUpdateListener(View view, AnimatorUpdateListener animatorUpdateListener) {
    }

    public void addAnimatorListener(View view, AnimatorListener animatorListener) {
    }

    public void setImageAssetsDelegateFolder(View view, String imageAssetsFolder) {
    }

    public long getDuration(View view) {
        return 0;
    }

    public float getProgress(View view) {
        return 0.0f;
    }
}
