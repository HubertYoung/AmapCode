package com.alipay.mobile.bqcscanservice;

public interface BQCScanCallback {
    void onCameraAutoFocus(boolean z);

    void onCameraClose();

    void onCameraFrameRecognized(boolean z, long j);

    void onCameraOpened();

    void onCameraReady();

    void onError(BQCScanError bQCScanError);

    void onOuterEnvDetected(boolean z);

    void onParametersSetted(long j);

    void onPreviewFrameShow();

    void onSurfaceAvaliable();
}
