package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.routecommon.ajx.ModuleRoute;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.route.common.view.RouteBanner;

@MultipleImpl(ema.class)
/* renamed from: eib reason: default package */
/* compiled from: AjxRegister */
public class eib implements ema {
    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleRoute.class);
    }

    public void onWidgetRegister() {
        Ajx.getInstance().registerView("prompttip", RouteBanner.class);
    }
}
