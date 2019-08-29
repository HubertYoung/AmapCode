package com.alipay.mobile.nebulauc.view;

import android.graphics.Bitmap;
import android.view.View;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.webview.APWebViewListener;
import com.alipay.mobile.nebulauc.impl.UCWebView;
import com.uc.webview.export.extension.EmbedViewConfig;
import com.uc.webview.export.extension.IEmbedView;

public class UCEmbededViewWrapper implements IEmbedView {
    private EmbedViewConfig mConfig;
    private View mRealView;
    private APWebViewListener mWebViewListener;

    public UCEmbededViewWrapper(View realView, APWebViewListener webViewListener, EmbedViewConfig embedViewConfig) {
        this.mRealView = realView;
        this.mWebViewListener = webViewListener;
        this.mConfig = embedViewConfig;
    }

    public View getView() {
        return this.mRealView;
    }

    public Bitmap getSnapShot() {
        H5Log.d(UCWebView.TAG, "UCWebView onEmbedView getSnapShot " + this.mConfig.toString());
        return this.mWebViewListener.getSnapshot(this.mConfig.mWidth, this.mConfig.mHeight, String.valueOf(this.mConfig.mEmbedViewID), this.mConfig.mType, this.mConfig.mObjectParam);
    }
}
