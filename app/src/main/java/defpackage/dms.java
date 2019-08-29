package defpackage;

/* renamed from: dms reason: default package */
/* compiled from: GetMapLocationAction */
public class dms extends vz {
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0066 A[Catch:{ Exception -> 0x0104 }] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00b8 A[Catch:{ Exception -> 0x0104 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00e6 A[Catch:{ Exception -> 0x0104 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(org.json.JSONObject r9, defpackage.wa r10) {
        /*
            r8 = this;
            com.amap.bundle.jsadapter.JsAdapter r0 = r8.a()
            if (r0 == 0) goto L_0x0109
            bid r1 = r0.mPageContext
            if (r1 != 0) goto L_0x000c
            goto L_0x0109
        L_0x000c:
            com.autonavi.map.core.MapManager r1 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapManager()     // Catch:{ Exception -> 0x0104 }
            bty r1 = r1.getMapView()     // Catch:{ Exception -> 0x0104 }
            r2 = 0
            if (r1 == 0) goto L_0x001f
            com.autonavi.ae.gmap.glinterface.GLGeoPoint r1 = r1.n()     // Catch:{ Exception -> 0x0104 }
            com.autonavi.common.model.GeoPoint r2 = com.autonavi.common.model.GeoPoint.glGeoPoint2GeoPoint(r1)     // Catch:{ Exception -> 0x0104 }
        L_0x001f:
            java.lang.String r1 = "forceReturnValue"
            int r9 = r9.optInt(r1)     // Catch:{ Exception -> 0x0104 }
            com.autonavi.sdk.location.LocationInstrument r1 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x0104 }
            r3 = 5
            com.autonavi.common.model.GeoPoint r1 = r1.getLatestPosition(r3)     // Catch:{ Exception -> 0x0104 }
            if (r9 != 0) goto L_0x0035
            if (r1 != 0) goto L_0x0035
            if (r2 == 0) goto L_0x0035
            r1 = r2
        L_0x0035:
            com.autonavi.sdk.location.LocationInstrument r9 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x0104 }
            r3 = 0
            if (r9 == 0) goto L_0x004b
            android.location.Location r9 = r9.getLatestLocation()     // Catch:{ Exception -> 0x0104 }
            if (r9 == 0) goto L_0x004b
            float r3 = r9.getAccuracy()     // Catch:{ Exception -> 0x0104 }
            float r9 = r9.getBearing()     // Catch:{ Exception -> 0x0104 }
            goto L_0x004c
        L_0x004b:
            r9 = 0
        L_0x004c:
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x0104 }
            r4.<init>()     // Catch:{ Exception -> 0x0104 }
            java.lang.String r5 = "_action"
            java.lang.String r6 = r10.b     // Catch:{ Exception -> 0x0104 }
            r4.put(r5, r6)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r5 = "accuracy"
            double r6 = (double) r3     // Catch:{ Exception -> 0x0104 }
            r4.put(r5, r6)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r3 = "direction"
            double r5 = (double) r9     // Catch:{ Exception -> 0x0104 }
            r4.put(r3, r5)     // Catch:{ Exception -> 0x0104 }
            if (r1 == 0) goto L_0x00b8
            java.lang.String r9 = r1.getCity()     // Catch:{ Exception -> 0x0104 }
            if (r9 != 0) goto L_0x007b
            java.lang.String r9 = "adcode"
            java.lang.String r3 = ""
            r4.put(r9, r3)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r9 = "cityName"
            java.lang.String r3 = ""
            r4.put(r9, r3)     // Catch:{ Exception -> 0x0104 }
            goto L_0x0095
        L_0x007b:
            java.lang.String r3 = "adcode"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0104 }
            r5.<init>()     // Catch:{ Exception -> 0x0104 }
            int r6 = r1.getAdCode()     // Catch:{ Exception -> 0x0104 }
            r5.append(r6)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0104 }
            r4.put(r3, r5)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r3 = "cityName"
            r4.put(r3, r9)     // Catch:{ Exception -> 0x0104 }
        L_0x0095:
            java.lang.String r9 = "x"
            int r3 = r1.x     // Catch:{ Exception -> 0x0104 }
            r4.put(r9, r3)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r9 = "y"
            int r3 = r1.y     // Catch:{ Exception -> 0x0104 }
            r4.put(r9, r3)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r9 = "lon"
            double r5 = r1.getLongitude()     // Catch:{ Exception -> 0x0104 }
            r4.put(r9, r5)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r9 = "lat"
            double r5 = r1.getLatitude()     // Catch:{ Exception -> 0x0104 }
            r4.put(r9, r5)     // Catch:{ Exception -> 0x0104 }
            goto L_0x00e4
        L_0x00b8:
            java.lang.String r9 = "adcode"
            java.lang.String r1 = ""
            r4.put(r9, r1)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r9 = "cityName"
            java.lang.String r1 = ""
            r4.put(r9, r1)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r9 = "x"
            java.lang.String r1 = "0"
            r4.put(r9, r1)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r9 = "y"
            java.lang.String r1 = "0"
            r4.put(r9, r1)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r9 = "lon"
            java.lang.String r1 = "0"
            r4.put(r9, r1)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r9 = "lat"
            java.lang.String r1 = "0"
            r4.put(r9, r1)     // Catch:{ Exception -> 0x0104 }
        L_0x00e4:
            if (r2 == 0) goto L_0x00fa
            java.lang.String r9 = "view_x"
            double r5 = r2.getLongitude()     // Catch:{ Exception -> 0x0104 }
            r4.put(r9, r5)     // Catch:{ Exception -> 0x0104 }
            java.lang.String r9 = "view_y"
            double r1 = r2.getLatitude()     // Catch:{ Exception -> 0x0104 }
            r4.put(r9, r1)     // Catch:{ Exception -> 0x0104 }
        L_0x00fa:
            java.lang.String r9 = r10.a     // Catch:{ Exception -> 0x0104 }
            java.lang.String r10 = r4.toString()     // Catch:{ Exception -> 0x0104 }
            r0.callJs(r9, r10)     // Catch:{ Exception -> 0x0104 }
            return
        L_0x0104:
            r9 = move-exception
            defpackage.kf.a(r9)
            return
        L_0x0109:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dms.a(org.json.JSONObject, wa):void");
    }
}
