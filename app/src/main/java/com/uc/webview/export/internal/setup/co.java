package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class co implements ValueCallback<t> {
    final /* synthetic */ cn a;

    co(cn cnVar) {
        this.a = cnVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        Log.d("UpdateSetupTask", "setup callback.");
        UCMRunningInfo totalLoadedUCM = UCSetupTask.getTotalLoadedUCM();
        if (totalLoadedUCM == null) {
            return;
        }
        if (totalLoadedUCM.coreType != 2 || !SDKFactory.l) {
            tVar.stop();
        }
    }
}
