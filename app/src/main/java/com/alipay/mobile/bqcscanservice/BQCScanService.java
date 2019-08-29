package com.alipay.mobile.bqcscanservice;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.view.TextureView;
import com.alipay.mobile.bqcscanservice.BQCCameraParam.CameraConfigType;
import com.alipay.mobile.bqcscanservice.BQCCameraParam.MaEngineType;
import com.alipay.mobile.bqcscanservice.BQCScanEngine.EngineCallback;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.util.Map;

public abstract class BQCScanService extends ExternalService {
    public static final String SCAN_RECT_HEIGHT = "SCAN_RECT_HEIGHT";
    public static final String SCAN_RECT_WIDTH = "SCAN_RECT_WIDTH";
    public static final String SCAN_SCALE_SWITCH = "SCAN_SCALE_SWITCH";
    protected CameraHandler cameraHandler;
    public boolean firstSetup = true;

    public abstract void cleanup(long j);

    public abstract Camera getCamera();

    public abstract int getCameraDisplayOrientation();

    public abstract Object getCameraParam(String str);

    public abstract int getCurrentZoom();

    public abstract int getMaxZoom();

    public abstract boolean isPreviewing();

    public abstract boolean isScanEnable();

    public abstract boolean isTorchOn();

    public abstract void onSurfaceAvailable();

    public abstract void regScanEngine(String str, Class<? extends BQCScanEngine> cls, EngineCallback engineCallback);

    public abstract void setCameraId(int i);

    public abstract void setCameraParam(String str, Object obj);

    public abstract void setDisplay(TextureView textureView);

    public abstract void setEngineParameters(Map<String, Object> map);

    public abstract void setScanEnable(boolean z);

    public abstract void setScanRegion(Rect rect);

    public abstract void setScanRegion(Rect rect, Point point);

    public abstract boolean setScanType(String str);

    public abstract void setTorch(boolean z);

    public abstract void setZoom(int i);

    public abstract void setup(Context context, BQCScanCallback bQCScanCallback);

    public abstract void startPreview();

    public abstract void stopPreview();

    public void preOpenCamera() {
    }

    /* access modifiers changed from: protected */
    public void tryPreOpenCamera() {
    }

    public void postCloseCamera() {
    }

    /* access modifiers changed from: protected */
    public void tryPostCloseCamera() {
    }

    public boolean getFirstSetup() {
        return this.firstSetup;
    }

    public boolean setScanType(String type, MaEngineType subType) {
        return false;
    }

    public CameraHandler getCameraHandler() {
        return this.cameraHandler;
    }

    public void setPreviewCallback() {
    }

    public void changeCameraFeature(CameraConfigType type, Object... params) {
    }

    public void reconnectCamera() {
    }

    public void refocus() {
    }

    public void stopAutoFocus() {
    }

    public void enableCameraOpenWatcher(boolean enabled) {
    }

    public void serviceInit() {
    }

    public void serviceOut() {
    }

    public boolean checkEngineRegister(String type) {
        return false;
    }

    public void setTraceLogger(TraceLogger traceLogger) {
    }
}
