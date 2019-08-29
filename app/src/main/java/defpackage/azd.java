package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.routecommute.common.CommuteHelper;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;

/* renamed from: azd reason: default package */
/* compiled from: DriveCommuteProxy */
public final class azd implements axv {
    private CommuteHelper a;
    private boolean b = false;

    public final void a(int i) {
    }

    public final int m() {
        return 0;
    }

    public azd(CommuteHelper commuteHelper) {
        this.a = commuteHelper;
    }

    public final void a(AbstractBaseMapPage abstractBaseMapPage) {
        if (this.a != null) {
            this.a.a(abstractBaseMapPage);
        }
    }

    public final void a() {
        if (this.a != null) {
            this.a.a();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x005a, code lost:
        if (r3.a(r0) != false) goto L_0x005e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0060  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b() {
        /*
            r5 = this;
            java.lang.String r0 = "DriveCommuteProxy"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "daihq   onDefaultPageResume >>>  mExitPoiDetailView:"
            r1.<init>(r2)
            boolean r2 = r5.b
            r1.append(r2)
            java.lang.String r2 = " isCarPref: "
            r1.append(r2)
            boolean r2 = n()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            defpackage.azb.a(r0, r1)
            boolean r0 = n()
            if (r0 == 0) goto L_0x007d
            boolean r0 = r5.b
            r1 = 0
            if (r0 == 0) goto L_0x0072
            bid r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r2 = 1
            if (r0 == 0) goto L_0x005d
            esb r3 = defpackage.esb.a.a
            java.lang.Class<bci> r4 = defpackage.bci.class
            esc r3 = r3.a(r4)
            bci r3 = (defpackage.bci) r3
            if (r3 == 0) goto L_0x0048
            boolean r3 = r3.a(r0)
            if (r3 == 0) goto L_0x0048
            goto L_0x005e
        L_0x0048:
            esb r3 = defpackage.esb.a.a
            java.lang.Class<bdl> r4 = defpackage.bdl.class
            esc r3 = r3.a(r4)
            bdl r3 = (defpackage.bdl) r3
            if (r3 == 0) goto L_0x005d
            boolean r0 = r3.a(r0)
            if (r0 == 0) goto L_0x005d
            goto L_0x005e
        L_0x005d:
            r2 = 0
        L_0x005e:
            if (r2 != 0) goto L_0x0072
            esb r0 = defpackage.esb.a.a
            java.lang.Class<axv> r2 = defpackage.axv.class
            esc r0 = r0.a(r2)
            axv r0 = (defpackage.axv) r0
            if (r0 == 0) goto L_0x007b
            r0.a(r1)
            goto L_0x007b
        L_0x0072:
            com.autonavi.bundle.routecommute.common.CommuteHelper r0 = r5.a
            if (r0 == 0) goto L_0x007b
            com.autonavi.bundle.routecommute.common.CommuteHelper r0 = r5.a
            r0.e()
        L_0x007b:
            r5.b = r1
        L_0x007d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.azd.b():void");
    }

    public final void c() {
        azb.a("DriveCommuteProxy", "daihq   onDefaultPagePause");
    }

    public final void d() {
        StringBuilder sb = new StringBuilder("daihq   onDefaultPageDestroy >>>    mExitPoiDetailView:");
        sb.append(this.b);
        azb.a("DriveCommuteProxy", sb.toString());
        if (n()) {
            this.b = true;
            return;
        }
        axv axv = (axv) a.a.a(axv.class);
        if (axv != null) {
            axv.a(0);
        }
    }

    public final void e() {
        if (this.a != null) {
            this.a.e();
        }
    }

    public final void f() {
        if (this.a != null) {
            this.a.f();
        }
    }

    public final void g() {
        if (this.a != null) {
            this.a.g();
        }
    }

    public final void h() {
        if (this.a != null) {
            this.a.h();
        }
    }

    public final void i() {
        if (this.a != null) {
            this.a.i();
        }
    }

    public final boolean j() {
        if (this.a != null) {
            return this.a.j();
        }
        return false;
    }

    public final boolean a(String str) {
        if (this.a != null) {
            return this.a.a(str);
        }
        return false;
    }

    public final void k() {
        if (this.a != null) {
            this.a.k();
        }
    }

    public final void a(String str, String str2, String str3) {
        if (this.a != null) {
            this.a.a(str, str2, str3);
        }
    }

    public final void a(boolean z) {
        if (this.a != null) {
            this.a.a(z);
        }
    }

    public final void l() {
        if (this.a != null) {
            this.a.l();
        }
    }

    public final void b(String str) {
        if (this.a != null) {
            this.a.b(str);
        }
    }

    private static boolean n() {
        String g = azi.g();
        azb.a(CommuteHelper.a, "CommuteFactory isCarPref getBusCarPref switchCommuteType = ".concat(String.valueOf(g)));
        if (TextUtils.isEmpty(g)) {
            g = "0";
        }
        azb.a(CommuteHelper.a, "CommuteFactory isCarPref switchCommuteType = ".concat(String.valueOf(g)));
        return TextUtils.equals(g, "0");
    }
}
