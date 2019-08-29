package com.alipay.mobile.nebulacore.android;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.Rect;
import android.net.http.SslCertificate;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.view.IH5EmbedViewJSCallback;
import com.alipay.mobile.nebula.webview.APDownloadListener;
import com.alipay.mobile.nebula.webview.APHitTestResult;
import com.alipay.mobile.nebula.webview.APWebBackForwardList;
import com.alipay.mobile.nebula.webview.APWebChromeClient;
import com.alipay.mobile.nebula.webview.APWebSettings;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebula.webview.APWebViewClient;
import com.alipay.mobile.nebula.webview.APWebViewListener;
import com.alipay.mobile.nebula.webview.H5ScrollChangedCallback;
import com.alipay.mobile.nebula.webview.WebViewType;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndroidWebView implements APWebView {
    private APWebSettings a;
    private WebView b;
    /* access modifiers changed from: private */
    public APWebViewListener c;
    /* access modifiers changed from: private */
    public H5ScrollChangedCallback d;
    private String e;
    private float f = 1.0f;

    private class AndroidHitTestResult implements APHitTestResult {
        HitTestResult a;

        AndroidHitTestResult(HitTestResult hitTestResult) {
            this.a = hitTestResult;
        }

        public String getExtra() {
            return this.a.getExtra();
        }

        public int getType() {
            return this.a.getType();
        }
    }

    private final class WebViewEx extends WebView {
        public WebViewEx(Context context) {
            super(context);
            setTag("H5WebView");
        }

        /* access modifiers changed from: protected */
        public final boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            if (deltaY >= 0 || scrollY != 0 || AndroidWebView.this.c == null) {
                return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            }
            return AndroidWebView.this.c.overScrollBy(deltaX, deltaY, scrollX, scrollY);
        }

        /* access modifiers changed from: protected */
        public final void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (AndroidWebView.this.c != null) {
                AndroidWebView.this.c.onDetachedFromWindow();
            }
        }

        /* access modifiers changed from: protected */
        public final void onScrollChanged(int l, int t, int oldl, int oldt) {
            if (AndroidWebView.this.d != null) {
                AndroidWebView.this.d.onScroll(l - oldl, t - oldt);
            } else {
                super.onScrollChanged(l, t, oldl, oldt);
            }
        }
    }

    public AndroidWebView(Context context) {
        this.b = new WebViewEx(context);
        this.b.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                return false;
            }
        });
        this.a = new AndroidWebSettings(this.b.getSettings());
        CookieManager cookieManager = CookieManager.getInstance();
        if (VERSION.SDK_INT >= 21) {
            try {
                cookieManager.setAcceptThirdPartyCookies(this.b, true);
            } catch (Throwable t) {
                H5Log.e("AndroidWebView", "setAcceptThirdPartyCookies", t);
            }
        }
        this.a.setSavePassword(false);
    }

    public int getScrollY() {
        return this.b.getScrollY();
    }

    public boolean dispatchKeyEvent(KeyEvent var1) {
        return this.b.dispatchKeyEvent(var1);
    }

    public boolean getCurrentPageSnapshot(Rect dstRect, Rect srcRect, Bitmap bitmap, boolean clipByView, int coordinate) {
        return false;
    }

    public String getVersion() {
        if (TextUtils.isEmpty(this.e)) {
            String version = "1.1";
            if (VERSION.SDK_INT >= 19) {
                String ua = this.a.getUserAgentString();
                try {
                    Pattern pattern = H5PatternHelper.compile("Chrome/\\d+\\.\\d+\\.\\d+\\.\\d+");
                    if (pattern != null) {
                        Matcher matcher = pattern.matcher(ua);
                        if (matcher.find()) {
                            version = matcher.group(0);
                        }
                    }
                } catch (Throwable t) {
                    H5Log.e("AndroidWebView", "catch exception ", t);
                }
            }
            this.e = version;
        }
        return this.e;
    }

    public WebViewType getType() {
        return WebViewType.SYSTEM_BUILD_IN;
    }

    public Picture capturePicture() {
        return this.b.capturePicture();
    }

    public void setAPWebViewListener(APWebViewListener apWebViewListener) {
        this.c = apWebViewListener;
    }

    @SuppressLint({"JavascriptInterface"})
    public void addJavascriptInterface(Object obj, String interfaceName) {
        if (this.b != null) {
            this.b.addJavascriptInterface(obj, interfaceName);
        }
    }

    @TargetApi(11)
    public void removeJavascriptInterface(String name) {
        if (VERSION.SDK_INT >= 11) {
            this.b.removeJavascriptInterface(name);
        }
    }

    public void setWebContentsDebuggingEnabled(boolean b2) {
        if (VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(b2);
        }
    }

    public void flingScroll(int vx, int vy) {
        this.b.flingScroll(vx, vy);
    }

    public boolean zoomIn() {
        return this.b.zoomIn();
    }

    public boolean zoomOut() {
        return this.b.zoomOut();
    }

    public void setHorizontalScrollbarOverlay(boolean b2) {
        this.b.setHorizontalScrollbarOverlay(b2);
    }

    public void setVerticalScrollbarOverlay(boolean overlay) {
        this.b.setVerticalScrollbarOverlay(overlay);
    }

    public boolean overlayHorizontalScrollbar() {
        return this.b.overlayHorizontalScrollbar();
    }

    public boolean overlayVerticalScrollbar() {
        return this.b.overlayVerticalScrollbar();
    }

    public SslCertificate getCertificate() {
        return this.b.getCertificate();
    }

    public void savePassword(String s, String s2, String s3) {
        this.b.savePassword(s, s2, s3);
    }

    public void setHttpAuthUsernamePassword(String host, String realm, String username, String password) {
        this.b.setHttpAuthUsernamePassword(host, realm, username, password);
    }

    public String[] getHttpAuthUsernamePassword(String host, String realm) {
        return this.b.getHttpAuthUsernamePassword(host, realm);
    }

    public void destroy() {
        this.b.destroy();
    }

    public void setNetworkAvailable(boolean networkUp) {
        this.b.setNetworkAvailable(networkUp);
    }

    public APWebBackForwardList saveState(Bundle bundle) {
        WebBackForwardList list = this.b.saveState(bundle);
        if (list != null) {
            return new AndroidWebBackForwardList(list);
        }
        return null;
    }

    public APWebBackForwardList restoreState(Bundle bundle) {
        WebBackForwardList list = this.b.restoreState(bundle);
        if (list != null) {
            return new AndroidWebBackForwardList(list);
        }
        return null;
    }

    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        this.b.loadUrl(url, additionalHttpHeaders);
    }

    public void loadUrl(String url) {
        this.b.loadUrl(url);
    }

    public void execJavaScript4EmbedView(String url, IH5EmbedViewJSCallback ih5EmbedViewJSCallback) {
    }

    public void postUrl(String url, byte[] postData) {
        this.b.postUrl(url, postData);
    }

    public void loadData(String data, String mimeType, String encoding) {
        this.b.loadData(data, mimeType, encoding);
    }

    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        this.b.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    public void evaluateJavascript(String s, ValueCallback<String> stringValueCallback) {
        if (VERSION.SDK_INT >= 19) {
            this.b.evaluateJavascript(s, stringValueCallback);
        } else if (stringValueCallback != null) {
            stringValueCallback.onReceiveValue("");
        }
    }

    public void stopLoading() {
        this.b.stopLoading();
    }

    public void reload() {
        this.b.reload();
    }

    public boolean canGoBack() {
        return this.b.canGoBack();
    }

    public void goBack() {
        this.b.goBack();
    }

    public boolean canGoForward() {
        return this.b.canGoForward();
    }

    public void goForward() {
        this.b.goForward();
    }

    public boolean canGoBackOrForward(int steps) {
        return this.b.canGoBackOrForward(steps);
    }

    public void goBackOrForward(int steps) {
        this.b.goBackOrForward(steps);
    }

    public boolean pageUp(boolean top) {
        return this.b.pageUp(top);
    }

    public boolean pageDown(boolean bottom) {
        return this.b.pageDown(bottom);
    }

    public void setInitialScale(int scaleInPercent) {
        this.b.setInitialScale(scaleInPercent);
    }

    public void invokeZoomPicker() {
        this.b.invokeZoomPicker();
    }

    public String getUrl() {
        return this.b.getUrl();
    }

    public String getOriginalUrl() {
        return this.b.getOriginalUrl();
    }

    public String getTitle() {
        return this.b.getTitle();
    }

    public Bitmap getFavicon() {
        return this.b.getFavicon();
    }

    public int getProgress() {
        return this.b.getProgress();
    }

    public int getContentHeight() {
        return this.b.getContentHeight();
    }

    public int getContentWidth() {
        H5Log.w("AndroidWebView", "getContentWidth() is currently not supported yet");
        return 0;
    }

    public void onPause() {
        if (VERSION.SDK_INT >= 11) {
            this.b.onPause();
        }
    }

    public void onResume() {
        if (VERSION.SDK_INT >= 11) {
            this.b.onResume();
        }
    }

    public boolean isPaused() {
        return false;
    }

    public void freeMemory() {
        this.b.freeMemory();
    }

    public void clearCache(boolean includeDiskFiles) {
        this.b.clearCache(includeDiskFiles);
    }

    public void clearFormData() {
        this.b.clearFormData();
    }

    public void clearHistory() {
        this.b.clearHistory();
    }

    public void clearSslPreferences() {
        this.b.clearSslPreferences();
    }

    public APWebBackForwardList copyBackForwardList() {
        WebBackForwardList list = this.b.copyBackForwardList();
        if (list != null) {
            return new AndroidWebBackForwardList(list);
        }
        return null;
    }

    public void setWebViewClient(APWebViewClient apWebViewClient) {
        this.b.setWebViewClient(new AndroidWebViewClient(this, apWebViewClient));
    }

    public void setDownloadListener(APDownloadListener apDownloadListener) {
        this.b.setDownloadListener(new AndroidDownloadListener(apDownloadListener));
    }

    public void setWebChromeClient(APWebChromeClient apWebChromeClient) {
        this.b.setWebChromeClient(new AndroidWebChromeClient(this, apWebChromeClient));
    }

    public APWebSettings getSettings() {
        return this.a;
    }

    public APHitTestResult getHitTestResult() {
        HitTestResult hitTestResult = this.b.getHitTestResult();
        if (hitTestResult == null) {
            return null;
        }
        return new AndroidHitTestResult(hitTestResult);
    }

    public View getView() {
        return this.b;
    }

    public H5Plugin getH5NumInputKeyboard() {
        return null;
    }

    public H5Plugin getH5NativeInput() {
        return null;
    }

    public void setScale(float v) {
        this.f = v;
    }

    public float getScale() {
        return this.f;
    }

    public void setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
        this.b.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
    }

    public void setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled) {
        this.b.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled);
    }

    public void setOnScrollChangedCallback(H5ScrollChangedCallback changedCallback) {
        this.d = changedCallback;
    }
}
