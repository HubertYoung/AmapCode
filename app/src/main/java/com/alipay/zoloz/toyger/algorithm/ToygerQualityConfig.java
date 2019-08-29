package com.alipay.zoloz.toyger.algorithm;

public class ToygerQualityConfig {
    public float maxGaussian;
    public float maxMotion;
    public float maxPitch;
    public float maxYaw;
    public float max_iod;
    public float minBrightness;
    public float minFaceWidth;
    public float minIntegrity;
    public float minQuality;
    public float min_iod;
    public int stackSize;
    public int stackTime;

    public ToygerQualityConfig() {
    }

    public ToygerQualityConfig(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i, int i2, float f9, float f10) {
        this.minBrightness = f;
        this.minFaceWidth = f2;
        this.minIntegrity = f3;
        this.maxPitch = f4;
        this.maxYaw = f5;
        this.maxGaussian = f6;
        this.maxMotion = f7;
        this.minQuality = f8;
        this.stackSize = i;
        this.stackTime = i2;
        this.min_iod = f9;
        this.max_iod = f10;
    }

    public String toString() {
        return "ToygerQualityConfig{minBrightness=" + this.minBrightness + ", minFaceWidth=" + this.minFaceWidth + ", minIntegrity=" + this.minIntegrity + ", maxPitch=" + this.maxPitch + ", maxYaw=" + this.maxYaw + ", maxGaussian=" + this.maxGaussian + ", maxMotion=" + this.maxMotion + ", minQuality=" + this.minQuality + ", stackSize=" + this.stackSize + ", stackTime=" + this.stackTime + ", min_iod=" + this.min_iod + ", max_iod=" + this.max_iod + '}';
    }
}
