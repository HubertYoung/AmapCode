package com.autonavi.minimap.net;

import com.alipay.sdk.util.e;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import org.json.JSONObject;

public class Callback implements AosResponseCallback<AosByteResponse> {
    private static int b;
    private final int a = 3;

    /* JADX WARNING: Removed duplicated region for block: B:38:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0066 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void onSuccess(com.amap.bundle.aosservice.response.AosResponse r18) {
        /*
            r17 = this;
            r0 = r18
            com.amap.bundle.aosservice.response.AosByteResponse r0 = (com.amap.bundle.aosservice.response.AosByteResponse) r0
            r1 = 0
            b = r1
            boolean r1 = a(r0)
            if (r1 != 0) goto L_0x0015
            java.lang.String r0 = ""
            java.lang.String r1 = "failed"
            defpackage.emd.a(r0, r1)
            return
        L_0x0015:
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference
            java.lang.String r2 = "AfpSplashEvents"
            r1.<init>(r2)
            java.lang.String r2 = "afp_splash_events"
            r1.remove(r2)
            org.json.JSONObject r0 = b(r0)
            if (r0 != 0) goto L_0x0030
            java.lang.String r0 = ""
            java.lang.String r1 = "successed"
            defpackage.emd.a(r0, r1)
            return
        L_0x0030:
            java.lang.String r1 = "session_id"
            java.lang.String r1 = r0.optString(r1)
            java.lang.String r2 = "successed"
            defpackage.emd.a(r1, r2)
            java.util.ArrayList r1 = defpackage.chg.a(r0)
            boolean r2 = r1.isEmpty()
            if (r2 == 0) goto L_0x0051
            java.lang.String r0 = "basemap.splashscreen"
            java.lang.String r1 = "data"
            java.lang.String r2 = " no valid splash ad in response"
            com.amap.bundle.logs.AMapLog.error(r0, r1, r2)
            return
        L_0x0051:
            java.lang.String r0 = r0.toString()
            defpackage.chh.a(r0)
            dea r0 = defpackage.dea.a()
            boolean r2 = r1.isEmpty()
            if (r2 != 0) goto L_0x018d
            java.util.Iterator r1 = r1.iterator()
        L_0x0066:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x018d
            java.lang.Object r2 = r1.next()
            r11 = r2
            chi r11 = (defpackage.chi) r11
            java.lang.String r12 = r11.f
            boolean r2 = android.text.TextUtils.isEmpty(r12)
            if (r2 != 0) goto L_0x0066
            java.lang.String r7 = r11.j
            boolean r2 = android.text.TextUtils.isEmpty(r7)
            if (r2 != 0) goto L_0x0066
            java.lang.String r2 = defpackage.agy.a(r7)
            java.io.File r9 = new java.io.File
            java.lang.String r3 = defpackage.dea.a
            java.lang.String r4 = "i_"
            java.lang.String r5 = java.lang.String.valueOf(r2)
            java.lang.String r4 = r4.concat(r5)
            r9.<init>(r3, r4)
            java.io.File r10 = new java.io.File
            java.lang.String r3 = defpackage.dea.a
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "i_"
            r4.<init>(r5)
            r4.append(r2)
            java.lang.String r2 = ".tmp"
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            r10.<init>(r3, r2)
            java.lang.String r2 = "video"
            boolean r2 = r2.equals(r12)
            if (r2 != 0) goto L_0x00db
            java.lang.String r2 = "dynamic_image"
            boolean r2 = r2.equals(r12)
            if (r2 == 0) goto L_0x00c4
            goto L_0x00db
        L_0x00c4:
            java.lang.String r2 = "static_image"
            boolean r2 = r2.equals(r12)
            if (r2 == 0) goto L_0x0066
            ddz r8 = new ddz
            r8.<init>(r11, r12, r10)
            r3 = r11
            r4 = r12
            r5 = r7
            r6 = r9
            r7 = r10
            defpackage.dea.a(r3, r4, r5, r6, r7, r8)
            goto L_0x0066
        L_0x00db:
            java.lang.String r2 = "video"
            boolean r2 = r2.equals(r12)
            r3 = 0
            if (r2 == 0) goto L_0x00e9
            java.lang.String r2 = r11.g
        L_0x00e7:
            r13 = r2
            goto L_0x00f5
        L_0x00e9:
            java.lang.String r2 = "dynamic_image"
            boolean r2 = r2.equals(r12)
            if (r2 == 0) goto L_0x00f4
            java.lang.String r2 = r11.i
            goto L_0x00e7
        L_0x00f4:
            r13 = r3
        L_0x00f5:
            boolean r2 = android.text.TextUtils.isEmpty(r13)
            if (r2 != 0) goto L_0x0066
            java.lang.String r2 = defpackage.agy.a(r13)
            java.lang.String r4 = "video"
            boolean r4 = r4.equals(r12)
            if (r4 == 0) goto L_0x010c
            java.lang.String r3 = "v_"
            goto L_0x0116
        L_0x010c:
            java.lang.String r4 = "dynamic_image"
            boolean r4 = r4.equals(r12)
            if (r4 == 0) goto L_0x0116
            java.lang.String r3 = "g_"
        L_0x0116:
            java.io.File r14 = new java.io.File
            java.lang.String r4 = defpackage.dea.a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            r14.<init>(r4, r5)
            boolean r4 = r14.exists()
            if (r4 == 0) goto L_0x013b
            long r2 = java.lang.System.currentTimeMillis()
            r14.setLastModified(r2)
            goto L_0x0066
        L_0x013b:
            r4 = 4
            android.app.Application r5 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r5 = defpackage.aaw.b(r5)
            if (r4 != r5) goto L_0x0179
            java.io.File r15 = new java.io.File
            java.lang.String r4 = defpackage.dea.a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            r5.append(r2)
            java.lang.String r2 = ".tmp"
            r5.append(r2)
            java.lang.String r2 = r5.toString()
            r15.<init>(r4, r2)
            dea$1 r16 = new dea$1
            r2 = r16
            r3 = r0
            r4 = r11
            r5 = r12
            r6 = r15
            r8 = r11
            r2.<init>(r4, r5, r6, r7, r8, r9, r10)
            r3 = r11
            r4 = r12
            r5 = r13
            r6 = r14
            r7 = r15
            r8 = r16
            defpackage.dea.a(r3, r4, r5, r6, r7, r8)
            goto L_0x0066
        L_0x0179:
            java.lang.String r4 = "static_image"
            ddz r8 = new ddz
            java.lang.String r2 = "static_image"
            r8.<init>(r11, r2, r10)
            r3 = r11
            r5 = r7
            r6 = r9
            r7 = r10
            defpackage.dea.a(r3, r4, r5, r6, r7, r8)
            goto L_0x0066
        L_0x018d:
            defpackage.chh.b()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.net.Callback.onSuccess(com.amap.bundle.aosservice.response.AosResponse):void");
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (!NetworkReachability.b() || b >= 3) {
            emd.a("", e.b);
            b = 0;
            return;
        }
        b++;
        dsa.a();
    }

    private static boolean a(AosByteResponse aosByteResponse) {
        if (aosByteResponse == null || aosByteResponse.getResult() == null) {
            return false;
        }
        JSONObject jSONObject = null;
        try {
            jSONObject = new JSONObject(new String((byte[]) aosByteResponse.getResult(), "UTF-8").trim());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (jSONObject == null) {
            return false;
        }
        String optString = jSONObject.optString("code");
        if ("1".equals(optString) || "7".equals(optString)) {
            return true;
        }
        AMapLog.error("basemap.splashscreen", "data", " fail when request splash ad,code: ".concat(String.valueOf(optString)));
        return false;
    }

    private static JSONObject b(AosByteResponse aosByteResponse) {
        JSONObject jSONObject;
        if (aosByteResponse == null || aosByteResponse.getResult() == null) {
            return null;
        }
        try {
            jSONObject = new JSONObject(new String((byte[]) aosByteResponse.getResult(), "UTF-8").trim());
        } catch (Throwable th) {
            th.printStackTrace();
            jSONObject = null;
        }
        if (jSONObject == null) {
            return null;
        }
        String optString = jSONObject.optString("code");
        if ("1".equals(optString) || "7".equals(optString)) {
            return jSONObject.optJSONObject("data");
        }
        return null;
    }
}
