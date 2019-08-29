package com.alipay.mobile.security.faceauth.api;

import android.graphics.Rect;
import android.graphics.RectF;
import com.alipay.mobile.security.faceauth.FaceDetectType;

public abstract class FaceFrame {
    protected FaceInfo a;
    private int b = 0;
    private float c = 0.0f;
    private double d = 0.0d;
    private int e;
    private int f;
    private int g;
    private FaceDetectType h;
    private FaceFrameType i;

    public abstract byte[] getImageData(Rect rect, boolean z, int i2, int i3, boolean z2, boolean z3, int i4);

    public abstract byte[] getYuvData();

    public void setFaceInfo(FaceInfo faceInfo) {
        this.a = faceInfo;
    }

    public synchronized Rect getFaceSize() {
        Rect rect;
        if (this.a == null) {
            rect = null;
        } else {
            rect = this.a.faceSize;
        }
        return rect;
    }

    public boolean isEyeBlink() {
        if (this.a == null || !this.a.eyeBlink) {
            return false;
        }
        return true;
    }

    public boolean isMouthOpen() {
        if (this.a == null || !this.a.mouthOpen) {
            return false;
        }
        return true;
    }

    public RectF getFacePos() {
        if (this.a == null) {
            return null;
        }
        return this.a.position;
    }

    public float getYawAngle() {
        if (this.a == null) {
            return -1.0f;
        }
        return this.a.yaw;
    }

    public float getPitchAngle() {
        if (this.a == null) {
            return -1.0f;
        }
        return this.a.pitch;
    }

    public float getGaussianBlur() {
        if (this.a == null) {
            return -1.0f;
        }
        return this.a.gaussianBlur;
    }

    public float getMouthDet() {
        if (this.a == null) {
            return -1.0f;
        }
        return this.a.mouthDet;
    }

    public float getMotionBlur() {
        if (this.a == null) {
            return -1.0f;
        }
        return this.a.motionBlur;
    }

    public float getBrightness() {
        if (this.a == null) {
            return -1.0f;
        }
        return this.a.brightness;
    }

    public float getFaceQuality() {
        if (this.a == null) {
            return -1.0f;
        }
        return this.a.faceQuality;
    }

    public boolean hasFace() {
        if (this.a != null) {
            return this.a.hasFace;
        }
        return false;
    }

    public float getLeftEyeHwratio() {
        if (this.a == null) {
            return -1.0f;
        }
        return this.a.leftEyeHWRatio;
    }

    public float getRightEyeHwratio() {
        if (this.a == null) {
            return -1.0f;
        }
        return this.a.rightEyeHWRatio;
    }

    @Deprecated
    public float getMouthHwratio() {
        if (this.a == null) {
            return -1.0f;
        }
        return this.a.mouthHWRatio;
    }

    public float getEyeLeftDet() {
        if (this.a == null) {
            return 0.0f;
        }
        return this.a.eyeLeftDet;
    }

    public float getEyeRightDet() {
        if (this.a == null) {
            return 0.0f;
        }
        return this.a.eyeRightDet;
    }

    public float getMouthOcclusion() {
        if (this.a == null) {
            return 0.0f;
        }
        return this.a.mouthOcclussion;
    }

    public float getEyeLeftOcclussion() {
        if (this.a == null) {
            return 0.0f;
        }
        return this.a.eyeLeftOcclussion;
    }

    public float getEyeRightOcclussion() {
        if (this.a == null) {
            return 0.0f;
        }
        return this.a.eyeRightOcclussion;
    }

    public boolean isNoVideo() {
        if (this.a == null) {
            return false;
        }
        return this.a.notVideo;
    }

    public float getIntegrity() {
        if (this.a == null) {
            return 0.0f;
        }
        return this.a.integrity;
    }

    public float getWearGlass() {
        if (this.a == null) {
            return 0.0f;
        }
        return this.a.wearGlass;
    }

    public FaceFrameType getFaceFrameType() {
        return this.i;
    }

    public void setFaceFrameType(FaceFrameType faceFrameType) {
        this.i = faceFrameType;
    }

    public int getDeviceAngle() {
        return this.b;
    }

    public void setDeviceAngle(int i2) {
        this.b = i2;
    }

    public int getYuvWidth() {
        return this.e;
    }

    public void setYuvWidth(int i2) {
        this.e = i2;
    }

    public int getYuvHeight() {
        return this.f;
    }

    public void setYuvHeight(int i2) {
        this.f = i2;
    }

    public int getYuvAngle() {
        return this.g;
    }

    public void setYuvAngle(int i2) {
        this.g = i2;
    }

    public float getDeviceLight() {
        return this.c;
    }

    public void setDeviceLight(float f2) {
        this.c = f2;
    }

    public double getFgyroangle() {
        return this.d;
    }

    public void setFgyroangle(double d2) {
        this.d = d2;
    }

    public FaceDetectType getFaceDetectType() {
        return this.h;
    }

    public void setFaceDetectType(FaceDetectType faceDetectType) {
        this.h = faceDetectType;
    }
}
