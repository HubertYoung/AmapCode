package defpackage;

import java.io.File;

/* renamed from: cx reason: default package */
/* compiled from: SerializeHelper */
public final class cx {
    private static File a;

    /* JADX WARNING: Can't wrap try/catch for region: R(4:53|(0)|57|58) */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0060, code lost:
        r13 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0061, code lost:
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0064, code lost:
        r13 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0065, code lost:
        r7 = null;
        r8 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        r7.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:57:0x00f5 */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0060 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:8:0x0011] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x007c A[Catch:{ all -> 0x00ef }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0083 A[SYNTHETIC, Splitter:B:36:0x0083] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a1  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00f2 A[SYNTHETIC, Splitter:B:55:0x00f2] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:57:0x00f5=Splitter:B:57:0x00f5, B:40:0x0087=Splitter:B:40:0x0087} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(java.io.Serializable r13, java.io.File r14, anet.channel.statist.StrategyStatObject r15) {
        /*
            java.lang.Class<cx> r0 = defpackage.cx.class
            monitor-enter(r0)
            r1 = 0
            r2 = 0
            if (r13 == 0) goto L_0x00f6
            if (r14 != 0) goto L_0x000b
            goto L_0x00f6
        L_0x000b:
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0101 }
            r5 = 2
            r6 = 1
            java.util.UUID r7 = java.util.UUID.randomUUID()     // Catch:{ Exception -> 0x0064, all -> 0x0060 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0064, all -> 0x0060 }
            java.lang.String r8 = "-"
            java.lang.String r9 = ""
            java.lang.String r7 = r7.replace(r8, r9)     // Catch:{ Exception -> 0x0064, all -> 0x0060 }
            java.io.File r8 = a     // Catch:{ Exception -> 0x0064, all -> 0x0060 }
            if (r8 != 0) goto L_0x0031
            android.content.Context r8 = defpackage.m.a()     // Catch:{ Exception -> 0x0064, all -> 0x0060 }
            if (r8 == 0) goto L_0x0031
            java.io.File r8 = r8.getCacheDir()     // Catch:{ Exception -> 0x0064, all -> 0x0060 }
            a = r8     // Catch:{ Exception -> 0x0064, all -> 0x0060 }
        L_0x0031:
            java.io.File r8 = new java.io.File     // Catch:{ Exception -> 0x0064, all -> 0x0060 }
            java.io.File r9 = a     // Catch:{ Exception -> 0x0064, all -> 0x0060 }
            r8.<init>(r9, r7)     // Catch:{ Exception -> 0x0064, all -> 0x0060 }
            r8.createNewFile()     // Catch:{ Exception -> 0x005d, all -> 0x0060 }
            r8.setReadable(r6)     // Catch:{ Exception -> 0x005d, all -> 0x0060 }
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x005d, all -> 0x0060 }
            r7.<init>(r8)     // Catch:{ Exception -> 0x005d, all -> 0x0060 }
            java.io.ObjectOutputStream r9 = new java.io.ObjectOutputStream     // Catch:{ Exception -> 0x005b }
            java.io.BufferedOutputStream r10 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x005b }
            r10.<init>(r7)     // Catch:{ Exception -> 0x005b }
            r9.<init>(r10)     // Catch:{ Exception -> 0x005b }
            r9.writeObject(r13)     // Catch:{ Exception -> 0x005b }
            r9.flush()     // Catch:{ Exception -> 0x005b }
            r9.close()     // Catch:{ Exception -> 0x005b }
            r7.close()     // Catch:{ IOException -> 0x0059 }
        L_0x0059:
            r13 = 1
            goto L_0x0087
        L_0x005b:
            r13 = move-exception
            goto L_0x0067
        L_0x005d:
            r13 = move-exception
            r7 = r2
            goto L_0x0067
        L_0x0060:
            r13 = move-exception
            r7 = r2
            goto L_0x00f0
        L_0x0064:
            r13 = move-exception
            r7 = r2
            r8 = r7
        L_0x0067:
            java.lang.String r9 = "awcn.SerializeHelper"
            java.lang.String r10 = "persist fail. "
            java.lang.Object[] r11 = new java.lang.Object[r5]     // Catch:{ all -> 0x00ef }
            java.lang.String r12 = "file"
            r11[r1] = r12     // Catch:{ all -> 0x00ef }
            java.lang.String r12 = r14.getName()     // Catch:{ all -> 0x00ef }
            r11[r6] = r12     // Catch:{ all -> 0x00ef }
            defpackage.cl.e(r9, r10, r2, r11)     // Catch:{ all -> 0x00ef }
            if (r15 == 0) goto L_0x0081
            java.lang.String r9 = "SerializeHelper.persist()"
            r15.appendErrorTrace(r9, r13)     // Catch:{ all -> 0x00ef }
        L_0x0081:
            if (r7 == 0) goto L_0x0086
            r7.close()     // Catch:{ IOException -> 0x0086 }
        L_0x0086:
            r13 = 0
        L_0x0087:
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0101 }
            r7 = 0
            long r9 = r9 - r3
            if (r15 == 0) goto L_0x009f
            java.lang.String r3 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x0101 }
            r15.writeTempFilePath = r3     // Catch:{ all -> 0x0101 }
            java.lang.String r3 = java.lang.String.valueOf(r14)     // Catch:{ all -> 0x0101 }
            r15.writeStrategyFilePath = r3     // Catch:{ all -> 0x0101 }
            r15.isTempWriteSucceed = r13     // Catch:{ all -> 0x0101 }
            r15.writeCostTime = r9     // Catch:{ all -> 0x0101 }
        L_0x009f:
            if (r13 == 0) goto L_0x00ed
            boolean r13 = r8.renameTo(r14)     // Catch:{ all -> 0x0101 }
            if (r13 == 0) goto L_0x00d7
            java.lang.String r3 = "awcn.SerializeHelper"
            java.lang.String r4 = "persist end."
            r7 = 6
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x0101 }
            java.lang.String r8 = "file"
            r7[r1] = r8     // Catch:{ all -> 0x0101 }
            java.io.File r1 = r14.getAbsoluteFile()     // Catch:{ all -> 0x0101 }
            r7[r6] = r1     // Catch:{ all -> 0x0101 }
            java.lang.String r1 = "size"
            r7[r5] = r1     // Catch:{ all -> 0x0101 }
            r1 = 3
            long r5 = r14.length()     // Catch:{ all -> 0x0101 }
            java.lang.Long r14 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0101 }
            r7[r1] = r14     // Catch:{ all -> 0x0101 }
            r14 = 4
            java.lang.String r1 = "cost"
            r7[r14] = r1     // Catch:{ all -> 0x0101 }
            r14 = 5
            java.lang.Long r1 = java.lang.Long.valueOf(r9)     // Catch:{ all -> 0x0101 }
            r7[r14] = r1     // Catch:{ all -> 0x0101 }
            defpackage.cl.b(r3, r4, r2, r7)     // Catch:{ all -> 0x0101 }
            goto L_0x00e0
        L_0x00d7:
            java.lang.String r14 = "awcn.SerializeHelper"
            java.lang.String r3 = "rename failed."
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0101 }
            defpackage.cl.d(r14, r3, r2, r1)     // Catch:{ all -> 0x0101 }
        L_0x00e0:
            if (r15 == 0) goto L_0x00ed
            r15.isRenameSucceed = r13     // Catch:{ all -> 0x0101 }
            r15.isSucceed = r13     // Catch:{ all -> 0x0101 }
            z r13 = defpackage.x.a()     // Catch:{ all -> 0x0101 }
            r13.a(r15)     // Catch:{ all -> 0x0101 }
        L_0x00ed:
            monitor-exit(r0)
            return
        L_0x00ef:
            r13 = move-exception
        L_0x00f0:
            if (r7 == 0) goto L_0x00f5
            r7.close()     // Catch:{ IOException -> 0x00f5 }
        L_0x00f5:
            throw r13     // Catch:{ all -> 0x0101 }
        L_0x00f6:
            java.lang.String r13 = "awcn.SerializeHelper"
            java.lang.String r14 = "persist fail. Invalid parameter"
            java.lang.Object[] r15 = new java.lang.Object[r1]     // Catch:{ all -> 0x0101 }
            defpackage.cl.d(r13, r14, r2, r15)     // Catch:{ all -> 0x0101 }
            monitor-exit(r0)
            return
        L_0x0101:
            r13 = move-exception
            monitor-exit(r0)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cx.a(java.io.Serializable, java.io.File, anet.channel.statist.StrategyStatObject):void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:45|(0)|49|50) */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0034, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b2, code lost:
        if (r4 != null) goto L_0x008d;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:49:0x00bd */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00a2 A[Catch:{ all -> 0x00b7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00ad A[Catch:{ all -> 0x00b7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00ba A[SYNTHETIC, Splitter:B:47:0x00ba] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized <T> T a(java.io.File r14, anet.channel.statist.StrategyStatObject r15) {
        /*
            java.lang.Class<cx> r0 = defpackage.cx.class
            monitor-enter(r0)
            if (r15 == 0) goto L_0x000f
            java.lang.String r1 = java.lang.String.valueOf(r14)     // Catch:{ all -> 0x000c }
            r15.readStrategyFilePath = r1     // Catch:{ all -> 0x000c }
            goto L_0x000f
        L_0x000c:
            r14 = move-exception
            goto L_0x00be
        L_0x000f:
            r1 = 0
            r2 = 3
            r3 = 0
            boolean r4 = r14.exists()     // Catch:{ Throwable -> 0x0099, all -> 0x0096 }
            r5 = 2
            r6 = 1
            if (r4 != 0) goto L_0x0035
            boolean r4 = defpackage.cl.a(r2)     // Catch:{ Throwable -> 0x0099, all -> 0x0096 }
            if (r4 == 0) goto L_0x0033
            java.lang.String r4 = "awcn.SerializeHelper"
            java.lang.String r7 = "file not exist."
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0099, all -> 0x0096 }
            java.lang.String r8 = "file"
            r5[r1] = r8     // Catch:{ Throwable -> 0x0099, all -> 0x0096 }
            java.lang.String r14 = r14.getName()     // Catch:{ Throwable -> 0x0099, all -> 0x0096 }
            r5[r6] = r14     // Catch:{ Throwable -> 0x0099, all -> 0x0096 }
            defpackage.cl.c(r4, r7, r3, r5)     // Catch:{ Throwable -> 0x0099, all -> 0x0096 }
        L_0x0033:
            monitor-exit(r0)
            return r3
        L_0x0035:
            if (r15 == 0) goto L_0x0039
            r15.isFileExists = r6     // Catch:{ Throwable -> 0x0099, all -> 0x0096 }
        L_0x0039:
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0099, all -> 0x0096 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0099, all -> 0x0096 }
            r4.<init>(r14)     // Catch:{ Throwable -> 0x0099, all -> 0x0096 }
            java.io.ObjectInputStream r9 = new java.io.ObjectInputStream     // Catch:{ Throwable -> 0x0093 }
            java.io.BufferedInputStream r10 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x0093 }
            r10.<init>(r4)     // Catch:{ Throwable -> 0x0093 }
            r9.<init>(r10)     // Catch:{ Throwable -> 0x0093 }
            java.lang.Object r10 = r9.readObject()     // Catch:{ Throwable -> 0x0093 }
            r9.close()     // Catch:{ Throwable -> 0x0091 }
            long r11 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0091 }
            r9 = 0
            long r11 = r11 - r7
            if (r15 == 0) goto L_0x005f
            r15.isReadObjectSucceed = r6     // Catch:{ Throwable -> 0x0091 }
            r15.readCostTime = r11     // Catch:{ Throwable -> 0x0091 }
        L_0x005f:
            java.lang.String r7 = "awcn.SerializeHelper"
            java.lang.String r8 = "restore end."
            r9 = 6
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x0091 }
            java.lang.String r13 = "file"
            r9[r1] = r13     // Catch:{ Throwable -> 0x0091 }
            java.io.File r13 = r14.getAbsoluteFile()     // Catch:{ Throwable -> 0x0091 }
            r9[r6] = r13     // Catch:{ Throwable -> 0x0091 }
            java.lang.String r6 = "size"
            r9[r5] = r6     // Catch:{ Throwable -> 0x0091 }
            long r5 = r14.length()     // Catch:{ Throwable -> 0x0091 }
            java.lang.Long r14 = java.lang.Long.valueOf(r5)     // Catch:{ Throwable -> 0x0091 }
            r9[r2] = r14     // Catch:{ Throwable -> 0x0091 }
            r14 = 4
            java.lang.String r5 = "cost"
            r9[r14] = r5     // Catch:{ Throwable -> 0x0091 }
            r14 = 5
            java.lang.Long r5 = java.lang.Long.valueOf(r11)     // Catch:{ Throwable -> 0x0091 }
            r9[r14] = r5     // Catch:{ Throwable -> 0x0091 }
            defpackage.cl.b(r7, r8, r3, r9)     // Catch:{ Throwable -> 0x0091 }
        L_0x008d:
            r4.close()     // Catch:{ IOException -> 0x00b5 }
            goto L_0x00b5
        L_0x0091:
            r14 = move-exception
            goto L_0x009c
        L_0x0093:
            r14 = move-exception
            r10 = r3
            goto L_0x009c
        L_0x0096:
            r14 = move-exception
            r4 = r3
            goto L_0x00b8
        L_0x0099:
            r14 = move-exception
            r4 = r3
            r10 = r4
        L_0x009c:
            boolean r2 = defpackage.cl.a(r2)     // Catch:{ all -> 0x00b7 }
            if (r2 == 0) goto L_0x00ab
            java.lang.String r2 = "awcn.SerializeHelper"
            java.lang.String r5 = "restore file fail."
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x00b7 }
            defpackage.cl.a(r2, r5, r3, r14, r1)     // Catch:{ all -> 0x00b7 }
        L_0x00ab:
            if (r15 == 0) goto L_0x00b2
            java.lang.String r1 = "SerializeHelper.restore()"
            r15.appendErrorTrace(r1, r14)     // Catch:{ all -> 0x00b7 }
        L_0x00b2:
            if (r4 == 0) goto L_0x00b5
            goto L_0x008d
        L_0x00b5:
            monitor-exit(r0)
            return r10
        L_0x00b7:
            r14 = move-exception
        L_0x00b8:
            if (r4 == 0) goto L_0x00bd
            r4.close()     // Catch:{ IOException -> 0x00bd }
        L_0x00bd:
            throw r14     // Catch:{ all -> 0x000c }
        L_0x00be:
            monitor-exit(r0)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cx.a(java.io.File, anet.channel.statist.StrategyStatObject):java.lang.Object");
    }
}
