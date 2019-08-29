package defpackage;

import android.content.Context;
import android.provider.Settings.Secure;
import android.text.TextUtils;

/* renamed from: km reason: default package */
/* compiled from: GenID */
public class km {
    private static volatile km b;
    public String a;

    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r6v0, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r6v1, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r6v2 */
    /* JADX WARNING: type inference failed for: r6v3 */
    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: type inference failed for: r6v9 */
    /* JADX WARNING: type inference failed for: r6v10 */
    /* JADX WARNING: type inference failed for: r6v11 */
    /* JADX WARNING: type inference failed for: r6v12 */
    /* JADX WARNING: type inference failed for: r6v13 */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x016b */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0174  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x01a8 A[SYNTHETIC, Splitter:B:56:0x01a8] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x01ad A[SYNTHETIC, Splitter:B:60:0x01ad] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x01c0 A[SYNTHETIC, Splitter:B:71:0x01c0] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x01c5 A[SYNTHETIC, Splitter:B:75:0x01c5] */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private km() {
        /*
            r14 = this;
            r14.<init>()
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            long r1 = java.lang.System.currentTimeMillis()
            com.amap.bundle.mapstorage.MapSharePreference r3 = new com.amap.bundle.mapstorage.MapSharePreference
            java.lang.String r4 = "GenID"
            r3.<init>(r4)
            java.lang.String r4 = "genID"
            java.lang.String r5 = ""
            java.lang.String r4 = r3.getStringValue(r4, r5)
            r14.a = r4
            java.lang.String r4 = r14.a
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 == 0) goto L_0x01d4
            r4 = 0
            java.io.File r5 = new java.io.File     // Catch:{ Throwable -> 0x018a, all -> 0x0185 }
            java.io.File r6 = r0.getFilesDir()     // Catch:{ Throwable -> 0x018a, all -> 0x0185 }
            java.lang.String r7 = "GenID.lck"
            r5.<init>(r6, r7)     // Catch:{ Throwable -> 0x018a, all -> 0x0185 }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0182, all -> 0x017f }
            r6.<init>(r5)     // Catch:{ Throwable -> 0x0182, all -> 0x017f }
            java.nio.channels.FileChannel r7 = r6.getChannel()     // Catch:{ Throwable -> 0x017d }
            java.nio.channels.FileLock r7 = r7.lock()     // Catch:{ Throwable -> 0x017d }
            java.lang.String r4 = "MP_GenID"
            android.content.SharedPreferences r4 = com.amap.bundle.mapstorage.MPSharedPreferences.a(r0, r4)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r8 = "genID"
            java.lang.String r9 = ""
            java.lang.String r8 = r4.getString(r8, r9)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r14.a = r8     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r8 = r14.a     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            if (r8 == 0) goto L_0x013a
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r8.<init>()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r9 = "gen"
            r8.append(r9)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r0 = b(r0)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            boolean r9 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            if (r9 == 0) goto L_0x006c
            java.lang.String r0 = ""
            goto L_0x0072
        L_0x006c:
            r9 = 16
            java.lang.String r0 = b(r0, r9)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
        L_0x0072:
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r9 = java.lang.Long.toHexString(r9)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r10 = 11
            java.lang.String r9 = b(r9, r10)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.util.Random r10 = new java.util.Random     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r10.<init>()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r11 = 15
            int r10 = r10.nextInt(r11)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r9 = a(r9, r10)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            boolean r11 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            if (r11 == 0) goto L_0x00cd
            java.util.Random r0 = new java.util.Random     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r0.<init>()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            long r11 = r0.nextLong()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r0 = java.lang.Long.toHexString(r11)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r11 = 8
            java.lang.String r0 = b(r0, r11)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            long r12 = java.lang.System.nanoTime()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r12 = java.lang.Long.toHexString(r12)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r11 = b(r12, r11)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r12.<init>()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r12.append(r11)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r12.append(r0)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r0 = r12.toString()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r0 = a(r0, r10)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r11 = "1"
            r8.append(r11)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            goto L_0x00d6
        L_0x00cd:
            java.lang.String r0 = a(r0, r10)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r11 = "2"
            r8.append(r11)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
        L_0x00d6:
            java.lang.String r10 = java.lang.Integer.toHexString(r10)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r11 = 1
            java.lang.String r10 = b(r10, r11)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r8.append(r10)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r10 = android.os.Build.MODEL     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            int r10 = r10.hashCode()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            int r10 = r10 % 255
            int r10 = java.lang.Math.abs(r10)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r10 = java.lang.Integer.toHexString(r10)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r11 = 2
            java.lang.String r10 = b(r10, r11)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r8.append(r10)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r10.<init>()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r10.append(r9)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r10.append(r0)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r10 = a(r10)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r8.append(r10)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r8.append(r9)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r8.append(r0)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r0 = r8.toString()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r14.a = r0     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r0 = r14.a     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            if (r0 != 0) goto L_0x013a
            android.content.SharedPreferences$Editor r0 = r4.edit()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r4 = "genID"
            java.lang.String r8 = r14.a     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            android.content.SharedPreferences$Editor r0 = r0.putString(r4, r8)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r0.commit()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r0 = "genID"
            java.lang.String r4 = r14.a     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r3.putStringValue(r0, r4)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
        L_0x013a:
            java.lang.String r0 = "paas.blutils"
            java.lang.String r3 = "GenID"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r8 = "generate ID: "
            r4.<init>(r8)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r8 = r14.a     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            if (r8 != 0) goto L_0x014c
            java.lang.String r8 = "null"
            goto L_0x014e
        L_0x014c:
            java.lang.String r8 = r14.a     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
        L_0x014e:
            r4.append(r8)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r8 = ", costTime: "
            r4.append(r8)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            r10 = 0
            long r8 = r8 - r1
            r4.append(r8)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            java.lang.String r1 = r4.toString()     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            com.amap.bundle.logs.AMapLog.info(r0, r3, r1)     // Catch:{ Throwable -> 0x017a, all -> 0x0178 }
            if (r7 == 0) goto L_0x016b
            r7.release()     // Catch:{ IOException -> 0x016b }
        L_0x016b:
            r6.close()     // Catch:{ IOException -> 0x016e }
        L_0x016e:
            boolean r0 = r5.exists()
            if (r0 == 0) goto L_0x01d4
            r5.delete()
            return
        L_0x0178:
            r0 = move-exception
            goto L_0x01be
        L_0x017a:
            r0 = move-exception
            r4 = r7
            goto L_0x018d
        L_0x017d:
            r0 = move-exception
            goto L_0x018d
        L_0x017f:
            r0 = move-exception
            r6 = r4
            goto L_0x0188
        L_0x0182:
            r0 = move-exception
            r6 = r4
            goto L_0x018d
        L_0x0185:
            r0 = move-exception
            r5 = r4
            r6 = r5
        L_0x0188:
            r7 = r6
            goto L_0x01be
        L_0x018a:
            r0 = move-exception
            r5 = r4
            r6 = r5
        L_0x018d:
            java.lang.String r1 = "paas.blutils"
            java.lang.String r2 = "GenID"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01bc }
            java.lang.String r7 = "init error: "
            r3.<init>(r7)     // Catch:{ all -> 0x01bc }
            java.lang.String r0 = r0.getLocalizedMessage()     // Catch:{ all -> 0x01bc }
            r3.append(r0)     // Catch:{ all -> 0x01bc }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x01bc }
            com.amap.bundle.logs.AMapLog.error(r1, r2, r0)     // Catch:{ all -> 0x01bc }
            if (r4 == 0) goto L_0x01ab
            r4.release()     // Catch:{ IOException -> 0x01ab }
        L_0x01ab:
            if (r6 == 0) goto L_0x01b0
            r6.close()     // Catch:{ IOException -> 0x01b0 }
        L_0x01b0:
            if (r5 == 0) goto L_0x01d4
            boolean r0 = r5.exists()
            if (r0 == 0) goto L_0x01d4
            r5.delete()
            return
        L_0x01bc:
            r0 = move-exception
            r7 = r4
        L_0x01be:
            if (r7 == 0) goto L_0x01c3
            r7.release()     // Catch:{ IOException -> 0x01c3 }
        L_0x01c3:
            if (r6 == 0) goto L_0x01c8
            r6.close()     // Catch:{ IOException -> 0x01c8 }
        L_0x01c8:
            if (r5 == 0) goto L_0x01d3
            boolean r1 = r5.exists()
            if (r1 == 0) goto L_0x01d3
            r5.delete()
        L_0x01d3:
            throw r0
        L_0x01d4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.km.<init>():void");
    }

    public static km a() {
        if (b == null) {
            synchronized (km.class) {
                try {
                    if (b == null) {
                        b = new km();
                    }
                }
            }
        }
        return b;
    }

    public static void a(Context context) {
        if (context != null) {
            context.getSharedPreferences("GenID", 0);
        }
    }

    private static String b(Context context) {
        String str;
        try {
            str = Secure.getString(context.getContentResolver(), "android_id");
            try {
                if (TextUtils.isEmpty(str) || str.equalsIgnoreCase("a5f5faddde9e9f02") || str.equalsIgnoreCase("8e17f7422b35fbea") || str.equalsIgnoreCase("0000000000000000")) {
                    return "";
                }
            } catch (Throwable unused) {
            }
        } catch (Throwable unused2) {
            str = "";
        }
        return str;
    }

    private static String a(String str, int i) {
        if (TextUtils.isEmpty(str) || i <= 0) {
            return str;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        if (i > length) {
            i %= length;
        }
        int i2 = length - 1;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = i2 - i; i4 < i5; i5--) {
            char c = charArray[i4];
            charArray[i4] = charArray[i5];
            charArray[i5] = c;
            i4++;
        }
        int i6 = length - i;
        for (int i7 = i2; i6 < i7; i7--) {
            char c2 = charArray[i6];
            charArray[i6] = charArray[i7];
            charArray[i7] = c2;
            i6++;
        }
        while (i3 < i2) {
            char c3 = charArray[i3];
            charArray[i3] = charArray[i2];
            charArray[i2] = c3;
            i3++;
            i2--;
        }
        return new String(charArray);
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "00";
        }
        int i = 0;
        for (char c : str.toCharArray()) {
            i += c;
        }
        return b(Integer.toHexString(Math.abs(i / 255)), 2);
    }

    private static String b(String str, int i) {
        if (str == null || str.length() == i) {
            return str;
        }
        int length = str.length();
        if (length > i) {
            return str.substring(length - i);
        }
        if (length >= i) {
            return str;
        }
        int i2 = i - length;
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append('0');
        }
        sb.append(str);
        return sb.toString();
    }
}
