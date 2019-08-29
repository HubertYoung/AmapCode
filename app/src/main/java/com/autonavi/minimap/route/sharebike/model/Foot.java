package com.autonavi.minimap.route.sharebike.model;

import com.autonavi.common.model.GeoPoint;
import java.io.Serializable;
import java.util.Vector;

public class Foot implements Serializable {
    public String distance;
    public Vector<GeoPoint> points;
    public String time;

    public Foot(String str, String str2, Vector<GeoPoint> vector) {
        this.distance = str;
        this.time = str2;
        this.points = vector;
    }
}
