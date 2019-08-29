package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class bj implements ValueCallback<t> {
    final /* synthetic */ bf a;

    bj(bf bfVar) {
        this.a = bfVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        Log.d(bf.a, "success ".concat(String.valueOf((t) obj)));
        WaStat.stat((String) IWaStat.SHARE_CORE_DELAY_SEARE_CORE_FILE_SUCCESS_PV);
    }
}
