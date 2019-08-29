package com.autonavi.bl.search;

import java.util.ArrayList;

public class InfoliteResponse {
    public String bounds;
    public ArrayList<Bus> bus_list;
    public String busline_count;
    public ArrayList<Busline> busline_list;
    public ArrayList<Classify> classify;
    public String classify_json;
    public int code;
    public String codepoint;
    public String general_flag;
    public String is_general_search;
    public String keywords;
    public Locres locres;
    public Lqii lqii;
    public String message;
    public ArrayList<PoilistPoiInfo> poi_list;
    public String result;
    public Routing routing;
    public ArrayList<Scenefilter> scenefilter;
    public String self_navigation;
    public Suggestion suggestion;
    public String timestamp;
    public int total;
    public String version;
}
