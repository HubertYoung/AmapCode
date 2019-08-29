package defpackage;

import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import java.util.ArrayList;

@BundleInterface(bax.class)
/* renamed from: ecx reason: default package */
/* compiled from: RoutePlanService */
public class ecx extends esi implements bax {
    private static final String b = "ecx";
    private static final ArrayList<String> c;
    acg a = ((acg) a.a.a(acg.class));

    static {
        ArrayList<String> arrayList = new ArrayList<>();
        c = arrayList;
        arrayList.add("original_route");
        c.add("bundle_key_poi_start");
        c.add("bundle_key_poi_end");
    }

    public final RouteType a() {
        if (this.a != null) {
            return this.a.a();
        }
        return null;
    }

    public final void a(RouteType routeType) {
        if (this.a != null) {
            this.a.a(routeType);
        }
    }

    public final String b() {
        if (this.a != null) {
            return this.a.b();
        }
        return null;
    }

    public final void a(IRouteResultData iRouteResultData, RouteType routeType) {
        if (this.a != null) {
            this.a.a(iRouteResultData, routeType);
        }
    }

    public final void a(PageBundle pageBundle) {
        if (this.a != null) {
            this.a.a(pageBundle);
        }
    }

    public final void b(PageBundle pageBundle) {
        if (this.a != null) {
            this.a.b(pageBundle);
        }
    }

    public final void c(PageBundle pageBundle) {
        if (this.a != null) {
            this.a.c(pageBundle);
        }
    }

    public final int c() {
        if (this.a != null) {
            return this.a.c();
        }
        return 0;
    }

    public final boolean d() {
        if (this.a != null) {
            return this.a.d();
        }
        return false;
    }

    public final String e() {
        return this.a != null ? this.a.e() : "8.3.0.0";
    }
}
