package com.jiuyan.inimage.e;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: InFaceLabel */
class l implements f {
    private float b;
    private float c;

    public l(float f, float f2) {
        this.b = f;
        this.c = f2;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public g a(c cVar, int i, int i2, int i3, int i4, int i5) {
        b bVar = cVar.b()[i];
        float f = bVar.a;
        float f2 = bVar.b;
        float f3 = bVar.d * 0.9f;
        float f4 = bVar.e;
        float f5 = (float) ((((double) ((90.0f - this.c) - f4)) * 3.141592653589793d) / 180.0d);
        return new g((float) (((double) f) + (((double) (this.b * f3)) * Math.cos((double) f5))), (float) (((double) f2) - (((double) (this.b * f3)) * Math.sin((double) f5))), f4);
    }
}
