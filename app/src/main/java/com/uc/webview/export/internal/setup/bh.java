package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class bh implements ValueCallback<t> {
    final /* synthetic */ bf a;

    bh(bf bfVar) {
        this.a = bfVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        Log.d(bf.a, "setup callback.");
        WaStat.stat((String) IWaStat.SHARE_CORE_DELAY_SEARE_CORE_FILE_SETUP_PV);
        ((t) obj).stop();
    }
}
