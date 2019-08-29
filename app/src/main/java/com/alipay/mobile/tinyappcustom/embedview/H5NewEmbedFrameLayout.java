package com.alipay.mobile.tinyappcustom.embedview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.nebula.newembedview.H5NewBaseEmbedView;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcustom.embedview.H5NewEmbedScrollView.DelegateScrollView;

public class H5NewEmbedFrameLayout extends H5NewBaseEmbedView {
    private View a;

    /* access modifiers changed from: protected */
    public View a() {
        if (this.a == null) {
            this.a = new H5EmbedBaseFrameLayout((Context) this.mContext.get());
        }
        return this.a;
    }

    public View getView() {
        this.a = a();
        H5Log.d("H5NewEmbedFrameLayout", "getView " + this.a.hashCode());
        return this.a;
    }

    public void onEmbedViewAttachedToWebView() {
    }

    public void onEmbedViewDetachedFromWebView() {
    }

    public void onEmbedViewDestory() {
    }

    public void onEmbedViewParamChanged() {
    }

    public void onEmbedViewVisibilityChanged() {
    }

    public void onWebViewResume() {
    }

    public void onWebViewPause() {
    }

    public void onWebViewDestory() {
    }

    public void onReceivedMessage(String s, JSONObject jsonObject, H5BridgeContext h5BridgeContext) {
        if (jsonObject != null && !jsonObject.isEmpty()) {
            H5Log.d("H5NewEmbedFrameLayout", "onReceivedMessage actionType " + s + ", data " + jsonObject.toJSONString());
        }
    }

    public void onReceivedRender(JSONObject jsonObject, H5BridgeContext h5BridgeContext) {
        if (jsonObject != null && !jsonObject.isEmpty()) {
            H5Log.d("H5NewEmbedFrameLayout", "onReceivedRender data " + jsonObject.toJSONString() + Token.SEPARATOR + this.a.hashCode());
            H5EmbedBaseFrameLayout container = null;
            if (this.a instanceof H5EmbedBaseFrameLayout) {
                container = (H5EmbedBaseFrameLayout) this.a;
            } else if (this.a instanceof DelegateScrollView) {
                container = ((DelegateScrollView) this.a).getContainer();
            }
            if (container != null) {
                container.onReceivedRender(this.a.getContext(), jsonObject);
            }
        }
    }

    public void onReceivedData(JSONObject jsonObject, H5BridgeContext h5BridgeContext) {
        if (jsonObject != null && !jsonObject.isEmpty()) {
            H5Log.d("H5NewEmbedFrameLayout", "onReceivedData data " + jsonObject.toJSONString());
        }
    }

    public Bitmap getSnapshot() {
        return null;
    }

    public void triggerPreSnapshot() {
    }
}
