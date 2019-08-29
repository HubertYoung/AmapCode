package com.alipay.camera.open;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import com.alipay.mobile.bqcscanservice.Logger;

public final class OpenCameraInterface {
    public static final int NO_REQUESTED_CAMERA = -1;
    public static int cameraOrientation;
    public static int sCameraId;

    private OpenCameraInterface() {
    }

    public static Camera open(int cameraId) {
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras == 0) {
            throw new RuntimeException("No Cameras!");
        }
        int indexOfBack = 0;
        while (true) {
            if (indexOfBack >= numCameras) {
                break;
            }
            CameraInfo cameraInfo = new CameraInfo();
            Camera.getCameraInfo(indexOfBack, cameraInfo);
            if (cameraInfo.facing == 0) {
                Logger.d("OpenCameraInterface", "The original orientation of camera is " + cameraInfo.orientation);
                cameraOrientation = cameraInfo.orientation;
                break;
            }
            indexOfBack++;
        }
        if (indexOfBack >= numCameras) {
            Logger.w("OpenCameraInterface", "Requested camera does not exist: " + cameraId);
            cameraOrientation = -1;
            sCameraId = -1;
            throw new RuntimeException("Requested camera does not exist: " + cameraId);
        }
        int cameraId2 = indexOfBack;
        Logger.i("OpenCameraInterface", "Opening camera #" + cameraId2);
        Camera camera = Camera.open(cameraId2);
        sCameraId = cameraId2;
        return camera;
    }
}
