package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import java.util.List;

/* renamed from: acg reason: default package */
/* compiled from: IPlanHomeService */
public interface acg extends esc {
    RouteType a();

    void a(acy acy);

    void a(ada ada);

    void a(RouteType routeType);

    void a(@NonNull RouteType routeType, POI poi);

    void a(@NonNull RouteType routeType, List<POI> list);

    void a(PageBundle pageBundle);

    void a(POI poi);

    void a(POI poi, POI poi2, RouteType routeType);

    void a(IRouteResultData iRouteResultData, RouteType routeType);

    void a(String str);

    void a(List<POI> list);

    void a(String[] strArr);

    boolean a(POI poi, POI poi2);

    boolean a(List<POI> list, List<POI> list2);

    String b();

    void b(@NonNull RouteType routeType, POI poi);

    void b(PageBundle pageBundle);

    void b(POI poi);

    void b(String str);

    boolean b(ada ada);

    int c();

    void c(PageBundle pageBundle);

    boolean d();

    String e();

    @Nullable
    POI f();

    @Nullable
    List<POI> g();

    @Nullable
    POI h();

    RouteType i();

    RouteType j();

    RouteType k();
}
