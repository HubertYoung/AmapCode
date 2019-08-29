package com.ali.auth.third.accountlink.ui;

import android.webkit.WebView;
import com.ali.auth.third.accountlink.AccountLinkService;
import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.core.model.ResultCode;
import com.ali.auth.third.ui.webview.AuthWebView;
import com.ali.auth.third.ui.webview.BaseWebViewClient;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class a extends BaseWebViewClient {
    final /* synthetic */ UnbindWebViewActivity a;

    a(UnbindWebViewActivity unbindWebViewActivity) {
        this.a = unbindWebViewActivity;
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (str.startsWith("https://accountlink.taobao.com/confirmUnbind.htm")) {
            this.a.setResult(ResultCode.SUCCESS.code, null);
            this.a.finish();
            return true;
        } else if (str.startsWith("http://err.taobao.com/error2.html")) {
            this.a.setResult(ResultCode.USER_CANCEL.code, null);
            this.a.finish();
            return true;
        } else if (com.ali.auth.third.accountlink.a.a.e.isLoginUrl(str)) {
            this.a.a = true;
            ((AccountLinkService) MemberSDK.getService(AccountLinkService.class)).bind(new b(this, webView));
            return true;
        } else {
            if (str.startsWith("https://aq.taobao.com/durex/wirelessValidate") && str.contains("+")) {
                String[] split = str.substring(str.indexOf("?") + 1, str.length()).split("&");
                int i = 0;
                while (true) {
                    if (i >= split.length) {
                        break;
                    }
                    String[] split2 = split[i].split("=");
                    if (split2.length == 2 && split2[0].equals("param")) {
                        try {
                            str = str.replace(split2[1], URLEncoder.encode(split2[1], "UTF-8"));
                            break;
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    i++;
                }
            }
            if (webView instanceof AuthWebView) {
                ((AuthWebView) webView).loadUrl(str);
                return true;
            }
            webView.loadUrl(str);
            return true;
        }
    }
}
