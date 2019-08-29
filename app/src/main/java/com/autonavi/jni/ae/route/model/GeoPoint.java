package com.autonavi.jni.ae.route.model;

public class GeoPoint {
    public static final double EarthCircumferenceInMeters = 4.007501668557849E7d;
    public static final int EarthRadiusInMeters = 6378137;
    public static final int MAXZOOMLEVEL = 20;
    public static final double MaxLatitude = 85.0511287798d;
    public static final double MaxLongitude = 180.0d;
    public static final double MinLatitude = -85.0511287798d;
    public static final double MinLongitude = -180.0d;
    public static final int PixelsPerTile = 256;
    public static final int TileSplitLevel = 0;
    public int x;
    public int y;

    public GeoPoint() {
    }

    public GeoPoint(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public GeoPoint(double d, double d2) {
        LatLongToPixels(d, d2, 20);
    }

    public void setLonLat(double d, double d2) {
        LatLongToPixels(d, d2, 20);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public double getLatitude() {
        return (1.5707963267948966d - (Math.atan(Math.exp((-(2.0037508342789244E7d - (((double) this.y) * 0.14929107086948487d))) / 6378137.0d)) * 2.0d)) * 57.29577951308232d;
    }

    public double getLongitude() {
        return (((((double) this.x) * 0.14929107086948487d) - 2.0037508342789244E7d) / 6378137.0d) * 57.29577951308232d;
    }

    private void LatLongToPixels(double d, double d2, int i) {
        double sin = Math.sin((Clip(d2, -85.0511287798d, 85.0511287798d) * 3.141592653589793d) / 180.0d);
        double log = Math.log((sin + 1.0d) / (1.0d - sin)) * 3189068.0d;
        long j = 256 << i;
        double d3 = 4.007501668557849E7d / ((double) j);
        double d4 = (double) (j - 1);
        this.x = (int) Clip((((((Clip(d, -180.0d, 180.0d) * 3.141592653589793d) / 180.0d) * 6378137.0d) + 2.0037508342789244E7d) / d3) + 0.5d, 0.0d, d4);
        this.y = (int) Clip(((2.0037508342789244E7d - log) / d3) + 0.5d, 0.0d, d4);
    }

    private double Clip(double d, double d2, double d3) {
        return Math.min(Math.max(d, d2), d3);
    }
}
