package com.autonavi.minimap.ajx3.upgrade;

import android.content.Context;
import com.amap.bundle.mapstorage.MapSharePreference;

public class Ajx3SpUtil {
    private static final String AJX_APP_VERSION = "ajx_app_version";
    private static final String AJX_BUNDLE_INFO = "ajx_bundle_info";
    private static final String AJX_BUNDLE_NAMES = "ajx_bundle_names";
    private static final String AJX_WEB_CONFIG_INFO = "ajx_web_config_info";
    private static final String JS_BUNDLE_NAMES = "js_bundle_names";
    private static final String SP_NAME_AuiCache = "AuiCache";

    private Ajx3SpUtil() {
    }

    static String getAppVersion(Context context) {
        return auiSp(context).getStringValue(AJX_APP_VERSION, null);
    }

    static void setAppVersion(Context context, String str) {
        auiSp(context).putStringValue(AJX_APP_VERSION, str);
    }

    public static void setCloudConfig(Context context, String str, String str2) {
        auiSp(context).putStringValue(str, str2);
    }

    public static String getCloudConfig(Context context, String str) {
        return auiSp(context).getStringValue(str, null);
    }

    public static void setJsAjxPageConfig(Context context, String str, String str2) {
        auiSp(context).putStringValue(str, str2);
    }

    public static String getJsAjxPageConfig(Context context, String str) {
        return auiSp(context).getStringValue(str, null);
    }

    private static MapSharePreference auiSp(Context context) {
        return new MapSharePreference((String) SP_NAME_AuiCache);
    }

    static void setBundleNames(Context context, String str) {
        auiSp(context).putStringValue(AJX_BUNDLE_NAMES, str);
    }

    static String getBundleNames(Context context) {
        return auiSp(context).getStringValue(AJX_BUNDLE_NAMES, "");
    }

    static void setBundleInfo(Context context, String str) {
        auiSp(context).putStringValue(AJX_BUNDLE_INFO, str);
    }

    static String getBundleInfo(Context context) {
        return auiSp(context).getStringValue(AJX_BUNDLE_INFO, "");
    }

    static void setJsBundleNames(Context context, String str) {
        auiSp(context).putStringValue(JS_BUNDLE_NAMES, str);
    }

    static String getJsBundleNames(Context context) {
        return auiSp(context).getStringValue(JS_BUNDLE_NAMES, "");
    }

    static void setWebConfigInfo(Context context, String str) {
        auiSp(context).putStringValue(AJX_WEB_CONFIG_INFO, str);
    }

    static String getWebConfigInfo(Context context) {
        return auiSp(context).getStringValue(AJX_WEB_CONFIG_INFO, "");
    }

    static void setAjxEngineVersion(Context context, String str) {
        auiSp(context).putStringValue("ajx_update_engine_version", str);
    }

    static String getAjxEngineVersion(Context context) {
        return auiSp(context).getStringValue("ajx_update_engine_version", "");
    }

    static void setLastCheckResponse(Context context, String str) {
        auiSp(context).putStringValue("last_check_update_response", str);
    }

    static String getLastCheckResponse(Context context) {
        return auiSp(context).getStringValue("last_check_update_response", "");
    }

    static void setLastCheckTask(Context context, String str) {
        auiSp(context).putStringValue("last_check_update_task", str);
    }

    static String getLastCheckTask(Context context) {
        return auiSp(context).getStringValue("last_check_update_task", "");
    }

    static void setPatchInvalidTime(Context context, String str, long j) {
        auiSp(context).putLongValue(str, j);
    }

    static long getPatchInvalidTime(Context context, String str) {
        return auiSp(context).getLongValue(str, -1);
    }

    static void remoevPatchInvalidTime(Context context, String str) {
        auiSp(context).remove(str);
    }

    static void commit(Context context) {
        auiSp(context).edit().commit();
    }

    static void saveAjxRollbackAllIds(Context context, String str) {
        auiSp(context).putStringValue("ajx_rollback_all", str);
        commit(context);
    }

    static String getAjxRollbackAllIds(Context context) {
        return auiSp(context).getStringValue("ajx_rollback_all", "");
    }
}
