package com.alipay.mobile.nebula.webview;

import android.graphics.Bitmap;
import android.view.View;
import java.util.Map;

public interface APWebViewListener {
    View getEmbedView(int i, int i2, String str, String str2, Map<String, String> map);

    Bitmap getSnapshot(int i, int i2, String str, String str2, Map<String, String> map);

    void onDetachedFromWindow();

    void onEmbedViewAttachedToWebView(int i, int i2, String str, String str2, Map<String, String> map);

    void onEmbedViewDestory(int i, int i2, String str, String str2, Map<String, String> map);

    void onEmbedViewDetachedFromWebView(int i, int i2, String str, String str2, Map<String, String> map);

    void onEmbedViewParamChanged(int i, int i2, String str, String str2, Map<String, String> map, String[] strArr, String[] strArr2);

    void onEmbedViewVisibilityChanged(int i, int i2, String str, String str2, Map<String, String> map, int i3);

    boolean overScrollBy(int i, int i2, int i3, int i4);
}
