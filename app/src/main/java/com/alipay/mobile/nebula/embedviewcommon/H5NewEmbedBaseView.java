package com.alipay.mobile.nebula.embedviewcommon;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.newembedview.H5NewEmbedViewProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.view.H5BaseEmbedView;
import java.util.Map;

public class H5NewEmbedBaseView extends H5BaseEmbedView {
    private static final String TAG = "H5NewEmbedBaseView";
    private H5NewEmbedCommonLayout mCore;
    private H5NewEmbedViewProvider newEmbedViewProvider;

    public View getView(int width, int height, String viewId, String mType, Map<String, String> params) {
        if (this.mCore == null) {
            if (this.mContext.get() != null) {
                this.mCore = new H5NewEmbedCommonLayout((Context) this.mContext.get());
                if (this.mH5Page.get() != null) {
                    ((H5Page) this.mH5Page.get()).setNewEmbedViewRoot(this.mCore);
                    this.newEmbedViewProvider = ((H5Page) this.mH5Page.get()).getNewEmbedViewProvider();
                } else {
                    H5Log.e((String) TAG, (String) "fatal error h5page is null");
                }
            } else {
                H5Log.e((String) TAG, (String) "fatal error context is null");
            }
        }
        return this.mCore;
    }

    public void onEmbedViewAttachedToWebView(int width, int height, String viewId, String mType, Map<String, String> params) {
        if (this.newEmbedViewProvider != null) {
            this.newEmbedViewProvider.onEmbedViewAttachedToWebView();
        }
    }

    public void onEmbedViewDetachedFromWebView(int width, int height, String viewId, String mType, Map<String, String> params) {
        if (this.newEmbedViewProvider != null) {
            this.newEmbedViewProvider.onEmbedViewDetachedFromWebView();
        }
    }

    public void onEmbedViewDestory(int width, int height, String viewId, String mType, Map<String, String> params) {
        if (this.newEmbedViewProvider != null) {
            this.newEmbedViewProvider.onEmbedViewDestory();
        }
    }

    public void onEmbedViewParamChanged(int width, int height, String viewId, String mType, Map<String, String> params, String[] name, String[] value) {
        if (this.newEmbedViewProvider != null) {
            this.newEmbedViewProvider.onEmbedViewParamChanged();
        }
    }

    public void onEmbedViewVisibilityChanged(int width, int height, String viewId, String mType, Map<String, String> params, int reason) {
        if (this.newEmbedViewProvider != null) {
            this.newEmbedViewProvider.onEmbedViewVisibilityChanged();
        }
    }

    public void onWebViewResume() {
        if (this.newEmbedViewProvider != null) {
            this.newEmbedViewProvider.onWebViewResume();
        }
    }

    public void onWebViewPause() {
        if (this.newEmbedViewProvider != null) {
            this.newEmbedViewProvider.onWebViewPause();
        }
    }

    public void onWebViewDestory() {
        if (this.newEmbedViewProvider != null) {
            this.newEmbedViewProvider.onWebViewDestory();
        }
    }

    public void onReceivedMessage(String actionType, JSONObject data, H5BridgeContext bridgeContext) {
    }

    public void onReceivedRender(JSONObject data, H5BridgeContext bridgeContext) {
    }

    public View getSpecialRestoreView(int width, int height, String viewId, String mType, Map<String, String> params) {
        return null;
    }

    public Bitmap getSnapshot(int width, int height, String viewId, String mType, Map<String, String> params) {
        return null;
    }

    public void triggerPreSnapshot() {
    }
}
