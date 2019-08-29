package com.autonavi.bundle.routecommute.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;

public class BasemapCommuteTypeReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (!(intent == null || intent.getAction() == null || !"com.autonavi.minimap.action.TRIP_TOOL_SELECTED".equals(intent.getAction()))) {
            String stringValue = new MapSharePreference((String) "basemap").getStringValue("userIndividualityType", "");
            AMapLog.d("BasemapCommuteTypeReceiver", "type = ".concat(String.valueOf(stringValue)), true);
            if (!TextUtils.isEmpty(stringValue)) {
                if ("1".equals(stringValue)) {
                    azi.a((String) "0");
                } else if ("2".equals(stringValue)) {
                    azi.a((String) "1");
                }
            }
        }
    }
}
