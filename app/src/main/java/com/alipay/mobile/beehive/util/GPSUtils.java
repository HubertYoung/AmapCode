package com.alipay.mobile.beehive.util;

public class GPSUtils {
    public static String latitudeRef(double latitude) {
        return latitude < 0.0d ? "S" : "N";
    }

    public static String longitudeRef(double longitude) {
        return longitude < 0.0d ? "W" : "E";
    }

    public static final String convert2DMS(double lol) {
        double lol2 = Math.abs(lol);
        String sOut = Integer.toString((int) lol2) + "/1,";
        double lol3 = (lol2 % 1.0d) * 60.0d;
        return (sOut + Integer.toString((int) lol3) + "/1,") + Integer.toString((int) ((lol3 % 1.0d) * 60000.0d)) + "/1000";
    }
}
