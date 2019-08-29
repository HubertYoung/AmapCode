package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.utility.download.UpdateTask;

/* compiled from: ProGuard */
final class cu implements ValueCallback<UpdateTask> {
    final /* synthetic */ bx a;
    final /* synthetic */ cn b;

    cu(cn cnVar, bx bxVar) {
        this.b = cnVar;
        this.a = bxVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        this.a.a(4, null);
    }
}
