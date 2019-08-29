package defpackage;

import com.amap.bundle.searchservice.service.offline.OfflinePoiEngineFactoryImpl;
import com.autonavi.ae.search.interfaces.OnSearchResultListener;
import com.autonavi.ae.search.model.GPoiBase;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;

/* renamed from: aeh reason: default package */
/* compiled from: OfflineSearchServerImpl */
public final class aeh implements ady {
    private aee a;

    public aeh(aee aee) {
        this.a = aee;
    }

    public final boolean a(int i) {
        return aee.a(i);
    }

    public final void a() {
        this.a.d();
    }

    public final boolean b() {
        return OfflinePoiEngineFactoryImpl.c().a();
    }

    public final int c() {
        aee.e();
        OfflinePoiEngineFactoryImpl c = OfflinePoiEngineFactoryImpl.c();
        if (c.a && c.b != null) {
            c.b.destroy();
            c.b = null;
            c.a = false;
        }
        return 0;
    }

    public final boolean d() {
        return aee.b();
    }

    public final void a(OnSearchResultListener onSearchResultListener) {
        this.a.a(1, onSearchResultListener, 10, false);
    }

    public final boolean a(String str) {
        return this.a.a(str);
    }

    public final void a(GeoPoint geoPoint, String str, OnSearchResultListener onSearchResultListener) {
        aee.a(geoPoint, str, onSearchResultListener);
    }

    public final POI a(GPoiBase gPoiBase) {
        return aee.a(gPoiBase);
    }

    public final void a(float f, float f2, OnSearchResultListener onSearchResultListener) {
        aee.a(f, f2, onSearchResultListener);
    }
}
