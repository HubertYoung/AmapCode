package com.autonavi.minimap.route.bus.realtimebus.model;

import java.io.Serializable;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class BusEndPointData implements Serializable {
    private boolean isBeyond2Hours = false;
    private String lastStationID;
    private String lastStationLonLat;
    private long timeStamp = 0;

    public boolean isBeyond2Hours() {
        return this.isBeyond2Hours;
    }

    public void setBeyond2Hours(boolean z) {
        this.isBeyond2Hours = z;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long j) {
        this.timeStamp = j;
    }

    public String getLastStationID() {
        return this.lastStationID;
    }

    public void setLastStationID(String str) {
        this.lastStationID = str;
    }

    public String getLastStationLonLat() {
        return this.lastStationLonLat;
    }

    public void setLastStationLonLat(String str) {
        this.lastStationLonLat = str;
    }
}
