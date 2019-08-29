package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SilkUtils {
    public static short[] getShortArray(byte[] bytes, int size) {
        if (bytes == null) {
            return null;
        }
        short[] shorts = new short[(size / 2)];
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        byteBuffer.put(bytes, 0, size);
        byteBuffer.flip();
        for (int i = 0; i < shorts.length; i++) {
            shorts[i] = byteBuffer.getShort();
        }
        return shorts;
    }

    public static short getLittleEndianShort(byte[] bytes) {
        return (short) (bytes[0] | (bytes[1] << 8));
    }

    public static byte[] convertToLittleEndian(short input) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(input);
        return buffer.array();
    }

    public static boolean convertToFormat(String src, File targetFile, String format) {
        if (TextUtils.isEmpty(src) || targetFile == null || !"wav".equalsIgnoreCase(format)) {
            return false;
        }
        return a(src, targetFile);
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x019c  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x01bd  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x01d3  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x01ee  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x020f  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0228  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.lang.String r28, java.io.File r29) {
        /*
            r19 = r28
            r12 = 0
            r17 = 0
            r23 = 2
            r0 = r23
            byte[] r15 = new byte[r0]
            r23 = 4096(0x1000, float:5.74E-42)
            r0 = r23
            byte[] r11 = new byte[r0]
            r23 = 960(0x3c0, float:1.345E-42)
            r0 = r23
            short[] r8 = new short[r0]
            r21 = 0
            java.io.FileInputStream r13 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0160 }
            r0 = r19
            r13.<init>(r0)     // Catch:{ Exception -> 0x0160 }
            java.lang.String r23 = "#!SILK_V3"
            byte[] r6 = r23.getBytes()     // Catch:{ Exception -> 0x0240, all -> 0x022b }
            r23 = 0
            int r0 = r6.length     // Catch:{ Exception -> 0x0240, all -> 0x022b }
            r24 = r0
            r0 = r23
            r1 = r24
            int r20 = r13.read(r11, r0, r1)     // Catch:{ Exception -> 0x0240, all -> 0x022b }
            r0 = r20
            byte[] r23 = java.util.Arrays.copyOf(r11, r0)     // Catch:{ Exception -> 0x0240, all -> 0x022b }
            r0 = r23
            boolean r23 = java.util.Arrays.equals(r0, r6)     // Catch:{ Exception -> 0x0240, all -> 0x022b }
            if (r23 != 0) goto L_0x005f
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r13)     // Catch:{ Exception -> 0x0240, all -> 0x022b }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r13)
            r23 = 0
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r23)
            java.lang.String r23 = "SilkUtils"
            java.lang.String r24 = "convertToWav finally error? true"
            r25 = 0
            r0 = r25
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r25 = r0
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r23, r24, r25)
            r23 = 0
            r12 = r13
        L_0x005e:
            return r23
        L_0x005f:
            java.io.RandomAccessFile r18 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0240, all -> 0x022b }
            java.lang.String r23 = "rw"
            r0 = r18
            r1 = r29
            r2 = r23
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x0240, all -> 0x022b }
            r24 = 44
            r0 = r18
            r1 = r24
            r0.seek(r1)     // Catch:{ Exception -> 0x0244, all -> 0x0230 }
            r7 = 0
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkApi r22 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkApi     // Catch:{ Exception -> 0x0244, all -> 0x0230 }
            r22.<init>()     // Catch:{ Exception -> 0x0244, all -> 0x0230 }
            r23 = 16000(0x3e80, float:2.2421E-41)
            int r16 = r22.openDecoder(r23)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            java.lang.String r23 = "SilkUtils"
            java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            java.lang.String r25 = "convertToWav openRet: "
            r24.<init>(r25)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            r0 = r24
            r1 = r16
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            java.lang.String r24 = r24.toString()     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            r25 = 0
            r0 = r25
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            r25 = r0
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r23, r24, r25)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
        L_0x00a1:
            r23 = 0
            r24 = 2
            r0 = r23
            r1 = r24
            int r23 = r13.read(r15, r0, r1)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            r24 = -1
            r0 = r23
            r1 = r24
            if (r0 == r1) goto L_0x011d
            short r14 = getLittleEndianShort(r15)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            if (r14 < 0) goto L_0x011d
            r23 = 0
            r0 = r23
            int r20 = r13.read(r11, r0, r14)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            r0 = r20
            if (r0 == r14) goto L_0x0101
            java.lang.String r23 = "SilkUtils"
            java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            java.lang.String r25 = "path: "
            r24.<init>(r25)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            r0 = r24
            r1 = r19
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            java.lang.String r25 = ", read: "
            java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            r0 = r24
            r1 = r20
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            java.lang.String r25 = ", len: "
            java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            r0 = r24
            java.lang.StringBuilder r24 = r0.append(r14)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            java.lang.String r24 = r24.toString()     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            r25 = 0
            r0 = r25
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            r25 = r0
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r23, r24, r25)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
        L_0x0101:
            r23 = -1
            r0 = r20
            r1 = r23
            if (r0 == r1) goto L_0x011d
            r0 = r22
            r1 = r20
            int r9 = r0.decode(r11, r8, r1)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            if (r9 <= 0) goto L_0x00a1
            r0 = r18
            a(r0, r8, r9)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            int r23 = r9 * 2
            int r7 = r7 + r23
            goto L_0x00a1
        L_0x011d:
            long r0 = (long) r7     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            r24 = r0
            int r23 = r7 + 36
            r0 = r23
            long r0 = (long) r0     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            r26 = r0
            r0 = r18
            r1 = r24
            r3 = r26
            a(r0, r1, r3)     // Catch:{ Exception -> 0x024a, all -> 0x0237 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r13)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r18)
            java.lang.String r23 = "SilkUtils"
            java.lang.String r24 = "convertToWav finally error? false"
            r25 = 0
            r0 = r25
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r25 = r0
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r23, r24, r25)
            r22.closeDecoder()
            java.lang.String r23 = "SilkUtils"
            java.lang.String r24 = "convertToWav silkApi closeDecoder"
            r25 = 0
            r0 = r25
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r25 = r0
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r23, r24, r25)
            r23 = 1
            r21 = r22
            r17 = r18
            r12 = r13
            goto L_0x005e
        L_0x0160:
            r10 = move-exception
        L_0x0161:
            java.lang.String r23 = "SilkUtils"
            java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d6 }
            java.lang.String r25 = "convertToWav error, path: "
            r24.<init>(r25)     // Catch:{ all -> 0x01d6 }
            r0 = r24
            r1 = r19
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x01d6 }
            java.lang.String r24 = r24.toString()     // Catch:{ all -> 0x01d6 }
            r25 = 0
            r0 = r25
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x01d6 }
            r25 = r0
            r0 = r23
            r1 = r24
            r2 = r25
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r0, r10, r1, r2)     // Catch:{ all -> 0x01d6 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r12)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r17)
            java.lang.String r24 = "SilkUtils"
            java.lang.StringBuilder r25 = new java.lang.StringBuilder
            java.lang.String r23 = "convertToWav finally error? "
            r0 = r25
            r1 = r23
            r0.<init>(r1)
            if (r21 != 0) goto L_0x01d3
            r23 = 1
        L_0x019e:
            r0 = r25
            r1 = r23
            java.lang.StringBuilder r23 = r0.append(r1)
            java.lang.String r23 = r23.toString()
            r25 = 0
            r0 = r25
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r25 = r0
            r0 = r24
            r1 = r23
            r2 = r25
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r0, r1, r2)
            if (r21 == 0) goto L_0x01cf
            r21.closeDecoder()
            java.lang.String r23 = "SilkUtils"
            java.lang.String r24 = "convertToWav silkApi closeDecoder"
            r25 = 0
            r0 = r25
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r25 = r0
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r23, r24, r25)
        L_0x01cf:
            r23 = 0
            goto L_0x005e
        L_0x01d3:
            r23 = 0
            goto L_0x019e
        L_0x01d6:
            r23 = move-exception
            r24 = r23
        L_0x01d9:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r12)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r17)
            java.lang.String r25 = "SilkUtils"
            java.lang.StringBuilder r26 = new java.lang.StringBuilder
            java.lang.String r23 = "convertToWav finally error? "
            r0 = r26
            r1 = r23
            r0.<init>(r1)
            if (r21 != 0) goto L_0x0228
            r23 = 1
        L_0x01f0:
            r0 = r26
            r1 = r23
            java.lang.StringBuilder r23 = r0.append(r1)
            java.lang.String r23 = r23.toString()
            r26 = 0
            r0 = r26
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r26 = r0
            r0 = r25
            r1 = r23
            r2 = r26
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r0, r1, r2)
            if (r21 == 0) goto L_0x0227
            r21.closeDecoder()
            java.lang.String r23 = "SilkUtils"
            java.lang.String r25 = "convertToWav silkApi closeDecoder"
            r26 = 0
            r0 = r26
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r26 = r0
            r0 = r23
            r1 = r25
            r2 = r26
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r0, r1, r2)
        L_0x0227:
            throw r24
        L_0x0228:
            r23 = 0
            goto L_0x01f0
        L_0x022b:
            r23 = move-exception
            r24 = r23
            r12 = r13
            goto L_0x01d9
        L_0x0230:
            r23 = move-exception
            r24 = r23
            r17 = r18
            r12 = r13
            goto L_0x01d9
        L_0x0237:
            r23 = move-exception
            r24 = r23
            r21 = r22
            r17 = r18
            r12 = r13
            goto L_0x01d9
        L_0x0240:
            r10 = move-exception
            r12 = r13
            goto L_0x0161
        L_0x0244:
            r10 = move-exception
            r17 = r18
            r12 = r13
            goto L_0x0161
        L_0x024a:
            r10 = move-exception
            r21 = r22
            r17 = r18
            r12 = r13
            goto L_0x0161
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkUtils.a(java.lang.String, java.io.File):boolean");
    }

    private static void a(RandomAccessFile out, long totalAudioLen, long totalDataLen) {
        Logger.D("SilkUtils", "fillWaveFileHeader out: " + out + ", totalAudioLen: " + totalAudioLen + ", totalDataLen: " + totalDataLen + ", sampleRate: 16000, channels: 1, encoding: 2, byteRate: 32000", new Object[0]);
        out.seek(0);
        out.write(new byte[]{ImageFileType.HEAD_WEBP_0, 73, 70, 70, (byte) ((int) (totalDataLen & 255)), (byte) ((int) ((totalDataLen >> 8) & 255)), (byte) ((int) ((totalDataLen >> 16) & 255)), (byte) ((int) ((totalDataLen >> 24) & 255)), 87, 65, 86, 69, 102, 109, 116, 32, 16, 0, 0, 0, 1, 0, 1, 0, Byte.MIN_VALUE, 62, 0, 0, 0, 125, 0, 0, 4, 0, 16, 0, 100, 97, 116, 97, (byte) ((int) (totalAudioLen & 255)), (byte) ((int) ((totalAudioLen >> 8) & 255)), (byte) ((int) ((totalAudioLen >> 16) & 255)), (byte) ((int) ((totalAudioLen >> 24) & 255))}, 0, 44);
    }

    private static void a(RandomAccessFile out, short[] data, int length) {
        for (int i = 0; i < length; i++) {
            out.writeByte(data[i] & 255);
            out.writeByte((data[i] >> 8) & 255);
        }
    }
}
