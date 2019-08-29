package defpackage;

import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.coach.util.CoachStartEndPoiTextUpdater$1;
import java.util.ArrayList;
import java.util.List;

/* renamed from: dzt reason: default package */
/* compiled from: CoachStartEndPoiTextUpdater */
public final class dzt {
    public int a = 0;
    public int b = 0;
    public dzk c = new dzk();
    List<a> d = new ArrayList();
    List<a> e = new ArrayList();
    public String f;
    public String g;
    public dzj h;
    private acg i = ((acg) defpackage.esb.a.a.a(acg.class));
    private zz j;
    private zz k;
    private RouteType l;
    private com.autonavi.common.Callback.a m;

    /* renamed from: dzt$a */
    /* compiled from: CoachStartEndPoiTextUpdater */
    static class a {
        POI a;
        String b;
        String c;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    /* renamed from: dzt$b */
    /* compiled from: CoachStartEndPoiTextUpdater */
    static class b implements defpackage.dzk.b {
        private boolean a;
        private acg b;
        private c c;
        private RouteType d;
        private dzk e;
        private dzt f;

        public b(dzt dzt, acg acg, c cVar, RouteType routeType, boolean z, dzk dzk) {
            this.f = dzt;
            this.b = acg;
            this.c = cVar;
            this.d = routeType;
            this.a = z;
            this.e = dzk;
        }

        public final void a(int i) {
            if (dzk.a == i) {
                this.b.a((String) DriveUtil.MY_LOCATION_LOADING);
                return;
            }
            if (dzk.b == i) {
                this.b.b((String) DriveUtil.MY_LOCATION_LOADING);
            }
        }

        public final void a(String str, String str2) {
            if (this.d == this.b.i()) {
                if (this.a) {
                    this.c.a = str;
                } else {
                    this.c.b = str2;
                }
                b();
                this.c.a();
                if (this.c.b()) {
                    c();
                }
            }
        }

        public final void a() {
            if (this.d == this.b.i()) {
                b();
                this.c.a();
                if (this.c.b()) {
                    c();
                }
            }
        }

        private void b() {
            if (this.a) {
                if (TextUtils.isEmpty(this.c.a)) {
                    this.f.a(this.e.d == null ? "" : this.e.d.getName());
                    return;
                }
                this.f.a(this.c.a);
                a a2 = dzt.a(this.f, true, this.e.d);
                if (a2 == null) {
                    a2 = new a(0);
                    a2.a = this.e.d;
                    this.f.d.add(a2);
                }
                a2.b = this.c.a;
            } else if (TextUtils.isEmpty(this.c.b)) {
                this.f.b(this.e.e == null ? "" : this.e.e.getName());
            } else {
                this.f.b(this.c.b);
                a a3 = dzt.a(this.f, false, this.e.e);
                if (a3 == null) {
                    a3 = new a(0);
                    a3.a = this.e.e;
                    this.f.e.add(a3);
                }
                a3.c = this.c.b;
            }
        }

        private void c() {
            boolean z = !TextUtils.isEmpty(this.c.b);
            if ((!TextUtils.isEmpty(this.c.a)) && z && TextUtils.equals(this.c.a, this.c.b)) {
                String name = this.e.d != null ? this.e.d.getName() : "";
                if ("我的位置".equals(name)) {
                    a a2 = dzt.a(this.f, true, this.e.d);
                    if (a2 != null) {
                        this.f.a(a2.b);
                    } else {
                        this.f.a(name);
                    }
                } else {
                    this.f.a(name);
                }
                String name2 = this.e.e != null ? this.e.e.getName() : "";
                if ("我的位置".equals(name2)) {
                    a a3 = dzt.a(this.f, false, this.e.e);
                    if (a3 != null) {
                        this.f.b(a3.c);
                    } else {
                        this.f.b(name2);
                    }
                } else {
                    this.f.b(name2);
                }
            }
        }
    }

    /* renamed from: dzt$c */
    /* compiled from: CoachStartEndPoiTextUpdater */
    static class c {
        String a;
        String b;
        private int c = 2;

        public final void a() {
            this.c = Math.max(0, this.c - 1);
        }

        public final boolean b() {
            return this.c == 0;
        }
    }

    public final void a(RouteType routeType, POI poi, POI poi2) {
        if (this.m != null) {
            this.m.cancel();
        }
        if (poi == null || poi2 == null || poi.getPoint() == null || poi2.getPoint() == null) {
            a(routeType, poi, poi2, 1, 1, false);
        } else {
            this.m = ReverseGeocodeManager.getReverseGeocodeResult(poi.getPoint(), new CoachStartEndPoiTextUpdater$1(this, poi2, routeType, poi));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0063, code lost:
        if ("我的位置".equals(r6.getName()) != false) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00ed, code lost:
        if ("我的位置".equals(r6.getName()) != false) goto L_0x0065;
     */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00f5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(com.autonavi.bundle.routecommon.model.RouteType r4, com.autonavi.common.model.POI r5, com.autonavi.common.model.POI r6, int r7, int r8, boolean r9) {
        /*
            r3 = this;
            boolean r0 = defpackage.bno.a
            if (r0 == 0) goto L_0x0035
            java.lang.String r0 = "CoachPOI"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "update(B) routeType: "
            r1.<init>(r2)
            if (r4 == 0) goto L_0x0019
            int r2 = r4.getValue()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x001b
        L_0x0019:
            java.lang.String r2 = ""
        L_0x001b:
            r1.append(r2)
            java.lang.String r2 = " startStatus: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r2 = " endStatus: "
            r1.append(r2)
            r1.append(r8)
            java.lang.String r1 = r1.toString()
            defpackage.eao.a(r0, r1)
        L_0x0035:
            r0 = 1
            r1 = 0
            if (r5 != 0) goto L_0x0068
            if (r6 == 0) goto L_0x0068
            acg r8 = r3.i
            com.autonavi.bundle.routecommon.model.RouteType r8 = r8.k()
            if (r8 == 0) goto L_0x0057
            acg r8 = r3.i
            com.autonavi.bundle.routecommon.model.RouteType r8 = r8.k()
            int r8 = r8.getValue()
            com.autonavi.bundle.routecommon.model.RouteType r9 = com.autonavi.bundle.routecommon.model.RouteType.COACH
            int r9 = r9.getValue()
            if (r8 != r9) goto L_0x0057
            r8 = 1
            goto L_0x0058
        L_0x0057:
            r8 = 0
        L_0x0058:
            java.lang.String r9 = "我的位置"
            java.lang.String r0 = r6.getName()
            boolean r9 = r9.equals(r0)
            if (r9 == 0) goto L_0x00f1
        L_0x0065:
            r8 = 0
            goto L_0x00f1
        L_0x0068:
            if (r6 != 0) goto L_0x0098
            if (r5 == 0) goto L_0x0098
            acg r7 = r3.i
            com.autonavi.bundle.routecommon.model.RouteType r7 = r7.j()
            if (r7 == 0) goto L_0x0088
            acg r7 = r3.i
            com.autonavi.bundle.routecommon.model.RouteType r7 = r7.j()
            int r7 = r7.getValue()
            com.autonavi.bundle.routecommon.model.RouteType r9 = com.autonavi.bundle.routecommon.model.RouteType.COACH
            int r9 = r9.getValue()
            if (r7 != r9) goto L_0x0088
            r7 = 1
            goto L_0x0089
        L_0x0088:
            r7 = 0
        L_0x0089:
            java.lang.String r9 = "我的位置"
            java.lang.String r0 = r5.getName()
            boolean r9 = r9.equals(r0)
            if (r9 == 0) goto L_0x00f1
            r7 = 0
            goto L_0x00f1
        L_0x0098:
            if (r5 == 0) goto L_0x012f
            if (r6 == 0) goto L_0x012f
            if (r9 != 0) goto L_0x00d4
            acg r9 = r3.i
            com.autonavi.bundle.routecommon.model.RouteType r9 = r9.j()
            if (r9 == 0) goto L_0x00b9
            acg r9 = r3.i
            com.autonavi.bundle.routecommon.model.RouteType r9 = r9.j()
            int r9 = r9.getValue()
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.COACH
            int r2 = r2.getValue()
            if (r9 != r2) goto L_0x00b9
            r7 = 1
        L_0x00b9:
            acg r9 = r3.i
            com.autonavi.bundle.routecommon.model.RouteType r9 = r9.k()
            if (r9 == 0) goto L_0x00d4
            acg r9 = r3.i
            com.autonavi.bundle.routecommon.model.RouteType r9 = r9.k()
            int r9 = r9.getValue()
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.COACH
            int r2 = r2.getValue()
            if (r9 != r2) goto L_0x00d4
            r8 = 1
        L_0x00d4:
            java.lang.String r9 = "我的位置"
            java.lang.String r0 = r5.getName()
            boolean r9 = r9.equals(r0)
            if (r9 == 0) goto L_0x00e2
            r7 = 0
        L_0x00e2:
            java.lang.String r9 = "我的位置"
            java.lang.String r0 = r6.getName()
            boolean r9 = r9.equals(r0)
            if (r9 == 0) goto L_0x00f1
            goto L_0x0065
        L_0x00f1:
            boolean r9 = defpackage.bno.a
            if (r9 == 0) goto L_0x011d
            java.lang.String r9 = "CoachPOI"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "update(C) routeType: "
            r0.<init>(r1)
            int r1 = r4.getValue()
            r0.append(r1)
            java.lang.String r1 = " startStatus: "
            r0.append(r1)
            r0.append(r7)
            java.lang.String r1 = " endStatus: "
            r0.append(r1)
            r0.append(r8)
            java.lang.String r0 = r0.toString()
            defpackage.eao.a(r9, r0)
        L_0x011d:
            r3.l = r4
            r3.a = r7
            r3.b = r8
            dzt$c r4 = new dzt$c
            r4.<init>()
            r3.a(r5, r4)
            r3.b(r6, r4)
            return
        L_0x012f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dzt.a(com.autonavi.bundle.routecommon.model.RouteType, com.autonavi.common.model.POI, com.autonavi.common.model.POI, int, int, boolean):void");
    }

    private void a(POI poi, c cVar) {
        if (1 != this.a) {
            if (this.j != null) {
                this.j.b();
            }
            this.j = a(poi, true, cVar);
        } else if (poi == null) {
            a((String) "");
        } else {
            a(poi.getName());
        }
    }

    private void b(POI poi, c cVar) {
        if (1 != this.b) {
            if (this.k != null) {
                this.k.b();
            }
            this.k = a(poi, false, cVar);
        } else if (poi == null) {
            b("");
        } else {
            b(poi.getName());
        }
    }

    private zz a(POI poi, boolean z, c cVar) {
        b bVar = new b(this, this.i, cVar, this.l, z, this.c);
        return this.c.a(poi, bVar, z);
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str) {
        this.f = str;
        this.i.a(str);
        a(true);
    }

    /* access modifiers changed from: 0000 */
    public final void b(String str) {
        this.g = str;
        this.i.b(str);
        a(false);
    }

    private void a(boolean z) {
        dzj dzj = this.h;
        if (dzj != null) {
            if (z) {
                dzj.a = true;
            } else {
                dzj.b = true;
            }
            dzj.a();
        }
    }

    static /* synthetic */ a a(dzt dzt, boolean z, POI poi) {
        if (z) {
            for (a next : dzt.d) {
                if (bnx.a(next.a, poi)) {
                    return next;
                }
            }
        } else {
            for (a next2 : dzt.e) {
                if (bnx.a(next2.a, poi)) {
                    return next2;
                }
            }
        }
        return null;
    }
}
