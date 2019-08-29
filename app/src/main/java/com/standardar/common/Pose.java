package com.standardar.common;

public class Pose {
    private final Quaternion quat;
    private final float[] translate;

    public Pose() {
        this.quat = new Quaternion();
        this.translate = new float[]{0.0f, 0.0f, 0.0f};
    }

    public Pose(float[] fArr, float[] fArr2) {
        this(fArr[0], fArr[1], fArr[2], fArr2[0], fArr2[1], fArr2[2], fArr2[3]);
    }

    private Pose(float[] fArr, Quaternion quaternion) {
        this.translate = fArr;
        this.quat = quaternion;
    }

    private Pose(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        this.quat = new Quaternion(f4, f5, f6, f7);
        this.translate = new float[]{f, f2, f3};
    }

    public float tx() {
        return this.translate[0];
    }

    public float ty() {
        return this.translate[1];
    }

    public float tz() {
        return this.translate[2];
    }

    public float qx() {
        return this.quat.x();
    }

    public float qy() {
        return this.quat.y();
    }

    public float qz() {
        return this.quat.z();
    }

    public float qw() {
        return this.quat.w();
    }

    /* access modifiers changed from: 0000 */
    public Quaternion getQuaternion() {
        return this.quat;
    }

    public void getTranslation(float[] fArr, int i) {
        fArr[i + 0] = this.translate[0];
        fArr[i + 1] = this.translate[1];
        fArr[i + 2] = this.translate[2];
    }

    public void getRotationQuaternion(float[] fArr, int i) {
        fArr[i + 0] = this.quat.x();
        fArr[i + 1] = this.quat.y();
        fArr[i + 2] = this.quat.z();
        fArr[i + 3] = this.quat.w();
    }

    public void getXAxis(float[] fArr, int i) {
        this.quat.xAxis(fArr, i);
    }

    public void getYAxis(float[] fArr, int i) {
        this.quat.yAxis(fArr, i);
    }

    public void getZAxis(float[] fArr, int i) {
        this.quat.zAxis(fArr, i);
    }

    public float[] getXAxis() {
        return this.quat.xAxis();
    }

    public float[] getYAxis() {
        return this.quat.yAxis();
    }

    public float[] getZAxis() {
        return this.quat.zAxis();
    }

    public Pose inverse() {
        float[] fArr = new float[3];
        Quaternion inverse = this.quat.inverse();
        Quaternion.rotateVector(inverse, this.translate, 0, fArr, 0);
        fArr[0] = -fArr[0];
        fArr[1] = -fArr[1];
        fArr[2] = -fArr[2];
        return new Pose(fArr, inverse);
    }

    public void getTransformedAxis(int i, float f, float[] fArr, int i2) {
        switch (i) {
            case 0:
                getXAxis(fArr, i2);
                break;
            case 1:
                getYAxis(fArr, i2);
                break;
            case 2:
                getZAxis(fArr, i2);
                break;
        }
        int i3 = i2 + 0;
        fArr[i3] = fArr[i3] * f;
        int i4 = i2 + 1;
        fArr[i4] = fArr[i4] * f;
        int i5 = i2 + 2;
        fArr[i5] = fArr[i5] * f;
    }

    public void toMatrix(float[] fArr, int i) {
        this.quat.toMatrix(fArr, i, 4);
        fArr[i + 0 + 12] = this.translate[0];
        fArr[i + 1 + 12] = this.translate[1];
        fArr[i + 2 + 12] = this.translate[2];
        fArr[i + 3] = 0.0f;
        fArr[i + 7] = 0.0f;
        fArr[i + 11] = 0.0f;
        fArr[i + 15] = 1.0f;
    }
}
