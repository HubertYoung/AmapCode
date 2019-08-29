package defpackage;

import android.content.Context;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;

/* renamed from: djm reason: default package */
/* compiled from: LineErrorInfo */
public final class djm {
    public int a;
    public int b = 0;
    public String c;
    public GeoPoint d = new GeoPoint();
    public POI e = POIFactory.createPOI("", this.d);
    public String f = "";
    public String g = "";
    public String h = "00";

    public final boolean a() {
        return this.b == 7 || this.b == 3 || this.b == 6 || this.b == 5 || this.b == 8 || this.b == 9 || this.b == 10 || this.b == 12;
    }

    public final void a(Context context, int i) {
        this.b = i;
        this.c = djs.a(i);
        this.f = djs.a(context, i);
    }
}
