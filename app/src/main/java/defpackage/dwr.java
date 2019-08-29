package defpackage;

import android.app.Dialog;
import com.autonavi.bundle.routecommon.entity.BusPathSection;

/* renamed from: dwr reason: default package */
/* compiled from: RouteBusIrregularTimeManager */
public final class dwr {
    public Dialog a;
    public a b;

    /* renamed from: dwr$a */
    /* compiled from: RouteBusIrregularTimeManager */
    public interface a {
        Dialog a(BusPathSection busPathSection);
    }

    public final void a() {
        if (this.a != null) {
            this.a.dismiss();
            this.a = null;
        }
    }
}
