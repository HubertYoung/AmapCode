package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.g;

/* compiled from: ProGuard */
final class bi implements ValueCallback<t> {
    final /* synthetic */ bf a;

    bi(bf bfVar) {
        this.a = bfVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        Log.d(bf.a, "exception ".concat(String.valueOf((t) obj)));
        WaStat.stat((String) IWaStat.SHARE_CORE_DELAY_SEARE_CORE_FILE_EXCEPTION_PV);
        try {
            g.a(this.a.getContext().getApplicationContext());
        } catch (Throwable unused) {
        }
    }
}
