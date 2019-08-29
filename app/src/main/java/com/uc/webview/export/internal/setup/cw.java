package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.utility.download.UpdateTask;

/* compiled from: ProGuard */
final class cw implements ValueCallback<UpdateTask> {
    final /* synthetic */ bx a;
    final /* synthetic */ cn b;

    cw(cn cnVar, bx bxVar) {
        this.b = cnVar;
        this.a = bxVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        this.a.a(0, null);
    }
}
