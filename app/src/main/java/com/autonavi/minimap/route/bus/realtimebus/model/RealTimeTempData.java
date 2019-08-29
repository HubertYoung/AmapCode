package com.autonavi.minimap.route.bus.realtimebus.model;

import com.autonavi.minimap.route.bus.model.Bus;
import java.io.Serializable;
import java.util.List;

public class RealTimeTempData implements Serializable {
    public String mAdCode;
    public Bus mBus;
    public int mLineIndex;
    public List<RecommenStationLines> mLines;

    public RealTimeTempData(Bus bus, String str, List<RecommenStationLines> list, int i) {
        this.mBus = bus;
        this.mLineIndex = i;
        this.mLines = list;
        this.mAdCode = str;
    }
}
