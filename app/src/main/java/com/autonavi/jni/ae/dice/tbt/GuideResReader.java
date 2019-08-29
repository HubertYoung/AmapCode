package com.autonavi.jni.ae.dice.tbt;

import android.content.Context;

public class GuideResReader {
    private static final int FILE_TYPE_CHANGEPLAY = 3;
    private static final int FILE_TYPE_CITY = 1;
    private static final int FILE_TYPE_CUSTOM_VOICE_RIDE = 6;
    private static final int FILE_TYPE_CUSTOM_VOICE_WALK = 7;
    private static final int FILE_TYPE_GUIDESAFE = 4;
    private static final int FILE_TYPE_LUA = 8;
    private static final int FILE_TYPE_NAVISOUND = 2;
    private static final int FILE_TYPE_NAVISOUND_TRUCK = 5;
    private static final int FILE_TYPE_TYPEMOTORCYCLE = 9;
    private Context mContext = null;
    private long mPtr = 0;

    private static native long nativeCreate();

    private static native void nativeDestroy(long j);

    private static native void nativeSetObject(long j, GuideResReader guideResReader);

    public GuideResReader(Context context) {
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

    /* JADX WARNING: Removed duplicated region for block: B:100:0x00d2 A[SYNTHETIC, Splitter:B:100:0x00d2] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x009c A[SYNTHETIC, Splitter:B:71:0x009c] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x00a6 A[SYNTHETIC, Splitter:B:76:0x00a6] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x00b2 A[SYNTHETIC, Splitter:B:83:0x00b2] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x00bc A[SYNTHETIC, Splitter:B:88:0x00bc] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x00c8 A[SYNTHETIC, Splitter:B:95:0x00c8] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:68:0x0097=Splitter:B:68:0x0097, B:80:0x00ad=Splitter:B:80:0x00ad} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] readAssetsFile(int r6, int r7) {
        /*
            r5 = this;
            android.content.Context r0 = r5.mContext
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            r0 = 1
            if (r6 != r0) goto L_0x000c
            java.lang.String r6 = "navi/cityinfo.dat"
            goto L_0x0047
        L_0x000c:
            r2 = 2
            r3 = 3
            if (r6 != r2) goto L_0x001d
            if (r7 != r0) goto L_0x0015
            java.lang.String r6 = "navi/odd_config.bin"
            goto L_0x0047
        L_0x0015:
            if (r7 != r3) goto L_0x001a
            java.lang.String r6 = "navi/default_config_eng.bin"
            goto L_0x0047
        L_0x001a:
            java.lang.String r6 = "navi/default_config.bin"
            goto L_0x0047
        L_0x001d:
            if (r6 != r3) goto L_0x0022
            java.lang.String r6 = "navi/changeplay.bin"
            goto L_0x0047
        L_0x0022:
            r2 = 4
            if (r6 != r2) goto L_0x0028
            java.lang.String r6 = "navi/GSafeConfig.dat"
            goto L_0x0047
        L_0x0028:
            r2 = 5
            if (r6 != r2) goto L_0x0033
            if (r7 != r0) goto L_0x0030
            java.lang.String r6 = "navi/odd_config_truck.bin"
            goto L_0x0047
        L_0x0030:
            java.lang.String r6 = "navi/default_config_truck.bin"
            goto L_0x0047
        L_0x0033:
            r2 = 8
            if (r6 != r2) goto L_0x003a
            java.lang.String r6 = "navi/guidelua.bin"
            goto L_0x0047
        L_0x003a:
            r2 = 9
            if (r6 != r2) goto L_0x0046
            if (r7 != r0) goto L_0x0043
            java.lang.String r6 = "navi/odd_config_motorcycle.bin"
            goto L_0x0047
        L_0x0043:
            java.lang.String r6 = "navi/default_config_motorcycle.bin"
            goto L_0x0047
        L_0x0046:
            r6 = r1
        L_0x0047:
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 == 0) goto L_0x004e
            return r1
        L_0x004e:
            android.content.Context r7 = r5.mContext     // Catch:{ Exception -> 0x00aa, OutOfMemoryError -> 0x0094, all -> 0x0090 }
            android.content.res.AssetManager r7 = r7.getAssets()     // Catch:{ Exception -> 0x00aa, OutOfMemoryError -> 0x0094, all -> 0x0090 }
            java.io.InputStream r6 = r7.open(r6)     // Catch:{ Exception -> 0x00aa, OutOfMemoryError -> 0x0094, all -> 0x0090 }
            java.io.ByteArrayOutputStream r7 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x008d, OutOfMemoryError -> 0x008a, all -> 0x0087 }
            r7.<init>()     // Catch:{ Exception -> 0x008d, OutOfMemoryError -> 0x008a, all -> 0x0087 }
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0085, OutOfMemoryError -> 0x0083 }
        L_0x0061:
            r3 = 0
            int r4 = r6.read(r2, r3, r0)     // Catch:{ Exception -> 0x0085, OutOfMemoryError -> 0x0083 }
            if (r4 <= 0) goto L_0x006c
            r7.write(r2, r3, r4)     // Catch:{ Exception -> 0x0085, OutOfMemoryError -> 0x0083 }
            goto L_0x0061
        L_0x006c:
            byte[] r0 = r7.toByteArray()     // Catch:{ Exception -> 0x0085, OutOfMemoryError -> 0x0083 }
            if (r6 == 0) goto L_0x007a
            r6.close()     // Catch:{ IOException -> 0x0076 }
            goto L_0x007a
        L_0x0076:
            r6 = move-exception
            r6.printStackTrace()
        L_0x007a:
            r7.close()     // Catch:{ IOException -> 0x007e }
            goto L_0x0082
        L_0x007e:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0082:
            return r0
        L_0x0083:
            r0 = move-exception
            goto L_0x0097
        L_0x0085:
            r0 = move-exception
            goto L_0x00ad
        L_0x0087:
            r0 = move-exception
            r7 = r1
            goto L_0x00c6
        L_0x008a:
            r0 = move-exception
            r7 = r1
            goto L_0x0097
        L_0x008d:
            r0 = move-exception
            r7 = r1
            goto L_0x00ad
        L_0x0090:
            r0 = move-exception
            r6 = r1
            r7 = r6
            goto L_0x00c6
        L_0x0094:
            r0 = move-exception
            r6 = r1
            r7 = r6
        L_0x0097:
            r0.printStackTrace()     // Catch:{ all -> 0x00c5 }
            if (r6 == 0) goto L_0x00a4
            r6.close()     // Catch:{ IOException -> 0x00a0 }
            goto L_0x00a4
        L_0x00a0:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00a4:
            if (r7 == 0) goto L_0x00c4
            r7.close()     // Catch:{ IOException -> 0x00c0 }
            goto L_0x00c4
        L_0x00aa:
            r0 = move-exception
            r6 = r1
            r7 = r6
        L_0x00ad:
            r0.printStackTrace()     // Catch:{ all -> 0x00c5 }
            if (r6 == 0) goto L_0x00ba
            r6.close()     // Catch:{ IOException -> 0x00b6 }
            goto L_0x00ba
        L_0x00b6:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00ba:
            if (r7 == 0) goto L_0x00c4
            r7.close()     // Catch:{ IOException -> 0x00c0 }
            goto L_0x00c4
        L_0x00c0:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00c4:
            return r1
        L_0x00c5:
            r0 = move-exception
        L_0x00c6:
            if (r6 == 0) goto L_0x00d0
            r6.close()     // Catch:{ IOException -> 0x00cc }
            goto L_0x00d0
        L_0x00cc:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00d0:
            if (r7 == 0) goto L_0x00da
            r7.close()     // Catch:{ IOException -> 0x00d6 }
            goto L_0x00da
        L_0x00d6:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00da:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.jni.ae.dice.tbt.GuideResReader.readAssetsFile(int, int):byte[]");
    }
}
