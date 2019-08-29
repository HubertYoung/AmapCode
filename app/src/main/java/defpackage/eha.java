package defpackage;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.minimap.route.sharebike.model.AbsBicycleItem;
import com.autonavi.minimap.route.sharebike.model.BicycleBasicItem;
import com.autonavi.minimap.route.sharebike.model.BicycleStatus;
import com.autonavi.minimap.route.sharebike.overlay.RouteShareBikeLineOverlay;
import com.autonavi.minimap.route.sharebike.overlay.ShareBikeFootTipOverlay;
import com.autonavi.minimap.route.sharebike.overlay.ShareBikeIconFoucesOverlay;
import com.autonavi.minimap.route.sharebike.overlay.ShareBikeIconOverlay;
import com.autonavi.minimap.route.sharebike.overlay.ShareBikeIndicatorOverlay;
import com.autonavi.minimap.route.sharebike.overlay.ShareBikeNearOverlay;
import com.autonavi.minimap.route.sharebike.overlay.ShareBikeShadowOverlay;
import com.autonavi.minimap.route.sharebike.page.ShareBikePage;
import java.util.ArrayList;
import java.util.List;

/* renamed from: eha reason: default package */
/* compiled from: ShareBikePageOverlayManager */
public final class eha {
    ShareBikePage a;
    Context b;
    bty c;
    public List<BicycleBasicItem> d;
    public a e;
    String f = "500";
    public GeoPoint g;
    ShareBikeIconOverlay h;
    Rect i;
    private PointOverlayItem j;
    private ShareBikeIconFoucesOverlay k;
    private RouteShareBikeLineOverlay l;
    private ShareBikeFootTipOverlay m;
    private ShareBikeIndicatorOverlay n;
    private ShareBikeNearOverlay o;
    private ShareBikeShadowOverlay p;
    private ean q;

    /* renamed from: eha$a */
    /* compiled from: ShareBikePageOverlayManager */
    public interface a {
        void a();

        void a(GeoPoint geoPoint, BicycleBasicItem bicycleBasicItem, String str);
    }

    public eha(ShareBikePage shareBikePage) {
        this.a = shareBikePage;
        this.c = shareBikePage.getMapManager().getMapView();
        this.b = shareBikePage.getContext();
        this.p = new ShareBikeShadowOverlay(this.c);
        this.h = new ShareBikeIconOverlay(this.c);
        this.k = new ShareBikeIconFoucesOverlay(this.c);
        this.k.setOnIconFoucesDrawOverCallBack(new com.autonavi.minimap.route.sharebike.overlay.ShareBikeIconFoucesOverlay.a() {
            public final void a(PointOverlayItem pointOverlayItem, BicycleBasicItem bicycleBasicItem) {
                if (bicycleBasicItem != null) {
                    GeoPoint geoPoint = pointOverlayItem.getGeoPoint();
                    if (eha.this.b != null && NetworkReachability.b()) {
                        eha.this.a((String[]) null, geoPoint, false, true);
                    }
                    eha.this.e.a(geoPoint, bicycleBasicItem, eha.this.f);
                }
            }
        });
        this.l = new RouteShareBikeLineOverlay(this.c);
        this.m = new ShareBikeFootTipOverlay(this.c);
        this.m.setAnimatorType(9);
        this.n = new ShareBikeIndicatorOverlay(this.c);
        this.o = new ShareBikeNearOverlay(this.c);
        this.n.setOverlayOnTop(true);
        this.n.setClickable(false);
        this.m.setOverlayOnTop(true);
        this.p.setMoveToFocus(false);
        this.h.setMoveToFocus(false);
        this.h.setAutoSetFocus(false);
        this.k.setMoveToFocus(false);
        this.m.setMoveToFocus(false);
        this.n.setMoveToFocus(false);
        this.o.setMoveToFocus(false);
        if (this.h == null) {
            eao.a((String) "Amap#", (String) "Bike# Bike Icon Click RETURN !!");
        } else {
            this.h.setOnItemClickListener(new OnItemClickListener() {
                public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                    PointOverlayItem pointOverlayItem = (PointOverlayItem) obj;
                    if (pointOverlayItem != null) {
                        eha.a(eha.this, eha.this.h.getItemIndex(pointOverlayItem), pointOverlayItem);
                    }
                }
            });
        }
        if (this.o == null) {
            eao.a((String) "Amap#", (String) "Bike# Bike Icon Click RETURN !!");
        } else {
            this.o.setOnItemClickListener(new OnItemClickListener() {
                public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                    if (eha.this.d != null && !eha.this.d.isEmpty() && eha.this.h != null && eha.this.h.getItem(0) != null) {
                        eha.a(eha.this, 0, (PointOverlayItem) eha.this.h.getItem(0));
                    }
                }
            });
        }
        if (this.m == null) {
            eao.a((String) "Amap#", (String) "Bike# Foot Tip Click RETURN !!");
        } else {
            this.m.setonTipItemClick(new com.autonavi.minimap.route.sharebike.overlay.ShareBikeFootTipOverlay.a() {
                public final void a() {
                    if (eha.this.e != null) {
                        eha.this.e.a();
                    }
                }
            });
        }
        shareBikePage.addOverlay(this.l);
        shareBikePage.addOverlay(this.p);
        shareBikePage.addOverlay(this.h);
        shareBikePage.addOverlay(this.o);
        shareBikePage.addOverlay(this.k);
        shareBikePage.addOverlay(this.n);
        shareBikePage.addOverlay(this.m);
        this.q = new ean(shareBikePage.getMapManager().getMapView(), this.l, null);
        this.h.setLinkOverlay(this.p, this.o);
    }

    public final void a(BicycleStatus bicycleStatus, boolean z, boolean z2) {
        if (bicycleStatus != null) {
            ego bicycle = bicycleStatus.getBicycle();
            if (bicycle != null) {
                egp egp = bicycle.a;
                if (egp != null) {
                    List<AbsBicycleItem> list = egp.c;
                    if (list != null && !list.isEmpty() && (list instanceof ArrayList)) {
                        this.d = (List) ((ArrayList) list).clone();
                        if (this.d != null && !this.d.isEmpty()) {
                            this.g = new GeoPoint(this.d.get(0).getX(), this.d.get(0).getY());
                            this.f = bicycleStatus.getBicycle().a.b;
                            if (z2) {
                                this.h.setAnimatorType(7);
                                this.h.drawShareBikeIcon(this.d, true, z);
                            } else {
                                this.h.setAnimatorType(0);
                                this.h.drawShareBikeIcon(this.d, false, z);
                            }
                            if (!z) {
                                int n2 = n();
                                if (n2 >= 0 && n2 < this.d.size()) {
                                    this.k.drawBikeIconFouces(this.d.get(n2), false);
                                }
                            } else if (z2) {
                                this.o.setAnimatorType(9);
                            } else {
                                this.o.setAnimatorType(0);
                            }
                        }
                    }
                }
            }
        }
    }

    public final void a(GeoPoint[] geoPointArr) {
        if (this.l != null && geoPointArr != null && geoPointArr.length != 0) {
            this.l.clear();
            this.l.createAndAddBackgroundLineItem(geoPointArr);
            this.l.createAndAddArrowLineItem(geoPointArr);
        }
    }

    public final void a(String[] strArr, GeoPoint geoPoint, boolean z, boolean z2) {
        if (z2) {
            this.m.setAnimatorType(9);
        } else {
            this.m.setAnimatorType(0);
        }
        this.m.setOverlayEnabled(z);
        this.m.drawFootTipOverlay(strArr, geoPoint);
    }

    public final void a(GeoPoint geoPoint) {
        this.n.drawIndicatorOverlay(geoPoint);
    }

    public final void a() {
        b();
        c();
        d();
    }

    public final void b() {
        if (this.h != null) {
            this.h.clear();
        }
        h();
        i();
    }

    private void h() {
        if (this.p != null) {
            this.p.clear();
        }
    }

    public final void c() {
        if (this.o != null) {
            this.o.clear();
        }
    }

    public final void d() {
        k();
        l();
        j();
    }

    private void i() {
        if (this.k != null && this.h != null) {
            this.k.clear();
        }
    }

    public final void e() {
        i();
        m();
        if (this.h != null) {
            this.h.setClickId("");
        }
    }

    private void j() {
        if (this.n != null) {
            this.n.clear();
        }
    }

    private void k() {
        if (this.l != null) {
            this.l.clear();
        }
    }

    private void l() {
        if (this.m != null) {
            this.m.clear();
        }
    }

    private void m() {
        if (this.j != null && !this.j.isIconVisible()) {
            this.h.setPointItemVisble(n(), true, true);
        }
    }

    private int n() {
        if (this.c == null || this.d == null || this.h == null) {
            return -1;
        }
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            BicycleBasicItem bicycleBasicItem = this.d.get(i2);
            if (bicycleBasicItem != null && TextUtils.equals(bicycleBasicItem.getId(), this.h.getClickId())) {
                return i2;
            }
        }
        return -1;
    }

    public final void f() {
        if (this.q == null) {
            eao.a((String) "Amap#", (String) "Bike# Line Operation is null !!");
        } else {
            this.q.b();
        }
    }

    private static double a(double d2, double d3) {
        return 20.0d - (Math.log(d3 / d2) / Math.log(2.0d));
    }

    public final int a(int i2, int i3, int i4, int i5) {
        if (this.l == null) {
            eao.a((String) "Amap#", (String) "Bike# fillFootLine is failed !! line overlay is null");
            return -1;
        } else if (Math.abs(i2 - i4) < 10 && Math.abs(i3 - i5) < 10) {
            return -1;
        } else {
            return this.l.createAndAddLinkPathItem(new GeoPoint[]{new GeoPoint(i2, i3), new GeoPoint(i4, i5)});
        }
    }

    public final void g() {
        if (this.h != null) {
            this.h.clearRunnable();
        }
    }

    public final void a(BicycleStatus bicycleStatus, GeoPoint geoPoint) {
        Rect rect;
        float f2;
        if (bicycleStatus != null) {
            ego bicycle = bicycleStatus.getBicycle();
            if (bicycle != null) {
                egp egp = bicycle.a;
                if (egp != null) {
                    List<AbsBicycleItem> list = egp.c;
                    if (list != null && !list.isEmpty() && (list instanceof ArrayList)) {
                        ArrayList arrayList = (ArrayList) list;
                        if (!arrayList.isEmpty() && this.c != null && geoPoint != null) {
                            GeoPoint[] geoPointArr = new GeoPoint[list.size()];
                            for (int i2 = 0; i2 < list.size(); i2++) {
                                BicycleBasicItem bicycleBasicItem = (BicycleBasicItem) arrayList.get(i2);
                                if (bicycleBasicItem != null) {
                                    geoPointArr[i2] = new GeoPoint(bicycleBasicItem.getX(), bicycleBasicItem.getY());
                                }
                            }
                            if (geoPointArr.length < 2 || geoPoint == null) {
                                rect = null;
                            } else {
                                int i3 = 999999999;
                                int i4 = 999999999;
                                int i5 = -999999999;
                                int i6 = -999999999;
                                for (GeoPoint geoPoint2 : geoPointArr) {
                                    i3 = Math.min(i3, geoPoint2.x);
                                    i4 = Math.min(i4, geoPoint2.y);
                                    i5 = Math.max(i5, geoPoint2.x);
                                    i6 = Math.max(i6, geoPoint2.y);
                                }
                                int i7 = geoPoint.x;
                                int i8 = geoPoint.y;
                                int i9 = i3 - i7;
                                int i10 = i5 - i7;
                                int abs = Math.abs(i9) > Math.abs(i10) ? Math.abs(i9) : Math.abs(i10);
                                int i11 = i4 - i8;
                                int i12 = i6 - i8;
                                int abs2 = Math.abs(i11) > Math.abs(i12) ? Math.abs(i11) : Math.abs(i12);
                                rect = new Rect(i7 - abs, i8 - abs2, i7 + abs, i8 + abs2);
                            }
                            if (rect != null && this.c != null && geoPoint != null) {
                                if (this.c == null) {
                                    f2 = 0.0f;
                                } else {
                                    float U = this.c.U();
                                    f2 = Math.min(19.0f, Math.max(3.0f, Math.min((float) a((double) ((((float) this.c.al()) - ((float) agn.a(AMapPageUtil.getAppContext(), 80.0f))) * U), (double) rect.width()), (float) a((double) ((((float) this.c.am()) - ((float) agn.a(AMapPageUtil.getAppContext(), 390.0f))) * U), (double) rect.height()))));
                                }
                                if (f2 != 0.0f) {
                                    this.c.a(400, f2 == -1.0f ? -9999.0f : f2, 0, 0, geoPoint.x, geoPoint.y, true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public final void a(boolean z) {
        if (this.q != null) {
            if (z) {
                this.q.a(130, 265, 130, AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST);
            } else {
                this.q.a(0, 0, 0, 0);
            }
        }
    }

    public final void b(boolean z) {
        if (this.o != null) {
            this.o.setVisible(z);
        }
    }

    static /* synthetic */ void a(eha eha, int i2, PointOverlayItem pointOverlayItem) {
        if (eha.c != null && eha.d != null && eha.d.size() > i2 && i2 >= 0) {
            BicycleBasicItem bicycleBasicItem = eha.d.get(i2);
            if (!(eha.e == null || bicycleBasicItem == null)) {
                if (eha.m == null) {
                    eao.a((String) "Amap#", (String) "Bike# onClick return !! footTipOvelay is null");
                } else if (!TextUtils.equals(eha.h.getClickId(), bicycleBasicItem.getId())) {
                    eha.l();
                    eha.k();
                    eha.c();
                    eha.m();
                    eha.h.setClickId(bicycleBasicItem.getId());
                    eha.h.setPointItemVisble(pointOverlayItem, false, false);
                    eha.k.drawBikeIconFouces(bicycleBasicItem, true);
                    eha.j = pointOverlayItem;
                }
            }
        }
    }
}
