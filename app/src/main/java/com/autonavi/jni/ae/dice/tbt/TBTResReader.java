package com.autonavi.jni.ae.dice.tbt;

import android.content.Context;

public class TBTResReader {
    private static final int TBT_DATA_TYPE_CHANGE_PLAY = 32;
    private static final int TBT_DATA_TYPE_CHANGE_PLAY_BUS = 21;
    private static final int TBT_DATA_TYPE_CHANGE_PLAY_ELECTROMBILE = 22;
    private static final int TBT_DATA_TYPE_CHANGE_PLAY_RIDE = 19;
    private static final int TBT_DATA_TYPE_CHANGE_PLAY_WALK = 20;
    private static final int TBT_DATA_TYPE_CITY = 2;
    private static final int TBT_DATA_TYPE_DEFAULT = 0;
    private static final int TBT_DATA_TYPE_DOMAIN = 1;
    private static final int TBT_DATA_TYPE_ROAD_CONFIG_DRIVE = 64;
    private static final int TBT_DATA_TYPE_SAFETY_CRUISE = 33;
    private static final int TBT_DATA_TYPE_SOUND_PLAY_DEFAULT = 34;
    private static final int TBT_DATA_TYPE_SOUND_PLAY_DEFAULT_MOTORCYCLE = 39;
    private static final int TBT_DATA_TYPE_SOUND_PLAY_DEFAULT_TRUCK = 37;
    private static final int TBT_DATA_TYPE_SOUND_PLAY_ENGLISH = 36;
    private static final int TBT_DATA_TYPE_SOUND_PLAY_SKILLED = 35;
    private static final int TBT_DATA_TYPE_SOUND_PLAY_SKILLED_MOTORCYCLE = 40;
    private static final int TBT_DATA_TYPE_SOUND_PLAY_SKILLED_TRUCK = 38;
    private Context mContext = null;
    private long mPtr = 0;

    private static native long nativeCreate();

    private static native void nativeDestroy(long j);

    private static native void nativeSetObject(long j, TBTResReader tBTResReader);

    public TBTResReader(Context context) {
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

    /* JADX WARNING: Removed duplicated region for block: B:63:0x009a A[SYNTHETIC, Splitter:B:63:0x009a] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00a4 A[SYNTHETIC, Splitter:B:68:0x00a4] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00b0 A[SYNTHETIC, Splitter:B:75:0x00b0] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x00ba A[SYNTHETIC, Splitter:B:80:0x00ba] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x00c6 A[SYNTHETIC, Splitter:B:87:0x00c6] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x00d0 A[SYNTHETIC, Splitter:B:92:0x00d0] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:60:0x0095=Splitter:B:60:0x0095, B:72:0x00ab=Splitter:B:72:0x00ab} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] readAssetsFile(int r8) {
        /*
            r7 = this;
            android.content.Context r0 = r7.mContext
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            r0 = 64
            if (r8 == r0) goto L_0x0040
            switch(r8) {
                case 1: goto L_0x003e;
                case 2: goto L_0x003b;
                default: goto L_0x000d;
            }
        L_0x000d:
            switch(r8) {
                case 19: goto L_0x0038;
                case 20: goto L_0x0035;
                case 21: goto L_0x0032;
                case 22: goto L_0x002f;
                default: goto L_0x0010;
            }
        L_0x0010:
            switch(r8) {
                case 32: goto L_0x002c;
                case 33: goto L_0x0029;
                case 34: goto L_0x0026;
                case 35: goto L_0x0023;
                case 36: goto L_0x0020;
                case 37: goto L_0x001d;
                case 38: goto L_0x001a;
                case 39: goto L_0x0017;
                case 40: goto L_0x0014;
                default: goto L_0x0013;
            }
        L_0x0013:
            goto L_0x003e
        L_0x0014:
            java.lang.String r8 = "navi/odd_config_motorcycle.bin"
            goto L_0x0042
        L_0x0017:
            java.lang.String r8 = "navi/default_config_motorcycle.bin"
            goto L_0x0042
        L_0x001a:
            java.lang.String r8 = "navi/odd_config_truck.bin"
            goto L_0x0042
        L_0x001d:
            java.lang.String r8 = "navi/default_config_truck.bin"
            goto L_0x0042
        L_0x0020:
            java.lang.String r8 = "navi/default_config_eng.bin"
            goto L_0x0042
        L_0x0023:
            java.lang.String r8 = "navi/odd_config.bin"
            goto L_0x0042
        L_0x0026:
            java.lang.String r8 = "navi/default_config.bin"
            goto L_0x0042
        L_0x0029:
            java.lang.String r8 = "navi/GSafeConfig.dat"
            goto L_0x0042
        L_0x002c:
            java.lang.String r8 = "navi/changeplay.bin"
            goto L_0x0042
        L_0x002f:
            java.lang.String r8 = "navi/custom_voice_elec.bin"
            goto L_0x0042
        L_0x0032:
            java.lang.String r8 = "navi/custom_voice_bus.bin"
            goto L_0x0042
        L_0x0035:
            java.lang.String r8 = "navi/custom_voice_walk.bin"
            goto L_0x0042
        L_0x0038:
            java.lang.String r8 = "navi/custom_voice_ride.bin"
            goto L_0x0042
        L_0x003b:
            java.lang.String r8 = "navi/cityinfo.dat"
            goto L_0x0042
        L_0x003e:
            r8 = r1
            goto L_0x0042
        L_0x0040:
            java.lang.String r8 = "navi/road_config.bin"
        L_0x0042:
            boolean r0 = android.text.TextUtils.isEmpty(r8)
            if (r0 == 0) goto L_0x0049
            return r1
        L_0x0049:
            android.content.Context r0 = r7.mContext     // Catch:{ Exception -> 0x00a8, OutOfMemoryError -> 0x0092, all -> 0x008d }
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch:{ Exception -> 0x00a8, OutOfMemoryError -> 0x0092, all -> 0x008d }
            java.io.InputStream r8 = r0.open(r8)     // Catch:{ Exception -> 0x00a8, OutOfMemoryError -> 0x0092, all -> 0x008d }
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x008a, OutOfMemoryError -> 0x0087, all -> 0x0082 }
            r0.<init>()     // Catch:{ Exception -> 0x008a, OutOfMemoryError -> 0x0087, all -> 0x0082 }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r2]     // Catch:{ Exception -> 0x0080, OutOfMemoryError -> 0x007e }
        L_0x005c:
            r4 = 0
            int r5 = r8.read(r3, r4, r2)     // Catch:{ Exception -> 0x0080, OutOfMemoryError -> 0x007e }
            if (r5 <= 0) goto L_0x0067
            r0.write(r3, r4, r5)     // Catch:{ Exception -> 0x0080, OutOfMemoryError -> 0x007e }
            goto L_0x005c
        L_0x0067:
            byte[] r2 = r0.toByteArray()     // Catch:{ Exception -> 0x0080, OutOfMemoryError -> 0x007e }
            if (r8 == 0) goto L_0x0075
            r8.close()     // Catch:{ IOException -> 0x0071 }
            goto L_0x0075
        L_0x0071:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0075:
            r0.close()     // Catch:{ IOException -> 0x0079 }
            goto L_0x007d
        L_0x0079:
            r8 = move-exception
            r8.printStackTrace()
        L_0x007d:
            return r2
        L_0x007e:
            r2 = move-exception
            goto L_0x0095
        L_0x0080:
            r2 = move-exception
            goto L_0x00ab
        L_0x0082:
            r0 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
            goto L_0x00c4
        L_0x0087:
            r2 = move-exception
            r0 = r1
            goto L_0x0095
        L_0x008a:
            r2 = move-exception
            r0 = r1
            goto L_0x00ab
        L_0x008d:
            r8 = move-exception
            r0 = r1
            r1 = r8
            r8 = r0
            goto L_0x00c4
        L_0x0092:
            r2 = move-exception
            r8 = r1
            r0 = r8
        L_0x0095:
            r2.printStackTrace()     // Catch:{ all -> 0x00c3 }
            if (r8 == 0) goto L_0x00a2
            r8.close()     // Catch:{ IOException -> 0x009e }
            goto L_0x00a2
        L_0x009e:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00a2:
            if (r0 == 0) goto L_0x00c2
            r0.close()     // Catch:{ IOException -> 0x00be }
            goto L_0x00c2
        L_0x00a8:
            r2 = move-exception
            r8 = r1
            r0 = r8
        L_0x00ab:
            r2.printStackTrace()     // Catch:{ all -> 0x00c3 }
            if (r8 == 0) goto L_0x00b8
            r8.close()     // Catch:{ IOException -> 0x00b4 }
            goto L_0x00b8
        L_0x00b4:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00b8:
            if (r0 == 0) goto L_0x00c2
            r0.close()     // Catch:{ IOException -> 0x00be }
            goto L_0x00c2
        L_0x00be:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00c2:
            return r1
        L_0x00c3:
            r1 = move-exception
        L_0x00c4:
            if (r8 == 0) goto L_0x00ce
            r8.close()     // Catch:{ IOException -> 0x00ca }
            goto L_0x00ce
        L_0x00ca:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00ce:
            if (r0 == 0) goto L_0x00d8
            r0.close()     // Catch:{ IOException -> 0x00d4 }
            goto L_0x00d8
        L_0x00d4:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00d8:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.jni.ae.dice.tbt.TBTResReader.readAssetsFile(int):byte[]");
    }
}
