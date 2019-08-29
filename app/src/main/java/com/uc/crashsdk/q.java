package com.uc.crashsdk;

import com.uc.crashsdk.a.a;
import com.uc.crashsdk.a.b;
import com.uc.crashsdk.a.c;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;

/* compiled from: ProGuard */
public final class q {
    public static int a = 5000;

    private static byte[] a(File file) {
        FileInputStream fileInputStream;
        BufferedInputStream bufferedInputStream;
        Throwable th;
        byte[] bArr = null;
        if (file.isFile()) {
            try {
                int length = (int) file.length();
                fileInputStream = new FileInputStream(file);
                try {
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    try {
                        bArr = new byte[length];
                        int i = 0;
                        while (i < length) {
                            int read = bufferedInputStream.read(bArr, i, length - i);
                            if (-1 == read) {
                                break;
                            }
                            i += read;
                        }
                        b.a((Closeable) bufferedInputStream);
                    } catch (Exception e) {
                        e = e;
                        try {
                            a.a(e, true);
                            b.a((Closeable) bufferedInputStream);
                            b.a((Closeable) fileInputStream);
                            return bArr;
                        } catch (Throwable th2) {
                            th = th2;
                            b.a((Closeable) bufferedInputStream);
                            b.a((Closeable) fileInputStream);
                            throw th;
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    bufferedInputStream = null;
                    a.a(e, true);
                    b.a((Closeable) bufferedInputStream);
                    b.a((Closeable) fileInputStream);
                    return bArr;
                } catch (Throwable th3) {
                    bufferedInputStream = null;
                    th = th3;
                    b.a((Closeable) bufferedInputStream);
                    b.a((Closeable) fileInputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                bufferedInputStream = null;
                fileInputStream = null;
                a.a(e, true);
                b.a((Closeable) bufferedInputStream);
                b.a((Closeable) fileInputStream);
                return bArr;
            } catch (Throwable th4) {
                bufferedInputStream = null;
                fileInputStream = null;
                th = th4;
                b.a((Closeable) bufferedInputStream);
                b.a((Closeable) fileInputStream);
                throw th;
            }
            b.a((Closeable) fileInputStream);
        }
        return bArr;
    }

    public static boolean a(File file, String str, String str2) {
        for (int i = 0; i < 2; i++) {
            if (b(file, str, str2)) {
                return true;
            }
            c.b("upload try again: " + str);
        }
        return false;
    }

    private static boolean b(File file, String str, String str2) {
        try {
            byte[] a2 = a(file);
            if (a2 == null || a2.length == 0) {
                return false;
            }
            return a(a2, str, str2);
        } catch (Exception e) {
            a.a(e, false);
            return false;
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(byte[] r8, java.lang.String r9, java.lang.String r10) {
        /*
            r0 = 1
            r1 = 0
            if (r8 == 0) goto L_0x0007
            int r2 = r8.length     // Catch:{ Exception -> 0x0191 }
            if (r2 != 0) goto L_0x0009
        L_0x0007:
            r0 = r1
        L_0x0008:
            return r0
        L_0x0009:
            org.apache.http.impl.client.DefaultHttpClient r3 = new org.apache.http.impl.client.DefaultHttpClient     // Catch:{ Exception -> 0x0191 }
            r3.<init>()     // Catch:{ Exception -> 0x0191 }
            org.apache.http.params.HttpParams r2 = r3.getParams()     // Catch:{ Exception -> 0x0191 }
            java.lang.String r4 = "http.connection.timeout"
            r5 = 10000(0x2710, float:1.4013E-41)
            r2.setIntParameter(r4, r5)     // Catch:{ Exception -> 0x0191 }
            org.apache.http.params.HttpParams r2 = r3.getParams()     // Catch:{ Exception -> 0x0191 }
            java.lang.String r4 = "http.socket.timeout"
            r5 = 60000(0xea60, float:8.4078E-41)
            r2.setIntParameter(r4, r5)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r2 = com.uc.crashsdk.a.e.a()     // Catch:{ Exception -> 0x0191 }
            if (r2 == 0) goto L_0x003d
            org.apache.http.params.HttpParams r4 = r3.getParams()     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = "http.route.default-proxy"
            org.apache.http.HttpHost r6 = new org.apache.http.HttpHost     // Catch:{ Exception -> 0x0191 }
            int r7 = com.uc.crashsdk.a.e.b()     // Catch:{ Exception -> 0x0191 }
            r6.<init>(r2, r7)     // Catch:{ Exception -> 0x0191 }
            r4.setParameter(r5, r6)     // Catch:{ Exception -> 0x0191 }
        L_0x003d:
            if (r10 == 0) goto L_0x019f
            int r2 = r10.length()     // Catch:{ Exception -> 0x0191 }
            if (r2 <= 0) goto L_0x019f
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0191 }
            java.lang.String r4 = "uploadCrashLog get url ["
            r2.<init>(r4)     // Catch:{ Exception -> 0x0191 }
            java.lang.StringBuilder r2 = r2.append(r10)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r4 = "] ..."
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0191 }
            com.uc.crashsdk.a.c.b(r2)     // Catch:{ Exception -> 0x0191 }
            org.apache.http.client.methods.HttpPost r4 = new org.apache.http.client.methods.HttpPost     // Catch:{ Exception -> 0x0191 }
            r4.<init>(r10)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r2 = "http://"
            boolean r2 = r10.startsWith(r2)     // Catch:{ Exception -> 0x0191 }
            if (r2 == 0) goto L_0x0167
            r2 = 7
            java.lang.String r10 = r10.substring(r2)     // Catch:{ Exception -> 0x0191 }
        L_0x006f:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = "uploadCrashLog after substr url ["
            r2.<init>(r5)     // Catch:{ Exception -> 0x0191 }
            java.lang.StringBuilder r2 = r2.append(r10)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = "] ..."
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0191 }
            com.uc.crashsdk.a.c.b(r2)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r2 = ":"
            boolean r2 = r10.contains(r2)     // Catch:{ Exception -> 0x0191 }
            if (r2 == 0) goto L_0x0177
            r2 = 0
            java.lang.String r5 = ":"
            int r5 = r10.indexOf(r5)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = r10.substring(r2, r5)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r2 = "/"
            int r2 = r10.indexOf(r2)     // Catch:{ Exception -> 0x0191 }
            if (r2 >= 0) goto L_0x00a6
            int r2 = r10.length()     // Catch:{ Exception -> 0x0191 }
        L_0x00a6:
            java.lang.String r6 = ":"
            int r6 = r10.indexOf(r6)     // Catch:{ Exception -> 0x0191 }
            int r6 = r6 + 1
            java.lang.String r6 = r10.substring(r6, r2)     // Catch:{ Exception -> 0x0191 }
            org.apache.http.HttpHost r2 = new org.apache.http.HttpHost     // Catch:{ Exception -> 0x0191 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x0191 }
            int r6 = r6.intValue()     // Catch:{ Exception -> 0x0191 }
            r2.<init>(r5, r6)     // Catch:{ Exception -> 0x0191 }
        L_0x00bf:
            java.lang.String r5 = "Content-Type"
            java.lang.String r6 = "multipart/form-data; boundary=----------izQ290kHh6g3Yn2IeyJCoc"
            r4.setHeader(r5, r6)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = "Content-Disposition"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0191 }
            java.lang.String r7 = "form-data; name=\"file\"; filename="
            r6.<init>(r7)     // Catch:{ Exception -> 0x0191 }
            java.lang.StringBuilder r6 = r6.append(r9)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0191 }
            r4.setHeader(r5, r6)     // Catch:{ Exception -> 0x0191 }
            java.lang.StringBuffer r5 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0191 }
            r5.<init>()     // Catch:{ Exception -> 0x0191 }
            java.lang.String r6 = "------------izQ290kHh6g3Yn2IeyJCoc\r\n"
            r5.append(r6)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r6 = "Content-Disposition: form-data; name=\"file\";"
            r5.append(r6)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r6 = " filename=\""
            java.lang.StringBuffer r6 = r5.append(r6)     // Catch:{ Exception -> 0x0191 }
            java.lang.StringBuffer r6 = r6.append(r9)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r7 = "\"\r\n"
            r6.append(r7)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r6 = "Content-Type: application/octet-stream\r\n"
            r5.append(r6)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r6 = "\r\n"
            r5.append(r6)     // Catch:{ Exception -> 0x0191 }
            java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0191 }
            r6.<init>()     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0191 }
            byte[] r5 = r5.getBytes()     // Catch:{ Exception -> 0x0191 }
            r6.write(r5)     // Catch:{ Exception -> 0x0191 }
            r6.write(r8)     // Catch:{ Exception -> 0x0191 }
            java.lang.StringBuffer r5 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0191 }
            r5.<init>()     // Catch:{ Exception -> 0x0191 }
            java.lang.String r7 = "\r\n------------izQ290kHh6g3Yn2IeyJCoc--\r\n"
            r5.append(r7)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0191 }
            byte[] r5 = r5.getBytes()     // Catch:{ Exception -> 0x0191 }
            r6.write(r5)     // Catch:{ Exception -> 0x0191 }
            r6.close()     // Catch:{ Exception -> 0x0191 }
            org.apache.http.entity.ByteArrayEntity r5 = new org.apache.http.entity.ByteArrayEntity     // Catch:{ Exception -> 0x0191 }
            byte[] r6 = r6.toByteArray()     // Catch:{ Exception -> 0x0191 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0191 }
            r4.setEntity(r5)     // Catch:{ Exception -> 0x0191 }
            org.apache.http.HttpResponse r2 = r3.execute(r2, r4)     // Catch:{ Throwable -> 0x01a2 }
            org.apache.http.StatusLine r2 = r2.getStatusLine()     // Catch:{ Throwable -> 0x01a2 }
            int r2 = r2.getStatusCode()     // Catch:{ Throwable -> 0x01a2 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r5 = "StatusLine: "
            r4.<init>(r5)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.StringBuilder r4 = r4.append(r2)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r5 = "crashsdk"
            com.uc.crashsdk.a.c.a(r5, r4)     // Catch:{ Throwable -> 0x01a2 }
            org.apache.http.conn.ClientConnectionManager r3 = r3.getConnectionManager()     // Catch:{ Exception -> 0x0191 }
            r3.shutdown()     // Catch:{ Exception -> 0x0191 }
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 == r3) goto L_0x0008
            r0 = r1
            goto L_0x0008
        L_0x0167:
            java.lang.String r2 = "https://"
            boolean r2 = r10.startsWith(r2)     // Catch:{ Exception -> 0x0191 }
            if (r2 == 0) goto L_0x006f
            r2 = 8
            java.lang.String r10 = r10.substring(r2)     // Catch:{ Exception -> 0x0191 }
            goto L_0x006f
        L_0x0177:
            java.lang.String r2 = "/"
            boolean r2 = r10.contains(r2)     // Catch:{ Exception -> 0x0191 }
            if (r2 == 0) goto L_0x0198
            r2 = 0
            java.lang.String r5 = "/"
            int r5 = r10.indexOf(r5)     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = r10.substring(r2, r5)     // Catch:{ Exception -> 0x0191 }
            org.apache.http.HttpHost r2 = new org.apache.http.HttpHost     // Catch:{ Exception -> 0x0191 }
            r2.<init>(r5)     // Catch:{ Exception -> 0x0191 }
            goto L_0x00bf
        L_0x0191:
            r2 = move-exception
            com.uc.crashsdk.a.a.a(r2, r0)
            r0 = r1
            goto L_0x0008
        L_0x0198:
            org.apache.http.HttpHost r2 = new org.apache.http.HttpHost     // Catch:{ Exception -> 0x0191 }
            r2.<init>(r10)     // Catch:{ Exception -> 0x0191 }
            goto L_0x00bf
        L_0x019f:
            r0 = r1
            goto L_0x0008
        L_0x01a2:
            r2 = move-exception
            r4 = 1
            com.uc.crashsdk.a.a.a(r2, r4)     // Catch:{ all -> 0x01b1 }
            org.apache.http.conn.ClientConnectionManager r2 = r3.getConnectionManager()     // Catch:{ Exception -> 0x0191 }
            r2.shutdown()     // Catch:{ Exception -> 0x0191 }
            r0 = r1
            goto L_0x0008
        L_0x01b1:
            r2 = move-exception
            org.apache.http.conn.ClientConnectionManager r3 = r3.getConnectionManager()     // Catch:{ Exception -> 0x0191 }
            r3.shutdown()     // Catch:{ Exception -> 0x0191 }
            throw r2     // Catch:{ Exception -> 0x0191 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.q.a(byte[], java.lang.String, java.lang.String):boolean");
    }
}
