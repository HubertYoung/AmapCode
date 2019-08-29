package com.uc.webview.export.internal.setup;

import android.content.Context;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWebView;
import com.uc.webview.export.internal.uc.startup.StartupTrace;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
public final class aa extends UCSubSetupTask<aa, aa> {
    private static boolean a = false;
    private static final Object b = new Object();

    static /* synthetic */ IWebView a(aa aaVar) {
        Log.d("PrecreateWebViewTask", "createUCWebView");
        StartupTrace.a();
        IWebView iWebView = (IWebView) SDKFactory.invoke(10012, (Context) aaVar.mOptions.get(UCCore.OPTION_CONTEXT), 0, 0, Boolean.FALSE, Boolean.FALSE, new int[1]);
        StartupTrace.traceEventEnd("PrecreateWebViewTask.createUCWebView");
        return iWebView;
    }

    public static void a() {
        synchronized (b) {
            a = true;
        }
    }

    public static boolean b() {
        boolean z;
        synchronized (b) {
            z = true;
            if (!a) {
                z = false;
            }
        }
        return z;
    }

    public final void run() {
        Log.d("PrecreateWebViewTask", "run --begin--");
        StartupTrace.traceEvent("PrecreateWebViewTask.run");
        if (!b()) {
            SDKFactory.invoke(UCMPackageInfo.compareVersion, new ab(this));
        }
    }
}
