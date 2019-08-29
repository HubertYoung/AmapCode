package com.autonavi.bundle.routecommute.common;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class NaviAddressManager$1 extends FalconAosPrepareResponseCallback<JSONObject> {
    final /* synthetic */ int a;
    final /* synthetic */ a b;

    public NaviAddressManager$1(int i, a aVar) {
        this.a = i;
        this.b = aVar;
    }

    public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
        return b(aosByteResponse);
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x01d3  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0263  */
    /* JADX WARNING: Removed duplicated region for block: B:80:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ void a(java.lang.Object r13) {
        /*
            r12 = this;
            org.json.JSONObject r13 = (org.json.JSONObject) r13
            r0 = 0
            if (r13 != 0) goto L_0x0008
        L_0x0005:
            r13 = r0
            goto L_0x01cf
        L_0x0008:
            java.lang.String r1 = com.autonavi.bundle.routecommute.common.CommuteHelper.a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "parseNaviAddress jsonObject = "
            r2.<init>(r3)
            java.lang.String r3 = r13.toString()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            defpackage.azb.a(r1, r2)
            java.lang.String r1 = "code"
            int r1 = r13.optInt(r1)
            r2 = 7
            r3 = 1
            if (r1 == r3) goto L_0x002b
            if (r1 != r2) goto L_0x0031
        L_0x002b:
            defpackage.azi.b()
            defpackage.azi.d()
        L_0x0031:
            if (r1 != r2) goto L_0x003a
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r13 = new com.autonavi.bundle.routecommute.common.bean.NaviAddress
            r13.<init>()
            goto L_0x01cf
        L_0x003a:
            if (r1 == r3) goto L_0x003d
            goto L_0x0005
        L_0x003d:
            java.lang.String r1 = "data"
            org.json.JSONObject r13 = r13.optJSONObject(r1)
            if (r13 != 0) goto L_0x0046
            goto L_0x0005
        L_0x0046:
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r1 = new com.autonavi.bundle.routecommute.common.bean.NaviAddress
            r1.<init>()
            java.lang.String r2 = "home"
            org.json.JSONObject r2 = r13.optJSONObject(r2)
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r4 = new com.autonavi.bundle.routecommute.common.bean.NaviAddressHome
            r4.<init>()
            r5 = 0
            if (r2 == 0) goto L_0x00e7
            com.amap.bundle.datamodel.poi.POIBase r6 = new com.amap.bundle.datamodel.poi.POIBase
            r6.<init>()
            java.lang.String r7 = "poiid"
            java.lang.String r7 = r2.optString(r7)
            r6.setId(r7)
            java.lang.String r7 = "name"
            java.lang.String r7 = r2.optString(r7)
            r6.setName(r7)
            java.lang.String r7 = "city_code"
            java.lang.String r7 = r2.optString(r7)
            r6.setCityCode(r7)
            java.lang.String r7 = "adcode"
            java.lang.String r7 = r2.optString(r7)
            r6.setAdCode(r7)
            java.lang.String r7 = "x"
            java.lang.String r7 = r2.optString(r7)
            java.lang.String r8 = "y"
            java.lang.String r8 = r2.optString(r8)
            boolean r9 = android.text.TextUtils.isEmpty(r7)
            if (r9 != 0) goto L_0x00ae
            boolean r9 = android.text.TextUtils.isEmpty(r8)
            if (r9 != 0) goto L_0x00ae
            com.autonavi.common.model.GeoPoint r9 = new com.autonavi.common.model.GeoPoint
            float r7 = java.lang.Float.parseFloat(r7)
            double r10 = (double) r7
            float r7 = java.lang.Float.parseFloat(r8)
            double r7 = (double) r7
            r9.<init>(r10, r7)
            r6.setPoint(r9)
        L_0x00ae:
            java.lang.String r7 = "address"
            java.lang.String r7 = r2.optString(r7)
            r6.setAddr(r7)
            java.lang.String r7 = "typecode"
            java.lang.String r7 = r2.optString(r7)
            r6.setType(r7)
            java.lang.String r7 = "f_nona"
            java.lang.String r7 = r2.optString(r7)
            r6.setInoorFloorNoName(r7)
            r4.setHome(r6)
            java.lang.String r7 = "p_home"
            java.lang.String r7 = r2.optString(r7)
            r4.pHome = r7
            r4.source = r5
            java.lang.String r7 = "is_search"
            int r2 = r2.optInt(r7)
            boolean r6 = defpackage.bnx.a(r6)
            if (r6 == 0) goto L_0x00e7
            if (r2 != r3) goto L_0x00e7
            r1.home = r4
        L_0x00e7:
            java.lang.String r2 = "company"
            org.json.JSONObject r2 = r13.optJSONObject(r2)
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r4 = new com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany
            r4.<init>()
            if (r2 == 0) goto L_0x0182
            com.amap.bundle.datamodel.poi.POIBase r6 = new com.amap.bundle.datamodel.poi.POIBase
            r6.<init>()
            java.lang.String r7 = "poiid"
            java.lang.String r7 = r2.optString(r7)
            r6.setId(r7)
            java.lang.String r7 = "name"
            java.lang.String r7 = r2.optString(r7)
            r6.setName(r7)
            java.lang.String r7 = "city_code"
            java.lang.String r7 = r2.optString(r7)
            r6.setCityCode(r7)
            java.lang.String r7 = "adcode"
            java.lang.String r7 = r2.optString(r7)
            r6.setAdCode(r7)
            java.lang.String r7 = "x"
            java.lang.String r7 = r2.optString(r7)
            java.lang.String r8 = "y"
            java.lang.String r8 = r2.optString(r8)
            boolean r9 = android.text.TextUtils.isEmpty(r7)
            if (r9 != 0) goto L_0x0149
            boolean r9 = android.text.TextUtils.isEmpty(r8)
            if (r9 != 0) goto L_0x0149
            com.autonavi.common.model.GeoPoint r9 = new com.autonavi.common.model.GeoPoint
            float r7 = java.lang.Float.parseFloat(r7)
            double r10 = (double) r7
            float r7 = java.lang.Float.parseFloat(r8)
            double r7 = (double) r7
            r9.<init>(r10, r7)
            r6.setPoint(r9)
        L_0x0149:
            java.lang.String r7 = "address"
            java.lang.String r7 = r2.optString(r7)
            r6.setAddr(r7)
            java.lang.String r7 = "typecode"
            java.lang.String r7 = r2.optString(r7)
            r6.setType(r7)
            java.lang.String r7 = "f_nona"
            java.lang.String r7 = r2.optString(r7)
            r6.setInoorFloorNoName(r7)
            r4.setCompany(r6)
            java.lang.String r7 = "p_company"
            java.lang.String r7 = r2.optString(r7)
            r4.pCompany = r7
            r4.source = r5
            java.lang.String r5 = "is_search"
            int r2 = r2.optInt(r5)
            boolean r5 = defpackage.bnx.a(r6)
            if (r5 == 0) goto L_0x0182
            if (r2 != r3) goto L_0x0182
            r1.company = r4
        L_0x0182:
            java.lang.String r2 = "car_pref"
            java.lang.String r2 = r13.optString(r2)
            java.lang.String r3 = "bus_pref"
            java.lang.String r13 = r13.optString(r3)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x01ca
            boolean r3 = android.text.TextUtils.isEmpty(r13)
            if (r3 == 0) goto L_0x019b
            goto L_0x01ca
        L_0x019b:
            float r2 = java.lang.Float.parseFloat(r2)
            float r13 = java.lang.Float.parseFloat(r13)
            int r3 = (r2 > r13 ? 1 : (r2 == r13 ? 0 : -1))
            if (r3 > 0) goto L_0x01c7
            int r3 = (r2 > r13 ? 1 : (r2 == r13 ? 0 : -1))
            r4 = 4591870180066957722(0x3fb999999999999a, double:0.1)
            if (r3 > 0) goto L_0x01b7
            float r3 = r13 - r2
            double r6 = (double) r3
            int r3 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r3 <= 0) goto L_0x01c7
        L_0x01b7:
            int r3 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
            if (r3 <= 0) goto L_0x01be
            java.lang.String r13 = "1"
            goto L_0x01cc
        L_0x01be:
            float r13 = r13 - r2
            double r2 = (double) r13
            int r13 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r13 <= 0) goto L_0x01c7
            java.lang.String r13 = "1"
            goto L_0x01cc
        L_0x01c7:
            java.lang.String r13 = "0"
            goto L_0x01cc
        L_0x01ca:
            java.lang.String r13 = "0"
        L_0x01cc:
            r1.busCarPref = r13
            r13 = r1
        L_0x01cf:
            int r1 = r12.a
            if (r13 == 0) goto L_0x025f
            switch(r1) {
                case 0: goto L_0x0254;
                case 1: goto L_0x022e;
                case 2: goto L_0x0208;
                case 3: goto L_0x01d8;
                default: goto L_0x01d6;
            }
        L_0x01d6:
            goto L_0x025f
        L_0x01d8:
            defpackage.azf.a = r13
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r1 = r13.home
            if (r1 != 0) goto L_0x01e2
            defpackage.azi.a(r0)
            goto L_0x01eb
        L_0x01e2:
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r1 = r13.home
            com.autonavi.common.model.POI r1 = r1.getHome()
            defpackage.azi.a(r1)
        L_0x01eb:
            defpackage.azi.b()
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r1 = r13.company
            if (r1 != 0) goto L_0x01f6
            defpackage.azi.b(r0)
            goto L_0x01ff
        L_0x01f6:
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r0 = r13.company
            com.autonavi.common.model.POI r0 = r0.getCompany()
            defpackage.azi.b(r0)
        L_0x01ff:
            defpackage.azi.d()
            java.lang.String r13 = r13.busCarPref
            defpackage.azi.a(r13)
            goto L_0x025f
        L_0x0208:
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r1 = defpackage.azf.a
            java.lang.String r2 = r13.busCarPref
            r1.busCarPref = r2
            java.lang.String r1 = r13.busCarPref
            defpackage.azi.a(r1)
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r1 = defpackage.azf.a
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r2 = r13.company
            r1.company = r2
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r1 = r13.company
            if (r1 != 0) goto L_0x0221
            defpackage.azi.b(r0)
            goto L_0x022a
        L_0x0221:
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r13 = r13.company
            com.autonavi.common.model.POI r13 = r13.getCompany()
            defpackage.azi.b(r13)
        L_0x022a:
            defpackage.azi.d()
            goto L_0x025f
        L_0x022e:
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r1 = defpackage.azf.a
            java.lang.String r2 = r13.busCarPref
            r1.busCarPref = r2
            java.lang.String r1 = r13.busCarPref
            defpackage.azi.a(r1)
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r1 = defpackage.azf.a
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r2 = r13.home
            r1.home = r2
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r1 = r13.home
            if (r1 != 0) goto L_0x0247
            defpackage.azi.a(r0)
            goto L_0x0250
        L_0x0247:
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r13 = r13.home
            com.autonavi.common.model.POI r13 = r13.getHome()
            defpackage.azi.a(r13)
        L_0x0250:
            defpackage.azi.b()
            goto L_0x025f
        L_0x0254:
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r0 = defpackage.azf.a
            java.lang.String r1 = r13.busCarPref
            r0.busCarPref = r1
            java.lang.String r13 = r13.busCarPref
            defpackage.azi.a(r13)
        L_0x025f:
            azf$a r13 = r12.b
            if (r13 == 0) goto L_0x026a
            azf$a r13 = r12.b
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r0 = defpackage.azf.a
            r13.a(r0)
        L_0x026a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.routecommute.common.NaviAddressManager$1.a(java.lang.Object):void");
    }

    private static JSONObject b(AosByteResponse aosByteResponse) {
        byte[] bArr = (byte[]) aosByteResponse.getResult();
        if (bArr == null) {
            return null;
        }
        try {
            if (bArr.length > 0) {
                return new JSONObject(new String((byte[]) aosByteResponse.getResult(), "UTF-8").trim());
            }
            return null;
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.b != null) {
            this.b.a(azf.a);
        }
    }
}
