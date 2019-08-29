package com.alipay.mobile.nebula.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import java.util.Map;

public interface IH5EmbedView {
    void execJavaScript(String str, IH5EmbedViewJSCallback iH5EmbedViewJSCallback);

    void getComponentResourceDataWithUrl(String str, ResponseListen responseListen, H5Page h5Page);

    Bitmap getSnapshot(int i, int i2, String str, String str2, Map<String, String> map);

    View getSpecialRestoreView(int i, int i2, String str, String str2, Map<String, String> map);

    View getView(int i, int i2, String str, String str2, Map<String, String> map);

    void onCreate(Context context, H5Page h5Page);

    void onEmbedViewAttachedToWebView(int i, int i2, String str, String str2, Map<String, String> map);

    void onEmbedViewDestory(int i, int i2, String str, String str2, Map<String, String> map);

    void onEmbedViewDetachedFromWebView(int i, int i2, String str, String str2, Map<String, String> map);

    void onEmbedViewParamChanged(int i, int i2, String str, String str2, Map<String, String> map, String[] strArr, String[] strArr2);

    void onEmbedViewVisibilityChanged(int i, int i2, String str, String str2, Map<String, String> map, int i3);

    void onReceivedMessage(String str, JSONObject jSONObject, H5BridgeContext h5BridgeContext);

    void onReceivedRender(JSONObject jSONObject, H5BridgeContext h5BridgeContext);

    void onRequestPermissionResult(int i, String[] strArr, int[] iArr);

    void onWebViewDestory();

    void onWebViewPause();

    void onWebViewResume();

    void triggerPreSnapshot();
}
