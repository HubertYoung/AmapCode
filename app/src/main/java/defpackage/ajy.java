package defpackage;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/* renamed from: ajy reason: default package */
/* compiled from: DefaultAnimations */
final class ajy implements akb {
    ajy() {
    }

    public final Animation a(ViewGroup viewGroup) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, (float) (-viewGroup.getWidth()), 0.0f, 0.0f);
        translateAnimation.setDuration(300);
        return translateAnimation;
    }

    public final Animation b(ViewGroup viewGroup) {
        TranslateAnimation translateAnimation = new TranslateAnimation((float) viewGroup.getWidth(), 0.0f, 0.0f, 0.0f);
        translateAnimation.setDuration(300);
        return translateAnimation;
    }

    public final Animation c(ViewGroup viewGroup) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, (float) viewGroup.getWidth(), 0.0f, 0.0f);
        translateAnimation.setDuration(300);
        return translateAnimation;
    }

    public final Animation d(ViewGroup viewGroup) {
        TranslateAnimation translateAnimation = new TranslateAnimation((float) (-viewGroup.getWidth()), 0.0f, 0.0f, 0.0f);
        translateAnimation.setDuration(300);
        return translateAnimation;
    }
}
