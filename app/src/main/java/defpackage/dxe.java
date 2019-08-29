package defpackage;

import android.app.Activity;
import android.text.TextUtils;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.IBusErrorReportRemind;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dxe reason: default package */
/* compiled from: FeedbackAccessManager */
final class dxe extends dxc<dwz> {
    dxe() {
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void b(final a aVar, Object obj) {
        final dwz dwz = (dwz) obj;
        super.b(aVar, dwz);
        String string = AMapAppGlobal.getApplication().getString(R.string.action_log_type_bus);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", "B022", jSONObject);
        IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
        if (iErrorReportStarter != null) {
            iErrorReportStarter.doReportError(aVar.getMapManager(), new coi() {
                /* JADX WARNING: Removed duplicated region for block: B:10:0x0023  */
                /* JADX WARNING: Removed duplicated region for block: B:62:0x0215  */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void doReportError(java.lang.String r14) {
                    /*
                        r13 = this;
                        java.lang.Class<com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter> r0 = com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter.class
                        java.lang.Object r0 = defpackage.ank.a(r0)
                        com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter r0 = (com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter) r0
                        if (r0 == 0) goto L_0x022a
                        dwz r1 = r5
                        com.autonavi.bundle.routecommon.entity.IBusRouteResult r2 = r1.a
                        r3 = 0
                        if (r2 == 0) goto L_0x001c
                        com.autonavi.bundle.routecommon.entity.IBusRouteResult r1 = r1.a
                        com.autonavi.bundle.routecommon.entity.BusPath r1 = r1.getFocusBusPath()
                        if (r1 == 0) goto L_0x001c
                        boolean r1 = r1.isRidePath
                        goto L_0x001d
                    L_0x001c:
                        r1 = 0
                    L_0x001d:
                        dwz r2 = r5
                        boolean r2 = r2.c
                        if (r2 == 0) goto L_0x0215
                        dws$a r2 = r4
                        android.content.Context r2 = r2.getContext()
                        dwz r4 = r5
                        com.autonavi.bundle.routecommon.entity.IBusRouteResult r4 = r4.a
                        dwz r5 = r5
                        java.lang.String r5 = defpackage.dxe.a(r5)
                        r6 = 0
                        if (r4 != 0) goto L_0x0038
                        goto L_0x0211
                    L_0x0038:
                        com.autonavi.common.PageBundle r7 = new com.autonavi.common.PageBundle
                        r7.<init>()
                        java.lang.String r8 = "has_ride_for_bus_route"
                        r7.putBoolean(r8, r1)
                        java.lang.String r1 = "page_action"
                        java.lang.String r8 = "com.basemap.action.feedback_entry_list"
                        r7.putString(r1, r8)
                        java.lang.String r1 = "page_id"
                        r8 = 8
                        r7.putInt(r1, r8)
                        java.lang.String r1 = "sourcepage"
                        r8 = 3
                        r7.putInt(r1, r8)
                        com.autonavi.sdk.location.LocationInstrument r1 = com.autonavi.sdk.location.LocationInstrument.getInstance()
                        java.lang.String r1 = r1.getLocationLog(r2)
                        java.lang.String r2 = "location_log"
                        r7.putString(r2, r1)
                        java.lang.String r1 = "error_pic_path"
                        r7.putString(r1, r14)
                        java.lang.String r14 = "bsid"
                        java.lang.String r1 = r4.getBsid()
                        r7.putString(r14, r1)
                        com.autonavi.common.model.POI r14 = r4.getFromPOI()
                        com.autonavi.common.model.POI r1 = r4.getToPOI()
                        if (r14 == 0) goto L_0x00a2
                        com.autonavi.common.model.POI r14 = r14.clone()
                        android.content.Context r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
                        int r8 = com.autonavi.minimap.R.string.bus_my_location
                        java.lang.String r2 = r2.getString(r8)
                        java.lang.String r8 = r14.getName()
                        boolean r2 = android.text.TextUtils.equals(r2, r8)
                        if (r2 == 0) goto L_0x00a2
                        com.autonavi.bundle.routecommon.entity.BusPaths r2 = r4.getBusPathsResult()
                        if (r2 == 0) goto L_0x00a2
                        com.autonavi.bundle.routecommon.entity.BusPaths r2 = r4.getBusPathsResult()
                        java.lang.String r2 = r2.mStartDes
                        r14.setName(r2)
                    L_0x00a2:
                        if (r1 == 0) goto L_0x00cb
                        com.autonavi.common.model.POI r1 = r1.clone()
                        android.content.Context r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
                        int r8 = com.autonavi.minimap.R.string.bus_my_location
                        java.lang.String r2 = r2.getString(r8)
                        java.lang.String r8 = r1.getName()
                        boolean r2 = android.text.TextUtils.equals(r2, r8)
                        if (r2 == 0) goto L_0x00cb
                        com.autonavi.bundle.routecommon.entity.BusPaths r2 = r4.getBusPathsResult()
                        if (r2 == 0) goto L_0x00cb
                        com.autonavi.bundle.routecommon.entity.BusPaths r2 = r4.getBusPathsResult()
                        java.lang.String r2 = r2.mEndDes
                        r1.setName(r2)
                    L_0x00cb:
                        java.lang.String r2 = "startpoint"
                        r7.putObject(r2, r14)
                        java.lang.String r2 = "endpoint"
                        r7.putObject(r2, r1)
                        java.lang.String r2 = "category"
                        java.lang.String r8 = r4.getMethod()
                        r7.putString(r2, r8)
                        com.autonavi.common.model.POI r2 = r4.getToPOI()
                        if (r2 == 0) goto L_0x011b
                        java.lang.String r2 = "poiid"
                        com.autonavi.common.model.POI r8 = r4.getToPOI()
                        java.lang.String r8 = r8.getId()
                        r7.putString(r2, r8)
                        java.lang.String r2 = "Ad2"
                        li r8 = defpackage.li.a()
                        com.autonavi.common.model.POI r9 = r4.getToPOI()
                        com.autonavi.common.model.GeoPoint r9 = r9.getPoint()
                        double r9 = r9.getLongitude()
                        com.autonavi.common.model.POI r11 = r4.getToPOI()
                        com.autonavi.common.model.GeoPoint r11 = r11.getPoint()
                        double r11 = r11.getLatitude()
                        int r8 = r8.a(r9, r11)
                        java.lang.String r8 = java.lang.String.valueOf(r8)
                        r7.putString(r2, r8)
                    L_0x011b:
                        com.autonavi.common.model.POI r2 = r4.getFromPOI()
                        if (r2 == 0) goto L_0x014a
                        java.lang.String r2 = "Ad1"
                        li r8 = defpackage.li.a()
                        com.autonavi.common.model.POI r9 = r4.getFromPOI()
                        com.autonavi.common.model.GeoPoint r9 = r9.getPoint()
                        double r9 = r9.getLongitude()
                        com.autonavi.common.model.POI r11 = r4.getFromPOI()
                        com.autonavi.common.model.GeoPoint r11 = r11.getPoint()
                        double r11 = r11.getLatitude()
                        int r8 = r8.a(r9, r11)
                        java.lang.String r8 = java.lang.String.valueOf(r8)
                        r7.putString(r2, r8)
                    L_0x014a:
                        com.autonavi.bundle.routecommon.entity.BusPaths r2 = r4.getBusPathsResult()
                        com.autonavi.bundle.routecommon.entity.BusPath[] r2 = r2.mBusPaths
                        if (r2 == 0) goto L_0x0211
                        int r8 = r2.length
                        if (r8 > 0) goto L_0x0157
                        goto L_0x0211
                    L_0x0157:
                        int r8 = r2.length
                        int r9 = r4.getFocusBusPathIndex()
                        if (r8 > r9) goto L_0x0160
                        goto L_0x0211
                    L_0x0160:
                        int r6 = r4.getFocusBusPathIndex()
                        r2 = r2[r6]
                        if (r14 == 0) goto L_0x017c
                        com.autonavi.bundle.routecommon.entity.BusPath$BusDisplayObj r6 = r2.mStartObj
                        if (r6 == 0) goto L_0x017c
                        com.autonavi.common.model.GeoPoint r6 = new com.autonavi.common.model.GeoPoint
                        com.autonavi.bundle.routecommon.entity.BusPath$BusDisplayObj r8 = r2.mStartObj
                        int r8 = r8.mDisX
                        com.autonavi.bundle.routecommon.entity.BusPath$BusDisplayObj r9 = r2.mStartObj
                        int r9 = r9.mDisY
                        r6.<init>(r8, r9)
                        r14.setPoint(r6)
                    L_0x017c:
                        if (r1 == 0) goto L_0x0192
                        com.autonavi.bundle.routecommon.entity.BusPath$BusDisplayObj r14 = r2.mEndObj
                        if (r14 == 0) goto L_0x0192
                        com.autonavi.common.model.GeoPoint r14 = new com.autonavi.common.model.GeoPoint
                        com.autonavi.bundle.routecommon.entity.BusPath$BusDisplayObj r6 = r2.mEndObj
                        int r6 = r6.mDisX
                        com.autonavi.bundle.routecommon.entity.BusPath$BusDisplayObj r8 = r2.mEndObj
                        int r8 = r8.mDisY
                        r14.<init>(r6, r8)
                        r1.setPoint(r14)
                    L_0x0192:
                        java.lang.StringBuilder r14 = new java.lang.StringBuilder
                        r14.<init>()
                        r1 = 0
                    L_0x0198:
                        com.autonavi.bundle.routecommon.entity.BusPathSection[] r6 = r2.mPathSections
                        int r6 = r6.length
                        if (r1 >= r6) goto L_0x01dd
                        int r6 = r14.length()
                        if (r6 <= 0) goto L_0x01a8
                        java.lang.String r6 = " -> "
                        r14.append(r6)
                    L_0x01a8:
                        com.autonavi.bundle.routecommon.entity.BusPathSection[] r6 = r2.mPathSections
                        r6 = r6[r1]
                        java.lang.String r6 = r6.mSectionName
                        java.lang.String r8 = "("
                        int r6 = r6.lastIndexOf(r8)
                        if (r6 > 0) goto L_0x01c3
                        com.autonavi.bundle.routecommon.entity.BusPathSection[] r6 = r2.mPathSections
                        r6 = r6[r1]
                        java.lang.String r6 = r6.mSectionName
                        java.lang.String r8 = "（"
                        int r6 = r6.lastIndexOf(r8)
                    L_0x01c3:
                        if (r6 <= 0) goto L_0x01d3
                        com.autonavi.bundle.routecommon.entity.BusPathSection[] r8 = r2.mPathSections
                        r8 = r8[r1]
                        java.lang.String r8 = r8.mSectionName
                        java.lang.String r6 = r8.substring(r3, r6)
                        r14.append(r6)
                        goto L_0x01da
                    L_0x01d3:
                        com.autonavi.bundle.routecommon.entity.BusPathSection[] r6 = r2.mPathSections
                        r6 = r6[r1]
                        r14.append(r6)
                    L_0x01da:
                        int r1 = r1 + 1
                        goto L_0x0198
                    L_0x01dd:
                        java.lang.String r1 = "name"
                        java.lang.String r14 = r14.toString()
                        r7.putString(r1, r14)
                        java.lang.String r14 = "bus_path"
                        r7.putObject(r14, r2)
                        java.lang.String r14 = "foot_path"
                        java.util.ArrayList r1 = r4.getBusResultFootErrorData()
                        r7.putObject(r14, r1)
                        java.lang.String r14 = "bundle_key_boolean_default"
                        r7.putBoolean(r14, r3)
                        java.lang.String r14 = "zoom_level"
                        r1 = 15
                        r7.putInt(r14, r1)
                        java.lang.String r14 = "overlay_style"
                        dxi$a r1 = new dxi$a
                        r1.<init>(r4)
                        r7.putObject(r14, r1)
                        java.lang.String r14 = "realtime_bus_param"
                        r7.putString(r14, r5)
                        r6 = r7
                    L_0x0211:
                        r0.startFeedback(r6)
                        return
                    L_0x0215:
                        android.content.Context r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
                        dwz r3 = r5
                        com.autonavi.bundle.routecommon.entity.IBusRouteResult r3 = r3.a
                        dwz r4 = r5
                        java.lang.String r4 = defpackage.dxe.a(r4)
                        com.autonavi.common.PageBundle r14 = defpackage.dxi.a(r2, r3, r14, r1, r4)
                        r0.startFeedback(r14)
                    L_0x022a:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: defpackage.dxe.AnonymousClass1.doReportError(java.lang.String):void");
                }
            });
        }
    }

    static void a(Activity activity, boolean z) {
        if (activity != null) {
            IBusErrorReportRemind iBusErrorReportRemind = (IBusErrorReportRemind) ank.a(IBusErrorReportRemind.class);
            if (iBusErrorReportRemind != null) {
                if (z) {
                    iBusErrorReportRemind.handlePageOnResume(activity, 1);
                } else {
                    iBusErrorReportRemind.handleResume(activity);
                }
            }
        }
    }

    static String a(dwz dwz) {
        if (TextUtils.isEmpty(dwz.b) || dwz.a == null) {
            return null;
        }
        int focusBusPathIndex = dwz.a.getFocusBusPathIndex();
        try {
            JSONArray jSONArray = new JSONObject(dwz.b).getJSONArray("buslist");
            if (jSONArray != null) {
                String jSONObject = jSONArray.getJSONObject(focusBusPathIndex).toString();
                eao.b("song---", "getSelectOriginPathData = ".concat(String.valueOf(jSONObject)));
                return jSONObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
