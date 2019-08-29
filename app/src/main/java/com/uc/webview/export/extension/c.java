package com.uc.webview.export.extension;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.setup.t;

/* compiled from: ProGuard */
final class c implements ValueCallback<t> {
    final /* synthetic */ a a;

    c(a aVar) {
        this.a = aVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        UCCore.a("downloadException", (t) obj, this.a.b);
    }
}
