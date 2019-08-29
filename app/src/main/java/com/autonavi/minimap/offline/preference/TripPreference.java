package com.autonavi.minimap.offline.preference;

public class TripPreference extends BaseOfflinePreference {
    public static final String KEY_NAVI_CONFIG_ONLINE = "navi_config_online";
    private static final String NAME = "Trip_Config";

    public static BaseOfflinePreference getInstance() {
        return BaseOfflinePreference.getInstance("Trip_Config");
    }
}
