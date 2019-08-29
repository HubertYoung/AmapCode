package com.jiuyan.inimage.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: CropperView */
class c extends AnimatorListenerAdapter {
    final /* synthetic */ CropperView a;

    c(CropperView cropperView) {
        this.a = cropperView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onAnimationEnd(Animator animator) {
        this.a.setVisibility(8);
        this.a.e();
        if (this.a.b != null) {
            this.a.b.onComponentEvent(Integer.valueOf(1), Integer.valueOf(101), null);
        }
    }

    public void onAnimationCancel(Animator animator) {
        this.a.setVisibility(8);
        this.a.e();
        if (this.a.b != null) {
            this.a.b.onComponentEvent(Integer.valueOf(1), Integer.valueOf(101), null);
        }
    }
}
