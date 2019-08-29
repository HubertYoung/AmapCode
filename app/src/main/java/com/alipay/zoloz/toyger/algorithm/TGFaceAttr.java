package com.alipay.zoloz.toyger.algorithm;

import android.graphics.RectF;

public class TGFaceAttr {
    public float brightness;
    public short distance;
    public boolean eyeBlink;
    public RectF faceRegion;
    public float gaussian;
    public boolean hasFace;
    public float integrity;
    public float leftEyeBlinkRatio;
    public float motion;
    public float pitch;
    public float quality;
    public float rightEyeBlinkRatio;
    public float yaw;

    public TGFaceAttr() {
        this.faceRegion = new RectF();
    }

    public TGFaceAttr(boolean z, boolean z2, RectF rectF, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, short s) {
        this.hasFace = z;
        this.eyeBlink = z2;
        this.faceRegion = rectF;
        this.quality = f;
        this.yaw = f2;
        this.pitch = f3;
        this.gaussian = f4;
        this.motion = f5;
        this.brightness = f6;
        this.integrity = f7;
        this.leftEyeBlinkRatio = f8;
        this.rightEyeBlinkRatio = f9;
        this.distance = s;
    }

    public TGFaceAttr(TGFaceAttr tGFaceAttr) {
        if (tGFaceAttr != null) {
            this.hasFace = tGFaceAttr.hasFace;
            this.eyeBlink = tGFaceAttr.eyeBlink;
            this.faceRegion = new RectF(tGFaceAttr.faceRegion);
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
    }

    public String toString() {
        return "TGFaceAttr{hasFace=" + this.hasFace + ", eyeBlink=" + this.eyeBlink + ", faceRegion=" + this.faceRegion + ", quality=" + this.quality + ", yaw=" + this.yaw + ", pitch=" + this.pitch + ", gaussian=" + this.gaussian + ", motion=" + this.motion + ", brightness=" + this.brightness + ", integrity=" + this.integrity + ", leftEyeBlinkRatio=" + this.leftEyeBlinkRatio + ", rightEyeBlinkRatio=" + this.rightEyeBlinkRatio + ", distance=" + this.distance + '}';
    }
}
