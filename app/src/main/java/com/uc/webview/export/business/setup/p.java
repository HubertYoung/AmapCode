package com.uc.webview.export.business.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.business.a.c;
import com.uc.webview.export.internal.setup.t;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.j;

/* compiled from: ProGuard */
final class p implements ValueCallback<t> {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ o c;

    p(o oVar, String str, String str2) {
        this.c = oVar;
        this.a = str;
        this.b = str2;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        Log.d(o.a, "setup callback.");
        tVar.stop();
        this.c.b.a(c.g);
        if (j.a((Boolean) this.c.getOption("o_flag_odex_done"))) {
            o.a(this.a, this.b);
        }
        o.a(this.c, tVar);
    }
}
