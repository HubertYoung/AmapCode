package defpackage;

/* renamed from: eoc reason: default package */
/* compiled from: FileUtil */
public final class eoc {
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004a A[SYNTHETIC, Splitter:B:25:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0056 A[SYNTHETIC, Splitter:B:32:0x0056] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.io.File r3, java.lang.String r4) {
        /*
            boolean r0 = r3.exists()
            if (r0 != 0) goto L_0x0018
            java.lang.String r0 = r3.getParent()
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            boolean r0 = r1.exists()
            if (r0 != 0) goto L_0x0018
            r1.mkdirs()
        L_0x0018:
            r0 = 0
            java.io.FileWriter r1 = new java.io.FileWriter     // Catch:{ IOException -> 0x0044 }
            r2 = 1
            r1.<init>(r3, r2)     // Catch:{ IOException -> 0x0044 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x003e, all -> 0x003c }
            r3.<init>()     // Catch:{ IOException -> 0x003e, all -> 0x003c }
            r3.append(r4)     // Catch:{ IOException -> 0x003e, all -> 0x003c }
            java.lang.String r4 = "\n"
            r3.append(r4)     // Catch:{ IOException -> 0x003e, all -> 0x003c }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x003e, all -> 0x003c }
            r1.write(r3)     // Catch:{ IOException -> 0x003e, all -> 0x003c }
            r1.close()     // Catch:{ IOException -> 0x0037 }
            return
        L_0x0037:
            r3 = move-exception
            r3.printStackTrace()
            return
        L_0x003c:
            r3 = move-exception
            goto L_0x0054
        L_0x003e:
            r3 = move-exception
            r0 = r1
            goto L_0x0045
        L_0x0041:
            r3 = move-exception
            r1 = r0
            goto L_0x0054
        L_0x0044:
            r3 = move-exception
        L_0x0045:
            r3.printStackTrace()     // Catch:{ all -> 0x0041 }
            if (r0 == 0) goto L_0x0053
            r0.close()     // Catch:{ IOException -> 0x004e }
            goto L_0x0053
        L_0x004e:
            r3 = move-exception
            r3.printStackTrace()
            return
        L_0x0053:
            return
        L_0x0054:
            if (r1 == 0) goto L_0x005e
            r1.close()     // Catch:{ IOException -> 0x005a }
            goto L_0x005e
        L_0x005a:
            r4 = move-exception
            r4.printStackTrace()
        L_0x005e:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eoc.a(java.io.File, java.lang.String):void");
    }
}
