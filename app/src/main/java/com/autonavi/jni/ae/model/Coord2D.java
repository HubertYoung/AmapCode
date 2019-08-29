package com.autonavi.jni.ae.model;

public class Coord2D {
    public double lat;
    public double lon;

    public Coord2D(double d, double d2) {
        this.lon = d;
        this.lat = d2;
    }

    public Coord2D() {
        this.lon = 0.0d;
        this.lat = 0.0d;
    }
}
