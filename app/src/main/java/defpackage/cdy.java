package defpackage;

import android.os.Bundle;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;

/* renamed from: cdy reason: default package */
/* compiled from: GpsLayerItem */
public final class cdy {
    public int a = 0;
    public int b = 0;
    public int c = 0;
    public String d = null;
    protected String e;
    public Bundle f;
    public POI g = null;

    public cdy(GeoPoint geoPoint) {
        this.g = POIFactory.createPOI("", geoPoint);
        this.a = 0;
    }

    public final GeoPoint a() {
        return this.g.getPoint();
    }

    public final String b() {
        return this.d;
    }

    public final String c() {
        return this.e;
    }
}
