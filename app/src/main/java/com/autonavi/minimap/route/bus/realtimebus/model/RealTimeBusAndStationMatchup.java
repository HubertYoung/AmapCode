package com.autonavi.minimap.route.bus.realtimebus.model;

import android.text.TextUtils;
import com.autonavi.bundle.routecommon.entity.Trip;
import com.autonavi.minimap.route.net.module.RTBusLocation;
import java.io.Serializable;

public class RealTimeBusAndStationMatchup implements Serializable {
    private static final long serialVersionUID = 1;
    public boolean isFollowBus;
    private boolean isLoadFinish;
    public boolean isNew;
    public btd mBean;
    public String mBuslineDescr;
    public String mBuslineID;
    public String mBuslineName;
    public String mStationID;
    public double mStationLat;
    public double mStationLon;
    public String mStationName;
    public int mStatus;
    public Trip mTrip;
    public int pointItemIndex;
    public int retryTimes;

    public RealTimeBusAndStationMatchup() {
        this.isNew = false;
        this.isFollowBus = false;
        this.isLoadFinish = false;
        this.retryTimes = 0;
    }

    public boolean isLoadFinish() {
        return this.isLoadFinish;
    }

    public void setLoadFinish(boolean z) {
        this.isLoadFinish = z;
    }

    public boolean isHasData() {
        return isLoadFinish() && this.mStatus != 0;
    }

    public boolean isBusComming() {
        return this.mTrip != null && this.mStatus == 1;
    }

    public boolean isBusStop() {
        return this.mStatus == 3;
    }

    public void updateRTBusStatus(RTBusLocation rTBusLocation) {
        this.isLoadFinish = true;
        if (rTBusLocation == null) {
            this.mStatus = 0;
            this.mTrip = null;
            return;
        }
        this.mStatus = rTBusLocation.getStatus();
        this.mTrip = rTBusLocation.getTrip(0);
    }

    public void updateRTBusStatus(RealTimeBusAndStationMatchup realTimeBusAndStationMatchup) {
        this.isLoadFinish = true;
        if (realTimeBusAndStationMatchup == null) {
            this.mStatus = 0;
            this.mTrip = null;
            return;
        }
        this.mStatus = realTimeBusAndStationMatchup.mStatus;
        this.mTrip = realTimeBusAndStationMatchup.mTrip;
    }

    public static boolean isBusStationMatch(RealTimeBusAndStationMatchup realTimeBusAndStationMatchup, RealTimeBusAndStationMatchup realTimeBusAndStationMatchup2) {
        if (realTimeBusAndStationMatchup == null || realTimeBusAndStationMatchup2 == null || !TextUtils.equals(realTimeBusAndStationMatchup.mBuslineID, realTimeBusAndStationMatchup2.mBuslineID) || !TextUtils.equals(realTimeBusAndStationMatchup.mStationID, realTimeBusAndStationMatchup2.mStationID)) {
            return false;
        }
        return true;
    }

    public RealTimeBusAndStationMatchup(String str, String str2, String str3) {
        this.isNew = false;
        this.isFollowBus = false;
        this.isLoadFinish = false;
        this.retryTimes = 0;
        this.mBuslineID = str;
        this.mStationID = str2;
        this.mBuslineDescr = str3;
    }

    public RealTimeBusAndStationMatchup(String str, String str2, String str3, String str4, String str5) {
        this(str, str3, str5);
        this.mStationName = str4;
        this.mBuslineName = str2;
    }

    public RealTimeBusAndStationMatchup(String str, String str2, String str3, String str4, String str5, double d, double d2) {
        this(str, str2, str3, str4, str5);
        this.mStationLon = d;
        this.mStationLat = d2;
    }

    public void setTrip(Trip trip) {
        this.mTrip = trip;
    }

    public String busName() {
        return this.mBean.bus_name;
    }

    public String busId() {
        return this.mBean.bus_id;
    }

    public String busDescribe() {
        return this.mBean.bus_describe;
    }

    public String stationId() {
        return this.mBean.station_id;
    }

    public String stationName() {
        return this.mBean.station_name;
    }

    public double stationLon() {
        return this.mBean.station_lon.doubleValue();
    }

    public double stationLat() {
        return this.mBean.station_lat.doubleValue();
    }

    public String poiid1() {
        return this.mBean.poiid1;
    }

    public String startName() {
        return this.mBean.start_name;
    }

    public String endName() {
        return this.mBean.end_name;
    }

    public String adcode() {
        return this.mBean.adcode;
    }

    public String busAreacode() {
        return this.mBean.bus_areacode;
    }

    public boolean isFollow() {
        return this.isFollowBus;
    }

    public void setFollow(boolean z) {
        this.isFollowBus = z;
    }
}
