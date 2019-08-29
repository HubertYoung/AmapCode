package defpackage;

import com.autonavi.minimap.map.DPoint;

/* renamed from: bey reason: default package */
/* compiled from: SearchServerUtil */
public final class bey {
    public static DPoint a(long j, long j2) {
        DPoint dPoint = new DPoint();
        dPoint.y = 1.5707963267948966d - (Math.atan(Math.exp((-(2.0037508342789244E7d - (((double) j2) * 0.14929107086948487d))) / 6378137.0d)) * 2.0d);
        dPoint.y *= 57.29577951308232d;
        dPoint.x = ((((double) j) * 0.14929107086948487d) - 2.0037508342789244E7d) / 6378137.0d;
        dPoint.x *= 57.29577951308232d;
        return dPoint;
    }
}
