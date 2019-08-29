package com.alipay.zoloz.toyger.workspace;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.mobile.security.bio.utils.StringUtil;

public class NavWebViewClient extends WebViewClient {
    public static final int HANDLE_MSG_LOAD_LOCAL_URL = 1;
    public static final int HANDLE_MSG_LOAD__URL_FAIL = 2;
    public static final int HANDLE_MSG_RECORD = 4;
    public static final int HANDLE_MSG_START_SAMPLE = 0;
    public static final int HANDLE_MSG_STOP_SAMPLE = 3;
    Handler mHandler;
    private String mUserName;

    public NavWebViewClient(Handler handler, String str) {
        this.mUserName = str;
        this.mHandler = handler;
        this.mHandler.removeMessages(1);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), 1500);
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
    }

    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        if (!StringUtil.isNullorEmpty(this.mUserName)) {
            webView.loadUrl("javascript:setUserName('" + this.mUserName + "')");
        }
        this.mHandler.removeMessages(1);
    }

    public void onReceivedError(WebView webView, int i, String str, String str2) {
        super.onReceivedError(webView, i, str, str2);
        this.mHandler.sendEmptyMessage(2);
    }

    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
    }
}
