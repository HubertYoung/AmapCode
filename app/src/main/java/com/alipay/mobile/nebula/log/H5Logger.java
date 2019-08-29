package com.alipay.mobile.nebula.log;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alipay.android.phone.wallet.spmtracker.SpmTracker;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.api.H5AvailablePageData;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5PageLoader;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.monitor.track.TrackIntegrator;
import com.alipay.mobile.monitor.track.TrackIntegrator.PageInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class H5Logger {
    public static String DIAGNOSE = "diagnose";
    public static final String H5_ABNORMAL_ERROR = "H5_ABNORMAL_ERROR";
    public static final String H5_AL_PAGE_RESUME = "H5_AL_PAGE_RESUME";
    public static final String H5_GETLOCATION_RESULT = "H5_GETLOCATION_RESULT";
    public static final String LOG_HEADER_AM = "H5-AM";
    public static final String LOG_HEADER_EM = "H-EM";
    public static final String LOG_HEADER_VM = "H5-VM";
    public static String MONITOR = "monitor";
    public static final String MTBIZ_H5 = "MTBIZ_H5";
    public static final String TAG = "H5Logger";
    public static String bizScenario = "";

    @Deprecated
    public static void performanceLogger(String seedID, String ucId, String param1, String param2, String param3, String param4, String uniteParam4) {
        performanceLogger(seedID, ucId, param1, param2, param3, param4 + "^" + uniteParam4);
    }

    @Deprecated
    public static void performanceLoggerV2(String seedID, String ucId, String param1, String param2, String param3, String param4, String uniteParam4, String logHeader) {
        performanceLoggerV2(seedID, ucId, param1, param2, param3, param4 + "^" + uniteParam4, logHeader);
    }

    @Deprecated
    public static void performanceLoggerV2(String seedID, String ucId, String param1, String param2, String param3, String param4, String logHeader) {
        exceptionLog(seedID, ucId, param1, param2, param3, param4, logHeader, "");
    }

    @Deprecated
    public static void performanceH5Exception(String seedID, String ucId, String param1, String param2, String param3, String param4, String uniteParam4, String logHeader, String remoteLogType) {
        exceptionLog(seedID, ucId, param1, param2, param3, param4 + "^" + uniteParam4, logHeader, remoteLogType);
    }

    @Deprecated
    public static void performanceLogger(String seedID, String ucId, String param1, String param2, String param3, String params4) {
        if (enableStockTradeLog()) {
            final String str = seedID;
            final String str2 = ucId;
            final String str3 = param1;
            final String str4 = param2;
            final String str5 = param3;
            final String str6 = params4;
            H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                public final void run() {
                    if (H5Logger.getH5LogProvider() != null) {
                        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                        if (h5ConfigProvider != null) {
                            JSONArray jsonArray = H5Utils.parseArray(h5ConfigProvider.getConfigWithProcessCache("h5_log_blackList"));
                            if (jsonArray != null && jsonArray.contains(str)) {
                                H5Log.d(H5Logger.TAG, "h5_log_blackList contain " + str + " not log");
                                return;
                            }
                        }
                        H5Logger.getH5LogProvider().performanceLogger(str, str2, str3, str4, str5, str6, H5Logger.bizScenario);
                    }
                }
            });
        }
    }

    @Deprecated
    private static void exceptionLog(String seedID, String ucId, String param1, String param2, String param3, String params4, String logHeader, String remoteLogType) {
        if (enableStockTradeLog()) {
            final String str = seedID;
            final String str2 = ucId;
            final String str3 = param1;
            final String str4 = param2;
            final String str5 = param3;
            final String str6 = params4;
            final String str7 = logHeader;
            final String str8 = remoteLogType;
            H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                public final void run() {
                    if (H5Logger.getH5LogProvider() != null) {
                        H5Logger.getH5LogProvider().exceptionLog(str, str2, str3, str4, str5, str6, str7, str8, H5Logger.bizScenario);
                    }
                }
            });
        }
    }

    public static void h5BehaviorLogger(String type, String seedID, String abTestInfo, String entityId, String pageId, String ucId, String param1, String param2, String param3, String params4, String uniteParam4, int logLevel, String actionId) {
        if (enableStockTradeLog()) {
            final String str = type;
            final String str2 = seedID;
            final String str3 = abTestInfo;
            final String str4 = entityId;
            final String str5 = pageId;
            final String str6 = ucId;
            final String str7 = param1;
            final String str8 = param2;
            final String str9 = param3;
            final String str10 = params4;
            final String str11 = uniteParam4;
            final int i = logLevel;
            final String str12 = actionId;
            H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                public final void run() {
                    if (H5Logger.getH5LogProvider() != null) {
                        H5Logger.getH5LogProvider().h5BehaviorLogger(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, i, str12, H5Logger.bizScenario);
                    }
                }
            });
        }
    }

    public static void h5RemoteLogClickLogger(String pageId, String spmId, String bizCode, String abTestInfo, String entityId, String param1, String param2, String param3, String params4, String uniteParam4, int logLevel, String actionId) {
        if (enableStockTradeLog()) {
            final String str = pageId;
            final String str2 = spmId;
            final String str3 = bizCode;
            final String str4 = abTestInfo;
            final String str5 = entityId;
            final String str6 = param1;
            final String str7 = param2;
            final String str8 = param3;
            final String str9 = params4;
            final String str10 = uniteParam4;
            final int i = logLevel;
            final String str11 = actionId;
            H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                public final void run() {
                    if (H5Logger.getH5LogProvider() != null) {
                        H5Logger.getH5LogProvider().h5RemoteLogClickLogger(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, i, str11, H5Logger.bizScenario);
                    }
                }
            });
        }
    }

    public static void mtBizReport(final String bizName, final String subName, final String failCode, final Map<String, String> extParams) {
        H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
            public final void run() {
                if (H5Logger.getH5LogProvider() != null) {
                    H5Logger.getH5LogProvider().mtBizReport(bizName, subName, failCode, extParams);
                }
            }
        });
    }

    public static String getPsd(H5PageData pageData, boolean isOfflineApp) {
        if (TextUtils.isEmpty(H5AppUtil.currentPsd)) {
            return (H5AppUtil.isH5ContainerAppId(pageData.getAppId()) || isOfflineApp) ? BehavorReporter.PROVIDE_BY_LOCAL : "online";
        }
        return H5AppUtil.currentPsd;
    }

    public static String getUniteParam4(H5Page h5Page) {
        if (h5Page == null || h5Page.getPageData() == null || h5Page.getParams() == null) {
            return "";
        }
        return getUniteParam4(h5Page.getPageData(), h5Page.getParams());
    }

    public static String getUniteParam4(H5PageData h5PageData, Bundle param) {
        return getUniteParam4(h5PageData, H5Utils.getString(param, (String) H5Param.LONG_BIZ_SCENARIO), param != null && !TextUtils.isEmpty(H5Utils.getString(param, (String) H5Param.OFFLINE_HOST)));
    }

    private static String getUniteParam4(H5PageData h5PageData, String bizScenario2, boolean isOfflineApp) {
        return getUniteParam4(h5PageData, "", bizScenario2, isOfflineApp);
    }

    public static String getUniteParam4(H5PageData h5PageData, String referUrl, String bizScenario2, boolean isOfflineApp) {
        try {
            String title = h5PageData.getTitle();
            boolean isH5Activity = h5PageData.getBooleanExtra(H5PageData.IS_H5ACTIVITY, false);
            try {
                if (!TextUtils.isEmpty(title)) {
                    title = title.trim().replace(Token.SEPARATOR, "").replace(",", "").replace("\n", "");
                }
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
            String customParams = "";
            try {
                customParams = URLEncoder.encode(h5PageData.getCustomParams(), "utf-8");
            } catch (Exception e2) {
                H5Log.e((String) TAG, (Throwable) e2);
            }
            H5Log.debug(TAG, "g5PageData : " + h5PageData.hashCode() + " ,referer :  " + h5PageData.getReferUrl() + " pageUrl:" + h5PageData.getPageUrl());
            String viewId = "";
            String refViewID = "";
            if (isH5Activity) {
                if (!TextUtils.isEmpty(h5PageData.getPageUrl())) {
                    viewId = H5Utils.getCleanUrl(h5PageData.getPageUrl());
                    if (enableStockTradeLog()) {
                        putContextParam(LogContext.STORAGE_VIEWID, viewId);
                    }
                } else if (getH5LogProvider() != null) {
                    viewId = getContextParam(LogContext.STORAGE_VIEWID);
                }
                if (!TextUtils.isEmpty(h5PageData.getReferUrl())) {
                    refViewID = H5Utils.getCleanUrl(h5PageData.getReferUrl());
                    if (enableStockTradeLog() && getH5LogProvider() != null) {
                        putContextParam(LogContext.STORAGE_REFVIEWID, refViewID);
                    }
                } else if (getH5LogProvider() != null) {
                    refViewID = getContextParam(LogContext.STORAGE_REFVIEWID);
                }
            } else if (getH5LogProvider() != null) {
                viewId = getContextParam(LogContext.STORAGE_VIEWID);
                refViewID = getContextParam(LogContext.STORAGE_REFVIEWID);
            }
            String xContentVersion = "";
            if (!TextUtils.isEmpty(h5PageData.getXContentVersion())) {
                xContentVersion = "^xContentVersion=" + h5PageData.getXContentVersion();
            }
            String eagleId = "";
            if (!TextUtils.isEmpty(h5PageData.getEagleId())) {
                eagleId = "^eagleId=" + h5PageData.getEagleId();
            }
            String requestId = "";
            if (!TextUtils.isEmpty(h5PageData.getRequestId())) {
                requestId = "^requestId=" + h5PageData.getRequestId();
            }
            String spmId = "";
            if (!TextUtils.isEmpty(h5PageData.getSpmId())) {
                spmId = "^spmId=" + h5PageData.getSpmId();
            }
            String log = "url=" + H5Utils.getMaxLogStr(h5PageData.getPageUrl()) + xContentVersion + eagleId + requestId + spmId + "^referer=" + H5Utils.getMaxLogStr(h5PageData.getReferUrl()) + "^appId=" + h5PageData.getAppId() + "^version=" + h5PageData.getAppVersion() + "^publicId=" + h5PageData.getPublicId() + "^sourceId=" + H5AppUtil.secAppId + "^psd=" + getPsd(h5PageData, isOfflineApp) + "^viewId=" + viewId + "^refviewId=" + refViewID + "^bizScenario=" + bizScenario2 + "^token=" + h5PageData.getToken() + "^h5Token=" + h5PageData.getH5Token() + "^h5SessionToken=H5Session" + h5PageData.getH5SessionToken() + "^openAppId=" + h5PageData.getOpenAppId() + "^shopId=" + h5PageData.getShopId() + "^title=" + title + "^customParams=" + customParams + "^webViewType=" + h5PageData.getWebViewType() + "^isTinyApp=" + h5PageData.getIsTinyApp();
            if (!TextUtils.isEmpty(h5PageData.getReleaseType())) {
                return log + "^log_release_type=" + h5PageData.getReleaseType();
            }
            return log;
        } catch (Exception e3) {
            H5Log.e((String) TAG, (Throwable) e3);
            return "";
        }
    }

    public static Map<String, Object> getPerformanceDataMap(H5PageData pageData) {
        Map dataMap = new HashMap();
        if (pageData != null) {
            dataMap.put(H5PageData.KEY_UC_START, Long.valueOf(pageData.getStart()));
            dataMap.put("finishLoad", Long.valueOf(pageData.getComplete()));
            dataMap.put("blankScreen", Long.valueOf(pageData.getFirstVisuallyRender()));
            dataMap.put("router", "[" + pageData.getPageUrl() + "{" + pageData.getPageNetLoad() + "}->(" + pageData.getStatusCode() + ")]");
            dataMap.put("cssReqNum", Integer.valueOf(pageData.getCssReqNum()));
            dataMap.put("jsReqNum", Integer.valueOf(pageData.getJsReqNum()));
            dataMap.put("imgReqNum", Integer.valueOf(pageData.getImgReqNum()));
            dataMap.put("otherReqNum", Integer.valueOf(pageData.getOtherReqNum()));
            dataMap.put("requestNum", Integer.valueOf(pageData.getRequestNum()));
            dataMap.put(H5Param.LONG_BIZ_SCENARIO, bizScenario);
            dataMap.put("pageSize", Long.valueOf(pageData.getPageSize()));
            dataMap.put("status", Integer.valueOf(pageData.getStatusCode()));
            dataMap.put("htmlSize", Long.valueOf(pageData.getHtmlSize()));
            dataMap.put("firstByte", Long.valueOf(pageData.getFirstByte()));
            dataMap.put("startRender", Long.valueOf(pageData.getAppear()));
            dataMap.put("startRenderFromNative", Long.valueOf(pageData.getAppearFromNative()));
            dataMap.put("cssSize", Long.valueOf(pageData.getCssSize()));
            dataMap.put("jsSize", Long.valueOf(pageData.getJsSize()));
            dataMap.put("imgSize", Long.valueOf(pageData.getImgSize()));
            dataMap.put("otherSize", Long.valueOf(pageData.getOtherSize()));
            dataMap.put("imageSizeLimit60", Integer.valueOf(pageData.getSizeLimit60()));
            dataMap.put("imageSizeLoadLimit60", Integer.valueOf(pageData.getSizeLoadLimit60()));
            dataMap.put("sizeLimit200", Integer.valueOf(pageData.getSizeLimit200()));
            dataMap.put("302Num", Integer.valueOf(pageData.getNum302()));
            dataMap.put("304Num", Integer.valueOf(pageData.getNum304()));
            dataMap.put("300Num", Integer.valueOf(pageData.getNum300()));
            dataMap.put("404Num", Integer.valueOf(pageData.getNum404()));
            dataMap.put("400Num", Integer.valueOf(pageData.getNum400()));
            dataMap.put("500Num", Integer.valueOf(pageData.getNum500()));
            dataMap.put("1000Num", Integer.valueOf(pageData.getNum1000()));
            dataMap.put("pageNetLoad", Long.valueOf(pageData.getPageNetLoad()));
            dataMap.put("jsLoadNum", Integer.valueOf(pageData.getJsLoadNum()));
            dataMap.put("cssLoadNum", Integer.valueOf(pageData.getCssLoadNum()));
            dataMap.put("imgLoadNum", Integer.valueOf(pageData.getImgLoadNum()));
            dataMap.put("otherLoadNum", Integer.valueOf(pageData.getOtherLoadNum()));
            dataMap.put("requestLoadNum", Integer.valueOf(pageData.getRequestLoadNum()));
            dataMap.put("serverResponse", Long.valueOf(pageData.getPageNetLoad()));
            dataMap.put("imageSizeLimit60Urls", "[" + pageData.getImageSizeLimit60Urls() + "]");
            dataMap.put("sizeLimit200Urls", "[" + pageData.getSizeLimit200Urls() + "]");
            dataMap.put("create", Long.valueOf(pageData.getCreate() != 0 ? pageData.getCreate() : H5PageLoader.sServiceStart));
            dataMap.put("createScenario", pageData.getCreateScenario());
            dataMap.put("ucFirstWebView", Boolean.valueOf(pageData.isUcFistWebView()));
            dataMap.put("aboutBlank", Integer.valueOf(pageData.getAboutBlankNum()));
            dataMap.put("htmlLoadSize", Long.valueOf(pageData.getHtmlLoadSize()));
            dataMap.put(H5Param.FROM_TYPE, pageData.getFromType());
            dataMap.put("srcClick", Long.valueOf(pageData.getSrcClick()));
            dataMap.put("containerVisible", Long.valueOf(pageData.getContainerVisible()));
            dataMap.put(CommonEvents.PRE_RENDER, Integer.valueOf(pageData.getPreRender()));
            dataMap.put("pkg", pageData.getResPkgInfo());
            dataMap.put("netperf", getNetPerfStr(pageData));
            dataMap.put(H5PageData.BRIDGE_READY, pageData.getStringExtra(H5PageData.BRIDGE_READY, ""));
            dataMap.put(H5PageData.KEY_UC_T0, Integer.valueOf(pageData.getIntExtra(H5PageData.KEY_UC_T0, -1)));
            dataMap.put(H5PageData.KEY_UC_T1, Integer.valueOf(pageData.getIntExtra(H5PageData.KEY_UC_T1, -1)));
            dataMap.put(H5PageData.KEY_UC_T2, Integer.valueOf(pageData.getIntExtra(H5PageData.KEY_UC_T2, -1)));
            dataMap.put(H5PageData.KEY_UC_T2_PAINT, Integer.valueOf(pageData.getIntExtra(H5PageData.KEY_UC_T2_PAINT, -1)));
            dataMap.put(H5PageData.KEY_UC_T3, Integer.valueOf(pageData.getIntExtra(H5PageData.KEY_UC_T3, -1)));
        }
        return dataMap;
    }

    public static String getPerformanceData(H5PageData pageData) {
        return "^start=" + pageData.getStart() + "^finishLoad=" + pageData.getComplete() + "^blankScreen=" + pageData.getFirstVisuallyRender() + "^MCCMNC=^router=" + ("[" + pageData.getPageUrl() + "{" + pageData.getPageNetLoad() + "}->(" + pageData.getStatusCode() + ")]") + "^cssReqNum=" + pageData.getCssReqNum() + "^jsReqNum=" + pageData.getJsReqNum() + "^imgReqNum=" + pageData.getImgReqNum() + "^otherReqNum=" + pageData.getOtherReqNum() + "^requestNum=" + pageData.getRequestNum() + "^bizScenario=" + bizScenario + "^pageSize=" + pageData.getPageSize() + "^status=" + pageData.getStatusCode() + "^htmlSize=" + pageData.getHtmlSize() + "^firstByte=" + pageData.getFirstByte() + "^startRender=" + pageData.getAppear() + "^startRenderFromNative=" + pageData.getAppearFromNative() + "^cssSize=" + pageData.getCssSize() + "^jsSize=" + pageData.getJsSize() + "^imgSize=" + pageData.getImgSize() + "^otherSize=" + pageData.getOtherSize() + "^imageSizeLimit60=" + pageData.getSizeLimit60() + "^imageSizeLoadLimit60=" + pageData.getSizeLoadLimit60() + "^sizeLimit200=" + pageData.getSizeLimit200() + "^302Num=" + pageData.getNum302() + "^304Num=" + pageData.getNum304() + "^300Num=" + pageData.getNum300() + "^404Num=" + pageData.getNum404() + "^400Num=" + pageData.getNum400() + "^500Num=" + pageData.getNum500() + "^1000Num=" + pageData.getNum1000() + "^pageNetLoad=" + pageData.getPageNetLoad() + "^jsLoadNum=" + pageData.getJsLoadNum() + "^cssLoadNum=" + pageData.getCssLoadNum() + "^imgLoadNum=" + pageData.getImgLoadNum() + "^otherLoadNum=" + pageData.getOtherLoadNum() + "^requestLoadNum=" + pageData.getRequestLoadNum() + "^serverResponse=" + pageData.getPageNetLoad() + "^imageSizeLimit60Urls=[" + pageData.getImageSizeLimit60Urls() + "]^sizeLimit200Urls=[" + pageData.getSizeLimit200Urls() + "]^create=" + (pageData.getCreate() != 0 ? pageData.getCreate() : H5PageLoader.sServiceStart) + "^createScenario=" + pageData.getCreateScenario() + "^aboutBlank=" + pageData.getAboutBlankNum() + "^htmlLoadSize=" + pageData.getHtmlLoadSize() + "^fromType=" + pageData.getFromType() + "^srcClick=" + pageData.getSrcClick() + "^containerVisible=" + pageData.getContainerVisible() + "^preRender=" + pageData.getPreRender() + "^pkg=" + pageData.getResPkgInfo() + "^netperf=" + getNetPerfStr(pageData);
    }

    public static String getNetPerfStr(H5PageData pageData) {
        return "1" + "_" + pageData.getNetJsReqNum() + "_" + pageData.getNetOtherReqNum() + "_" + pageData.getNetErrorJsNum() + "_" + pageData.getNetErrorOtherNum() + "_" + pageData.getNetJsSize() + "_" + pageData.getNetOtherSize() + "_" + pageData.getNetJsTime() + "_" + pageData.getNetOtherTime();
    }

    public static void reportTabBarLog(Bundle bundle, String seedId, String logHeader) {
        H5LogUtil.logNebulaTech(H5LogData.seedId(seedId).param4().add("appId", H5Utils.getString(bundle, (String) "appId")).add("url", H5Utils.getString(bundle, (String) "url")).add("version", H5Utils.getString(bundle, (String) "appVersion")));
    }

    public static boolean enableStockTradeLog() {
        return H5Flag.isUploadLog;
    }

    public static long getLongValue(String endValue) {
        if (TextUtils.isEmpty(endValue)) {
            return 0;
        }
        if (endValue.contains(".")) {
            return Long.valueOf(endValue.substring(0, endValue.indexOf("."))).longValue();
        }
        return Long.valueOf(endValue).longValue();
    }

    public static long getCorrectStopLoading(long loadingValue, H5PageData pageData, H5AvailablePageData availablePageData) {
        if (pageData == null || availablePageData == null) {
            return 0;
        }
        if (loadingValue == 0) {
            return System.currentTimeMillis() - getCorrectStartTime(pageData);
        }
        return loadingValue - getCorrectStartTime(pageData);
    }

    public static long getCorrectStartTime(H5PageData pageData) {
        if (pageData == null) {
            return 0;
        }
        return pageData.getNavigationStart() > 0 ? pageData.getNavigationStart() : pageData.getStart();
    }

    public static String getDslVersion(H5PageData pageData) {
        if (pageData == null) {
            return "";
        }
        String pkgInfo = pageData.getResPkgInfo();
        if (TextUtils.isEmpty(pkgInfo) || !pkgInfo.contains("66666692")) {
            return "";
        }
        String[] pkgList = pkgInfo.split("\\|");
        if (pkgList == null || pkgList.length == 0) {
            return "";
        }
        for (String appInfo : pkgList) {
            if (appInfo.contains("66666692")) {
                String[] infoList = appInfo.split("_");
                if (infoList == null || infoList.length != 3) {
                    return "";
                }
                return infoList[2];
            }
        }
        return "";
    }

    public static H5LogProvider getH5LogProvider() {
        H5Service service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (service != null) {
            return (H5LogProvider) service.getProviderManager().getProvider(H5LogProvider.class.getName());
        }
        return null;
    }

    public static void putContextParam(String key, String value) {
        LoggerFactory.getLogContext().putContextParam(key, value);
    }

    public static String getContextParam(String key) {
        return LoggerFactory.getLogContext().getContextParam(key);
    }

    public static void setLastClickSpm(String spm) {
        SpmTracker.setLastClickSpm(spm);
    }

    public static String getToken() {
        return LoggerFactory.getLogContext().getContextParam(LogContext.LOCAL_STORAGE_ACTIONTOKEN);
    }

    public static String getSpmRpcId(Object view) {
        try {
            return SpmTracker.getMiniPageId(view);
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
            return "";
        }
    }

    public static String getAutoSpmRpcId(Object view) {
        PageInfo pageInfo = TrackIntegrator.getInstance().getAutoPageInfoByView(view);
        if (pageInfo == null || TextUtils.isEmpty(pageInfo.miniPageId) || !pageInfo.needRpc) {
            return null;
        }
        return pageInfo.miniPageId;
    }

    public static String getPageId(Object view) {
        return SpmTracker.getPageId(view);
    }

    public static String getSrcSpm(Object view) {
        try {
            return SpmTracker.getSrcSpm(view);
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
            return "";
        }
    }

    public static String getLastClickSpmId() {
        try {
            return SpmTracker.getLastClickSpmId();
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
            return "";
        }
    }

    public static Map<String, String> getTracerInfo(Object view) {
        try {
            return SpmTracker.getTracerInfo(view);
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
            return null;
        }
    }

    public static Long getTrackLastClickTime() {
        return Long.valueOf(TrackIntegrator.getInstance().lastClickTime);
    }
}
