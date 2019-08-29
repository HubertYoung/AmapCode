package com.alipay.mobile.antui.basic;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;

/* compiled from: AUNetErrorView */
final class x implements AnimatorListener {
    final /* synthetic */ AUNetErrorView a;

    x(AUNetErrorView this$0) {
        this.a = this$0;
    }

    public final void onAnimationStart(Animator animation) {
        this.a.mIcon.setVisibility(8);
    }

    public final void onAnimationEnd(Animator animation) {
    }

    public final void onAnimationCancel(Animator animation) {
    }

    public final void onAnimationRepeat(Animator animation) {
    }
}
