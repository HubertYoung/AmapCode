package com.alipay.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.text.TextUtils;
import com.alipay.camera.open.OpenCameraInterface;
import com.alipay.mobile.bqcscanservice.Logger;
import com.alipay.mobile.bqcscanservice.behavior.BehaviorBury;
import com.alipay.mobile.bqcscanservice.monitor.ScanExceptionHandler.TorchException;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.Map;

public final class CameraManager {
    private final Context a;
    private final CameraConfigurationManager b;
    private Camera c;
    private Parameters d;
    private AutoFocusManager e;
    private boolean f;
    private long g = 2000;
    private int h;
    private final float i = 0.5f;
    private volatile boolean j;
    private Point k;
    private Point l;
    private Point m;
    private Point n;

    public CameraManager(Context context, Parameters cameraParameters, Point screenResolution, Point previewResolution, Point pictureResolution, Point previewViewSize) {
        this.a = context;
        this.k = screenResolution;
        this.l = previewResolution;
        this.m = pictureResolution;
        this.b = new CameraConfigurationManager(context, screenResolution, previewResolution, pictureResolution);
        this.d = cameraParameters;
        this.n = previewViewSize;
    }

    public final void setCameraOpened(Camera cameraOpened) {
        Logger.d("CameraManager", "ScanRect.setCameraOpened: cameraOpened(cameraOpened)");
        this.c = cameraOpened;
    }

    public final int getCameraDisplayOrientation() {
        return this.h;
    }

    public final int getPreviewWidth() {
        Point point = this.l;
        if (point == null) {
            return -1;
        }
        return point.x;
    }

    public final int getPreviewHeight() {
        Point point = this.l;
        if (point == null) {
            return -1;
        }
        return point.y;
    }

    public final int getPictureFormat() {
        if (this.b != null) {
            return this.b.getPreviewFmt();
        }
        return -1;
    }

    public final Point getPreviewResolution() {
        return this.l;
    }

    public final Point getPictureResolution() {
        return this.m;
    }

    public final Point getScreenResolution() {
        return this.k;
    }

    public final void setPreviewTexture(SurfaceTexture surface) {
        if (this.c != null) {
            this.c.setPreviewTexture(surface);
        }
    }

    public final void openDriver() {
        if (this.c == null) {
            Camera theCamera = OpenCameraInterface.open(-1);
            Logger.d("CameraManager", "ScanRect: open camera result: camera=" + theCamera);
            this.c = theCamera;
        }
    }

    public final void setPreviewParameters() {
        Camera theCamera = this.c;
        Logger.d("CameraManager", "Camera Opened, Set Preview Parameters");
        if (this.d == null || this.k == null || this.l == null || this.m == null) {
            this.d = this.b.initFromCameraParameters(theCamera, this.n);
            this.k = this.b.getScreenResolution();
            this.l = this.b.getPreviewSize();
            this.m = this.b.getPictureSize();
        }
        try {
            this.b.setDesiredCameraParameters(theCamera, this.d, OpenCameraInterface.sCameraId);
        } catch (RuntimeException e2) {
            Logger.w("CameraManager", "Camera rejected parameters. Setting only minimal safe-mode parameters");
            Logger.i("CameraManager", "Resetting to saved camera params");
            if (this.d != null) {
                try {
                    this.b.setDesiredCameraParameters(theCamera, this.d, OpenCameraInterface.sCameraId);
                } catch (RuntimeException e3) {
                    Logger.w("CameraManager", "Camera rejected even safe-mode parameters! No configuration");
                }
            }
        }
        Logger.d("CameraManager", "End of Setting Preview Parameters");
        this.h = this.b.getCameraDisplayOrientation();
        this.l = this.b.getPreviewSize();
    }

    public final boolean isOpen() {
        return this.c != null;
    }

    public final void closeDriver() {
        Logger.d("CameraManager", "ScanRect.getCamera: cameraManager(closeDriver)");
        if (this.c != null) {
            this.c.release();
            this.c = null;
        }
    }

    public final void startPreview() {
        Camera theCamera = this.c;
        if (theCamera != null) {
            try {
                if (!this.f) {
                    Logger.d("CameraManager", "start native startPreview()");
                    theCamera.startPreview();
                    Logger.d("CameraManager", "end native startPreview()");
                    this.f = true;
                    if (this.b != null && TextUtils.equals(this.b.getFocusMode(), "auto")) {
                        this.e = new AutoFocusManager(this.a, this.c);
                        this.e.setAutoFocusInterval(this.g);
                        this.e.startAutoFocus();
                    }
                }
            } catch (Exception ex) {
                Logger.e("CameraManager", "startPreview error:" + ex.getMessage());
            }
        }
    }

    public final void refocus() {
        if (this.e != null) {
            this.e.stop();
            this.e.restart();
        }
    }

    public final void stopAutoFocus() {
        if (this.e != null) {
            this.e.stop();
        }
    }

    public final void stopPreview() {
        if (this.e != null) {
            this.e.stop();
            this.e = null;
        }
        if (this.c != null && this.f) {
            this.c.stopPreview();
            this.f = false;
        }
    }

    public final void setTorch(boolean newSetting) {
        try {
            if (newSetting != this.b.getTorchState(this.c) && this.c != null) {
                if (this.e != null) {
                    this.e.stop();
                }
                this.b.setTorch(this.c, newSetting);
                if (this.e != null) {
                    this.e.restart();
                }
            }
        } catch (TorchException e2) {
            throw new TorchException(e2.state, e2.errorCode, e2.getMessage());
        } catch (Exception ex) {
            Logger.e("CameraManager", "maybe light hardware broken ");
            throw new TorchException(newSetting, 4002, ex.getMessage());
        }
    }

    public final void requestPreviewFrameWithBuffer(PreviewCallback previewCallback) {
        Camera theCamera = this.c;
        if (theCamera != null) {
            try {
                theCamera.setPreviewCallbackWithBuffer(previewCallback);
            } catch (Exception e2) {
                Logger.e("CameraManager", e2.getMessage(), e2);
            }
        }
    }

    public final int getZoomParameter() {
        if (this.c != null) {
            return this.c.getParameters().getZoom();
        }
        return -1;
    }

    public final int getMaxZoom() {
        return this.c.getParameters().getMaxZoom();
    }

    public final void setZoomParameter(int zoom) {
        int objectZoom;
        if (this.c != null && !this.j) {
            this.j = true;
            try {
                Parameters p = this.c.getParameters();
                int maxZoom = (int) (((double) (((float) p.getMaxZoom()) * 0.5f)) + 0.5d);
                if (zoom == Integer.MIN_VALUE) {
                    objectZoom = p.getZoom() <= ((int) (((double) maxZoom) * 0.1d)) ? maxZoom : (int) (((double) maxZoom) * 0.1d);
                } else {
                    objectZoom = (int) (((double) p.getZoom()) + (((double) zoom) * 0.01d * ((double) maxZoom)));
                    if (objectZoom >= maxZoom) {
                        objectZoom = maxZoom;
                    }
                    if (objectZoom < ((int) (((double) maxZoom) * 0.1d))) {
                        objectZoom = (int) (((double) maxZoom) * 0.1d);
                    }
                }
                if (p.isZoomSupported()) {
                    p.setZoom(objectZoom);
                    this.c.setParameters(p);
                }
            } catch (Exception e2) {
                BehaviorBury.recordSetZoomException(zoom);
                Logger.e("CameraManager", "SetZoomParameters : " + zoom, e2);
            }
            this.j = false;
        }
    }

    public final Camera getCamera() {
        return this.c;
    }

    public final void setCompatibleRotation(String compatibleRotation) {
        this.b.setCompatibleRotation(compatibleRotation);
    }

    public final Parameters getCameraParameters() {
        return this.d;
    }

    public final void setPreviewSize(int width, int height) {
        if (this.c != null) {
            try {
                Parameters p = this.c.getParameters();
                p.setPreviewSize(width, height);
                this.c.setParameters(p);
                this.l = new Point(width, height);
                Size afterSize = this.c.getParameters().getPreviewSize();
                if (afterSize == null) {
                    return;
                }
                if (this.l.x != afterSize.width || this.l.y != afterSize.height) {
                    Logger.w("CameraManager", "Set preview size failed " + this.l.x + 'x' + this.l.y + ", but after setting it, preview size is " + afterSize.width + 'x' + afterSize.height);
                    this.l.x = afterSize.width;
                    this.l.y = afterSize.height;
                }
            } catch (Exception e2) {
                Logger.e("CameraManager", "setPreviewSize : " + width + DictionaryKeys.CTRLXY_X + height);
            }
        }
    }

    public final void setCameraAbParameters(Map<String, Object> parameters) {
        if (this.b != null && parameters != null) {
            this.b.setCameraAbParameters(parameters);
        }
    }
}
