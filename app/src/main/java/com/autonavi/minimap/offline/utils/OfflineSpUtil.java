package com.autonavi.minimap.offline.utils;

import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.minimap.offline.externalimport.IExternalService;
import com.autonavi.minimap.offline.preference.BaseOfflinePreference;
import com.autonavi.minimap.offline.preference.HintPopupWindowPreference;
import com.autonavi.minimap.offline.preference.OfflinePreference;
import com.autonavi.minimap.offline.preference.PathPreference;
import com.autonavi.minimap.offline.preference.TripPreference;

public class OfflineSpUtil {
    private OfflineSpUtil() {
    }

    public static void setMapBaseDBStorage(String str) {
        if (VERSION.SDK_INT > 18) {
            PathPreference.getInstance().putStringValue(PathPreference.KEY_MAP_BASE_PATH_V44, str);
        } else {
            PathPreference.getInstance().putStringValue(PathPreference.KEY_MAP_BASE_PATH, str);
        }
    }

    public static float getTrafficSavedSp() {
        return OfflinePreference.getInstance().getFloatValue(OfflinePreference.KEY_TRAFFIC_SAVED, 0.0f);
    }

    public static void setTrafficSavedSp(float f) {
        OfflinePreference.getInstance().putFloatValue(OfflinePreference.KEY_TRAFFIC_SAVED, f);
    }

    public static float getTodaySavedTraffic() {
        return OfflinePreference.getInstance().getFloatValue(OfflinePreference.KEY_TODAY_TRAFFIC, 0.0f);
    }

    public static void setTodaySavedTraffic(float f) {
        OfflinePreference.getInstance().putFloatValue(OfflinePreference.KEY_TODAY_TRAFFIC, f);
    }

    public static boolean getAe8RedNeedShowSp() {
        return OfflinePreference.getInstance().getBooleanValue(OfflinePreference.KEY_AE8_RED_SHOW, true);
    }

    public static void setAe8RedNeedShowSp(boolean z) {
        OfflinePreference.getInstance().putBooleanValue(OfflinePreference.KEY_AE8_RED_SHOW, z);
    }

    public static boolean getOfflineMapPrioriSp() {
        if (OfflinePreference.getInstance().contains(OfflinePreference.KEY_OFFLINE_MAP_PRIORI_ENABLED_OLD)) {
            setOfflineMapPrioriSp(OfflinePreference.getInstance().getBooleanValue(OfflinePreference.KEY_OFFLINE_MAP_PRIORI_ENABLED_OLD, false));
            OfflinePreference.getInstance().removeValue(OfflinePreference.KEY_OFFLINE_MAP_PRIORI_ENABLED_OLD);
        }
        return OfflinePreference.getInstance().getBooleanValue(OfflinePreference.KEY_OFFLINE_MAP_PRIORI_ENABLED, false);
    }

    public static void setOfflineMapPrioriSp(boolean z) {
        OfflinePreference.getInstance().putBooleanValue(OfflinePreference.KEY_OFFLINE_MAP_PRIORI_ENABLED, z);
    }

    public static boolean getWifiAutoUpdateSp() {
        return bim.aa().k((String) UploadConstants.STATUS_NET_NOT_MATCH);
    }

    public static void setWifiAutoUpdateSp(boolean z) {
        bim.aa().a((String) UploadConstants.STATUS_NET_NOT_MATCH, z ? 1 : 0);
    }

    public static String getAdminReginoVersion() {
        BaseOfflinePreference instance = OfflinePreference.getInstance();
        StringBuilder sb = new StringBuilder(OfflinePreference.KEY_PREFIX_ADMIN_REGION_VERSION_V5);
        sb.append(NetworkParam.getDiv());
        return instance.getStringValue(sb.toString(), "");
    }

    public static void setAdminReginoVersion(String str) {
        BaseOfflinePreference instance = OfflinePreference.getInstance();
        StringBuilder sb = new StringBuilder(OfflinePreference.KEY_PREFIX_ADMIN_REGION_VERSION_V5);
        sb.append(NetworkParam.getDiv());
        instance.putStringValue(sb.toString(), str);
    }

    public static boolean getHintPopupWindow() {
        BaseOfflinePreference instance = HintPopupWindowPreference.getInstance();
        StringBuilder sb = new StringBuilder(HintPopupWindowPreference.KEY_PREFIX_HINT_POPUP_WINDOW);
        sb.append(NetworkParam.getDiv());
        return instance.getBooleanValue(sb.toString(), true);
    }

    public static void setHintPopupWindow(boolean z) {
        BaseOfflinePreference instance = HintPopupWindowPreference.getInstance();
        StringBuilder sb = new StringBuilder(HintPopupWindowPreference.KEY_PREFIX_HINT_POPUP_WINDOW);
        sb.append(NetworkParam.getDiv());
        instance.putBooleanValue(sb.toString(), z);
    }

    public static boolean getOfflineGuideTipShown() {
        return OfflinePreference.getInstance().getBooleanValue(OfflinePreference.KEY_OFFLINE_GUIDE_TIP_SHOWN, false);
    }

    public static void setOfflineGuideTipShown(boolean z) {
        OfflinePreference.getInstance().putBooleanValue(OfflinePreference.KEY_OFFLINE_GUIDE_TIP_SHOWN, z);
    }

    public static boolean getOfflineTTSGuideTipShown() {
        return OfflinePreference.getInstance().getBooleanValue(OfflinePreference.KEY_OFFLINE_TTS_GUIDE_TIP_SHOWN, false);
    }

    public static void setOfflineTTSGuideTipShown(boolean z) {
        OfflinePreference.getInstance().putBooleanValue(OfflinePreference.KEY_OFFLINE_TTS_GUIDE_TIP_SHOWN, z);
    }

    public static String getOfflineDataUpdateMapVer() {
        return OfflinePreference.getInstance().getStringValue(OfflinePreference.KEY_OFFLINE_UPDATE_MAP_VERSION, "");
    }

    public static String getOfflineDataUpdateNaviVer() {
        return OfflinePreference.getInstance().getStringValue(OfflinePreference.KEY_OFFLINE_UPDATE_NAVI_VERSION, "");
    }

    public static boolean isOfflineDataUpdateShow() {
        String offlineDataUpdateMapVer = getOfflineDataUpdateMapVer();
        String offlineDataUpdateNaviVer = getOfflineDataUpdateNaviVer();
        StringBuilder sb = new StringBuilder("isOfflineDataUpdateShow mapVer:");
        sb.append(offlineDataUpdateMapVer);
        sb.append(", naviVer:");
        sb.append(offlineDataUpdateNaviVer);
        OfflineLog.d("Offline", sb.toString());
        boolean z = false;
        if (TextUtils.isEmpty(offlineDataUpdateMapVer) && TextUtils.isEmpty(offlineDataUpdateNaviVer)) {
            return false;
        }
        if (OfflinePreference.getInstance().getBooleanValue(OfflinePreference.KEY_OFFLINE_UPDATE_MAP_VERSION.concat(String.valueOf(offlineDataUpdateMapVer)), false) || OfflinePreference.getInstance().getBooleanValue(OfflinePreference.KEY_OFFLINE_UPDATE_NAVI_VERSION.concat(String.valueOf(offlineDataUpdateNaviVer)), false)) {
            z = true;
        }
        StringBuilder sb2 = new StringBuilder("isOfflineDataUpdateShow mapVer:");
        sb2.append(offlineDataUpdateMapVer);
        sb2.append(", naviVer:");
        sb2.append(offlineDataUpdateNaviVer);
        sb2.append(", update:");
        sb2.append(z);
        OfflineLog.d("Offline", sb2.toString());
        return z;
    }

    public static void setOfflineDataUpdateShow(boolean z) {
        String offlineDataUpdateMapVer = getOfflineDataUpdateMapVer();
        String offlineDataUpdateNaviVer = getOfflineDataUpdateNaviVer();
        if (!TextUtils.isEmpty(offlineDataUpdateMapVer) || !TextUtils.isEmpty(offlineDataUpdateNaviVer)) {
            if (!TextUtils.isEmpty(offlineDataUpdateMapVer)) {
                OfflinePreference.getInstance().putBooleanValue(OfflinePreference.KEY_OFFLINE_UPDATE_MAP_VERSION.concat(String.valueOf(offlineDataUpdateMapVer)), z);
            }
            if (!TextUtils.isEmpty(offlineDataUpdateNaviVer)) {
                OfflinePreference.getInstance().putBooleanValue(OfflinePreference.KEY_OFFLINE_UPDATE_NAVI_VERSION.concat(String.valueOf(offlineDataUpdateNaviVer)), z);
            }
        }
    }

    public static boolean isTodayEnterApp() {
        boolean z = false;
        try {
            if (OfflineUtil.getFormattedToday() == OfflinePreference.getInstance().getLongValue(OfflinePreference.KEY_UPDATE_DOWNLOAD_LIST_DATE, -1)) {
                z = true;
            }
            return z;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void recordUpdateDownloadListDate() {
        try {
            OfflinePreference.getInstance().putLongValue(OfflinePreference.KEY_UPDATE_DOWNLOAD_LIST_DATE, OfflineUtil.getFormattedToday());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setOfflineMoreRedSP(boolean z) {
        OfflinePreference.getInstance().putBooleanValue(OfflinePreference.KEY_OFFLINE_MORE, z);
    }

    public static boolean getOfflineMOreRedSp() {
        return OfflinePreference.getInstance().getBooleanValue(OfflinePreference.KEY_OFFLINE_MORE, true);
    }

    public static void setFrequentlyQuestionsRedSP(boolean z) {
        OfflinePreference.getInstance().putBooleanValue(OfflinePreference.KEY_FREQUENTLY_QUESTIONS, z);
    }

    public static boolean getFrequentlyQuestionsRedSp() {
        return OfflinePreference.getInstance().getBooleanValue(OfflinePreference.KEY_FREQUENTLY_QUESTIONS, true);
    }

    public static boolean getNaviConfigOnline() {
        return TripPreference.getInstance().getBooleanValue("navi_config_online", true);
    }

    public static void setNaviConfigOnline(boolean z) {
        TripPreference.getInstance().putBooleanValue("navi_config_online", z);
    }

    public static void setShowOfflineTipTimes(int i) {
        OfflinePreference.getInstance().putIntValue(OfflinePreference.KEY_SHOW_OFFLINE_TIP_TIMES, i);
    }

    public static String getOfflineGuideTipVersion() {
        return OfflinePreference.getInstance().getStringValue(OfflinePreference.KEY_GUIDE_DIALOG, "");
    }

    public static void setOfflineGuideTipVersion(String str) {
        OfflinePreference.getInstance().putStringValue(OfflinePreference.KEY_GUIDE_DIALOG, str);
    }

    public static void setSuportDimension(boolean z) {
        ank.a(IExternalService.class);
        Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
        edit.putBoolean("key_navi_3d_support", z);
        edit.apply();
        dfm dfm = (dfm) ank.a(dfm.class);
        if (dfm != null && dfm.c()) {
            AMapLog.d(AutoConstants.AUTO_FILE_3DCROSS, "network call back gpu support = ".concat(String.valueOf(z)));
            dfm.b(z);
        }
    }

    public static boolean isAlreadyUpdateOfflineDataToday() {
        boolean z = false;
        try {
            if (OfflineUtil.getFormattedToday() == OfflinePreference.getInstance().getLongValue(OfflinePreference.KEY_UPDATE_DATA_DATE, -1)) {
                z = true;
            }
            return z;
        } catch (Exception unused) {
            return false;
        }
    }

    public static void recordOfflineDataUpdateDate() {
        try {
            OfflinePreference.getInstance().putLongValue(OfflinePreference.KEY_UPDATE_DATA_DATE, OfflineUtil.getFormattedToday());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPPHelperSwitchSP(String str) {
        OfflinePreference.getInstance().putStringValue(OfflinePreference.KEY_NAVITTS_PP_SWITCH, str);
    }

    public static String getPPHelperSwitchSP() {
        return OfflinePreference.getInstance().getStringValue(OfflinePreference.KEY_NAVITTS_PP_SWITCH, "0");
    }

    public static void setPPApkDownloadPathSP(String str) {
        OfflinePreference.getInstance().putStringValue(OfflinePreference.KEY_NAVITTS_PP_APK_DOWNLOAD_PATH, str);
    }

    public static String getPPApkDownloadPathSP() {
        return OfflinePreference.getInstance().getStringValue(OfflinePreference.KEY_NAVITTS_PP_APK_DOWNLOAD_PATH, "");
    }
}
