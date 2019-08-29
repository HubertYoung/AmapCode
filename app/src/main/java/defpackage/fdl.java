package defpackage;

import android.support.annotation.NonNull;

/* renamed from: fdl reason: default package */
/* compiled from: ErrorCodeMappingAfterFilter */
public final class fdl implements fdg {
    @NonNull
    public final String a() {
        return "mtopsdk.ErrorCodeMappingAfterFilter";
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:108:0x0291=Splitter:B:108:0x0291, B:79:0x01a6=Splitter:B:79:0x01a6} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String a(defpackage.fdf r10) {
        /*
            r9 = this;
            java.lang.String r0 = r10.h
            java.lang.String r1 = "CONTINUE"
            defpackage.fff.a()
            boolean r2 = defpackage.fff.b()
            if (r2 != 0) goto L_0x0015
            java.lang.String r10 = "mtopsdk.ErrorCodeMappingAfterFilter"
            java.lang.String r2 = "GlobalErrorCodeMappingOpen=false,Don't do ErrorCode Mapping."
            mtopsdk.common.util.TBSdkLog.b(r10, r0, r2)
            return r1
        L_0x0015:
            mtopsdk.mtop.domain.MtopResponse r2 = r10.c
            boolean r3 = r2.isApiSuccess()
            if (r3 == 0) goto L_0x001e
            return r1
        L_0x001e:
            r3 = 128(0x80, float:1.794E-43)
            boolean r4 = r2.isNetworkError()     // Catch:{ all -> 0x02c9 }
            r5 = 1
            if (r4 == 0) goto L_0x00aa
            java.lang.String r4 = r2.getRetCode()     // Catch:{ all -> 0x02c9 }
            java.lang.String r4 = mtopsdk.mtop.util.ErrorConstant.a(r4)     // Catch:{ all -> 0x02c9 }
            r2.mappingCodeSuffix = r4     // Catch:{ all -> 0x02c9 }
            int r4 = r2.getResponseCode()     // Catch:{ all -> 0x02c9 }
            java.lang.String r6 = r2.mappingCodeSuffix     // Catch:{ all -> 0x02c9 }
            java.lang.String r4 = mtopsdk.mtop.util.ErrorConstant.a(r4, r6)     // Catch:{ all -> 0x02c9 }
            r2.mappingCode = r4     // Catch:{ all -> 0x02c9 }
            java.util.Map<java.lang.String, java.lang.String> r4 = defpackage.fff.d     // Catch:{ all -> 0x02c9 }
            java.lang.String r6 = "NETWORK_ERROR_MAPPING"
            java.lang.Object r4 = r4.get(r6)     // Catch:{ all -> 0x02c9 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ all -> 0x02c9 }
            if (r4 == 0) goto L_0x004a
            goto L_0x004c
        L_0x004a:
            java.lang.String r4 = "网络竟然崩溃了"
        L_0x004c:
            r2.setRetMsg(r4)     // Catch:{ all -> 0x02c9 }
            mtopsdk.mtop.util.MtopStatistics r10 = r10.g     // Catch:{ all -> 0x02c9 }
            r10.p = r5     // Catch:{ all -> 0x02c9 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r3)
        L_0x0058:
            java.lang.String r3 = "api="
            r10.append(r3)
            java.lang.String r3 = r2.getApi()
            r10.append(r3)
            java.lang.String r3 = " , v="
            r10.append(r3)
            java.lang.String r3 = r2.getV()
            r10.append(r3)
            java.lang.String r3 = " , retCode="
            r10.append(r3)
            java.lang.String r3 = r2.getRetCode()
            r10.append(r3)
            java.lang.String r3 = " , retMsg="
            r10.append(r3)
            java.lang.String r3 = r2.getRetMsg()
            r10.append(r3)
            java.lang.String r3 = " , mappingCode="
            r10.append(r3)
            java.lang.String r3 = r2.getMappingCode()
            r10.append(r3)
            java.lang.String r3 = " , responseHeader="
            r10.append(r3)
            java.util.Map r2 = r2.getHeaderFields()
            r10.append(r2)
            java.lang.String r2 = "mtopsdk.ErrorCodeMappingAfterFilter"
            java.lang.String r10 = r10.toString()
            mtopsdk.common.util.TBSdkLog.d(r2, r0, r10)
            return r1
        L_0x00aa:
            mtopsdk.mtop.util.MtopStatistics r4 = r10.g     // Catch:{ all -> 0x02c9 }
            r6 = 2
            r4.p = r6     // Catch:{ all -> 0x02c9 }
            boolean r4 = r2.is41XResult()     // Catch:{ all -> 0x02c9 }
            if (r4 != 0) goto L_0x0291
            boolean r4 = r2.isApiLockedResult()     // Catch:{ all -> 0x02c9 }
            if (r4 == 0) goto L_0x00bd
            goto L_0x0291
        L_0x00bd:
            boolean r4 = r2.isMtopServerError()     // Catch:{ all -> 0x02c9 }
            if (r4 == 0) goto L_0x0103
            java.lang.String r10 = r2.mappingCodeSuffix     // Catch:{ all -> 0x02c9 }
            boolean r10 = defpackage.fdd.b(r10)     // Catch:{ all -> 0x02c9 }
            if (r10 == 0) goto L_0x00de
            java.lang.String r10 = r2.getRetCode()     // Catch:{ all -> 0x02c9 }
            java.lang.String r10 = mtopsdk.mtop.util.ErrorConstant.a(r10)     // Catch:{ all -> 0x02c9 }
            boolean r4 = defpackage.fdd.a(r10)     // Catch:{ all -> 0x02c9 }
            if (r4 == 0) goto L_0x00da
            goto L_0x00dc
        L_0x00da:
            java.lang.String r10 = "ES00000"
        L_0x00dc:
            r2.mappingCodeSuffix = r10     // Catch:{ all -> 0x02c9 }
        L_0x00de:
            int r10 = r2.getResponseCode()     // Catch:{ all -> 0x02c9 }
            java.lang.String r4 = r2.mappingCodeSuffix     // Catch:{ all -> 0x02c9 }
            java.lang.String r10 = mtopsdk.mtop.util.ErrorConstant.a(r10, r4)     // Catch:{ all -> 0x02c9 }
            r2.mappingCode = r10     // Catch:{ all -> 0x02c9 }
            java.util.Map<java.lang.String, java.lang.String> r10 = defpackage.fff.d     // Catch:{ all -> 0x02c9 }
            java.lang.String r4 = "SERVICE_ERROR_MAPPING"
            java.lang.Object r10 = r10.get(r4)     // Catch:{ all -> 0x02c9 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ all -> 0x02c9 }
            if (r10 == 0) goto L_0x00f7
            goto L_0x00f9
        L_0x00f7:
            java.lang.String r10 = "服务竟然出错了"
        L_0x00f9:
            r2.setRetMsg(r10)     // Catch:{ all -> 0x02c9 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r3)
            goto L_0x0058
        L_0x0103:
            boolean r4 = r2.isMtopSdkError()     // Catch:{ all -> 0x02c9 }
            if (r4 == 0) goto L_0x014d
            java.lang.String r10 = r2.getRetCode()     // Catch:{ all -> 0x02c9 }
            java.lang.String r4 = mtopsdk.mtop.util.ErrorConstant.a(r10)     // Catch:{ all -> 0x02c9 }
            if (r10 == 0) goto L_0x011d
            java.lang.String r5 = "ANDROID_SYS_GENERATE_MTOP_SIGN_ERROR"
            boolean r10 = r10.startsWith(r5)     // Catch:{ all -> 0x02c9 }
            if (r10 == 0) goto L_0x011d
            java.lang.String r4 = "EC40002"
        L_0x011d:
            boolean r10 = defpackage.fdd.a(r4)     // Catch:{ all -> 0x02c9 }
            if (r10 == 0) goto L_0x0124
            goto L_0x0126
        L_0x0124:
            java.lang.String r4 = "EC00000"
        L_0x0126:
            r2.mappingCodeSuffix = r4     // Catch:{ all -> 0x02c9 }
            int r10 = r2.getResponseCode()     // Catch:{ all -> 0x02c9 }
            java.lang.String r4 = r2.mappingCodeSuffix     // Catch:{ all -> 0x02c9 }
            java.lang.String r10 = mtopsdk.mtop.util.ErrorConstant.a(r10, r4)     // Catch:{ all -> 0x02c9 }
            r2.mappingCode = r10     // Catch:{ all -> 0x02c9 }
            java.util.Map<java.lang.String, java.lang.String> r10 = defpackage.fff.d     // Catch:{ all -> 0x02c9 }
            java.lang.String r4 = "SERVICE_ERROR_MAPPING"
            java.lang.Object r10 = r10.get(r4)     // Catch:{ all -> 0x02c9 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ all -> 0x02c9 }
            if (r10 == 0) goto L_0x0141
            goto L_0x0143
        L_0x0141:
            java.lang.String r10 = "服务竟然出错了"
        L_0x0143:
            r2.setRetMsg(r10)     // Catch:{ all -> 0x02c9 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r3)
            goto L_0x0058
        L_0x014d:
            mtopsdk.mtop.util.MtopStatistics r4 = r10.g     // Catch:{ all -> 0x02c9 }
            r6 = 3
            r4.p = r6     // Catch:{ all -> 0x02c9 }
            java.lang.String r4 = r2.mappingCodeSuffix     // Catch:{ all -> 0x02c9 }
            boolean r4 = defpackage.fdd.a(r4)     // Catch:{ all -> 0x02c9 }
            if (r4 == 0) goto L_0x0165
            java.lang.String r10 = r2.mappingCodeSuffix     // Catch:{ all -> 0x02c9 }
            r2.mappingCode = r10     // Catch:{ all -> 0x02c9 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r3)
            goto L_0x0058
        L_0x0165:
            java.lang.String r4 = r2.getRetCode()     // Catch:{ all -> 0x02c9 }
            r2.mappingCode = r4     // Catch:{ all -> 0x02c9 }
            boolean r6 = defpackage.fdd.b(r4)     // Catch:{ all -> 0x02c9 }
            if (r6 == 0) goto L_0x0178
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r3)
            goto L_0x0058
        L_0x0178:
            defpackage.fff.a()     // Catch:{ all -> 0x02c9 }
            boolean r6 = defpackage.fff.c()     // Catch:{ all -> 0x02c9 }
            if (r6 != 0) goto L_0x018f
            java.lang.String r10 = "mtopsdk.ErrorCodeMappingAfterFilter"
            java.lang.String r4 = "BizErrorCodeMappingOpen=false,Don't do BizErrorCode Mapping."
            mtopsdk.common.util.TBSdkLog.b(r10, r0, r4)     // Catch:{ all -> 0x02c9 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r3)
            goto L_0x0058
        L_0x018f:
            int r6 = r4.length()     // Catch:{ all -> 0x02c9 }
            r7 = 17
            if (r6 != r7) goto L_0x01a6
            char r5 = r4.charAt(r5)     // Catch:{ all -> 0x02c9 }
            r6 = 45
            if (r5 != r6) goto L_0x01a6
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r3)
            goto L_0x0058
        L_0x01a6:
            fff r5 = defpackage.fff.a()     // Catch:{ all -> 0x02c9 }
            java.util.Set<java.lang.String> r5 = r5.c     // Catch:{ all -> 0x02c9 }
            if (r5 == 0) goto L_0x01de
            mtopsdk.mtop.domain.MtopRequest r10 = r10.b     // Catch:{ all -> 0x02c9 }
            java.lang.String r10 = r10.getKey()     // Catch:{ all -> 0x02c9 }
            fff r5 = defpackage.fff.a()     // Catch:{ all -> 0x02c9 }
            java.util.Set<java.lang.String> r5 = r5.c     // Catch:{ all -> 0x02c9 }
            boolean r5 = r5.contains(r10)     // Catch:{ all -> 0x02c9 }
            if (r5 == 0) goto L_0x01de
            mtopsdk.common.util.TBSdkLog$LogEnable r4 = mtopsdk.common.util.TBSdkLog.LogEnable.InfoEnable     // Catch:{ all -> 0x02c9 }
            boolean r4 = mtopsdk.common.util.TBSdkLog.a(r4)     // Catch:{ all -> 0x02c9 }
            if (r4 == 0) goto L_0x01d7
            java.lang.String r4 = "mtopsdk.ErrorCodeMappingAfterFilter"
            java.lang.String r5 = "apiKey in degradeBizErrorMappingApiSet,apiKey="
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch:{ all -> 0x02c9 }
            java.lang.String r10 = r5.concat(r10)     // Catch:{ all -> 0x02c9 }
            mtopsdk.common.util.TBSdkLog.b(r4, r0, r10)     // Catch:{ all -> 0x02c9 }
        L_0x01d7:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r3)
            goto L_0x0058
        L_0x01de:
            boolean r10 = defpackage.fdb.e(r4)     // Catch:{ Exception -> 0x022a }
            if (r10 == 0) goto L_0x01fe
            java.lang.String r10 = "TERR00000"
            r2.mappingCode = r10     // Catch:{ Exception -> 0x022a }
            java.lang.String r10 = "mtopsdk.ErrorCodeMappingAfterFilter"
            java.lang.String r5 = "retCode contain chinese character,retCode="
            java.lang.String r6 = java.lang.String.valueOf(r4)     // Catch:{ Exception -> 0x022a }
            java.lang.String r5 = r5.concat(r6)     // Catch:{ Exception -> 0x022a }
            mtopsdk.common.util.TBSdkLog.d(r10, r0, r5)     // Catch:{ Exception -> 0x022a }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r3)
            goto L_0x0058
        L_0x01fe:
            java.lang.String r10 = defpackage.fdb.d(r4)     // Catch:{ Exception -> 0x022a }
            boolean r5 = defpackage.fdd.a(r10)     // Catch:{ Exception -> 0x022a }
            if (r5 == 0) goto L_0x023a
            defpackage.fff.a()     // Catch:{ Exception -> 0x022a }
            long r5 = defpackage.fff.h()     // Catch:{ Exception -> 0x022a }
            int r7 = r10.length()     // Catch:{ Exception -> 0x022a }
            long r7 = (long) r7     // Catch:{ Exception -> 0x022a }
            int r7 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x0227
            r7 = 0
            int r7 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r7 <= 0) goto L_0x0227
            r7 = 0
            int r5 = (int) r5     // Catch:{ Exception -> 0x022a }
            java.lang.String r10 = r10.substring(r7, r5)     // Catch:{ Exception -> 0x022a }
            r2.mappingCode = r10     // Catch:{ Exception -> 0x022a }
            goto L_0x023a
        L_0x0227:
            r2.mappingCode = r10     // Catch:{ Exception -> 0x022a }
            goto L_0x023a
        L_0x022a:
            r10 = move-exception
            java.lang.String r5 = "mtopsdk.ErrorCodeMappingAfterFilter"
            java.lang.String r6 = "Mapping biz retCode to mappingCode error.retCode="
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x02c9 }
            java.lang.String r4 = r6.concat(r4)     // Catch:{ all -> 0x02c9 }
            mtopsdk.common.util.TBSdkLog.b(r5, r0, r4, r10)     // Catch:{ all -> 0x02c9 }
        L_0x023a:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r3)
            java.lang.String r3 = "api="
            r10.append(r3)
            java.lang.String r3 = r2.getApi()
            r10.append(r3)
            java.lang.String r3 = " , v="
            r10.append(r3)
            java.lang.String r3 = r2.getV()
            r10.append(r3)
            java.lang.String r3 = " , retCode="
            r10.append(r3)
            java.lang.String r3 = r2.getRetCode()
            r10.append(r3)
            java.lang.String r3 = " , retMsg="
            r10.append(r3)
            java.lang.String r3 = r2.getRetMsg()
            r10.append(r3)
            java.lang.String r3 = " , mappingCode="
            r10.append(r3)
            java.lang.String r3 = r2.getMappingCode()
            r10.append(r3)
            java.lang.String r3 = " , responseHeader="
            r10.append(r3)
            java.util.Map r2 = r2.getHeaderFields()
            r10.append(r2)
            java.lang.String r2 = "mtopsdk.ErrorCodeMappingAfterFilter"
            java.lang.String r10 = r10.toString()
            mtopsdk.common.util.TBSdkLog.d(r2, r0, r10)
            return r1
        L_0x0291:
            java.lang.String r10 = r2.getRetCode()     // Catch:{ all -> 0x02c9 }
            java.lang.String r10 = mtopsdk.mtop.util.ErrorConstant.a(r10)     // Catch:{ all -> 0x02c9 }
            boolean r4 = defpackage.fdd.a(r10)     // Catch:{ all -> 0x02c9 }
            if (r4 == 0) goto L_0x02a0
            goto L_0x02a2
        L_0x02a0:
            java.lang.String r10 = "ES00000"
        L_0x02a2:
            r2.mappingCodeSuffix = r10     // Catch:{ all -> 0x02c9 }
            int r10 = r2.getResponseCode()     // Catch:{ all -> 0x02c9 }
            java.lang.String r4 = r2.mappingCodeSuffix     // Catch:{ all -> 0x02c9 }
            java.lang.String r10 = mtopsdk.mtop.util.ErrorConstant.a(r10, r4)     // Catch:{ all -> 0x02c9 }
            r2.mappingCode = r10     // Catch:{ all -> 0x02c9 }
            java.util.Map<java.lang.String, java.lang.String> r10 = defpackage.fff.d     // Catch:{ all -> 0x02c9 }
            java.lang.String r4 = "FLOW_LIMIT_ERROR_MAPPING"
            java.lang.Object r10 = r10.get(r4)     // Catch:{ all -> 0x02c9 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ all -> 0x02c9 }
            if (r10 == 0) goto L_0x02bd
            goto L_0x02bf
        L_0x02bd:
            java.lang.String r10 = "前方拥挤，亲稍等再试试"
        L_0x02bf:
            r2.setRetMsg(r10)     // Catch:{ all -> 0x02c9 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r3)
            goto L_0x0058
        L_0x02c9:
            r10 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r3)
            java.lang.String r3 = "api="
            r1.append(r3)
            java.lang.String r3 = r2.getApi()
            r1.append(r3)
            java.lang.String r3 = " , v="
            r1.append(r3)
            java.lang.String r3 = r2.getV()
            r1.append(r3)
            java.lang.String r3 = " , retCode="
            r1.append(r3)
            java.lang.String r3 = r2.getRetCode()
            r1.append(r3)
            java.lang.String r3 = " , retMsg="
            r1.append(r3)
            java.lang.String r3 = r2.getRetMsg()
            r1.append(r3)
            java.lang.String r3 = " , mappingCode="
            r1.append(r3)
            java.lang.String r3 = r2.getMappingCode()
            r1.append(r3)
            java.lang.String r3 = " , responseHeader="
            r1.append(r3)
            java.util.Map r2 = r2.getHeaderFields()
            r1.append(r2)
            java.lang.String r2 = "mtopsdk.ErrorCodeMappingAfterFilter"
            java.lang.String r1 = r1.toString()
            mtopsdk.common.util.TBSdkLog.d(r2, r0, r1)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fdl.a(fdf):java.lang.String");
    }
}
