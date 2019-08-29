package com.alipay.mobile.nebulacore.wallet;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.wallet.spmtracker.SpmTracker;
import com.alipay.android.phone.wallet.spmtracker.TinyTracker;
import com.alipay.mobile.beehive.capture.utils.PhotoBehavior;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.monitor.track.TrackAutoHelper;
import com.alipay.mobile.monitor.track.TrackIntegrator;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.log.H5BehaviorLogConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.log.H5MonitorLogConfig;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import com.alipay.mobile.nebulacore.util.H5NebulaUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class H5LogProviderImpl implements H5LogProvider {
    public static final String H5_SHOW_LOG_UPLOAD_CONFIG = "h5_show_log_upload";
    private static JSONArray a;

    private class H5AutoClick implements Runnable {
        private H5Event b;

        H5AutoClick(H5Event h5Event) {
            this.b = h5Event;
        }

        public void run() {
            JSONObject params = H5Utils.getJSONObject(this.b.getParam(), "param", null);
            String abTestInfo = H5Utils.getString(params, (String) "abTestInfo");
            String entityId = H5Utils.getString(params, (String) "entityId");
            String spmId = H5Utils.getString(params, (String) "spmId");
            int logLevel = H5Utils.getInt(params, (String) "logLevel");
            JSONObject param1 = H5Utils.getJSONObject(params, PhotoBehavior.PARAM_1, null);
            JSONObject param2 = H5Utils.getJSONObject(params, PhotoBehavior.PARAM_2, null);
            JSONObject param3 = H5Utils.getJSONObject(params, PhotoBehavior.PARAM_3, null);
            JSONObject param4 = H5Utils.getJSONObject(params, "param4", null);
            String ucId = H5Utils.getString(params, (String) "ucId");
            String xPath = H5Utils.getString(params, (String) "xPath");
            Behavor behavor = new Behavor();
            H5LogProviderImpl.b(behavor, param1);
            H5LogProviderImpl.b(behavor, param2);
            H5LogProviderImpl.b(behavor, param3);
            H5LogProviderImpl.b(behavor, param4);
            behavor.setViewID(xPath);
            behavor.setUserCaseID(ucId);
            behavor.setEntityContentId(entityId);
            behavor.setBehaviourPro(TrackAutoHelper.AUTO_TRACK_TYPE);
            behavor.setSeedID(spmId);
            behavor.setAbTestInfo(abTestInfo);
            if (this.b.getTarget() instanceof H5Page) {
                H5Page h5Page = (H5Page) this.b.getTarget();
                if (!(h5Page == null || h5Page.getPageData() == null)) {
                    H5LogProviderImpl.b(behavor, H5Logger.getUniteParam4(h5Page.getPageData(), h5Page.getParams()));
                }
            }
            behavor.addExtParam(Performance.KEY_LOG_HEADER, H5Logger.LOG_HEADER_AM);
            if (logLevel != 0) {
                if (logLevel == 1) {
                    behavor.setLoggerLevel(1);
                } else if (logLevel == 2) {
                    behavor.setLoggerLevel(2);
                } else if (logLevel == 3) {
                    behavor.setLoggerLevel(3);
                }
            }
            LoggerFactory.getBehavorLogger().autoClick(behavor);
            H5Log.d("H5WalletLogProvider", " spmId:" + spmId + " abTestInfo:" + abTestInfo + "param1:" + param1 + ", ### param2:" + param2 + ", ###param3:" + param3 + ",  ###param4:" + behavor.getExtParams().toString());
        }
    }

    static {
        JSONArray jSONArray = new JSONArray();
        a = jSONArray;
        jSONArray.add("H5_PAGE_PERFORMANCE|H5_AL_SESSION_FROM_NATIVE|H5_AL_SESSION_MAP_SUCCESS|H5_AL_SESSION_FALLBACK|H5_GETLOCATION_RESULT");
    }

    public void log(String logName, String param1, String param2, String param3, String param4, String unitParam4) {
        H5Logger.performanceLogger(logName, null, param1, param2, param3, param4, unitParam4);
    }

    public void logV2(String logName, String param1, String param2, String param3, String param4, String unitParam4, String logHeader) {
        H5Logger.performanceLoggerV2(logName, null, param1, param2, param3, param4, unitParam4, logHeader);
    }

    public void log(String logName, String param1, String param2, String param3, String param4) {
        H5Logger.performanceLogger(logName, null, param1, param2, param3, param4);
    }

    public void logV2(String logName, String param1, String param2, String param3, String param4, String logHeader) {
        H5Logger.performanceLoggerV2(logName, null, param1, param2, param3, param4, logHeader);
    }

    public void upload() {
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public void run() {
                LoggerFactory.getLogContext().upload(LogCategory.CATEGORY_WEBAPP);
            }
        });
    }

    public void monitorLog(final H5LogData logData, final H5MonitorLogConfig logConfig) {
        if (H5Logger.enableStockTradeLog()) {
            H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                public void run() {
                    if (logData == null || logConfig == null) {
                        H5Log.e((String) "H5WalletLogProvider", (String) "logData == null || logConfig == null, monitorLog error!");
                        return;
                    }
                    Performance performance = new Performance();
                    String seedID = logData.getSeedId();
                    String param1 = H5LogProviderImpl.b(logData.getParam1Map());
                    String param2 = H5LogProviderImpl.b(logData.getParam2Map());
                    String param3 = H5LogProviderImpl.b(logData.getParam3Map());
                    String param4 = H5LogProviderImpl.b(logData.getParam4Map());
                    performance.setSubType(seedID);
                    performance.setParam1(param1);
                    performance.setParam2(param2);
                    performance.setParam3(param3);
                    Map extParam = logData.getParam4Map();
                    if (extParam != null && extParam.size() > 0) {
                        for (Entry entry : extParam.entrySet()) {
                            performance.addExtParam((String) entry.getKey(), (String) entry.getValue());
                        }
                    }
                    Map headerMap = new HashMap();
                    headerMap.put(Performance.KEY_LOG_HEADER, logConfig.getLogHeader());
                    String logType = logConfig.getLogType();
                    H5LoggerSwitch.printApLog(seedID, param1, param2, param3, param4, logType);
                    if (TextUtils.equals(H5MonitorLogConfig.H5EXCEPTION_TYPE, logType) && H5LoggerSwitch.isUploadMdap(seedID, param1, param2, param3, param4)) {
                        LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_H5EXCEPTION, performance, headerMap);
                    }
                    if (TextUtils.equals(H5MonitorLogConfig.WEBAPP_TYPE, logType) && H5LoggerSwitch.isUploadMdap(seedID, param1, param2, param3, param4)) {
                        LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_WEBAPP, performance, headerMap);
                        if (extParam != null && "YES".equalsIgnoreCase(extParam.get("isTinyApp"))) {
                            LoggerFactory.getMonitorLogger().performance((String) H5MonitorLogConfig.WEBAPP_TINY_TYPE, performance);
                        }
                    }
                }
            });
        }
    }

    public void behaviorLog(final H5LogData logData, final H5BehaviorLogConfig logConfig) {
        if (H5Logger.enableStockTradeLog()) {
            H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                public void run() {
                    if (logData == null || logConfig == null) {
                        H5Log.e((String) "H5WalletLogProvider", (String) "logData == null || logConfig == null, behaviorLog error!");
                        return;
                    }
                    String seedID = logData.getSeedId();
                    String param1 = H5LogProviderImpl.b(logData.getParam1Map());
                    String param2 = H5LogProviderImpl.b(logData.getParam2Map());
                    String param3 = H5LogProviderImpl.b(logData.getParam3Map());
                    String param4 = H5LogProviderImpl.b(logData.getParam4Map());
                    Behavor behavor = new Behavor();
                    int logLevel = logConfig.getLogLevel();
                    if (logLevel == 1) {
                        behavor.setLoggerLevel(1);
                    } else if (logLevel == 2) {
                        behavor.setLoggerLevel(2);
                    } else if (logLevel == 3) {
                        behavor.setLoggerLevel(3);
                    }
                    behavor.setBehaviourPro(logConfig.getBehaviourPro());
                    behavor.setUserCaseID(logConfig.getUserCaseId());
                    if (H5DevConfig.getBooleanConfig(H5LogProviderImpl.H5_SHOW_LOG_UPLOAD_CONFIG, false)) {
                        behavor.setSeedID("a." + seedID);
                    } else {
                        behavor.setSeedID(seedID);
                    }
                    behavor.setAbTestInfo(logConfig.getAbTestInfo());
                    behavor.setEntityContentId(logConfig.getEntityContentId());
                    if (!TextUtils.isEmpty(logConfig.getPageId())) {
                        behavor.setPageId(logConfig.getPageId());
                    }
                    behavor.setParam1(param1);
                    behavor.setParam2(param2);
                    behavor.setParam3(param3);
                    Map extParam = logData.getParam4Map();
                    if (extParam != null && extParam.size() > 0) {
                        for (Entry entry : extParam.entrySet()) {
                            behavor.addExtParam((String) entry.getKey(), (String) entry.getValue());
                        }
                    }
                    String behaviourType = logConfig.getBehaviourPro();
                    boolean readConfig = false;
                    if (TextUtils.equals(H5BehaviorLogConfig.NEBULA_TCEH_BEHAVIOUR, behaviourType) || TextUtils.equals(H5BehaviorLogConfig.H5SECURITY_BEHAVIOUR, behaviourType) || TextUtils.equals(H5BehaviorLogConfig.WEBSTAT_BEHAVIOUR, behaviourType)) {
                        H5LoggerSwitch.printApLog(seedID, param1, param2, param3, param4, behaviourType);
                        readConfig = true;
                    }
                    behavor.addExtParam(Performance.KEY_LOG_HEADER, H5Logger.LOG_HEADER_VM);
                    if (!TextUtils.isEmpty(logConfig.getActionId())) {
                        LoggerFactory.getBehavorLogger().event(logConfig.getActionId(), behavor);
                    } else if (!readConfig || H5LoggerSwitch.isUploadMdap(seedID, param1, param2, param3, param4)) {
                        LoggerFactory.getBehavorLogger().click(behavor);
                    } else {
                        H5Log.d("H5WalletLogProvider", "H5LoggerSwitch not upload : " + seedID);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static String b(Map<String, String> params) {
        if (params == null || params.size() == 0) {
            return "";
        }
        StringBuilder paramBuilder = new StringBuilder();
        boolean isFirst = true;
        for (Entry entry : params.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key != null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    paramBuilder.append('^');
                }
                if (params.size() != 1 || !TextUtils.isEmpty(value)) {
                    paramBuilder.append(key).append('=').append(value);
                } else {
                    paramBuilder.append(key);
                }
            }
        }
        return paramBuilder.toString();
    }

    public void exceptionLog(String seedID, String ucId, String param1, String param2, String param3, String params4, String logHeader, String remoteLogType, String bizScenario) {
        String param4 = params4;
        Performance performance = new Performance();
        performance.setSubType(seedID);
        performance.setParam1(param1);
        performance.setParam2(param2);
        performance.setParam3(param3);
        if (!TextUtils.isEmpty(ucId)) {
            if (!TextUtils.isEmpty(param4)) {
                param4 = param4 + "^";
            }
            param4 = param4 + "ucId=" + ucId;
        }
        String param42 = param4 + "^bizScenario=" + bizScenario;
        Performance performance2 = a(performance, param42);
        Map headerMap = new HashMap();
        headerMap.put(Performance.KEY_LOG_HEADER, logHeader);
        if (!TextUtils.equals(seedID, "H5_PAGE_ABNORMAL") && !TextUtils.equals(seedID, "H5_AL_NETWORK_PERFORMANCE_ERROR") && !TextUtils.equals(remoteLogType, "error") && !TextUtils.equals(seedID, "H5_AL_PAGE_JSERROR")) {
            a(performance2, seedID, ucId, param1, param2, param3, param42, headerMap);
        } else if (H5LoggerSwitch.isUploadMdap(seedID, param1, param2, param3, param42)) {
            LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_H5EXCEPTION, performance2, headerMap);
        }
    }

    public void performanceLogger(String seedID, String ucId, String param1, String param2, String param3, String params4, String bizScenario) {
        String param4 = params4;
        Performance performance = new Performance();
        performance.setSubType(seedID);
        performance.setParam1(param1);
        performance.setParam2(param2);
        performance.setParam3(param3);
        if (!TextUtils.isEmpty(ucId)) {
            if (!TextUtils.isEmpty(param4)) {
                param4 = param4 + "^";
            }
            param4 = param4 + "ucId=" + ucId;
        }
        String param42 = param4 + "^bizScenario=" + bizScenario;
        a(a(performance, param42), seedID, ucId, param1, param2, param3, param42, null);
    }

    public void h5BehaviorLogger(String type, String seedID, String abTestInfo, String entityId, String pageId, String ucId, String param1, String param2, String param3, String params4, String uniteParam4, int logLevel, String actionId, String bizScenario) {
        String param4 = params4;
        try {
            Behavor behavor = new Behavor();
            if (logLevel != 0) {
                if (logLevel == 1) {
                    behavor.setLoggerLevel(1);
                } else if (logLevel == 2) {
                    behavor.setLoggerLevel(2);
                } else if (logLevel == 3) {
                    behavor.setLoggerLevel(3);
                }
            }
            behavor.setBehaviourPro(type);
            behavor.setUserCaseID(ucId);
            if (H5DevConfig.getBooleanConfig(H5_SHOW_LOG_UPLOAD_CONFIG, false)) {
                behavor.setSeedID("a." + seedID);
            } else {
                behavor.setSeedID(seedID);
            }
            behavor.setSeedID(seedID);
            behavor.setAbTestInfo(abTestInfo);
            behavor.setEntityContentId(entityId);
            if (!TextUtils.isEmpty(pageId)) {
                behavor.setPageId(pageId);
            }
            behavor.setParam1(param1);
            behavor.setParam2(param2);
            behavor.setParam3(param3);
            b(behavor, (param4 + "^bizScenario=" + bizScenario) + "^" + uniteParam4);
            behavor.addExtParam(Performance.KEY_LOG_HEADER, H5Logger.LOG_HEADER_VM);
            if (TextUtils.isEmpty(actionId)) {
                LoggerFactory.getBehavorLogger().click(behavor);
            } else {
                LoggerFactory.getBehavorLogger().event(actionId, behavor);
            }
            H5Log.d("H5WalletLogProvider", "h5BehavorLogger!!! seedId:" + seedID + " param1:" + param1 + ", ### param2:" + param2 + ", ###param3:" + param3 + ",  ###param4:" + behavor.getExtParams().toString() + " logLevel:" + logLevel + " actionId:" + actionId + " behaviourPro:" + type);
        } catch (Throwable e) {
            H5Log.e((String) "H5WalletLogProvider", e);
        }
    }

    public void h5RemoteLogClickLogger(String pageId, String spmId, String bizCode, String abTestInfo, String entityId, String param1, String param2, String param3, String params4, String uniteParam4, int logLevel, String actionId, String bizScenario) {
        String param4 = params4;
        try {
            Behavor behavor = new Behavor();
            if (logLevel != 0) {
                if (logLevel == 1) {
                    behavor.setLoggerLevel(1);
                } else if (logLevel == 2) {
                    behavor.setLoggerLevel(2);
                } else if (logLevel == 3) {
                    behavor.setLoggerLevel(3);
                }
            }
            behavor.setUserCaseID("UC-KB");
            behavor.setSeedID(spmId);
            behavor.setAbTestInfo(abTestInfo);
            behavor.setEntityContentId(entityId);
            behavor.setBehaviourPro(bizCode);
            if (!TextUtils.isEmpty(pageId)) {
                behavor.setPageId(pageId);
            }
            behavor.setParam1(param1);
            behavor.setParam2(param2);
            behavor.setParam3(param3);
            behavor.addExtParam(Performance.KEY_LOG_HEADER, H5Logger.LOG_HEADER_VM);
            b(behavor, (param4 + "^bizScenario=" + bizScenario) + "^" + uniteParam4);
            if (TextUtils.isEmpty(actionId)) {
                LoggerFactory.getBehavorLogger().click(behavor);
            } else {
                LoggerFactory.getBehavorLogger().event(actionId, behavor);
            }
            H5Log.d("H5WalletLogProvider", "h5RemoteLogClickLogger!!! seedId:" + spmId + " abTestInfo:" + abTestInfo + " entityId:" + entityId + " param1:" + param1 + ", ### param2:" + param2 + ", ###param3:" + param3 + ",  ###param4:" + behavor.getExtParams().toString());
        } catch (Throwable t) {
            H5Log.e((String) "H5WalletLogProvider", t);
        }
    }

    public void mtBizReport(String bizName, String subName, String failCode, Map<String, String> extParams) {
        LoggerFactory.getMonitorLogger().mtBizReport(bizName, subName, failCode, extParams);
    }

    public void autoClick(H5Event event) {
        H5ThreadPoolFactory.getSingleThreadExecutor().execute(new H5AutoClick(event));
    }

    /* access modifiers changed from: private */
    public static void b(Behavor behavor, JSONObject jsonObject) {
        if (jsonObject != null && !jsonObject.isEmpty()) {
            for (String key : jsonObject.keySet()) {
                try {
                    behavor.addExtParam(key, jsonObject.get(key).toString());
                } catch (Exception e) {
                    H5Log.e((String) "H5WalletLogProvider", (Throwable) e);
                }
            }
        }
    }

    private static Performance a(Performance p, String paramStr) {
        String[] params;
        try {
            if (TextUtils.isEmpty(paramStr)) {
                return p;
            }
            if (!paramStr.contains("^")) {
                p.addExtParam("param4", paramStr);
                return p;
            }
            int extIndex = 0;
            for (String param : paramStr.split("\\^")) {
                if (!TextUtils.isEmpty(param)) {
                    if (!param.contains("=")) {
                        p.addExtParam(new StringBuilder(ProcessInfo.ALIAS_EXT).append(extIndex).toString(), param);
                        extIndex++;
                    } else {
                        int index = param.indexOf("=");
                        String key = param.substring(0, index);
                        String value = "";
                        if (index < param.length() - 1) {
                            value = param.substring(index + 1);
                        }
                        p.addExtParam(key, value);
                    }
                }
            }
            return p;
        } catch (Exception e) {
            H5Log.e((String) "H5WalletLogProvider", (Throwable) e);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static void b(Behavor behavor, String paramStr) {
        String[] params;
        if (behavor != null && !TextUtils.isEmpty(paramStr)) {
            if (!paramStr.contains("^")) {
                behavor.addExtParam("param4", paramStr);
                return;
            }
            int extIndex = 0;
            for (String param : paramStr.split("\\^")) {
                if (!TextUtils.isEmpty(param)) {
                    if (!param.contains("=")) {
                        behavor.addExtParam(new StringBuilder(ProcessInfo.ALIAS_EXT).append(extIndex).toString(), param);
                        extIndex++;
                    } else {
                        int index = param.indexOf("=");
                        String key = param.substring(0, index);
                        String value = "";
                        if (index < param.length() - 1) {
                            value = param.substring(index + 1);
                        }
                        if (behavor.getExtParams() == null || !behavor.getExtParams().containsKey(key)) {
                            behavor.addExtParam(key, value);
                        } else {
                            H5Log.d("H5WalletLogProvider", "key " + key + " alerday exist not add");
                        }
                    }
                }
            }
        }
    }

    private static void a(Performance performance, String seedID, String ucId, String param1, String param2, String param3, String param4, Map<String, String> headerMap) {
        if (H5Logger.enableStockTradeLog()) {
            String logNebulaTechEnable = H5NebulaUtil.getConfigWithProcessCache("h5_logNebulaTechEnable");
            boolean needWebAppLog = false;
            if (TextUtils.isEmpty(logNebulaTechEnable)) {
                needWebAppLog = true;
            } else if (logNebulaTechEnable.equalsIgnoreCase("yes")) {
                JSONArray h5LogWebAppWhiteList = H5Utils.parseArray(H5NebulaUtil.getConfigWithProcessCache("h5_logWebAppWhitelist"));
                if (h5LogWebAppWhiteList == null || h5LogWebAppWhiteList.isEmpty()) {
                    h5LogWebAppWhiteList = a;
                }
                if (!h5LogWebAppWhiteList.isEmpty()) {
                    int i = 0;
                    while (true) {
                        if (i >= h5LogWebAppWhiteList.size()) {
                            break;
                        }
                        String whiteItem = h5LogWebAppWhiteList.getString(i);
                        if (!TextUtils.isEmpty(whiteItem) && H5PatternHelper.matchRegex(whiteItem, seedID)) {
                            needWebAppLog = true;
                            H5Log.d("H5WalletLogProvider", "seedID match : " + seedID);
                            break;
                        }
                        i++;
                    }
                }
            }
            if (!needWebAppLog) {
                Behavor behavor = new Behavor();
                behavor.setLoggerLevel(2);
                if (H5DevConfig.getBooleanConfig(H5_SHOW_LOG_UPLOAD_CONFIG, false)) {
                    seedID = "a." + seedID;
                }
                behavor.setSeedID(seedID);
                behavor.setBehaviourPro(H5BehaviorLogConfig.NEBULA_TCEH_BEHAVIOUR);
                behavor.setUserCaseID(ucId);
                behavor.addExtParam(Performance.KEY_LOG_HEADER, H5Logger.LOG_HEADER_VM);
                behavor.setParam1(param1);
                behavor.setParam2(param2);
                behavor.setParam3(param3);
                b(behavor, param4);
                if (H5LoggerSwitch.isUploadMdap(seedID, param1, param2, param3, param4)) {
                    LoggerFactory.getBehavorLogger().click(behavor);
                    return;
                }
                return;
            }
            H5Log.d("H5WalletLogProvider", "seedId:" + seedID + " ### param1:" + param1 + ", ### param2:" + param2 + ", ###param3:" + param3 + ",  ###param4:" + param4);
            if (headerMap == null) {
                LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_WEBAPP, performance);
            } else {
                LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_WEBAPP, performance, headerMap);
            }
        }
    }

    public void logAutoBehavorPageStart(String spm, Object view) {
        TrackIntegrator.getInstance().logAutoBehavorPageStart(spm, view);
        try {
            SpmTracker.onPageResume(view, spm);
        } catch (Throwable e) {
            H5Log.e((String) "H5WalletLogProvider", e);
        }
    }

    public void logAutoBehavorPageStart(String spm, Object view, boolean rpc) {
        TrackIntegrator.getInstance().logAutoBehavorPageStart(spm, view, rpc);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x007b A[Catch:{ Exception -> 0x008b }, LOOP:0: B:17:0x0075->B:19:0x007b, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void logAutoBehavorPageEnd(java.lang.String r15, java.lang.String r16, java.lang.Object r17, java.lang.String r18, java.util.Map<java.lang.String, java.lang.String> r19) {
        /*
            r14 = this;
            if (r19 != 0) goto L_0x0009
            java.util.HashMap r12 = new java.util.HashMap     // Catch:{ Exception -> 0x008b }
            r12.<init>()     // Catch:{ Exception -> 0x008b }
            r19 = r12
        L_0x0009:
            java.lang.String r3 = "header"
            r0 = r19
            boolean r3 = r0.containsKey(r3)     // Catch:{ Exception -> 0x008b }
            if (r3 == 0) goto L_0x001a
            java.lang.String r3 = "header"
            r0 = r19
            r0.remove(r3)     // Catch:{ Exception -> 0x008b }
        L_0x001a:
            com.alipay.mobile.common.logging.api.behavor.Behavor r8 = new com.alipay.mobile.common.logging.api.behavor.Behavor     // Catch:{ Exception -> 0x008b }
            r8.<init>()     // Catch:{ Exception -> 0x008b }
            r8.setSeedID(r15)     // Catch:{ Exception -> 0x008b }
            r0 = r16
            r8.setAbTestInfo(r0)     // Catch:{ Exception -> 0x008b }
            java.lang.String r3 = com.alipay.mobile.monitor.track.TrackAutoHelper.AUTO_TRACK_TYPE     // Catch:{ Exception -> 0x008b }
            r8.setBehaviourPro(r3)     // Catch:{ Exception -> 0x008b }
            java.lang.String r3 = "chInfo"
            r0 = r19
            boolean r3 = r0.containsKey(r3)     // Catch:{ Throwable -> 0x009c }
            if (r3 == 0) goto L_0x0092
            java.lang.String r3 = "chInfo"
            r0 = r19
            java.lang.Object r3 = r0.get(r3)     // Catch:{ Throwable -> 0x009c }
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch:{ Throwable -> 0x009c }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x009c }
            if (r3 != 0) goto L_0x0092
            java.util.concurrent.ConcurrentHashMap r13 = new java.util.concurrent.ConcurrentHashMap     // Catch:{ Throwable -> 0x009c }
            r13.<init>()     // Catch:{ Throwable -> 0x009c }
            r0 = r19
            r13.putAll(r0)     // Catch:{ Throwable -> 0x009c }
            java.lang.String r3 = "chInfo"
            java.lang.Object r9 = r13.remove(r3)     // Catch:{ Throwable -> 0x009c }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ Throwable -> 0x009c }
            r0 = r17
            r1 = r18
            com.alipay.android.phone.wallet.spmtracker.SpmTracker.onPagePause(r0, r15, r1, r13, r9)     // Catch:{ Throwable -> 0x009c }
        L_0x005f:
            java.lang.String r3 = "header"
            java.lang.String r4 = "H5-AM"
            r0 = r19
            r0.put(r3, r4)     // Catch:{ Exception -> 0x008b }
            java.util.HashMap r7 = new java.util.HashMap     // Catch:{ Exception -> 0x008b }
            r7.<init>()     // Catch:{ Exception -> 0x008b }
            java.util.Set r3 = r19.keySet()     // Catch:{ Exception -> 0x008b }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Exception -> 0x008b }
        L_0x0075:
            boolean r4 = r3.hasNext()     // Catch:{ Exception -> 0x008b }
            if (r4 == 0) goto L_0x00a3
            java.lang.Object r11 = r3.next()     // Catch:{ Exception -> 0x008b }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ Exception -> 0x008b }
            r0 = r19
            java.lang.Object r4 = r0.get(r11)     // Catch:{ Exception -> 0x008b }
            r7.put(r11, r4)     // Catch:{ Exception -> 0x008b }
            goto L_0x0075
        L_0x008b:
            r10 = move-exception
            java.lang.String r3 = ""
            com.alipay.mobile.nebula.util.H5Log.e(r3, r10)
        L_0x0091:
            return
        L_0x0092:
            r0 = r17
            r1 = r18
            r2 = r19
            com.alipay.android.phone.wallet.spmtracker.SpmTracker.onPagePause(r0, r15, r1, r2)     // Catch:{ Throwable -> 0x009c }
            goto L_0x005f
        L_0x009c:
            r10 = move-exception
            java.lang.String r3 = "H5WalletLogProvider"
            com.alipay.mobile.nebula.util.H5Log.e(r3, r10)     // Catch:{ Exception -> 0x008b }
            goto L_0x005f
        L_0x00a3:
            com.alipay.mobile.monitor.track.TrackIntegrator r3 = com.alipay.mobile.monitor.track.TrackIntegrator.getInstance()     // Catch:{ Exception -> 0x008b }
            r4 = r15
            r5 = r17
            r6 = r18
            r3.logAutoBehavorPageEnd(r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x008b }
            goto L_0x0091
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.wallet.H5LogProviderImpl.logAutoBehavorPageEnd(java.lang.String, java.lang.String, java.lang.Object, java.lang.String, java.util.Map):void");
    }

    public boolean isPageStarted(Object view) {
        try {
            return SpmTracker.isPageStarted(view);
        } catch (Throwable throwable) {
            H5Log.e((String) "H5WalletLogProvider", throwable);
            return false;
        }
    }

    public void logTinyTrackerStart(Object page, String seedId) {
        TinyTracker.onPageResume(page, seedId);
    }

    public void logTinyTrackerEnd(Object page, String seedId, String bizCode, Map<String, String> map) {
        if (!map.containsKey("chInfo") || TextUtils.isEmpty(map.get("chInfo"))) {
            TinyTracker.onPagePause(page, seedId, bizCode, map);
        } else {
            TinyTracker.onPagePause(page, seedId, bizCode, map, (String) new ConcurrentHashMap(map).remove("chInfo"));
        }
    }
}
