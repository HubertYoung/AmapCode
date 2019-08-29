package defpackage;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;

/* renamed from: dua reason: default package */
/* compiled from: OpenCameraInterface */
public final class dua {
    private static final String a = "dua";

    private dua() {
    }

    public static Camera a(int i) {
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera camera = null;
        if (numberOfCameras == 0) {
            return null;
        }
        boolean z = i >= 0;
        if (!z) {
            i = 0;
            while (i < numberOfCameras) {
                CameraInfo cameraInfo = new CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing == 0) {
                    break;
                }
                i++;
            }
        }
        if (i < numberOfCameras) {
            camera = Camera.open(i);
        } else if (!z) {
            camera = Camera.open(0);
        }
        return camera;
    }
}
