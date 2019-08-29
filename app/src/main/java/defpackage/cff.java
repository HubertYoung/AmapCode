package defpackage;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.server.aos.ServerkeyGeoPoint;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.server.aos.serverkey;

/* renamed from: cff reason: default package */
/* compiled from: Projection */
public final class cff {
    public static GeoPoint a(int i, int i2) {
        DPoint a = cob.a((long) i, (long) i2, 20);
        ServerkeyGeoPoint serverkeyGeoPoint = new ServerkeyGeoPoint();
        if (serverkey.translatePointLocal((int) (a.x * 1000000.0d), (int) (a.y * 1000000.0d), serverkeyGeoPoint) != 0) {
            return new GeoPoint(i, i2);
        }
        return cob.a(((double) serverkeyGeoPoint.y) / 1000000.0d, ((double) serverkeyGeoPoint.x) / 1000000.0d);
    }
}
