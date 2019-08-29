package defpackage;

import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.POI;

/* renamed from: eag reason: default package */
/* compiled from: RouteRequestParam */
public final class eag {
    public RouteType a;
    public POI b;
    public POI c;
    public String d;
    public long e = -1;

    public eag() {
    }

    public eag(RouteType routeType, POI poi, POI poi2) {
        this.a = routeType;
        this.b = poi;
        this.c = poi2;
    }
}
