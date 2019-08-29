package defpackage;

import android.text.TextUtils;
import com.autonavi.common.Callback;
import com.autonavi.minimap.route.bus.busline.newmodel.RouteBuslineDataModel$1;
import com.autonavi.minimap.route.bus.inter.IBusLineResult;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.inter.impl.BusLineSearch;
import com.autonavi.minimap.route.bus.model.Bus;

/* renamed from: dun reason: default package */
/* compiled from: RouteBuslineDataModel */
public final class dun {
    public IBusLineResult a;
    public Bus b;
    public Bus c;
    public boolean d = false;
    public a e = this;
    private String f;
    private String g;

    /* renamed from: dun$a */
    /* compiled from: RouteBuslineDataModel */
    public interface a {
        void a();
    }

    public final void a(IBusLineResult iBusLineResult, Bus bus) {
        this.a = iBusLineResult;
        this.b = bus;
        this.c = null;
        if (this.b != null && !this.b.id.equals(this.b.returnId)) {
            this.f = this.b.returnId;
            this.g = this.b.areacode;
        }
    }

    public final boolean a() {
        return !TextUtils.isEmpty(this.f);
    }

    public final void b() {
        if (this.c == null) {
            BusLineSearch.a(this.f, this.g, (Callback<IBusLineSearchResult>) new RouteBuslineDataModel$1<IBusLineSearchResult>(this), true);
        } else {
            c();
        }
    }

    public final void c() {
        if (this.c != null) {
            Bus bus = this.b;
            this.b = this.c;
            this.c = bus;
            this.d = !this.d;
            if (this.e != null) {
                this.e.a();
            }
        }
    }
}
