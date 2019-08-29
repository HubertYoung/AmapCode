package com.alipay.mobile.nebulacore.android;

import android.net.Uri;
import android.webkit.WebResourceRequest;
import com.alipay.mobile.nebula.webview.APWebResourceRequest;
import java.util.Map;

public class AndroidWebResourceRequest implements APWebResourceRequest {
    private WebResourceRequest a;

    public AndroidWebResourceRequest(WebResourceRequest request) {
        this.a = request;
    }

    public String getMethod() {
        if (this.a != null) {
            return this.a.getMethod();
        }
        return null;
    }

    public Map<String, String> getRequestHeaders() {
        if (this.a != null) {
            return this.a.getRequestHeaders();
        }
        return null;
    }

    public Uri getUrl() {
        if (this.a != null) {
            return this.a.getUrl();
        }
        return null;
    }

    public boolean hasGesture() {
        if (this.a != null) {
            return this.a.hasGesture();
        }
        return false;
    }

    public boolean isForMainFrame() {
        if (this.a != null) {
            return this.a.isForMainFrame();
        }
        return false;
    }
}
