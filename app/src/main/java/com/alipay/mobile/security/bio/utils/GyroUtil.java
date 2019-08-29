package com.alipay.mobile.security.bio.utils;

public class GyroUtil {
    public static double getDeviceAngle(float f, float f2) {
        double d = 1.0d;
        double sqrt = ((double) f) / Math.sqrt((double) ((f2 * f2) + (f * f)));
        if (sqrt <= 1.0d) {
            if (sqrt < -1.0d) {
                d = -1.0d;
            } else {
                d = sqrt;
            }
        }
        double acos = Math.acos(d);
        if (f2 < 0.0f) {
            acos = 6.283185307179586d - acos;
        }
        return (acos * 360.0d) / 6.283185307179586d;
    }
}
