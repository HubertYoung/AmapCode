package defpackage;

import android.app.Dialog;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.common.model.GeoPoint;
import java.util.ArrayList;

/* renamed from: dwq reason: default package */
/* compiled from: RouteBusAlterListManager */
public final class dwq {
    public Dialog a;
    public b b;
    public a c;
    public int d;

    /* renamed from: dwq$a */
    /* compiled from: RouteBusAlterListManager */
    public interface a {
        void a(boolean z, boolean z2);
    }

    /* renamed from: dwq$b */
    /* compiled from: RouteBusAlterListManager */
    public interface b {
        Dialog a(BusPath busPath, GeoPoint geoPoint, ArrayList<BusPathSection> arrayList, com.autonavi.minimap.route.bus.widget.RouteBusAlertListDialog.a aVar);
    }

    public final void a() {
        if (this.a != null) {
            this.a.dismiss();
            this.a = null;
        }
    }
}
