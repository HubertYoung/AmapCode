package defpackage;

import android.view.View;
import com.amap.bundle.planhome.view.RouteInputViewContainer;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.c;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.common.PageBundle;

/* renamed from: acx reason: default package */
/* compiled from: RouteInputEditViewDispatcher */
public final class acx implements c {
    public axe a;
    private RouteInputViewContainer b;

    public acx(RouteInputViewContainer routeInputViewContainer) {
        this.b = routeInputViewContainer;
    }

    private void a(PageBundle pageBundle, int i) {
        pageBundle.putInt("route_pass_poi", i);
        a(IRouteHeaderEvent.PASS_POI_CLICK, pageBundle);
    }

    private void b(PageBundle pageBundle, int i) {
        pageBundle.putInt("route_pass_poi", i);
        a(IRouteHeaderEvent.REMOVE_PASS_POI_CLICK, pageBundle);
    }

    public final void a(IRouteHeaderEvent iRouteHeaderEvent, PageBundle pageBundle) {
        if (this.a != null) {
            this.a.a(iRouteHeaderEvent, pageBundle);
        }
    }

    public final void a(View view, int i) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("route_edit_dispatch_widget_id", i);
        pageBundle.putObject("route_edit_dispatch_widget_view", view);
        switch (i) {
            case 1:
                a(IRouteHeaderEvent.BACK_CLICK, pageBundle);
                return;
            case 2:
                a(IRouteHeaderEvent.ADD_CLICK, pageBundle);
                return;
            case 3:
                if (this.b.canExchange()) {
                    a(IRouteHeaderEvent.EXCHANGE_CLICK, pageBundle);
                }
                return;
            case 16:
            case 17:
            case 256:
                a(IRouteHeaderEvent.START_CLICK, pageBundle);
                break;
            case 32:
            case 33:
            case 512:
            case 3840:
                a(IRouteHeaderEvent.END_CLICK, pageBundle);
                return;
            case 48:
            case 49:
            case 64:
            case 65:
            case 768:
                a(pageBundle, 0);
                return;
            case 66:
                b(pageBundle, 0);
                return;
            case 80:
            case 81:
                a(pageBundle, 1);
                return;
            case 82:
                b(pageBundle, 1);
                return;
            case 96:
            case 97:
                a(pageBundle, 2);
                return;
            case 98:
                b(pageBundle, 2);
                return;
        }
    }
}
