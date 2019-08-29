package com.autonavi.minimap.controller;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class AppManager {
    private static volatile li cityInfoService;
    private static AppManager mInstance;

    private AppManager() {
    }

    public static AppManager getInstance() {
        if (mInstance == null) {
            synchronized (AppManager.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new AppManager();
                    }
                }
            }
        }
        return mInstance;
    }

    public lj getMapCenterCityInfo(bty bty) {
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(bty.n());
        if (glGeoPoint2GeoPoint != null) {
            return li.a().b(glGeoPoint2GeoPoint.x, glGeoPoint2GeoPoint.y);
        }
        return null;
    }

    public lj getMyLocationOrMapCenterCityInfo(bty bty) {
        lj myLocationCityInfo = getMyLocationCityInfo();
        if (myLocationCityInfo != null) {
            return myLocationCityInfo;
        }
        return getMapCenterCityInfo(bty);
    }

    public lj getMyLocationCityInfo() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        if (latestPosition != null) {
            return li.a().b(latestPosition.x, latestPosition.y);
        }
        return null;
    }

    public String getUserLocInfo() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        if (latestPosition == null) {
            return "";
        }
        DPoint a = cfg.a((long) latestPosition.x, (long) latestPosition.y);
        StringBuilder sb = new StringBuilder();
        sb.append(a.x);
        sb.append(",");
        sb.append(a.y);
        return sb.toString();
    }

    public String getLastUserLocInfo() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition == null) {
            return "";
        }
        DPoint a = cfg.a((long) latestPosition.x, (long) latestPosition.y);
        StringBuilder sb = new StringBuilder();
        sb.append(a.x);
        sb.append(",");
        sb.append(a.y);
        return sb.toString();
    }
}
