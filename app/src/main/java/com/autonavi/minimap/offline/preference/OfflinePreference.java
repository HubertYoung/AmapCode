package com.autonavi.minimap.offline.preference;

public class OfflinePreference extends BaseOfflinePreference {
    public static final String KEY_ACTIVE_POSSIVE = "ACTIVE_POSSIVE";
    public static final String KEY_AE8_RED_SHOW = "Ae8RedShow";
    public static final String KEY_DOWNLOAD_BRIEF_MAP = "DownloadBriefMap";
    public static final String KEY_DOWNLOAD_CURRENT_CITY_MAP = "DownloadCurrentCityMap";
    public static final String KEY_FREQUENTLY_QUESTIONS = "FrequentlyQuestions";
    public static final String KEY_GUIDE_DIALOG = "OfflineGuideTipVersion";
    public static final String KEY_NAVITTS_NEW_FEATURE = "NaviTtsNewFeature";
    public static final String KEY_NAVITTS_PP_APK_DOWNLOAD_PATH = "download_path";
    public static final String KEY_NAVITTS_PP_SWITCH = "pp_switch";
    public static final String KEY_NAVITTS_RED_HINT_ADD_737 = "NaviTtsRedHint_Add_737";
    public static final String KEY_NAVITTS_TTS_NEW_VERSION = "NaviTtsNewVersion";
    public static final String KEY_NAVITTS_UPDATE_VERSION = "NaviTtsUpdateVer";
    public static final String KEY_NAVITTS_USING_VOICE = "NaviTtsUsingVoice";
    public static final String KEY_NAVITTS_USING_VOICE_JSON_DATA = "NaviTtsUsingVoiceJsonData";
    public static final String KEY_NAVITTS_VERSION = "AE8NaviTtsVersion";
    public static final String KEY_NAVITTS_VERSION_TIME = "NaviTtsVersionTime";
    public static final String KEY_OFFLINE_GUIDE_TIP_SHOWN = "OfflineGuideTipShown";
    public static final String KEY_OFFLINE_MAP_PRIORI_ENABLED = "offlineMapSwitchEnabled";
    public static final String KEY_OFFLINE_MAP_PRIORI_ENABLED_OLD = "offlineMapEnabled";
    public static final String KEY_OFFLINE_MORE = "OfflineMore";
    public static final String KEY_OFFLINE_TTS_GUIDE_TIP_SHOWN = "OfflineTTSGuideTipShown";
    public static final String KEY_OFFLINE_UPDATE_MAP_VERSION = "OfflineUpdateMapVer";
    public static final String KEY_OFFLINE_UPDATE_NAVI_VERSION = "OfflineUpdateNaviVer";
    public static final String KEY_PASSIVE_LISTS_MOBILE_NETWORK = "PASSIVE_LISTS_MOBILE_NETWORK";
    public static final String KEY_PREFIX_ADMIN_REGION_VERSION_V5 = "AdminRegionVersion_V5";
    public static final String KEY_PREFIX_NAVITTS_UPDATE_CHECK_TAG = "NaviTtsUpdateCheckTag";
    public static final String KEY_SHOW_OFFLINE_TIP_TIMES = "show_offline_tip_times";
    public static final String KEY_TODAY_TRAFFIC = "todayTraffic";
    public static final String KEY_TRAFFIC_SAVED = "trafficSaved";
    public static final String KEY_UPDATE_DATA_DATE = "updateDataDate";
    public static final String KEY_UPDATE_DOWNLOAD_LIST_DATE = "updateDownloadListDate";
    public static final String KEY_WIFI_ENABLED = "wifiEnabled";
    private static final String NAME = "SharedPreferences";

    public static BaseOfflinePreference getInstance() {
        return BaseOfflinePreference.getInstance(NAME);
    }
}
