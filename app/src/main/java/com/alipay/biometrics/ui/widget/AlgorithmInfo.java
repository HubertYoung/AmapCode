package com.alipay.biometrics.ui.widget;

import android.graphics.RectF;

public class AlgorithmInfo {
    private float brightness;
    private short distance;
    private boolean eyeBlink;
    private RectF faceRegion = new RectF();
    private float gaussian;
    private boolean hasFace;
    private float integrity;
    private float leftEyeBlinkRatio;
    private float motion;
    private float pitch;
    private float quality;
    private float rightEyeBlinkRatio;
    private float yaw;

    public boolean isHasFace() {
        return this.hasFace;
    }

    public void setHasFace(boolean z) {
        this.hasFace = z;
    }

    public boolean isEyeBlink() {
        return this.eyeBlink;
    }

    public void setEyeBlink(boolean z) {
        this.eyeBlink = z;
    }

    public RectF getFaceRegion() {
        return this.faceRegion;
    }

    public void setFaceRegion(RectF rectF) {
        this.faceRegion = rectF;
    }

    public float getQuality() {
        return this.quality;
    }

    public void setQuality(float f) {
        this.quality = f;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float f) {
        this.yaw = f;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float f) {
        this.pitch = f;
    }

    public float getGaussian() {
        return this.gaussian;
    }

    public void setGaussian(float f) {
        this.gaussian = f;
    }

    public float getMotion() {
        return this.motion;
    }

    public void setMotion(float f) {
        this.motion = f;
    }

    public float getBrightness() {
        return this.brightness;
    }

    public void setBrightness(float f) {
        this.brightness = f;
    }

    public float getIntegrity() {
        return this.integrity;
    }

    public void setIntegrity(float f) {
        this.integrity = f;
    }

    public float getLeftEyeBlinkRatio() {
        return this.leftEyeBlinkRatio;
    }

    public void setLeftEyeBlinkRatio(float f) {
        this.leftEyeBlinkRatio = f;
    }

    public float getRightEyeBlinkRatio() {
        return this.rightEyeBlinkRatio;
    }

    public void setRightEyeBlinkRatio(float f) {
        this.rightEyeBlinkRatio = f;
    }

    public short getDistance() {
        return this.distance;
    }

    public void setDistance(short s) {
        this.distance = s;
    }
}
