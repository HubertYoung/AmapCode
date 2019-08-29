package com.autonavi.jni.eyrie.amap.tbt.bus.response;

import java.util.ArrayList;

public class BusTrafficResponse {
    public String code;
    public String message;
    public ArrayList<BusLineTrafficSegmentListSection> segList;
    public String timestamp;
    public String version;

    public static class BusLineTrafficLinkSection {
        public String eidx;
        public String sidx;
        public String v;
    }

    public static class BusLineTrafficSegmentListSection {
        public String endId;
        public String etaCoords;
        public String lnId;
        public String lnName;
        public ArrayList<BusLineTrafficLinkSection> lnk;
        public String startId;
    }
}
