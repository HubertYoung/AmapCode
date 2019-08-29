package defpackage;

import android.graphics.Point;
import android.os.Looper;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: bzi reason: default package */
/* compiled from: SearchResultOverlayManagerImpl */
public final class bzi extends bzh {
    public bzi(bty bty) {
        super(bty);
    }

    public final void a(boolean z) {
        t();
        if (z) {
            a();
            if (this.b != null) {
                this.b.clearFocus();
            }
        }
    }

    public final void b(POI poi) {
        b(false);
        c();
        if (poi != null && this.e != null) {
            this.e.addGeoItem(poi);
        }
    }

    public final void b(boolean z) {
        if (this.f != null) {
            this.f.setVisible(z);
        }
    }

    public final int i() {
        if (this.f == null) {
            return -1;
        }
        return this.f.getLastFocusedIndex();
    }

    public final bbr j() {
        if (this.f == null) {
            return null;
        }
        return (bbr) this.f.getFocus();
    }

    public final void a(int i) {
        if (this.f != null) {
            this.f.setFocus(i, false);
        }
    }

    public final void b(int i) {
        if (this.f != null) {
            this.f.setFocusIndex(i);
        }
    }

    public final List<bbr> k() {
        if (this.f == null) {
            return null;
        }
        return this.f.getItems();
    }

    public final bbr l() {
        if (this.f == null) {
            return null;
        }
        return (bbr) this.f.getItem(0);
    }

    public final void a(bxf bxf) {
        this.c.setVisible((bxf == null || bxf.c == -1) ? false : true);
    }

    public final void m() {
        if (this.l != null) {
            byz byz = this.l;
            byz.f = (bzw) byz.b.getFocus();
        }
    }

    public final void a(cce cce, POI poi, InfoliteResult infoliteResult, boolean z) {
        int w = this.n.w();
        if (infoliteResult != null) {
            a(infoliteResult);
            if (this.k != null) {
                this.k.b();
                if (!(poi == null || !"citycard".equals(poi.getIndustry()) || this.j == null)) {
                    if (w > 13) {
                        if (!this.j.d()) {
                            this.j.b();
                        }
                        if (this.j.c()) {
                            this.j.b(false);
                        }
                    } else {
                        if (!this.j.d()) {
                            this.j.b();
                        }
                        if (w == 13) {
                            if (this.j.c()) {
                                this.j.b(false);
                            }
                        } else if (!this.j.c()) {
                            this.j.b(true);
                        }
                    }
                }
            }
            if (this.l != null && !Float.valueOf(this.a).equals(Float.valueOf(this.n.v())) && infoliteResult.searchInfo.u == 1) {
                byz.e = true;
                this.l.a();
                this.a = this.n.v();
            }
            if (!elc.b) {
                if (w >= 14 || z) {
                    if (this.b != null) {
                        this.b.setVisible(true);
                    }
                } else if (this.b != null) {
                    this.b.setVisible(false);
                }
                if (w >= 12 || !bcy.a(infoliteResult) || z) {
                    if (this.c != null) {
                        this.c.setVisible(true);
                    }
                    if (!(cce == null || !cce.a || poi == null)) {
                        SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
                        if (!(searchPoi.getPoiChildrenInfo() == null || searchPoi.getPoiChildrenInfo().poiList == null)) {
                            cce.a = false;
                            a(cce, poi, infoliteResult);
                        }
                    }
                } else {
                    if (this.c != null) {
                        this.c.setVisible(false);
                    }
                    if (cce != null) {
                        cce.a = true;
                        cce.b(2);
                    }
                }
            }
        }
    }

    public final void c(int i) {
        a();
        if (this.c != null) {
            this.c.setFocus(i, false);
        }
    }

    public final void a(cce cce, POI poi, InfoliteResult infoliteResult) {
        if (poi != null) {
            SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
            if (!(searchPoi == null || searchPoi.getPoiChildrenInfo() == null || searchPoi.getPoiChildrenInfo().poiList == null || cce == null)) {
                cce.a = false;
                cce.a((List) searchPoi.getPoiChildrenInfo().poiList, 2, infoliteResult.searchInfo.a.o);
            }
        }
    }

    public final boolean n() {
        if (this.l != null) {
            byz byz = this.l;
            if ((byz.b == null || byz.b.getFocus() == null) ? false : true) {
                return true;
            }
        }
        return false;
    }

    public final synchronized void a(InfoliteResult infoliteResult) {
        if (this.l == null) {
            this.l = byz.a(this.n, this.g, this.h);
        }
        this.l.a(infoliteResult);
    }

    public final void a(List<POI> list) {
        if (this.b != null) {
            this.c.clear();
            this.b.addStation(list);
        }
    }

    public final void a(cce cce, int i, POI poi, InfoliteResult infoliteResult) {
        if (infoliteResult != null && this.c != null) {
            if (cce != null) {
                if (cce.a) {
                    cce.a = false;
                    a(cce, poi, infoliteResult);
                }
                ahm.a(new Runnable(poi, i, infoliteResult.searchInfo.a.o) {
                    final /* synthetic */ POI a;
                    final /* synthetic */ int b;
                    final /* synthetic */ int c;

                    {
                        this.a = r2;
                        this.b = r3;
                        this.c = r4;
                    }

                    public final void run() {
                        POI poi = this.a;
                        if (poi != null) {
                            SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
                            if (searchPoi != null && searchPoi.getPoiChildrenInfo() != null && searchPoi.getPoiChildrenInfo().poiList != null) {
                                ArrayList arrayList = new ArrayList();
                                arrayList.addAll(searchPoi.getPoiChildrenInfo().poiList);
                                if (this.b >= 0 && this.b < arrayList.size()) {
                                    cce.this.a((POI) arrayList.get(this.b), 2, this.c);
                                }
                            }
                        }
                    }
                });
            }
            this.c.setVisible(true);
            this.c.setFocus(i, false);
        }
    }

    public final void a(int i, int i2) {
        if ((this.f != null && this.f.getFocus() == null) || !(this.f == null || this.f.getFocus() == null || i == this.b.mFocusedPoiIndex)) {
            this.f.setFocus(i2, false);
        }
        this.b.setVisible(true);
        this.b.setFocus(i, false);
    }

    public final void o() {
        if (this.l != null) {
            this.l.c();
            this.l.b();
        }
    }

    public final void p() {
        if (this.l != null) {
            this.l.b();
        }
    }

    public final void q() {
        this.c.clear();
        this.i.clear();
        this.b.clear();
        this.e.clear();
    }

    public final void s() {
        if (this.b != null) {
            this.b.setPointItemVisble(0, true, true);
        }
    }

    public final void a(boolean z, int i) {
        if ((z || !(this.b.mFocusedPoiIndex == -1 || this.b.mFocusedPoiIndex == i)) && this.b != null) {
            this.b.clear();
            this.b.mFocusedPoiIndex = -1;
        }
        if ((z || !(this.c.mFocusedPoiIndex == -1 || this.c.mFocusedPoiIndex == i)) && this.c != null) {
            this.c.clear();
            this.c.mFocusedPoiIndex = -1;
        }
    }

    public final void r() {
        if (this.f != null) {
            this.f.setOnItemClickListener(null);
            this.f.setFocusChangeCallback(null);
        }
        if (this.b != null) {
            this.b.setOnItemClickListener(null);
        }
        if (this.c != null) {
            this.c.setOnItemClickListener(null);
        }
        if (this.d != null) {
            this.d.setOnItemClickListener(null);
        }
        if (this.e != null) {
            this.e.setOnItemClickListener(null);
        }
        if (this.g != null) {
            this.g.setOnItemClickListener(null);
        }
        if (this.h != null) {
            this.h.setOnItemClickListener(null);
        }
        if (this.l != null) {
            byz byz = this.l;
            byz.c();
            byz.b();
        }
        if (this.k != null) {
            bzc bzc = this.k;
            if (bzc.c != null) {
                bzc.c.setOnItemClickListener(null);
            }
            bzc.e = null;
        }
        this.m = null;
        super.a((BaseMapOverlay) this.b);
        super.a((BaseMapOverlay) this.c);
        super.a((BaseMapOverlay) this.d);
        super.a((BaseMapOverlay) this.e);
        super.a((BaseMapOverlay) this.f);
        super.a((BaseMapOverlay) this.g);
        super.a((BaseMapOverlay) this.h);
        super.a((BaseMapOverlay) this.i);
        super.a((BaseMapOverlay) this.j.a);
        super.a((BaseMapOverlay) this.j.b);
        super.a((BaseMapOverlay) this.k.a);
        super.a((BaseMapOverlay) this.k.b);
        super.a((BaseMapOverlay) this.k.c);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0023 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(com.autonavi.common.model.POI r4) {
        /*
            r3 = this;
            bzc r0 = r3.k
            r1 = 0
            if (r0 == 0) goto L_0x0024
            r0 = 1
            if (r4 != 0) goto L_0x000a
        L_0x0008:
            r4 = 0
            goto L_0x0021
        L_0x000a:
            java.util.HashMap r4 = r4.getPoiExtra()
            if (r4 != 0) goto L_0x0011
            goto L_0x0008
        L_0x0011:
            java.lang.String r2 = "parkinfo_geometry"
            java.lang.Object r4 = r4.get(r2)
            java.lang.String r4 = (java.lang.String) r4
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 == 0) goto L_0x0020
            goto L_0x0008
        L_0x0020:
            r4 = 1
        L_0x0021:
            if (r4 == 0) goto L_0x0024
            return r0
        L_0x0024:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bzi.a(com.autonavi.common.model.POI):boolean");
    }

    public final void g() {
        if (this.k != null) {
            bzc bzc = this.k;
            if (bzc.f != null && bzc.f.w() < 18) {
                bzc.f.c(18.0f);
            }
        }
    }

    public final PointOverlayItem h() {
        PointOverlayItem pointOverlayItem = (PointOverlayItem) this.c.getFocus();
        if (pointOverlayItem == null) {
            pointOverlayItem = (PointOverlayItem) this.b.getFocus();
        }
        if (pointOverlayItem == null) {
            pointOverlayItem = (PointOverlayItem) this.f.getFocus();
        }
        if (pointOverlayItem == null) {
            pointOverlayItem = (PointOverlayItem) this.h.getItem(0);
        }
        return pointOverlayItem == null ? (PointOverlayItem) this.e.getFocus() : pointOverlayItem;
    }

    public final void a(bzu bzu, boolean z, boolean z2) {
        PointOverlay pointOverlay;
        switch (bzu.b) {
            case 1:
                pointOverlay = this.f;
                break;
            case 2:
                pointOverlay = this.c;
                break;
            case 3:
                pointOverlay = this.b;
                break;
            default:
                pointOverlay = null;
                break;
        }
        if (pointOverlay != null) {
            pointOverlay.setPointItemVisble((PointOverlayItem) bzu.a, z, z2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(com.autonavi.bundle.entity.infolite.internal.InfoliteResult r6, defpackage.cbn r7, android.graphics.Rect r8) {
        /*
            r5 = this;
            byz r0 = r5.l
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x001f
            byz r0 = r5.l
            com.autonavi.bl.search.InfoliteParam r3 = r6.mWrapper
            java.lang.String r3 = r3.keywords
            java.lang.String r4 = r0.d
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x001d
            java.lang.String r0 = r0.d
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x001d
            goto L_0x001f
        L_0x001d:
            r0 = 0
            goto L_0x0020
        L_0x001f:
            r0 = 1
        L_0x0020:
            byz r3 = r5.l
            if (r3 == 0) goto L_0x004e
            if (r7 == 0) goto L_0x004e
            cbn$a r7 = r7.a
            float r7 = r7.a
            java.lang.Float r7 = java.lang.Float.valueOf(r7)
            bty r3 = r5.n
            float r3 = r3.v()
            java.lang.Float r3 = java.lang.Float.valueOf(r3)
            boolean r7 = r7.equals(r3)
            if (r7 != 0) goto L_0x004e
            byz r6 = r5.l
            r6.e()
            byz r6 = r5.l
            r6.a(r2)
            byz r6 = r5.l
            r6.a()
            goto L_0x006a
        L_0x004e:
            com.autonavi.map.search.overlay.SearchPoiMarkOverlay r7 = r5.g
            java.util.List r7 = r7.getItems()
            if (r7 == 0) goto L_0x0067
            if (r0 == 0) goto L_0x0067
            byz r7 = r5.l
            if (r7 == 0) goto L_0x0067
            byz r6 = r5.l
            r6.e()
            byz r6 = r5.l
            r6.a(r1)
            goto L_0x006a
        L_0x0067:
            r5.a(r6)
        L_0x006a:
            if (r8 == 0) goto L_0x009c
            com.autonavi.common.model.GeoPoint r6 = new com.autonavi.common.model.GeoPoint
            r6.<init>()
            int r7 = r8.left
            int r0 = r8.right
            int r1 = r8.left
            int r0 = r0 - r1
            int r0 = r0 / 2
            int r7 = r7 + r0
            r6.x = r7
            int r7 = r8.top
            int r0 = r8.bottom
            int r8 = r8.top
            int r0 = r0 - r8
            int r0 = r0 / 2
            int r7 = r7 + r0
            r6.y = r7
            com.autonavi.minimap.base.overlay.PointOverlayItem r7 = new com.autonavi.minimap.base.overlay.PointOverlayItem
            r7.<init>(r6)
            com.autonavi.map.search.overlay.SearchCenterOverlay r6 = r5.d
            if (r6 == 0) goto L_0x009c
            com.autonavi.map.search.overlay.SearchCenterOverlay r6 = r5.d
            r6.clear()
            com.autonavi.map.search.overlay.SearchCenterOverlay r6 = r5.d
            r6.addCenterPoint(r7)
        L_0x009c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bzi.a(com.autonavi.bundle.entity.infolite.internal.InfoliteResult, cbn, android.graphics.Rect):void");
    }

    public final void c(POI poi) {
        if (!(poi == null || this.i == null)) {
            this.i.clear();
            this.i.showRoadOverlay(poi);
        }
        if (poi != null && this.j != null) {
            this.j.a();
            bbp bbp = this.j;
            if (poi != null) {
                bbp.a(poi, false);
            }
        }
    }

    /* access modifiers changed from: private */
    public void b(POI poi, boolean z) {
        if (this.j != null) {
            this.j.a(poi, z);
        }
        this.j.a(!elc.a);
    }

    public final void a(final POI poi, final boolean z) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            b(poi, z);
        } else {
            aho.a(new Runnable() {
                public final void run() {
                    bzi.this.b(poi, z);
                }
            });
        }
        if (poi == null || poi.getType() == null || !poi.getType().startsWith("15090")) {
            this.k.a();
        } else {
            bzc bzc = this.k;
            HashMap<String, Serializable> poiExtra = poi.getPoiExtra();
            if (poiExtra != null) {
                String str = (String) poiExtra.get("parkinfo_geometry");
                if (str == null) {
                    bzc.a();
                } else {
                    bzc.d = poi;
                    bzc.a();
                    if ("5".equals(str)) {
                        String str2 = (String) poiExtra.get("parkinfo_points");
                        bzc.b.clear();
                        if (str2 != null) {
                            try {
                                JSONArray jSONArray = new JSONArray(str2);
                                int length = jSONArray.length();
                                if (length > 0) {
                                    int[] iArr = new int[length];
                                    int[] iArr2 = new int[length];
                                    for (int i = 0; i < length; i++) {
                                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                                        Point a = cfg.a(jSONObject.optDouble(DictionaryKeys.CTRLXY_Y), jSONObject.optDouble(DictionaryKeys.CTRLXY_X));
                                        iArr[i] = a.x;
                                        iArr2[i] = a.y;
                                    }
                                    bzc.b.addPolygon(iArr, iArr2, 872024576);
                                }
                            } catch (Exception unused) {
                            }
                        }
                        bzc.b((String) poiExtra.get("parkinfo_inout_info"));
                    } else if ("3".equals(str)) {
                        bzc.a((String) poiExtra.get("parkinfo_points"));
                    } else if ("1".equals(str)) {
                        bzc.b((String) poiExtra.get("parkinfo_inout_info"));
                    }
                }
            }
        }
        if (this.i != null) {
            this.i.showRoadOverlay(poi);
        }
    }

    public final void a(PointOverlayItem pointOverlayItem, String str) {
        this.f.clearFocus();
        this.c.clear();
        this.b.clear();
        this.i.clear();
        this.j.a();
        this.k.a();
        if (this.l != null) {
            byz byz = this.l;
            byz.a.setFocus(pointOverlayItem, false);
            byz.a(pointOverlayItem, str, false);
        }
    }

    public final void a(cce cce, int i, bxh bxh) {
        if (this.c != null) {
            this.c.clear();
        }
        if (this.i != null) {
            this.i.clear();
        }
        if (this.b != null) {
            this.b.clear();
        }
        List<POI> list = bxh.c;
        InfoliteResult infoliteResult = bxh.b;
        if (list != null && i != -1 && list.size() > i) {
            SearchPoi searchPoi = (SearchPoi) list.get(i).as(SearchPoi.class);
            if (searchPoi.getPoiChildrenInfo() == null || searchPoi.getPoiChildrenInfo().childType != 1) {
                if (!(searchPoi.getPoiChildrenInfo() == null || searchPoi.getPoiChildrenInfo().childType != 2 || this.c == null)) {
                    this.c.clear();
                    this.c.addChildPoi(searchPoi, i, -1);
                    if (this.n.w() >= 12 || !bcy.a(infoliteResult)) {
                        this.c.setVisible(true);
                        if (elc.b) {
                            this.c.setVisible(false);
                            return;
                        }
                        a(cce, (POI) searchPoi, infoliteResult);
                    } else {
                        this.c.setVisible(false);
                    }
                }
            } else if (this.b != null) {
                this.b.clear();
                this.b.addStation(searchPoi, i, -1);
                if (this.n.w() >= 14 || !bcy.a(infoliteResult)) {
                    this.b.setVisible(true);
                } else {
                    this.b.setVisible(false);
                }
            }
        }
    }

    public final void b(List<POI> list) {
        if (list != null && this.c != null) {
            this.c.clear();
            this.c.addChildrenPOI(list);
        }
    }
}
