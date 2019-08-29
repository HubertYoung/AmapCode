package com.uc.webview.export.internal.android;

import android.os.Build.VERSION;
import android.webkit.ValueCallback;
import android.webkit.WebStorage;
import android.webkit.WebStorage.Origin;
import com.uc.webview.export.internal.interfaces.IWebStorage;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: ProGuard */
public final class q implements IWebStorage {
    private WebStorage a = WebStorage.getInstance();

    /* compiled from: ProGuard */
    class a implements ValueCallback<Map> {
        private ValueCallback<Map> b;

        public final /* synthetic */ void onReceiveValue(Object obj) {
            Map map = (Map) obj;
            if (this.b != null) {
                if (VERSION.SDK_INT >= 11) {
                    ValueCallback<Map> valueCallback = this.b;
                    HashMap hashMap = new HashMap();
                    for (Entry entry : map.entrySet()) {
                        Origin origin = (Origin) entry.getValue();
                        com.uc.webview.export.WebStorage.Origin origin2 = new com.uc.webview.export.WebStorage.Origin(origin.getOrigin(), origin.getQuota(), origin.getUsage());
                        hashMap.put(entry.getKey(), origin2);
                    }
                    valueCallback.onReceiveValue(hashMap);
                    return;
                }
                this.b.onReceiveValue(map);
            }
        }

        public a(ValueCallback<Map> valueCallback) {
            this.b = valueCallback;
        }
    }

    public final void getOrigins(ValueCallback<Map> valueCallback) {
        this.a.getOrigins(new a(valueCallback));
    }

    public final void getUsageForOrigin(String str, ValueCallback<Long> valueCallback) {
        this.a.getUsageForOrigin(str, valueCallback);
    }

    public final void getQuotaForOrigin(String str, ValueCallback<Long> valueCallback) {
        this.a.getQuotaForOrigin(str, valueCallback);
    }

    @Deprecated
    public final void setQuotaForOrigin(String str, long j) {
        this.a.setQuotaForOrigin(str, j);
    }

    public final void deleteOrigin(String str) {
        this.a.deleteOrigin(str);
    }

    public final void deleteAllData() {
        this.a.deleteAllData();
    }
}
