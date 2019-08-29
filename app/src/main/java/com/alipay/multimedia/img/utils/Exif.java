package com.alipay.multimedia.img.utils;

import android.media.ExifInterface;
import android.os.Build.VERSION;
import com.alipay.multimedia.img.base.StaticOptions;
import com.alipay.multimedia.io.FileUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class Exif {
    private static final String TAG = "CameraExif";
    private static Boolean canUseRandomAccessFileExif = null;

    public static int getRotation(byte[] jpeg) {
        int orientation = getOrientation(jpeg);
        switch (orientation) {
            case 1:
                return 0;
            case 3:
                return 180;
            case 6:
                return 90;
            case 8:
                return 270;
            default:
                return orientation;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0062, code lost:
        if (r2 <= 8) goto L_0x00cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0064, code lost:
        r8 = pack(r15, r5, 4, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006b, code lost:
        if (r8 == 1229531648) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0070, code lost:
        if (r8 == 1296891946) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0072, code lost:
        com.alipay.multimedia.img.utils.LogUtils.e(TAG, "Invalid byte order");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0080, code lost:
        if (r8 != 1229531648) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0082, code lost:
        r0 = pack(r15, r5 + 4, 4, r3) + 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x008c, code lost:
        if (r0 < 10) goto L_0x0090;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x008e, code lost:
        if (r0 <= r2) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0090, code lost:
        com.alipay.multimedia.img.utils.LogUtils.e(TAG, "Invalid offset");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0099, code lost:
        r3 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x009b, code lost:
        r5 = r5 + r0;
        r2 = r2 - r0;
        r0 = pack(r15, r5 - 2, 2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a4, code lost:
        r1 = r0;
        r0 = r1 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00a6, code lost:
        if (r1 <= 0) goto L_0x00cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00aa, code lost:
        if (r2 < 12) goto L_0x00cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b2, code lost:
        if (pack(r15, r5, 2, r3) != 274) goto L_0x00c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00b4, code lost:
        r7 = pack(r15, r5 + 8, 2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00ba, code lost:
        switch(r7) {
            case 1: goto L_0x00c6;
            case 2: goto L_0x00c6;
            case 3: goto L_0x00c6;
            case 4: goto L_0x00c6;
            case 5: goto L_0x00c6;
            case 6: goto L_0x00c6;
            case 7: goto L_0x00c6;
            case 8: goto L_0x00c6;
            default: goto L_0x00bd;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00bd, code lost:
        com.alipay.multimedia.img.utils.LogUtils.i(TAG, "Unsupported rotation");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00c9, code lost:
        r5 = r5 + 12;
        r2 = r2 - 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00cf, code lost:
        com.alipay.multimedia.img.utils.LogUtils.i(TAG, "Orientation not found");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:?, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:?, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getOrientation(byte[] r15) {
        /*
            r14 = 8
            r3 = 1
            r13 = 4
            r12 = 2
            r9 = 0
            if (r15 != 0) goto L_0x0009
        L_0x0008:
            return r9
        L_0x0009:
            r5 = 0
            r2 = 0
        L_0x000b:
            int r10 = r5 + 3
            int r11 = r15.length
            if (r10 >= r11) goto L_0x0062
            int r6 = r5 + 1
            byte r10 = r15[r5]
            r10 = r10 & 255(0xff, float:3.57E-43)
            r11 = 255(0xff, float:3.57E-43)
            if (r10 != r11) goto L_0x00db
            byte r10 = r15[r6]
            r4 = r10 & 255(0xff, float:3.57E-43)
            r10 = 255(0xff, float:3.57E-43)
            if (r4 == r10) goto L_0x00d8
            int r5 = r6 + 1
            r10 = 216(0xd8, float:3.03E-43)
            if (r4 == r10) goto L_0x000b
            if (r4 == r3) goto L_0x000b
            r10 = 217(0xd9, float:3.04E-43)
            if (r4 == r10) goto L_0x0062
            r10 = 218(0xda, float:3.05E-43)
            if (r4 == r10) goto L_0x0062
            int r2 = pack(r15, r5, r12, r9)
            if (r2 < r12) goto L_0x003d
            int r10 = r5 + r2
            int r11 = r15.length
            if (r10 <= r11) goto L_0x0045
        L_0x003d:
            java.lang.String r10 = "CameraExif"
            java.lang.String r11 = "Invalid length"
            com.alipay.multimedia.img.utils.LogUtils.e(r10, r11)
            goto L_0x0008
        L_0x0045:
            r10 = 225(0xe1, float:3.15E-43)
            if (r4 != r10) goto L_0x007a
            if (r2 < r14) goto L_0x007a
            int r10 = r5 + 2
            int r10 = pack(r15, r10, r13, r9)
            r11 = 1165519206(0x45786966, float:3974.5874)
            if (r10 != r11) goto L_0x007a
            int r10 = r5 + 6
            int r10 = pack(r15, r10, r12, r9)
            if (r10 != 0) goto L_0x007a
            int r5 = r5 + 8
            int r2 = r2 + -8
        L_0x0062:
            if (r2 <= r14) goto L_0x00cf
            int r8 = pack(r15, r5, r13, r9)
            r10 = 1229531648(0x49492a00, float:823968.0)
            if (r8 == r10) goto L_0x007d
            r10 = 1296891946(0x4d4d002a, float:2.14958752E8)
            if (r8 == r10) goto L_0x007d
            java.lang.String r10 = "CameraExif"
            java.lang.String r11 = "Invalid byte order"
            com.alipay.multimedia.img.utils.LogUtils.e(r10, r11)
            goto L_0x0008
        L_0x007a:
            int r5 = r5 + r2
            r2 = 0
            goto L_0x000b
        L_0x007d:
            r10 = 1229531648(0x49492a00, float:823968.0)
            if (r8 != r10) goto L_0x0099
        L_0x0082:
            int r10 = r5 + 4
            int r10 = pack(r15, r10, r13, r3)
            int r0 = r10 + 2
            r10 = 10
            if (r0 < r10) goto L_0x0090
            if (r0 <= r2) goto L_0x009b
        L_0x0090:
            java.lang.String r10 = "CameraExif"
            java.lang.String r11 = "Invalid offset"
            com.alipay.multimedia.img.utils.LogUtils.e(r10, r11)
            goto L_0x0008
        L_0x0099:
            r3 = r9
            goto L_0x0082
        L_0x009b:
            int r5 = r5 + r0
            int r2 = r2 - r0
            int r10 = r5 + -2
            int r0 = pack(r15, r10, r12, r3)
            r1 = r0
        L_0x00a4:
            int r0 = r1 + -1
            if (r1 <= 0) goto L_0x00cf
            r10 = 12
            if (r2 < r10) goto L_0x00cf
            int r10 = pack(r15, r5, r12, r3)
            r11 = 274(0x112, float:3.84E-43)
            if (r10 != r11) goto L_0x00c9
            int r10 = r5 + 8
            int r7 = pack(r15, r10, r12, r3)
            switch(r7) {
                case 1: goto L_0x00c6;
                case 2: goto L_0x00c6;
                case 3: goto L_0x00c6;
                case 4: goto L_0x00c6;
                case 5: goto L_0x00c6;
                case 6: goto L_0x00c6;
                case 7: goto L_0x00c6;
                case 8: goto L_0x00c6;
                default: goto L_0x00bd;
            }
        L_0x00bd:
            java.lang.String r10 = "CameraExif"
            java.lang.String r11 = "Unsupported rotation"
            com.alipay.multimedia.img.utils.LogUtils.i(r10, r11)
            goto L_0x0008
        L_0x00c6:
            r9 = r7
            goto L_0x0008
        L_0x00c9:
            int r5 = r5 + 12
            int r2 = r2 + -12
            r1 = r0
            goto L_0x00a4
        L_0x00cf:
            java.lang.String r10 = "CameraExif"
            java.lang.String r11 = "Orientation not found"
            com.alipay.multimedia.img.utils.LogUtils.i(r10, r11)
            goto L_0x0008
        L_0x00d8:
            r5 = r6
            goto L_0x000b
        L_0x00db:
            r5 = r6
            goto L_0x0062
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.img.utils.Exif.getOrientation(byte[]):int");
    }

    private static int pack(byte[] bytes, int offset, int length, boolean littleEndian) {
        int step = 1;
        if (littleEndian) {
            offset += length - 1;
            step = -1;
        }
        int value = 0;
        while (true) {
            int length2 = length;
            length = length2 - 1;
            if (length2 <= 0) {
                return value;
            }
            value = (value << 8) | (bytes[offset] & 255);
            offset += step;
        }
    }

    public static int getRotation(String path) {
        int orientation = getOrientation(path);
        switch (orientation) {
            case 1:
                return 0;
            case 3:
                return 180;
            case 6:
                return 90;
            case 8:
                return 270;
            default:
                return orientation;
        }
    }

    public static int getOrientation(String path) {
        if (!FileUtils.checkFile(path)) {
            return 1;
        }
        if (!StaticOptions.useRandomAccessFileExif || !isCanUseRandomAccessFileExif()) {
            return getOrientationByExifInterface(path);
        }
        return getOrientationByRandomAccessFile(path);
    }

    public static int getOrientation(FileDescriptor fd) {
        if (fd == null) {
            return 1;
        }
        try {
            if (VERSION.SDK_INT < 24 || ImageFileType.detectImageFileType((InputStream) new FileInputStream(fd)) != 0) {
                return 1;
            }
            return new ExifInterface(fd).getAttributeInt("Orientation", 1);
        } catch (Throwable e) {
            LogUtils.w(TAG, "getOrientation error, fd: " + fd + ", e: " + e);
            return 1;
        }
    }

    private static int getOrientationByExifInterface(String path) {
        int orientation = 1;
        if (!ImageFileType.isJPEG(new File(path))) {
            return orientation;
        }
        try {
            return new ExifInterface(path).getAttributeInt("Orientation", 1);
        } catch (Throwable e) {
            LogUtils.w(TAG, "getOrientationByExifInterface error, path: " + path + ", e: " + e);
            return orientation;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:103:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0059, code lost:
        com.alipay.multimedia.io.IOUtils.closeQuietly(r13);
        r12 = r13;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int getOrientationByRandomAccessFile(java.lang.String r18) {
        /*
            r11 = 1
            r12 = 0
            java.io.RandomAccessFile r13 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0136 }
            java.lang.String r15 = "r"
            r0 = r18
            r13.<init>(r0, r15)     // Catch:{ Exception -> 0x0136 }
            long r16 = r13.length()     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            r0 = r16
            int r5 = (int) r0     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            r9 = 0
            r6 = 0
            r10 = r9
        L_0x0015:
            int r15 = r10 + 3
            if (r15 >= r5) goto L_0x0163
            int r9 = r10 + 1
            byte r15 = getB(r13, r10)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            r15 = r15 & 255(0xff, float:3.57E-43)
            r16 = 255(0xff, float:3.57E-43)
            r0 = r16
            if (r15 != r0) goto L_0x0090
            byte r15 = getB(r13, r9)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            r8 = r15 & 255(0xff, float:3.57E-43)
            r15 = 255(0xff, float:3.57E-43)
            if (r8 == r15) goto L_0x0160
            int r9 = r9 + 1
            r15 = 216(0xd8, float:3.03E-43)
            if (r8 == r15) goto L_0x0160
            r15 = 1
            if (r8 == r15) goto L_0x0160
            r15 = 217(0xd9, float:3.04E-43)
            if (r8 == r15) goto L_0x0090
            r15 = 218(0xda, float:3.05E-43)
            if (r8 == r15) goto L_0x0090
            r15 = 2
            r16 = 0
            r0 = r16
            int r6 = pack(r13, r9, r15, r0)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            r15 = 2
            if (r6 < r15) goto L_0x0052
            int r15 = r9 + r6
            if (r15 <= r5) goto L_0x005f
        L_0x0052:
            java.lang.String r15 = "CameraExif"
            java.lang.String r16 = "Invalid length"
            com.alipay.multimedia.img.utils.LogUtils.e(r15, r16)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            com.alipay.multimedia.io.IOUtils.closeQuietly(r13)
            r15 = 0
            r12 = r13
        L_0x005e:
            return r15
        L_0x005f:
            r15 = 225(0xe1, float:3.15E-43)
            if (r8 != r15) goto L_0x00b4
            r15 = 8
            if (r6 < r15) goto L_0x00b4
            int r15 = r9 + 2
            r16 = 4
            r17 = 0
            r0 = r16
            r1 = r17
            int r15 = pack(r13, r15, r0, r1)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            r16 = 1165519206(0x45786966, float:3974.5874)
            r0 = r16
            if (r15 != r0) goto L_0x00b4
            int r15 = r9 + 6
            r16 = 2
            r17 = 0
            r0 = r16
            r1 = r17
            int r15 = pack(r13, r15, r0, r1)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            if (r15 != 0) goto L_0x00b4
            int r9 = r9 + 8
            int r6 = r6 + -8
        L_0x0090:
            r15 = 8
            if (r6 <= r15) goto L_0x0128
            r15 = 4
            r16 = 0
            r0 = r16
            int r14 = pack(r13, r9, r15, r0)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            r15 = 1229531648(0x49492a00, float:823968.0)
            if (r14 == r15) goto L_0x00b9
            r15 = 1296891946(0x4d4d002a, float:2.14958752E8)
            if (r14 == r15) goto L_0x00b9
            java.lang.String r15 = "CameraExif"
            java.lang.String r16 = "Invalid byte order"
            com.alipay.multimedia.img.utils.LogUtils.e(r15, r16)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            com.alipay.multimedia.io.IOUtils.closeQuietly(r13)
            r15 = 0
            r12 = r13
            goto L_0x005e
        L_0x00b4:
            int r9 = r9 + r6
            r6 = 0
            r10 = r9
            goto L_0x0015
        L_0x00b9:
            r15 = 1229531648(0x49492a00, float:823968.0)
            if (r14 != r15) goto L_0x00de
            r7 = 1
        L_0x00bf:
            int r15 = r9 + 4
            r16 = 4
            r0 = r16
            int r15 = pack(r13, r15, r0, r7)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            int r2 = r15 + 2
            r15 = 10
            if (r2 < r15) goto L_0x00d1
            if (r2 <= r6) goto L_0x00e0
        L_0x00d1:
            java.lang.String r15 = "CameraExif"
            java.lang.String r16 = "Invalid offset"
            com.alipay.multimedia.img.utils.LogUtils.e(r15, r16)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            com.alipay.multimedia.io.IOUtils.closeQuietly(r13)
            r15 = 0
            r12 = r13
            goto L_0x005e
        L_0x00de:
            r7 = 0
            goto L_0x00bf
        L_0x00e0:
            int r9 = r9 + r2
            int r6 = r6 - r2
            int r15 = r9 + -2
            r16 = 2
            r0 = r16
            int r2 = pack(r13, r15, r0, r7)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            r3 = r2
        L_0x00ed:
            int r2 = r3 + -1
            if (r3 <= 0) goto L_0x0128
            r15 = 12
            if (r6 < r15) goto L_0x0128
            r15 = 2
            int r15 = pack(r13, r9, r15, r7)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            r16 = 274(0x112, float:3.84E-43)
            r0 = r16
            if (r15 != r0) goto L_0x0122
            int r15 = r9 + 8
            r16 = 2
            r0 = r16
            int r11 = pack(r13, r15, r0, r7)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            switch(r11) {
                case 1: goto L_0x011b;
                case 2: goto L_0x011b;
                case 3: goto L_0x011b;
                case 4: goto L_0x011b;
                case 5: goto L_0x011b;
                case 6: goto L_0x011b;
                case 7: goto L_0x011b;
                case 8: goto L_0x011b;
                default: goto L_0x010d;
            }     // Catch:{ Exception -> 0x015d, all -> 0x015a }
        L_0x010d:
            java.lang.String r15 = "CameraExif"
            java.lang.String r16 = "Unsupported rotation"
            com.alipay.multimedia.img.utils.LogUtils.i(r15, r16)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            com.alipay.multimedia.io.IOUtils.closeQuietly(r13)
            r15 = 0
            r12 = r13
            goto L_0x005e
        L_0x011b:
            com.alipay.multimedia.io.IOUtils.closeQuietly(r13)
            r12 = r13
            r15 = r11
            goto L_0x005e
        L_0x0122:
            int r9 = r9 + 12
            int r6 = r6 + -12
            r3 = r2
            goto L_0x00ed
        L_0x0128:
            java.lang.String r15 = "CameraExif"
            java.lang.String r16 = "Orientation not found"
            com.alipay.multimedia.img.utils.LogUtils.i(r15, r16)     // Catch:{ Exception -> 0x015d, all -> 0x015a }
            com.alipay.multimedia.io.IOUtils.closeQuietly(r13)
            r12 = r13
        L_0x0133:
            r15 = r11
            goto L_0x005e
        L_0x0136:
            r4 = move-exception
        L_0x0137:
            java.lang.String r15 = "CameraExif"
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ all -> 0x0155 }
            java.lang.String r17 = "parse exif error, path: "
            r16.<init>(r17)     // Catch:{ all -> 0x0155 }
            r0 = r16
            r1 = r18
            java.lang.StringBuilder r16 = r0.append(r1)     // Catch:{ all -> 0x0155 }
            java.lang.String r16 = r16.toString()     // Catch:{ all -> 0x0155 }
            r0 = r16
            com.alipay.multimedia.img.utils.LogUtils.e(r15, r0, r4)     // Catch:{ all -> 0x0155 }
            com.alipay.multimedia.io.IOUtils.closeQuietly(r12)
            goto L_0x0133
        L_0x0155:
            r15 = move-exception
        L_0x0156:
            com.alipay.multimedia.io.IOUtils.closeQuietly(r12)
            throw r15
        L_0x015a:
            r15 = move-exception
            r12 = r13
            goto L_0x0156
        L_0x015d:
            r4 = move-exception
            r12 = r13
            goto L_0x0137
        L_0x0160:
            r10 = r9
            goto L_0x0015
        L_0x0163:
            r9 = r10
            goto L_0x0090
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.img.utils.Exif.getOrientationByRandomAccessFile(java.lang.String):int");
    }

    private static int pack(RandomAccessFile raf, int offset, int length, boolean littleEndian) {
        int step = 1;
        if (littleEndian) {
            offset += length - 1;
            step = -1;
        }
        int value = 0;
        while (true) {
            int length2 = length;
            length = length2 - 1;
            if (length2 <= 0) {
                return value;
            }
            value = (value << 8) | (getB(raf, offset) & 255);
            offset += step;
        }
    }

    private static byte getB(RandomAccessFile raf, int offset) {
        raf.seek((long) offset);
        return raf.readByte();
    }

    public static boolean isCanUseRandomAccessFileExif() {
        long startTime = System.currentTimeMillis();
        if (canUseRandomAccessFileExif == null) {
            canUseRandomAccessFileExif = Boolean.valueOf(!"WoJ+gTjJ0cwDAC/MmNMhIxJX".equalsIgnoreCase(LogUtils.getDeviceId()));
            LogUtils.d(TAG, "isCanUseRandomAccessFileExif canUseRandomAccessFileExif: " + canUseRandomAccessFileExif + ", timecoast: " + (System.currentTimeMillis() - startTime));
        }
        if (canUseRandomAccessFileExif == null) {
            canUseRandomAccessFileExif = Boolean.valueOf(true);
        }
        return canUseRandomAccessFileExif.booleanValue();
    }
}
