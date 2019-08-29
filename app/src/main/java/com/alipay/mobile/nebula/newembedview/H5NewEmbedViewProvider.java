package com.alipay.mobile.nebula.newembedview;

import android.graphics.Bitmap;
import android.view.View;

public interface H5NewEmbedViewProvider {
    void clearAllView();

    void deleteView(String str);

    View getEmbedView(String str, String str2);

    IH5NewEmbedView getNewEmbedViewById(String str);

    Bitmap getSnapshot();

    void onEmbedViewAttachedToWebView();

    void onEmbedViewDestory();

    void onEmbedViewDetachedFromWebView();

    void onEmbedViewParamChanged();

    void onEmbedViewVisibilityChanged();

    void onWebViewDestory();

    void onWebViewPause();

    void onWebViewResume();

    void releaseView();

    void triggerPreSnapshot();
}
