package com.huawei.android.pushselfshow.richpush.html;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.utils.d;
import java.io.File;

class e extends WebViewClient {
    final /* synthetic */ HtmlViewer a;

    e(HtmlViewer htmlViewer) {
        this.a = htmlViewer;
    }

    public void onLoadResource(WebView webView, String str) {
        super.onLoadResource(webView, str);
    }

    public void onPageFinished(WebView webView, String str) {
        StringBuilder sb;
        super.onPageFinished(webView, str);
        StringBuilder sb2 = new StringBuilder("onPageFinished:");
        sb2.append(str);
        sb2.append(",title:");
        sb2.append(webView.getTitle());
        c.a("PushSelfShowLog", sb2.toString());
        String title = webView.getTitle();
        if (title != null && title.endsWith(".html")) {
            this.a.a(this.a.d.getString(d.a(this.a.d, "hwpush_richmedia")));
        }
        try {
            if (this.a.e != null) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.a.d.getFilesDir().getPath());
                sb3.append(File.separator);
                sb3.append("PushService");
                sb3.append(File.separator);
                sb3.append("richpush");
                sb3.append(File.separator);
                sb3.append("error.html");
                if (!str.equals(sb3.toString())) {
                    if ("text/html_local".equals(this.a.g.F)) {
                        String prepareJS = this.a.prepareJS(str);
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("var newscript = document.createElement(\"script\");");
                        sb4.append("newscript.src=\"");
                        sb4.append(prepareJS);
                        sb4.append("\";");
                        String sb5 = sb4.toString();
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(sb5);
                        sb6.append("newscript.onload=function(){ try {onDeviceReady();}catch(err){}};");
                        String sb7 = sb6.toString();
                        sb = new StringBuilder();
                        sb.append(sb7);
                        sb.append("document.body.appendChild(newscript);");
                    } else {
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("var newscript = document.createElement(\"script\");");
                        sb8.append("newscript.src=\"http://open.hicloud.com/android/push1.0.js\";");
                        String sb9 = sb8.toString();
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append(sb9);
                        sb10.append("newscript.onload=function(){ try { onDeviceReady();}catch(err){}};");
                        String sb11 = sb10.toString();
                        sb = new StringBuilder();
                        sb.append(sb11);
                        sb.append("document.body.appendChild(newscript);");
                    }
                    String sb12 = sb.toString();
                    c.a("PushSelfShowLog", "load js ".concat(String.valueOf(sb12)));
                    this.a.e.loadUrl("javascript:".concat(String.valueOf(sb12)));
                }
            }
        } catch (Exception e) {
            StringBuilder sb13 = new StringBuilder("onPageFinished load err ");
            sb13.append(e.toString());
            c.a((String) "PushSelfShowLog", sb13.toString(), (Throwable) e);
        }
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
        c.a("PushSelfShowLog", "onPageStarted:".concat(String.valueOf(str)));
        this.a.setProgress(5);
        this.a.a(this.a.d.getString(d.a(this.a.d, "hwpush_richmedia")));
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        try {
            if (!str.startsWith(com.uc.webview.export.WebView.SCHEME_MAILTO) && !str.startsWith(com.uc.webview.export.WebView.SCHEME_TEL) && !str.startsWith("smsto:") && !str.startsWith("sms:")) {
                if (!str.startsWith("geo:")) {
                    return false;
                }
            }
            this.a.d.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception e) {
            c.a((String) "PushSelfShowLog", (String) "", (Throwable) e);
        }
        return true;
    }
}
