package com.alipay.mobile.security.faceauth.api;

import android.graphics.Rect;
import android.graphics.RectF;

public class FaceInfo {
    public float brightness = 0.0f;
    public boolean eyeBlink = false;
    public float eyeLeftDet = 0.0f;
    public float eyeLeftOcclussion;
    public float eyeRightDet = 0.0f;
    public float eyeRightOcclussion;
    public float faceQuality = 0.0f;
    public Rect faceSize;
    public float gaussianBlur = 0.0f;
    public boolean hasFace;
    public float integrity = 0.0f;
    public float leftEyeHWRatio = 0.0f;
    public float motionBlur = 0.0f;
    public float mouthDet = 0.0f;
    public float mouthHWRatio = 0.0f;
    public float mouthOcclussion;
    public boolean mouthOpen = false;
    public boolean notVideo = false;
    public float pitch = 0.0f;
    public boolean pitch3d = false;
    public RectF position;
    public float rightEyeHWRatio = 0.0f;
    public float smoothPitch = 0.0f;
    public float smoothQuality = 0.0f;
    public float smoothYaw = 0.0f;
    public float wearGlass = 0.0f;
    public float yaw = 0.0f;
}
