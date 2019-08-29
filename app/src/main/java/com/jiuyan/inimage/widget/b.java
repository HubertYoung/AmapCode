package com.jiuyan.inimage.widget;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: CropperView */
class b implements Runnable {
    final /* synthetic */ CropperView a;

    b(CropperView cropperView) {
        this.a = cropperView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        this.a.setVisibility(0);
        this.a.i();
    }
}
