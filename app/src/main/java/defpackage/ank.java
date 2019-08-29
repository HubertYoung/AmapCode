package defpackage;

import java.util.HashMap;

/* renamed from: ank reason: default package */
/* compiled from: AMapServiceManager */
public final class ank {
    private static HashMap<String, Object> a;

    /* JADX WARNING: type inference failed for: r6v0, types: [java.lang.Class, java.lang.Class<T>] */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007a, code lost:
        return r6;
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized <T> T a(java.lang.Class<T> r6) {
        /*
            java.lang.Class<ank> r0 = defpackage.ank.class
            monitor-enter(r0)
            java.lang.Class<bie> r1 = defpackage.bie.class
            boolean r1 = r1.isAssignableFrom(r6)     // Catch:{ all -> 0x007b }
            if (r1 == 0) goto L_0x0016
            java.util.HashMap<java.lang.String, java.lang.Object> r2 = a     // Catch:{ all -> 0x007b }
            if (r2 != 0) goto L_0x0016
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ all -> 0x007b }
            r2.<init>()     // Catch:{ all -> 0x007b }
            a = r2     // Catch:{ all -> 0x007b }
        L_0x0016:
            java.lang.String r2 = r6.getName()     // Catch:{ all -> 0x007b }
            r3 = 0
            if (r1 == 0) goto L_0x0027
            java.util.HashMap<java.lang.String, java.lang.Object> r4 = a     // Catch:{ all -> 0x007b }
            java.lang.Object r4 = r4.get(r2)     // Catch:{ all -> 0x007b }
            if (r4 == 0) goto L_0x0028
            monitor-exit(r0)
            return r4
        L_0x0027:
            r4 = r3
        L_0x0028:
            java.lang.Class<com.autonavi.inter.IServiceLoader> r5 = com.autonavi.inter.IServiceLoader.class
            java.lang.Object r5 = defpackage.bqn.a(r5)     // Catch:{ Exception -> 0x006b }
            com.autonavi.inter.IServiceLoader r5 = (com.autonavi.inter.IServiceLoader) r5     // Catch:{ Exception -> 0x006b }
            if (r5 == 0) goto L_0x0036
            java.lang.Class r3 = r5.getService(r6)     // Catch:{ Exception -> 0x006b }
        L_0x0036:
            if (r3 != 0) goto L_0x0066
            java.lang.Package r3 = r6.getPackage()     // Catch:{ Exception -> 0x006b }
            java.lang.String r3 = r3.getName()     // Catch:{ Exception -> 0x006b }
            java.lang.String r6 = r6.getSimpleName()     // Catch:{ Exception -> 0x006b }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006b }
            r5.<init>()     // Catch:{ Exception -> 0x006b }
            r5.append(r3)     // Catch:{ Exception -> 0x006b }
            java.lang.String r3 = ".impl."
            r5.append(r3)     // Catch:{ Exception -> 0x006b }
            r3 = 1
            java.lang.String r6 = r6.substring(r3)     // Catch:{ Exception -> 0x006b }
            r5.append(r6)     // Catch:{ Exception -> 0x006b }
            java.lang.String r6 = "Impl"
            r5.append(r6)     // Catch:{ Exception -> 0x006b }
            java.lang.String r6 = r5.toString()     // Catch:{ Exception -> 0x006b }
            java.lang.Class r3 = java.lang.Class.forName(r6)     // Catch:{ Exception -> 0x006b }
        L_0x0066:
            java.lang.Object r6 = r3.newInstance()     // Catch:{ Exception -> 0x006b }
            goto L_0x0070
        L_0x006b:
            r6 = move-exception
            r6.printStackTrace()     // Catch:{ all -> 0x007b }
            r6 = r4
        L_0x0070:
            if (r1 == 0) goto L_0x0079
            if (r6 == 0) goto L_0x0079
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = a     // Catch:{ all -> 0x007b }
            r1.put(r2, r6)     // Catch:{ all -> 0x007b }
        L_0x0079:
            monitor-exit(r0)
            return r6
        L_0x007b:
            r6 = move-exception
            monitor-exit(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ank.a(java.lang.Class):java.lang.Object");
    }
}
