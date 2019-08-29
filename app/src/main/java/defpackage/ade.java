package defpackage;

import android.support.annotation.Nullable;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.POI;
import java.util.List;

/* renamed from: ade reason: default package */
/* compiled from: PlanDataProvider */
public class ade {
    private static ade g;
    public adb a = new adb();
    public adb b = new adb();
    public acy c;
    public RouteType d = RouteType.DEFAULT;
    public RouteType e = RouteType.DEFAULT;
    private final String f = "PlanDataProvider";
    private boolean h = true;

    private ade() {
    }

    public static ade a() {
        if (g == null) {
            synchronized (ade.class) {
                try {
                    if (g == null) {
                        g = new ade();
                    }
                }
            }
        }
        return g;
    }

    public final void a(POI poi, RouteType routeType) {
        new StringBuilder("setStartPOI() called with: startPOI = ").append(poi == null ? "null" : poi.getName());
        this.h = b(poi, adb.a(this.a.a));
        this.b = (adb) this.a.clone();
        this.a.a = poi;
        this.d = routeType;
        e();
    }

    public final void a(List<POI> list) {
        new StringBuilder("setMidPOIList: midPOIs size = ").append(list == null ? "0" : String.valueOf(list.size()));
        this.h = acj.a(list, this.a.a());
        this.b = (adb) this.a.clone();
        this.a.c = list;
        e();
    }

    private void b(POI poi, RouteType routeType) {
        new StringBuilder("setEndPOI() called with: endPOI = ").append(poi == null ? "null" : poi.getName());
        this.h = b(poi, adb.a(this.a.b));
        this.b = (adb) this.a.clone();
        this.a.b = poi;
        this.e = routeType;
        e();
    }

    public final void a(POI poi, POI poi2, RouteType routeType) {
        new StringBuilder("setPOIs() called with: startPOI = ").append(poi == null ? "null" : poi.getName());
        new StringBuilder("setPOIs() called with: endPOI = ").append(poi2 == null ? "null" : poi2.getName());
        this.h = b(poi, adb.a(this.a.a)) || b(poi2, adb.a(this.a.b));
        this.b = (adb) this.a.clone();
        this.a.a = poi;
        this.a.b = poi2;
        this.d = routeType;
        this.e = routeType;
        e();
    }

    private void a(POI poi, List<POI> list, POI poi2, RouteType routeType) {
        new StringBuilder("setAllPOIs() called with: startPOI = ").append(poi == null ? "null" : poi.getName());
        new StringBuilder("setAllPOIs() called with: middlePOI size = ").append(list == null ? "0" : String.valueOf(list.size()));
        new StringBuilder("setAllPOIs() called with: endPOI = ").append(poi2 == null ? "null" : poi2.getName());
        this.h = b(poi, adb.a(this.a.a)) || b(poi2, adb.a(this.a.b)) || acj.a(list, this.a.a());
        this.b = (adb) this.a.clone();
        this.a.a = poi;
        this.a.c = list;
        this.a.b = poi2;
        this.d = routeType;
        this.e = routeType;
        e();
    }

    @Nullable
    public final POI a(boolean z) {
        POI poi;
        if (z) {
            poi = adb.a(this.b.a);
        } else {
            poi = this.b.a;
        }
        StringBuilder sb = new StringBuilder("getOldStartPOI() called, clone = ");
        sb.append(z);
        sb.append(", startPOI = ");
        sb.append(poi == null ? "null" : poi.getName());
        return poi;
    }

    @Nullable
    public final POI b(boolean z) {
        POI poi;
        if (z) {
            poi = adb.a(this.a.a);
        } else {
            poi = this.a.a;
        }
        StringBuilder sb = new StringBuilder("getStartPOI() called, clone = ");
        sb.append(z);
        sb.append(", startPOI = ");
        sb.append(poi == null ? "null" : poi.getName());
        return poi;
    }

    @Nullable
    public final List<POI> b() {
        List<POI> a2 = this.b.a();
        new StringBuilder("getOldMidPOIList: oldMidPOIS size = ").append(a2 == null ? "0" : String.valueOf(a2.size()));
        return a2;
    }

    @Nullable
    public final List<POI> c() {
        List<POI> a2 = this.a.a();
        new StringBuilder("getMidPOIList: midPOIs size = ").append(a2 == null ? "0" : String.valueOf(a2.size()));
        return a2;
    }

    @Nullable
    public final POI c(boolean z) {
        POI poi;
        if (z) {
            poi = adb.a(this.b.b);
        } else {
            poi = this.b.b;
        }
        StringBuilder sb = new StringBuilder("getOldEndPOI() called, clone = ");
        sb.append(z);
        sb.append(", oldEndPOI = ");
        sb.append(poi == null ? "null" : poi.getName());
        return poi;
    }

    @Nullable
    public final POI d(boolean z) {
        POI poi;
        if (z) {
            poi = adb.a(this.a.b);
        } else {
            poi = this.a.b;
        }
        StringBuilder sb = new StringBuilder("getEndPOI() called, clone = ");
        sb.append(z);
        sb.append(", endPOI = ");
        sb.append(poi == null ? "null" : poi.getName());
        return poi;
    }

    public final void d() {
        this.c = null;
        g = null;
    }

    private void e() {
        new StringBuilder("notifyDataChange() called, needNotify = ").append(this.h);
        StringBuilder sb = new StringBuilder("PlanDataProvider: notifyDataChange :");
        sb.append(this.h);
        sb.append(" mDataChangeListener:");
        sb.append(this.c);
        eao.b("access_point", sb.toString());
        if (this.h && this.c != null) {
            this.c.onDataChange(b(true), c(), d(true));
        }
    }

    private static boolean b(POI poi, POI poi2) {
        return !acj.a(poi, poi2, true);
    }

    public final void a(POI poi) {
        a(poi, adf.a().b());
    }

    public final void b(POI poi) {
        b(poi, adf.a().b());
    }

    public final void a(POI poi, POI poi2) {
        a(poi, poi2, adf.a().b());
    }

    public final void a(POI poi, List<POI> list, POI poi2) {
        a(poi, list, poi2, adf.a().b());
    }
}
