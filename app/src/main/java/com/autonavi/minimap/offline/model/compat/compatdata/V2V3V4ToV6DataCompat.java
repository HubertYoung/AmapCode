package com.autonavi.minimap.offline.model.compat.compatdata;

import com.autonavi.minimap.offline.utils.OfflineLog;

public class V2V3V4ToV6DataCompat extends CompatData {
    private static final String TAG = "V2V3V4ToV6DataCompat";
    private final String filePath;

    public V2V3V4ToV6DataCompat(String str) {
        this.filePath = str;
    }

    public void dataCheck() {
        OfflineLog.d(TAG, "dataCheck()");
        deleteVoicetData();
    }
}
