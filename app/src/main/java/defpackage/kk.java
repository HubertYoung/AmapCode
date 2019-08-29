package defpackage;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.server.aos.ServerkeyGeoPoint;
import com.autonavi.server.aos.serverkey;

/* renamed from: kk reason: default package */
/* compiled from: Projection */
public final class kk {
    public static GeoPoint a(double d, double d2) {
        ServerkeyGeoPoint serverkeyGeoPoint = new ServerkeyGeoPoint();
        if (serverkey.translatePointLocal((int) (d * 1000000.0d), (int) (d2 * 1000000.0d), serverkeyGeoPoint) != 0) {
            return new GeoPoint(d, d2);
        }
        return cob.a(((double) serverkeyGeoPoint.y) / 1000000.0d, ((double) serverkeyGeoPoint.x) / 1000000.0d);
    }
}
