package com.alipay.mobile.nebula.newembedview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import com.alipay.mobile.nebula.view.IH5EmbedViewJSCallback;

public interface IH5NewEmbedView {
    void execJavaScript(String str, IH5EmbedViewJSCallback iH5EmbedViewJSCallback);

    void getComponentResourceDataWithUrl(String str, ResponseListen responseListen, H5Page h5Page);

    Bitmap getSnapshot();

    View getView();

    void onCreate(Context context, H5Page h5Page);

    void onEmbedViewAttachedToWebView();

    void onEmbedViewDestory();

    void onEmbedViewDetachedFromWebView();

    void onEmbedViewParamChanged();

    void onEmbedViewVisibilityChanged();

    void onReceivedData(JSONObject jSONObject, H5BridgeContext h5BridgeContext);

    void onReceivedMessage(String str, JSONObject jSONObject, H5BridgeContext h5BridgeContext);

    void onReceivedRender(JSONObject jSONObject, H5BridgeContext h5BridgeContext);

    void onWebViewDestory();

    void onWebViewPause();

    void onWebViewResume();

    void triggerPreSnapshot();
}
