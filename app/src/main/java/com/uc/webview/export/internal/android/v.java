package com.uc.webview.export.internal.android;

import com.uc.webview.export.internal.a;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.interfaces.IWebView;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.utility.Utils;

/* compiled from: ProGuard */
public final class v extends a {
    Runnable f = new w(this);

    public final void a(IWebView iWebView, int i) {
        if (i == 0) {
            if (d != 1) {
                d = 1;
            }
        } else if (d == 1) {
            e.removeCallbacks(this.f);
            e.post(this.f);
        }
    }

    public final void b(IWebView iWebView) {
        a.remove(iWebView);
        if (a.isEmpty()) {
            if (Utils.sWAPrintLog) {
                Log.d("SDKWaStat", "WebViewDetector:destroy");
            }
            WaStat.saveData(true);
        }
    }
}
