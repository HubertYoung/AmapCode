package com.alipay.zoloz.a;

import android.graphics.Rect;
import com.alipay.streammedia.devicesengine.DevicesNativeEngineApi;
import com.alipay.streammedia.devicesengine.camera.FaceRectParams;
import com.alipay.streammedia.devicesengine.camera.ispAdjustResult;

/* compiled from: ToygerIsp */
public final class b {
    public DevicesNativeEngineApi a;

    static {
        try {
            DevicesNativeEngineApi.loadLibrariesOnce(null);
        } catch (Throwable th) {
        }
    }

    public final a a(byte[] bArr, short[] sArr, Rect rect, long j, int i) {
        a aVar;
        if (this.a != null) {
            try {
                FaceRectParams faceRectParams = new FaceRectParams();
                faceRectParams.setX(rect.left);
                faceRectParams.setY(rect.top);
                faceRectParams.setWidth(rect.right - rect.left);
                faceRectParams.setHeight(rect.bottom - rect.top);
                ispAdjustResult ispAdjust = this.a.ispAdjust(bArr, sArr, faceRectParams, j, (long) i);
                aVar = new a(ispAdjust.needSet, ispAdjust.exposureTime, ispAdjust.ISO);
            } catch (Throwable th) {
            }
            new StringBuilder("ToygerIsp.adjustIsp(), ispResult=").append(aVar);
            return aVar;
        }
        aVar = null;
        new StringBuilder("ToygerIsp.adjustIsp(), ispResult=").append(aVar);
        return aVar;
    }
}
