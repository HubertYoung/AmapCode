package com.amap.bundle.network.biz.statistic.apm;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Callback;

public class ApmUploadUtil$1 implements AosResponseCallback<AosByteResponse> {
    final /* synthetic */ Callback a;

    public ApmUploadUtil$1(Callback callback) {
        this.a = callback;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0033, code lost:
        if (r5.getInt("code") == 1) goto L_0x003b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ void onSuccess(com.amap.bundle.aosservice.response.AosResponse r5) {
        /*
            r4 = this;
            com.amap.bundle.aosservice.response.AosByteResponse r5 = (com.amap.bundle.aosservice.response.AosByteResponse) r5
            r0 = 0
            org.json.JSONObject r5 = com.amap.bundle.network.response.AbstractAOSParser.aosByteResponseToJSONObject(r5)     // Catch:{ JSONException -> 0x0057 }
            boolean r1 = defpackage.bno.a
            if (r1 == 0) goto L_0x0023
            java.lang.String r1 = "ApmUploadUtil"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "uploadLogSync callback --> "
            r2.<init>(r3)
            java.lang.String r3 = r5.toString()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.amap.bundle.logs.AMapLog.d(r1, r2)
        L_0x0023:
            java.lang.String r1 = "result"
            boolean r1 = r5.getBoolean(r1)     // Catch:{ JSONException -> 0x0036 }
            r2 = 1
            if (r1 == 0) goto L_0x003a
            java.lang.String r1 = "code"
            int r5 = r5.getInt(r1)     // Catch:{ JSONException -> 0x0036 }
            if (r5 == r2) goto L_0x003b
            goto L_0x003a
        L_0x0036:
            r5 = move-exception
            r5.printStackTrace()
        L_0x003a:
            r2 = 0
        L_0x003b:
            if (r2 == 0) goto L_0x0049
            com.autonavi.common.Callback r5 = r4.a
            r0 = 200(0xc8, float:2.8E-43)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r5.callback(r0)
            return
        L_0x0049:
            com.autonavi.common.Callback r5 = r4.a
            java.lang.Exception r1 = new java.lang.Exception
            java.lang.String r2 = "uploadFailed"
            r1.<init>(r2)
            r5.error(r1, r0)
            return
        L_0x0057:
            com.autonavi.common.Callback r5 = r4.a
            java.lang.Exception r1 = new java.lang.Exception
            java.lang.String r2 = "uploadFailed"
            r1.<init>(r2)
            r5.error(r1, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.network.biz.statistic.apm.ApmUploadUtil$1.onSuccess(com.amap.bundle.aosservice.response.AosResponse):void");
    }

    public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        StringBuilder sb = new StringBuilder("uploadLogSync error --> ");
        sb.append(aosResponseException.toString());
        AMapLog.d("ApmUploadUtil", sb.toString());
        if (this.a != null) {
            this.a.error(aosResponseException, false);
        }
    }
}
