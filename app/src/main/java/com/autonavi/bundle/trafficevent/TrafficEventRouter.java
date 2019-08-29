package com.autonavi.bundle.trafficevent;

import com.autonavi.annotation.Router;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Router({"trafficEvent", "feedback"})
public class TrafficEventRouter extends esk {

    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TrafficEventRouterDef {
        public static final String FEEDBACK_HOST = "feedback";
        public static final String TRAFFIC_EVENT_HOST = "trafficEvent";
        public static final String TRAFFIC_EVENT_PATH_OPEN_REPORT = "openReport";
    }

    /* JADX INFO: used method not loaded: esk.startPageForResult(java.lang.Class, com.autonavi.common.PageBundle, int):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x007d, code lost:
        r3 = new com.autonavi.common.PageBundle();
        r3.putObject("ReportType", r1);
        startPageForResult(com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage.class, r3, 99);
     */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00a6 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean start(defpackage.ese r6) {
        /*
            r5 = this;
            r0 = 0
            if (r6 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.String r1 = r6.a(r0)
            java.lang.String r2 = "trafficevent"
            boolean r1 = r2.equals(r1)
            r2 = 1
            if (r1 == 0) goto L_0x00a7
            android.net.Uri r1 = r6.a
            if (r1 == 0) goto L_0x00a3
            java.lang.String r1 = "report"
            java.lang.String r3 = r6.a(r2)
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x008f
            android.net.Uri r1 = r6.a
            if (r1 == 0) goto L_0x00a3
            java.lang.String r3 = "type"
            java.lang.String r1 = r1.getQueryParameter(r3)
            r3 = -1
            int r4 = r1.hashCode()
            switch(r4) {
                case -2143202801: goto L_0x0060;
                case -1995960111: goto L_0x0056;
                case -396296243: goto L_0x004c;
                case 792315675: goto L_0x0041;
                case 1616533543: goto L_0x0037;
                default: goto L_0x0036;
            }
        L_0x0036:
            goto L_0x006a
        L_0x0037:
            java.lang.String r4 = "congestion"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x006a
            r1 = 2
            goto L_0x006b
        L_0x0041:
            java.lang.String r4 = "roadclosure"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x006a
            r1 = 4
            goto L_0x006b
        L_0x004c:
            java.lang.String r4 = "ponding"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x006a
            r1 = 3
            goto L_0x006b
        L_0x0056:
            java.lang.String r4 = "construction"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x006a
            r1 = 1
            goto L_0x006b
        L_0x0060:
            java.lang.String r4 = "accident"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x006a
            r1 = 0
            goto L_0x006b
        L_0x006a:
            r1 = -1
        L_0x006b:
            switch(r1) {
                case 0: goto L_0x007b;
                case 1: goto L_0x0078;
                case 2: goto L_0x0075;
                case 3: goto L_0x0072;
                case 4: goto L_0x006f;
                default: goto L_0x006e;
            }
        L_0x006e:
            goto L_0x00a3
        L_0x006f:
            com.autonavi.minimap.basemap.traffic.ReportType r1 = com.autonavi.minimap.basemap.traffic.ReportType.STOP
            goto L_0x007d
        L_0x0072:
            com.autonavi.minimap.basemap.traffic.ReportType r1 = com.autonavi.minimap.basemap.traffic.ReportType.PONDING
            goto L_0x007d
        L_0x0075:
            com.autonavi.minimap.basemap.traffic.ReportType r1 = com.autonavi.minimap.basemap.traffic.ReportType.CONGESTION
            goto L_0x007d
        L_0x0078:
            com.autonavi.minimap.basemap.traffic.ReportType r1 = com.autonavi.minimap.basemap.traffic.ReportType.PROCESS
            goto L_0x007d
        L_0x007b:
            com.autonavi.minimap.basemap.traffic.ReportType r1 = com.autonavi.minimap.basemap.traffic.ReportType.ACCIDENT
        L_0x007d:
            com.autonavi.common.PageBundle r3 = new com.autonavi.common.PageBundle
            r3.<init>()
            java.lang.String r4 = "ReportType"
            r3.putObject(r4, r1)
            java.lang.Class<com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage> r1 = com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage.class
            r4 = 99
            r5.startPageForResult(r1, r3, r4)
            goto L_0x00a1
        L_0x008f:
            java.lang.String r1 = "122call"
            java.lang.String r3 = r6.a(r2)
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00a3
            java.lang.Class<com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage> r1 = com.autonavi.minimap.basemap.traffic.page.TrafficAlarmPage.class
            r3 = 0
            r5.startPage(r1, r3)
        L_0x00a1:
            r1 = 1
            goto L_0x00a4
        L_0x00a3:
            r1 = 0
        L_0x00a4:
            if (r1 == 0) goto L_0x00a7
            return r2
        L_0x00a7:
            android.net.Uri r6 = r6.a
            if (r6 == 0) goto L_0x010e
            java.lang.String r1 = r6.getHost()
            java.util.List r3 = r6.getPathSegments()
            java.lang.String r4 = "trafficEvent"
            boolean r1 = android.text.TextUtils.equals(r1, r4)
            if (r1 == 0) goto L_0x00c5
            if (r3 == 0) goto L_0x00c4
            boolean r1 = r3.isEmpty()
            if (r1 == 0) goto L_0x00c5
        L_0x00c4:
            return r0
        L_0x00c5:
            java.lang.Object r1 = r3.get(r0)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r3 = "openReport"
            boolean r1 = android.text.TextUtils.equals(r1, r3)
            if (r1 == 0) goto L_0x010e
            bid r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r1 == 0) goto L_0x010e
            boolean r3 = r1 instanceof com.autonavi.minimap.basemap.traffic.page.TrafficMainMapPage
            if (r3 == 0) goto L_0x010e
            com.autonavi.minimap.basemap.traffic.page.TrafficMainMapPage r1 = (com.autonavi.minimap.basemap.traffic.page.TrafficMainMapPage) r1
            java.lang.String r0 = "data"
            java.lang.String r6 = r6.getQueryParameter(r0)
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0109 }
            r0.<init>(r6)     // Catch:{ JSONException -> 0x0109 }
            java.lang.String r6 = "buttonFlag"
            java.lang.String r6 = r0.optString(r6)     // Catch:{ JSONException -> 0x0109 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ JSONException -> 0x0109 }
            int r6 = (int) r6     // Catch:{ JSONException -> 0x0109 }
            com.autonavi.common.PageBundle r0 = r1.a(r6)     // Catch:{ JSONException -> 0x0109 }
            boolean r0 = r0.isEmpty()     // Catch:{ JSONException -> 0x0109 }
            if (r0 != 0) goto L_0x010d
            java.lang.Class<com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage> r0 = com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage.class
            com.autonavi.common.PageBundle r6 = r1.a(r6)     // Catch:{ JSONException -> 0x0109 }
            r1.startPage(r0, r6)     // Catch:{ JSONException -> 0x0109 }
            goto L_0x010d
        L_0x0109:
            r6 = move-exception
            r6.printStackTrace()
        L_0x010d:
            return r2
        L_0x010e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.trafficevent.TrafficEventRouter.start(ese):boolean");
    }
}
