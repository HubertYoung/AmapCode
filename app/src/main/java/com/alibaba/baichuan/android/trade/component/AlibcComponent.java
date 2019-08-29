package com.alibaba.baichuan.android.trade.component;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.b.a;
import com.alibaba.baichuan.android.trade.callback.AlibcCallbackContext;
import com.alibaba.baichuan.android.trade.ui.activity.AlibcWebViewActivity;
import com.alibaba.baichuan.android.trade.utils.g;
import java.io.Serializable;
import java.util.Map;

public class AlibcComponent {
    public static final AlibcComponent INSTANCE = new AlibcComponent();
    /* access modifiers changed from: private */
    public static final String a = "AlibcComponent";

    private AlibcComponent() {
    }

    public void show(Activity activity, String str, WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient, Map map, a aVar, Map map2, boolean z) {
        g gVar = AlibcContext.executorService;
        a aVar2 = new a(this, activity, aVar, webView, map, webViewClient, webChromeClient, str, map2);
        gVar.b(aVar2);
    }

    public void show(Activity activity, String str, a aVar) {
        show(activity, str, null, aVar);
    }

    public void show(Activity activity, String str, Serializable serializable, a aVar) {
        Intent intent = new Intent(activity, AlibcWebViewActivity.class);
        intent.putExtra("url", str);
        if (serializable != null) {
            intent.putExtra("ui_contextParams", serializable);
        }
        AlibcCallbackContext.showProcessContext = aVar;
        activity.startActivityForResult(intent, AlibcWebViewActivity.e);
    }
}
