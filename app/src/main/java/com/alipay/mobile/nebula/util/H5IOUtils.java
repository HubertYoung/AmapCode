package com.alipay.mobile.nebula.util;

import com.alipay.mobile.nebula.io.ByteArrayPool;
import java.io.Closeable;
import java.io.IOException;

public class H5IOUtils {
    private static final String TAG = "H5IOUtils";
    private static final ByteArrayPool sByteArrayPool = new ByteArrayPool(20480);

    public static ByteArrayPool getByteArrayPool() {
        return sByteArrayPool;
    }

    public static byte[] getBuf(int size) {
        return getByteArrayPool().getBuf(size);
    }

    public static void returnBuf(byte[] buffer) {
        getByteArrayPool().returnBuf(buffer);
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [byte[], java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r5v6, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r5v7, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: type inference failed for: r5v9 */
    /* JADX WARNING: type inference failed for: r5v10 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v0, types: [byte[], java.io.Closeable]
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY]]
      uses: [byte[], java.io.Closeable]
      mth insns count: 31
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
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] inputToByte(java.io.InputStream r7) {
        /*
            r5 = 0
            r2 = 0
            r0 = 0
            if (r7 != 0) goto L_0x000c
            returnBuf(r5)
            closeQuietly(r5)
        L_0x000b:
            return r5
        L_0x000c:
            r6 = 2048(0x800, float:2.87E-42)
            byte[] r2 = getBuf(r6)     // Catch:{ Exception -> 0x0048 }
            com.alipay.mobile.nebula.io.PoolingByteArrayOutputStream r1 = new com.alipay.mobile.nebula.io.PoolingByteArrayOutputStream     // Catch:{ Exception -> 0x0048 }
            r1.<init>()     // Catch:{ Exception -> 0x0048 }
        L_0x0017:
            int r3 = r7.read(r2)     // Catch:{ Exception -> 0x0023, all -> 0x0045 }
            r6 = -1
            if (r3 == r6) goto L_0x0031
            r6 = 0
            r1.write(r2, r6, r3)     // Catch:{ Exception -> 0x0023, all -> 0x0045 }
            goto L_0x0017
        L_0x0023:
            r4 = move-exception
            r0 = r1
        L_0x0025:
            java.lang.String r6 = "H5IOUtils"
            com.alipay.mobile.nebula.util.H5Log.e(r6, r4)     // Catch:{ all -> 0x003d }
            returnBuf(r2)
            closeQuietly(r0)
            goto L_0x000b
        L_0x0031:
            byte[] r5 = r1.toByteArray()     // Catch:{ Exception -> 0x0023, all -> 0x0045 }
            returnBuf(r2)
            closeQuietly(r1)
            r0 = r1
            goto L_0x000b
        L_0x003d:
            r5 = move-exception
        L_0x003e:
            returnBuf(r2)
            closeQuietly(r0)
            throw r5
        L_0x0045:
            r5 = move-exception
            r0 = r1
            goto L_0x003e
        L_0x0048:
            r4 = move-exception
            goto L_0x0025
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebula.util.H5IOUtils.inputToByte(java.io.InputStream):byte[]");
    }
}
