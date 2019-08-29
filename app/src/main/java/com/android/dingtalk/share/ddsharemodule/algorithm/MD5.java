package com.android.dingtalk.share.ddsharemodule.algorithm;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class MD5 {
    public static final String getMessageDigest(byte[] bArr) {
        byte[] digest;
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            char[] cArr2 = new char[(r1 * 2)];
            int i = 0;
            for (byte b : instance.digest()) {
                int i2 = i + 1;
                cArr2[i] = cArr[(b >>> 4) & 15];
                i = i2 + 1;
                cArr2[i2] = cArr[b & 15];
            }
            return new String(cArr2);
        } catch (Exception unused) {
            return null;
        }
    }

    public static final byte[] getRawDigest(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (Exception unused) {
            return null;
        }
    }

    public static final String getMD5(FileInputStream fileInputStream, int i, int i2, int i3) {
        if (fileInputStream == null || i <= 0 || i2 < 0 || i3 <= 0) {
            return null;
        }
        long j = (long) i2;
        try {
            if (fileInputStream.skip(j) < j) {
                return null;
            }
            MessageDigest instance = MessageDigest.getInstance("MD5");
            StringBuilder sb = new StringBuilder(32);
            byte[] bArr = new byte[i];
            int i4 = 0;
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1 || i4 >= i3) {
                    byte[] digest = instance.digest();
                } else {
                    int i5 = i4 + read;
                    if (i5 <= i3) {
                        instance.update(bArr, 0, read);
                        i4 = i5;
                    } else {
                        instance.update(bArr, 0, i3 - i4);
                        i4 = i3;
                    }
                }
            }
            byte[] digest2 = instance.digest();
            for (byte b : digest2) {
                sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public static final String getMD5(FileInputStream fileInputStream, int i) {
        int i2;
        if (fileInputStream == null || i <= 0) {
            return null;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            StringBuilder sb = new StringBuilder(32);
            byte[] bArr = new byte[i];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                instance.update(bArr, 0, read);
            }
            byte[] digest = instance.digest();
            for (byte b : digest) {
                sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getMD5(String str) {
        if (str != null) {
            File file = new File(str);
            if (file.exists()) {
                return getMD5(file, 102400);
            }
        }
        return null;
    }

    public static String getMD5(File file) {
        return getMD5(file, 102400);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0030 A[SYNTHETIC, Splitter:B:22:0x0030] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0037 A[SYNTHETIC, Splitter:B:30:0x0037] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getMD5(java.io.File r6, int r7) {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x003b
            if (r7 <= 0) goto L_0x003b
            boolean r1 = r6.exists()
            if (r1 == 0) goto L_0x003b
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0034, all -> 0x002c }
            r1.<init>(r6)     // Catch:{ Exception -> 0x0034, all -> 0x002c }
            long r2 = (long) r7
            long r4 = r6.length()     // Catch:{ Exception -> 0x0035, all -> 0x002a }
            int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r7 > 0) goto L_0x001a
            goto L_0x001e
        L_0x001a:
            long r2 = r6.length()     // Catch:{ Exception -> 0x0035, all -> 0x002a }
        L_0x001e:
            int r6 = (int) r2     // Catch:{ Exception -> 0x0035, all -> 0x002a }
            java.lang.String r6 = getMD5(r1, r6)     // Catch:{ Exception -> 0x0035, all -> 0x002a }
            r1.close()     // Catch:{ Exception -> 0x0035, all -> 0x002a }
            r1.close()     // Catch:{ IOException -> 0x0029 }
        L_0x0029:
            return r6
        L_0x002a:
            r6 = move-exception
            goto L_0x002e
        L_0x002c:
            r6 = move-exception
            r1 = r0
        L_0x002e:
            if (r1 == 0) goto L_0x0033
            r1.close()     // Catch:{ IOException -> 0x0033 }
        L_0x0033:
            throw r6
        L_0x0034:
            r1 = r0
        L_0x0035:
            if (r1 == 0) goto L_0x003a
            r1.close()     // Catch:{ IOException -> 0x003a }
        L_0x003a:
            return r0
        L_0x003b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.dingtalk.share.ddsharemodule.algorithm.MD5.getMD5(java.io.File, int):java.lang.String");
    }

    public static String getMD5(String str, int i, int i2) {
        if (str != null) {
            File file = new File(str);
            if (file.exists()) {
                return getMD5(file, i, i2);
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0026 A[SYNTHETIC, Splitter:B:19:0x0026] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x002d A[SYNTHETIC, Splitter:B:27:0x002d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getMD5(java.io.File r2, int r3, int r4) {
        /*
            r0 = 0
            if (r2 == 0) goto L_0x0031
            boolean r1 = r2.exists()
            if (r1 == 0) goto L_0x0031
            if (r3 < 0) goto L_0x0031
            if (r4 <= 0) goto L_0x0031
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x002a, all -> 0x0023 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x002a, all -> 0x0023 }
            r2 = 102400(0x19000, float:1.43493E-40)
            java.lang.String r2 = getMD5(r1, r2, r3, r4)     // Catch:{ Exception -> 0x002b, all -> 0x0020 }
            r1.close()     // Catch:{ Exception -> 0x002b, all -> 0x0020 }
            r1.close()     // Catch:{ IOException -> 0x001f }
        L_0x001f:
            return r2
        L_0x0020:
            r2 = move-exception
            r0 = r1
            goto L_0x0024
        L_0x0023:
            r2 = move-exception
        L_0x0024:
            if (r0 == 0) goto L_0x0029
            r0.close()     // Catch:{ IOException -> 0x0029 }
        L_0x0029:
            throw r2
        L_0x002a:
            r1 = r0
        L_0x002b:
            if (r1 == 0) goto L_0x0030
            r1.close()     // Catch:{ IOException -> 0x0030 }
        L_0x0030:
            return r0
        L_0x0031:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.dingtalk.share.ddsharemodule.algorithm.MD5.getMD5(java.io.File, int, int):java.lang.String");
    }
}
