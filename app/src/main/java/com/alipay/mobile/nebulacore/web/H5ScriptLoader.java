package com.alipay.mobile.nebulacore.web;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.WebViewType;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.worker.remotedebug.RemoteDebugConstants;
import com.alipay.sdk.util.h;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class H5ScriptLoader {
    public static final String TAG = "H5ScriptLoader";
    private static Boolean k = null;
    private static JSONArray l = null;
    public static final String startupParams = "startupParams";
    private Object a;
    private boolean b;
    public boolean bizLoaded = false;
    public boolean bridgeLoaded = false;
    private HashMap<String, String> c;
    private H5WebView d;
    private String e;
    private String f;
    private String g;
    private Bundle h;
    private H5Page i;
    private boolean j;

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0039, code lost:
        r3 = r2.handlerStartupParams(r10.i, r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public H5ScriptLoader(com.alipay.mobile.nebulacore.core.H5PageImpl r11) {
        /*
            r10 = this;
            r9 = 0
            r8 = 0
            r10.<init>()
            r10.i = r11
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.getWebView()
            r10.d = r6
            r10.bizLoaded = r8
            r10.bridgeLoaded = r8
            com.alipay.mobile.h5container.api.H5Page r6 = r10.i
            android.os.Bundle r3 = r6.getParams()
            java.lang.String r6 = "isTinyApp"
            boolean r6 = com.alipay.mobile.nebula.util.H5Utils.getBoolean(r3, r6, r8)
            if (r6 != 0) goto L_0x002b
            java.lang.String r6 = "MINI-PROGRAM-WEB-VIEW-TAG"
            java.lang.String r6 = com.alipay.mobile.nebula.util.H5Utils.getString(r3, r6)
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x003f
        L_0x002b:
            java.lang.Class<com.alipay.mobile.nebula.provider.H5TinyAppProvider> r6 = com.alipay.mobile.nebula.provider.H5TinyAppProvider.class
            java.lang.String r6 = r6.getName()
            java.lang.Object r2 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r6)
            com.alipay.mobile.nebula.provider.H5TinyAppProvider r2 = (com.alipay.mobile.nebula.provider.H5TinyAppProvider) r2
            if (r2 == 0) goto L_0x003f
            com.alipay.mobile.h5container.api.H5Page r6 = r10.i
            android.os.Bundle r3 = r2.handlerStartupParams(r6, r3)
        L_0x003f:
            r10.h = r3
            android.os.Bundle r6 = r10.h
            java.lang.String r7 = "publicId"
            java.lang.String r6 = com.alipay.mobile.nebula.util.H5Utils.getString(r6, r7)
            r10.f = r6
            android.os.Bundle r6 = r10.h
            java.lang.String r7 = "appId"
            java.lang.String r6 = com.alipay.mobile.nebula.util.H5Utils.getString(r6, r7)
            r10.e = r6
            android.os.Bundle r6 = r10.h
            java.lang.String r7 = "isTinyApp"
            boolean r6 = com.alipay.mobile.nebula.util.H5Utils.getBoolean(r6, r7, r8)
            r10.j = r6
            java.lang.Object r6 = new java.lang.Object
            r6.<init>()
            r10.a = r6
            r10.b = r8
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            r10.c = r6
            android.os.Bundle r6 = r10.h
            com.alibaba.fastjson.JSONObject r4 = com.alipay.mobile.nebula.util.H5Utils.toJSONObject(r6)
            java.lang.String r6 = "bizScenario"
            java.lang.String r5 = com.alipay.mobile.nebula.util.H5Utils.getString(r4, r6)
            java.lang.String r6 = "scanApp"
            boolean r6 = r6.equals(r5)
            if (r6 == 0) goto L_0x0086
            r10.a()
        L_0x0086:
            com.alipay.mobile.nebulacore.Nebula.removeBridgeTimeParam(r4)
            java.lang.String r6 = "startupParams"
            java.lang.String r7 = r4.toJSONString()
            r10.setParamsToWebPage(r6, r7)
            java.lang.Boolean r6 = k
            if (r6 != 0) goto L_0x00c2
            java.lang.String r6 = "h5_nativeInput4Android"
            java.lang.String r6 = com.alipay.mobile.nebulacore.env.H5Environment.getConfigWithProcessCache(r6)
            com.alibaba.fastjson.JSONObject r0 = com.alipay.mobile.nebula.util.H5Utils.parseObject(r6)
            if (r0 == 0) goto L_0x00c2
            java.lang.String r6 = "textArea"
            com.alibaba.fastjson.JSONObject r1 = com.alipay.mobile.nebula.util.H5Utils.getJSONObject(r0, r6, r9)
            java.lang.String r6 = "yes"
            java.lang.String r7 = "disable"
            java.lang.String r7 = com.alipay.mobile.nebula.util.H5Utils.getString(r1, r7)
            boolean r6 = r6.equalsIgnoreCase(r7)
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)
            k = r6
            java.lang.String r6 = "appId"
            com.alibaba.fastjson.JSONArray r6 = com.alipay.mobile.nebula.util.H5Utils.getJSONArray(r1, r6, r9)
            l = r6
        L_0x00c2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.web.H5ScriptLoader.<init>(com.alipay.mobile.nebulacore.core.H5PageImpl):void");
    }

    private void a() {
        this.b = true;
    }

    public void resetBridge() {
        H5Log.d(TAG, "resetBridge " + System.currentTimeMillis());
        this.bizLoaded = false;
        this.bridgeLoaded = false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x016b, code lost:
        com.alipay.mobile.nebula.util.H5Log.d(TAG, "load javascript elapse [" + (java.lang.System.currentTimeMillis() - r4) + "] for " + r11.g);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean loadScript() {
        /*
            r11 = this;
            r6 = 0
            r7 = 1
            com.alipay.mobile.nebulacore.web.H5WebView r8 = r11.d
            if (r8 != 0) goto L_0x000e
            java.lang.String r7 = "H5ScriptLoader"
            java.lang.String r8 = "invalid web view parameter!"
            com.alipay.mobile.nebula.util.H5Log.e(r7, r8)
        L_0x000d:
            return r6
        L_0x000e:
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.d
            java.lang.String r6 = r6.getUrl()
            r11.g = r6
            long r4 = java.lang.System.currentTimeMillis()
            java.lang.Object r8 = r11.a
            monitor-enter(r8)
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.d     // Catch:{ all -> 0x0194 }
            r11.b(r6)     // Catch:{ all -> 0x0194 }
            boolean r6 = r11.bridgeLoaded     // Catch:{ all -> 0x0194 }
            if (r6 == 0) goto L_0x002d
            boolean r6 = r11.bizLoaded     // Catch:{ all -> 0x0194 }
            if (r6 == 0) goto L_0x002d
            monitor-exit(r8)     // Catch:{ all -> 0x0194 }
            r6 = r7
            goto L_0x000d
        L_0x002d:
            android.os.Bundle r6 = r11.h     // Catch:{ all -> 0x0194 }
            java.lang.String r9 = "isPrerender"
            r10 = 0
            boolean r6 = com.alipay.mobile.nebula.util.H5Utils.getBoolean(r6, r9, r10)     // Catch:{ all -> 0x0194 }
            if (r6 != 0) goto L_0x004a
            com.alipay.mobile.h5container.api.H5Page r6 = r11.i     // Catch:{ all -> 0x0194 }
            if (r6 == 0) goto L_0x004a
            java.lang.String r6 = "H5ScriptLoader"
            java.lang.String r9 = "injectPageReady when normal window present"
            com.alipay.mobile.nebula.util.H5Log.d(r6, r9)     // Catch:{ all -> 0x0194 }
            com.alipay.mobile.h5container.api.H5Page r6 = r11.i     // Catch:{ all -> 0x0194 }
            com.alipay.mobile.nebulacore.core.H5PageImpl r6 = (com.alipay.mobile.nebulacore.core.H5PageImpl) r6     // Catch:{ all -> 0x0194 }
            r6.injectPageReady()     // Catch:{ all -> 0x0194 }
        L_0x004a:
            boolean r6 = r11.bridgeLoaded     // Catch:{ all -> 0x0194 }
            if (r6 != 0) goto L_0x0053
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.d     // Catch:{ all -> 0x0194 }
            r11.a(r6)     // Catch:{ all -> 0x0194 }
        L_0x0053:
            boolean r6 = r11.bizLoaded     // Catch:{ all -> 0x0194 }
            if (r6 != 0) goto L_0x013f
            r6 = 1
            r11.bizLoaded = r6     // Catch:{ all -> 0x0194 }
            boolean r6 = r11.b     // Catch:{ all -> 0x0194 }
            if (r6 == 0) goto L_0x0070
            java.lang.String r6 = "H5ScriptLoader"
            java.lang.String r9 = "load raw h5_scan.js"
            com.alipay.mobile.nebula.util.H5Log.d(r6, r9)     // Catch:{ all -> 0x0194 }
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.d     // Catch:{ all -> 0x0194 }
            int r9 = com.alipay.mobile.nebula.R.raw.h5_scan     // Catch:{ all -> 0x0194 }
            java.lang.String r9 = com.alipay.mobile.nebula.appcenter.res.H5ResourceManager.getRaw(r9)     // Catch:{ all -> 0x0194 }
            b(r6, r9)     // Catch:{ all -> 0x0194 }
        L_0x0070:
            java.lang.String r6 = "H5ScriptLoader"
            java.lang.String r9 = "load raw share_new_min.js"
            com.alipay.mobile.nebula.util.H5Log.d(r6, r9)     // Catch:{ all -> 0x0194 }
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.d     // Catch:{ all -> 0x0194 }
            int r9 = com.alipay.mobile.nebula.R.raw.share_new_min     // Catch:{ all -> 0x0194 }
            java.lang.String r9 = com.alipay.mobile.nebula.appcenter.res.H5ResourceManager.getRaw(r9)     // Catch:{ all -> 0x0194 }
            b(r6, r9)     // Catch:{ all -> 0x0194 }
            r1 = 0
            boolean r6 = r11.b()     // Catch:{ all -> 0x0194 }
            if (r6 == 0) goto L_0x00a1
            int r6 = com.alipay.mobile.nebula.R.raw.h5_keyboard     // Catch:{ all -> 0x0194 }
            java.lang.String r1 = com.alipay.mobile.nebula.appcenter.res.H5ResourceManager.getRaw(r6)     // Catch:{ all -> 0x0194 }
            boolean r6 = com.alipay.mobile.nebulacore.Nebula.DEBUG     // Catch:{ all -> 0x0194 }
            if (r6 == 0) goto L_0x00a1
            java.lang.String r6 = "/sdcard/h5_keyboard.js"
            boolean r6 = com.alipay.mobile.nebula.util.H5FileUtil.exists(r6)     // Catch:{ all -> 0x0194 }
            if (r6 == 0) goto L_0x00a1
            java.lang.String r6 = "/sdcard/h5_keyboard.js"
            java.lang.String r1 = com.alipay.mobile.nebula.util.H5FileUtil.read(r6)     // Catch:{ all -> 0x0194 }
        L_0x00a1:
            com.alipay.mobile.h5container.api.H5Page r6 = r11.i     // Catch:{ all -> 0x0194 }
            boolean r6 = com.alipay.mobile.nebulacore.Nebula.enableNativeKeyboard(r6)     // Catch:{ all -> 0x0194 }
            if (r6 == 0) goto L_0x00cc
            com.alipay.mobile.h5container.api.H5Page r6 = r11.i     // Catch:{ all -> 0x0194 }
            if (r6 == 0) goto L_0x00cc
            com.alipay.mobile.h5container.api.H5Page r6 = r11.i     // Catch:{ all -> 0x0194 }
            android.os.Bundle r6 = r6.getParams()     // Catch:{ all -> 0x0194 }
            java.lang.String r9 = "DEBUG_PANEL_PAGE_TAG"
            boolean r6 = r6.containsKey(r9)     // Catch:{ all -> 0x0194 }
            if (r6 != 0) goto L_0x00cc
            java.lang.String r6 = "H5ScriptLoader"
            java.lang.String r9 = "inject native input js"
            com.alipay.mobile.nebula.util.H5Log.d(r6, r9)     // Catch:{ all -> 0x0194 }
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.d     // Catch:{ all -> 0x0194 }
            b(r6, r1)     // Catch:{ all -> 0x0194 }
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.d     // Catch:{ all -> 0x0194 }
            r11.c(r6)     // Catch:{ all -> 0x0194 }
        L_0x00cc:
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.d     // Catch:{ all -> 0x0194 }
            int r9 = com.alipay.mobile.nebula.R.raw.h5_newembedview     // Catch:{ all -> 0x0194 }
            java.lang.String r9 = com.alipay.mobile.nebula.appcenter.res.H5ResourceManager.getRaw(r9)     // Catch:{ all -> 0x0194 }
            b(r6, r9)     // Catch:{ all -> 0x0194 }
            boolean r6 = r11.j     // Catch:{ all -> 0x0194 }
            if (r6 == 0) goto L_0x00ed
            java.lang.String r6 = "yes"
            java.lang.String r9 = "h5_loadJavascript_for_tiny"
            java.lang.String r9 = com.alipay.mobile.nebulacore.env.H5Environment.getConfigWithProcessCache(r9)     // Catch:{ all -> 0x0194 }
            boolean r6 = r6.equalsIgnoreCase(r9)     // Catch:{ all -> 0x0194 }
            if (r6 != 0) goto L_0x00ed
            monitor-exit(r8)     // Catch:{ all -> 0x0194 }
            r6 = r7
            goto L_0x000d
        L_0x00ed:
            java.lang.String r6 = "H5ScriptLoader"
            java.lang.String r9 = "load raw h5_performance.js"
            com.alipay.mobile.nebula.util.H5Log.d(r6, r9)     // Catch:{ all -> 0x0194 }
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.d     // Catch:{ all -> 0x0194 }
            int r9 = com.alipay.mobile.nebula.R.raw.h5_performance     // Catch:{ all -> 0x0194 }
            java.lang.String r9 = com.alipay.mobile.nebula.appcenter.res.H5ResourceManager.getRaw(r9)     // Catch:{ all -> 0x0194 }
            b(r6, r9)     // Catch:{ all -> 0x0194 }
            java.lang.String r6 = "H5ScriptLoader"
            java.lang.String r9 = "load raw h5_bizlog_pre.js"
            com.alipay.mobile.nebula.util.H5Log.d(r6, r9)     // Catch:{ all -> 0x0194 }
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.d     // Catch:{ all -> 0x0194 }
            int r9 = com.alipay.mobile.nebula.R.raw.h5_bizlog_pre     // Catch:{ all -> 0x0194 }
            java.lang.String r9 = com.alipay.mobile.nebula.appcenter.res.H5ResourceManager.getRaw(r9)     // Catch:{ all -> 0x0194 }
            b(r6, r9)     // Catch:{ all -> 0x0194 }
            java.lang.String r6 = "H5ScriptLoader"
            java.lang.String r9 = "load raw h5_startparam.js"
            com.alipay.mobile.nebula.util.H5Log.d(r6, r9)     // Catch:{ all -> 0x0194 }
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.d     // Catch:{ all -> 0x0194 }
            int r9 = com.alipay.mobile.nebula.R.raw.h5_startparam     // Catch:{ all -> 0x0194 }
            java.lang.String r9 = com.alipay.mobile.nebula.appcenter.res.H5ResourceManager.getRaw(r9)     // Catch:{ all -> 0x0194 }
            b(r6, r9)     // Catch:{ all -> 0x0194 }
            com.alipay.mobile.h5container.api.H5Page r6 = r11.i     // Catch:{ all -> 0x0194 }
            java.lang.String r9 = "getLocation"
            boolean r6 = com.alipay.mobile.nebulacore.Nebula.supportJsaApi(r6, r9)     // Catch:{ all -> 0x0194 }
            if (r6 == 0) goto L_0x013f
            java.lang.String r6 = "H5ScriptLoader"
            java.lang.String r9 = "load raw h5location_min.js"
            com.alipay.mobile.nebula.util.H5Log.d(r6, r9)     // Catch:{ all -> 0x0194 }
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.d     // Catch:{ all -> 0x0194 }
            int r9 = com.alipay.mobile.nebula.R.raw.h5location_min     // Catch:{ all -> 0x0194 }
            java.lang.String r9 = com.alipay.mobile.nebula.appcenter.res.H5ResourceManager.getRaw(r9)     // Catch:{ all -> 0x0194 }
            b(r6, r9)     // Catch:{ all -> 0x0194 }
        L_0x013f:
            boolean r6 = com.alipay.mobile.nebulacore.Nebula.DEBUG     // Catch:{ all -> 0x0194 }
            if (r6 == 0) goto L_0x016a
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0194 }
            r0.<init>()     // Catch:{ all -> 0x0194 }
            java.lang.String r6 = "console.log('%cWelcome to NEBULA%c powered by UC\\n%cTips:\\n', 'color:#1874CD;font-size:20px','color:#FF7F24;font-size:15px','color:red;font-size:13px');"
            r0.append(r6)     // Catch:{ all -> 0x0194 }
            java.lang.String r6 = "console.log('%c1.ajax with get method may be cached, you can try to set \\'Cache-control: no-cache\\' in server side or add random param in request url','color:red;font-size:13px');"
            r0.append(r6)     // Catch:{ all -> 0x0194 }
            com.alipay.mobile.nebulacore.web.H5WebView r6 = r11.d     // Catch:{ all -> 0x0194 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0194 }
            java.lang.String r10 = "javascript:"
            r9.<init>(r10)     // Catch:{ all -> 0x0194 }
            java.lang.String r10 = r0.toString()     // Catch:{ all -> 0x0194 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x0194 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x0194 }
            r6.loadUrl(r9)     // Catch:{ all -> 0x0194 }
        L_0x016a:
            monitor-exit(r8)     // Catch:{ all -> 0x0194 }
            long r8 = java.lang.System.currentTimeMillis()
            long r2 = r8 - r4
            java.lang.String r6 = "H5ScriptLoader"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "load javascript elapse ["
            r8.<init>(r9)
            java.lang.StringBuilder r8 = r8.append(r2)
            java.lang.String r9 = "] for "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r11.g
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r6, r8)
            r6 = r7
            goto L_0x000d
        L_0x0194:
            r6 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x0194 }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.web.H5ScriptLoader.loadScript():boolean");
    }

    private boolean b() {
        return !H5Flag.useSysWebView || ("true".equals(H5Utils.getString(this.h, (String) RemoteDebugConstants.IS_REMOTE_DEBUG_MODE)) && this.d.getType() != WebViewType.SYSTEM_BUILD_IN);
    }

    public void setParamsToWebPage(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            H5Log.e((String) TAG, (String) "invalid js parameters!");
            return;
        }
        synchronized (this.a) {
            this.c.put(key, value);
            if (!this.bridgeLoaded) {
                H5Log.d(TAG, "bridge not loaded.");
                return;
            }
            H5Log.d(TAG, "setParamsToWebPage [key] " + key + " [value] " + value);
            this.d.loadUrl("javascript:if(typeof AlipayJSBridge === 'object'){AlipayJSBridge." + key + "=" + value + h.d);
        }
    }

    private void a(H5WebView webView) {
        long enterTime = System.currentTimeMillis();
        String bridgeStr = composeBridge();
        H5Log.d(TAG, "bridgeStr " + bridgeStr);
        webView.loadUrl("javascript:" + bridgeStr);
        H5Log.d(TAG, "bridge data loaded!");
        c();
        H5Log.d(TAG, "load bridge delta time " + (System.currentTimeMillis() - enterTime));
    }

    private void c() {
        if (this.i != null && this.i.getPageData() != null) {
            this.i.getPageData().putStringExtra(H5PageData.BRIDGE_READY, String.valueOf(System.currentTimeMillis()));
        }
    }

    public String composeBridge() {
        if (this.bridgeLoaded) {
            H5Log.d(TAG, "bridge already loaded!");
            return null;
        }
        H5Log.d(TAG, "composeBridge " + System.currentTimeMillis());
        this.bridgeLoaded = true;
        return Nebula.loadJsBridge(this.c, 1, this.i != null ? this.i.getWebViewId() : -1);
    }

    private void b(final H5WebView webView) {
        String h5_dsRules = "h5_dsRules";
        if (this.j) {
            h5_dsRules = "h5_dsRulesForTinyApp";
        }
        final String h5_DynamicScript = H5Environment.getConfigWithProcessCache(h5_dsRules);
        if (H5Utils.getBoolean(this.i.getParams(), (String) "isTinyApp", false)) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5ScriptLoader.this.loadDynamicJs(webView, h5_DynamicScript);
                }
            }, 1000);
        } else {
            loadDynamicJs(webView, h5_DynamicScript);
        }
        if (Nebula.DEBUG) {
            loadDynamicJs4Jsapi(webView, null);
        }
    }

    public void loadDynamicJs(H5WebView webView, String h5_DynamicScript) {
        long enterTime = System.currentTimeMillis();
        String finalScript = a(webView, h5_DynamicScript).toString();
        if (!TextUtils.isEmpty(finalScript)) {
            b(webView, a(finalScript));
            H5Log.d(TAG, "load dynamic delta time " + (System.currentTimeMillis() - enterTime));
        }
    }

    public void loadDynamicJs4Jsapi(H5WebView webView, String h5_DynamicScript) {
        long enterTime = System.currentTimeMillis();
        if (TextUtils.isEmpty(h5_DynamicScript) && !TextUtils.isEmpty(H5DevConfig.H5_LOAD_JS)) {
            h5_DynamicScript = H5DevConfig.H5_LOAD_JS;
        }
        if (TextUtils.isEmpty(h5_DynamicScript)) {
            H5Log.d(TAG, "load loadDynamicJs4Jsapi none return");
            return;
        }
        b(webView, "(function(){function onDOMReady(callback){var readyRE=/complete|loaded|interactive/;if(readyRE.test(document.readyState)){setTimeout(function(){callback()},1);}else{document.defaultView.addEventListener(\"DOMContentLoaded\",function(){callback()},false);}}onDOMReady(function(){" + a(webView, h5_DynamicScript) + "console.log(\"load insertJS success\")});})();");
        H5Log.d(TAG, "load dynamic4jsapi delta time " + (System.currentTimeMillis() - enterTime));
    }

    @NonNull
    private StringBuilder a(H5WebView webView, String h5_DynamicScript) {
        StringBuilder scriptBuilder = new StringBuilder();
        if (webView == null || TextUtils.isEmpty(h5_DynamicScript)) {
            H5Log.d(TAG, "no config found for dynamic script");
        } else {
            JSONArray jaScripts = H5Utils.parseArray(h5_DynamicScript);
            if (jaScripts == null || jaScripts.isEmpty()) {
                H5Log.w(TAG, "invalid dynamic script.");
            } else {
                for (int index = 0; index < jaScripts.size(); index++) {
                    List scriptList = null;
                    try {
                        scriptList = a(jaScripts.getJSONObject(index));
                    } catch (Throwable t) {
                        H5Log.e(TAG, "parse dynamic script exception.", t);
                    }
                    if (scriptList != null && !scriptList.isEmpty()) {
                        for (String dynamicScript : scriptList) {
                            if (!TextUtils.isEmpty(dynamicScript)) {
                                scriptBuilder.append(b(dynamicScript));
                            }
                        }
                    }
                }
            }
        }
        return scriptBuilder;
    }

    private static String a(String js) {
        return "(function(){if(window.HASINJECTDRNAMICSCRIPT){console.log(\"has load h5_dsRulesV2: \"+window.HASINJECTDRNAMICSCRIPT);return}window.HASINJECTDRNAMICSCRIPT=false;function onDOMReady(callback){var readyRE=/complete|loaded|interactive/;if(readyRE.test(document.readyState)){setTimeout(function(){callback()},1)}else{document.defaultView.addEventListener(\"DOMContentLoaded\",function(){callback()},false)}}onDOMReady(function(){if(!window.HASINJECTDRNAMICSCRIPT){" + js + "console.log(\"load h5_dsRulesV2 success\");window.HASINJECTDRNAMICSCRIPT=true}})})();";
    }

    private static String b(String dynamicScript) {
        H5Log.d(TAG, "load dynamicV2 script " + dynamicScript);
        return "var script,head=document.head||document.documentElement;script=document.createElement(\"script\");script.async=true;script.charset=\"UTF-8\";script.src=\"" + dynamicScript + "\";" + c(dynamicScript) + "head.insertBefore(script,head.firstChild);";
    }

    private static String c(String dynamicScript) {
        if (TextUtils.isEmpty(dynamicScript) || !dynamicScript.contains("nebula-addcors")) {
            return "";
        }
        return "script.setAttribute('crossorigin','');";
    }

    private List<String> a(JSONObject joScript) {
        if (this.d == null || joScript == null || joScript.isEmpty()) {
            return null;
        }
        List scriptList = new ArrayList();
        for (String url : joScript.keySet()) {
            if (!TextUtils.isEmpty(url)) {
                JSONArray jaConds = joScript.getJSONArray(url);
                if (jaConds != null && !jaConds.isEmpty()) {
                    int index = 0;
                    while (true) {
                        if (index >= jaConds.size()) {
                            break;
                        }
                        JSONObject joCond = jaConds.getJSONObject(index);
                        String appIdCond = H5Utils.getString(joCond, (String) "appId");
                        String publicIdCond = H5Utils.getString(joCond, (String) H5Param.PUBLIC_ID);
                        String urlCond = H5Utils.getString(joCond, (String) "url");
                        if ((TextUtils.isEmpty(appIdCond) || a(this.e, appIdCond)) && (TextUtils.isEmpty(publicIdCond) || a(this.f, publicIdCond)) && (TextUtils.isEmpty(urlCond) || a(this.g, urlCond))) {
                            scriptList.add(url);
                            break;
                        }
                        index++;
                    }
                } else {
                    scriptList.add(url);
                }
            }
        }
        return scriptList;
    }

    private static boolean a(String value, String cond) {
        Pattern pattern = H5PatternHelper.compile(cond);
        if (pattern == null) {
            return false;
        }
        return pattern.matcher(value).find();
    }

    private static void b(H5WebView webView, String javascript) {
        if (!TextUtils.isEmpty(javascript)) {
            webView.loadUrl("javascript:" + javascript);
        }
    }

    private void c(H5WebView webView) {
        if (webView != null) {
            if (k != null && k.booleanValue()) {
                webView.loadUrl("javascript:(function(){window.disableNativeTextArea=true;})()");
                H5Log.d(TAG, "disable all native textarea");
            } else if (this.i != null) {
                String appId = H5Utils.getString(this.i.getParams(), (String) "appId");
                if (!TextUtils.isEmpty(appId) && l != null && !l.isEmpty()) {
                    for (int i2 = 0; i2 < l.size(); i2++) {
                        if (appId.equals(l.getString(i2))) {
                            webView.loadUrl("javascript:(function(){window.disableNativeTextArea=true;})()");
                            H5Log.d(TAG, "disable native textarea : " + appId);
                            return;
                        }
                    }
                }
            }
        }
    }
}
