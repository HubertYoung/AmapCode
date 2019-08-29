package defpackage;

import android.os.Handler;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.minimap.route.bus.realtimebus.RecommendResponse;
import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatManager;
import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatManager.b;
import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatRequest;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses;
import com.autonavi.minimap.route.bus.realtimebus.net.param.RealTimeBusRecommendParam;
import com.autonavi.minimap.route.bus.realtimebus.net.param.RealTimeBuslineParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* renamed from: dxu reason: default package */
/* compiled from: RealTimeBusRequestManager */
public final class dxu {
    private static yq a;
    private static AosRequest b;

    public static HashMap<String, RealTimeBusAndStationMatchup> a(List<btd> list) {
        HashMap<String, RealTimeBusAndStationMatchup> hashMap = new HashMap<>();
        if (list == null || list.size() <= 0) {
            return hashMap;
        }
        for (btd next : list) {
            RealTimeBusAndStationMatchup realTimeBusAndStationMatchup = new RealTimeBusAndStationMatchup(next.bus_id, next.bus_name, next.station_id, next.station_name, next.bus_describe, next.station_lon.doubleValue(), next.station_lat.doubleValue());
            realTimeBusAndStationMatchup.mBean = next;
            hashMap.put(next.station_id, realTimeBusAndStationMatchup);
        }
        return hashMap;
    }

    public static a a(List<btd> list, Callback<dyk> callback, String str, String str2, String str3) {
        ArrayList arrayList = null;
        if (list == null || list.size() <= 0) {
            return null;
        }
        if (list != null && list.size() > 0) {
            arrayList = new ArrayList();
            for (btd next : list) {
                RealTimeBusAndStationMatchup realTimeBusAndStationMatchup = new RealTimeBusAndStationMatchup(next.bus_id, next.station_id, next.bus_describe);
                realTimeBusAndStationMatchup.mBean = next;
                arrayList.add(realTimeBusAndStationMatchup);
            }
        }
        a aVar = new a(new Handler(), callback, arrayList, str, str2, str3);
        aVar.c = true;
        if (!aVar.isCancelled()) {
            aVar.cancel();
        }
        aVar.d = false;
        aVar.e.removeCallbacks(aVar.f);
        aVar.a(false);
        return aVar;
    }

    public static HeartBeatRequest a(String str, String str2, String str3, dyc<RealtimeBuses> dyc, String str4, String str5, String str6, boolean z) {
        RealTimeBuslineParam realTimeBuslineParam;
        a aVar = new a();
        aVar.a = str;
        aVar.b = str2;
        aVar.c = str3;
        aVar.f = str4;
        aVar.g = str5;
        aVar.e = str6;
        aVar.i = "2";
        aVar.k = "2";
        aVar.j = "2";
        aVar.h = true;
        aVar.d = true;
        HeartBeatRequest heartBeatRequest = new HeartBeatRequest();
        if (TextUtils.isEmpty(aVar.a) || TextUtils.isEmpty(aVar.b) || TextUtils.isEmpty(aVar.c)) {
            realTimeBuslineParam = null;
        } else {
            realTimeBuslineParam = new RealTimeBuslineParam();
            realTimeBuslineParam.adcode = aVar.a;
            realTimeBuslineParam.lines = aVar.b;
            realTimeBuslineParam.stations = aVar.c;
            realTimeBuslineParam.source_type = aVar.f;
            realTimeBuslineParam.from_page = aVar.g;
            realTimeBuslineParam.is_refresh = aVar.e;
            if (!TextUtils.isEmpty(aVar.k)) {
                realTimeBuslineParam.need_bus_track = aVar.k;
            }
            if (!TextUtils.isEmpty(aVar.j)) {
                realTimeBuslineParam.need_over_station = aVar.j;
            }
            if (!TextUtils.isEmpty(aVar.i)) {
                realTimeBuslineParam.count = aVar.i;
            }
            realTimeBuslineParam.need_not_depart = aVar.h;
            if (aVar.d) {
                realTimeBuslineParam.need_bus_status = "1";
            } else {
                realTimeBuslineParam.need_bus_status = "0";
            }
        }
        aax.a((ParamEntity) realTimeBuslineParam, (AosRequest) heartBeatRequest);
        heartBeatRequest.b = z;
        HeartBeatManager a2 = HeartBeatManager.a();
        Class<RealtimeBuses> cls = RealtimeBuses.class;
        if (dyc != null) {
            if (!a2.a.containsKey(heartBeatRequest)) {
                HeartBeatManager.a aVar2 = new HeartBeatManager.a(new b(heartBeatRequest, dyc, cls));
                a2.a.put(heartBeatRequest, aVar2);
                aVar2.a();
            } else {
                a2.b(heartBeatRequest);
            }
        }
        return heartBeatRequest;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:121:0x0258 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:125:0x0277 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList<com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation> a(java.util.ArrayList<com.autonavi.common.model.POI> r37, android.content.Context r38) {
        /*
            r1 = 0
            if (r37 != 0) goto L_0x0004
            return r1
        L_0x0004:
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.Iterator r2 = r37.iterator()
        L_0x000d:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x02d6
            java.lang.Object r4 = r2.next()
            com.autonavi.common.model.POI r4 = (com.autonavi.common.model.POI) r4
            java.lang.String r5 = r4.getName()
            java.util.HashMap r4 = r4.getPoiExtra()
            java.lang.String r6 = "stations"
            java.lang.Object r6 = r4.get(r6)
            java.lang.String r6 = (java.lang.String) r6
            java.lang.String r7 = "child_stations"
            r4.get(r7)
            boolean r4 = android.text.TextUtils.isEmpty(r6)
            if (r4 != 0) goto L_0x000d
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x02ca }
            r4.<init>()     // Catch:{ JSONException -> 0x02ca }
            com.autonavi.sdk.location.LocationInstrument r7 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x02ca }
            com.autonavi.common.model.GeoPoint r7 = r7.getLatestPosition()     // Catch:{ JSONException -> 0x02ca }
            int r7 = r7.getAdCode()     // Catch:{ JSONException -> 0x02ca }
            r4.append(r7)     // Catch:{ JSONException -> 0x02ca }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x02ca }
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ JSONException -> 0x02ca }
            r7.<init>(r6)     // Catch:{ JSONException -> 0x02ca }
            java.lang.String r6 = "businfo_realbus"
            boolean r6 = r7.has(r6)     // Catch:{ JSONException -> 0x02ca }
            if (r6 == 0) goto L_0x006d
            java.lang.String r6 = "businfo_realbus"
            java.lang.String r6 = r7.getString(r6)     // Catch:{ JSONException -> 0x0067 }
            java.lang.String r8 = "\\|"
            java.lang.String[] r6 = r6.split(r8)     // Catch:{ JSONException -> 0x0067 }
            goto L_0x006e
        L_0x0067:
            r0 = move-exception
            r1 = r0
            r16 = r2
            goto L_0x02ce
        L_0x006d:
            r6 = r1
        L_0x006e:
            java.lang.String r8 = "businfo_lineids"
            boolean r8 = r7.has(r8)     // Catch:{ JSONException -> 0x02ca }
            if (r8 == 0) goto L_0x0083
            java.lang.String r8 = "businfo_lineids"
            java.lang.String r8 = r7.getString(r8)     // Catch:{ JSONException -> 0x0067 }
            java.lang.String r9 = "\\|"
            java.lang.String[] r8 = r8.split(r9)     // Catch:{ JSONException -> 0x0067 }
            goto L_0x0084
        L_0x0083:
            r8 = r1
        L_0x0084:
            java.lang.String r9 = "businfo_line_keys"
            boolean r9 = r7.has(r9)     // Catch:{ JSONException -> 0x02ca }
            if (r9 == 0) goto L_0x0099
            java.lang.String r9 = "businfo_line_keys"
            java.lang.String r9 = r7.getString(r9)     // Catch:{ JSONException -> 0x0067 }
            java.lang.String r10 = "\\|"
            java.lang.String[] r9 = r9.split(r10)     // Catch:{ JSONException -> 0x0067 }
            goto L_0x009a
        L_0x0099:
            r9 = r1
        L_0x009a:
            java.lang.String r10 = "businfo_stationids"
            boolean r10 = r7.has(r10)     // Catch:{ JSONException -> 0x02ca }
            if (r10 == 0) goto L_0x00af
            java.lang.String r10 = "businfo_stationids"
            java.lang.String r10 = r7.getString(r10)     // Catch:{ JSONException -> 0x0067 }
            java.lang.String r11 = "\\|"
            java.lang.String[] r10 = r10.split(r11)     // Catch:{ JSONException -> 0x0067 }
            goto L_0x00b0
        L_0x00af:
            r10 = r1
        L_0x00b0:
            java.lang.String r11 = "businfo_line_names"
            boolean r11 = r7.has(r11)     // Catch:{ JSONException -> 0x02ca }
            if (r11 == 0) goto L_0x00c5
            java.lang.String r11 = "businfo_line_names"
            java.lang.String r11 = r7.getString(r11)     // Catch:{ JSONException -> 0x0067 }
            java.lang.String r12 = "\\|"
            java.lang.String[] r11 = r11.split(r12)     // Catch:{ JSONException -> 0x0067 }
            goto L_0x00c6
        L_0x00c5:
            r11 = r1
        L_0x00c6:
            java.lang.String r12 = "businfo_ids"
            boolean r12 = r7.has(r12)     // Catch:{ JSONException -> 0x02ca }
            if (r12 == 0) goto L_0x00db
            java.lang.String r12 = "businfo_ids"
            java.lang.String r12 = r7.getString(r12)     // Catch:{ JSONException -> 0x0067 }
            java.lang.String r13 = "\\|"
            java.lang.String[] r12 = r12.split(r13)     // Catch:{ JSONException -> 0x0067 }
            goto L_0x00dc
        L_0x00db:
            r12 = r1
        L_0x00dc:
            java.lang.String r13 = "ys"
            boolean r13 = r7.has(r13)     // Catch:{ JSONException -> 0x02ca }
            if (r13 == 0) goto L_0x00f3
            java.lang.String r13 = "ys"
            java.lang.String r13 = r7.getString(r13)     // Catch:{ JSONException -> 0x0067 }
            java.lang.String r14 = "\\|"
            java.lang.String[] r13 = r13.split(r14)     // Catch:{ JSONException -> 0x0067 }
            goto L_0x00f4
        L_0x00f3:
            r13 = r1
        L_0x00f4:
            java.lang.String r14 = "xs"
            boolean r14 = r7.has(r14)     // Catch:{ JSONException -> 0x02ca }
            if (r14 == 0) goto L_0x010b
            java.lang.String r14 = "xs"
            java.lang.String r7 = r7.getString(r14)     // Catch:{ JSONException -> 0x0067 }
            java.lang.String r14 = "\\|"
            java.lang.String[] r7 = r7.split(r14)     // Catch:{ JSONException -> 0x0067 }
            goto L_0x010c
        L_0x010b:
            r7 = r1
        L_0x010c:
            if (r6 == 0) goto L_0x02c7
            if (r8 == 0) goto L_0x02c7
            if (r9 == 0) goto L_0x02c7
            if (r10 == 0) goto L_0x02c7
            if (r11 == 0) goto L_0x02c7
            if (r13 == 0) goto L_0x02c7
            if (r7 == 0) goto L_0x02c7
            if (r12 == 0) goto L_0x02c7
            int r14 = r6.length     // Catch:{ JSONException -> 0x02ca }
            int r15 = r8.length     // Catch:{ JSONException -> 0x02ca }
            if (r14 != r15) goto L_0x02c7
            int r14 = r8.length     // Catch:{ JSONException -> 0x02ca }
            int r15 = r9.length     // Catch:{ JSONException -> 0x02ca }
            if (r14 != r15) goto L_0x02c7
            int r14 = r9.length     // Catch:{ JSONException -> 0x02ca }
            int r15 = r10.length     // Catch:{ JSONException -> 0x02ca }
            if (r14 != r15) goto L_0x02c7
            int r14 = r9.length     // Catch:{ JSONException -> 0x02ca }
            int r15 = r11.length     // Catch:{ JSONException -> 0x02ca }
            if (r14 != r15) goto L_0x02c7
            int r14 = r9.length     // Catch:{ JSONException -> 0x02ca }
            int r15 = r13.length     // Catch:{ JSONException -> 0x02ca }
            if (r14 != r15) goto L_0x02c7
            int r14 = r9.length     // Catch:{ JSONException -> 0x02ca }
            int r15 = r7.length     // Catch:{ JSONException -> 0x02ca }
            if (r14 != r15) goto L_0x02c7
            int r14 = r7.length     // Catch:{ JSONException -> 0x02ca }
            int r15 = r12.length     // Catch:{ JSONException -> 0x02ca }
            if (r14 == r15) goto L_0x013a
            goto L_0x02c7
        L_0x013a:
            r15 = 0
        L_0x013b:
            int r1 = r6.length     // Catch:{ JSONException -> 0x02ca }
            if (r15 >= r1) goto L_0x02d3
            r1 = r6[r15]     // Catch:{ JSONException -> 0x02ca }
            java.lang.String r14 = ";"
            java.lang.String[] r1 = r1.split(r14)     // Catch:{ JSONException -> 0x02ca }
            r14 = r8[r15]     // Catch:{ JSONException -> 0x02ca }
            r16 = r2
            java.lang.String r2 = ";"
            java.lang.String[] r2 = r14.split(r2)     // Catch:{ JSONException -> 0x02c5 }
            r14 = r9[r15]     // Catch:{ JSONException -> 0x02c5 }
            r17 = r6
            java.lang.String r6 = ";"
            java.lang.String[] r6 = r14.split(r6)     // Catch:{ JSONException -> 0x02c5 }
            r14 = r10[r15]     // Catch:{ JSONException -> 0x02c5 }
            r18 = r8
            java.lang.String r8 = ";"
            java.lang.String[] r8 = r14.split(r8)     // Catch:{ JSONException -> 0x02c5 }
            r14 = r11[r15]     // Catch:{ JSONException -> 0x02c5 }
            r19 = r9
            java.lang.String r9 = ";"
            java.lang.String[] r9 = r14.split(r9)     // Catch:{ JSONException -> 0x02c5 }
            int r14 = r9.length     // Catch:{ JSONException -> 0x02c5 }
            java.lang.String[] r14 = new java.lang.String[r14]     // Catch:{ JSONException -> 0x02c5 }
            r20 = r10
            int r10 = r9.length     // Catch:{ JSONException -> 0x02c5 }
            java.lang.String[] r10 = new java.lang.String[r10]     // Catch:{ JSONException -> 0x02c5 }
            r21 = r11
            int r11 = r9.length     // Catch:{ JSONException -> 0x02c5 }
            java.lang.String[] r11 = new java.lang.String[r11]     // Catch:{ JSONException -> 0x02c5 }
            r22 = r6
            int r6 = com.autonavi.minimap.R.string.bus_real_time_around_keyword     // Catch:{ JSONException -> 0x02c5 }
            r23 = r2
            r2 = r38
            java.lang.String r6 = r2.getString(r6)     // Catch:{ JSONException -> 0x02c5 }
            boolean r24 = r5.contains(r6)     // Catch:{ JSONException -> 0x02c5 }
            if (r24 == 0) goto L_0x0194
            java.lang.String r2 = ""
            java.lang.String r2 = r5.replace(r6, r2)     // Catch:{ JSONException -> 0x02c5 }
            goto L_0x0195
        L_0x0194:
            r2 = r5
        L_0x0195:
            r25 = r5
            r6 = 0
        L_0x0198:
            int r5 = r9.length     // Catch:{ JSONException -> 0x02c5 }
            if (r6 >= r5) goto L_0x01f8
            r5 = r9[r6]     // Catch:{ JSONException -> 0x02c5 }
            r26 = r12
            r12 = r9[r6]     // Catch:{ JSONException -> 0x02c5 }
            r27 = r2
            java.lang.String r2 = "--"
            int r2 = r12.indexOf(r2)     // Catch:{ JSONException -> 0x02c5 }
            int r2 = r2 + 2
            r12 = r9[r6]     // Catch:{ JSONException -> 0x02c5 }
            r28 = r8
            java.lang.String r8 = ")"
            int r8 = r12.lastIndexOf(r8)     // Catch:{ JSONException -> 0x02c5 }
            java.lang.String r2 = r5.substring(r2, r8)     // Catch:{ JSONException -> 0x02c5 }
            r14[r6] = r2     // Catch:{ JSONException -> 0x02c5 }
            r2 = r9[r6]     // Catch:{ JSONException -> 0x02c5 }
            r5 = r9[r6]     // Catch:{ JSONException -> 0x02c5 }
            java.lang.String r8 = "("
            int r5 = r5.indexOf(r8)     // Catch:{ JSONException -> 0x02c5 }
            int r5 = r5 + 1
            r8 = r9[r6]     // Catch:{ JSONException -> 0x02c5 }
            java.lang.String r12 = "--"
            int r8 = r8.indexOf(r12)     // Catch:{ JSONException -> 0x02c5 }
            java.lang.String r2 = r2.substring(r5, r8)     // Catch:{ JSONException -> 0x02c5 }
            r10[r6] = r2     // Catch:{ JSONException -> 0x02c5 }
            r2 = r9[r6]     // Catch:{ JSONException -> 0x02c5 }
            r5 = r9[r6]     // Catch:{ JSONException -> 0x02c5 }
            java.lang.String r8 = "--"
            int r5 = r5.indexOf(r8)     // Catch:{ JSONException -> 0x02c5 }
            int r5 = r5 + 2
            r8 = r9[r6]     // Catch:{ JSONException -> 0x02c5 }
            java.lang.String r12 = ")"
            int r8 = r8.lastIndexOf(r12)     // Catch:{ JSONException -> 0x02c5 }
            java.lang.String r2 = r2.substring(r5, r8)     // Catch:{ JSONException -> 0x02c5 }
            r11[r6] = r2     // Catch:{ JSONException -> 0x02c5 }
            int r6 = r6 + 1
            r12 = r26
            r2 = r27
            r8 = r28
            goto L_0x0198
        L_0x01f8:
            r27 = r2
            r28 = r8
            r26 = r12
            r2 = 0
        L_0x01ff:
            int r5 = r1.length     // Catch:{ JSONException -> 0x02c5 }
            if (r2 >= r5) goto L_0x02b1
            com.autonavi.sdk.location.LocationInstrument r5 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x02c5 }
            com.autonavi.common.model.GeoPoint r5 = r5.getLatestPosition()     // Catch:{ JSONException -> 0x02c5 }
            double r29 = r5.getLongitude()     // Catch:{ JSONException -> 0x02c5 }
            com.autonavi.sdk.location.LocationInstrument r5 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x02c5 }
            com.autonavi.common.model.GeoPoint r5 = r5.getLatestPosition()     // Catch:{ JSONException -> 0x02c5 }
            double r31 = r5.getLatitude()     // Catch:{ JSONException -> 0x02c5 }
            r5 = r7[r15]     // Catch:{ JSONException -> 0x02c5 }
            double r33 = java.lang.Double.parseDouble(r5)     // Catch:{ JSONException -> 0x02c5 }
            r5 = r13[r15]     // Catch:{ JSONException -> 0x02c5 }
            double r35 = java.lang.Double.parseDouble(r5)     // Catch:{ JSONException -> 0x02c5 }
            float r5 = defpackage.cfe.a(r29, r31, r33, r35)     // Catch:{ JSONException -> 0x02c5 }
            r6 = 1148846080(0x447a0000, float:1000.0)
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 >= 0) goto L_0x02a9
            int r5 = r3.size()     // Catch:{ JSONException -> 0x02c5 }
            r6 = 1000(0x3e8, float:1.401E-42)
            if (r5 >= r6) goto L_0x02a9
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation r5 = new com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation     // Catch:{ JSONException -> 0x02c5 }
            r5.<init>()     // Catch:{ JSONException -> 0x02c5 }
            r6 = r1[r2]     // Catch:{ JSONException -> 0x02c5 }
            r5.is_realtime = r6     // Catch:{ JSONException -> 0x02c5 }
            r5.adcode = r4     // Catch:{ JSONException -> 0x02c5 }
            r6 = r28[r2]     // Catch:{ JSONException -> 0x02c5 }
            r5.station_id = r6     // Catch:{ JSONException -> 0x02c5 }
            r6 = r27
            r5.station_name = r6     // Catch:{ JSONException -> 0x02c5 }
            r8 = r13[r15]     // Catch:{ NumberFormatException -> 0x0258 }
            double r8 = java.lang.Double.parseDouble(r8)     // Catch:{ NumberFormatException -> 0x0258 }
            java.lang.Double r8 = java.lang.Double.valueOf(r8)     // Catch:{ NumberFormatException -> 0x0258 }
            r5.station_lat = r8     // Catch:{ NumberFormatException -> 0x0258 }
            goto L_0x026a
        L_0x0258:
            com.autonavi.sdk.location.LocationInstrument r8 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x02c5 }
            com.autonavi.common.model.GeoPoint r8 = r8.getLatestPosition()     // Catch:{ JSONException -> 0x02c5 }
            double r8 = r8.getLatitude()     // Catch:{ JSONException -> 0x02c5 }
            java.lang.Double r8 = java.lang.Double.valueOf(r8)     // Catch:{ JSONException -> 0x02c5 }
            r5.station_lat = r8     // Catch:{ JSONException -> 0x02c5 }
        L_0x026a:
            r8 = r7[r15]     // Catch:{ NumberFormatException -> 0x0277 }
            double r8 = java.lang.Double.parseDouble(r8)     // Catch:{ NumberFormatException -> 0x0277 }
            java.lang.Double r8 = java.lang.Double.valueOf(r8)     // Catch:{ NumberFormatException -> 0x0277 }
            r5.station_lon = r8     // Catch:{ NumberFormatException -> 0x0277 }
            goto L_0x0289
        L_0x0277:
            com.autonavi.sdk.location.LocationInstrument r8 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x02c5 }
            com.autonavi.common.model.GeoPoint r8 = r8.getLatestPosition()     // Catch:{ JSONException -> 0x02c5 }
            double r8 = r8.getLongitude()     // Catch:{ JSONException -> 0x02c5 }
            java.lang.Double r8 = java.lang.Double.valueOf(r8)     // Catch:{ JSONException -> 0x02c5 }
            r5.station_lon = r8     // Catch:{ JSONException -> 0x02c5 }
        L_0x0289:
            java.lang.String r8 = ""
            r5.bus_areacode = r8     // Catch:{ JSONException -> 0x02c5 }
            r8 = r26[r15]     // Catch:{ JSONException -> 0x02c5 }
            r5.poiid1 = r8     // Catch:{ JSONException -> 0x02c5 }
            r8 = r23[r2]     // Catch:{ JSONException -> 0x02c5 }
            r5.bus_id = r8     // Catch:{ JSONException -> 0x02c5 }
            r8 = r22[r2]     // Catch:{ JSONException -> 0x02c5 }
            r5.bus_name = r8     // Catch:{ JSONException -> 0x02c5 }
            r8 = r14[r2]     // Catch:{ JSONException -> 0x02c5 }
            r5.bus_describe = r8     // Catch:{ JSONException -> 0x02c5 }
            r8 = r10[r2]     // Catch:{ JSONException -> 0x02c5 }
            r5.start_name = r8     // Catch:{ JSONException -> 0x02c5 }
            r8 = r11[r2]     // Catch:{ JSONException -> 0x02c5 }
            r5.end_name = r8     // Catch:{ JSONException -> 0x02c5 }
            r3.add(r5)     // Catch:{ JSONException -> 0x02c5 }
            goto L_0x02ab
        L_0x02a9:
            r6 = r27
        L_0x02ab:
            int r2 = r2 + 1
            r27 = r6
            goto L_0x01ff
        L_0x02b1:
            int r15 = r15 + 1
            r2 = r16
            r6 = r17
            r8 = r18
            r9 = r19
            r10 = r20
            r11 = r21
            r5 = r25
            r12 = r26
            goto L_0x013b
        L_0x02c5:
            r0 = move-exception
            goto L_0x02cd
        L_0x02c7:
            r16 = r2
            goto L_0x02d1
        L_0x02ca:
            r0 = move-exception
            r16 = r2
        L_0x02cd:
            r1 = r0
        L_0x02ce:
            r1.printStackTrace()
        L_0x02d1:
            r2 = r16
        L_0x02d3:
            r1 = 0
            goto L_0x000d
        L_0x02d6:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dxu.a(java.util.ArrayList, android.content.Context):java.util.ArrayList");
    }

    public static void a(AosResponseCallback<RecommendResponse> aosResponseCallback) {
        a = yq.a();
        AosPostRequest b2 = aax.b(new RealTimeBusRecommendParam());
        b = b2;
        yq.a((AosRequest) b2, aosResponseCallback);
    }
}
