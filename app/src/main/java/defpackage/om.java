package defpackage;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.drive.offline.OfflineNaviQueryMgr.EnumNaviResponseType;
import com.amap.bundle.drive.result.driveresult.net.RouteCarCompanyParamUrlWrapper;
import com.amap.bundle.drive.result.driveresult.net.RouteCarHomeParamUrlWrapper;
import com.amap.bundle.drive.result.driveresult.net.RouteCarRequstCallBack;
import com.amap.bundle.drive.result.driveresult.net.RouteCarRequstTmcCallBack;
import com.amap.bundle.drive.result.driveresult.net.RouteCarTmcParamUrlWrapper;
import com.amap.bundle.drivecommon.request.RouteCarParamUrlWrapper;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.network.request.param.builder.URLBuilderFactory;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.route.CalcRouteScene;

/* renamed from: om reason: default package */
/* compiled from: DriveRouteManager */
public final class om {

    /* renamed from: om$3 reason: invalid class name */
    /* compiled from: DriveRouteManager */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] a = new int[EnumNaviResponseType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|(3:11|12|14)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.amap.bundle.drive.offline.OfflineNaviQueryMgr$EnumNaviResponseType[] r0 = com.amap.bundle.drive.offline.OfflineNaviQueryMgr.EnumNaviResponseType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.amap.bundle.drive.offline.OfflineNaviQueryMgr$EnumNaviResponseType r1 = com.amap.bundle.drive.offline.OfflineNaviQueryMgr.EnumNaviResponseType.SUCCESS     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.amap.bundle.drive.offline.OfflineNaviQueryMgr$EnumNaviResponseType r1 = com.amap.bundle.drive.offline.OfflineNaviQueryMgr.EnumNaviResponseType.FAIL     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.amap.bundle.drive.offline.OfflineNaviQueryMgr$EnumNaviResponseType r1 = com.amap.bundle.drive.offline.OfflineNaviQueryMgr.EnumNaviResponseType.NEEDREBOOT     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.amap.bundle.drive.offline.OfflineNaviQueryMgr$EnumNaviResponseType r1 = com.amap.bundle.drive.offline.OfflineNaviQueryMgr.EnumNaviResponseType.EXISTDATA     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.amap.bundle.drive.offline.OfflineNaviQueryMgr$EnumNaviResponseType r1 = com.amap.bundle.drive.offline.OfflineNaviQueryMgr.EnumNaviResponseType.NOENGINE     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x004b }
                com.amap.bundle.drive.offline.OfflineNaviQueryMgr$EnumNaviResponseType r1 = com.amap.bundle.drive.offline.OfflineNaviQueryMgr.EnumNaviResponseType.NODATA     // Catch:{ NoSuchFieldError -> 0x004b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.om.AnonymousClass3.<clinit>():void");
        }
    }

    private static int a(int i) {
        if (i == 1) {
            return 1;
        }
        return i == 11 ? 2 : 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x01d3  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01d6  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x01e9  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x01f3  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0244  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0247  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x026e  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0277  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.autonavi.common.Callback.a a(defpackage.ta r20, defpackage.axa r21) {
        /*
            r0 = r20
            r1 = r21
            boolean r2 = r0.v
            r3 = 1
            if (r2 == 0) goto L_0x028b
            com.autonavi.minimap.drive.route.CalcRouteScene r2 = r0.A
            com.autonavi.common.model.POI r4 = r0.a
            com.autonavi.common.model.POI r5 = r0.b
            java.util.List<com.autonavi.common.model.POI> r6 = r0.c
            r7 = 0
            if (r4 == 0) goto L_0x0289
            if (r5 == 0) goto L_0x0289
            com.amap.bundle.drive.offline.OfflineNaviQueryMgr r8 = new com.amap.bundle.drive.offline.OfflineNaviQueryMgr
            android.app.Application r9 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            r8.<init>(r9)
            com.amap.bundle.drivecommon.model.RouteCarResultData r9 = new com.amap.bundle.drivecommon.model.RouteCarResultData
            com.autonavi.minimap.drive.route.CalcRouteScene r10 = r0.A
            r9.<init>(r10)
            r9.setFromPOI(r4)
            r9.setToPOI(r5)
            java.util.ArrayList r4 = defpackage.agj.a(r6)
            r9.setMidPOIs(r4)
            java.lang.String r4 = com.amap.bundle.drivecommon.tools.DriveUtil.getLastRoutingChoice()
            r9.setMethod(r4)
            om$1 r4 = new om$1
            r4.<init>(r0, r1)
            com.autonavi.common.model.POI r0 = r9.getFromPOI()
            r1 = -98
            r5 = 0
            if (r0 == 0) goto L_0x0065
            com.autonavi.common.model.POI r0 = r9.getToPOI()
            if (r0 != 0) goto L_0x004f
            goto L_0x0065
        L_0x004f:
            com.amap.bundle.drive.navi.naviwrapper.NaviManager r0 = com.amap.bundle.drive.navi.naviwrapper.NaviManager.a()
            boolean r0 = r0.c()
            if (r0 == 0) goto L_0x005b
            r0 = 1
            goto L_0x006f
        L_0x005b:
            boolean r0 = r8.b
            if (r0 != 0) goto L_0x006e
            com.amap.bundle.drive.offline.OfflineNaviQueryMgr$EnumNaviResponseType r0 = com.amap.bundle.drive.offline.OfflineNaviQueryMgr.EnumNaviResponseType.FROM_TO_ERROR
            r4.a(r0, r7, r1)
            goto L_0x006e
        L_0x0065:
            boolean r0 = r8.b
            if (r0 != 0) goto L_0x006e
            com.amap.bundle.drive.offline.OfflineNaviQueryMgr$EnumNaviResponseType r0 = com.amap.bundle.drive.offline.OfflineNaviQueryMgr.EnumNaviResponseType.FROM_TO_ERROR
            r4.a(r0, r7, r1)
        L_0x006e:
            r0 = 0
        L_0x006f:
            if (r0 == 0) goto L_0x0289
            android.app.Activity r0 = com.autonavi.amap.app.AMapAppGlobal.getTopActivity()
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r6 = com.autonavi.minimap.R.string.progress_message
            java.lang.String r1 = r1.getString(r6)
            if (r0 == 0) goto L_0x00bc
            boolean r6 = android.text.TextUtils.isEmpty(r1)
            if (r6 != 0) goto L_0x00bc
            boolean r6 = r0.isFinishing()
            if (r6 == 0) goto L_0x008e
            goto L_0x00bc
        L_0x008e:
            com.autonavi.map.widget.ProgressDlg r6 = r8.c
            if (r6 != 0) goto L_0x00af
            com.autonavi.map.widget.ProgressDlg r6 = new com.autonavi.map.widget.ProgressDlg
            r6.<init>(r0, r1)
            r8.c = r6
            com.autonavi.map.widget.ProgressDlg r0 = r8.c
            r0.setCancelable(r3)
            com.autonavi.map.widget.ProgressDlg r0 = r8.c
            com.amap.bundle.drive.offline.OfflineNaviQueryMgr$2 r1 = new com.amap.bundle.drive.offline.OfflineNaviQueryMgr$2
            r1.<init>()
            r0.setOnCancelListener(r1)
            com.autonavi.map.widget.ProgressDlg r0 = r8.c
            android.content.DialogInterface$OnDismissListener r1 = r8.d
            r0.setOnDismissListener(r1)
        L_0x00af:
            com.autonavi.map.widget.ProgressDlg r0 = r8.c
            boolean r0 = r0.isShowing()
            if (r0 != 0) goto L_0x00bc
            com.autonavi.map.widget.ProgressDlg r0 = r8.c
            r0.show()
        L_0x00bc:
            java.lang.String r0 = "0"
            java.lang.String r0 = defpackage.ru.a(r0)
            int r1 = defpackage.dhw.d(r0)
            int r0 = defpackage.dhw.c(r0)
            com.amap.bundle.drive.offline.OfflineNaviQueryMgr$1 r3 = new com.amap.bundle.drive.offline.OfflineNaviQueryMgr$1
            r3.<init>(r4, r9, r2)
            r8.a = r3
            com.amap.bundle.drive.navi.naviwrapper.NaviManager r2 = com.amap.bundle.drive.navi.naviwrapper.NaviManager.a()
            com.autonavi.jni.ae.route.observer.RouteObserver r3 = r8.a
            java.util.List<com.autonavi.jni.ae.route.observer.RouteObserver> r6 = r2.g
            if (r6 != 0) goto L_0x00e2
            java.util.concurrent.CopyOnWriteArrayList r6 = new java.util.concurrent.CopyOnWriteArrayList
            r6.<init>()
            r2.g = r6
        L_0x00e2:
            java.util.List<com.autonavi.jni.ae.route.observer.RouteObserver> r6 = r2.g
            boolean r6 = r6.contains(r3)
            if (r6 != 0) goto L_0x00ef
            java.util.List<com.autonavi.jni.ae.route.observer.RouteObserver> r2 = r2.g
            r2.add(r3)
        L_0x00ef:
            com.autonavi.common.model.POI r2 = r9.getFromPOI()
            com.autonavi.common.model.POI r3 = r9.getFromPOI()
            java.util.ArrayList r3 = r3.getExitList()
            com.amap.bundle.drive.util.ModelCovertUtils$POIType r6 = com.amap.bundle.drive.util.ModelCovertUtils.POIType.START
            com.autonavi.jni.ae.route.model.POIInfo[] r11 = com.amap.bundle.drive.util.ModelCovertUtils.a(r2, r3, r6)
            com.autonavi.common.model.POI r2 = r9.getToPOI()
            com.autonavi.common.model.POI r3 = r9.getToPOI()
            java.util.ArrayList r3 = r3.getEntranceList()
            com.amap.bundle.drive.util.ModelCovertUtils$POIType r6 = com.amap.bundle.drive.util.ModelCovertUtils.POIType.END
            com.autonavi.jni.ae.route.model.POIInfo[] r13 = com.amap.bundle.drive.util.ModelCovertUtils.a(r2, r3, r6)
            java.util.ArrayList r2 = r9.getMidPOIs()
            if (r2 == 0) goto L_0x01c2
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x01c2
            int r6 = r2.size()
            com.autonavi.jni.ae.route.model.POIInfo[] r6 = new com.autonavi.jni.ae.route.model.POIInfo[r6]
            r10 = 0
        L_0x0126:
            if (r10 >= r3) goto L_0x01be
            com.autonavi.jni.ae.route.model.POIInfo r12 = new com.autonavi.jni.ae.route.model.POIInfo
            r12.<init>()
            r6[r10] = r12
            java.lang.Object r12 = r2.get(r10)
            com.autonavi.common.model.POI r12 = (com.autonavi.common.model.POI) r12
            if (r12 == 0) goto L_0x01b5
            java.lang.String r14 = r12.getId()
            boolean r14 = com.amap.bundle.drivecommon.tools.DriveUtil.isLegalPoiId(r14)
            if (r14 == 0) goto L_0x0149
            r14 = r6[r10]
            java.lang.String r15 = r12.getId()
            r14.poiID = r15
        L_0x0149:
            java.util.ArrayList r14 = r12.getEntranceList()
            if (r14 == 0) goto L_0x0186
            int r15 = r14.size()
            if (r15 <= 0) goto L_0x0186
            java.lang.Object r14 = r14.get(r5)
            com.autonavi.common.model.GeoPoint r14 = (com.autonavi.common.model.GeoPoint) r14
            if (r14 == 0) goto L_0x0186
            double r15 = r14.getLongitude()
            r17 = 0
            int r15 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
            if (r15 <= 0) goto L_0x0186
            double r15 = r14.getLatitude()
            int r15 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
            if (r15 <= 0) goto L_0x0186
            r15 = r6[r10]
            r19 = r8
            double r7 = r14.getLatitude()
            float r7 = (float) r7
            double r7 = (double) r7
            r15.naviLat = r7
            r7 = r6[r10]
            double r14 = r14.getLongitude()
            float r8 = (float) r14
            double r14 = (double) r8
            r7.naviLon = r14
            goto L_0x0188
        L_0x0186:
            r19 = r8
        L_0x0188:
            r7 = r6[r10]
            com.autonavi.common.model.GeoPoint r8 = r12.getPoint()
            double r14 = r8.getLatitude()
            float r8 = (float) r14
            double r14 = (double) r8
            r7.latitude = r14
            r7 = r6[r10]
            com.autonavi.common.model.GeoPoint r8 = r12.getPoint()
            double r14 = r8.getLongitude()
            float r8 = (float) r14
            double r14 = (double) r8
            r7.longitude = r14
            r7 = r6[r10]
            java.lang.String r8 = r12.getType()
            r7.typeCode = r8
            r7 = r6[r10]
            int r8 = com.amap.bundle.drivecommon.tools.DriveUtil.genPointType(r12)
            r7.type = r8
            goto L_0x01b7
        L_0x01b5:
            r19 = r8
        L_0x01b7:
            int r10 = r10 + 1
            r8 = r19
            r7 = 0
            goto L_0x0126
        L_0x01be:
            r19 = r8
            r12 = r6
            goto L_0x01c5
        L_0x01c2:
            r19 = r8
            r12 = 0
        L_0x01c5:
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.Context r2 = r2.getApplicationContext()
            boolean r2 = defpackage.aaw.c(r2)
            if (r2 == 0) goto L_0x01d6
            r0 = r0 | 8192(0x2000, float:1.14794E-41)
            goto L_0x01d8
        L_0x01d6:
            r0 = r0 & -8193(0xffffffffffffdfff, float:NaN)
        L_0x01d8:
            java.lang.String r2 = "我的位置"
            com.autonavi.common.model.POI r3 = r9.getFromPOI()
            java.lang.String r3 = r3.getName()
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x01f3
            com.autonavi.sdk.location.LocationInstrument r2 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            android.location.Location r7 = r2.getLatestLocation()
            r10 = r7
            goto L_0x01f4
        L_0x01f3:
            r10 = 0
        L_0x01f4:
            com.amap.bundle.drive.navi.naviwrapper.NaviManager r2 = com.amap.bundle.drive.navi.naviwrapper.NaviManager.a()
            r3 = 256(0x100, float:3.59E-43)
            r0 = r0 | r3
            r14 = 0
            r15 = 1
            com.autonavi.jni.ae.route.model.POIForRequest r6 = com.amap.bundle.drivecommon.tools.DriveUtil.convertLocationToPoiForRequest(r10, r11, r12, r13, r14, r15)
            boolean r7 = r2.c()
            if (r7 == 0) goto L_0x0275
            if (r6 == 0) goto L_0x0275
            com.autonavi.jni.ae.guide.GuideService r5 = r2.b
            r7 = 70
            java.lang.String r8 = java.lang.String.valueOf(r1)
            r5.control(r7, r8)
            com.autonavi.jni.ae.guide.GuideService r5 = r2.b
            r7 = 71
            java.lang.String r8 = java.lang.String.valueOf(r0)
            r5.control(r7, r8)
            java.lang.String r5 = "TYPE_FLAG"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "路线规划或直接导航算路 type="
            r7.<init>(r8)
            r7.append(r1)
            java.lang.String r8 = " flag="
            r7.append(r8)
            r7.append(r0)
            java.lang.String r8 = " flag HEX= 0x"
            r7.append(r8)
            java.lang.String r8 = java.lang.Integer.toHexString(r0)
            r7.append(r8)
            r8 = r0 & 256(0x100, float:3.59E-43)
            if (r8 != r3) goto L_0x0247
            java.lang.String r3 = " 离线算路"
            goto L_0x0249
        L_0x0247:
            java.lang.String r3 = " 在线算路"
        L_0x0249:
            r7.append(r3)
            java.lang.String r3 = r7.toString()
            java.lang.String r7 = "NaviMonitor"
            defpackage.tq.b(r7, r5, r3)
            com.autonavi.jni.ae.route.model.RouteOption r3 = new com.autonavi.jni.ae.route.model.RouteOption
            r3.<init>()
            r3.setConstrainCode(r0)
            r3.setRequestRouteType(r1)
            r3.setPOIForRequest(r6)
            com.autonavi.jni.ae.route.RouteService r0 = r2.a
            int r5 = r0.requestRoute(r3)
            r3.release()
            if (r8 <= 0) goto L_0x0275
            java.lang.String r0 = "P00158"
            java.lang.String r1 = "B004"
            com.amap.bundle.statistics.LogManager.actionLogV2(r0, r1)
        L_0x0275:
            if (r5 != 0) goto L_0x0287
            r0 = r19
            boolean r1 = r0.b
            if (r1 != 0) goto L_0x0287
            com.amap.bundle.drive.offline.OfflineNaviQueryMgr$EnumNaviResponseType r1 = com.amap.bundle.drive.offline.OfflineNaviQueryMgr.EnumNaviResponseType.FAIL
            r2 = -99
            r4.a(r1, r9, r2)
            r0.a()
        L_0x0287:
            r0 = 0
            goto L_0x028a
        L_0x0289:
            r0 = r7
        L_0x028a:
            return r0
        L_0x028b:
            java.lang.String r2 = r0.e
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0299
            java.lang.String r2 = com.amap.bundle.drivecommon.tools.DriveUtil.getLastRoutingChoice()
            r0.e = r2
        L_0x0299:
            r0.h = r3
            java.lang.String r2 = r0.d
            if (r2 != 0) goto L_0x02a3
            java.lang.String r2 = "plan"
            r0.d = r2
        L_0x02a3:
            java.lang.String r2 = r0.d
            java.lang.String r3 = "home"
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 != 0) goto L_0x02c8
            java.lang.String r2 = r0.d
            java.lang.String r3 = "work"
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 != 0) goto L_0x02c8
            boolean r2 = r0.z
            if (r2 == 0) goto L_0x02c8
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r3 = com.autonavi.minimap.R.string.progress_message
            java.lang.String r2 = r2.getString(r3)
            r0.f = r2
        L_0x02c8:
            com.amap.bundle.drive.result.DriveRouteManager$1 r2 = new com.amap.bundle.drive.result.DriveRouteManager$1
            r2.<init>(r0, r1)
            com.autonavi.common.Callback$a r0 = a(r0, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.om.a(ta, axa):com.autonavi.common.Callback$a");
    }

    private static synchronized a a(ta taVar, Callback<tb> callback) {
        synchronized (om.class) {
            if (!bnx.a(taVar.a, taVar.b) || !(taVar.c == null || taVar.c.size() == 0)) {
                if (TextUtils.isEmpty(taVar.e)) {
                    taVar.e = DriveUtil.getLastRoutingChoice();
                }
                taVar.h = true;
                taVar.D = DriveUtil.getVtype(DriveUtil.getCarInfo(), 0);
                RouteCarParamUrlWrapper a = ou.a(0, taVar);
                DriveUtil.addCarRouteLog("2.RouteRequestManagerImpl requestRouteCarResponse:\n url=".concat(String.valueOf(URLBuilderFactory.build(a, false).getUrl())));
                RouteCarRequstCallBack routeCarRequstCallBack = new RouteCarRequstCallBack(callback, taVar);
                routeCarRequstCallBack.a = taVar.E;
                td.a().a(2);
                final AosPostRequest b = aax.b(a);
                in.a().a((AosRequest) b, (AosResponseCallback<T>) routeCarRequstCallBack);
                AnonymousClass2 r5 = new a() {
                    public final void cancel() {
                        in.a().a((AosRequest) b);
                    }

                    public final boolean isCancelled() {
                        if (b != null) {
                            return b.isCanceled();
                        }
                        return false;
                    }
                };
                return r5;
            }
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.route_same_from_to));
            return null;
        }
    }

    public static AosRequest a(int i, Callback<tc> callback, ta... taVarArr) {
        RouteCarParamUrlWrapper routeCarParamUrlWrapper;
        AosPostRequest aosPostRequest = null;
        if (taVarArr == null || taVarArr.length == 0) {
            return null;
        }
        a(i, taVarArr);
        if (taVarArr.length == 1) {
            ta taVar = taVarArr[0];
            taVar.D = DriveUtil.getVtype(DriveUtil.getCarInfo(), i);
            RouteCarParamUrlWrapper a = ou.a(i, taVar);
            if (CalcRouteScene.SCENE_HOME_TMC == taVar.A) {
                routeCarParamUrlWrapper = new RouteCarHomeParamUrlWrapper(a);
            } else {
                routeCarParamUrlWrapper = new RouteCarCompanyParamUrlWrapper(a);
            }
        } else {
            RouteCarParamUrlWrapper routeCarParamUrlWrapper2 = null;
            RouteCarParamUrlWrapper routeCarParamUrlWrapper3 = null;
            for (ta taVar2 : taVarArr) {
                taVar2.D = DriveUtil.getVtype(DriveUtil.getCarInfo(), i);
                if (CalcRouteScene.SCENE_HOME_TMC == taVar2.A) {
                    routeCarParamUrlWrapper2 = ou.a(i, taVar2);
                } else if (CalcRouteScene.SCENE_COMPANY_TMC == taVar2.A) {
                    routeCarParamUrlWrapper3 = ou.a(i, taVar2);
                }
            }
            if (routeCarParamUrlWrapper2 == null || routeCarParamUrlWrapper3 == null) {
                routeCarParamUrlWrapper = null;
            } else {
                routeCarParamUrlWrapper = new RouteCarTmcParamUrlWrapper(routeCarParamUrlWrapper2, routeCarParamUrlWrapper3);
                routeCarParamUrlWrapper.use_truck_engine = a(i);
            }
        }
        if (routeCarParamUrlWrapper != null) {
            routeCarParamUrlWrapper.use_truck_engine = a(i);
            RouteCarRequstTmcCallBack routeCarRequstTmcCallBack = new RouteCarRequstTmcCallBack(callback, taVarArr);
            aosPostRequest = aax.b(routeCarParamUrlWrapper);
            yq.a();
            yq.a((AosRequest) aosPostRequest, (AosResponseCallback<T>) routeCarRequstTmcCallBack);
        }
        return aosPostRequest;
    }

    private static void a(int i, ta... taVarArr) {
        for (ta taVar : taVarArr) {
            if (TextUtils.isEmpty(taVar.e)) {
                if (i == 1) {
                    taVar.e = DriveUtil.getTruckRoutingChoice();
                } else if (i == 11) {
                    taVar.e = ro.e();
                } else {
                    taVar.e = DriveUtil.getLastRoutingChoice();
                }
            }
            taVar.h = true;
            if (taVar.d == null) {
                taVar.d = "plan";
            }
        }
    }
}
