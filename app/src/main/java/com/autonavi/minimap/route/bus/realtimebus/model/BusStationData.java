package com.autonavi.minimap.route.bus.realtimebus.model;

import com.autonavi.common.model.GeoPoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class BusStationData {
    public String poiId;
    public int pointItemIndex;
    public int selectedLineIndex = 0;
    public Double station_lat;
    public Double station_lon;
    public String station_name;
    public List<RealTimeBusStation> stations;

    public BusStationData(String str, String str2, Double d, Double d2) {
        this.station_name = str;
        this.poiId = str2;
        this.station_lon = d;
        this.station_lat = d2;
        this.stations = Collections.synchronizedList(new ArrayList());
    }

    public void addRealTimeBusStation(RealTimeBusStation realTimeBusStation) {
        if (realTimeBusStation != null) {
            if (this.stations == null) {
                this.stations = new ArrayList();
            } else if (this.stations.contains(realTimeBusStation)) {
                return;
            }
            this.stations.add(realTimeBusStation);
        }
    }

    public Map<String, RealTimeBusStation> convertListToMap() {
        if (dxx.a((Collection<T>) this.stations)) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (RealTimeBusStation next : this.stations) {
            hashMap.put(next.bus_id, next);
        }
        return hashMap;
    }

    public GeoPoint getGeoPoint() {
        return new GeoPoint(this.station_lon.doubleValue(), this.station_lat.doubleValue());
    }

    public RealTimeBusStation getBusStation() {
        return getBusStationByIndex(0);
    }

    public RealTimeBusStation getBusStationByIndex(int i) {
        if (dxx.a((Collection<T>) this.stations) || i < 0 || i >= this.stations.size()) {
            return null;
        }
        return this.stations.get(i);
    }

    public boolean isFollow() {
        if (!dxx.a((Collection<T>) this.stations)) {
            synchronized (this.stations) {
                try {
                    for (RealTimeBusStation next : this.stations) {
                        if (next != null && next.isFollow) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isRTStation() {
        if (!dxx.a((Collection<T>) this.stations)) {
            synchronized (this.stations) {
                try {
                    for (RealTimeBusStation next : this.stations) {
                        if (next != null && next.isRealtime()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
