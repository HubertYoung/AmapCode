package com.alipay.zoloz.toyger.workspace;

import android.os.Handler;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.alipay.mobile.security.bio.utils.BioLog;

public class NavWebChromeClient extends WebChromeClient {
    public Handler mMainHandler;

    public NavWebChromeClient(Handler handler) {
        this.mMainHandler = handler;
    }

    public void onProgressChanged(WebView webView, int i) {
        super.onProgressChanged(webView, i);
    }

    public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        BioLog.i("onJsPrompt:" + str2);
        if ("face_auth".endsWith(str2)) {
            super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
            jsPromptResult.cancel();
            this.mMainHandler.sendMessage(this.mMainHandler.obtainMessage(0));
            return true;
        } else if ("navi_close".endsWith(str2)) {
            super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
            jsPromptResult.cancel();
            this.mMainHandler.sendMessage(this.mMainHandler.obtainMessage(3));
            return true;
        } else if (!"guide_log".endsWith(str2)) {
            return super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
        } else {
            super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
            jsPromptResult.cancel();
            this.mMainHandler.sendMessage(this.mMainHandler.obtainMessage(4, str3));
            return true;
        }
    }
}
