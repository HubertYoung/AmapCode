package com.alipay.mobile.common.transport.utils;

import android.content.Context;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public class SwitchMonitorLogUtil {
    public static final String SRC_AMDC = "amdc";
    public static final String SRC_PUSH = "push";
    public static final String SRC_RPC = "rpc";
    public static final String SUB_TYPE_RECV = "recv";
    public static final String SUB_TYPE_UPDATED = "updated";

    public static final void monitorLog(Context context, String subType, Map<String, String> switchIdMap, String source) {
        if (switchIdMap == null || switchIdMap.isEmpty()) {
            LogCatUtil.warn((String) "SwitchMonitorLogUtil", (String) "monitorLog.  switchIdMap is empty");
            return;
        }
        String switchId = a(switchIdMap);
        if (MiscUtils.isEmpty(switchId)) {
            LogCatUtil.warn((String) "SwitchMonitorLogUtil", (String) "monitorLog.  switchId is empty");
        } else {
            monitorLog(context, subType, switchId, source);
        }
    }

    public static final void monitorLog(Context context, String subType, String switchId, String source) {
        if (context == null) {
            LogCatUtil.warn((String) "SwitchMonitorLogUtil", (String) "monitorLog.  context is null");
        } else if (MiscUtils.isEmpty(subType)) {
            LogCatUtil.warn((String) "SwitchMonitorLogUtil", (String) "monitorLog.  subType is empty");
        } else if (MiscUtils.isEmpty(switchId)) {
            LogCatUtil.warn((String) "SwitchMonitorLogUtil", (String) "monitorLog.  switchId is empty");
        } else if (MiscUtils.isEmpty(source)) {
            LogCatUtil.warn((String) "SwitchMonitorLogUtil", (String) "monitorLog.  source is empty");
        } else {
            try {
                Performance pf = new TransportPerformance();
                pf.setSubType("SWITCH");
                pf.setParam1("1.0");
                pf.setParam2("-");
                pf.setParam3(subType);
                pf.getExtPramas().put("switch_id", switchId);
                pf.getExtPramas().put("proc", MiscUtils.getCurShortProcessName(context));
                pf.getExtPramas().put("source", source);
                LogCatUtil.info(pf.getSubType() + "_PERF", pf.toString() + "\n");
                MonitorLoggerUtils.uploadPerfLog(pf);
            } catch (Throwable e) {
                LogCatUtil.error("SwitchMonitorLogUtil", "monitorLog. Print monitor log error", e);
            }
        }
    }

    public static void monitorCoreSwitchRecvLog(Context context, String strConf, String src) {
        try {
            JSONObject coreSwitchJSON = JSONUtil.convertJSONObject(strConf);
            Map switchIdMaps = new LinkedHashMap(1);
            putSwitchId(coreSwitchJSON, switchIdMaps, "stl1");
            monitorLog(context, (String) SUB_TYPE_RECV, switchIdMaps, src);
        } catch (Throwable e) {
            LogCatUtil.warn("SwitchMonitorLogUtil", "monitorCoreSwitchRecvLog fail", e);
        }
    }

    public static final void monitorSwitchUpdatedLog(Context context, String source) {
        try {
            monitorLog(context, (String) SUB_TYPE_UPDATED, a(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SWITCH_TAG_LOG1), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SWITCH_TAG_LOG2)), source);
        } catch (Throwable e) {
            LogCatUtil.error("SwitchMonitorLogUtil", "monitorSwitchUpdatedLog error", e);
        }
    }

    public static void putSwitchId(JSONObject jsonObj, Map<String, String> switchIdMaps, String key) {
        if (jsonObj != null && !MiscUtils.isEmpty(key) && switchIdMaps != null) {
            switchIdMaps.put(key, jsonObj.optString(key, TransportConfigureItem.SWITCH_TAG_LOG1.getStringValue()));
        }
    }

    private static String a(Map<String, String> switchIdMap) {
        StringBuilder switchIdBuilder = new StringBuilder();
        for (Entry entry : switchIdMap.entrySet()) {
            String stlKey = (String) entry.getKey();
            String stlValue = (String) entry.getValue();
            if (MiscUtils.isEmpty(stlKey) || MiscUtils.isEmpty(stlValue)) {
                LogCatUtil.warn((String) "SwitchMonitorLogUtil", "buildSwitchId.  stlKey:" + stlKey + "，stlValue:" + stlValue);
            } else {
                switchIdBuilder.append(MergeUtil.SEPARATOR_KV).append(stlKey).append("_").append(stlValue);
            }
        }
        if (switchIdBuilder.length() <= 0) {
            return "";
        }
        switchIdBuilder.deleteCharAt(0);
        return switchIdBuilder.toString();
    }

    private static String a(String stl1, String stl2) {
        return "stl1_" + stl1 + "|stl2_" + stl2;
    }
}
