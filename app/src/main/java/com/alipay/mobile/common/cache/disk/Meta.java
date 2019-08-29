package com.alipay.mobile.common.cache.disk;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.File;
import java.io.IOException;

public class Meta {
    private DiskCache a;
    private String b = (this.a.getDirectory() + File.separator + "_meta");

    public Meta(DiskCache diskCache) {
        this.a = diskCache;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0091 A[SYNTHETIC, Splitter:B:17:0x0091] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00be A[SYNTHETIC, Splitter:B:29:0x00be] */
    /* JADX WARNING: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void init() {
        /*
            r14 = this;
            java.io.File r3 = new java.io.File
            java.lang.String r11 = r14.b
            r3.<init>(r11)
            boolean r11 = r3.exists()
            if (r11 != 0) goto L_0x000e
        L_0x000d:
            return
        L_0x000e:
            r8 = 0
            java.io.BufferedReader r9 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00ea }
            java.io.FileReader r11 = new java.io.FileReader     // Catch:{ Exception -> 0x00ea }
            r11.<init>(r3)     // Catch:{ Exception -> 0x00ea }
            r9.<init>(r11)     // Catch:{ Exception -> 0x00ea }
            java.lang.String r5 = r9.readLine()     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.String r11 = r9.readLine()     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            int r10 = java.lang.Integer.parseInt(r11)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.String r11 = r9.readLine()     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            long r6 = java.lang.Long.parseLong(r11)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.String r0 = r9.readLine()     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.String r11 = "alipay.diskcache"
            boolean r11 = r5.equals(r11)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            if (r11 == 0) goto L_0x003f
            int r11 = r0.length()     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            if (r11 != 0) goto L_0x00a2
        L_0x003f:
            java.io.IOException r11 = new java.io.IOException     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.String r13 = "Unexpected cache meta file: ["
            r12.<init>(r13)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.StringBuilder r12 = r12.append(r5)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.String r13 = ", "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.StringBuilder r12 = r12.append(r10)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.String r13 = ", "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.StringBuilder r12 = r12.append(r6)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.String r13 = ", "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.String r13 = "]"
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            r11.<init>(r12)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            throw r11     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
        L_0x0078:
            r2 = move-exception
            r8 = r9
        L_0x007a:
            r3.delete()     // Catch:{ all -> 0x00e8 }
            com.alipay.mobile.common.cache.disk.DiskCache r11 = r14.a     // Catch:{ all -> 0x00e8 }
            r11.clear()     // Catch:{ all -> 0x00e8 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x00e8 }
            java.lang.String r12 = "DiskCache"
            java.lang.String r13 = r2.getMessage()     // Catch:{ all -> 0x00e8 }
            r11.error(r12, r13)     // Catch:{ all -> 0x00e8 }
            if (r8 == 0) goto L_0x000d
            r8.close()     // Catch:{ IOException -> 0x0096 }
            goto L_0x000d
        L_0x0096:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r12 = "DiskCache"
            r11.error(r12, r1)
            goto L_0x000d
        L_0x00a2:
            r11 = 1
            if (r10 <= r11) goto L_0x00c2
            java.io.IOException r11 = new java.io.IOException     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.String r13 = "Unexpected meta file version:"
            r12.<init>(r13)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.StringBuilder r12 = r12.append(r10)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            r11.<init>(r12)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            throw r11     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
        L_0x00ba:
            r11 = move-exception
            r8 = r9
        L_0x00bc:
            if (r8 == 0) goto L_0x00c1
            r8.close()     // Catch:{ IOException -> 0x00dd }
        L_0x00c1:
            throw r11
        L_0x00c2:
            java.lang.String r4 = r9.readLine()     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            if (r4 == 0) goto L_0x00cc
            r14.a(r4)     // Catch:{ Exception -> 0x0078, all -> 0x00ba }
            goto L_0x00c2
        L_0x00cc:
            r9.close()     // Catch:{ IOException -> 0x00d1 }
            goto L_0x000d
        L_0x00d1:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r12 = "DiskCache"
            r11.error(r12, r1)
            goto L_0x000d
        L_0x00dd:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r12 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r13 = "DiskCache"
            r12.error(r13, r1)
            goto L_0x00c1
        L_0x00e8:
            r11 = move-exception
            goto L_0x00bc
        L_0x00ea:
            r2 = move-exception
            goto L_0x007a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.cache.disk.Meta.init():void");
    }

    private void a(String line) {
        String[] parts = line.split("   ");
        if (parts.length < 9) {
            throw new IOException("unexpected meta line: " + line);
        }
        this.a.addEntity(new Entity(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Long.parseLong(parts[4]), parts[5], Long.parseLong(parts[6]), Long.parseLong(parts[7]), parts[8]));
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x00ed A[Catch:{ all -> 0x0129 }] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00f9 A[SYNTHETIC, Splitter:B:17:0x00f9] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0115 A[SYNTHETIC, Splitter:B:26:0x0115] */
    /* JADX WARNING: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeMeta(java.util.HashMap<java.lang.String, com.alipay.mobile.common.cache.disk.Entity> r12) {
        /*
            r11 = this;
            java.io.File r5 = new java.io.File
            java.lang.String r7 = r11.b
            r5.<init>(r7)
            r0 = 0
            java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch:{ IOException -> 0x0142 }
            java.io.FileWriter r7 = new java.io.FileWriter     // Catch:{ IOException -> 0x0142 }
            r7.<init>(r5)     // Catch:{ IOException -> 0x0142 }
            r1.<init>(r7)     // Catch:{ IOException -> 0x0142 }
            java.lang.String r7 = "alipay.diskcache"
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r7 = 10
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = "1"
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r7 = 10
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            com.alipay.mobile.common.cache.disk.DiskCache r7 = r11.a     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            long r8 = r7.getMaxsize()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = java.lang.String.valueOf(r8)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r7 = 10
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r7 = 32
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r7 = 10
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.util.Collection r7 = r12.values()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.util.Iterator r6 = r7.iterator()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
        L_0x004a:
            boolean r7 = r6.hasNext()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            if (r7 == 0) goto L_0x00fd
            java.lang.Object r3 = r6.next()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            com.alipay.mobile.common.cache.disk.Entity r3 = (com.alipay.mobile.common.cache.disk.Entity) r3     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = r3.getOwner()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = "   "
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = r3.getGroup()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = "   "
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = r3.getUrl()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = "   "
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            int r7 = r3.getUsedTime()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = "   "
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            long r8 = r3.getSize()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = java.lang.String.valueOf(r8)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = "   "
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = r3.getPath()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = "   "
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            long r8 = r3.getCreateTime()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = java.lang.String.valueOf(r8)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = "   "
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            long r8 = r3.getPeriod()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = java.lang.String.valueOf(r8)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = "   "
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            java.lang.String r7 = r3.getContentType()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r7 = 10
            r1.write(r7)     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            goto L_0x004a
        L_0x00d4:
            r4 = move-exception
            r0 = r1
        L_0x00d6:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0129 }
            java.lang.String r9 = "Meta"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0129 }
            java.lang.String r10 = "fail to write meta file:"
            r7.<init>(r10)     // Catch:{ all -> 0x0129 }
            java.lang.StringBuilder r7 = r7.append(r4)     // Catch:{ all -> 0x0129 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0129 }
            if (r7 != 0) goto L_0x0115
            java.lang.String r7 = ""
        L_0x00ef:
            r8.error(r9, r7)     // Catch:{ all -> 0x0129 }
            com.alipay.mobile.common.cache.disk.DiskCache r7 = r11.a     // Catch:{ all -> 0x0129 }
            r7.clear()     // Catch:{ all -> 0x0129 }
            if (r0 == 0) goto L_0x00fc
            r0.close()     // Catch:{ IOException -> 0x011a }
        L_0x00fc:
            return
        L_0x00fd:
            r1.flush()     // Catch:{ IOException -> 0x00d4, all -> 0x013f }
            r1.close()     // Catch:{ IOException -> 0x0105 }
            r0 = r1
            goto L_0x00fc
        L_0x0105:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "Meta"
            java.lang.String r9 = java.lang.String.valueOf(r2)
            r7.error(r8, r9)
            r0 = r1
            goto L_0x00fc
        L_0x0115:
            java.lang.String r7 = r4.getMessage()     // Catch:{ all -> 0x0129 }
            goto L_0x00ef
        L_0x011a:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "Meta"
            java.lang.String r9 = java.lang.String.valueOf(r2)
            r7.error(r8, r9)
            goto L_0x00fc
        L_0x0129:
            r7 = move-exception
        L_0x012a:
            if (r0 == 0) goto L_0x012f
            r0.close()     // Catch:{ IOException -> 0x0130 }
        L_0x012f:
            throw r7
        L_0x0130:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r9 = "Meta"
            java.lang.String r10 = java.lang.String.valueOf(r2)
            r8.error(r9, r10)
            goto L_0x012f
        L_0x013f:
            r7 = move-exception
            r0 = r1
            goto L_0x012a
        L_0x0142:
            r4 = move-exception
            goto L_0x00d6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.cache.disk.Meta.writeMeta(java.util.HashMap):void");
    }
}
