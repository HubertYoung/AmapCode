package com.amap.bundle.webview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.webkit.JavascriptInterface;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.alipay.mobile.common.transportext.biz.util.NetInfoHelper;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.KeyValueStorage.WebStorage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.widget.OnWebViewEventListener;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import com.uc.webview.export.DownloadListener;
import com.uc.webview.export.JsResult;
import com.uc.webview.export.WebChromeClient;
import com.uc.webview.export.WebSettings;
import com.uc.webview.export.WebView;
import com.uc.webview.export.WebViewClient;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
public class ExtendedWebView extends AbstractBaseWebView {
    public static final String TAG = "ExtendedWebView";
    /* access modifiers changed from: private */
    public boolean isWebViewAlive = true;
    /* access modifiers changed from: private */
    public JsResult mJsResult;
    private boolean mOpenSafeExFeature = false;
    private String mUrl;
    private WebChromeClient mWebChromeClient = new com.amap.bundle.webview.widget.WebViewEx.a() {
        public final void onReceivedTitle(WebView webView, String str) {
            if (ExtendedWebView.this.mOnWebViewEventListener != null) {
                ExtendedWebView.this.mOnWebViewEventListener.onReceivedTitle(webView, str);
            }
        }

        public final void onProgressChanged(WebView webView, int i) {
            if (ExtendedWebView.this.mShowTopProress) {
                ExtendedWebView.this.mTopProgressBar.setProgress(i);
            }
            if (i == 100) {
                ExtendedWebView.this.mProgressBar.dismiss();
                ExtendedWebView.this.mTopProgressBar.setVisibility(8);
            }
        }

        public final boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
            final bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null) {
                return true;
            }
            com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(pageContext.getActivity());
            aVar.a((CharSequence) str2);
            aVar.a(true);
            aVar.a(17039370, (defpackage.ern.a) new defpackage.ern.a() {
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
            com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(pageContext.getActivity());
            aVar.a((CharSequence) str2);
            aVar.a(false);
            aVar.a(17039370, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    jsResult.confirm();
                    ExtendedWebView.this.mJsResult = null;
                    pageContext.dismissViewLayer(alertView);
                }
            });
            aVar.b(17039360, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    jsResult.cancel();
                    ExtendedWebView.this.mJsResult = null;
                    pageContext.dismissViewLayer(alertView);
                }
            });
            ExtendedWebView.this.mJsResult = jsResult;
            aVar.a(true);
            AlertView a2 = aVar.a();
            pageContext.showViewLayer(a2);
            a2.startAnimation();
            return true;
        }
    };
    private WebViewClient mWebViewClient = new com.amap.bundle.webview.widget.WebViewEx.b() {
        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            if (ExtendedWebView.this.mLaunchTaobaoLogin != null && !TextUtils.isEmpty(str) && str.contains("http://h5.m.taobao.com/mlapp/olist.html?ttid=@aligaode")) {
                ExtendedWebView.this.mLaunchTaobaoLogin.c();
            }
            if (ExtendedWebView.this.mLaunchTaobaoLogin != null && !TextUtils.isEmpty(str) && str.contains("taobao.com/trip/hotel/order/")) {
                ExtendedWebView.this.mLaunchTaobaoLogin.c();
            }
        }

        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            ExtendedWebView.this.setComKeyUrls(str);
            if (boe.a(webView, str) || boe.a(str)) {
                return true;
            }
            if (ExtendedWebView.this.mLaunchTaobaoLogin == null || TextUtils.isEmpty(str) || (!str.contains("login.m.taobao.com/login.htm") && !str.contains("login.tmall.com"))) {
                if (ExtendedWebView.this.mShowProgress) {
                    ExtendedWebView.this.mProgressBar.show();
                }
                if (ExtendedWebView.this.mShowTopProress) {
                    ExtendedWebView.this.mTopProgressBar.setVisibility(0);
                }
                int contentHeight = webView.getContentHeight();
                if (ExtendedWebView.this.mWebViewOverloadListener != null && !ExtendedWebView.this.mWebViewOverloadListener.b(str)) {
                    if (ExtendedWebView.this.mWebViewList.size() - 1 >= 0) {
                        WebView webView2 = (WebView) ExtendedWebView.this.mWebViewList.remove(ExtendedWebView.this.mWebViewList.size() - 1);
                        webView2.onPause();
                        ExtendedWebView.this.destroyWebView(webView2);
                    }
                    a(webView, str);
                } else if (contentHeight <= 0 || str.contains("trafficViolations/index.html")) {
                    b(webView, str);
                } else {
                    int indexOf = ExtendedWebView.this.mWebViewList.indexOf(ExtendedWebView.this.mCurWebView);
                    if (indexOf < ExtendedWebView.this.mWebViewList.size() - 1) {
                        int size = (ExtendedWebView.this.mWebViewList.size() - 1) - indexOf;
                        for (int i = 0; i < size; i++) {
                            WebView webView3 = (WebView) ExtendedWebView.this.mWebViewList.remove(ExtendedWebView.this.mWebViewList.size() - 1);
                            webView3.onPause();
                            ExtendedWebView.this.destroyWebView(webView3);
                        }
                    }
                    a(webView, str);
                }
                if (ExtendedWebView.this.mOnWebViewEventListener != null) {
                    ExtendedWebView.this.mOnWebViewEventListener.onWebViewPageStart(ExtendedWebView.this.mCurWebView);
                }
                return true;
            }
            ExtendedWebView.this.mLaunchTaobaoLogin.c();
            return true;
        }

        private void a(WebView webView, String str) {
            ExtendedWebView.this.mCurWebView = new WebViewEx(ExtendedWebView.this.mContext);
            ExtendedWebView.this.mCurWebView.setContentDescription(ExtendedWebView.TAG);
            ExtendedWebView.this.mCurWebView.setFocusable(ExtendedWebView.this.focusable);
            if (ExtendedWebView.this.touchListener != null) {
                ExtendedWebView.this.mCurWebView.setOnTouchListener(ExtendedWebView.this.touchListener);
            }
            ExtendedWebView.this.mWebViewContainer.removeAllViews();
            ExtendedWebView.this.mWebViewContainer.addView(ExtendedWebView.this.mCurWebView, ExtendedWebView.this.mWebViewLayoutParams);
            ExtendedWebView.this.mWebViewContainer.requestLayout();
            ExtendedWebView.this.initializeWebView((WebView) ExtendedWebView.this.mCurWebView, ExtendedWebView.this.jsMethods, ExtendedWebView.this.mHandler, ExtendedWebView.this.isEnableJs);
            ExtendedWebView.this.mWebViewList.add(ExtendedWebView.this.mCurWebView);
            ExtendedWebView.this.setWebViewProxy();
            b(webView, str);
        }

        private void b(WebView webView, String str) {
            StringBuilder sb = new StringBuilder();
            if (a((WebViewEx) webView, sb, str)) {
                ExtendedWebView.this.mCurWebView.loadUrl(sb.toString());
            } else {
                ExtendedWebView.this.mCurWebView.loadUrl(str);
            }
        }

        public final void onPageFinished(final WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (ExtendedWebView.this.mOnWebViewEventListener != null) {
                ExtendedWebView.this.mIsPageFinished = true;
                ExtendedWebView.this.mOnWebViewEventListener.onWebViewPageFinished(webView);
            }
            ahl.b(new defpackage.ahl.a() {
                public final void onError(Throwable th) {
                }

                public final Object doBackground() throws Exception {
                    return ahd.a(ExtendedWebView.this.mContext, (String) "js/activeEvent.js", Charset.forName("utf-8"));
                }

                public final void onFinished(Object obj) {
                    if (ExtendedWebView.this.isWebViewAlive && (obj instanceof String)) {
                        if (!TextUtils.isEmpty((String) obj)) {
                            ExtendedWebView.this.mCurWebView.loadUrl("javascript:".concat(String.valueOf(obj)));
                        }
                        try {
                            webView.requestFocus();
                        } catch (NullPointerException unused) {
                        }
                    }
                }
            });
        }

        public final void onReceivedError(WebView webView, int i, String str, String str2) {
            webView.clearCache(true);
            ExtendedWebView.this.oldUrl = str2;
            ExtendedWebView.this.saveWebError(i, str, str2);
            StringBuilder sb = new StringBuilder();
            WebViewEx webViewEx = (WebViewEx) webView;
            if (b(webViewEx, sb, str2) && sb.length() > 0) {
                webViewEx.setUrlRewriteEnable(false);
                ExtendedWebView.this.loadUrl(sb.toString());
                webViewEx.setUrlRewriteEnable(true);
            } else if (i == 404) {
                ExtendedWebView.this.loadUrl("file:///android_asset/not_found_error.html");
            } else {
                ExtendedWebView.this.loadUrl("file:///android_asset/connect_error.html");
            }
        }
    };
    /* access modifiers changed from: private */
    public RelativeLayout mWebViewContainer;
    /* access modifiers changed from: private */
    public LayoutParams mWebViewLayoutParams;
    /* access modifiers changed from: private */
    public ArrayList<WebViewEx> mWebViewList = new ArrayList<>();
    /* access modifiers changed from: private */
    public a mWebViewOverloadListener;
    b mjavaScrpitHandler;
    OnLongClickListener onLongClickListener = new OnLongClickListener() {
        public final boolean onLongClick(View view) {
            return false;
        }
    };

    public interface a {
        boolean b(String str);
    }

    public static class b extends Handler {
        private WeakReference<Object> a;

        static class a {
            public Method a;
            public String[] b;

            a() {
            }
        }

        public b(Object obj) {
            this.a = new WeakReference<>(obj);
        }

        @JavascriptInterface
        @com.uc.webview.export.JavascriptInterface
        public final void invokeMethod(String str, String[] strArr) {
            a aVar = new a();
            aVar.b = strArr;
            Class[] clsArr = {String[].class};
            try {
                Object obj = this.a.get();
                if (obj != null) {
                    aVar.a = obj.getClass().getMethod(str, clsArr);
                    Message obtainMessage = obtainMessage(0);
                    obtainMessage.obj = aVar;
                    sendMessage(obtainMessage);
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

        @JavascriptInterface
        @com.uc.webview.export.JavascriptInterface
        public final String getItem(String str, String str2) {
            return bic.a(str).get(str2);
        }

        @JavascriptInterface
        @com.uc.webview.export.JavascriptInterface
        public final void setItem(String str, String str2, String str3) {
            WebStorage a2 = bic.a(str);
            a2.beginTransaction();
            a2.set(str2, str3);
            a2.commit();
        }

        @JavascriptInterface
        @com.uc.webview.export.JavascriptInterface
        public final void handleMessage(Message message) {
            a aVar = (a) message.obj;
            if (!(message.what != 0 || aVar == null || aVar.a == null)) {
                try {
                    Object obj = this.a.get();
                    if (obj != null) {
                        aVar.a.invoke(obj, new Object[]{aVar.b});
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
    }

    /* access modifiers changed from: protected */
    public void onResume() {
    }

    public void showBottomMenu(boolean z) {
    }

    public String getKeyUrl() {
        return this.keyUrl;
    }

    public void setKeyUrl(String str) {
        this.keyUrl = str;
    }

    public ExtendedWebView(Context context) {
        super(context);
        init(context);
    }

    public ExtendedWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public ExtendedWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void init(Context context) {
        this.mContext = context;
        this.mHandler = new Handler();
        this.mTopProgressBar = new ProgressBar(this.mContext, null, 16842872);
        this.mTopProgressBar.setId(R.id.extend_web_view_progress_id);
        this.mTopProgressBar.setVisibility(8);
        LayoutParams layoutParams = new LayoutParams(-1, 4);
        layoutParams.addRule(10);
        addView(this.mTopProgressBar, layoutParams);
        this.mProgressBar = new ProgressDlg(AMapAppGlobal.getTopActivity(), AMapAppGlobal.getApplication().getApplicationContext().getResources().getString(R.string.extendedwebview_in_progress));
        this.mProgressBar.setCancelable(true);
        this.mProgressBar.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        });
        this.mWebViewContainer = new RelativeLayout(context);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        layoutParams2.addRule(3, this.mTopProgressBar.getId());
        layoutParams2.alignWithParent = true;
        addView(this.mWebViewContainer, layoutParams2);
        this.mWebViewLayoutParams = new LayoutParams(-1, -1);
        if (this.mWebViewList.size() == 0) {
            this.mCurWebView = new WebViewEx(this.mContext);
            this.mWebViewList.add(this.mCurWebView);
        } else {
            this.mCurWebView = this.mWebViewList.get(0);
            this.mWebViewList.clear();
            this.mWebViewList.add(this.mCurWebView);
        }
        this.mCurWebView.setContentDescription(TAG);
        this.mWebViewContainer.removeAllViews();
        this.mWebViewContainer.addView(this.mCurWebView, this.mWebViewLayoutParams);
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

    public void setOnWebViewOverloadListener(a aVar) {
        this.mWebViewOverloadListener = aVar;
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

    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x003c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initializeWebView(boolean r6, com.uc.webview.export.WebView r7, java.lang.Object r8, android.os.Handler r9, boolean r10) {
        /*
            r5 = this;
            if (r7 != 0) goto L_0x0003
            return
        L_0x0003:
            if (r9 == 0) goto L_0x0007
            r5.mHandler = r9
        L_0x0007:
            r5.jsMethods = r8
            r5.isEnableJs = r10
            int r9 = android.os.Build.VERSION.SDK_INT
            r0 = 16
            r1 = 1
            if (r9 < r0) goto L_0x001c
            int r9 = android.os.Build.VERSION.SDK_INT
            r2 = 18
            if (r9 > r2) goto L_0x001c
            r9 = 0
            r7.setLayerType(r1, r9)
        L_0x001c:
            r9 = 0
            r7.setDrawingCacheEnabled(r9)
            boolean r2 = r5.mOpenSafeExFeature
            if (r2 == 0) goto L_0x002d
            com.amap.bundle.webview.widget.WebViewEx r2 = r5.mCurWebView
            if (r2 == 0) goto L_0x002d
            com.amap.bundle.webview.widget.WebViewEx r2 = r5.mCurWebView
            r2.openSafeExFeature()
        L_0x002d:
            com.uc.webview.export.WebSettings r2 = r7.getSettings()
            r2.setJavaScriptEnabled(r10)     // Catch:{ NullPointerException -> 0x003c, SecurityException -> 0x0038 }
            r5.disableAccessibility()     // Catch:{ NullPointerException -> 0x003c, SecurityException -> 0x0038 }
            goto L_0x003c
        L_0x0038:
            r10 = move-exception
            r10.printStackTrace()
        L_0x003c:
            r2.setUseWideViewPort(r1)     // Catch:{ Exception -> 0x0097 }
            r2.setLoadWithOverviewMode(r1)     // Catch:{ Exception -> 0x0097 }
            r5.addAmapUserAgent(r2)     // Catch:{ Exception -> 0x0097 }
            if (r6 == 0) goto L_0x0055
            r2.setAppCacheEnabled(r1)     // Catch:{ Exception -> 0x0097 }
            r3 = 20971520(0x1400000, double:1.03613076E-316)
            r2.setAppCacheMaxSize(r3)     // Catch:{ Exception -> 0x0097 }
            r6 = -1
            r2.setCacheMode(r6)     // Catch:{ Exception -> 0x0097 }
            goto L_0x005c
        L_0x0055:
            r2.setAppCacheEnabled(r9)     // Catch:{ Exception -> 0x0097 }
            r6 = 2
            r2.setCacheMode(r6)     // Catch:{ Exception -> 0x0097 }
        L_0x005c:
            r2.setDatabaseEnabled(r1)     // Catch:{ Exception -> 0x0097 }
            android.content.Context r6 = r5.getContext()     // Catch:{ Exception -> 0x0097 }
            java.lang.String r10 = "databases"
            java.io.File r6 = r6.getDir(r10, r9)     // Catch:{ Exception -> 0x0097 }
            java.lang.String r6 = r6.getPath()     // Catch:{ Exception -> 0x0097 }
            r2.setDatabasePath(r6)     // Catch:{ Exception -> 0x0097 }
            r2.setAllowFileAccess(r1)     // Catch:{ Exception -> 0x0097 }
            r2.setDomStorageEnabled(r1)     // Catch:{ Exception -> 0x0097 }
            r6 = 100
            r2.setTextZoom(r6)     // Catch:{ Exception -> 0x0097 }
            int r6 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0097 }
            if (r6 < r0) goto L_0x0082
            r2.setAllowUniversalAccessFromFileURLs(r1)     // Catch:{ Exception -> 0x0097 }
        L_0x0082:
            boolean r6 = r5.mSupportZoom     // Catch:{ Exception -> 0x0097 }
            if (r6 == 0) goto L_0x008d
            r2.setBuiltInZoomControls(r1)     // Catch:{ Exception -> 0x0097 }
            r2.setSupportZoom(r1)     // Catch:{ Exception -> 0x0097 }
            goto L_0x0093
        L_0x008d:
            r2.setBuiltInZoomControls(r9)     // Catch:{ Exception -> 0x0097 }
            r2.setSupportZoom(r9)     // Catch:{ Exception -> 0x0097 }
        L_0x0093:
            r2.setJavaScriptCanOpenWindowsAutomatically(r1)     // Catch:{ Exception -> 0x0097 }
            goto L_0x009b
        L_0x0097:
            r6 = move-exception
            r6.printStackTrace()
        L_0x009b:
            r7.setScrollBarStyle(r9)
            com.amap.bundle.webview.widget.ExtendedWebView$b r6 = new com.amap.bundle.webview.widget.ExtendedWebView$b
            r6.<init>(r8)
            r5.mjavaScrpitHandler = r6
            com.amap.bundle.webview.widget.ExtendedWebView$b r6 = r5.mjavaScrpitHandler
            java.lang.String r8 = "jsInterface"
            r7.addJavascriptInterface(r6, r8)
            bnr r6 = new bnr
            r6.<init>()
            java.lang.String r8 = "kvInterface"
            r7.addJavascriptInterface(r6, r8)
            com.uc.webview.export.WebViewClient r6 = r5.mWebViewClient
            r7.setWebViewClient(r6)
            com.uc.webview.export.WebChromeClient r6 = r5.mWebChromeClient
            r7.setWebChromeClient(r6)
            com.amap.bundle.webview.widget.ExtendedWebView$2 r6 = new com.amap.bundle.webview.widget.ExtendedWebView$2
            r6.<init>()
            r7.setDownloadListener(r6)
            android.view.View$OnLongClickListener r6 = r5.onLongClickListener
            r7.setOnLongClickListener(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.webview.widget.ExtendedWebView.initializeWebView(boolean, com.uc.webview.export.WebView, java.lang.Object, android.os.Handler, boolean):void");
    }

    /* access modifiers changed from: private */
    public void setComKeyUrls(String str) {
        if (TextUtils.isEmpty(str) || !str.contains("yhedit.html")) {
            setKeyUrl(null);
        } else {
            setKeyUrl("yhedit");
        }
    }

    private void addAmapUserAgent(WebSettings webSettings) {
        if (webSettings != null) {
            StringBuilder sb = new StringBuilder();
            String userAgentString = webSettings.getUserAgentString();
            if (!TextUtils.isEmpty(userAgentString)) {
                sb.append(userAgentString);
                sb.append(Token.SEPARATOR);
            }
            String str = defpackage.ahp.a.a().a;
            String a2 = ajp.a();
            sb.append("amap/");
            sb.append(str);
            sb.append(Token.SEPARATOR);
            sb.append("Mac=".concat(String.valueOf(a2)));
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
        if (this.isWebViewAlive && this.mCurWebView != null) {
            setWebViewProxy();
            if (this.mShowProgress) {
                this.mProgressBar.show();
            }
            if (this.mShowTopProress) {
                this.mTopProgressBar.setVisibility(0);
            }
            try {
                logInvalidFileUrl(str);
                this.mCurWebView.loadUrl(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadUrlWithNewWebview(String str) {
        if (this.isWebViewAlive) {
            if (this.mShowProgress) {
                this.mProgressBar.show();
            }
            if (this.mShowTopProress) {
                this.mTopProgressBar.setVisibility(0);
            }
            int indexOf = this.mWebViewList.indexOf(this.mCurWebView);
            if (indexOf < this.mWebViewList.size() - 1) {
                int size = (this.mWebViewList.size() - 1) - indexOf;
                for (int i = 0; i < size; i++) {
                    this.mWebViewList.remove(this.mWebViewList.size() - 1);
                }
            }
            this.mCurWebView = new WebViewEx(this.mContext);
            initializeWebView((WebView) this.mCurWebView, this.jsMethods, this.mHandler, this.isEnableJs);
            this.mWebViewList.add(this.mCurWebView);
            setWebViewProxy();
            this.mCurWebView.loadUrl(str);
            logInvalidFileUrl(str);
            this.mWebViewContainer.removeAllViews();
            this.mWebViewContainer.addView(this.mCurWebView, this.mWebViewLayoutParams);
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
            if (this.mWebViewList.size() > 0) {
                this.mWebViewList.remove(this.mWebViewList.size() - 1);
            }
            this.mCurWebView = new WebViewEx(this.mContext);
            this.mWebViewList.add(this.mCurWebView);
            initializeWebView((WebView) this.mCurWebView, this.jsMethods, this.mHandler, this.isEnableJs);
            setWebViewProxy();
            this.mCurWebView.loadUrl(str);
            logInvalidFileUrl(str);
            this.mWebViewContainer.removeAllViews();
            this.mWebViewContainer.addView(this.mCurWebView, this.mWebViewLayoutParams);
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
                this.mCurWebView.evaluateJavascript(str, null);
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
            int indexOf = this.mWebViewList.indexOf(this.mCurWebView);
            String url = this.mCurWebView.getUrl();
            this.mCurWebView.destroy();
            this.mCurWebView = new WebViewEx(this.mContext);
            this.mCurWebView.setContentDescription(TAG);
            this.mCurWebView.setFocusable(this.focusable);
            if (this.touchListener != null) {
                this.mCurWebView.setOnTouchListener(this.touchListener);
            }
            this.mWebViewContainer.removeAllViews();
            this.mWebViewContainer.addView(this.mCurWebView, this.mWebViewLayoutParams);
            this.mWebViewContainer.requestLayout();
            initializeWebView((WebView) this.mCurWebView, this.jsMethods, this.mHandler, this.isEnableJs);
            setWebViewProxy();
            if (this.mShowProgress) {
                this.mProgressBar.show();
            }
            if (this.mShowTopProress) {
                this.mTopProgressBar.setVisibility(0);
            }
            if (this.mCurWebView != null) {
                if (indexOf >= 0 && indexOf < this.mWebViewList.size()) {
                    this.mWebViewList.remove(indexOf);
                }
                if (indexOf >= 0 && indexOf <= this.mWebViewList.size()) {
                    this.mWebViewList.add(indexOf, this.mCurWebView);
                }
                if (url != null) {
                    if (url.equals("file:///android_asset/not_found_error.html") || url.equals("file:///android_asset/connect_error.html")) {
                        this.mCurWebView.loadUrl(this.oldUrl);
                        return;
                    }
                    this.mCurWebView.loadUrl(url);
                }
            }
        }
    }

    public void reload(String str) {
        if (this.isWebViewAlive) {
            if (TextUtils.isEmpty(this.mUrl)) {
                this.mUrl = str;
            }
            if (this.mOnWebViewEventListener != null) {
                this.mOnWebViewEventListener.onWebViewPageRefresh(this.mCurWebView);
            }
            int indexOf = this.mWebViewList.indexOf(this.mCurWebView);
            String url = this.mCurWebView.getUrl();
            if (TextUtils.isEmpty(url) || "about:blank".equals(url)) {
                url = this.mUrl;
            } else {
                this.mUrl = url;
            }
            this.mCurWebView.destroy();
            this.mCurWebView = new WebViewEx(this.mContext);
            this.mCurWebView.setContentDescription(TAG);
            this.mCurWebView.setFocusable(this.focusable);
            if (this.touchListener != null) {
                this.mCurWebView.setOnTouchListener(this.touchListener);
            }
            this.mWebViewContainer.removeAllViews();
            this.mWebViewContainer.addView(this.mCurWebView, this.mWebViewLayoutParams);
            this.mWebViewContainer.requestLayout();
            initializeWebView((WebView) this.mCurWebView, this.jsMethods, this.mHandler, this.isEnableJs);
            setWebViewProxy();
            if (this.mShowProgress) {
                this.mProgressBar.show();
            }
            if (this.mShowTopProress) {
                this.mTopProgressBar.setVisibility(0);
            }
            if (this.mCurWebView != null) {
                if (indexOf >= 0 && indexOf < this.mWebViewList.size()) {
                    this.mWebViewList.remove(indexOf);
                }
                if (indexOf >= 0 && indexOf <= this.mWebViewList.size()) {
                    this.mWebViewList.add(indexOf, this.mCurWebView);
                }
                if (url != null) {
                    if (url.equals("file:///android_asset/not_found_error.html") || url.equals("file:///android_asset/connect_error.html")) {
                        this.mCurWebView.loadUrl(this.oldUrl);
                        return;
                    }
                    this.mCurWebView.loadUrl(url);
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
        int indexOf = this.mWebViewList.indexOf(this.mCurWebView);
        return (indexOf != 1 || !"about:blank".equals(this.mWebViewList.get(0).getUrl())) && indexOf > 0;
    }

    public void gobackByStep() {
        if (this.jsMethods instanceof JsAdapter) {
            JsAdapter jsAdapter = (JsAdapter) this.jsMethods;
            int i = jsAdapter.getBundle().getInt("gobackStep");
            if (i > 0) {
                goBackOrForward(-i);
                jsAdapter.getBundle().remove("gobackStep");
                return;
            }
            goBack();
        }
    }

    public void goBack() {
        int indexOf = this.mWebViewList.indexOf(this.mCurWebView);
        if (indexOf > 0) {
            if (!this.mCurWebView.canGoBack() || this.mCurWebView.getUrl().equals("file:///android_asset/connect_error.html") || this.mCurWebView.getUrl().equals("file:///android_asset/not_found_error.html")) {
                this.mCurWebView = this.mWebViewList.get(indexOf - 1);
                this.mWebViewContainer.removeAllViews();
                this.mWebViewContainer.addView(this.mCurWebView, this.mWebViewLayoutParams);
            } else {
                this.mCurWebView.goBack();
            }
            if (this.mOnWebViewEventListener != null) {
                this.mOnWebViewEventListener.onWebViewPageStart(this.mCurWebView);
                this.mOnWebViewEventListener.onReceivedTitle(this.mCurWebView, this.mCurWebView.getTitle());
                this.mOnWebViewEventListener.onWebViewPageFinished(this.mCurWebView);
            }
            this.mCurWebView.onResume();
            activeEvent(Constants.EVENT_RESUME, "2");
        }
    }

    public void goBackWithJs(JSONObject jSONObject, wa waVar) {
        int indexOf = this.mWebViewList.indexOf(this.mCurWebView);
        goBack();
        if (indexOf > 0 && (this.jsMethods instanceof JsAdapter)) {
            try {
                ((JsAdapter) this.jsMethods).callJs(waVar.a, jSONObject.toString());
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }

    public void goBackOrForward(int i) {
        int indexOf = this.mWebViewList.indexOf(this.mCurWebView);
        if (i <= 0) {
            i += indexOf;
            if (i < 0) {
                i = 0;
            }
        }
        this.mCurWebView = this.mWebViewList.get(i);
        this.mWebViewContainer.removeAllViews();
        this.mWebViewContainer.addView(this.mCurWebView);
        if (this.mOnWebViewEventListener != null) {
            this.mOnWebViewEventListener.onWebViewPageStart(this.mCurWebView);
            this.mOnWebViewEventListener.onReceivedTitle(this.mCurWebView, this.mCurWebView.getTitle());
            this.mOnWebViewEventListener.onWebViewPageFinished(this.mCurWebView);
        }
        activeEvent(Constants.EVENT_RESUME, "2");
    }

    public boolean canGoForward() {
        return this.mWebViewList.indexOf(this.mCurWebView) < this.mWebViewList.size() - 1;
    }

    public void goForward() {
        int indexOf = this.mWebViewList.indexOf(this.mCurWebView);
        if (indexOf < this.mWebViewList.size() - 1) {
            this.mCurWebView = this.mWebViewList.get(indexOf + 1);
            this.mWebViewContainer.removeAllViews();
            this.mWebViewContainer.addView(this.mCurWebView, this.mWebViewLayoutParams);
            if (this.mOnWebViewEventListener != null) {
                this.mOnWebViewEventListener.onWebViewPageStart(this.mCurWebView);
                this.mOnWebViewEventListener.onReceivedTitle(this.mCurWebView, this.mCurWebView.getTitle());
                this.mOnWebViewEventListener.onWebViewPageFinished(this.mCurWebView);
            }
        }
    }

    private void activeEvent(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("action", "activeEvent");
            jSONObject.put("type", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("reson", str2);
            jSONObject.put("data", jSONObject2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder("javascript:activeEvent(");
        sb.append(jSONObject.toString());
        sb.append(")");
        loadJs(sb.toString());
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
        this.mWebViewList.clear();
        this.mWebViewList.add(this.mCurWebView);
    }

    public void destroy() {
        this.isWebViewAlive = false;
        int size = this.mWebViewList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                destroyWebView(this.mWebViewList.get(i));
            }
            this.mWebViewList.clear();
        }
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mHandler = null;
        }
        if (this.mjavaScrpitHandler != null) {
            this.mjavaScrpitHandler.removeCallbacksAndMessages(null);
            this.mjavaScrpitHandler = null;
        }
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
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.getType() != 1) {
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

    public void cancelAlert() {
        if (this.mJsResult != null) {
            this.mJsResult.cancel();
        }
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
        if (this.mWebViewList != null) {
            Iterator<WebViewEx> it = this.mWebViewList.iterator();
            while (it.hasNext()) {
                it.next().setFocusable(z);
            }
        }
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.touchListener = onTouchListener;
        if (!(this.mWebViewList == null || this.touchListener == null)) {
            Iterator<WebViewEx> it = this.mWebViewList.iterator();
            while (it.hasNext()) {
                it.next().setOnTouchListener(this.touchListener);
            }
        }
        if (this.mCurWebView != null && this.mCurWebView.isFocusable()) {
            this.mCurWebView.requestFocus();
        }
    }

    public void openSafeExFeature(boolean z) {
        if (this.mCurWebView != null && (this.mCurWebView instanceof WebViewEx) && z) {
            if (this.mjavaScrpitHandler != null) {
                this.mCurWebView.openSafeExFeature();
                this.mCurWebView.addJavascriptInterface(this.mjavaScrpitHandler, "jsInterface");
                this.mCurWebView.addJavascriptInterface(new bnr(), "kvInterface");
                return;
            }
            this.mOpenSafeExFeature = z;
        }
    }

    public int getWebListSize() {
        if (this.mWebViewList != null) {
            return this.mWebViewList.size();
        }
        return 0;
    }

    public void saveWebError(int i, String str, String str2) {
        final int i2 = i;
        final String str3 = str;
        final String str4 = str2;
        AnonymousClass6 r0 = new Thread("ExtendedWebViewThread") {
            public final void run() {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("errorCode", i2);
                    jSONObject.put("description", str3);
                    jSONObject.put("isHasUrl", ahd.b(str4.replace("file://", "")));
                    jSONObject.put("failingUrl", str4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2(LogConstant.POI_ERROR_PAGE_ID, "B003", jSONObject);
            }
        };
        r0.start();
    }
}
