package defpackage;

import android.content.Context;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.a;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;

/* renamed from: ebc reason: default package */
/* compiled from: RouteErrorBundleUtil */
public final class ebc {
    private static final dta a = new dta() {
        public final List<LineOverlayItem> a(Context context, GeoPoint[] geoPointArr) {
            eap a = eap.a(102);
            ArrayList arrayList = new ArrayList();
            LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, agn.a(context, (float) a.f()));
            lineOverlayItem.setFillLineColor(a.a());
            lineOverlayItem.setFillLineId(R.drawable.route_map_frontlr);
            lineOverlayItem.setBackgroundColor(a.b());
            lineOverlayItem.setBackgrondId(R.drawable.route_map_lr);
            LineOverlayItem lineOverlayItem2 = new LineOverlayItem(6, geoPointArr, agn.a(context, (float) a.f()));
            lineOverlayItem2.setFillLineId(a.c());
            arrayList.add(lineOverlayItem);
            arrayList.add(lineOverlayItem2);
            return arrayList;
        }
    };
    private static final dta b = new dta() {
        public final List<LineOverlayItem> a(Context context, GeoPoint[] geoPointArr) {
            eap a = eap.a(103);
            ArrayList arrayList = new ArrayList();
            LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, agn.a(context, (float) a.f()));
            lineOverlayItem.setFillLineColor(a.a());
            lineOverlayItem.setFillLineId(R.drawable.route_map_frontlr);
            lineOverlayItem.setBackgroundColor(a.b());
            lineOverlayItem.setBackgrondId(R.drawable.route_map_lr);
            LineOverlayItem lineOverlayItem2 = new LineOverlayItem(6, geoPointArr, agn.a(context, (float) a.f()));
            lineOverlayItem2.setFillLineId(a.c());
            arrayList.add(lineOverlayItem);
            arrayList.add(lineOverlayItem2);
            return arrayList;
        }
    };

    /* JADX WARNING: type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r4v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v2, types: [com.autonavi.common.model.POI] */
    /* JADX WARNING: type inference failed for: r5v1 */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v12, types: [com.autonavi.common.model.POI] */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v7, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r4v6, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r5v13, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r5v14 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r1v15 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r1v16 */
    /* JADX WARNING: type inference failed for: r1v17 */
    /* JADX WARNING: type inference failed for: r1v18 */
    /* JADX WARNING: type inference failed for: r1v19 */
    /* JADX WARNING: type inference failed for: r1v20 */
    /* JADX WARNING: type inference failed for: r1v21 */
    /* JADX WARNING: type inference failed for: r1v22 */
    /* JADX WARNING: type inference failed for: r1v23 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: type inference failed for: r2v16 */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: type inference failed for: r2v18 */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: type inference failed for: r3v12 */
    /* JADX WARNING: type inference failed for: r3v13 */
    /* JADX WARNING: type inference failed for: r3v14 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: type inference failed for: r4v11 */
    /* JADX WARNING: type inference failed for: r5v15 */
    /* JADX WARNING: type inference failed for: r5v16 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v1
      assigns: []
      uses: []
      mth insns count: 116
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00f3  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00fe  */
    /* JADX WARNING: Unknown variable types count: 29 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.autonavi.common.PageBundle a(android.content.Context r10, java.lang.String r11, java.lang.String r12) {
        /*
            com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            java.lang.String r10 = r0.getLocationLog(r10)
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r1 = "page_action"
            java.lang.String r2 = "com.basemap.action.feedback_entry_list"
            r0.putString(r1, r2)
            java.lang.String r1 = "location_log"
            r0.putString(r1, r10)
            java.lang.String r10 = "error_pic_path"
            r0.putString(r10, r11)
            java.lang.String r10 = "from_route_type"
            com.autonavi.bundle.routecommon.model.RouteType r11 = com.autonavi.bundle.routecommon.model.RouteType.ONFOOT
            int r11 = r11.ordinal()
            r0.putInt(r10, r11)
            java.lang.String r10 = "ajx_select_poi"
            r11 = 1
            r0.putBoolean(r10, r11)
            if (r12 == 0) goto L_0x011d
            java.lang.String r10 = r12.trim()
            java.lang.String r11 = ""
            boolean r10 = r10.equals(r11)
            if (r10 == 0) goto L_0x003f
            goto L_0x011d
        L_0x003f:
            r10 = 0
            org.json.JSONObject r11 = new org.json.JSONObject     // Catch:{ Exception -> 0x00d8 }
            r11.<init>(r12)     // Catch:{ Exception -> 0x00d8 }
            java.lang.String r12 = "startPoi"
            java.lang.String r12 = r11.optString(r12)     // Catch:{ Exception -> 0x00d8 }
            com.autonavi.common.model.POI r12 = defpackage.bnx.a(r12)     // Catch:{ Exception -> 0x00d8 }
            java.lang.String r1 = "endPoi"
            java.lang.String r1 = r11.optString(r1)     // Catch:{ Exception -> 0x00d5 }
            com.autonavi.common.model.POI r1 = defpackage.bnx.a(r1)     // Catch:{ Exception -> 0x00d5 }
            java.lang.String r2 = "naviID"
            java.lang.String r2 = r11.optString(r2)     // Catch:{ Exception -> 0x00d2 }
            java.lang.String r3 = "resultID"
            java.lang.String r3 = r11.optString(r3)     // Catch:{ Exception -> 0x00cf }
            java.lang.String r4 = "pathID"
            java.lang.String r4 = r11.optString(r4)     // Catch:{ Exception -> 0x00cc }
            java.lang.String r5 = "server_spoi"
            java.lang.String r5 = r11.optString(r5)     // Catch:{ Exception -> 0x00c9 }
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ Exception -> 0x00c9 }
            r6.<init>(r5)     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r5 = "coor"
            java.lang.String r5 = r6.getString(r5)     // Catch:{ Exception -> 0x00c9 }
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ Exception -> 0x00c9 }
            r6.<init>(r5)     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r5 = "x"
            double r7 = r6.optDouble(r5)     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r5 = "y"
            double r5 = r6.optDouble(r5)     // Catch:{ Exception -> 0x00c9 }
            com.autonavi.common.model.GeoPoint r9 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x00c9 }
            r9.<init>(r7, r5)     // Catch:{ Exception -> 0x00c9 }
            r12.setPoint(r9)     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r5 = "server_epoi"
            java.lang.String r11 = r11.optString(r5)     // Catch:{ Exception -> 0x00c9 }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x00c9 }
            r5.<init>(r11)     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r11 = "coor"
            java.lang.String r11 = r5.getString(r11)     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r6 = "poiID"
            java.lang.String r5 = r5.optString(r6)     // Catch:{ Exception -> 0x00c9 }
            org.json.JSONObject r10 = new org.json.JSONObject     // Catch:{ Exception -> 0x00c6 }
            r10.<init>(r11)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r11 = "x"
            double r6 = r10.optDouble(r11)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r11 = "y"
            double r10 = r10.optDouble(r11)     // Catch:{ Exception -> 0x00c6 }
            com.autonavi.common.model.GeoPoint r8 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x00c6 }
            r8.<init>(r6, r10)     // Catch:{ Exception -> 0x00c6 }
            r1.setPoint(r8)     // Catch:{ Exception -> 0x00c6 }
            goto L_0x00e2
        L_0x00c6:
            r10 = move-exception
            r11 = r10
            goto L_0x00df
        L_0x00c9:
            r11 = move-exception
            r5 = r10
            goto L_0x00df
        L_0x00cc:
            r11 = move-exception
            r4 = r10
            goto L_0x00de
        L_0x00cf:
            r11 = move-exception
            r3 = r10
            goto L_0x00dd
        L_0x00d2:
            r11 = move-exception
            r2 = r10
            goto L_0x00dc
        L_0x00d5:
            r11 = move-exception
            r1 = r10
            goto L_0x00db
        L_0x00d8:
            r11 = move-exception
            r12 = r10
            r1 = r12
        L_0x00db:
            r2 = r1
        L_0x00dc:
            r3 = r2
        L_0x00dd:
            r4 = r3
        L_0x00de:
            r5 = r4
        L_0x00df:
            r11.printStackTrace()
        L_0x00e2:
            java.lang.String r10 = "naviid"
            r0.putString(r10, r2)
            java.lang.String r10 = "resultid"
            r0.putString(r10, r3)
            java.lang.String r10 = "pathid"
            r0.putString(r10, r4)
            if (r12 == 0) goto L_0x00fc
            com.autonavi.common.model.POI r10 = r12.clone()
            java.lang.String r11 = "startpoint"
            r0.putObject(r11, r10)
        L_0x00fc:
            if (r1 == 0) goto L_0x0115
            com.autonavi.common.model.POI r10 = r1.clone()
            java.lang.String r11 = "endpoint"
            r0.putObject(r11, r10)
            java.lang.String r11 = "sonPOIID"
            r0.putString(r11, r5)
            java.lang.String r11 = "poiid"
            java.lang.String r10 = r10.getId()
            r0.putString(r11, r10)
        L_0x0115:
            java.lang.String r10 = "overlay_style"
            dta r11 = a
            r0.putObject(r10, r11)
            return r0
        L_0x011d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ebc.a(android.content.Context, java.lang.String, java.lang.String):com.autonavi.common.PageBundle");
    }

    public static PageBundle a(Context context, RideTraceHistory rideTraceHistory) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("sourcepage", "35");
        pageBundle.putInt("page_id", 26);
        pageBundle.putString("location_log", LocationInstrument.getInstance().getLocationLog(context));
        if (!(rideTraceHistory == null || rideTraceHistory.j == null)) {
            POI poi = rideTraceHistory.j.a;
            POI poi2 = rideTraceHistory.j.b;
            if (poi != null) {
                pageBundle.putObject("startpoint", poi.clone());
            }
            if (poi2 != null) {
                POI clone = poi2.clone();
                pageBundle.putObject("endpoint", clone);
                pageBundle.putObject(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, clone.getId());
                pageBundle.putObject("sonPOIID", clone.getPoiExtra().get("end_sub_poiid"));
            }
            pageBundle.putObject("overlay_style", b);
            ArrayList<a> arrayList = rideTraceHistory.j.e;
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList2.add(arrayList.get(i).a.getPoint());
            }
            pageBundle.putObject("foot_navi_points", (GeoPoint[]) arrayList2.toArray(new GeoPoint[arrayList2.size()]));
        }
        return pageBundle;
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v7, types: [com.autonavi.common.model.POI] */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: type inference failed for: r1v15 */
    /* JADX WARNING: type inference failed for: r1v17, types: [com.autonavi.common.model.POI] */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v12, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r3v6, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r4v5, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r4v6 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r1v18 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r1v19 */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: type inference failed for: r1v20 */
    /* JADX WARNING: type inference failed for: r1v21 */
    /* JADX WARNING: type inference failed for: r1v22 */
    /* JADX WARNING: type inference failed for: r1v23 */
    /* JADX WARNING: type inference failed for: r1v24 */
    /* JADX WARNING: type inference failed for: r1v25 */
    /* JADX WARNING: type inference failed for: r1v26 */
    /* JADX WARNING: type inference failed for: r2v16 */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: type inference failed for: r2v18 */
    /* JADX WARNING: type inference failed for: r2v19 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r4v1
      assigns: []
      uses: []
      mth insns count: 111
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x010b  */
    /* JADX WARNING: Unknown variable types count: 21 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.autonavi.common.PageBundle b(android.content.Context r10, java.lang.String r11, java.lang.String r12) {
        /*
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r1 = "error_type"
            r2 = 7
            r0.putInt(r1, r2)
            com.autonavi.sdk.location.LocationInstrument r1 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            java.lang.String r10 = r1.getLocationLog(r10)
            java.lang.String r1 = "location_log"
            r0.putString(r1, r10)
            java.lang.String r10 = "sourcepage"
            r1 = 16
            r0.putInt(r10, r1)
            java.lang.String r10 = "ajx_select_poi"
            r1 = 1
            r0.putBoolean(r10, r1)
            java.lang.String r10 = "from_route_type"
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.RIDE
            int r1 = r1.ordinal()
            r0.putInt(r10, r1)
            if (r11 == 0) goto L_0x0037
            java.lang.String r10 = "error_pic_path"
            r0.putString(r10, r11)
        L_0x0037:
            if (r12 == 0) goto L_0x0139
            java.lang.String r10 = r12.trim()
            java.lang.String r11 = ""
            boolean r10 = r10.equals(r11)
            if (r10 == 0) goto L_0x0047
            goto L_0x0139
        L_0x0047:
            r10 = 0
            org.json.JSONObject r11 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00d6 }
            r11.<init>(r12)     // Catch:{ JSONException -> 0x00d6 }
            java.lang.String r12 = "startPoi"
            java.lang.String r12 = r11.optString(r12)     // Catch:{ JSONException -> 0x00d6 }
            com.autonavi.common.model.POI r12 = defpackage.bnx.a(r12)     // Catch:{ JSONException -> 0x00d6 }
            java.lang.String r1 = "endPoi"
            java.lang.String r1 = r11.optString(r1)     // Catch:{ JSONException -> 0x00d3 }
            com.autonavi.common.model.POI r1 = defpackage.bnx.a(r1)     // Catch:{ JSONException -> 0x00d3 }
            java.lang.String r2 = "naviID"
            java.lang.String r2 = r11.optString(r2)     // Catch:{ JSONException -> 0x00d0 }
            java.lang.String r3 = "resultID"
            java.lang.String r3 = r11.optString(r3)     // Catch:{ JSONException -> 0x00cd }
            java.lang.String r4 = "pathID"
            java.lang.String r4 = r11.optString(r4)     // Catch:{ JSONException -> 0x00ca }
            java.lang.String r5 = "server_spoi"
            java.lang.String r5 = r11.optString(r5)     // Catch:{ JSONException -> 0x00c8 }
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00c8 }
            r6.<init>(r5)     // Catch:{ JSONException -> 0x00c8 }
            java.lang.String r5 = "coor"
            java.lang.String r5 = r6.getString(r5)     // Catch:{ JSONException -> 0x00c8 }
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00c8 }
            r6.<init>(r5)     // Catch:{ JSONException -> 0x00c8 }
            java.lang.String r5 = "x"
            double r7 = r6.optDouble(r5)     // Catch:{ JSONException -> 0x00c8 }
            java.lang.String r5 = "y"
            double r5 = r6.optDouble(r5)     // Catch:{ JSONException -> 0x00c8 }
            com.autonavi.common.model.GeoPoint r9 = new com.autonavi.common.model.GeoPoint     // Catch:{ JSONException -> 0x00c8 }
            r9.<init>(r7, r5)     // Catch:{ JSONException -> 0x00c8 }
            r12.setPoint(r9)     // Catch:{ JSONException -> 0x00c8 }
            java.lang.String r5 = "server_epoi"
            java.lang.String r11 = r11.optString(r5)     // Catch:{ JSONException -> 0x00c8 }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00c8 }
            r5.<init>(r11)     // Catch:{ JSONException -> 0x00c8 }
            java.lang.String r11 = "coor"
            java.lang.String r11 = r5.getString(r11)     // Catch:{ JSONException -> 0x00c8 }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00c8 }
            r5.<init>(r11)     // Catch:{ JSONException -> 0x00c8 }
            java.lang.String r11 = "x"
            double r6 = r5.optDouble(r11)     // Catch:{ JSONException -> 0x00c8 }
            java.lang.String r11 = "y"
            double r8 = r5.optDouble(r11)     // Catch:{ JSONException -> 0x00c8 }
            com.autonavi.common.model.GeoPoint r11 = new com.autonavi.common.model.GeoPoint     // Catch:{ JSONException -> 0x00c8 }
            r11.<init>(r6, r8)     // Catch:{ JSONException -> 0x00c8 }
            r1.setPoint(r11)     // Catch:{ JSONException -> 0x00c8 }
            goto L_0x00df
        L_0x00c8:
            r11 = move-exception
            goto L_0x00dc
        L_0x00ca:
            r11 = move-exception
            r4 = r10
            goto L_0x00dc
        L_0x00cd:
            r11 = move-exception
            r3 = r10
            goto L_0x00db
        L_0x00d0:
            r11 = move-exception
            r2 = r10
            goto L_0x00da
        L_0x00d3:
            r11 = move-exception
            r1 = r10
            goto L_0x00d9
        L_0x00d6:
            r11 = move-exception
            r12 = r10
            r1 = r12
        L_0x00d9:
            r2 = r1
        L_0x00da:
            r3 = r2
        L_0x00db:
            r4 = r3
        L_0x00dc:
            r11.printStackTrace()
        L_0x00df:
            java.lang.String r11 = "naviid"
            r0.putString(r11, r2)
            java.lang.String r11 = "resultid"
            r0.putString(r11, r3)
            java.lang.String r11 = "pathid"
            r0.putString(r11, r4)
            java.lang.String r11 = "car_used"
            boolean r2 = defpackage.ebm.c()
            if (r2 == 0) goto L_0x00f9
            java.lang.String r2 = "3"
            goto L_0x00fb
        L_0x00f9:
            java.lang.String r2 = "1"
        L_0x00fb:
            r0.putString(r11, r2)
            if (r12 == 0) goto L_0x0109
            com.autonavi.common.model.POI r11 = r12.clone()
            java.lang.String r12 = "startpoint"
            r0.putObject(r12, r11)
        L_0x0109:
            if (r1 == 0) goto L_0x0131
            com.autonavi.common.model.POI r11 = r1.clone()
            java.lang.String r12 = "endpoint"
            r0.putObject(r12, r11)
            java.lang.String r12 = "sonPOIID"
            if (r11 != 0) goto L_0x0119
            goto L_0x0125
        L_0x0119:
            java.util.HashMap r10 = r11.getPoiExtra()
            java.lang.String r1 = "end_sub_poiid"
            java.lang.Object r10 = r10.get(r1)
            java.lang.String r10 = (java.lang.String) r10
        L_0x0125:
            r0.putString(r12, r10)
            java.lang.String r10 = "poiid"
            java.lang.String r11 = r11.getId()
            r0.putString(r10, r11)
        L_0x0131:
            java.lang.String r10 = "overlay_style"
            dta r11 = b
            r0.putObject(r10, r11)
            return r0
        L_0x0139:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ebc.b(android.content.Context, java.lang.String, java.lang.String):com.autonavi.common.PageBundle");
    }
}
