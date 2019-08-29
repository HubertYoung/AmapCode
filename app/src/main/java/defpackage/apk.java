package defpackage;

import com.autonavi.bundle.airticket.module.ModuleAirTicket;
import com.autonavi.minimap.ajx3.views.AmapAjxViewInterface;

/* renamed from: apk reason: default package */
/* compiled from: IModuleAirTicketImpl */
public final class apk implements apm {

    /* renamed from: apk$a */
    /* compiled from: IModuleAirTicketImpl */
    static class a {
        static apk a = new apk();
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, axi axi) {
        if (amapAjxViewInterface != null) {
            ModuleAirTicket moduleAirTicket = (ModuleAirTicket) amapAjxViewInterface.getJsModule(ModuleAirTicket.MODULE_NAME);
            if (moduleAirTicket != null) {
                moduleAirTicket.setHistoryItemClickListener(axi);
            }
        }
    }
}
