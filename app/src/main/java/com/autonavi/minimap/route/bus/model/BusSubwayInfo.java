package com.autonavi.minimap.route.bus.model;

import java.io.Serializable;
import java.util.ArrayList;

public class BusSubwayInfo implements Serializable {
    private static final long serialVersionUID = -3648194853556478827L;
    public ArrayList<BusSubwayStation> subwayList = new ArrayList<>();

    public ArrayList<BusSubwayStation> getSubwayInfoByName(String str, String str2) {
        ArrayList<BusSubwayStation> arrayList = new ArrayList<>();
        if (this.subwayList == null || this.subwayList.size() == 0) {
            return arrayList;
        }
        int size = this.subwayList.size();
        for (int i = 0; i < size; i++) {
            BusSubwayStation busSubwayStation = this.subwayList.get(i);
            if (busSubwayStation != null && busSubwayStation.busStationName.equals(str) && busSubwayStation.busStationId.equals(str2)) {
                arrayList.add(busSubwayStation);
            }
        }
        return arrayList;
    }
}
