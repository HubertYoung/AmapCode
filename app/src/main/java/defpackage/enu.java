package defpackage;

/* renamed from: enu reason: default package */
/* compiled from: ZipUtil */
public final class enu {
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        r2.closeEntry();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0075, code lost:
        defpackage.enr.a(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0079, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.io.File r6, java.io.File r7) {
        /*
            boolean r0 = r6.isFile()
            r1 = 0
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            boolean r0 = r7.exists()
            if (r0 != 0) goto L_0x0011
            r7.mkdirs()
        L_0x0011:
            r0 = 0
            java.util.zip.ZipInputStream r2 = new java.util.zip.ZipInputStream     // Catch:{ Exception -> 0x0084, all -> 0x007e }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0084, all -> 0x007e }
            r3.<init>(r6)     // Catch:{ Exception -> 0x0084, all -> 0x007e }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0084, all -> 0x007e }
        L_0x001c:
            java.util.zip.ZipEntry r6 = r2.getNextEntry()     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            if (r6 == 0) goto L_0x0072
            boolean r3 = r6.isDirectory()     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            if (r3 != 0) goto L_0x0072
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            java.lang.String r6 = r6.getName()     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            r3.<init>(r7, r6)     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            java.io.File r6 = r3.getParentFile()     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            boolean r6 = r6.exists()     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            if (r6 != 0) goto L_0x0042
            java.io.File r6 = r3.getParentFile()     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            r6.mkdirs()     // Catch:{ Exception -> 0x007c, all -> 0x007a }
        L_0x0042:
            java.io.BufferedOutputStream r6 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x006a, all -> 0x0064 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x006a, all -> 0x0064 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x006a, all -> 0x0064 }
            r6.<init>(r4)     // Catch:{ Exception -> 0x006a, all -> 0x0064 }
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x006b, all -> 0x0062 }
        L_0x0050:
            int r4 = r2.read(r3)     // Catch:{ Exception -> 0x006b, all -> 0x0062 }
            r5 = -1
            if (r4 == r5) goto L_0x005b
            r6.write(r3, r1, r4)     // Catch:{ Exception -> 0x006b, all -> 0x0062 }
            goto L_0x0050
        L_0x005b:
            r6.flush()     // Catch:{ Exception -> 0x006b, all -> 0x0062 }
            defpackage.enr.a(r6)     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            goto L_0x001c
        L_0x0062:
            r7 = move-exception
            goto L_0x0066
        L_0x0064:
            r7 = move-exception
            r6 = r0
        L_0x0066:
            defpackage.enr.a(r6)     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            throw r7     // Catch:{ Exception -> 0x007c, all -> 0x007a }
        L_0x006a:
            r6 = r0
        L_0x006b:
            defpackage.enr.a(r6)     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            defpackage.enr.a(r2)
            return r1
        L_0x0072:
            r2.closeEntry()     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            defpackage.enr.a(r2)
            r6 = 1
            return r6
        L_0x007a:
            r6 = move-exception
            goto L_0x0080
        L_0x007c:
            r0 = r2
            goto L_0x0084
        L_0x007e:
            r6 = move-exception
            r2 = r0
        L_0x0080:
            defpackage.enr.a(r2)
            throw r6
        L_0x0084:
            defpackage.enr.a(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.enu.a(java.io.File, java.io.File):boolean");
    }
}
