package defpackage;

import android.os.Handler;
import android.os.Looper;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.bundle.routecommute.bus.bean.BusCommuteJumpBean.PoiObj;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.IClickListener;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.BaseOverlayItem;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: bah reason: default package */
/* compiled from: DriveCommuteTipsManager */
public class bah implements IClickListener {
    /* access modifiers changed from: private */
    public static final String d = "bah";
    a a = new a() {
        public final void a() {
            bag.c(bah.this.e);
            bag.e(bah.this.e);
        }

        public final void b() {
            bah.this.j.postDelayed(new Runnable() {
                public final void run() {
                    bah.this.a((String) "0");
                }
            }, 100);
        }

        public final void c() {
            bah.g(bah.this);
        }
    };
    a b = new a() {
        public final void a() {
            bag.c(2);
            bag.f(2);
        }

        public final void b() {
            bag.c(4);
            bag.f(4);
        }

        public final void c() {
            bah.this.j.postDelayed(new Runnable() {
                public final void run() {
                    bah.this.a((String) "0");
                }
            }, 100);
        }
    };
    a c = new a() {
        public final void a() {
            int a2 = bah.this.e;
            bah.this.i;
            bag.d(a2);
            bag.a(bah.this.e, (String) "0");
            bah.this.c();
        }

        public final void b() {
            azi.i();
            azi.l();
            bag.a(bah.this.e, (String) "1");
            bah.this.c();
        }
    };
    /* access modifiers changed from: private */
    public int e = 0;
    private cde f;
    private MapManager g;
    private bty h;
    /* access modifiers changed from: private */
    public AbstractBaseMapPage i;
    /* access modifiers changed from: private */
    public Handler j;
    private GeoPoint k;
    /* access modifiers changed from: private */
    public azu l;
    /* access modifiers changed from: private */
    public azs m;
    private String n;
    /* access modifiers changed from: private */
    public bai o;
    /* access modifiers changed from: private */
    public b p;

    private static int c(int i2) {
        if (i2 == 2) {
            return 1;
        }
        if (i2 == 4) {
            return 3;
        }
        if (i2 == 6) {
            return 5;
        }
        if (i2 == 8) {
            return 7;
        }
        if (i2 != 10) {
            return i2;
        }
        return 9;
    }

    public bah(AbstractBaseMapPage abstractBaseMapPage, String str) {
        this.f = abstractBaseMapPage.getSuspendManager();
        this.h = abstractBaseMapPage.getMapManager().getMapView();
        this.i = abstractBaseMapPage;
        this.n = str;
        this.g = abstractBaseMapPage.getMapManager();
        this.j = new Handler(Looper.getMainLooper());
        this.o = new bai(abstractBaseMapPage, this.h);
        this.o.e = this.b;
        this.o.d = this.a;
        this.o.f = this.c;
        this.o.g = this;
    }

    public final void a(final int i2) {
        String str = d;
        StringBuilder sb = new StringBuilder("showCommuteTips tipsType=");
        sb.append(i2);
        sb.append(" mCommuteTipType=");
        sb.append(this.e);
        azb.a(str, sb.toString());
        if (i2 != this.e) {
            switch (i2) {
                case 0:
                    return;
                case 1:
                case 3:
                case 5:
                case 7:
                case 9:
                    b(i2);
                    return;
                case 2:
                case 4:
                case 6:
                case 8:
                case 10:
                    if (bag.a(this.i) || azk.a()) {
                        b(c(i2));
                        return;
                    }
                    String str2 = d;
                    StringBuilder sb2 = new StringBuilder("openCommuteTipsView tipsType=");
                    sb2.append(i2);
                    sb2.append(" source=1");
                    azb.a(str2, sb2.toString());
                    if (!NetworkReachability.b()) {
                        azb.a(d, "openCommuteTipsView No network, clear tips!");
                        j();
                        return;
                    }
                    this.p = new bau().a(i2, (a) new a() {
                        public final void a(Object obj, int i) {
                            String i2 = bah.d;
                            StringBuilder sb = new StringBuilder("requestCommuteData commuteTipType:");
                            sb.append(i);
                            sb.append(" mCommuteTipType:");
                            sb.append(bah.this.e);
                            sb.append(" data:");
                            sb.append(obj);
                            sb.append(" isCancel:");
                            sb.append(bah.this.p.a);
                            azb.a(i2, sb.toString());
                            if (obj != null && !bah.this.p.a) {
                                if (obj instanceof azu) {
                                    azu azu = (azu) obj;
                                    if (azu.a.size() == 0) {
                                        azb.a(bah.d, "Request EtaEtdRestricData info error!");
                                        bah.this.j();
                                        return;
                                    }
                                    if (AMapPageUtil.isHomePage()) {
                                        bah.a(bah.this, azu, i2);
                                        if (azu != null && azu.b == 1) {
                                            bag.h(i);
                                        }
                                    }
                                    return;
                                }
                                if (obj instanceof azs) {
                                    azs azs = (azs) obj;
                                    if (azs.b == null || azs.a == null) {
                                        azb.a(bah.d, "Request CPointData info error!");
                                        bah.this.j();
                                    } else if (AMapPageUtil.isHomePage()) {
                                        bah.a(bah.this, azs, i2);
                                    }
                                }
                            }
                        }

                        public final void a() {
                            bah.this.j();
                        }
                    });
                    return;
                case 11:
                case 12:
                    azb.a(d, "openCommuteGuideTipsView");
                    if (this.h != null && AMapPageUtil.isHomePage() && azk.b(i2) && a.a.b() != null) {
                        if (NetworkReachability.b()) {
                            this.e = i2;
                            azg.a(new b() {
                                public final void a(boolean z) {
                                    if (z) {
                                        bah.this.c(LocationInstrument.getInstance().getLatestPosition(5));
                                        bag.g(i2);
                                        return;
                                    }
                                    bah.this.c();
                                }
                            });
                            break;
                        } else {
                            azb.a(d, "openCommuteGuideTipsView No network, destory guide tips!");
                            c();
                            return;
                        }
                    } else {
                        return;
                    }
            }
        }
    }

    public final void a() {
        azb.a(d, "destoryAllCommuteTips");
        b();
        c();
        this.e = 0;
    }

    public final void b() {
        azb.a(d, "destoryCommuteTipsOverlay");
        if (this.o.c()) {
            this.o.a();
            this.e = 0;
        }
        if (this.p != null) {
            this.p.a = true;
        }
    }

    public final void c() {
        azb.a(d, "destoryGuideTipsOverlay");
        if (this.o.d()) {
            this.o.b();
            this.e = 0;
        }
    }

    public final void d() {
        this.o.a(true);
        if (g()) {
            j();
        }
        if (h()) {
            a((Runnable) new Runnable() {
                public final void run() {
                    if (bah.this.o.d()) {
                        bai d = bah.this.o;
                        if (d.c != null) {
                            d.c.clear();
                            d.c = null;
                        }
                        bah.this.e = 0;
                    }
                }
            });
        }
        a(bag.a(this.n, this.k));
    }

    public final void e() {
        this.o.a(false);
        if (this.j != null) {
            this.j.removeCallbacksAndMessages(null);
        }
    }

    public final void f() {
        if (l()) {
            a((String) "1");
        }
    }

    public final void a(GeoPoint geoPoint) {
        if (this.k != null && this.k.equals(geoPoint)) {
            return;
        }
        if (AMapPageUtil.isHomePage() || azk.a()) {
            this.k = geoPoint;
            if (this.o.d()) {
                c(geoPoint);
            }
            if (this.e != 0) {
                if (l()) {
                    if (this.e != 10 && this.l != null) {
                        a(geoPoint, this.l);
                    } else if (this.e == 10 && this.m != null) {
                        a(geoPoint, this.m);
                    }
                } else if (m()) {
                    b(geoPoint);
                }
            }
        }
    }

    private void b(final int i2) {
        azb.a(d, "openCommuteTipsSimView tipsType=".concat(String.valueOf(i2)));
        if (i2 == 0) {
            azb.a(d, "Tips already close!");
        } else if (this.h != null && AMapPageUtil.isHomePage()) {
            if (!NetworkReachability.b()) {
                azb.a(d, "openCommuteTipsSimView No network, clear tips!");
                j();
                return;
            }
            this.p = new bau().a(i2, (a) new a() {
                public final void a(Object obj, int i) {
                    String i2 = bah.d;
                    StringBuilder sb = new StringBuilder("requestCommuteData commuteTipType:");
                    sb.append(i);
                    sb.append(" mCommuteTipType:");
                    sb.append(bah.this.e);
                    sb.append(" data:");
                    sb.append(obj);
                    sb.append(" isCancel:");
                    sb.append(bah.this.p.a);
                    azb.a(i2, sb.toString());
                    if (obj != null && !bah.this.p.a) {
                        if (obj instanceof azu) {
                            azu azu = (azu) obj;
                            if (azu.a.size() == 0) {
                                azb.a(bah.d, "Request EtaEtdRestricData info error!");
                                bah.this.j();
                                return;
                            }
                            if (AMapPageUtil.isHomePage()) {
                                bah.b(bah.this, azu, i2);
                            }
                            return;
                        }
                        if (obj instanceof azs) {
                            azs azs = (azs) obj;
                            if (azs.b == null || azs.a == null) {
                                azb.a(bah.d, "Request CPointData info error!");
                                bah.this.j();
                            } else if (AMapPageUtil.isHomePage()) {
                                bah.b(bah.this, azs, i2);
                            }
                        }
                    }
                }

                public final void a() {
                    bah.this.j();
                }
            });
        }
    }

    private void a(GeoPoint geoPoint, azu azu) {
        if (AMapPageUtil.isHomePage()) {
            this.o.a(geoPoint, azu, this.e);
        }
    }

    private void a(GeoPoint geoPoint, azs azs) {
        if (AMapPageUtil.isHomePage()) {
            this.o.a(geoPoint, azs, this.e);
        }
    }

    private void b(GeoPoint geoPoint) {
        if (AMapPageUtil.isHomePage()) {
            this.o.a(geoPoint, this.e);
        }
    }

    /* access modifiers changed from: private */
    public void c(GeoPoint geoPoint) {
        if (AMapPageUtil.isHomePage() && a.a.c()) {
            this.o.a(this.e, geoPoint);
        }
    }

    public void onClick(BaseOverlay baseOverlay, BaseOverlayItem baseOverlayItem, int i2) {
        AMapLog.d(d, "onClick   i:".concat(String.valueOf(i2)));
        if (m()) {
            this.g.getOverlayManager().clearAllFocus();
            bag.a(this.e, this.n, this.k, (String) "B280");
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                bci bci = (bci) a.a.a(bci.class);
                if (bci == null || !bci.a(pageContext)) {
                    bdl bdl = (bdl) a.a.a(bdl.class);
                    if (bdl != null && bdl.a(pageContext)) {
                        pageContext.finish();
                    }
                } else {
                    pageContext.finish();
                }
            }
            int i3 = this.e;
            if (i3 == 1) {
                i3 = 2;
            } else if (i3 == 3) {
                i3 = 4;
            } else if (i3 == 5) {
                i3 = 6;
            } else if (i3 == 7) {
                i3 = 8;
            } else if (i3 == 9) {
                i3 = 10;
            }
            this.e = i3;
            if (l()) {
                if (this.e != 10 && this.l != null) {
                    a(this.k, this.l);
                    if (this.l != null && this.l.b == 1) {
                        bag.h(this.e);
                    }
                } else if (this.e == 10 && this.m != null) {
                    a(this.k, this.m);
                }
                bag.a((String) "0", this.e);
            }
        }
    }

    /* access modifiers changed from: private */
    public void j() {
        a((Runnable) new Runnable() {
            public final void run() {
                if (bah.this.o.c()) {
                    bai d = bah.this.o;
                    if (d.b != null) {
                        d.b.clear();
                    }
                    if (d.a != null) {
                        d.a.clear();
                        d.a = null;
                    }
                    bah.this.e = 0;
                }
            }
        });
    }

    private boolean k() {
        if (this.h == null) {
            azb.a(d, "isInMapAnimation mMapView is Null!");
            return false;
        }
        boolean g2 = this.h.g();
        azb.a(d, "isInMapAnimation =".concat(String.valueOf(g2)));
        return g2;
    }

    public static void a(int i2, PageBundle pageBundle) {
        if (i2 == 17 || i2 == 18) {
            POI a2 = bag.a(pageBundle);
            if (i2 == 18) {
                auz auz = (auz) a.a.a(auz.class);
                if (auz != null) {
                    auz.b(a2);
                }
                return;
            }
            auz auz2 = (auz) a.a.a(auz.class);
            if (auz2 != null) {
                auz2.a(a2);
            }
        }
    }

    private boolean l() {
        int i2 = this.e;
        return i2 == 2 || i2 == 4 || i2 == 6 || i2 == 8 || i2 == 10;
    }

    private boolean m() {
        int i2 = this.e;
        return i2 == 1 || i2 == 3 || i2 == 5 || i2 == 7 || i2 == 9;
    }

    public final boolean g() {
        return (this.e == 0 || this.e == 12 || this.e == 11 || this.o == null || !this.o.c()) ? false : true;
    }

    public final boolean h() {
        return (this.e == 12 || this.e == 11) && this.o != null && this.o.d();
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        int i2 = this.e;
        this.e = c(this.e);
        if (m()) {
            b(this.k);
            bag.a(str, i2, this.n, this.k);
        }
    }

    private static PoiObj a(POI poi) {
        if (poi == null) {
            return null;
        }
        PoiObj poiObj = new PoiObj();
        poiObj.longitude = String.valueOf(poi.getPoint().getLongitude());
        poiObj.latitude = String.valueOf(poi.getPoint().getLatitude());
        poiObj.name = poi.getName();
        poiObj.adCode = poi.getAdCode();
        poiObj.id = poi.getId();
        poiObj.type = poi.getType();
        return poiObj;
    }

    private void a(Runnable runnable) {
        Looper mainLooper = Looper.getMainLooper();
        if (mainLooper == null || mainLooper.getThread() != Thread.currentThread()) {
            this.j.post(runnable);
        } else {
            runnable.run();
        }
    }

    static /* synthetic */ void a(bah bah, Object obj, int i2) {
        new StringBuilder("updateCommuteTipView data=").append(obj);
        if (bah.h != null && AMapPageUtil.isHomePage() && !bah.k()) {
            bah.e = i2;
            bag.a((String) "1", i2);
            if (obj instanceof azu) {
                bah.a(LocationInstrument.getInstance().getLatestPosition(5), (azu) obj);
                return;
            }
            if (obj instanceof azs) {
                bah.a(LocationInstrument.getInstance().getLatestPosition(5), (azs) obj);
            }
        }
    }

    static /* synthetic */ void a(bah bah, int i2) {
        if (bah.h != null && AMapPageUtil.isHomePage() && !bah.k()) {
            bah.e = i2;
            bah.b(LocationInstrument.getInstance().getLatestPosition(5));
            bag.a(bah.e, bah.n, bah.k, (String) "B279");
        }
    }

    static /* synthetic */ void a(bah bah, final azu azu, final int i2) {
        if (azu != null) {
            bah.a((Runnable) new Runnable() {
                public final void run() {
                    bah.this.l = azu;
                    bah.a(bah.this, (Object) azu, i2);
                }
            });
        }
    }

    static /* synthetic */ void a(bah bah, final azs azs, final int i2) {
        if (azs != null) {
            bah.a((Runnable) new Runnable() {
                public final void run() {
                    bah.this.m = azs;
                    bah.a(bah.this, (Object) azs, i2);
                }
            });
        }
    }

    static /* synthetic */ void b(bah bah, final azu azu, final int i2) {
        if (azu != null) {
            bah.a((Runnable) new Runnable() {
                public final void run() {
                    bah.this.l = azu;
                    bah.a(bah.this, i2);
                }
            });
        }
    }

    static /* synthetic */ void b(bah bah, final azs azs, final int i2) {
        if (azs != null) {
            bah.a((Runnable) new Runnable() {
                public final void run() {
                    bah.this.m = azs;
                    bah.a(bah.this, i2);
                }
            });
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x006a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void g(defpackage.bah r6) {
        /*
            com.autonavi.bundle.routecommute.bus.bean.BusCommuteJumpBean r0 = new com.autonavi.bundle.routecommute.bus.bean.BusCommuteJumpBean
            r0.<init>()
            int r6 = r6.e
            r1 = 2
            r2 = 1
            r3 = 0
            if (r6 == r1) goto L_0x0023
            r1 = 4
            if (r6 == r1) goto L_0x0017
            r1 = 6
            if (r6 == r1) goto L_0x0023
            r1 = 8
            if (r6 == r1) goto L_0x0017
            return
        L_0x0017:
            com.autonavi.common.model.POI r6 = defpackage.azf.c()
            com.autonavi.common.model.POI r1 = defpackage.azf.d()
            r4 = r1
            r1 = r6
            r6 = 0
            goto L_0x002e
        L_0x0023:
            com.autonavi.common.model.POI r6 = defpackage.azf.d()
            com.autonavi.common.model.POI r1 = defpackage.azf.c()
            r4 = r1
            r1 = r6
            r6 = 1
        L_0x002e:
            r0.commuteEndType = r6
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r5 = defpackage.azf.b()
            if (r5 == 0) goto L_0x008c
            if (r6 != r2) goto L_0x0041
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r6 = r5.home
            if (r6 == 0) goto L_0x004a
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r6 = r5.home
            int r6 = r6.source
            goto L_0x004b
        L_0x0041:
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r6 = r5.company
            if (r6 == 0) goto L_0x004a
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r6 = r5.company
            int r6 = r6.source
            goto L_0x004b
        L_0x004a:
            r6 = 0
        L_0x004b:
            if (r6 != 0) goto L_0x004e
            r2 = 0
        L_0x004e:
            r0.userType = r2
            java.lang.String r6 = "10"
            r0.from = r6
            com.autonavi.bundle.routecommute.bus.bean.BusCommuteJumpBean$PoiObj r6 = a(r1)
            r0.startPoi = r6
            com.autonavi.bundle.routecommute.bus.bean.BusCommuteJumpBean$PoiObj r6 = a(r4)
            r0.endPoi = r6
            java.lang.String r6 = com.alibaba.fastjson.JSON.toJSONString(r0)
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 != 0) goto L_0x008c
            java.lang.String r0 = d
            java.lang.String r1 = "jumpToBusPage json = "
            java.lang.String r2 = java.lang.String.valueOf(r6)
            java.lang.String r1 = r1.concat(r2)
            defpackage.azb.a(r0, r1)
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r1 = defpackage.ays.a
            r0.putString(r1, r6)
            bid r6 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            java.lang.Class<com.autonavi.bundle.routecommute.bus.details.BusCommuteListPage> r1 = com.autonavi.bundle.routecommute.bus.details.BusCommuteListPage.class
            r6.startPage(r1, r0)
        L_0x008c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bah.g(bah):void");
    }
}
