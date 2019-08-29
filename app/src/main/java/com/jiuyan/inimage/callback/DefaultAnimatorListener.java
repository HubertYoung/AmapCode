package com.jiuyan.inimage.callback;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.InPhotoEditActivity;

public class DefaultAnimatorListener implements AnimatorListener {
    private InPhotoEditActivity activity;
    private boolean called;
    private int type = 0;

    public DefaultAnimatorListener(InPhotoEditActivity inPhotoEditActivity, int i) {
        this.activity = inPhotoEditActivity;
        this.called = false;
        this.type = i;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onAnimationCancel(Animator animator) {
    }

    public void onAnimationEnd(Animator animator) {
        if (!this.called) {
            this.activity.a(this.type);
            this.called = true;
        }
    }

    public void onAnimationRepeat(Animator animator) {
    }

    public void onAnimationStart(Animator animator) {
    }
}
