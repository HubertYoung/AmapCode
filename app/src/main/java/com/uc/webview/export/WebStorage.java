package com.uc.webview.export;

import android.webkit.ValueCallback;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWebStorage;
import com.uc.webview.export.internal.setup.UCAsyncTask;
import java.util.HashMap;
import java.util.Map;

@Api
/* compiled from: ProGuard */
public class WebStorage {
    private static HashMap<Integer, WebStorage> a;
    private IWebStorage b;

    @Api
    /* compiled from: ProGuard */
    public static class Origin {
        private String a = null;
        private long b = 0;
        private long c = 0;

        public Origin(String str, long j, long j2) {
            this.a = str;
            this.b = j;
            this.c = j2;
        }

        public Origin(String str, long j) {
            this.a = str;
            this.b = j;
        }

        public Origin(String str) {
            this.a = str;
        }

        public String getOrigin() {
            return this.a;
        }

        public long getQuota() {
            return this.b;
        }

        public long getUsage() {
            return this.c;
        }
    }

    private WebStorage(IWebStorage iWebStorage) {
        this.b = iWebStorage;
    }

    public void getOrigins(ValueCallback<Map> valueCallback) {
        this.b.getOrigins(valueCallback);
    }

    public void getUsageForOrigin(String str, ValueCallback<Long> valueCallback) {
        this.b.getUsageForOrigin(str, valueCallback);
    }

    public void getQuotaForOrigin(String str, ValueCallback<Long> valueCallback) {
        this.b.getQuotaForOrigin(str, valueCallback);
    }

    @Deprecated
    public void setQuotaForOrigin(String str, long j) {
        this.b.setQuotaForOrigin(str, j);
    }

    public void deleteOrigin(String str) {
        this.b.deleteOrigin(str);
    }

    public void deleteAllData() {
        this.b.deleteAllData();
    }

    public static WebStorage getInstance() {
        return a(((Integer) SDKFactory.invoke(SDKFactory.getCoreType, new Object[0])).intValue());
    }

    public static WebStorage getInstance(WebView webView) {
        return a(webView.getCurrentViewCoreType());
    }

    private static synchronized WebStorage a(int i) throws RuntimeException {
        WebStorage webStorage;
        synchronized (WebStorage.class) {
            if (a == null) {
                a = new HashMap<>();
            }
            webStorage = a.get(Integer.valueOf(i));
            if (webStorage == null) {
                webStorage = new WebStorage((IWebStorage) SDKFactory.invoke(UCAsyncTask.inThread, Integer.valueOf(i)));
                a.put(Integer.valueOf(i), webStorage);
            }
        }
        return webStorage;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("WebStorage@");
        sb.append(hashCode());
        sb.append("[");
        sb.append(this.b);
        sb.append("]");
        return sb.toString();
    }
}
