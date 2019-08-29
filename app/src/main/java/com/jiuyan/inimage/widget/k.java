package com.jiuyan.inimage.widget;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.widget.ImageView;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: MagicWandView */
class k implements Runnable {
    final /* synthetic */ MagicWandView a;
    /* access modifiers changed from: private */
    public ImageView b;
    /* access modifiers changed from: private */
    public ObjectAnimator c;

    public k(MagicWandView magicWandView, ImageView imageView) {
        this.a = magicWandView;
        this.b = imageView;
        this.c = ObjectAnimator.ofFloat(imageView, "rotationY", new float[]{0.0f, -90.0f});
        this.c.setDuration(400);
        this.c.addListener(new l(this, magicWandView));
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        if (this.b != null && this.c != null) {
            this.c.start();
        }
    }
}
