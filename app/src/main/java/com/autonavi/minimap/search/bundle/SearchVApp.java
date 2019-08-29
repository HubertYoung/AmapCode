package com.autonavi.minimap.search.bundle;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.statistics.LogManager;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.offline.model.IFilePathHelper;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchVApp extends esh {
    public static final String a = "SearchVApp";
    awb b = ((awb) a.a.a(awb.class));
    b c = new a() {
        public final bty a() {
            return DoNotUseTool.getMapView();
        }

        private static boolean d() {
            bid pageContext = AMapPageUtil.getPageContext();
            apr apr = (apr) a.a.a(apr.class);
            bci bci = (bci) a.a.a(bci.class);
            return (apr != null && apr.a(pageContext)) || (bci != null && bci.a(pageContext)) || ((bdl) a.a.a(bdl.class)).a(pageContext);
        }

        public final boolean b() {
            return d();
        }

        /* JADX WARNING: Removed duplicated region for block: B:44:0x00d0  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean a(java.util.List<defpackage.als> r14) {
            /*
                r13 = this;
                r0 = 0
                if (r14 == 0) goto L_0x01b4
                int r1 = r14.size()
                if (r1 <= 0) goto L_0x01b4
                java.lang.Object r14 = r14.get(r0)
                als r14 = (defpackage.als) r14
                esb r1 = defpackage.esb.a.a
                java.lang.Class<je> r2 = defpackage.je.class
                esc r1 = r1.a(r2)
                je r1 = (defpackage.je) r1
                if (r1 == 0) goto L_0x0023
                boolean r1 = r1.a(r14)
                if (r1 != 0) goto L_0x01b4
            L_0x0023:
                if (r14 == 0) goto L_0x0033
                int r1 = r14.i
                boolean r1 = com.autonavi.minimap.search.bundle.SearchVApp.a(r1)
                if (r1 != 0) goto L_0x01b4
                int r1 = r14.i
                r2 = 16777216(0x1000000, float:2.3509887E-38)
                if (r1 == r2) goto L_0x01b4
            L_0x0033:
                java.lang.String r1 = r14.a
                boolean r1 = android.text.TextUtils.isEmpty(r1)
                if (r1 != 0) goto L_0x01b4
                bid r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
                java.lang.String r2 = r14.a
                com.autonavi.common.model.GeoPoint r3 = new com.autonavi.common.model.GeoPoint
                int r4 = r14.e
                int r5 = r14.f
                r3.<init>(r4, r5)
                com.autonavi.common.model.POI r2 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r2, r3)
                java.lang.String r3 = r14.b
                boolean r3 = android.text.TextUtils.isEmpty(r3)
                if (r3 != 0) goto L_0x0065
                java.lang.String r3 = "0"
                java.lang.String r4 = r14.b
                boolean r3 = r3.equals(r4)
                if (r3 != 0) goto L_0x0065
                java.lang.String r3 = r14.b
                r2.setId(r3)
            L_0x0065:
                int r3 = r14.h
                r4 = 2
                r5 = 1
                if (r3 != r4) goto L_0x006d
                r3 = 1
                goto L_0x006e
            L_0x006d:
                r3 = 0
            L_0x006e:
                int r6 = r14.h
                if (r6 != r5) goto L_0x0074
                r6 = 1
                goto L_0x0075
            L_0x0074:
                r6 = 0
            L_0x0075:
                if (r6 == 0) goto L_0x0088
                java.util.HashMap r6 = r2.getPoiExtra()
                if (r6 == 0) goto L_0x0088
                java.util.HashMap r6 = r2.getPoiExtra()
                java.lang.String r7 = "IS_SCENIC"
                java.lang.Boolean r8 = java.lang.Boolean.TRUE
                r6.put(r7, r8)
            L_0x0088:
                java.lang.String r6 = com.autonavi.minimap.search.bundle.SearchVApp.a
                java.lang.Class<apt> r6 = defpackage.apt.class
                java.lang.Object r6 = defpackage.ank.a(r6)
                apt r6 = (defpackage.apt) r6
                int r7 = r14.i
                r8 = 20190000(0x1341330, float:3.3074545E-38)
                if (r7 == r8) goto L_0x00c2
                int r7 = r14.i
                r8 = 20190001(0x1341331, float:3.3074548E-38)
                if (r7 != r8) goto L_0x00a1
                goto L_0x00c2
            L_0x00a1:
                java.lang.String r7 = r14.b
                int r8 = r14.i
                r6.a(r7, r8)
                esb r6 = defpackage.esb.a.a
                java.lang.Class<bci> r7 = defpackage.bci.class
                esc r6 = r6.a(r7)
                bci r6 = (defpackage.bci) r6
                if (r6 == 0) goto L_0x00c0
                com.autonavi.minimap.search.bundle.SearchVApp r7 = com.autonavi.minimap.search.bundle.SearchVApp.this
                java.util.List r7 = r7.f
                r6.a(r1, r2, r7, r0)
                goto L_0x00c9
            L_0x00c0:
                r6 = 0
                goto L_0x00ca
            L_0x00c2:
                java.lang.String r7 = r14.b
                int r8 = r14.i
                r6.a(r7, r8)
            L_0x00c9:
                r6 = 1
            L_0x00ca:
                bty r7 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapView()
                if (r7 == 0) goto L_0x01b3
                boolean r8 = r7.s()
                if (r8 == 0) goto L_0x00d8
                r8 = 1
                goto L_0x00d9
            L_0x00d8:
                r8 = 2
            L_0x00d9:
                esb r9 = defpackage.esb.a.a
                java.lang.Class<apr> r10 = defpackage.apr.class
                esc r9 = r9.a(r10)
                apr r9 = (defpackage.apr) r9
                if (r9 == 0) goto L_0x00ef
                boolean r9 = r9.a(r1)
                if (r9 == 0) goto L_0x00ef
                r9 = 1
                goto L_0x00f0
            L_0x00ef:
                r9 = 0
            L_0x00f0:
                if (r9 == 0) goto L_0x00f3
                r4 = 1
            L_0x00f3:
                esb r9 = defpackage.esb.a.a
                java.lang.Class<bdl> r10 = defpackage.bdl.class
                esc r9 = r9.a(r10)
                bdl r9 = (defpackage.bdl) r9
                boolean r9 = r9.a(r1)
                if (r9 == 0) goto L_0x0108
                r1.finish()
            L_0x0108:
                java.lang.String r1 = r2.getAdCode()
                boolean r1 = android.text.TextUtils.isEmpty(r1)
                if (r1 != 0) goto L_0x0117
                java.lang.String r1 = r2.getAdCode()
                goto L_0x011b
            L_0x0117:
                java.lang.String r1 = r2.getCityCode()
            L_0x011b:
                boolean r9 = android.text.TextUtils.isEmpty(r1)
                if (r9 == 0) goto L_0x012f
                com.autonavi.common.model.GeoPoint r9 = r2.getPoint()
                if (r9 == 0) goto L_0x012f
                int r1 = r9.getAdCode()
                java.lang.String r1 = java.lang.String.valueOf(r1)
            L_0x012f:
                com.autonavi.common.model.GeoPoint r9 = r2.getPoint()
                org.json.JSONObject r10 = new org.json.JSONObject
                r10.<init>()
                if (r3 == 0) goto L_0x0162
                com.amap.bundle.mapstorage.MapSharePreference r3 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ JSONException -> 0x0160 }
                java.lang.String r11 = "indoor_config"
                r3.<init>(r11)     // Catch:{ JSONException -> 0x0160 }
                java.lang.String r11 = "indoor_building_poiid"
                java.lang.String r12 = ""
                java.lang.String r3 = r3.getStringValue(r11, r12)     // Catch:{ JSONException -> 0x0160 }
                java.lang.String r11 = "poiName"
                java.lang.String r2 = r2.getName()     // Catch:{ JSONException -> 0x0160 }
                r10.put(r11, r2)     // Catch:{ JSONException -> 0x0160 }
                boolean r2 = android.text.TextUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x0160 }
                if (r2 != 0) goto L_0x015e
                java.lang.String r2 = "parent"
                r10.put(r2, r3)     // Catch:{ JSONException -> 0x0160 }
                goto L_0x0162
            L_0x015e:
                r0 = 1
                goto L_0x0162
            L_0x0160:
                r14 = move-exception
                goto L_0x01a7
            L_0x0162:
                java.lang.String r2 = "from"
                int r3 = r7.w()     // Catch:{ JSONException -> 0x0160 }
                r10.put(r2, r3)     // Catch:{ JSONException -> 0x0160 }
                java.lang.String r2 = "lat"
                if (r9 == 0) goto L_0x0178
                double r11 = r9.getLatitude()     // Catch:{ JSONException -> 0x0160 }
                java.lang.Double r3 = java.lang.Double.valueOf(r11)     // Catch:{ JSONException -> 0x0160 }
                goto L_0x017a
            L_0x0178:
                java.lang.String r3 = ""
            L_0x017a:
                r10.put(r2, r3)     // Catch:{ JSONException -> 0x0160 }
                java.lang.String r2 = "lon"
                if (r9 == 0) goto L_0x018a
                double r11 = r9.getLongitude()     // Catch:{ JSONException -> 0x0160 }
                java.lang.Double r3 = java.lang.Double.valueOf(r11)     // Catch:{ JSONException -> 0x0160 }
                goto L_0x018c
            L_0x018a:
                java.lang.String r3 = ""
            L_0x018c:
                r10.put(r2, r3)     // Catch:{ JSONException -> 0x0160 }
                java.lang.String r2 = "status"
                r10.put(r2, r8)     // Catch:{ JSONException -> 0x0160 }
                java.lang.String r2 = "poiid"
                java.lang.String r14 = r14.b     // Catch:{ JSONException -> 0x0160 }
                r10.put(r2, r14)     // Catch:{ JSONException -> 0x0160 }
                java.lang.String r14 = "itemId"
                r10.put(r14, r4)     // Catch:{ JSONException -> 0x0160 }
                java.lang.String r14 = "adcode"
                r10.put(r14, r1)     // Catch:{ JSONException -> 0x0160 }
                goto L_0x01aa
            L_0x01a7:
                r14.printStackTrace()
            L_0x01aa:
                if (r0 != 0) goto L_0x01b3
                java.lang.String r14 = "P00001"
                java.lang.String r0 = "B159"
                com.amap.bundle.statistics.LogManager.actionLogV2(r14, r0, r10)
            L_0x01b3:
                r0 = r6
            L_0x01b4:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.search.bundle.SearchVApp.AnonymousClass2.a(java.util.List):boolean");
        }

        public final boolean c() {
            return d();
        }

        public final void a(GeoPoint geoPoint) {
            String str;
            String str2 = SearchVApp.a;
            ((apt) ank.a(apt.class)).a("", -1);
            bid pageContext = AMapPageUtil.getPageContext();
            GeocodePOI geocodePOI = (GeocodePOI) POIFactory.createPOI("", geoPoint).as(GeocodePOI.class);
            geocodePOI.setName(AMapAppGlobal.getApplication().getString(R.string.select_point_from_map));
            bci bci = (bci) a.a.a(bci.class);
            if (bci != null) {
                bci.a(pageContext, (POI) geocodePOI);
            }
            bty mapView = DoNotUseTool.getMapView();
            if (mapView != null) {
                int i = 2;
                int i2 = mapView.s() ? 1 : 2;
                apr apr = (apr) a.a.a(apr.class);
                if (apr != null && apr.a(pageContext)) {
                    i = 1;
                }
                if (((bdl) a.a.a(bdl.class)).a(pageContext)) {
                    pageContext.finish();
                }
                if (!TextUtils.isEmpty(geocodePOI.getAdCode())) {
                    str = geocodePOI.getAdCode();
                } else {
                    str = geocodePOI.getCityCode();
                }
                if (TextUtils.isEmpty(str)) {
                    GeoPoint point = geocodePOI.getPoint();
                    if (point != null) {
                        str = String.valueOf(point.getAdCode());
                    }
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("from", mapView.w());
                    jSONObject.put("lat", geoPoint.getLatitude());
                    jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, geoPoint.getLongitude());
                    jSONObject.put("status", i2);
                    jSONObject.put("itemId", i);
                    jSONObject.put(AutoJsonUtils.JSON_ADCODE, str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_LONGPRESS, jSONObject);
            }
        }
    };
    bdw d = new bdw(this.c) {
        public final void onSelectSubWayActive(byte[] bArr) {
            SearchVApp.this.f = awc.parseSubWayActiveIds(bArr);
        }
    };
    private boolean e = false;
    /* access modifiers changed from: private */
    public List<Long> f = null;

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        this.e = hasPermission();
        bgl.a((bgd) new bwu());
        bgl.a((bgd) new bwt());
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        if (this.b != null) {
            this.b.a((MainMapEventListener) this.d);
        }
        ahm.a(new Runnable() {
            public final void run() {
                AMapPageUtil.getAppContext();
                SearchVApp.a();
            }
        });
        adz adz = (adz) a.a.a(adz.class);
        ady b2 = adz != null ? adz.b() : null;
        if (b2 != null) {
            IFilePathHelper iFilePathHelper = (IFilePathHelper) ank.a(IFilePathHelper.class);
            if (iFilePathHelper != null) {
                iFilePathHelper.getPoiFileDir();
                b2.b();
            }
        }
        SearchHistoryHelper.getInstance(null).tryMergeOldDataAsync();
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        if (this.e) {
            adz adz = (adz) a.a.a(adz.class);
            if (adz != null) {
                ady b2 = adz.b();
                if (b2 != null) {
                    b2.c();
                }
            }
        }
    }

    static /* synthetic */ void a() {
        bgx bgx = (bgx) a.a.a(bgx.class);
        if (bgx != null) {
            bgx.update();
        }
    }

    static /* synthetic */ boolean a(int i) {
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            return awo.d(i);
        }
        return false;
    }
}
