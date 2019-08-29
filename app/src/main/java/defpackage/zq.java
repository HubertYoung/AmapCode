package defpackage;

import android.os.Environment;
import com.amap.bundle.blutils.FileUtil;
import com.autonavi.minimap.offline.model.FilePathHelper;

/* renamed from: zq reason: default package */
/* compiled from: ApmUploader */
public final class zq {
    private static zq b;
    private String a;

    private zq() {
        try {
            this.a = FileUtil.getFilesDir().getAbsolutePath();
            if (bno.a) {
                StringBuilder sb = new StringBuilder();
                sb.append(Environment.getExternalStorageDirectory());
                sb.append(FilePathHelper.APP_FOLDER);
                this.a = sb.toString();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static synchronized zq a() {
        zq zqVar;
        synchronized (zq.class) {
            if (b == null) {
                b = new zq();
            }
            zqVar = b;
        }
        return zqVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x008b A[SYNTHETIC, Splitter:B:31:0x008b] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0093 A[SYNTHETIC, Splitter:B:35:0x0093] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a3 A[Catch:{ Exception -> 0x00ad }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(java.lang.String r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            long r0 = java.lang.System.nanoTime()     // Catch:{ all -> 0x00b3 }
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x00b3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b3 }
            r3.<init>()     // Catch:{ all -> 0x00b3 }
            java.lang.String r4 = r5.a     // Catch:{ all -> 0x00b3 }
            r3.append(r4)     // Catch:{ all -> 0x00b3 }
            java.lang.String r4 = "/apm/"
            r3.append(r4)     // Catch:{ all -> 0x00b3 }
            java.util.UUID r4 = java.util.UUID.randomUUID()     // Catch:{ all -> 0x00b3 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00b3 }
            r3.append(r4)     // Catch:{ all -> 0x00b3 }
            java.lang.String r4 = "@"
            r3.append(r4)     // Catch:{ all -> 0x00b3 }
            r3.append(r0)     // Catch:{ all -> 0x00b3 }
            java.lang.String r0 = "@.logtmp"
            r3.append(r0)     // Catch:{ all -> 0x00b3 }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x00b3 }
            r2.<init>(r0)     // Catch:{ all -> 0x00b3 }
            java.lang.String r0 = "ApmUploader"
            java.lang.String r1 = "uploadlOG ---> "
            java.lang.String r3 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x00b3 }
            java.lang.String r1 = r1.concat(r3)     // Catch:{ all -> 0x00b3 }
            com.amap.bundle.logs.AMapLog.d(r0, r1)     // Catch:{ all -> 0x00b3 }
            boolean r0 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x00ad }
            if (r0 != 0) goto L_0x009c
            java.io.File r0 = r2.getParentFile()     // Catch:{ Exception -> 0x00ad }
            boolean r1 = r0.exists()     // Catch:{ Exception -> 0x00ad }
            if (r1 != 0) goto L_0x0057
            r0.mkdirs()     // Catch:{ Exception -> 0x00ad }
        L_0x0057:
            boolean r0 = r2.exists()     // Catch:{ Exception -> 0x00ad }
            if (r0 == 0) goto L_0x0060
            r2.delete()     // Catch:{ Exception -> 0x00ad }
        L_0x0060:
            r0 = 0
            r2.createNewFile()     // Catch:{ Exception -> 0x0085 }
            byte[] r1 = r6.getBytes()     // Catch:{ Exception -> 0x0085 }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0085 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x0085 }
            r3.write(r1)     // Catch:{ Exception -> 0x007f, all -> 0x007c }
            r3.flush()     // Catch:{ Exception -> 0x007f, all -> 0x007c }
            r3.close()     // Catch:{ IOException -> 0x0077 }
            goto L_0x009c
        L_0x0077:
            r0 = move-exception
        L_0x0078:
            r0.printStackTrace()     // Catch:{ Exception -> 0x00ad }
            goto L_0x009c
        L_0x007c:
            r6 = move-exception
            r0 = r3
            goto L_0x0091
        L_0x007f:
            r0 = move-exception
            r1 = r0
            r0 = r3
            goto L_0x0086
        L_0x0083:
            r6 = move-exception
            goto L_0x0091
        L_0x0085:
            r1 = move-exception
        L_0x0086:
            defpackage.kf.a(r1)     // Catch:{ all -> 0x0083 }
            if (r0 == 0) goto L_0x009c
            r0.close()     // Catch:{ IOException -> 0x008f }
            goto L_0x009c
        L_0x008f:
            r0 = move-exception
            goto L_0x0078
        L_0x0091:
            if (r0 == 0) goto L_0x009b
            r0.close()     // Catch:{ IOException -> 0x0097 }
            goto L_0x009b
        L_0x0097:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x00ad }
        L_0x009b:
            throw r6     // Catch:{ Exception -> 0x00ad }
        L_0x009c:
            r0 = 1
            java.io.File r0 = defpackage.zp.a(r2, r0)     // Catch:{ Exception -> 0x00ad }
            if (r0 == 0) goto L_0x00ab
            com.amap.bundle.network.biz.statistic.apm.ApmUploader$1 r1 = new com.amap.bundle.network.biz.statistic.apm.ApmUploader$1     // Catch:{ Exception -> 0x00ad }
            r1.<init>(r5, r6, r0)     // Catch:{ Exception -> 0x00ad }
            defpackage.zp.a(r0, r1)     // Catch:{ Exception -> 0x00ad }
        L_0x00ab:
            monitor-exit(r5)
            return
        L_0x00ad:
            r6 = move-exception
            r6.printStackTrace()     // Catch:{ all -> 0x00b3 }
            monitor-exit(r5)
            return
        L_0x00b3:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zq.a(java.lang.String):void");
    }
}
