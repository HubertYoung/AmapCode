package defpackage;

import com.autonavi.bundle.rideresult.ajx.ModuleRide;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.route.ajx.inter.OnAjxRideNaviInterface;

/* renamed from: eeb reason: default package */
/* compiled from: ModuleRideImpl */
public final class eeb implements awx {

    /* renamed from: eeb$a */
    /* compiled from: ModuleRideImpl */
    static class a {
        static eeb a = new eeb();
    }

    public final void a(AmapAjxView amapAjxView, axi axi) {
        if (amapAjxView != null) {
            ModuleRide moduleRide = (ModuleRide) amapAjxView.getJsModule(ModuleRide.MODULE_NAME);
            if (moduleRide != null) {
                moduleRide.setHistoryItemClickListener(axi);
            }
        }
    }

    public final void a(AmapAjxView amapAjxView, axj axj) {
        if (amapAjxView != null) {
            ModuleRide moduleRide = (ModuleRide) amapAjxView.getJsModule(ModuleRide.MODULE_NAME);
            if (moduleRide != null) {
                moduleRide.setWidgetPosListener(axj);
            }
        }
    }

    public final void a(AmapAjxView amapAjxView, OnAjxRideNaviInterface onAjxRideNaviInterface) {
        if (amapAjxView != null) {
            ModuleRide moduleRide = (ModuleRide) amapAjxView.getJsModule(ModuleRide.MODULE_NAME);
            if (moduleRide != null) {
                moduleRide.setOnNotifyChangeInterface(onAjxRideNaviInterface);
            }
        }
    }

    public final void a(AmapAjxView amapAjxView) {
        if (amapAjxView != null) {
            ModuleRide moduleRide = (ModuleRide) amapAjxView.getJsModule(ModuleRide.MODULE_NAME);
            if (moduleRide != null) {
                moduleRide.finishRideNaviCallBack();
            }
        }
    }

    public final void a(AmapAjxView amapAjxView, boolean z) {
        if (amapAjxView != null) {
            ModuleRide moduleRide = (ModuleRide) amapAjxView.getJsModule(ModuleRide.MODULE_NAME);
            if (moduleRide != null) {
                moduleRide.setOnRideAccuracyChanged(z);
            }
        }
    }

    public final String b(AmapAjxView amapAjxView) {
        if (amapAjxView != null) {
            ModuleRide moduleRide = (ModuleRide) amapAjxView.getJsModule(ModuleRide.MODULE_NAME);
            if (moduleRide != null) {
                return moduleRide.getErrorReportData();
            }
        }
        return null;
    }

    public final void a(AmapAjxView amapAjxView, axl axl) {
        if (amapAjxView != null) {
            ModuleRide moduleRide = (ModuleRide) amapAjxView.getJsModule(ModuleRide.MODULE_NAME);
            if (moduleRide != null) {
                moduleRide.setRideEndShareListener(axl);
            }
        }
    }

    public final void c(AmapAjxView amapAjxView) {
        if (amapAjxView != null) {
            ModuleRide moduleRide = (ModuleRide) amapAjxView.getJsModule(ModuleRide.MODULE_NAME);
            if (moduleRide != null) {
                moduleRide.finishRideNaviCallBack();
            }
        }
    }
}
