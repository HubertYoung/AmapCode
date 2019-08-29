package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.widget.ConfirmDlgLifeCircle;

@Router({"showTraffic", "navi", "navi2SpecialDest", "keywordNavi", "openFeature", "drive", "edog"})
/* renamed from: mn reason: default package */
/* compiled from: DriveNaviRouter */
public class mn extends esk {
    private boolean a = false;
    private long b = 0;
    private String c = null;

    /* JADX WARNING: Removed duplicated region for block: B:113:0x02e2 A[Catch:{ JSONException -> 0x02e8 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(android.content.Intent r11) {
        /*
            r10 = this;
            android.net.Uri r0 = r11.getData()
            java.lang.String r1 = r0.getHost()
            r2 = 0
            if (r1 != 0) goto L_0x000c
            return r2
        L_0x000c:
            java.lang.String r3 = "drive"
            boolean r3 = android.text.TextUtils.equals(r1, r3)
            if (r3 != 0) goto L_0x0015
            return r2
        L_0x0015:
            java.util.List r3 = r0.getPathSegments()
            if (r3 == 0) goto L_0x02ef
            java.util.List r3 = r0.getPathSegments()
            int r3 = r3.size()
            if (r3 != 0) goto L_0x0027
            goto L_0x02ef
        L_0x0027:
            java.util.List r3 = r0.getPathSegments()
            java.lang.Object r3 = r3.get(r2)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "&"
            java.lang.String[] r3 = r3.split(r4)
            r3 = r3[r2]
            if (r3 == 0) goto L_0x02ee
            int r4 = r3.length()
            if (r4 > 0) goto L_0x0043
            goto L_0x02ee
        L_0x0043:
            com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            java.lang.String r4 = "drive"
            boolean r1 = android.text.TextUtils.equals(r1, r4)
            if (r1 == 0) goto L_0x02ed
            java.lang.String r1 = "param_no_need_remove_page"
            r11.getBooleanExtra(r1, r2)
            java.lang.String r1 = "settings"
            boolean r1 = android.text.TextUtils.equals(r3, r1)
            r4 = 1
            if (r1 == 0) goto L_0x009f
            java.lang.String r11 = "type"
            java.lang.String r11 = r0.getQueryParameter(r11)
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.MOTOR
            java.lang.String r0 = r0.getKeyName()
            boolean r0 = android.text.TextUtils.equals(r0, r11)
            if (r0 == 0) goto L_0x0070
            r11 = 4
            goto L_0x008d
        L_0x0070:
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            java.lang.String r0 = r0.getKeyName()
            boolean r0 = android.text.TextUtils.equals(r0, r11)
            if (r0 == 0) goto L_0x007e
            r11 = 3
            goto L_0x008d
        L_0x007e:
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            java.lang.String r0 = r0.getKeyName()
            boolean r11 = android.text.TextUtils.equals(r0, r11)
            if (r11 == 0) goto L_0x008c
            r11 = 1
            goto L_0x008d
        L_0x008c:
            r11 = 2
        L_0x008d:
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r1 = "amap.extra.prefer.from"
            r0.putInt(r1, r11)
            java.lang.String r11 = "amap.drive.action.navigation.prefer"
            r1 = 1000(0x3e8, float:1.401E-42)
            r10.startPageForResult(r11, r0, r1)
            return r4
        L_0x009f:
            java.lang.String r1 = "testNaviMessage"
            boolean r1 = android.text.TextUtils.equals(r3, r1)
            if (r1 == 0) goto L_0x00bb
            boolean r11 = com.autonavi.ae.AEUtil.IS_DEBUG
            if (r11 == 0) goto L_0x00ba
            java.lang.String r11 = "m"
            r0.getQueryParameter(r11)     // Catch:{ Exception -> 0x00b1 }
            goto L_0x00ba
        L_0x00b1:
            r11 = move-exception
            r11.printStackTrace()
            java.lang.String r11 = "参数错误"
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r11)
        L_0x00ba:
            return r4
        L_0x00bb:
            java.lang.String r0 = "navi"
            boolean r0 = android.text.TextUtils.equals(r3, r0)
            r1 = 0
            if (r0 == 0) goto L_0x0140
            java.lang.String r0 = r11.getAction()
            java.lang.String r3 = "com.autonavi.minimap.ACTION"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x013f
            java.lang.String r3 = r11.getDataString()
            java.lang.String r5 = "isFromShortcutNavi"
            boolean r11 = r11.getBooleanExtra(r5, r2)
            boolean r5 = android.text.TextUtils.isEmpty(r3)
            if (r5 != 0) goto L_0x013f
            boolean r5 = r10.a
            if (r5 == 0) goto L_0x0102
            java.lang.String r5 = r10.c
            boolean r5 = r3.equals(r5)
            if (r5 == 0) goto L_0x0102
            long r5 = java.lang.System.currentTimeMillis()
            long r7 = r10.b
            long r5 = r5 - r7
            r7 = 500(0x1f4, double:2.47E-321)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 >= 0) goto L_0x0102
            r10.a = r2
            r10.c = r1
            r0 = 0
            r10.b = r0
            goto L_0x013f
        L_0x0102:
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle
            r1.<init>()
            java.lang.String r2 = "navi_action"
            r1.putString(r2, r0)
            java.lang.String r0 = "navi_form_shortcutnavi_new_scheme"
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r1.putObject(r0, r2)
            java.lang.String r0 = "navi_data"
            r1.putObject(r0, r3)
            java.lang.String r0 = "navi_form_shortcutnavi"
            r1.putBoolean(r0, r11)
            java.lang.Class<com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController> r11 = com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController.class
            java.lang.Object r11 = defpackage.ank.a(r11)
            com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController r11 = (com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController) r11
            if (r11 == 0) goto L_0x012a
            r11.a()
        L_0x012a:
            a()
            r10.a = r4
            long r5 = java.lang.System.currentTimeMillis()
            r10.b = r5
            r10.c = r3
            mn$1 r11 = new mn$1
            r11.<init>(r1)
            defpackage.aho.a(r11)
        L_0x013f:
            return r4
        L_0x0140:
            java.lang.String r0 = "etd"
            boolean r0 = android.text.TextUtils.equals(r3, r0)
            if (r0 == 0) goto L_0x02ed
            android.net.Uri r0 = r11.getData()
            if (r0 != 0) goto L_0x0157
            java.lang.String r11 = "DriveNaviRouter"
            java.lang.String r0 = "handleETDScheme intent.getData is null"
            com.amap.bundle.logs.AMapLog.d(r11, r0)
            goto L_0x02ec
        L_0x0157:
            android.net.Uri r11 = r11.getData()
            java.lang.String r11 = r11.toString()
            java.lang.String r11 = android.net.Uri.decode(r11)
            android.net.Uri r11 = android.net.Uri.parse(r11)
            if (r11 != 0) goto L_0x0172
            java.lang.String r11 = "DriveNaviRouter"
            java.lang.String r0 = "handleETDScheme uri is null"
            com.amap.bundle.logs.AMapLog.d(r11, r0)
            goto L_0x02ec
        L_0x0172:
            java.lang.String r0 = "clearStack"
            java.lang.String r0 = r11.getQueryParameter(r0)
            java.lang.String r3 = "0"
            boolean r0 = r3.equals(r0)
            if (r0 != 0) goto L_0x0183
            a()
        L_0x0183:
            com.autonavi.common.model.POI r0 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            com.autonavi.common.model.POI r3 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            java.lang.String r5 = ""
            java.lang.String r6 = "sourceApplication"
            java.lang.String r6 = r11.getQueryParameter(r6)     // Catch:{ Exception -> 0x0245 }
            java.lang.String r5 = "sname"
            java.lang.String r5 = r11.getQueryParameter(r5)     // Catch:{ Exception -> 0x0243 }
            java.lang.String r7 = "slat"
            java.lang.String r7 = r11.getQueryParameter(r7)     // Catch:{ Exception -> 0x0243 }
            java.lang.String r8 = "slon"
            java.lang.String r8 = r11.getQueryParameter(r8)     // Catch:{ Exception -> 0x0243 }
            if (r5 != 0) goto L_0x01aa
            java.lang.String r5 = ""
            goto L_0x01ae
        L_0x01aa:
            java.lang.String r5 = r5.trim()     // Catch:{ Exception -> 0x0243 }
        L_0x01ae:
            boolean r9 = com.amap.bundle.drivecommon.tools.DriveUtil.isLatLonValid(r7, r8)     // Catch:{ Exception -> 0x0243 }
            if (r9 != 0) goto L_0x01d7
            com.autonavi.common.model.POI r9 = com.amap.bundle.drivecommon.tools.DriveUtil.getMyLocationPoi()     // Catch:{ Exception -> 0x0243 }
            if (r9 == 0) goto L_0x01df
            java.lang.String r5 = r9.getName()     // Catch:{ Exception -> 0x0243 }
            com.autonavi.common.model.GeoPoint r7 = r9.getPoint()     // Catch:{ Exception -> 0x0243 }
            double r7 = r7.getLatitude()     // Catch:{ Exception -> 0x0243 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ Exception -> 0x0243 }
            com.autonavi.common.model.GeoPoint r8 = r9.getPoint()     // Catch:{ Exception -> 0x0243 }
            double r8 = r8.getLongitude()     // Catch:{ Exception -> 0x0243 }
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ Exception -> 0x0243 }
            goto L_0x01df
        L_0x01d7:
            boolean r9 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0243 }
            if (r9 == 0) goto L_0x01df
            java.lang.String r5 = "已选择的位置"
        L_0x01df:
            com.amap.bundle.drivecommon.tools.DriveUtil.setFromPOIParams(r0, r5, r7, r8, r2)     // Catch:{ Exception -> 0x0243 }
            java.lang.String r5 = "dname"
            java.lang.String r5 = r11.getQueryParameter(r5)     // Catch:{ Exception -> 0x0243 }
            java.lang.String r7 = "dlat"
            java.lang.String r7 = r11.getQueryParameter(r7)     // Catch:{ Exception -> 0x0243 }
            java.lang.String r8 = "dlon"
            java.lang.String r11 = r11.getQueryParameter(r8)     // Catch:{ Exception -> 0x0243 }
            if (r5 != 0) goto L_0x01f9
            java.lang.String r5 = ""
            goto L_0x01fd
        L_0x01f9:
            java.lang.String r5 = r5.trim()     // Catch:{ Exception -> 0x0243 }
        L_0x01fd:
            boolean r8 = com.amap.bundle.drivecommon.tools.DriveUtil.isLatLonValid(r7, r11)     // Catch:{ Exception -> 0x0243 }
            if (r8 != 0) goto L_0x0237
            com.autonavi.bundle.routecommon.model.RouteType r11 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ Exception -> 0x0243 }
            java.lang.String r5 = defpackage.dhw.a(r2)     // Catch:{ Exception -> 0x0243 }
            com.autonavi.common.PageBundle r7 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x0243 }
            r7.<init>()     // Catch:{ Exception -> 0x0243 }
            java.lang.String r8 = "bundle_key_route_type"
            r7.putObject(r8, r11)     // Catch:{ Exception -> 0x0243 }
            java.lang.String r11 = "bundle_key_poi_start"
            r7.putObject(r11, r0)     // Catch:{ Exception -> 0x0243 }
            java.lang.String r11 = "bundle_key_method"
            r7.putString(r11, r5)     // Catch:{ Exception -> 0x0243 }
            boolean r11 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x0243 }
            if (r11 != 0) goto L_0x0228
            java.lang.String r11 = "sourceApplication"
            r7.putString(r11, r6)     // Catch:{ Exception -> 0x0243 }
        L_0x0228:
            java.lang.String r11 = "bundle_key_method_flag"
            r7.putInt(r11, r2)     // Catch:{ Exception -> 0x0243 }
            java.lang.String r11 = "bundle_key_from_scheme"
            r7.putBoolean(r11, r4)     // Catch:{ Exception -> 0x0243 }
            com.amap.bundle.drivecommon.tools.DriveUtil.startRoutePage(r7)     // Catch:{ Exception -> 0x0243 }
            goto L_0x02ec
        L_0x0237:
            boolean r8 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0243 }
            if (r8 == 0) goto L_0x023f
            java.lang.String r5 = "已选择的位置"
        L_0x023f:
            com.amap.bundle.drivecommon.tools.DriveUtil.setToPOIParams(r3, r5, r7, r11, r2)     // Catch:{ Exception -> 0x0243 }
            goto L_0x024a
        L_0x0243:
            r11 = move-exception
            goto L_0x0247
        L_0x0245:
            r11 = move-exception
            r6 = r5
        L_0x0247:
            r11.printStackTrace()
        L_0x024a:
            org.json.JSONObject r11 = new org.json.JSONObject
            r11.<init>()
            java.lang.String r5 = "is_need_request"
            r11.put(r5, r4)     // Catch:{ JSONException -> 0x0292 }
            java.lang.String r5 = "start_poi"
            org.json.JSONObject r7 = defpackage.bnx.b(r0)     // Catch:{ JSONException -> 0x0292 }
            r11.put(r5, r7)     // Catch:{ JSONException -> 0x0292 }
            java.lang.String r5 = "end_poi"
            org.json.JSONObject r7 = defpackage.bnx.b(r3)     // Catch:{ JSONException -> 0x0292 }
            r11.put(r5, r7)     // Catch:{ JSONException -> 0x0292 }
            java.lang.String r5 = "aos_url"
            com.amap.bundle.blutils.app.ConfigerHelper r7 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()     // Catch:{ JSONException -> 0x0292 }
            java.lang.String r8 = "drive_aos_url"
            java.lang.String r7 = r7.getKeyValue(r8)     // Catch:{ JSONException -> 0x0292 }
            r11.put(r5, r7)     // Catch:{ JSONException -> 0x0292 }
            java.lang.String r5 = "favId"
            java.lang.String r7 = ""
            r11.put(r5, r7)     // Catch:{ JSONException -> 0x0292 }
            java.lang.String r5 = "isScene"
            r11.put(r5, r2)     // Catch:{ JSONException -> 0x0292 }
            java.lang.String r5 = "routeType"
            com.autonavi.bundle.routecommon.model.RouteType r7 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ JSONException -> 0x0292 }
            int r7 = r7.getValue()     // Catch:{ JSONException -> 0x0292 }
            r11.put(r5, r7)     // Catch:{ JSONException -> 0x0292 }
            java.lang.String r5 = "sourceApplication"
            r11.put(r5, r6)     // Catch:{ JSONException -> 0x0292 }
            goto L_0x0296
        L_0x0292:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0296:
            java.lang.String r11 = r11.toString()
            ta r5 = new ta
            com.autonavi.minimap.drive.route.CalcRouteScene r6 = com.autonavi.minimap.drive.route.CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN
            r5.<init>(r0, r3, r1, r6)
            com.amap.bundle.drivecommon.request.RouteCarParamUrlWrapper r0 = defpackage.ou.a(r2, r5)
            java.lang.String r0 = com.alibaba.fastjson.JSON.toJSONString(r0)
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle     // Catch:{ JSONException -> 0x02e8 }
            r1.<init>()     // Catch:{ JSONException -> 0x02e8 }
            java.lang.String r2 = "url"
            java.lang.String r3 = "path://amap_bundle_drive/src/etd/page/RouteETDPage.page.js"
            r1.putString(r2, r3)     // Catch:{ JSONException -> 0x02e8 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x02e8 }
            r2.<init>()     // Catch:{ JSONException -> 0x02e8 }
            java.lang.String r3 = "points"
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x02e8 }
            r5.<init>(r11)     // Catch:{ JSONException -> 0x02e8 }
            r2.put(r3, r5)     // Catch:{ JSONException -> 0x02e8 }
            java.lang.String r11 = "route_params"
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x02e8 }
            r3.<init>(r0)     // Catch:{ JSONException -> 0x02e8 }
            r2.put(r11, r3)     // Catch:{ JSONException -> 0x02e8 }
            java.lang.String r11 = "is_from_schema"
            r2.put(r11, r4)     // Catch:{ JSONException -> 0x02e8 }
            java.lang.String r11 = "jsData"
            java.lang.String r0 = r2.toString()     // Catch:{ JSONException -> 0x02e8 }
            r1.putString(r11, r0)     // Catch:{ JSONException -> 0x02e8 }
            bid r11 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()     // Catch:{ JSONException -> 0x02e8 }
            if (r11 == 0) goto L_0x02ec
            java.lang.Class<com.amap.bundle.drive.result.driveresult.etd.AjxRouteETDPage> r0 = com.amap.bundle.drive.result.driveresult.etd.AjxRouteETDPage.class
            r11.startPage(r0, r1)     // Catch:{ JSONException -> 0x02e8 }
            goto L_0x02ec
        L_0x02e8:
            r11 = move-exception
            r11.printStackTrace()
        L_0x02ec:
            return r4
        L_0x02ed:
            return r2
        L_0x02ee:
            return r2
        L_0x02ef:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.mn.a(android.content.Intent):boolean");
    }

    private static void a() {
        try {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
            }
            ConfirmDlgLifeCircle.removeAll();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage());
            AMapLog.e("BaseIntentDispatcher", sb.toString());
        }
    }

    private static boolean b() {
        return NetworkParam.getSa() != null && NetworkParam.getSa().equals(Constant.SOURCE_SINA);
    }

    /* JADX WARNING: Removed duplicated region for block: B:74:0x01fb  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x023b  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0252  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean start(defpackage.ese r9) {
        /*
            r8 = this;
            android.net.Uri r0 = r9.a
            android.content.Intent r9 = r9.b
            r1 = 0
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            java.lang.String r2 = r0.getHost()
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 == 0) goto L_0x0013
            return r1
        L_0x0013:
            java.lang.String r3 = "showTraffic"
            boolean r3 = android.text.TextUtils.equals(r3, r2)
            r4 = 0
            r5 = 1
            if (r3 == 0) goto L_0x0169
            a()
            java.lang.String r9 = "voicebroadcast"
            java.lang.String r9 = r0.getQueryParameter(r9)
            boolean r2 = android.text.TextUtils.isEmpty(r9)
            if (r2 != 0) goto L_0x0070
            java.lang.String r2 = "yes"
            boolean r9 = r2.equalsIgnoreCase(r9)
            if (r9 == 0) goto L_0x0060
            bim r9 = defpackage.bim.aa()
            java.lang.String r2 = "207"
            r9.b(r2, r5)
            com.amap.bundle.drive.navi.naviwrapper.NaviManager r9 = com.amap.bundle.drive.navi.naviwrapper.NaviManager.a()
            r9.a(r5)
            boolean r9 = b()
            if (r9 == 0) goto L_0x0070
            com.iflytek.tts.TtsService.TtsManager r9 = com.iflytek.tts.TtsService.TtsManager.getInstance()
            android.content.Context r2 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getContext()
            android.app.Application r3 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r6 = com.autonavi.minimap.R.string.tts_start_broadcast_traffic
            java.lang.String r3 = r3.getString(r6)
            r9.TTS_Txt_Later(r2, r3)
            goto L_0x0070
        L_0x0060:
            bim r9 = defpackage.bim.aa()
            java.lang.String r2 = "207"
            r9.b(r2, r1)
            com.amap.bundle.drive.navi.naviwrapper.NaviManager r9 = com.amap.bundle.drive.navi.naviwrapper.NaviManager.a()
            r9.a(r1)
        L_0x0070:
            com.autonavi.common.PageBundle r9 = new com.autonavi.common.PageBundle
            r9.<init>()
            r2 = 17
            java.lang.String r3 = "level"
            java.lang.String r3 = r0.getQueryParameter(r3)
            if (r3 == 0) goto L_0x0091
            java.lang.String r6 = ""
            boolean r3 = r3.equals(r6)
            if (r3 != 0) goto L_0x0091
            java.lang.String r2 = "level"
            java.lang.String r2 = r0.getQueryParameter(r2)
            int r2 = java.lang.Integer.parseInt(r2)
        L_0x0091:
            java.lang.String r3 = "level"
            r9.putInt(r3, r2)
            java.lang.String r3 = "lat"
            java.lang.String r3 = r0.getQueryParameter(r3)
            java.lang.String r6 = "lon"
            java.lang.String r6 = r0.getQueryParameter(r6)
            boolean r7 = android.text.TextUtils.isEmpty(r3)
            if (r7 != 0) goto L_0x011e
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 != 0) goto L_0x011e
            com.autonavi.common.model.POI r1 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            java.lang.Double r3 = java.lang.Double.valueOf(r3)
            double r3 = r3.doubleValue()
            java.lang.Double r6 = java.lang.Double.valueOf(r6)
            double r6 = r6.doubleValue()
            android.graphics.Point r3 = defpackage.cfg.a(r3, r6)
            java.lang.String r4 = "dev"
            java.lang.String r4 = r0.getQueryParameter(r4)
            int r4 = java.lang.Integer.parseInt(r4)
            if (r4 != r5) goto L_0x00db
            int r4 = r3.x
            int r3 = r3.y
            com.autonavi.common.model.GeoPoint r3 = defpackage.cff.a(r4, r3)
            goto L_0x00e5
        L_0x00db:
            com.autonavi.common.model.GeoPoint r4 = new com.autonavi.common.model.GeoPoint
            int r6 = r3.x
            int r3 = r3.y
            r4.<init>(r6, r3)
            r3 = r4
        L_0x00e5:
            java.lang.String r4 = "poiid"
            java.lang.String r4 = r0.getQueryParameter(r4)
            r1.setId(r4)
            java.lang.String r4 = "poiname"
            java.lang.String r4 = r0.getQueryParameter(r4)
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x0104
            java.lang.String r4 = "poiname"
            java.lang.String r0 = r0.getQueryParameter(r4)
            r1.setName(r0)
            goto L_0x0111
        L_0x0104:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r4 = com.autonavi.minimap.R.string.map_center_point
            java.lang.String r0 = r0.getString(r4)
            r1.setName(r0)
        L_0x0111:
            r1.setPoint(r3)
            com.autonavi.common.model.POI r4 = r1.clone()
            java.lang.String r0 = "POI"
            r9.putObject(r0, r1)
            r1 = 1
        L_0x011e:
            boolean r0 = b()
            if (r0 == 0) goto L_0x0132
            if (r1 == 0) goto L_0x0132
            bid r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r0 == 0) goto L_0x0168
            java.lang.String r1 = "amap.basemap.action.acticities"
            r0.startPage(r1, r9)
            goto L_0x0168
        L_0x0132:
            com.autonavi.common.PageBundle r9 = new com.autonavi.common.PageBundle
            r9.<init>()
            java.lang.String r0 = "key_action"
            java.lang.String r3 = "action_base_map_scheme"
            r9.putString(r0, r3)
            java.lang.String r0 = "key_scheme_feature"
            com.autonavi.bundle.amaphome.api.MapHomeIntentAction r3 = com.autonavi.bundle.amaphome.api.MapHomeIntentAction.OPEN_TRAFFIC_CONDITION
            r9.putObject(r0, r3)
            java.lang.String r0 = "level"
            r9.putInt(r0, r2)
            if (r1 == 0) goto L_0x0151
            java.lang.String r0 = "POI"
            r9.putObject(r0, r4)
        L_0x0151:
            bid r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r0 == 0) goto L_0x0168
            esb r1 = defpackage.esb.a.a
            java.lang.Class<apr> r2 = defpackage.apr.class
            esc r1 = r1.a(r2)
            apr r1 = (defpackage.apr) r1
            if (r1 == 0) goto L_0x0168
            r1.a(r0, r9)
        L_0x0168:
            return r5
        L_0x0169:
            java.lang.String r3 = "navi"
            boolean r3 = android.text.TextUtils.equals(r3, r2)
            if (r3 == 0) goto L_0x020b
            android.app.Activity r9 = com.autonavi.amap.app.AMapAppGlobal.getTopActivity()
            if (r9 == 0) goto L_0x020a
            java.lang.String r1 = "lat"
            java.lang.String r1 = r0.getQueryParameter(r1)
            java.lang.String r2 = "lon"
            java.lang.String r2 = r0.getQueryParameter(r2)
            boolean r1 = com.amap.bundle.drivecommon.tools.DriveUtil.isLatLonValid(r1, r2)
            if (r1 != 0) goto L_0x0198
            android.app.Application r9 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r0 = com.autonavi.minimap.R.string.drive_route_end_invalid
            java.lang.String r9 = r9.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r9)
            goto L_0x020a
        L_0x0198:
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle
            r1.<init>()
            java.lang.String r2 = "navi_uri"
            r1.putObject(r2, r0)
            if (r0 == 0) goto L_0x01f8
            java.lang.String r2 = "backScheme"
            java.lang.String r2 = r0.getQueryParameter(r2)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x01b5
            android.net.Uri r2 = android.net.Uri.parse(r2)
            goto L_0x01b6
        L_0x01b5:
            r2 = r4
        L_0x01b6:
            java.lang.String r3 = "sourceApplication"
            java.lang.String r3 = r0.getQueryParameter(r3)
            java.lang.String r6 = "backCategory"
            java.lang.String r6 = r0.getQueryParameter(r6)
            java.lang.String r7 = "backAction"
            java.lang.String r0 = r0.getQueryParameter(r7)
            boolean r7 = android.text.TextUtils.isEmpty(r3)
            if (r7 == 0) goto L_0x01d8
            android.app.Application r3 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r7 = com.autonavi.minimap.R.string.third_part
            java.lang.String r3 = r3.getString(r7)
        L_0x01d8:
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 == 0) goto L_0x01e0
            java.lang.String r6 = "android.intent.category.DEFAULT"
        L_0x01e0:
            if (r2 == 0) goto L_0x01f8
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 != 0) goto L_0x01f8
            dlg r7 = new dlg
            r7.<init>()
            r7.a = r5
            r7.b = r2
            r7.c = r3
            r7.d = r6
            r7.e = r0
            goto L_0x01f9
        L_0x01f8:
            r7 = r4
        L_0x01f9:
            if (r7 == 0) goto L_0x0207
            java.lang.String r0 = "key_action"
            java.lang.String r2 = "actiono_back_scheme"
            r1.putObject(r0, r2)
            java.lang.String r0 = "key_back_scheme_param"
            r1.putObject(r0, r7)
        L_0x0207:
            defpackage.nm.a(r9, r1, r4, r4)
        L_0x020a:
            return r5
        L_0x020b:
            java.lang.String r3 = "navi2SpecialDest"
            boolean r3 = android.text.TextUtils.equals(r3, r2)
            if (r3 == 0) goto L_0x02a1
            java.lang.String r9 = "dest"
            java.lang.String r9 = r0.getQueryParameter(r9)
            if (r9 == 0) goto L_0x02a0
            java.lang.String r0 = "home"
            boolean r0 = r9.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x0229
            com.autonavi.common.model.POI r9 = com.amap.bundle.drivecommon.tools.DriveUtil.getPOIHome()
        L_0x0227:
            r0 = 0
            goto L_0x0239
        L_0x0229:
            java.lang.String r0 = "corp"
            boolean r9 = r9.equalsIgnoreCase(r0)
            if (r9 == 0) goto L_0x0237
            com.autonavi.common.model.POI r9 = com.amap.bundle.drivecommon.tools.DriveUtil.getPOICompany()
            r0 = 1
            goto L_0x0239
        L_0x0237:
            r9 = r4
            goto L_0x0227
        L_0x0239:
            if (r9 == 0) goto L_0x0252
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r2 = "EndPOI"
            r0.putObject(r2, r9)
            java.lang.String r2 = "IsSimNavi"
            r0.putBoolean(r2, r1)
            android.app.Activity r1 = com.autonavi.amap.app.AMapAppGlobal.getTopActivity()
            defpackage.nm.a(r1, r0, r4, r9)
            goto L_0x02a0
        L_0x0252:
            com.autonavi.common.PageBundle r9 = new com.autonavi.common.PageBundle
            r9.<init>()
            java.lang.String r1 = "openMinePage"
            r9.putBoolean(r1, r5)
            bid r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r1 == 0) goto L_0x0267
            java.lang.String r2 = "amap.basemap.action.favorite_page"
            r1.startPage(r2, r9)
        L_0x0267:
            if (r0 == 0) goto L_0x0285
            boolean r9 = b()
            if (r9 == 0) goto L_0x02a0
            com.iflytek.tts.TtsService.TtsManager r9 = com.iflytek.tts.TtsService.TtsManager.getInstance()
            android.content.Context r0 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getContext()
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r2 = com.autonavi.minimap.R.string.tts_broadcast_work_not_set
            java.lang.String r1 = r1.getString(r2)
            r9.TTS_Txt_Later(r0, r1)
            goto L_0x02a0
        L_0x0285:
            boolean r9 = b()
            if (r9 == 0) goto L_0x02a0
            com.iflytek.tts.TtsService.TtsManager r9 = com.iflytek.tts.TtsService.TtsManager.getInstance()
            android.content.Context r0 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getContext()
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r2 = com.autonavi.minimap.R.string.tts_broadcast_home_not_set
            java.lang.String r1 = r1.getString(r2)
            r9.TTS_Txt_Later(r0, r1)
        L_0x02a0:
            return r5
        L_0x02a1:
            java.lang.String r3 = "keywordNavi"
            boolean r3 = android.text.TextUtils.equals(r3, r2)
            if (r3 == 0) goto L_0x02f8
            java.lang.String r9 = "keyword"
            java.lang.String r9 = r0.getQueryParameter(r9)
            java.lang.String r2 = "style"
            java.lang.String r0 = r0.getQueryParameter(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x02f7
            boolean r2 = android.text.TextUtils.isEmpty(r9)
            if (r2 == 0) goto L_0x02c2
            goto L_0x02f7
        L_0x02c2:
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x02c7 }
            goto L_0x02cc
        L_0x02c7:
            r0 = move-exception
            defpackage.kf.a(r0)
            r0 = 0
        L_0x02cc:
            java.lang.String r0 = defpackage.dhw.a(r0)
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle
            r1.<init>()
            esb r2 = defpackage.esb.a.a
            java.lang.Class<bax> r3 = defpackage.bax.class
            esc r2 = r2.a(r3)
            bax r2 = (defpackage.bax) r2
            if (r2 == 0) goto L_0x02f7
            java.lang.String r3 = "bundle_key_route_type"
            com.autonavi.bundle.routecommon.model.RouteType r4 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            r1.putObject(r3, r4)
            java.lang.String r3 = "bundle_key_keyword"
            r1.putObject(r3, r9)
            java.lang.String r9 = "bundle_key_method"
            r1.putObject(r9, r0)
            r2.a(r1)
        L_0x02f7:
            return r5
        L_0x02f8:
            java.lang.String r0 = "openFeature"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0370
            android.net.Uri r9 = r9.getData()
            java.lang.String r0 = "featureName"
            java.lang.String r0 = r9.getQueryParameter(r0)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 == 0) goto L_0x031e
            android.app.Application r9 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r0 = com.autonavi.minimap.R.string.intent_open_fail_param_error
            java.lang.String r9 = r9.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r9)
            goto L_0x036e
        L_0x031e:
            java.lang.String r2 = "DriveIntentDispatcher"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "doOpenFeature uri "
            r3.<init>(r4)
            java.lang.String r9 = r9.toString()
            r3.append(r9)
            java.lang.String r9 = " , params "
            r3.append(r9)
            r3.append(r0)
            java.lang.String r9 = r3.toString()
            com.amap.bundle.logs.AMapLog.d(r2, r9)
            java.lang.String r9 = "DirectNavigation"
            boolean r9 = r0.equalsIgnoreCase(r9)
            if (r9 == 0) goto L_0x0363
            com.autonavi.common.PageBundle r9 = new com.autonavi.common.PageBundle
            r9.<init>()
            esb r0 = defpackage.esb.a.a
            java.lang.Class<bax> r1 = defpackage.bax.class
            esc r0 = r0.a(r1)
            bax r0 = (defpackage.bax) r0
            if (r0 == 0) goto L_0x036e
            java.lang.String r1 = "bundle_key_route_type"
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            r9.putObject(r1, r2)
            r0.a(r9)
            goto L_0x036e
        L_0x0363:
            java.lang.String r9 = "openTrafficEdog"
            boolean r9 = r0.equalsIgnoreCase(r9)
            if (r9 == 0) goto L_0x036f
            defpackage.nm.a()
        L_0x036e:
            return r5
        L_0x036f:
            return r1
        L_0x0370:
            java.lang.String r0 = "drive"
            boolean r0 = android.text.TextUtils.equals(r2, r0)
            if (r0 == 0) goto L_0x037d
            boolean r9 = r8.a(r9)
            return r9
        L_0x037d:
            java.lang.String r0 = "edog"
            boolean r0 = android.text.TextUtils.equals(r2, r0)
            if (r0 == 0) goto L_0x03a0
            android.net.Uri r9 = r9.getData()
            java.util.List r9 = r9.getPathSegments()
            java.lang.Object r9 = r9.get(r1)
            java.lang.String r9 = (java.lang.String) r9
            java.lang.String r0 = "home"
            boolean r9 = android.text.TextUtils.equals(r0, r9)
            if (r9 == 0) goto L_0x039f
            defpackage.nm.a()
            return r5
        L_0x039f:
            return r1
        L_0x03a0:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.mn.start(ese):boolean");
    }
}
