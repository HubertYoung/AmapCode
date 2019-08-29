package com.alipay.zoloz.toyger.algorithm;

public class ToygerConfig {
    public ToygerCameraConfig cameraConfig;
    public ToygerLivenessConfig livenessConfig;
    public ToygerQualityConfig qualityConfig;

    public ToygerConfig() {
        this.qualityConfig = new ToygerQualityConfig();
        this.livenessConfig = new ToygerLivenessConfig();
        this.cameraConfig = new ToygerCameraConfig();
    }

    public ToygerConfig(ToygerQualityConfig toygerQualityConfig, ToygerLivenessConfig toygerLivenessConfig, ToygerCameraConfig toygerCameraConfig) {
        this.qualityConfig = toygerQualityConfig;
        this.livenessConfig = toygerLivenessConfig;
        this.cameraConfig = toygerCameraConfig;
    }

    public String toString() {
        return "ToygerConfig{qualityConfig=" + this.qualityConfig + ", livenessConfig=" + this.livenessConfig + ", cameraConfig=" + this.cameraConfig + '}';
    }
}
