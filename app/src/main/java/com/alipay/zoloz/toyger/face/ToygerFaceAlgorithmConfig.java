package com.alipay.zoloz.toyger.face;

import com.alibaba.fastjson.JSON;
import com.alipay.zoloz.toyger.ToygerAlgorithmConfig;
import com.alipay.zoloz.toyger.algorithm.ToygerCameraConfig;
import com.alipay.zoloz.toyger.algorithm.ToygerConfig;
import com.alipay.zoloz.toyger.algorithm.ToygerLivenessConfig;
import com.alipay.zoloz.toyger.algorithm.ToygerQualityConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToygerFaceAlgorithmConfig extends ToygerAlgorithmConfig {
    public static final String BAT_LIVENESS = "BatLiveness";
    public static final String DARK = "dark";
    public static final String DEPTH = "depth";
    public static final String DRAGONFLY_LIVENESS = "DragonflyLiveness";
    public static final String GEMINI_LIVENESS = "GeminiLiveness";
    public static final String NO_LIVENESS = "NoLiveness";
    public static final String PANO = "pano";
    public int blink = 0;
    public float differ;
    public int disWeight = 4;
    public float eyeHwratio = 0.16f;
    public int facesize = 50;
    public List<String> liveness_combination = new ArrayList();
    public String liveness_combinations = NO_LIVENESS;
    public int log_level = 0;
    public float max_iod;
    public float min_iod;
    public int minpose = 0;
    public int mouth = 0;
    public int pitchWeight = 4;
    public int pose_distanceMax = 6000;
    public int pose_distanceMin = 5000;
    public float pose_gaussian = 0.15f;
    public float pose_integrity = 0.9f;
    public float pose_light = 0.16f;
    public float pose_motion = 0.2f;
    public float pose_pitch = 0.2f;
    public float pose_pitchMin = -0.2f;
    public float pose_rectwidth = 0.25f;
    public float pose_yaw = 0.2f;
    public float pose_yawMin = -0.17f;
    public int quality_min_quality = 20;
    public int stack_size = 2;
    public int stack_time = 2;
    public Map<String, Object> threshold = new HashMap<String, Object>() {
    };
    public List<String> upload = new ArrayList();
    public int yawWeight = 1;
    public int yunqiQuality = 4;

    public ToygerFaceAlgorithmConfig() {
        this.upload.add(PANO);
        this.min_iod = 0.18f;
        this.max_iod = 0.45f;
    }

    public static ToygerFaceAlgorithmConfig from(String str) {
        return (ToygerFaceAlgorithmConfig) JSON.parseObject(str, ToygerFaceAlgorithmConfig.class);
    }

    public ToygerConfig toToygerConfig() {
        ToygerQualityConfig toygerQualityConfig = new ToygerQualityConfig(this.pose_light, this.pose_rectwidth, this.pose_integrity, this.pose_pitch, this.pose_yaw, this.pose_gaussian, this.pose_motion, (float) this.quality_min_quality, this.stack_size, this.stack_time, this.min_iod, this.max_iod);
        ToygerLivenessConfig toygerLivenessConfig = new ToygerLivenessConfig(this.liveness_combinations, this.eyeHwratio, this.differ, (float) this.yunqiQuality, 0.0f);
        ToygerConfig toygerConfig = new ToygerConfig();
        toygerConfig.qualityConfig = toygerQualityConfig;
        toygerConfig.livenessConfig = toygerLivenessConfig;
        toygerConfig.cameraConfig = new ToygerCameraConfig();
        return toygerConfig;
    }
}
