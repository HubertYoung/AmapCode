package com.uc.webview.export;

import android.webkit.ValueCallback;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IGeolocationPermissions;
import com.uc.webview.export.internal.setup.UCAsyncTask;
import java.util.HashMap;
import java.util.Set;

@Api
/* compiled from: ProGuard */
public class GeolocationPermissions implements IGeolocationPermissions {
    private static HashMap<Integer, GeolocationPermissions> b;
    private IGeolocationPermissions a;

    @Api
    /* compiled from: ProGuard */
    public interface Callback {
        void invoke(String str, boolean z, boolean z2);
    }

    private GeolocationPermissions(IGeolocationPermissions iGeolocationPermissions) {
        this.a = iGeolocationPermissions;
    }

    public static GeolocationPermissions getInstance() throws RuntimeException {
        return a(((Integer) SDKFactory.invoke(SDKFactory.getCoreType, new Object[0])).intValue());
    }

    public static GeolocationPermissions getInstance(WebView webView) throws RuntimeException {
        return a(webView.getCurrentViewCoreType());
    }

    private static synchronized GeolocationPermissions a(int i) throws RuntimeException {
        GeolocationPermissions geolocationPermissions;
        synchronized (GeolocationPermissions.class) {
            if (b == null) {
                b = new HashMap<>();
            }
            geolocationPermissions = b.get(Integer.valueOf(i));
            if (geolocationPermissions == null) {
                geolocationPermissions = new GeolocationPermissions((IGeolocationPermissions) SDKFactory.invoke(UCAsyncTask.isPaused, Integer.valueOf(i)));
                b.put(Integer.valueOf(i), geolocationPermissions);
            }
        }
        return geolocationPermissions;
    }

    public void getOrigins(ValueCallback<Set<String>> valueCallback) {
        this.a.getOrigins(valueCallback);
    }

    public void getAllowed(String str, ValueCallback<Boolean> valueCallback) {
        this.a.getAllowed(str, valueCallback);
    }

    public void clear(String str) {
        this.a.clear(str);
    }

    public void allow(String str) {
        this.a.allow(str);
    }

    public void clearAll() {
        this.a.clearAll();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GeolocationPermissions@");
        sb.append(hashCode());
        sb.append("[");
        sb.append(this.a);
        sb.append("]");
        return sb.toString();
    }
}
