package com.alipay.mobile.security.bio.utils;

import java.io.File;

public class ZipUtils {
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r4v1, types: [java.util.zip.ZipFile] */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r4v4, types: [java.util.zip.ZipFile] */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v4, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r2v18, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r2v24, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r2v27 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: type inference failed for: r2v29 */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0146, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0147, code lost:
        r1 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x018b, code lost:
        r0 = th;
        r4 = r4;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v6
      assigns: []
      uses: []
      mth insns count: 141
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
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x013d A[SYNTHETIC, Splitter:B:50:0x013d] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0142 A[SYNTHETIC, Splitter:B:53:0x0142] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x016c A[SYNTHETIC, Splitter:B:65:0x016c] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x018b A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x000a] */
    /* JADX WARNING: Unknown variable types count: 8 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void unzip(java.lang.String r10, java.lang.String r11) {
        /*
            r1 = 0
            r9 = -1
            delAllFile(r10)
            java.util.zip.ZipFile r4 = new java.util.zip.ZipFile     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            r4.<init>(r10)     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            java.util.Enumeration r5 = r4.entries()     // Catch:{ IOException -> 0x0146, all -> 0x018b }
            java.io.File r0 = new java.io.File     // Catch:{ IOException -> 0x0146, all -> 0x018b }
            r0.<init>(r11)     // Catch:{ IOException -> 0x0146, all -> 0x018b }
            boolean r2 = r0.exists()     // Catch:{ IOException -> 0x0146, all -> 0x018b }
            if (r2 == 0) goto L_0x0020
            java.lang.String r2 = r0.toString()     // Catch:{ IOException -> 0x0146, all -> 0x018b }
            delAllFile(r2)     // Catch:{ IOException -> 0x0146, all -> 0x018b }
        L_0x0020:
            java.lang.String r2 = "unzip mkdirs"
            com.alipay.mobile.security.bio.utils.BioLog.i(r2)     // Catch:{ IOException -> 0x0146, all -> 0x018b }
            r0.mkdirs()     // Catch:{ IOException -> 0x0146, all -> 0x018b }
        L_0x0028:
            boolean r0 = r5.hasMoreElements()     // Catch:{ IOException -> 0x0146, all -> 0x018b }
            if (r0 == 0) goto L_0x0179
            java.lang.Object r0 = r5.nextElement()     // Catch:{ IOException -> 0x0146, all -> 0x018b }
            java.util.zip.ZipEntry r0 = (java.util.zip.ZipEntry) r0     // Catch:{ IOException -> 0x0146, all -> 0x018b }
            java.lang.String r2 = r0.getName()     // Catch:{ IOException -> 0x0146, all -> 0x018b }
            boolean r3 = r0.isDirectory()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            if (r3 == 0) goto L_0x007b
            java.lang.String r0 = r0.getName()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r2 = 0
            int r3 = r0.length()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            int r3 = r3 + -1
            java.lang.String r0 = r0.substring(r2, r3)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.io.File r2 = new java.io.File     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r3.<init>()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r3 = r3.append(r11)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.String r6 = java.io.File.separator     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r3 = r3.append(r6)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r2.<init>(r0)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r2.mkdirs()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r2 = r1
            r3 = r1
        L_0x006e:
            if (r3 == 0) goto L_0x0073
            r3.close()     // Catch:{ IOException -> 0x017d, all -> 0x018b }
        L_0x0073:
            if (r2 == 0) goto L_0x0028
            r2.close()     // Catch:{ IOException -> 0x0079, all -> 0x018b }
            goto L_0x0028
        L_0x0079:
            r0 = move-exception
            goto L_0x0028
        L_0x007b:
            java.lang.String r3 = "\\"
            int r3 = r2.lastIndexOf(r3)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            if (r3 == r9) goto L_0x00a7
            java.io.File r6 = new java.io.File     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r7.<init>()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r7 = r7.append(r11)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.String r8 = java.io.File.separator     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r8 = 0
            java.lang.String r3 = r2.substring(r8, r3)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r3 = r7.append(r3)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r6.<init>(r3)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r6.mkdirs()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
        L_0x00a7:
            java.lang.String r3 = "/"
            int r3 = r2.lastIndexOf(r3)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            if (r3 == r9) goto L_0x00d3
            java.io.File r6 = new java.io.File     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r7.<init>()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r7 = r7.append(r11)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.String r8 = java.io.File.separator     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r8 = 0
            java.lang.String r2 = r2.substring(r8, r3)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r2 = r7.append(r2)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r6.<init>(r2)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r6.mkdirs()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
        L_0x00d3:
            java.io.File r6 = new java.io.File     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r2.<init>()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r2 = r2.append(r11)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.String r3 = java.io.File.separator     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.String r3 = r0.getName()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            r6.<init>(r2)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            boolean r2 = r6.exists()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            if (r2 == 0) goto L_0x00fc
            r6.delete()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
        L_0x00fc:
            r6.createNewFile()     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.io.InputStream r3 = r4.getInputStream(r0)     // Catch:{ IOException -> 0x0195, all -> 0x018f }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0198, all -> 0x0192 }
            r2.<init>(r6)     // Catch:{ IOException -> 0x0198, all -> 0x0192 }
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]     // Catch:{ IOException -> 0x0117, all -> 0x0175 }
        L_0x010c:
            int r6 = r3.read(r0)     // Catch:{ IOException -> 0x0117, all -> 0x0175 }
            if (r6 == r9) goto L_0x0170
            r7 = 0
            r2.write(r0, r7, r6)     // Catch:{ IOException -> 0x0117, all -> 0x0175 }
            goto L_0x010c
        L_0x0117:
            r0 = move-exception
            r1 = r2
            r2 = r3
        L_0x011a:
            java.lang.String r3 = r0.toString()     // Catch:{ all -> 0x013a }
            com.alipay.mobile.security.bio.utils.BioLog.e(r3)     // Catch:{ all -> 0x013a }
            java.io.IOException r3 = new java.io.IOException     // Catch:{ all -> 0x013a }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x013a }
            java.lang.String r6 = "解压失败："
            r5.<init>(r6)     // Catch:{ all -> 0x013a }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x013a }
            java.lang.StringBuilder r0 = r5.append(r0)     // Catch:{ all -> 0x013a }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x013a }
            r3.<init>(r0)     // Catch:{ all -> 0x013a }
            throw r3     // Catch:{ all -> 0x013a }
        L_0x013a:
            r0 = move-exception
        L_0x013b:
            if (r2 == 0) goto L_0x0140
            r2.close()     // Catch:{ IOException -> 0x0180, all -> 0x018b }
        L_0x0140:
            if (r1 == 0) goto L_0x0145
            r1.close()     // Catch:{ IOException -> 0x0182, all -> 0x018b }
        L_0x0145:
            throw r0     // Catch:{ IOException -> 0x0146, all -> 0x018b }
        L_0x0146:
            r0 = move-exception
            r1 = r4
        L_0x0148:
            java.lang.String r2 = r0.toString()     // Catch:{ all -> 0x0168 }
            com.alipay.mobile.security.bio.utils.BioLog.e(r2)     // Catch:{ all -> 0x0168 }
            java.io.IOException r2 = new java.io.IOException     // Catch:{ all -> 0x0168 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0168 }
            java.lang.String r4 = "解压失败："
            r3.<init>(r4)     // Catch:{ all -> 0x0168 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0168 }
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ all -> 0x0168 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0168 }
            r2.<init>(r0)     // Catch:{ all -> 0x0168 }
            throw r2     // Catch:{ all -> 0x0168 }
        L_0x0168:
            r0 = move-exception
            r4 = r1
        L_0x016a:
            if (r4 == 0) goto L_0x016f
            r4.close()     // Catch:{ IOException -> 0x0186 }
        L_0x016f:
            throw r0
        L_0x0170:
            r2.flush()     // Catch:{ IOException -> 0x0117, all -> 0x0175 }
            goto L_0x006e
        L_0x0175:
            r0 = move-exception
            r1 = r2
            r2 = r3
            goto L_0x013b
        L_0x0179:
            r4.close()     // Catch:{ IOException -> 0x0184 }
        L_0x017c:
            return
        L_0x017d:
            r0 = move-exception
            goto L_0x0073
        L_0x0180:
            r2 = move-exception
            goto L_0x0140
        L_0x0182:
            r1 = move-exception
            goto L_0x0145
        L_0x0184:
            r0 = move-exception
            goto L_0x017c
        L_0x0186:
            r1 = move-exception
            goto L_0x016f
        L_0x0188:
            r0 = move-exception
            r4 = r1
            goto L_0x016a
        L_0x018b:
            r0 = move-exception
            goto L_0x016a
        L_0x018d:
            r0 = move-exception
            goto L_0x0148
        L_0x018f:
            r0 = move-exception
            r2 = r1
            goto L_0x013b
        L_0x0192:
            r0 = move-exception
            r2 = r3
            goto L_0x013b
        L_0x0195:
            r0 = move-exception
            r2 = r1
            goto L_0x011a
        L_0x0198:
            r0 = move-exception
            r2 = r3
            goto L_0x011a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.bio.utils.ZipUtils.unzip(java.lang.String, java.lang.String):void");
    }

    public void delFile(String str) {
        try {
            new File(str.toString()).delete();
        } catch (Exception e) {
        }
    }

    public static void delFolder(String str) {
        try {
            delAllFile(str);
            new File(str.toString()).delete();
        } catch (Exception e) {
        }
    }

    public static void delAllFile(String str) {
        File file;
        File file2 = new File(str);
        if (file2.exists() && file2.isDirectory()) {
            String[] list = file2.list();
            for (int i = 0; i < list.length; i++) {
                if (str.endsWith(File.separator)) {
                    file = new File(str + list[i]);
                } else {
                    file = new File(str + File.separator + list[i]);
                }
                if (file.isFile()) {
                    file.delete();
                }
                if (file.isDirectory()) {
                    delAllFile(str + "/" + list[i]);
                    delFolder(str + "/" + list[i]);
                }
            }
        }
    }
}
