package com.alipay.zoloz.toyger.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

/* compiled from: ToygerCirclePattern */
final class j implements AnimatorUpdateListener {
    final /* synthetic */ int a;
    final /* synthetic */ ToygerCirclePattern b;

    j(ToygerCirclePattern toygerCirclePattern, int i) {
        this.b = toygerCirclePattern;
        this.a = i;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.b.mAnimateValue = Integer.parseInt(this.b.mValueAnimator.getAnimatedValue());
        this.b.mRoundProgressBar.setProgress(this.b.mAnimateValue);
        if (this.a == this.b.mAnimateValue) {
            this.b.mIsShowProcess = false;
            this.b.mValueAnimator.cancel();
            this.b.mValueAnimator = null;
        }
    }
}
