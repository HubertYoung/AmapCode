package com.alipay.mobile.security.bio.config.bean;

import com.alipay.zoloz.toyger.face.ToygerFaceAlgorithmConfig;
import java.util.Arrays;

public class Algorithm {
    private int A = 0;
    private float B = 0.05f;
    private String[] C;
    private Threshold D;
    private float a = 0.15f;
    private float b = 0.16f;
    private int c = 0;
    private int d = 0;
    private int e = 50;
    private int f = 4;
    private int g = 0;
    private int h = 4;
    private int i = 1;
    private int j = 4;
    private String k = ToygerFaceAlgorithmConfig.BAT_LIVENESS;
    private float l = 79.79f;
    private float m = 0.2f;
    private float n = 0.25f;
    private float o = 0.2f;
    private float p = 0.17f;
    private float q = 40.0f;
    private float r = 0.9f;
    private float s = 0.15f;
    private float t = 200.0f;
    private float u = 1500.0f;
    private float v = -0.2f;
    private float w = -0.2f;
    private int x = 0;
    private float y = 9.0f;
    private int z = 1;

    public String[] getLiveness_combination() {
        return this.C;
    }

    public void setLiveness_combination(String[] strArr) {
        this.C = strArr;
    }

    public void setPitchWeight(int i2) {
        this.j = i2;
    }

    public int getPitchWeight() {
        return this.j;
    }

    public void setYawWeight(int i2) {
        this.i = i2;
    }

    public int getYawWeight() {
        return this.i;
    }

    public void setDisWeight(int i2) {
        this.h = i2;
    }

    public int getDisWeight() {
        return this.h;
    }

    public void setMinpose(int i2) {
        this.g = i2;
    }

    public int getMinpose() {
        return this.g;
    }

    public void setYunqiQuality(int i2) {
        this.f = i2;
    }

    public int getYunqiQuality() {
        return this.f;
    }

    public void setFacesize(int i2) {
        this.e = i2;
    }

    public int getFacesize() {
        return this.e;
    }

    public void setMouth(int i2) {
        this.d = i2;
    }

    public int getMouth() {
        return this.d;
    }

    public void setBlink(int i2) {
        this.c = i2;
    }

    public int getBlink() {
        return this.c;
    }

    public void setEyeHwratio(float f2) {
        this.b = f2;
    }

    public float getEyeHwratio() {
        return this.b;
    }

    public void setDiffer(float f2) {
        this.a = f2;
    }

    public float getDiffer() {
        return this.a;
    }

    public float getPose_motion() {
        return this.m;
    }

    public void setPose_motion(float f2) {
        this.m = f2;
    }

    public float getPose_rectwidth() {
        return this.n;
    }

    public void setPose_rectwidth(float f2) {
        this.n = f2;
    }

    public float getPose_pitch() {
        return this.o;
    }

    public void setPose_pitch(float f2) {
        this.o = f2;
    }

    public float getPose_yaw() {
        return this.p;
    }

    public void setPose_yaw(float f2) {
        this.p = f2;
    }

    public float getPose_light() {
        return this.q;
    }

    public void setPose_light(float f2) {
        this.q = f2;
    }

    public float getPose_integrity() {
        return this.r;
    }

    public void setPose_integrity(float f2) {
        this.r = f2;
    }

    public float getPose_gaussian() {
        return this.s;
    }

    public void setPose_gaussian(float f2) {
        this.s = f2;
    }

    public float getPose_distanceMax() {
        return this.u;
    }

    public void setPose_distanceMax(float f2) {
        this.u = f2;
    }

    public float getPose_distanceMin() {
        return this.t;
    }

    public void setPose_distanceMin(float f2) {
        this.t = f2;
    }

    public float getPose_pitchMin() {
        return this.w;
    }

    public void setPose_pitchMin(float f2) {
        this.w = f2;
    }

    public float getPose_yawMin() {
        return this.w;
    }

    public void setPose_yawMin(float f2) {
        this.w = f2;
    }

    public float getQuality_min_quality() {
        return this.y;
    }

    public void setQuality_min_quality(float f2) {
        this.y = f2;
    }

    public String getLiveness_combinations() {
        return this.k;
    }

    public void setLiveness_combinations(String str) {
        this.k = str;
    }

    public float getMatching_score() {
        return this.l;
    }

    public void setMatching_score(float f2) {
        this.l = f2;
    }

    public int getLog_level() {
        return this.x;
    }

    public void setLog_level(int i2) {
        this.x = i2;
    }

    public int getStack_size() {
        return this.z;
    }

    public void setStack_size(int i2) {
        this.z = i2;
    }

    public int getStack_time() {
        return this.A;
    }

    public void setStack_time(int i2) {
        this.A = i2;
    }

    public float getBatLivenessThreshold() {
        return this.B;
    }

    public void setBatLivenessThreshold(float f2) {
        this.B = f2;
    }

    public String toString() {
        return "Algorithm{differ=" + this.a + ", eyeHwratio=" + this.b + ", blink=" + this.c + ", mouth=" + this.d + ", facesize=" + this.e + ", yunqiQuality=" + this.f + ", minpose=" + this.g + ", disWeight=" + this.h + ", yawWeight=" + this.i + ", pitchWeight=" + this.j + ", liveness_combinations='" + this.k + '\'' + ", matching_score=" + this.l + ", pose_motion=" + this.m + ", pose_rectwidth=" + this.n + ", pose_pitch=" + this.o + ", pose_yaw=" + this.p + ", pose_light=" + this.q + ", pose_integrity=" + this.r + ", pose_gaussian=" + this.s + ", pose_distanceMin=" + this.t + ", pose_distanceMax=" + this.u + ", pose_yawMin=" + this.v + ", pose_pitchMin=" + this.w + ", log_level=" + this.x + ", quality_min_quality=" + this.y + ", stack_size=" + this.z + ", stack_time=" + this.A + ", batLivenessThreshold=" + this.B + ", liveness_combination=" + Arrays.toString(this.C) + ", threshold=" + this.D + '}';
    }
}
