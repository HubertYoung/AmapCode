package com.autonavi.minimap.route.bus.realtimebus.model;

import com.autonavi.bundle.routecommon.entity.Trip;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RealTimeBusline implements Serializable {
    private static final long serialVersionUID = 2891410290119295330L;
    public String lindID;
    public String stationID;
    public Map<String, stTrip> stationMap;
    public int status;
    public Map<Integer, TripInfo> tripInfoMap;
    public List<Trip> tripList;
}
