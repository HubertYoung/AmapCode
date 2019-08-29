package defpackage;

/* renamed from: fds reason: default package */
/* compiled from: ProtocolParamBuilderBeforeFilter */
public final class fds implements fdh {
    private ffl a;

    public final String a() {
        return "mtopsdk.ProtocolParamBuilderBeforeFilter";
    }

    public fds(ffl ffl) {
        this.a = ffl;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0086  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String b(defpackage.fdf r9) {
        /*
            r8 = this;
            mtopsdk.mtop.domain.MtopRequest r0 = r9.b
            r1 = 0
            ffl r2 = r8.a     // Catch:{ Throwable -> 0x005e }
            java.util.Map r2 = r2.a(r9)     // Catch:{ Throwable -> 0x005e }
            if (r2 != 0) goto L_0x001f
            mtopsdk.mtop.domain.MtopResponse r1 = new mtopsdk.mtop.domain.MtopResponse     // Catch:{ Throwable -> 0x001d }
            java.lang.String r3 = r0.getApiName()     // Catch:{ Throwable -> 0x001d }
            java.lang.String r4 = r0.getVersion()     // Catch:{ Throwable -> 0x001d }
            java.lang.String r5 = "ANDROID_SYS_INIT_MTOP_ISIGN_ERROR"
            java.lang.String r6 = "初始化Mtop签名类ISign失败"
            r1.<init>(r3, r4, r5, r6)     // Catch:{ Throwable -> 0x001d }
            goto L_0x007c
        L_0x001d:
            r1 = move-exception
            goto L_0x0062
        L_0x001f:
            java.lang.String r3 = "sign"
            java.lang.Object r3 = r2.get(r3)     // Catch:{ Throwable -> 0x001d }
            if (r3 != 0) goto L_0x007c
            java.lang.String r1 = "SG_ERROR_CODE"
            java.lang.Object r1 = r2.get(r1)     // Catch:{ Throwable -> 0x001d }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x001d }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x001d }
            r4 = 48
            r3.<init>(r4)     // Catch:{ Throwable -> 0x001d }
            java.lang.String r4 = "ANDROID_SYS_GENERATE_MTOP_SIGN_ERROR"
            r3.append(r4)     // Catch:{ Throwable -> 0x001d }
            if (r1 == 0) goto L_0x004a
            java.lang.String r4 = "("
            r3.append(r4)     // Catch:{ Throwable -> 0x001d }
            r3.append(r1)     // Catch:{ Throwable -> 0x001d }
            java.lang.String r1 = ")"
            r3.append(r1)     // Catch:{ Throwable -> 0x001d }
        L_0x004a:
            mtopsdk.mtop.domain.MtopResponse r1 = new mtopsdk.mtop.domain.MtopResponse     // Catch:{ Throwable -> 0x001d }
            java.lang.String r4 = r0.getApiName()     // Catch:{ Throwable -> 0x001d }
            java.lang.String r5 = r0.getVersion()     // Catch:{ Throwable -> 0x001d }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x001d }
            java.lang.String r6 = "生成Mtop签名sign失败"
            r1.<init>(r4, r5, r3, r6)     // Catch:{ Throwable -> 0x001d }
            goto L_0x007c
        L_0x005e:
            r2 = move-exception
            r7 = r2
            r2 = r1
            r1 = r7
        L_0x0062:
            java.lang.String r3 = "mtopsdk.ProtocolParamBuilderBeforeFilter"
            java.lang.String r4 = r9.h
            java.lang.String r5 = "[deBefore]execute ProtocolParamBuilder buildParams error."
            mtopsdk.common.util.TBSdkLog.b(r3, r4, r5, r1)
            mtopsdk.mtop.domain.MtopResponse r1 = new mtopsdk.mtop.domain.MtopResponse
            java.lang.String r3 = r0.getApiName()
            java.lang.String r0 = r0.getVersion()
            java.lang.String r4 = "ANDROID_SYS_BUILD_PROTOCOL_PARAMS_ERROR"
            java.lang.String r5 = "组装MTOP协议参数错误"
            r1.<init>(r3, r0, r4, r5)
        L_0x007c:
            if (r1 == 0) goto L_0x0086
            r9.c = r1
            defpackage.fed.a(r9)
            java.lang.String r9 = "STOP"
            return r9
        L_0x0086:
            r9.i = r2
            java.lang.String r9 = "CONTINUE"
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fds.b(fdf):java.lang.String");
    }
}
