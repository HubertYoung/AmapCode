package com.jiuyan.inimage.widget;

import android.util.Log;
import android.view.View;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.util.d;

/* compiled from: PhotoCropViewFreedom */
class o implements Runnable {
    final /* synthetic */ PhotoCropViewFreedom a;
    private final float b;
    private final float c;
    private final float d;
    private final float e;

    public o(PhotoCropViewFreedom photoCropViewFreedom, float f, float f2, float f3, float f4) {
        this.a = photoCropViewFreedom;
        this.d = f2;
        this.b = f3;
        this.c = f4;
        if (f < f2) {
            this.e = 1.07f;
        } else {
            this.e = 0.93f;
        }
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        if (this.a.f) {
            this.a.x.postScale(this.e, this.e, this.b, this.c);
            this.a.i();
            this.a.setImageViewMatrix(this.a.getDisplayMatrix());
            float scale = this.a.getScale();
            if ((this.e <= 1.0f || scale >= this.d) && (this.e >= 1.0f || this.d >= scale)) {
                float f = this.d / scale;
                this.a.x.postScale(f, f, this.b, this.c);
                this.a.i();
                this.a.setImageViewMatrix(this.a.getDisplayMatrix());
                return;
            }
            d.a((View) this.a, (Runnable) this);
        }
    }
}
