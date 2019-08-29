package com.uc.webview.export.internal.android;

import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IGeolocationPermissions;
import java.util.Set;

/* compiled from: ProGuard */
public final class b implements IGeolocationPermissions {
    private GeolocationPermissions a = GeolocationPermissions.getInstance();

    public final void getOrigins(ValueCallback<Set<String>> valueCallback) {
        this.a.getOrigins(valueCallback);
    }

    public final void getAllowed(String str, ValueCallback<Boolean> valueCallback) {
        this.a.getAllowed(str, valueCallback);
    }

    public final void clear(String str) {
        this.a.clear(str);
    }

    public final void allow(String str) {
        this.a.allow(str);
    }

    public final void clearAll() {
        this.a.clearAll();
    }
}
