package com.jiuyan.inimage.e;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: InFaceLabel */
class j implements f {
    private float b;
    private float c;

    public j(float f, float f2) {
        this.b = f;
        this.c = f2;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public g a(c cVar, int i, int i2, int i3, int i4, int i5) {
        return new g((float) ((((double) (((float) (i2 - i4)) / 2.0f)) * Math.sin((((double) this.c) / 180.0d) * 3.141592653589793d) * ((double) this.b)) + ((double) (((float) i2) / 2.0f))), (float) (((double) (((float) i3) / 2.0f)) - (((double) this.b) * (((double) (((float) (i3 - i5)) / 2.0f)) * Math.cos((((double) this.c) / 180.0d) * 3.141592653589793d)))), this.c);
    }
}
