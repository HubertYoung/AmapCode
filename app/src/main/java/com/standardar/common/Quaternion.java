package com.standardar.common;

public class Quaternion {
    private float w;
    private float x;
    private float y;
    private float z;

    public Quaternion() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 1.0f;
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 1.0f;
    }

    public Quaternion(Quaternion quaternion) {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 1.0f;
        this.x = quaternion.x;
        this.y = quaternion.y;
        this.z = quaternion.z;
        this.w = quaternion.w;
    }

    public Quaternion(float f, float f2, float f3, float f4) {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 1.0f;
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public Quaternion(float[] fArr) {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 1.0f;
        this.x = fArr[0];
        this.y = fArr[1];
        this.z = fArr[2];
        this.w = fArr[3];
    }

    public void identify() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 1.0f;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public float z() {
        return this.z;
    }

    public float w() {
        return this.w;
    }

    public void xAxis(float[] fArr, int i) {
        rotateVector(this, new float[]{1.0f, 0.0f, 0.0f}, 0, fArr, i);
    }

    public void yAxis(float[] fArr, int i) {
        rotateVector(this, new float[]{0.0f, 1.0f, 0.0f}, 0, fArr, i);
    }

    public void zAxis(float[] fArr, int i) {
        rotateVector(this, new float[]{0.0f, 0.0f, 1.0f}, 0, fArr, i);
    }

    public float[] xAxis() {
        float[] fArr = new float[3];
        rotateVector(this, new float[]{1.0f, 0.0f, 0.0f}, 0, fArr, 0);
        return fArr;
    }

    public float[] yAxis() {
        float[] fArr = new float[3];
        rotateVector(this, new float[]{0.0f, 1.0f, 0.0f}, 0, fArr, 0);
        return fArr;
    }

    public float[] zAxis() {
        float[] fArr = new float[3];
        rotateVector(this, new float[]{0.0f, 0.0f, 1.0f}, 0, fArr, 0);
        return fArr;
    }

    public static void rotateVector(Quaternion quaternion, float[] fArr, int i, float[] fArr2, int i2) {
        float f = fArr[i + 0];
        float f2 = fArr[i + 1];
        float f3 = fArr[i + 2];
        float x2 = quaternion.x();
        float y2 = quaternion.y();
        float z2 = quaternion.z();
        float w2 = quaternion.w();
        float f4 = ((w2 * f) + (y2 * f3)) - (z2 * f2);
        float f5 = ((w2 * f2) + (z2 * f)) - (x2 * f3);
        float f6 = ((w2 * f3) + (x2 * f2)) - (y2 * f);
        float f7 = -x2;
        float f8 = ((f * f7) - (f2 * y2)) - (f3 * z2);
        float f9 = -z2;
        float f10 = -y2;
        fArr2[i2 + 0] = (((f4 * w2) + (f8 * f7)) + (f5 * f9)) - (f6 * f10);
        fArr2[i2 + 1] = (((f5 * w2) + (f8 * f10)) + (f6 * f7)) - (f4 * f9);
        fArr2[i2 + 2] = (((f6 * w2) + (f8 * f9)) + (f4 * f10)) - (f5 * f7);
    }

    public Quaternion inverse() {
        return new Quaternion(-this.x, -this.y, -this.z, this.w);
    }

    public void toMatrix(float[] fArr, int i, int i2) {
        float f = this.x * this.x;
        float f2 = this.x * this.y;
        float f3 = this.x * this.z;
        float f4 = this.x * this.w;
        float f5 = this.y * this.y;
        float f6 = this.y * this.z;
        float f7 = this.y * this.w;
        float f8 = this.z * this.z;
        float f9 = this.z * this.w;
        int i3 = i + 0;
        int i4 = i2 * 0;
        fArr[i3 + i4] = 1.0f - ((f5 + f8) * 2.0f);
        int i5 = i2 * 1;
        fArr[i3 + i5] = (f2 - f9) * 2.0f;
        int i6 = i2 * 2;
        fArr[i3 + i6] = (f3 + f7) * 2.0f;
        int i7 = i + 1;
        fArr[i7 + i4] = (f2 + f9) * 2.0f;
        fArr[i7 + i5] = 1.0f - ((f8 + f) * 2.0f);
        fArr[i7 + i6] = (f6 - f4) * 2.0f;
        int i8 = i + 2;
        fArr[i4 + i8] = (f3 - f7) * 2.0f;
        fArr[i5 + i8] = (f6 + f4) * 2.0f;
        fArr[i8 + i6] = 1.0f - ((f + f5) * 2.0f);
    }
}
