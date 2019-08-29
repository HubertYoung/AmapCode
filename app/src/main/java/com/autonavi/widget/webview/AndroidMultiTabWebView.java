package com.autonavi.widget.webview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.alipay.mobile.common.transportext.biz.util.NetInfoHelper;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.widget.R;
import com.autonavi.widget.webview.android.SafeWebView;
import com.autonavi.widget.webview.android.WebChromeClientDispather;
import com.autonavi.widget.webview.android.WebViewClientDispatcher;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;

public class AndroidMultiTabWebView extends RelativeLayout {
    protected final String CMWAP = NetInfoHelper.CMWAP_PROXY_HOST;
    private LayoutParams mContainerParams;
    /* access modifiers changed from: private */
    public String mCurrentUrl;
    private SafeWebView mCurrentWebView;
    /* access modifiers changed from: private */
    public List<DownloadListener> mDownloadListeners = new ArrayList();
    private MultiTabWebView mFather;
    private HashMap<Object, String> mJavaScriptInterfaces = new HashMap<>();
    /* access modifiers changed from: private */
    public a mMultiTabHandle;
    /* access modifiers changed from: private */
    public ProgressBar mProgressBar;
    /* access modifiers changed from: private */
    public boolean mSupportMultiTab = false;
    private erz mUICreator;
    private WebChromeClientDispather mWebChromeClientDispather = new WebChromeClientDispather() {
        private boolean isFullScreenOrignal = false;
        private Activity mActivity = ((Activity) AndroidMultiTabWebView.this.getContext());
        @Nullable
        private View mDisplayView;
        private int mOldOrientation;

        public void onProgressChanged(WebView webView, int i) {
            if (AndroidMultiTabWebView.this.mWebViewProgressDialog != null) {
                AndroidMultiTabWebView.this.mWebViewProgressDialog;
                if (i == 100) {
                    AndroidMultiTabWebView.this.mWebViewProgressDialog;
                }
            }
            if (AndroidMultiTabWebView.this.mProgressBar != null) {
                AndroidMultiTabWebView.this.mProgressBar.setProgress(i);
                if (i == 100) {
                    AndroidMultiTabWebView.this.mProgressBar.setVisibility(8);
                }
            }
        }

        public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
            if (this.mActivity == null) {
                this.mActivity = (Activity) AndroidMultiTabWebView.this.getContext();
            }
            if (this.mActivity != null) {
                if (this.mDisplayView != null) {
                    customViewCallback.onCustomViewHidden();
                }
                this.mOldOrientation = this.mActivity.getRequestedOrientation();
                boolean z = false;
                if (this.mOldOrientation != 0) {
                    this.mActivity.setRequestedOrientation(0);
                }
                if ((this.mActivity.getWindow().getAttributes().flags | -1025) == 0) {
                    z = true;
                }
                this.isFullScreenOrignal = z;
                if (!this.isFullScreenOrignal) {
                    this.mActivity.getWindow().addFlags(1024);
                }
                this.mDisplayView = view;
                this.mActivity.addContentView(this.mDisplayView, new ViewGroup.LayoutParams(-1, -1));
                super.onShowCustomView(view, customViewCallback);
            }
        }

        public void onHideCustomView() {
            if (this.mActivity == null) {
                this.mActivity = (Activity) AndroidMultiTabWebView.this.getContext();
            }
            if (this.mActivity != null) {
                if (!this.isFullScreenOrignal) {
                    this.mActivity.getWindow().clearFlags(1024);
                }
                if (this.mOldOrientation != 0) {
                    this.mActivity.setRequestedOrientation(this.mOldOrientation);
                }
                if (this.mDisplayView != null && (this.mDisplayView.getParent() instanceof ViewGroup)) {
                    ((ViewGroup) this.mDisplayView.getParent()).removeView(this.mDisplayView);
                }
                this.mDisplayView = null;
                super.onHideCustomView();
            }
        }
    };
    private erw mWebSettings;
    private WebViewClientDispatcher mWebViewClientDispatcher = new WebViewClientDispatcher() {
        private boolean isRedirect = false;

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            AndroidMultiTabWebView.this.mCurrentUrl = str;
            this.isRedirect = true;
            super.onPageStarted(webView, str, bitmap);
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (super.shouldOverrideUrlLoading(webView, str)) {
                return true;
            }
            if (AndroidMultiTabWebView.this.mProgressBar != null) {
                AndroidMultiTabWebView.this.mProgressBar.setVisibility(0);
            }
            if (AndroidMultiTabWebView.this.mWebViewProgressDialog != null) {
                AndroidMultiTabWebView.this.mWebViewProgressDialog;
            }
            if (!AndroidMultiTabWebView.this.mSupportMultiTab) {
                return false;
            }
            if (AndroidMultiTabWebView.this.mMultiTabHandle != null && !AndroidMultiTabWebView.this.mMultiTabHandle.a()) {
                return false;
            }
            if (this.isRedirect || AndroidMultiTabWebView.this.mCurrentUrl == null || (AndroidMultiTabWebView.this.mCurrentUrl.equals(str) && !str.startsWith("file://"))) {
                AndroidMultiTabWebView.this.loadUrl(str, false);
            } else {
                AndroidMultiTabWebView.this.loadUrl(str, true);
            }
            return true;
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (str.equals(AndroidMultiTabWebView.this.mCurrentUrl)) {
                this.isRedirect = false;
            }
        }
    };
    private FrameLayout mWebViewContainer;
    private Stack<SafeWebView> mWebViewGoForwardStack;
    private Stack<SafeWebView> mWebViewHistoryStack;
    /* access modifiers changed from: private */
    public esa mWebViewProgressDialog;

    public interface a {
        boolean a();
    }

    public void setMultiTabHandle(a aVar) {
        this.mMultiTabHandle = aVar;
    }

    public AndroidMultiTabWebView(Context context, AttributeSet attributeSet, int i, MultiTabWebView multiTabWebView) {
        super(context, attributeSet, i);
        this.mFather = multiTabWebView;
        init(context);
    }

    private void init(Context context) {
        this.mWebViewContainer = new FrameLayout(context);
        this.mContainerParams = new LayoutParams(-1, -1);
        this.mCurrentWebView = new SafeWebView(context);
        initializeWebView(this.mCurrentWebView);
        setWebSettings(this.mCurrentWebView, this.mWebSettings);
        this.mWebViewHistoryStack = new Stack<>();
        this.mWebViewHistoryStack.push(this.mCurrentWebView);
        this.mWebViewGoForwardStack = new Stack<>();
        this.mWebViewContainer.addView(this.mCurrentWebView, this.mContainerParams);
        this.mProgressBar = new ProgressBar(context, null, 16842872);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, 4);
        layoutParams.addRule(10);
        addView(this.mProgressBar, layoutParams);
        this.mProgressBar.setId(R.id.webview_progressbar_id);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams2.addRule(3, R.id.webview_progressbar_id);
        addView(this.mWebViewContainer, layoutParams2);
        this.mWebViewProgressDialog = null;
    }

    public void setSupportMultiTab(boolean z) {
        this.mSupportMultiTab = z;
    }

    public boolean isSupportMultiTab() {
        return this.mSupportMultiTab;
    }

    public void setUICreator(erz erz) {
        if (this.mUICreator != erz) {
            this.mProgressBar = erz.a();
            if (this.mProgressBar != null) {
                removeAllViews();
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, 4);
                layoutParams.addRule(10);
                addView(this.mProgressBar, layoutParams);
                this.mProgressBar.setId(R.id.webview_progressbar_id);
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
                layoutParams2.addRule(3, R.id.webview_progressbar_id);
                addView(this.mWebViewContainer, layoutParams2);
            }
            this.mWebViewProgressDialog = null;
            this.mUICreator = erz;
        }
    }

    public void loadUrl(@NonNull String str) {
        loadUrl(str, false);
    }

    public void loadUrl(@NonNull String str, boolean z) {
        if (z) {
            this.mSupportMultiTab = true;
            this.mCurrentWebView = new SafeWebView(getContext());
            initializeWebView(this.mCurrentWebView);
            setWebSettings(this.mCurrentWebView, this.mWebSettings);
            this.mWebViewHistoryStack.add(this.mCurrentWebView);
            this.mWebViewContainer.removeAllViews();
            this.mWebViewContainer.addView(this.mCurrentWebView, this.mContainerParams);
            while (!this.mWebViewGoForwardStack.isEmpty()) {
                destory(this.mWebViewGoForwardStack.pop());
            }
        }
        this.mCurrentWebView.loadUrl(str);
    }

    public void loadData(String str, String str2, String str3) {
        this.mCurrentWebView.loadData(str, str2, str3);
    }

    public void addJavaScriptInterface(Object obj, String str) {
        if (obj != null && str != null) {
            this.mJavaScriptInterfaces.put(obj, str);
            for (int size = this.mWebViewHistoryStack.size() - 1; size >= 0; size--) {
                ((SafeWebView) this.mWebViewHistoryStack.get(size)).addJavascriptInterface(obj, str);
            }
            for (int size2 = this.mWebViewGoForwardStack.size() - 1; size2 >= 0; size2--) {
                ((SafeWebView) this.mWebViewGoForwardStack.get(size2)).addJavascriptInterface(obj, str);
            }
        }
    }

    public void stopLoading() {
        this.mCurrentWebView.stopLoading();
        if (this.mProgressBar != null) {
            this.mProgressBar.setVisibility(8);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0087  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void goBackOrForward(int r5) {
        /*
            r4 = this;
            boolean r0 = r4.mSupportMultiTab
            if (r0 != 0) goto L_0x0017
            com.autonavi.widget.webview.android.SafeWebView r0 = r4.mCurrentWebView
            r0.goBackOrForward(r5)
            com.autonavi.widget.webview.android.WebChromeClientDispather r5 = r4.mWebChromeClientDispather
            com.autonavi.widget.webview.android.SafeWebView r0 = r4.mCurrentWebView
            com.autonavi.widget.webview.android.SafeWebView r1 = r4.mCurrentWebView
            java.lang.String r1 = r1.getTitle()
            r5.onReceivedTitle(r0, r1)
            return
        L_0x0017:
            r0 = 0
            r1 = 1
            if (r5 <= 0) goto L_0x004e
            java.util.Stack<com.autonavi.widget.webview.android.SafeWebView> r2 = r4.mWebViewGoForwardStack
            int r2 = r2.size()
            if (r2 < r5) goto L_0x0084
        L_0x0023:
            if (r0 >= r5) goto L_0x0035
            java.util.Stack<com.autonavi.widget.webview.android.SafeWebView> r2 = r4.mWebViewGoForwardStack
            java.lang.Object r2 = r2.pop()
            com.autonavi.widget.webview.android.SafeWebView r2 = (com.autonavi.widget.webview.android.SafeWebView) r2
            java.util.Stack<com.autonavi.widget.webview.android.SafeWebView> r3 = r4.mWebViewHistoryStack
            r3.push(r2)
            int r0 = r0 + 1
            goto L_0x0023
        L_0x0035:
            android.widget.FrameLayout r5 = r4.mWebViewContainer
            r5.removeAllViews()
            java.util.Stack<com.autonavi.widget.webview.android.SafeWebView> r5 = r4.mWebViewHistoryStack
            java.lang.Object r5 = r5.peek()
            com.autonavi.widget.webview.android.SafeWebView r5 = (com.autonavi.widget.webview.android.SafeWebView) r5
            r4.mCurrentWebView = r5
            android.widget.FrameLayout r5 = r4.mWebViewContainer
            com.autonavi.widget.webview.android.SafeWebView r0 = r4.mCurrentWebView
            android.widget.FrameLayout$LayoutParams r2 = r4.mContainerParams
            r5.addView(r0, r2)
            goto L_0x0085
        L_0x004e:
            if (r5 >= 0) goto L_0x0084
            int r5 = -r5
            java.util.Stack<com.autonavi.widget.webview.android.SafeWebView> r2 = r4.mWebViewHistoryStack
            int r2 = r2.size()
            if (r2 <= r5) goto L_0x0084
        L_0x0059:
            if (r0 >= r5) goto L_0x006b
            java.util.Stack<com.autonavi.widget.webview.android.SafeWebView> r2 = r4.mWebViewHistoryStack
            java.lang.Object r2 = r2.pop()
            com.autonavi.widget.webview.android.SafeWebView r2 = (com.autonavi.widget.webview.android.SafeWebView) r2
            java.util.Stack<com.autonavi.widget.webview.android.SafeWebView> r3 = r4.mWebViewGoForwardStack
            r3.push(r2)
            int r0 = r0 + 1
            goto L_0x0059
        L_0x006b:
            android.widget.FrameLayout r5 = r4.mWebViewContainer
            r5.removeAllViews()
            java.util.Stack<com.autonavi.widget.webview.android.SafeWebView> r5 = r4.mWebViewHistoryStack
            java.lang.Object r5 = r5.peek()
            com.autonavi.widget.webview.android.SafeWebView r5 = (com.autonavi.widget.webview.android.SafeWebView) r5
            r4.mCurrentWebView = r5
            android.widget.FrameLayout r5 = r4.mWebViewContainer
            com.autonavi.widget.webview.android.SafeWebView r0 = r4.mCurrentWebView
            android.widget.FrameLayout$LayoutParams r2 = r4.mContainerParams
            r5.addView(r0, r2)
            goto L_0x0085
        L_0x0084:
            r1 = 0
        L_0x0085:
            if (r1 == 0) goto L_0x009c
            com.autonavi.widget.webview.android.WebChromeClientDispather r5 = r4.mWebChromeClientDispather
            com.autonavi.widget.webview.android.SafeWebView r0 = r4.mCurrentWebView
            com.autonavi.widget.webview.android.SafeWebView r1 = r4.mCurrentWebView
            java.lang.String r1 = r1.getTitle()
            r5.onReceivedTitle(r0, r1)
            com.autonavi.widget.webview.android.SafeWebView r5 = r4.mCurrentWebView
            java.lang.String r5 = r5.getUrl()
            r4.mCurrentUrl = r5
        L_0x009c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.widget.webview.AndroidMultiTabWebView.goBackOrForward(int):void");
    }

    public boolean canGoBack() {
        if (!this.mCurrentWebView.canGoBack() && this.mWebViewHistoryStack.size() <= 1) {
            return false;
        }
        return true;
    }

    public void goBack() {
        boolean z = true;
        if (this.mCurrentWebView.canGoBack()) {
            this.mCurrentWebView.goBack();
        } else if (this.mWebViewHistoryStack.size() > 1) {
            SafeWebView pop = this.mWebViewHistoryStack.pop();
            this.mCurrentWebView = this.mWebViewHistoryStack.peek();
            this.mWebViewContainer.removeView(pop);
            this.mWebViewContainer.addView(this.mCurrentWebView, this.mContainerParams);
            this.mWebViewGoForwardStack.push(pop);
        } else {
            z = false;
        }
        if (z) {
            this.mWebChromeClientDispather.onReceivedTitle(this.mCurrentWebView, this.mCurrentWebView.getTitle());
            this.mCurrentUrl = this.mCurrentWebView.getUrl();
        }
    }

    public boolean canGoForward() {
        if (!this.mCurrentWebView.canGoForward() && this.mWebViewGoForwardStack.size() <= 0) {
            return false;
        }
        return true;
    }

    public void goForward() {
        boolean z = true;
        if (this.mCurrentWebView.canGoForward()) {
            this.mCurrentWebView.goForward();
        } else if (this.mWebViewGoForwardStack.size() > 0) {
            this.mWebViewContainer.removeView(this.mCurrentWebView);
            this.mCurrentWebView = this.mWebViewGoForwardStack.pop();
            this.mWebViewContainer.addView(this.mCurrentWebView, this.mContainerParams);
            this.mWebViewHistoryStack.push(this.mCurrentWebView);
        } else {
            z = false;
        }
        if (z) {
            this.mWebChromeClientDispather.onReceivedTitle(this.mCurrentWebView, this.mCurrentWebView.getTitle());
            this.mCurrentUrl = this.mCurrentWebView.getUrl();
        }
    }

    public String getUrl() {
        return this.mCurrentWebView.getUrl();
    }

    public void loadJs(@NonNull String str) {
        if (VERSION.SDK_INT >= 19) {
            this.mCurrentWebView.evaluateJavascript(str, null);
        } else {
            this.mCurrentWebView.loadUrl(str);
        }
    }

    @NonNull
    public WebView getCurrentWebView() {
        return this.mCurrentWebView;
    }

    public final void setWebSettings(@NonNull erw erw) {
        this.mWebSettings = erw;
        this.mWebSettings.e = this.mFather;
        for (int size = this.mWebViewHistoryStack.size() - 1; size >= 0; size--) {
            setWebSettings((SafeWebView) this.mWebViewHistoryStack.get(size), this.mWebSettings);
        }
        for (int size2 = this.mWebViewGoForwardStack.size() - 1; size2 >= 0; size2--) {
            setWebSettings((SafeWebView) this.mWebViewGoForwardStack.get(size2), this.mWebSettings);
        }
    }

    public erw getWebSettings() {
        if (this.mWebSettings == null) {
            this.mWebSettings = new erw();
            this.mWebSettings.e = this.mFather;
        }
        return this.mWebSettings;
    }

    public void clearHistory() {
        while (!this.mWebViewGoForwardStack.isEmpty()) {
            destory(this.mWebViewGoForwardStack.pop());
        }
        this.mCurrentWebView = this.mWebViewHistoryStack.pop();
        this.mWebViewContainer.removeAllViews();
        while (!this.mWebViewHistoryStack.isEmpty()) {
            destory(this.mWebViewHistoryStack.pop());
        }
        this.mWebViewContainer.addView(this.mCurrentWebView, this.mContainerParams);
        this.mWebViewHistoryStack.push(this.mCurrentWebView);
    }

    public final void addWebViewClient(@NonNull WebViewClient webViewClient) {
        this.mWebViewClientDispatcher.addWebViewClient(webViewClient);
    }

    public final void removeWebViewCline(@NonNull WebViewClient webViewClient) {
        this.mWebViewClientDispatcher.removeWebViewClient(webViewClient);
    }

    public final void addWebChromeClient(@NonNull WebChromeClient webChromeClient) {
        this.mWebChromeClientDispather.addWebChromeClient(webChromeClient);
    }

    public final void removeWebChromeClient(@NonNull WebChromeClient webChromeClient) {
        this.mWebChromeClientDispather.removeWebChromeClient(webChromeClient);
    }

    public final void addDownloadListener(@NonNull DownloadListener downloadListener) {
        if (!this.mDownloadListeners.contains(downloadListener)) {
            this.mDownloadListeners.add(downloadListener);
        }
    }

    public final void removeDownloadListener(@NonNull DownloadListener downloadListener) {
        if (this.mDownloadListeners.contains(downloadListener)) {
            this.mDownloadListeners.remove(downloadListener);
        }
    }

    public WebViewClient getWebViewClient() {
        return this.mWebViewClientDispatcher;
    }

    public WebChromeClient getWebChromeClient() {
        return this.mWebChromeClientDispather;
    }

    private void destory(SafeWebView safeWebView) {
        if (safeWebView != null) {
            try {
                safeWebView.setWebViewClient(null);
                safeWebView.setWebChromeClient(null);
                safeWebView.setDownloadListener(null);
                safeWebView.destroyDrawingCache();
                safeWebView.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void destroy() {
        clearHistory();
        destory(this.mCurrentWebView);
        this.mJavaScriptInterfaces.clear();
        this.mWebChromeClientDispather.close();
        this.mWebViewClientDispatcher.close();
        this.mDownloadListeners.clear();
        if (this.mWebSettings != null) {
            this.mWebSettings.e = null;
            this.mWebSettings = null;
        }
    }

    public void reload() {
        this.mCurrentWebView.reload();
    }

    public void disableAccessibility() {
        if (VERSION.SDK_INT == 17) {
            Context context = getContext();
            if (context != null) {
                try {
                    AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
                    if (accessibilityManager.isEnabled()) {
                        Method declaredMethod = accessibilityManager.getClass().getDeclaredMethod("setState", new Class[]{Integer.TYPE});
                        declaredMethod.setAccessible(true);
                        declaredMethod.invoke(accessibilityManager, new Object[]{Integer.valueOf(0)});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setWebViewProxy(WebView webView) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.getType() != 1) {
            String defaultHost = Proxy.getDefaultHost();
            int defaultPort = Proxy.getDefaultPort();
            if (defaultHost != null && defaultHost.equals(NetInfoHelper.CMWAP_PROXY_HOST)) {
                webView.setHttpAuthUsernamePassword("10.0.0.172:".concat(String.valueOf(defaultPort)), "", "", "");
            }
        }
    }

    private void initializeWebView(WebView webView) {
        if (webView != null) {
            setWebViewProxy(webView);
            if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 18) {
                webView.setLayerType(1, null);
            }
            webView.setDrawingCacheEnabled(false);
            webView.setScrollBarStyle(0);
            for (Entry next : this.mJavaScriptInterfaces.entrySet()) {
                webView.addJavascriptInterface(next.getKey(), (String) next.getValue());
            }
            webView.setWebViewClient(this.mWebViewClientDispatcher);
            webView.setWebChromeClient(this.mWebChromeClientDispather);
            webView.setDownloadListener(new DownloadListener() {
                public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                    for (int size = AndroidMultiTabWebView.this.mDownloadListeners.size() - 1; size >= 0; size--) {
                        ((DownloadListener) AndroidMultiTabWebView.this.mDownloadListeners.get(size)).onDownloadStart(str, str2, str3, str4, j);
                    }
                }
            });
        }
    }

    private void setWebSettings(SafeWebView safeWebView, erw erw) {
        if (erw == null) {
            erw = new erw();
        }
        WebSettings settings = safeWebView.getSettings();
        try {
            defaultSetting(settings);
            settings.setJavaScriptEnabled(erw.c);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            disableAccessibility();
            if (!TextUtils.isEmpty(erw.d)) {
                StringBuilder sb = new StringBuilder();
                String userAgentString = settings.getUserAgentString();
                if (!TextUtils.isEmpty(userAgentString)) {
                    sb.append(userAgentString);
                    sb.append(Token.SEPARATOR);
                }
                sb.append(erw.d);
                settings.setUserAgentString(sb.toString());
            }
            if (erw.a) {
                settings.setAppCacheEnabled(true);
                settings.setAppCacheMaxSize(20971520);
                settings.setCacheMode(-1);
            } else {
                settings.setAppCacheEnabled(false);
                settings.setCacheMode(2);
            }
            if (erw.b) {
                settings.setBuiltInZoomControls(true);
                settings.setDisplayZoomControls(false);
                settings.setSupportZoom(true);
                return;
            }
            settings.setBuiltInZoomControls(false);
            settings.setSupportZoom(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void defaultSetting(WebSettings webSettings) {
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDatabaseEnabled(true);
        String path = getContext().getDir("databases", 0).getPath();
        webSettings.setDatabasePath(path);
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath(path);
        webSettings.setTextZoom(100);
        if (VERSION.SDK_INT >= 16) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
    }
}
