package com.autonavi.bundle.routecommon.inter;

import android.view.View;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import java.util.List;

public interface IRouteUI {

    public enum ContainerType {
        HEAD_VIEW,
        FLOW_VIEW,
        CONTAINER_VIEW
    }

    public interface a {
        void a();
    }

    void a(View view);

    void a(RouteType routeType);

    void a(RouteType routeType, PageBundle pageBundle);

    void a(POI poi);

    void a(POI poi, List<POI> list, POI poi2);

    void a(Class cls, RouteType routeType, PageBundle pageBundle);

    @Deprecated
    void a(Class<? extends AbstractBasePage> cls, PageBundle pageBundle);

    void a(String str);

    void a(List<POI> list);

    void a(boolean z);

    void a(ContainerType[] containerTypeArr);

    boolean a(axe axe);

    ContainerType[] a();

    View b();

    void b(View view);

    void b(axe axe);

    void b(RouteType routeType);

    void b(POI poi);

    void b(List<POI> list);

    int c(RouteType routeType);

    View c();

    POI d();

    List<POI> e();

    POI f();

    RouteType g();

    RouteType[] h();

    RouteType i();

    boolean j();

    void k();

    boolean l();

    int m();

    void n();

    boolean o();

    void p();

    int r();

    void s();
}
