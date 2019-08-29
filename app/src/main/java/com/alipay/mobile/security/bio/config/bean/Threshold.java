package com.alipay.mobile.security.bio.config.bean;

import java.util.Arrays;

public class Threshold {
    private float[] a;
    private float[] b;

    public float[] getGeminiLiveness() {
        return this.a;
    }

    public void setGeminiLiveness(float[] fArr) {
        this.a = fArr;
    }

    public float[] getDragonflyLiveness() {
        return this.b;
    }

    public void setDragonflyLiveness(float[] fArr) {
        this.b = fArr;
    }

    public String toString() {
        return "Threshold{GeminiLiveness=" + Arrays.toString(this.a) + ", DragonflyLiveness=" + Arrays.toString(this.b) + '}';
    }
}
