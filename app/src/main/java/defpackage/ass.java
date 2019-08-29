package defpackage;

import com.autonavi.bundle.buscard.module.ModuleBusCard;
import com.autonavi.minimap.ajx3.views.AmapAjxViewInterface;

/* renamed from: ass reason: default package */
/* compiled from: ModuleBusCardImpl */
public final class ass implements asp {

    /* renamed from: ass$a */
    /* compiled from: ModuleBusCardImpl */
    static class a {
        static ass a = new ass();
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, axc axc) {
        if (amapAjxViewInterface != null) {
            ModuleBusCard moduleBusCard = (ModuleBusCard) amapAjxViewInterface.getJsModule(ModuleBusCard.MODULE_NAME);
            if (moduleBusCard != null) {
                moduleBusCard.setBusCardListener(axc);
            }
        }
    }
}
