package com.jiuyan.inimage.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;

/* compiled from: MagicWandView */
class l extends AnimatorListenerAdapter {
    final /* synthetic */ MagicWandView a;
    final /* synthetic */ k b;

    l(k kVar, MagicWandView magicWandView) {
        this.b = kVar;
        this.a = magicWandView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onAnimationEnd(Animator animator) {
        this.b.b.setImageResource(R.drawable.in_alipay_icon_magic);
        this.b.b.setRotationY(-270.0f);
        this.b.c = ObjectAnimator.ofFloat(this.b.b, "rotationY", new float[]{-270.0f, -360.0f});
        this.b.c.setDuration(400);
        this.b.c.start();
    }
}
