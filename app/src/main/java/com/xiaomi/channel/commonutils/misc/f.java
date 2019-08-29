package com.xiaomi.channel.commonutils.misc;

import android.content.Context;
import android.os.Environment;
import com.xiaomi.channel.commonutils.file.a;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

public class f {
    private static final String a;
    private static final String b;
    private static final String c;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getPath());
        sb.append("/mipush/");
        a = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(a);
        sb2.append("lcfp");
        b = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(a);
        sb3.append("lcfp.lock");
        c = sb3.toString();
    }

    public static boolean a(Context context, String str, long j) {
        RandomAccessFile randomAccessFile;
        FileLock fileLock = null;
        try {
            File file = new File(c);
            a.a(file);
            randomAccessFile = new RandomAccessFile(file, "rw");
            try {
                FileLock lock = randomAccessFile.getChannel().lock();
                try {
                    boolean b2 = b(context, str, j);
                    if (lock != null && lock.isValid()) {
                        try {
                            lock.release();
                        } catch (IOException unused) {
                        }
                    }
                    a.a(randomAccessFile);
                    return b2;
                } catch (IOException e) {
                    e = e;
                    fileLock = lock;
                    try {
                        e.printStackTrace();
                        try {
                            fileLock.release();
                        } catch (IOException unused2) {
                        }
                        a.a(randomAccessFile);
                        return true;
                    } catch (Throwable th) {
                        th = th;
                        if (fileLock != null && fileLock.isValid()) {
                            try {
                                fileLock.release();
                            } catch (IOException unused3) {
                            }
                        }
                        a.a(randomAccessFile);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileLock = lock;
                    fileLock.release();
                    a.a(randomAccessFile);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                if (fileLock != null && fileLock.isValid()) {
                    fileLock.release();
                }
                a.a(randomAccessFile);
                return true;
            }
        } catch (IOException e3) {
            e = e3;
            randomAccessFile = null;
            e.printStackTrace();
            fileLock.release();
            a.a(randomAccessFile);
            return true;
        } catch (Throwable th3) {
            th = th3;
            randomAccessFile = null;
            fileLock.release();
            a.a(randomAccessFile);
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x00e7 A[Catch:{ IOException -> 0x00f7 }, LOOP:1: B:52:0x00e1->B:54:0x00e7, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean b(android.content.Context r17, java.lang.String r18, long r19) {
        /*
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 1
            r3 = 23
            if (r1 < r3) goto L_0x0012
            java.lang.String r1 = "android.permission.WRITE_EXTERNAL_STORAGE"
            r3 = r17
            boolean r1 = com.xiaomi.channel.commonutils.android.a.g(r3, r1)
            if (r1 != 0) goto L_0x0014
            return r2
        L_0x0012:
            r3 = r17
        L_0x0014:
            java.io.File r1 = new java.io.File
            java.lang.String r4 = b
            r1.<init>(r4)
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            long r5 = java.lang.System.currentTimeMillis()
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r8 = r18
            r7.append(r8)
            java.lang.String r9 = ":"
            r7.append(r9)
            java.lang.String r9 = r17.getPackageName()
            r7.append(r9)
            java.lang.String r9 = ","
            r7.append(r9)
            r7.append(r5)
            java.lang.String r7 = r7.toString()
            boolean r9 = r1.exists()
            if (r9 == 0) goto L_0x00c9
            java.io.BufferedReader r9 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00ba, all -> 0x00b6 }
            java.io.FileReader r11 = new java.io.FileReader     // Catch:{ Exception -> 0x00ba, all -> 0x00b6 }
            r11.<init>(r1)     // Catch:{ Exception -> 0x00ba, all -> 0x00b6 }
            r9.<init>(r11)     // Catch:{ Exception -> 0x00ba, all -> 0x00b6 }
        L_0x0056:
            java.lang.String r11 = r9.readLine()     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            if (r11 == 0) goto L_0x00ad
            java.lang.String r12 = ":"
            java.lang.String[] r12 = r11.split(r12)     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            int r13 = r12.length     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            r14 = 2
            if (r13 != r14) goto L_0x0056
            r13 = 0
            r15 = r12[r13]     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            java.lang.String r10 = java.lang.String.valueOf(r18)     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            boolean r10 = android.text.TextUtils.equals(r15, r10)     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            if (r10 == 0) goto L_0x00a9
            r10 = r12[r2]     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            java.lang.String r11 = ","
            java.lang.String[] r10 = r10.split(r11)     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            int r11 = r10.length     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            if (r11 != r14) goto L_0x0056
            r11 = r10[r2]     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            long r11 = java.lang.Long.parseLong(r11)     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            r10 = r10[r13]     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            java.lang.String r14 = r17.getPackageName()     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            boolean r10 = android.text.TextUtils.equals(r10, r14)     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            if (r10 != 0) goto L_0x0056
            long r10 = r5 - r11
            long r10 = java.lang.Math.abs(r10)     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            float r10 = (float) r10
            r11 = 1000(0x3e8, double:4.94E-321)
            long r11 = r11 * r19
            float r11 = (float) r11
            r12 = 1063675494(0x3f666666, float:0.9)
            float r11 = r11 * r12
            int r10 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
            if (r10 >= 0) goto L_0x0056
            com.xiaomi.channel.commonutils.file.a.a(r9)
            return r13
        L_0x00a9:
            r4.add(r11)     // Catch:{ Exception -> 0x00b4, all -> 0x00b1 }
            goto L_0x0056
        L_0x00ad:
            com.xiaomi.channel.commonutils.file.a.a(r9)
            goto L_0x00d0
        L_0x00b1:
            r0 = move-exception
            r1 = r0
            goto L_0x00c5
        L_0x00b4:
            r10 = r9
            goto L_0x00bb
        L_0x00b6:
            r0 = move-exception
            r1 = r0
            r9 = 0
            goto L_0x00c5
        L_0x00ba:
            r10 = 0
        L_0x00bb:
            r4.clear()     // Catch:{ all -> 0x00c2 }
            com.xiaomi.channel.commonutils.file.a.a(r10)
            goto L_0x00d0
        L_0x00c2:
            r0 = move-exception
            r1 = r0
            r9 = r10
        L_0x00c5:
            com.xiaomi.channel.commonutils.file.a.a(r9)
            throw r1
        L_0x00c9:
            boolean r3 = com.xiaomi.channel.commonutils.file.a.a(r1)
            if (r3 != 0) goto L_0x00d0
            return r2
        L_0x00d0:
            r4.add(r7)
            java.io.BufferedWriter r10 = new java.io.BufferedWriter     // Catch:{ IOException -> 0x00fe, all -> 0x00fa }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ IOException -> 0x00fe, all -> 0x00fa }
            r3.<init>(r1)     // Catch:{ IOException -> 0x00fe, all -> 0x00fa }
            r10.<init>(r3)     // Catch:{ IOException -> 0x00fe, all -> 0x00fa }
            java.util.Iterator r1 = r4.iterator()     // Catch:{ IOException -> 0x00f7 }
        L_0x00e1:
            boolean r3 = r1.hasNext()     // Catch:{ IOException -> 0x00f7 }
            if (r3 == 0) goto L_0x0108
            java.lang.Object r3 = r1.next()     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ IOException -> 0x00f7 }
            r10.write(r3)     // Catch:{ IOException -> 0x00f7 }
            r10.newLine()     // Catch:{ IOException -> 0x00f7 }
            r10.flush()     // Catch:{ IOException -> 0x00f7 }
            goto L_0x00e1
        L_0x00f7:
            r0 = move-exception
            r1 = r0
            goto L_0x0101
        L_0x00fa:
            r0 = move-exception
            r1 = r0
            r10 = 0
            goto L_0x010e
        L_0x00fe:
            r0 = move-exception
            r1 = r0
            r10 = 0
        L_0x0101:
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x010c }
            com.xiaomi.channel.commonutils.logger.b.d(r1)     // Catch:{ all -> 0x010c }
        L_0x0108:
            com.xiaomi.channel.commonutils.file.a.a(r10)
            return r2
        L_0x010c:
            r0 = move-exception
            r1 = r0
        L_0x010e:
            com.xiaomi.channel.commonutils.file.a.a(r10)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.channel.commonutils.misc.f.b(android.content.Context, java.lang.String, long):boolean");
    }
}
