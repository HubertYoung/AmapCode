package defpackage;

import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;

/* renamed from: axy reason: default package */
/* compiled from: BusCommuteMainPageBase */
public abstract class axy implements ayb {
    ayi a;
    AbstractBaseMapPage b;
    POI c;
    POI d;
    int e = -1;
    int f = 0;

    public void a(AbstractBaseMapPage abstractBaseMapPage) {
        this.b = abstractBaseMapPage;
    }

    public void a(int i, String str) {
        this.e = i;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a() {
        /*
            r3 = this;
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r0 = defpackage.azf.b()
            r1 = 0
            if (r0 == 0) goto L_0x001f
            boolean r2 = r3.c()
            if (r2 == 0) goto L_0x0016
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r2 = r0.home
            if (r2 == 0) goto L_0x001f
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r0 = r0.home
            int r0 = r0.source
            goto L_0x0020
        L_0x0016:
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r2 = r0.company
            if (r2 == 0) goto L_0x001f
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r0 = r0.company
            int r0 = r0.source
            goto L_0x0020
        L_0x001f:
            r0 = 0
        L_0x0020:
            if (r0 != 0) goto L_0x0023
            goto L_0x0024
        L_0x0023:
            r1 = 1
        L_0x0024:
            r3.f = r1
            java.lang.String r0 = "song---"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "setUserTyper mUserTyper = "
            r1.<init>(r2)
            int r2 = r3.f
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            defpackage.azb.a(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.axy.a():void");
    }

    /* access modifiers changed from: 0000 */
    public final boolean b() {
        POI c2 = azf.c();
        POI d2 = azf.d();
        if (c2 == null || d2 == null) {
            return false;
        }
        if (this.e != 0 && (this.e == 1 || c())) {
            POI poi = d2;
            d2 = c2;
            c2 = poi;
        }
        if (this.c != null && this.d != null && bnx.a(c2, this.c) && bnx.a(d2, this.d)) {
            return false;
        }
        this.c = c2;
        this.d = d2;
        if (this.a == null) {
            azb.a("song---", "setStartEndPoi mBusCommuteResult is empty");
        } else {
            this.a.f = this.c;
            this.a.g = this.d;
            azb.a("song---", "setStartEndPoi---");
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final boolean c() {
        aym aym = b.a;
        if (this.e == 0 || (this.e != 1 && !aym.a(aym.b) && !aym.a(aym.d))) {
            return false;
        }
        return true;
    }
}
