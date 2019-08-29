package com.alipay.sdk.auth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.text.TextUtils;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.sdk.util.l;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"SetJavaScriptEnabled", "DefaultLocale"})
public class AuthActivity extends Activity {
    static final String a = "params";
    static final String b = "redirectUri";
    /* access modifiers changed from: private */
    public WebView c;
    /* access modifiers changed from: private */
    public String d;
    private com.alipay.sdk.widget.a e;
    /* access modifiers changed from: private */
    public Handler f;
    /* access modifiers changed from: private */
    public boolean g;
    /* access modifiers changed from: private */
    public boolean h;
    /* access modifiers changed from: private */
    public Runnable i = new d(this);

    class a extends WebChromeClient {
        private a() {
        }

        /* synthetic */ a(AuthActivity authActivity, byte b) {
            this();
        }

        public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            String message = consoleMessage.message();
            if (TextUtils.isEmpty(message)) {
                return super.onConsoleMessage(consoleMessage);
            }
            String str = null;
            if (message.startsWith("h5container.message: ")) {
                str = message.replaceFirst("h5container.message: ", "");
            }
            if (TextUtils.isEmpty(str)) {
                return super.onConsoleMessage(consoleMessage);
            }
            AuthActivity.b(AuthActivity.this, str);
            return super.onConsoleMessage(consoleMessage);
        }
    }

    class b extends WebViewClient {
        private b() {
        }

        /* synthetic */ b(AuthActivity authActivity, byte b) {
            this();
        }

        public final void onReceivedError(WebView webView, int i, String str, String str2) {
            AuthActivity.this.h = true;
            super.onReceivedError(webView, i, str, str2);
        }

        public final void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (AuthActivity.this.g) {
                sslErrorHandler.proceed();
                AuthActivity.this.g = false;
                return;
            }
            AuthActivity.this.runOnUiThread(new e(this, sslErrorHandler));
        }

        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str.toLowerCase().startsWith(com.alipay.sdk.cons.a.i.toLowerCase()) || str.toLowerCase().startsWith(com.alipay.sdk.cons.a.j.toLowerCase())) {
                try {
                    com.alipay.sdk.util.l.a a2 = l.a((Context) AuthActivity.this);
                    if (a2 != null) {
                        if (!a2.a()) {
                            if (str.startsWith("intent://platformapi/startapp")) {
                                str = str.replaceFirst(com.alipay.sdk.cons.a.j, com.alipay.sdk.cons.a.i);
                            }
                            AuthActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                            return true;
                        }
                    }
                    return true;
                } catch (Throwable unused) {
                }
            } else if (!AuthActivity.a(AuthActivity.this, str)) {
                return super.shouldOverrideUrlLoading(webView, str);
            } else {
                webView.stopLoading();
                return true;
            }
        }

        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            AuthActivity.d(AuthActivity.this);
            AuthActivity.this.f.postDelayed(AuthActivity.this.i, StatisticConfig.MIN_UPLOAD_INTERVAL);
            super.onPageStarted(webView, str, bitmap);
        }

        public final void onPageFinished(WebView webView, String str) {
            AuthActivity.g(AuthActivity.this);
            AuthActivity.this.f.removeCallbacks(AuthActivity.this.i);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't wrap try/catch for region: R(8:13|(3:15|16|(1:18))|19|20|(3:21|22|(1:24))|25|(1:28)|29) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x00fa */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0111 */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0122 A[Catch:{ Throwable -> 0x0144 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x014a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r6) {
        /*
            r5 = this;
            super.onCreate(r6)
            android.content.Intent r6 = r5.getIntent()     // Catch:{ Exception -> 0x0158 }
            android.os.Bundle r6 = r6.getExtras()     // Catch:{ Exception -> 0x0158 }
            if (r6 != 0) goto L_0x0011
            r5.finish()
            return
        L_0x0011:
            java.lang.String r0 = "redirectUri"
            java.lang.String r0 = r6.getString(r0)     // Catch:{ Exception -> 0x0154 }
            r5.d = r0     // Catch:{ Exception -> 0x0154 }
            java.lang.String r0 = "params"
            java.lang.String r6 = r6.getString(r0)     // Catch:{ Exception -> 0x0154 }
            boolean r0 = com.alipay.sdk.util.l.b(r6)
            if (r0 != 0) goto L_0x0029
            r5.finish()
            return
        L_0x0029:
            r0 = 1
            super.requestWindowFeature(r0)
            android.os.Handler r1 = new android.os.Handler
            android.os.Looper r2 = r5.getMainLooper()
            r1.<init>(r2)
            r5.f = r1
            android.widget.LinearLayout r1 = new android.widget.LinearLayout
            r1.<init>(r5)
            android.widget.LinearLayout$LayoutParams r2 = new android.widget.LinearLayout$LayoutParams
            r3 = -1
            r2.<init>(r3, r3)
            r1.setOrientation(r0)
            r5.setContentView(r1, r2)
            android.webkit.WebView r3 = new android.webkit.WebView
            r3.<init>(r5)
            r5.c = r3
            r3 = 1065353216(0x3f800000, float:1.0)
            r2.weight = r3
            android.webkit.WebView r3 = r5.c
            r4 = 0
            r3.setVisibility(r4)
            android.webkit.WebView r3 = r5.c
            r1.addView(r3, r2)
            android.webkit.WebView r1 = r5.c
            android.webkit.WebSettings r1 = r1.getSettings()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r1.getUserAgentString()
            r2.append(r3)
            android.content.Context r3 = r5.getApplicationContext()
            java.lang.String r3 = com.alipay.sdk.util.l.e(r3)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.setUserAgentString(r2)
            android.webkit.WebSettings$RenderPriority r2 = android.webkit.WebSettings.RenderPriority.HIGH
            r1.setRenderPriority(r2)
            r1.setSupportMultipleWindows(r0)
            r1.setJavaScriptEnabled(r0)
            r1.setSavePassword(r4)
            r1.setJavaScriptCanOpenWindowsAutomatically(r0)
            int r2 = r1.getMinimumFontSize()
            int r2 = r2 + 8
            r1.setMinimumFontSize(r2)
            r1.setAllowFileAccess(r4)
            android.webkit.WebSettings$TextSize r2 = android.webkit.WebSettings.TextSize.NORMAL
            r1.setTextSize(r2)
            android.webkit.WebView r1 = r5.c
            r1.setVerticalScrollbarOverlay(r0)
            android.webkit.WebView r1 = r5.c
            com.alipay.sdk.auth.AuthActivity$b r2 = new com.alipay.sdk.auth.AuthActivity$b
            r2.<init>(r5, r4)
            r1.setWebViewClient(r2)
            android.webkit.WebView r1 = r5.c
            com.alipay.sdk.auth.AuthActivity$a r2 = new com.alipay.sdk.auth.AuthActivity$a
            r2.<init>(r5, r4)
            r1.setWebChromeClient(r2)
            android.webkit.WebView r1 = r5.c
            com.alipay.sdk.auth.a r2 = new com.alipay.sdk.auth.a
            r2.<init>(r5)
            r1.setDownloadListener(r2)
            android.webkit.WebView r1 = r5.c
            r1.loadUrl(r6)
            int r6 = android.os.Build.VERSION.SDK_INT
            r1 = 7
            if (r6 < r1) goto L_0x00fa
            android.webkit.WebView r6 = r5.c     // Catch:{ Exception -> 0x00fa }
            android.webkit.WebSettings r6 = r6.getSettings()     // Catch:{ Exception -> 0x00fa }
            java.lang.Class r6 = r6.getClass()     // Catch:{ Exception -> 0x00fa }
            java.lang.String r1 = "setDomStorageEnabled"
            java.lang.Class[] r2 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x00fa }
            java.lang.Class r3 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x00fa }
            r2[r4] = r3     // Catch:{ Exception -> 0x00fa }
            java.lang.reflect.Method r6 = r6.getMethod(r1, r2)     // Catch:{ Exception -> 0x00fa }
            if (r6 == 0) goto L_0x00fa
            android.webkit.WebView r1 = r5.c     // Catch:{ Exception -> 0x00fa }
            android.webkit.WebSettings r1 = r1.getSettings()     // Catch:{ Exception -> 0x00fa }
            java.lang.Object[] r2 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x00fa }
            java.lang.Boolean r3 = java.lang.Boolean.TRUE     // Catch:{ Exception -> 0x00fa }
            r2[r4] = r3     // Catch:{ Exception -> 0x00fa }
            r6.invoke(r1, r2)     // Catch:{ Exception -> 0x00fa }
        L_0x00fa:
            android.webkit.WebView r6 = r5.c     // Catch:{ Throwable -> 0x0111 }
            java.lang.String r1 = "searchBoxJavaBridge_"
            r6.removeJavascriptInterface(r1)     // Catch:{ Throwable -> 0x0111 }
            android.webkit.WebView r6 = r5.c     // Catch:{ Throwable -> 0x0111 }
            java.lang.String r1 = "accessibility"
            r6.removeJavascriptInterface(r1)     // Catch:{ Throwable -> 0x0111 }
            android.webkit.WebView r6 = r5.c     // Catch:{ Throwable -> 0x0111 }
            java.lang.String r1 = "accessibilityTraversal"
            r6.removeJavascriptInterface(r1)     // Catch:{ Throwable -> 0x0111 }
            goto L_0x0144
        L_0x0111:
            android.webkit.WebView r6 = r5.c     // Catch:{ Throwable -> 0x0144 }
            java.lang.Class r6 = r6.getClass()     // Catch:{ Throwable -> 0x0144 }
            java.lang.String r1 = "removeJavascriptInterface"
            java.lang.Class[] r2 = new java.lang.Class[r4]     // Catch:{ Throwable -> 0x0144 }
            java.lang.reflect.Method r6 = r6.getMethod(r1, r2)     // Catch:{ Throwable -> 0x0144 }
            if (r6 == 0) goto L_0x0144
            android.webkit.WebView r1 = r5.c     // Catch:{ Throwable -> 0x0144 }
            java.lang.Object[] r2 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x0144 }
            java.lang.String r3 = "searchBoxJavaBridge_"
            r2[r4] = r3     // Catch:{ Throwable -> 0x0144 }
            r6.invoke(r1, r2)     // Catch:{ Throwable -> 0x0144 }
            android.webkit.WebView r1 = r5.c     // Catch:{ Throwable -> 0x0144 }
            java.lang.Object[] r2 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x0144 }
            java.lang.String r3 = "accessibility"
            r2[r4] = r3     // Catch:{ Throwable -> 0x0144 }
            r6.invoke(r1, r2)     // Catch:{ Throwable -> 0x0144 }
            android.webkit.WebView r1 = r5.c     // Catch:{ Throwable -> 0x0144 }
            java.lang.Object[] r2 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x0144 }
            java.lang.String r3 = "accessibilityTraversal"
            r2[r4] = r3     // Catch:{ Throwable -> 0x0144 }
            r6.invoke(r1, r2)     // Catch:{ Throwable -> 0x0144 }
        L_0x0144:
            int r6 = android.os.Build.VERSION.SDK_INT
            r1 = 19
            if (r6 < r1) goto L_0x0153
            android.webkit.WebView r6 = r5.c
            android.webkit.WebSettings r6 = r6.getSettings()
            r6.setCacheMode(r0)
        L_0x0153:
            return
        L_0x0154:
            r5.finish()
            return
        L_0x0158:
            r5.finish()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.auth.AuthActivity.onCreate(android.os.Bundle):void");
    }

    public void onBackPressed() {
        if (!this.c.canGoBack()) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.d);
            sb.append("?resultCode=150");
            h.a((Activity) this, sb.toString());
            finish();
        } else if (this.h) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.d);
            sb2.append("?resultCode=150");
            h.a((Activity) this, sb2.toString());
            finish();
        }
    }

    private boolean a(String str) {
        if (TextUtils.isEmpty(str) || str.startsWith(AjxHttpLoader.DOMAIN_HTTP) || str.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
            return false;
        }
        if (!"SDKLite://h5quit".equalsIgnoreCase(str)) {
            if (TextUtils.equals(str, this.d)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("?resultCode=150");
                str = sb.toString();
            }
            h.a((Activity) this, str);
        }
        finish();
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0054 A[SYNTHETIC, Splitter:B:17:0x0054] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.lang.String r7) {
        /*
            r6 = this;
            com.alipay.sdk.authjs.d r0 = new com.alipay.sdk.authjs.d
            android.content.Context r1 = r6.getApplicationContext()
            com.alipay.sdk.auth.b r2 = new com.alipay.sdk.auth.b
            r2.<init>(r6)
            r0.<init>(r1, r2)
            r1 = 0
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x004d }
            r2.<init>(r7)     // Catch:{ Exception -> 0x004d }
            java.lang.String r7 = "clientId"
            java.lang.String r7 = r2.getString(r7)     // Catch:{ Exception -> 0x004d }
            boolean r3 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Exception -> 0x004e }
            if (r3 == 0) goto L_0x0021
            return
        L_0x0021:
            java.lang.String r3 = "param"
            org.json.JSONObject r3 = r2.getJSONObject(r3)     // Catch:{ Exception -> 0x004e }
            boolean r4 = r3 instanceof org.json.JSONObject     // Catch:{ Exception -> 0x004e }
            if (r4 == 0) goto L_0x002e
            r1 = r3
            org.json.JSONObject r1 = (org.json.JSONObject) r1     // Catch:{ Exception -> 0x004e }
        L_0x002e:
            java.lang.String r3 = "func"
            java.lang.String r3 = r2.getString(r3)     // Catch:{ Exception -> 0x004e }
            java.lang.String r4 = "bundleName"
            java.lang.String r2 = r2.getString(r4)     // Catch:{ Exception -> 0x004e }
            com.alipay.sdk.authjs.a r4 = new com.alipay.sdk.authjs.a     // Catch:{ Exception -> 0x004e }
            java.lang.String r5 = "call"
            r4.<init>(r5)     // Catch:{ Exception -> 0x004e }
            r4.j = r2     // Catch:{ Exception -> 0x004e }
            r4.k = r3     // Catch:{ Exception -> 0x004e }
            r4.m = r1     // Catch:{ Exception -> 0x004e }
            r4.i = r7     // Catch:{ Exception -> 0x004e }
            r0.a(r4)     // Catch:{ Exception -> 0x004e }
            return
        L_0x004d:
            r7 = r1
        L_0x004e:
            boolean r1 = android.text.TextUtils.isEmpty(r7)
            if (r1 != 0) goto L_0x005a
            int r1 = com.alipay.sdk.authjs.a.C0005a.d     // Catch:{ JSONException -> 0x005a }
            r0.a(r7, r1)     // Catch:{ JSONException -> 0x005a }
            return
        L_0x005a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.auth.AuthActivity.b(java.lang.String):void");
    }

    private void a(com.alipay.sdk.authjs.a aVar) {
        if (this.c != null && aVar != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("clientId", aVar.i);
                jSONObject.put("func", aVar.k);
                jSONObject.put("param", aVar.m);
                jSONObject.put("msgType", aVar.l);
                runOnUiThread(new c(this, String.format("AlipayJSBridge._invokeJS(%s)", new Object[]{jSONObject.toString()})));
            } catch (JSONException unused) {
            }
        }
    }

    private void a() {
        try {
            if (this.e == null) {
                this.e = new com.alipay.sdk.widget.a(this, com.alipay.sdk.widget.a.a);
            }
            this.e.a();
        } catch (Exception unused) {
            this.e = null;
        }
    }

    private void b() {
        if (this.e != null) {
            this.e.b();
        }
        this.e = null;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.c != null) {
            this.c.removeAllViews();
            try {
                this.c.destroy();
            } catch (Throwable unused) {
            }
            this.c = null;
        }
    }

    static /* synthetic */ boolean a(AuthActivity authActivity, String str) {
        if (TextUtils.isEmpty(str) || str.startsWith(AjxHttpLoader.DOMAIN_HTTP) || str.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
            return false;
        }
        if (!"SDKLite://h5quit".equalsIgnoreCase(str)) {
            if (TextUtils.equals(str, authActivity.d)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("?resultCode=150");
                str = sb.toString();
            }
            h.a((Activity) authActivity, str);
        }
        authActivity.finish();
        return true;
    }

    static /* synthetic */ void d(AuthActivity authActivity) {
        try {
            if (authActivity.e == null) {
                authActivity.e = new com.alipay.sdk.widget.a(authActivity, com.alipay.sdk.widget.a.a);
            }
            authActivity.e.a();
        } catch (Exception unused) {
            authActivity.e = null;
        }
    }

    static /* synthetic */ void g(AuthActivity authActivity) {
        if (authActivity.e != null) {
            authActivity.e.b();
        }
        authActivity.e = null;
    }

    /* JADX WARNING: type inference failed for: r5v1 */
    /* JADX WARNING: type inference failed for: r5v4, types: [org.json.JSONObject] */
    /* JADX WARNING: type inference failed for: r5v6, types: [org.json.JSONObject] */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0054 A[SYNTHETIC, Splitter:B:17:0x0054] */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void b(com.alipay.sdk.auth.AuthActivity r5, java.lang.String r6) {
        /*
            com.alipay.sdk.authjs.d r0 = new com.alipay.sdk.authjs.d
            android.content.Context r1 = r5.getApplicationContext()
            com.alipay.sdk.auth.b r2 = new com.alipay.sdk.auth.b
            r2.<init>(r5)
            r0.<init>(r1, r2)
            r5 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x004e }
            r1.<init>(r6)     // Catch:{ Exception -> 0x004e }
            java.lang.String r6 = "clientId"
            java.lang.String r6 = r1.getString(r6)     // Catch:{ Exception -> 0x004e }
            boolean r2 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x004d }
            if (r2 == 0) goto L_0x0021
            return
        L_0x0021:
            java.lang.String r2 = "param"
            org.json.JSONObject r2 = r1.getJSONObject(r2)     // Catch:{ Exception -> 0x004d }
            boolean r3 = r2 instanceof org.json.JSONObject     // Catch:{ Exception -> 0x004d }
            if (r3 == 0) goto L_0x002e
            r5 = r2
            org.json.JSONObject r5 = (org.json.JSONObject) r5     // Catch:{ Exception -> 0x004d }
        L_0x002e:
            java.lang.String r2 = "func"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x004d }
            java.lang.String r3 = "bundleName"
            java.lang.String r1 = r1.getString(r3)     // Catch:{ Exception -> 0x004d }
            com.alipay.sdk.authjs.a r3 = new com.alipay.sdk.authjs.a     // Catch:{ Exception -> 0x004d }
            java.lang.String r4 = "call"
            r3.<init>(r4)     // Catch:{ Exception -> 0x004d }
            r3.j = r1     // Catch:{ Exception -> 0x004d }
            r3.k = r2     // Catch:{ Exception -> 0x004d }
            r3.m = r5     // Catch:{ Exception -> 0x004d }
            r3.i = r6     // Catch:{ Exception -> 0x004d }
            r0.a(r3)     // Catch:{ Exception -> 0x004d }
            return
        L_0x004d:
            r5 = r6
        L_0x004e:
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 != 0) goto L_0x005a
            int r6 = com.alipay.sdk.authjs.a.C0005a.d     // Catch:{ JSONException -> 0x005a }
            r0.a(r5, r6)     // Catch:{ JSONException -> 0x005a }
            return
        L_0x005a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.auth.AuthActivity.b(com.alipay.sdk.auth.AuthActivity, java.lang.String):void");
    }

    static /* synthetic */ void a(AuthActivity authActivity, com.alipay.sdk.authjs.a aVar) {
        if (authActivity.c != null && aVar != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("clientId", aVar.i);
                jSONObject.put("func", aVar.k);
                jSONObject.put("param", aVar.m);
                jSONObject.put("msgType", aVar.l);
                authActivity.runOnUiThread(new c(authActivity, String.format("AlipayJSBridge._invokeJS(%s)", new Object[]{jSONObject.toString()})));
            } catch (JSONException unused) {
            }
        }
    }
}
