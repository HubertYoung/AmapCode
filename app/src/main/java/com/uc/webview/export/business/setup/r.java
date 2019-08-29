package com.uc.webview.export.business.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.business.a.c;
import com.uc.webview.export.internal.setup.t;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class r implements ValueCallback<t> {
    final /* synthetic */ o a;

    r(o oVar) {
        this.a = oVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        Log.d(o.a, "exception ".concat(String.valueOf(tVar)));
        this.a.b.a(c.h);
        o.a(this.a, tVar);
    }
}
