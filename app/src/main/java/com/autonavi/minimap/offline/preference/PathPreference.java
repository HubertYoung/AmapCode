package com.autonavi.minimap.offline.preference;

public class PathPreference extends BaseOfflinePreference {
    public static final String KEY_MAP_BASE_PATH = "map_base_path";
    public static final String KEY_MAP_BASE_PATH_V44 = "map_base_path_v44";
    private static final String NAME = "base_path";

    public static BaseOfflinePreference getInstance() {
        return BaseOfflinePreference.getInstance(NAME);
    }
}
