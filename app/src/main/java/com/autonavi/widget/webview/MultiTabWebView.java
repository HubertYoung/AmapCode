package com.autonavi.widget.webview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.alipay.mobile.common.transportext.biz.util.NetInfoHelper;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.widget.R;
import com.autonavi.widget.webview.inner.SafeWebView;
import com.autonavi.widget.webview.inner.SafeWebView.b;
import com.uc.webview.export.DownloadListener;
import com.uc.webview.export.WebChromeClient;
import com.uc.webview.export.WebChromeClient.CustomViewCallback;
import com.uc.webview.export.WebSettings;
import com.uc.webview.export.WebView;
import com.uc.webview.export.WebViewClient;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;

public class MultiTabWebView extends RelativeLayout {
    protected final String CMWAP;
    private AndroidMultiTabWebView mAndroidMultiTabWebView;
    private LayoutParams mContainerParams;
    /* access modifiers changed from: private */
    public String mCurrentUrl;
    private SafeWebView mCurrentWebView;
    /* access modifiers changed from: private */
    public List<DownloadListener> mDownloadListeners;
    private HashMap<Object, String> mJavaScriptInterfaces;
    /* access modifiers changed from: private */
    public a mMultiTabHandle;
    /* access modifiers changed from: private */
    public ProgressBar mProgressBar;
    /* access modifiers changed from: private */
    public boolean mSupportMultiTab;
    private erz mUICreator;
    private erx mWebChromeClientDispather;
    private erw mWebSettings;
    private ery mWebViewClientDispatcher;
    private FrameLayout mWebViewContainer;
    private Stack<SafeWebView> mWebViewGoForwardStack;
    private Stack<SafeWebView> mWebViewHistoryStack;
    /* access modifiers changed from: private */
    public esa mWebViewProgressDialog;

    public interface a {
        boolean a(String str);
    }

    public void setMultiTabHandle(a aVar) {
        this.mMultiTabHandle = aVar;
    }

    public MultiTabWebView(Context context) {
        this(context, null, 0);
    }

    public MultiTabWebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MultiTabWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mJavaScriptInterfaces = new HashMap<>();
        this.mSupportMultiTab = false;
        this.mWebChromeClientDispather = new erx() {
            private Activity c = ((Activity) MultiTabWebView.this.getContext());
            @Nullable
            private View d;
            private int e;
            private boolean f = false;

            public final void onProgressChanged(WebView webView, int i) {
                if (MultiTabWebView.this.mWebViewProgressDialog != null) {
                    MultiTabWebView.this.mWebViewProgressDialog;
                    if (i == 100) {
                        MultiTabWebView.this.mWebViewProgressDialog;
                    }
                }
                if (MultiTabWebView.this.mProgressBar != null) {
                    MultiTabWebView.this.mProgressBar.setProgress(i);
                    if (i == 100) {
                        MultiTabWebView.this.mProgressBar.setVisibility(8);
                    }
                }
            }

            public final void onShowCustomView(View view, CustomViewCallback customViewCallback) {
                if (this.c == null) {
                    this.c = (Activity) MultiTabWebView.this.getContext();
                }
                if (this.c != null) {
                    if (this.d != null) {
                        customViewCallback.onCustomViewHidden();
                    }
                    this.e = this.c.getRequestedOrientation();
                    boolean z = false;
                    if (this.e != 0) {
                        this.c.setRequestedOrientation(0);
                    }
                    if ((this.c.getWindow().getAttributes().flags | -1025) == 0) {
                        z = true;
                    }
                    this.f = z;
                    if (!this.f) {
                        this.c.getWindow().addFlags(1024);
                    }
                    this.d = view;
                    this.c.addContentView(this.d, new ViewGroup.LayoutParams(-1, -1));
                    super.onShowCustomView(view, customViewCallback);
                }
            }

            public final void onHideCustomView() {
                if (this.c == null) {
                    this.c = (Activity) MultiTabWebView.this.getContext();
                }
                if (this.c != null) {
                    if (!this.f) {
                        this.c.getWindow().clearFlags(1024);
                    }
                    if (this.e != 0) {
                        this.c.setRequestedOrientation(this.e);
                    }
                    if (this.d != null && (this.d.getParent() instanceof ViewGroup)) {
                        ((ViewGroup) this.d.getParent()).removeView(this.d);
                    }
                    this.d = null;
                    super.onHideCustomView();
                }
            }
        };
        this.mWebViewClientDispatcher = new ery() {
            private boolean c = false;

            public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                MultiTabWebView.this.mCurrentUrl = str;
                this.c = true;
                super.onPageStarted(webView, str, bitmap);
            }

            public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (super.shouldOverrideUrlLoading(webView, str)) {
                    return true;
                }
                if (MultiTabWebView.this.mProgressBar != null) {
                    MultiTabWebView.this.mProgressBar.setVisibility(0);
                }
                if (MultiTabWebView.this.mWebViewProgressDialog != null) {
                    MultiTabWebView.this.mWebViewProgressDialog;
                }
                if (!MultiTabWebView.this.mSupportMultiTab) {
                    return false;
                }
                if (MultiTabWebView.this.mMultiTabHandle != null && !MultiTabWebView.this.mMultiTabHandle.a(str)) {
                    return false;
                }
                if (this.c || MultiTabWebView.this.mCurrentUrl == null || (MultiTabWebView.this.mCurrentUrl.equals(str) && !str.startsWith("file://"))) {
                    MultiTabWebView.this.loadUrl(str, false);
                } else {
                    MultiTabWebView.this.loadUrl(str, true);
                }
                return true;
            }

            public final void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                if (str.equals(MultiTabWebView.this.mCurrentUrl)) {
                    this.c = false;
                }
            }
        };
        this.mDownloadListeners = new ArrayList();
        this.CMWAP = NetInfoHelper.CMWAP_PROXY_HOST;
        init(context, false);
    }

    public MultiTabWebView(Context context, boolean z) {
        super(context, null, 0);
        this.mJavaScriptInterfaces = new HashMap<>();
        this.mSupportMultiTab = false;
        this.mWebChromeClientDispather = new erx() {
            private Activity c = ((Activity) MultiTabWebView.this.getContext());
            @Nullable
            private View d;
            private int e;
            private boolean f = false;

            public final void onProgressChanged(WebView webView, int i) {
                if (MultiTabWebView.this.mWebViewProgressDialog != null) {
                    MultiTabWebView.this.mWebViewProgressDialog;
                    if (i == 100) {
                        MultiTabWebView.this.mWebViewProgressDialog;
                    }
                }
                if (MultiTabWebView.this.mProgressBar != null) {
                    MultiTabWebView.this.mProgressBar.setProgress(i);
                    if (i == 100) {
                        MultiTabWebView.this.mProgressBar.setVisibility(8);
                    }
                }
            }

            public final void onShowCustomView(View view, CustomViewCallback customViewCallback) {
                if (this.c == null) {
                    this.c = (Activity) MultiTabWebView.this.getContext();
                }
                if (this.c != null) {
                    if (this.d != null) {
                        customViewCallback.onCustomViewHidden();
                    }
                    this.e = this.c.getRequestedOrientation();
                    boolean z = false;
                    if (this.e != 0) {
                        this.c.setRequestedOrientation(0);
                    }
                    if ((this.c.getWindow().getAttributes().flags | -1025) == 0) {
                        z = true;
                    }
                    this.f = z;
                    if (!this.f) {
                        this.c.getWindow().addFlags(1024);
                    }
                    this.d = view;
                    this.c.addContentView(this.d, new ViewGroup.LayoutParams(-1, -1));
                    super.onShowCustomView(view, customViewCallback);
                }
            }

            public final void onHideCustomView() {
                if (this.c == null) {
                    this.c = (Activity) MultiTabWebView.this.getContext();
                }
                if (this.c != null) {
                    if (!this.f) {
                        this.c.getWindow().clearFlags(1024);
                    }
                    if (this.e != 0) {
                        this.c.setRequestedOrientation(this.e);
                    }
                    if (this.d != null && (this.d.getParent() instanceof ViewGroup)) {
                        ((ViewGroup) this.d.getParent()).removeView(this.d);
                    }
                    this.d = null;
                    super.onHideCustomView();
                }
            }
        };
        this.mWebViewClientDispatcher = new ery() {
            private boolean c = false;

            public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                MultiTabWebView.this.mCurrentUrl = str;
                this.c = true;
                super.onPageStarted(webView, str, bitmap);
            }

            public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (super.shouldOverrideUrlLoading(webView, str)) {
                    return true;
                }
                if (MultiTabWebView.this.mProgressBar != null) {
                    MultiTabWebView.this.mProgressBar.setVisibility(0);
                }
                if (MultiTabWebView.this.mWebViewProgressDialog != null) {
                    MultiTabWebView.this.mWebViewProgressDialog;
                }
                if (!MultiTabWebView.this.mSupportMultiTab) {
                    return false;
                }
                if (MultiTabWebView.this.mMultiTabHandle != null && !MultiTabWebView.this.mMultiTabHandle.a(str)) {
                    return false;
                }
                if (this.c || MultiTabWebView.this.mCurrentUrl == null || (MultiTabWebView.this.mCurrentUrl.equals(str) && !str.startsWith("file://"))) {
                    MultiTabWebView.this.loadUrl(str, false);
                } else {
                    MultiTabWebView.this.loadUrl(str, true);
                }
                return true;
            }

            public final void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                if (str.equals(MultiTabWebView.this.mCurrentUrl)) {
                    this.c = false;
                }
            }
        };
        this.mDownloadListeners = new ArrayList();
        this.CMWAP = NetInfoHelper.CMWAP_PROXY_HOST;
        init(context, z);
    }

    private void init(Context context, boolean z) {
        if (z) {
            this.mAndroidMultiTabWebView = new AndroidMultiTabWebView(context, null, 0, this);
            addView(this.mAndroidMultiTabWebView, new RelativeLayout.LayoutParams(-1, -1));
            return;
        }
        erv.a();
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
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.setSupportMultiTab(z);
        } else {
            this.mSupportMultiTab = z;
        }
    }

    public boolean isSupportMultiTab() {
        if (this.mAndroidMultiTabWebView != null) {
            return this.mAndroidMultiTabWebView.isSupportMultiTab();
        }
        return this.mSupportMultiTab;
    }

    public void setUICreator(erz erz) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.setUICreator(erz);
            return;
        }
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
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.loadUrl(str);
        } else {
            loadUrl(str, false);
        }
    }

    public void loadUrl(@NonNull String str, boolean z) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.loadUrl(str, z);
            return;
        }
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
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.loadData(str, str2, str3);
        } else {
            this.mCurrentWebView.loadData(str, str2, str3);
        }
    }

    public void addJavaScriptInterface(Object obj, String str) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.addJavaScriptInterface(obj, str);
            return;
        }
        if (!(obj == null || str == null)) {
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
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.stopLoading();
            return;
        }
        this.mCurrentWebView.stopLoading();
        if (this.mProgressBar != null) {
            this.mProgressBar.setVisibility(8);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0091  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void goBackOrForward(int r5) {
        /*
            r4 = this;
            com.autonavi.widget.webview.AndroidMultiTabWebView r0 = r4.mAndroidMultiTabWebView
            if (r0 == 0) goto L_0x000a
            com.autonavi.widget.webview.AndroidMultiTabWebView r0 = r4.mAndroidMultiTabWebView
            r0.goBackOrForward(r5)
            return
        L_0x000a:
            boolean r0 = r4.mSupportMultiTab
            if (r0 != 0) goto L_0x0021
            com.autonavi.widget.webview.inner.SafeWebView r0 = r4.mCurrentWebView
            r0.goBackOrForward(r5)
            erx r5 = r4.mWebChromeClientDispather
            com.autonavi.widget.webview.inner.SafeWebView r0 = r4.mCurrentWebView
            com.autonavi.widget.webview.inner.SafeWebView r1 = r4.mCurrentWebView
            java.lang.String r1 = r1.getTitle()
            r5.onReceivedTitle(r0, r1)
            return
        L_0x0021:
            r0 = 0
            r1 = 1
            if (r5 <= 0) goto L_0x0058
            java.util.Stack<com.autonavi.widget.webview.inner.SafeWebView> r2 = r4.mWebViewGoForwardStack
            int r2 = r2.size()
            if (r2 < r5) goto L_0x008e
        L_0x002d:
            if (r0 >= r5) goto L_0x003f
            java.util.Stack<com.autonavi.widget.webview.inner.SafeWebView> r2 = r4.mWebViewGoForwardStack
            java.lang.Object r2 = r2.pop()
            com.autonavi.widget.webview.inner.SafeWebView r2 = (com.autonavi.widget.webview.inner.SafeWebView) r2
            java.util.Stack<com.autonavi.widget.webview.inner.SafeWebView> r3 = r4.mWebViewHistoryStack
            r3.push(r2)
            int r0 = r0 + 1
            goto L_0x002d
        L_0x003f:
            android.widget.FrameLayout r5 = r4.mWebViewContainer
            r5.removeAllViews()
            java.util.Stack<com.autonavi.widget.webview.inner.SafeWebView> r5 = r4.mWebViewHistoryStack
            java.lang.Object r5 = r5.peek()
            com.autonavi.widget.webview.inner.SafeWebView r5 = (com.autonavi.widget.webview.inner.SafeWebView) r5
            r4.mCurrentWebView = r5
            android.widget.FrameLayout r5 = r4.mWebViewContainer
            com.autonavi.widget.webview.inner.SafeWebView r0 = r4.mCurrentWebView
            android.widget.FrameLayout$LayoutParams r2 = r4.mContainerParams
            r5.addView(r0, r2)
            goto L_0x008f
        L_0x0058:
            if (r5 >= 0) goto L_0x008e
            int r5 = -r5
            java.util.Stack<com.autonavi.widget.webview.inner.SafeWebView> r2 = r4.mWebViewHistoryStack
            int r2 = r2.size()
            if (r2 <= r5) goto L_0x008e
        L_0x0063:
            if (r0 >= r5) goto L_0x0075
            java.util.Stack<com.autonavi.widget.webview.inner.SafeWebView> r2 = r4.mWebViewHistoryStack
            java.lang.Object r2 = r2.pop()
            com.autonavi.widget.webview.inner.SafeWebView r2 = (com.autonavi.widget.webview.inner.SafeWebView) r2
            java.util.Stack<com.autonavi.widget.webview.inner.SafeWebView> r3 = r4.mWebViewGoForwardStack
            r3.push(r2)
            int r0 = r0 + 1
            goto L_0x0063
        L_0x0075:
            android.widget.FrameLayout r5 = r4.mWebViewContainer
            r5.removeAllViews()
            java.util.Stack<com.autonavi.widget.webview.inner.SafeWebView> r5 = r4.mWebViewHistoryStack
            java.lang.Object r5 = r5.peek()
            com.autonavi.widget.webview.inner.SafeWebView r5 = (com.autonavi.widget.webview.inner.SafeWebView) r5
            r4.mCurrentWebView = r5
            android.widget.FrameLayout r5 = r4.mWebViewContainer
            com.autonavi.widget.webview.inner.SafeWebView r0 = r4.mCurrentWebView
            android.widget.FrameLayout$LayoutParams r2 = r4.mContainerParams
            r5.addView(r0, r2)
            goto L_0x008f
        L_0x008e:
            r1 = 0
        L_0x008f:
            if (r1 == 0) goto L_0x00a6
            erx r5 = r4.mWebChromeClientDispather
            com.autonavi.widget.webview.inner.SafeWebView r0 = r4.mCurrentWebView
            com.autonavi.widget.webview.inner.SafeWebView r1 = r4.mCurrentWebView
            java.lang.String r1 = r1.getTitle()
            r5.onReceivedTitle(r0, r1)
            com.autonavi.widget.webview.inner.SafeWebView r5 = r4.mCurrentWebView
            java.lang.String r5 = r5.getUrl()
            r4.mCurrentUrl = r5
        L_0x00a6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.widget.webview.MultiTabWebView.goBackOrForward(int):void");
    }

    public boolean canGoBack() {
        if (this.mAndroidMultiTabWebView != null) {
            return this.mAndroidMultiTabWebView.canGoBack();
        }
        if (!this.mCurrentWebView.canGoBack() && this.mWebViewHistoryStack.size() <= 1) {
            return false;
        }
        return true;
    }

    public void goBack() {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.goBack();
            return;
        }
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
        if (this.mAndroidMultiTabWebView != null) {
            return this.mAndroidMultiTabWebView.canGoForward();
        }
        if (!this.mCurrentWebView.canGoForward() && this.mWebViewGoForwardStack.size() <= 0) {
            return false;
        }
        return true;
    }

    public void goForward() {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.goForward();
            return;
        }
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
        if (this.mAndroidMultiTabWebView != null) {
            return this.mAndroidMultiTabWebView.getUrl();
        }
        return this.mCurrentWebView.getUrl();
    }

    public void loadJs(@NonNull String str) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.loadJs(str);
        } else if (VERSION.SDK_INT >= 19) {
            this.mCurrentWebView.evaluateJavascript(str, null);
        } else {
            this.mCurrentWebView.loadUrl(str);
        }
    }

    @NonNull
    public WebView getCurrentWebView() {
        if (this.mAndroidMultiTabWebView != null) {
            return null;
        }
        return this.mCurrentWebView;
    }

    public final void setWebSettings(@NonNull erw erw) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.setWebSettings(erw);
            return;
        }
        this.mWebSettings = erw;
        this.mWebSettings.e = this;
        for (int size = this.mWebViewHistoryStack.size() - 1; size >= 0; size--) {
            setWebSettings((SafeWebView) this.mWebViewHistoryStack.get(size), this.mWebSettings);
        }
        for (int size2 = this.mWebViewGoForwardStack.size() - 1; size2 >= 0; size2--) {
            setWebSettings((SafeWebView) this.mWebViewGoForwardStack.get(size2), this.mWebSettings);
        }
    }

    public erw getWebSettings() {
        if (this.mAndroidMultiTabWebView != null) {
            return this.mAndroidMultiTabWebView.getWebSettings();
        }
        if (this.mWebSettings == null) {
            this.mWebSettings = new erw();
            this.mWebSettings.e = this;
        }
        return this.mWebSettings;
    }

    public void clearHistory() {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.clearHistory();
            return;
        }
        while (!this.mWebViewGoForwardStack.isEmpty()) {
            SafeWebView pop = this.mWebViewGoForwardStack.pop();
            if (pop != this.mCurrentWebView) {
                destory(pop);
            }
        }
        this.mCurrentWebView = this.mWebViewHistoryStack.pop();
        this.mWebViewContainer.removeAllViews();
        while (!this.mWebViewHistoryStack.isEmpty()) {
            SafeWebView pop2 = this.mWebViewHistoryStack.pop();
            if (pop2 != this.mCurrentWebView) {
                destory(pop2);
            }
        }
        try {
            this.mWebViewContainer.addView(this.mCurrentWebView, this.mContainerParams);
            this.mWebViewHistoryStack.push(this.mCurrentWebView);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public final void addWebViewClient(@NonNull b bVar) {
        if (this.mAndroidMultiTabWebView == null) {
            this.mWebViewClientDispatcher.a(bVar);
        }
    }

    public final void removeWebViewCline(@NonNull b bVar) {
        if (this.mAndroidMultiTabWebView == null) {
            this.mWebViewClientDispatcher.b(bVar);
        }
    }

    public final void addWebChromeClient(@NonNull WebChromeClient webChromeClient) {
        if (this.mAndroidMultiTabWebView == null) {
            this.mWebChromeClientDispather.a(webChromeClient);
        }
    }

    public final void removeWebChromeClient(@NonNull WebChromeClient webChromeClient) {
        if (this.mAndroidMultiTabWebView == null) {
            this.mWebChromeClientDispather.b(webChromeClient);
        }
    }

    public final void addDownloadListener(@NonNull DownloadListener downloadListener) {
        if (this.mAndroidMultiTabWebView == null && !this.mDownloadListeners.contains(downloadListener)) {
            this.mDownloadListeners.add(downloadListener);
        }
    }

    public final void removeDownloadListener(@NonNull DownloadListener downloadListener) {
        if (this.mAndroidMultiTabWebView == null && this.mDownloadListeners.contains(downloadListener)) {
            this.mDownloadListeners.remove(downloadListener);
        }
    }

    public WebViewClient getWebViewClient() {
        if (this.mAndroidMultiTabWebView != null) {
            return null;
        }
        return this.mWebViewClientDispatcher;
    }

    public WebChromeClient getWebChromeClient() {
        if (this.mAndroidMultiTabWebView != null) {
            return null;
        }
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
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.destroy();
            return;
        }
        clearHistory();
        destory(this.mCurrentWebView);
        this.mJavaScriptInterfaces.clear();
        this.mWebChromeClientDispather.b.clear();
        this.mWebViewClientDispatcher.b.clear();
        this.mDownloadListeners.clear();
        if (this.mWebSettings != null) {
            this.mWebSettings.e = null;
            this.mWebSettings = null;
        }
    }

    public void reload() {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.reload();
        } else {
            this.mCurrentWebView.reload();
        }
    }

    /* access modifiers changed from: protected */
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
                public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                    for (int size = MultiTabWebView.this.mDownloadListeners.size() - 1; size >= 0; size--) {
                        ((DownloadListener) MultiTabWebView.this.mDownloadListeners.get(size)).onDownloadStart(str, str2, str3, str4, j);
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

    public final void addAndroidWebViewClient(@NonNull android.webkit.WebViewClient webViewClient) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.addWebViewClient(webViewClient);
        }
    }

    public final void removeAndroidWebViewCline(@NonNull android.webkit.WebViewClient webViewClient) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.removeWebViewCline(webViewClient);
        }
    }

    public final void addAndroidWebChromeClient(@NonNull android.webkit.WebChromeClient webChromeClient) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.addWebChromeClient(webChromeClient);
        }
    }

    public final void removeAndroidWebChromeClient(@NonNull android.webkit.WebChromeClient webChromeClient) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.removeWebChromeClient(webChromeClient);
        }
    }

    public final void addAndroidDownloadListener(@NonNull android.webkit.DownloadListener downloadListener) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.addDownloadListener(downloadListener);
        }
    }

    public final void removeAndroidDownloadListener(@NonNull android.webkit.DownloadListener downloadListener) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.removeDownloadListener(downloadListener);
        }
    }

    public android.webkit.WebViewClient getAndroidWebViewClient() {
        if (this.mAndroidMultiTabWebView != null) {
            return this.mAndroidMultiTabWebView.getWebViewClient();
        }
        return null;
    }

    public android.webkit.WebChromeClient getAndroidWebChromeClient() {
        if (this.mAndroidMultiTabWebView != null) {
            return this.mAndroidMultiTabWebView.getWebChromeClient();
        }
        return null;
    }

    public android.webkit.WebView getAndroidWebView() {
        if (this.mAndroidMultiTabWebView != null) {
            return this.mAndroidMultiTabWebView.getCurrentWebView();
        }
        return null;
    }

    public void setViewLongClickable(boolean z) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.getCurrentWebView().setLongClickable(z);
        } else {
            getCurrentWebView().setLongClickable(z);
        }
    }

    public void setViewOnTouchListener(OnTouchListener onTouchListener) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.getCurrentWebView().setOnTouchListener(onTouchListener);
        } else {
            getCurrentWebView().setOnTouchListener(onTouchListener);
        }
    }

    public void setViewLayerType(int i, Paint paint) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.getCurrentWebView().setLayerType(i, paint);
        } else {
            getCurrentWebView().setLayerType(i, paint);
        }
    }

    public void setViewBackgroundColor(int i) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.getCurrentWebView().setBackgroundColor(i);
        } else {
            getCurrentWebView().setBackgroundColor(i);
        }
    }

    public void onViewResume() {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.getCurrentWebView().onResume();
        } else {
            getCurrentWebView().onResume();
        }
    }

    public void onViewPause() {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.getCurrentWebView().onPause();
        } else {
            getCurrentWebView().onPause();
        }
    }

    public void setUseWideViewPort(boolean z) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.getCurrentWebView().getSettings().setUseWideViewPort(z);
        } else {
            getCurrentWebView().getSettings().setUseWideViewPort(z);
        }
    }

    public void setLoadWithOverviewMode(boolean z) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.getCurrentWebView().getSettings().setLoadWithOverviewMode(z);
        } else {
            getCurrentWebView().getSettings().setLoadWithOverviewMode(z);
        }
    }

    public void setSavePassword(boolean z) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.getCurrentWebView().getSettings().setSavePassword(z);
        } else {
            getCurrentWebView().getSettings().setSavePassword(z);
        }
    }

    public void setJavaScriptEnable(boolean z) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.getWebSettings().b(z);
        } else {
            getWebSettings().b(z);
        }
    }

    public void setSupportZoom(boolean z) {
        if (this.mAndroidMultiTabWebView != null) {
            this.mAndroidMultiTabWebView.getWebSettings().a(z);
        } else {
            getWebSettings().a(z);
        }
    }
}
