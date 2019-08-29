package com.uc.webview.export.internal.setup;

import com.uc.webview.export.WebChromeClient;
import com.uc.webview.export.WebViewClient;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.interfaces.IWebView;
import com.uc.webview.export.internal.uc.startup.StartupTrace;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.j;

/* compiled from: ProGuard */
final class ab implements Runnable {
    final /* synthetic */ aa a;

    ab(aa aaVar) {
        this.a = aaVar;
    }

    public final void run() {
        IWebView a2;
        StartupTrace.a();
        try {
            if (!aa.b()) {
                a2 = aa.a(this.a);
                Log.d("PrecreateWebViewTask", "main run mWebView:".concat(String.valueOf(a2)));
                if (a2 != null) {
                    a2.getSettingsInner().setJavaScriptEnabled(true);
                    a2.setWebViewClient(new WebViewClient());
                    a2.setWebChromeClient(new WebChromeClient());
                    String str = (String) this.a.getOption(UCCore.OPTION_PRECREATE_WEBVIEW_URL);
                    Log.d("PrecreateWebViewTask", "main run precreateWebViewUrl:".concat(String.valueOf(str)));
                    if (!j.a(str)) {
                        a2.loadUrl(str);
                    } else {
                        a2.loadData("<html><head></head><body onload=\"console.log(\"WebView init\");\"></body></html>", "text/html", null);
                    }
                    Log.d("PrecreateWebViewTask", "main run finally destroy webview.");
                    a2.destroy();
                }
            }
        } catch (Throwable unused) {
        }
        StartupTrace.traceEventEnd("PrecreateWebViewTask.runOnUIThread");
    }
}
