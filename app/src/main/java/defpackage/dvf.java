package defpackage;

/* renamed from: dvf reason: default package */
/* compiled from: BusRequestImpl */
public final class dvf implements atc {

    /* renamed from: dvf$a */
    /* compiled from: BusRequestImpl */
    static class a {
        static dvf a = new dvf();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0073, code lost:
        if (r1.length() > 0) goto L_0x0077;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.amap.bundle.aosservice.request.AosRequest a(android.content.Context r18, com.autonavi.common.model.POI r19, com.autonavi.common.model.POI r20, java.lang.String r21, long r22, defpackage.axa r24) {
        /*
            r17 = this;
            eag r0 = new eag
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.BUS
            r2 = r19
            r3 = r20
            r0.<init>(r1, r2, r3)
            r1 = r21
            r0.d = r1
            r1 = r22
            r0.e = r1
            java.lang.String r1 = r0.d
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x001f
            java.lang.String r1 = "0"
            r0.d = r1
        L_0x001f:
            long r1 = r0.e
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            r5 = -1
            if (r1 != 0) goto L_0x002b
            r0.e = r5
        L_0x002b:
            com.autonavi.common.model.POI r10 = r0.b
            com.autonavi.common.model.POI r11 = r0.c
            java.lang.String r12 = r0.d
            long r13 = r0.e
            com.autonavi.minimap.route.bus.localbus.net.BusRequestHelper$1 r9 = new com.autonavi.minimap.route.bus.localbus.net.BusRequestHelper$1
            r1 = r24
            r9.<init>(r1, r0)
            boolean r0 = defpackage.bnx.a(r10, r11)
            r1 = 0
            if (r0 == 0) goto L_0x0042
            return r1
        L_0x0042:
            com.autonavi.minimap.route.bus.localbus.net.param.RouteBusParamUrlWrapper r0 = new com.autonavi.minimap.route.bus.localbus.net.param.RouteBusParamUrlWrapper
            r0.<init>()
            if (r12 == 0) goto L_0x0058
            java.lang.String r2 = r12.trim()
            java.lang.String r7 = ""
            boolean r2 = r2.equals(r7)
            if (r2 == 0) goto L_0x0056
            goto L_0x0058
        L_0x0056:
            r1 = r12
            goto L_0x0077
        L_0x0058:
            java.lang.String r2 = "0"
            if (r18 == 0) goto L_0x0076
            com.amap.bundle.mapstorage.MapSharePreference r7 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r15 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.user_route_method_info
            r7.<init>(r15)
            android.content.SharedPreferences r7 = r7.sharedPrefs()
            java.lang.String r15 = "bus_method"
            java.lang.String r1 = ""
            java.lang.String r1 = r7.getString(r15, r1)
            int r7 = r1.length()
            if (r7 <= 0) goto L_0x0076
            goto L_0x0077
        L_0x0076:
            r1 = r2
        L_0x0077:
            r0.type = r1
            r2 = 4
            r7 = 3
            r15 = 2
            r3 = 1
            if (r10 == 0) goto L_0x00ef
            java.lang.String r4 = r10.getName()
            java.lang.String r5 = "我的位置"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0098
            com.autonavi.sdk.location.LocationInstrument r4 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            com.autonavi.common.model.GeoPoint r4 = r4.getLatestPosition()
            if (r4 == 0) goto L_0x0098
            r10.setPoint(r4)
        L_0x0098:
            com.autonavi.common.model.GeoPoint r4 = r10.getPoint()
            double r4 = r4.getLongitude()
            r0.x1 = r4
            com.autonavi.common.model.GeoPoint r4 = r10.getPoint()
            double r4 = r4.getLatitude()
            r0.y1 = r4
            java.lang.String r4 = r10.getId()
            r0.poiid1 = r4
            java.lang.String r4 = r0.poiid1
            boolean r4 = defpackage.ebm.b(r4)
            if (r4 == 0) goto L_0x00bd
            r0.precision1 = r7
            goto L_0x00e1
        L_0x00bd:
            java.lang.String r4 = r10.getName()
            java.lang.String r5 = "我的位置"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x00cc
            r0.precision1 = r3
            goto L_0x00dd
        L_0x00cc:
            java.lang.String r4 = r10.getName()
            java.lang.String r5 = "地图指定位置"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x00db
            r0.precision1 = r15
            goto L_0x00dd
        L_0x00db:
            r0.precision1 = r2
        L_0x00dd:
            java.lang.String r4 = ""
            r0.poiid1 = r4
        L_0x00e1:
            java.lang.String r4 = r10.getAdCode()
            r0.ad1 = r4
            java.lang.String r4 = r0.ad1
            if (r4 != 0) goto L_0x00ef
            java.lang.String r4 = ""
            r0.ad1 = r4
        L_0x00ef:
            if (r11 == 0) goto L_0x0161
            java.lang.String r4 = r11.getName()
            java.lang.String r5 = "我的位置"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x010a
            com.autonavi.sdk.location.LocationInstrument r4 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            com.autonavi.common.model.GeoPoint r4 = r4.getLatestPosition()
            if (r4 == 0) goto L_0x010a
            r11.setPoint(r4)
        L_0x010a:
            com.autonavi.common.model.GeoPoint r4 = r11.getPoint()
            double r4 = r4.getLongitude()
            r0.x2 = r4
            com.autonavi.common.model.GeoPoint r4 = r11.getPoint()
            double r4 = r4.getLatitude()
            r0.y2 = r4
            java.lang.String r4 = r11.getId()
            r0.poiid2 = r4
            java.lang.String r4 = r0.poiid2
            boolean r4 = defpackage.ebm.b(r4)
            if (r4 == 0) goto L_0x012f
            r0.precision2 = r7
            goto L_0x0153
        L_0x012f:
            java.lang.String r4 = r11.getName()
            java.lang.String r5 = "我的位置"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x013e
            r0.precision2 = r3
            goto L_0x014f
        L_0x013e:
            java.lang.String r4 = r11.getName()
            java.lang.String r5 = "地图指定位置"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x014d
            r0.precision2 = r15
            goto L_0x014f
        L_0x014d:
            r0.precision2 = r2
        L_0x014f:
            java.lang.String r2 = ""
            r0.poiid2 = r2
        L_0x0153:
            java.lang.String r2 = r11.getAdCode()
            r0.ad2 = r2
            java.lang.String r2 = r0.ad2
            if (r2 != 0) goto L_0x0161
            java.lang.String r2 = ""
            r0.ad2 = r2
        L_0x0161:
            java.lang.String r2 = r0.type
            java.lang.String r4 = "0"
            boolean r2 = android.text.TextUtils.equals(r2, r4)
            if (r2 == 0) goto L_0x0181
            int r2 = r0.group
            if (r2 != r3) goto L_0x0181
            java.lang.String r2 = r0.date
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0181
            java.lang.String r2 = r0.time
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0181
            r0.taxi = r3
        L_0x0181:
            r4 = -1
            int r2 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r2 == 0) goto L_0x019e
            r4 = 0
            int r2 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r2 != 0) goto L_0x018e
            goto L_0x019e
        L_0x018e:
            r4 = -2
            int r2 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r2 == 0) goto L_0x019c
            java.util.Calendar r2 = java.util.Calendar.getInstance()
            r2.setTimeInMillis(r13)
            goto L_0x01a2
        L_0x019c:
            r2 = 0
            goto L_0x01a2
        L_0x019e:
            java.util.Calendar r2 = java.util.Calendar.getInstance()
        L_0x01a2:
            if (r2 == 0) goto L_0x01f3
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            int r4 = r2.get(r3)
            r1.append(r4)
            java.lang.String r4 = "-"
            r1.append(r4)
            int r4 = r2.get(r15)
            int r4 = r4 + r3
            r1.append(r4)
            java.lang.String r3 = "-"
            r1.append(r3)
            r3 = 5
            int r3 = r2.get(r3)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r4 = 11
            int r4 = r2.get(r4)
            r3.append(r4)
            java.lang.String r4 = "-"
            r3.append(r4)
            r4 = 12
            int r2 = r2.get(r4)
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            r0.date = r1
            r0.time = r2
            goto L_0x01fd
        L_0x01f3:
            java.lang.String r2 = "0"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x01fd
            r0.taxi = r3
        L_0x01fd:
            com.autonavi.minimap.route.bus.localbus.RouteBusResultCallBack r1 = new com.autonavi.minimap.route.bus.localbus.RouteBusResultCallBack
            r7 = r1
            r8 = r18
            r7.<init>(r8, r9, r10, r11, r12, r13)
            com.amap.bundle.aosservice.request.AosPostRequest r0 = defpackage.ebh.a(r1, r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dvf.a(android.content.Context, com.autonavi.common.model.POI, com.autonavi.common.model.POI, java.lang.String, long, axa):com.amap.bundle.aosservice.request.AosRequest");
    }
}
