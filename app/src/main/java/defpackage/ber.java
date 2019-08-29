package defpackage;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/* renamed from: ber reason: default package */
/* compiled from: TabTransitionAnimtation */
public final class ber {
    public static Animation a(float f, float f2) {
        TranslateAnimation translateAnimation = new TranslateAnimation(f, f2, 0.0f, 0.0f);
        translateAnimation.setDuration(375);
        return translateAnimation;
    }

    public static Animation a(Animation... animationArr) {
        AnimationSet animationSet = new AnimationSet(true);
        for (int i = 0; i <= 0; i++) {
            animationSet.addAnimation(animationArr[0]);
        }
        animationSet.setDuration(375);
        animationSet.setInterpolator(new buq());
        return animationSet;
    }
}
