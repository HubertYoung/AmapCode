package com.xiaomi.tinyData;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.xiaomi.channel.commonutils.misc.h;
import java.io.File;

public class b {
    private static boolean a = false;

    static class a implements Runnable {
        private Context a;
        private e b;

        public a(Context context, e eVar) {
            this.b = eVar;
            this.a = context;
        }

        public void run() {
            b.c(this.a, this.b);
        }
    }

    private static void a(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir());
        sb.append("/tdReadTemp");
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void a(Context context, e eVar) {
        h.a(context).a((Runnable) new a(context, eVar));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0085, code lost:
        r14 = "TinyData read from cache file failed cause lengthBuffer < 1 || too big. length:".concat(java.lang.String.valueOf(r7));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(android.content.Context r11, com.xiaomi.tinyData.e r12, java.io.File r13, byte[] r14) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 4
            byte[] r2 = new byte[r1]
            r3 = 0
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x00b2 }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00b2 }
            r5.<init>(r13)     // Catch:{ Exception -> 0x00b2 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x00b2 }
            r3 = 0
        L_0x0014:
            r5 = 0
            r6 = 0
        L_0x0016:
            int r7 = r4.read(r2)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            r8 = -1
            if (r7 == r8) goto L_0x0090
            if (r7 == r1) goto L_0x002d
            java.lang.String r14 = "TinyData read from cache file failed cause lengthBuffer error. size:"
            java.lang.String r1 = java.lang.String.valueOf(r7)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            java.lang.String r14 = r14.concat(r1)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
        L_0x0029:
            com.xiaomi.channel.commonutils.logger.b.d(r14)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            goto L_0x0090
        L_0x002d:
            int r7 = com.xiaomi.channel.commonutils.misc.b.a(r2)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            if (r7 <= 0) goto L_0x0085
            r8 = 10240(0x2800, float:1.4349E-41)
            if (r7 <= r8) goto L_0x0038
            goto L_0x0085
        L_0x0038:
            byte[] r9 = new byte[r7]     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            int r10 = r4.read(r9)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            if (r10 == r7) goto L_0x0057
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            java.lang.String r1 = "TinyData read from cache file failed cause buffer size not equal length. size:"
            r14.<init>(r1)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            r14.append(r10)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            java.lang.String r1 = "__length:"
            r14.append(r1)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            r14.append(r7)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            goto L_0x0029
        L_0x0057:
            byte[] r7 = com.xiaomi.channel.commonutils.android.c.a(r14, r9)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            if (r7 == 0) goto L_0x007f
            int r9 = r7.length     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            if (r9 != 0) goto L_0x0061
            goto L_0x007f
        L_0x0061:
            com.xiaomi.xmpush.thrift.f r9 = new com.xiaomi.xmpush.thrift.f     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            r9.<init>()     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            com.xiaomi.xmpush.thrift.au.a(r9, r7)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            r0.add(r9)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            int r5 = r5 + 1
            int r7 = r7.length     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            int r6 = r6 + r7
            r7 = 8
            if (r5 >= r7) goto L_0x0076
            if (r6 < r8) goto L_0x0016
        L_0x0076:
            com.xiaomi.tinyData.c.a(r11, r12, r0)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            r0.<init>()     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            goto L_0x0014
        L_0x007f:
            java.lang.String r7 = "TinyData read from cache file failed cause decrypt fail"
            com.xiaomi.channel.commonutils.logger.b.d(r7)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            goto L_0x0016
        L_0x0085:
            java.lang.String r14 = "TinyData read from cache file failed cause lengthBuffer < 1 || too big. length:"
            java.lang.String r1 = java.lang.String.valueOf(r7)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            java.lang.String r14 = r14.concat(r1)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            goto L_0x0029
        L_0x0090:
            com.xiaomi.tinyData.c.a(r11, r12, r0)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            if (r13 == 0) goto L_0x00a6
            boolean r11 = r13.exists()     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            if (r11 == 0) goto L_0x00a6
            boolean r11 = r13.delete()     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
            if (r11 != 0) goto L_0x00a6
            java.lang.String r11 = "TinyData delete reading temp file failed"
            com.xiaomi.channel.commonutils.logger.b.a(r11)     // Catch:{ Exception -> 0x00ac, all -> 0x00aa }
        L_0x00a6:
            com.xiaomi.channel.commonutils.file.a.a(r4)
            return
        L_0x00aa:
            r11 = move-exception
            goto L_0x00ba
        L_0x00ac:
            r11 = move-exception
            r3 = r4
            goto L_0x00b3
        L_0x00af:
            r11 = move-exception
            r4 = r3
            goto L_0x00ba
        L_0x00b2:
            r11 = move-exception
        L_0x00b3:
            com.xiaomi.channel.commonutils.logger.b.a(r11)     // Catch:{ all -> 0x00af }
            com.xiaomi.channel.commonutils.file.a.a(r3)
            return
        L_0x00ba:
            com.xiaomi.channel.commonutils.file.a.a(r4)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.tinyData.b.a(android.content.Context, com.xiaomi.tinyData.e, java.io.File, byte[]):void");
    }

    private static void b(Context context) {
        Editor edit = context.getSharedPreferences("mipush_extra", 4).edit();
        edit.putLong("last_tiny_data_upload_timestamp", System.currentTimeMillis() / 1000);
        edit.commit();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void c(android.content.Context r7, com.xiaomi.tinyData.e r8) {
        /*
            boolean r0 = a
            if (r0 != 0) goto L_0x00d9
            r0 = 1
            a = r0
            java.io.File r0 = new java.io.File
            java.io.File r1 = r7.getFilesDir()
            java.lang.String r2 = "tiny_data.data"
            r0.<init>(r1, r2)
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x001e
            java.lang.String r7 = "TinyData no ready file to get data."
            com.xiaomi.channel.commonutils.logger.b.a(r7)
            return
        L_0x001e:
            a(r7)
            byte[] r1 = com.xiaomi.push.service.bg.a(r7)
            r2 = 0
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            java.io.File r4 = r7.getFilesDir()     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            java.lang.String r5 = "tiny_data.lock"
            r3.<init>(r4, r5)     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            com.xiaomi.channel.commonutils.file.a.a(r3)     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            java.io.RandomAccessFile r4 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            java.lang.String r5 = "rw"
            r4.<init>(r3, r5)     // Catch:{ Exception -> 0x0079, all -> 0x0076 }
            java.nio.channels.FileChannel r3 = r4.getChannel()     // Catch:{ Exception -> 0x0074 }
            java.nio.channels.FileLock r3 = r3.lock()     // Catch:{ Exception -> 0x0074 }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0071, all -> 0x006e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0071, all -> 0x006e }
            r5.<init>()     // Catch:{ Exception -> 0x0071, all -> 0x006e }
            java.io.File r6 = r7.getFilesDir()     // Catch:{ Exception -> 0x0071, all -> 0x006e }
            r5.append(r6)     // Catch:{ Exception -> 0x0071, all -> 0x006e }
            java.lang.String r6 = "/tdReadTemp/tiny_data.data"
            r5.append(r6)     // Catch:{ Exception -> 0x0071, all -> 0x006e }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0071, all -> 0x006e }
            r2.<init>(r5)     // Catch:{ Exception -> 0x0071, all -> 0x006e }
            r0.renameTo(r2)     // Catch:{ Exception -> 0x0071, all -> 0x006e }
            if (r3 == 0) goto L_0x008e
            boolean r0 = r3.isValid()
            if (r0 == 0) goto L_0x008e
            r3.release()     // Catch:{ IOException -> 0x006c }
            goto L_0x008e
        L_0x006c:
            r0 = move-exception
            goto L_0x008b
        L_0x006e:
            r7 = move-exception
            r2 = r3
            goto L_0x00c5
        L_0x0071:
            r0 = move-exception
            r2 = r3
            goto L_0x007b
        L_0x0074:
            r0 = move-exception
            goto L_0x007b
        L_0x0076:
            r7 = move-exception
            r4 = r2
            goto L_0x00c5
        L_0x0079:
            r0 = move-exception
            r4 = r2
        L_0x007b:
            com.xiaomi.channel.commonutils.logger.b.a(r0)     // Catch:{ all -> 0x00c4 }
            if (r2 == 0) goto L_0x008e
            boolean r0 = r2.isValid()
            if (r0 == 0) goto L_0x008e
            r2.release()     // Catch:{ IOException -> 0x008a }
            goto L_0x008e
        L_0x008a:
            r0 = move-exception
        L_0x008b:
            com.xiaomi.channel.commonutils.logger.b.a(r0)
        L_0x008e:
            com.xiaomi.channel.commonutils.file.a.a(r4)
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.io.File r3 = r7.getFilesDir()
            r2.append(r3)
            java.lang.String r3 = "/tdReadTemp/tiny_data.data"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            boolean r2 = r0.exists()
            if (r2 != 0) goto L_0x00b7
            java.lang.String r7 = "TinyData no ready file to get data."
            com.xiaomi.channel.commonutils.logger.b.a(r7)
            return
        L_0x00b7:
            a(r7, r8, r0, r1)
            r8 = 0
            com.xiaomi.tinyData.a.a(r8)
            b(r7)
            a = r8
            return
        L_0x00c4:
            r7 = move-exception
        L_0x00c5:
            if (r2 == 0) goto L_0x00d5
            boolean r8 = r2.isValid()
            if (r8 == 0) goto L_0x00d5
            r2.release()     // Catch:{ IOException -> 0x00d1 }
            goto L_0x00d5
        L_0x00d1:
            r8 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r8)
        L_0x00d5:
            com.xiaomi.channel.commonutils.file.a.a(r4)
            throw r7
        L_0x00d9:
            java.lang.String r7 = "TinyData extractTinyData is running"
            com.xiaomi.channel.commonutils.logger.b.a(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.tinyData.b.c(android.content.Context, com.xiaomi.tinyData.e):void");
    }
}
