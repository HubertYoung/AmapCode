package com.ant.phone.imu.sensor;

import android.opengl.Matrix;

public class HeadTransform {
    private final float[] a;

    public HeadTransform() {
        float[] fArr = new float[16];
        this.a = fArr;
        Matrix.setIdentityM(fArr, 0);
    }

    public final float[] a() {
        return this.a;
    }

    public final void a(float[] eulerAngles) {
        if (3 > eulerAngles.length) {
            throw new IllegalArgumentException("Not enough space to write the result");
        }
        Matrix.invertM(this.a, 0, this.a, 0);
        eulerAngles[0] = -((float) Math.atan2((double) this.a[9], (double) this.a[10]));
        eulerAngles[1] = -((float) Math.atan2((double) (-this.a[8]), Math.sqrt((double) ((this.a[9] * this.a[9]) + (this.a[10] * this.a[10])))));
        eulerAngles[2] = -((float) Math.atan2((double) this.a[4], (double) this.a[0]));
    }
}
