package com.autonavi.minimap.route.net.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.autonavi.bundle.routecommon.entity.Trip;
import com.autonavi.bundle.routecommon.entity.Trip.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RTBusLocation {
    private String line;
    private ArrayList<Trip> mTripList;
    private String station;
    private int status;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getLine() {
        return this.line;
    }

    public void setLine(String str) {
        this.line = str;
    }

    public String getStation() {
        return this.station;
    }

    public void setStation(String str) {
        this.station = str;
    }

    public ArrayList<Trip> getTripList() {
        return this.mTripList;
    }

    public Trip getTrip(int i) {
        if (this.mTripList == null || i < 0 || i >= this.mTripList.size()) {
            return null;
        }
        return this.mTripList.get(i);
    }

    @JSONField(name = "trip")
    public void setTrip(ArrayList<RTBusTrip> arrayList) {
        if (arrayList == null) {
            this.mTripList = null;
            return;
        }
        this.mTripList = new ArrayList<>(arrayList.size());
        Iterator<RTBusTrip> it = arrayList.iterator();
        while (it.hasNext()) {
            this.mTripList.add(it.next().convertToTrip());
        }
        Collections.sort(this.mTripList, new b());
    }
}
