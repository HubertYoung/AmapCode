package com.autonavi.minimap.life.spotguide.inter.impl;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.bundle.life.api.api.ISpotGuideManager;
import com.autonavi.sdk.location.LocationInstrument;

public class SpotGuideManagerImpl implements ISpotGuideManager {
    public final int a() {
        long longValue = new MapSharePreference((String) "SharedPreferences").getLongValue("spot_guid_update_time", -1);
        return (longValue == -1 || System.currentTimeMillis() - longValue > 1296000000) ? 1 : 0;
    }

    public final int b() {
        long j;
        long a = li.a().a(LocationInstrument.getInstance().getLatestPosition().x, LocationInstrument.getInstance().getLatestPosition().y);
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        try {
            j = mapSharePreference.getLongValue("last_adcode", -1);
        } catch (Exception unused) {
            j = (long) mapSharePreference.getIntValue("last_adcode", -1);
        }
        return (j != -1 && a == j) ? 0 : 1;
    }
}
