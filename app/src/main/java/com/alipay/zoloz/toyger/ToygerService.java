package com.alipay.zoloz.toyger;

import android.content.Context;
import android.graphics.PointF;
import com.alipay.zoloz.toyger.ToygerAlgorithmConfig;
import com.alipay.zoloz.toyger.ToygerAttr;
import com.alipay.zoloz.toyger.ToygerBiometricInfo;
import com.alipay.zoloz.toyger.ToygerCallback;
import com.alipay.zoloz.toyger.ToygerState;
import com.alipay.zoloz.toyger.algorithm.IToygerDelegate;
import com.alipay.zoloz.toyger.algorithm.TGDepthFrame;
import com.alipay.zoloz.toyger.algorithm.TGFrame;
import java.util.List;
import java.util.Map;

public abstract class ToygerService<C extends ToygerCallback, State extends ToygerState, Attr extends ToygerAttr, Info extends ToygerBiometricInfo, AlgorithmConfig extends ToygerAlgorithmConfig> implements IToygerDelegate<State, Attr, Info> {
    public static final String ASSET_DOC = "zdoc_client.bin";
    public static final String ASSET_FACE = "toyger.dat";
    public static final String ASSET_LICENSE = "toyger.license";
    public static final String KEY_DOC_FRAME_RECT = "docframerect";
    public static final String KEY_IS_MIRROR = "is_mirror";
    public static final String KEY_META_SERIALIZER = "meta_serializer";
    public static final String KEY_PUBLIC_KEY = "pubkey";
    public static final String KEY_RES_9_CONTENT = "content";
    public static final String KEY_RES_9_IS_UTF8 = "isUTF8";
    public static final String KEY_RES_9_KEY = "key";
    public static final String TAG = "TOYGER";
    public C mToygerCallback;

    public abstract void handleCaptureCompleted(int i, List<Info> list, Map<String, Object> map);

    public abstract void handleInfoReady(TGFrame tGFrame, Attr attr);

    public abstract void handleStateUpdated(State state, Attr attr);

    public abstract boolean init(Context context, C c, String str, String str2, Map<String, Object> map);

    public abstract boolean processImage(List<TGFrame> list, TGDepthFrame tGDepthFrame);

    public abstract void release();

    public abstract void reset();

    public static boolean preLoad(Context context) {
        return false;
    }

    public PointF handleAlignDepthPoint(PointF pointF) {
        return this.mToygerCallback.onAlignDepthPoint(pointF);
    }

    public void handleLog(int i, String str, byte[] bArr) {
        new StringBuilder("handleLog() logLevel=").append(i).append(", log=").append(str);
    }

    public void handleEventTriggered(int i, String str) {
        new StringBuilder("handleEventTriggered() eventCode=").append(i).append(", message=").append(str);
        this.mToygerCallback.onEvent(i, null);
    }
}
