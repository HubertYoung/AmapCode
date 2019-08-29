package com.autonavi.minimap.route.bus.realtimebus.model;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class RTBusData {
    private volatile Map<String, BusStationData> mRTBusDatas = new ConcurrentHashMap();
    public volatile RecommendStation recommendStation;

    public void resetDataCache() {
        if (this.mRTBusDatas == null) {
            this.mRTBusDatas = new ConcurrentHashMap();
        } else {
            this.mRTBusDatas.clear();
        }
    }

    public void addRTBusData(String str, BusStationData busStationData) {
        if (this.mRTBusDatas != null && !TextUtils.isEmpty(str) && busStationData != null) {
            synchronized (this.mRTBusDatas) {
                this.mRTBusDatas.put(str, busStationData);
            }
        }
    }

    public BusStationData getRTBusData(String str) {
        BusStationData busStationData;
        if (this.mRTBusDatas == null || TextUtils.isEmpty(str)) {
            return null;
        }
        synchronized (this.mRTBusDatas) {
            try {
                busStationData = this.mRTBusDatas.get(str);
            }
        }
        return busStationData;
    }

    public List<BusStationData> convertResultToList() {
        ArrayList arrayList;
        if (this.mRTBusDatas == null) {
            return null;
        }
        synchronized (this.mRTBusDatas) {
            arrayList = new ArrayList();
            for (Entry<String, BusStationData> value : this.mRTBusDatas.entrySet()) {
                arrayList.add(value.getValue());
            }
        }
        return arrayList;
    }

    public void filterNoRTStation() {
        if (this.mRTBusDatas != null) {
            synchronized (this.mRTBusDatas) {
                Iterator<Entry<String, BusStationData>> it = this.mRTBusDatas.entrySet().iterator();
                while (it.hasNext()) {
                    if (!((BusStationData) it.next().getValue()).isRTStation()) {
                        it.remove();
                    }
                }
            }
        }
    }

    public static Map<String, BusStationData> convertListToMap(List<BusStationData> list) {
        HashMap hashMap;
        if (dxx.a((Collection<T>) list)) {
            return null;
        }
        synchronized (list) {
            hashMap = new HashMap();
            for (BusStationData next : list) {
                if (next != null) {
                    hashMap.put(next.poiId, next);
                }
            }
        }
        return hashMap;
    }
}
