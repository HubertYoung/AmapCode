package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import java.io.UnsupportedEncodingException;

/* renamed from: dwx reason: default package */
/* compiled from: BusNaviDetailInputInfoData */
public final class dwx {
    public boolean a;
    public IBusRouteResult b;
    public String c;
    public boolean d = false;
    public String e;
    private PageBundle f;
    private int g = -1;
    private String h;

    public final boolean a() {
        return this.g == 102;
    }

    @NonNull
    public static dwx a(@Nullable PageBundle pageBundle) {
        IBusRouteResult iBusRouteResult;
        dwx dwx = new dwx();
        dwx.f = pageBundle;
        if (pageBundle != null) {
            dwx.d = true;
            dwx.g = pageBundle.getInt("key_source", 100);
            if (dwx.g == 102) {
                iBusRouteResult = (IBusRouteResult) pageBundle.get("key_result");
            } else {
                iBusRouteResult = (IBusRouteResult) pageBundle.get(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ);
            }
            if (iBusRouteResult == null) {
                eao.e("BusNaviDetailInputInfoData", "illegal params: bundleData is null");
                dwx.d = false;
                return dwx;
            }
            dwx.b = iBusRouteResult;
            dwx.c = pageBundle.getString("original_busroute_data", "");
            if (TextUtils.isEmpty(dwx.c) && dwx.b != null) {
                try {
                    dwx.c = new String(dwx.b.getBaseData(), "UTF-8");
                    eao.e("BusNaviDetailInputInfoData------json str", "");
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                }
            }
            dwx.a = pageBundle.getBoolean("bundle_key_favorite", false);
            dwx.e = pageBundle.getString("item_key_from_favorites");
            dwx.h = pageBundle.getString("bundle_key_options", "0");
        } else {
            eao.e("BusNaviDetailInputInfoData", "illegal params: bundle is null");
            dwx.d = false;
            dwx.g = 100;
        }
        return dwx;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x00c6 A[Catch:{ JSONException -> 0x0166 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00e0 A[Catch:{ JSONException -> 0x0166 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x011d A[ADDED_TO_REGION, Catch:{ JSONException -> 0x0166 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0124 A[Catch:{ JSONException -> 0x0166 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String b() {
        /*
            r9 = this;
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            com.autonavi.bundle.routecommon.entity.IBusRouteResult r1 = r9.b     // Catch:{ JSONException -> 0x0166 }
            if (r1 == 0) goto L_0x0065
            com.autonavi.bundle.routecommon.entity.IBusRouteResult r1 = r9.b     // Catch:{ JSONException -> 0x0166 }
            com.autonavi.bundle.routecommon.entity.BusPaths r1 = r1.getBusPathsResult()     // Catch:{ JSONException -> 0x0166 }
            com.autonavi.bundle.routecommon.entity.IBusRouteResult r2 = r9.b     // Catch:{ JSONException -> 0x0166 }
            com.autonavi.common.model.POI r2 = r2.getFromPOI()     // Catch:{ JSONException -> 0x0166 }
            com.autonavi.common.model.POI r2 = r2.clone()     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r3 = "我的位置"
            java.lang.String r4 = r2.getName()     // Catch:{ JSONException -> 0x0166 }
            boolean r3 = r3.equals(r4)     // Catch:{ JSONException -> 0x0166 }
            if (r3 == 0) goto L_0x002b
            java.lang.String r3 = r1.mStartDes     // Catch:{ JSONException -> 0x0166 }
            r2.setName(r3)     // Catch:{ JSONException -> 0x0166 }
        L_0x002b:
            com.autonavi.bundle.routecommon.entity.IBusRouteResult r3 = r9.b     // Catch:{ JSONException -> 0x0166 }
            com.autonavi.common.model.POI r3 = r3.getToPOI()     // Catch:{ JSONException -> 0x0166 }
            com.autonavi.common.model.POI r3 = r3.clone()     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r4 = "我的位置"
            java.lang.String r5 = r3.getName()     // Catch:{ JSONException -> 0x0166 }
            boolean r4 = r4.equals(r5)     // Catch:{ JSONException -> 0x0166 }
            if (r4 == 0) goto L_0x0047
            java.lang.String r1 = r1.mEndDes     // Catch:{ JSONException -> 0x0166 }
            r3.setName(r1)     // Catch:{ JSONException -> 0x0166 }
        L_0x0047:
            java.lang.String r1 = "snapshotStartName"
            java.lang.String r2 = r2.getName()     // Catch:{ JSONException -> 0x0166 }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r1 = "snapshotEndName"
            java.lang.String r2 = r3.getName()     // Catch:{ JSONException -> 0x0166 }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r1 = "listNumber"
            com.autonavi.bundle.routecommon.entity.IBusRouteResult r2 = r9.b     // Catch:{ JSONException -> 0x0166 }
            int r2 = r2.getFocusBusPathIndex()     // Catch:{ JSONException -> 0x0166 }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0166 }
            goto L_0x007a
        L_0x0065:
            java.lang.String r1 = "snapshotStartName"
            java.lang.String r2 = ""
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r1 = "snapshotEndName"
            java.lang.String r2 = ""
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r1 = "listNumber"
            java.lang.String r2 = ""
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0166 }
        L_0x007a:
            lt r1 = defpackage.lt.a()     // Catch:{ JSONException -> 0x0166 }
            ls r1 = r1.c     // Catch:{ JSONException -> 0x0166 }
            lx r1 = r1.w     // Catch:{ JSONException -> 0x0166 }
            r2 = 0
            if (r1 == 0) goto L_0x0127
            java.lang.Boolean r3 = r1.b     // Catch:{ JSONException -> 0x0166 }
            if (r3 == 0) goto L_0x0127
            java.lang.Boolean r3 = r1.b     // Catch:{ JSONException -> 0x0166 }
            boolean r3 = r3.booleanValue()     // Catch:{ JSONException -> 0x0166 }
            com.autonavi.bundle.routecommon.entity.IBusRouteResult r4 = r9.b     // Catch:{ JSONException -> 0x0166 }
            if (r4 == 0) goto L_0x0126
            java.lang.Boolean r1 = r1.b     // Catch:{ JSONException -> 0x0166 }
            boolean r1 = r1.booleanValue()     // Catch:{ JSONException -> 0x0166 }
            r3 = 1
            if (r1 == 0) goto L_0x0127
            com.autonavi.bundle.routecommon.entity.IBusRouteResult r1 = r9.b     // Catch:{ JSONException -> 0x0166 }
            com.autonavi.common.model.POI r1 = r1.getFromPOI()     // Catch:{ JSONException -> 0x0166 }
            com.autonavi.bundle.routecommon.entity.IBusRouteResult r4 = r9.b     // Catch:{ JSONException -> 0x0166 }
            com.autonavi.common.model.POI r4 = r4.getToPOI()     // Catch:{ JSONException -> 0x0166 }
            com.autonavi.sdk.location.LocationInstrument r5 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x0166 }
            com.autonavi.common.model.GeoPoint r5 = r5.getLatestPosition()     // Catch:{ JSONException -> 0x0166 }
            if (r5 == 0) goto L_0x00c3
            li r6 = defpackage.li.a()     // Catch:{ JSONException -> 0x0166 }
            int r7 = r5.x     // Catch:{ JSONException -> 0x0166 }
            int r5 = r5.y     // Catch:{ JSONException -> 0x0166 }
            lj r5 = r6.b(r7, r5)     // Catch:{ JSONException -> 0x0166 }
            if (r5 == 0) goto L_0x00c3
            int r5 = r5.j     // Catch:{ JSONException -> 0x0166 }
            goto L_0x00c4
        L_0x00c3:
            r5 = 0
        L_0x00c4:
            if (r1 == 0) goto L_0x00dd
            com.autonavi.common.model.GeoPoint r1 = r1.getPoint()     // Catch:{ JSONException -> 0x0166 }
            if (r1 == 0) goto L_0x00dd
            li r6 = defpackage.li.a()     // Catch:{ JSONException -> 0x0166 }
            int r7 = r1.x     // Catch:{ JSONException -> 0x0166 }
            int r1 = r1.y     // Catch:{ JSONException -> 0x0166 }
            lj r1 = r6.b(r7, r1)     // Catch:{ JSONException -> 0x0166 }
            if (r1 == 0) goto L_0x00dd
            int r1 = r1.j     // Catch:{ JSONException -> 0x0166 }
            goto L_0x00de
        L_0x00dd:
            r1 = 0
        L_0x00de:
            if (r4 == 0) goto L_0x00f7
            com.autonavi.common.model.GeoPoint r4 = r4.getPoint()     // Catch:{ JSONException -> 0x0166 }
            if (r4 == 0) goto L_0x00f7
            li r6 = defpackage.li.a()     // Catch:{ JSONException -> 0x0166 }
            int r7 = r4.x     // Catch:{ JSONException -> 0x0166 }
            int r4 = r4.y     // Catch:{ JSONException -> 0x0166 }
            lj r4 = r6.b(r7, r4)     // Catch:{ JSONException -> 0x0166 }
            if (r4 == 0) goto L_0x00f7
            int r4 = r4.j     // Catch:{ JSONException -> 0x0166 }
            goto L_0x00f8
        L_0x00f7:
            r4 = 0
        L_0x00f8:
            java.lang.String r6 = "GeoCodeUtils"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r8 = "cur:"
            r7.<init>(r8)     // Catch:{ JSONException -> 0x0166 }
            r7.append(r5)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r8 = " start:"
            r7.append(r8)     // Catch:{ JSONException -> 0x0166 }
            r7.append(r1)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r8 = " end:"
            r7.append(r8)     // Catch:{ JSONException -> 0x0166 }
            r7.append(r4)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x0166 }
            defpackage.eao.e(r6, r7)     // Catch:{ JSONException -> 0x0166 }
            if (r5 != r1) goto L_0x0121
            if (r1 != r4) goto L_0x0121
            r1 = 1
            goto L_0x0122
        L_0x0121:
            r1 = 0
        L_0x0122:
            if (r1 == 0) goto L_0x0127
            r2 = 1
            goto L_0x0127
        L_0x0126:
            r2 = r3
        L_0x0127:
            java.lang.String r1 = "hasShareBike"
            if (r2 == 0) goto L_0x012e
            java.lang.String r2 = "1"
            goto L_0x0130
        L_0x012e:
            java.lang.String r2 = "0"
        L_0x0130:
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r1 = "resultData"
            java.lang.String r2 = r9.c     // Catch:{ JSONException -> 0x0166 }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r1 = "source_common"
            boolean r2 = r9.a     // Catch:{ JSONException -> 0x0166 }
            if (r2 == 0) goto L_0x0143
            java.lang.String r1 = "source_favorite"
            goto L_0x014b
        L_0x0143:
            boolean r2 = r9.a()     // Catch:{ JSONException -> 0x0166 }
            if (r2 == 0) goto L_0x014b
            java.lang.String r1 = "source_etrip"
        L_0x014b:
            java.lang.String r2 = "sourceType"
            r0.put(r2, r1)     // Catch:{ JSONException -> 0x0166 }
            boolean r1 = r9.d     // Catch:{ JSONException -> 0x0166 }
            if (r1 == 0) goto L_0x0157
            java.lang.String r1 = r9.h     // Catch:{ JSONException -> 0x0166 }
            goto L_0x0159
        L_0x0157:
            java.lang.String r1 = "0"
        L_0x0159:
            java.lang.String r2 = "cellHeight"
            r0.put(r2, r1)     // Catch:{ JSONException -> 0x0166 }
            java.lang.String r1 = "isFromFavorite"
            boolean r2 = r9.a     // Catch:{ JSONException -> 0x0166 }
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0166 }
            goto L_0x016a
        L_0x0166:
            r1 = move-exception
            r1.printStackTrace()
        L_0x016a:
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dwx.b():java.lang.String");
    }
}
