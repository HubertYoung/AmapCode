package com.alibaba.baichuan.android.trade.c.b;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.adapter.ut.UserTrackerCompoment;
import com.alibaba.baichuan.android.trade.adapter.ut.performance.Point4UrlLoad;
import com.alibaba.baichuan.android.trade.b.b;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.ui.a.a;
import com.alibaba.baichuan.android.trade.utils.i;
import com.alibaba.baichuan.android.trade.utils.k;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;

public class f extends WebViewClient {
    private static final String a = "f";
    private boolean b = true;
    private boolean c = false;
    private b d = new b();

    public f(d dVar) {
        this.d.b = dVar;
    }

    private void a(WebView webView, String str) {
        if (webView != null && !k.a(str)) {
            webView.setTag(i.a(AlibcContext.context, "id", "com_taobao_nb_sdk_webview_click"), Boolean.FALSE);
        }
    }

    public void onPageFinished(WebView webView, String str) {
        if (this.b) {
            UserTrackerCompoment.sendUseabilitySuccess((String) this.d.b.b.get(UserTrackerConstants.U_LABEL));
            this.d.b.c.b(UserTrackerConstants.PM_URL_LOAD_TIME);
            this.d.b.c.b(UserTrackerConstants.PM_ALL_TIME);
            UserTrackerCompoment.sendPerfomancePoint(this.d.b.c.b);
            this.b = false;
        } else if (this.c) {
            this.c = false;
        } else if (this.d.a != null) {
            this.d.a.timeEnd(UserTrackerConstants.PM_URL_LOAD_TIME);
            this.d.a.timeEnd(UserTrackerConstants.PM_ALL_TIME);
            UserTrackerCompoment.sendPerfomancePoint(this.d.a);
            this.d.a = null;
        }
        super.onPageFinished(webView, str);
    }

    public void onReceivedError(WebView webView, int i, String str, String str2) {
        if (this.b) {
            UserTrackerCompoment.sendUseabilityFailure((String) this.d.b.b.get(UserTrackerConstants.U_LABEL), UserTrackerConstants.EM_LOAD_FAILURE, String.valueOf(i));
            AlibcLogger.e(a, "onReceivedError: failurl = ".concat(String.valueOf(str2)));
            this.b = false;
        }
        this.c = true;
        super.onReceivedError(webView, i, str, str2);
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (!this.b) {
            this.d.a = new Point4UrlLoad();
            this.d.a.timeBegin(UserTrackerConstants.PM_ALL_TIME);
        }
        a(this.d.b.a, str);
        return a.a().a(this.d, webView, str);
    }
}
