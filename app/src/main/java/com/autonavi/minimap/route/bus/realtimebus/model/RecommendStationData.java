package com.autonavi.minimap.route.bus.realtimebus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class RecommendStationData implements Serializable {
    private String bubble_style;
    private String line_ids;
    private List<RecommenStationLines> lines;
    private String recommend_station;
    private String station_ids;

    public String getRecommend_station() {
        return this.recommend_station;
    }

    public void setRecommend_station(String str) {
        this.recommend_station = str;
    }

    public String getLine_ids() {
        return this.line_ids;
    }

    public void setLine_ids(String str) {
        this.line_ids = str;
    }

    public String getBubble_style() {
        return this.bubble_style;
    }

    public void setBubble_style(String str) {
        this.bubble_style = str;
    }

    public String getStation_ids() {
        return this.station_ids;
    }

    public void setStation_ids(String str) {
        this.station_ids = str;
    }

    public List<RecommenStationLines> getLines() {
        return this.lines;
    }

    public List<RecommenStationLines> getLinesInOrder(BusStationData busStationData) {
        if (!dxx.a((Collection<T>) this.lines) && busStationData != null) {
            Map<String, RealTimeBusStation> convertListToMap = busStationData.convertListToMap();
            ArrayList arrayList = new ArrayList();
            int i = 0;
            for (RecommenStationLines next : this.lines) {
                RealTimeBusStation realTimeBusStation = convertListToMap != null ? convertListToMap.get(next.getLineid()) : null;
                if (realTimeBusStation == null || !realTimeBusStation.isFollow) {
                    next.isFollow = false;
                    arrayList.add(arrayList.size(), next);
                } else {
                    next.isFollow = true;
                    arrayList.add(i, next);
                    i++;
                }
            }
            this.lines.clear();
            this.lines.addAll(arrayList);
            arrayList.clear();
        }
        return this.lines;
    }

    public void setLines(List<RecommenStationLines> list) {
        this.lines = list;
    }

    public Map<String, RecommenStationLines> convertLinesToMap() {
        if (dxx.a((Collection<T>) this.lines)) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (RecommenStationLines next : this.lines) {
            hashMap.put(next.getLineid(), next);
        }
        return hashMap;
    }

    private RecommenStationLines getFastArriveWithFollow(BusStationData busStationData) {
        int i;
        int i2;
        if (busStationData != null && busStationData.isFollow()) {
            List<RealTimeBusStation> list = busStationData.stations;
            Map<String, RecommenStationLines> convertLinesToMap = convertLinesToMap();
            if (!(list == null || convertLinesToMap == null)) {
                ArrayList arrayList = new ArrayList();
                int size = list.size();
                for (int i3 = 0; i3 < size; i3++) {
                    RealTimeBusStation realTimeBusStation = list.get(i3);
                    if (realTimeBusStation.isFollow) {
                        RecommenStationLines recommenStationLines = convertLinesToMap.get(realTimeBusStation.bus_id);
                        recommenStationLines.isFollow = true;
                        List<RecommenStationBuses> buses = recommenStationLines.getBuses();
                        if (arrayList.size() == 0 || dxx.a((Collection<T>) buses)) {
                            arrayList.add(recommenStationLines);
                        } else {
                            List<RecommenStationBuses> buses2 = ((RecommenStationLines) arrayList.get(0)).getBuses();
                            if (!dxx.a((Collection<T>) buses2)) {
                                RecommenStationBuses recommenStationBuses = buses2.get(0);
                                RecommenStationBuses recommenStationBuses2 = buses.get(0);
                                if (recommenStationBuses == null) {
                                    i = 0;
                                } else {
                                    i = recommenStationBuses.getArrivalInt();
                                }
                                if (recommenStationBuses2 == null) {
                                    i2 = 0;
                                } else {
                                    i2 = recommenStationBuses2.getArrivalInt();
                                }
                                if (i2 >= i) {
                                    arrayList.add(1, recommenStationLines);
                                }
                            }
                            arrayList.add(0, recommenStationLines);
                        }
                    }
                }
                if (arrayList.size() == 0) {
                    return null;
                }
                return (RecommenStationLines) arrayList.get(0);
            }
        }
        return null;
    }

    public int getDefaultSelectedLineIndex(BusStationData busStationData) {
        RecommenStationLines fastArriveWithFollow = getFastArriveWithFollow(busStationData);
        if (fastArriveWithFollow != null) {
            return this.lines.indexOf(fastArriveWithFollow);
        }
        if (this.lines == null) {
            return 0;
        }
        int size = this.lines.size();
        RecommenStationLines recommenStationLines = this.lines.get(0);
        for (int i = 1; i < size; i++) {
            RecommenStationLines recommenStationLines2 = this.lines.get(i);
            List<RecommenStationBuses> buses = recommenStationLines.getBuses();
            List<RecommenStationBuses> buses2 = recommenStationLines2.getBuses();
            if (!dxx.a((Collection<T>) buses2)) {
                RecommenStationBuses recommenStationBuses = buses2.get(0);
                RecommenStationBuses recommenStationBuses2 = dxx.a((Collection<T>) buses) ? null : buses.get(0);
                if (recommenStationBuses2 == null || recommenStationBuses.getArrivalInt() < recommenStationBuses2.getArrivalInt()) {
                    recommenStationLines = recommenStationLines2;
                }
            }
        }
        return this.lines.indexOf(recommenStationLines);
    }
}
