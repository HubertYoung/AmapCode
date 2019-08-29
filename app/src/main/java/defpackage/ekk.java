package defpackage;

import android.content.Intent;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.voice.IRouteVoice;
import com.autonavi.minimap.route.voice.model.PoiModel;
import com.autonavi.minimap.route.voice.model.RouteRideNaviModel;
import com.autonavi.sdk.location.LocationInstrument;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.UCAsyncTask;

/* renamed from: ekk reason: default package */
/* compiled from: RouteVoiceImp */
public final class ekk implements IRouteVoice {
    private ekm a;

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0069, code lost:
        if (r2.equals(r6.poiName) != false) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0076, code lost:
        if (r13 == r3) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007a, code lost:
        if (r9 != 0) goto L_0x004e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x008b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void requestRoutePlan(int r21, com.autonavi.minimap.route.voice.model.RoutePlanModel r22) {
        /*
            r20 = this;
            r1 = r21
            r2 = r22
            boolean r3 = defpackage.eko.b(r21)
            if (r3 != 0) goto L_0x000b
            return
        L_0x000b:
            if (r2 == 0) goto L_0x0184
            com.autonavi.minimap.route.voice.model.PoiModel r3 = r2.startPoi
            com.autonavi.common.model.POI r3 = a(r1, r3)
            if (r3 != 0) goto L_0x0016
            return
        L_0x0016:
            com.autonavi.minimap.route.voice.model.PoiModel r4 = r2.endPoi
            com.autonavi.common.model.POI r4 = a(r1, r4)
            if (r4 != 0) goto L_0x001f
            return
        L_0x001f:
            com.autonavi.minimap.route.voice.model.PoiModel r5 = r2.startPoi
            com.autonavi.minimap.route.voice.model.PoiModel r6 = r2.endPoi
            if (r5 == 0) goto L_0x007d
            if (r6 != 0) goto L_0x0028
            goto L_0x007d
        L_0x0028:
            int r9 = r5.poiType
            int r10 = r6.poiType
            double r11 = r5.lon
            double r13 = r5.lat
            double r7 = r6.lon
            r16 = r3
            r17 = r4
            double r3 = r6.lat
            java.lang.String r15 = r5.poiId
            java.lang.String r2 = r6.poiId
            boolean r19 = android.text.TextUtils.isEmpty(r15)
            if (r19 != 0) goto L_0x0051
            boolean r19 = android.text.TextUtils.isEmpty(r2)
            if (r19 != 0) goto L_0x0051
            boolean r2 = r15.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x0051
        L_0x004e:
            r18 = 1
            goto L_0x0083
        L_0x0051:
            int r2 = com.autonavi.minimap.R.string.LocationMe
            android.app.Application r15 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r2 = r15.getString(r2)
            java.lang.String r5 = r5.poiName
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x006c
            java.lang.String r5 = r6.poiName
            boolean r2 = r2.equals(r5)
            if (r2 == 0) goto L_0x006c
            goto L_0x004e
        L_0x006c:
            if (r9 != r10) goto L_0x0078
            if (r9 != 0) goto L_0x0078
            int r2 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r2 != 0) goto L_0x0078
            int r2 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1))
            if (r2 == 0) goto L_0x004e
        L_0x0078:
            if (r9 != r10) goto L_0x0081
            if (r9 == 0) goto L_0x0081
            goto L_0x004e
        L_0x007d:
            r16 = r3
            r17 = r4
        L_0x0081:
            r18 = 0
        L_0x0083:
            if (r18 == 0) goto L_0x008b
            r2 = 10009(0x2719, float:1.4026E-41)
            defpackage.eko.a(r1, r2)
            return
        L_0x008b:
            r2 = r22
            int r3 = r2.t
            r4 = 10001(0x2711, float:1.4014E-41)
            r5 = 3
            r6 = 1
            if (r3 == r6) goto L_0x00a2
            int r3 = r2.t
            r6 = 2
            if (r3 == r6) goto L_0x00a2
            int r3 = r2.t
            if (r3 == r5) goto L_0x00a2
            defpackage.eko.a(r1, r4)
            return
        L_0x00a2:
            int r3 = r2.t
            if (r5 != r3) goto L_0x00cb
            java.lang.String r3 = r2.rideType
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 == 0) goto L_0x00b3
            java.lang.String r1 = "bike"
            r2.rideType = r1
            goto L_0x00cb
        L_0x00b3:
            java.lang.String r3 = r2.rideType
            java.lang.String r5 = "bike"
            boolean r3 = android.text.TextUtils.equals(r3, r5)
            if (r3 != 0) goto L_0x00cb
            java.lang.String r3 = r2.rideType
            java.lang.String r5 = "elebike"
            boolean r3 = android.text.TextUtils.equals(r3, r5)
            if (r3 != 0) goto L_0x00cb
            defpackage.eko.a(r1, r4)
            return
        L_0x00cb:
            r1 = 0
            bid r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()     // Catch:{ Exception -> 0x00d9 }
            java.lang.String r4 = "amap.basemap.action.default_page"
            r3.startPage(r4, r1)     // Catch:{ Exception -> 0x00d9 }
            com.autonavi.minimap.widget.ConfirmDlgLifeCircle.removeAll()     // Catch:{ Exception -> 0x00d9 }
            goto L_0x00f0
        L_0x00d9:
            r0 = move-exception
            r3 = r0
            java.lang.String r4 = "IntentController"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r3 = r3.getMessage()
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            com.amap.bundle.logs.AMapLog.e(r4, r3)
        L_0x00f0:
            int r3 = r2.t
            java.lang.String r2 = r2.rideType
            if (r16 == 0) goto L_0x0180
            r4 = r16
            com.autonavi.common.model.GeoPoint r5 = r4.getPoint()
            if (r5 == 0) goto L_0x0180
            if (r17 == 0) goto L_0x0180
            r5 = r17
            com.autonavi.common.model.GeoPoint r6 = r5.getPoint()
            if (r6 == 0) goto L_0x0180
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r6 = "amapuri://routePlan/plan?sid=BGVIS1&t="
            r1.append(r6)
            r1.append(r3)
            java.lang.String r3 = "&dlat="
            r1.append(r3)
            com.autonavi.common.model.GeoPoint r3 = r5.getPoint()
            double r6 = r3.getLatitude()
            r1.append(r6)
            java.lang.String r3 = "&dev=0&dname="
            r1.append(r3)
            java.lang.String r3 = r5.getName()
            r1.append(r3)
            java.lang.String r3 = "&slat="
            r1.append(r3)
            com.autonavi.common.model.GeoPoint r3 = r4.getPoint()
            double r6 = r3.getLatitude()
            r1.append(r6)
            java.lang.String r3 = "&dlon="
            r1.append(r3)
            com.autonavi.common.model.GeoPoint r3 = r5.getPoint()
            double r5 = r3.getLongitude()
            r1.append(r5)
            java.lang.String r3 = "&did=BGVIS2&slon="
            r1.append(r3)
            com.autonavi.common.model.GeoPoint r3 = r4.getPoint()
            double r5 = r3.getLongitude()
            r1.append(r5)
            java.lang.String r3 = "&m=0&rideType="
            r1.append(r3)
            r1.append(r2)
            java.lang.String r3 = "&sname="
            r1.append(r3)
            java.lang.String r3 = r4.getName()
            r1.append(r3)
            defpackage.eko.c(r2)
            java.lang.String r1 = r1.toString()
            android.content.Intent r1 = defpackage.eko.d(r1)
        L_0x0180:
            defpackage.eko.a(r1)
            return
        L_0x0184:
            r2 = 10020(0x2724, float:1.4041E-41)
            defpackage.eko.a(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ekk.requestRoutePlan(int, com.autonavi.minimap.route.voice.model.RoutePlanModel):void");
    }

    public final void requestRouteRideNavi(int i, RouteRideNaviModel routeRideNaviModel) {
        Intent intent;
        if (eko.b(i)) {
            if (routeRideNaviModel != null) {
                POI a2 = a(i, routeRideNaviModel.endPoi);
                if (a2 != null) {
                    if (routeRideNaviModel.endPoi.poiType == 3) {
                        eko.a(i, 10001);
                        return;
                    }
                    if (TextUtils.isEmpty(routeRideNaviModel.rideType)) {
                        routeRideNaviModel.rideType = "bike";
                    } else if (!TextUtils.equals(routeRideNaviModel.rideType, "bike") && !TextUtils.equals(routeRideNaviModel.rideType, "elebike")) {
                        eko.a(i, 10001);
                        return;
                    }
                    String str = routeRideNaviModel.rideType;
                    if (a2 == null || a2.getPoint() == null || TextUtils.isEmpty(str)) {
                        intent = null;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("androidamap://rideNavi?featureName=OnRideNavi&");
                        sb.append("lon=");
                        sb.append(a2.getPoint().getLongitude());
                        sb.append("&sourceApplication=appName&dev=0&rideType=");
                        sb.append(str);
                        sb.append("&lat=");
                        sb.append(a2.getPoint().getLatitude());
                        sb.append("&endName=");
                        sb.append(a2.getName());
                        sb.append("&poiId=");
                        sb.append(a2.getId());
                        eko.c(str);
                        intent = eko.d(sb.toString());
                    }
                    eko.a(intent);
                    return;
                }
                return;
            }
            eko.a(i, (int) SDKFactory.getCoreType);
        }
    }

    public final void requestRouteFootNavi(int i, PoiModel poiModel) {
        Intent intent;
        if (eko.b(i)) {
            if (poiModel != null) {
                POI a2 = a(i, poiModel);
                if (a2 != null) {
                    if (poiModel.poiType == 3) {
                        eko.a(i, 10001);
                        return;
                    }
                    if (a2 == null || a2.getPoint() == null) {
                        intent = null;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("amapuri://footNavi?featureName=OnFootNavi&lon=");
                        sb.append(a2.getPoint().getLongitude());
                        sb.append("&sourceApplication=amap&dev=0&lat=");
                        sb.append(a2.getPoint().getLatitude());
                        sb.append("&endName=");
                        sb.append(a2.getName());
                        sb.append("&poiId=");
                        sb.append(a2.getId());
                        intent = eko.d(sb.toString());
                    }
                    eko.a(intent);
                    return;
                }
                return;
            }
            eko.a(i, 10001);
        }
    }

    public final void searchBusLine(int i, String str, String str2) {
        Intent intent;
        if (eko.b(i)) {
            if (TextUtils.isEmpty(str)) {
                eko.a(i, 10001);
                return;
            }
            if (TextUtils.isEmpty(str2)) {
                if (!bnz.b()) {
                    eko.a(i, 10037);
                    return;
                }
                str2 = LocationInstrument.getInstance().getLatestPosition().getCity();
            }
            if (!TextUtils.isEmpty(str2)) {
                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                    intent = null;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("amapuri://busline?featureName=BuslineSearch&busname=");
                    sb.append(str);
                    sb.append("&city=");
                    sb.append(str2);
                    intent = eko.d(sb.toString());
                }
                eko.a(intent);
                return;
            }
            eko.a(i, (int) SDKFactory.getCoreType);
        }
    }

    public final void searchSubwayLine(int i, String str) {
        Intent intent;
        if (eko.b(i)) {
            if (TextUtils.isEmpty(str)) {
                if (!bnz.b()) {
                    eko.a(i, 10037);
                    return;
                }
                str = String.valueOf(LocationInstrument.getInstance().getLatestPosition().getAdCode());
            }
            if (!TextUtils.isEmpty(str)) {
                if (!TextUtils.isEmpty(str)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("amapuri://subway?&adCode=");
                    sb.append(str);
                    intent = eko.d(sb.toString());
                } else {
                    intent = null;
                }
                eko.a(intent);
                return;
            }
            eko.a(i, (int) SDKFactory.getCoreType);
        }
    }

    private static POI a(int i, PoiModel poiModel) {
        POI poi;
        POI poi2 = null;
        if (poiModel != null) {
            switch (poiModel.poiType) {
                case 0:
                    if (!eko.b(poiModel.lon, poiModel.lat)) {
                        if (eko.a(poiModel.lon, poiModel.lat)) {
                            if (TextUtils.isEmpty(poiModel.poiName)) {
                                poiModel.poiName = "地图选定位置";
                            }
                            poi2 = POIFactory.createPOI(poiModel.poiName, new GeoPoint(poiModel.lon, poiModel.lat));
                            poi2.setId(poiModel.poiId);
                            break;
                        } else {
                            eko.a(i, 10005);
                            return null;
                        }
                    } else {
                        eko.a(i, 10001);
                        return null;
                    }
                case 1:
                    poi = eko.a();
                    if (poi == null) {
                        eko.a(i, 10012);
                        return null;
                    }
                    break;
                case 2:
                    poi = eko.b();
                    if (poi == null) {
                        eko.a(i, (int) UCAsyncTask.getPriority);
                        return null;
                    }
                    break;
                case 3:
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
                    if (latestPosition != null) {
                        poi2 = POIFactory.createPOI();
                        poi2.setPoint(latestPosition);
                        poi2.setName("我的位置");
                        break;
                    } else {
                        if (!bnz.b()) {
                            eko.a(i, 10037);
                        } else {
                            eko.a(i, 10003);
                        }
                        return null;
                    }
                default:
                    eko.a(i, 10001);
                    break;
            }
            poi2 = poi;
        }
        return poi2;
    }

    public final void exitNavi(int i) {
        if (this.a != null) {
            this.a.a();
        }
    }

    public final void setExitNaviListener(ekm ekm) {
        this.a = ekm;
    }
}
