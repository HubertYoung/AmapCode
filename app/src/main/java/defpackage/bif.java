package defpackage;

import android.view.View;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

/* renamed from: bif reason: default package */
/* compiled from: AnimationFactory */
public final class bif {
    public static void a(View view, int i, AnimationListener animationListener) {
        if (view != null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
            translateAnimation.setDuration((long) i);
            if (animationListener != null) {
                translateAnimation.setAnimationListener(animationListener);
            }
            translateAnimation.setFillAfter(false);
            translateAnimation.setStartOffset(0);
            view.setDrawingCacheEnabled(true);
            view.startAnimation(translateAnimation);
        }
    }

    public static void b(View view, int i, AnimationListener animationListener) {
        if (view != null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
            translateAnimation.setDuration((long) i);
            if (animationListener != null) {
                translateAnimation.setAnimationListener(animationListener);
            }
            translateAnimation.setFillAfter(false);
            translateAnimation.setStartOffset(0);
            view.setDrawingCacheEnabled(true);
            view.startAnimation(translateAnimation);
        }
    }
}
