package com.alipay.zoloz.toyger.face;

import com.alipay.zoloz.toyger.ToygerState;
import com.alipay.zoloz.toyger.algorithm.TGFaceState;

public class ToygerFaceState extends TGFaceState implements ToygerState {
    public ToygerFaceState() {
    }

    public ToygerFaceState(TGFaceState tGFaceState) {
        this.hasFace = tGFaceState.hasFace;
        this.brightness = tGFaceState.brightness;
        this.distance = tGFaceState.distance;
        this.faceInCenter = tGFaceState.faceInCenter;
        this.goodPitch = tGFaceState.goodPitch;
        this.goodYaw = tGFaceState.goodYaw;
        this.isMoving = tGFaceState.isMoving;
        this.goodQuality = tGFaceState.goodQuality;
        this.progress = tGFaceState.progress;
        this.messageCode = tGFaceState.messageCode;
    }

    public boolean hasTarget() {
        return this.hasFace;
    }

    public int brightness() {
        return this.brightness;
    }

    public int distance() {
        return this.distance;
    }

    public boolean isInCenter() {
        return this.faceInCenter;
    }

    public boolean isMoving() {
        return this.isMoving;
    }

    public boolean isGoodQuality() {
        return this.goodQuality;
    }

    @Deprecated
    public int goodPitch() {
        return this.goodPitch;
    }

    @Deprecated
    public int goodYaw() {
        return this.goodYaw;
    }

    public float getProgress() {
        return this.progress;
    }

    public int getMessageCode() {
        return this.messageCode;
    }
}
