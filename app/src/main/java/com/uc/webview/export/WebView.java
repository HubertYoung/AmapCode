package com.uc.webview.export;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.MutableContextWrapper;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.ValueCallback;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.annotations.Reflection;
import com.uc.webview.export.extension.CommonExtension;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.extension.UCExtension;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWebView;
import com.uc.webview.export.internal.interfaces.IWebView.IHitTestResult;
import com.uc.webview.export.internal.interfaces.IWebViewOverride;
import com.uc.webview.export.internal.interfaces.InvokeObject;
import com.uc.webview.export.internal.setup.UCAsyncTask;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import com.uc.webview.export.internal.uc.startup.StartupTrace;
import com.uc.webview.export.internal.utility.ReflectionUtil;
import java.lang.reflect.Field;
import java.util.Map;

@Api
/* compiled from: ProGuard */
public class WebView extends FrameLayout implements IWebViewOverride {
    public static final int CORE_TYPE_ANDROID = 2;
    public static final int CORE_TYPE_U3 = 1;
    public static final int CORE_TYPE_U4 = 3;
    public static final int CREATE_FLAG_FORCE_USING_SYSTEM = 2;
    public static final int CREATE_FLAG_QUICK = 1;
    public static final int DEFAULT_CORE_TYPE = 1;
    public static final String SCHEME_GEO = "geo:0,0?q=";
    public static final String SCHEME_MAILTO = "mailto:";
    public static final String SCHEME_TEL = "tel:";
    public static int[] sInstanceCount = {0, 0, 0, 0};
    private WebSettings a;
    private com.uc.webview.export.internal.a b;
    private CommonExtension c;
    private UCExtension d;
    private boolean e;
    private a f;
    private int g;
    private WebViewCountting h;
    private boolean i;
    public IWebView mWebView;

    @Api
    /* compiled from: ProGuard */
    public interface FindListener {
        void onFindResultReceived(int i, int i2, boolean z);
    }

    @Api
    /* compiled from: ProGuard */
    public class HitTestResult {
        @Deprecated
        public static final int ANCHOR_TYPE = 1;
        public static final int EDIT_TEXT_TYPE = 9;
        public static final int EMAIL_TYPE = 4;
        public static final int GEO_TYPE = 3;
        @Deprecated
        public static final int IMAGE_ANCHOR_TYPE = 6;
        public static final int IMAGE_TYPE = 5;
        public static final int PHONE_TYPE = 2;
        public static final int SRC_ANCHOR_TYPE = 7;
        public static final int SRC_IMAGE_ANCHOR_TYPE = 8;
        public static final int UNKNOWN_TYPE = 0;
        private IHitTestResult a;

        /* synthetic */ HitTestResult(WebView webView, IHitTestResult iHitTestResult, byte b2) {
            this(iHitTestResult);
        }

        public HitTestResult() {
        }

        private HitTestResult(IHitTestResult iHitTestResult) {
            this.a = iHitTestResult;
        }

        public int getType() {
            return this.a.getType();
        }

        public String getExtra() {
            return this.a.getExtra();
        }
    }

    @Reflection
    /* compiled from: ProGuard */
    static class WebViewCountting {
        WebViewCountting() {
        }
    }

    @Api
    /* compiled from: ProGuard */
    public class WebViewTransport {
        private WebView b;

        public WebViewTransport() {
        }

        public synchronized void setWebView(WebView webView) {
            this.b = webView;
        }

        public synchronized WebView getWebView() {
            return this.b;
        }
    }

    /* compiled from: ProGuard */
    static class a extends MutableContextWrapper {
        WebView a;

        public a(Context context, WebView webView) {
            super(context);
            if (context == null) {
                throw new IllegalArgumentException("Base context can not be null.");
            }
            this.a = webView;
        }

        public final void setBaseContext(Context context) {
            super.setBaseContext(context);
            if (this.a != null) {
                WebView.a(this.a, this);
            }
        }
    }

    /* compiled from: ProGuard */
    public static abstract class b {
    }

    static /* synthetic */ void a(WebView webView, Context context) {
        if (webView.mWebView != null && webView.mWebView.getUCExtension() != null) {
            webView.mWebView.getUCExtension().invoke(25, new Object[]{context});
        }
    }

    public CommonExtension getCommonExtension() {
        return this.c;
    }

    public UCExtension getUCExtension() {
        return this.d;
    }

    public WebView(Context context) throws RuntimeException {
        this(context, null, 16842885, false, false);
    }

    public WebView(Context context, boolean z) throws RuntimeException {
        this(context, null, 16842885, z, false);
    }

    public WebView(Context context, int i2) {
        this(context, null, 16842885, (i2 & 2) == 2, (i2 & 1) == 1);
    }

    public WebView(Context context, AttributeSet attributeSet) throws RuntimeException {
        this(context, attributeSet, 16842885, false, false);
    }

    public WebView(Context context, AttributeSet attributeSet, boolean z) throws RuntimeException {
        this(context, attributeSet, 16842885, z, false);
    }

    public WebView(Context context, AttributeSet attributeSet, int i2) throws RuntimeException {
        this(context, attributeSet, i2, false, false);
    }

    public WebView(Context context, AttributeSet attributeSet, boolean z, int i2) throws RuntimeException {
        this(context, attributeSet, i2, z, false);
    }

    @Deprecated
    public WebView(Context context, AttributeSet attributeSet, int i2, boolean z) throws RuntimeException {
        this(context, attributeSet, i2, false, false);
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    private WebView(Context context, AttributeSet attributeSet, int i2, boolean z, boolean z2) throws RuntimeException {
        // Context context2 = context;
        // AttributeSet attributeSet2 = attributeSet;
        super(context, attributeSet, i2);
        this.a = null;
        this.b = null;
        this.e = true;
        this.h = new WebViewCountting();
        this.i = false;
        StartupTrace.a();
        if (context2 == null) {
            try {
                throw new IllegalArgumentException("Invalid context argument");
            } catch (Throwable th) {
                Throwable th2 = th;
                if (z2) {
                    SDKFactory.invoke(10061, Boolean.FALSE);
                    SDKFactory.invoke(10062, Boolean.FALSE);
                    SDKFactory.invoke(10063, Boolean.FALSE);
                }
                throw th2;
            }
        } else {
            if (z2) {
                SDKFactory.invoke(10061, Boolean.TRUE);
                SDKFactory.invoke(10062, Boolean.TRUE);
                SDKFactory.invoke(10063, Boolean.TRUE);
            }
            this.f = new a(context2, this);
            int[] iArr = new int[1];
            this.mWebView = (IWebView) SDKFactory.invoke(10012, this.f, attributeSet2, this, Boolean.valueOf(z), Boolean.valueOf(z2), iArr);
            this.mWebView.setOverrideObject(this);
            this.g = iArr[0];
            int[] iArr2 = sInstanceCount;
            int i3 = this.g;
            iArr2[i3] = iArr2[i3] + 1;
            this.b = (com.uc.webview.export.internal.a) SDKFactory.invoke(UCAsyncTask.getTaskCount, Integer.valueOf(this.g), this.f.getApplicationContext());
            this.a = this.mWebView.getSettingsInner();
            if (attributeSet2 == null) {
                addView(this.mWebView.getView(), new LayoutParams(-1, -1));
            } else if (((Boolean) SDKFactory.invoke(UCMPackageInfo.getVersion, new Object[0])).booleanValue()) {
                addView(this.mWebView.getView());
            } else {
                addView(this.mWebView.getView(), generateLayoutParams(attributeSet2));
            }
            this.c = new CommonExtension(this.mWebView);
            this.d = (UCExtension) SDKFactory.invoke(UCAsyncTask.getPriority, context2, this.mWebView, Integer.valueOf(this.g));
            if (this.d != null) {
                this.d.setPrivateBrowsing(false);
            }
            if (!SDKFactory.g) {
                setWillNotDraw(false);
            }
            if (this.g == 3 && (this.mWebView instanceof InvokeObject)) {
                ((InvokeObject) this.mWebView).invoke(9, null);
            }
            if (z2) {
                SDKFactory.invoke(10061, Boolean.FALSE);
                SDKFactory.invoke(10062, Boolean.FALSE);
                SDKFactory.invoke(10063, Boolean.FALSE);
            }
            StartupTrace.traceEventEnd("export.WebView.<init>");
        }
    }

    public void addJavascriptInterface(Object obj, String str) {
        a();
        this.mWebView.addJavascriptInterface(obj, str);
    }

    public boolean canGoBack() {
        a();
        return this.mWebView.canGoBack();
    }

    public boolean canGoBackOrForward(int i2) {
        a();
        return this.mWebView.canGoBackOrForward(i2);
    }

    public boolean canGoForward() {
        a();
        return this.mWebView.canGoForward();
    }

    public void clearCache(boolean z) {
        a();
        this.mWebView.clearCache(z);
    }

    public void clearFormData() {
        a();
        this.mWebView.clearFormData();
    }

    public void clearHistory() {
        a();
        this.mWebView.clearHistory();
    }

    public void clearMatches() {
        a();
        this.mWebView.clearMatches();
    }

    public void clearSslPreferences() {
        a();
        this.mWebView.clearSslPreferences();
    }

    public WebBackForwardList copyBackForwardList() {
        a();
        return this.mWebView.copyBackForwardListInner();
    }

    private void a() {
        if (this.mWebView == null) {
            throw new IllegalStateException("WebView had destroyed,forbid it's interfaces to be called.");
        }
    }

    public boolean isDestroied() {
        return this.i || this.mWebView == null;
    }

    public void destroy() {
        synchronized (this) {
            if (this.i) {
                throw new RuntimeException("destroy() already called.");
            }
            this.i = true;
            this.h = null;
        }
        this.mWebView.destroy();
        this.mWebView = null;
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        if (this.f != null) {
            a aVar = this.f;
            Context baseContext = aVar.getBaseContext();
            if (baseContext != null) {
                try {
                    Field declaredField = ContextWrapper.class.getDeclaredField("mBase");
                    if (declaredField != null) {
                        declaredField.setAccessible(true);
                        declaredField.set(aVar, baseContext.getApplicationContext());
                    }
                } catch (IllegalAccessException | NoSuchFieldException unused) {
                }
            }
            if (aVar.a != null) {
                aVar.a = null;
            }
            this.f = null;
        }
    }

    public void documentHasImages(Message message) {
        a();
        this.mWebView.documentHasImages(message);
    }

    public void findAllAsync(String str) {
        a();
        this.mWebView.findAllAsync(str);
    }

    public void findNext(boolean z) {
        a();
        this.mWebView.findNext(z);
    }

    public void setFindListener(FindListener findListener) {
        a();
        this.mWebView.setFindListener(findListener);
    }

    public static void enableSlowWholeDocumentDraw() {
        SDKFactory.invoke(10055, new Object[0]);
    }

    public void flingScroll(int i2, int i3) {
        a();
        this.mWebView.flingScroll(i2, i3);
    }

    public SslCertificate getCertificate() {
        a();
        return this.mWebView.getCertificate();
    }

    public int getContentHeight() {
        a();
        return this.mWebView.getContentHeight();
    }

    public Bitmap getFavicon() {
        a();
        return this.mWebView.getFavicon();
    }

    public HitTestResult getHitTestResult() {
        a();
        if (this.mWebView.getHitTestResultInner() != null) {
            return new HitTestResult(this, this.mWebView.getHitTestResultInner(), 0);
        }
        return null;
    }

    public String[] getHttpAuthUsernamePassword(String str, String str2) {
        a();
        return this.mWebView.getHttpAuthUsernamePassword(str, str2);
    }

    public String getOriginalUrl() {
        a();
        return this.mWebView.getOriginalUrl();
    }

    public int getProgress() {
        a();
        return this.mWebView.getProgress();
    }

    @Deprecated
    public float getScale() {
        a();
        return this.mWebView.getScale();
    }

    public String getTitle() {
        a();
        return this.mWebView.getTitle();
    }

    public String getUrl() {
        a();
        return this.mWebView.getUrl();
    }

    public void goBack() {
        a();
        this.mWebView.goBack();
    }

    public void goBackOrForward(int i2) {
        a();
        this.mWebView.goBackOrForward(i2);
    }

    public void goForward() {
        a();
        this.mWebView.goForward();
    }

    public void invokeZoomPicker() {
        a();
        this.mWebView.invokeZoomPicker();
    }

    public void loadData(String str, String str2, String str3) {
        StartupTrace.a();
        a();
        this.mWebView.loadData(str, str2, str3);
        StartupTrace.traceEventEnd("export.WebView.loadData");
    }

    public void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5) {
        StartupTrace.a();
        a();
        this.mWebView.loadDataWithBaseURL(str, str2, str3, str4, str5);
        StartupTrace.traceEventEnd("export.WebView.loadDataWithBaseURL");
    }

    public void loadUrl(String str) {
        StartupTrace.a();
        a();
        this.mWebView.loadUrl(str);
        StartupTrace.traceEventEnd("export.WebView.loadUrl");
    }

    public void loadUrl(String str, Map<String, String> map) {
        StartupTrace.a();
        a();
        this.mWebView.loadUrl(str, map);
        StartupTrace.traceEventEnd("export.WebView.loadUrlHeaders");
    }

    public void onPause() {
        a();
        this.mWebView.onPause();
    }

    public void onResume() {
        a();
        this.mWebView.onResume();
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        a();
        if (onLongClickListener != null) {
            this.mWebView.setOnLongClickListener(new a(this, onLongClickListener));
        } else {
            this.mWebView.setOnLongClickListener(null);
        }
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        a();
        if (onTouchListener != null) {
            this.mWebView.setOnTouchListener(new b(this, onTouchListener));
        } else {
            this.mWebView.setOnTouchListener(null);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean onInterceptTouchEvent = super.onInterceptTouchEvent(motionEvent);
        if (motionEvent == null || (motionEvent.getSource() & 8194) != 8194) {
            return onInterceptTouchEvent;
        }
        return false;
    }

    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    public void setOnKeyListener(OnKeyListener onKeyListener) {
        a();
        if (onKeyListener != null) {
            this.mWebView.setOnKeyListener(new c(this, onKeyListener));
        } else {
            this.mWebView.setOnKeyListener(null);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        a();
        return this.mWebView.dispatchKeyEvent(keyEvent);
    }

    public boolean isVerticalScrollBarEnabled() {
        a();
        return this.mWebView.isVerticalScrollBarEnabled();
    }

    public void setVerticalScrollBarEnabled(boolean z) {
        a();
        this.mWebView.setVerticalScrollBarEnabled(z);
    }

    public boolean isHorizontalScrollBarEnabled() {
        a();
        return this.mWebView.isHorizontalScrollBarEnabled();
    }

    public void setHorizontalScrollBarEnabled(boolean z) {
        a();
        this.mWebView.setHorizontalScrollBarEnabled(z);
    }

    public boolean overlayHorizontalScrollbar() {
        a();
        return this.mWebView.overlayHorizontalScrollbar();
    }

    public boolean overlayVerticalScrollbar() {
        a();
        return this.mWebView.overlayVerticalScrollbar();
    }

    public boolean pageDown(boolean z) {
        a();
        return this.mWebView.pageDown(z);
    }

    public boolean pageUp(boolean z) {
        a();
        return this.mWebView.pageUp(z);
    }

    public void postVisualStateCallback(long j, b bVar) {
        this.mWebView.postVisualStateCallback(j, bVar);
    }

    public void pauseTimers() {
        a();
        this.mWebView.pauseTimers();
    }

    public void postUrl(String str, byte[] bArr) {
        a();
        this.mWebView.postUrl(str, bArr);
    }

    public void reload() {
        a();
        this.mWebView.reload();
    }

    public void removeJavascriptInterface(String str) {
        a();
        try {
            this.mWebView.removeJavascriptInterface(str);
        } catch (Throwable unused) {
        }
    }

    public WebMessagePort[] createWebMessageChannel() {
        a();
        Object createWebMessageChannelInner = this.mWebView.createWebMessageChannelInner();
        if (createWebMessageChannelInner instanceof WebMessagePort[]) {
            return (WebMessagePort[]) createWebMessageChannelInner;
        }
        return null;
    }

    public void postWebMessage(WebMessage webMessage, Uri uri) {
        a();
        this.mWebView.postWebMessageInner(webMessage, uri);
    }

    public WebSettings getSettings() {
        a();
        return this.a;
    }

    public void requestFocusNodeHref(Message message) {
        a();
        this.mWebView.requestFocusNodeHref(message);
    }

    public void requestImageRef(Message message) {
        a();
        this.mWebView.requestImageRef(message);
    }

    public WebBackForwardList restoreState(Bundle bundle) {
        a();
        return this.mWebView.restoreStateInner(bundle);
    }

    public void resumeTimers() {
        a();
        this.mWebView.resumeTimers();
    }

    public WebBackForwardList saveState(Bundle bundle) {
        a();
        return this.mWebView.saveStateInner(bundle);
    }

    public void setBackgroundColor(int i2) {
        super.setBackgroundColor(i2);
        if (this.mWebView != null) {
            this.mWebView.setBackgroundColor(i2);
        }
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        a();
        this.mWebView.setDownloadListener(downloadListener);
    }

    public void setHttpAuthUsernamePassword(String str, String str2, String str3, String str4) {
        a();
        this.mWebView.setHttpAuthUsernamePassword(str, str2, str3, str4);
    }

    public void setInitialScale(int i2) {
        a();
        this.mWebView.setInitialScale(i2);
    }

    public void setNetworkAvailable(boolean z) {
        a();
        this.mWebView.setNetworkAvailable(z);
    }

    public final void setOverScrollMode(int i2) {
        if (this.mWebView != null && getCoreType() != 1) {
            this.mWebView.setOverScrollMode(i2);
        }
    }

    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
        if (getCoreView() != null && layoutParams.height < 0) {
            getCoreView().setLayoutParams(new LayoutParams(layoutParams));
        }
    }

    public void setVerticalScrollbarOverlay(boolean z) {
        a();
        this.mWebView.setVerticalScrollbarOverlay(z);
    }

    public void saveWebArchive(String str) {
        a();
        this.mWebView.saveWebArchive(str);
    }

    public void saveWebArchive(String str, boolean z, ValueCallback<String> valueCallback) {
        a();
        this.mWebView.saveWebArchive(str, z, valueCallback);
    }

    public void stopLoading() {
        a();
        this.mWebView.stopLoading();
    }

    public boolean zoomIn() {
        a();
        return this.mWebView.zoomIn();
    }

    public boolean zoomOut() {
        a();
        return this.mWebView.zoomOut();
    }

    public void zoomBy(float f2) {
        a();
        double d2 = (double) f2;
        if (d2 < 0.01d) {
            throw new IllegalArgumentException("zoomFactor must be greater than 0.01.");
        } else if (d2 > 100.0d) {
            throw new IllegalArgumentException("zoomFactor must be less than 100.");
        } else {
            if (getCoreType() != 2) {
                ((InvokeObject) this.mWebView).invoke(7, new Object[]{Float.valueOf(f2)});
            } else if (VERSION.SDK_INT >= 21) {
                try {
                    ReflectionUtil.invoke((Object) getCoreView(), (String) "zoomBy", new Class[]{Float.TYPE}, new Object[]{Float.valueOf(f2)});
                } catch (Exception unused) {
                }
            }
        }
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        a();
        this.mWebView.setWebViewClient(webViewClient);
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        a();
        this.mWebView.setWebChromeClient(webChromeClient);
    }

    public static void setWebContentsDebuggingEnabled(boolean z) {
        if (getCoreType() != 2) {
            UCCore.notifyCoreEvent(100, Boolean.valueOf(z));
        } else if (VERSION.SDK_INT >= 19) {
            try {
                android.webkit.WebView.setWebContentsDebuggingEnabled(z);
            } catch (Exception unused) {
            }
        }
    }

    @Deprecated
    public int findAll(String str) {
        a();
        return this.mWebView.findAll(str);
    }

    public void setScrollBarStyle(int i2) {
        if (this.mWebView != null) {
            this.mWebView.setScrollBarStyle(i2);
        }
        super.setScrollBarStyle(i2);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mWebView != null && this.b != null) {
            com.uc.webview.export.internal.a.a(this.mWebView);
        }
    }

    public void evaluateJavascript(String str, ValueCallback<String> valueCallback) {
        a();
        this.mWebView.evaluateJavascript(str, valueCallback);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mWebView != null && this.b != null) {
            this.b.b(this.mWebView);
        }
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int i2) {
        super.onWindowVisibilityChanged(i2);
        if (this.mWebView != null && this.b != null) {
            this.b.a(this.mWebView, i2);
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (this.mWebView != null && this.b != null) {
            this.b.a(i2, i3);
        }
    }

    @Deprecated
    public boolean canZoomIn() {
        a();
        try {
            return this.mWebView.canZoomIn();
        } catch (NoSuchMethodError unused) {
            return false;
        }
    }

    @Deprecated
    public boolean canZoomOut() {
        a();
        try {
            return this.mWebView.canZoomOut();
        } catch (NoSuchMethodError unused) {
            return false;
        }
    }

    @Deprecated
    public Picture capturePicture() {
        a();
        return this.mWebView.capturePicture();
    }

    public static int getCoreType() {
        return ((Integer) SDKFactory.invoke(SDKFactory.getCoreType, new Object[0])).intValue();
    }

    public int getCurrentViewCoreType() {
        return this.g;
    }

    public View getCoreView() {
        a();
        return this.mWebView.getView();
    }

    public boolean isPrivateBrowsingEnabled() {
        Boolean bool = (Boolean) ((InvokeObject) this.mWebView).invoke(8, null);
        return bool != null && bool.booleanValue();
    }

    public final void computeScroll() {
        super.computeScroll();
    }

    public void coreComputeScroll() {
        if (this.mWebView != null) {
            this.mWebView.superComputeScroll();
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void coreOnConfigurationChanged(Configuration configuration) {
        if (this.mWebView != null) {
            this.mWebView.superOnConfigurationChanged(configuration);
        }
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        if (this.mWebView != null) {
            return this.mWebView.onCreateInputConnection(editorInfo);
        }
        return null;
    }

    public final void onVisibilityChanged(View view, int i2) {
        super.onVisibilityChanged(view, i2);
    }

    public void coreOnVisibilityChanged(View view, int i2) {
        if (this.mWebView != null) {
            this.mWebView.superOnVisibilityChanged(view, i2);
        }
    }

    public final void onScrollChanged(int i2, int i3, int i4, int i5) {
        super.onScrollChanged(i2, i3, i4, i5);
    }

    public void coreOnScrollChanged(int i2, int i3, int i4, int i5) {
        if (this.mWebView != null) {
            this.mWebView.superOnScrollChanged(i2, i3, i4, i5);
        }
    }

    public boolean coreDispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mWebView != null) {
            return this.mWebView.superDispatchTouchEvent(motionEvent);
        }
        return false;
    }

    public final void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void coreDraw(Canvas canvas) {
        if (this.mWebView != null) {
            this.mWebView.superDraw(canvas);
        }
    }

    public void coreDestroy() {
        if (this.mWebView != null) {
            this.mWebView.superDestroy();
        }
    }

    public final void setVisibility(int i2) {
        super.setVisibility(i2);
    }

    public void coreSetVisibility(int i2) {
        if (this.mWebView != null) {
            this.mWebView.superSetVisibility(i2);
        }
    }

    public final void requestLayout() {
        super.requestLayout();
    }

    public void coreRequestLayout() {
        if (this.mWebView != null) {
            this.mWebView.superRequestLayout();
        }
    }

    public boolean overScrollBy(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z) {
        return super.overScrollBy(i2, i3, i4, i5, i6, i7, i8, i9, z);
    }

    public boolean coreOverScrollBy(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z) {
        if (this.mWebView != null) {
            return this.mWebView.superOverScrollBy(i2, i3, i4, i5, i6, i7, i8, i9, z);
        }
        return false;
    }

    public void coreOnInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (this.mWebView != null) {
            accessibilityNodeInfo.setClassName(WebView.class.getName());
            ((InvokeObject) this.mWebView).invoke(1, new Object[]{accessibilityNodeInfo});
        }
    }

    public void coreOnInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (this.mWebView != null) {
            accessibilityEvent.setClassName(WebView.class.getName());
            ((InvokeObject) this.mWebView).invoke(2, new Object[]{accessibilityEvent});
        }
    }

    public boolean corePerformAccessibilityAction(int i2, Bundle bundle) {
        if (this.mWebView == null) {
            return false;
        }
        return Boolean.parseBoolean(((InvokeObject) this.mWebView).invoke(3, new Object[]{Integer.valueOf(i2), bundle}).toString());
    }

    public CharSequence getAccessibilityClassName() {
        return WebView.class.getName();
    }

    public static <T extends WebView> void asyncNew(Class<T> cls, Class<?>[] clsArr, Object[] objArr, ValueCallback<Pair<T, Throwable>> valueCallback) {
        new Thread(new d(cls, clsArr, objArr, valueCallback)).start();
    }
}
