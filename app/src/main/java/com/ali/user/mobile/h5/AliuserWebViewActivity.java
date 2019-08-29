package com.ali.user.mobile.h5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JsPromptResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APButton;
import com.ali.user.mobile.ui.widget.APFlowTipView;
import com.ali.user.mobile.ui.widget.APTitleBar;
import com.alipay.mobile.h5container.api.H5Param;

public class AliuserWebViewActivity extends BaseActivity {
    private String actionUrl = "";
    private APButton mActionButton;
    private APFlowTipView mApFlowTipView;
    /* access modifiers changed from: private */
    public String mErrorActionUrl;
    /* access modifiers changed from: private */
    public boolean mLoadUrlError;
    /* access modifiers changed from: private */
    public APTitleBar mTitleBar;
    private String mUrl;
    /* access modifiers changed from: private */
    public WebView mWebView;
    private String title = "";

    public class AliUserNavWebChromeClient extends WebChromeClient {
        public AliUserNavWebChromeClient() {
        }

        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            if (i == 100) {
                AliuserWebViewActivity.this.mTitleBar.stopProgressBar();
            }
        }

        public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
            if (!"navi_close".endsWith(str2)) {
                return super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
            }
            super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
            jsPromptResult.cancel();
            return true;
        }
    }

    public class AliUserNavWebViewClient extends WebViewClient {
        public AliUserNavWebViewClient() {
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (!AliuserWebViewActivity.this.mLoadUrlError) {
                AliuserWebViewActivity.this.hideErrorPage();
            } else {
                AliuserWebViewActivity.this.showErrorPage();
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            AliuserWebViewActivity.this.mLoadUrlError = true;
            AliuserWebViewActivity.this.mErrorActionUrl = str2;
            AliuserWebViewActivity.this.showErrorPage();
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            return super.shouldOverrideUrlLoading(webView, str);
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
        }
    }

    public void setAppId() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.M);
        Intent intent = getIntent();
        if (intent != null) {
            this.actionUrl = intent.getStringExtra("url");
            this.title = intent.getStringExtra("dt");
        }
        initTitleBar();
        initWebView();
        initErrorPage();
        startLoadUrl(this.actionUrl);
        setOnBackBtnListener();
    }

    private void initTitleBar() {
        this.mTitleBar = (APTitleBar) findViewById(R.id.m);
        this.mTitleBar.setBackgroundColor(-1);
        this.mTitleBar.setTitleText(this.title);
        this.mTitleBar.setVisibility(0);
    }

    private void initWebView() {
        this.mWebView = (WebView) findViewById(R.id.af);
        this.mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        this.mWebView.removeJavascriptInterface("accessibility");
        this.mWebView.removeJavascriptInterface("accessibilityTraversal");
        this.mWebView.setWebViewClient(new AliUserNavWebViewClient());
        this.mWebView.setWebChromeClient(new AliUserNavWebChromeClient());
        this.mWebView.setHorizontalScrollBarEnabled(false);
        this.mWebView.setVerticalScrollBarEnabled(false);
        setWebViewSettings();
    }

    private void initErrorPage() {
        this.mApFlowTipView = (APFlowTipView) findViewById(R.id.k);
        this.mApFlowTipView.resetFlowTipType(16);
        this.mApFlowTipView.setTips(getString(R.string.br));
        this.mApFlowTipView.setSubTips(getString(R.string.bq));
        this.mActionButton = this.mApFlowTipView.getActionButton();
        this.mActionButton.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void startLoadUrl(String str) {
        this.mApFlowTipView.setVisibility(8);
        this.mWebView.loadUrl(str);
        this.mTitleBar.startProgressBar();
    }

    private void setWebViewSettings() {
        WebSettings settings = this.mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setCacheMode(-1);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setSavePassword(false);
        settings.setDisplayZoomControls(false);
    }

    private void setOnBackBtnListener() {
        this.mTitleBar.setBackButtonListener(new OnClickListener() {
            public void onClick(View view) {
                if (AliuserWebViewActivity.this.mWebView.canGoBack()) {
                    AliuserWebViewActivity.this.mWebView.goBack();
                    AliUserLog.c("webview", H5Param.DEFAULT_LONG_BACK_BEHAVIOR);
                    return;
                }
                try {
                    ((InputMethodManager) AliuserWebViewActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                    AliuserWebViewActivity.this.onBackPressed();
                } catch (Exception unused) {
                    AliUserLog.c("webview", "back error");
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void showErrorPage() {
        this.mActionButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AliuserWebViewActivity.this.mLoadUrlError = false;
                AliuserWebViewActivity.this.startLoadUrl(AliuserWebViewActivity.this.mErrorActionUrl);
            }
        });
        this.mWebView.setVisibility(8);
        this.mApFlowTipView.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void hideErrorPage() {
        this.mWebView.setVisibility(0);
        this.mApFlowTipView.setVisibility(8);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || !this.mWebView.canGoBack()) {
            return super.onKeyDown(i, keyEvent);
        }
        this.mWebView.goBack();
        AliUserLog.c("webview", H5Param.DEFAULT_LONG_BACK_BEHAVIOR);
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
