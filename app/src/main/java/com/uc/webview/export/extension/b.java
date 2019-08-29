package com.uc.webview.export.extension;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.setup.t;

/* compiled from: ProGuard */
final class b implements ValueCallback<t> {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        UCCore.a("updateProgress", (t) obj, this.a.b);
    }
}
