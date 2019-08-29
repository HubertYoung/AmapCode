package com.alibaba.baichuan.android.trade.c.a.b;

import android.webkit.WebView;
import com.alibaba.baichuan.android.trade.c.a.a.b.c;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.alibaba.baichuan.android.trade.utils.StringUtils;
import com.alibaba.baichuan.android.trade.utils.i;

class e implements AlibcLoginCallback {
    final /* synthetic */ c a;
    final /* synthetic */ d b;

    e(d dVar, c cVar) {
        this.b = dVar;
        this.a = cVar;
    }

    public void onFailure(int i, String str) {
        if (this.a != null) {
            WebView webView = this.a.e != null ? this.a.e.getWebView() : null;
            if (webView != null && !StringUtils.obj2Boolean(webView.getTag(i.a(this.a.g, "id", "com_taobao_nb_sdk_webview_click")))) {
                if (this.a.e.a()) {
                    this.a.e.e();
                } else if (this.a.g != null) {
                    this.a.g.finish();
                }
            }
        }
    }

    public void onSuccess() {
        this.a.e.d();
        this.b.a();
    }
}
