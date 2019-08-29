package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class bg implements ValueCallback<t> {
    final /* synthetic */ bf a;

    bg(bf bfVar) {
        this.a = bfVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        Log.d(bf.a, "switch callback do nothing.");
    }
}
