package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class ap implements ValueCallback<t> {
    final /* synthetic */ ao a;

    ap(ao aoVar) {
        this.a = aoVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        Log.d("SdkSetupTask", "ShareCoreSdcardSetupTask exception ".concat(String.valueOf((t) obj)));
        WaStat.stat((String) IWaStat.SHARE_CORE_UPD_SC_INIT_EXCEPTION_PV);
    }
}
