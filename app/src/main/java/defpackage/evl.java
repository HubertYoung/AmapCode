package defpackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/* renamed from: evl reason: default package */
/* compiled from: HMTInfoService */
public class evl {
    private static final String a = "evl";
    private static Context b;

    /* renamed from: evl$a */
    /* compiled from: HMTInfoService */
    static class a {
        public static final evl a = new evl(0);
    }

    /* synthetic */ evl(byte b2) {
        this();
    }

    private evl() {
        ewg.a(ewh.a(b));
    }

    public static evl a(Context context) {
        b = context.getApplicationContext();
        return a.a;
    }

    private static void a() {
        StringBuilder sb = new StringBuilder("Lock HashCode = ");
        sb.append(evl.class.hashCode());
        euw.a(sb.toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b8 A[SYNTHETIC, Splitter:B:41:0x00b8] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00f9 A[SYNTHETIC, Splitter:B:60:0x00f9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(java.lang.String r4, java.lang.String r5, java.lang.String r6) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.String r0 = "------save"
            defpackage.euw.a(r0)     // Catch:{ all -> 0x0134 }
            a()     // Catch:{ all -> 0x0134 }
            java.lang.String r0 = defpackage.evk.b(r5)     // Catch:{ IOException -> 0x0019 }
            java.lang.String r1 = defpackage.evd.o     // Catch:{ IOException -> 0x0019 }
            java.lang.String r1 = defpackage.eve.d(r1)     // Catch:{ IOException -> 0x0019 }
            java.lang.String r0 = defpackage.ewf.a(r1, r0)     // Catch:{ IOException -> 0x0019 }
            r5 = r0
            goto L_0x002f
        L_0x0019:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0134 }
            java.lang.String r2 = "Collected:"
            r1.<init>(r2)     // Catch:{ all -> 0x0134 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0134 }
            r1.append(r0)     // Catch:{ all -> 0x0134 }
            java.lang.String r0 = r1.toString()     // Catch:{ all -> 0x0134 }
            defpackage.euw.a(r0)     // Catch:{ all -> 0x0134 }
        L_0x002f:
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = defpackage.ewg.a()     // Catch:{ Exception -> 0x00a0 }
            r1.beginTransaction()     // Catch:{ Exception -> 0x009a, all -> 0x0098 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x009a, all -> 0x0098 }
            java.lang.String r2 = "insert into "
            r0.<init>(r2)     // Catch:{ Exception -> 0x009a, all -> 0x0098 }
            r0.append(r6)     // Catch:{ Exception -> 0x009a, all -> 0x0098 }
            java.lang.String r6 = "(type,info)values(?,?)"
            r0.append(r6)     // Catch:{ Exception -> 0x009a, all -> 0x0098 }
            java.lang.String r6 = r0.toString()     // Catch:{ Exception -> 0x009a, all -> 0x0098 }
            r0 = 2
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x009a, all -> 0x0098 }
            r2 = 0
            r0[r2] = r4     // Catch:{ Exception -> 0x009a, all -> 0x0098 }
            r4 = 1
            r0[r4] = r5     // Catch:{ Exception -> 0x009a, all -> 0x0098 }
            r1.execSQL(r6, r0)     // Catch:{ Exception -> 0x009a, all -> 0x0098 }
            r1.setTransactionSuccessful()     // Catch:{ Exception -> 0x009a, all -> 0x0098 }
            if (r1 == 0) goto L_0x00f5
            boolean r4 = r1.inTransaction()     // Catch:{ Exception -> 0x0065 }
            if (r4 == 0) goto L_0x007b
            r1.endTransaction()     // Catch:{ Exception -> 0x0065 }
            goto L_0x007b
        L_0x0065:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0134 }
            java.lang.String r6 = "Collected:"
            r5.<init>(r6)     // Catch:{ all -> 0x0134 }
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x0134 }
            r5.append(r4)     // Catch:{ all -> 0x0134 }
            java.lang.String r4 = r5.toString()     // Catch:{ all -> 0x0134 }
            defpackage.euw.a(r4)     // Catch:{ all -> 0x0134 }
        L_0x007b:
            defpackage.ewg.b()     // Catch:{ Exception -> 0x0080 }
            monitor-exit(r3)
            return
        L_0x0080:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0134 }
            java.lang.String r6 = "Collected:"
            r5.<init>(r6)     // Catch:{ all -> 0x0134 }
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x0134 }
            r5.append(r4)     // Catch:{ all -> 0x0134 }
            java.lang.String r4 = r5.toString()     // Catch:{ all -> 0x0134 }
            defpackage.euw.a(r4)     // Catch:{ all -> 0x0134 }
            monitor-exit(r3)
            return
        L_0x0098:
            r4 = move-exception
            goto L_0x00f7
        L_0x009a:
            r4 = move-exception
            r0 = r1
            goto L_0x00a1
        L_0x009d:
            r4 = move-exception
            r1 = r0
            goto L_0x00f7
        L_0x00a0:
            r4 = move-exception
        L_0x00a1:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x009d }
            java.lang.String r6 = "Collected:"
            r5.<init>(r6)     // Catch:{ all -> 0x009d }
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x009d }
            r5.append(r4)     // Catch:{ all -> 0x009d }
            java.lang.String r4 = r5.toString()     // Catch:{ all -> 0x009d }
            defpackage.euw.a(r4)     // Catch:{ all -> 0x009d }
            if (r0 == 0) goto L_0x00f5
            boolean r4 = r0.inTransaction()     // Catch:{ Exception -> 0x00c2 }
            if (r4 == 0) goto L_0x00d8
            r0.endTransaction()     // Catch:{ Exception -> 0x00c2 }
            goto L_0x00d8
        L_0x00c2:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0134 }
            java.lang.String r6 = "Collected:"
            r5.<init>(r6)     // Catch:{ all -> 0x0134 }
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x0134 }
            r5.append(r4)     // Catch:{ all -> 0x0134 }
            java.lang.String r4 = r5.toString()     // Catch:{ all -> 0x0134 }
            defpackage.euw.a(r4)     // Catch:{ all -> 0x0134 }
        L_0x00d8:
            defpackage.ewg.b()     // Catch:{ Exception -> 0x00dd }
            monitor-exit(r3)
            return
        L_0x00dd:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0134 }
            java.lang.String r6 = "Collected:"
            r5.<init>(r6)     // Catch:{ all -> 0x0134 }
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x0134 }
            r5.append(r4)     // Catch:{ all -> 0x0134 }
            java.lang.String r4 = r5.toString()     // Catch:{ all -> 0x0134 }
            defpackage.euw.a(r4)     // Catch:{ all -> 0x0134 }
            monitor-exit(r3)
            return
        L_0x00f5:
            monitor-exit(r3)
            return
        L_0x00f7:
            if (r1 == 0) goto L_0x0133
            boolean r5 = r1.inTransaction()     // Catch:{ Exception -> 0x0103 }
            if (r5 == 0) goto L_0x0119
            r1.endTransaction()     // Catch:{ Exception -> 0x0103 }
            goto L_0x0119
        L_0x0103:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0134 }
            java.lang.String r0 = "Collected:"
            r6.<init>(r0)     // Catch:{ all -> 0x0134 }
            java.lang.String r5 = r5.getMessage()     // Catch:{ all -> 0x0134 }
            r6.append(r5)     // Catch:{ all -> 0x0134 }
            java.lang.String r5 = r6.toString()     // Catch:{ all -> 0x0134 }
            defpackage.euw.a(r5)     // Catch:{ all -> 0x0134 }
        L_0x0119:
            defpackage.ewg.b()     // Catch:{ Exception -> 0x011d }
            goto L_0x0133
        L_0x011d:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0134 }
            java.lang.String r0 = "Collected:"
            r6.<init>(r0)     // Catch:{ all -> 0x0134 }
            java.lang.String r5 = r5.getMessage()     // Catch:{ all -> 0x0134 }
            r6.append(r5)     // Catch:{ all -> 0x0134 }
            java.lang.String r5 = r6.toString()     // Catch:{ all -> 0x0134 }
            defpackage.euw.a(r5)     // Catch:{ all -> 0x0134 }
        L_0x0133:
            throw r4     // Catch:{ all -> 0x0134 }
        L_0x0134:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.evl.a(java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0089 A[SYNTHETIC, Splitter:B:35:0x0089] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00ca A[SYNTHETIC, Splitter:B:54:0x00ca] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(java.lang.String r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.String r0 = "------resetTableSequence"
            defpackage.euw.a(r0)     // Catch:{ all -> 0x0105 }
            a()     // Catch:{ all -> 0x0105 }
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = defpackage.ewg.a()     // Catch:{ Exception -> 0x0071 }
            r1.beginTransaction()     // Catch:{ Exception -> 0x006b, all -> 0x0069 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006b, all -> 0x0069 }
            java.lang.String r2 = "UPDATE sqlite_sequence SET seq = 0 WHERE name='"
            r0.<init>(r2)     // Catch:{ Exception -> 0x006b, all -> 0x0069 }
            r0.append(r4)     // Catch:{ Exception -> 0x006b, all -> 0x0069 }
            java.lang.String r4 = "'"
            r0.append(r4)     // Catch:{ Exception -> 0x006b, all -> 0x0069 }
            java.lang.String r4 = r0.toString()     // Catch:{ Exception -> 0x006b, all -> 0x0069 }
            r1.execSQL(r4)     // Catch:{ Exception -> 0x006b, all -> 0x0069 }
            r1.setTransactionSuccessful()     // Catch:{ Exception -> 0x006b, all -> 0x0069 }
            if (r1 == 0) goto L_0x00c6
            boolean r4 = r1.inTransaction()     // Catch:{ Exception -> 0x0036 }
            if (r4 == 0) goto L_0x004c
            r1.endTransaction()     // Catch:{ Exception -> 0x0036 }
            goto L_0x004c
        L_0x0036:
            r4 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0105 }
            java.lang.String r1 = "Collected:"
            r0.<init>(r1)     // Catch:{ all -> 0x0105 }
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x0105 }
            r0.append(r4)     // Catch:{ all -> 0x0105 }
            java.lang.String r4 = r0.toString()     // Catch:{ all -> 0x0105 }
            defpackage.euw.a(r4)     // Catch:{ all -> 0x0105 }
        L_0x004c:
            defpackage.ewg.b()     // Catch:{ Exception -> 0x0051 }
            monitor-exit(r3)
            return
        L_0x0051:
            r4 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0105 }
            java.lang.String r1 = "Collected:"
            r0.<init>(r1)     // Catch:{ all -> 0x0105 }
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x0105 }
            r0.append(r4)     // Catch:{ all -> 0x0105 }
            java.lang.String r4 = r0.toString()     // Catch:{ all -> 0x0105 }
            defpackage.euw.a(r4)     // Catch:{ all -> 0x0105 }
            monitor-exit(r3)
            return
        L_0x0069:
            r4 = move-exception
            goto L_0x00c8
        L_0x006b:
            r4 = move-exception
            r0 = r1
            goto L_0x0072
        L_0x006e:
            r4 = move-exception
            r1 = r0
            goto L_0x00c8
        L_0x0071:
            r4 = move-exception
        L_0x0072:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x006e }
            java.lang.String r2 = "Collected:"
            r1.<init>(r2)     // Catch:{ all -> 0x006e }
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x006e }
            r1.append(r4)     // Catch:{ all -> 0x006e }
            java.lang.String r4 = r1.toString()     // Catch:{ all -> 0x006e }
            defpackage.euw.a(r4)     // Catch:{ all -> 0x006e }
            if (r0 == 0) goto L_0x00c6
            boolean r4 = r0.inTransaction()     // Catch:{ Exception -> 0x0093 }
            if (r4 == 0) goto L_0x00a9
            r0.endTransaction()     // Catch:{ Exception -> 0x0093 }
            goto L_0x00a9
        L_0x0093:
            r4 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0105 }
            java.lang.String r1 = "Collected:"
            r0.<init>(r1)     // Catch:{ all -> 0x0105 }
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x0105 }
            r0.append(r4)     // Catch:{ all -> 0x0105 }
            java.lang.String r4 = r0.toString()     // Catch:{ all -> 0x0105 }
            defpackage.euw.a(r4)     // Catch:{ all -> 0x0105 }
        L_0x00a9:
            defpackage.ewg.b()     // Catch:{ Exception -> 0x00ae }
            monitor-exit(r3)
            return
        L_0x00ae:
            r4 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0105 }
            java.lang.String r1 = "Collected:"
            r0.<init>(r1)     // Catch:{ all -> 0x0105 }
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x0105 }
            r0.append(r4)     // Catch:{ all -> 0x0105 }
            java.lang.String r4 = r0.toString()     // Catch:{ all -> 0x0105 }
            defpackage.euw.a(r4)     // Catch:{ all -> 0x0105 }
            monitor-exit(r3)
            return
        L_0x00c6:
            monitor-exit(r3)
            return
        L_0x00c8:
            if (r1 == 0) goto L_0x0104
            boolean r0 = r1.inTransaction()     // Catch:{ Exception -> 0x00d4 }
            if (r0 == 0) goto L_0x00ea
            r1.endTransaction()     // Catch:{ Exception -> 0x00d4 }
            goto L_0x00ea
        L_0x00d4:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0105 }
            java.lang.String r2 = "Collected:"
            r1.<init>(r2)     // Catch:{ all -> 0x0105 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0105 }
            r1.append(r0)     // Catch:{ all -> 0x0105 }
            java.lang.String r0 = r1.toString()     // Catch:{ all -> 0x0105 }
            defpackage.euw.a(r0)     // Catch:{ all -> 0x0105 }
        L_0x00ea:
            defpackage.ewg.b()     // Catch:{ Exception -> 0x00ee }
            goto L_0x0104
        L_0x00ee:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0105 }
            java.lang.String r2 = "Collected:"
            r1.<init>(r2)     // Catch:{ all -> 0x0105 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0105 }
            r1.append(r0)     // Catch:{ all -> 0x0105 }
            java.lang.String r0 = r1.toString()     // Catch:{ all -> 0x0105 }
            defpackage.euw.a(r0)     // Catch:{ all -> 0x0105 }
        L_0x0104:
            throw r4     // Catch:{ all -> 0x0105 }
        L_0x0105:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.evl.a(java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0061 A[SYNTHETIC, Splitter:B:32:0x0061] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0081 A[SYNTHETIC, Splitter:B:47:0x0081] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean b(java.lang.String r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            a()     // Catch:{ all -> 0x00a0 }
            r0 = 0
            r1 = 0
            android.database.sqlite.SQLiteDatabase r2 = defpackage.ewg.a()     // Catch:{ Exception -> 0x0057 }
            r2.beginTransaction()     // Catch:{ Exception -> 0x0051, all -> 0x004f }
            int r1 = r2.delete(r6, r1, r1)     // Catch:{ Exception -> 0x0051, all -> 0x004f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0051, all -> 0x004f }
            java.lang.String r4 = "clean table"
            r3.<init>(r4)     // Catch:{ Exception -> 0x0051, all -> 0x004f }
            r3.append(r6)     // Catch:{ Exception -> 0x0051, all -> 0x004f }
            java.lang.String r6 = "  = "
            r3.append(r6)     // Catch:{ Exception -> 0x0051, all -> 0x004f }
            r3.append(r1)     // Catch:{ Exception -> 0x0051, all -> 0x004f }
            java.lang.String r6 = r3.toString()     // Catch:{ Exception -> 0x0051, all -> 0x004f }
            defpackage.euw.a(r6)     // Catch:{ Exception -> 0x0051, all -> 0x004f }
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x0051, all -> 0x004f }
            r0 = 1
            if (r2 == 0) goto L_0x007d
            boolean r6 = r2.inTransaction()     // Catch:{ Exception -> 0x003a }
            if (r6 == 0) goto L_0x0042
            r2.endTransaction()     // Catch:{ Exception -> 0x003a }
            goto L_0x0042
        L_0x003a:
            r6 = move-exception
            java.lang.String r6 = r6.getMessage()     // Catch:{ all -> 0x00a0 }
            defpackage.euw.a(r6)     // Catch:{ all -> 0x00a0 }
        L_0x0042:
            defpackage.ewg.b()     // Catch:{ Exception -> 0x0046 }
            goto L_0x007d
        L_0x0046:
            r6 = move-exception
            java.lang.String r6 = r6.getMessage()     // Catch:{ all -> 0x00a0 }
        L_0x004b:
            defpackage.euw.a(r6)     // Catch:{ all -> 0x00a0 }
            goto L_0x007d
        L_0x004f:
            r6 = move-exception
            goto L_0x007f
        L_0x0051:
            r6 = move-exception
            r1 = r2
            goto L_0x0058
        L_0x0054:
            r6 = move-exception
            r2 = r1
            goto L_0x007f
        L_0x0057:
            r6 = move-exception
        L_0x0058:
            java.lang.String r6 = r6.getMessage()     // Catch:{ all -> 0x0054 }
            defpackage.euw.a(r6)     // Catch:{ all -> 0x0054 }
            if (r1 == 0) goto L_0x007d
            boolean r6 = r1.inTransaction()     // Catch:{ Exception -> 0x006b }
            if (r6 == 0) goto L_0x0073
            r1.endTransaction()     // Catch:{ Exception -> 0x006b }
            goto L_0x0073
        L_0x006b:
            r6 = move-exception
            java.lang.String r6 = r6.getMessage()     // Catch:{ all -> 0x00a0 }
            defpackage.euw.a(r6)     // Catch:{ all -> 0x00a0 }
        L_0x0073:
            defpackage.ewg.b()     // Catch:{ Exception -> 0x0077 }
            goto L_0x007d
        L_0x0077:
            r6 = move-exception
            java.lang.String r6 = r6.getMessage()     // Catch:{ all -> 0x00a0 }
            goto L_0x004b
        L_0x007d:
            monitor-exit(r5)
            return r0
        L_0x007f:
            if (r2 == 0) goto L_0x009f
            boolean r0 = r2.inTransaction()     // Catch:{ Exception -> 0x008b }
            if (r0 == 0) goto L_0x0093
            r2.endTransaction()     // Catch:{ Exception -> 0x008b }
            goto L_0x0093
        L_0x008b:
            r0 = move-exception
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x00a0 }
            defpackage.euw.a(r0)     // Catch:{ all -> 0x00a0 }
        L_0x0093:
            defpackage.ewg.b()     // Catch:{ Exception -> 0x0097 }
            goto L_0x009f
        L_0x0097:
            r0 = move-exception
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x00a0 }
            defpackage.euw.a(r0)     // Catch:{ all -> 0x00a0 }
        L_0x009f:
            throw r6     // Catch:{ all -> 0x00a0 }
        L_0x00a0:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.evl.b(java.lang.String):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x00cd A[SYNTHETIC, Splitter:B:38:0x00cd] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x014e A[SYNTHETIC, Splitter:B:65:0x014e] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:49:0x0100=Splitter:B:49:0x0100, B:35:0x00b6=Splitter:B:35:0x00b6} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized java.util.ArrayList<defpackage.ewj> a(java.lang.String r7, int r8) {
        /*
            r6 = this;
            monitor-enter(r6)
            java.lang.String r0 = "------getScrollData"
            defpackage.euw.a(r0)     // Catch:{ all -> 0x0183 }
            a()     // Catch:{ all -> 0x0183 }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0183 }
            r0.<init>()     // Catch:{ all -> 0x0183 }
            r1 = 0
            android.database.sqlite.SQLiteDatabase r2 = defpackage.ewg.a()     // Catch:{ Exception -> 0x00ff, Error -> 0x00b5 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ff, Error -> 0x00b5 }
            java.lang.String r4 = "select * from "
            r3.<init>(r4)     // Catch:{ Exception -> 0x00ff, Error -> 0x00b5 }
            r3.append(r7)     // Catch:{ Exception -> 0x00ff, Error -> 0x00b5 }
            java.lang.String r7 = " order by id asc limit ?"
            r3.append(r7)     // Catch:{ Exception -> 0x00ff, Error -> 0x00b5 }
            java.lang.String r7 = r3.toString()     // Catch:{ Exception -> 0x00ff, Error -> 0x00b5 }
            r3 = 1
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch:{ Exception -> 0x00ff, Error -> 0x00b5 }
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ Exception -> 0x00ff, Error -> 0x00b5 }
            r5 = 0
            r4[r5] = r8     // Catch:{ Exception -> 0x00ff, Error -> 0x00b5 }
            android.database.Cursor r7 = r2.rawQuery(r7, r4)     // Catch:{ Exception -> 0x00ff, Error -> 0x00b5 }
        L_0x0034:
            boolean r8 = r7.moveToNext()     // Catch:{ Exception -> 0x00af, Error -> 0x00ac, all -> 0x00a8 }
            if (r8 == 0) goto L_0x006f
            ewj r8 = new ewj     // Catch:{ IOException -> 0x0058 }
            int r1 = r7.getInt(r5)     // Catch:{ IOException -> 0x0058 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ IOException -> 0x0058 }
            java.lang.String r2 = r7.getString(r3)     // Catch:{ IOException -> 0x0058 }
            r4 = 2
            java.lang.String r4 = r7.getString(r4)     // Catch:{ IOException -> 0x0058 }
            java.lang.String r4 = defpackage.evk.c(r4)     // Catch:{ IOException -> 0x0058 }
            r8.<init>(r1, r2, r4)     // Catch:{ IOException -> 0x0058 }
            r0.add(r8)     // Catch:{ IOException -> 0x0058 }
            goto L_0x0034
        L_0x0058:
            r8 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00af, Error -> 0x00ac, all -> 0x00a8 }
            java.lang.String r2 = "Collected:"
            r1.<init>(r2)     // Catch:{ Exception -> 0x00af, Error -> 0x00ac, all -> 0x00a8 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x00af, Error -> 0x00ac, all -> 0x00a8 }
            r1.append(r8)     // Catch:{ Exception -> 0x00af, Error -> 0x00ac, all -> 0x00a8 }
            java.lang.String r8 = r1.toString()     // Catch:{ Exception -> 0x00af, Error -> 0x00ac, all -> 0x00a8 }
            defpackage.euw.a(r8)     // Catch:{ Exception -> 0x00af, Error -> 0x00ac, all -> 0x00a8 }
            goto L_0x0034
        L_0x006f:
            if (r7 == 0) goto L_0x008b
            r7.close()     // Catch:{ Exception -> 0x0075 }
            goto L_0x008b
        L_0x0075:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0183 }
            java.lang.String r1 = "Collected:"
            r8.<init>(r1)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r7.getMessage()     // Catch:{ all -> 0x0183 }
            r8.append(r7)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r8.toString()     // Catch:{ all -> 0x0183 }
            defpackage.euw.a(r7)     // Catch:{ all -> 0x0183 }
        L_0x008b:
            defpackage.ewg.b()     // Catch:{ Exception -> 0x0090 }
            goto L_0x014a
        L_0x0090:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0183 }
            java.lang.String r1 = "Collected:"
            r8.<init>(r1)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r7.getMessage()     // Catch:{ all -> 0x0183 }
            r8.append(r7)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r8.toString()     // Catch:{ all -> 0x0183 }
        L_0x00a3:
            defpackage.euw.a(r7)     // Catch:{ all -> 0x0183 }
            goto L_0x014a
        L_0x00a8:
            r8 = move-exception
            r1 = r7
            goto L_0x014c
        L_0x00ac:
            r8 = move-exception
            r1 = r7
            goto L_0x00b6
        L_0x00af:
            r8 = move-exception
            r1 = r7
            goto L_0x0100
        L_0x00b2:
            r8 = move-exception
            goto L_0x014c
        L_0x00b5:
            r8 = move-exception
        L_0x00b6:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b2 }
            java.lang.String r2 = "Collected:"
            r7.<init>(r2)     // Catch:{ all -> 0x00b2 }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x00b2 }
            r7.append(r8)     // Catch:{ all -> 0x00b2 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x00b2 }
            defpackage.euw.a(r7)     // Catch:{ all -> 0x00b2 }
            if (r1 == 0) goto L_0x00e7
            r1.close()     // Catch:{ Exception -> 0x00d1 }
            goto L_0x00e7
        L_0x00d1:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0183 }
            java.lang.String r1 = "Collected:"
            r8.<init>(r1)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r7.getMessage()     // Catch:{ all -> 0x0183 }
            r8.append(r7)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r8.toString()     // Catch:{ all -> 0x0183 }
            defpackage.euw.a(r7)     // Catch:{ all -> 0x0183 }
        L_0x00e7:
            defpackage.ewg.b()     // Catch:{ Exception -> 0x00eb }
            goto L_0x014a
        L_0x00eb:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0183 }
            java.lang.String r1 = "Collected:"
            r8.<init>(r1)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r7.getMessage()     // Catch:{ all -> 0x0183 }
            r8.append(r7)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r8.toString()     // Catch:{ all -> 0x0183 }
            goto L_0x00a3
        L_0x00ff:
            r8 = move-exception
        L_0x0100:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b2 }
            java.lang.String r2 = "Collected:"
            r7.<init>(r2)     // Catch:{ all -> 0x00b2 }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x00b2 }
            r7.append(r8)     // Catch:{ all -> 0x00b2 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x00b2 }
            defpackage.euw.a(r7)     // Catch:{ all -> 0x00b2 }
            if (r1 == 0) goto L_0x0131
            r1.close()     // Catch:{ Exception -> 0x011b }
            goto L_0x0131
        L_0x011b:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0183 }
            java.lang.String r1 = "Collected:"
            r8.<init>(r1)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r7.getMessage()     // Catch:{ all -> 0x0183 }
            r8.append(r7)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r8.toString()     // Catch:{ all -> 0x0183 }
            defpackage.euw.a(r7)     // Catch:{ all -> 0x0183 }
        L_0x0131:
            defpackage.ewg.b()     // Catch:{ Exception -> 0x0135 }
            goto L_0x014a
        L_0x0135:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0183 }
            java.lang.String r1 = "Collected:"
            r8.<init>(r1)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r7.getMessage()     // Catch:{ all -> 0x0183 }
            r8.append(r7)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r8.toString()     // Catch:{ all -> 0x0183 }
            goto L_0x00a3
        L_0x014a:
            monitor-exit(r6)
            return r0
        L_0x014c:
            if (r1 == 0) goto L_0x0168
            r1.close()     // Catch:{ Exception -> 0x0152 }
            goto L_0x0168
        L_0x0152:
            r7 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0183 }
            java.lang.String r1 = "Collected:"
            r0.<init>(r1)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r7.getMessage()     // Catch:{ all -> 0x0183 }
            r0.append(r7)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r0.toString()     // Catch:{ all -> 0x0183 }
            defpackage.euw.a(r7)     // Catch:{ all -> 0x0183 }
        L_0x0168:
            defpackage.ewg.b()     // Catch:{ Exception -> 0x016c }
            goto L_0x0182
        L_0x016c:
            r7 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0183 }
            java.lang.String r1 = "Collected:"
            r0.<init>(r1)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r7.getMessage()     // Catch:{ all -> 0x0183 }
            r0.append(r7)     // Catch:{ all -> 0x0183 }
            java.lang.String r7 = r0.toString()     // Catch:{ all -> 0x0183 }
            defpackage.euw.a(r7)     // Catch:{ all -> 0x0183 }
        L_0x0182:
            throw r8     // Catch:{ all -> 0x0183 }
        L_0x0183:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.evl.a(java.lang.String, int):java.util.ArrayList");
    }

    public final synchronized void b(String str, int i) {
        euw.a((String) "------deleteData");
        a();
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = ewg.a();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            euw.a(sb.toString());
        } catch (Throwable th) {
            if (sQLiteDatabase != null) {
                try {
                    if (sQLiteDatabase.inTransaction()) {
                        sQLiteDatabase.endTransaction();
                    }
                } catch (Exception e2) {
                    StringBuilder sb2 = new StringBuilder("Collected:");
                    sb2.append(e2.getMessage());
                    euw.a(sb2.toString());
                }
                try {
                    ewg.b();
                } catch (Exception e3) {
                    StringBuilder sb3 = new StringBuilder("Collected:");
                    sb3.append(e3.getMessage());
                    euw.a(sb3.toString());
                }
            }
            throw th;
        }
        try {
            sQLiteDatabase.beginTransaction();
            StringBuilder sb4 = new StringBuilder("delete from ");
            sb4.append(str);
            sb4.append(" where id<=");
            sb4.append(i);
            sQLiteDatabase.execSQL(sb4.toString());
            sQLiteDatabase.setTransactionSuccessful();
            if (sQLiteDatabase != null) {
                try {
                    if (sQLiteDatabase.inTransaction()) {
                        sQLiteDatabase.endTransaction();
                    }
                } catch (Exception e4) {
                    StringBuilder sb5 = new StringBuilder("Collected:");
                    sb5.append(e4.getMessage());
                    euw.a(sb5.toString());
                }
                try {
                    ewg.b();
                    return;
                } catch (Exception e5) {
                    StringBuilder sb6 = new StringBuilder("Collected:");
                    sb6.append(e5.getMessage());
                    euw.a(sb6.toString());
                    return;
                }
            }
        } catch (Exception e6) {
            StringBuilder sb7 = new StringBuilder("Collected:");
            sb7.append(e6.getMessage());
            euw.a(sb7.toString());
            if (sQLiteDatabase != null) {
                try {
                    if (sQLiteDatabase.inTransaction()) {
                        sQLiteDatabase.endTransaction();
                    }
                } catch (Exception e7) {
                    StringBuilder sb8 = new StringBuilder("Collected:");
                    sb8.append(e7.getMessage());
                    euw.a(sb8.toString());
                }
                try {
                    ewg.b();
                    return;
                } catch (Exception e8) {
                    StringBuilder sb9 = new StringBuilder("Collected:");
                    sb9.append(e8.getMessage());
                    euw.a(sb9.toString());
                    return;
                }
            }
            return;
        }
    }
}
