package defpackage;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.guide.model.ExitDirectionInfo;
import com.autonavi.jni.ae.guide.model.NaviInfo;
import com.autonavi.jni.ae.guide.model.PathTrafficEventInfo;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.jni.ae.route.route.Route;
import com.autonavi.minimap.ajx3.modules.ModuleNavi;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/* renamed from: rk reason: default package */
/* compiled from: NaviIntentUtil */
public final class rk {
    public static sp a(Context context, PageBundle pageBundle) {
        boolean z;
        sp spVar = new sp();
        boolean z2 = true;
        if (pageBundle == null || pageBundle.isEmpty()) {
            spVar.n = true;
        }
        String string = pageBundle.getString("navi_type", "car");
        spVar.b = string;
        Object object = pageBundle.getObject("navi_uri");
        if (object != null) {
            spVar.z = ((Uri) object).getQueryParameter(DriveUtil.SOURCE_APPLICATION);
        }
        boolean a = a(pageBundle, string, spVar);
        boolean b = b(pageBundle, string, spVar);
        spVar.l = pageBundle.getBoolean("tipNaviFlag");
        spVar.o = pageBundle.getBoolean("isCommuteFromHome", true);
        boolean z3 = false;
        spVar.p = pageBundle.getBoolean("isFromRouteResult", false);
        spVar.k = a || b;
        spVar.t = pageBundle.getBoolean("mIsRadar", false);
        if (pageBundle.containsKey("radarNaviInfo")) {
            spVar.u = (NaviInfo[]) pageBundle.getObject("radarNaviInfo");
        }
        if (pageBundle.containsKey("deletePathIDs")) {
            spVar.v = pageBundle.getLongArray("deletePathIDs");
        }
        if (pageBundle.containsKey("radarTrafficEvent")) {
            spVar.y = (PathTrafficEventInfo[]) pageBundle.getObject("radarTrafficEvent");
        }
        if (pageBundle.containsKey("radarExitSegmentIndex")) {
            spVar.w = pageBundle.getInt("radarExitSegmentIndex");
        }
        if (pageBundle.containsKey("radarExitInfo")) {
            spVar.x = (ExitDirectionInfo) pageBundle.getObject("radarExitInfo");
        }
        int i = spVar.d;
        if (!a && !b) {
            boolean z4 = pageBundle.getBoolean("IsSimNavi");
            int i2 = pageBundle.getInt("NaviMethod");
            i = pageBundle.getInt("NaviFlags");
            if (pageBundle.containsKey("isOfflinePlan")) {
                z = pageBundle.getBoolean("isOfflinePlan");
                ku.a().c("NaviMonitor", "[NaviIntentUtil]#processNodeFragmentBundle-bundle.containsKey(NAVI_IS_OFFLINE_PLAN)-isOffline:".concat(String.valueOf(z)));
            } else {
                z = false;
            }
            if (!z) {
                z = DriveSpUtil.shouldRouteOffline();
                ku.a().c("NaviMonitor", "[NaviIntentUtil]#processNodeFragmentBundle-!DrivingNavigationSPUtilImpl.getSearchRouteInNetMode()-isOffline:".concat(String.valueOf(z)));
            }
            POI poi = (POI) pageBundle.getSerializable("StartPOI");
            ArrayList arrayList = (ArrayList) pageBundle.getSerializable("ThrouthPOI");
            POI poi2 = (POI) pageBundle.getSerializable("EndPOI");
            if (pageBundle.containsKey("RouteObj")) {
                spVar.e = (Route) pageBundle.getObject("RouteObj");
            }
            if (pageBundle.containsKey("calc_route_result")) {
                spVar.q = (CalcRouteResult) pageBundle.getObject("calc_route_result");
                spVar.r = pageBundle.getBoolean("mIsMultiRoute", false);
            }
            if (pageBundle.containsKey("need_backprev")) {
                spVar.s = pageBundle.getBoolean("need_backprev", false);
            }
            spVar.a = z4;
            spVar.c = i2;
            spVar.f = a(poi);
            List<POI> a2 = a((List<POI>) arrayList);
            spVar.g = b(a2);
            spVar.h = a(a2, z4);
            spVar.i = a(poi2);
            z3 = z;
        }
        if (z3 || aaw.c(context)) {
            z2 = z3;
        } else {
            ku.a().c("NaviMonitor", "[NaviIntentUtil]#processNodeFragmentBundle-!NavigationUtil.isNetworkConnected()-isOffline:true");
        }
        int i3 = z2 ? i | 256 : i & -257;
        pageBundle.putInt("NaviFlags", i3);
        spVar.d = i3;
        spVar.j = z2;
        return spVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0162  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(com.autonavi.common.PageBundle r11, java.lang.String r12, defpackage.sp r13) {
        /*
            java.lang.String r0 = "navi_uri"
            boolean r0 = r11.containsKey(r0)
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            java.lang.String r0 = "key_action"
            boolean r0 = r11.containsKey(r0)
            if (r0 == 0) goto L_0x002a
            java.lang.String r0 = "key_action"
            java.lang.String r0 = r11.getString(r0)
            java.lang.String r2 = "actiono_back_scheme"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x002a
            java.lang.String r0 = "key_back_scheme_param"
            java.lang.Object r0 = r11.getObject(r0)
            dlg r0 = (defpackage.dlg) r0
            r13.m = r0
        L_0x002a:
            java.lang.String r0 = "navi_uri"
            java.lang.Object r11 = r11.getObject(r0)
            android.net.Uri r11 = (android.net.Uri) r11
            if (r11 == 0) goto L_0x018a
            boolean r0 = r11.isHierarchical()
            if (r0 == 0) goto L_0x018a
            java.lang.String r0 = r11.getScheme()
            if (r0 == 0) goto L_0x018a
            java.lang.String r0 = r11.getScheme()
            java.lang.String r2 = "androidamap"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x018a
            java.lang.String r0 = "sourceApplication"
            java.lang.String r0 = r11.getQueryParameter(r0)
            com.amap.bundle.network.request.param.NetworkParam.setSa(r0)
            r2 = 0
            r0 = 1
            java.lang.String r4 = "lat"
            java.lang.String r4 = r11.getQueryParameter(r4)     // Catch:{ NumberFormatException -> 0x0097 }
            java.lang.String r5 = "lon"
            java.lang.String r5 = r11.getQueryParameter(r5)     // Catch:{ NumberFormatException -> 0x0097 }
            boolean r6 = android.text.TextUtils.isEmpty(r4)     // Catch:{ NumberFormatException -> 0x0097 }
            if (r6 != 0) goto L_0x007e
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ NumberFormatException -> 0x0097 }
            if (r6 != 0) goto L_0x007e
            double r6 = java.lang.Double.parseDouble(r4)     // Catch:{ NumberFormatException -> 0x0097 }
            double r4 = java.lang.Double.parseDouble(r5)     // Catch:{ NumberFormatException -> 0x007a }
            r2 = r6
            goto L_0x0081
        L_0x007a:
            r4 = move-exception
            r9 = r2
            r2 = r6
            goto L_0x0095
        L_0x007e:
            r13.n = r0     // Catch:{ NumberFormatException -> 0x0097 }
            r4 = r2
        L_0x0081:
            java.lang.String r6 = "dev"
            java.lang.String r6 = r11.getQueryParameter(r6)     // Catch:{ NumberFormatException -> 0x0092 }
            boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch:{ NumberFormatException -> 0x0092 }
            if (r7 != 0) goto L_0x009f
            int r6 = java.lang.Integer.parseInt(r6)     // Catch:{ NumberFormatException -> 0x0092 }
            goto L_0x00a0
        L_0x0092:
            r6 = move-exception
            r9 = r4
            r4 = r6
        L_0x0095:
            r5 = r9
            goto L_0x0099
        L_0x0097:
            r4 = move-exception
            r5 = r2
        L_0x0099:
            r13.n = r0
            r4.printStackTrace()
            r4 = r5
        L_0x009f:
            r6 = 0
        L_0x00a0:
            android.graphics.Point r2 = defpackage.cfg.a(r2, r4)
            if (r6 != r0) goto L_0x00af
            int r3 = r2.x
            int r2 = r2.y
            com.autonavi.common.model.GeoPoint r2 = defpackage.cff.a(r3, r2)
            goto L_0x00b9
        L_0x00af:
            com.autonavi.common.model.GeoPoint r3 = new com.autonavi.common.model.GeoPoint
            int r4 = r2.x
            int r2 = r2.y
            r3.<init>(r4, r2)
            r2 = r3
        L_0x00b9:
            java.lang.String r3 = "poiname"
            java.lang.String r3 = r11.getQueryParameter(r3)
            java.lang.String r4 = "poiid"
            java.lang.String r4 = r11.getQueryParameter(r4)
            java.lang.String r5 = "truck"
            boolean r12 = android.text.TextUtils.equals(r12, r5)
            if (r12 == 0) goto L_0x00d2
            java.lang.String r12 = com.amap.bundle.drivecommon.tools.DriveUtil.getTruckRoutingChoice()
            goto L_0x00d6
        L_0x00d2:
            java.lang.String r12 = com.amap.bundle.drivecommon.tools.DriveUtil.getLastRoutingChoice()
        L_0x00d6:
            com.autonavi.common.model.POI r2 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r3, r2)
            r2.setId(r4)
            r13.i = r2
            int r2 = defpackage.dhw.d(r12)
            r13.c = r2
            int r12 = defpackage.dhw.c(r12)
            r13.d = r12
            java.util.ArrayList r12 = new java.util.ArrayList
            r12.<init>()
            java.lang.String r2 = "throughpoint"
            java.lang.String r11 = r11.getQueryParameter(r2)
            if (r11 == 0) goto L_0x0189
            int r2 = r11.length()
            if (r2 <= 0) goto L_0x0189
            java.lang.String r2 = ";"
            java.lang.String[] r11 = r11.split(r2)
            int r2 = r11.length
            r3 = 0
        L_0x0106:
            if (r3 >= r2) goto L_0x015c
            r4 = r11[r3]
            int r5 = r4.length()
            if (r5 == 0) goto L_0x0159
            java.lang.String r5 = ","
            java.lang.String[] r4 = r4.split(r5)
            int r5 = r4.length
            if (r5 <= r0) goto L_0x0159
            r5 = r4[r1]
            int r5 = r5.length()
            if (r5 <= 0) goto L_0x0159
            r5 = r4[r0]
            int r5 = r5.length()
            if (r5 <= 0) goto L_0x0159
            r5 = r4[r1]
            java.lang.Double r5 = java.lang.Double.valueOf(r5)
            double r7 = r5.doubleValue()
            r4 = r4[r0]
            java.lang.Double r4 = java.lang.Double.valueOf(r4)
            double r4 = r4.doubleValue()
            android.graphics.Point r4 = defpackage.cfg.a(r4, r7)
            if (r6 != r0) goto L_0x014c
            int r5 = r4.x
            int r4 = r4.y
            com.autonavi.common.model.GeoPoint r4 = defpackage.cff.a(r5, r4)
            goto L_0x0156
        L_0x014c:
            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint
            int r7 = r4.x
            int r4 = r4.y
            r5.<init>(r7, r4)
            r4 = r5
        L_0x0156:
            r12.add(r4)
        L_0x0159:
            int r3 = r3 + 1
            goto L_0x0106
        L_0x015c:
            int r11 = r12.size()
            if (r11 <= 0) goto L_0x0189
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
        L_0x0167:
            int r2 = r12.size()
            if (r1 >= r2) goto L_0x0187
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r3 = com.autonavi.minimap.R.string.autonavi_util_pass_name
            java.lang.String r2 = r2.getString(r3)
            java.lang.Object r3 = r12.get(r1)
            com.autonavi.common.model.GeoPoint r3 = (com.autonavi.common.model.GeoPoint) r3
            com.autonavi.common.model.POI r2 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r2, r3)
            r11.add(r2)
            int r1 = r1 + 1
            goto L_0x0167
        L_0x0187:
            r13.g = r11
        L_0x0189:
            return r0
        L_0x018a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.rk.a(com.autonavi.common.PageBundle, java.lang.String, sp):boolean");
    }

    private static boolean b(PageBundle pageBundle, String str, sp spVar) {
        boolean z;
        String str2;
        String str3;
        String str4;
        String str5;
        double d;
        double d2;
        double d3;
        String str6;
        String str7;
        String str8;
        String str9;
        double d4;
        String str10;
        PageBundle pageBundle2 = pageBundle;
        sp spVar2 = spVar;
        String string = pageBundle2.getString("navi_action");
        String string2 = pageBundle2.getString("navi_data");
        int i = 0;
        boolean z2 = pageBundle2.getBoolean("navi_form_shortcutnavi", false);
        boolean z3 = pageBundle2.getBoolean("navi_form_shortcutnavi_new_scheme", false);
        if (TextUtils.isEmpty(string) || !"com.autonavi.minimap.ACTION".equals(string)) {
            return false;
        }
        if (TextUtils.isEmpty(string2)) {
            spVar2.n = true;
            return true;
        }
        Uri parse = Uri.parse(string2);
        String scheme = parse.getScheme();
        if (TextUtils.isEmpty(scheme) || (!ModuleNavi.MODULE_NAME.equals(scheme) && (!z3 || !scheme.equals("amapuri")))) {
            spVar2.n = true;
            return true;
        }
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(string2.substring(5), ",");
            if (z3) {
                String queryParameter = parse.getQueryParameter(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
                String queryParameter2 = parse.getQueryParameter("poiname");
                String queryParameter3 = parse.getQueryParameter(LocationParams.PARA_FLP_AUTONAVI_LON);
                double parseDouble = !TextUtils.isEmpty(queryParameter3) ? Double.parseDouble(queryParameter3) : 0.0d;
                String queryParameter4 = parse.getQueryParameter("lat");
                double parseDouble2 = !TextUtils.isEmpty(queryParameter4) ? Double.parseDouble(queryParameter4) : 0.0d;
                String queryParameter5 = parse.getQueryParameter("dlon");
                double parseDouble3 = !TextUtils.isEmpty(queryParameter5) ? Double.parseDouble(queryParameter5) : 0.0d;
                String queryParameter6 = parse.getQueryParameter("dlat");
                double parseDouble4 = !TextUtils.isEmpty(queryParameter6) ? Double.parseDouble(queryParameter6) : 0.0d;
                String queryParameter7 = parse.getQueryParameter("navitype");
                if (!TextUtils.isEmpty(queryParameter7)) {
                    spVar2.c = Integer.parseInt(queryParameter7);
                }
                String queryParameter8 = parse.getQueryParameter("naviflag");
                if (!TextUtils.isEmpty(queryParameter8)) {
                    spVar2.d = Integer.parseInt(queryParameter8);
                }
                str8 = parse.getQueryParameter("endpoiextension");
                String queryParameter9 = parse.getQueryParameter("newtype");
                String queryParameter10 = parse.getQueryParameter("parentid");
                String queryParameter11 = parse.getQueryParameter("childtype");
                String queryParameter12 = parse.getQueryParameter("towardsangle");
                str2 = parse.getQueryParameter("fnona");
                d4 = parseDouble3;
                str5 = queryParameter10;
                str3 = queryParameter11;
                str4 = queryParameter12;
                d = parseDouble4;
                d3 = parseDouble2;
                d2 = parseDouble;
                str7 = queryParameter;
                str6 = queryParameter2;
                str9 = queryParameter9;
            } else {
                d3 = 0.0d;
                d2 = 0.0d;
                d = 0.0d;
                double d5 = 0.0d;
                while (stringTokenizer.hasMoreTokens()) {
                    try {
                        String nextToken = stringTokenizer.nextToken();
                        if (i == 0) {
                            d3 = Double.parseDouble(nextToken);
                        } else if (i == 1) {
                            d2 = Double.parseDouble(nextToken);
                        } else if (i != 2) {
                            if (i == 3) {
                                spVar2.c = Integer.parseInt(nextToken);
                            } else if (i == 4) {
                                NetworkParam.setSa(nextToken);
                            } else if (i == 5) {
                                spVar2.d = Integer.parseInt(nextToken);
                            } else if (i == 6) {
                                d = Double.parseDouble(nextToken);
                            } else if (i == 7) {
                                d5 = Double.parseDouble(nextToken);
                            }
                        }
                        i++;
                    } catch (Exception e) {
                        e.printStackTrace();
                        spVar2.n = true;
                    }
                }
                str9 = null;
                str8 = null;
                str7 = null;
                str6 = null;
                str5 = null;
                str4 = null;
                str3 = null;
                str2 = null;
                d4 = d5;
            }
            if (z2) {
                str10 = str9;
                String truckRoutingChoice = TextUtils.equals(str, DriveUtil.NAVI_TYPE_TRUCK) ? DriveUtil.getTruckRoutingChoice() : DriveUtil.getLastRoutingChoice();
                spVar2.c = dhw.d(truckRoutingChoice);
                spVar2.d = dhw.c(truckRoutingChoice);
            } else {
                str10 = str9;
            }
            GeoPoint geoPoint = new GeoPoint();
            Point a = cfg.a(d3, d2);
            geoPoint.x = a.x;
            geoPoint.y = a.y;
            POI createPOI = POIFactory.createPOI("未知位置", geoPoint);
            if (d > 0.0d && d4 > 0.0d) {
                ArrayList arrayList = new ArrayList();
                GeoPoint geoPoint2 = new GeoPoint();
                Point a2 = cfg.a(d, d4);
                geoPoint2.x = a2.x;
                geoPoint2.y = a2.y;
                arrayList.add(geoPoint2);
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(geoPoint);
                createPOI.setExitList(arrayList2);
                createPOI.setEntranceList(arrayList);
            }
            if (z3) {
                if (!TextUtils.isEmpty(str7)) {
                    createPOI.setId(str7);
                }
                if (!TextUtils.isEmpty(str6)) {
                    createPOI.setName(str6);
                }
                if (!TextUtils.isEmpty(str8)) {
                    createPOI.setEndPoiExtension(str8);
                }
                String str11 = str10;
                if (!TextUtils.isEmpty(str11)) {
                    createPOI.setType(str11);
                }
                ISearchPoiData iSearchPoiData = (ISearchPoiData) createPOI.as(ISearchPoiData.class);
                String str12 = str4;
                if (!TextUtils.isEmpty(str12)) {
                    iSearchPoiData.setTowardsAngle(str12);
                }
                String str13 = str5;
                if (!TextUtils.isEmpty(str13)) {
                    iSearchPoiData.setParent(str13);
                }
                String str14 = str3;
                if (!TextUtils.isEmpty(str14)) {
                    iSearchPoiData.setChildType(str14);
                }
                String str15 = str2;
                if (!TextUtils.isEmpty(str15)) {
                    iSearchPoiData.setFnona(str15);
                }
                createPOI = iSearchPoiData.as(POI.class);
            }
            spVar2.i = createPOI;
            z = true;
        } catch (Exception e2) {
            e2.printStackTrace();
            z = true;
            spVar2.n = true;
        }
        return z;
    }

    private static POI a(POI poi) {
        if (poi == null) {
            return null;
        }
        return poi.clone();
    }

    private static List<POI> a(List<POI> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (POI a : list) {
            arrayList.add(a(a));
        }
        return arrayList;
    }

    private static List<POI> b(List<POI> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (POI next : list) {
            if (!next.getPoiExtra().containsKey("viaPassedPoi")) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    private static List<so> a(List<POI> list, boolean z) {
        if (list == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            POI poi = list.get(i);
            so soVar = new so(poi, i);
            if (poi.getPoiExtra().containsKey("viaPassedPoi")) {
                soVar.b = true;
                soVar.c = -1;
            }
            soVar.d = z;
            arrayList.add(soVar);
        }
        return arrayList;
    }
}
