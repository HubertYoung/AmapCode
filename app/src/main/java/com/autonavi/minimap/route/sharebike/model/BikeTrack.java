package com.autonavi.minimap.route.sharebike.model;

import java.io.Serializable;

public class BikeTrack implements Serializable {
    public String distance;
    public String ridingTime;
    public String startTime;
    public String totalFee;

    public BikeTrack(String str, String str2, String str3, String str4) {
        this.startTime = str;
        this.totalFee = str2;
        this.ridingTime = str3;
        this.distance = str4;
    }
}
