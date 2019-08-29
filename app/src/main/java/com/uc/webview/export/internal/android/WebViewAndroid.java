package com.uc.webview.export.internal.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.ValueCallback;
import android.webkit.WebMessagePort;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import com.uc.webview.export.DownloadListener;
import com.uc.webview.export.WebBackForwardList;
import com.uc.webview.export.WebChromeClient;
import com.uc.webview.export.WebMessage;
import com.uc.webview.export.WebSettings;
import com.uc.webview.export.WebView.FindListener;
import com.uc.webview.export.WebView.b;
import com.uc.webview.export.WebViewClient;
import com.uc.webview.export.annotations.Interface;
import com.uc.webview.export.internal.interfaces.ICommonExtension;
import com.uc.webview.export.internal.interfaces.IUCExtension;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.interfaces.IWebView;
import com.uc.webview.export.internal.interfaces.IWebView.IHitTestResult;
import com.uc.webview.export.internal.interfaces.IWebViewOverride;

@Interface
/* compiled from: ProGuard */
public class WebViewAndroid extends WebView implements IWebView {
    private com.uc.webview.export.WebView a;
    private IWebViewOverride b;

    /* compiled from: ProGuard */
    class a implements IHitTestResult {
        private HitTestResult b;

        /* synthetic */ a(WebViewAndroid webViewAndroid, HitTestResult hitTestResult, byte b2) {
            this(hitTestResult);
        }

        private a(HitTestResult hitTestResult) {
            this.b = hitTestResult;
        }

        public final int getType() {
            return this.b.getType();
        }

        public final String getExtra() {
            return this.b.getExtra();
        }
    }

    public void clearClientCertPreferencesNoStatic(Runnable runnable) {
    }

    public ICommonExtension getCommonExtension() {
        return this;
    }

    public IUCExtension getUCExtension() {
        return null;
    }

    public View getView() {
        return this;
    }

    public void notifyForegroundChanged(boolean z) {
    }

    public WebViewAndroid(Context context, AttributeSet attributeSet, com.uc.webview.export.WebView webView) {
        super(context, attributeSet);
        this.a = webView;
        setWebViewClient(new t(webView, new WebViewClient()));
        getSettings().setSavePassword(false);
        WaStat.stat((String) IWaStat.KEY_SYSTEM_NEW_WEBVIEW_PV);
    }

    public void computeScroll() {
        if (this.b != null) {
            this.b.coreComputeScroll();
        } else {
            super.computeScroll();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (this.b != null) {
            this.b.coreOnConfigurationChanged(configuration);
        } else {
            super.onConfigurationChanged(configuration);
        }
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        return super.onCreateInputConnection(editorInfo);
    }

    public void onVisibilityChanged(View view, int i) {
        if (this.b != null) {
            this.b.coreOnVisibilityChanged(view, i);
        } else {
            super.onVisibilityChanged(view, i);
        }
    }

    public void onScrollChanged(int i, int i2, int i3, int i4) {
        if (this.b != null) {
            this.b.coreOnScrollChanged(i, i2, i3, i4);
        } else {
            super.onScrollChanged(i, i2, i3, i4);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.b != null) {
            return this.b.coreDispatchTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void draw(Canvas canvas) {
        if (this.b != null) {
            this.b.coreDraw(canvas);
        } else {
            super.draw(canvas);
        }
    }

    public void destroy() {
        if (this.b != null) {
            this.b.coreDestroy();
        } else {
            super.destroy();
        }
    }

    public void setVisibility(int i) {
        if (this.b != null) {
            this.b.coreSetVisibility(i);
        } else {
            super.setVisibility(i);
        }
    }

    public void requestLayout() {
        if (this.b != null) {
            this.b.coreRequestLayout();
        } else {
            super.requestLayout();
        }
    }

    @TargetApi(9)
    public boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        if (VERSION.SDK_INT < 9) {
            return false;
        }
        if (this.b != null) {
            return this.b.coreOverScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
        }
        return super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
    }

    public void superComputeScroll() {
        super.computeScroll();
    }

    public void superOnConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void superOnVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
    }

    public void superOnScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
    }

    public boolean superDispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    public void superDraw(Canvas canvas) {
        super.draw(canvas);
    }

    public void superDestroy() {
        super.destroy();
    }

    public void superSetVisibility(int i) {
        super.setVisibility(i);
    }

    public void superRequestLayout() {
        super.requestLayout();
    }

    public boolean superOverScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        return super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
    }

    public void superOnInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (this.b != null) {
            this.b.coreOnInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        } else {
            onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        }
    }

    public void superOnInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (this.b != null) {
            this.b.coreOnInitializeAccessibilityEvent(accessibilityEvent);
        } else {
            onInitializeAccessibilityEvent(accessibilityEvent);
        }
    }

    public boolean superPerformAccessibilityAction(int i, Bundle bundle) {
        if (this.b != null) {
            return this.b.corePerformAccessibilityAction(i, bundle);
        }
        return false;
    }

    public void setOverrideObject(IWebViewOverride iWebViewOverride) {
        this.b = iWebViewOverride;
    }

    public IWebViewOverride getOverrideObject() {
        return this.b;
    }

    public void findAllAsync(String str) {
        if (VERSION.SDK_INT >= 16) {
            super.findAllAsync(str);
        }
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        if (downloadListener == null) {
            super.setDownloadListener(null);
        } else {
            super.setDownloadListener(new a(downloadListener));
        }
    }

    public void setFindListener(FindListener findListener) {
        WebView.FindListener findListener2;
        if (VERSION.SDK_INT >= 16) {
            if (findListener == null) {
                findListener2 = null;
            } else {
                findListener2 = new r(this, findListener);
            }
            super.setFindListener(findListener2);
        }
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        if (webViewClient == null) {
            webViewClient = new WebViewClient();
        }
        setWebViewClient(new t(this.a, webViewClient));
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        if (webChromeClient == null) {
            super.setWebChromeClient(null);
        } else {
            super.setWebChromeClient(new i(this.a, webChromeClient));
        }
    }

    public void evaluateJavascript(String str, ValueCallback<String> valueCallback) {
        if (VERSION.SDK_INT >= 19) {
            super.evaluateJavascript(str, valueCallback);
        }
    }

    public WebBackForwardList copyBackForwardListInner() {
        android.webkit.WebBackForwardList copyBackForwardList = super.copyBackForwardList();
        if (copyBackForwardList != null) {
            return new h(copyBackForwardList);
        }
        return null;
    }

    public IHitTestResult getHitTestResultInner() {
        HitTestResult hitTestResult = super.getHitTestResult();
        if (hitTestResult != null) {
            return new a(this, hitTestResult, 0);
        }
        return null;
    }

    public WebSettings getSettingsInner() {
        return new p(super.getSettings());
    }

    public WebBackForwardList restoreStateInner(Bundle bundle) {
        android.webkit.WebBackForwardList restoreState = super.restoreState(bundle);
        if (restoreState == null) {
            return null;
        }
        return new h(restoreState);
    }

    public WebBackForwardList saveStateInner(Bundle bundle) {
        android.webkit.WebBackForwardList saveState = super.saveState(bundle);
        if (saveState == null) {
            return null;
        }
        return new h(saveState);
    }

    public void postVisualStateCallback(long j, b bVar) {
        if (VERSION.SDK_INT >= 23) {
            super.postVisualStateCallback(j, new s(this, bVar));
        }
    }

    @TargetApi(23)
    public Object createWebMessageChannelInner() {
        WebMessagePort[] createWebMessageChannel = super.createWebMessageChannel();
        if (createWebMessageChannel == null) {
            return null;
        }
        n[] nVarArr = new n[createWebMessageChannel.length];
        for (int i = 0; i < createWebMessageChannel.length; i++) {
            nVarArr[i] = new n(createWebMessageChannel[i]);
        }
        return nVarArr;
    }

    @TargetApi(23)
    public void postWebMessageInner(WebMessage webMessage, Uri uri) {
        super.postWebMessage(new m(webMessage), uri);
    }
}
