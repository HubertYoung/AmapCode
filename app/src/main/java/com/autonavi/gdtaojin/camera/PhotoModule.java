package com.autonavi.gdtaojin.camera;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.AutoFocusMoveCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.view.SurfaceHolder;

public class PhotoModule {
    public static final String TAG = "gxd_camera";
    private Camera mCameraDevice;
    public CameraInstance mCameraHolder = null;
    private Parameters mParameters;

    public void init() {
        this.mCameraHolder = CameraInstance.instance();
    }

    public Camera openCamera() {
        if (this.mCameraHolder == null) {
            return null;
        }
        try {
            this.mCameraDevice = this.mCameraHolder.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.mCameraDevice;
    }

    public void onCancelAutoFocus() {
        if (this.mCameraDevice != null && ApiChecker.AT_LEAST_5) {
            this.mCameraDevice.cancelAutoFocus();
        }
    }

    public void onAutoFocus(AutoFocusCallback autoFocusCallback) {
        if (this.mCameraDevice != null) {
            this.mCameraDevice.autoFocus(autoFocusCallback);
        }
    }

    public Parameters getParameters() {
        if (this.mCameraDevice == null) {
            return null;
        }
        return this.mCameraDevice.getParameters();
    }

    public void setParameters(Parameters parameters) {
        if (this.mCameraDevice != null) {
            try {
                this.mCameraDevice.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onStartPreview() {
        if (this.mCameraDevice != null) {
            this.mCameraDevice.startPreview();
        }
    }

    public void onStopPreview() {
        if (this.mCameraDevice != null) {
            this.mCameraDevice.stopPreview();
        }
    }

    public void setDisplayOrientation(int i) {
        if (this.mCameraDevice != null && ApiChecker.AT_LEAST_8) {
            try {
                this.mCameraDevice.setDisplayOrientation(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(16)
    public void setAutoFocusMoveCallBack(AutoFocusMoveCallback autoFocusMoveCallback) {
        if (this.mCameraDevice != null && ApiChecker.HAS_AUTO_FOCUS_MOVE_CALLBACK) {
            this.mCameraDevice.setAutoFocusMoveCallback(autoFocusMoveCallback);
        }
    }

    public void release() {
        if (this.mCameraHolder != null) {
            this.mCameraHolder.release();
        } else {
            CameraInstance.instance().release();
        }
        this.mCameraDevice = null;
    }

    public void onCapture(ShutterCallback shutterCallback, PictureCallback pictureCallback, PictureCallback pictureCallback2) {
        if (this.mCameraDevice != null) {
            try {
                this.mCameraDevice.takePicture(shutterCallback, pictureCallback, pictureCallback2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setPreviewDisplay(SurfaceHolder surfaceHolder) {
        if (this.mCameraDevice != null) {
            try {
                this.mCameraDevice.setPreviewDisplay(surfaceHolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void releaseCameraHolder() {
        if (this.mCameraHolder != null) {
            this.mCameraHolder.releaseInstance();
        }
    }
}
