package defpackage;

import java.io.File;
import java.io.FilenameFilter;

/* renamed from: bng reason: default package */
/* compiled from: UploadFileFilter */
public final class bng {
    private File a;

    public bng(File file) {
        this.a = file;
    }

    public final File[] a() {
        if (this.a == null || !this.a.exists()) {
            return null;
        }
        File file = this.a;
        if (file != null && file.exists()) {
            File[] listFiles = file.listFiles(new FilenameFilter() {
                public final boolean accept(File file, String str) {
                    return str.endsWith("zip") || new File(file, str).length() == 0;
                }
            });
            if (!(listFiles == null || listFiles.length == 0)) {
                for (File file2 : listFiles) {
                    StringBuilder sb = new StringBuilder("file name = ");
                    sb.append(file2.getName());
                    sb.append(" is delete");
                    file2.delete();
                }
            }
        }
        return this.a.listFiles(new FilenameFilter() {
            public final boolean accept(File file, String str) {
                return str.startsWith("crash") && str.endsWith("tmp") && new File(file, str).length() != 0;
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:65:0x00b6 A[SYNTHETIC, Splitter:B:65:0x00b6] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00bb A[SYNTHETIC, Splitter:B:69:0x00bb] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x00c3 A[SYNTHETIC, Splitter:B:76:0x00c3] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x00c8 A[SYNTHETIC, Splitter:B:80:0x00c8] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.io.File a(java.util.List<java.io.File> r19, java.lang.String r20) {
        /*
            r18 = this;
            r1 = r20
            boolean r2 = android.text.TextUtils.isEmpty(r20)
            r3 = 0
            if (r2 == 0) goto L_0x000a
            return r3
        L_0x000a:
            java.io.File[] r2 = r18.a()
            if (r2 == 0) goto L_0x00cc
            int r4 = r2.length
            if (r4 != 0) goto L_0x0015
            goto L_0x00cc
        L_0x0015:
            r4 = 0
            java.io.File r6 = new java.io.File
            r6.<init>(r1)
            java.util.zip.ZipOutputStream r7 = new java.util.zip.ZipOutputStream     // Catch:{ Exception -> 0x00ad, all -> 0x00a8 }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00ad, all -> 0x00a8 }
            r9 = 1
            r8.<init>(r6, r9)     // Catch:{ Exception -> 0x00ad, all -> 0x00a8 }
            r7.<init>(r8)     // Catch:{ Exception -> 0x00ad, all -> 0x00a8 }
            r8 = 1024(0x400, float:1.435E-42)
            byte[] r9 = new byte[r8]     // Catch:{ Exception -> 0x00a4, all -> 0x00a0 }
            int r10 = r2.length     // Catch:{ Exception -> 0x00a4, all -> 0x00a0 }
            r11 = 0
            r12 = r4
            r4 = 0
            r5 = r3
        L_0x0030:
            if (r4 >= r10) goto L_0x0081
            r14 = r2[r4]     // Catch:{ Exception -> 0x007d, all -> 0x0079 }
            long r15 = r14.length()     // Catch:{ Exception -> 0x007d, all -> 0x0079 }
            r17 = 0
            long r12 = r12 + r15
            r15 = 670000(0xa3930, double:3.31024E-318)
            int r12 = (r12 > r15 ? 1 : (r12 == r15 ? 0 : -1))
            if (r12 >= 0) goto L_0x0081
            java.io.BufferedInputStream r12 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x007d, all -> 0x0079 }
            java.io.FileInputStream r13 = new java.io.FileInputStream     // Catch:{ Exception -> 0x007d, all -> 0x0079 }
            r13.<init>(r14)     // Catch:{ Exception -> 0x007d, all -> 0x0079 }
            r12.<init>(r13)     // Catch:{ Exception -> 0x007d, all -> 0x0079 }
            java.util.zip.ZipEntry r5 = new java.util.zip.ZipEntry     // Catch:{ Exception -> 0x0076 }
            java.lang.String r13 = r14.getName()     // Catch:{ Exception -> 0x0076 }
            r5.<init>(r13)     // Catch:{ Exception -> 0x0076 }
            r7.putNextEntry(r5)     // Catch:{ Exception -> 0x0076 }
        L_0x0058:
            int r5 = r12.read(r9, r11, r8)     // Catch:{ Exception -> 0x0076 }
            if (r5 <= 0) goto L_0x0062
            r7.write(r9, r11, r5)     // Catch:{ Exception -> 0x0076 }
            goto L_0x0058
        L_0x0062:
            long r15 = r6.length()     // Catch:{ Exception -> 0x0076 }
            r7.closeEntry()     // Catch:{ Exception -> 0x0076 }
            r12.close()     // Catch:{ Exception -> 0x0076 }
            r13 = r19
            r13.add(r14)     // Catch:{ Exception -> 0x0076 }
            int r4 = r4 + 1
            r5 = r12
            r12 = r15
            goto L_0x0030
        L_0x0076:
            r0 = move-exception
            r1 = r0
            goto L_0x00b1
        L_0x0079:
            r0 = move-exception
            r1 = r0
            r12 = r5
            goto L_0x00c1
        L_0x007d:
            r0 = move-exception
            r1 = r0
            r12 = r5
            goto L_0x00b1
        L_0x0081:
            r13 = r19
            boolean r2 = r19.isEmpty()     // Catch:{ Exception -> 0x007d, all -> 0x0079 }
            if (r2 == 0) goto L_0x0092
            r7.close()     // Catch:{ IOException -> 0x008c }
        L_0x008c:
            if (r5 == 0) goto L_0x0091
            r5.close()     // Catch:{ IOException -> 0x0091 }
        L_0x0091:
            return r3
        L_0x0092:
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x007d, all -> 0x0079 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x007d, all -> 0x0079 }
            r7.close()     // Catch:{ IOException -> 0x009a }
        L_0x009a:
            if (r5 == 0) goto L_0x009f
            r5.close()     // Catch:{ IOException -> 0x009f }
        L_0x009f:
            return r2
        L_0x00a0:
            r0 = move-exception
            r1 = r0
            r12 = r3
            goto L_0x00c1
        L_0x00a4:
            r0 = move-exception
            r1 = r0
            r12 = r3
            goto L_0x00b1
        L_0x00a8:
            r0 = move-exception
            r1 = r0
            r7 = r3
            r12 = r7
            goto L_0x00c1
        L_0x00ad:
            r0 = move-exception
            r1 = r0
            r7 = r3
            r12 = r7
        L_0x00b1:
            r1.printStackTrace()     // Catch:{ all -> 0x00bf }
            if (r7 == 0) goto L_0x00b9
            r7.close()     // Catch:{ IOException -> 0x00b9 }
        L_0x00b9:
            if (r12 == 0) goto L_0x00be
            r12.close()     // Catch:{ IOException -> 0x00be }
        L_0x00be:
            return r3
        L_0x00bf:
            r0 = move-exception
            r1 = r0
        L_0x00c1:
            if (r7 == 0) goto L_0x00c6
            r7.close()     // Catch:{ IOException -> 0x00c6 }
        L_0x00c6:
            if (r12 == 0) goto L_0x00cb
            r12.close()     // Catch:{ IOException -> 0x00cb }
        L_0x00cb:
            throw r1
        L_0x00cc:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bng.a(java.util.List, java.lang.String):java.io.File");
    }
}
