package com.alipay.streammedia.mmengine.picture;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.alipay.multimedia.img.utils.ImageFileType;

public class EmptyJpegGenerator {
    private static final byte[] EMPTY_JPEG_PREFIX = {-1, -40, -1, -37, 0, 67, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -64, 0, 17, 8};
    private static final byte[] EMPTY_JPEG_SUFFIX = {3, 1, 34, 0, 2, 17, 0, 3, 17, 0, -1, -60, 0, 31, 0, 0, 1, 5, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, -1, -60, 0, -75, 16, 0, 2, 1, 3, 3, 2, 4, 3, 5, 5, 4, 4, 0, 0, 1, 125, 1, 2, 3, 0, 4, 17, 5, 18, 33, 49, 65, 6, 19, 81, 97, 7, 34, 113, 20, 50, -127, -111, -95, 8, 35, 66, -79, -63, 21, ImageFileType.HEAD_WEBP_0, -47, -16, 36, 51, 98, 114, -126, 9, 10, 22, 23, 24, 25, 26, 37, 38, 39, 40, 41, 42, 52, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 83, 84, 85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, -125, -124, -123, -122, -121, -120, -119, -118, -110, -109, -108, -107, -106, -105, -104, -103, -102, -94, -93, -92, -91, -90, -89, -88, -87, -86, -78, -77, -76, -75, -74, -73, -72, -71, -70, -62, -61, -60, -59, -58, -57, -56, -55, -54, -46, -45, -44, -43, -42, -41, -40, -39, -38, -31, -30, -29, -28, -27, -26, -25, -24, -23, -22, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -1, -60, 0, 31, 1, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, -1, -60, 0, -75, 17, 0, 2, 1, 2, 4, 4, 3, 4, 7, 5, 4, 4, 0, 1, 2, 119, 0, 1, 2, 3, 17, 4, 5, 33, 49, 6, 18, 65, 81, 7, 97, 113, 19, 34, 50, -127, 8, 20, 66, -111, -95, -79, -63, 9, 35, 51, ImageFileType.HEAD_WEBP_0, -16, 21, 98, 114, -47, 10, 22, 36, 52, -31, 37, -15, 23, 24, 25, 26, 38, 39, 40, 41, 42, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 83, 84, 85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, -126, -125, -124, -123, -122, -121, -120, -119, -118, -110, -109, -108, -107, -106, -105, -104, -103, -102, -94, -93, -92, -91, -90, -89, -88, -87, -86, -78, -77, -76, -75, -74, -73, -72, -71, -70, -62, -61, -60, -59, -58, -57, -56, -55, -54, -46, -45, -44, -43, -42, -41, -40, -39, -38, -30, -29, -28, -27, -26, -25, -24, -23, -22, -14, -13, -12, -11, -10, -9, -8, -7, -6, -1, -38, 0, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 3, 1, 0, 2, 17, 3, 17, 0, 63, 0, -114, -118, 40, -96, 15, -1, -39};

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0043 A[SYNTHETIC, Splitter:B:14:0x0043] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] generate(short r5, short r6) {
        /*
            r1 = 0
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0039 }
            byte[] r3 = EMPTY_JPEG_PREFIX     // Catch:{ IOException -> 0x0039 }
            int r3 = r3.length     // Catch:{ IOException -> 0x0039 }
            byte[] r4 = EMPTY_JPEG_SUFFIX     // Catch:{ IOException -> 0x0039 }
            int r4 = r4.length     // Catch:{ IOException -> 0x0039 }
            int r3 = r3 + r4
            int r3 = r3 + 4
            r2.<init>(r3)     // Catch:{ IOException -> 0x0039 }
            byte[] r3 = EMPTY_JPEG_PREFIX     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r2.write(r3)     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            int r3 = r6 >> 8
            byte r3 = (byte) r3     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r2.write(r3)     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r3 = r6 & 255(0xff, float:3.57E-43)
            byte r3 = (byte) r3     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r2.write(r3)     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            int r3 = r5 >> 8
            byte r3 = (byte) r3     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r2.write(r3)     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r3 = r5 & 255(0xff, float:3.57E-43)
            byte r3 = (byte) r3     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r2.write(r3)     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            byte[] r3 = EMPTY_JPEG_SUFFIX     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r2.write(r3)     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            byte[] r3 = r2.toByteArray()     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r2.close()     // Catch:{ IOException -> 0x0047 }
        L_0x0038:
            return r3
        L_0x0039:
            r0 = move-exception
        L_0x003a:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ all -> 0x0040 }
            r3.<init>(r0)     // Catch:{ all -> 0x0040 }
            throw r3     // Catch:{ all -> 0x0040 }
        L_0x0040:
            r3 = move-exception
        L_0x0041:
            if (r1 == 0) goto L_0x0046
            r1.close()     // Catch:{ IOException -> 0x0049 }
        L_0x0046:
            throw r3
        L_0x0047:
            r4 = move-exception
            goto L_0x0038
        L_0x0049:
            r4 = move-exception
            goto L_0x0046
        L_0x004b:
            r3 = move-exception
            r1 = r2
            goto L_0x0041
        L_0x004e:
            r0 = move-exception
            r1 = r2
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.streammedia.mmengine.picture.EmptyJpegGenerator.generate(short, short):byte[]");
    }
}
