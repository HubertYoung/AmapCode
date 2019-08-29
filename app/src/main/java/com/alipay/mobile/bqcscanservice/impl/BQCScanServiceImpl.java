package com.alipay.mobile.bqcscanservice.impl;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.camera.CameraManager;
import com.alipay.camera.CameraPreControl;
import com.alipay.camera.util.FocusWhiteList;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.bqcscanservice.BQCCameraParam.CameraConfigType;
import com.alipay.mobile.bqcscanservice.BQCCameraParam.CameraPropertyParam;
import com.alipay.mobile.bqcscanservice.BQCCameraParam.ConfigParam;
import com.alipay.mobile.bqcscanservice.BQCCameraParam.MaEngineType;
import com.alipay.mobile.bqcscanservice.BQCCameraParam.ServiceConfig;
import com.alipay.mobile.bqcscanservice.BQCScanCallback;
import com.alipay.mobile.bqcscanservice.BQCScanEngine;
import com.alipay.mobile.bqcscanservice.BQCScanEngine.EngineCallback;
import com.alipay.mobile.bqcscanservice.BQCScanError;
import com.alipay.mobile.bqcscanservice.BQCScanError.ErrorType;
import com.alipay.mobile.bqcscanservice.BQCScanService;
import com.alipay.mobile.bqcscanservice.CameraHandler;
import com.alipay.mobile.bqcscanservice.Logger;
import com.alipay.mobile.bqcscanservice.behavior.BehaviorBury;
import com.alipay.mobile.bqcscanservice.executor.ScanRecognizedExecutor;
import com.alipay.mobile.bqcscanservice.monitor.ScanCodeState;
import com.alipay.mobile.bqcscanservice.monitor.ScanExceptionHandler;
import com.alipay.mobile.bqcscanservice.monitor.ScanExceptionHandler.TorchException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BQCScanServiceImpl extends BQCScanService {
    private CameraManager a = null;
    /* access modifiers changed from: private */
    public BQCScanController b = null;
    private TextureView c = null;
    /* access modifiers changed from: private */
    public SurfaceTexture d = null;
    private SurfaceTextureListener e;
    /* access modifiers changed from: private */
    public volatile long f = 0;
    /* access modifiers changed from: private */
    public volatile long g = 0;
    private Parameters h;
    private Point i;
    private Point j;
    private Point k;
    private boolean l;
    private ScanCodeState m;
    protected Map<String, Object> mEngineParameters;
    private CameraPreControl n;
    private Point o;
    private boolean p;
    protected long postcode;
    /* access modifiers changed from: private */
    public volatile boolean q = true;
    /* access modifiers changed from: private */
    public volatile boolean r = false;
    private boolean s = false;
    private boolean t = false;
    private boolean u = false;
    /* access modifiers changed from: private */
    public boolean v = false;

    class BQCSurfaceCallback implements SurfaceTextureListener {
        private BQCSurfaceCallback() {
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            Logger.d("BQCScanServiceImpl", "BQCSurfaceCallback:onSurfaceTextureAvailable(): surface: " + surface + ", width: " + width + ", height: " + height);
            BQCScanServiceImpl.this.d = surface;
            if (BQCScanServiceImpl.this.b != null) {
                BQCScanServiceImpl.this.b.reportSurfaceViewAvailable();
            }
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            Logger.d("BQCScanServiceImpl", "onSurfaceTextureSizeChanged: " + BQCScanServiceImpl.this.d);
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            Logger.d("BQCScanServiceImpl", "onSurfaceTextureDestroyed: " + surface);
            return true;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            Logger.p("BQCScanServiceImpl", "onSurfaceTextureUpdated(" + surface + "), frameShowReported is " + BQCScanServiceImpl.this.v);
            BQCScanServiceImpl.this.f = BQCScanServiceImpl.this.f + 10;
            if (!BQCScanServiceImpl.this.v && BQCScanServiceImpl.this.r) {
                try {
                    if (BQCScanServiceImpl.this.b != null) {
                        BQCScanServiceImpl.this.b.reportPreviewFrameShow();
                        BQCScanServiceImpl.this.v = true;
                    }
                } catch (Exception e) {
                    Logger.e("BQCScanServiceImpl", e.getMessage());
                }
            }
        }
    }

    public void preOpenCamera() {
        if (this.cameraHandler != null) {
            this.cameraHandler.preOpenCamera();
        }
    }

    /* access modifiers changed from: protected */
    public void tryPreOpenCamera() {
        this.n = new CameraPreControl();
        this.n.openCamera();
    }

    public void postCloseCamera() {
        if (this.cameraHandler != null) {
            this.cameraHandler.postCloseCamera();
        }
    }

    /* access modifiers changed from: protected */
    public void tryPostCloseCamera() {
        if (this.n != null) {
            this.n.closeCamera();
        }
    }

    public void setup(Context ctx, BQCScanCallback callback) {
        Logger.d("BQCScanServiceImpl", "setup()");
        if (ctx != null) {
            this.a = new CameraManager(ctx, this.h, this.i, this.j, this.k, this.o);
            if (!(this.n == null || this.n.getTheCamera() == null)) {
                this.a.setCameraOpened(this.n.getTheCamera());
            }
            this.b = new BQCScanController(ctx, this.mEngineParameters, this.cameraHandler, this.firstSetup);
            this.b.setResultCallback(callback);
            this.e = new BQCSurfaceCallback();
            this.b.reportParametersSet(this.postcode);
            ScanRecognizedExecutor.open();
            if (this.l && this.m != null) {
                this.m.reset();
            }
            this.p = false;
        }
    }

    public void setPreviewCallback() {
        Logger.d("BQCScanServiceImpl", "setPreviewCallback()");
        if (this.a != null && this.a.getCamera() != null) {
            int previewWidth = this.a.getPreviewWidth();
            int previewHeight = this.a.getPreviewHeight();
            int pictureFormat = this.a.getPictureFormat();
            if (previewWidth != -1 && previewHeight != -1 && pictureFormat != -1) {
                try {
                    int bufferSize = ((previewWidth * previewHeight) * ImageFormat.getBitsPerPixel(pictureFormat)) / 8;
                    byte[] buffer = new byte[bufferSize];
                    this.a.getCamera().addCallbackBuffer(buffer);
                    byte[] buffer2 = null;
                    if (!this.p) {
                        buffer2 = new byte[bufferSize];
                    }
                    this.b.setCameraBuffers(buffer, buffer2);
                    Logger.d("BQCScanServiceImpl", "requestPreviewFrameWithBuffer");
                    this.a.requestPreviewFrameWithBuffer(this.b);
                } catch (Throwable t2) {
                    Logger.e("BQCScanServiceImpl", "setPreviewCallback error: " + t2.getMessage());
                }
            }
        }
    }

    public void changeCameraFeature(CameraConfigType type, Object... params) {
        int previewHeight;
        if (CameraConfigType.SET_PREVIEW_SIZE.equals(type) && params != null && params.length > 1) {
            int previewWidth = params[0] != null ? params[0].intValue() : -1;
            if (params[1] != null) {
                previewHeight = params[1].intValue();
            } else {
                previewHeight = -1;
            }
            if (previewWidth > 0 && previewHeight > 0) {
                Logger.d("BQCScanServiceImpl", "setPreviewSize(): width=" + previewWidth + ", height=" + previewHeight);
                if (this.a != null && this.a.getCamera() != null) {
                    this.a.requestPreviewFrameWithBuffer(null);
                    this.a.getCamera().stopPreview();
                    this.a.setPreviewSize(previewWidth, previewHeight);
                    this.b.resetPreviewSize();
                    this.a.getCamera().startPreview();
                }
            }
        }
    }

    public void reconnectCamera() {
        if (this.d != null) {
            Camera camera = getCamera();
            if (this.a != null && camera != null) {
                Logger.d("BQCScanServiceImpl", "reconnectCamera");
                try {
                    this.a.setPreviewTexture(this.d);
                    setPreviewCallback();
                    camera.startPreview();
                } catch (Exception e2) {
                    Logger.d("BQCScanServiceImpl", "reconnectCamera Exception : " + e2.getMessage());
                    if (this.l && this.m != null) {
                        this.m.setPreviewFailed(ScanExceptionHandler.getPreviewErrorCode(ScanExceptionHandler.PREVIEW_RECONNECT_CAMERA));
                    }
                }
            }
        }
    }

    public void refocus() {
        if (this.a != null) {
            this.a.refocus();
        }
    }

    public void stopAutoFocus() {
        if (this.a != null) {
            this.a.stopAutoFocus();
        }
    }

    public void enableCameraOpenWatcher(boolean enabled) {
        Logger.d("BQCScanServiceImpl", "enableCameraOpenWatcher: enabled=" + enabled);
        this.q = enabled;
    }

    public void setDisplay(TextureView view) {
        if (view == null) {
            TextureView localTextureView = this.c;
            if (localTextureView != null) {
                localTextureView.setSurfaceTextureListener(null);
                return;
            }
            return;
        }
        Logger.d("BQCScanServiceImpl", "setDisplay():surfaceCallback:" + this.e);
        view.setSurfaceTextureListener(this.e);
        boolean isSurfaceReady = view.isAvailable();
        if (isSurfaceReady) {
            this.d = view.getSurfaceTexture();
        } else {
            this.d = null;
        }
        Logger.d("BQCScanServiceImpl", "setDisplay():texture.isAvailable()" + isSurfaceReady + "surfaceTexture is " + this.d);
        this.c = view;
    }

    public void regScanEngine(String type, Class<? extends BQCScanEngine> engine, EngineCallback engineCallback) {
        Logger.d("BQCScanServiceImpl", "regScanEngine()");
        if (this.b != null) {
            this.b.regScanEngine(type, engine, engineCallback);
        }
    }

    public boolean setScanType(String type) {
        return setScanType(type, null);
    }

    public boolean setScanType(String type, MaEngineType subType) {
        boolean z;
        Logger.d("BQCScanServiceImpl", "setScanType(" + type + ", " + subType + ")");
        synchronized (this) {
            if (this.a == null || this.b == null) {
                z = false;
            } else {
                z = this.b.setScanType(type, subType);
            }
        }
        return z;
    }

    public void startPreview() {
        if (this.a == null) {
            Logger.e("BQCScanServiceImpl", "startPreview(): cameraManager is null");
        } else if (this.s) {
            Logger.e("BQCScanServiceImpl", "startPreview(): camera is previewing");
        } else {
            this.s = true;
            String msg = null;
            this.f = 0;
            this.g = System.currentTimeMillis();
            try {
                this.a.openDriver();
                if (this.b != null) {
                    this.b.reportCameraOpened();
                }
                this.a.setPreviewParameters();
                setPreviewCallback();
                this.r = true;
                this.h = this.a.getCameraParameters();
                this.i = this.a.getScreenResolution();
                this.j = this.a.getPreviewResolution();
                this.k = this.a.getPictureResolution();
                if (this.d != null) {
                    onSurfaceAvailable();
                }
                if (this.q) {
                    final int terminate_duration = this.firstSetup ? 20 : 10;
                    new Thread(new Runnable() {
                        public void run() {
                            int seconds = 0;
                            long bqcCode = BQCScanServiceImpl.this.g;
                            do {
                                try {
                                    TimeUnit.SECONDS.sleep(2);
                                    seconds += 2;
                                } catch (Exception e) {
                                    Logger.e("BQCScanServiceImpl", e.getMessage(), e);
                                }
                            } while (seconds < terminate_duration);
                            if (!BQCScanServiceImpl.this.q) {
                                Logger.d("BQCScanServiceImpl", "enableCameraOpenWatcher is false, not check camera open status");
                                return;
                            }
                            Logger.d("BQCScanServiceImpl", "The Postcode is " + BQCScanServiceImpl.this.g + ", the bqcCode is " + bqcCode + ", the statisticCamera is " + BQCScanServiceImpl.this.f);
                            if (bqcCode == BQCScanServiceImpl.this.g && BQCScanServiceImpl.this.f == 0 && BQCScanServiceImpl.this.b != null) {
                                try {
                                    BQCScanServiceImpl.this.b.reportError(new BQCScanError(ErrorType.CameraOpenError, "preview_error"));
                                } catch (Exception e2) {
                                    Logger.d("BQCScanServiceImpl", "reportError: " + e2.getMessage());
                                }
                            }
                        }
                    }).start();
                }
                if (this.a == null || !this.a.isOpen()) {
                    this.r = false;
                    Logger.e("BQCScanServiceImpl", "camera open false");
                }
                if (!this.r) {
                    this.s = false;
                    this.h = null;
                    this.i = null;
                    this.j = null;
                    this.k = null;
                    if (this.b != null && this.q) {
                        this.b.reportError(new BQCScanError(ErrorType.CameraOpenError, ""));
                    }
                }
            } catch (Throwable th) {
                if (this.a == null || !this.a.isOpen()) {
                    this.r = false;
                    Logger.e("BQCScanServiceImpl", "camera open false");
                }
                if (!this.r) {
                    this.s = false;
                    this.h = null;
                    this.i = null;
                    this.j = null;
                    this.k = null;
                    if (this.b != null && this.q) {
                        BQCScanController bQCScanController = this.b;
                        ErrorType errorType = ErrorType.CameraOpenError;
                        if (0 == 0) {
                            msg = "";
                        }
                        bQCScanController.reportError(new BQCScanError(errorType, msg));
                    }
                }
                throw th;
            }
        }
    }

    public void onSurfaceAvailable() {
        Logger.d("BQCScanServiceImpl", "onSurfaceAvailable:surfaceTexture:" + (this.d == null) + ", is surfaceAvailable " + this.d + ", surfaceAlreadySet:" + this.u);
        if (this.d != null && !this.u && this.a != null && this.r) {
            this.u = true;
            try {
                this.a.setPreviewTexture(this.d);
                this.a.startPreview();
                if (this.b != null) {
                    this.b.reportCameraReady();
                }
            } catch (Exception e2) {
                Logger.e("BQCScanServiceImpl", "Set Preview Exception : " + e2.getMessage());
                if (this.l && this.m != null) {
                    this.m.setPreviewFailed(ScanExceptionHandler.getPreviewErrorCode(ScanExceptionHandler.PREVIEW_START_CAMERA));
                }
            }
        }
    }

    public void setEngineParameters(Map<String, Object> parameters) {
        this.mEngineParameters = parameters;
        if (this.b != null) {
            this.b.setEngineParams(this.mEngineParameters);
        }
    }

    public int getCameraDisplayOrientation() {
        int i2 = 0;
        if (this.a == null) {
            return i2;
        }
        try {
            return this.a.getCameraDisplayOrientation();
        } catch (Exception e2) {
            return i2;
        }
    }

    public void stopPreview() {
        synchronized (this) {
            this.g = 0;
            if (this.b != null) {
                this.b.setScanEnable(false);
                if (this.l && this.m != null) {
                    this.m.setRecognizeFailed(this.b.getScanResultMonitor());
                }
            }
            if (this.a != null) {
                try {
                    this.a.requestPreviewFrameWithBuffer(null);
                    this.a.stopPreview();
                    this.u = false;
                    this.d = null;
                    this.a.closeDriver();
                } catch (Throwable t2) {
                    Logger.e("BQCScanServiceImpl", "camera stopPreview error: " + t2.getMessage());
                }
            }
            this.r = false;
            this.s = false;
            this.v = false;
            this.t = false;
            this.f = 0;
            if (this.l && this.m != null) {
                BehaviorBury.recordScanDiagnose(this.m);
                this.m.reset();
            }
            if (this.b != null) {
                this.b.reportCameraClosed();
            }
        }
    }

    public void cleanup(long postcode2) {
        if (postcode2 == this.postcode) {
            this.firstSetup = false;
            this.n = null;
            this.a = null;
            if (this.b != null) {
                this.b.setResultCallback(null);
                this.b.destroy();
                this.b = null;
            }
            if (this.c != null) {
                this.c.setSurfaceTextureListener(null);
                this.c = null;
            }
            this.d = null;
            this.u = false;
            this.e = null;
            this.r = false;
            this.s = false;
            this.t = false;
            ScanRecognizedExecutor.close();
            if (this.l && this.m != null) {
                this.m.reset();
            }
            this.p = false;
            Logger.updateAll();
        }
    }

    public boolean isPreviewing() {
        throw new UnsupportedOperationException("Do not use this");
    }

    public void setScanEnable(boolean enable) {
        try {
            if (this.a != null && this.b != null) {
                this.b.setScanEnable(enable);
            }
        } catch (Exception e2) {
            Logger.e("BQCScanServiceImpl", e2.getMessage());
        }
    }

    public boolean isScanEnable() {
        if (this.b != null) {
            return this.b.isScanEnable();
        }
        return false;
    }

    public void setScanRegion(Rect region) {
        setScanRegion(region, this.o);
    }

    public void setScanRegion(Rect region, Point previewViewSize) {
        this.o = previewViewSize;
        if (this.a != null && this.b != null) {
            this.b.setScanRegion(region);
        }
    }

    public void setTorch(boolean on) {
        if (this.a != null && this.a.isOpen()) {
            try {
                this.a.setTorch(on);
                this.t = on;
            } catch (TorchException e2) {
                if (this.l && this.m != null) {
                    this.m.setTorchFailed(e2.state, e2.errorCode);
                }
            } catch (Exception e3) {
                Logger.e("BQCScanServiceImpl", "setTorch exception");
                if (this.l && this.m != null) {
                    this.m.setTorchFailed(on, 4003);
                }
            }
        }
    }

    public boolean isTorchOn() {
        Camera camera = getCamera();
        if (camera == null) {
            return this.t;
        }
        if (CameraParams.FLASH_MODE_OFF.equalsIgnoreCase(camera.getParameters().getFlashMode())) {
            return false;
        }
        return true;
    }

    public int getCurrentZoom() {
        if (this.a == null || !this.a.isOpen()) {
            return -1;
        }
        return this.a.getZoomParameter();
    }

    public void setZoom(int zoom) {
        if (this.a != null && this.a.isOpen()) {
            try {
                this.a.setZoomParameter(zoom);
            } catch (Exception e2) {
                Logger.e("BQCScanServiceImpl", "setZoom exception");
            }
        }
    }

    public int getMaxZoom() {
        if (this.a != null && this.a.isOpen()) {
            try {
                return this.a.getMaxZoom();
            } catch (Exception e2) {
                Logger.e("BQCScanServiceImpl", "getMaxZoom exception");
            }
        }
        return -1;
    }

    public void setCameraParam(String key, Object value) {
        if (!TextUtils.isEmpty(key)) {
            if (key.equalsIgnoreCase(ConfigParam.KEY_COMPATIBLE_ROTATION)) {
                if (this.a != null && (value instanceof String)) {
                    this.a.setCompatibleRotation((String) value);
                }
            } else if (TextUtils.equals(key, ConfigParam.KEY_CONTINUOUS_FOCUS_MODEL) && (value instanceof String)) {
                FocusWhiteList.refreshConfigList((String) value);
            } else if (!TextUtils.equals(key, ConfigParam.KEY_AB_CAMERA_PARAMS) || !(value instanceof Map)) {
                if (TextUtils.equals(key, ConfigParam.KEY_ENABLE_FRAME_RECOGNIZED_CALLBACK)) {
                    if (this.b != null) {
                        this.b.setEnableFrameRecogCallback(TextUtils.equals((CharSequence) value, "yes"));
                    }
                } else if (TextUtils.equals(key, ConfigParam.KEY_NOT_USE_DOUBLE_BUFFER) && (value instanceof String) && TextUtils.equals((String) value, "yes")) {
                    this.p = true;
                }
            } else if (this.a != null) {
                this.a.setCameraAbParameters((Map) value);
            }
        }
    }

    public Object getCameraParam(String key) {
        if (TextUtils.equals(key, CameraPropertyParam.PREVIEW_HEIGHT)) {
            if (this.a == null) {
                return Integer.valueOf(-1);
            }
            try {
                return Integer.valueOf(this.a.getPreviewHeight());
            } catch (Exception e2) {
                return Integer.valueOf(-1);
            }
        } else if (TextUtils.equals(key, CameraPropertyParam.PREVIEW_WIDTH)) {
            if (this.a == null) {
                return Integer.valueOf(-1);
            }
            try {
                return Integer.valueOf(this.a.getPreviewWidth());
            } catch (Exception e3) {
                return Integer.valueOf(-1);
            }
        } else if (TextUtils.equals(key, CameraPropertyParam.FRAME_GAP)) {
            return Integer.valueOf(-1);
        } else {
            return null;
        }
    }

    public void setCameraId(int id) {
    }

    public Camera getCamera() {
        Logger.d("BQCScanServiceImpl", "ScanRect.getCamera: cameraManager(" + this.a + ")");
        if (this.a == null) {
            return null;
        }
        Logger.d("BQCScanServiceImpl", "ScanRect.getCamera: cameraManager(" + this.a.getCamera() + ")");
        return this.a.getCamera();
    }

    public boolean checkEngineRegister(String type) {
        if (this.b != null) {
            return this.b.checkEngineRegister(type);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.l = true;
        if (bundle != null && TextUtils.equals(bundle.getString(ServiceConfig.KEY_NOT_USE_SCAN_CODE_STATE, BQCCameraParam.VALUE_NO), "yes")) {
            this.l = false;
        }
        this.cameraHandler = new CameraHandler();
        this.cameraHandler.setBqcScanService(this);
        this.postcode = 0;
        this.h = null;
        this.i = null;
        this.k = null;
        this.j = null;
        if (this.l && this.m == null) {
            this.m = new ScanCodeState();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        this.cameraHandler.destroy();
        this.postcode = 0;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = null;
        this.m = null;
    }
}
