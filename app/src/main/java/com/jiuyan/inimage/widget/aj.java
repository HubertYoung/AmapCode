package com.jiuyan.inimage.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: TextWaterMarkView */
class aj extends AnimatorListenerAdapter {
    final /* synthetic */ TextWaterMarkView a;

    aj(TextWaterMarkView textWaterMarkView) {
        this.a = textWaterMarkView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onAnimationEnd(Animator animator) {
        this.a.setVisibility(8);
        this.a.f();
        if (this.a.b != null) {
            this.a.b.onComponentEvent(Integer.valueOf(3), Integer.valueOf(101), null);
        }
    }

    public void onAnimationCancel(Animator animator) {
        this.a.setVisibility(8);
        this.a.f();
        if (this.a.b != null) {
            this.a.b.onComponentEvent(Integer.valueOf(3), Integer.valueOf(101), null);
        }
    }
}
