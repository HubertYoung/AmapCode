package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import java.lang.reflect.Array;

public final class HybridBinarizer extends GlobalHistogramBinarizer {
    private static final int BLOCK_SIZE = 8;
    private static final int BLOCK_SIZE_MASK = 7;
    private static final int BLOCK_SIZE_POWER = 3;
    private static final int MINIMUM_DIMENSION = 40;
    private static final int MIN_DYNAMIC_RANGE = 24;
    private BitMatrix matrix;

    private static int cap(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    public final BitMatrix getBlackMatrix() throws NotFoundException {
        if (this.matrix != null) {
            return this.matrix;
        }
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        if (width < 40 || height < 40) {
            this.matrix = super.getBlackMatrix();
        } else {
            byte[] matrix2 = luminanceSource.getMatrix();
            int i = width >> 3;
            if ((width & 7) != 0) {
                i++;
            }
            int i2 = i;
            int i3 = height >> 3;
            if ((height & 7) != 0) {
                i3++;
            }
            int i4 = i3;
            int[][] calculateBlackPoints = calculateBlackPoints(matrix2, i2, i4, width, height);
            BitMatrix bitMatrix = new BitMatrix(width, height);
            calculateThresholdForBlock(matrix2, i2, i4, width, height, calculateBlackPoints, bitMatrix);
            this.matrix = bitMatrix;
        }
        return this.matrix;
    }

    public final Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    private static void calculateThresholdForBlock(byte[] bArr, int i, int i2, int i3, int i4, int[][] iArr, BitMatrix bitMatrix) {
        int i5 = i;
        int i6 = i2;
        for (int i7 = 0; i7 < i6; i7++) {
            int i8 = i7 << 3;
            int i9 = i4 - 8;
            if (i8 > i9) {
                i8 = i9;
            }
            for (int i10 = 0; i10 < i5; i10++) {
                int i11 = i10 << 3;
                int i12 = i3 - 8;
                if (i11 <= i12) {
                    i12 = i11;
                }
                int cap = cap(i10, 2, i5 - 3);
                int cap2 = cap(i7, 2, i6 - 3);
                int i13 = 0;
                for (int i14 = -2; i14 <= 2; i14++) {
                    int[] iArr2 = iArr[cap2 + i14];
                    i13 += iArr2[cap - 2] + iArr2[cap - 1] + iArr2[cap] + iArr2[cap + 1] + iArr2[cap + 2];
                }
                thresholdBlock(bArr, i12, i8, i13 / 25, i3, bitMatrix);
            }
        }
    }

    private static void thresholdBlock(byte[] bArr, int i, int i2, int i3, int i4, BitMatrix bitMatrix) {
        int i5 = (i2 * i4) + i;
        int i6 = 0;
        while (i6 < 8) {
            for (int i7 = 0; i7 < 8; i7++) {
                if ((bArr[i5 + i7] & 255) <= i3) {
                    bitMatrix.set(i + i7, i2 + i6);
                }
            }
            i6++;
            i5 += i4;
        }
    }

    private static int[][] calculateBlackPoints(byte[] bArr, int i, int i2, int i3, int i4) {
        int i5;
        int i6 = i;
        int i7 = i2;
        int[][] iArr = (int[][]) Array.newInstance(int.class, new int[]{i7, i6});
        for (int i8 = 0; i8 < i7; i8++) {
            int i9 = i8 << 3;
            int i10 = 8;
            int i11 = i4 - 8;
            if (i9 <= i11) {
                i11 = i9;
            }
            for (int i12 = 0; i12 < i6; i12++) {
                int i13 = i12 << 3;
                int i14 = i3 - 8;
                if (i13 > i14) {
                    i13 = i14;
                }
                int i15 = (i11 * i3) + i13;
                int i16 = 0;
                int i17 = 0;
                byte b = 0;
                byte b2 = 255;
                while (i16 < i10) {
                    int i18 = i17;
                    int i19 = 0;
                    while (i19 < i10) {
                        byte b3 = bArr[i15 + i19] & 255;
                        i18 += b3;
                        if (b3 < b2) {
                            b2 = b3;
                        }
                        if (b3 > b) {
                            b = b3;
                        }
                        i19++;
                        i10 = 8;
                    }
                    if (b - b2 > 24) {
                        int i20 = i16 + 1;
                        i5 = i15 + i3;
                        while (i20 < i10) {
                            int i21 = 0;
                            while (i21 < i10) {
                                i18 += bArr[i5 + i21] & 255;
                                i21++;
                                i10 = 8;
                            }
                            i20++;
                            i5 += i3;
                        }
                        i16 = i20;
                    } else {
                        i5 = i15;
                    }
                    i16++;
                    i15 = i5 + i3;
                    i17 = i18;
                    i10 = 8;
                }
                int i22 = i17 >> 6;
                if (b - b2 <= 24) {
                    i22 = b2 / 2;
                    if (i8 > 0 && i12 > 0) {
                        int i23 = i8 - 1;
                        int i24 = i12 - 1;
                        int i25 = ((iArr[i23][i12] + (iArr[i8][i24] * 2)) + iArr[i23][i24]) / 4;
                        if (b2 < i25) {
                            i22 = i25;
                        }
                    }
                }
                iArr[i8][i12] = i22;
            }
        }
        return iArr;
    }
}
