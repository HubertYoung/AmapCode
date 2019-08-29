package com.alipay.zoloz.toyger.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.webkit.JsPromptResult;
import android.webkit.WebView;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.ProgressBar;
import com.alipay.mobile.security.bio.utils.BioLog;

public class ToygerProgressWebView extends WebView {
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public ProgressBar mProgressBar;

    public class WebChromeClient extends android.webkit.WebChromeClient {
        public WebChromeClient() {
        }

        public void onProgressChanged(WebView webView, int i) {
            if (i == 100) {
                ToygerProgressWebView.this.mProgressBar.setVisibility(8);
            } else {
                if (ToygerProgressWebView.this.mProgressBar.getVisibility() == 8) {
                    ToygerProgressWebView.this.mProgressBar.setVisibility(0);
                }
                ToygerProgressWebView.this.mProgressBar.setProgress(i);
            }
            super.onProgressChanged(webView, i);
        }

        public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
            BioLog.i("onJsPrompt:" + str2);
            if ("face_auth".endsWith(str2)) {
                super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
                jsPromptResult.cancel();
                ToygerProgressWebView.this.mHandler.sendMessage(ToygerProgressWebView.this.mHandler.obtainMessage(0));
                return true;
            } else if ("navi_close".endsWith(str2)) {
                super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
                jsPromptResult.cancel();
                ToygerProgressWebView.this.mHandler.sendMessage(ToygerProgressWebView.this.mHandler.obtainMessage(3));
                return true;
            } else if (!"guide_log".endsWith(str2)) {
                return super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
            } else {
                super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
                jsPromptResult.cancel();
                Message obtainMessage = ToygerProgressWebView.this.mHandler.obtainMessage(4);
                obtainMessage.obj = str3;
                ToygerProgressWebView.this.mHandler.sendMessage(obtainMessage);
                return true;
            }
        }
    }

    public ToygerProgressWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mProgressBar = new ProgressBar(context, null, 16842872);
        this.mProgressBar.setLayoutParams(new LayoutParams(-1, 3, 0, 0));
        addView(this.mProgressBar);
        setWebChromeClient(new WebChromeClient());
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        LayoutParams layoutParams = (LayoutParams) this.mProgressBar.getLayoutParams();
        layoutParams.x = i;
        layoutParams.y = i2;
        this.mProgressBar.setLayoutParams(layoutParams);
        super.onScrollChanged(i, i2, i3, i4);
    }
}
