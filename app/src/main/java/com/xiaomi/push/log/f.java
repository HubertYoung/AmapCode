package com.xiaomi.push.log;

import android.content.Context;
import android.util.Pair;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.channel.commonutils.misc.k;
import com.xiaomi.channel.commonutils.misc.k.b;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class f implements LoggerInterface {
    private static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa");
    private static k b = new k(true);
    private static String c = "/MiPushLog";
    /* access modifiers changed from: private */
    public static List<Pair<String, Throwable>> g = Collections.synchronizedList(new ArrayList());
    /* access modifiers changed from: private */
    public String d;
    private Context e;
    private String f = "";

    public f(Context context) {
        this.e = context;
        if (context.getApplicationContext() != null) {
            this.e = context.getApplicationContext();
        }
        this.d = this.e.getPackageName();
        File externalFilesDir = this.e.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(externalFilesDir.getAbsolutePath());
            this.f = sb.toString();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0111 A[SYNTHETIC, Splitter:B:59:0x0111] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0121 A[SYNTHETIC, Splitter:B:69:0x0121] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0129 A[SYNTHETIC, Splitter:B:77:0x0129] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0139 A[SYNTHETIC, Splitter:B:87:0x0139] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b() {
        /*
            r9 = this;
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            r2.<init>()     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            java.lang.String r3 = r9.f     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            r2.append(r3)     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            java.lang.String r3 = c     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            r2.append(r3)     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            boolean r2 = r1.exists()     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            if (r2 == 0) goto L_0x0025
            boolean r2 = r1.isDirectory()     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            if (r2 != 0) goto L_0x002c
        L_0x0025:
            boolean r2 = r1.mkdirs()     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            if (r2 != 0) goto L_0x002c
            return
        L_0x002c:
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            java.lang.String r3 = "log.lock"
            r2.<init>(r1, r3)     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            if (r3 == 0) goto L_0x003f
            boolean r3 = r2.isDirectory()     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            if (r3 == 0) goto L_0x0042
        L_0x003f:
            r2.createNewFile()     // Catch:{ Exception -> 0x0125, all -> 0x010c }
        L_0x0042:
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            java.lang.String r4 = "rw"
            r3.<init>(r2, r4)     // Catch:{ Exception -> 0x0125, all -> 0x010c }
            java.nio.channels.FileChannel r2 = r3.getChannel()     // Catch:{ Exception -> 0x010a, all -> 0x0107 }
            java.nio.channels.FileLock r2 = r2.lock()     // Catch:{ Exception -> 0x010a, all -> 0x0107 }
            java.io.BufferedWriter r4 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            java.io.OutputStreamWriter r5 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            java.io.File r7 = new java.io.File     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            java.lang.String r8 = "log1.txt"
            r7.<init>(r1, r8)     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            r8 = 1
            r6.<init>(r7, r8)     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
        L_0x0068:
            java.util.List<android.util.Pair<java.lang.String, java.lang.Throwable>> r5 = g     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            boolean r5 = r5.isEmpty()     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            if (r5 != 0) goto L_0x00be
            java.util.List<android.util.Pair<java.lang.String, java.lang.Throwable>> r5 = g     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            r6 = 0
            java.lang.Object r5 = r5.remove(r6)     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            android.util.Pair r5 = (android.util.Pair) r5     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            java.lang.Object r6 = r5.first     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            java.lang.Object r7 = r5.second     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            if (r7 == 0) goto L_0x00a9
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            r7.<init>()     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            r7.append(r6)     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            java.lang.String r6 = "\n"
            r7.append(r6)     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            java.lang.String r6 = r7.toString()     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            r7.<init>()     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            r7.append(r6)     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            java.lang.Object r5 = r5.second     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            java.lang.Throwable r5 = (java.lang.Throwable) r5     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            java.lang.String r5 = android.util.Log.getStackTraceString(r5)     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            r7.append(r5)     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            java.lang.String r6 = r7.toString()     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
        L_0x00a9:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            r5.<init>()     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            r5.append(r6)     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            java.lang.String r6 = "\n"
            r5.append(r6)     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            r4.write(r5)     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            goto L_0x0068
        L_0x00be:
            r4.flush()     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            r4.close()     // Catch:{ Exception -> 0x0103, all -> 0x0100 }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            java.lang.String r5 = "log1.txt"
            r4.<init>(r1, r5)     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            long r5 = r4.length()     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            r7 = 1048576(0x100000, double:5.180654E-318)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 < 0) goto L_0x00ef
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            java.lang.String r6 = "log0.txt"
            r5.<init>(r1, r6)     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            boolean r1 = r5.exists()     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            if (r1 == 0) goto L_0x00ec
            boolean r1 = r5.isFile()     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
            if (r1 == 0) goto L_0x00ec
            r5.delete()     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
        L_0x00ec:
            r4.renameTo(r5)     // Catch:{ Exception -> 0x0127, all -> 0x0105 }
        L_0x00ef:
            if (r2 == 0) goto L_0x00fa
            boolean r0 = r2.isValid()
            if (r0 == 0) goto L_0x00fa
            r2.release()     // Catch:{ IOException -> 0x00fa }
        L_0x00fa:
            if (r3 == 0) goto L_0x00ff
            r3.close()     // Catch:{ IOException -> 0x00ff }
        L_0x00ff:
            return
        L_0x0100:
            r1 = move-exception
            r0 = r4
            goto L_0x010f
        L_0x0103:
            r0 = r4
            goto L_0x0127
        L_0x0105:
            r1 = move-exception
            goto L_0x010f
        L_0x0107:
            r1 = move-exception
            r2 = r0
            goto L_0x010f
        L_0x010a:
            r2 = r0
            goto L_0x0127
        L_0x010c:
            r1 = move-exception
            r2 = r0
            r3 = r2
        L_0x010f:
            if (r0 == 0) goto L_0x0114
            r0.close()     // Catch:{ IOException -> 0x0114 }
        L_0x0114:
            if (r2 == 0) goto L_0x011f
            boolean r0 = r2.isValid()
            if (r0 == 0) goto L_0x011f
            r2.release()     // Catch:{ IOException -> 0x011f }
        L_0x011f:
            if (r3 == 0) goto L_0x0124
            r3.close()     // Catch:{ IOException -> 0x0124 }
        L_0x0124:
            throw r1
        L_0x0125:
            r2 = r0
            r3 = r2
        L_0x0127:
            if (r0 == 0) goto L_0x012c
            r0.close()     // Catch:{ IOException -> 0x012c }
        L_0x012c:
            if (r2 == 0) goto L_0x0137
            boolean r0 = r2.isValid()
            if (r0 == 0) goto L_0x0137
            r2.release()     // Catch:{ IOException -> 0x0137 }
        L_0x0137:
            if (r3 == 0) goto L_0x013c
            r3.close()     // Catch:{ IOException -> 0x013c }
        L_0x013c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.log.f.b():void");
    }

    public final void log(String str) {
        log(str, null);
    }

    public final void log(String str, Throwable th) {
        g.add(new Pair(String.format("%1$s %2$s %3$s ", new Object[]{a.format(new Date()), this.d, str}), th));
        b.a((b) new g(this));
    }

    public final void setTag(String str) {
        this.d = str;
    }
}
