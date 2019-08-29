package defpackage;

import android.view.View;
import android.view.ViewGroup;
import com.amap.bundle.planhome.view.RouteInputViewContainer;
import com.amap.bundle.planhome.view.RouteInputViewContainer.TitleStyle;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.POI;
import com.autonavi.common.model.RouteHeaderModel;
import java.util.List;

/* renamed from: acq reason: default package */
/* compiled from: RouteInputManager */
public class acq {
    public static acq a;
    public RouteInputViewContainer b;

    private acq() {
    }

    public static acq a() {
        if (a == null) {
            synchronized (acq.class) {
                try {
                    if (a == null) {
                        a = new acq();
                    }
                }
            }
        }
        return a;
    }

    public final ViewGroup a(RouteType routeType) {
        if (this.b == null) {
            return null;
        }
        return this.b.getTabViewGroup(routeType);
    }

    public final void a(TitleStyle titleStyle) {
        if (this.b != null) {
            this.b.setStyle(titleStyle);
        }
    }

    public final void b(RouteType routeType) {
        if (this.b != null && routeType != null) {
            this.b.selectTab(routeType);
        }
    }

    public final void c(RouteType routeType) {
        if (this.b != null && routeType != null) {
            this.b.addRouteType(routeType);
        }
    }

    public final boolean b() {
        if (this.b == null) {
            return false;
        }
        return this.b.isToolbarShown();
    }

    public final void c() {
        if (this.b != null) {
            this.b.setToolbarVisibility(8);
        }
    }

    public final boolean d() {
        if (this.b == null) {
            return false;
        }
        return this.b.getEditPOIEnable();
    }

    public final boolean e() {
        if (this.b == null) {
            return false;
        }
        return this.b.canExchange();
    }

    public final void d(RouteType routeType) {
        if (this.b != null && routeType != null) {
            this.b.scrollToTab(routeType);
        }
    }

    public final boolean f() {
        if (this.b == null) {
            return false;
        }
        return this.b.isAddMiddleVisibility();
    }

    public final void e(RouteType routeType) {
        if (this.b != null && routeType != null) {
            this.b.selectTab(routeType);
        }
    }

    public final void g() {
        if (this.b != null) {
            this.b.changeStateForLevel(2);
        }
    }

    public final void a(POI poi) {
        String str = "";
        if (!(poi == null || poi.getName() == null)) {
            str = poi.getName();
        }
        a(str);
    }

    public final void a(String str) {
        if (this.b != null) {
            this.b.setStartView(str);
        }
    }

    public final String h() {
        if (this.b == null) {
            return "";
        }
        return this.b.getStartViewContent();
    }

    public final void b(POI poi) {
        String str = "";
        if (!(poi == null || poi.getName() == null)) {
            str = poi.getName();
        }
        b(str);
    }

    public final void b(String str) {
        if (this.b != null) {
            this.b.setEndView(str);
        }
    }

    public final String i() {
        if (this.b == null) {
            return "";
        }
        return this.b.getEndViewContent();
    }

    public final void a(List<POI> list) {
        String[] strArr;
        int size = list == null ? 0 : list.size();
        if (size > 0) {
            strArr = new String[size];
            for (int i = 0; i < size; i++) {
                strArr[i] = list.get(i).getName();
            }
        } else {
            strArr = null;
        }
        b(strArr);
    }

    private void b(String... strArr) {
        StringBuilder sb = new StringBuilder("RouteInputManager: setMiddleViewContent : ");
        sb.append(this.b);
        eao.b("access_point", sb.toString());
        if (this.b != null) {
            this.b.setMidView(RouteHeaderModel.getMidSummaryDes((Object[]) strArr), strArr);
        }
    }

    public final void a(String[] strArr) {
        if (this.b != null) {
            this.b.setInputHint(strArr);
        }
    }

    public final View j() {
        if (this.b == null) {
            return null;
        }
        return this.b.getInputHeaderWithoutShadow();
    }
}
