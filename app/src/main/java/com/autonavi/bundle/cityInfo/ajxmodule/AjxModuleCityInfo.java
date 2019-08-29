package com.autonavi.bundle.cityInfo.ajxmodule;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.falcon.AbstractModuleCityInfo;
import java.util.List;
import org.json.JSONArray;

public class AjxModuleCityInfo extends AbstractModuleCityInfo {
    private static final String TAG = "AjxModuleCityInfo";
    private List<lj> mCityInfoList = null;
    private JSONArray mJSONAllCityInfo;

    public AjxModuleCityInfo(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void getAllCityInfo(com.autonavi.minimap.ajx3.core.JsFunctionCallback r10) {
        /*
            r9 = this;
            monitor-enter(r9)
            org.json.JSONArray r0 = r9.mJSONAllCityInfo     // Catch:{ all -> 0x018a }
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x002b
            org.json.JSONArray r0 = r9.mJSONAllCityInfo     // Catch:{ all -> 0x018a }
            int r0 = r0.length()     // Catch:{ all -> 0x018a }
            if (r0 <= 0) goto L_0x002b
            org.json.JSONArray r0 = r9.mJSONAllCityInfo     // Catch:{ all -> 0x018a }
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x018a }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x018a }
            r2[r1] = r0     // Catch:{ all -> 0x018a }
            r10.callback(r2)     // Catch:{ all -> 0x018a }
            boolean r10 = defpackage.bno.a     // Catch:{ all -> 0x018a }
            if (r10 == 0) goto L_0x0029
            java.lang.String r10 = "paas.cityinfo"
            java.lang.String r0 = "AjxModuleCityInfo"
            java.lang.String r1 = "getAllCityInfo- json mem"
            com.amap.bundle.logs.AMapLog.debug(r10, r0, r1)     // Catch:{ all -> 0x018a }
        L_0x0029:
            monitor-exit(r9)
            return
        L_0x002b:
            java.util.List<lj> r0 = r9.mCityInfoList     // Catch:{ all -> 0x018a }
            if (r0 == 0) goto L_0x0035
            int r3 = r0.size()     // Catch:{ all -> 0x018a }
            if (r3 != 0) goto L_0x0082
        L_0x0035:
            boolean r0 = defpackage.bno.a     // Catch:{ all -> 0x018a }
            if (r0 == 0) goto L_0x0042
            java.lang.String r0 = "paas.cityinfo"
            java.lang.String r3 = "AjxModuleCityInfo"
            java.lang.String r4 = "get cityInfo list"
            com.amap.bundle.logs.AMapLog.debug(r0, r3, r4)     // Catch:{ all -> 0x018a }
        L_0x0042:
            li r0 = defpackage.li.a()     // Catch:{ all -> 0x018a }
            java.lang.ref.SoftReference<java.util.ArrayList<lj>> r3 = r0.a     // Catch:{ all -> 0x018a }
            if (r3 == 0) goto L_0x0071
            java.lang.ref.SoftReference<java.util.ArrayList<lj>> r3 = r0.a     // Catch:{ all -> 0x018a }
            java.lang.Object r3 = r3.get()     // Catch:{ all -> 0x018a }
            java.util.ArrayList r3 = (java.util.ArrayList) r3     // Catch:{ all -> 0x018a }
            if (r3 == 0) goto L_0x0071
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x018a }
            r0.<init>()     // Catch:{ all -> 0x018a }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x018a }
        L_0x005d:
            boolean r4 = r3.hasNext()     // Catch:{ all -> 0x018a }
            if (r4 == 0) goto L_0x0080
            java.lang.Object r4 = r3.next()     // Catch:{ all -> 0x018a }
            lj r4 = (defpackage.lj) r4     // Catch:{ all -> 0x018a }
            lj r4 = r4.clone()     // Catch:{ all -> 0x018a }
            r0.add(r4)     // Catch:{ all -> 0x018a }
            goto L_0x005d
        L_0x0071:
            java.util.ArrayList r3 = r0.b()     // Catch:{ all -> 0x018a }
            java.util.Collections.sort(r3)     // Catch:{ all -> 0x018a }
            java.lang.ref.SoftReference r4 = new java.lang.ref.SoftReference     // Catch:{ all -> 0x018a }
            r4.<init>(r3)     // Catch:{ all -> 0x018a }
            r0.a = r4     // Catch:{ all -> 0x018a }
            r0 = r3
        L_0x0080:
            r9.mCityInfoList = r0     // Catch:{ all -> 0x018a }
        L_0x0082:
            if (r0 == 0) goto L_0x0172
            int r3 = r0.size()     // Catch:{ all -> 0x018a }
            if (r3 != 0) goto L_0x008c
            goto L_0x0172
        L_0x008c:
            boolean r3 = defpackage.bno.a     // Catch:{ all -> 0x018a }
            if (r3 == 0) goto L_0x0099
            java.lang.String r3 = "paas.cityinfo"
            java.lang.String r4 = "AjxModuleCityInfo"
            java.lang.String r5 = "build jsonArray"
            com.amap.bundle.logs.AMapLog.debug(r3, r4, r5)     // Catch:{ all -> 0x018a }
        L_0x0099:
            org.json.JSONArray r3 = new org.json.JSONArray     // Catch:{ all -> 0x018a }
            r3.<init>()     // Catch:{ all -> 0x018a }
            r9.mJSONAllCityInfo = r3     // Catch:{ all -> 0x018a }
            java.util.Iterator r3 = r0.iterator()     // Catch:{ all -> 0x018a }
        L_0x00a4:
            boolean r4 = r3.hasNext()     // Catch:{ all -> 0x018a }
            if (r4 == 0) goto L_0x0138
            java.lang.Object r4 = r3.next()     // Catch:{ all -> 0x018a }
            lj r4 = (defpackage.lj) r4     // Catch:{ all -> 0x018a }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ all -> 0x018a }
            r5.<init>()     // Catch:{ all -> 0x018a }
            java.lang.String r6 = "cityPinyin"
            java.lang.String r7 = r4.b     // Catch:{ JSONException -> 0x0132 }
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r6 = "identity"
            int r7 = r4.j     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ JSONException -> 0x0132 }
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r6 = "cityCoordX"
            int r7 = r4.f     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ JSONException -> 0x0132 }
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r6 = "cityCoordY"
            int r7 = r4.g     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ JSONException -> 0x0132 }
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r6 = "province"
            java.lang.String r7 = r4.e     // Catch:{ JSONException -> 0x0132 }
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r6 = "cityName"
            java.lang.String r7 = r4.a     // Catch:{ JSONException -> 0x0132 }
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r6 = "cityInitLetters"
            java.lang.String r7 = r4.c     // Catch:{ JSONException -> 0x0132 }
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r6 = "cityCode"
            java.lang.String r7 = r4.i     // Catch:{ JSONException -> 0x0132 }
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r6 = "level"
            int r7 = r4.h     // Catch:{ JSONException -> 0x0132 }
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r6 = "cityInitLetter"
            java.lang.String r7 = r4.d     // Catch:{ JSONException -> 0x0132 }
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r6 = "adCode"
            int r4 = r4.j     // Catch:{ JSONException -> 0x0132 }
            r5.put(r6, r4)     // Catch:{ JSONException -> 0x0132 }
            boolean r4 = defpackage.bno.a     // Catch:{ JSONException -> 0x0132 }
            if (r4 == 0) goto L_0x012b
            java.lang.String r4 = "paas.cityinfo"
            java.lang.String r6 = "AjxModuleCityInfo"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r8 = "jsonObject:"
            r7.<init>(r8)     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r8 = r5.toString()     // Catch:{ JSONException -> 0x0132 }
            r7.append(r8)     // Catch:{ JSONException -> 0x0132 }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x0132 }
            com.amap.bundle.logs.AMapLog.debug(r4, r6, r7)     // Catch:{ JSONException -> 0x0132 }
        L_0x012b:
            org.json.JSONArray r4 = r9.mJSONAllCityInfo     // Catch:{ JSONException -> 0x0132 }
            r4.put(r5)     // Catch:{ JSONException -> 0x0132 }
            goto L_0x00a4
        L_0x0132:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ all -> 0x018a }
            goto L_0x00a4
        L_0x0138:
            boolean r3 = defpackage.bno.a     // Catch:{ all -> 0x018a }
            if (r3 == 0) goto L_0x0163
            java.lang.String r3 = "paas.cityinfo"
            java.lang.String r4 = "AjxModuleCityInfo"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x018a }
            java.lang.String r6 = "callback-size:"
            r5.<init>(r6)     // Catch:{ all -> 0x018a }
            int r0 = r0.size()     // Catch:{ all -> 0x018a }
            r5.append(r0)     // Catch:{ all -> 0x018a }
            java.lang.String r0 = ",callbackData:"
            r5.append(r0)     // Catch:{ all -> 0x018a }
            org.json.JSONArray r0 = r9.mJSONAllCityInfo     // Catch:{ all -> 0x018a }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x018a }
            r5.append(r0)     // Catch:{ all -> 0x018a }
            java.lang.String r0 = r5.toString()     // Catch:{ all -> 0x018a }
            com.amap.bundle.logs.AMapLog.debug(r3, r4, r0)     // Catch:{ all -> 0x018a }
        L_0x0163:
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ all -> 0x018a }
            org.json.JSONArray r2 = r9.mJSONAllCityInfo     // Catch:{ all -> 0x018a }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x018a }
            r0[r1] = r2     // Catch:{ all -> 0x018a }
            r10.callback(r0)     // Catch:{ all -> 0x018a }
            monitor-exit(r9)
            return
        L_0x0172:
            boolean r0 = defpackage.bno.a     // Catch:{ all -> 0x018a }
            if (r0 == 0) goto L_0x017f
            java.lang.String r0 = "paas.cityinfo"
            java.lang.String r3 = "AjxModuleCityInfo"
            java.lang.String r4 = "mem city list is null"
            com.amap.bundle.logs.AMapLog.debug(r0, r3, r4)     // Catch:{ all -> 0x018a }
        L_0x017f:
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ all -> 0x018a }
            java.lang.String r2 = ""
            r0[r1] = r2     // Catch:{ all -> 0x018a }
            r10.callback(r0)     // Catch:{ all -> 0x018a }
            monitor-exit(r9)
            return
        L_0x018a:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.cityInfo.ajxmodule.AjxModuleCityInfo.getAllCityInfo(com.autonavi.minimap.ajx3.core.JsFunctionCallback):void");
    }
}
