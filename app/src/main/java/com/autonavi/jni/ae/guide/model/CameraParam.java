package com.autonavi.jni.ae.guide.model;

public class CameraParam {
    public int[] checkDistance = new int[3];
    public int enable = -858993460;
    public int[] filter = new int[32];
    public int maxCount = -858993460;

    public CameraParam() {
        for (int i = 0; i < this.checkDistance.length; i++) {
            this.checkDistance[i] = -858993460;
        }
        for (int i2 = 0; i2 < this.filter.length; i2++) {
            this.filter[i2] = -858993460;
        }
    }
}
