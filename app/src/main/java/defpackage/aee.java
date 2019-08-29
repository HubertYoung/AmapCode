package defpackage;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.searchservice.service.offline.OfflinePoiEngineFactoryImpl;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.search.SearchEngine;
import com.autonavi.ae.search.interfaces.OnSearchResultListener;
import com.autonavi.ae.search.model.GADAREAEXTRAINFO;
import com.autonavi.ae.search.model.GPoiBase;
import com.autonavi.ae.search.model.GPoiBean;
import com.autonavi.bundle.entity.common.OfflineSearchMode;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import org.json.JSONObject;

/* renamed from: aee reason: default package */
/* compiled from: OfflineSearchUtil */
public class aee {
    public static SearchEngine a;
    private static String c;
    private static String d;
    private static String e;
    private static aee j;
    private static SharedPreferences k;
    long b = 0;
    private boolean f = false;
    private String g;
    private int h;
    private DPoint i;

    public static synchronized aee a(OfflineSearchMode offlineSearchMode) {
        aee a2;
        synchronized (aee.class) {
            a2 = a(offlineSearchMode.strAdCode, offlineSearchMode.strLongitude, offlineSearchMode.strLatitude);
        }
        return a2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0042  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized defpackage.aee a(java.lang.String r3, java.lang.String r4, java.lang.String r5) {
        /*
            java.lang.Class<aee> r0 = defpackage.aee.class
            monitor-enter(r0)
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ all -> 0x0056 }
            java.lang.String r2 = "SharedPreferences"
            r1.<init>(r2)     // Catch:{ all -> 0x0056 }
            android.content.SharedPreferences r1 = r1.sharedPrefs()     // Catch:{ all -> 0x0056 }
            k = r1     // Catch:{ all -> 0x0056 }
            boolean r1 = defpackage.bcz.a(r3)     // Catch:{ all -> 0x0056 }
            if (r1 == 0) goto L_0x0019
            r3 = 0
            monitor-exit(r0)
            return r3
        L_0x0019:
            aee r1 = j     // Catch:{ all -> 0x0056 }
            if (r1 == 0) goto L_0x0031
            boolean r1 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x0056 }
            if (r1 != 0) goto L_0x002c
            java.lang.String r1 = c     // Catch:{ all -> 0x0056 }
            boolean r1 = r3.equals(r1)     // Catch:{ all -> 0x0056 }
            if (r1 != 0) goto L_0x002c
            goto L_0x0031
        L_0x002c:
            d = r4     // Catch:{ all -> 0x0056 }
            e = r5     // Catch:{ all -> 0x0056 }
            goto L_0x003e
        L_0x0031:
            c = r3     // Catch:{ all -> 0x0056 }
            d = r4     // Catch:{ all -> 0x0056 }
            e = r5     // Catch:{ all -> 0x0056 }
            aee r3 = new aee     // Catch:{ all -> 0x0056 }
            r3.<init>()     // Catch:{ all -> 0x0056 }
            j = r3     // Catch:{ all -> 0x0056 }
        L_0x003e:
            com.autonavi.ae.search.SearchEngine r3 = a     // Catch:{ all -> 0x0056 }
            if (r3 != 0) goto L_0x0052
            com.amap.bundle.searchservice.service.offline.OfflinePoiEngineFactoryImpl r3 = com.amap.bundle.searchservice.service.offline.OfflinePoiEngineFactoryImpl.c()     // Catch:{ all -> 0x0056 }
            defpackage.afa.a()     // Catch:{ all -> 0x0056 }
            r3.a()     // Catch:{ all -> 0x0056 }
            com.autonavi.ae.search.SearchEngine r3 = r3.b()     // Catch:{ all -> 0x0056 }
            a = r3     // Catch:{ all -> 0x0056 }
        L_0x0052:
            aee r3 = j     // Catch:{ all -> 0x0056 }
            monitor-exit(r0)
            return r3
        L_0x0056:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aee.a(java.lang.String, java.lang.String, java.lang.String):aee");
    }

    public static aee a() {
        if (j == null) {
            synchronized (aee.class) {
                try {
                    if (j == null) {
                        j = new aee();
                    }
                }
            }
        }
        return j;
    }

    public static boolean a(int i2) {
        if (a != null) {
            return SearchEngine.isExistByAdCode(i2);
        }
        return false;
    }

    private aee() {
        if (a == null) {
            OfflinePoiEngineFactoryImpl c2 = OfflinePoiEngineFactoryImpl.c();
            if (c2 != null) {
                afa.a();
                c2.a();
                a = c2.b();
            }
        }
    }

    public static boolean b() {
        if (a == null) {
            a = OfflinePoiEngineFactoryImpl.c().b();
        }
        return a != null;
    }

    public final void a(int i2, OnSearchResultListener onSearchResultListener, int i3, boolean z) {
        c();
        if (TextUtils.isEmpty(this.g) || this.h <= 0 || this.i == null) {
            if (onSearchResultListener != null) {
                onSearchResultListener.onGetSearchResult(-1, null);
            }
            return;
        }
        int i4 = i3 == -1 ? 200 : i3;
        if (z) {
            a.preSearch(i2, this.g, this.h, (float) this.i.x, (float) this.i.y, i4, onSearchResultListener);
        } else {
            a.startSearch(i2, this.g, this.h, (float) this.i.x, (float) this.i.y, i4, onSearchResultListener);
        }
    }

    public static void c() {
        int i2;
        if (k != null) {
            int i3 = k.getInt("offline_search_count", 0);
            try {
                JSONObject jSONObject = new JSONObject();
                i2 = i3 + 1;
                try {
                    jSONObject.put(NewHtcHomeBadger.COUNT, i3);
                    LogManager.actionLogV2("P00158", "B003", jSONObject);
                } catch (Exception unused) {
                }
            } catch (Exception unused2) {
                i2 = i3;
            }
            k.edit().putInt("offline_search_count", i2).apply();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0065 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0066  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(java.lang.String r10) {
        /*
            r9 = this;
            r0 = 0
            r9.f = r0
            boolean r1 = defpackage.bcz.a(r10)
            if (r1 == 0) goto L_0x000a
            return r0
        L_0x000a:
            com.autonavi.ae.search.SearchEngine r1 = a
            if (r1 != 0) goto L_0x001a
            com.amap.bundle.searchservice.service.offline.OfflinePoiEngineFactoryImpl r1 = com.amap.bundle.searchservice.service.offline.OfflinePoiEngineFactoryImpl.c()
            if (r1 == 0) goto L_0x001a
            com.autonavi.ae.search.SearchEngine r1 = r1.b()
            a = r1
        L_0x001a:
            com.autonavi.ae.search.SearchEngine r1 = a
            if (r1 == 0) goto L_0x009f
            r1 = 0
            r2 = -1
            java.lang.String r3 = c     // Catch:{ NumberFormatException -> 0x0055 }
            boolean r3 = defpackage.bcz.a(r3)     // Catch:{ NumberFormatException -> 0x0055 }
            if (r3 != 0) goto L_0x002f
            java.lang.String r3 = c     // Catch:{ NumberFormatException -> 0x0055 }
            int r3 = java.lang.Integer.parseInt(r3)     // Catch:{ NumberFormatException -> 0x0055 }
            goto L_0x0030
        L_0x002f:
            r3 = -1
        L_0x0030:
            java.lang.String r4 = d     // Catch:{ NumberFormatException -> 0x0053 }
            boolean r4 = defpackage.bcz.a(r4)     // Catch:{ NumberFormatException -> 0x0053 }
            if (r4 != 0) goto L_0x005b
            java.lang.String r4 = e     // Catch:{ NumberFormatException -> 0x0053 }
            boolean r4 = defpackage.bcz.a(r4)     // Catch:{ NumberFormatException -> 0x0053 }
            if (r4 != 0) goto L_0x005b
            com.autonavi.common.model.GeoPoint r4 = new com.autonavi.common.model.GeoPoint     // Catch:{ NumberFormatException -> 0x0053 }
            java.lang.String r5 = d     // Catch:{ NumberFormatException -> 0x0053 }
            double r5 = java.lang.Double.parseDouble(r5)     // Catch:{ NumberFormatException -> 0x0053 }
            java.lang.String r7 = e     // Catch:{ NumberFormatException -> 0x0053 }
            double r7 = java.lang.Double.parseDouble(r7)     // Catch:{ NumberFormatException -> 0x0053 }
            r4.<init>(r5, r7)     // Catch:{ NumberFormatException -> 0x0053 }
            r1 = r4
            goto L_0x005b
        L_0x0053:
            r4 = move-exception
            goto L_0x0058
        L_0x0055:
            r3 = move-exception
            r4 = r3
            r3 = -1
        L_0x0058:
            defpackage.kf.a(r4)
        L_0x005b:
            if (r3 != r2) goto L_0x0063
            if (r1 == 0) goto L_0x0063
            int r3 = r1.getAdCode()
        L_0x0063:
            if (r3 != r2) goto L_0x0066
            return r0
        L_0x0066:
            if (r1 == 0) goto L_0x0073
            int r2 = r1.x
            long r4 = (long) r2
            int r1 = r1.y
            long r1 = (long) r1
            com.autonavi.minimap.map.DPoint r1 = defpackage.cfg.a(r4, r1)
            goto L_0x007a
        L_0x0073:
            com.autonavi.minimap.map.DPoint r1 = new com.autonavi.minimap.map.DPoint
            r4 = 0
            r1.<init>(r4, r4)
        L_0x007a:
            long r4 = java.lang.System.currentTimeMillis()
            long r6 = r9.b
            long r4 = r4 - r6
            r6 = 1000(0x3e8, double:4.94E-321)
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x0092
            com.autonavi.ae.search.SearchEngine r2 = a
            r2.cancelQuery()
            long r4 = java.lang.System.currentTimeMillis()
            r9.b = r4
        L_0x0092:
            boolean r2 = r9.f
            if (r2 == 0) goto L_0x0097
            return r0
        L_0x0097:
            r9.g = r10
            r9.h = r3
            r9.i = r1
            r10 = 1
            return r10
        L_0x009f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aee.a(java.lang.String):boolean");
    }

    public static void a(GeoPoint geoPoint, String str, OnSearchResultListener onSearchResultListener) {
        if (a != null) {
            e = null;
            d = null;
            a.getPoiDetail(str, (float) geoPoint.getLongitude(), (float) geoPoint.getLatitude(), onSearchResultListener);
            return;
        }
        if (onSearchResultListener != null) {
            onSearchResultListener.onGetSearchResult(3, null);
        }
    }

    public final void d() {
        this.f = true;
        if (a != null) {
            a.cancelQuery();
        }
    }

    public static POI a(GPoiBase gPoiBase) {
        POI createPOI = POIFactory.createPOI();
        if (gPoiBase instanceof GPoiBean) {
            GPoiBean gPoiBean = (GPoiBean) gPoiBase;
            createPOI.setId(gPoiBean.getPoiID());
            StringBuilder sb = new StringBuilder();
            sb.append(gPoiBean.getCatCode());
            createPOI.setType(sb.toString());
            createPOI.setAdCode(String.valueOf(gPoiBean.getAdcode()));
            createPOI.setAddr(gPoiBean.getAddr());
            createPOI.setName(gPoiBean.getName());
            createPOI.setPoint(new GeoPoint((double) gPoiBean.getLocalPoint().x, (double) gPoiBean.getLocalPoint().y));
            createPOI.setPhone(gPoiBean.getTel());
            int i2 = 0;
            if (!TextUtils.isEmpty(e) || !TextUtils.isEmpty(d)) {
                try {
                    i2 = (int) bcw.a((double) gPoiBean.getLocalPoint().x, (double) gPoiBean.getLocalPoint().y, Double.parseDouble(d), Double.parseDouble(e));
                } catch (Exception unused) {
                }
            } else {
                GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                if (latestPosition != null) {
                    double latitude = latestPosition.getLatitude();
                    i2 = (int) bcw.a((double) gPoiBean.getLocalPoint().x, (double) gPoiBean.getLocalPoint().y, latestPosition.getLongitude(), latitude);
                }
            }
            if (i2 != 0) {
                createPOI.setDistance(i2);
            }
            int catCode = gPoiBean.getCatCode();
            if (catCode > 0) {
                createPOI.setType(String.valueOf(catCode));
            }
            if (gPoiBean.getNaviPoint() != null && gPoiBean.getNaviPoint().x > 1.0f && gPoiBean.getNaviPoint().y > 1.0f) {
                ArrayList arrayList = new ArrayList(1);
                arrayList.add(new GeoPoint((double) gPoiBean.getNaviPoint().x, (double) gPoiBean.getNaviPoint().y));
                createPOI.setEntranceList(arrayList);
            }
            createPOI.getPoiExtra().put("SrcType", "nativepoi");
        } else if (gPoiBase != null) {
            createPOI.setName(gPoiBase.getName());
        }
        return createPOI;
    }

    public static GADAREAEXTRAINFO b(int i2) {
        return a.GetAdareaInfo(i2);
    }

    public static void a(float f2, float f3, OnSearchResultListener onSearchResultListener) {
        a.searchNearestPoi(f2, f3, onSearchResultListener);
    }

    public static void e() {
        j = null;
        a = null;
    }
}
