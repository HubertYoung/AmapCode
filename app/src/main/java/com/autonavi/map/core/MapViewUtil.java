package com.autonavi.map.core;

public final class MapViewUtil {
    public static int INVALID_CAMERA_DEGREE = -9999;

    public static void updateLockMapAngleState(bty bty, float f) {
        if (bty != null) {
            if (f == ((float) INVALID_CAMERA_DEGREE)) {
                f = bty.J();
            }
            if (f != 0.0f) {
                bty.B();
            } else if (!bty.C() && bin.a.o("201")) {
                bty.e(false);
            }
        }
    }
}
