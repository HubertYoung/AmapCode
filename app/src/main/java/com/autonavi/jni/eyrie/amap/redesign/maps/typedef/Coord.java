package com.autonavi.jni.eyrie.amap.redesign.maps.typedef;

public class Coord {
    public double lat;
    public double lon;
    public double z;

    private static native Coord nativeP20ToLonLat(int i, int i2);

    public Coord() {
        this.lon = 0.0d;
        this.lat = 0.0d;
        this.z = 0.0d;
    }

    public Coord(double d, double d2, double d3) {
        this.lon = d;
        this.lat = d2;
        this.z = d3;
    }

    public Coord(double d, double d2) {
        this.lon = d;
        this.lat = d2;
        this.z = 0.0d;
    }

    public Coord(int i, int i2) {
        Coord nativeP20ToLonLat = nativeP20ToLonLat(i, i2);
        this.lon = nativeP20ToLonLat.lon;
        this.lat = nativeP20ToLonLat.lat;
        this.z = 0.0d;
    }
}
