package com.alipay.zoloz.hardware.camera;

import android.graphics.PointF;
import android.view.SurfaceHolder;
import com.alipay.mobile.security.faceauth.circle.protocol.DeviceSetting;

/* compiled from: ICameraInterface */
public interface c {
    void closeCamera();

    PointF colorToDepth(PointF pointF);

    int getCameraViewRotation();

    int getColorHeight();

    int getColorWidth();

    int getDepthHeight();

    int getDepthWidth();

    int getPreviewHeight();

    int getPreviewWidth();

    void initCamera(DeviceSetting deviceSetting);

    void setCallback(b bVar);

    void startCamera();

    void startPreview(SurfaceHolder surfaceHolder, float f, int i, int i2);

    void stopCamera();
}
