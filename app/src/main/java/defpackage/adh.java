package defpackage;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.planhome.page.PlanHomePage;
import com.autonavi.annotation.Router;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import org.json.JSONException;
import org.json.JSONObject;

@Router({"drive"})
/* renamed from: adh reason: default package */
/* compiled from: PlanTaxiRouter */
public class adh extends esk {
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0243 A[Catch:{ Exception -> 0x02ec }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00f0 A[Catch:{ Exception -> 0x02eb }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f7 A[Catch:{ Exception -> 0x02eb }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00fa A[Catch:{ Exception -> 0x02eb }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00fd A[Catch:{ Exception -> 0x02eb }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x010c A[SYNTHETIC, Splitter:B:50:0x010c] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0120 A[SYNTHETIC, Splitter:B:57:0x0120] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0165 A[SYNTHETIC, Splitter:B:70:0x0165] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0182 A[SYNTHETIC, Splitter:B:77:0x0182] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x01c1 A[Catch:{ Exception -> 0x02e8 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(android.net.Uri r20) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            com.autonavi.common.model.POI r3 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            com.autonavi.common.model.POI r4 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            java.lang.String r5 = "dev"
            int r5 = a(r2, r5)
            bid r7 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()     // Catch:{ Exception -> 0x02eb }
            if (r7 == 0) goto L_0x001d
            android.app.Activity r8 = r7.getActivity()     // Catch:{ Exception -> 0x02eb }
            goto L_0x001e
        L_0x001d:
            r8 = 0
        L_0x001e:
            if (r7 == 0) goto L_0x002b
            if (r8 == 0) goto L_0x002b
            boolean r8 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.isHomePage()     // Catch:{ Exception -> 0x02eb }
            if (r8 == 0) goto L_0x002b
            r7.dismissAllViewLayers()     // Catch:{ Exception -> 0x02eb }
        L_0x002b:
            java.lang.String r7 = "encrypt"
            int r7 = a(r2, r7)     // Catch:{ Exception -> 0x02eb }
            java.lang.String r8 = "sname"
            java.lang.String r8 = r2.getQueryParameter(r8)     // Catch:{ Exception -> 0x02eb }
            java.lang.String r9 = "slat"
            java.lang.String r9 = r2.getQueryParameter(r9)     // Catch:{ Exception -> 0x02eb }
            java.lang.String r10 = "slon"
            java.lang.String r10 = r2.getQueryParameter(r10)     // Catch:{ Exception -> 0x02eb }
            if (r8 != 0) goto L_0x0048
            java.lang.String r8 = ""
            goto L_0x004c
        L_0x0048:
            java.lang.String r8 = r8.trim()     // Catch:{ Exception -> 0x02eb }
        L_0x004c:
            r11 = 1
            if (r7 != r11) goto L_0x0057
            java.lang.String r9 = com.autonavi.server.aos.serverkey.amapDecode(r9)     // Catch:{ Exception -> 0x02eb }
            java.lang.String r10 = com.autonavi.server.aos.serverkey.amapDecode(r10)     // Catch:{ Exception -> 0x02eb }
        L_0x0057:
            boolean r12 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x02eb }
            if (r12 != 0) goto L_0x00a0
            boolean r12 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x02eb }
            if (r12 != 0) goto L_0x00a0
            boolean r12 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Exception -> 0x02eb }
            if (r12 != 0) goto L_0x006d
            r3.setName(r8)     // Catch:{ Exception -> 0x02eb }
            goto L_0x0073
        L_0x006d:
            java.lang.String r8 = "地图选定位置"
            r3.setName(r8)     // Catch:{ Exception -> 0x02eb }
        L_0x0073:
            java.lang.Double r8 = java.lang.Double.valueOf(r9)     // Catch:{ Exception -> 0x02eb }
            double r8 = r8.doubleValue()     // Catch:{ Exception -> 0x02eb }
            java.lang.Double r10 = java.lang.Double.valueOf(r10)     // Catch:{ Exception -> 0x02eb }
            double r12 = r10.doubleValue()     // Catch:{ Exception -> 0x02eb }
            android.graphics.Point r8 = defpackage.cfg.a(r8, r12)     // Catch:{ Exception -> 0x02eb }
            if (r5 != r11) goto L_0x0092
            int r9 = r8.x     // Catch:{ Exception -> 0x02eb }
            int r8 = r8.y     // Catch:{ Exception -> 0x02eb }
            com.autonavi.common.model.GeoPoint r8 = defpackage.cff.a(r9, r8)     // Catch:{ Exception -> 0x02eb }
            goto L_0x009c
        L_0x0092:
            com.autonavi.common.model.GeoPoint r9 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x02eb }
            int r10 = r8.x     // Catch:{ Exception -> 0x02eb }
            int r8 = r8.y     // Catch:{ Exception -> 0x02eb }
            r9.<init>(r10, r8)     // Catch:{ Exception -> 0x02eb }
            r8 = r9
        L_0x009c:
            r3.setPoint(r8)     // Catch:{ Exception -> 0x02eb }
            goto L_0x00c5
        L_0x00a0:
            boolean r9 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Exception -> 0x02eb }
            if (r9 != 0) goto L_0x00b4
            java.lang.String r9 = "我的位置"
            boolean r9 = r9.equals(r8)     // Catch:{ Exception -> 0x02eb }
            if (r9 != 0) goto L_0x00b4
            r3.setName(r8)     // Catch:{ Exception -> 0x02eb }
            r8 = 1
            goto L_0x00c6
        L_0x00b4:
            com.autonavi.sdk.location.LocationInstrument r8 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x02eb }
            com.autonavi.common.model.GeoPoint r8 = r8.getLatestPosition()     // Catch:{ Exception -> 0x02eb }
            java.lang.String r9 = "我的位置"
            r3.setName(r9)     // Catch:{ Exception -> 0x02eb }
            r3.setPoint(r8)     // Catch:{ Exception -> 0x02eb }
        L_0x00c5:
            r8 = 0
        L_0x00c6:
            java.lang.String r9 = "dname"
            java.lang.String r9 = r2.getQueryParameter(r9)     // Catch:{ Exception -> 0x02eb }
            java.lang.String r10 = "dlat"
            java.lang.String r10 = r2.getQueryParameter(r10)     // Catch:{ Exception -> 0x02eb }
            java.lang.String r12 = "dlon"
            java.lang.String r12 = r2.getQueryParameter(r12)     // Catch:{ Exception -> 0x02eb }
            java.lang.String r13 = "dpoi"
            java.lang.String r13 = r2.getQueryParameter(r13)     // Catch:{ Exception -> 0x02eb }
            java.lang.String r14 = "dcenterlat"
            java.lang.String r14 = r2.getQueryParameter(r14)     // Catch:{ Exception -> 0x02eb }
            java.lang.String r15 = "dcenterlon"
            java.lang.String r15 = r2.getQueryParameter(r15)     // Catch:{ Exception -> 0x02eb }
            boolean r16 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Exception -> 0x02eb }
            if (r16 == 0) goto L_0x00f1
            r14 = r10
        L_0x00f1:
            boolean r16 = android.text.TextUtils.isEmpty(r15)     // Catch:{ Exception -> 0x02eb }
            if (r16 == 0) goto L_0x00f8
            r15 = r12
        L_0x00f8:
            if (r9 != 0) goto L_0x00fd
            java.lang.String r9 = ""
            goto L_0x0101
        L_0x00fd:
            java.lang.String r9 = r9.trim()     // Catch:{ Exception -> 0x02eb }
        L_0x0101:
            java.lang.String r6 = "0"
            java.lang.String r11 = "page"
            java.lang.String r11 = r2.getQueryParameter(r11)     // Catch:{ Exception -> 0x02eb }
            r1 = 1
            if (r7 != r1) goto L_0x011a
            java.lang.String r10 = com.autonavi.server.aos.serverkey.amapDecode(r10)     // Catch:{ Exception -> 0x0115 }
            java.lang.String r12 = com.autonavi.server.aos.serverkey.amapDecode(r12)     // Catch:{ Exception -> 0x0115 }
            goto L_0x011a
        L_0x0115:
            r1 = 0
            r2 = r19
            goto L_0x02ed
        L_0x011a:
            boolean r1 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Exception -> 0x02e8 }
            if (r1 != 0) goto L_0x015f
            boolean r1 = android.text.TextUtils.isEmpty(r15)     // Catch:{ Exception -> 0x0115 }
            if (r1 != 0) goto L_0x015f
            java.lang.Double r1 = java.lang.Double.valueOf(r14)     // Catch:{ Exception -> 0x0115 }
            double r1 = r1.doubleValue()     // Catch:{ Exception -> 0x0115 }
            java.lang.Double r7 = java.lang.Double.valueOf(r15)     // Catch:{ Exception -> 0x0115 }
            double r14 = r7.doubleValue()     // Catch:{ Exception -> 0x0115 }
            android.graphics.Point r1 = defpackage.cfg.a(r1, r14)     // Catch:{ Exception -> 0x0115 }
            r2 = 1
            if (r5 != r2) goto L_0x0146
            int r2 = r1.x     // Catch:{ Exception -> 0x0115 }
            int r1 = r1.y     // Catch:{ Exception -> 0x0115 }
            com.autonavi.common.model.GeoPoint r1 = defpackage.cff.a(r2, r1)     // Catch:{ Exception -> 0x0115 }
            goto L_0x0150
        L_0x0146:
            com.autonavi.common.model.GeoPoint r2 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x0115 }
            int r5 = r1.x     // Catch:{ Exception -> 0x0115 }
            int r1 = r1.y     // Catch:{ Exception -> 0x0115 }
            r2.<init>(r5, r1)     // Catch:{ Exception -> 0x0115 }
            r1 = r2
        L_0x0150:
            r4.setPoint(r1)     // Catch:{ Exception -> 0x0115 }
            boolean r1 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x0115 }
            if (r1 == 0) goto L_0x015f
            java.lang.String r1 = "地图选定位置"
            r4.setName(r1)     // Catch:{ Exception -> 0x0115 }
        L_0x015f:
            boolean r1 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x02e8 }
            if (r1 != 0) goto L_0x017c
            r4.setName(r9)     // Catch:{ Exception -> 0x0115 }
            java.lang.String r1 = "我的位置"
            boolean r1 = r1.equals(r9)     // Catch:{ Exception -> 0x0115 }
            if (r1 == 0) goto L_0x017c
            com.autonavi.sdk.location.LocationInstrument r1 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x0115 }
            com.autonavi.common.model.GeoPoint r1 = r1.getLatestPosition()     // Catch:{ Exception -> 0x0115 }
            r4.setPoint(r1)     // Catch:{ Exception -> 0x0115 }
        L_0x017c:
            boolean r1 = android.text.TextUtils.isEmpty(r13)     // Catch:{ Exception -> 0x02e8 }
            if (r1 != 0) goto L_0x0185
            r4.setId(r13)     // Catch:{ Exception -> 0x0115 }
        L_0x0185:
            boolean r1 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Exception -> 0x01b6 }
            if (r1 != 0) goto L_0x01b1
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x01b6 }
            if (r1 != 0) goto L_0x01b1
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ Exception -> 0x01b6 }
            r2 = 1
            r1.<init>(r2)     // Catch:{ Exception -> 0x01b6 }
            com.autonavi.common.model.GeoPoint r2 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x01b6 }
            double r13 = java.lang.Double.parseDouble(r12)     // Catch:{ Exception -> 0x01b6 }
            r17 = r11
            r18 = r12
            double r11 = java.lang.Double.parseDouble(r10)     // Catch:{ Exception -> 0x01af }
            r2.<init>(r13, r11)     // Catch:{ Exception -> 0x01af }
            r1.add(r2)     // Catch:{ Exception -> 0x01af }
            r4.setEntranceList(r1)     // Catch:{ Exception -> 0x01af }
            goto L_0x01bf
        L_0x01af:
            r0 = move-exception
            goto L_0x01bb
        L_0x01b1:
            r17 = r11
            r18 = r12
            goto L_0x01bf
        L_0x01b6:
            r0 = move-exception
            r17 = r11
            r18 = r12
        L_0x01bb:
            r1 = r0
            r1.printStackTrace()     // Catch:{ Exception -> 0x02e8 }
        L_0x01bf:
            if (r8 == 0) goto L_0x0243
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x02e8 }
            r1.<init>()     // Catch:{ Exception -> 0x02e8 }
            java.lang.String r2 = "bundle_key_poi_start"
            r1.putObject(r2, r3)     // Catch:{ Exception -> 0x02e8 }
            java.lang.String r2 = "bundle_key_poi_end"
            r1.putObject(r2, r4)     // Catch:{ Exception -> 0x02e8 }
            java.lang.String r2 = "bundle_key_keyword"
            java.lang.String r3 = r3.getName()     // Catch:{ Exception -> 0x02e8 }
            r1.putString(r2, r3)     // Catch:{ Exception -> 0x02e8 }
            java.lang.String r2 = "bundle_key_method"
            r1.putString(r2, r6)     // Catch:{ Exception -> 0x02e8 }
            java.lang.String r2 = "bundle_key_taxi_ajx_param"
            r5 = r17
            r1.putString(r2, r5)     // Catch:{ Exception -> 0x02e8 }
            java.lang.String r2 = "bundle_key_request_code"
            r3 = 1001(0x3e9, float:1.403E-42)
            r1.putInt(r2, r3)     // Catch:{ Exception -> 0x02e8 }
            java.lang.String r2 = "bundle_key_from_scheme"
            r3 = 1
            r1.putBoolean(r2, r3)     // Catch:{ Exception -> 0x02e8 }
            boolean r2 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x02e8 }
            if (r2 != 0) goto L_0x0212
            java.lang.String r2 = "我的位置"
            boolean r2 = r2.equals(r9)     // Catch:{ Exception -> 0x0115 }
            if (r2 == 0) goto L_0x0212
            com.autonavi.sdk.location.LocationInstrument r2 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x0115 }
            com.autonavi.common.model.GeoPoint r2 = r2.getLatestPosition()     // Catch:{ Exception -> 0x0115 }
            r4.setPoint(r2)     // Catch:{ Exception -> 0x0115 }
            java.lang.String r2 = "bundle_key_poi_end"
            r1.putObject(r2, r4)     // Catch:{ Exception -> 0x0115 }
            goto L_0x023a
        L_0x0212:
            boolean r2 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x02e8 }
            if (r2 != 0) goto L_0x0226
            r12 = r18
            boolean r2 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Exception -> 0x0115 }
            if (r2 != 0) goto L_0x0226
            java.lang.String r2 = "bundle_key_poi_end"
            r1.putObject(r2, r4)     // Catch:{ Exception -> 0x0115 }
            goto L_0x023a
        L_0x0226:
            boolean r2 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x02e8 }
            if (r2 != 0) goto L_0x023a
            java.lang.String r2 = "我的位置"
            boolean r2 = r2.equals(r9)     // Catch:{ Exception -> 0x0115 }
            if (r2 != 0) goto L_0x023a
            java.lang.String r2 = "bundle_key_end_poi_name_passed_in"
            r1.putString(r2, r9)     // Catch:{ Exception -> 0x0115 }
        L_0x023a:
            r2 = r19
            r7 = r20
            r2.a(r1, r7)     // Catch:{ Exception -> 0x02ec }
            r1 = 1
            return r1
        L_0x0243:
            r5 = r17
            r12 = r18
            r2 = r19
            r7 = r20
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x02ec }
            if (r1 == 0) goto L_0x02b7
            boolean r1 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Exception -> 0x02ec }
            if (r1 == 0) goto L_0x02b7
            boolean r1 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x02ec }
            if (r1 == 0) goto L_0x027b
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x02ec }
            r1.<init>()     // Catch:{ Exception -> 0x02ec }
            java.lang.String r4 = "bundle_key_poi_start"
            r1.putObject(r4, r3)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "bundle_key_method"
            r1.putString(r3, r6)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "bundle_key_from_scheme"
            r4 = 1
            r1.putBoolean(r3, r4)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "bundle_key_taxi_ajx_param"
            r1.putString(r3, r5)     // Catch:{ Exception -> 0x02ec }
            r2.a(r1, r7)     // Catch:{ Exception -> 0x02ec }
            return r4
        L_0x027b:
            java.lang.String r1 = "我的位置"
            boolean r1 = r1.equals(r9)     // Catch:{ Exception -> 0x02ec }
            if (r1 != 0) goto L_0x02b7
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x02ec }
            r1.<init>()     // Catch:{ Exception -> 0x02ec }
            java.lang.String r8 = "bundle_key_poi_start"
            r1.putObject(r8, r3)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "bundle_key_poi_end"
            r1.putObject(r3, r4)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "bundle_key_keyword"
            java.lang.String r4 = r4.getName()     // Catch:{ Exception -> 0x02ec }
            r1.putString(r3, r4)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "bundle_key_method"
            r1.putString(r3, r6)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "bundle_key_request_code"
            r4 = 1002(0x3ea, float:1.404E-42)
            r1.putInt(r3, r4)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "bundle_key_from_scheme"
            r4 = 1
            r1.putBoolean(r3, r4)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "bundle_key_taxi_ajx_param"
            r1.putString(r3, r5)     // Catch:{ Exception -> 0x02ec }
            r2.a(r1, r7)     // Catch:{ Exception -> 0x02ec }
            return r4
        L_0x02b7:
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x02ec }
            r1.<init>()     // Catch:{ Exception -> 0x02ec }
            java.lang.String r8 = "bundle_key_route_type"
            com.autonavi.bundle.routecommon.model.RouteType r9 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ Exception -> 0x02ec }
            r1.putObject(r8, r9)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r8 = "bundle_key_poi_start"
            r1.putObject(r8, r3)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "bundle_key_poi_end"
            r1.putObject(r3, r4)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "bundle_key_method"
            r1.putString(r3, r6)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "bundle_key_from_scheme"
            r4 = 1
            r1.putBoolean(r3, r4)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "key_savehistory"
            r4 = 0
            r1.putBoolean(r3, r4)     // Catch:{ Exception -> 0x02ec }
            java.lang.String r3 = "bundle_key_taxi_ajx_param"
            r1.putString(r3, r5)     // Catch:{ Exception -> 0x02ec }
            r2.a(r1, r7)     // Catch:{ Exception -> 0x02ec }
            r1 = 1
            return r1
        L_0x02e8:
            r2 = r19
            goto L_0x02ec
        L_0x02eb:
            r2 = r1
        L_0x02ec:
            r1 = 0
        L_0x02ed:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.adh.a(android.net.Uri):boolean");
    }

    private void a(final PageBundle pageBundle, final Uri uri) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("plan_bundle_key_schema", uri.toString());
            if (pageBundle.getInt("key_source", 0) == 102) {
                jSONObject.put("fromSource", "etrip");
            }
        } catch (JSONException unused) {
        }
        pageBundle.putObject("jsData", jSONObject);
        pageBundle.setFlags(4);
        pageBundle.putBoolean("plan_bundle_schema_agent", true);
        pageBundle.putObject("bundle_key_route_type", RouteType.TAXI);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public final void run() {
                boolean z;
                esj a2 = adh.this.mWingContext;
                if (a2 != null) {
                    boolean z2 = true;
                    try {
                        if (new JSONObject(lo.a().a((String) FunctionSupportConfiger.TAXI_TAG)).optInt("taxi_home_page_redesign", 0) == 1) {
                            z = true;
                            if (a2.c == null || a2.c.c() != PlanHomePage.class) {
                                z2 = false;
                            }
                            boolean isFileExists = AjxFileInfo.isFileExists(Ajx.getInstance().lookupLoader("path://amap_bundle_taxi/src/taxi_index/page/independent/TaxiIndependentHome.page.js").processingPath(PictureParams.make(null, "path://amap_bundle_taxi/src/taxi_index/page/independent/TaxiIndependentHome.page.js", false)));
                            String queryParameter = uri.getQueryParameter("targetPage");
                            if (z || z2 || !isFileExists || TextUtils.equals(queryParameter, "tabHome")) {
                                a2.a(PlanHomePage.class, pageBundle);
                            }
                            pageBundle.putString("url", "path://amap_bundle_taxi/src/taxi_index/page/independent/TaxiIndependentHome.page.js");
                            a2.a(Ajx3Page.class, pageBundle);
                            return;
                        }
                    } catch (Exception unused) {
                    }
                    z = false;
                    z2 = false;
                    boolean isFileExists2 = AjxFileInfo.isFileExists(Ajx.getInstance().lookupLoader("path://amap_bundle_taxi/src/taxi_index/page/independent/TaxiIndependentHome.page.js").processingPath(PictureParams.make(null, "path://amap_bundle_taxi/src/taxi_index/page/independent/TaxiIndependentHome.page.js", false)));
                    String queryParameter2 = uri.getQueryParameter("targetPage");
                    if (z) {
                    }
                    a2.a(PlanHomePage.class, pageBundle);
                }
            }
        });
    }

    private static int a(Uri uri, String str) {
        try {
            return Integer.parseInt(uri.getQueryParameter(str));
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    public boolean start(ese ese) {
        Uri uri = ese.a;
        String host = uri.getHost();
        if (host == null || !host.equals("drive") || uri.getPathSegments() == null || uri.getPathSegments().size() == 0) {
            return false;
        }
        String str = uri.getPathSegments().get(0).split("&")[0];
        if (str != null && str.length() > 0 && TextUtils.equals(str, "takeTaxi")) {
            a(uri);
            return true;
        }
        return false;
    }
}
