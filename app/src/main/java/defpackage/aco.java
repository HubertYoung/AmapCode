package defpackage;

import com.amap.bundle.planhome.view.RippleLayout;
import com.autonavi.bundle.routecommon.model.RouteType;

/* renamed from: aco reason: default package */
/* compiled from: PlanTaxiOrderAnimUtil */
public class aco implements ada {
    public static aco a = null;
    public static boolean c = false;
    public RippleLayout b;
    private final String d = aco.class.getSimpleName();

    public static aco a() {
        if (a == null) {
            synchronized (aco.class) {
                try {
                    if (a == null) {
                        a = new aco();
                    }
                }
            }
        }
        return a;
    }

    public final void b() {
        StringBuilder sb = new StringBuilder("excute() called, inTaxiOrder = [");
        sb.append(c);
        sb.append("]");
        if (!c || adf.a().b() == RouteType.TAXI) {
            d();
        } else {
            c();
        }
    }

    private void c() {
        if (this.b != null) {
            this.b.setVisibility(0);
            this.b.startRippleAnimation();
        }
    }

    private void d() {
        if (this.b != null) {
            this.b.stopRippleAnimation();
            this.b.setVisibility(8);
        }
    }

    public void onTypeChange(RouteType routeType, RouteType routeType2) {
        StringBuilder sb = new StringBuilder("onTypeChange() called with: lastType = [");
        sb.append(routeType);
        sb.append("], currType = [");
        sb.append(routeType2);
        sb.append("]");
        b();
    }
}
