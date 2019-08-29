package com.autonavi.minimap.route.foot.model;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class FootEndUGCData {
    public EndBean end;
    public String naviid;
    public int source;
    public StartBean start;

    @KeepClassMembers
    @KeepName
    public class EndBean {
        public String address;
        public String end_poi_extension;
        public String lat;
        public String lon;
        public String name;
        public String new_type;
        public String phoneNumbers;
        public String poiid;
        public String transparent;
        public String type;
        public String x;
        public String y;

        public EndBean() {
        }
    }

    @KeepClassMembers
    @KeepName
    public class StartBean {
        public String lon;
        public String name;
        public String x;
        public String y;

        public StartBean() {
        }
    }
}
