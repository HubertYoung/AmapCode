package com.alipay.android.phone.wallet.minizxing;

import android.support.v4.media.TransportMediator;
import com.autonavi.minimap.route.sharebike.model.EndBill;
import com.autonavi.minimap.route.sharebike.model.ScanQrcode;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;

final class l {
    private static final int[][] a = {new int[]{1, 1, 1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1, 1, 1}};
    private static final int[][] b = {new int[]{1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 0, 1, 0, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1}};
    private static final int[][] c = {new int[]{-1, -1, -1, -1, -1, -1, -1}, new int[]{6, 18, -1, -1, -1, -1, -1}, new int[]{6, 22, -1, -1, -1, -1, -1}, new int[]{6, 26, -1, -1, -1, -1, -1}, new int[]{6, 30, -1, -1, -1, -1, -1}, new int[]{6, 34, -1, -1, -1, -1, -1}, new int[]{6, 22, 38, -1, -1, -1, -1}, new int[]{6, 24, 42, -1, -1, -1, -1}, new int[]{6, 26, 46, -1, -1, -1, -1}, new int[]{6, 28, 50, -1, -1, -1, -1}, new int[]{6, 30, 54, -1, -1, -1, -1}, new int[]{6, 32, 58, -1, -1, -1, -1}, new int[]{6, 34, 62, -1, -1, -1, -1}, new int[]{6, 26, 46, 66, -1, -1, -1}, new int[]{6, 26, 48, 70, -1, -1, -1}, new int[]{6, 26, 50, 74, -1, -1, -1}, new int[]{6, 30, 54, 78, -1, -1, -1}, new int[]{6, 30, 56, 82, -1, -1, -1}, new int[]{6, 30, 58, 86, -1, -1, -1}, new int[]{6, 34, 62, 90, -1, -1, -1}, new int[]{6, 28, 50, 72, 94, -1, -1}, new int[]{6, 26, 50, 74, 98, -1, -1}, new int[]{6, 30, 54, 78, 102, -1, -1}, new int[]{6, 28, 54, 80, 106, -1, -1}, new int[]{6, 32, 58, 84, 110, -1, -1}, new int[]{6, 30, 58, 86, 114, -1, -1}, new int[]{6, 34, 62, 90, 118, -1, -1}, new int[]{6, 26, 50, 74, 98, 122, -1}, new int[]{6, 30, 54, 78, 102, TransportMediator.KEYCODE_MEDIA_PLAY, -1}, new int[]{6, 26, 52, 78, 104, 130, -1}, new int[]{6, 30, 56, 82, 108, 134, -1}, new int[]{6, 34, 60, 86, 112, DumpSegment.ANDROID_ROOT_FINALIZING, -1}, new int[]{6, 30, 58, 86, 114, DumpSegment.ANDROID_ROOT_JNI_MONITOR, -1}, new int[]{6, 34, 62, 90, 118, 146, -1}, new int[]{6, 30, 54, 78, 102, TransportMediator.KEYCODE_MEDIA_PLAY, 150}, new int[]{6, 24, 50, 76, 102, 128, ScanQrcode.SAME_BIKE_USING_RESPONSE_CODE}, new int[]{6, 28, 54, 80, 106, 132, 158}, new int[]{6, 32, 58, 84, 110, 136, EndBill.UNKNOWN_END_ORDER_FAILED}, new int[]{6, 26, 54, 82, 110, DumpSegment.ANDROID_ROOT_FINALIZING, 166}, new int[]{6, 30, 58, 86, 114, DumpSegment.ANDROID_ROOT_JNI_MONITOR, 170}};
    private static final int[][] d = {new int[]{8, 0}, new int[]{8, 1}, new int[]{8, 2}, new int[]{8, 3}, new int[]{8, 4}, new int[]{8, 5}, new int[]{8, 7}, new int[]{8, 8}, new int[]{7, 8}, new int[]{5, 8}, new int[]{4, 8}, new int[]{3, 8}, new int[]{2, 8}, new int[]{1, 8}, new int[]{0, 8}};

    private static void a(d matrix) {
        matrix.d();
    }

    static void a(b dataBits, ErrorCorrectionLevel ecLevel, t version, int maskPattern, d matrix) {
        a(matrix);
        a(version, matrix);
        a(ecLevel, maskPattern, matrix);
        b(version, matrix);
        a(dataBits, maskPattern, matrix);
    }

    private static void a(t version, d matrix) {
        d(matrix);
        c(matrix);
        c(version, matrix);
        b(matrix);
    }

    private static void a(ErrorCorrectionLevel ecLevel, int maskPattern, d matrix) {
        b typeInfoBits = new b();
        a(ecLevel, maskPattern, typeInfoBits);
        for (int i = 0; i < typeInfoBits.a(); i++) {
            boolean bit = typeInfoBits.a((typeInfoBits.a() - 1) - i);
            matrix.a(d[i][0], d[i][1], bit);
            if (i < 8) {
                matrix.a((matrix.b() - i) - 1, 8, bit);
            } else {
                matrix.a(8, (matrix.a() - 7) + (i - 8), bit);
            }
        }
    }

    private static void b(t version, d matrix) {
        if (version.a() >= 7) {
            b versionInfoBits = new b();
            a(version, versionInfoBits);
            int bitIndex = 17;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 3; j++) {
                    boolean bit = versionInfoBits.a(bitIndex);
                    bitIndex--;
                    matrix.a(i, (matrix.a() - 11) + j, bit);
                    matrix.a((matrix.a() - 11) + j, i, bit);
                }
            }
        }
    }

    private static void a(b dataBits, int maskPattern, d matrix) {
        boolean bit;
        int bitIndex = 0;
        int direction = -1;
        int x = matrix.b() - 1;
        int y = matrix.a() - 1;
        while (x > 0) {
            if (x == 6) {
                x--;
            }
            while (y >= 0 && y < matrix.a()) {
                for (int i = 0; i < 2; i++) {
                    int xx = x - i;
                    if (b((int) matrix.a(xx, y))) {
                        if (bitIndex < dataBits.a()) {
                            bit = dataBits.a(bitIndex);
                            bitIndex++;
                        } else {
                            bit = false;
                        }
                        if (maskPattern != -1 && k.a(maskPattern, xx, y)) {
                            bit = !bit;
                        }
                        matrix.a(xx, y, bit);
                    }
                }
                y += direction;
            }
            direction = -direction;
            y += direction;
            x -= 2;
        }
        if (bitIndex != dataBits.a()) {
            throw new WriterException("Not all bits consumed: " + bitIndex + '/' + dataBits.a());
        }
    }

    private static int a(int value) {
        int numDigits = 0;
        while (value != 0) {
            value >>>= 1;
            numDigits++;
        }
        return numDigits;
    }

    private static int a(int value, int poly) {
        if (poly == 0) {
            throw new IllegalArgumentException("0 polynomial");
        }
        int msbSetInPoly = a(poly);
        int value2 = value << (msbSetInPoly - 1);
        while (a(value2) >= msbSetInPoly) {
            value2 ^= poly << (a(value2) - msbSetInPoly);
        }
        return value2;
    }

    private static void a(ErrorCorrectionLevel ecLevel, int maskPattern, b bits) {
        if (!o.b(maskPattern)) {
            throw new WriterException((String) "Invalid mask pattern");
        }
        int typeInfo = (ecLevel.getBits() << 3) | maskPattern;
        bits.a(typeInfo, 5);
        bits.a(a(typeInfo, 1335), 10);
        b maskBits = new b();
        maskBits.a(21522, 15);
        bits.b(maskBits);
        if (bits.a() != 15) {
            throw new WriterException("should not happen but we got: " + bits.a());
        }
    }

    private static void a(t version, b bits) {
        bits.a(version.a(), 6);
        bits.a(a(version.a(), 7973), 12);
        if (bits.a() != 18) {
            throw new WriterException("should not happen but we got: " + bits.a());
        }
    }

    private static boolean b(int value) {
        return value == -1;
    }

    private static void b(d matrix) {
        for (int i = 8; i < matrix.b() - 8; i++) {
            int bit = (i + 1) % 2;
            if (b((int) matrix.a(i, 6))) {
                matrix.a(i, 6, bit);
            }
            if (b((int) matrix.a(6, i))) {
                matrix.a(6, i, bit);
            }
        }
    }

    private static void c(d matrix) {
        if (matrix.a(8, matrix.a() - 8) == 0) {
            throw new WriterException();
        }
        matrix.a(8, matrix.a() - 8, 1);
    }

    private static void a(int xStart, int yStart, d matrix) {
        for (int x = 0; x < 8; x++) {
            if (!b((int) matrix.a(xStart + x, yStart))) {
                throw new WriterException();
            }
            matrix.a(xStart + x, yStart, 0);
        }
    }

    private static void b(int xStart, int yStart, d matrix) {
        for (int y = 0; y < 7; y++) {
            if (!b((int) matrix.a(xStart, yStart + y))) {
                throw new WriterException();
            }
            matrix.a(xStart, yStart + y, 0);
        }
    }

    private static void c(int xStart, int yStart, d matrix) {
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                matrix.a(xStart + x, yStart + y, b[y][x]);
            }
        }
    }

    private static void d(int xStart, int yStart, d matrix) {
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                matrix.a(xStart + x, yStart + y, a[y][x]);
            }
        }
    }

    private static void d(d matrix) {
        int pdpWidth = a[0].length;
        d(0, 0, matrix);
        d(matrix.b() - pdpWidth, 0, matrix);
        d(0, matrix.b() - pdpWidth, matrix);
        a(0, 7, matrix);
        a(matrix.b() - 8, 7, matrix);
        a(0, matrix.b() - 8, matrix);
        b(7, 0, matrix);
        b((matrix.a() - 7) - 1, 0, matrix);
        b(7, matrix.a() - 7, matrix);
    }

    private static void c(t version, d matrix) {
        if (version.a() >= 2) {
            int index = version.a() - 1;
            int[] coordinates = c[index];
            int numCoordinates = c[index].length;
            for (int i = 0; i < numCoordinates; i++) {
                for (int j = 0; j < numCoordinates; j++) {
                    int y = coordinates[i];
                    int x = coordinates[j];
                    if (!(x == -1 || y == -1 || !b((int) matrix.a(x, y)))) {
                        c(x - 2, y - 2, matrix);
                    }
                }
            }
        }
    }
}
