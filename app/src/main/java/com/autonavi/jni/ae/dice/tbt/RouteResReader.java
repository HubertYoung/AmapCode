package com.autonavi.jni.ae.dice.tbt;

import android.content.Context;

public class RouteResReader {
    private Context mContext = null;
    private long mPtr = 0;

    private static native long nativeCreate();

    private static native void nativeDestroy(long j);

    private static native void nativeSetObject(long j, RouteResReader routeResReader);

    public RouteResReader(Context context) {
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
        this.mPtr = nativeCreate();
        nativeSetObject(this.mPtr, this);
    }

    public long getPtr() {
        return this.mPtr;
    }

    public void release() {
        this.mContext = null;
        nativeDestroy(this.mPtr);
        this.mPtr = 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:46:0x0065 A[SYNTHETIC, Splitter:B:46:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x006f A[SYNTHETIC, Splitter:B:51:0x006f] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x007b A[SYNTHETIC, Splitter:B:58:0x007b] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0085 A[SYNTHETIC, Splitter:B:63:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0091 A[SYNTHETIC, Splitter:B:70:0x0091] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x009b A[SYNTHETIC, Splitter:B:75:0x009b] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:43:0x0060=Splitter:B:43:0x0060, B:55:0x0076=Splitter:B:55:0x0076} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] readAssetsFile(int r7, int r8) {
        /*
            r6 = this;
            android.content.Context r7 = r6.mContext
            r0 = 0
            if (r7 != 0) goto L_0x0006
            return r0
        L_0x0006:
            r7 = 1
            if (r8 != r7) goto L_0x000c
            java.lang.String r7 = "navi/road_config.bin"
            goto L_0x000d
        L_0x000c:
            r7 = r0
        L_0x000d:
            boolean r8 = android.text.TextUtils.isEmpty(r7)
            if (r8 == 0) goto L_0x0014
            return r0
        L_0x0014:
            android.content.Context r8 = r6.mContext     // Catch:{ Exception -> 0x0073, OutOfMemoryError -> 0x005d, all -> 0x0058 }
            android.content.res.AssetManager r8 = r8.getAssets()     // Catch:{ Exception -> 0x0073, OutOfMemoryError -> 0x005d, all -> 0x0058 }
            java.io.InputStream r7 = r8.open(r7)     // Catch:{ Exception -> 0x0073, OutOfMemoryError -> 0x005d, all -> 0x0058 }
            java.io.ByteArrayOutputStream r8 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0055, OutOfMemoryError -> 0x0052, all -> 0x004d }
            r8.<init>()     // Catch:{ Exception -> 0x0055, OutOfMemoryError -> 0x0052, all -> 0x004d }
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r1]     // Catch:{ Exception -> 0x004b, OutOfMemoryError -> 0x0049 }
        L_0x0027:
            r3 = 0
            int r4 = r7.read(r2, r3, r1)     // Catch:{ Exception -> 0x004b, OutOfMemoryError -> 0x0049 }
            if (r4 <= 0) goto L_0x0032
            r8.write(r2, r3, r4)     // Catch:{ Exception -> 0x004b, OutOfMemoryError -> 0x0049 }
            goto L_0x0027
        L_0x0032:
            byte[] r1 = r8.toByteArray()     // Catch:{ Exception -> 0x004b, OutOfMemoryError -> 0x0049 }
            if (r7 == 0) goto L_0x0040
            r7.close()     // Catch:{ IOException -> 0x003c }
            goto L_0x0040
        L_0x003c:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0040:
            r8.close()     // Catch:{ IOException -> 0x0044 }
            goto L_0x0048
        L_0x0044:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0048:
            return r1
        L_0x0049:
            r1 = move-exception
            goto L_0x0060
        L_0x004b:
            r1 = move-exception
            goto L_0x0076
        L_0x004d:
            r8 = move-exception
            r5 = r0
            r0 = r8
            r8 = r5
            goto L_0x008f
        L_0x0052:
            r1 = move-exception
            r8 = r0
            goto L_0x0060
        L_0x0055:
            r1 = move-exception
            r8 = r0
            goto L_0x0076
        L_0x0058:
            r7 = move-exception
            r8 = r0
            r0 = r7
            r7 = r8
            goto L_0x008f
        L_0x005d:
            r1 = move-exception
            r7 = r0
            r8 = r7
        L_0x0060:
            r1.printStackTrace()     // Catch:{ all -> 0x008e }
            if (r7 == 0) goto L_0x006d
            r7.close()     // Catch:{ IOException -> 0x0069 }
            goto L_0x006d
        L_0x0069:
            r7 = move-exception
            r7.printStackTrace()
        L_0x006d:
            if (r8 == 0) goto L_0x008d
            r8.close()     // Catch:{ IOException -> 0x0089 }
            goto L_0x008d
        L_0x0073:
            r1 = move-exception
            r7 = r0
            r8 = r7
        L_0x0076:
            r1.printStackTrace()     // Catch:{ all -> 0x008e }
            if (r7 == 0) goto L_0x0083
            r7.close()     // Catch:{ IOException -> 0x007f }
            goto L_0x0083
        L_0x007f:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0083:
            if (r8 == 0) goto L_0x008d
            r8.close()     // Catch:{ IOException -> 0x0089 }
            goto L_0x008d
        L_0x0089:
            r7 = move-exception
            r7.printStackTrace()
        L_0x008d:
            return r0
        L_0x008e:
            r0 = move-exception
        L_0x008f:
            if (r7 == 0) goto L_0x0099
            r7.close()     // Catch:{ IOException -> 0x0095 }
            goto L_0x0099
        L_0x0095:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0099:
            if (r8 == 0) goto L_0x00a3
            r8.close()     // Catch:{ IOException -> 0x009f }
            goto L_0x00a3
        L_0x009f:
            r7 = move-exception
            r7.printStackTrace()
        L_0x00a3:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.jni.ae.dice.tbt.RouteResReader.readAssetsFile(int, int):byte[]");
    }
}
