package com.autonavi.minimap.route.bus.realtimebus.model;

import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses.RealtimeBusTrip;
import java.util.List;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class RealTimeBusStation extends btd {
    public String is_realtime;
    public String line;
    public int status;
    public List<RealtimeBusTrip> trip;

    public RealTimeBusStation() {
    }

    public RealTimeBusStation(String str, String str2, String str3, String str4, String str5, Double d, Double d2) {
        this.bus_id = str;
        this.bus_name = str2;
        this.station_id = str3;
        this.station_name = str4;
        this.bus_describe = str5;
        this.station_lon = d;
        this.station_lat = d2;
    }

    public void setPOIId(String str) {
        this.poiid1 = str;
    }

    public void setIsFollow(boolean z) {
        this.isFollow = z;
    }

    public boolean isRealtime() {
        return "1".equals(this.is_realtime);
    }
}
