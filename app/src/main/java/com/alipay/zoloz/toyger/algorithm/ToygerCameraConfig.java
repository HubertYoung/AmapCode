package com.alipay.zoloz.toyger.algorithm;

import java.util.Arrays;

public class ToygerCameraConfig {
    public float[] color2depthExtrin;
    public float[] colorIntrin;
    public float[] depthIntrin;

    public ToygerCameraConfig() {
    }

    public ToygerCameraConfig(float[] fArr, float[] fArr2, float[] fArr3) {
        this.colorIntrin = fArr;
        this.depthIntrin = fArr2;
        this.color2depthExtrin = fArr3;
    }

    public String toString() {
        return "ToygerCameraConfig{colorIntrin=" + Arrays.toString(this.colorIntrin) + ", depthIntrin=" + Arrays.toString(this.depthIntrin) + ", color2depthExtrin=" + Arrays.toString(this.color2depthExtrin) + '}';
    }
}
