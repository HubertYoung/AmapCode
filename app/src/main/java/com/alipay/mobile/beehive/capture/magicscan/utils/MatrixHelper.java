package com.alipay.mobile.beehive.capture.magicscan.utils;

import android.opengl.Matrix;
import java.lang.reflect.Array;

public class MatrixHelper {
    private float[] currMatrix;
    float[] mMVPMatrix = new float[16];
    private float[] mProjMatrix = new float[16];
    private float[] mStMatrix = new float[16];
    private float[][] mStack = ((float[][]) Array.newInstance(Float.TYPE, new int[]{10, 16}));
    private float[] mVMatrix = new float[16];
    private int stackTop = -1;

    public MatrixHelper() {
        setInitStack();
    }

    public void setInitStack() {
        if (this.currMatrix == null) {
            this.currMatrix = new float[16];
        }
        Matrix.setIdentityM(this.currMatrix, 0);
    }

    public void pushMatrix() {
        this.stackTop++;
        for (int i = 0; i < 16; i++) {
            this.mStack[this.stackTop][i] = this.currMatrix[i];
        }
    }

    public void popMatrix() {
        for (int i = 0; i < 16; i++) {
            this.currMatrix[i] = this.mStack[this.stackTop][i];
        }
        this.stackTop--;
    }

    public void translate(float x, float y, float z) {
        Matrix.translateM(this.currMatrix, 0, x, y, z);
    }

    public void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(this.currMatrix, 0, angle, x, y, z);
    }

    public void multiplyRotate(float[] eyeData) {
        Matrix.multiplyMM(this.currMatrix, 0, eyeData, 0, this.mVMatrix, 0);
    }

    public void setCamera(float cx, float cy, float cz, float tx, float ty, float tz, float upx, float upy, float upz) {
        Matrix.setLookAtM(this.mVMatrix, 0, cx, cy, cz, tx, ty, tz, upx, upy, upz);
    }

    public void setPerspective(float fovy, float ratio, float near, float far) {
        Matrix.perspectiveM(this.mProjMatrix, 0, fovy, ratio, near, far);
    }

    public void setOrthoM(float left, float right, float bottom, float top, float near, float far) {
        Matrix.orthoM(this.mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    public float[] getFinalMatrix() {
        Matrix.multiplyMM(this.mMVPMatrix, 0, this.mVMatrix, 0, this.currMatrix, 0);
        Matrix.multiplyMM(this.mMVPMatrix, 0, this.mProjMatrix, 0, this.mMVPMatrix, 0);
        return this.mMVPMatrix;
    }

    public void setSTMatrix(float[] matrix) {
        System.arraycopy(matrix, 0, this.mStMatrix, 0, 16);
    }

    public float[] getSTMatrix() {
        return this.mStMatrix;
    }

    public float[] getMMatrix() {
        return this.currMatrix;
    }
}
