package com.autonavi.gdtaojin.camera;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;

public class CameraInstance {
    public static final String TAG = "gxd_camera";
    private static CameraInstance sHolder;
    private int mBackCameraId = -1;
    private Camera mCameraDevice;
    private int mCameraId = -1;
    private boolean mCameraOpened;
    private int mFrontCameraId = -1;
    private final CameraInfo[] mInfo;
    private final int mNumberOfCameras;
    private Parameters mParameters;

    public static synchronized CameraInstance instance() {
        CameraInstance cameraInstance;
        synchronized (CameraInstance.class) {
            try {
                if (sHolder == null) {
                    sHolder = new CameraInstance();
                }
                cameraInstance = sHolder;
            }
        }
        return cameraInstance;
    }

    private CameraInstance() {
        if (ApiChecker.HAS_GET_CAMERA_NUMBER) {
            this.mNumberOfCameras = 1;
            this.mBackCameraId = 0;
            this.mFrontCameraId = 1;
            this.mInfo = null;
            return;
        }
        this.mNumberOfCameras = Camera.getNumberOfCameras();
        this.mInfo = new CameraInfo[this.mNumberOfCameras];
        for (int i = 0; i < this.mNumberOfCameras; i++) {
            this.mInfo[i] = new CameraInfo();
            Camera.getCameraInfo(i, this.mInfo[i]);
        }
        for (int i2 = 0; i2 < this.mNumberOfCameras; i2++) {
            if (this.mBackCameraId == -1 && this.mInfo[i2].facing == 0) {
                this.mBackCameraId = i2;
            } else if (this.mFrontCameraId == -1 && this.mInfo[i2].facing == 1) {
                this.mFrontCameraId = i2;
            }
        }
    }

    public synchronized Camera open() {
        try {
            if (!(this.mCameraDevice == null || this.mCameraId == this.mBackCameraId)) {
                this.mCameraDevice.release();
                this.mCameraDevice = null;
                this.mCameraId = -1;
            }
            if (this.mCameraDevice == null) {
                if (ApiChecker.AT_LEAST_10) {
                    this.mCameraDevice = Camera.open(this.mBackCameraId);
                } else {
                    this.mCameraDevice = Camera.open();
                }
                if (this.mCameraDevice == null) {
                    return null;
                }
                this.mCameraId = this.mBackCameraId;
            }
            this.mCameraOpened = true;
            return this.mCameraDevice;
        }
    }

    public int getBackCameraId() {
        return this.mBackCameraId;
    }

    public int getFrontCameraId() {
        return this.mFrontCameraId;
    }

    public synchronized void release() {
        if (this.mCameraDevice != null) {
            strongRelease();
        }
    }

    public synchronized void strongRelease() {
        if (this.mCameraDevice != null) {
            this.mCameraOpened = false;
            this.mCameraDevice.release();
            this.mCameraDevice = null;
            this.mParameters = null;
            this.mCameraId = -1;
        }
    }

    public void releaseInstance() {
        sHolder = null;
    }
}
