package com.jiuyan.inimage.widget;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: RotateView */
class z implements Runnable {
    final /* synthetic */ RotateView a;

    z(RotateView rotateView) {
        this.a = rotateView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        this.a.setVisibility(0);
        this.a.h();
    }
}
