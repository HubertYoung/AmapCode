package com.alipay.mobile.nebulacore.log;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.webkit.WebSettings;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5AvailablePageData;
import com.alipay.mobile.h5container.api.H5JsCallData;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.dev.H5BugmeLogCollector;
import com.alipay.mobile.nebula.log.H5BehaviorLogConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.log.H5MonitorLogConfig;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5DeviceHelper;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.appcenter.parse.H5PackageParser;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.wallet.H5JsApiConfigModel;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.sdk.app.statistic.c;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class H5PerformanceLogRunnable extends H5LogRunnable {
    /* access modifiers changed from: private */
    public static String b = "";
    private String c;
    private H5PageData d;
    private Map<String, String> e;
    private H5AvailablePageData f;
    private String g;

    public H5PerformanceLogRunnable(H5PageData pageData, Map<String, Object> performance, H5AvailablePageData availablePageData, H5Page h5Page, String jsError) {
        super(performance);
        this.d = pageData;
        this.f = availablePageData;
        this.c = jsError;
        if (h5Page != null) {
            this.e = new HashMap();
            this.e = new HashMap();
            String mimeType = H5FileUtil.getMimeType(h5Page.getUrl());
            if (!TextUtils.isEmpty(H5PageData.ucCacheSdcardLimit)) {
                this.e.put("ucCacheSdcardLimit", H5PageData.ucCacheSdcardLimit);
            }
            this.e.put("type", mimeType);
            this.e.put("end", System.currentTimeMillis());
            APWebView apWebView = h5Page.getWebView();
            if (apWebView != null) {
                this.g = apWebView.getVersion();
                this.e.put("webViewVersion", this.g);
                if (TextUtils.isEmpty(b)) {
                    b = "0.0.0.0";
                    f();
                }
            }
        }
    }

    public void run() {
        if (this.d != null && this.f != null) {
            c();
            if (!TextUtils.isEmpty(this.c)) {
                H5LogUtil.logH5Exception(H5LogData.seedId("H5_AL_PAGE_JSERROR").param3().add(this.c, null).param4().addUniteParam(this.d).add("webViewVersion", this.g).addNonNullValue("dslVersion", a(this.d)));
            }
            if (H5Utils.enableJsApiPerformance()) {
                e();
            }
            String jsApiConfig = H5Environment.getConfigWithProcessCache("h5_secJsApiCallConfig");
            if (!TextUtils.isEmpty(jsApiConfig)) {
                b(jsApiConfig);
            }
            if ("yes".equalsIgnoreCase(H5Utils.getString(H5Environment.getConfigJSONObject("h5_netstatlog"), (String) FunctionSupportConfiger.SWITCH_TAG))) {
                b();
            }
        }
    }

    private void b() {
        if (this.d != null) {
            Map netInfo = this.d.getNetRequestInfo();
            if (netInfo != null && !netInfo.isEmpty()) {
                H5LogData netInfoData = H5LogData.seedId("H5_WSNET_PERF");
                StringBuilder netInfoStr = new StringBuilder();
                for (Entry entry : netInfo.entrySet()) {
                    netInfoStr.append("url::").append((String) entry.getKey()).append("_").append((String) entry.getValue()).append(MergeUtil.SEPARATOR_KV);
                }
                netInfoData.param3().add("netinfo", netInfoStr.toString());
                H5LogUtil.behaviorLog(netInfoData, H5BehaviorLogConfig.newH5BehaviorLogConfig().setBehaviourPro(H5BehaviorLogConfig.WEBSTAT_BEHAVIOUR));
            }
        }
    }

    private void c() {
        String publicId;
        H5LogData param2 = H5LogData.seedId("H5_PAGE_PERFORMANCE").param1().add(H5Utils.getMaxLogStr(this.d.getPageUrl()), null).param2();
        if (TextUtils.isEmpty(this.d.getPublicId())) {
            publicId = this.d.getAppId();
        } else {
            publicId = this.d.getPublicId();
        }
        H5LogData performanceData = param2.add(publicId, null).param3().add(this.d.getTitle(), null).param4().addMapParam(H5Logger.getPerformanceDataMap(this.d)).addMapParam(this.e).addUniteParam(this.d);
        if (this.a != null && !this.a.isEmpty()) {
            performanceData.param4().addMapParam(this.a);
            performanceData.param4().add(H5PageData.JS_ERRORS, Integer.valueOf(this.d.getIntExtra(H5PageData.JS_ERRORS, 0)));
            if (this.a.containsKey("pageLoad")) {
                performanceData.param4().add("pageLoad", c("pageLoad"));
            }
            if (this.a.containsKey("domReady")) {
                performanceData.param4().add("domReady", c("domReady"));
            }
        }
        performanceData.param4().add("stopLoading", Long.valueOf(H5Logger.getCorrectStopLoading(this.f.getStopLoadingTime(), this.d, this.f))).add("stopLoadingWithoutLocating", Long.valueOf(H5Logger.getCorrectStopLoading(this.f.getStopLoadingTimeWithLoc(), this.d, this.f)));
        if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_uploadCacheResNum"))) {
            performanceData.param4().add("cacheResNum", Integer.valueOf(this.d.getUcCacheResNum())).add("aliResNum", Integer.valueOf(this.d.getAliRequestResNum()));
        }
        if (H5BugmeLogCollector.enabled()) {
            performanceData.param4().add("enableBugmeDump", Boolean.valueOf(true));
        }
        if (H5PackageParser.sHasSetLastModifiedFail) {
            performanceData.param4().add("lastModifiedFailPKGI", Boolean.valueOf(true));
        }
        Context context = H5Utils.getContext();
        if (context != null) {
            performanceData.param4().add("screenDisplay", H5DimensionUtil.getScreenHeight(context) + DictionaryKeys.CTRLXY_X + H5DimensionUtil.getScreenWidth(context));
        }
        String cpuHardware = H5DeviceHelper.getCpuHardware();
        if (!TextUtils.isEmpty(cpuHardware)) {
            performanceData.param4().add("cpuHardware", cpuHardware.replaceAll("\\s+", ""));
        }
        a(performanceData);
        if (H5PageData.swFirstJsApiCallTime != 0) {
            long cost = H5PageData.swFirstJsApiCallTime - H5PageData.createAppTime;
            performanceData.param4().add("swFirstJsApiCallTime", Long.valueOf(cost));
            H5Log.d("H5PerformanceLog", "tinyApp swFirstJsApiCallTime " + cost);
            H5PageData.swFirstJsApiCallTime = 0;
        }
        if (TextUtils.isEmpty(b)) {
            b = "0.0.0.0";
        }
        performanceData.param4().add("systemWebViewVersion", b);
        r0 = "abtestUsedTime";
        performanceData.param4().add("isLocal", this.d.getIsLocal()).add("containCORSRes", Boolean.valueOf(this.d.getBooleanExtra("containCORSRes", false))).add("usePreRequest", Boolean.valueOf(this.d.isUsePreRequest())).add("preConnectRequest", Boolean.valueOf(this.d.isPreConnectRequest())).addNonNullValue("abtestUsedTime", this.d.getAbTestUsedTime() > 0 ? Integer.valueOf(this.d.getAbTestUsedTime()) : "").add("fallbackType", Integer.valueOf(this.d.getIntExtra("fallbackType", 0))).add("protocal", this.d.getProtocol()).add("responseDuration", Long.valueOf(this.d.getLastResponseTimestamp() - this.d.getStart())).add("h2", Integer.valueOf(this.d.getRequestCountByProtocal("h2"))).add(ExtTransportStrategy.EXT_PROTO_SPDY, Integer.valueOf(this.d.getRequestCountByProtocal(ExtTransportStrategy.EXT_PROTO_SPDY))).add("h1", Integer.valueOf(this.d.getRequestCountByProtocal("h1"))).add("useWebViewPool", Boolean.valueOf(this.d.isUseWebViewPool())).add("lottieLoadStart", Long.valueOf(this.d.getLottieLoadingAnimStart())).add("lottieLoadEnd", Long.valueOf(this.d.getLottieLoadingAnimEnd())).add("isLowerDevice", H5PageImpl.sIsLowerDevice).add("multiProcessMode", Integer.valueOf(this.d.getMultiProcessMode())).add("pushWindowAnim", Boolean.valueOf(this.d.isUsePushWindowAnim()));
        if (!TextUtils.isEmpty(this.d.getUCCorePerfExtra())) {
            performanceData.param4().add("ucCorePerfExtra", this.d.getUCCorePerfExtra());
        }
        H5LogUtil.monitorLog(performanceData, H5MonitorLogConfig.newH5MonitorLogConfig().setLogType(H5MonitorLogConfig.WEBAPP_TYPE).setLogHeader(H5MonitorLogConfig.MONITOR_HEADER));
    }

    private void b(String jsApiConfig) {
        String spdyDetail;
        H5JsApiConfigModel jsApiConfigModel = (H5JsApiConfigModel) JSON.parseObject(jsApiConfig, H5JsApiConfigModel.class);
        String jsApiDetail = this.d.getJsApiDetail();
        if (jsApiConfigModel != null && jsApiConfigModel.isEnable() && !TextUtils.isEmpty(jsApiDetail)) {
            if (!TextUtils.isEmpty(this.d.getErrorSpdyMsg())) {
                spdyDetail = "Y`_`Y`_`" + this.d.getErrorCode() + "`_`" + this.d.getErrorSpdyMsg();
            } else {
                spdyDetail = "" + (this.d.isSpdy() ? "Y" : "N") + "`_`N`_``_`";
            }
            H5LogUtil.behaviorLog(H5LogData.seedId("H5_SEC_JSAPICALL").param3().add("logVersion", "1").add("jsapi", jsApiDetail).add(ExtTransportStrategy.EXT_PROTO_SPDY, spdyDetail).add("pageLevel", d()).param4().addUniteParam(this.d), H5BehaviorLogConfig.newH5BehaviorLogConfig().setBehaviourPro(H5BehaviorLogConfig.H5SECURITY_BEHAVIOUR));
        }
    }

    private String d() {
        if (this.d == null) {
            return "";
        }
        H5ConfigProvider configProvider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (configProvider == null) {
            return "";
        }
        if (configProvider.isRpcDomains(this.d.getPageUrl())) {
            return "rpc";
        }
        if (configProvider.isAlipayDomains(this.d.getPageUrl())) {
            return BehavorReporter.PROVIDE_BY_ALIPAY;
        }
        if (configProvider.isSeriousAliDomains(this.d.getPageUrl())) {
            return "seriousAli";
        }
        if (configProvider.isAliDomains(this.d.getPageUrl())) {
            return "ali";
        }
        if (configProvider.isPartnerDomains(this.d.getPageUrl())) {
            return c.H;
        }
        return "else";
    }

    private void e() {
        String jsApiPerformance = "";
        Map jsApiMap = this.d.getJsapiInfoList();
        if (jsApiMap != null && jsApiMap.size() > 0) {
            for (Entry<String, H5JsCallData> value : jsApiMap.entrySet()) {
                H5JsCallData callData = (H5JsCallData) value.getValue();
                if (TextUtils.isEmpty(jsApiPerformance)) {
                    jsApiPerformance = callData.getAction() + "_" + callData.getElapse();
                } else {
                    jsApiPerformance = jsApiPerformance + MergeUtil.SEPARATOR_KV + callData.getAction() + "_" + callData.getElapse();
                }
            }
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_JSAPI_PERFORMANCE").param3().add(this.d.getsApiPerExtra(), null).param4().add("appId", this.d.getAppId()).add("url", this.d.getPageUrl()).add("jsApi", jsApiPerformance));
        }
    }

    private static String a(H5PageData pageData) {
        if (pageData == null || !"YES".equals(pageData.getIsTinyApp())) {
            return "";
        }
        return H5Logger.getDslVersion(pageData);
    }

    private void a(H5LogData h5LogData) {
        int jsApiNum = 0;
        String jsApiList = "";
        Map jsapiInfoList = this.d.getJsapiInfoList();
        JSONArray blackJSApiList = H5LogUtil.getPerformanceJSApiBlackList();
        Map filterJSApiMap = new ConcurrentHashMap();
        for (Entry<String, H5JsCallData> value : jsapiInfoList.entrySet()) {
            String jsApiName = ((H5JsCallData) value.getValue()).getAction();
            if (blackJSApiList == null || !blackJSApiList.contains(jsApiName)) {
                jsApiList = jsApiList + jsApiName + MergeUtil.SEPARATOR_KV;
                jsApiNum++;
            } else if (filterJSApiMap.containsKey(jsApiName)) {
                filterJSApiMap.put(jsApiName, Integer.valueOf(((Integer) filterJSApiMap.get(jsApiName)).intValue() + 1));
            } else {
                filterJSApiMap.put(jsApiName, Integer.valueOf(1));
            }
        }
        String filterJSApiInfo = "";
        if (filterJSApiMap.size() > 0) {
            for (Entry filterEntry : filterJSApiMap.entrySet()) {
                if (TextUtils.isEmpty(filterJSApiInfo)) {
                    filterJSApiInfo = filterJSApiInfo + ((String) filterEntry.getKey()) + "(" + filterEntry.getValue() + ")";
                } else {
                    filterJSApiInfo = filterJSApiInfo + MergeUtil.SEPARATOR_KV + ((String) filterEntry.getKey()) + "(" + filterEntry.getValue() + ")";
                }
            }
        }
        if (jsApiNum > 0) {
            h5LogData.param4().add("jsApiNum", Integer.valueOf(jsApiNum)).add("jsApiNames", jsApiList).addNonNullValue("filterJsapiLogs", filterJSApiInfo);
        }
    }

    private String c(String key) {
        try {
            long start = H5Logger.getCorrectStartTime(this.d);
            long end = H5Logger.getLongValue(String.valueOf(this.a.get(key)));
            long elapsed = 0;
            if (end > start) {
                elapsed = end - start;
            }
            return String.valueOf(elapsed);
        } catch (Exception e2) {
            H5Log.e((String) "H5PerformanceLog", (Throwable) e2);
            return "0";
        }
    }

    private void f() {
        if ("yes".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_enableGetSystemWebViewVersion"))) {
            H5Utils.getExecutor(H5ThreadType.NORMAL).execute(new Runnable() {
                public void run() {
                    try {
                        H5PerformanceLogRunnable.b = H5PerformanceLogRunnable.b((Context) LauncherApplicationAgent.getInstance().getApplicationContext());
                        if (TextUtils.isEmpty(H5PerformanceLogRunnable.b)) {
                            H5PerformanceLogRunnable.b = "0.0.0.0";
                        }
                    } catch (Throwable th) {
                        if (TextUtils.isEmpty(H5PerformanceLogRunnable.b)) {
                            H5PerformanceLogRunnable.b = "0.0.0.0";
                        }
                        throw th;
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static String b(Context context) {
        String ua = null;
        try {
            if (VERSION.SDK_INT >= 17) {
                ua = WebSettings.getDefaultUserAgent(context);
                H5Log.d("H5PerformanceLog", "getSystemWebViewVersion, getDefaultUserAgent");
            }
        } catch (Throwable e2) {
            H5Log.e((String) "H5PerformanceLog", "getSystemWebViewVersion" + e2);
        }
        H5Log.d("H5PerformanceLog", "getSystemWebViewVersion, ua = " + ua);
        if (TextUtils.isEmpty(ua)) {
            return "0.0.0.0";
        }
        int chromeTagIndex = ua.indexOf("Chrome/");
        int versionStartIndex = chromeTagIndex + 7;
        if (chromeTagIndex < 0 || versionStartIndex > ua.length()) {
            return "0.0.0.0";
        }
        String sub = ua.substring(versionStartIndex);
        if (TextUtils.isEmpty(sub)) {
            return "0.0.0.0";
        }
        int spaceIndex = sub.indexOf(Token.SEPARATOR);
        if (spaceIndex >= 0) {
            return sub.substring(0, spaceIndex);
        }
        return "0.0.0.0";
    }
}
