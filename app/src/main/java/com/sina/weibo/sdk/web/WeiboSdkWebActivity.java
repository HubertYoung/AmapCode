package com.sina.weibo.sdk.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.sina.weibo.sdk.auth.BaseSsoHandler;
import com.sina.weibo.sdk.utils.ResourceManager;
import com.sina.weibo.sdk.utils.UIUtils;
import com.sina.weibo.sdk.utils.WbUtils;
import com.sina.weibo.sdk.web.client.AuthWebViewClient;
import com.sina.weibo.sdk.web.client.BaseWebViewClient;
import com.sina.weibo.sdk.web.client.DefaultWebViewClient;
import com.sina.weibo.sdk.web.client.ShareWebViewClient;
import com.sina.weibo.sdk.web.param.AuthWebViewRequestParam;
import com.sina.weibo.sdk.web.param.BaseWebViewRequestParam;
import com.sina.weibo.sdk.web.param.BaseWebViewRequestParam.ExtraTaskCallback;
import com.sina.weibo.sdk.web.param.DefaultWebViewRequestParam;
import com.sina.weibo.sdk.web.param.ShareWebViewRequestParam;
import com.sina.weibo.sdk.web.view.LoadingBar;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;

public class WeiboSdkWebActivity extends Activity implements WebViewRequestCallback {
    public static final String BROWSER_CLOSE_SCHEME = "sinaweibo://browser/close";
    private static final String CANCEL_EN = "Close";
    private static final String CANCEL_ZH_CN = "关闭";
    private static final String CANCEL_ZH_TW = "关闭";
    private static final String CHANNEL_DATA_ERROR_EN = "channel_data_error";
    private static final String CHANNEL_DATA_ERROR_ZH_CN = "重新加载";
    private static final String CHANNEL_DATA_ERROR_ZH_TW = "重新載入";
    private static final String EMPTY_PROMPT_BAD_NETWORK_UI_EN = "A network error occurs, please tap the button to reload";
    private static final String EMPTY_PROMPT_BAD_NETWORK_UI_ZH_CN = "网络出错啦，请点击按钮重新加载";
    private static final String EMPTY_PROMPT_BAD_NETWORK_UI_ZH_TW = "網路出錯啦，請點擊按鈕重新載入";
    private static final String LOADINFO_EN = "Loading....";
    private static final String LOADINFO_ZH_CN = "加载中....";
    private static final String LOADINFO_ZH_TW = "載入中....";
    private static final String WEIBOBROWSER_NO_TITLE_EN = "No Title";
    private static final String WEIBOBROWSER_NO_TITLE_ZH_CN = "无标题";
    private static final String WEIBOBROWSER_NO_TITLE_ZH_TW = "無標題";
    /* access modifiers changed from: private */
    public BaseWebViewRequestParam baseParam;
    private TextView leftBtn;
    /* access modifiers changed from: private */
    public LoadingBar loadingBar;
    /* access modifiers changed from: private */
    public int pageStatus = 0;
    private Button retryBtn;
    private LinearLayout retryLayout;
    private TextView retryTitle;
    /* access modifiers changed from: private */
    public TextView titleText;
    /* access modifiers changed from: private */
    public WebView webView;
    /* access modifiers changed from: private */
    public BaseWebViewClient webViewClient;

    class MyChromeClient extends WebChromeClient {
        private MyChromeClient() {
        }

        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            WeiboSdkWebActivity.this.loadingBar.drawProgress(i);
            if (i == 100) {
                WeiboSdkWebActivity.this.loadingBar.setVisibility(4);
            } else {
                WeiboSdkWebActivity.this.loadingBar.setVisibility(0);
            }
        }

        public void onReceivedTitle(WebView webView, String str) {
            super.onReceivedTitle(webView, str);
            if (TextUtils.isEmpty(WeiboSdkWebActivity.this.baseParam.getBaseData().getSpecifyTitle())) {
                WeiboSdkWebActivity.this.titleText.setText(str);
            }
        }
    }

    public void onPageStartedCallBack(WebView webView2, String str, Bitmap bitmap) {
    }

    public boolean shouldOverrideUrlLoadingCallBack(WebView webView2, String str) {
        return false;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        setContentView(initView());
        initLoad();
    }

    private void initLoad() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }
        int i = extras.getInt("type", -1);
        if (i == -1) {
            finish();
            return;
        }
        switch (i) {
            case 0:
                this.baseParam = new DefaultWebViewRequestParam();
                this.webViewClient = new DefaultWebViewClient(this, this.baseParam);
                break;
            case 1:
                this.baseParam = new ShareWebViewRequestParam(this);
                this.webViewClient = new ShareWebViewClient(this, this, this.baseParam);
                break;
            case 2:
                this.baseParam = new AuthWebViewRequestParam();
                this.webViewClient = new AuthWebViewClient(this, this, this.baseParam);
                break;
        }
        this.webView.setWebViewClient(this.webViewClient);
        this.baseParam.transformBundle(extras);
        initWebView();
        if (this.baseParam.hasExtraTask()) {
            this.baseParam.doExtraTask(new ExtraTaskCallback() {
                public void onComplete(String str) {
                    if (WeiboSdkWebActivity.this.checkRequestUrl(WeiboSdkWebActivity.this.baseParam.getRequestUrl())) {
                        WeiboSdkWebActivity.this.webView.loadUrl(WeiboSdkWebActivity.this.baseParam.getRequestUrl());
                    }
                }

                public void onException(String str) {
                    WeiboSdkWebActivity.this.webViewClient.errorBack(WeiboSdkWebActivity.this, "pic upload error");
                    WeiboSdkWebActivity.this.finish();
                }
            });
            return;
        }
        String requestUrl = this.baseParam.getRequestUrl();
        if (checkRequestUrl(requestUrl)) {
            this.webView.loadUrl(requestUrl);
        }
    }

    /* access modifiers changed from: private */
    public boolean checkRequestUrl(String str) {
        return !TextUtils.isEmpty(str) && (str.startsWith(ShareWebViewRequestParam.SHARE_URL) || str.startsWith(BaseSsoHandler.OAUTH2_BASE_URL));
    }

    private View initView() {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setBackgroundColor(-1);
        RelativeLayout relativeLayout2 = new RelativeLayout(this);
        this.leftBtn = new TextView(this);
        this.leftBtn.setTextSize(17.0f);
        this.leftBtn.setTextColor(ResourceManager.createColorStateList(-32256, 1728020992));
        this.leftBtn.setText(ResourceManager.getString(this, CANCEL_EN, "关闭", "关闭"));
        this.leftBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                WeiboSdkWebActivity.this.webViewClient.closeWeb();
                WeiboSdkWebActivity.this.closeActivity();
            }
        });
        this.titleText = new TextView(this);
        this.titleText.setTextSize(18.0f);
        this.titleText.setTextColor(-11382190);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        LayoutParams layoutParams2 = new LayoutParams(-2, -2);
        layoutParams.addRule(15);
        this.leftBtn.setPadding(UIUtils.dip2px(10, this), 0, UIUtils.dip2px(10, this), 0);
        layoutParams2.addRule(13);
        relativeLayout2.addView(this.leftBtn, layoutParams);
        relativeLayout2.addView(this.titleText, layoutParams2);
        relativeLayout.addView(relativeLayout2, new LayoutParams(-1, UIUtils.dip2px(55, this)));
        this.webView = new WebView(getApplicationContext());
        this.webView.getSettings().setSavePassword(false);
        this.webView.getSettings().setAllowFileAccess(false);
        this.webView.getSettings().setAllowContentAccess(false);
        LayoutParams layoutParams3 = new LayoutParams(-1, -1);
        layoutParams3.topMargin = UIUtils.dip2px(55, this);
        relativeLayout.addView(this.webView, layoutParams3);
        this.loadingBar = new LoadingBar(this);
        LayoutParams layoutParams4 = new LayoutParams(-1, UIUtils.dip2px(3, this));
        layoutParams4.topMargin = UIUtils.dip2px(55, this);
        relativeLayout.addView(this.loadingBar, layoutParams4);
        View view = new View(this);
        view.setBackgroundResource(getResources().getIdentifier("weibosdk_common_shadow_top", ResUtils.DRAWABLE, getPackageName()));
        LayoutParams layoutParams5 = new LayoutParams(-1, UIUtils.dip2px(3, this));
        layoutParams5.topMargin = UIUtils.dip2px(55, this);
        relativeLayout.addView(view, layoutParams5);
        this.retryLayout = new LinearLayout(this);
        this.retryLayout.setOrientation(1);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(getResources().getIdentifier("weibosdk_empty_failed", ResUtils.DRAWABLE, getPackageName()));
        this.retryLayout.addView(imageView);
        this.retryTitle = new TextView(this);
        this.retryTitle.setTextSize(14.0f);
        this.retryTitle.setTextColor(-4342339);
        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams6.topMargin = UIUtils.dip2px(18, this);
        layoutParams6.bottomMargin = UIUtils.dip2px(20, this);
        this.retryLayout.addView(this.retryTitle, layoutParams6);
        this.retryBtn = new Button(this);
        this.retryBtn.setTextSize(16.0f);
        this.retryBtn.setTextColor(-8882056);
        LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(UIUtils.dip2px(DumpSegment.ANDROID_ROOT_JNI_MONITOR, this), UIUtils.dip2px(46, this));
        layoutParams7.gravity = 17;
        this.retryLayout.addView(this.retryBtn, layoutParams7);
        this.retryBtn.setBackgroundResource(getResources().getIdentifier("retry_btn_selector", ResUtils.DRAWABLE, getPackageName()));
        LayoutParams layoutParams8 = new LayoutParams(-2, -2);
        layoutParams8.addRule(13);
        relativeLayout.addView(this.retryLayout, layoutParams8);
        this.retryLayout.setVisibility(8);
        this.webView.setWebChromeClient(new MyChromeClient());
        this.retryBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                WeiboSdkWebActivity.this.pageStatus = 0;
                WeiboSdkWebActivity.this.showDefaultPage();
                WeiboSdkWebActivity.this.webView.reload();
            }
        });
        this.retryTitle.setText(ResourceManager.getString(this, EMPTY_PROMPT_BAD_NETWORK_UI_EN, EMPTY_PROMPT_BAD_NETWORK_UI_ZH_CN, EMPTY_PROMPT_BAD_NETWORK_UI_ZH_TW));
        this.retryBtn.setText(ResourceManager.getString(this, CHANNEL_DATA_ERROR_EN, CHANNEL_DATA_ERROR_ZH_CN, CHANNEL_DATA_ERROR_ZH_TW));
        return relativeLayout;
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void initWebView() {
        if (!TextUtils.isEmpty(this.baseParam.getBaseData().getSpecifyTitle())) {
            this.titleText.setText(this.baseParam.getBaseData().getSpecifyTitle());
        }
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setSavePassword(false);
        this.webView.getSettings().setUserAgentString(WbUtils.generateUA(this, this.baseParam.getBaseData().getAuthInfo().getAppKey()));
        this.webView.requestFocus();
        this.webView.setScrollBarStyle(0);
        removeJavascriptInterface(this.webView, "searchBoxJavaBridge_");
        removeJavascriptInterface(this.webView, "accessibility");
        removeJavascriptInterface(this.webView, "accessibilityTraversal");
        if (VERSION.SDK_INT >= 21) {
            this.webView.getSettings().setMixedContentMode(2);
        }
    }

    public static void removeJavascriptInterface(WebView webView2, String str) {
        try {
            WebView.class.getDeclaredMethod("removeJavascriptInterface", new Class[]{String.class}).invoke(webView2, new Object[]{str});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void closeActivity() {
        finish();
    }

    private void showErrorPage() {
        this.retryLayout.setVisibility(0);
        this.webView.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void showDefaultPage() {
        this.retryLayout.setVisibility(8);
        this.webView.setVisibility(0);
    }

    public void onPageFinishedCallBack(WebView webView2, String str) {
        if (this.pageStatus == -1) {
            showErrorPage();
        } else {
            showDefaultPage();
        }
    }

    public void onReceivedErrorCallBack(WebView webView2, int i, String str, String str2) {
        String url = webView2.getUrl();
        try {
            if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(str2)) {
                Uri parse = Uri.parse(url);
                Uri parse2 = Uri.parse(str2);
                if (parse.getHost().equals(parse2.getHost()) && parse.getScheme().equals(parse2.getScheme())) {
                    this.pageStatus = -1;
                }
            }
        } catch (Exception unused) {
        }
    }

    public void onReceivedSslErrorCallBack(WebView webView2, final SslErrorHandler sslErrorHandler, SslError sslError) {
        Builder builder = new Builder(this);
        builder.setTitle("警告");
        builder.setMessage("你访问的连接可能存在隐患，是否继续访问");
        builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                sslErrorHandler.proceed();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                sslErrorHandler.cancel();
            }
        });
        builder.create().show();
    }

    public void closePage() {
        finish();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            if (this.webViewClient.onBackKeyDown()) {
                return true;
            }
            if (this.webView.canGoBack()) {
                this.webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }
}
