package defpackage;

import com.autonavi.bundle.routecommon.model.RouteType;
import java.util.HashSet;
import java.util.Set;

/* renamed from: adf reason: default package */
/* compiled from: PlanTypeProvider */
public class adf {
    public static adf a;
    public Set<ada> b = new HashSet();
    private final String c = "PlanTypeProvider";
    private RouteType d;
    private RouteType e = RouteType.DEFAULT;

    private adf() {
    }

    public static adf a() {
        if (a == null) {
            synchronized (adf.class) {
                try {
                    if (a == null) {
                        a = new adf();
                    }
                }
            }
        }
        return a;
    }

    public final void a(ada ada) {
        if (ada != null) {
            this.b.add(ada);
        }
    }

    public final boolean b(ada ada) {
        if (ada == null) {
            return false;
        }
        return this.b.remove(ada);
    }

    public final void a(RouteType routeType) {
        new StringBuilder("setCurrPlanType() called with: routeType = ").append(routeType == null ? "null" : routeType.getName());
        if (routeType != this.e) {
            this.d = this.e;
            this.e = routeType;
            d();
        }
    }

    public final RouteType b() {
        new StringBuilder("getCurrPlanType() called with: routeType = ").append(this.e == null ? "null" : this.e.getName());
        return this.e;
    }

    public final RouteType c() {
        new StringBuilder("getLastPlanType() called with: routeType = ").append(this.d == null ? "null" : this.d.getName());
        return this.d;
    }

    private void d() {
        for (ada next : this.b) {
            if (next != null) {
                next.onTypeChange(this.d, this.e);
            }
        }
    }
}
