package com.alipay.mobile.nebulacore.wallet;

import android.app.Activity;
import android.os.Bundle;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5WebViewChoose;
import com.alipay.mobile.nebulacore.ui.H5Activity;

public class H5WebViewFactory {
    private static H5WebViewFactory a = null;

    private H5WebViewFactory() {
    }

    public static H5WebViewFactory instance() {
        if (a == null) {
            a = new H5WebViewFactory();
        }
        return a;
    }

    private static boolean a(Activity activity, String bizType, Bundle bundle) {
        if (!H5Flag.initUcNormal) {
            H5Log.e((String) "H5WebViewFactory", (String) "uc init throw exception, so not need init ");
            return true;
        }
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            JSONArray jsonArray = H5Utils.parseArray(h5ConfigProvider.getConfig("h5_first_init_use_android_webView_biz"));
            if (jsonArray != null && !jsonArray.isEmpty() && jsonArray.contains(bizType)) {
                return false;
            }
        }
        if (H5WebViewChoose.useSysWebWillCrash()) {
            return false;
        }
        if (!(activity instanceof H5Activity) || H5Utils.getBoolean(bundle, (String) H5Param.FIRST_INIT_USE_ANDROID_WEBVIEW, false) || h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_first_init_use_android_webView_startPage"))) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: type inference failed for: r17v0 */
    /* JADX WARNING: type inference failed for: r17v1 */
    /* JADX WARNING: type inference failed for: r18v0 */
    /* JADX WARNING: type inference failed for: r18v1 */
    /* JADX WARNING: type inference failed for: r17v5, types: [com.alipay.mobile.nebula.webview.APWebView] */
    /* JADX WARNING: type inference failed for: r18v2 */
    /* JADX WARNING: type inference failed for: r17v6, types: [com.alipay.mobile.nebula.webview.APWebView] */
    /* JADX WARNING: type inference failed for: r18v3 */
    /* JADX WARNING: type inference failed for: r18v4 */
    /* JADX WARNING: type inference failed for: r18v5 */
    /* JADX WARNING: type inference failed for: r17v9, types: [com.alipay.mobile.nebula.webview.APWebView] */
    /* JADX WARNING: type inference failed for: r17v10 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0138  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x014b  */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.mobile.nebula.webview.APWebView createWebView(android.app.Activity r25, java.lang.String r26, android.content.Context r27, android.os.Bundle r28) {
        /*
            r24 = this;
            long r14 = java.lang.System.currentTimeMillis()
            com.alipay.mobile.nebula.webview.WebViewType r19 = com.alipay.mobile.nebulacore.env.H5WebViewChoose.getWebViewType(r26, r27, r28)
            com.alibaba.fastjson.JSONObject r10 = new com.alibaba.fastjson.JSONObject
            r10.<init>()
            r17 = 0
            com.alipay.mobile.nebula.webview.WebViewType r20 = com.alipay.mobile.nebula.webview.WebViewType.RN_VIEW
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x002a
            com.alipay.mobile.h5container.service.RnService r11 = com.alipay.mobile.nebulacore.env.H5Environment.getRnService()
            if (r11 == 0) goto L_0x01a8
            java.lang.String r20 = "H5WebViewFactory"
            java.lang.String r21 = "create rn view"
            com.alipay.mobile.nebula.util.H5Log.d(r20, r21)     // Catch:{ Throwable -> 0x0180 }
            r0 = r27
            com.alipay.mobile.nebula.webview.APWebView r17 = r11.createWebView(r0)     // Catch:{ Throwable -> 0x0180 }
        L_0x002a:
            com.alipay.mobile.nebula.webview.WebViewType r20 = com.alipay.mobile.nebula.webview.WebViewType.THIRD_PARTY
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x0250
            boolean r20 = com.alipay.mobile.h5container.api.H5Flag.ucReady
            if (r20 != 0) goto L_0x01d5
            r0 = r25
            r1 = r26
            r2 = r28
            boolean r20 = a(r0, r1, r2)
            if (r20 == 0) goto L_0x01d5
            java.lang.String r20 = "H5WebViewFactory"
            java.lang.String r21 = "uc is not Ready canUseAndroidWebView create android web view "
            com.alipay.mobile.nebula.util.H5Log.d(r20, r21)     // Catch:{ Throwable -> 0x01b1 }
            com.alipay.mobile.nebulacore.android.AndroidWebView r17 = new com.alipay.mobile.nebulacore.android.AndroidWebView     // Catch:{ Throwable -> 0x01b1 }
            r0 = r17
            r1 = r27
            r0.<init>(r1)     // Catch:{ Throwable -> 0x01b1 }
            r18 = r17
        L_0x0054:
            if (r18 != 0) goto L_0x0067
            boolean r20 = com.alipay.mobile.h5container.api.H5Flag.ucReady
            if (r20 != 0) goto L_0x0067
            com.alipay.mobile.nebula.webview.WebViewType r20 = com.alipay.mobile.nebula.webview.WebViewType.THIRD_PARTY
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x0067
            r20 = 0
            com.alipay.mobile.nebulacore.env.H5WebViewChoose.sendUcReceiver(r20)
        L_0x0067:
            if (r18 != 0) goto L_0x0280
            java.lang.String r20 = "H5WebViewFactory"
            java.lang.String r21 = "create android web view"
            com.alipay.mobile.nebula.util.H5Log.d(r20, r21)     // Catch:{ Throwable -> 0x0254 }
            com.alipay.mobile.nebulacore.android.AndroidWebView r17 = new com.alipay.mobile.nebulacore.android.AndroidWebView     // Catch:{ Throwable -> 0x0254 }
            r0 = r17
            r1 = r27
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0254 }
        L_0x0079:
            if (r17 == 0) goto L_0x00a0
            java.lang.String r16 = r17.getVersion()
            java.lang.String r20 = "version"
            r0 = r20
            r1 = r16
            r10.put(r0, r1)
            java.lang.String r20 = "H5WebViewFactory"
            java.lang.StringBuilder r21 = new java.lang.StringBuilder
            java.lang.String r22 = "webview version: "
            r21.<init>(r22)
            r0 = r21
            r1 = r16
            java.lang.StringBuilder r21 = r0.append(r1)
            java.lang.String r21 = r21.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r20, r21)
        L_0x00a0:
            com.alipay.mobile.nebulacore.api.NebulaService r20 = com.alipay.mobile.nebulacore.Nebula.getService()
            if (r20 == 0) goto L_0x00d3
            com.alipay.mobile.nebulacore.api.NebulaService r20 = com.alipay.mobile.nebulacore.Nebula.getService()
            com.alipay.mobile.h5container.api.H5Session r20 = r20.getTopSession()
            if (r20 == 0) goto L_0x00d3
            com.alipay.mobile.nebulacore.api.NebulaService r20 = com.alipay.mobile.nebulacore.Nebula.getService()
            com.alipay.mobile.h5container.api.H5Session r20 = r20.getTopSession()
            com.alipay.mobile.h5container.api.H5Page r20 = r20.getTopPage()
            if (r20 == 0) goto L_0x00d3
            com.alipay.mobile.nebulacore.api.NebulaService r20 = com.alipay.mobile.nebulacore.Nebula.getService()
            com.alipay.mobile.h5container.api.H5Session r20 = r20.getTopSession()
            com.alipay.mobile.h5container.api.H5Page r9 = r20.getTopPage()
            if (r9 == 0) goto L_0x00d3
            java.lang.String r20 = "h5PageCreateWebView"
            r0 = r20
            r9.sendEvent(r0, r10)
        L_0x00d3:
            if (r17 == 0) goto L_0x0147
            com.alipay.mobile.nebula.webview.WebViewType r20 = com.alipay.mobile.nebula.webview.WebViewType.SYSTEM_BUILD_IN
            com.alipay.mobile.nebula.webview.WebViewType r21 = r17.getType()
            r0 = r20
            r1 = r21
            if (r0 != r1) goto L_0x0147
            java.lang.String r20 = "appId"
            r0 = r28
            r1 = r20
            java.lang.String r4 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r1)
            java.lang.String r20 = "isTinyApp"
            r21 = 0
            r0 = r28
            r1 = r20
            r2 = r21
            boolean r8 = com.alipay.mobile.nebula.util.H5Utils.getBoolean(r0, r1, r2)
            java.lang.String r20 = "H5_WebView_Type"
            com.alipay.mobile.nebula.log.H5LogData r20 = com.alipay.mobile.nebula.log.H5LogData.seedId(r20)
            com.alipay.mobile.nebula.log.H5LogData r20 = r20.param1()
            r21 = 0
            r0 = r20
            r1 = r21
            com.alipay.mobile.nebula.log.H5LogData r20 = r0.add(r4, r1)
            com.alipay.mobile.nebula.log.H5LogData r20 = r20.param2()
            java.lang.String r21 = "AndroidWebView"
            r22 = 0
            com.alipay.mobile.nebula.log.H5LogData r20 = r20.add(r21, r22)
            com.alipay.mobile.nebula.log.H5LogData r20 = r20.param3()
            java.lang.String r21 = java.lang.String.valueOf(r8)
            r22 = 0
            com.alipay.mobile.nebula.log.H5LogData r20 = r20.add(r21, r22)
            com.alipay.mobile.nebula.log.H5LogData r20 = r20.param4()
            java.lang.String r21 = "使用Android系统WebView"
            r22 = 0
            com.alipay.mobile.nebula.log.H5LogData r20 = r20.add(r21, r22)
            com.alipay.mobile.nebula.log.H5LogUtil.logNebulaTech(r20)
            if (r8 == 0) goto L_0x0147
            java.lang.String r20 = "MTBIZ_H5"
            java.lang.String r21 = "H5_TinyApp_Use_AndroidWebView"
            r22 = 0
            r0 = r20
            r1 = r21
            r2 = r22
            com.alipay.mobile.nebula.log.H5Logger.mtBizReport(r0, r1, r4, r2)
        L_0x0147:
            boolean r20 = com.alipay.mobile.nebulacore.Nebula.DEBUG
            if (r20 == 0) goto L_0x0158
            java.lang.String r20 = "pageLoad|createWebViewFinishPoint"
            long r22 = java.lang.System.currentTimeMillis()
            java.lang.Long r21 = java.lang.Long.valueOf(r22)
            com.alipay.mobile.nebula.util.TestDataUtils.storeJSParams(r20, r21)
        L_0x0158:
            long r20 = java.lang.System.currentTimeMillis()
            long r6 = r20 - r14
            java.lang.String r20 = "H5WebViewFactory"
            java.lang.StringBuilder r21 = new java.lang.StringBuilder
            java.lang.String r22 = "createWebView elapse "
            r21.<init>(r22)
            r0 = r21
            java.lang.StringBuilder r21 = r0.append(r6)
            java.lang.String r21 = r21.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r20, r21)
            java.lang.String r20 = "create_webView"
            java.lang.String r21 = "create_webView"
            r0 = r20
            r1 = r21
            com.alipay.mobile.nebulacore.util.H5TimeUtil.timeLog(r0, r1, r14)
            return r17
        L_0x0180:
            r5 = move-exception
            java.lang.String r20 = "degradeWebView"
            r21 = 1
            java.lang.Boolean r21 = java.lang.Boolean.valueOf(r21)
            r0 = r20
            r1 = r21
            r10.put(r0, r1)
            java.lang.String r20 = "H5WebViewFactory"
            java.lang.StringBuilder r21 = new java.lang.StringBuilder
            java.lang.String r22 = "create ReactNebulaView fail:"
            r21.<init>(r22)
            r0 = r21
            java.lang.StringBuilder r21 = r0.append(r5)
            java.lang.String r21 = r21.toString()
            com.alipay.mobile.nebula.util.H5Log.e(r20, r21)
            goto L_0x002a
        L_0x01a8:
            java.lang.String r20 = "H5WebViewFactory"
            java.lang.String r21 = "failed to get rn service"
            com.alipay.mobile.nebula.util.H5Log.d(r20, r21)
            goto L_0x002a
        L_0x01b1:
            r12 = move-exception
            java.lang.String r20 = "H5WebViewFactory"
            java.lang.String r21 = "create android webview failed"
            r0 = r20
            r1 = r21
            com.alipay.mobile.nebula.util.H5Log.e(r0, r1, r12)
            java.lang.String r20 = "H5WebViewFactory"
            com.alipay.mobile.nebula.log.H5LogData r20 = com.alipay.mobile.nebula.log.H5LogData.seedId(r20)
            com.alipay.mobile.nebula.log.H5LogData r20 = r20.param4()
            java.lang.String r21 = "createAndroidWebViewException"
            r0 = r20
            r1 = r21
            com.alipay.mobile.nebula.log.H5LogData r20 = r0.add(r1, r12)
            com.alipay.mobile.nebula.log.H5LogUtil.logNebulaTech(r20)
            throw r12
        L_0x01d5:
            com.alipay.mobile.h5container.service.UcService r13 = com.alipay.mobile.nebula.util.H5ServiceUtils.getUcService()
            if (r13 != 0) goto L_0x01e6
            java.lang.String r20 = "H5WebViewFactory"
            java.lang.String r21 = "failed to get uc service"
            com.alipay.mobile.nebula.util.H5Log.d(r20, r21)
            r18 = r17
            goto L_0x0054
        L_0x01e6:
            java.lang.String r20 = "H5WebViewFactory"
            java.lang.String r21 = "create uc web view"
            com.alipay.mobile.nebula.util.H5Log.d(r20, r21)     // Catch:{ Throwable -> 0x021e }
            boolean r20 = com.alipay.mobile.nebulacore.Nebula.disableHWACByUCStyle()     // Catch:{ Throwable -> 0x021e }
            if (r20 == 0) goto L_0x020e
            r0 = r28
            r1 = r26
            boolean r20 = com.alipay.mobile.nebulacore.web.H5HardwarePolicy.disableHardwareAccelerate(r0, r1)     // Catch:{ Throwable -> 0x021e }
            if (r20 != 0) goto L_0x020b
            r20 = 1
        L_0x01ff:
            r0 = r27
            r1 = r20
            com.alipay.mobile.nebula.webview.APWebView r17 = r13.createWebView(r0, r1)     // Catch:{ Throwable -> 0x021e }
            r18 = r17
            goto L_0x0054
        L_0x020b:
            r20 = 0
            goto L_0x01ff
        L_0x020e:
            boolean r20 = com.alipay.mobile.nebulacore.web.H5HardwarePolicy.isAbove14Level()     // Catch:{ Throwable -> 0x021e }
            r0 = r27
            r1 = r20
            com.alipay.mobile.nebula.webview.APWebView r17 = r13.createWebView(r0, r1)     // Catch:{ Throwable -> 0x021e }
            r18 = r17
            goto L_0x0054
        L_0x021e:
            r5 = move-exception
            java.lang.String r20 = "degradeWebView"
            r21 = 1
            java.lang.Boolean r21 = java.lang.Boolean.valueOf(r21)
            r0 = r20
            r1 = r21
            r10.put(r0, r1)
            java.lang.String r20 = "H5WebViewFactory"
            java.lang.String r21 = "create uc web view failed"
            r0 = r20
            r1 = r21
            com.alipay.mobile.nebula.util.H5Log.e(r0, r1, r5)
            java.lang.String r20 = "H5WebViewFactory"
            com.alipay.mobile.nebula.log.H5LogData r20 = com.alipay.mobile.nebula.log.H5LogData.seedId(r20)
            com.alipay.mobile.nebula.log.H5LogData r20 = r20.param4()
            java.lang.String r21 = "createUCWebViewException"
            r0 = r20
            r1 = r21
            com.alipay.mobile.nebula.log.H5LogData r20 = r0.add(r1, r5)
            com.alipay.mobile.nebula.log.H5LogUtil.logNebulaTech(r20)
        L_0x0250:
            r18 = r17
            goto L_0x0054
        L_0x0254:
            r12 = move-exception
            java.lang.String r20 = "H5WebViewFactory"
            java.lang.String r21 = "create android webview failed"
            r0 = r20
            r1 = r21
            com.alipay.mobile.nebula.util.H5Log.e(r0, r1, r12)
            java.lang.String r20 = "H5WebViewFactory"
            com.alipay.mobile.nebula.log.H5LogData r20 = com.alipay.mobile.nebula.log.H5LogData.seedId(r20)
            com.alipay.mobile.nebula.log.H5LogData r20 = r20.param4()
            java.lang.String r21 = "createAndroidWebViewException"
            r0 = r20
            r1 = r21
            com.alipay.mobile.nebula.log.H5LogData r20 = r0.add(r1, r12)
            com.alipay.mobile.nebula.log.H5LogUtil.logNebulaTech(r20)
            r0 = r25
            boolean r0 = r0 instanceof com.alipay.mobile.nebulacore.ui.H5Activity
            r20 = r0
            if (r20 == 0) goto L_0x0280
            throw r12
        L_0x0280:
            r17 = r18
            goto L_0x0079
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.wallet.H5WebViewFactory.createWebView(android.app.Activity, java.lang.String, android.content.Context, android.os.Bundle):com.alipay.mobile.nebula.webview.APWebView");
    }
}
