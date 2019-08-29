package com.alipay.zoloz.toyger;

import android.graphics.Bitmap;
import android.graphics.PointF;
import com.alipay.zoloz.toyger.ToygerAttr;
import com.alipay.zoloz.toyger.ToygerState;
import java.util.Map;

public interface ToygerCallback<State extends ToygerState, Attr extends ToygerAttr> {
    public static final int EVENT_CODE_DARK_SCREEN = -1;
    public static final int EVENT_CODE_INIT_FAIL = -4;
    public static final int EVENT_CODE_LIVENESS_FAILED = -3;
    public static final int EVENT_CODE_MODEL_ERROR = -2;

    PointF onAlignDepthPoint(PointF pointF);

    boolean onComplete(int i, byte[] bArr, byte[] bArr2, boolean z);

    boolean onEvent(int i, Map<String, Object> map);

    boolean onHighQualityFrame(Bitmap bitmap, Attr attr);

    boolean onStateUpdated(State state, Attr attr, Map<String, Object> map);
}
