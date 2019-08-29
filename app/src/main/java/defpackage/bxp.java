package defpackage;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.search.fragment.PoiDetailPageNew;
import com.autonavi.map.search.fragment.SearchResultBasePage;
import com.autonavi.map.search.view.SlidingUpPanelLayout.SlideState;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* renamed from: bxp reason: default package */
/* compiled from: SearchResultMapController */
public final class bxp extends bxo implements a {
    private bvq P = new bvq() {
    };
    public bzl a;
    private bzq d;

    /* access modifiers changed from: protected */
    public final void A() {
    }

    public final void D() {
    }

    /* access modifiers changed from: protected */
    public final void F() {
    }

    /* access modifiers changed from: protected */
    public final void J() {
    }

    public final void a(int i) {
    }

    public final void a(ccv ccv, ccy ccy) {
    }

    public final void a(POI poi, int i) {
    }

    public final void a(Object obj) {
    }

    public final void a(String str) {
    }

    public final void b_() {
    }

    public final boolean s() {
        return false;
    }

    public bxp(SearchResultBasePage searchResultBasePage) {
        super(searchResultBasePage);
    }

    /* access modifiers changed from: protected */
    public final bzh a(bty bty) {
        return new bzi(bty);
    }

    /* access modifiers changed from: 0000 */
    public final bzg B() {
        return new bzf(this, this.H);
    }

    /* access modifiers changed from: protected */
    public final void a(View view) {
        super.a(view);
        if (this.F instanceof bzq) {
            this.d = (bzq) this.F;
        } else {
            this.d = new a();
        }
        this.a = (bzl) this.F;
    }

    /* access modifiers changed from: protected */
    public final void a(PageBundle pageBundle, boolean z) {
        super.a(pageBundle, z);
        this.f.getArguments().putLong("key_search_process_key", this.j.longValue());
        InfoliteResult infoliteResult = (InfoliteResult) pageBundle.getObject("poi_search_result");
        this.e = bxd.a(this.j, Integer.valueOf(hashCode()));
        bxf bxf = new bxf();
        if (this.A == null) {
            this.A = new e(this.f.getGLMapView());
        }
        this.l = new cce(this.f.getMapManager().getMapView());
        this.k = new bxc(this.l);
        this.C.a(infoliteResult);
        this.e.d = this.D;
        this.e.b = bxf;
        this.e.g = this.h;
        this.e.f = this.C;
        this.e.a = this.A;
        this.e.c = this.k;
        this.e.e = this.l;
        this.C.a((a) this);
        M();
        af();
    }

    public final void a(Context context) {
        this.j = Long.valueOf(System.currentTimeMillis());
        super.a(context);
        this.a.a(this.C, this.B);
    }

    public final void k() {
        super.k();
    }

    public final void l() {
        this.f.getMapManager();
        super.l();
        this.D.p();
        boolean z = this.f.getArguments().getBoolean("is_go_detail");
        if (this.o || z) {
            if (this.F.j() != SlideState.ANCHORED || this.n) {
                if (this.F.j() == SlideState.COLLAPSED) {
                    Z();
                }
            } else if (!(this.H == null || this.H.c() == null)) {
                bzs bzs = this.F;
                this.H.c();
                bzs.r();
            }
        }
        if (this.H != null) {
            a c = this.H.c();
            if (c != null) {
                if (this.F.k() == SlideState.ANCHORED) {
                    if (c.c != 0.0f) {
                        this.F.r();
                    }
                } else if (this.F.k() == SlideState.COLLAPSED && c.d != 0.0f) {
                    this.F.r();
                }
            }
        }
        boolean z2 = false;
        this.o = false;
        this.f.getArguments().putBoolean("is_go_detail", false);
        Set<String> set = this.e.h;
        bxh bxh = this.C;
        if (set != null) {
            if (bxh.i == null) {
                bxh.i = new HashSet();
            }
            bxh.i.addAll(set);
        }
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            z2 = bqx.a();
        }
        this.q.d(z2);
        ai();
    }

    public final void m() {
        super.m();
        aj();
    }

    public final void a(PageBundle pageBundle) {
        if (!pageBundle.isEmpty()) {
            this.D = a(this.f.getMapManager().getMapView());
            S();
            this.j = Long.valueOf(System.currentTimeMillis());
            super.a(pageBundle);
            this.a.a(this.C);
            ah();
        }
    }

    public final void a(int i, ResultType resultType, PageBundle pageBundle) {
        if (i == 1 && resultType == ResultType.CANCEL) {
            a b = this.H.b();
            if (b != null && b.a == 3) {
                this.H.a();
                this.f.getCQLayerController().dismissCQLayer(false);
            }
        }
        this.F.a((AbstractBasePage) this.f, i, resultType, pageBundle);
    }

    public final ON_BACK_TYPE q() {
        if (this.F.q()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        this.f.finish();
        return super.q();
    }

    public final void r() {
        super.r();
    }

    /* access modifiers changed from: protected */
    public final void N() {
        super.N();
    }

    /* access modifiers changed from: protected */
    public final void G() {
        this.C = new bxh();
    }

    public final void d(int i) {
        this.F.b(1);
        g(i);
        T();
        bxf bxf = new bxf();
        bxf.a(0);
        bxf.b(i);
        bxf.a(this.C.b(i));
        if (this.F.j() == SlideState.ANCHORED) {
            bxf.e = this.M;
        }
        a(bxf, 6);
    }

    public final void e(int i) {
        b(this.D.i(), i);
    }

    public final void f(int i) {
        int i2 = this.D.i();
        bxf bxf = new bxf();
        bxf.a(2);
        bxf.b(i2);
        bxf.c(i);
        bxf.a((POI) this.E.get(i));
        bxf.e = this.M;
        a(bxf, 7);
    }

    public final void a(bzx bzx) {
        if (bzx.getPOI() != null) {
            bzx.getPOI().getPoiExtra().put("requestPoiData", Boolean.TRUE);
        }
        POI poi = bzx.getPOI();
        bxf bxf = new bxf();
        bxf.a(3);
        bxf.g = bzx;
        bxf.a(poi);
        a(bxf, 6);
    }

    public final void h(int i) {
        bxf bxf = new bxf();
        bxf.a(0);
        bxf.b(i);
        bxf.a(this.C.b(i));
        a(bxf, 2);
    }

    public final void E() {
        this.k.a(this.C.c, this.D.f, this.p, this.J);
        this.n = true;
        ac();
    }

    private void b(int i, int i2) {
        SuperId.getInstance().setBit3("19");
        POI poi = (POI) this.E.get(i2);
        poi.getPoiExtra().put("requestPoiData", Boolean.TRUE);
        bxf bxf = new bxf();
        bxf.a(1);
        bxf.b(i);
        bxf.c(i2);
        bxf.a(poi);
        bxf.e = this.M;
        a(bxf, 6);
    }

    public final void i(int i) {
        POI c = bcy.c(bcy.p(this.C.b));
        ArrayList arrayList = null;
        if (c != null) {
            SearchPoi searchPoi = (SearchPoi) c.as(SearchPoi.class);
            if (!(searchPoi.getPoiChildrenInfo() == null || searchPoi.getPoiChildrenInfo().geoList == null)) {
                arrayList = new ArrayList(searchPoi.getPoiChildrenInfo().geoList);
            }
        }
        if (arrayList != null && i >= 0 && i < arrayList.size()) {
            bxf bxf = new bxf();
            bxf.a(4);
            bxf.c(i);
            bxf.a((POI) arrayList.get(i));
            a(bxf, 6);
        }
    }

    public final void k(int i) {
        elk c = this.C.c(i);
        if (c != null) {
            POI poi = c.b;
            if (l(i)) {
                h(i);
                return;
            }
            int a2 = this.C.a(poi);
            this.k.a(this.C.c, this.D.f, this.p, this.J);
            h(a2);
        }
    }

    public final void a(int i, int i2) {
        elk c = this.C.c(i);
        if (c != null) {
            a(c.b);
            if (l(i)) {
                b(i, i2);
                return;
            }
            int a2 = this.C.a(c.b);
            this.k.a(this.C.c, this.D.f, this.p, this.J);
            b(a2, i2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00af  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void al() {
        /*
            r10 = this;
            com.autonavi.map.search.fragment.SearchResultBasePage r0 = r10.f
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            boolean r0 = com.amap.bundle.network.util.NetworkReachability.b()
            if (r0 != 0) goto L_0x0019
            android.content.Context r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
            int r1 = com.autonavi.minimap.R.string.ic_net_error_tipinfo
            java.lang.String r0 = r0.getString(r1)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r0)
            return
        L_0x0019:
            com.autonavi.map.search.fragment.SearchResultBasePage r0 = r10.f
            com.autonavi.map.core.MapManager r0 = r0.getMapManager()
            bty r0 = r0.getMapView()
            if (r0 != 0) goto L_0x0026
            return
        L_0x0026:
            java.lang.String r1 = "P00005"
            java.lang.String r2 = "B010"
            com.amap.bundle.statistics.LogManager.actionLogV2(r1, r2)
            esb r1 = defpackage.esb.a.a
            java.lang.Class<asy> r2 = defpackage.asy.class
            esc r1 = r1.a(r2)
            asy r1 = (defpackage.asy) r1
            if (r1 == 0) goto L_0x00d4
            bxh r2 = r10.C
            auk r2 = r2.e()
            if (r2 == 0) goto L_0x00d4
            bxh r2 = r10.C
            java.lang.Double[] r2 = r2.j
            r3 = 0
            if (r2 == 0) goto L_0x008b
            int r4 = r2.length
            r5 = 4
            if (r4 == r5) goto L_0x004f
            goto L_0x008b
        L_0x004f:
            r4 = 0
            r4 = r2[r4]
            r5 = 1
            r5 = r2[r5]
            r6 = 2
            r6 = r2[r6]
            r7 = 3
            r2 = r2[r7]
            if (r4 == 0) goto L_0x008b
            if (r5 == 0) goto L_0x008b
            if (r6 == 0) goto L_0x008b
            if (r2 == 0) goto L_0x008b
            com.autonavi.common.model.GeoPoint r7 = new com.autonavi.common.model.GeoPoint
            double r8 = r4.doubleValue()
            double r4 = r5.doubleValue()
            r7.<init>(r8, r4)
            com.autonavi.common.model.GeoPoint r4 = new com.autonavi.common.model.GeoPoint
            double r5 = r6.doubleValue()
            double r8 = r2.doubleValue()
            r4.<init>(r5, r8)
            android.graphics.Rect r2 = new android.graphics.Rect
            int r5 = r7.x
            int r6 = r7.y
            int r7 = r4.x
            int r4 = r4.y
            r2.<init>(r5, r6, r7, r4)
            goto L_0x008c
        L_0x008b:
            r2 = r3
        L_0x008c:
            bxh r4 = r10.C
            auk r5 = r4.e()
            if (r5 != 0) goto L_0x0095
            goto L_0x009b
        L_0x0095:
            auk r3 = r4.e()
            java.lang.String r3 = r3.j
        L_0x009b:
            if (r2 == 0) goto L_0x00af
            com.autonavi.common.model.GeoPoint r0 = new com.autonavi.common.model.GeoPoint
            int r3 = r2.centerX()
            int r2 = r2.centerY()
            r0.<init>(r3, r2)
            java.lang.String r3 = r0.getCity()
            goto L_0x00c2
        L_0x00af:
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L_0x00b6
            goto L_0x00c2
        L_0x00b6:
            com.autonavi.ae.gmap.glinterface.GLGeoPoint r0 = r0.n()
            com.autonavi.common.model.GeoPoint r0 = com.autonavi.common.model.GeoPoint.glGeoPoint2GeoPoint(r0)
            java.lang.String r3 = r0.getCity()
        L_0x00c2:
            ata r0 = r1.a()
            bxh r1 = r10.C
            java.lang.String r1 = r1.b()
            com.autonavi.map.search.fragment.SearchResultMapController$1 r2 = new com.autonavi.map.search.fragment.SearchResultMapController$1
            r2.<init>(r10)
            r0.a(r1, r3, r2)
        L_0x00d4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bxp.al():void");
    }

    private boolean l(int i) {
        return i < this.C.g();
    }

    public final void am() {
        ac();
    }

    private void a(bxf bxf, int i) {
        this.o = true;
        PageBundle a2 = bxz.a(this.f.getArguments(), this.O, this.C.b, bxf.d, "poitip", this.i);
        a2.putInt("poi_detail_page_type", i);
        a2.putLong("key_search_process_key", this.j.longValue());
        if (this.d != null && this.F.k() == SlideState.ANCHORED) {
            this.K = -1;
            this.L = -1;
        }
        a2.putInt("originalX", this.K);
        a2.putInt("originalY", this.L);
        bxd.a(this.j, Integer.valueOf(hashCode())).b = bxf;
        if (bxf.b != -1) {
            a2.putObject("key_detail_main_poi", this.C.b(bxf.b));
        }
        if (bxf.a == 3) {
            a2.putObject("key_detail_main_poi", bxf.d);
        }
        this.f.startPageForResult(PoiDetailPageNew.class, a2, 1);
    }

    /* access modifiers changed from: protected */
    public final void a(cbn cbn) {
        boolean z = this.f.getArguments().getBoolean("is_go_detail");
        if (!this.o && !z) {
            super.a(cbn);
        }
    }

    /* access modifiers changed from: protected */
    public final void x() {
        if (!this.a.b()) {
            O();
        }
    }

    public final void an() {
        this.w.setVisibility(0);
    }

    public final boolean onMapTouchEvent(MotionEvent motionEvent) {
        if (this.e != null && !this.e.i && motionEvent.getAction() == 2 && this.F.j() == SlideState.COLLAPSED) {
            this.e.i = true;
        }
        return super.onMapTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public final void I() {
        this.f.getMapManager().getOverlayManager().clearAllFocus();
        this.F.d();
        Q();
    }

    /* access modifiers changed from: protected */
    public final void K() {
        this.H.a();
        this.f.getCQLayerController().dismissCQLayer(false);
    }

    public final void a(int i, String str) {
        if (this.f.isAlive() && this.C.b != null && (!bcy.b(this.C.b) || !this.C.b.searchInfo.l.isEmpty())) {
            if (i != 8) {
                switch (i) {
                    case 2:
                    case 3:
                    case 4:
                        break;
                }
            }
            bxv.a((AbstractBaseMapPage) this.f);
            bct.a();
            aa();
            ab();
            this.f.getArguments().putObject("poi_search_result", this.C.b);
            super.N();
            P();
            x();
            this.C.f();
            ah();
            if (byb.a(this.C.b)) {
                ac();
            }
        }
    }

    public final boolean onBlankClick() {
        if (this.H != null) {
            a b = this.H.b();
            if (!(b == null || b.a == 3 || this.F.k() == SlideState.COLLAPSED || this.H.c() == null || this.H.c().c == 0.0f)) {
                SlideState slideState = SlideState.COLLAPSED;
                this.F.e(false);
            }
        }
        return super.onBlankClick();
    }

    public final void Q() {
        if (this.f.getMapManager() != null && this.f.getMapManager().getOverlayManager() != null && this.f.getMapManager().getOverlayManager().getMapPointOverlay() != null) {
            if (!((this.p != null && this.p.b >= 0) || !this.f.getMapManager().getOverlayManager().getMapPointOverlay().getItems().isEmpty() || !this.f.getMapManager().getOverlayManager().getGeoCodeOverlay().getItems().isEmpty() || this.D.n()) && !cch.a(L().e())) {
                POI b = this.C.b(0);
                if (b != null) {
                    this.D.c(b);
                }
            }
        }
    }

    public final bzh e() {
        return this.D;
    }
}
