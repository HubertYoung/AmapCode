package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.SDKFactory;

/* compiled from: ProGuard */
final class ch implements ValueCallback {
    ch() {
    }

    public final void onReceiveValue(Object obj) {
        SDKFactory.q = false;
        SDKFactory.invoke(UCMPackageInfo.sortByLastModified, new Object[0]);
    }
}
