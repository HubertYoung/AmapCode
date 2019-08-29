package com.autonavi.minimap.route.bus.realtimebus;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.searchservice.api.ISearchService;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;
import com.autonavi.minimap.route.bus.realtimebus.net.MultiThreadCallbackWrapper;
import com.autonavi.minimap.route.bus.realtimebus.net.MultiThreadCallbackWrapper.IResultProcessor;
import com.autonavi.minimap.route.net.base.resp.BusJsonResp;
import com.autonavi.minimap.route.net.base.resp.BusJsonRespCallback;
import com.autonavi.minimap.route.net.module.RTBusLocation;
import com.autonavi.minimap.route.net.module.RTBusLocationSet;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class RTBusDataManager {
    Context a;
    public a b;
    public String c;
    final Object d = new Object();
    bbm e;
    final HashMap<String, RealTimeBusAndStationMatchup> f = new HashMap<>();
    private edh g;
    private ArrayList<btd> h;
    private ArrayList<dyi> i;
    private AosResponseCallback j = new BusJsonRespCallback() {
        public final Class<?> a() {
            return RTBusLocationSet.class;
        }

        public final boolean a(BusJsonResp busJsonResp, Object obj) {
            StringBuilder sb = new StringBuilder("sendLoadAroundRequset onProcessData ");
            sb.append((String) busJsonResp.getResult());
            eao.a((String) FunctionSupportConfiger.ROUTE_BUS_TAG, sb.toString());
            boolean z = false;
            if (obj != null && (obj instanceof RTBusLocationSet)) {
                RTBusLocationSet rTBusLocationSet = (RTBusLocationSet) obj;
                if (rTBusLocationSet.getCode() == 1) {
                    z = true;
                }
                if (z) {
                    HashMap<String, RTBusLocation> buses = rTBusLocationSet.getBuses();
                    synchronized (RTBusDataManager.this.f) {
                        for (Entry next : RTBusDataManager.this.f.entrySet()) {
                            String str = (String) next.getKey();
                            RealTimeBusAndStationMatchup realTimeBusAndStationMatchup = (RealTimeBusAndStationMatchup) next.getValue();
                            if (realTimeBusAndStationMatchup != null) {
                                realTimeBusAndStationMatchup.updateRTBusStatus(buses != null ? buses.get(str) : null);
                            }
                        }
                    }
                }
            }
            return z;
        }

        public final void a(Object obj) {
            eao.a((String) FunctionSupportConfiger.ROUTE_BUS_TAG, (String) "sendLoadAroundRequset onUiSuccess");
            RTBusDataManager.this.b(RTBusDataManager.c(RTBusDataManager.this.b()));
            RTBusDataManager.this.a(DataManagerRequest.AROUND, DataManagerStatus.SUCCESS);
        }

        public final void a(AosResponseException aosResponseException) {
            StringBuilder sb = new StringBuilder("sendLoadAroundRequset onUiFailure ");
            sb.append(aosResponseException.getMessage());
            eao.a((String) FunctionSupportConfiger.ROUTE_BUS_TAG, sb.toString());
            RTBusDataManager.this.a(DataManagerRequest.AROUND, DataManagerStatus.ERROR);
        }
    };

    public enum DataManagerRequest {
        SEARCH,
        ATTENTION,
        AROUND
    }

    public enum DataManagerStatus {
        LOADING,
        EMPTY,
        ERROR,
        DATASET_CHANGED,
        SUCCESS
    }

    class POISearchCallback implements aeb<aud>, IResultProcessor<aud> {
        private boolean mIsAuto = false;
        private MultiThreadCallbackWrapper<aud> resultWrapper = new MultiThreadCallbackWrapper<>();

        POISearchCallback() {
        }

        public void onSafeSubHandler(aud aud) {
            aud aud2 = aud;
            ArrayList<btd> d = RTBusDataManager.this.d((aud2 == null || aud2.b == null) ? null : aud2.b.d);
            RTBusDataManager.this.a(d);
            RTBusDataManager rTBusDataManager = RTBusDataManager.this;
            if (d != null) {
                HashMap hashMap = new HashMap();
                if (!TextUtils.isEmpty(rTBusDataManager.c)) {
                    List<btd> c = bso.a().c(rTBusDataManager.c);
                    if (c != null) {
                        for (btd next : c) {
                            hashMap.put(next.station_id, next);
                        }
                    }
                }
                HashMap<String, RealTimeBusAndStationMatchup> a2 = dxu.a((List<btd>) d);
                HashMap hashMap2 = new HashMap();
                ArrayList arrayList = new ArrayList();
                for (btd next2 : d) {
                    dyi dyi = (dyi) hashMap2.get(next2.poiid1);
                    if (dyi == null) {
                        dyi = new dyi(next2.station_id, next2.station_name, next2.station_lon.doubleValue(), next2.station_lat.doubleValue(), next2.poiid1);
                        hashMap2.put(next2.poiid1, dyi);
                        arrayList.add(dyi);
                    }
                    RealTimeBusAndStationMatchup realTimeBusAndStationMatchup = a2.get(next2.station_id);
                    if (realTimeBusAndStationMatchup != null) {
                        realTimeBusAndStationMatchup.setFollow(hashMap.containsKey(realTimeBusAndStationMatchup.mStationID));
                        synchronized (dyi.f) {
                            if (realTimeBusAndStationMatchup != null) {
                                try {
                                    dyi.f.add(realTimeBusAndStationMatchup);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }
                }
                synchronized (rTBusDataManager.f) {
                    try {
                        rTBusDataManager.f.putAll(a2);
                    } catch (Throwable th2) {
                        while (true) {
                            throw th2;
                        }
                    }
                }
                rTBusDataManager.b(RTBusDataManager.c(arrayList));
            }
        }

        public void onSafeMainHandler(aud aud) {
            RTBusDataManager.this.a(DataManagerRequest.SEARCH, DataManagerStatus.SUCCESS);
            RTBusDataManager.this.b(true);
        }

        public void callback(aud aud) {
            synchronized (RTBusDataManager.this.d) {
                RTBusDataManager.this.e = null;
            }
            this.resultWrapper.callback(aud, this);
        }

        public void error(int i) {
            synchronized (RTBusDataManager.this.d) {
                RTBusDataManager.this.e = null;
            }
            RTBusDataManager.this.a();
            if (!this.mIsAuto) {
                RTBusDataManager.this.a(DataManagerRequest.SEARCH, DataManagerStatus.ERROR);
            }
        }

        public void error(Throwable th, boolean z) {
            synchronized (RTBusDataManager.this.d) {
                RTBusDataManager.this.e = null;
            }
            RTBusDataManager.this.a();
            if (!this.mIsAuto) {
                RTBusDataManager.this.a(DataManagerRequest.SEARCH, DataManagerStatus.ERROR);
            }
        }
    }

    public interface a {
        void a(DataManagerRequest dataManagerRequest, DataManagerStatus dataManagerStatus);
    }

    public RTBusDataManager(Context context) {
        this.a = context;
    }

    public final void a() {
        a((ArrayList<btd>) null);
        b((ArrayList<dyi>) null);
        synchronized (this.f) {
            this.f.clear();
        }
    }

    private synchronized ArrayList<btd> e() {
        if (this.h != null) {
            return (ArrayList) this.h.clone();
        }
        return new ArrayList<>();
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void a(ArrayList<btd> arrayList) {
        this.h = arrayList;
    }

    public final synchronized ArrayList<dyi> b() {
        if (this.i != null) {
            return (ArrayList) this.i.clone();
        }
        return new ArrayList<>();
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void b(ArrayList<dyi> arrayList) {
        this.i = arrayList;
    }

    public final void c() {
        synchronized (this.d) {
            if (this.e != null) {
                this.e.a();
                this.e = null;
            }
        }
    }

    public final void d() {
        synchronized (this.d) {
            if (this.g != null) {
                this.g.b();
                this.g = null;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x005e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(boolean r14) {
        /*
            r13 = this;
            java.util.ArrayList r0 = r13.e()
            java.lang.Object r1 = r13.d
            monitor-enter(r1)
            r13.d()     // Catch:{ all -> 0x0116 }
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L_0x0102
            int r4 = r0.size()     // Catch:{ all -> 0x0116 }
            if (r4 <= 0) goto L_0x0102
            java.lang.String r4 = "rtbus"
            java.lang.String r5 = "sendLoadAroundRequset"
            defpackage.eao.a(r4, r5)     // Catch:{ all -> 0x0116 }
            java.lang.String r4 = "5"
            java.lang.String r5 = "1"
            if (r14 == 0) goto L_0x0026
            java.lang.String r14 = "0"
            goto L_0x0028
        L_0x0026:
            java.lang.String r14 = "1"
        L_0x0028:
            ede r6 = defpackage.edj.a()     // Catch:{ all -> 0x0116 }
            if (r0 == 0) goto L_0x005b
            int r7 = r0.size()     // Catch:{ all -> 0x0116 }
            if (r7 > 0) goto L_0x0035
            goto L_0x005b
        L_0x0035:
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ all -> 0x0116 }
            r7.<init>()     // Catch:{ all -> 0x0116 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0116 }
        L_0x003e:
            boolean r8 = r0.hasNext()     // Catch:{ all -> 0x0116 }
            if (r8 == 0) goto L_0x005c
            java.lang.Object r8 = r0.next()     // Catch:{ all -> 0x0116 }
            btd r8 = (defpackage.btd) r8     // Catch:{ all -> 0x0116 }
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup r9 = new com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup     // Catch:{ all -> 0x0116 }
            java.lang.String r10 = r8.bus_id     // Catch:{ all -> 0x0116 }
            java.lang.String r11 = r8.station_id     // Catch:{ all -> 0x0116 }
            java.lang.String r12 = r8.bus_describe     // Catch:{ all -> 0x0116 }
            r9.<init>(r10, r11, r12)     // Catch:{ all -> 0x0116 }
            r9.mBean = r8     // Catch:{ all -> 0x0116 }
            r7.add(r9)     // Catch:{ all -> 0x0116 }
            goto L_0x003e
        L_0x005b:
            r7 = 0
        L_0x005c:
            if (r7 == 0) goto L_0x00ea
            int r0 = r7.size()     // Catch:{ all -> 0x0116 }
            if (r0 > 0) goto L_0x0066
            goto L_0x00ea
        L_0x0066:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0116 }
            r0.<init>()     // Catch:{ all -> 0x0116 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0116 }
            r8.<init>()     // Catch:{ all -> 0x0116 }
            r9 = 0
            r10 = 0
        L_0x0072:
            int r11 = r7.size()     // Catch:{ all -> 0x0116 }
            if (r9 >= r11) goto L_0x00a4
            java.lang.Object r11 = r7.get(r9)     // Catch:{ all -> 0x0116 }
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup r11 = (com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup) r11     // Catch:{ all -> 0x0116 }
            btd r12 = r11.mBean     // Catch:{ all -> 0x0116 }
            if (r12 == 0) goto L_0x00a1
            btd r12 = r11.mBean     // Catch:{ all -> 0x0116 }
            boolean r12 = r12.isRealTimeBus()     // Catch:{ all -> 0x0116 }
            if (r12 == 0) goto L_0x00a1
            if (r10 == 0) goto L_0x0096
            java.lang.String r10 = ","
            r0.append(r10)     // Catch:{ all -> 0x0116 }
            java.lang.String r10 = ","
            r8.append(r10)     // Catch:{ all -> 0x0116 }
        L_0x0096:
            java.lang.String r10 = r11.mBuslineID     // Catch:{ all -> 0x0116 }
            r0.append(r10)     // Catch:{ all -> 0x0116 }
            java.lang.String r10 = r11.mStationID     // Catch:{ all -> 0x0116 }
            r8.append(r10)     // Catch:{ all -> 0x0116 }
            r10 = 1
        L_0x00a1:
            int r9 = r9 + 1
            goto L_0x0072
        L_0x00a4:
            java.lang.String r2 = "adcode"
            java.lang.Object r3 = r7.get(r3)     // Catch:{ all -> 0x0116 }
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup r3 = (com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup) r3     // Catch:{ all -> 0x0116 }
            java.lang.String r3 = r3.adcode()     // Catch:{ all -> 0x0116 }
            r6.b(r2, r3)     // Catch:{ all -> 0x0116 }
            java.lang.String r2 = "lines"
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0116 }
            r6.b(r2, r0)     // Catch:{ all -> 0x0116 }
            java.lang.String r0 = "stations"
            java.lang.String r2 = r8.toString()     // Catch:{ all -> 0x0116 }
            r6.b(r0, r2)     // Catch:{ all -> 0x0116 }
            java.lang.String r0 = "source_type"
            r6.a(r0, r4)     // Catch:{ all -> 0x0116 }
            java.lang.String r0 = "from_page"
            r6.a(r0, r5)     // Catch:{ all -> 0x0116 }
            java.lang.String r0 = "is_refresh"
            r6.a(r0, r14)     // Catch:{ all -> 0x0116 }
            java.lang.String r14 = "need_not_depart"
            java.lang.String r0 = "false"
            r6.a(r14, r0)     // Catch:{ all -> 0x0116 }
            java.lang.String r14 = "need_bus_status"
            java.lang.String r0 = "1"
            r6.a(r14, r0)     // Catch:{ all -> 0x0116 }
            com.autonavi.minimap.route.net.base.req.BusBaseReq r14 = new com.autonavi.minimap.route.net.base.req.BusBaseReq     // Catch:{ all -> 0x0116 }
            r14.<init>(r6)     // Catch:{ all -> 0x0116 }
            goto L_0x00ef
        L_0x00ea:
            com.autonavi.minimap.route.net.base.req.BusBaseReq r14 = new com.autonavi.minimap.route.net.base.req.BusBaseReq     // Catch:{ all -> 0x0116 }
            r14.<init>(r6)     // Catch:{ all -> 0x0116 }
        L_0x00ef:
            edh r0 = new edh     // Catch:{ all -> 0x0116 }
            com.amap.bundle.aosservice.response.AosResponseCallback r2 = r13.j     // Catch:{ all -> 0x0116 }
            r0.<init>(r14, r2)     // Catch:{ all -> 0x0116 }
            r13.g = r0     // Catch:{ all -> 0x0116 }
            edh r14 = r13.g     // Catch:{ all -> 0x0116 }
            r14.a()     // Catch:{ all -> 0x0116 }
            com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager$DataManagerRequest r14 = com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager.DataManagerRequest.AROUND     // Catch:{ all -> 0x0116 }
            com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager$DataManagerStatus r0 = com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager.DataManagerStatus.LOADING     // Catch:{ all -> 0x0116 }
            goto L_0x0111
        L_0x0102:
            com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager$DataManagerRequest r14 = com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager.DataManagerRequest.AROUND     // Catch:{ all -> 0x0116 }
            bbm r0 = r13.e     // Catch:{ all -> 0x0116 }
            if (r0 == 0) goto L_0x0109
            goto L_0x010a
        L_0x0109:
            r2 = 0
        L_0x010a:
            if (r2 == 0) goto L_0x010f
            com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager$DataManagerStatus r0 = com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager.DataManagerStatus.LOADING     // Catch:{ all -> 0x0116 }
            goto L_0x0111
        L_0x010f:
            com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager$DataManagerStatus r0 = com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager.DataManagerStatus.EMPTY     // Catch:{ all -> 0x0116 }
        L_0x0111:
            r13.a(r14, r0)     // Catch:{ all -> 0x0116 }
            monitor-exit(r1)     // Catch:{ all -> 0x0116 }
            return
        L_0x0116:
            r14 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0116 }
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager.b(boolean):void");
    }

    /* access modifiers changed from: 0000 */
    public final ArrayList<btd> d(ArrayList<POI> arrayList) {
        Iterator<POI> it;
        JSONException jSONException;
        String[] strArr;
        String str;
        String[] strArr2;
        String str2;
        String[] strArr3;
        String str3;
        String str4;
        btd btc;
        RTBusDataManager rTBusDataManager = this;
        String[] strArr4 = null;
        if (arrayList == null) {
            return null;
        }
        ArrayList<btd> arrayList2 = new ArrayList<>();
        Iterator<POI> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            POI next = it2.next();
            String name = next.getName();
            HashMap<String, Serializable> poiExtra = next.getPoiExtra();
            String str5 = (String) poiExtra.get("stations");
            String str6 = (String) poiExtra.get("child_stations");
            if (!TextUtils.isEmpty(str5)) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append(LocationInstrument.getInstance().getLatestPosition().getAdCode());
                    String sb2 = sb.toString();
                    JSONObject jSONObject = new JSONObject(str5);
                    if (jSONObject.has("businfo_realbus")) {
                        try {
                            strArr = jSONObject.getString("businfo_realbus").split("\\|");
                        } catch (JSONException e2) {
                            jSONException = e2;
                            it = it2;
                            jSONException.printStackTrace();
                            it2 = it;
                            rTBusDataManager = this;
                            strArr4 = null;
                        }
                    } else {
                        strArr = strArr4;
                    }
                    String[] split = jSONObject.has("businfo_lineids") ? jSONObject.getString("businfo_lineids").split("\\|") : strArr4;
                    String[] split2 = jSONObject.has("businfo_line_keys") ? jSONObject.getString("businfo_line_keys").split("\\|") : strArr4;
                    String[] split3 = jSONObject.has("businfo_stationids") ? jSONObject.getString("businfo_stationids").split("\\|") : strArr4;
                    String[] split4 = jSONObject.has("businfo_line_names") ? jSONObject.getString("businfo_line_names").split("\\|") : strArr4;
                    String[] split5 = jSONObject.has("businfo_ids") ? jSONObject.getString("businfo_ids").split("\\|") : strArr4;
                    String[] split6 = jSONObject.has("ys") ? jSONObject.getString("ys").split("\\|") : null;
                    String[] split7 = jSONObject.has("xs") ? jSONObject.getString("xs").split("\\|") : null;
                    if (strArr == null || split == null || split2 == null || split3 == null || split4 == null || split6 == null || split7 == null || split5 == null) {
                        it = it2;
                        it2 = it;
                        rTBusDataManager = this;
                        strArr4 = null;
                    } else {
                        it = it2;
                        try {
                            if (strArr.length == split.length && split.length == split2.length && split2.length == split3.length && split2.length == split4.length && split2.length == split6.length && split2.length == split7.length) {
                                if (split7.length == split5.length) {
                                    int i2 = 0;
                                    while (i2 < strArr.length) {
                                        String[] strArr5 = strArr;
                                        String[] split8 = strArr[i2].split(";");
                                        String[] strArr6 = split;
                                        String[] split9 = split[i2].split(";");
                                        String[] strArr7 = split2;
                                        String[] split10 = split2[i2].split(";");
                                        String[] strArr8 = split3;
                                        String[] split11 = split3[i2].split(";");
                                        String[] strArr9 = split4;
                                        String[] split12 = split4[i2].split(";");
                                        String[] strArr10 = new String[split12.length];
                                        String[] strArr11 = split10;
                                        String[] strArr12 = new String[split12.length];
                                        String[] strArr13 = split9;
                                        String[] strArr14 = new String[split12.length];
                                        if (!TextUtils.isEmpty(str6)) {
                                            strArr3 = split11;
                                            JSONArray jSONArray = new JSONArray(str6);
                                            str2 = str6;
                                            String str7 = split5[i2];
                                            String str8 = "";
                                            str = sb2;
                                            strArr2 = split5;
                                            int i3 = 0;
                                            while (true) {
                                                if (i3 >= jSONArray.length()) {
                                                    break;
                                                }
                                                JSONObject jSONObject2 = jSONArray.getJSONObject(i3);
                                                JSONArray jSONArray2 = jSONArray;
                                                String string = jSONObject2.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
                                                if (string != null && string.equals(str7)) {
                                                    str8 = jSONObject2.getString("bus_alias");
                                                    break;
                                                }
                                                i3++;
                                                jSONArray = jSONArray2;
                                            }
                                            StringBuilder sb3 = new StringBuilder("(");
                                            sb3.append(str8);
                                            sb3.append(rTBusDataManager.a.getString(R.string.station_platform));
                                            sb3.append(")");
                                            String sb4 = sb3.toString();
                                            StringBuilder sb5 = new StringBuilder("(");
                                            sb5.append(rTBusDataManager.a.getString(R.string.bus_real_time_around_keyword));
                                            sb5.append(")");
                                            String sb6 = sb5.toString();
                                            if (name.contains(sb6)) {
                                                str3 = name.replace(sb6, sb4);
                                            } else {
                                                StringBuilder sb7 = new StringBuilder();
                                                sb7.append(name);
                                                sb7.append(sb4);
                                                str3 = sb7.toString();
                                            }
                                        } else {
                                            str2 = str6;
                                            str = sb2;
                                            strArr3 = split11;
                                            strArr2 = split5;
                                            str3 = name;
                                        }
                                        int i4 = 0;
                                        while (i4 < split12.length) {
                                            strArr10[i4] = split12[i4].substring(split12[i4].indexOf("--") + 2, split12[i4].lastIndexOf(")"));
                                            strArr12[i4] = split12[i4].substring(split12[i4].indexOf("(") + 1, split12[i4].indexOf("--"));
                                            strArr14[i4] = split12[i4].substring(split12[i4].indexOf("--") + 2, split12[i4].lastIndexOf(")"));
                                            i4++;
                                            name = name;
                                        }
                                        String str9 = name;
                                        int i5 = 0;
                                        while (i5 < split8.length) {
                                            if (cfe.a(LocationInstrument.getInstance().getLatestPosition().getLongitude(), LocationInstrument.getInstance().getLatestPosition().getLatitude(), Double.parseDouble(split7[i2]), Double.parseDouble(split6[i2])) >= 1000.0f || arrayList2.size() >= 1000) {
                                                str4 = str;
                                            } else {
                                                if ("1".equals(split8[i5])) {
                                                    btc = new btd();
                                                } else {
                                                    btc = new btc();
                                                }
                                                btd btd = btc;
                                                str4 = str;
                                                btd.adcode = str4;
                                                btd.station_id = strArr3[i5];
                                                btd.station_name = str3;
                                                btd.station_lat = Double.valueOf(Double.parseDouble(split6[i2]));
                                                btd.station_lon = Double.valueOf(Double.parseDouble(split7[i2]));
                                                btd.bus_areacode = "";
                                                btd.poiid1 = strArr2[i2];
                                                btd.bus_id = strArr13[i5];
                                                btd.bus_name = strArr11[i5];
                                                btd.bus_describe = strArr10[i5];
                                                btd.start_name = strArr12[i5];
                                                btd.end_name = strArr14[i5];
                                                arrayList2.add(btd);
                                            }
                                            i5++;
                                            str = str4;
                                        }
                                        i2++;
                                        sb2 = str;
                                        strArr = strArr5;
                                        split = strArr6;
                                        split2 = strArr7;
                                        split3 = strArr8;
                                        split4 = strArr9;
                                        str6 = str2;
                                        split5 = strArr2;
                                        name = str9;
                                        rTBusDataManager = this;
                                    }
                                    it2 = it;
                                    strArr4 = null;
                                }
                            }
                        } catch (JSONException e3) {
                            e = e3;
                            jSONException = e;
                            jSONException.printStackTrace();
                            it2 = it;
                            rTBusDataManager = this;
                            strArr4 = null;
                        }
                        it2 = it;
                        rTBusDataManager = this;
                        strArr4 = null;
                    }
                } catch (JSONException e4) {
                    e = e4;
                    it = it2;
                    jSONException = e;
                    jSONException.printStackTrace();
                    it2 = it;
                    rTBusDataManager = this;
                    strArr4 = null;
                }
            }
        }
        return arrayList2;
    }

    public final void a(boolean z) {
        c();
        d();
        a();
        if (TextUtils.isEmpty(this.c)) {
            a(DataManagerRequest.SEARCH, DataManagerStatus.EMPTY);
        } else if (!NetworkReachability.b()) {
            a(DataManagerRequest.SEARCH, DataManagerStatus.ERROR);
        } else {
            synchronized (this.d) {
                c();
                if (LocationInstrument.getInstance().getLatestPosition() != null) {
                    ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
                    ael ael = new ael(this.a.getString(R.string.bus_real_time_around_keyword), LocationInstrument.getInstance().getLatestPosition());
                    ael.h = 0;
                    ael.e = "1000";
                    ael.d = 1;
                    ael.k = 100;
                    SuperId.getInstance().reset();
                    SuperId.getInstance().setBit1(SuperId.BIT_1_REALTIMEBUS_BUSSTATION);
                    if (z) {
                        SuperId.getInstance().setBit2(SuperId.BIT_2_REALTIMEBUS_BUSSTATION);
                    } else {
                        SuperId.getInstance().setBit2(SuperId.BIT_2_REALTIMEBUS_BUSSTATION_AUTO);
                    }
                    if (iSearchService != null) {
                        iSearchService.b(aew.a((aem) ael), 0, new POISearchCallback());
                    }
                }
                a(DataManagerRequest.SEARCH, DataManagerStatus.LOADING);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(DataManagerRequest dataManagerRequest, DataManagerStatus dataManagerStatus) {
        a aVar = this.b;
        if (aVar != null) {
            aVar.a(dataManagerRequest, dataManagerStatus);
        }
    }

    static ArrayList<dyi> c(ArrayList<dyi> arrayList) {
        Collections.sort(arrayList, new b(LocationInstrument.getInstance().getLatestPosition().getLongitude(), LocationInstrument.getInstance().getLatestPosition().getLatitude()));
        return arrayList;
    }
}
