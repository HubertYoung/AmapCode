package com.alipay.mobile.quinox.bundle.tools;

import android.util.SparseArray;
import com.alipay.mobile.quinox.log.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class BundleResHelper {
    private static final int BASE_PACKAGE_ID_ZONE = 27;
    public static final int MAX_PACKAGE_ID = (((TYPE_ID_OFFSETS.length * 101) + 27) - 1);
    private static final int MAX_PACKAGE_ID_ZONE = 127;
    public static final int MIN_PACKAGE_ID = 27;
    private static final short PACKAGE_CHUNK_TYPE = 512;
    private static final int PKG_ID_COUNT_ZONE = 101;
    private static final String TAG = "BundleResHelper";
    public static final int[] TYPE_ID_OFFSETS = {0, 32, 64, 96, 128, 160, 192, 224};
    public static final int TYPE_ID_OFFSET_DISTANCE = 32;

    public static class Result {
        public final int packageId;
        public final int typeIdOffset;

        public Result(int i, int i2) {
            this.packageId = i;
            this.typeIdOffset = i2;
        }
    }

    public static int getPackageId(int i) {
        return i >>> 24;
    }

    public static int getResId(int i, int i2, int i3) {
        return (i << 24) | (i2 << 16) | i3;
    }

    public static Result calculatePkgIdAndTypeOffset(int i) {
        int i2;
        if (i < 27 || i > MAX_PACKAGE_ID) {
            StringBuilder sb = new StringBuilder("packageId only can be [27-");
            sb.append(MAX_PACKAGE_ID);
            sb.append("]");
            throw new IllegalArgumentException(sb.toString());
        }
        if (i <= 127) {
            i2 = 0;
        } else {
            int i3 = i - 27;
            int i4 = 27 + (i3 % 101);
            i2 = TYPE_ID_OFFSETS[i3 / 101];
            i = i4;
        }
        return new Result(i, i2);
    }

    public static int calculateRealPackageId(int i) {
        return i <= 127 ? i : ((i - 27) % 101) + 27;
    }

    public static int calculateBundlePackageId(int i, int i2) {
        int i3 = -1;
        for (int i4 = 0; i4 < TYPE_ID_OFFSETS.length; i4++) {
            if (i2 == TYPE_ID_OFFSETS[i4]) {
                i3 = i4;
            }
        }
        return i3 < 0 ? i : i + (i3 * 101);
    }

    public static int getTypeIdOffset(int i) {
        int i2 = (16711680 & i) >>> 16;
        int i3 = 0;
        while (i3 < TYPE_ID_OFFSETS.length) {
            if (i2 <= TYPE_ID_OFFSETS[i3]) {
                if (i3 == 0) {
                    return TYPE_ID_OFFSETS[0];
                }
                return TYPE_ID_OFFSETS[i3 - 1];
            } else if (i3 == TYPE_ID_OFFSETS.length - 1 && i2 > TYPE_ID_OFFSETS[i3]) {
                return TYPE_ID_OFFSETS[i3];
            } else {
                i3++;
            }
        }
        StringBuilder sb = new StringBuilder("getTypeIdOffset failed! resId:0x");
        sb.append(Integer.toHexString(i));
        sb.append(" TYPE_ID_OFFSETS:");
        sb.append(Arrays.toString(TYPE_ID_OFFSETS));
        Log.w((String) TAG, sb.toString());
        return 0;
    }

    public static String readPackageNameFromArsc(String str, int i) throws IOException {
        if (i < 0) {
            return null;
        }
        SparseArray<String> readPackageFromArsc = readPackageFromArsc(str);
        if (readPackageFromArsc != null) {
            return readPackageFromArsc.get(i);
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00f1 A[SYNTHETIC, Splitter:B:40:0x00f1] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.util.SparseArray<java.lang.String> readPackageFromArsc(java.lang.String r15) throws java.io.IOException {
        /*
            r0 = 0
            java.util.zip.ZipFile r1 = new java.util.zip.ZipFile     // Catch:{ all -> 0x0108 }
            r1.<init>(r15)     // Catch:{ all -> 0x0108 }
            java.lang.String r2 = "resources.arsc"
            java.util.zip.ZipEntry r2 = r1.getEntry(r2)     // Catch:{ all -> 0x0106 }
            if (r2 == 0) goto L_0x00f5
            java.io.InputStream r15 = r1.getInputStream(r2)     // Catch:{ all -> 0x00eb }
            r2 = 2
            forceSkip(r15, r2)     // Catch:{ all -> 0x00e9 }
            forceSkip(r15, r2)     // Catch:{ all -> 0x00e9 }
            r4 = 4
            forceSkip(r15, r4)     // Catch:{ all -> 0x00e9 }
            r4 = 256(0x100, float:3.59E-43)
            byte[] r5 = new byte[r4]     // Catch:{ all -> 0x00e9 }
            r6 = 4
            r7 = 0
            forceRead(r15, r5, r7, r6)     // Catch:{ all -> 0x00e9 }
            java.nio.ByteBuffer r8 = java.nio.ByteBuffer.wrap(r5, r7, r6)     // Catch:{ all -> 0x00e9 }
            java.nio.ByteOrder r9 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ all -> 0x00e9 }
            java.nio.ByteBuffer r8 = r8.order(r9)     // Catch:{ all -> 0x00e9 }
            int r8 = r8.getInt()     // Catch:{ all -> 0x00e9 }
            if (r8 != 0) goto L_0x0042
            if (r15 == 0) goto L_0x003e
            r15.close()     // Catch:{ all -> 0x0106 }
        L_0x003e:
            r1.close()
            return r0
        L_0x0042:
            r8 = 2
            forceRead(r15, r5, r7, r8)     // Catch:{ all -> 0x00e9 }
            java.nio.ByteBuffer r9 = java.nio.ByteBuffer.wrap(r5, r7, r8)     // Catch:{ all -> 0x00e9 }
            java.nio.ByteOrder r10 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ all -> 0x00e9 }
            java.nio.ByteBuffer r9 = r9.order(r10)     // Catch:{ all -> 0x00e9 }
            short r9 = r9.getShort()     // Catch:{ all -> 0x00e9 }
        L_0x0054:
            r10 = 512(0x200, float:7.175E-43)
            if (r9 != r10) goto L_0x00b0
            forceSkip(r15, r2)     // Catch:{ all -> 0x00e9 }
            forceRead(r15, r5, r7, r6)     // Catch:{ all -> 0x00e9 }
            java.nio.ByteBuffer r9 = java.nio.ByteBuffer.wrap(r5, r7, r6)     // Catch:{ all -> 0x00e9 }
            java.nio.ByteOrder r10 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ all -> 0x00e9 }
            java.nio.ByteBuffer r9 = r9.order(r10)     // Catch:{ all -> 0x00e9 }
            int r9 = r9.getInt()     // Catch:{ all -> 0x00e9 }
            forceRead(r15, r5, r7, r6)     // Catch:{ all -> 0x00e9 }
            java.nio.ByteBuffer r10 = java.nio.ByteBuffer.wrap(r5, r7, r6)     // Catch:{ all -> 0x00e9 }
            java.nio.ByteOrder r11 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ all -> 0x00e9 }
            java.nio.ByteBuffer r10 = r10.order(r11)     // Catch:{ all -> 0x00e9 }
            int r10 = r10.getInt()     // Catch:{ all -> 0x00e9 }
            forceRead(r15, r5, r7, r4)     // Catch:{ all -> 0x00e9 }
            java.nio.ByteBuffer r11 = java.nio.ByteBuffer.wrap(r5)     // Catch:{ all -> 0x00e9 }
            java.nio.ByteOrder r12 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ all -> 0x00e9 }
            java.nio.ByteBuffer r11 = r11.order(r12)     // Catch:{ all -> 0x00e9 }
            java.lang.String r12 = new java.lang.String     // Catch:{ all -> 0x00e9 }
            byte[] r11 = r11.array()     // Catch:{ all -> 0x00e9 }
            java.lang.String r13 = "UTF-16LE"
            java.nio.charset.Charset r13 = java.nio.charset.Charset.forName(r13)     // Catch:{ all -> 0x00e9 }
            r12.<init>(r11, r13)     // Catch:{ all -> 0x00e9 }
            java.lang.String r11 = r12.trim()     // Catch:{ all -> 0x00e9 }
            if (r0 != 0) goto L_0x00a4
            android.util.SparseArray r0 = new android.util.SparseArray     // Catch:{ all -> 0x00e9 }
            r0.<init>()     // Catch:{ all -> 0x00e9 }
        L_0x00a4:
            r0.put(r10, r11)     // Catch:{ all -> 0x00e9 }
            int r9 = r9 + -8
            int r9 = r9 - r6
            int r9 = r9 - r4
            long r9 = (long) r9     // Catch:{ all -> 0x00e9 }
            forceSkip(r15, r9)     // Catch:{ all -> 0x00e9 }
            goto L_0x00ca
        L_0x00b0:
            forceSkip(r15, r2)     // Catch:{ all -> 0x00e9 }
            forceRead(r15, r5, r7, r6)     // Catch:{ all -> 0x00e9 }
            java.nio.ByteBuffer r9 = java.nio.ByteBuffer.wrap(r5, r7, r6)     // Catch:{ all -> 0x00e9 }
            java.nio.ByteOrder r10 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ all -> 0x00e9 }
            java.nio.ByteBuffer r9 = r9.order(r10)     // Catch:{ all -> 0x00e9 }
            int r9 = r9.getInt()     // Catch:{ all -> 0x00e9 }
            int r9 = r9 + -8
            long r9 = (long) r9     // Catch:{ all -> 0x00e9 }
            forceSkip(r15, r9)     // Catch:{ all -> 0x00e9 }
        L_0x00ca:
            int r9 = r15.read(r5, r7, r8)     // Catch:{ all -> 0x00e9 }
            if (r9 < r8) goto L_0x00e0
            java.nio.ByteBuffer r9 = java.nio.ByteBuffer.wrap(r5, r7, r8)     // Catch:{ all -> 0x00e9 }
            java.nio.ByteOrder r10 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ all -> 0x00e9 }
            java.nio.ByteBuffer r9 = r9.order(r10)     // Catch:{ all -> 0x00e9 }
            short r9 = r9.getShort()     // Catch:{ all -> 0x00e9 }
            goto L_0x0054
        L_0x00e0:
            if (r15 == 0) goto L_0x00e5
            r15.close()     // Catch:{ all -> 0x0106 }
        L_0x00e5:
            r1.close()
            return r0
        L_0x00e9:
            r0 = move-exception
            goto L_0x00ef
        L_0x00eb:
            r15 = move-exception
            r14 = r0
            r0 = r15
            r15 = r14
        L_0x00ef:
            if (r15 == 0) goto L_0x00f4
            r15.close()     // Catch:{ all -> 0x0106 }
        L_0x00f4:
            throw r0     // Catch:{ all -> 0x0106 }
        L_0x00f5:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x0106 }
            java.lang.String r2 = "resources.arsc not found in "
            java.lang.String r15 = java.lang.String.valueOf(r15)     // Catch:{ all -> 0x0106 }
            java.lang.String r15 = r2.concat(r15)     // Catch:{ all -> 0x0106 }
            r0.<init>(r15)     // Catch:{ all -> 0x0106 }
            throw r0     // Catch:{ all -> 0x0106 }
        L_0x0106:
            r15 = move-exception
            goto L_0x010a
        L_0x0108:
            r15 = move-exception
            r1 = r0
        L_0x010a:
            if (r1 == 0) goto L_0x010f
            r1.close()
        L_0x010f:
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.bundle.tools.BundleResHelper.readPackageFromArsc(java.lang.String):android.util.SparseArray");
    }

    private static void forceSkip(InputStream inputStream, long j) throws IOException {
        long skip = inputStream.skip(j);
        if (skip != j) {
            StringBuilder sb = new StringBuilder("want skip ");
            sb.append(j);
            sb.append(" bytes, but skipped ");
            sb.append(skip);
            sb.append(" bytes at fact.");
            throw new IOException(sb.toString());
        }
    }

    private static void forceRead(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        int read = inputStream.read(bArr, i, i2);
        if (read != i2) {
            StringBuilder sb = new StringBuilder("want read ");
            sb.append(i2);
            sb.append(" bytes, but read ");
            sb.append(read);
            sb.append(" bytes at fact.");
            throw new IOException(sb.toString());
        }
    }
}
