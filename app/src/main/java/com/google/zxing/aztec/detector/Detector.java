package com.google.zxing.aztec.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.uc.webview.export.extension.UCCore;

public final class Detector {
    private static final int[] EXPECTED_CORNER_BITS = {3808, 476, 2107, 1799};
    private boolean compact;
    private final BitMatrix image;
    private int nbCenterLayers;
    private int nbDataBlocks;
    private int nbLayers;
    private int shift;

    static final class Point {
        private final int x;
        private final int y;

        /* access modifiers changed from: 0000 */
        public final ResultPoint toResultPoint() {
            return new ResultPoint((float) getX(), (float) getY());
        }

        Point(int i, int i2) {
            this.x = i;
            this.y = i2;
        }

        /* access modifiers changed from: 0000 */
        public final int getX() {
            return this.x;
        }

        /* access modifiers changed from: 0000 */
        public final int getY() {
            return this.y;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder(SimpleComparison.LESS_THAN_OPERATION);
            sb.append(this.x);
            sb.append(' ');
            sb.append(this.y);
            sb.append('>');
            return sb.toString();
        }
    }

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    public final AztecDetectorResult detect() throws NotFoundException {
        return detect(false);
    }

    public final AztecDetectorResult detect(boolean z) throws NotFoundException {
        ResultPoint[] bullsEyeCorners = getBullsEyeCorners(getMatrixCenter());
        if (z) {
            ResultPoint resultPoint = bullsEyeCorners[0];
            bullsEyeCorners[0] = bullsEyeCorners[2];
            bullsEyeCorners[2] = resultPoint;
        }
        extractParameters(bullsEyeCorners);
        AztecDetectorResult aztecDetectorResult = new AztecDetectorResult(sampleGrid(this.image, bullsEyeCorners[this.shift % 4], bullsEyeCorners[(this.shift + 1) % 4], bullsEyeCorners[(this.shift + 2) % 4], bullsEyeCorners[(this.shift + 3) % 4]), getMatrixCornerPoints(bullsEyeCorners), this.compact, this.nbDataBlocks, this.nbLayers);
        return aztecDetectorResult;
    }

    private void extractParameters(ResultPoint[] resultPointArr) throws NotFoundException {
        long j;
        long j2;
        if (!isValid(resultPointArr[0]) || !isValid(resultPointArr[1]) || !isValid(resultPointArr[2]) || !isValid(resultPointArr[3])) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i = this.nbCenterLayers * 2;
        int[] iArr = {sampleLine(resultPointArr[0], resultPointArr[1], i), sampleLine(resultPointArr[1], resultPointArr[2], i), sampleLine(resultPointArr[2], resultPointArr[3], i), sampleLine(resultPointArr[3], resultPointArr[0], i)};
        this.shift = getRotation(iArr, i);
        long j3 = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            int i3 = iArr[(this.shift + i2) % 4];
            if (this.compact) {
                j2 = j3 << 7;
                j = (long) ((i3 >> 1) & 127);
            } else {
                j2 = j3 << 10;
                j = (long) (((i3 >> 2) & 992) + ((i3 >> 1) & 31));
            }
            j3 = j2 + j;
        }
        int correctedParameterData = getCorrectedParameterData(j3, this.compact);
        if (this.compact) {
            this.nbLayers = (correctedParameterData >> 6) + 1;
            this.nbDataBlocks = (correctedParameterData & 63) + 1;
            return;
        }
        this.nbLayers = (correctedParameterData >> 11) + 1;
        this.nbDataBlocks = (correctedParameterData & UCCore.SPEEDUP_DEXOPT_POLICY_ALL) + 1;
    }

    private static int getRotation(int[] iArr, int i) throws NotFoundException {
        int i2 = 0;
        for (int i3 : iArr) {
            i2 = (i2 << 3) + ((i3 >> (i - 2)) << 1) + (i3 & 1);
        }
        int i4 = ((i2 & 1) << 11) + (i2 >> 1);
        for (int i5 = 0; i5 < 4; i5++) {
            if (Integer.bitCount(EXPECTED_CORNER_BITS[i5] ^ i4) <= 2) {
                return i5;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int getCorrectedParameterData(long j, boolean z) throws NotFoundException {
        int i;
        int i2;
        if (z) {
            i = 7;
            i2 = 2;
        } else {
            i = 10;
            i2 = 4;
        }
        int i3 = i - i2;
        int[] iArr = new int[i];
        for (int i4 = i - 1; i4 >= 0; i4--) {
            iArr[i4] = ((int) j) & 15;
            j >>= 4;
        }
        try {
            new ReedSolomonDecoder(GenericGF.AZTEC_PARAM).decode(iArr, i3);
            int i5 = 0;
            for (int i6 = 0; i6 < i2; i6++) {
                i5 = (i5 << 4) + iArr[i6];
            }
            return i5;
        } catch (ReedSolomonException unused) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    private ResultPoint[] getBullsEyeCorners(Point point) throws NotFoundException {
        this.nbCenterLayers = 1;
        Point point2 = point;
        Point point3 = point2;
        Point point4 = point3;
        boolean z = true;
        while (this.nbCenterLayers < 9) {
            Point firstDifferent = getFirstDifferent(point, z, 1, -1);
            Point firstDifferent2 = getFirstDifferent(point2, z, 1, 1);
            Point firstDifferent3 = getFirstDifferent(point3, z, -1, 1);
            Point firstDifferent4 = getFirstDifferent(point4, z, -1, -1);
            if (this.nbCenterLayers > 2) {
                double distance = (double) ((distance(firstDifferent4, firstDifferent) * ((float) this.nbCenterLayers)) / (distance(point4, point) * ((float) (this.nbCenterLayers + 2))));
                if (distance < 0.75d || distance > 1.25d || !isWhiteOrBlackRectangle(firstDifferent, firstDifferent2, firstDifferent3, firstDifferent4)) {
                    break;
                }
            }
            z = !z;
            this.nbCenterLayers++;
            point4 = firstDifferent4;
            point = firstDifferent;
            point2 = firstDifferent2;
            point3 = firstDifferent3;
        }
        if (this.nbCenterLayers == 5 || this.nbCenterLayers == 7) {
            this.compact = this.nbCenterLayers == 5;
            return expandSquare(new ResultPoint[]{new ResultPoint(((float) point.getX()) + 0.5f, ((float) point.getY()) - 0.5f), new ResultPoint(((float) point2.getX()) + 0.5f, ((float) point2.getY()) + 0.5f), new ResultPoint(((float) point3.getX()) - 0.5f, ((float) point3.getY()) + 0.5f), new ResultPoint(((float) point4.getX()) - 0.5f, ((float) point4.getY()) - 0.5f)}, (float) ((this.nbCenterLayers * 2) - 3), (float) (this.nbCenterLayers * 2));
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private Point getMatrixCenter() {
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        ResultPoint resultPoint3;
        ResultPoint resultPoint4;
        ResultPoint resultPoint5;
        ResultPoint resultPoint6;
        ResultPoint resultPoint7;
        ResultPoint resultPoint8;
        try {
            ResultPoint[] detect = new WhiteRectangleDetector(this.image).detect();
            resultPoint3 = detect[0];
            resultPoint2 = detect[1];
            resultPoint = detect[2];
            resultPoint4 = detect[3];
        } catch (NotFoundException unused) {
            int width = this.image.getWidth() / 2;
            int height = this.image.getHeight() / 2;
            int i = width + 7;
            int i2 = height - 7;
            ResultPoint resultPoint9 = getFirstDifferent(new Point(i, i2), false, 1, -1).toResultPoint();
            int i3 = height + 7;
            ResultPoint resultPoint10 = getFirstDifferent(new Point(i, i3), false, 1, 1).toResultPoint();
            int i4 = width - 7;
            ResultPoint resultPoint11 = getFirstDifferent(new Point(i4, i3), false, -1, 1).toResultPoint();
            resultPoint4 = getFirstDifferent(new Point(i4, i2), false, -1, -1).toResultPoint();
            ResultPoint resultPoint12 = resultPoint10;
            resultPoint = resultPoint11;
            resultPoint3 = resultPoint9;
            resultPoint2 = resultPoint12;
        }
        int round = MathUtils.round((((resultPoint3.getX() + resultPoint4.getX()) + resultPoint2.getX()) + resultPoint.getX()) / 4.0f);
        int round2 = MathUtils.round((((resultPoint3.getY() + resultPoint4.getY()) + resultPoint2.getY()) + resultPoint.getY()) / 4.0f);
        try {
            ResultPoint[] detect2 = new WhiteRectangleDetector(this.image, 15, round, round2).detect();
            resultPoint6 = detect2[0];
            resultPoint5 = detect2[1];
            resultPoint7 = detect2[2];
            resultPoint8 = detect2[3];
        } catch (NotFoundException unused2) {
            int i5 = round + 7;
            int i6 = round2 - 7;
            resultPoint6 = getFirstDifferent(new Point(i5, i6), false, 1, -1).toResultPoint();
            int i7 = round2 + 7;
            resultPoint5 = getFirstDifferent(new Point(i5, i7), false, 1, 1).toResultPoint();
            int i8 = round - 7;
            resultPoint7 = getFirstDifferent(new Point(i8, i7), false, -1, 1).toResultPoint();
            resultPoint8 = getFirstDifferent(new Point(i8, i6), false, -1, -1).toResultPoint();
        }
        return new Point(MathUtils.round((((resultPoint6.getX() + resultPoint8.getX()) + resultPoint5.getX()) + resultPoint7.getX()) / 4.0f), MathUtils.round((((resultPoint6.getY() + resultPoint8.getY()) + resultPoint5.getY()) + resultPoint7.getY()) / 4.0f));
    }

    private ResultPoint[] getMatrixCornerPoints(ResultPoint[] resultPointArr) {
        return expandSquare(resultPointArr, (float) (this.nbCenterLayers * 2), (float) getDimension());
    }

    private BitMatrix sampleGrid(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) throws NotFoundException {
        GridSampler instance = GridSampler.getInstance();
        int dimension = getDimension();
        float f = ((float) dimension) / 2.0f;
        float f2 = f - ((float) this.nbCenterLayers);
        float f3 = f + ((float) this.nbCenterLayers);
        return instance.sampleGrid(bitMatrix, dimension, dimension, f2, f2, f3, f2, f3, f3, f2, f3, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint4.getX(), resultPoint4.getY());
    }

    private int sampleLine(ResultPoint resultPoint, ResultPoint resultPoint2, int i) {
        float distance = distance(resultPoint, resultPoint2);
        float f = distance / ((float) i);
        float x = resultPoint.getX();
        float y = resultPoint.getY();
        float x2 = ((resultPoint2.getX() - resultPoint.getX()) * f) / distance;
        float y2 = (f * (resultPoint2.getY() - resultPoint.getY())) / distance;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            float f2 = (float) i3;
            if (this.image.get(MathUtils.round((f2 * x2) + x), MathUtils.round((f2 * y2) + y))) {
                i2 |= 1 << ((i - i3) - 1);
            }
        }
        return i2;
    }

    private boolean isWhiteOrBlackRectangle(Point point, Point point2, Point point3, Point point4) {
        Point point5 = new Point(point.getX() - 3, point.getY() + 3);
        Point point6 = new Point(point2.getX() - 3, point2.getY() - 3);
        Point point7 = new Point(point3.getX() + 3, point3.getY() - 3);
        Point point8 = new Point(point4.getX() + 3, point4.getY() + 3);
        int color = getColor(point8, point5);
        if (color != 0 && getColor(point5, point6) == color && getColor(point6, point7) == color && getColor(point7, point8) == color) {
            return true;
        }
        return false;
    }

    private int getColor(Point point, Point point2) {
        float distance = distance(point, point2);
        float x = ((float) (point2.getX() - point.getX())) / distance;
        float y = ((float) (point2.getY() - point.getY())) / distance;
        boolean z = this.image.get(point.getX(), point.getY());
        int ceil = (int) Math.ceil((double) distance);
        boolean z2 = false;
        float x2 = (float) point.getX();
        float y2 = (float) point.getY();
        int i = 0;
        for (int i2 = 0; i2 < ceil; i2++) {
            x2 += x;
            y2 += y;
            if (this.image.get(MathUtils.round(x2), MathUtils.round(y2)) != z) {
                i++;
            }
        }
        float f = ((float) i) / distance;
        if (f > 0.1f && f < 0.9f) {
            return 0;
        }
        if (f <= 0.1f) {
            z2 = true;
        }
        return z2 == z ? 1 : -1;
    }

    private Point getFirstDifferent(Point point, boolean z, int i, int i2) {
        int x = point.getX() + i;
        int y = point.getY();
        while (true) {
            y += i2;
            if (!isValid(x, y) || this.image.get(x, y) != z) {
                int i3 = x - i;
                int i4 = y - i2;
            } else {
                x += i;
            }
        }
        int i32 = x - i;
        int i42 = y - i2;
        while (isValid(i32, i42) && this.image.get(i32, i42) == z) {
            i32 += i;
        }
        int i5 = i32 - i;
        while (isValid(i5, i42) && this.image.get(i5, i42) == z) {
            i42 += i2;
        }
        return new Point(i5, i42 - i2);
    }

    private static ResultPoint[] expandSquare(ResultPoint[] resultPointArr, float f, float f2) {
        float f3 = f2 / (f * 2.0f);
        float x = (resultPointArr[0].getX() + resultPointArr[2].getX()) / 2.0f;
        float y = (resultPointArr[0].getY() + resultPointArr[2].getY()) / 2.0f;
        float x2 = (resultPointArr[0].getX() - resultPointArr[2].getX()) * f3;
        float y2 = (resultPointArr[0].getY() - resultPointArr[2].getY()) * f3;
        ResultPoint resultPoint = new ResultPoint(x + x2, y + y2);
        ResultPoint resultPoint2 = new ResultPoint(x - x2, y - y2);
        float x3 = (resultPointArr[1].getX() + resultPointArr[3].getX()) / 2.0f;
        float y3 = (resultPointArr[1].getY() + resultPointArr[3].getY()) / 2.0f;
        float x4 = (resultPointArr[1].getX() - resultPointArr[3].getX()) * f3;
        float y4 = f3 * (resultPointArr[1].getY() - resultPointArr[3].getY());
        return new ResultPoint[]{resultPoint, new ResultPoint(x3 + x4, y3 + y4), resultPoint2, new ResultPoint(x3 - x4, y3 - y4)};
    }

    private boolean isValid(int i, int i2) {
        return i >= 0 && i < this.image.getWidth() && i2 > 0 && i2 < this.image.getHeight();
    }

    private boolean isValid(ResultPoint resultPoint) {
        return isValid(MathUtils.round(resultPoint.getX()), MathUtils.round(resultPoint.getY()));
    }

    private static float distance(Point point, Point point2) {
        return MathUtils.distance(point.getX(), point.getY(), point2.getX(), point2.getY());
    }

    private static float distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.distance(resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    private int getDimension() {
        if (this.compact) {
            return (this.nbLayers * 4) + 11;
        }
        if (this.nbLayers <= 4) {
            return (this.nbLayers * 4) + 15;
        }
        return (this.nbLayers * 4) + ((((this.nbLayers - 4) / 8) + 1) * 2) + 15;
    }
}
