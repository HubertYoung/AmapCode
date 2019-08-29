package com.alipay.mobile.nebula.provider;

import android.graphics.Bitmap;
import android.view.View;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.nebula.view.IH5EmbedView;
import java.util.Map;

public interface H5EmbededViewProvider {
    void addPendingMessage(String str, H5BridgeContext h5BridgeContext, JSONObject jSONObject);

    void clearBaseView();

    View getEmbedView(int i, int i2, String str, String str2, Map<String, String> map);

    IH5EmbedView getEmbedViewWrapperById(String str);

    Bitmap getSnapshot(int i, int i2, String str, String str2, Map<String, String> map);

    void onEmbedViewAttachedToWebView(int i, int i2, String str, String str2, Map<String, String> map);

    void onEmbedViewDestory(int i, int i2, String str, String str2, Map<String, String> map);

    void onEmbedViewDetachedFromWebView(int i, int i2, String str, String str2, Map<String, String> map);

    void onEmbedViewParamChanged(int i, int i2, String str, String str2, Map<String, String> map, String[] strArr, String[] strArr2);

    void onEmbedViewVisibilityChanged(int i, int i2, String str, String str2, Map<String, String> map, int i3);

    void onRequestPermissionResult(int i, String[] strArr, int[] iArr);

    void onWebViewDestory();

    void onWebViewPause();

    void onWebViewResume();

    void releaseView();

    void triggerPreSnapshot();
}
