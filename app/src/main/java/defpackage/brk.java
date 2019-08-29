package defpackage;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.server.aos.serverkey;
import java.net.Proxy;
import org.json.JSONObject;

/* renamed from: brk reason: default package */
/* compiled from: EngineUtils */
public final class brk implements alq {
    public final String b() {
        return "";
    }

    public final String c() {
        return "";
    }

    public final String a(String str) {
        return serverkey.amapEncode(str);
    }

    public final void a(String str, String str2, JSONObject jSONObject) {
        LogManager.actionLogV2(str, str2, jSONObject);
    }

    public final double[] a(GLGeoPoint gLGeoPoint) {
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(gLGeoPoint);
        double[] dArr = new double[2];
        if (glGeoPoint2GeoPoint != null) {
            dArr[0] = glGeoPoint2GeoPoint.getLongitude();
            dArr[1] = glGeoPoint2GeoPoint.getLatitude();
        }
        return dArr;
    }

    public final Proxy a() {
        return ahi.a(false);
    }
}
