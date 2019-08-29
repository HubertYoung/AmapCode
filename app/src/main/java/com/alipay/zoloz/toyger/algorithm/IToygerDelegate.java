package com.alipay.zoloz.toyger.algorithm;

import android.graphics.PointF;
import com.alipay.zoloz.toyger.ToygerAttr;
import com.alipay.zoloz.toyger.ToygerBiometricInfo;
import com.alipay.zoloz.toyger.ToygerState;
import java.util.List;
import java.util.Map;

public interface IToygerDelegate<State extends ToygerState, Attr extends ToygerAttr, Info extends ToygerBiometricInfo> {
    @Deprecated
    public static final int EVENT_CODE_DARK_SCREEN = -1;
    public static final int LOG_DEBUG = 1;
    public static final int LOG_DEVICE = 2;
    public static final int LOG_MONITOR = 3;

    PointF handleAlignDepthPoint(PointF pointF);

    void handleCaptureCompleted(int i, List<Info> list, Map<String, Object> map);

    void handleEventTriggered(int i, String str);

    void handleInfoReady(TGFrame tGFrame, Attr attr);

    void handleLog(int i, String str, byte[] bArr);

    void handleStateUpdated(State state, Attr attr);
}
