package com.uc.crashsdk.a;

import android.support.v4.media.TransportMediator;
import com.alipay.android.phone.mobilesdk.socketcraft.api.WSContextConstant;
import com.autonavi.indoor.constant.MessageCode;
import com.autonavi.jni.ae.pos.SpeedState;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;

/* compiled from: ProGuard */
public final class d {
    public static final int[] a = {TransportMediator.KEYCODE_MEDIA_PLAY, 147, 115, FavoritesPointFragment.REQUEST_HOME, 101, 198, MessageCode.MSG_ONLINE_BUILDING_LOCATED, 134};
    public static final int[] b = {125, WSContextConstant.HANDSHAKE_RECEIVE_SIZE, 233, 226, 129, DumpSegment.ANDROID_ROOT_JNI_MONITOR, 151, SpeedState.ENO_DEFINE};
    public static final int[] c = {238, WSContextConstant.HANDSHAKE_RECEIVE_SIZE, 233, 179, 129, DumpSegment.ANDROID_ROOT_JNI_MONITOR, 151, 167};

    public static String a(String str) {
        FileInputStream fileInputStream;
        File file = new File(str);
        if (!file.exists()) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[((int) file.length())];
                fileInputStream.read(bArr);
                fileInputStream.close();
                byte[] a2 = a(bArr, a);
                if (a2 == null || a2.length <= 0) {
                    b.a((Closeable) fileInputStream);
                    return null;
                }
                int length = a2.length;
                String str2 = a2[length + -1] == 10 ? new String(a2, 0, length - 1) : new String(a2);
                b.a((Closeable) fileInputStream);
                return str2;
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            fileInputStream = null;
            try {
                a.a(e, false);
                b.a((Closeable) fileInputStream);
                return null;
            } catch (Throwable th) {
                th = th;
                b.a((Closeable) fileInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
            b.a((Closeable) fileInputStream);
            throw th;
        }
    }

    private static byte[] a(File file) {
        FileInputStream fileInputStream;
        byte[] bArr;
        FileInputStream fileInputStream2 = null;
        if (!file.exists()) {
            return null;
        }
        try {
            bArr = new byte[((int) file.length())];
            fileInputStream = new FileInputStream(file);
            try {
                fileInputStream.read(bArr);
                b.a((Closeable) fileInputStream);
            } catch (Throwable th) {
                th = th;
                try {
                    a.a(th, false);
                    b.a((Closeable) fileInputStream);
                    bArr = null;
                    return bArr;
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream2 = fileInputStream;
                    b.a((Closeable) fileInputStream2);
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            b.a((Closeable) fileInputStream2);
            throw th;
        }
        return bArr;
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r5v3, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r2v5, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r2v6, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v2, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r5v7 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r5v8, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r5v11 */
    /* JADX WARNING: type inference failed for: r5v12 */
    /* JADX WARNING: type inference failed for: r5v13 */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004a A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:68:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r8, java.lang.String r9, boolean r10) {
        /*
            r2 = 0
            r3 = 1
            r4 = 0
            if (r10 != 0) goto L_0x0006
        L_0x0005:
            return r8
        L_0x0006:
            boolean r0 = com.uc.crashsdk.a.h.a(r8)
            if (r0 != 0) goto L_0x0005
            java.io.File r7 = new java.io.File
            r7.<init>(r8)
            boolean r0 = r7.exists()
            if (r0 == 0) goto L_0x0005
            long r0 = r7.length()
            r5 = 3145728(0x300000, double:1.554196E-317)
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 > 0) goto L_0x0005
            byte[] r6 = a(r7)
            if (r6 == 0) goto L_0x0005
            int r0 = r6.length
            if (r0 <= 0) goto L_0x0005
            if (r10 == 0) goto L_0x00cd
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x0092, all -> 0x00a0 }
            r5.<init>()     // Catch:{ Throwable -> 0x0092, all -> 0x00a0 }
            java.util.zip.GZIPOutputStream r1 = new java.util.zip.GZIPOutputStream     // Catch:{ Throwable -> 0x00c3, all -> 0x00ba }
            r1.<init>(r5)     // Catch:{ Throwable -> 0x00c3, all -> 0x00ba }
            r1.write(r6)     // Catch:{ Throwable -> 0x00c7, all -> 0x00bc }
            r5.flush()     // Catch:{ Throwable -> 0x00c7, all -> 0x00bc }
            com.uc.crashsdk.a.b.a(r5)
            com.uc.crashsdk.a.b.a(r1)
        L_0x0043:
            byte[] r0 = r5.toByteArray()     // Catch:{ Throwable -> 0x00a9 }
            r1 = r3
        L_0x0048:
            if (r1 == 0) goto L_0x0005
            if (r0 == 0) goto L_0x0005
            int r1 = r0.length
            if (r1 <= 0) goto L_0x0005
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r8)
            java.lang.StringBuilder r1 = r1.append(r9)
            java.lang.String r1 = r1.toString()
            r5 = r0
            r0 = r3
        L_0x0062:
            if (r0 == 0) goto L_0x0005
            java.lang.String r0 = r7.getName()
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00ca
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r2 = ".tmp"
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            r2 = r3
        L_0x0082:
            java.io.File r6 = new java.io.File
            r6.<init>(r0)
            boolean r0 = com.uc.crashsdk.a.b.a(r6, r5)
            if (r0 != 0) goto L_0x00b0
        L_0x008d:
            if (r4 == 0) goto L_0x0005
            r8 = r1
            goto L_0x0005
        L_0x0092:
            r0 = move-exception
            r1 = r2
        L_0x0094:
            r5 = 0
            com.uc.crashsdk.a.a.a(r0, r5)     // Catch:{ all -> 0x00bf }
            com.uc.crashsdk.a.b.a(r2)
            com.uc.crashsdk.a.b.a(r1)
            r5 = r2
            goto L_0x0043
        L_0x00a0:
            r0 = move-exception
            r5 = r2
        L_0x00a2:
            com.uc.crashsdk.a.b.a(r5)
            com.uc.crashsdk.a.b.a(r2)
            throw r0
        L_0x00a9:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r4)
            r1 = r4
            r0 = r6
            goto L_0x0048
        L_0x00b0:
            if (r2 == 0) goto L_0x00b8
            r7.delete()
            r6.renameTo(r7)
        L_0x00b8:
            r4 = r3
            goto L_0x008d
        L_0x00ba:
            r0 = move-exception
            goto L_0x00a2
        L_0x00bc:
            r0 = move-exception
            r2 = r1
            goto L_0x00a2
        L_0x00bf:
            r0 = move-exception
            r5 = r2
            r2 = r1
            goto L_0x00a2
        L_0x00c3:
            r0 = move-exception
            r1 = r2
            r2 = r5
            goto L_0x0094
        L_0x00c7:
            r0 = move-exception
            r2 = r5
            goto L_0x0094
        L_0x00ca:
            r0 = r1
            r2 = r4
            goto L_0x0082
        L_0x00cd:
            r0 = r4
            r1 = r8
            r5 = r6
            goto L_0x0062
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.d.a(java.lang.String, java.lang.String, boolean):java.lang.String");
    }

    private static byte[] a(byte[] bArr, int[] iArr) {
        if (bArr.length + 0 < 2 || iArr == null || iArr.length != 8) {
            return null;
        }
        int length = (bArr.length - 2) + 0;
        try {
            byte[] bArr2 = new byte[length];
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                byte b2 = (byte) (bArr[i2 + 0] ^ iArr[i2 % 8]);
                bArr2[i2] = b2;
                i = (byte) (i ^ b2);
            }
            if (bArr[length + 0] == ((byte) ((iArr[0] ^ i) & 255)) && bArr[length + 1 + 0] == ((byte) ((iArr[1] ^ i) & 255))) {
                return bArr2;
            }
            return null;
        } catch (Exception e) {
            a.a(e, false);
            return null;
        }
    }

    private static byte[] b(byte[] bArr, int[] iArr) {
        byte[] bArr2 = null;
        if (!(bArr == null || iArr == null || iArr.length != 8)) {
            int length = bArr.length;
            try {
                bArr2 = new byte[(length + 2)];
                byte b2 = 0;
                for (int i = 0; i < length; i++) {
                    byte b3 = bArr[i];
                    bArr2[i] = (byte) (iArr[i % 8] ^ b3);
                    b2 = (byte) (b2 ^ b3);
                }
                bArr2[length] = (byte) (iArr[0] ^ b2);
                bArr2[length + 1] = (byte) (iArr[1] ^ b2);
            } catch (Exception e) {
                a.a(e, false);
            }
        }
        return bArr2;
    }

    public static boolean a(String str, String str2) {
        boolean z;
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            fileOutputStream = new FileOutputStream(file);
        } catch (Throwable th) {
            a.a(th, false);
        }
        if (fileOutputStream == null) {
            return false;
        }
        byte[] b2 = b(str2.getBytes(), a);
        if (b2 == null) {
            try {
                fileOutputStream.close();
                return false;
            } catch (Throwable th2) {
                a.a(th2, false);
                return false;
            }
        } else {
            try {
                fileOutputStream.write(b2);
                z = true;
                try {
                    fileOutputStream.close();
                } catch (Throwable th3) {
                    a.a(th3, false);
                }
            } catch (Throwable th4) {
                a.a(th4, false);
                z = false;
            }
            return z;
        }
    }
}
