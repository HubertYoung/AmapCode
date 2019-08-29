package com.alipay.zoloz.toyger.face;

import android.graphics.RectF;
import com.alipay.zoloz.toyger.ToygerAttr;
import com.alipay.zoloz.toyger.algorithm.TGFaceAttr;

public class ToygerFaceAttr extends TGFaceAttr implements ToygerAttr {
    public ToygerFaceAttr() {
    }

    public ToygerFaceAttr(TGFaceAttr tGFaceAttr) {
        this.hasFace = tGFaceAttr.hasFace;
        this.eyeBlink = tGFaceAttr.eyeBlink;
        this.faceRegion = tGFaceAttr.faceRegion;
        this.quality = tGFaceAttr.quality;
        this.yaw = tGFaceAttr.yaw;
        this.pitch = tGFaceAttr.pitch;
        this.gaussian = tGFaceAttr.gaussian;
        this.motion = tGFaceAttr.motion;
        this.brightness = tGFaceAttr.brightness;
        this.integrity = tGFaceAttr.integrity;
        this.leftEyeBlinkRatio = tGFaceAttr.leftEyeBlinkRatio;
        this.rightEyeBlinkRatio = tGFaceAttr.rightEyeBlinkRatio;
        this.distance = tGFaceAttr.distance;
    }

    public boolean hasTarget() {
        return this.hasFace;
    }

    public RectF region() {
        return this.faceRegion;
    }

    public float quality() {
        return this.quality;
    }

    public float gaussian() {
        return this.gaussian;
    }

    public float motion() {
        return this.motion;
    }

    public float brightness() {
        return this.brightness;
    }

    public float integrity() {
        return this.integrity;
    }

    public short distance() {
        return this.distance;
    }

    public boolean eyeBlink() {
        return this.eyeBlink;
    }

    public float yaw() {
        return this.yaw;
    }

    public float pitch() {
        return this.pitch;
    }

    public float leftEyeBlinkRatio() {
        return this.leftEyeBlinkRatio;
    }

    public float rightEyeBlinkRatio() {
        return this.rightEyeBlinkRatio;
    }
}
