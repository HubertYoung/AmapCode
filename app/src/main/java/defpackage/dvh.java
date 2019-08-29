package defpackage;

import com.autonavi.bundle.busnavi.ajx.ModuleBus;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.ajx3.views.AmapAjxViewInterface;

/* renamed from: dvh reason: default package */
/* compiled from: ModuleBusImpl */
public final class dvh implements ate {

    /* renamed from: dvh$a */
    /* compiled from: ModuleBusImpl */
    static class a {
        static dvh a = new dvh();
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, axi axi) {
        if (amapAjxViewInterface != null) {
            ModuleBus moduleBus = (ModuleBus) amapAjxViewInterface.getJsModule(ModuleBus.MODULE_NAME);
            if (moduleBus != null) {
                moduleBus.setHistoryItemClickListener(axi);
            }
        }
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, POI poi, POI poi2) {
        if (amapAjxViewInterface != null) {
            ModuleBus moduleBus = (ModuleBus) amapAjxViewInterface.getJsModule(ModuleBus.MODULE_NAME);
            if (moduleBus != null) {
                moduleBus.exChangeStartEndPoi(poi, poi2);
            }
        }
    }

    public final String a(AmapAjxViewInterface amapAjxViewInterface) {
        if (amapAjxViewInterface != null) {
            ModuleBus moduleBus = (ModuleBus) amapAjxViewInterface.getJsModule(ModuleBus.MODULE_NAME);
            if (moduleBus != null) {
                return moduleBus.getOriginalBusRouteData();
            }
        }
        return null;
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, axh<IBusRouteResult> axh) {
        if (amapAjxViewInterface != null) {
            ModuleBus moduleBus = (ModuleBus) amapAjxViewInterface.getJsModule(ModuleBus.MODULE_NAME);
            if (moduleBus != null) {
                moduleBus.setRouteDataParseListener(axh);
            }
        }
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, String str) {
        if (amapAjxViewInterface != null) {
            ModuleBus moduleBus = (ModuleBus) amapAjxViewInterface.getJsModule(ModuleBus.MODULE_NAME);
            if (moduleBus != null) {
                moduleBus.setRefreshBusList(str);
            }
        }
    }

    public final void b(AmapAjxViewInterface amapAjxViewInterface, String str) {
        if (amapAjxViewInterface != null) {
            ModuleBus moduleBus = (ModuleBus) amapAjxViewInterface.getJsModule(ModuleBus.MODULE_NAME);
            if (moduleBus != null) {
                moduleBus.setOriginalBusRouteData(str);
            }
        }
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, boolean z) {
        if (amapAjxViewInterface != null) {
            ModuleBus moduleBus = (ModuleBus) amapAjxViewInterface.getJsModule(ModuleBus.MODULE_NAME);
            if (moduleBus != null) {
                moduleBus.setCommentCloseState(z);
            }
        }
    }

    public final void b(AmapAjxViewInterface amapAjxViewInterface, POI poi, POI poi2) {
        if (amapAjxViewInterface != null) {
            ModuleBus moduleBus = (ModuleBus) amapAjxViewInterface.getJsModule(ModuleBus.MODULE_NAME);
            if (moduleBus != null) {
                moduleBus.requestRouteResult(poi, poi2);
            }
        }
    }
}
