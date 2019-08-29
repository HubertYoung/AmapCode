package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.alibaba.sdk.trade.container.utils.AlibcComponentTrack;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.map.DPoint;
import java.util.Timer;
import java.util.TimerTask;

/* renamed from: act reason: default package */
/* compiled from: SmartTripModeTools */
public final class act {
    public a a;
    Handler b = new Handler() {
        public final void handleMessage(Message message) {
            if (act.this.a != null) {
                act.this.a.a();
            }
            super.handleMessage(message);
        }
    };
    Timer c = new Timer();
    TimerTask d = new TimerTask() {
        public final void run() {
            act.this.b.sendEmptyMessage(1);
        }
    };
    private final String e = "P00014";
    private final String f = "B069";

    /* renamed from: act$a */
    /* compiled from: SmartTripModeTools */
    public interface a {
        void a();

        void a(String str);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.autonavi.bundle.routecommon.model.RouteType a(com.autonavi.bundle.routecommon.model.RouteType r8, com.autonavi.common.model.GeoPoint r9, com.autonavi.common.model.GeoPoint r10, java.lang.String r11) {
        /*
            r7 = this;
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.DEFAULT
            java.lang.String r1 = ""
            boolean r2 = defpackage.bno.a
            if (r2 == 0) goto L_0x0019
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            double r2 = a(r9, r10)
            int r2 = (int) r2
            r1.append(r2)
            java.lang.String r1 = r1.toString()
        L_0x0019:
            int[] r2 = defpackage.act.AnonymousClass3.a
            int r3 = r8.ordinal()
            r2 = r2[r3]
            r3 = 4652007308841189376(0x408f400000000000, double:1000.0)
            switch(r2) {
                case 1: goto L_0x00d3;
                case 2: goto L_0x00a2;
                case 3: goto L_0x008c;
                case 4: goto L_0x005a;
                case 5: goto L_0x0042;
                case 6: goto L_0x002b;
                default: goto L_0x0029;
            }
        L_0x0029:
            goto L_0x00e9
        L_0x002b:
            boolean r11 = b(r11)
            if (r11 != 0) goto L_0x00e9
            double r9 = a(r9, r10)
            int r9 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r9 >= 0) goto L_0x00e9
            com.autonavi.bundle.routecommon.model.RouteType r9 = com.autonavi.bundle.routecommon.model.RouteType.ONFOOT
            int r10 = com.autonavi.minimap.R.string.route_smart_trip_tips_onfoot
            r7.a(r10, r1)
            goto L_0x00ea
        L_0x0042:
            boolean r11 = b(r11)
            if (r11 != 0) goto L_0x00e9
            double r9 = a(r9, r10)
            int r9 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r9 >= 0) goto L_0x00e9
            com.autonavi.bundle.routecommon.model.RouteType r9 = com.autonavi.bundle.routecommon.model.RouteType.ONFOOT
            int r10 = com.autonavi.minimap.R.string.route_smart_trip_tips_onfoot
            r11 = 0
            r7.a(r10, r11)
            goto L_0x00ea
        L_0x005a:
            boolean r2 = c(r11)
            r3 = 4671226772094713856(0x40d3880000000000, double:20000.0)
            if (r2 == 0) goto L_0x0076
            double r5 = a(r9, r10)
            int r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x0076
            com.autonavi.bundle.routecommon.model.RouteType r9 = com.autonavi.bundle.routecommon.model.RouteType.BUS
            int r10 = com.autonavi.minimap.R.string.route_smart_trip_tips_bus
            r7.a(r10, r1)
            goto L_0x00ea
        L_0x0076:
            boolean r11 = c(r11)
            if (r11 != 0) goto L_0x00e9
            double r9 = a(r9, r10)
            int r9 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r9 <= 0) goto L_0x00e9
            com.autonavi.bundle.routecommon.model.RouteType r9 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            int r10 = com.autonavi.minimap.R.string.route_smart_trip_tips_car
            r7.a(r10, r1)
            goto L_0x00ea
        L_0x008c:
            boolean r11 = b(r11)
            if (r11 != 0) goto L_0x00e9
            double r9 = a(r9, r10)
            int r9 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r9 >= 0) goto L_0x00e9
            com.autonavi.bundle.routecommon.model.RouteType r9 = com.autonavi.bundle.routecommon.model.RouteType.ONFOOT
            int r10 = com.autonavi.minimap.R.string.route_smart_trip_tips_onfoot
            r7.a(r10, r1)
            goto L_0x00ea
        L_0x00a2:
            boolean r2 = c(r11)
            r3 = 4662219572839972864(0x40b3880000000000, double:5000.0)
            if (r2 == 0) goto L_0x00bd
            double r5 = a(r9, r10)
            int r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x00bd
            com.autonavi.bundle.routecommon.model.RouteType r9 = com.autonavi.bundle.routecommon.model.RouteType.BUS
            int r10 = com.autonavi.minimap.R.string.route_smart_trip_tips_bus
            r7.a(r10, r1)
            goto L_0x00ea
        L_0x00bd:
            boolean r11 = c(r11)
            if (r11 != 0) goto L_0x00e9
            double r9 = a(r9, r10)
            int r9 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r9 <= 0) goto L_0x00e9
            com.autonavi.bundle.routecommon.model.RouteType r9 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            int r10 = com.autonavi.minimap.R.string.route_smart_trip_tips_car
            r7.a(r10, r1)
            goto L_0x00ea
        L_0x00d3:
            boolean r11 = b(r11)
            if (r11 != 0) goto L_0x00e9
            double r9 = a(r9, r10)
            int r9 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r9 >= 0) goto L_0x00e9
            com.autonavi.bundle.routecommon.model.RouteType r9 = com.autonavi.bundle.routecommon.model.RouteType.ONFOOT
            int r10 = com.autonavi.minimap.R.string.route_smart_trip_tips_onfoot
            r7.a(r10, r1)
            goto L_0x00ea
        L_0x00e9:
            r9 = r0
        L_0x00ea:
            com.autonavi.bundle.routecommon.model.RouteType r10 = com.autonavi.bundle.routecommon.model.RouteType.DEFAULT
            if (r9 == r10) goto L_0x0112
            org.json.JSONObject r10 = new org.json.JSONObject
            r10.<init>()
            java.lang.String r11 = "from"
            java.lang.String r8 = a(r8)     // Catch:{ JSONException -> 0x0106 }
            r10.put(r11, r8)     // Catch:{ JSONException -> 0x0106 }
            java.lang.String r8 = "status"
            java.lang.String r11 = a(r9)     // Catch:{ JSONException -> 0x0106 }
            r10.put(r8, r11)     // Catch:{ JSONException -> 0x0106 }
            goto L_0x010a
        L_0x0106:
            r8 = move-exception
            r8.printStackTrace()
        L_0x010a:
            java.lang.String r8 = "P00014"
            java.lang.String r11 = "B069"
            com.amap.bundle.statistics.LogManager.actionLogV2(r8, r11, r10)
            r8 = r9
        L_0x0112:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.act.a(com.autonavi.bundle.routecommon.model.RouteType, com.autonavi.common.model.GeoPoint, com.autonavi.common.model.GeoPoint, java.lang.String):com.autonavi.bundle.routecommon.model.RouteType");
    }

    private static String a(RouteType routeType) {
        switch (routeType) {
            case CAR:
                return "car";
            case ONFOOT:
                return "foot";
            case TRAIN:
                return "train";
            case RIDE:
                return ShowRouteActionProcessor.SEARCH_TYPE_RIDE;
            case COACH:
                return "coach";
            case TRUCK:
                return DriveUtil.NAVI_TYPE_TRUCK;
            case ETRIP:
                return "etrip";
            case TAXI:
                return FunctionSupportConfiger.TAXI_TAG;
            case FREERIDE:
                return "freeride";
            case BUS:
                return ShowRouteActionProcessor.SEARCH_TYPE_BUS;
            default:
                return Constants.ANIMATOR_NONE;
        }
    }

    private void a(String str) {
        if (this.a != null) {
            this.a.a(str);
            this.c.schedule(this.d, 5000);
        }
    }

    private void a(int i, String str) {
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(appContext.getResources().getString(i));
            sb.append(str);
            a(sb.toString());
        }
    }

    private static double a(GeoPoint geoPoint, GeoPoint geoPoint2) {
        GeoPoint geoPoint3 = geoPoint;
        GeoPoint geoPoint4 = geoPoint2;
        if (geoPoint3 == null || geoPoint4 == null) {
            return 0.0d;
        }
        DPoint a2 = cfg.a((long) geoPoint3.x, (long) geoPoint3.y);
        DPoint a3 = cfg.a((long) geoPoint4.x, (long) geoPoint4.y);
        return Math.abs(((a2.x * 2.003750834E7d) / 180.0d) - ((a3.x * 2.003750834E7d) / 180.0d)) + Math.abs((((Math.log(Math.tan(((a2.y + 90.0d) * 3.141592653589793d) / 360.0d)) / 0.017453292519943295d) * 2.003750834E7d) / 180.0d) - (((Math.log(Math.tan(((a3.y + 90.0d) * 3.141592653589793d) / 360.0d)) / 0.017453292519943295d) * 2.003750834E7d) / 180.0d));
    }

    private static boolean b(String str) {
        return str != null && (str.equals("150900") || str.equals("150903") || str.equals("150904") || str.equals("150905") || str.equals("150906") || str.equals("150907") || str.equals("150908") || str.equals("150909") || str.equals("180200") || str.equals(AlibcComponentTrack.ERRNO_COMPONENT_INIT_FAIL) || str.equals("180202") || str.equals("180203") || str.equals("180300") || str.equals("180301") || str.equals("180302") || str.equals("010000") || str.equals("010100") || str.equals("010101") || str.equals("010102") || str.equals("010103") || str.equals("010104") || str.equals("010105") || str.equals("010107") || str.equals("010108") || str.equals("010109") || str.equals("010110") || str.equals("010111") || str.equals("010112") || str.equals("010200") || str.equals("010300") || str.equals("011100"));
    }

    private static boolean c(String str) {
        return str != null && (str.equals("150500") || str.equals("150501") || str.equals("150600") || str.equals("150700") || str.equals("150701") || str.equals("150702"));
    }
}
