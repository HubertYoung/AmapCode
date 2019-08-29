package com.amap.location.common.f;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/* compiled from: GZipUtil */
public class f {
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r1v0, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v4, types: [java.io.OutputStream, java.io.ByteArrayOutputStream, java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r2v5, types: [java.io.Closeable, java.util.zip.GZIPOutputStream] */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v1
      assigns: []
      uses: []
      mth insns count: 37
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
    /* JADX WARNING: Unknown variable types count: 10 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(byte[] r3) throws java.lang.Exception {
        /*
            r0 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0029, all -> 0x0026 }
            r1.<init>()     // Catch:{ Exception -> 0x0029, all -> 0x0026 }
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ Exception -> 0x0022, all -> 0x0020 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0022, all -> 0x0020 }
            r2.write(r3)     // Catch:{ Exception -> 0x001e, all -> 0x001c }
            r2.finish()     // Catch:{ Exception -> 0x001e, all -> 0x001c }
            byte[] r3 = r1.toByteArray()     // Catch:{ Exception -> 0x001e, all -> 0x001c }
            com.amap.location.common.f.g.a(r1)
            com.amap.location.common.f.g.a(r2)
            return r3
        L_0x001c:
            r3 = move-exception
            goto L_0x002e
        L_0x001e:
            r3 = move-exception
            goto L_0x0024
        L_0x0020:
            r3 = move-exception
            goto L_0x002f
        L_0x0022:
            r3 = move-exception
            r2 = r0
        L_0x0024:
            r0 = r1
            goto L_0x002b
        L_0x0026:
            r3 = move-exception
            r1 = r0
            goto L_0x002f
        L_0x0029:
            r3 = move-exception
            r2 = r0
        L_0x002b:
            throw r3     // Catch:{ all -> 0x002c }
        L_0x002c:
            r3 = move-exception
            r1 = r0
        L_0x002e:
            r0 = r2
        L_0x002f:
            com.amap.location.common.f.g.a(r1)
            com.amap.location.common.f.g.a(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.common.f.f.a(byte[]):byte[]");
    }

    public static byte[] b(byte[] bArr) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream inputStream;
        InputStream inputStream2;
        InputStream gZIPInputStream;
        InputStream inputStream3;
        InputStream inputStream4 = null;
        try {
            inputStream = new ByteArrayInputStream(bArr);
            try {
                gZIPInputStream = new GZIPInputStream(inputStream);
            } catch (Exception e) {
                e = e;
                byteArrayOutputStream = null;
                inputStream4 = inputStream;
                inputStream2 = null;
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    InputStream inputStream5 = inputStream2;
                    inputStream = inputStream4;
                    inputStream4 = inputStream5;
                }
            } catch (Throwable th2) {
                th = th2;
                byteArrayOutputStream = null;
                g.a((Closeable) inputStream);
                g.a((Closeable) inputStream4);
                g.a((Closeable) byteArrayOutputStream);
                throw th;
            }
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (Exception e2) {
                inputStream3 = inputStream;
                inputStream2 = gZIPInputStream;
                e = e2;
                byteArrayOutputStream = null;
                inputStream4 = inputStream3;
                throw e;
            } catch (Throwable th3) {
                inputStream4 = gZIPInputStream;
                th = th3;
                byteArrayOutputStream = null;
                g.a((Closeable) inputStream);
                g.a((Closeable) inputStream4);
                g.a((Closeable) byteArrayOutputStream);
                throw th;
            }
            try {
                g.a(gZIPInputStream, byteArrayOutputStream);
                byteArrayOutputStream.flush();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                g.a((Closeable) inputStream);
                g.a((Closeable) gZIPInputStream);
                g.a((Closeable) byteArrayOutputStream);
                return byteArray;
            } catch (Exception e3) {
                inputStream3 = inputStream;
                inputStream2 = gZIPInputStream;
                e = e3;
                inputStream4 = inputStream3;
                throw e;
            } catch (Throwable th4) {
                Throwable th5 = th4;
                inputStream4 = gZIPInputStream;
                th = th5;
                g.a((Closeable) inputStream);
                g.a((Closeable) inputStream4);
                g.a((Closeable) byteArrayOutputStream);
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            inputStream2 = null;
            byteArrayOutputStream = null;
            throw e;
        } catch (Throwable th6) {
            th = th6;
            inputStream = null;
            byteArrayOutputStream = null;
            g.a((Closeable) inputStream);
            g.a((Closeable) inputStream4);
            g.a((Closeable) byteArrayOutputStream);
            throw th;
        }
    }
}
