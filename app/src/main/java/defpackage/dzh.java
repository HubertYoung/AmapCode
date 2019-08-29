package defpackage;

import com.autonavi.bundle.coach.ajx.ModuleCoach;
import com.autonavi.minimap.ajx3.views.AmapAjxView;

/* renamed from: dzh reason: default package */
/* compiled from: IModuleCoachImpl */
final class dzh implements aty {

    /* renamed from: dzh$a */
    /* compiled from: IModuleCoachImpl */
    static class a {
        static dzh a = new dzh();
    }

    dzh() {
    }

    public final void a(AmapAjxView amapAjxView, axi axi) {
        if (amapAjxView != null) {
            ModuleCoach moduleCoach = (ModuleCoach) amapAjxView.getJsModule(ModuleCoach.MODULE_NAME);
            if (moduleCoach != null) {
                moduleCoach.setHistoryItemClickListener(axi);
            }
        }
    }
}
