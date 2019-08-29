package com.alipay.mobile.nebula.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.util.H5Utils;
import com.autonavi.minimap.ajx3.util.AjxDebugConfig;

public class H5LogUtil {
    public static void logNebulaTech(H5LogData logData) {
        behaviorLog(logData, H5BehaviorLogConfig.newH5BehaviorLogConfig().setLogLevel(2).setBehaviourPro(H5BehaviorLogConfig.NEBULA_TCEH_BEHAVIOUR));
    }

    public static void logH5Exception(H5LogData logData) {
        monitorLog(logData, H5MonitorLogConfig.newH5MonitorLogConfig().setLogHeader(H5Logger.LOG_HEADER_EM).setLogType(H5MonitorLogConfig.H5EXCEPTION_TYPE));
    }

    public static void monitorLog(H5LogData logData, H5MonitorLogConfig logConfig) {
        H5LogProvider h5LogProvider = H5AppUtil.getH5LogProvider();
        if (h5LogProvider != null) {
            h5LogProvider.monitorLog(logData, logConfig);
        }
    }

    public static void behaviorLog(H5LogData logData, H5BehaviorLogConfig logConfig) {
        H5LogProvider h5LogProvider = H5AppUtil.getH5LogProvider();
        if (h5LogProvider != null) {
            h5LogProvider.behaviorLog(logData, logConfig);
        }
    }

    public static boolean useNewLogUpload() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !"yes".equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_newLogUpload"))) {
            return false;
        }
        return true;
    }

    public static JSONArray getPerformanceJSApiBlackList() {
        return getJSApiBlackList(AjxDebugConfig.PERFORMANCE);
    }

    public static JSONArray getSecurityJSApiBlackList() {
        return getJSApiBlackList("security");
    }

    private static JSONArray getJSApiBlackList(String key) {
        JSONArray jsonArray = new JSONArray();
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null) {
            return jsonArray;
        }
        JSONObject config = h5ConfigProvider.getConfigJSONObject("h5_apiLogBlackList");
        if (config == null || config.isEmpty()) {
            return jsonArray;
        }
        return H5Utils.getJSONArray(config, key, jsonArray);
    }
}
