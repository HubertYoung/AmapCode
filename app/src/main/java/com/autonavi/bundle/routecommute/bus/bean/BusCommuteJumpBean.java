package com.autonavi.bundle.routecommute.bus.bean;

import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse.RealTimeBusLineInfo;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRouteResponse.StopEvent;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.TaxiComparatorResponse.TaxiComparatorInfo;
import java.io.Serializable;
import java.util.ArrayList;

public class BusCommuteJumpBean implements Serializable {
    private static final long serialVersionUID = 1;
    public ArrayList<BusPath> busPaths;
    public int commuteEndType;
    public String endName;
    public PoiObj endPoi;
    public String from;
    public boolean isCpoint;
    public RealTimeBusLineInfo realTimeInfo;
    public SelectObj selectindex;
    public boolean showCloseRTToast;
    public PoiObj startPoi;
    public ArrayList<StopEvent> stopEventList;
    public TaxiComparatorInfo taxiInfo;
    public int userType;

    public static class PoiObj implements Serializable {
        public String adCode;
        public String id;
        public String latitude;
        public String longitude;
        public String name;
        public String type;
    }

    public static class SelectObj implements Serializable {
        public int alterIndex;
        public int busListIndex;
    }
}
