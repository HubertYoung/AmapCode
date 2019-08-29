package defpackage;

import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.mtop.common.MtopNetworkProp;
import mtopsdk.mtop.domain.EnvModeEnum;
import mtopsdk.mtop.intf.Mtop;
import mtopsdk.mtop.util.MtopStatistics;

/* renamed from: fdt reason: default package */
/* compiled from: AppConfigDuplexFilter */
public final class fdt implements fdg, fdh {
    public final String a() {
        return "mtopsdk.AppConfigDuplexFilter";
    }

    public final String a(fdf fdf) {
        final long j;
        Map<String, List<String>> headerFields = fdf.c.getHeaderFields();
        ffd ffd = fdf.a.c;
        String a = fcz.a(headerFields, "x-orange-p-i");
        if (fdd.a(a) && fdd.a(a)) {
            try {
                fgc.a();
                fgc.a(URLDecoder.decode(a, "utf-8"));
            } catch (Exception e) {
                TBSdkLog.a("mtopsdk.AppConfigDuplexFilter", fdf.h, "parse XCommand header field x-orange-p error,xcmdOrange=".concat(String.valueOf(a)), e);
            }
        }
        String a2 = fcz.a(headerFields, "x-app-conf-v");
        if (fdd.b(a2)) {
            return "CONTINUE";
        }
        try {
            j = Long.parseLong(a2);
        } catch (NumberFormatException e2) {
            TBSdkLog.b("mtopsdk.AppConfigDuplexFilter", fdf.h, "parse remoteAppConfigVersion error.appConfigVersion=".concat(String.valueOf(a2)), e2);
            j = 0;
        }
        if (j > ffd.s) {
            final ffd ffd2 = fdf.a.c;
            final fdf fdf2 = fdf;
            AnonymousClass1 r7 = new Runnable() {
                /* JADX WARNING: Removed duplicated region for block: B:30:0x008e A[SYNTHETIC, Splitter:B:30:0x008e] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void run() {
                    /*
                        r8 = this;
                        ffd r0 = r9
                        byte[] r0 = r0.t
                        monitor-enter(r0)
                        long r1 = r10     // Catch:{ all -> 0x00f9 }
                        ffd r3 = r9     // Catch:{ all -> 0x00f9 }
                        long r3 = r3.s     // Catch:{ all -> 0x00f9 }
                        int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                        if (r1 > 0) goto L_0x0011
                        monitor-exit(r0)     // Catch:{ all -> 0x00f9 }
                        return
                    L_0x0011:
                        fdf r1 = r12     // Catch:{ all -> 0x00f9 }
                        mtopsdk.mtop.domain.MtopResponse r1 = r1.c     // Catch:{ all -> 0x00f9 }
                        byte[] r1 = r1.getBytedata()     // Catch:{ all -> 0x00f9 }
                        if (r1 != 0) goto L_0x001d
                        monitor-exit(r0)     // Catch:{ all -> 0x00f9 }
                        return
                    L_0x001d:
                        r2 = 0
                        r3 = 0
                        java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x007e }
                        java.lang.String r5 = "utf-8"
                        r4.<init>(r1, r5)     // Catch:{ Exception -> 0x007e }
                        org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x007e }
                        r1.<init>(r4)     // Catch:{ Exception -> 0x007e }
                        java.lang.String r4 = "appConf"
                        java.lang.String r1 = r1.optString(r4)     // Catch:{ Exception -> 0x007e }
                        boolean r4 = defpackage.fdd.a(r1)     // Catch:{ Exception -> 0x007c }
                        if (r4 == 0) goto L_0x0045
                        fde r4 = defpackage.fde.a.a     // Catch:{ Exception -> 0x007c }
                        fdf r5 = r12     // Catch:{ Exception -> 0x007c }
                        java.lang.String r5 = r5.h     // Catch:{ Exception -> 0x007c }
                        boolean r4 = r4.a(r1, r5)     // Catch:{ Exception -> 0x007c }
                        r3 = r4
                    L_0x0045:
                        if (r3 == 0) goto L_0x008b
                        ffd r4 = r9     // Catch:{ Exception -> 0x007c }
                        long r5 = r10     // Catch:{ Exception -> 0x007c }
                        r4.s = r5     // Catch:{ Exception -> 0x007c }
                        mtopsdk.common.util.TBSdkLog$LogEnable r4 = mtopsdk.common.util.TBSdkLog.LogEnable.InfoEnable     // Catch:{ Exception -> 0x007c }
                        boolean r4 = mtopsdk.common.util.TBSdkLog.a(r4)     // Catch:{ Exception -> 0x007c }
                        if (r4 == 0) goto L_0x008b
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x007c }
                        r5 = 64
                        r4.<init>(r5)     // Catch:{ Exception -> 0x007c }
                        java.lang.String r5 = "[updateAppConf]update AppConf succeed!appConfVersion="
                        r4.append(r5)     // Catch:{ Exception -> 0x007c }
                        long r5 = r10     // Catch:{ Exception -> 0x007c }
                        r4.append(r5)     // Catch:{ Exception -> 0x007c }
                        java.lang.String r5 = ", appConf="
                        r4.append(r5)     // Catch:{ Exception -> 0x007c }
                        r4.append(r1)     // Catch:{ Exception -> 0x007c }
                        java.lang.String r5 = "mtopsdk.AppConfigDuplexFilter"
                        fdf r6 = r12     // Catch:{ Exception -> 0x007c }
                        java.lang.String r6 = r6.h     // Catch:{ Exception -> 0x007c }
                        java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x007c }
                        mtopsdk.common.util.TBSdkLog.b(r5, r6, r4)     // Catch:{ Exception -> 0x007c }
                        goto L_0x008b
                    L_0x007c:
                        r4 = move-exception
                        goto L_0x0080
                    L_0x007e:
                        r4 = move-exception
                        r1 = r2
                    L_0x0080:
                        java.lang.String r5 = "mtopsdk.AppConfigDuplexFilter"
                        fdf r6 = r12     // Catch:{ all -> 0x00f9 }
                        java.lang.String r6 = r6.h     // Catch:{ all -> 0x00f9 }
                        java.lang.String r7 = "[updateAppConf]parse and persist AppConf in data error"
                        mtopsdk.common.util.TBSdkLog.b(r5, r6, r7, r4)     // Catch:{ all -> 0x00f9 }
                    L_0x008b:
                        monitor-exit(r0)     // Catch:{ all -> 0x00f9 }
                        if (r3 == 0) goto L_0x00f8
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00de }
                        r0.<init>()     // Catch:{ Exception -> 0x00de }
                        ffd r3 = r9     // Catch:{ Exception -> 0x00de }
                        android.content.Context r3 = r3.e     // Catch:{ Exception -> 0x00de }
                        java.io.File r2 = r3.getExternalFilesDir(r2)     // Catch:{ Exception -> 0x00de }
                        java.io.File r2 = r2.getAbsoluteFile()     // Catch:{ Exception -> 0x00de }
                        r0.append(r2)     // Catch:{ Exception -> 0x00de }
                        java.lang.String r2 = "/mtop"
                        r0.append(r2)     // Catch:{ Exception -> 0x00de }
                        java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00de }
                        mtopsdk.mtop.cache.domain.AppConfigDo r2 = new mtopsdk.mtop.cache.domain.AppConfigDo     // Catch:{ Exception -> 0x00de }
                        long r3 = r10     // Catch:{ Exception -> 0x00de }
                        r2.<init>(r1, r3)     // Catch:{ Exception -> 0x00de }
                        java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x00de }
                        r1.<init>(r0)     // Catch:{ Exception -> 0x00de }
                        java.lang.String r0 = "appConf"
                        defpackage.fdb.a(r2, r1, r0)     // Catch:{ Exception -> 0x00de }
                        mtopsdk.common.util.TBSdkLog$LogEnable r0 = mtopsdk.common.util.TBSdkLog.LogEnable.InfoEnable     // Catch:{ Exception -> 0x00de }
                        boolean r0 = mtopsdk.common.util.TBSdkLog.a(r0)     // Catch:{ Exception -> 0x00de }
                        if (r0 == 0) goto L_0x00dd
                        java.lang.String r0 = "mtopsdk.AppConfigDuplexFilter"
                        fdf r1 = r12     // Catch:{ Exception -> 0x00de }
                        java.lang.String r1 = r1.h     // Catch:{ Exception -> 0x00de }
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00de }
                        java.lang.String r3 = "[updateAppConf] store appConf succeed. appConfVersion="
                        r2.<init>(r3)     // Catch:{ Exception -> 0x00de }
                        long r3 = r10     // Catch:{ Exception -> 0x00de }
                        r2.append(r3)     // Catch:{ Exception -> 0x00de }
                        java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00de }
                        mtopsdk.common.util.TBSdkLog.b(r0, r1, r2)     // Catch:{ Exception -> 0x00de }
                    L_0x00dd:
                        return
                    L_0x00de:
                        r0 = move-exception
                        java.lang.String r1 = "mtopsdk.AppConfigDuplexFilter"
                        fdf r2 = r12
                        java.lang.String r2 = r2.h
                        java.lang.StringBuilder r3 = new java.lang.StringBuilder
                        java.lang.String r4 = "[updateAppConf] store appConf error. appConfVersion="
                        r3.<init>(r4)
                        long r4 = r10
                        r3.append(r4)
                        java.lang.String r3 = r3.toString()
                        mtopsdk.common.util.TBSdkLog.b(r1, r2, r3, r0)
                    L_0x00f8:
                        return
                    L_0x00f9:
                        r1 = move-exception
                        monitor-exit(r0)     // Catch:{ all -> 0x00f9 }
                        throw r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: defpackage.fdt.AnonymousClass1.run():void");
                }
            };
            ffy.a(r7);
        }
        return "CONTINUE";
    }

    public final String b(fdf fdf) {
        Mtop mtop = fdf.a;
        MtopStatistics mtopStatistics = fdf.g;
        MtopNetworkProp mtopNetworkProp = fdf.d;
        try {
            StringBuilder sb = new StringBuilder(64);
            sb.append(mtop.c.n);
            sb.append(System.currentTimeMillis());
            sb.append(new DecimalFormat("0000").format((long) (mtopStatistics.F % 10000)));
            sb.append("1");
            sb.append(mtop.c.q);
            mtopNetworkProp.clientTraceId = sb.toString();
            mtopStatistics.G = mtopNetworkProp.clientTraceId;
        } catch (Exception e) {
            TBSdkLog.b("mtopsdk.AppConfigDuplexFilter", fdf.h, "generate client-trace-id failed.", e);
        }
        try {
            fde a = a.a;
            if (a.b.contains(fdf.b.getKey())) {
                EnvModeEnum envModeEnum = mtop.c.c;
                if (envModeEnum != null) {
                    switch (envModeEnum) {
                        case ONLINE:
                            mtopNetworkProp.customOnlineDomain = "trade-acs.m.taobao.com";
                            break;
                        case PREPARE:
                            mtopNetworkProp.customPreDomain = "trade-acs.wapa.taobao.com";
                            break;
                        case TEST:
                        case TEST_SANDBOX:
                            mtopNetworkProp.customDailyDomain = "trade-acs.waptest.taobao.com";
                            break;
                    }
                }
            }
        } catch (Exception e2) {
            TBSdkLog.b("mtopsdk.AppConfigDuplexFilter", fdf.h, "setCustomDomain for trade unit api error", e2);
        }
        return "CONTINUE";
    }
}
