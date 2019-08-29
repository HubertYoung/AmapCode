package com.alipay.mobile.bqcscanservice;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import com.alipay.mobile.bqcscanservice.BQCCameraParam.MaEngineType;
import java.util.Map;

public abstract class BQCScanEngine {

    public interface EngineCallback {
    }

    public abstract void destroy();

    public abstract boolean init(Context context, Map<String, Object> map);

    public abstract boolean onProcessFinish(BQCScanResult bQCScanResult);

    public abstract BQCScanResult process(byte[] bArr, Camera camera, Rect rect, Size size, int i);

    public abstract void setResultCallback(EngineCallback engineCallback);

    public abstract void start();

    public boolean isQrCodeEngine() {
        return false;
    }

    public float getCodeSize() {
        return 0.0f;
    }

    public void setSubScanType(MaEngineType type) {
    }

    public void setWhetherFirstSetup(boolean firstSetup) {
    }
}
