package com.autonavi.minimap.offline.koala.impl;

import android.content.Context;
import com.autonavi.minimap.offline.koala.KoalaUtils;
import com.autonavi.minimap.offline.koala.intf.IKoalaPersistence;
import java.io.File;

public class KoalaPersistence implements IKoalaPersistence {
    private String mFilePath;

    public KoalaPersistence(Context context) {
        String cacheFilePath = KoalaUtils.getCacheFilePath(context);
        StringBuilder sb = new StringBuilder();
        sb.append(cacheFilePath);
        sb.append(File.separator);
        sb.append("Koala");
        sb.append(File.separator);
        sb.append("persistence");
        sb.append(File.separator);
        this.mFilePath = sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0052 A[SYNTHETIC, Splitter:B:22:0x0052] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0061 A[SYNTHETIC, Splitter:B:30:0x0061] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.autonavi.minimap.offline.koala.model.KoalaDownloadModel load(java.lang.String r5) {
        /*
            r4 = this;
            java.io.File r0 = new java.io.File
            java.lang.String r1 = r4.mFilePath
            r0.<init>(r1)
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x0010
            r0.mkdirs()
        L_0x0010:
            java.io.File r1 = new java.io.File
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r5)
            java.lang.String r5 = ".dat"
            r2.append(r5)
            java.lang.String r5 = r2.toString()
            r1.<init>(r0, r5)
            boolean r5 = r1.exists()
            r0 = 0
            if (r5 != 0) goto L_0x002e
            return r0
        L_0x002e:
            java.io.ObjectInputStream r5 = new java.io.ObjectInputStream     // Catch:{ Exception -> 0x004b, all -> 0x0049 }
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x004b, all -> 0x0049 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x004b, all -> 0x0049 }
            r5.<init>(r2)     // Catch:{ Exception -> 0x004b, all -> 0x0049 }
            java.lang.Object r1 = r5.readObject()     // Catch:{ Exception -> 0x0047 }
            com.autonavi.minimap.offline.koala.model.KoalaDownloadModel r1 = (com.autonavi.minimap.offline.koala.model.KoalaDownloadModel) r1     // Catch:{ Exception -> 0x0047 }
            r5.close()     // Catch:{ IOException -> 0x0042 }
            goto L_0x0046
        L_0x0042:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0046:
            return r1
        L_0x0047:
            r1 = move-exception
            goto L_0x004d
        L_0x0049:
            r5 = move-exception
            goto L_0x005f
        L_0x004b:
            r1 = move-exception
            r5 = r0
        L_0x004d:
            r1.printStackTrace()     // Catch:{ all -> 0x005b }
            if (r5 == 0) goto L_0x005a
            r5.close()     // Catch:{ IOException -> 0x0056 }
            goto L_0x005a
        L_0x0056:
            r5 = move-exception
            r5.printStackTrace()
        L_0x005a:
            return r0
        L_0x005b:
            r0 = move-exception
            r3 = r0
            r0 = r5
            r5 = r3
        L_0x005f:
            if (r0 == 0) goto L_0x0069
            r0.close()     // Catch:{ IOException -> 0x0065 }
            goto L_0x0069
        L_0x0065:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0069:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.koala.impl.KoalaPersistence.load(java.lang.String):com.autonavi.minimap.offline.koala.model.KoalaDownloadModel");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0052 A[SYNTHETIC, Splitter:B:23:0x0052] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x005b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x005e A[SYNTHETIC, Splitter:B:31:0x005e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void save(com.autonavi.minimap.offline.koala.model.KoalaDownloadModel r5) {
        /*
            r4 = this;
            java.io.File r0 = new java.io.File
            java.lang.String r1 = r4.mFilePath
            r0.<init>(r1)
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x0010
            r0.mkdirs()
        L_0x0010:
            java.io.File r1 = new java.io.File
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r5.getKey()
            r2.append(r3)
            java.lang.String r3 = ".dat"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r0, r2)
            r0 = 0
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x004c }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x004c }
            r3.<init>(r1)     // Catch:{ IOException -> 0x004c }
            r2.<init>(r3)     // Catch:{ IOException -> 0x004c }
            r2.writeObject(r5)     // Catch:{ IOException -> 0x0047, all -> 0x0044 }
            r2.flush()     // Catch:{ IOException -> 0x0047, all -> 0x0044 }
            r2.close()     // Catch:{ IOException -> 0x003f }
            return
        L_0x003f:
            r5 = move-exception
            r5.printStackTrace()
            return
        L_0x0044:
            r5 = move-exception
            r0 = r2
            goto L_0x005c
        L_0x0047:
            r5 = move-exception
            r0 = r2
            goto L_0x004d
        L_0x004a:
            r5 = move-exception
            goto L_0x005c
        L_0x004c:
            r5 = move-exception
        L_0x004d:
            r5.printStackTrace()     // Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x005b
            r0.close()     // Catch:{ IOException -> 0x0056 }
            return
        L_0x0056:
            r5 = move-exception
            r5.printStackTrace()
            return
        L_0x005b:
            return
        L_0x005c:
            if (r0 == 0) goto L_0x0066
            r0.close()     // Catch:{ IOException -> 0x0062 }
            goto L_0x0066
        L_0x0062:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0066:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.koala.impl.KoalaPersistence.save(com.autonavi.minimap.offline.koala.model.KoalaDownloadModel):void");
    }
}
