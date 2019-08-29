package com.amap.bundle.webview.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import com.alipay.mobile.common.transportext.biz.util.NetInfoHelper;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.webview.widget.AbstractBaseWebView.c;
import com.amap.bundle.webview.widget.WebViewEx.a;
import com.amap.bundle.webview.widget.WebViewEx.b;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.OnWebViewEventListener;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import com.uc.webview.export.DownloadListener;
import com.uc.webview.export.GeolocationPermissions.Callback;
import com.uc.webview.export.JsResult;
import com.uc.webview.export.WebBackForwardList;
import com.uc.webview.export.WebChromeClient;
import com.uc.webview.export.WebHistoryItem;
import com.uc.webview.export.WebSettings;
import com.uc.webview.export.WebView;
import com.uc.webview.export.WebViewClient;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
public class NormalWebView extends AbstractBaseWebView implements OnCancelListener {
    public static final String TAG = "NormalWebView";
    /* access modifiers changed from: private */
    public boolean isWebViewAlive = true;
    private boolean mOpenSafeExFeature = false;
    private WebChromeClient mWebChromeClient = new a() {
        public final void onGeolocationPermissionsShowPrompt(String str, Callback callback) {
            callback.invoke(str, true, true);
            super.onGeolocationPermissionsShowPrompt(str, callback);
        }

        public final void onReceivedTitle(WebView webView, String str) {
            if (NormalWebView.this.mOnWebViewEventListener != null) {
                NormalWebView.this.mOnWebViewEventListener.onReceivedTitle(webView, str);
            }
        }

        public final void onProgressChanged(WebView webView, int i) {
            if (NormalWebView.this.mShowTopProress) {
                NormalWebView.this.mTopProgressBar.setProgress(i);
            }
            if (i == 100) {
                NormalWebView.this.mProgressBar.dismiss();
                NormalWebView.this.mTopProgressBar.setVisibility(8);
            }
        }

        public final boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
            final bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null) {
                return true;
            }
            AlertView.a aVar = new AlertView.a(pageContext.getActivity());
            aVar.a((CharSequence) str2);
            aVar.a(false);
            aVar.a(17039370, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                }
            });
            aVar.a(true);
            AlertView a2 = aVar.a();
            pageContext.showViewLayer(a2);
            a2.startAnimation();
            jsResult.confirm();
            return true;
        }

        public final boolean onJsConfirm(WebView webView, String str, String str2, final JsResult jsResult) {
            final bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null) {
                return true;
            }
            AlertView.a aVar = new AlertView.a(pageContext.getActivity());
            aVar.a((CharSequence) str2);
            aVar.a(false);
            aVar.a(17039370, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    jsResult.confirm();
                }
            });
            aVar.b(17039360, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                }
            });
            aVar.a(true);
            AlertView a2 = aVar.a();
            pageContext.showViewLayer(a2);
            a2.startAnimation();
            return true;
        }
    };
    private WebViewClient mWebViewClient = new b() {
        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
        }

        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            AMapLog.i(NormalWebView.TAG, "url:".concat(String.valueOf(str)));
            AMapLog.e("Aragorn", "shouldOverrideUrlLoading， url = ".concat(String.valueOf(str)));
            if (boe.a(webView, str) || boe.a(str)) {
                return true;
            }
            if (TextUtils.isEmpty(str) || NormalWebView.this.mLaunchTaobaoLogin == null || !NormalWebView.this.mLaunchTaobaoLogin.a(str)) {
                if (!TextUtils.isEmpty(str) && str.contains("sso/mz-login")) {
                    NormalWebView.this.mMeizuAuthroizedListener.b(Uri.parse(str).getQueryParameter("code"));
                }
                if (NormalWebView.this.mShowProgress) {
                    NormalWebView.this.mProgressBar.show();
                }
                if (NormalWebView.this.mShowTopProress) {
                    NormalWebView.this.mTopProgressBar.setVisibility(0);
                }
                if (NormalWebView.this.mOnWebViewEventListener != null) {
                    NormalWebView.this.mOnWebViewEventListener.onWebViewPageStart(NormalWebView.this.mCurWebView);
                }
                StringBuilder sb = new StringBuilder();
                WebViewEx webViewEx = (WebViewEx) webView;
                if (a(webViewEx, sb, str)) {
                    webViewEx.loadUrl(sb.toString());
                    return true;
                } else if (!webViewEx.isTmallHost(str)) {
                    return false;
                } else {
                    webViewEx.loadUrl(str);
                    return true;
                }
            } else {
                NormalWebView.this.mLaunchTaobaoLogin.c();
                return true;
            }
        }

        public final void onPageFinished(final WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (NormalWebView.this.mOnWebViewEventListener != null) {
                NormalWebView.this.mIsPageFinished = true;
                NormalWebView.this.mOnWebViewEventListener.onWebViewPageFinished(webView);
            }
            ahl.b(new a() {
                public final void onError(Throwable th) {
                }

                public final Object doBackground() throws Exception {
                    return ahd.a(NormalWebView.this.mContext, (String) "js/activeEvent.js", Charset.forName("utf-8"));
                }

                public final void onFinished(Object obj) {
                    if (NormalWebView.this.isWebViewAlive && (obj instanceof String)) {
                        String str = (String) obj;
                        if (!TextUtils.isEmpty(str)) {
                            WebViewEx webViewEx = NormalWebView.this.mCurWebView;
                            StringBuilder sb = new StringBuilder("javascript:");
                            sb.append(str);
                            webViewEx.loadUrl(sb.toString());
                        }
                        if (webView != null) {
                            webView.requestFocus();
                        }
                    }
                }
            });
        }

        public final void onReceivedError(WebView webView, int i, String str, String str2) {
            webView.clearCache(true);
            NormalWebView.this.oldUrl = str2;
            NormalWebView.this.saveWebError(i, str, str2);
            StringBuilder sb = new StringBuilder();
            WebViewEx webViewEx = (WebViewEx) webView;
            if (b(webViewEx, sb, str2) && sb.length() > 0) {
                webViewEx.setUrlRewriteEnable(false);
                NormalWebView.this.loadUrl(sb.toString());
                webViewEx.setUrlRewriteEnable(true);
            } else if (i == 404) {
                NormalWebView.this.loadUrl("file:///android_asset/not_found_error.html");
            } else {
                NormalWebView.this.loadUrl("file:///android_asset/connect_error.html");
            }
        }
    };
    c mjavaScrpitHandler;
    OnLongClickListener onLongClickListener = new OnLongClickListener() {
        public final boolean onLongClick(View view) {
            return false;
        }
    };

    /* access modifiers changed from: protected */
    public void onPause() {
    }

    /* access modifiers changed from: protected */
    public void onResume() {
    }

    public void reload(String str) {
    }

    public void showBottomMenu(boolean z) {
    }

    public NormalWebView(Context context) {
        super(context);
        init(context);
    }

    public NormalWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public NormalWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public c getJavaScriptInterface() {
        return this.mjavaScrpitHandler;
    }

    /* access modifiers changed from: protected */
    public void init(Context context) {
        this.mContext = context;
        this.mHandler = new Handler();
        this.mTopProgressBar = new ProgressBar(this.mContext, null, 16842872);
        this.mTopProgressBar.setId(R.id.normal_web_view_progress_id);
        this.mTopProgressBar.setVisibility(8);
        LayoutParams layoutParams = new LayoutParams(-1, 4);
        layoutParams.addRule(10);
        addView(this.mTopProgressBar, layoutParams);
        this.mProgressBar = new ProgressDlg(AMapAppGlobal.getTopActivity(), getContext().getString(R.string.loading));
        this.mProgressBar.setCancelable(true);
        this.mProgressBar.setOnCancelListener(this);
        this.mCurWebView = new WebViewEx(context);
        this.mCurWebView.setContentDescription(TAG);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        layoutParams2.addRule(3, this.mTopProgressBar.getId());
        layoutParams2.alignWithParent = true;
        addView(this.mCurWebView, layoutParams2);
        adapteFoldScreen(this.mCurWebView);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        adapteFoldScreen(this.mCurWebView);
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        this.mCurWebView.setDownloadListener(downloadListener);
    }

    public void initializeWebView(Object obj, Handler handler, boolean z, boolean z2) {
        initializeWebView((WebView) this.mCurWebView, obj, handler, z, z2);
    }

    public void initializeWebView(WebView webView, Object obj, Handler handler, boolean z, boolean z2) {
        this.mShowProgress = z2;
        initializeWebView(webView, obj, handler, z);
    }

    public void initializeWebView(Object obj, Handler handler, boolean z, boolean z2, boolean z3) {
        initializeWebView(this.mCurWebView, obj, handler, z, z2, z3);
    }

    public void initializeWebView(WebView webView, Object obj, Handler handler, boolean z, boolean z2, boolean z3) {
        this.mShowProgress = z2;
        this.mSupportZoom = z3;
        initializeWebView(webView, obj, handler, z);
    }

    public void initializeWebView(WebView webView, Object obj, Handler handler, boolean z) {
        initializeWebView(true, webView, obj, handler, z);
    }

    public void initializeWebView(boolean z, WebView webView, Object obj, Handler handler, boolean z2) {
        if (webView != null) {
            if (handler != null) {
                this.mHandler = handler;
            }
            this.jsMethods = obj;
            this.isEnableJs = z2;
            if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 18) {
                webView.setLayerType(1, null);
            }
            webView.setDrawingCacheEnabled(false);
            if (this.mOpenSafeExFeature) {
                this.mCurWebView.openSafeExFeature();
            }
            WebSettings settings = webView.getSettings();
            try {
                settings.setJavaScriptEnabled(z2);
                disableAccessibility();
            } catch (NullPointerException unused) {
            }
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setTextZoom(100);
            addAmapUserAgent(settings);
            if (z) {
                settings.setAppCacheEnabled(true);
                settings.setAppCacheMaxSize(20971520);
                settings.setCacheMode(-1);
            } else {
                settings.setAppCacheEnabled(false);
                settings.setCacheMode(2);
            }
            settings.setDatabaseEnabled(true);
            String path = getContext().getDir("databases", 0).getPath();
            settings.setDatabasePath(path);
            settings.setAllowFileAccess(true);
            settings.setDomStorageEnabled(true);
            settings.setGeolocationEnabled(true);
            settings.setGeolocationDatabasePath(path);
            settings.setAllowContentAccess(true);
            if (VERSION.SDK_INT >= 16) {
                settings.setAllowUniversalAccessFromFileURLs(true);
            }
            if (this.mSupportZoom) {
                settings.setBuiltInZoomControls(true);
                settings.setSupportZoom(true);
            } else {
                settings.setBuiltInZoomControls(false);
                settings.setSupportZoom(false);
            }
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            webView.setScrollBarStyle(0);
            this.mjavaScrpitHandler = new c(obj);
            webView.addJavascriptInterface(this.mjavaScrpitHandler, "jsInterface");
            webView.setWebViewClient(this.mWebViewClient);
            webView.setWebChromeClient(this.mWebChromeClient);
            webView.setDownloadListener(new DownloadListener() {
                public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                    try {
                        if (intent.resolveActivity(NormalWebView.this.getContext().getPackageManager()) != null) {
                            Activity topActivity = AMapAppGlobal.getTopActivity();
                            if (topActivity != null) {
                                topActivity.startActivity(intent);
                            }
                        }
                    } catch (Exception unused) {
                    }
                }
            });
            webView.setOnLongClickListener(this.onLongClickListener);
        }
    }

    /* access modifiers changed from: protected */
    public void addAmapUserAgent(WebSettings webSettings) {
        if (webSettings != null) {
            StringBuilder sb = new StringBuilder();
            String userAgentString = webSettings.getUserAgentString();
            if (!TextUtils.isEmpty(userAgentString)) {
                sb.append(userAgentString);
                sb.append(Token.SEPARATOR);
            }
            String str = a.a().a;
            String a = ajp.a();
            sb.append("amap/");
            sb.append(str);
            sb.append(Token.SEPARATOR);
            sb.append("Mac=".concat(String.valueOf(a)));
            sb.append(Token.SEPARATOR);
            String str2 = null;
            String[] stringArray = getResources().getStringArray(R.array.network_type);
            switch (aaw.b(getContext())) {
                case 1:
                    str2 = stringArray[1];
                    break;
                case 2:
                    str2 = stringArray[2];
                    break;
                case 3:
                    str2 = stringArray[3];
                    break;
                case 4:
                    str2 = stringArray[0];
                    break;
            }
            if (!TextUtils.isEmpty(str2)) {
                sb.append("NetType/");
                sb.append(str2);
            }
            webSettings.setUserAgentString(sb.toString());
        }
    }

    public void loadUrl(String str) {
        if (this.mCurWebView != null) {
            setWebViewProxy();
            if (!"about:blank".equals(str)) {
                if (this.mShowProgress) {
                    this.mProgressBar.show();
                }
                if (this.mShowTopProress) {
                    this.mTopProgressBar.setVisibility(0);
                }
            }
            logInvalidFileUrl(str);
            this.mCurWebView.loadUrl(str);
        }
    }

    public void loadUrlInNewWebView(String str) {
        if (this.isWebViewAlive) {
            if (this.mShowProgress) {
                this.mProgressBar.show();
            }
            if (this.mShowTopProress) {
                this.mTopProgressBar.setVisibility(0);
            }
            logInvalidFileUrl(str);
            this.mCurWebView.loadUrl(str);
            this.mCurWebView.clearHistory();
        }
    }

    public void loadDataWithBaseUrl(String str, String str2, String str3, String str4, String str5) {
        if (this.isWebViewAlive) {
            this.mCurWebView.loadDataWithBaseURL(str, str2, str3, str4, str5);
            if (this.mShowProgress) {
                this.mProgressBar.show();
            }
        }
    }

    public void loadData(String str, String str2, String str3) {
        if (this.isWebViewAlive) {
            this.mCurWebView.loadData(str, str2, str3);
            if (this.mShowProgress) {
                this.mProgressBar.show();
            }
        }
    }

    public void loadJs(String str) {
        if (this.isWebViewAlive) {
            if (VERSION.SDK_INT >= 19) {
                try {
                    this.mCurWebView.evaluateJavascript(str, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                this.mCurWebView.loadUrl(str);
            }
        }
    }

    public WebView getWebView() {
        return this.mCurWebView;
    }

    public void reload() {
        if (this.isWebViewAlive) {
            if (this.mOnWebViewEventListener != null) {
                this.mOnWebViewEventListener.onWebViewPageRefresh(this.mCurWebView);
            }
            setWebViewProxy();
            if (this.mShowProgress) {
                this.mProgressBar.show();
            }
            if (this.mShowTopProress) {
                this.mTopProgressBar.setVisibility(0);
            }
            if (this.mCurWebView != null) {
                this.mCurWebView.clearView();
                String url = this.mCurWebView.getUrl();
                if (url != null) {
                    if (url.equals("file:///android_asset/not_found_error.html") || url.equals("file:///android_asset/connect_error.html")) {
                        this.mCurWebView.loadUrl(this.oldUrl);
                        return;
                    }
                    this.mCurWebView.reload();
                }
            }
        }
    }

    public void stopLoading() {
        this.mCurWebView.stopLoading();
        if (this.mShowTopProress) {
            this.mTopProgressBar.setVisibility(8);
        }
    }

    public boolean canGoBack() {
        WebBackForwardList copyBackForwardList = this.mCurWebView.copyBackForwardList();
        if (copyBackForwardList != null && copyBackForwardList.getCurrentIndex() == 1) {
            WebHistoryItem itemAtIndex = copyBackForwardList.getItemAtIndex(0);
            if (itemAtIndex.getUrl().equals(copyBackForwardList.getItemAtIndex(1).getUrl()) || itemAtIndex.getUrl().equals("about:blank")) {
                return false;
            }
        }
        return this.mCurWebView.canGoBack();
    }

    public void goBack() {
        this.mCurWebView.goBack();
    }

    public void gobackByStep() {
        goBack();
    }

    public void goBackWithJs(JSONObject jSONObject, wa waVar) {
        this.mCurWebView.goBack();
        if (this.jsMethods instanceof JsAdapter) {
            try {
                ((JsAdapter) this.jsMethods).callJs(waVar.a, jSONObject.toString());
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }

    public void goBackOrForward(int i) {
        this.mCurWebView.goBackOrForward(i);
    }

    public boolean canGoForward() {
        return this.mCurWebView.canGoForward();
    }

    public void goForward() {
        this.mCurWebView.goForward();
    }

    public String getUrl() {
        return this.mCurWebView.getUrl();
    }

    public void switched() {
        if (!this.mIsPageFinished) {
            this.mCurWebView.stopLoading();
        }
    }

    public boolean getPageFinished() {
        return this.mIsPageFinished;
    }

    public int getContentHeight() {
        return this.mCurWebView.getContentHeight();
    }

    public void clearCache(boolean z) {
        if (this.mCurWebView != null) {
            try {
                this.mCurWebView.clearCache(z);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clearView() {
        if (this.mCurWebView != null) {
            try {
                if (VERSION.SDK_INT < 18) {
                    this.mCurWebView.clearView();
                } else {
                    this.mCurWebView.loadUrl("about:blank");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clearHistory() {
        this.mCurWebView.clearHistory();
    }

    public void destroy() {
        this.isWebViewAlive = false;
        this.mCurWebView.destroyDrawingCache();
        this.mCurWebView.destroy();
    }

    public WebViewClient getWebViewClient() {
        return this.mWebViewClient;
    }

    public WebChromeClient getWebViewChromeClient() {
        return this.mWebChromeClient;
    }

    public void setOnWebViewEventListener(OnWebViewEventListener onWebViewEventListener) {
        this.mOnWebViewEventListener = onWebViewEventListener;
    }

    /* access modifiers changed from: protected */
    public void setWebViewProxy() {
        NetworkInfo networkInfo;
        try {
            networkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
            networkInfo = null;
        }
        if (networkInfo != null && networkInfo.getType() != 1) {
            String defaultHost = Proxy.getDefaultHost();
            int defaultPort = Proxy.getDefaultPort();
            if (defaultHost != null && defaultHost.equals(NetInfoHelper.CMWAP_PROXY_HOST)) {
                this.mCurWebView.setHttpAuthUsernamePassword("10.0.0.172:".concat(String.valueOf(defaultPort)), "", "", "");
            }
        }
    }

    public void setResId(int i) {
        this.mCurWebView.setId(i);
    }

    public void setShowTopProress(boolean z) {
        this.mShowTopProress = z;
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i == 8) {
            this.visible = false;
            onPause();
            return;
        }
        if (i == 0) {
            this.visible = true;
            onResume();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.visible) {
            try {
                removeAllViews();
                destroy();
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }

    public WebView getmCurWebView() {
        return this.mCurWebView;
    }

    public void setFocusable(boolean z) {
        this.focusable = z;
        this.mCurWebView.setFocusable(z);
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.mCurWebView.setOnTouchListener(this.touchListener);
    }

    public void openSafeExFeature(boolean z) {
        if (this.mCurWebView != null && (this.mCurWebView instanceof WebViewEx) && z) {
            if (this.mjavaScrpitHandler != null) {
                this.mCurWebView.openSafeExFeature();
                this.mCurWebView.addJavascriptInterface(this.mjavaScrpitHandler, "jsInterface");
                return;
            }
            this.mOpenSafeExFeature = z;
        }
    }

    public void resetWebView() {
        super.resetWebView();
        removeAllViews();
        LayoutParams layoutParams = new LayoutParams(-1, 4);
        layoutParams.addRule(10);
        addView(this.mTopProgressBar, layoutParams);
        this.mCurWebView = new WebViewEx(getContext());
        this.mCurWebView.setContentDescription(TAG);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        layoutParams2.addRule(3, this.mTopProgressBar.getId());
        layoutParams2.alignWithParent = true;
        addView(this.mCurWebView, layoutParams2);
    }

    public void saveWebError(final int i, final String str, final String str2) {
        ahm.a(new Runnable() {
            public final void run() {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("errorCode", i);
                    jSONObject.put("description", str);
                    boolean z = false;
                    if (!TextUtils.isEmpty(str2)) {
                        z = ahd.b(str2.replace("file://", ""));
                    }
                    jSONObject.put("isHasUrl", z);
                    jSONObject.put("failingUrl", str2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2(LogConstant.POI_ERROR_PAGE_ID, "B003", jSONObject);
            }
        });
    }

    public void onCancel(DialogInterface dialogInterface) {
        dialogInterface.dismiss();
    }
}
