package defpackage;

import com.autonavi.ae.search.interfaces.OnSearchResultListener;
import com.autonavi.ae.search.model.GPoiBase;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;

/* renamed from: ady reason: default package */
/* compiled from: IOfflineSearchServer */
public interface ady {
    POI a(GPoiBase gPoiBase);

    void a();

    void a(float f, float f2, OnSearchResultListener onSearchResultListener);

    void a(OnSearchResultListener onSearchResultListener);

    void a(GeoPoint geoPoint, String str, OnSearchResultListener onSearchResultListener);

    boolean a(int i);

    boolean a(String str);

    boolean b();

    int c();

    boolean d();
}
