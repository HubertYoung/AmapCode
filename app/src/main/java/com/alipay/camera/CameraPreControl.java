package com.alipay.camera;

import android.hardware.Camera;
import com.alipay.camera.open.OpenCameraInterface;
import com.alipay.mobile.bqcscanservice.Logger;
import com.alipay.mobile.bqcscanservice.behavior.BehaviorBury;

public class CameraPreControl {
    public static final String TAG = "CameraPreControl";
    private Camera a;

    public void openCamera() {
        try {
            this.a = OpenCameraInterface.open(-1);
        } catch (RuntimeException e) {
            Logger.d(TAG, "CameraPreControl:openCamera" + e.getMessage());
            this.a = null;
        }
        BehaviorBury.recordPreCameraOpenResult(this.a != null);
    }

    public Camera getTheCamera() {
        return this.a;
    }

    public void closeCamera() {
        if (this.a != null) {
            this.a.release();
            this.a = null;
        }
    }
}
