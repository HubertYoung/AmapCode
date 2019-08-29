package defpackage;

/* renamed from: ffm reason: default package */
/* compiled from: InnerProtocolParamBuilderImpl */
public final class ffm implements ffl {
    private ffd a = null;

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0117  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0135  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0140  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0154  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01be  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x01f2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.Map<java.lang.String, java.lang.String> a(defpackage.fdf r20) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            long r3 = java.lang.System.currentTimeMillis()
            mtopsdk.mtop.intf.Mtop r5 = r2.a
            ffd r6 = r5.c
            r1.a = r6
            ffd r6 = r1.a
            fgr r6 = r6.l
            if (r6 != 0) goto L_0x001f
            java.lang.String r3 = "mtopsdk.InnerProtocolParamBuilderImpl"
            java.lang.String r2 = r2.h
            java.lang.String r4 = "ISign of mtopConfig in mtopInstance is null"
            mtopsdk.common.util.TBSdkLog.d(r3, r2, r4)
            r2 = 0
            return r2
        L_0x001f:
            mtopsdk.mtop.domain.MtopRequest r7 = r2.b
            mtopsdk.mtop.common.MtopNetworkProp r8 = r2.d
            java.util.HashMap r9 = new java.util.HashMap
            r10 = 32
            r9.<init>(r10)
            java.lang.String r10 = "utdid"
            java.lang.String r11 = mtopsdk.mtop.intf.Mtop.d()
            r9.put(r10, r11)
            java.lang.String r10 = r8.reqUserId
            boolean r10 = defpackage.fdd.a(r10)
            if (r10 == 0) goto L_0x003e
            java.lang.String r10 = r8.reqUserId
            goto L_0x0044
        L_0x003e:
            java.lang.String r10 = r8.userInfo
            java.lang.String r10 = r5.d(r10)
        L_0x0044:
            java.lang.String r11 = "uid"
            r9.put(r11, r10)
            java.lang.String r10 = r8.reqBizExt
            boolean r10 = defpackage.fdd.a(r10)
            if (r10 == 0) goto L_0x0058
            java.lang.String r10 = "reqbiz-ext"
            java.lang.String r11 = r8.reqBizExt
            r9.put(r10, r11)
        L_0x0058:
            java.lang.String r10 = r8.reqAppKey
            boolean r10 = defpackage.fdd.b(r10)
            if (r10 == 0) goto L_0x006c
            ffd r10 = r1.a
            java.lang.String r10 = r10.j
            r8.reqAppKey = r10
            ffd r10 = r1.a
            java.lang.String r10 = r10.h
            r8.authCode = r10
        L_0x006c:
            java.lang.String r10 = r8.reqAppKey
            java.lang.String r11 = r8.authCode
            java.lang.String r12 = "appKey"
            r9.put(r12, r10)
            java.lang.String r12 = r7.getData()
            boolean r13 = r8.priorityFlag
            if (r13 == 0) goto L_0x00b6
            java.util.Map<java.lang.String, java.lang.String> r13 = r8.priorityData
            if (r13 == 0) goto L_0x00b6
            org.json.JSONObject r13 = new org.json.JSONObject     // Catch:{ Exception -> 0x0098 }
            r13.<init>(r12)     // Catch:{ Exception -> 0x0098 }
            java.lang.String r14 = "x-priority-data"
            java.util.Map<java.lang.String, java.lang.String> r15 = r8.priorityData     // Catch:{ Exception -> 0x0098 }
            java.lang.String r15 = com.alibaba.fastjson.JSON.toJSONString(r15)     // Catch:{ Exception -> 0x0098 }
            r13.putOpt(r14, r15)     // Catch:{ Exception -> 0x0098 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0098 }
            r17 = r3
            goto L_0x00bc
        L_0x0098:
            r0 = move-exception
            r13 = r0
            java.lang.String r14 = "mtopsdk.InnerProtocolParamBuilderImpl"
            java.lang.String r15 = r2.h
            r16 = r12
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r17 = r3
            java.lang.String r3 = "set api priority data error, priorityData:"
            r12.<init>(r3)
            java.util.Map<java.lang.String, java.lang.String> r3 = r8.priorityData
            r12.append(r3)
            java.lang.String r3 = r12.toString()
            mtopsdk.common.util.TBSdkLog.b(r14, r15, r3, r13)
            goto L_0x00ba
        L_0x00b6:
            r17 = r3
            r16 = r12
        L_0x00ba:
            r13 = r16
        L_0x00bc:
            java.lang.String r3 = "data"
            r9.put(r3, r13)
            long r3 = mtopsdk.mtop.global.SDKUtils.getCorrectionTime()
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.String r4 = "t"
            r9.put(r4, r3)
            java.lang.String r4 = "api"
            java.lang.String r12 = r7.getApiName()
            java.util.Locale r13 = java.util.Locale.US
            java.lang.String r12 = r12.toLowerCase(r13)
            r9.put(r4, r12)
            java.lang.String r4 = "v"
            java.lang.String r12 = r7.getVersion()
            java.util.Locale r13 = java.util.Locale.US
            java.lang.String r12 = r12.toLowerCase(r13)
            r9.put(r4, r12)
            java.lang.String r4 = "sid"
            java.lang.String r12 = r8.userInfo
            java.lang.String r12 = r5.c(r12)
            r9.put(r4, r12)
            java.lang.String r4 = "ttid"
            java.lang.String r12 = r8.ttid
            r9.put(r4, r12)
            java.lang.String r4 = "deviceId"
            java.lang.String r12 = r5.b
            java.lang.String r13 = "deviceId"
            java.lang.String r12 = defpackage.fgy.a(r12, r13)
            r9.put(r4, r12)
            java.lang.String r4 = "lat"
            java.lang.String r4 = defpackage.fgy.a(r4)
            boolean r12 = defpackage.fdd.a(r4)
            if (r12 == 0) goto L_0x012d
            java.lang.String r12 = "lng"
            java.lang.String r12 = defpackage.fgy.a(r12)
            boolean r13 = defpackage.fdd.a(r12)
            if (r13 == 0) goto L_0x012d
            java.lang.String r13 = "lat"
            r9.put(r13, r4)
            java.lang.String r4 = "lng"
            r9.put(r4, r12)
        L_0x012d:
            long r12 = mtopsdk.mtop.features.MtopFeatureManager.a(r5)
            int r4 = r8.reqSource
            if (r4 <= 0) goto L_0x013c
            r4 = 11
            long r14 = mtopsdk.mtop.features.MtopFeatureManager.a(r4)
            long r12 = r12 | r14
        L_0x013c:
            boolean r4 = r8.priorityFlag
            if (r4 == 0) goto L_0x0147
            r4 = 12
            long r14 = mtopsdk.mtop.features.MtopFeatureManager.a(r4)
            long r12 = r12 | r14
        L_0x0147:
            java.lang.String r4 = "x-features"
            java.lang.String r12 = java.lang.String.valueOf(r12)
            r9.put(r4, r12)
            mtopsdk.mtop.domain.ApiTypeEnum r4 = r8.apiType
            if (r4 == 0) goto L_0x01a7
            boolean r4 = r8.isInnerOpen
            if (r4 == 0) goto L_0x0168
            java.lang.String r4 = r5.b
            java.lang.String r5 = r8.openAppKey
            java.lang.String r4 = defpackage.fdd.a(r4, r5)
            java.lang.String r5 = "accessToken"
            java.lang.String r4 = defpackage.fgy.a(r4, r5)
            r8.accessToken = r4
        L_0x0168:
            java.lang.String r4 = "exttype"
            mtopsdk.mtop.domain.ApiTypeEnum r5 = r8.apiType
            java.lang.String r5 = r5.getApiType()
            r9.put(r4, r5)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r5 = 64
            r4.<init>(r5)
            java.lang.String r5 = r8.openAppKey
            boolean r5 = defpackage.fdd.a(r5)
            if (r5 == 0) goto L_0x018c
            java.lang.String r5 = "openappkey="
            r4.append(r5)
            java.lang.String r5 = r8.openAppKey
            r4.append(r5)
        L_0x018c:
            java.lang.String r5 = r8.accessToken
            boolean r5 = defpackage.fdd.a(r5)
            if (r5 == 0) goto L_0x019e
            java.lang.String r5 = ";accesstoken="
            r4.append(r5)
            java.lang.String r5 = r8.accessToken
            r4.append(r5)
        L_0x019e:
            java.lang.String r5 = "extdata"
            java.lang.String r4 = r4.toString()
            r9.put(r5, r4)
        L_0x01a7:
            long r4 = java.lang.System.currentTimeMillis()
            java.lang.String r12 = r6.a(r9, r10, r11)
            mtopsdk.mtop.util.MtopStatistics r13 = r2.g
            long r14 = java.lang.System.currentTimeMillis()
            long r14 = r14 - r4
            r13.g = r14
            boolean r4 = defpackage.fdd.b(r12)
            if (r4 == 0) goto L_0x01f2
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r4 = 128(0x80, float:1.794E-43)
            r3.<init>(r4)
            java.lang.String r4 = "apiKey="
            r3.append(r4)
            java.lang.String r4 = r7.getKey()
            r3.append(r4)
            java.lang.String r4 = " call getMtopApiSign failed.[appKey="
            r3.append(r4)
            r3.append(r10)
            java.lang.String r4 = ", authCode="
            r3.append(r4)
            r3.append(r11)
            java.lang.String r4 = "]"
            r3.append(r4)
            java.lang.String r4 = "mtopsdk.InnerProtocolParamBuilderImpl"
            java.lang.String r2 = r2.h
            java.lang.String r3 = r3.toString()
            mtopsdk.common.util.TBSdkLog.d(r4, r2, r3)
            return r9
        L_0x01f2:
            java.lang.String r4 = "sign"
            r9.put(r4, r12)
            boolean r4 = r6 instanceof defpackage.fgt
            if (r4 != 0) goto L_0x0273
            int r4 = r8.wuaFlag
            if (r4 < 0) goto L_0x0239
            long r4 = java.lang.System.currentTimeMillis()
            int r8 = r8.wuaFlag
            java.lang.String r8 = r6.a(r12, r11, r8)
            mtopsdk.mtop.util.MtopStatistics r12 = r2.g
            long r13 = java.lang.System.currentTimeMillis()
            long r13 = r13 - r4
            r12.h = r13
            java.lang.String r4 = "wua"
            r9.put(r4, r8)
            boolean r4 = defpackage.fdd.b(r8)
            if (r4 == 0) goto L_0x0239
            java.lang.String r4 = "mtopsdk.InnerProtocolParamBuilderImpl"
            java.lang.String r5 = r2.h
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r12 = r7.getKey()
            r8.append(r12)
            java.lang.String r12 = " call getAvmpSign for wua fail."
            r8.append(r12)
            java.lang.String r8 = r8.toString()
            mtopsdk.common.util.TBSdkLog.d(r4, r5, r8)
        L_0x0239:
            long r4 = java.lang.System.currentTimeMillis()
            r8 = 8
            java.lang.String r3 = r6.a(r3, r10, r11, r8)
            mtopsdk.mtop.util.MtopStatistics r6 = r2.g
            long r10 = java.lang.System.currentTimeMillis()
            long r10 = r10 - r4
            r6.i = r10
            java.lang.String r4 = "x-mini-wua"
            r9.put(r4, r3)
            boolean r3 = defpackage.fdd.b(r3)
            if (r3 == 0) goto L_0x0273
            java.lang.String r3 = "mtopsdk.InnerProtocolParamBuilderImpl"
            java.lang.String r4 = r2.h
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = r7.getKey()
            r5.append(r6)
            java.lang.String r6 = " call getSecurityBodyDataEx for mini_wua failed."
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            mtopsdk.common.util.TBSdkLog.d(r3, r4, r5)
        L_0x0273:
            mtopsdk.mtop.common.MtopNetworkProp r3 = r2.d
            java.lang.String r4 = "pv"
            java.lang.String r5 = "5.1"
            r9.put(r4, r5)
            java.lang.String r4 = "netType"
            java.lang.String r5 = "netType"
            java.lang.String r5 = defpackage.fgy.a(r5)
            r9.put(r4, r5)
            java.lang.String r4 = "nq"
            java.lang.String r5 = "nq"
            java.lang.String r5 = defpackage.fgy.a(r5)
            r9.put(r4, r5)
            java.lang.String r4 = "umt"
            mtopsdk.mtop.intf.Mtop r5 = r2.a
            java.lang.String r5 = r5.b
            java.lang.String r6 = "umt"
            java.lang.String r5 = defpackage.fgy.a(r5, r6)
            r9.put(r4, r5)
            ffd r4 = r1.a
            java.lang.String r4 = r4.o
            boolean r5 = defpackage.fdd.a(r4)
            if (r5 == 0) goto L_0x02b0
            java.lang.String r5 = "x-app-ver"
            r9.put(r5, r4)
        L_0x02b0:
            ffd r4 = r1.a
            java.lang.String r4 = r4.r
            boolean r5 = defpackage.fdd.a(r4)
            if (r5 == 0) goto L_0x02bf
            java.lang.String r5 = "x-orange-q"
            r9.put(r5, r4)
        L_0x02bf:
            java.lang.String r4 = "x-app-conf-v"
            ffd r5 = r1.a
            long r5 = r5.s
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r9.put(r4, r5)
            java.lang.String r4 = "ua"
            java.lang.String r4 = defpackage.fgy.a(r4)
            if (r4 == 0) goto L_0x02d9
            java.lang.String r5 = "user-agent"
            r9.put(r5, r4)
        L_0x02d9:
            java.lang.String r4 = "x-c-traceid"
            java.lang.String r5 = r3.clientTraceId
            r9.put(r4, r5)
            java.lang.String r4 = "f-refer"
            java.lang.String r5 = "mtop"
            r9.put(r4, r5)
            int r4 = r3.netParam
            if (r4 <= 0) goto L_0x0339
            org.json.JSONObject r4 = new org.json.JSONObject
            r4.<init>()
            int r5 = r3.netParam
            r5 = r5 & 1
            if (r5 == 0) goto L_0x030d
            java.lang.String r5 = mtopsdk.xstate.network.NetworkStateReceiver.c
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 != 0) goto L_0x030d
            java.lang.String r6 = "SSID"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x0304 }
            goto L_0x030d
        L_0x0304:
            r0 = move-exception
            r5 = r0
            java.lang.String r6 = "mtopsdk.InnerProtocolParamBuilderImpl"
            java.lang.String r7 = "set wifi ssid error."
            mtopsdk.common.util.TBSdkLog.a(r6, r7, r5)
        L_0x030d:
            int r5 = r3.netParam
            r5 = r5 & 2
            if (r5 == 0) goto L_0x032a
            java.lang.String r5 = mtopsdk.xstate.network.NetworkStateReceiver.d
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 != 0) goto L_0x032a
            java.lang.String r6 = "BSSID"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x0321 }
            goto L_0x032a
        L_0x0321:
            r0 = move-exception
            r5 = r0
            java.lang.String r6 = "mtopsdk.InnerProtocolParamBuilderImpl"
            java.lang.String r7 = "set wifi bssid error."
            mtopsdk.common.util.TBSdkLog.a(r6, r7, r5)
        L_0x032a:
            int r5 = r4.length()
            if (r5 <= 0) goto L_0x0339
            java.lang.String r5 = "x-netinfo"
            java.lang.String r4 = r4.toString()
            r9.put(r5, r4)
        L_0x0339:
            java.lang.String r4 = r3.pageName
            if (r4 == 0) goto L_0x0344
            java.lang.String r4 = "x-page-name"
            java.lang.String r5 = r3.pageName
            r9.put(r4, r5)
        L_0x0344:
            java.lang.String r4 = r3.pageUrl
            if (r4 == 0) goto L_0x0362
            java.lang.String r4 = "x-page-url"
            java.lang.String r5 = r3.pageUrl
            r9.put(r4, r5)
            ffd r4 = r1.a
            java.util.Map<java.lang.String, java.lang.String> r4 = r4.H
            java.lang.String r3 = r3.pageUrl
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            if (r3 == 0) goto L_0x0362
            java.lang.String r4 = "x-page-mab"
            r9.put(r4, r3)
        L_0x0362:
            mtopsdk.mtop.util.MtopStatistics r2 = r2.g
            long r3 = java.lang.System.currentTimeMillis()
            long r3 = r3 - r17
            r2.f = r3
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ffm.a(fdf):java.util.Map");
    }
}
