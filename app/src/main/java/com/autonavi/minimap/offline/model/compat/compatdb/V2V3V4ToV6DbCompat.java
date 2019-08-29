package com.autonavi.minimap.offline.model.compat.compatdb;

import com.autonavi.minimap.offline.OfflineSDK;
import com.autonavi.minimap.offline.utils.OfflineSpUtil;

public class V2V3V4ToV6DbCompat extends CompatDb {
    public static final int CATEGORY_MAP = 0;
    protected static final String TAG = "V2V3V4ToV6DbCompat";

    public V2V3V4ToV6DbCompat(String str) {
        super(str);
    }

    public boolean dataRestore() {
        if (this.db != null) {
            this.db.close();
            OfflineSDK.getInstance().setIsUpgradeAe8Version(true);
            OfflineSpUtil.setWifiAutoUpdateSp(true);
        }
        return false;
    }
}
