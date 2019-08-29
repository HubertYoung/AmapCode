package com.autonavi.minimap.route.bus.realtimebus.model;

import java.io.Serializable;
import java.util.Map;

public class stTrip implements Serializable {
    private static final long serialVersionUID = 1730686530202006870L;
    public String docktripnum;
    public String onwaytripnum;
    public String stid;
    public Map<Integer, TripInfo> tripinfomap;
}
