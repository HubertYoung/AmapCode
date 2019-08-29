package com.alibaba.baichuan.android.trade.ui.a;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.WebView;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.b.b;
import com.alibaba.baichuan.android.trade.c.a.a.e;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.HashMap;

public class a {
    private static final String a = "a";

    /* renamed from: com.alibaba.baichuan.android.trade.ui.a.a$a reason: collision with other inner class name */
    static class C0002a {
        /* access modifiers changed from: private */
        public static final a a = new a();
    }

    private a() {
    }

    public static a a() {
        return C0002a.a;
    }

    public boolean a(b bVar, WebView webView, String str) {
        AlibcLogger.i(a, "加参数前的url:".concat(String.valueOf(str)));
        AlibcLogger.d(a, str);
        if (webView == null) {
            return false;
        }
        if (bVar.a != null) {
            bVar.a.timeBegin(UserTrackerConstants.PM_URL_HANDLE_TIME);
        }
        com.alibaba.baichuan.android.trade.c.a.a.c.a aVar = new com.alibaba.baichuan.android.trade.c.a.a.c.a();
        aVar.g = (Activity) webView.getContext();
        aVar.a = webView;
        aVar.d = str;
        aVar.b = bVar.b;
        if (!bVar.b.c()) {
            aVar.f = "noForceH5";
        }
        aVar.e = 2;
        aVar.i = new HashMap();
        if (bVar.b.b() != null) {
            aVar.i.put("ui_contextParams", bVar.b.b());
        }
        com.alibaba.baichuan.android.trade.c.a.a.b b = e.a().b(aVar);
        if (b.a) {
            bVar.a = null;
            if (webView.getUrl().matches(AlibcContext.sclickPattern)) {
                ((Activity) webView.getContext()).finish();
            }
            return true;
        }
        String str2 = b.b;
        if (bVar.a != null) {
            bVar.a.timeEnd(UserTrackerConstants.PM_URL_HANDLE_TIME);
        }
        if (bVar.a != null) {
            bVar.a.timeBegin(UserTrackerConstants.PM_URL_LOAD_TIME);
        }
        if (TextUtils.equals(str2, str)) {
            AlibcLogger.i(a, "拦截加参后跟原来的url一样webview加载的url为:".concat(String.valueOf(str2)));
            return false;
        }
        AlibcLogger.i(a, "加载的url为:".concat(String.valueOf(str2)));
        webView.loadUrl(str2);
        return true;
    }
}
