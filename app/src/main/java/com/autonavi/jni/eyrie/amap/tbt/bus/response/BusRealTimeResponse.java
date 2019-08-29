package com.autonavi.jni.eyrie.amap.tbt.bus.response;

import java.util.ArrayList;

public class BusRealTimeResponse {
    public ArrayList<RealTimeBusLineInfo> buses;
    public String code;
    public boolean result;

    public static class RealTimeBusInfo {
        public String arrival;
        public int direction;
        public String dis;
        public String station_left;
        public RealTimeBusTrack track;
        public String x;
        public String y;
    }

    public static class RealTimeBusLineInfo {
        public String line;
        public String miss_time;
        public String schedule;
        public String station;
        public int status;
        public String sub_status;
        public String sub_text;
        public ArrayList<RealTimeBusInfo> trip;
    }

    public static class RealTimeBusTrack {
        public String xs;
        public String ys;
    }
}
