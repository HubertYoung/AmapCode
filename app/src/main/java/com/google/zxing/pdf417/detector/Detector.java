package com.google.zxing.pdf417.detector;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class Detector {
    private static final int BARCODE_MIN_HEIGHT = 10;
    private static final int[] INDEXES_START_PATTERN;
    private static final int[] INDEXES_STOP_PATTERN = {6, 2, 7, 3};
    private static final float MAX_AVG_VARIANCE = 0.42f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.8f;
    private static final int MAX_PATTERN_DRIFT = 5;
    private static final int MAX_PIXEL_DRIFT = 3;
    private static final int ROW_STEP = 5;
    private static final int SKIPPED_ROW_COUNT_MAX = 25;
    private static final int[] START_PATTERN = {8, 1, 1, 1, 1, 1, 1, 3};
    private static final int[] STOP_PATTERN = {7, 1, 1, 3, 1, 1, 1, 2, 1};

    static {
        int[] iArr = new int[4];
        iArr[1] = 4;
        iArr[2] = 1;
        iArr[3] = 5;
        INDEXES_START_PATTERN = iArr;
    }

    private Detector() {
    }

    public static PDF417DetectorResult detect(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, boolean z) throws NotFoundException {
        BitMatrix blackMatrix = binaryBitmap.getBlackMatrix();
        List<ResultPoint[]> detect = detect(z, blackMatrix);
        if (detect.isEmpty()) {
            blackMatrix = blackMatrix.clone();
            blackMatrix.rotate180();
            detect = detect(z, blackMatrix);
        }
        return new PDF417DetectorResult(blackMatrix, detect);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0029, code lost:
        if (r4.hasNext() != false) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002e, code lost:
        r5 = (com.google.zxing.ResultPoint[]) r4.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0036, code lost:
        if (r5[1] == null) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0038, code lost:
        r3 = (int) java.lang.Math.max((float) r3, r5[1].getY());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0046, code lost:
        if (r5[3] == null) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0048, code lost:
        r3 = java.lang.Math.max(r3, (int) r5[3].getY());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001f, code lost:
        if (r5 == false) goto L_0x007f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0021, code lost:
        r4 = r0.iterator();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.google.zxing.ResultPoint[]> detect(boolean r8, com.google.zxing.common.BitMatrix r9) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 1
            r2 = 0
            r3 = 0
        L_0x0008:
            r4 = 0
            r5 = 0
        L_0x000a:
            int r6 = r9.getHeight()
            if (r3 < r6) goto L_0x0012
            goto L_0x007f
        L_0x0012:
            com.google.zxing.ResultPoint[] r4 = findVertices(r9, r3, r4)
            r6 = r4[r2]
            if (r6 != 0) goto L_0x0054
            r6 = 3
            r7 = r4[r6]
            if (r7 != 0) goto L_0x0054
            if (r5 == 0) goto L_0x007f
            java.util.Iterator r4 = r0.iterator()
        L_0x0025:
            boolean r5 = r4.hasNext()
            if (r5 != 0) goto L_0x002e
            int r3 = r3 + 5
            goto L_0x0008
        L_0x002e:
            java.lang.Object r5 = r4.next()
            com.google.zxing.ResultPoint[] r5 = (com.google.zxing.ResultPoint[]) r5
            r7 = r5[r1]
            if (r7 == 0) goto L_0x0044
            float r3 = (float) r3
            r7 = r5[r1]
            float r7 = r7.getY()
            float r3 = java.lang.Math.max(r3, r7)
            int r3 = (int) r3
        L_0x0044:
            r7 = r5[r6]
            if (r7 == 0) goto L_0x0025
            r5 = r5[r6]
            float r5 = r5.getY()
            int r5 = (int) r5
            int r3 = java.lang.Math.max(r3, r5)
            goto L_0x0025
        L_0x0054:
            r0.add(r4)
            if (r8 == 0) goto L_0x007f
            r3 = 2
            r5 = r4[r3]
            if (r5 == 0) goto L_0x006f
            r5 = r4[r3]
            float r5 = r5.getX()
            int r5 = (int) r5
            r3 = r4[r3]
            float r3 = r3.getY()
            int r3 = (int) r3
        L_0x006c:
            r4 = r5
            r5 = 1
            goto L_0x000a
        L_0x006f:
            r3 = 4
            r5 = r4[r3]
            float r5 = r5.getX()
            int r5 = (int) r5
            r3 = r4[r3]
            float r3 = r3.getY()
            int r3 = (int) r3
            goto L_0x006c
        L_0x007f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.detector.Detector.detect(boolean, com.google.zxing.common.BitMatrix):java.util.List");
    }

    private static ResultPoint[] findVertices(BitMatrix bitMatrix, int i, int i2) {
        int height = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();
        ResultPoint[] resultPointArr = new ResultPoint[8];
        copyToResult(resultPointArr, findRowsWithPattern(bitMatrix, height, width, i, i2, START_PATTERN), INDEXES_START_PATTERN);
        if (resultPointArr[4] != null) {
            i2 = (int) resultPointArr[4].getX();
            i = (int) resultPointArr[4].getY();
        }
        copyToResult(resultPointArr, findRowsWithPattern(bitMatrix, height, width, i, i2, STOP_PATTERN), INDEXES_STOP_PATTERN);
        return resultPointArr;
    }

    private static void copyToResult(ResultPoint[] resultPointArr, ResultPoint[] resultPointArr2, int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            resultPointArr[iArr[i]] = resultPointArr2[i];
        }
    }

    private static ResultPoint[] findRowsWithPattern(BitMatrix bitMatrix, int i, int i2, int i3, int i4, int[] iArr) {
        int i5;
        boolean z;
        int i6;
        int i7;
        int[] iArr2;
        int i8 = i;
        ResultPoint[] resultPointArr = new ResultPoint[4];
        int[] iArr3 = iArr;
        int[] iArr4 = new int[iArr3.length];
        int i9 = i3;
        while (true) {
            if (i9 >= i8) {
                z = false;
                break;
            }
            int[] findGuardPattern = findGuardPattern(bitMatrix, i4, i9, i2, false, iArr3, iArr4);
            if (findGuardPattern != null) {
                while (true) {
                    iArr2 = findGuardPattern;
                    if (i9 > 0) {
                        i9--;
                        findGuardPattern = findGuardPattern(bitMatrix, i4, i9, i2, false, iArr3, iArr4);
                        if (findGuardPattern == null) {
                            i9++;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                float f = (float) i9;
                resultPointArr[0] = new ResultPoint((float) iArr2[0], f);
                resultPointArr[1] = new ResultPoint((float) iArr2[1], f);
                z = true;
            } else {
                i9 += 5;
            }
        }
        int i10 = i9 + 1;
        if (z) {
            int[] iArr5 = {(int) resultPointArr[0].getX(), (int) resultPointArr[1].getX()};
            int i11 = i10;
            int i12 = 0;
            while (true) {
                if (i11 >= i8) {
                    i6 = i12;
                    i7 = i11;
                    break;
                }
                i6 = i12;
                i7 = i11;
                int[] findGuardPattern2 = findGuardPattern(bitMatrix, iArr5[0], i11, i2, false, iArr3, iArr4);
                if (findGuardPattern2 == null || Math.abs(iArr5[0] - findGuardPattern2[0]) >= 5 || Math.abs(iArr5[1] - findGuardPattern2[1]) >= 5) {
                    if (i6 > 25) {
                        break;
                    }
                    i12 = i6 + 1;
                } else {
                    iArr5 = findGuardPattern2;
                    i12 = 0;
                }
                i11 = i7 + 1;
            }
            i10 = i7 - (i6 + 1);
            float f2 = (float) i10;
            resultPointArr[2] = new ResultPoint((float) iArr5[0], f2);
            resultPointArr[3] = new ResultPoint((float) iArr5[1], f2);
        }
        if (i10 - i9 < 10) {
            for (i5 = 0; i5 < 4; i5++) {
                resultPointArr[i5] = null;
            }
        }
        return resultPointArr;
    }

    private static int[] findGuardPattern(BitMatrix bitMatrix, int i, int i2, int i3, boolean z, int[] iArr, int[] iArr2) {
        Arrays.fill(iArr2, 0, iArr2.length, 0);
        int i4 = 0;
        while (bitMatrix.get(i, i2) && i > 0) {
            int i5 = i4 + 1;
            if (i4 >= 3) {
                break;
            }
            i--;
            i4 = i5;
        }
        int length = iArr.length;
        int i6 = i;
        boolean z2 = z;
        int i7 = 0;
        while (true) {
            boolean z3 = true;
            if (i < i3) {
                if (bitMatrix.get(i, i2) ^ z2) {
                    iArr2[i7] = iArr2[i7] + 1;
                } else {
                    int i8 = length - 1;
                    if (i7 != i8) {
                        i7++;
                    } else if (patternMatchVariance(iArr2, iArr, MAX_INDIVIDUAL_VARIANCE) < MAX_AVG_VARIANCE) {
                        return new int[]{i6, i};
                    } else {
                        i6 += iArr2[0] + iArr2[1];
                        int i9 = length - 2;
                        System.arraycopy(iArr2, 2, iArr2, 0, i9);
                        iArr2[i9] = 0;
                        iArr2[i8] = 0;
                        i7--;
                    }
                    iArr2[i7] = 1;
                    if (z2) {
                        z3 = false;
                    }
                    z2 = z3;
                }
                i++;
            } else if (i7 != length - 1 || patternMatchVariance(iArr2, iArr, MAX_INDIVIDUAL_VARIANCE) >= MAX_AVG_VARIANCE) {
                return null;
            } else {
                return new int[]{i6, i - 1};
            }
        }
    }

    private static float patternMatchVariance(int[] iArr, int[] iArr2, float f) {
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            i += iArr[i3];
            i2 += iArr2[i3];
        }
        if (i < i2) {
            return Float.POSITIVE_INFINITY;
        }
        float f2 = (float) i;
        float f3 = f2 / ((float) i2);
        float f4 = f * f3;
        float f5 = 0.0f;
        for (int i4 = 0; i4 < length; i4++) {
            float f6 = ((float) iArr2[i4]) * f3;
            float f7 = (float) iArr[i4];
            float f8 = f7 > f6 ? f7 - f6 : f6 - f7;
            if (f8 > f4) {
                return Float.POSITIVE_INFINITY;
            }
            f5 += f8;
        }
        return f5 / f2;
    }
}
