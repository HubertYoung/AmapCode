package com.autonavi.jni.ae.model;

public class Coord3D {
    public double lat;
    public double lon;
    public double z;

    public Coord3D(double d, double d2, double d3) {
        this.lon = d;
        this.lat = d2;
        this.z = d3;
    }

    public Coord3D() {
        this.lon = 0.0d;
        this.lat = 0.0d;
        this.z = 0.0d;
    }
}
