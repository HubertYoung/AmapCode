package com.alipay.mobile.nebulauc.impl;

import android.net.Uri;
import com.alipay.mobile.nebula.webview.APWebResourceRequest;
import com.uc.webview.export.WebResourceRequest;
import java.util.Map;

public class UCWebResourceRequest implements APWebResourceRequest {
    private WebResourceRequest request;

    public UCWebResourceRequest(WebResourceRequest request2) {
        this.request = request2;
    }

    public String getMethod() {
        if (this.request != null) {
            return this.request.getMethod();
        }
        return null;
    }

    public Map<String, String> getRequestHeaders() {
        if (this.request != null) {
            return this.request.getRequestHeaders();
        }
        return null;
    }

    public Uri getUrl() {
        if (this.request != null) {
            return this.request.getUrl();
        }
        return null;
    }

    public boolean hasGesture() {
        if (this.request != null) {
            return this.request.hasGesture();
        }
        return false;
    }

    public boolean isForMainFrame() {
        if (this.request != null) {
            return this.request.isForMainFrame();
        }
        return false;
    }
}
