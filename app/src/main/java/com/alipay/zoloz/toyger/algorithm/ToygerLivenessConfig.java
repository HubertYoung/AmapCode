package com.alipay.zoloz.toyger.algorithm;

public class ToygerLivenessConfig {
    public float batLivenessThreshold;
    public String collection;
    public float difference;
    public float dragonflyMax = 0.0f;
    public float dragonflyMin = 0.0f;
    public float eyeHwRatio;
    public float geminiMax = 0.0f;
    public float geminiMin = 0.0f;
    public String livenessCombinations;
    public float yunqiQuality;

    public ToygerLivenessConfig() {
    }

    public ToygerLivenessConfig(String str, float f, float f2, float f3, float f4) {
        this.livenessCombinations = str;
        this.eyeHwRatio = f;
        this.difference = f2;
        this.yunqiQuality = f3;
        this.batLivenessThreshold = f4;
    }

    public String toString() {
        return "ToygerLivenessConfig{livenessCombinations='" + this.livenessCombinations + '\'' + ", collection='" + this.collection + '\'' + ", eyeHwRatio=" + this.eyeHwRatio + ", difference=" + this.difference + ", yunqiQuality=" + this.yunqiQuality + ", batLivenessThreshold=" + this.batLivenessThreshold + ", dragonflyMin=" + this.dragonflyMin + ", dragonflyMax=" + this.dragonflyMax + ", geminiMin=" + this.geminiMin + ", geminiMax=" + this.geminiMax + '}';
    }
}
