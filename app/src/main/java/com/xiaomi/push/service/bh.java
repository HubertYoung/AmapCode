package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.xmpush.thrift.f;

final class bh implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ f b;

    bh(Context context, f fVar) {
        this.a = context;
        this.b = fVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0054 A[SYNTHETIC, Splitter:B:31:0x0054] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r6 = this;
            java.lang.Object r0 = com.xiaomi.push.service.bg.a
            monitor-enter(r0)
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            android.content.Context r3 = r6.a     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            java.io.File r3 = r3.getFilesDir()     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            java.lang.String r4 = "tiny_data.lock"
            r2.<init>(r3, r4)     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            com.xiaomi.channel.commonutils.file.a.a(r2)     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            java.lang.String r4 = "rw"
            r3.<init>(r2, r4)     // Catch:{ Exception -> 0x004d, all -> 0x004a }
            java.nio.channels.FileChannel r2 = r3.getChannel()     // Catch:{ Exception -> 0x0048 }
            java.nio.channels.FileLock r2 = r2.lock()     // Catch:{ Exception -> 0x0048 }
            android.content.Context r1 = r6.a     // Catch:{ Exception -> 0x0043, all -> 0x003e }
            com.xiaomi.xmpush.thrift.f r4 = r6.b     // Catch:{ Exception -> 0x0043, all -> 0x003e }
            com.xiaomi.push.service.bg.c(r1, r4)     // Catch:{ Exception -> 0x0043, all -> 0x003e }
            if (r2 == 0) goto L_0x003a
            boolean r1 = r2.isValid()     // Catch:{ all -> 0x0077 }
            if (r1 == 0) goto L_0x003a
            r2.release()     // Catch:{ IOException -> 0x0036 }
            goto L_0x003a
        L_0x0036:
            r1 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r1)     // Catch:{ all -> 0x0077 }
        L_0x003a:
            com.xiaomi.channel.commonutils.file.a.a(r3)     // Catch:{ all -> 0x0077 }
            goto L_0x0063
        L_0x003e:
            r1 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
            goto L_0x0066
        L_0x0043:
            r1 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
            goto L_0x004f
        L_0x0048:
            r2 = move-exception
            goto L_0x004f
        L_0x004a:
            r2 = move-exception
            r3 = r1
            goto L_0x0066
        L_0x004d:
            r2 = move-exception
            r3 = r1
        L_0x004f:
            com.xiaomi.channel.commonutils.logger.b.a(r2)     // Catch:{ all -> 0x0065 }
            if (r1 == 0) goto L_0x003a
            boolean r2 = r1.isValid()     // Catch:{ all -> 0x0077 }
            if (r2 == 0) goto L_0x003a
            r1.release()     // Catch:{ IOException -> 0x005e }
            goto L_0x003a
        L_0x005e:
            r1 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r1)     // Catch:{ all -> 0x0077 }
            goto L_0x003a
        L_0x0063:
            monitor-exit(r0)     // Catch:{ all -> 0x0077 }
            return
        L_0x0065:
            r2 = move-exception
        L_0x0066:
            if (r1 == 0) goto L_0x0079
            boolean r4 = r1.isValid()     // Catch:{ all -> 0x0077 }
            if (r4 == 0) goto L_0x0079
            r1.release()     // Catch:{ IOException -> 0x0072 }
            goto L_0x0079
        L_0x0072:
            r1 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r1)     // Catch:{ all -> 0x0077 }
            goto L_0x0079
        L_0x0077:
            r1 = move-exception
            goto L_0x007d
        L_0x0079:
            com.xiaomi.channel.commonutils.file.a.a(r3)     // Catch:{ all -> 0x0077 }
            throw r2     // Catch:{ all -> 0x0077 }
        L_0x007d:
            monitor-exit(r0)     // Catch:{ all -> 0x0077 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.bh.run():void");
    }
}
