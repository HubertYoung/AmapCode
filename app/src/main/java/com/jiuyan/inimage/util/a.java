package com.jiuyan.inimage.util;

import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.view.View;

/* compiled from: AnimationHelper */
public class a {
    public static void a(View view, boolean z, AnimatorListener animatorListener) {
        int i;
        if (z) {
            i = view.getHeight();
        } else {
            i = 0;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationY", new float[]{(float) i});
        ofFloat.setDuration(200);
        if (animatorListener != null) {
            ofFloat.addListener(animatorListener);
        }
        ofFloat.start();
    }

    public static void a(View view, int i) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationY", new float[]{(float) i});
        ofFloat.setDuration(350);
        ofFloat.start();
    }

    public static void b(View view, int i) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "rotationX", new float[]{(float) i});
        ofFloat.setDuration(200);
        ofFloat.start();
    }
}
