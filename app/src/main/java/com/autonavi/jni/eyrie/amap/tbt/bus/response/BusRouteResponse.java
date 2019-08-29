package com.autonavi.jni.eyrie.amap.tbt.bus.response;

import java.util.ArrayList;

public class BusRouteResponse {
    public String alternative;
    public String bsid;
    public ArrayList<BusPath> buslist;
    public String code;
    public String count;
    public String dynamicDataDesc;
    public String end_desc;
    public String message;
    public String rcDataTimestamp;
    public String rcDataVersion;
    public String rcVersion;
    public String result;
    public String samecity;
    public ShowInput show_input;
    public String startEndDis;
    public String start_desc;
    public ArrayList<StopEvent> stopEventList;
    public String taxicost;
    public String taxitime;
    public String ticketshow;
    public String time_tag;
    public String timestamp;
    public String version;
    public String why;

    public static class ShowInput {
        public String content;
        public String title;
        public String type;
    }

    public static class StopEvent {
        public String busid;
        public String busname;
        public String esdescription;
        public String esstatus;
        public String eventTag;
        public String ldescription;
        public String linetype;
        public String sdescription;
        public String sstatus;
    }
}
