package com.alipay.mobile.antui.dialog;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/* compiled from: AUImageDialog */
final class u implements AnimationListener {
    final /* synthetic */ AUImageDialog a;

    u(AUImageDialog this$0) {
        this.a = this$0;
    }

    public final void onAnimationStart(Animation animation) {
    }

    public final void onAnimationRepeat(Animation animation) {
    }

    public final void onAnimationEnd(Animation animation) {
        this.a.realDismiss();
    }
}
