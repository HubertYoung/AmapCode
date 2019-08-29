package com.uc.webview.export.business.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.setup.t;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.j;

/* compiled from: ProGuard */
final class q implements ValueCallback<t> {
    final /* synthetic */ o a;

    q(o oVar) {
        this.a = oVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        Log.d(o.a, "die ".concat(String.valueOf(tVar)));
        WaStat.stat(IWaStat.BUSINESS_DECOMPRESS_AND_ODEX, Long.toString(this.a.b.a), 1);
        if (j.e()) {
            WaStat.saveData();
            WaStat.upload();
        }
        o.a(this.a, tVar);
    }
}
