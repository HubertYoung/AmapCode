package com.alipay.zoloz.toyger.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

/* compiled from: CircleUploadPattern */
final class b implements AnimatorUpdateListener {
    final /* synthetic */ CircleUploadPattern a;

    b(CircleUploadPattern circleUploadPattern) {
        this.a = circleUploadPattern;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int parseInt = Integer.parseInt(this.a.mValueAnimator.getAnimatedValue());
        this.a.processsAngle = this.a.processsAngle + 4;
        this.a.mUploadProgressBar.setProgressAngle(this.a.processsAngle);
        if (30 == parseInt) {
            this.a.mValueAnimator.cancel();
            this.a.mValueAnimator = null;
        }
    }
}
