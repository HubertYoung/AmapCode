package com.alipay.mobile.antui.basic;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/* compiled from: AUPullLoadingView */
final class af implements AnimationListener {
    final /* synthetic */ AUPullLoadingView a;

    af(AUPullLoadingView this$0) {
        this.a = this$0;
    }

    public final void onAnimationStart(Animation animation) {
    }

    public final void onAnimationRepeat(Animation animation) {
    }

    public final void onAnimationEnd(Animation animation) {
        this.a.clearAnimation();
        if (animation == this.a.mCloseAnimation) {
            if (this.a.mIndicatorUpDrawable != null) {
                this.a.mIndicator.setImageDrawable(this.a.mIndicatorUpDrawable);
            }
        } else if (this.a.mIndicatorDownDrawable != null) {
            this.a.mIndicator.setImageDrawable(this.a.mIndicatorDownDrawable);
            this.a.mIndicatorDownDrawable.setLevel(10000);
        }
    }
}
