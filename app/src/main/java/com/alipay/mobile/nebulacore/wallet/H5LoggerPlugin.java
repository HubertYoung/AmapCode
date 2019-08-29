package com.alipay.mobile.nebulacore.wallet;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.mobile.beehive.capture.utils.PhotoBehavior;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.h5container.api.H5AvailablePageData;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5ErrorCode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5JsCallData;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5PageImageManager;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.appcenter.model.H5Refer;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.data.H5Trace;
import com.alipay.mobile.nebula.dev.H5BugmeIdGenerator;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.log.H5MonitorLogConfig;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5LastKnowLocationProvider;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5NetworkUtil;
import com.alipay.mobile.nebula.util.H5NetworkUtil.Network;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import com.alipay.mobile.nebula.webview.WebViewType;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.log.H5PerformanceLogRunnable;
import com.alipay.mobile.nebulacore.log.ReportDataHandler;
import com.alipay.mobile.nebulacore.log.TinyReportDataHandler;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.util.NebulaUtil;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.alipay.mobile.tinyappcommon.subpackage.TinyAppSubPackagePlugin;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.alipay.sdk.app.statistic.c;
import com.alipay.sdk.util.e;
import com.alipay.sdk.util.j;
import com.autonavi.minimap.ajx3.util.AjxDebugConfig;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class H5LoggerPlugin extends H5SimplePlugin {
    public static final String DSL_ERROR_LOG = "dslErrorLog";
    public static final String KEEP_ALIVE_PAGE_PERFORMANCE = "keepAlivePagePerformance";
    public static final String REPORT_DATA = "reportData";
    public static final String REPORT_TINY_DATA = "reportTinyData";
    private int a;
    private boolean b = false;
    private HashMap<String, String> c;
    private HashMap<String, Object> d;
    private JSONArray e;
    /* access modifiers changed from: private */
    public H5Page f;
    private String g;
    private String h;
    private String i;
    private H5PageData j;
    private H5AvailablePageData k;
    private boolean l;
    private boolean m;
    private String n;
    private String o;
    private int p;
    private boolean q = true;
    private AtomicBoolean r = new AtomicBoolean(false);
    private boolean s = false;
    private String t;
    private String u;
    private H5LogProvider v;
    private String w = "";
    private int x = 0;
    private ReportDataHandler y = new ReportDataHandler();
    private TinyReportDataHandler z = new TinyReportDataHandler();

    public void onInitialize(H5CoreNode coreNode) {
        this.f = (H5Page) coreNode;
        Bundle param = this.f.getParams();
        this.h = this.f.getVersion();
        this.g = H5Utils.getString(param, (String) H5Param.LONG_SAFEPAY_CONTEXT);
        this.i = H5Utils.getString(param, (String) H5Param.LONG_BIZ_SCENARIO);
        this.s = !TextUtils.isEmpty(H5Utils.getString(param, (String) H5Param.OFFLINE_HOST));
        this.j = this.f.getPageData();
        this.k = this.f.getAvailablePageData();
        this.m = false;
        this.o = H5BugmeIdGenerator.getBugmeViewId(this.f);
        H5Logger.bizScenario = this.i;
        this.c = new HashMap<>();
        this.d = new HashMap<>();
        if (!(this.f == null || this.f.getWebView() == null || this.j == null)) {
            if (this.f.getWebView().getType() == WebViewType.SYSTEM_BUILD_IN) {
                this.j.setWebViewType("AndroidWebView");
            } else if (this.f.getWebView().getType() == WebViewType.THIRD_PARTY) {
                this.j.setWebViewType("UCWebView");
            } else if (this.f.getWebView().getType() == WebViewType.RN_VIEW) {
                this.j.setWebViewType("RNView");
            }
        }
        this.v = Nebula.getH5LogHandler();
        this.a = 0;
        this.y.setPageData(this.j);
        this.z.setPageData(this.j);
    }

    public void onRelease() {
        this.f = null;
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext bridgeContext) {
        this.b = event.getActivity() instanceof H5Activity;
        String action = event.getAction();
        JSONObject param = event.getParam();
        if (event.getTarget() instanceof H5Page) {
            this.f = (H5Page) event.getTarget();
        }
        this.j = this.f.getPageData();
        this.j.putBooleanExtra(H5PageData.IS_H5ACTIVITY, this.b);
        this.j.putBooleanExtra(H5PageData.IS_OFFLINE_APP, this.s);
        this.k = this.f.getAvailablePageData();
        if (CommonEvents.H5_TOOLBAR_BACK.equals(action)) {
            b((String) "H5_TOOLBAR_BACK_BT", (String) null);
        } else if (CommonEvents.H5_PAGE_RESUME.equals(action)) {
            c(event);
        } else if (CommonEvents.H5_TOOLBAR_CLOSE.equals(action)) {
            b((String) "H5_TOOLBAR_CLOSE_BT", (String) null);
        } else if (CommonEvents.H5_TOOLBAR_MENU.equals(action)) {
            b((String) "H5_TOOLBAR_MEMU", (String) null);
        } else if (CommonEvents.H5_TOOLBAR_MENU_BT.equals(action)) {
            b((String) "H5_TOOLBAR_MEMU_BT", H5Utils.getString(param, (String) "name"));
        } else if (CommonEvents.H5_TOOLBAR_RELOAD.equals(action)) {
            b((String) "H5_TOOLBAR_REFRESH_BT", (String) null);
        } else if (CommonEvents.H5_TITLEBAR_OPTIONS.equals(action)) {
            b((String) "H5_TITLEBAR_RIGHT_BT", (String) null);
        } else if (CommonEvents.H5_TITLEBAR_SUBTITLE.equals(action)) {
            b((String) "H5_TITLEBAR_SUBTITLE_BT", (String) null);
        } else if (CommonEvents.H5_PAGE_STARTED.equals(action)) {
            if (a(event, param)) {
                return true;
            }
        } else if (CommonEvents.H5_PAGE_ERROR.equals(action)) {
            l(param);
        } else if (CommonEvents.H5_PAGE_CLOSED.equals(action)) {
            b(event);
        } else if (CommonEvents.H5_AL_PAY_RESULT.equals(action)) {
            k(param);
        } else if (CommonEvents.H5_AL_PAY_START.equals(action)) {
            j(param);
        } else if (CommonEvents.H5_TAOBAOSSO_TIME.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_TAOBAOSSO_TIME).param1().add(H5Utils.getString(param, (String) "url"), null).param3().add(this.h, null).param4().addUniteParam(this.j));
        } else if (CommonEvents.H5_TAOBAOSSO_RESULT.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_TAOBAOSSO_RESULT).param1().add(H5Utils.getString(param, (String) "result"), null).param2().add(H5Utils.getString(param, (String) "finalUrl"), null).param3().add(this.h, null).param4().addUniteParam(this.j));
            return true;
        } else if (CommonEvents.H5_SHARE_RESULT.equals(action)) {
            g(param);
            return true;
        } else if (CommonEvents.H5_FAVORITES_RESULT.equals(action)) {
            f(param);
            return true;
        } else if (CommonEvents.H5_LONG_CLICK.equals(action)) {
            b(CommonEvents.H5_LONG_CLICK, H5Utils.getString(param, (String) "itemName"));
            return true;
        } else if (CommonEvents.H5_OPEN_IN_BROWSER.equals(action)) {
            b(CommonEvents.H5_OPEN_IN_BROWSER, (String) null);
            return true;
        } else if (CommonEvents.SET_CLIPBOARD.equals(action)) {
            b((String) CommonEvents.SET_CLIPBOARD, (String) null);
            return true;
        } else if (CommonEvents.H5_AL_SESSION_FROM_NATIVE.equals(action)) {
            h(param);
            return true;
        } else if (CommonEvents.H5_AL_SESSION_TO_NATIVE.equals(action)) {
            b();
            return true;
        } else if (CommonEvents.H5_AL_SESSION_ENTRY_ERROR.equals(action)) {
            b(CommonEvents.H5_AL_SESSION_ENTRY_ERROR, (String) null);
            return true;
        } else if (CommonEvents.H5_AL_SESSION_ENTRYRPC_ERROR.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_AL_SESSION_ENTRYRPC_ERROR).param1().add(H5Logger.DIAGNOSE, null).param2().add(this.j.getPageInfo(), null).param3().add("errorType", "BizError").add("errorCode", H5Utils.getString(param, (String) "errorCode")).param4().addUniteParam(this.j));
            return true;
        } else if (CommonEvents.H5_AL_SESSION_AUTOLOGIN.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_AL_SESSION_AUTOLOGIN).param1().add(H5Logger.MONITOR, null).param2().add(this.j.getPageInfo(), null).param3().add("result", H5Utils.getString(param, (String) "result")).add("url", H5Utils.getString(param, (String) "url")).param4().addUniteParam(this.j));
            return true;
        } else if (CommonEvents.H5_AL_PAY_BEFORE_INTERCEPT.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_AL_PAY_BEFORE_INTERCEPT).param1().add(H5Logger.DIAGNOSE, null).param2().add(this.j.getPageInfo(), null).param3().add("requestUrl", H5Utils.getString(param, (String) "requestUrl")).param4().addUniteParam(this.j));
            return false;
        } else if (H5Logger.H5_GETLOCATION_RESULT.equals(action)) {
            e(param);
            return true;
        } else if ("remoteLog".equals(action)) {
            a(event, bridgeContext);
            return true;
        } else if ("remoteLogging".equals(action)) {
            b(event, bridgeContext);
            return true;
        } else if (CommonEvents.H5_AL_NETWORK_PERFORMANCE_ERROR.equals(action)) {
            d(param);
            return true;
        } else if (CommonEvents.H5_PAGE_ABNORMAL.equals(action)) {
            if (this.m) {
                return true;
            }
            this.m = true;
            this.n = H5Utils.getString(param, (String) "errorType");
            this.p = H5Utils.getInt(param, (String) "errorCode");
            return true;
        } else if (H5Param.REPORT_ABNORMAL.equals(action)) {
            if (param != null) {
                this.e = param.getJSONArray("data");
                this.m = true;
            }
            return true;
        } else if (CommonEvents.H5_PAGE_DOWNLOAD_APK.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_DOWNLOAD_APK").param1().add("apkurl", H5Utils.getString(param, (String) "url")).add("contentLength", Long.valueOf(H5Utils.getLong(param, (String) "contentLength"))).add("inApkWhiteList", Boolean.valueOf(H5Utils.getBoolean(param, (String) "inApkWhiteList", true))).param2().add(this.j.getPageInfo(), null).param4().addUniteParam(this.j));
            return true;
        } else if (CommonEvents.H5_PAGE_INTERCEPT_SCHEME.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_INTERCEPT_SCHEME").param1().add("url", H5Utils.getString(param, (String) "url")).add("scheme", H5Utils.getString(param, (String) "scheme")).add("type", H5Utils.getString(param, (String) "type")).param2().add(this.j.getPageInfo(), null).param4().addUniteParam(this.j));
        } else if (CommonEvents.H5_PAGE_QUERY_PP.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_QUERY_PP").param1().add("queryUrl", H5Utils.getString(param, (String) "queryUrl")).add("queryParam", H5Utils.getString(param, (String) "queryParam")).add("statusCode", H5Utils.getString(param, (String) "statusCode")).param2().add(this.j.getPageInfo(), null).param4().addUniteParam(this.j));
        } else if (CommonEvents.H5_PAGE_QUERY_PP_COST.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_QUERY_PP_COST").param1().add("httpcost", H5Utils.getString(param, (String) "httpcost")).param2().add(this.j.getPageInfo(), null).param4().addUniteParam(this.j));
        } else if (CommonEvents.H5_PAGE_JUMP_PP.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_JUMP_PP").param1().add("detailUrl", H5Utils.getString(param, (String) "detailUrl")).add("origin", H5Utils.getString(param, (String) "origin")).add("type", H5Utils.getString(param, (String) "type")).param2().add(this.j.getPageInfo(), null).param4().addUniteParam(this.j));
        } else if (CommonEvents.H5_PAGE_JUMP_PP_DOWNLOAD.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_JUMP_PP_DOWNLOAD").param1().add(TinyAppSubPackagePlugin.DOWNLOAD_URL, H5Utils.getString(param, (String) TinyAppSubPackagePlugin.DOWNLOAD_URL)).add("type", H5Utils.getString(param, (String) "type")).param2().add(this.j.getPageInfo(), null).param4().addUniteParam(this.j));
        } else if (CommonEvents.H5_PAGE_JUMP_PP_DOWNLOAD_SUCCESS.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_JUMP_PP_DOWNLOAD_SUCCESS").param1().add("type", H5Utils.getString(param, (String) "type")).param2().add(this.j.getPageInfo(), null).param4().addUniteParam(this.j));
        } else if (CommonEvents.H5_VC_OVERLIMIT.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_VC_OVERLIMIT).param1().add("urls", H5Utils.getString(param, (String) "urls")).param2().add("usedMemory", H5Utils.getString(param, (String) "usedMemory")).add("totalMemory", H5Utils.getString(param, (String) "totalMemory")).param4().addUniteParam(this.j));
        } else if (H5Logger.H5_AL_PAGE_RESUME.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId(H5Logger.H5_AL_PAGE_RESUME).param4().addUniteParam(this.j));
        } else if (CommonEvents.H5_DOWNLOAD_APK_RESULT.equals(action)) {
            c(param);
            return true;
        } else if (CommonEvents.H5_FILE_DOWNLOAD.equals(action)) {
            b(param);
            return true;
        } else if (CommonEvents.H5_PAGE_JSAPI_LOGIN.equals(action)) {
            a(param);
            return true;
        } else if (CommonEvents.H5_AL_GETSHARECONTENT_RESULT.equals(action)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_AL_GETSHARECONTENT_RESULT).param1().add(H5Utils.getString(param, (String) "url"), null).param2().add(H5Utils.getString(param, (String) "content"), null).param4().addUniteParam(this.j));
        } else if (CommonEvents.H5_PAGE_PAUSE.equals(action)) {
            a(event);
        } else if (CommonEvents.H5_PAGE_BACK.equals(action)) {
            if (this.y != null) {
                H5Log.d("H5LoggerPlugin", "H5_PAGE_BACK logPageEndWithSpmId " + this.y.getSpmId() + " spmBizType:" + this.y.getSpmBizType() + " chInfo:" + this.y.getChInfo() + " token:" + this.j.getPageToken());
            }
            d(event);
        } else if (KEEP_ALIVE_PAGE_PERFORMANCE.equals(action)) {
            this.q = true;
            if (this.f != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put((String) "fromKeepAlive", (Object) "1");
                this.f.sendEvent(CommonEvents.H5_AL_SESSION_FROM_NATIVE, jsonObject);
            }
            return true;
        } else if (DSL_ERROR_LOG.equals(action)) {
            a();
            return true;
        }
        return false;
    }

    private void a() {
        H5Log.d("H5LoggerPlugin", "DSL_ERROR_LOG");
        this.m = true;
        this.n = "errorRender";
        this.p = H5ErrorCode.BLANK_SCREEN_DSL_ERROR;
        g();
    }

    private void a(JSONObject param) {
        H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_PAGE_JSAPI_LOGIN).param1().add("loginResult", Boolean.valueOf(H5Utils.getBoolean(param, (String) "loginResult", false))).param2().add(this.f.getUrl(), null).param4().add("url", this.f.getUrl()).add("viewId", H5Logger.getContextParam(LogContext.STORAGE_VIEWID)).add(LogContext.STORAGE_REFVIEWID, H5Logger.getContextParam(LogContext.STORAGE_REFVIEWID)).addUniteParam(this.j));
    }

    private void b(JSONObject param) {
        String fileName = H5Utils.getString(param, (String) "fileName");
        H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_FILE_DOWNLOAD).param1().add("url", H5Utils.getString(param, (String) "url") + "&type=" + (fileName.split(".").length == 2 ? fileName.split(".")[1] : "") + "&contentLength=" + H5Utils.getString(param, (String) "contentLength") + "&status=" + H5Utils.getString(param, (String) "status")).param2().add(this.f.getUrl(), null).param4().addUniteParam(this.j));
    }

    private void c(JSONObject param) {
        String status = H5Utils.getString(param, (String) "status");
        H5LogData logData = H5LogData.seedId(CommonEvents.H5_DOWNLOAD_APK_RESULT);
        if ("success".equals(status)) {
            logData.param1().add("url", H5Utils.getString(param, (String) "url")).add("size", H5Utils.getString(param, (String) "apkSize"));
        } else if (e.b.equals(status)) {
            logData.param1().add("errorMsg", H5Utils.getString(param, (String) "errorMsg"));
        }
        H5LogUtil.logNebulaTech(logData.param2().add(this.f.getUrl(), null).param4().addUniteParam(this.j));
    }

    private void d(JSONObject param) {
        int statusCode;
        String targetUrl = H5Utils.getString(param, (String) "targetUrl");
        String appType = H5Utils.getInt(this.f.getParams(), (String) "appType", 2) == 2 ? "online" : BehavorReporter.PROVIDE_BY_LOCAL;
        int status = H5Utils.getInt(param, (String) "status");
        String mimeType = H5Utils.getString(param, (String) "type");
        String method = H5Utils.getString(param, (String) "method");
        long start = H5Utils.getLong(param, (String) H5PageData.KEY_UC_START);
        long duration = H5Utils.getLong(param, (String) "duration");
        long size = H5Utils.getLong(param, (String) "size");
        String isMainDoc = H5Utils.getString(param, (String) "isMainDoc");
        if ("YES".equals(isMainDoc)) {
            statusCode = this.j.getStatusCode();
        } else {
            statusCode = status;
        }
        String errorType = "";
        if (statusCode >= 400) {
            errorType = "errorResponse";
        }
        if (TextUtils.isEmpty(errorType) && duration > 60000) {
            errorType = "longRender";
        }
        H5LogUtil.logH5Exception(H5LogData.seedId("H5_AL_NETWORK_PERFORMANCE_ERROR").param1().add(AjxDebugConfig.PERFORMANCE, null).param2().add("appId", this.j.getAppId()).add("version", this.j.getAppVersion()).add(H5Param.PUBLIC_ID, this.j.getPublicId()).add("url", this.f.getUrl()).param3().add("targetUrl", targetUrl).add("method", method).add("psd", appType).add("status", Integer.valueOf(statusCode)).add("type", mimeType).add("size", Long.valueOf(size)).add(H5PageData.KEY_UC_START, Long.valueOf(start)).add("duration", Long.valueOf(duration)).add("errorType", errorType).add("errorCode", Integer.valueOf(statusCode)).add("isMainDoc", isMainDoc).param4().add("errorType", errorType).add("errorCode", Integer.valueOf(statusCode)).add("isMainDoc", isMainDoc).addUniteParam(this.j));
        if (Nebula.getH5LogHandler() != null) {
            Nebula.getH5LogHandler().upload();
        }
    }

    private void a(final H5Event event, H5BridgeContext bridgeContext) {
        H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                try {
                    H5LoggerPlugin.this.g(event);
                } catch (Exception e) {
                    H5Log.e((String) "H5LoggerPlugin", (Throwable) e);
                }
            }
        });
        if (bridgeContext != null) {
            bridgeContext.sendSuccess();
        }
    }

    private void e(JSONObject param) {
        H5LogData logData = H5LogData.seedId(H5Logger.H5_GETLOCATION_RESULT).param1().add("result", H5Utils.getString(param, (String) "result")).param2().add("locateDuration", Integer.valueOf(H5Utils.getInt(param, (String) "locateDuration"))).add("currentTimestamp", Long.valueOf(System.currentTimeMillis())).param3().add("reGeocodeDuration", Integer.valueOf(H5Utils.getInt(param, (String) "reGeocodeDuration"))).param4().add("locateStart", H5Utils.getString(param, (String) "locateStart")).add("locateEnd", H5Utils.getString(param, (String) "locateEnd")).add("reGeocodeStart", H5Utils.getString(param, (String) "reGeocodeStart")).add("reGeocodeEnd", H5Utils.getString(param, (String) "reGeocodeEnd"));
        if ("error".equals(H5Utils.getString(param, (String) "result"))) {
            logData.param4().add("errorMessage", H5Utils.getString(param, (String) "errorMessage")).add("errorCode", H5Utils.getString(param, (String) "errorCode")).add("errorType", H5Utils.getString(param, (String) "errorType")).addUniteParam(this.j);
        }
        H5LogUtil.logNebulaTech(logData);
    }

    private void f(JSONObject param) {
        H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_FAVORITES_RESULT).param1().add(H5Utils.getString(param, (String) "url"), null).param2().add(H5Utils.getString(param, (String) "description"), null).param3().add("appId", this.j.getAppId()).param4().add("bs", H5Utils.getString(param, (String) H5Param.LONG_BIZ_SCENARIO)).addUniteParam(this.j));
    }

    private void g(JSONObject param) {
        int shareResult = 0;
        String channelName = H5Utils.getString(param, (String) "channelName");
        if (H5Utils.getBoolean(param, (String) "shareResult", false)) {
            shareResult = 1;
        }
        H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_SHARE_RESULT).param1().add(H5Logger.MONITOR, null).param3().add("channelName", channelName).add("shareResult", Integer.valueOf(shareResult)).add("bizType", H5Utils.getString(param, (String) "bizType", (String) "20000067")).param4().add("url", this.f.getShareUrl()).addUniteParam(this.j));
    }

    private void b() {
        String viewId = "";
        if (Nebula.getH5LogHandler() != null) {
            viewId = H5Logger.getContextParam(LogContext.STORAGE_VIEWID);
        }
        H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_AL_SESSION_TO_NATIVE).param1().add(viewId, null).param4().add("url", this.f.getUrl()).addUniteParam(this.j));
    }

    private void h(final JSONObject param) {
        c();
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public void run() {
                if (H5LoggerPlugin.this.f != null) {
                    try {
                        String appId = H5Utils.getString(H5LoggerPlugin.this.f.getParams(), (String) "appId");
                        String highestAppVersion = H5Utils.getString(H5LoggerPlugin.this.f.getParams(), (String) "appVersion");
                        H5LogData h5LogData = H5LogData.seedId(CommonEvents.H5_AL_SESSION_FROM_NATIVE).param1().add(H5LoggerPlugin.this.f.getUrl(), null).param3().add("currentTimestamp", Long.valueOf(System.currentTimeMillis())).add(H5AppUtil.package_nick, H5Utils.getString(H5LoggerPlugin.this.f.getParams(), (String) H5AppUtil.package_nick)).add("install", H5LoggerPlugin.c(appId, highestAppVersion)).addNonNullValue("fromKeepAlive", H5Utils.getString(param, (String) "fromKeepAlive")).param4().addNonNullValue("localmaxnbv", highestAppVersion).addUniteParam(H5LoggerPlugin.this.f.getPageData());
                        if (NebulaUtil.enableRecordStartupParams()) {
                            h5LogData.param2().addMapParam(NebulaUtil.getStartupParamsMap(H5LoggerPlugin.this.f.getParams()));
                        }
                        H5LogUtil.monitorLog(h5LogData, H5MonitorLogConfig.newH5MonitorLogConfig().setLogType(H5MonitorLogConfig.WEBAPP_TYPE).setLogHeader(H5MonitorLogConfig.BEHAVIOR_HEADER));
                    } catch (Throwable throwable) {
                        H5Log.e((String) "H5LoggerPlugin", throwable);
                    }
                }
            }
        });
    }

    private void c() {
        if (this.f != null && this.f.getPageData() != null) {
            this.f.getPageData().putStringExtra(H5PageData.IS_ENTRANCE, "YES");
        }
    }

    private void i(JSONObject param) {
        H5Log.d("H5LoggerPlugins", "MONITOR_PERFORMANCE : praram = " + param);
        if (param != null) {
            JSONArray pagePerformance = param.getJSONArray("data");
            if (pagePerformance != null && pagePerformance.size() > 0) {
                for (int index = 0; index < pagePerformance.size(); index++) {
                    JSONObject object = pagePerformance.getJSONObject(index);
                    String name = H5Utils.getString(object, (String) "name");
                    if ("mixedContent".equals(name)) {
                        this.m = true;
                        this.r.set(true);
                    }
                    H5Trace.event(name, this.o, new String[0]);
                    this.d.put(name, object.get("value"));
                    if ("availableTime".equals(name) && this.f != null) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(name, (Object) H5Utils.getString(object, (String) "value"));
                        this.f.sendEvent(CommonEvents.H5_PAGE_BIZREADY, jsonObject);
                    }
                    a(object, name);
                }
            }
        }
        this.l = true;
    }

    private void a(JSONObject object, String name) {
        try {
            if (TextUtils.equals(name, H5PageData.JS_ERRORS)) {
                String fileName = H5Utils.getString(object, (String) "filename");
                String jsErrorInfo = "fileName=" + fileName + "^line=" + H5Utils.getInt(object, (String) "lineno") + "^desc=" + H5Utils.getString(object, (String) "value") + "^date=" + (object.get("date") != null ? String.valueOf(object.get("date")) : "") + "^colno=" + H5Utils.getInt(object, (String) "colno");
                if (this.x < 10) {
                    if (TextUtils.isEmpty(this.w)) {
                        this.w = jsErrorInfo;
                    } else {
                        this.w += MergeUtil.SEPARATOR_KV + jsErrorInfo;
                    }
                }
                this.x++;
                this.j.putIntExtra(H5PageData.JS_ERRORS, this.x);
                H5Log.d("H5LoggerPlugin", "jsErrorInfo : " + jsErrorInfo);
                if (this.j != null) {
                    H5LogUtil.logH5Exception(H5LogData.seedId("H5_AL_JSERROR").param1().add(this.j.getPageUrl(), null).param3().add(jsErrorInfo, null).param4().addUniteParam(this.j));
                }
            } else if (TextUtils.equals(name, "navigationStart")) {
                long navigationStart = H5Logger.getLongValue(String.valueOf(object.get("value")));
                if (this.j != null) {
                    this.j.setNavigationStart(navigationStart);
                }
            }
        } catch (Exception e2) {
            H5Log.e((String) "H5LoggerPlugin", (Throwable) e2);
        }
    }

    private void a(H5Event event) {
        if (this.y != null) {
            H5Log.d("H5LoggerPlugin", "H5_PAGE_PAUSE logPageEndWithSpmId " + this.y.getSpmId() + " spmBizType:" + this.y.getSpmBizType() + " chInfo:" + this.y.getChInfo() + " token:" + this.j.getPageToken());
        }
        d(event);
        if (this.q) {
            if (this.f == null || this.f.getParams() == null || !H5Utils.getBoolean(this.f.getParams(), (String) H5Param.LONG_ISPRERENDER, false)) {
                H5Log.d("H5LoggerPlugin", "H5_PAGE_PAUSE log performance");
                f(event);
                g();
                this.q = false;
            } else {
                H5Log.d("H5LoggerPlugin", "preRender page return");
                return;
            }
        }
        if (this.b && Nebula.getH5LogHandler() != null) {
            Nebula.getH5LogHandler().upload();
        }
    }

    private void j(JSONObject param) {
        H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_AL_PAY_START).param1().add(H5Utils.getString(param, (String) "url"), null).param3().add("type", H5Utils.getString(param, (String) "type")).add("tradeNo", H5Utils.getString(param, (String) "tradeNo")).addNonNullValue(c.H, H5Utils.getString(param, (String) BioDetector.EXT_KEY_PARTNER_ID)).addNonNullValue("bizType", H5Utils.getString(param, (String) "bizType")).addNonNullValue("bizSubType", H5Utils.getString(param, (String) "bizSubType")).addNonNullValue("bizContext", H5Utils.getString(param, (String) "bizContext")).addNonNullValue("shouldDisplayResultPage", H5Utils.getString(param, (String) "shouldDisplayResultPage")).addNonNullValue("bzContext", H5Utils.getString(param, (String) "bzContext")).addUniteParam(this.j));
    }

    private void k(JSONObject param) {
        H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_AL_PAY_RESULT).param1().add(H5Utils.getString(param, (String) "url"), null).param3().add("type", H5Utils.getString(param, (String) "type")).add("tradeNo", H5Utils.getString(param, (String) "tradeNo")).addNonNullValue(c.H, H5Utils.getString(param, (String) BioDetector.EXT_KEY_PARTNER_ID)).addNonNullValue("bizType", H5Utils.getString(param, (String) "bizType")).addNonNullValue("bizSubType", H5Utils.getString(param, (String) "bizSubType")).addNonNullValue("bizContext", H5Utils.getString(param, (String) "bizContext")).addNonNullValue("shouldDisplayResultPage", H5Utils.getString(param, (String) "shouldDisplayResultPage")).addNonNullValue("bzContext", H5Utils.getString(param, (String) "bzContext")).add("resultCode", H5Utils.getString(param, (String) "resultCode")).add("callbackUrl", H5Utils.getString(param, (String) "callbackUrl")).add(j.a, H5Utils.getString(param, (String) j.a)).add("resultCode", H5Utils.getString(param, (String) "resultCode")).addNonNullValue("errorMessage", H5Utils.getString(param, (String) "errorMessage")).add("orderStr", H5Utils.getString(param, (String) "bzContext", this.g)).addUniteParam(this.j));
    }

    private void b(H5Event event) {
        if (this.f != null) {
            H5PageImageManager.getInstance().uploadLog(this.f.getUrl());
        }
        this.j.onPageEnded(DataflowMonitorModel.METHOD_NAME_CLOSE);
        if (this.q) {
            f(event);
            f();
            g();
            this.q = false;
        }
    }

    private void l(JSONObject param) {
        int errorCode = H5Utils.getInt(param, (String) "errorCode");
        String url = H5Utils.getString(param, (String) "url");
        String type = H5Utils.getString(param, (String) "type");
        String networkType = H5Utils.getString(param, (String) "networkType");
        String deviceInfo = H5Utils.getString(param, (String) "deviceInfo");
        if (!"about:blank".equals(url) && !TextUtils.isEmpty(url)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_PAGE_LOAD_FAIL").param1().add(H5Logger.DIAGNOSE, null).param2().add(this.j.getPageInfo(), null).param3().add("error", Integer.valueOf(errorCode)).param4().add("url", url).add("errorCode", Integer.valueOf(errorCode)).add("type", type).add("networkType", networkType).add("deviceInfo", deviceInfo).addUniteParam(this.j));
        }
    }

    private void c(H5Event event) {
        if (this.y != null) {
            H5Log.d("H5LoggerPlugin", "H5_PAGE_RESUME logPageResumeWithSpmId " + this.y.getSpmId() + " spmBizType:" + this.y.getSpmBizType() + " chInfo:" + this.y.getChInfo() + " token:" + this.j.getPageToken());
        }
        if ("yes".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_resumeResetPageToken"))) {
            this.j.setPageToken(UUID.randomUUID().toString());
        }
        e(event);
        this.z.handlePageResume();
        this.y.handlePageResume();
    }

    private boolean a(H5Event event, JSONObject param) {
        String pageUrl = H5Utils.getString(param, (String) "url");
        this.q = true;
        if ("about:blank".equals(pageUrl)) {
            return true;
        }
        if (TextUtils.isEmpty(pageUrl) && (event.getActivity() instanceof H5Activity) && H5Logger.enableStockTradeLog()) {
            H5Logger.putContextParam(LogContext.STORAGE_VIEWID, H5Utils.getCleanUrl(pageUrl));
        }
        if (d()) {
            e();
        }
        if (this.l) {
            this.l = false;
            f(event);
        }
        this.j.onPageEnded(null);
        g();
        this.e = null;
        return false;
    }

    private void d(H5Event event) {
        this.z.end();
        this.y.endSpm(event);
    }

    private void b(String seedId, String extra) {
        if (!TextUtils.isEmpty(seedId) && this.f != null && this.j != null) {
            H5LogUtil.logNebulaTech(H5LogData.seedId(seedId).param1().add(this.f.getUrl(), null).param2().add(this.j.getPublicId(), null).param3().add(TextUtils.isEmpty(extra) ? this.h : extra + "/" + this.h, null).param4().addUniteParam(this.j));
        }
    }

    private static boolean d() {
        return !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_endSpmClearSpmDetail"));
    }

    private void e(H5Event event) {
        this.z.start();
        this.y.startSpm(event, this.b);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (event == null || event.getParam() == null || event.getAction() == null) {
            return false;
        }
        H5Page page = null;
        if (event.getTarget() instanceof H5Page) {
            page = (H5Page) event.getTarget();
        }
        String action = event.getAction();
        JSONObject param = event.getParam();
        if (page == null) {
            return false;
        }
        if (REPORT_DATA.equals(action)) {
            c(event, context);
            return true;
        } else if (REPORT_TINY_DATA.equals(action)) {
            d(event, context);
            return true;
        } else if ("mtBizReport".equals(action)) {
            String bizName = H5Utils.getString(param, (String) "bizName");
            String subName = H5Utils.getString(param, (String) "subName");
            String failCode = H5Utils.getString(param, (String) "failCode");
            if (TextUtils.isEmpty(bizName) || TextUtils.isEmpty(subName) || TextUtils.isEmpty(failCode)) {
                context.sendError(event, Error.INVALID_PARAM);
            } else {
                try {
                    Map map = new HashMap();
                    JSONObject extParams = H5Utils.getJSONObject(param, "extParams", null);
                    if (extParams != null && !extParams.isEmpty()) {
                        for (String key : extParams.keySet()) {
                            map.put(key, extParams.get(key).toString());
                        }
                    }
                    if (!map.containsKey("appId")) {
                        map.put("appId", H5Utils.getString(page.getParams(), (String) "appId"));
                    }
                    if (!map.containsKey("version")) {
                        map.put("version", H5Utils.getString(page.getParams(), (String) "appVersion"));
                    }
                    H5Logger.mtBizReport(bizName, subName, failCode, map);
                    context.sendSuccess();
                } catch (Exception e2) {
                    H5Log.e((String) "H5LoggerPlugin", (Throwable) e2);
                }
            }
            return true;
        } else if ("pageMonitor".equals(action)) {
            String actionType = H5Utils.getString(event.getParam(), (String) "actionType");
            if (TextUtils.equals("reset", actionType)) {
                if (this.y != null) {
                    H5Log.d("H5LoggerPlugin", "pageMonitor jsapi logPageEndWithSpmId" + this.y.getSpmId() + " spmBizType:" + this.y.getSpmBizType() + " chInfo:" + this.y.getChInfo() + " token:" + this.j.getPageToken());
                    this.y.endSpm(event);
                    this.y.startSpm(event, this.b);
                }
            } else if (TextUtils.equals("end", actionType)) {
                H5Log.d("H5LoggerPlugin", "pageMonitor jsapi logPageEndWithSpmId end");
                if (this.y != null) {
                    this.y.endSpm(event);
                }
            }
            context.sendSuccess();
            return true;
        } else if (!H5Param.MONITOR_PERFORMANCE.equals(action)) {
            return false;
        } else {
            i(param);
            return true;
        }
    }

    private void e() {
        this.z.clearSpmDetail();
        this.y.clearSpmDetail();
    }

    private void f() {
        int stayTime;
        if (this.f != null && this.j != null && !this.m) {
            String appId = this.j.getAppId();
            JSONObject jsonObject = H5Utils.parseObject(H5Environment.getConfig("h5_logNewBlankScreenConfig"));
            if (jsonObject != null && !jsonObject.isEmpty()) {
                String enable = H5Utils.getString(jsonObject, (String) "enable");
                String regex = H5Utils.getString(jsonObject, (String) "appId");
                if (H5NetworkUtil.getInstance().getNetworkType() == Network.NETWORK_WIFI) {
                    stayTime = H5Utils.getInt(jsonObject, (String) "wifiLimit");
                } else {
                    stayTime = H5Utils.getInt(jsonObject, (String) "elseNetWork");
                }
                if (!TextUtils.isEmpty(enable) && !TextUtils.isEmpty(regex) && !TextUtils.isEmpty(appId) && "yes".equalsIgnoreCase(enable) && NebulaUtil.isAppIdMatch(regex, appId)) {
                    if (this.j.getComplete() != 0 || (System.currentTimeMillis() - this.j.getCreate()) / 1000 <= ((long) stayTime)) {
                        if ((System.currentTimeMillis() - this.j.getCreate()) / 1000 < ((long) (H5Utils.getInt(jsonObject, (String) "testFilter") != 0 ? H5Utils.getInt(jsonObject, (String) "testFilter") : 1))) {
                            return;
                        }
                        if (this.f.getWebView() == null || this.f.getWebView().getType() != WebViewType.THIRD_PARTY) {
                            if (this.j.getAppear() == 0) {
                                this.m = true;
                                this.n = "errorRender";
                                this.p = H5ErrorCode.BLANK_SCREEN_OTHER_ERROR;
                                H5Log.d("H5LoggerPlugin", "log Android WebView blank error");
                            }
                        } else if (this.j.getFirstVisuallyRender() == 0) {
                            this.m = true;
                            this.n = "errorRender";
                            this.p = H5ErrorCode.BLANK_SCREEN_OTHER_ERROR;
                            H5Log.d("H5LoggerPlugin", "log UC WebView blank error");
                        }
                    } else {
                        this.m = true;
                        this.n = "errorRender";
                        this.p = H5ErrorCode.BLANK_SCREEN_UNFINISHED_ERROR;
                        H5Log.d("H5LoggerPlugin", "log WebView page unfinished error");
                    }
                }
            }
        }
    }

    private void g() {
        String errorCode;
        if (this.m) {
            H5Log.d("H5LoggerPlugin", "logPageAbnormal " + this.j.getPageUrl());
            this.m = false;
            String url = this.j.getPageUrl();
            if (!TextUtils.isEmpty(url) && !"about:blank".equals(url) && this.k != null) {
                String errorType = null;
                if (!TextUtils.isEmpty(this.n)) {
                    errorType = this.n;
                } else if (this.j.getStatusCode() >= 400 || this.j.getErrorCode() >= 400) {
                    errorType = "errorResponse";
                } else if (this.j.getStatusCode() <= 13 || this.j.getErrorCode() != 7000 || (this.j.getErrorCode() >= 0 && this.j.getErrorCode() <= 6)) {
                    errorType = "errorRequest";
                }
                if (this.p != 0) {
                    errorCode = this.p;
                } else {
                    errorCode = this.j.getStatusCode();
                }
                String errorTrigger = "auto";
                HashMap hashMap = new HashMap();
                if (this.e != null && !this.e.isEmpty()) {
                    for (int index = 0; index < this.e.size(); index++) {
                        JSONObject object = this.e.getJSONObject(index);
                        String name = H5Utils.getString(object, (String) "name");
                        String value = H5Utils.getString(object, (String) "value");
                        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(value)) {
                            hashMap.put(name, value);
                        }
                    }
                    if (hashMap.containsKey("errorType")) {
                        errorType = (String) hashMap.remove("errorType");
                    }
                    if (hashMap.containsKey("errorTrigger")) {
                        errorTrigger = (String) hashMap.remove("errorTrigger");
                    }
                    if (hashMap.containsKey("errorCode")) {
                        errorCode = (String) hashMap.remove("errorCode");
                    }
                    if (TextUtils.isEmpty(errorType)) {
                        errorType = "jsReport";
                    }
                }
                if (this.r.compareAndSet(true, false)) {
                    errorType = "mixedContent";
                }
                r1 = "psd";
                H5LogData abnormalData = H5LogData.seedId("H5_PAGE_ABNORMAL").param1().add("url", url).add("referer", this.j.getReferUrl()).add("errorType", errorType).add("errorCode", errorCode).add("errorTrigger", errorTrigger).add("startRender", Long.valueOf(this.j.getAppear())).add("startRenderFromNative", Long.valueOf(this.j.getAppearFromNative())).add("pageSize", Long.valueOf(this.j.getPageSize())).add("htmlSize", Long.valueOf(this.j.getHtmlSize())).add("status", Integer.valueOf(this.j.getStatusCode())).addNonNullValue("snapshotId", this.j.getMultimediaID()).addNonNullValue("wvErrorCode", this.j.getStringExtra(H5PageData.WEBVIEW_ERROR_CODE, "")).addNonNullValue("wvErrorDesc", this.j.getStringExtra(H5PageData.WEBVIEW_ERROR_DESC, "")).param2().addMapParam(hashMap).param3().add(this.j.getTitle(), null).param4().add("appId", this.j.getAppId()).add("version", this.j.getAppVersion()).add(H5Param.PUBLIC_ID, this.j.getPublicId()).add("psd", H5Utils.getInt(this.f.getParams(), (String) "appType", 2) == 2 ? "online" : BehavorReporter.PROVIDE_BY_LOCAL).addMapParam(H5Logger.getPerformanceDataMap(this.j)).add("stopLoading", Long.valueOf(H5Logger.getCorrectStopLoading(this.k.getStopLoadingTime(), this.j, this.k))).add("stopLoadingWithoutLocating", Long.valueOf(H5Logger.getCorrectStopLoading(this.k.getStopLoadingTimeWithLoc(), this.j, this.k)));
                if (this.j != null && "YES".equals(this.j.getIsTinyApp())) {
                    abnormalData.param4().add("dslVersion", H5Logger.getDslVersion(this.j));
                }
                if ("mixedContent".equals(errorType) && this.d != null && this.d.containsKey("mixedContent")) {
                    abnormalData.param4().add("mixedContent", this.d.get("mixedContent"));
                }
                if (this.j.getNavigationStart() > 0) {
                    abnormalData.param4().add("navigationStart", Long.valueOf(this.j.getNavigationStart()));
                }
                JSONObject ucNetConfig = H5Utils.parseObject(H5Environment.getConfig("h5_ucNetConfig"));
                String useAliNet = "NO";
                if (ucNetConfig != null) {
                    useAliNet = ucNetConfig.getString("useAlipayNet");
                }
                abnormalData.param4().add("type", H5FileUtil.getMimeType(this.f.getUrl())).add("charset", Charset.defaultCharset()).add("viewId", H5Logger.getContextParam(LogContext.STORAGE_REFVIEWID)).add("refviewId", H5Logger.getContextParam(LogContext.STORAGE_VIEWID)).add("isAlipayNetwork", useAliNet).add("SPDY", a(this.j.getPageUrl())).add("end", Long.valueOf(System.currentTimeMillis())).add("isLocal", this.j.getIsLocal()).addMapParam(this.c).addUniteParam(this.j);
                if (!(this.f == null || this.f.getWebView() == null)) {
                    abnormalData.param4().add("webViewVersion", this.f.getWebView().getVersion());
                }
                a(abnormalData);
                abnormalData.param4().add(H5TinyAppLogUtil.TINY_APP_STANDARD_EXTRA_APPXVERSION, H5Utils.getAppxSDKVersion(this.j.getAppId()));
                H5LogUtil.logH5Exception(abnormalData);
                if (Nebula.getH5LogHandler() != null) {
                    Nebula.getH5LogHandler().upload();
                }
            }
        }
    }

    private void a(H5LogData h5LogData) {
        int jsApiNum = 0;
        String jsApiList = "";
        Map jsapiInfoList = this.j.getJsapiInfoList();
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

    private static String a(String url) {
        Pattern pattern = null;
        boolean spdySwitch = false;
        JSONArray spdyBlackList = null;
        String useSpdy = "NO";
        JSONObject jsonObjNetWork = H5Utils.parseObject(H5Environment.getConfig("h5_androidSpdy"));
        if (jsonObjNetWork != null) {
            spdySwitch = "YES".equals(jsonObjNetWork.getString("useSpdy"));
        }
        H5Log.d("H5LoggerPlugin", "online config spdySwitch " + spdySwitch);
        if (spdySwitch) {
            String spdyWhiteList = jsonObjNetWork.getString("whiteList");
            if (!TextUtils.isEmpty(spdyWhiteList)) {
                pattern = H5PatternHelper.compile(spdyWhiteList);
            }
            spdyBlackList = JSON.parseArray(jsonObjNetWork.getString("blackList"));
            H5Log.d("H5LoggerPlugin", "online config  whiteList " + spdyWhiteList + ", blackList " + spdyBlackList.toJSONString());
        }
        String host = H5UrlHelper.parseUrl(url).getHost();
        if (pattern != null && pattern.matcher(host).matches()) {
            useSpdy = "YES";
        }
        if (spdyBlackList == null) {
            return useSpdy;
        }
        for (int i2 = 0; i2 < spdyBlackList.size(); i2++) {
            if (spdyBlackList.getString(i2).equals(host)) {
                return "NO";
            }
        }
        return useSpdy;
    }

    private void f(H5Event event) {
        if (this.f != null) {
            if (this.j.getStart() == 0) {
                H5Log.d("H5LoggerPlugin", "pageData.start == 0 not logPagePerformance");
            } else if (H5Logger.enableStockTradeLog()) {
                H5PageData myData = this.j;
                try {
                    myData = this.j.clone();
                } catch (Throwable throwable) {
                    H5Log.e((String) "H5LoggerPlugin", throwable);
                }
                try {
                    H5Utils.getExecutor(H5ThreadType.NORMAL).execute(new H5PerformanceLogRunnable(myData, (Map) this.d.clone(), this.k, this.f, new String(this.w.getBytes("utf-8"))));
                } catch (Exception e2) {
                    H5Log.d("H5LoggerPlugin", "Exception" + e2);
                }
                this.w = "";
                if (this.y != null) {
                    H5Log.d("H5LoggerPlugin", "logPagePerformance and logPageEndWithSpmId " + this.y.getSpmId() + " spmBizType:" + this.y.getSpmBizType() + " chInfo:" + this.y.getChInfo() + " token:" + this.j.getPageToken());
                }
                d(event);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0021, code lost:
        if ("".equals(r1) != false) goto L_0x0023;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(com.alipay.mobile.h5container.api.H5Event r13, com.alipay.mobile.h5container.api.H5BridgeContext r14) {
        /*
            r12 = this;
            com.alibaba.fastjson.JSONObject r10 = r13.getParam()
            java.lang.String r0 = "logData"
            com.alibaba.fastjson.JSONArray r9 = r10.getJSONArray(r0)
            java.lang.String r0 = "type"
            java.lang.String r1 = r10.getString(r0)
            if (r9 == 0) goto L_0x0018
            int r0 = r9.size()
            if (r0 != 0) goto L_0x0019
        L_0x0018:
            return
        L_0x0019:
            if (r1 == 0) goto L_0x0023
            java.lang.String r0 = ""
            boolean r0 = r0.equals(r1)     // Catch:{ Exception -> 0x00c0 }
            if (r0 == 0) goto L_0x0025
        L_0x0023:
            java.lang.String r1 = "monitor"
        L_0x0025:
            int r0 = r9.size()     // Catch:{ Exception -> 0x00c0 }
            r11 = 1
            if (r0 != r11) goto L_0x003c
            r0 = 0
            java.lang.String r2 = r9.getString(r0)     // Catch:{ Exception -> 0x00c0 }
            java.lang.String r3 = ""
            java.lang.String r4 = ""
            java.lang.String r5 = ""
            r6 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x00c0 }
        L_0x003c:
            int r0 = r9.size()     // Catch:{ Exception -> 0x00c0 }
            r11 = 2
            if (r0 != r11) goto L_0x0056
            r0 = 0
            java.lang.String r2 = r9.getString(r0)     // Catch:{ Exception -> 0x00c0 }
            r0 = 1
            java.lang.String r3 = r9.getString(r0)     // Catch:{ Exception -> 0x00c0 }
            java.lang.String r4 = ""
            java.lang.String r5 = ""
            r6 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x00c0 }
        L_0x0056:
            int r0 = r9.size()     // Catch:{ Exception -> 0x00c0 }
            r11 = 3
            if (r0 != r11) goto L_0x0073
            r0 = 0
            java.lang.String r2 = r9.getString(r0)     // Catch:{ Exception -> 0x00c0 }
            r0 = 1
            java.lang.String r3 = r9.getString(r0)     // Catch:{ Exception -> 0x00c0 }
            r0 = 2
            java.lang.String r4 = r9.getString(r0)     // Catch:{ Exception -> 0x00c0 }
            java.lang.String r5 = ""
            r6 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x00c0 }
        L_0x0073:
            int r0 = r9.size()     // Catch:{ Exception -> 0x00c0 }
            r11 = 4
            if (r0 != r11) goto L_0x0093
            r0 = 0
            java.lang.String r2 = r9.getString(r0)     // Catch:{ Exception -> 0x00c0 }
            r0 = 1
            java.lang.String r3 = r9.getString(r0)     // Catch:{ Exception -> 0x00c0 }
            r0 = 2
            java.lang.String r4 = r9.getString(r0)     // Catch:{ Exception -> 0x00c0 }
            r0 = 3
            java.lang.String r5 = r9.getString(r0)     // Catch:{ Exception -> 0x00c0 }
            r6 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x00c0 }
        L_0x0093:
            int r0 = r9.size()     // Catch:{ Exception -> 0x00c0 }
            r11 = 5
            if (r0 < r11) goto L_0x0018
            r0 = 0
            java.lang.Object r2 = r9.remove(r0)     // Catch:{ Exception -> 0x00c0 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x00c0 }
            r0 = 0
            java.lang.Object r3 = r9.remove(r0)     // Catch:{ Exception -> 0x00c0 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Exception -> 0x00c0 }
            r0 = 0
            java.lang.Object r4 = r9.remove(r0)     // Catch:{ Exception -> 0x00c0 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x00c0 }
            r0 = 0
            java.lang.Object r5 = r9.remove(r0)     // Catch:{ Exception -> 0x00c0 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x00c0 }
            java.lang.String r6 = r9.toJSONString()     // Catch:{ Exception -> 0x00c0 }
            r0 = r12
            r0.a(r1, r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x00c0 }
            goto L_0x0018
        L_0x00c0:
            r8 = move-exception
            java.lang.String r0 = "H5LoggerPlugin"
            java.lang.String r11 = "exception detail"
            com.alipay.mobile.nebula.util.H5Log.e(r0, r11, r8)
            com.alibaba.fastjson.JSONObject r7 = new com.alibaba.fastjson.JSONObject
            r7.<init>()
            java.lang.String r0 = "error"
            r11 = 10
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
            r7.put(r0, r11)
            r14.sendBridgeResult(r7)
            goto L_0x0018
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.wallet.H5LoggerPlugin.b(com.alipay.mobile.h5container.api.H5Event, com.alipay.mobile.h5container.api.H5BridgeContext):void");
    }

    private void a(String type, String ucId, String param1, String param2, String param3, String param4) {
        a(type, null, ucId, param1, param2, param3, param4, "", 0, "");
    }

    private void a(String type, String seedID, String ucId, String param1, String param2, String param3, String param4, String bizType, int logLevel, String actionId) {
        if ("monitor".equals(type) || "behavior".equals(type) || AjxDebugConfig.PERFORMANCE.equals(type) || "monitorWithLocation".equals(type) || "error".equals(type) || "behaviorAuto".equals(type) || "135".equals(type)) {
            if ("monitorWithLocation".equals(type)) {
                H5LastKnowLocationProvider provider = (H5LastKnowLocationProvider) Nebula.getProviderManager().getProvider(H5LastKnowLocationProvider.class.getName());
                if (provider != null) {
                    Location location = provider.getLocation();
                    if (location != null) {
                        param4 = param4 + "^longitude=" + location.getLongitude() + "^latitude=" + location.getLatitude();
                    }
                }
            }
            if ("135".equals(type)) {
                H5Logger.h5BehaviorLogger("135", seedID, i(), this.t, this.u, ucId, param1, param2, param3, param4, h(), logLevel, actionId);
            } else if ("behavior".equals(type) || "monitorWithLocation".equals(type) || "monitor".equals(type)) {
                if (TextUtils.isEmpty(bizType)) {
                    bizType = "H5behavior";
                }
                H5Logger.h5BehaviorLogger(bizType, seedID, i(), this.t, this.u, ucId, param1, param2, param3, param4, h(), logLevel, actionId);
            } else {
                String logHeader = "H-VM";
                if ("error".equals(type)) {
                    logHeader = H5Logger.LOG_HEADER_EM;
                }
                if ("behaviorAuto".equals(type)) {
                    logHeader = H5MonitorLogConfig.BEHAVIOR_HEADER;
                }
                H5Logger.performanceH5Exception(seedID, ucId, param1, param2, param3, param4, h(), logHeader, type);
                if (TextUtils.equals(type, "error") && this.f != null) {
                    H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                    if (h5ConfigProvider != null && h5ConfigProvider.isAliDomains(this.f.getUrl())) {
                        H5Log.debug("H5LoggerPlugin", "is aliDomain upload now ");
                        if (Nebula.getH5LogHandler() != null) {
                            Nebula.getH5LogHandler().upload();
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void g(H5Event event) {
        if (this.f != null && this.f.getParams() != null) {
            JSONObject params = event.getParam();
            int i2 = this.a + 1;
            this.a = i2;
            boolean maxCount = i2 > 1000;
            String seedId = H5Utils.getString(params, (String) "seedId");
            String spm = H5Utils.getString(params, (String) "spmId");
            String abTestInfo = H5Utils.getString(params, (String) "abTestInfo");
            String bizType = H5Utils.getString(params, (String) "bizType");
            this.t = H5Utils.getString(params, (String) "entityId");
            String extLogInfoS = "";
            try {
                if (!(this.f == null || this.f.getParams() == null)) {
                    extLogInfoS = H5Utils.getString(this.f.getParams(), (String) "extLogInfo");
                }
            } catch (Exception e2) {
                H5Log.e((String) "H5LoggerPlugin", (Throwable) e2);
            }
            int logLevel = H5Utils.getInt(params, (String) "logLevel", 0);
            String actionId = H5Utils.getString(params, (String) "actionId");
            if ((TextUtils.isEmpty(actionId) || "clicked".equalsIgnoreCase(actionId)) && !TextUtils.isEmpty(spm)) {
                try {
                    H5Logger.setLastClickSpm(spm);
                } catch (Throwable e3) {
                    H5Log.e((String) "H5LoggerPlugin", e3);
                }
            }
            if (TextUtils.isEmpty(seedId) && TextUtils.isEmpty(spm)) {
                H5Log.w("H5LoggerPlugin", "invalid seedId " + seedId);
            } else if (params == null || params.isEmpty() || maxCount) {
                H5Log.d("H5LoggerPlugin", "param == null || remoteLogCount over limit, remoteLogCount : " + this.a);
            } else {
                String type = H5Utils.getString(params, (String) "type");
                if (!"behavior".equals(type) && !AjxDebugConfig.PERFORMANCE.equals(type) && !"monitorWithLocation".equals(type) && !"error".equals(type) && !"behaviorAuto".equals(type) && !"135".equals(type)) {
                    type = "monitor";
                }
                H5Log.debug("H5LoggerPlugin", "remoteLog type " + type);
                String ucId = H5Utils.getString(params, (String) "ucId");
                String param1 = H5Utils.getString(params, (String) PhotoBehavior.PARAM_1);
                String param2 = H5Utils.getString(params, (String) PhotoBehavior.PARAM_2);
                String param3 = H5Utils.getString(params, (String) PhotoBehavior.PARAM_3);
                Object param4Obj = params.get("param4");
                String param4 = "" + "from=JSAPI^";
                if (param4Obj instanceof JSONObject) {
                    for (String key : ((JSONObject) param4Obj).keySet()) {
                        Object val = ((JSONObject) param4Obj).get(key);
                        if (val instanceof String) {
                            param4 = param4 + key + "=" + val + "^";
                        }
                    }
                }
                if (param4Obj instanceof String) {
                    param4 = param4 + param4Obj;
                }
                if (param4.endsWith("^")) {
                    param4 = param4.substring(0, param4.length() - 1);
                }
                if (!TextUtils.isEmpty(extLogInfoS)) {
                    JSONObject extLogInfoJ = JSONObject.parseObject(extLogInfoS);
                    if (extLogInfoJ != null) {
                        this.u = extLogInfoJ.getString("pageId");
                    }
                }
                if (TextUtils.isEmpty(spm) || this.j == null) {
                    a(type, seedId, ucId, param1, param2, param3, param4, bizType, logLevel, actionId);
                    return;
                }
                H5Log.d("H5LoggerPlugin", "h5RemoteLogClickLogger pageData.pageToken:" + this.j.getPageToken());
                if (this.v != null) {
                    String spmPageId = H5Logger.getPageId(this.j.getPageToken());
                    if (spmPageId != null) {
                        this.u = spmPageId;
                    }
                    H5Logger.h5RemoteLogClickLogger(this.u, spm, bizType, abTestInfo, this.t, param1, param2, param3, param4, h(), logLevel, actionId);
                }
            }
        }
    }

    private void c(H5Event event, H5BridgeContext context) {
        this.y.handleReportData(event, context, this.b);
    }

    private void d(H5Event event, H5BridgeContext context) {
        this.z.handleReportData(event, context);
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.H5_TOOLBAR_BACK);
        filter.addAction(CommonEvents.H5_TOOLBAR_CLOSE);
        filter.addAction(CommonEvents.H5_TOOLBAR_MENU);
        filter.addAction(CommonEvents.H5_TOOLBAR_RELOAD);
        filter.addAction(CommonEvents.H5_TOOLBAR_MENU_BT);
        filter.addAction(CommonEvents.H5_TITLEBAR_OPTIONS);
        filter.addAction(CommonEvents.H5_TITLEBAR_SUBTITLE);
        filter.addAction(CommonEvents.H5_PAGE_STARTED);
        filter.addAction(CommonEvents.H5_PAGE_CLOSED);
        filter.addAction(CommonEvents.H5_PAGE_ERROR);
        filter.addAction("pushWindow");
        filter.addAction(CommonEvents.H5_PAGE_ABNORMAL);
        filter.addAction(CommonEvents.H5_AL_PAY_RESULT);
        filter.addAction(CommonEvents.H5_AL_PAY_START);
        filter.addAction(CommonEvents.H5_TAOBAOSSO_TIME);
        filter.addAction(CommonEvents.H5_TAOBAOSSO_RESULT);
        filter.addAction(CommonEvents.H5_SHARE_RESULT);
        filter.addAction(CommonEvents.H5_FAVORITES_RESULT);
        filter.addAction(H5Param.H5_PAGE_CREATE_WEBVIEW);
        filter.addAction(CommonEvents.H5_AL_SESSION_FROM_NATIVE);
        filter.addAction(CommonEvents.H5_AL_SESSION_TO_NATIVE);
        filter.addAction(CommonEvents.H5_AL_SESSION_ENTRY_ERROR);
        filter.addAction(CommonEvents.H5_AL_SESSION_ENTRYRPC_ERROR);
        filter.addAction(CommonEvents.H5_AL_SESSION_AUTOLOGIN);
        filter.addAction(CommonEvents.H5_AL_NETWORK_PERFORMANCE_ERROR);
        filter.addAction(CommonEvents.H5_AL_PAY_BEFORE_INTERCEPT);
        filter.addAction(CommonEvents.H5_AL_SHARE_RESULT);
        filter.addAction(H5Logger.H5_AL_PAGE_RESUME);
        filter.addAction(H5Logger.H5_GETLOCATION_RESULT);
        filter.addAction("remoteLog");
        filter.addAction("remoteLogging");
        filter.addAction(CommonEvents.H5_LONG_CLICK);
        filter.addAction(CommonEvents.H5_OPEN_IN_BROWSER);
        filter.addAction(H5Param.REPORT_ABNORMAL);
        filter.addAction(H5Param.MONITOR_PERFORMANCE);
        filter.addAction(CommonEvents.H5_PAGE_DOWNLOAD_APK);
        filter.addAction(CommonEvents.H5_PAGE_INTERCEPT_SCHEME);
        filter.addAction(CommonEvents.H5_PAGE_QUERY_PP);
        filter.addAction(CommonEvents.H5_PAGE_QUERY_PP_COST);
        filter.addAction(CommonEvents.H5_PAGE_JUMP_PP);
        filter.addAction(CommonEvents.H5_PAGE_JUMP_PP_DOWNLOAD);
        filter.addAction(CommonEvents.H5_PAGE_JUMP_PP_DOWNLOAD_SUCCESS);
        filter.addAction(CommonEvents.H5_DOWNLOAD_APK_RESULT);
        filter.addAction(CommonEvents.H5_FILE_DOWNLOAD);
        filter.addAction(CommonEvents.H5_PAGE_JSAPI_LOGIN);
        filter.addAction(CommonEvents.H5_VC_OVERLIMIT);
        filter.addAction(CommonEvents.H5_AL_GETSHARECONTENT_RESULT);
        filter.addAction(CommonEvents.H5_PAGE_PAUSE);
        filter.addAction(REPORT_DATA);
        filter.addAction(REPORT_TINY_DATA);
        filter.addAction("mtBizReport");
        filter.addAction(CommonEvents.H5_PAGE_BACK);
        filter.addAction("pageMonitor");
        filter.addAction(CommonEvents.H5_PAGE_RESUME);
        filter.addAction(KEEP_ALIVE_PAGE_PERFORMANCE);
        filter.addAction(DSL_ERROR_LOG);
    }

    private String h() {
        return H5Logger.getUniteParam4(this.j, H5Refer.referUrl, this.i, this.s);
    }

    /* access modifiers changed from: private */
    public static String c(String appId, String version) {
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(version)) {
            return "0";
        }
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider != null) {
            return h5AppProvider.isInstalled(appId, version) ? "1" : "0";
        }
        return "0";
    }

    private String i() {
        if (this.y != null) {
            return this.y.getABTestInfo();
        }
        return null;
    }
}
