package defpackage;

import java.util.HashMap;
import java.util.Map;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;

/* renamed from: ffh reason: default package */
/* compiled from: MtopSetting */
public final class ffh {
    public static final Map<String, ffd> a = new HashMap();

    private ffh() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0044, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static defpackage.ffd b(java.lang.String r4) {
        /*
            if (r4 == 0) goto L_0x0003
            goto L_0x0005
        L_0x0003:
            java.lang.String r4 = "INNER"
        L_0x0005:
            java.util.Map<java.lang.String, mtopsdk.mtop.intf.Mtop> r0 = mtopsdk.mtop.intf.Mtop.a
            java.lang.Object r0 = r0.get(r4)
            mtopsdk.mtop.intf.Mtop r0 = (mtopsdk.mtop.intf.Mtop) r0
            if (r0 != 0) goto L_0x004a
            java.lang.Class<mtopsdk.mtop.intf.Mtop> r1 = mtopsdk.mtop.intf.Mtop.class
            monitor-enter(r1)
            java.util.Map<java.lang.String, mtopsdk.mtop.intf.Mtop> r0 = mtopsdk.mtop.intf.Mtop.a     // Catch:{ all -> 0x0047 }
            java.lang.Object r0 = r0.get(r4)     // Catch:{ all -> 0x0047 }
            mtopsdk.mtop.intf.Mtop r0 = (mtopsdk.mtop.intf.Mtop) r0     // Catch:{ all -> 0x0047 }
            if (r0 != 0) goto L_0x0045
            java.util.Map<java.lang.String, ffd> r0 = a     // Catch:{ all -> 0x0047 }
            java.lang.Object r0 = r0.get(r4)     // Catch:{ all -> 0x0047 }
            ffd r0 = (defpackage.ffd) r0     // Catch:{ all -> 0x0047 }
            if (r0 != 0) goto L_0x0043
            java.lang.Class<ffh> r0 = defpackage.ffh.class
            monitor-enter(r0)     // Catch:{ all -> 0x0047 }
            java.util.Map<java.lang.String, ffd> r2 = a     // Catch:{ all -> 0x0040 }
            java.lang.Object r2 = r2.get(r4)     // Catch:{ all -> 0x0040 }
            ffd r2 = (defpackage.ffd) r2     // Catch:{ all -> 0x0040 }
            if (r2 != 0) goto L_0x003d
            ffd r2 = new ffd     // Catch:{ all -> 0x0040 }
            r2.<init>(r4)     // Catch:{ all -> 0x0040 }
            java.util.Map<java.lang.String, ffd> r3 = a     // Catch:{ all -> 0x0040 }
            r3.put(r4, r2)     // Catch:{ all -> 0x0040 }
        L_0x003d:
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            r0 = r2
            goto L_0x0043
        L_0x0040:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            throw r4     // Catch:{ all -> 0x0047 }
        L_0x0043:
            monitor-exit(r1)     // Catch:{ all -> 0x0047 }
            return r0
        L_0x0045:
            monitor-exit(r1)     // Catch:{ all -> 0x0047 }
            goto L_0x004a
        L_0x0047:
            r4 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0047 }
            throw r4
        L_0x004a:
            ffd r4 = r0.c
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ffh.b(java.lang.String):ffd");
    }

    public static void a(String str, int i, int i2) {
        ffd b = b(str);
        b.f = i;
        b.g = i2;
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb = new StringBuilder();
            sb.append(b.a);
            sb.append(" [setAppKeyIndex] onlineAppKeyIndex=");
            sb.append(i);
            sb.append(",dailyAppkeyIndex=");
            sb.append(i2);
            TBSdkLog.b("mtopsdk.MtopSetting", sb.toString());
        }
    }

    @Deprecated
    public static void a(int i, int i2) {
        a(null, i, i2);
    }

    public static void a(String str, String str2) {
        ffd b = b(str);
        b.o = str2;
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb = new StringBuilder();
            sb.append(b.a);
            sb.append(" [setAppVersion] appVersion=");
            sb.append(str2);
            TBSdkLog.b("mtopsdk.MtopSetting", sb.toString());
        }
    }

    @Deprecated
    public static void a(String str) {
        a((String) null, str);
    }
}
