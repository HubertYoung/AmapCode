package defpackage;

import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b;
import java.util.HashSet;
import java.util.Set;

/* renamed from: bqk reason: default package */
/* compiled from: IBaseController */
public interface bqk {
    public static final Set<String> b = new HashSet();
    public static final bxf c = new bxf();

    MapBasePage a();

    void a(int i, boolean z);

    void a(bxf bxf);

    void a(GLGeoPoint gLGeoPoint);

    void a(SearchPoi searchPoi);

    void a(POI poi, int i);

    void a(b bVar);

    void a(String str);

    void a(boolean z);

    void a_(int i);

    void b(int i);

    void b(GLGeoPoint gLGeoPoint);

    boolean b();

    POI c();

    void c(int i);

    bzd d();

    bzh e();

    boolean f();

    void g();

    void h();

    int i();
}
