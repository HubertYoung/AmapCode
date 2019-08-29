package com.autonavi.widget.webview.inner;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.sdk.util.h;
import com.uc.webview.export.DownloadListener;
import com.uc.webview.export.HttpAuthHandler;
import com.uc.webview.export.JsPromptResult;
import com.uc.webview.export.SslErrorHandler;
import com.uc.webview.export.WebChromeClient;
import com.uc.webview.export.WebChromeClient.CustomViewCallback;
import com.uc.webview.export.WebView;
import com.uc.webview.export.WebViewClient;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

public class SafeWebView extends WebView {
    private static final boolean DEBUG = true;
    private static final String KEY_ARG_ARRAY = "args";
    private static final String KEY_FUNCTION_NAME = "func";
    private static final String KEY_INTERFACE_NAME = "obj";
    private static final String VAR_ARG_PREFIX = "arg";
    private static final String[] mFilterMethods = {"getClass", "hashCode", "notify", "notifyAll", "equals", ProcessInfo.SR_TO_STRING, "wait"};
    private WebChromeClient mCurrentWebChromeClient;
    private WebViewClient mCurrentWebViewClient;
    private HashMap<String, Object> mJsInterfaceMap = new HashMap<>();
    private String mJsStringCache = null;
    private erx mWebChromeClientDispather;
    private ery mWebViewClientDispatcher;

    class a extends WebChromeClient {
        public final void onShowCustomView(View view, CustomViewCallback customViewCallback) {
        }

        private a() {
        }

        /* synthetic */ a(SafeWebView safeWebView, byte b) {
            this();
        }

        public final void onProgressChanged(WebView webView, int i) {
            if ((webView instanceof SafeWebView) && !SafeWebView.hasJellyBeanMR1()) {
                ((SafeWebView) webView).injectJavascriptInterfaces(webView);
            }
            super.onProgressChanged(webView, i);
        }

        public final boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
            if (!(webView instanceof SafeWebView) || SafeWebView.hasJellyBeanMR1() || !((SafeWebView) webView).handleJsInterface(webView, str, str2, str3, jsPromptResult)) {
                return super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
            }
            return true;
        }

        public final void onReceivedTitle(WebView webView, String str) {
            if ((webView instanceof SafeWebView) && !SafeWebView.hasJellyBeanMR1()) {
                ((SafeWebView) webView).injectJavascriptInterfaces(webView);
            }
        }
    }

    public static class b extends WebViewClient {
        public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        }

        public void onLoadResource(WebView webView, String str) {
            if ((webView instanceof SafeWebView) && !SafeWebView.hasJellyBeanMR1()) {
                ((SafeWebView) webView).injectJavascriptInterfaces(webView);
            }
            super.onLoadResource(webView, str);
        }

        public void doUpdateVisitedHistory(WebView webView, String str, boolean z) {
            if ((webView instanceof SafeWebView) && !SafeWebView.hasJellyBeanMR1()) {
                ((SafeWebView) webView).injectJavascriptInterfaces(webView);
            }
            super.doUpdateVisitedHistory(webView, str, z);
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            if ((webView instanceof SafeWebView) && !SafeWebView.hasJellyBeanMR1()) {
                ((SafeWebView) webView).injectJavascriptInterfaces(webView);
            }
            super.onPageStarted(webView, str, bitmap);
        }

        public void onPageFinished(WebView webView, String str) {
            if ((webView instanceof SafeWebView) && !SafeWebView.hasJellyBeanMR1()) {
                ((SafeWebView) webView).injectJavascriptInterfaces(webView);
            }
            super.onPageFinished(webView, str);
        }
    }

    static {
        erv.a();
    }

    public SafeWebView(Context context) {
        super(context);
        init();
    }

    public SafeWebView(Context context, AttributeSet attributeSet) throws RuntimeException {
        super(context, attributeSet);
        init();
    }

    public SafeWebView(Context context, AttributeSet attributeSet, int i) throws RuntimeException {
        super(context, attributeSet, i);
        init();
    }

    public void loadUrl(String str) {
        if (!isDestroyed() && str != null) {
            try {
                super.loadUrl(str);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void init() {
        erv.a(this);
        this.mWebChromeClientDispather = new erx();
        this.mWebViewClientDispatcher = new ery();
        this.mWebChromeClientDispather.a(new a(this, 0));
        this.mWebViewClientDispatcher.a(new b());
        super.setWebChromeClient(this.mWebChromeClientDispather);
        super.setWebViewClient(this.mWebViewClientDispatcher);
        removeSearchBoxImpl();
        if (VERSION.SDK_INT >= 21) {
            getSettings().setMixedContentMode(0);
        }
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        if (!isDestroyed()) {
            if (this.mCurrentWebViewClient != null) {
                this.mWebViewClientDispatcher.b(this.mCurrentWebViewClient);
            }
            this.mCurrentWebViewClient = webViewClient;
            if (this.mCurrentWebViewClient != null) {
                this.mWebViewClientDispatcher.a(this.mCurrentWebViewClient);
            }
        }
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        if (!isDestroyed()) {
            if (this.mCurrentWebChromeClient != null) {
                this.mWebChromeClientDispather.b(this.mCurrentWebChromeClient);
            }
            this.mCurrentWebChromeClient = webChromeClient;
            if (this.mCurrentWebChromeClient != null) {
                this.mWebChromeClientDispather.a(this.mCurrentWebChromeClient);
            }
        }
    }

    public void addJavascriptInterface(Object obj, String str) {
        if (isDestroyed() || TextUtils.isEmpty(str)) {
            return;
        }
        if (hasJellyBeanMR1()) {
            try {
                super.addJavascriptInterface(obj, str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.mJsInterfaceMap.put(str, obj);
        }
    }

    public void removeJavascriptInterface(String str) {
        if (!isDestroyed()) {
            if (hasJellyBeanMR1()) {
                super.removeJavascriptInterface(str);
                return;
            }
            this.mJsInterfaceMap.remove(str);
            this.mJsStringCache = null;
            injectJavascriptInterfaces();
        }
    }

    public void destroy() {
        try {
            ViewParent parent = getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(this);
            }
            stopLoading();
            getSettings().setJavaScriptEnabled(false);
            clearView();
            removeAllViews();
            super.destroy();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void clearView() {
        if (!isDestroyed()) {
            loadUrl("about:blank");
        }
    }

    private boolean isDestroyed() {
        boolean isDestroied = super.isDestroied();
        if (isDestroied) {
            new IllegalStateException("WebView had destroyed,forbid it's interfaces to be called.").printStackTrace();
        }
        return isDestroied;
    }

    private boolean removeSearchBoxImpl() {
        if (!hasHoneycomb() || hasJellyBeanMR1()) {
            return false;
        }
        super.removeJavascriptInterface("searchBoxJavaBridge_");
        return true;
    }

    private void injectJavascriptInterfaces() {
        if (!TextUtils.isEmpty(this.mJsStringCache)) {
            loadJavascriptInterfaces();
            return;
        }
        this.mJsStringCache = genJavascriptInterfacesString();
        loadJavascriptInterfaces();
    }

    /* access modifiers changed from: private */
    public void injectJavascriptInterfaces(WebView webView) {
        if (webView instanceof SafeWebView) {
            injectJavascriptInterfaces();
        }
    }

    private void loadJavascriptInterfaces() {
        if (VERSION.SDK_INT >= 19) {
            evaluateJavascript(this.mJsStringCache, null);
        } else {
            loadUrl(this.mJsStringCache);
        }
    }

    private String genJavascriptInterfacesString() {
        if (this.mJsInterfaceMap.size() == 0) {
            this.mJsStringCache = null;
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("javascript:(function JsAddJavascriptInterface_(){");
        for (Entry next : this.mJsInterfaceMap.entrySet()) {
            try {
                createJsMethod((String) next.getKey(), next.getValue(), sb);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sb.append("})()");
        return sb.toString();
    }

    private void createJsMethod(String str, Object obj, StringBuilder sb) {
        Method[] methods;
        if (!TextUtils.isEmpty(str) && obj != null && sb != null) {
            Class<?> cls = obj.getClass();
            sb.append("if(typeof(window.");
            sb.append(str);
            sb.append(")!='undefined'){");
            StringBuilder sb2 = new StringBuilder("    console.log('window.");
            sb2.append(str);
            sb2.append("_js_interface_name is exist!!');");
            sb.append(sb2.toString());
            sb.append("}else {");
            sb.append("    window.");
            sb.append(str);
            sb.append("={");
            for (Method method : cls.getMethods()) {
                String name = method.getName();
                if (!filterMethods(name)) {
                    sb.append("        ");
                    sb.append(name);
                    sb.append(":function(");
                    int length = method.getParameterTypes().length;
                    if (length > 0) {
                        int i = length - 1;
                        for (int i2 = 0; i2 < i; i2++) {
                            sb.append(VAR_ARG_PREFIX);
                            sb.append(i2);
                            sb.append(",");
                        }
                        sb.append(VAR_ARG_PREFIX);
                        sb.append(i);
                    }
                    sb.append(") {");
                    if (method.getReturnType() != Void.TYPE) {
                        sb.append("            return prompt(");
                    } else {
                        sb.append("            prompt(");
                    }
                    sb.append("JSON.stringify({");
                    sb.append("obj:'");
                    sb.append(str);
                    sb.append("',");
                    sb.append("func:'");
                    sb.append(name);
                    sb.append("',");
                    sb.append("args:[");
                    if (length > 0) {
                        int i3 = length - 1;
                        for (int i4 = 0; i4 < i3; i4++) {
                            sb.append(VAR_ARG_PREFIX);
                            sb.append(i4);
                            sb.append(",");
                        }
                        sb.append(VAR_ARG_PREFIX);
                        sb.append(i3);
                    }
                    sb.append("]})");
                    sb.append(");");
                    sb.append("        }, ");
                }
            }
            sb.append("    };");
            sb.append(h.d);
        }
    }

    /* access modifiers changed from: private */
    public boolean handleJsInterface(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        try {
            JSONObject jSONObject = new JSONObject(str2);
            String string = jSONObject.getString(KEY_INTERFACE_NAME);
            String string2 = jSONObject.getString("func");
            JSONArray jSONArray = jSONObject.getJSONArray(KEY_ARG_ARRAY);
            Object[] objArr = null;
            if (jSONArray != null) {
                int length = jSONArray.length();
                if (length > 0) {
                    objArr = new Object[length];
                    for (int i = 0; i < length; i++) {
                        Object obj = jSONArray.get(i);
                        if (obj instanceof Number) {
                            objArr[i] = String.valueOf(obj);
                        } else {
                            objArr[i] = obj;
                        }
                    }
                }
            }
            if (invokeJSInterfaceMethod(jsPromptResult, string, string2, objArr)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsPromptResult.cancel();
        return false;
    }

    private boolean invokeJSInterfaceMethod(JsPromptResult jsPromptResult, String str, String str2, Object[] objArr) {
        String str3;
        Object obj = this.mJsInterfaceMap.get(str);
        boolean z = false;
        if (obj == null) {
            jsPromptResult.cancel();
            return false;
        }
        Class<String[]>[] clsArr = null;
        int length = objArr != 0 ? objArr.length : 0;
        if (length > 0) {
            clsArr = new Class[length];
            for (int i = 0; i < length; i++) {
                clsArr[i] = getClassFromJsonObject(objArr[i]);
                if (clsArr[i] == String[].class && (objArr[i] instanceof JSONArray)) {
                    try {
                        JSONArray jSONArray = (JSONArray) objArr[i];
                        String[] strArr = new String[jSONArray.length()];
                        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                            strArr[i2] = jSONArray.opt(i2).toString();
                        }
                        objArr[i] = strArr;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            Object invoke = obj.getClass().getMethod(str2, clsArr).invoke(obj, objArr);
            if (invoke == null || invoke.getClass() == Void.TYPE) {
                z = true;
            }
            if (z) {
                str3 = "";
            } else {
                str3 = invoke.toString();
            }
            jsPromptResult.confirm(str3);
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        jsPromptResult.cancel();
        return true;
    }

    private Class<?> getClassFromJsonObject(Object obj) {
        Class<?> cls = obj.getClass();
        if (cls == Boolean.class) {
            return Boolean.TYPE;
        }
        if (cls == JSONArray.class) {
            return String[].class;
        }
        return String.class;
    }

    private boolean filterMethods(String str) {
        for (String equals : mFilterMethods) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasHoneycomb() {
        return VERSION.SDK_INT >= 11;
    }

    /* access modifiers changed from: private */
    public static boolean hasJellyBeanMR1() {
        return VERSION.SDK_INT >= 19;
    }

    public void loadUrl(String str, Map<String, String> map) {
        if (!isDestroyed()) {
            super.loadUrl(str, map);
        }
    }

    public void loadData(String str, String str2, String str3) {
        if (!isDestroyed()) {
            super.loadData(str, str2, str3);
        }
    }

    public void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5) {
        if (!isDestroyed()) {
            super.loadDataWithBaseURL(str, str2, str3, str4, str5);
        }
    }

    public boolean canGoBack() {
        if (isDestroyed()) {
            return false;
        }
        return super.canGoBack();
    }

    public boolean canGoBackOrForward(int i) {
        if (isDestroyed()) {
            return false;
        }
        return canGoBackOrForward(i);
    }

    public boolean canGoForward() {
        if (isDestroyed()) {
            return false;
        }
        return super.canGoForward();
    }

    public void clearCache(boolean z) {
        if (!isDestroyed()) {
            super.clearCache(z);
        }
    }

    public void clearFormData() {
        if (!isDestroyed()) {
            super.clearFormData();
        }
    }

    public void clearHistory() {
        if (!isDestroyed()) {
            super.clearHistory();
        }
    }

    public String getOriginalUrl() {
        if (isDestroyed()) {
            return "";
        }
        return super.getOriginalUrl();
    }

    public int getProgress() {
        if (isDestroyed()) {
            return 0;
        }
        return super.getProgress();
    }

    public float getScale() {
        if (isDestroyed()) {
            return 0.0f;
        }
        return super.getScaleX();
    }

    public String getTitle() {
        if (isDestroyed()) {
            return "";
        }
        return super.getTitle();
    }

    public String getUrl() {
        if (isDestroyed()) {
            return "";
        }
        return super.getUrl();
    }

    public void goBack() {
        if (!isDestroyed()) {
            super.goBack();
        }
    }

    public void goBackOrForward(int i) {
        if (!isDestroyed()) {
            super.goBackOrForward(i);
        }
    }

    public void goForward() {
        if (!isDestroyed()) {
            super.goForward();
        }
    }

    public void onPause() {
        if (!isDestroyed()) {
            super.onPause();
        }
    }

    public void onResume() {
        if (!isDestroyed()) {
            super.onResume();
        }
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        if (!isDestroyed()) {
            super.setOnLongClickListener(onLongClickListener);
        }
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        if (!isDestroyed()) {
            super.setOnTouchListener(onTouchListener);
        }
    }

    public void reload() {
        if (!isDestroyed()) {
            super.reload();
        }
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        if (!isDestroyed()) {
            super.setDownloadListener(downloadListener);
        }
    }

    public void stopLoading() {
        if (!isDestroyed()) {
            super.stopLoading();
        }
    }
}
