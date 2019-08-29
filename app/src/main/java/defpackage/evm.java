package defpackage;

import android.content.Context;

/* renamed from: evm reason: default package */
/* compiled from: HVTInfoService */
public class evm {
    private static final Object a = new Object();
    private static final String b = "evm";
    private static Context c;

    /* renamed from: evm$a */
    /* compiled from: HVTInfoService */
    static class a {
        public static final evm a = new evm(0);
    }

    /* synthetic */ evm(byte b2) {
        this();
    }

    private evm() {
        ewl.a(ewm.a(c));
    }

    public static evm a(Context context) {
        c = context.getApplicationContext();
        return a.a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0077 A[SYNTHETIC, Splitter:B:34:0x0077] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x009b A[SYNTHETIC, Splitter:B:49:0x009b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.String r4) {
        /*
            java.lang.Object r0 = a
            if (r0 == 0) goto L_0x001b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Lock HashCode = "
            r0.<init>(r1)
            java.lang.Object r1 = a
            int r1 = r1.hashCode()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            defpackage.evf.a(r0)
        L_0x001b:
            r0 = 0
            java.lang.Object r1 = a
            monitor-enter(r1)
            r2 = 0
            ewl r3 = defpackage.ewl.a()     // Catch:{ Exception -> 0x006d }
            android.database.sqlite.SQLiteDatabase r3 = r3.b()     // Catch:{ Exception -> 0x006d }
            r3.beginTransaction()     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            int r4 = r3.delete(r4, r2, r2)     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            java.lang.String r2 = "clean table  = "
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            java.lang.String r4 = r2.concat(r4)     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            defpackage.evf.a(r4)     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            r3.setTransactionSuccessful()     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            r0 = 1
            if (r3 == 0) goto L_0x0097
            boolean r4 = r3.inTransaction()     // Catch:{ Exception -> 0x004c }
            if (r4 == 0) goto L_0x0054
            r3.endTransaction()     // Catch:{ Exception -> 0x004c }
            goto L_0x0054
        L_0x004c:
            r4 = move-exception
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x00a5 }
            defpackage.evf.a(r4)     // Catch:{ all -> 0x00a5 }
        L_0x0054:
            ewl r4 = defpackage.ewl.a()     // Catch:{ Exception -> 0x005c }
            r4.c()     // Catch:{ Exception -> 0x005c }
            goto L_0x0097
        L_0x005c:
            r4 = move-exception
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x00a5 }
        L_0x0061:
            defpackage.evf.a(r4)     // Catch:{ all -> 0x00a5 }
            goto L_0x0097
        L_0x0065:
            r4 = move-exception
            goto L_0x0099
        L_0x0067:
            r4 = move-exception
            r2 = r3
            goto L_0x006e
        L_0x006a:
            r4 = move-exception
            r3 = r2
            goto L_0x0099
        L_0x006d:
            r4 = move-exception
        L_0x006e:
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x006a }
            defpackage.evf.a(r4)     // Catch:{ all -> 0x006a }
            if (r2 == 0) goto L_0x0097
            boolean r4 = r2.inTransaction()     // Catch:{ Exception -> 0x0081 }
            if (r4 == 0) goto L_0x0089
            r2.endTransaction()     // Catch:{ Exception -> 0x0081 }
            goto L_0x0089
        L_0x0081:
            r4 = move-exception
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x00a5 }
            defpackage.evf.a(r4)     // Catch:{ all -> 0x00a5 }
        L_0x0089:
            ewl r4 = defpackage.ewl.a()     // Catch:{ Exception -> 0x0091 }
            r4.c()     // Catch:{ Exception -> 0x0091 }
            goto L_0x0097
        L_0x0091:
            r4 = move-exception
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x00a5 }
            goto L_0x0061
        L_0x0097:
            monitor-exit(r1)     // Catch:{ all -> 0x00a5 }
            return r0
        L_0x0099:
            if (r3 == 0) goto L_0x00bf
            boolean r0 = r3.inTransaction()     // Catch:{ Exception -> 0x00a7 }
            if (r0 == 0) goto L_0x00af
            r3.endTransaction()     // Catch:{ Exception -> 0x00a7 }
            goto L_0x00af
        L_0x00a5:
            r4 = move-exception
            goto L_0x00c0
        L_0x00a7:
            r0 = move-exception
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x00a5 }
            defpackage.evf.a(r0)     // Catch:{ all -> 0x00a5 }
        L_0x00af:
            ewl r0 = defpackage.ewl.a()     // Catch:{ Exception -> 0x00b7 }
            r0.c()     // Catch:{ Exception -> 0x00b7 }
            goto L_0x00bf
        L_0x00b7:
            r0 = move-exception
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x00a5 }
            defpackage.evf.a(r0)     // Catch:{ all -> 0x00a5 }
        L_0x00bf:
            throw r4     // Catch:{ all -> 0x00a5 }
        L_0x00c0:
            monitor-exit(r1)     // Catch:{ all -> 0x00a5 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.evm.a(java.lang.String):boolean");
    }
}
