package com.alipay.mobile.nebulauc.impl.setup;

import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.util.H5DeviceHelper;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulauc.impl.UCWebView;
import com.alipay.mobile.nebulauc.impl.UcServiceSetup;
import com.alipay.mobile.nebulaucsdk.UcSdkConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UcSetupTracing {
    public static final String REPORT_H5_INIT_UC_TIME = "h5_init_uc_time";
    public static final String TAG = "UcSetupTracing";
    private static Map<String, String> sCommonInfoMap = new ConcurrentHashMap();
    private static boolean sReported = false;
    private static Map<String, Long> sStartTimeMap = new HashMap();
    private static Map<String, Long> sTraceMap = new ConcurrentHashMap();

    public static void beginTrace(String key) {
        if (sReported) {
            H5Log.w(TAG, "trace over, cannot start " + key);
        } else {
            sStartTimeMap.put(key, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public static void endTrace(String key) {
        if (sReported) {
            H5Log.w(TAG, "trace over, cannot end " + key);
            return;
        }
        Long start = sStartTimeMap.remove(key);
        if (start != null) {
            long elapsed = System.currentTimeMillis() - start.longValue();
            H5Log.d(TAG, key + " used time: " + elapsed);
            if (sTraceMap != null) {
                sTraceMap.put(key, Long.valueOf(elapsed));
                return;
            }
            return;
        }
        H5Log.w(TAG, key + " cannot find start time!");
    }

    public static void setTrace(String key) {
        if (sReported) {
            H5Log.w(TAG, "trace over, cannot set " + key);
        } else if (sTraceMap != null) {
            sTraceMap.put(key, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public static void addCommonInfo(String key, String value) {
        if (sReported) {
            H5Log.w(TAG, "trace over, cannot addCommonInfo " + key);
            return;
        }
        H5Log.d(TAG, "addCommonInfo: " + key + " = " + value);
        if (sCommonInfoMap != null) {
            sCommonInfoMap.put(key, value);
        }
    }

    public static void report() {
        if (!sReported) {
            addCommonInfo("ucVersion", UcSdkConstants.UC_VERSION);
            addCommonInfo("webViewVersion", UCWebView.WEBVIEW_VERSION);
            addCommonInfo("cpuHardware", H5DeviceHelper.getCpuHardware());
            addCommonInfo("isLite", H5Utils.isInTinyProcess() + "");
            addCommonInfo("renderProcessLaunchTimeout", String.valueOf(UcServiceSetup.sRenderProcessLaunchTimeout));
            addCommonInfo("rebindIsolateProcessTimeout", String.valueOf(UcServiceSetup.sRebindIsolateProcessTimeout));
            addCommonInfo("isolateSpeedUp", String.valueOf(UcServiceSetup.sIsolateSpeedUp));
            sReported = true;
            H5LogUtil.logNebulaTech(H5LogData.seedId(REPORT_H5_INIT_UC_TIME).param1().addMapParam(sTraceMap).param2().addMapParam(sCommonInfoMap));
            sTraceMap = null;
            sStartTimeMap = null;
            sCommonInfoMap = null;
        }
    }
}
