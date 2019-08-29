package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class aq implements ValueCallback<t> {
    final /* synthetic */ ao a;

    aq(ao aoVar) {
        this.a = aoVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        Log.d("SdkSetupTask", "ShareCoreSdcardSetupTask success ".concat(String.valueOf((t) obj)));
        WaStat.stat((String) IWaStat.SHARE_CORE_UPD_SC_INIT_SUCCESS_PV);
    }
}
