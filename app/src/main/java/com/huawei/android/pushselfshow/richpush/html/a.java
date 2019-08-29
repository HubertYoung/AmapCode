package com.huawei.android.pushselfshow.richpush.html;

import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;
import com.huawei.android.pushagent.a.a.c;

class a implements DownloadListener {
    final /* synthetic */ HtmlViewer a;

    a(HtmlViewer htmlViewer) {
        this.a = htmlViewer;
    }

    public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
        try {
            c.a("PushSelfShowLog", "url=".concat(String.valueOf(str)));
            c.a("PushSelfShowLog", "userAgent=".concat(String.valueOf(str2)));
            c.a("PushSelfShowLog", "contentDisposition=".concat(String.valueOf(str3)));
            c.a("PushSelfShowLog", "mimetype=".concat(String.valueOf(str4)));
            c.a("PushSelfShowLog", "contentLength=".concat(String.valueOf(j)));
            this.a.d.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception e) {
            c.a((String) "PushSelfShowLog", (String) "onDownloadStart err", (Throwable) e);
        }
    }
}
