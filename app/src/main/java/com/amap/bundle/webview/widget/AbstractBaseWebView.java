package com.amap.bundle.webview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityManager;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.alipay.mobile.common.transportext.biz.util.NetInfoHelper;
import com.amap.bundle.blutils.FileUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.widget.OnWebViewEventListener;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import com.uc.webview.export.DownloadListener;
import com.uc.webview.export.WebChromeClient;
import com.uc.webview.export.WebView;
import com.uc.webview.export.WebViewClient;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
public abstract class AbstractBaseWebView extends RelativeLayout implements defpackage.wk.a {
    public static final String ERROR_URL_404 = "file:///android_asset/not_found_error.html";
    public static final String ERROR_URL_OTHER = "file:///android_asset/connect_error.html";
    public static final int RES_ID_WEBVIEW = 16;
    public static final String TAG = "AbstracBaseWebView";
    private static String sDefaultPackageUri = "file:///data/data/com.autonavi.minimap/";
    private static String sPackageUri;
    protected final String CMWAP = NetInfoHelper.CMWAP_PROXY_HOST;
    protected boolean focusable = true;
    protected boolean isEnableJs;
    protected Object jsMethods;
    protected String keyUrl;
    protected Context mContext;
    protected WebViewEx mCurWebView;
    protected a mDialogCallback;
    protected b mErrorCallback;
    public Handler mHandler;
    protected boolean mIsPageFinished = false;
    protected d mLaunchTaobaoLogin;
    protected e mMeizuAuthroizedListener;
    protected OnWebViewEventListener mOnWebViewEventListener;
    protected ProgressDlg mProgressBar;
    protected boolean mShowProgress = false;
    protected boolean mShowTopProress = false;
    protected boolean mSupportZoom = false;
    protected ProgressBar mTopProgressBar;
    protected String oldUrl;
    protected OnTouchListener touchListener;
    protected boolean visible = true;

    public interface a {
    }

    public interface b {
    }

    public static class c extends Handler {
        private WeakReference<Object> a;

        static class a {
            public Method a;
            public String[] b;

            a() {
            }
        }

        @JavascriptInterface
        @com.uc.webview.export.JavascriptInterface
        public final void InterceptBack(boolean z) {
        }

        public c(Object obj) {
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

        public final void handleMessage(Message message) {
            a aVar = (a) message.obj;
            if (!(message.what != 0 || aVar == null || aVar.a == null)) {
                try {
                    Object obj = this.a.get();
                    if (obj != null) {
                        aVar.a.invoke(obj, new Object[]{aVar.b});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface d {
        boolean a(String str);

        void c();
    }

    public interface e {
        void b(String str);
    }

    public abstract boolean canGoBack();

    public abstract boolean canGoForward();

    public void cancelAlert() {
    }

    public abstract void clearCache(boolean z);

    public abstract void clearHistory();

    public abstract void clearView();

    public abstract void destroy();

    public abstract int getContentHeight();

    public abstract boolean getPageFinished();

    public abstract String getUrl();

    public abstract WebView getWebView();

    public abstract WebChromeClient getWebViewChromeClient();

    public abstract WebViewClient getWebViewClient();

    public abstract void goBack();

    public abstract void goBackOrForward(int i);

    public abstract void goBackWithJs(JSONObject jSONObject, wa waVar);

    public abstract void goForward();

    public abstract void gobackByStep();

    public abstract void initializeWebView(WebView webView, Object obj, Handler handler, boolean z);

    public abstract void initializeWebView(WebView webView, Object obj, Handler handler, boolean z, boolean z2);

    public abstract void initializeWebView(WebView webView, Object obj, Handler handler, boolean z, boolean z2, boolean z3);

    public abstract void initializeWebView(Object obj, Handler handler, boolean z, boolean z2);

    public abstract void initializeWebView(Object obj, Handler handler, boolean z, boolean z2, boolean z3);

    /* access modifiers changed from: protected */
    public abstract void initializeWebView(boolean z, WebView webView, Object obj, Handler handler, boolean z2);

    public abstract void loadData(String str, String str2, String str3);

    public abstract void loadDataWithBaseUrl(String str, String str2, String str3, String str4, String str5);

    public abstract void loadJs(String str);

    public abstract void loadUrl(String str);

    public abstract void loadUrlInNewWebView(String str);

    /* access modifiers changed from: protected */
    public abstract void onPause();

    /* access modifiers changed from: protected */
    public abstract void onResume();

    public abstract void openSafeExFeature(boolean z);

    public void pauseTimers() {
    }

    public abstract void reload();

    public abstract void reload(String str);

    public void resetWebView() {
    }

    public void resumeTimers() {
    }

    public abstract void setDownloadListener(DownloadListener downloadListener);

    public abstract void setFocusable(boolean z);

    public abstract void setOnTouchListener(OnTouchListener onTouchListener);

    public abstract void setOnWebViewEventListener(OnWebViewEventListener onWebViewEventListener);

    public abstract void setResId(int i);

    public abstract void showBottomMenu(boolean z);

    public abstract void switched();

    public AbstractBaseWebView(Context context) {
        super(context);
        erv.a();
    }

    public AbstractBaseWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        erv.a();
    }

    public AbstractBaseWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        erv.a();
    }

    public void setOnLaunchTaobaoLogin(d dVar) {
        this.mLaunchTaobaoLogin = dVar;
    }

    public void setMeizuAuthroizedListener(e eVar) {
        this.mMeizuAuthroizedListener = eVar;
    }

    /* access modifiers changed from: protected */
    public void adapteFoldScreen(WebViewEx webViewEx) {
        int width = ags.a(getContext()).width();
        int height = ags.a(getContext()).height();
        LayoutParams layoutParams = webViewEx.getLayoutParams();
        if (height * 3 < width * 4) {
            layoutParams.width = (height * 9) / 16;
            ((ViewGroup) webViewEx.getParent()).setBackgroundColor(-16777216);
            if (layoutParams instanceof LinearLayout.LayoutParams) {
                ((LinearLayout.LayoutParams) layoutParams).gravity = 1;
            } else if (layoutParams instanceof FrameLayout.LayoutParams) {
                ((FrameLayout.LayoutParams) layoutParams).gravity = 1;
            } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) layoutParams).addRule(14);
            }
            webViewEx.setLayoutParams(layoutParams);
            webViewEx.invalidate();
            return;
        }
        layoutParams.width = -1;
        webViewEx.setLayoutParams(layoutParams);
        webViewEx.invalidate();
    }

    public void stopLoading() {
        this.mCurWebView.stopLoading();
        if (this.mShowTopProress) {
            this.mTopProgressBar.setVisibility(8);
        }
    }

    public void dismissProgressDlg() {
        if (this.mProgressBar != null && this.mProgressBar.isShowing()) {
            this.mProgressBar.dismiss();
        }
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

    public void setShowTopProress(boolean z) {
        this.mShowTopProress = z;
    }

    public void setShowProgress(boolean z) {
        this.mShowProgress = z;
    }

    public void destroyWebView(WebView webView) {
        if (webView != null) {
            try {
                webView.destroyDrawingCache();
                webView.destroy();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void webViewLoadComplete(WebView webView) {
        webView.clearAnimation();
        webView.clearDisappearingChildren();
        webView.destroyDrawingCache();
    }

    public void addErrorCallback(b bVar) {
        this.mErrorCallback = bVar;
    }

    public void removeErrorCallback() {
        this.mErrorCallback = null;
    }

    public void addDialogCallback(a aVar) {
        this.mDialogCallback = aVar;
    }

    public void removeDialogCallback() {
        this.mDialogCallback = null;
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
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
    }

    public WebView getmCurWebView() {
        return this.mCurWebView;
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
                } catch (Exception unused) {
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void logInvalidFileUrl(String str) {
        if (!TextUtils.isEmpty(str) && str.startsWith("file")) {
            if (sPackageUri == null) {
                sPackageUri = "";
                File cacheDir = FileUtil.getCacheDir();
                if (cacheDir != null && !TextUtils.isEmpty(cacheDir.getParent())) {
                    StringBuilder sb = new StringBuilder("file://");
                    sb.append(cacheDir.getParent());
                    sPackageUri = sb.toString();
                }
            }
            if (!str.startsWith(sDefaultPackageUri) && (TextUtils.isEmpty(sPackageUri) || !str.startsWith(sPackageUri))) {
                coe.a("P0018", ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, "AbstractBaseWebView:".concat(String.valueOf(str)));
            }
        }
    }
}
