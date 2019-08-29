package com.alibaba.analytics.core.logbuilder;

import android.content.Context;
import android.provider.Settings.Secure;

class Reserve5Helper {
    private static final String ANDROID_ID = "aid";
    private static final String WIDEVINE_ID = "wvid";
    private static boolean bInitAndroidId = false;
    private static boolean bInitWideVineId = false;
    private static boolean bReserve = false;
    private static String mAndroidId = "";
    private static String mReserve = "";
    private static String mWideVineId = "";

    Reserve5Helper() {
    }

    static String getReserve(Context context) {
        if (bReserve || context == null) {
            return mReserve;
        }
        synchronized (Reserve5Helper.class) {
            try {
                if (bReserve) {
                    String str = mReserve;
                    return str;
                }
                StringBuilder sb = new StringBuilder("aid=");
                sb.append(getAndroidID(context));
                sb.append(",wvid=");
                sb.append(getWideVineId());
                mReserve = sb.toString();
                bReserve = true;
                return mReserve;
            }
        }
    }

    private static String getAndroidID(Context context) {
        if (bInitAndroidId || context == null) {
            return mAndroidId;
        }
        try {
            mAndroidId = Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable unused) {
        }
        bInitAndroidId = true;
        return mAndroidId;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getWideVineId() {
        /*
            boolean r0 = bInitWideVineId
            if (r0 == 0) goto L_0x0007
            java.lang.String r0 = mWideVineId
            return r0
        L_0x0007:
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 18
            if (r0 < r1) goto L_0x0062
            java.util.UUID r0 = new java.util.UUID
            r1 = -1301668207276963122(0xedef8ba979d64ace, double:-3.563403477674908E221)
            r3 = -6645017420763422227(0xa3c827dcd51d21ed, double:-2.5964014370906125E-136)
            r0.<init>(r1, r3)
            r1 = 0
            r2 = 28
            android.media.MediaDrm r3 = new android.media.MediaDrm     // Catch:{ Throwable -> 0x0055, all -> 0x0045 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x0055, all -> 0x0045 }
            java.lang.String r0 = "deviceUniqueId"
            byte[] r0 = r3.getPropertyByteArray(r0)     // Catch:{ Throwable -> 0x0043, all -> 0x0041 }
            r1 = 0
            java.lang.String r0 = android.util.Base64.encodeToString(r0, r1)     // Catch:{ Throwable -> 0x0043, all -> 0x0041 }
            java.lang.String r0 = r0.trim()     // Catch:{ Throwable -> 0x0043, all -> 0x0041 }
            mWideVineId = r0     // Catch:{ Throwable -> 0x0043, all -> 0x0041 }
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r2) goto L_0x003d
            r3.close()
            goto L_0x0062
        L_0x003d:
            r3.release()
            goto L_0x0062
        L_0x0041:
            r0 = move-exception
            goto L_0x0047
        L_0x0043:
            r1 = r3
            goto L_0x0055
        L_0x0045:
            r0 = move-exception
            r3 = r1
        L_0x0047:
            if (r3 == 0) goto L_0x0054
            int r1 = android.os.Build.VERSION.SDK_INT
            if (r1 < r2) goto L_0x0051
            r3.close()
            goto L_0x0054
        L_0x0051:
            r3.release()
        L_0x0054:
            throw r0
        L_0x0055:
            if (r1 == 0) goto L_0x0062
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r2) goto L_0x005f
            r1.close()
            goto L_0x0062
        L_0x005f:
            r1.release()
        L_0x0062:
            r0 = 1
            bInitWideVineId = r0
            java.lang.String r0 = mWideVineId
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.logbuilder.Reserve5Helper.getWideVineId():java.lang.String");
    }
}
