package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.routecommute.bus.details.ModuleBusCommuteDetails;
import com.autonavi.bundle.routecommute.modlue.ModuleCommuteCommon;
import com.autonavi.bundle.routecommute.modlue.ModuleDriveRouteCommute;
import com.autonavi.minimap.ajx3.Ajx;

@MultipleImpl(ema.class)
/* renamed from: bav reason: default package */
/* compiled from: AjxRegister */
public class bav implements ema {
    public void onWidgetRegister() {
    }

    public void onModuleRegister() {
        Ajx.getInstance().registerModule(ModuleBusCommuteDetails.class);
        Ajx.getInstance().registerModule(ModuleDriveRouteCommute.class);
        Ajx.getInstance().registerModule(ModuleCommuteCommon.class);
    }
}
