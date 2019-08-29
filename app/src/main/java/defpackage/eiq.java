package defpackage;

import com.autonavi.bundle.train.ajx.ModuleTrain;
import com.autonavi.minimap.ajx3.views.AmapAjxView;

/* renamed from: eiq reason: default package */
/* compiled from: ModuleTrainImpl */
public final class eiq implements bdn {

    /* renamed from: eiq$a */
    /* compiled from: ModuleTrainImpl */
    static class a {
        static eiq a = new eiq();
    }

    public final void a(AmapAjxView amapAjxView, axi axi) {
        if (amapAjxView != null) {
            ModuleTrain moduleTrain = (ModuleTrain) amapAjxView.getJsModule(ModuleTrain.MODULE_NAME);
            if (moduleTrain != null) {
                moduleTrain.setHistoryItemClickListener(axi);
            }
        }
    }
}
