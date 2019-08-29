package com.alipay.android.phone.inside.log.util.sec;

public class GzipUtils {
    /* JADX WARNING: type inference failed for: r6v1, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.util.zip.GZIPOutputStream] */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r6v9 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r6v13, types: [java.io.OutputStream, java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r2v13, types: [java.util.zip.GZIPOutputStream] */
    /* JADX WARNING: type inference failed for: r6v16 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: type inference failed for: r2v16 */
    /* JADX WARNING: type inference failed for: r6v17 */
    /* JADX WARNING: type inference failed for: r6v18 */
    /* JADX WARNING: type inference failed for: r6v19 */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r6v4
      assigns: []
      uses: []
      mth insns count: 74
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
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0076 A[SYNTHETIC, Splitter:B:46:0x0076] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0086 A[SYNTHETIC, Splitter:B:51:0x0086] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0096 A[SYNTHETIC, Splitter:B:56:0x0096] */
    /* JADX WARNING: Unknown variable types count: 9 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(byte[] r6) throws java.io.IOException {
        /*
            r0 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x006d, all -> 0x0067 }
            r1.<init>(r6)     // Catch:{ IOException -> 0x006d, all -> 0x0067 }
            java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0064, all -> 0x0061 }
            r6.<init>()     // Catch:{ IOException -> 0x0064, all -> 0x0061 }
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ IOException -> 0x005c, all -> 0x0057 }
            r2.<init>(r6)     // Catch:{ IOException -> 0x005c, all -> 0x0057 }
            r0 = 4096(0x1000, float:5.74E-42)
            byte[] r0 = new byte[r0]     // Catch:{ IOException -> 0x0055 }
        L_0x0014:
            int r3 = r1.read(r0)     // Catch:{ IOException -> 0x0055 }
            r4 = -1
            if (r3 == r4) goto L_0x0020
            r4 = 0
            r2.write(r0, r4, r3)     // Catch:{ IOException -> 0x0055 }
            goto L_0x0014
        L_0x0020:
            r2.flush()     // Catch:{ IOException -> 0x0055 }
            r2.finish()     // Catch:{ IOException -> 0x0055 }
            byte[] r0 = r6.toByteArray()     // Catch:{ IOException -> 0x0055 }
            r1.close()     // Catch:{ Throwable -> 0x002e }
            goto L_0x0038
        L_0x002e:
            r1 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r3 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r4 = "inside"
            r3.c(r4, r1)
        L_0x0038:
            r6.close()     // Catch:{ Throwable -> 0x003c }
            goto L_0x0046
        L_0x003c:
            r6 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r3 = "inside"
            r1.c(r3, r6)
        L_0x0046:
            r2.close()     // Catch:{ Throwable -> 0x004a }
            goto L_0x0054
        L_0x004a:
            r6 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r2 = "inside"
            r1.c(r2, r6)
        L_0x0054:
            return r0
        L_0x0055:
            r0 = move-exception
            goto L_0x0072
        L_0x0057:
            r2 = move-exception
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x0074
        L_0x005c:
            r2 = move-exception
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x0072
        L_0x0061:
            r6 = move-exception
            r2 = r0
            goto L_0x006a
        L_0x0064:
            r6 = move-exception
            r2 = r0
            goto L_0x0070
        L_0x0067:
            r6 = move-exception
            r1 = r0
            r2 = r1
        L_0x006a:
            r0 = r6
            r6 = r2
            goto L_0x0074
        L_0x006d:
            r6 = move-exception
            r1 = r0
            r2 = r1
        L_0x0070:
            r0 = r6
            r6 = r2
        L_0x0072:
            throw r0     // Catch:{ all -> 0x0073 }
        L_0x0073:
            r0 = move-exception
        L_0x0074:
            if (r1 == 0) goto L_0x0084
            r1.close()     // Catch:{ Throwable -> 0x007a }
            goto L_0x0084
        L_0x007a:
            r1 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r3 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r4 = "inside"
            r3.c(r4, r1)
        L_0x0084:
            if (r6 == 0) goto L_0x0094
            r6.close()     // Catch:{ Throwable -> 0x008a }
            goto L_0x0094
        L_0x008a:
            r6 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r3 = "inside"
            r1.c(r3, r6)
        L_0x0094:
            if (r2 == 0) goto L_0x00a4
            r2.close()     // Catch:{ Throwable -> 0x009a }
            goto L_0x00a4
        L_0x009a:
            r6 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r2 = "inside"
            r1.c(r2, r6)
        L_0x00a4:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.log.util.sec.GzipUtils.a(byte[]):byte[]");
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.io.ByteArrayInputStream] */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r1v7, types: [java.io.ByteArrayInputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r4v6, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r7v1 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r0v17 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v2
      assigns: []
      uses: []
      mth insns count: 84
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
    /* JADX WARNING: Removed duplicated region for block: B:49:0x007f A[SYNTHETIC, Splitter:B:49:0x007f] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x008f A[SYNTHETIC, Splitter:B:54:0x008f] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x009f A[SYNTHETIC, Splitter:B:59:0x009f] */
    /* JADX WARNING: Unknown variable types count: 11 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] b(byte[] r8) throws java.io.IOException {
        /*
            r0 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x0076, all -> 0x0072 }
            r1.<init>(r8)     // Catch:{ IOException -> 0x0076, all -> 0x0072 }
            java.util.zip.GZIPInputStream r8 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x006d, all -> 0x0069 }
            r8.<init>(r1)     // Catch:{ IOException -> 0x006d, all -> 0x0069 }
            r2 = 4096(0x1000, float:5.74E-42)
            byte[] r3 = new byte[r2]     // Catch:{ IOException -> 0x0063, all -> 0x005d }
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0063, all -> 0x005d }
            r4.<init>()     // Catch:{ IOException -> 0x0063, all -> 0x005d }
        L_0x0014:
            r0 = 0
            int r5 = r8.read(r3, r0, r2)     // Catch:{ IOException -> 0x0057, all -> 0x0052 }
            r6 = -1
            if (r5 == r6) goto L_0x0020
            r4.write(r3, r0, r5)     // Catch:{ IOException -> 0x0057, all -> 0x0052 }
            goto L_0x0014
        L_0x0020:
            byte[] r0 = r4.toByteArray()     // Catch:{ IOException -> 0x0057, all -> 0x0052 }
            r4.flush()     // Catch:{ IOException -> 0x0057, all -> 0x0052 }
            r4.close()     // Catch:{ Throwable -> 0x002b }
            goto L_0x0035
        L_0x002b:
            r2 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r3 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r4 = "inside"
            r3.c(r4, r2)
        L_0x0035:
            r8.close()     // Catch:{ Throwable -> 0x0039 }
            goto L_0x0043
        L_0x0039:
            r8 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r2 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r3 = "inside"
            r2.c(r3, r8)
        L_0x0043:
            r1.close()     // Catch:{ Throwable -> 0x0047 }
            goto L_0x0051
        L_0x0047:
            r8 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r2 = "inside"
            r1.c(r2, r8)
        L_0x0051:
            return r0
        L_0x0052:
            r0 = move-exception
            r2 = r1
            r1 = r8
            r8 = r0
            goto L_0x007c
        L_0x0057:
            r0 = move-exception
            r7 = r1
            r1 = r8
            r8 = r0
            r0 = r7
            goto L_0x0079
        L_0x005d:
            r2 = move-exception
            r7 = r1
            r1 = r8
            r8 = r2
            r2 = r7
            goto L_0x007d
        L_0x0063:
            r2 = move-exception
            r4 = r0
            r0 = r1
            r1 = r8
            r8 = r2
            goto L_0x0079
        L_0x0069:
            r8 = move-exception
            r2 = r1
            r1 = r0
            goto L_0x007d
        L_0x006d:
            r8 = move-exception
            r4 = r0
            r0 = r1
            r1 = r4
            goto L_0x0079
        L_0x0072:
            r8 = move-exception
            r1 = r0
            r2 = r1
            goto L_0x007d
        L_0x0076:
            r8 = move-exception
            r1 = r0
            r4 = r1
        L_0x0079:
            throw r8     // Catch:{ all -> 0x007a }
        L_0x007a:
            r8 = move-exception
            r2 = r0
        L_0x007c:
            r0 = r4
        L_0x007d:
            if (r0 == 0) goto L_0x008d
            r0.close()     // Catch:{ Throwable -> 0x0083 }
            goto L_0x008d
        L_0x0083:
            r0 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r3 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r4 = "inside"
            r3.c(r4, r0)
        L_0x008d:
            if (r1 == 0) goto L_0x009d
            r1.close()     // Catch:{ Throwable -> 0x0093 }
            goto L_0x009d
        L_0x0093:
            r0 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r3 = "inside"
            r1.c(r3, r0)
        L_0x009d:
            if (r2 == 0) goto L_0x00ad
            r2.close()     // Catch:{ Throwable -> 0x00a3 }
            goto L_0x00ad
        L_0x00a3:
            r0 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r2 = "inside"
            r1.c(r2, r0)
        L_0x00ad:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.log.util.sec.GzipUtils.b(byte[]):byte[]");
    }
}
