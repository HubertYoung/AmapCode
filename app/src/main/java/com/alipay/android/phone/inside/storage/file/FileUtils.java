package com.alipay.android.phone.inside.storage.file;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.io.File;

public class FileUtils {
    private static boolean a(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        if (lastIndexOf == -1) {
            lastIndexOf = str.lastIndexOf("\\");
        }
        if (lastIndexOf == -1) {
            return false;
        }
        String substring = str.substring(0, lastIndexOf);
        boolean z = true;
        try {
            File file = new File(substring);
            if (!file.exists()) {
                z = file.mkdirs();
            }
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return z;
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r8v2, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r3v1, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r8v4, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r3v2, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r8v6 */
    /* JADX WARNING: type inference failed for: r8v7 */
    /* JADX WARNING: type inference failed for: r4v2, types: [java.io.OutputStream, java.io.BufferedOutputStream] */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r8v8 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r8v9 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v7, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r7v14, types: [java.io.ByteArrayInputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r8v14 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r8v16 */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: type inference failed for: r3v12 */
    /* JADX WARNING: type inference failed for: r3v13 */
    /* JADX WARNING: type inference failed for: r3v14 */
    /* JADX WARNING: type inference failed for: r3v15 */
    /* JADX WARNING: type inference failed for: r3v16 */
    /* JADX WARNING: type inference failed for: r8v20 */
    /* JADX WARNING: type inference failed for: r3v17 */
    /* JADX WARNING: type inference failed for: r3v18 */
    /* JADX WARNING: type inference failed for: r3v19 */
    /* JADX WARNING: type inference failed for: r3v20 */
    /* JADX WARNING: type inference failed for: r3v21 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v4
      assigns: [?[OBJECT, ARRAY], ?[int, float, boolean, short, byte, char, OBJECT, ARRAY]]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], java.io.OutputStream, ?[OBJECT, ARRAY], java.io.InputStream]
      mth insns count: 96
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00a7 A[SYNTHETIC, Splitter:B:51:0x00a7] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00b7 A[SYNTHETIC, Splitter:B:56:0x00b7] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00ca A[SYNTHETIC, Splitter:B:64:0x00ca] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00da A[SYNTHETIC, Splitter:B:69:0x00da] */
    /* JADX WARNING: Unknown variable types count: 12 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.String r7, java.lang.String r8) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            if (r8 != 0) goto L_0x000c
            java.lang.String r8 = ""
        L_0x000c:
            r0 = 1
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]
            a(r7)
            r3 = 0
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            r4.<init>(r7)     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            boolean r5 = r4.exists()     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            if (r5 != 0) goto L_0x002e
            boolean r4 = r4.createNewFile()     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            if (r4 != 0) goto L_0x002e
            java.lang.Exception r7 = new java.lang.Exception     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            java.lang.String r8 = "cache file create error."
            r7.<init>(r8)     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            throw r7     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
        L_0x002e:
            java.io.BufferedOutputStream r4 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            r5.<init>(r7, r1)     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            r4.<init>(r5)     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            java.lang.String r7 = ""
            boolean r7 = android.text.TextUtils.equals(r8, r7)     // Catch:{ Throwable -> 0x0090, all -> 0x008c }
            if (r7 == 0) goto L_0x0048
            byte[] r7 = r8.getBytes()     // Catch:{ Throwable -> 0x0090, all -> 0x008c }
            r4.write(r7)     // Catch:{ Throwable -> 0x0090, all -> 0x008c }
            goto L_0x005d
        L_0x0048:
            java.io.ByteArrayInputStream r7 = new java.io.ByteArrayInputStream     // Catch:{ Throwable -> 0x0090, all -> 0x008c }
            byte[] r8 = r8.getBytes()     // Catch:{ Throwable -> 0x0090, all -> 0x008c }
            r7.<init>(r8)     // Catch:{ Throwable -> 0x0090, all -> 0x008c }
        L_0x0051:
            int r8 = r7.read(r2)     // Catch:{ Throwable -> 0x0086, all -> 0x0080 }
            r3 = -1
            if (r8 == r3) goto L_0x005c
            r4.write(r2, r1, r8)     // Catch:{ Throwable -> 0x0086, all -> 0x0080 }
            goto L_0x0051
        L_0x005c:
            r3 = r7
        L_0x005d:
            r4.flush()     // Catch:{ Throwable -> 0x0090, all -> 0x008c }
            r4.close()     // Catch:{ Exception -> 0x0064 }
            goto L_0x006e
        L_0x0064:
            r7 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r8 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r1 = "inside"
            r8.b(r1, r7)
        L_0x006e:
            if (r3 == 0) goto L_0x00c6
            r3.close()     // Catch:{ Exception -> 0x0075 }
            goto L_0x00c6
        L_0x0075:
            r7 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r8 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r1 = "inside"
            r8.b(r1, r7)
            goto L_0x00c6
        L_0x0080:
            r8 = move-exception
            r3 = r4
            r6 = r8
            r8 = r7
            r7 = r6
            goto L_0x00c8
        L_0x0086:
            r8 = move-exception
            r3 = r4
            r6 = r8
            r8 = r7
            r7 = r6
            goto L_0x0099
        L_0x008c:
            r7 = move-exception
            r8 = r3
            r3 = r4
            goto L_0x00c8
        L_0x0090:
            r7 = move-exception
            r8 = r3
            r3 = r4
            goto L_0x0099
        L_0x0094:
            r7 = move-exception
            r8 = r3
            goto L_0x00c8
        L_0x0097:
            r7 = move-exception
            r8 = r3
        L_0x0099:
            com.alipay.android.phone.inside.log.api.ex.ExceptionLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.e()     // Catch:{ all -> 0x00c7 }
            java.lang.String r2 = "storage"
            java.lang.String r4 = "FileWriteEx"
            r0.a(r2, r4, r7)     // Catch:{ all -> 0x00c7 }
            if (r3 == 0) goto L_0x00b5
            r3.close()     // Catch:{ Exception -> 0x00ab }
            goto L_0x00b5
        L_0x00ab:
            r7 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r2 = "inside"
            r0.b(r2, r7)
        L_0x00b5:
            if (r8 == 0) goto L_0x00c5
            r8.close()     // Catch:{ Exception -> 0x00bb }
            goto L_0x00c5
        L_0x00bb:
            r7 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r8 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r0 = "inside"
            r8.b(r0, r7)
        L_0x00c5:
            r0 = 0
        L_0x00c6:
            return r0
        L_0x00c7:
            r7 = move-exception
        L_0x00c8:
            if (r3 == 0) goto L_0x00d8
            r3.close()     // Catch:{ Exception -> 0x00ce }
            goto L_0x00d8
        L_0x00ce:
            r0 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r2 = "inside"
            r1.b(r2, r0)
        L_0x00d8:
            if (r8 == 0) goto L_0x00e8
            r8.close()     // Catch:{ Exception -> 0x00de }
            goto L_0x00e8
        L_0x00de:
            r8 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r1 = "inside"
            r0.b(r1, r8)
        L_0x00e8:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.storage.file.FileUtils.a(java.lang.String, java.lang.String):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x004f A[SYNTHETIC, Splitter:B:24:0x004f] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0062 A[SYNTHETIC, Splitter:B:32:0x0062] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.io.File r6) {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x0071
            boolean r1 = r6.exists()
            if (r1 != 0) goto L_0x000b
            goto L_0x0071
        L_0x000b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0042, all -> 0x003f }
            r3.<init>(r6)     // Catch:{ Exception -> 0x0042, all -> 0x003f }
        L_0x0019:
            int r6 = r3.read(r2)     // Catch:{ Exception -> 0x003d }
            r4 = -1
            if (r6 == r4) goto L_0x002a
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x003d }
            r5 = 0
            r4.<init>(r2, r5, r6)     // Catch:{ Exception -> 0x003d }
            r1.append(r4)     // Catch:{ Exception -> 0x003d }
            goto L_0x0019
        L_0x002a:
            java.lang.String r6 = r1.toString()     // Catch:{ Exception -> 0x003d }
            r3.close()     // Catch:{ Exception -> 0x0032 }
            goto L_0x005e
        L_0x0032:
            r0 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r2 = "inside"
            r1.b(r2, r0)
            goto L_0x005e
        L_0x003d:
            r6 = move-exception
            goto L_0x0044
        L_0x003f:
            r6 = move-exception
            r3 = r0
            goto L_0x0060
        L_0x0042:
            r6 = move-exception
            r3 = r0
        L_0x0044:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x005f }
            java.lang.String r2 = "inside"
            r1.c(r2, r6)     // Catch:{ all -> 0x005f }
            if (r3 == 0) goto L_0x005d
            r3.close()     // Catch:{ Exception -> 0x0053 }
            goto L_0x005d
        L_0x0053:
            r6 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r2 = "inside"
            r1.b(r2, r6)
        L_0x005d:
            r6 = r0
        L_0x005e:
            return r6
        L_0x005f:
            r6 = move-exception
        L_0x0060:
            if (r3 == 0) goto L_0x0070
            r3.close()     // Catch:{ Exception -> 0x0066 }
            goto L_0x0070
        L_0x0066:
            r0 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r2 = "inside"
            r1.b(r2, r0)
        L_0x0070:
            throw r6
        L_0x0071:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.storage.file.FileUtils.a(java.io.File):java.lang.String");
    }
}
