package com.google.zxing.datamatrix.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class Detector {
    private final BitMatrix image;
    private final WhiteRectangleDetector rectangleDetector;

    static final class ResultPointsAndTransitions {
        private final ResultPoint from;
        private final ResultPoint to;
        private final int transitions;

        private ResultPointsAndTransitions(ResultPoint resultPoint, ResultPoint resultPoint2, int i) {
            this.from = resultPoint;
            this.to = resultPoint2;
            this.transitions = i;
        }

        /* synthetic */ ResultPointsAndTransitions(ResultPoint resultPoint, ResultPoint resultPoint2, int i, ResultPointsAndTransitions resultPointsAndTransitions) {
            this(resultPoint, resultPoint2, i);
        }

        /* access modifiers changed from: 0000 */
        public final ResultPoint getFrom() {
            return this.from;
        }

        /* access modifiers changed from: 0000 */
        public final ResultPoint getTo() {
            return this.to;
        }

        /* access modifiers changed from: 0000 */
        public final int getTransitions() {
            return this.transitions;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.from);
            sb.append("/");
            sb.append(this.to);
            sb.append('/');
            sb.append(this.transitions);
            return sb.toString();
        }
    }

    static final class ResultPointsAndTransitionsComparator implements Serializable, Comparator<ResultPointsAndTransitions> {
        private ResultPointsAndTransitionsComparator() {
        }

        /* synthetic */ ResultPointsAndTransitionsComparator(ResultPointsAndTransitionsComparator resultPointsAndTransitionsComparator) {
            this();
        }

        public final int compare(ResultPointsAndTransitions resultPointsAndTransitions, ResultPointsAndTransitions resultPointsAndTransitions2) {
            return resultPointsAndTransitions.getTransitions() - resultPointsAndTransitions2.getTransitions();
        }
    }

    public Detector(BitMatrix bitMatrix) throws NotFoundException {
        this.image = bitMatrix;
        this.rectangleDetector = new WhiteRectangleDetector(bitMatrix);
    }

    public final DetectorResult detect() throws NotFoundException {
        ResultPoint resultPoint;
        BitMatrix bitMatrix;
        ResultPoint[] detect = this.rectangleDetector.detect();
        ResultPoint resultPoint2 = detect[0];
        ResultPoint resultPoint3 = detect[1];
        ResultPoint resultPoint4 = detect[2];
        ResultPoint resultPoint5 = detect[3];
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(transitionsBetween(resultPoint2, resultPoint3));
        arrayList.add(transitionsBetween(resultPoint2, resultPoint4));
        arrayList.add(transitionsBetween(resultPoint3, resultPoint5));
        arrayList.add(transitionsBetween(resultPoint4, resultPoint5));
        ResultPoint resultPoint6 = null;
        Collections.sort(arrayList, new ResultPointsAndTransitionsComparator(null));
        ResultPointsAndTransitions resultPointsAndTransitions = (ResultPointsAndTransitions) arrayList.get(0);
        ResultPointsAndTransitions resultPointsAndTransitions2 = (ResultPointsAndTransitions) arrayList.get(1);
        HashMap hashMap = new HashMap();
        increment(hashMap, resultPointsAndTransitions.getFrom());
        increment(hashMap, resultPointsAndTransitions.getTo());
        increment(hashMap, resultPointsAndTransitions2.getFrom());
        increment(hashMap, resultPointsAndTransitions2.getTo());
        ResultPoint resultPoint7 = null;
        ResultPoint resultPoint8 = null;
        for (Entry entry : hashMap.entrySet()) {
            ResultPoint resultPoint9 = (ResultPoint) entry.getKey();
            if (((Integer) entry.getValue()).intValue() == 2) {
                resultPoint7 = resultPoint9;
            } else if (resultPoint6 == null) {
                resultPoint6 = resultPoint9;
            } else {
                resultPoint8 = resultPoint9;
            }
        }
        if (resultPoint6 == null || resultPoint7 == null || resultPoint8 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        ResultPoint[] resultPointArr = {resultPoint6, resultPoint7, resultPoint8};
        ResultPoint.orderBestPatterns(resultPointArr);
        ResultPoint resultPoint10 = resultPointArr[0];
        ResultPoint resultPoint11 = resultPointArr[1];
        ResultPoint resultPoint12 = resultPointArr[2];
        ResultPoint resultPoint13 = !hashMap.containsKey(resultPoint2) ? resultPoint2 : !hashMap.containsKey(resultPoint3) ? resultPoint3 : !hashMap.containsKey(resultPoint4) ? resultPoint4 : resultPoint5;
        int transitions = transitionsBetween(resultPoint12, resultPoint13).getTransitions();
        int transitions2 = transitionsBetween(resultPoint10, resultPoint13).getTransitions();
        if ((transitions & 1) == 1) {
            transitions++;
        }
        int i = transitions + 2;
        if ((transitions2 & 1) == 1) {
            transitions2++;
        }
        int i2 = transitions2 + 2;
        if (i * 4 >= i2 * 7 || i2 * 4 >= i * 7) {
            resultPoint = resultPoint12;
            ResultPoint correctTopRightRectangular = correctTopRightRectangular(resultPoint11, resultPoint10, resultPoint12, resultPoint13, i, i2);
            if (correctTopRightRectangular != null) {
                resultPoint13 = correctTopRightRectangular;
            }
            int transitions3 = transitionsBetween(resultPoint, resultPoint13).getTransitions();
            int transitions4 = transitionsBetween(resultPoint10, resultPoint13).getTransitions();
            if ((transitions3 & 1) == 1) {
                transitions3++;
            }
            int i3 = transitions3;
            if ((transitions4 & 1) == 1) {
                transitions4++;
            }
            bitMatrix = sampleGrid(this.image, resultPoint, resultPoint11, resultPoint10, resultPoint13, i3, transitions4);
        } else {
            ResultPoint correctTopRight = correctTopRight(resultPoint11, resultPoint10, resultPoint12, resultPoint13, Math.min(i2, i));
            if (correctTopRight != null) {
                resultPoint13 = correctTopRight;
            }
            int max = Math.max(transitionsBetween(resultPoint12, resultPoint13).getTransitions(), transitionsBetween(resultPoint10, resultPoint13).getTransitions()) + 1;
            if ((max & 1) == 1) {
                max++;
            }
            int i4 = max;
            bitMatrix = sampleGrid(this.image, resultPoint12, resultPoint11, resultPoint10, resultPoint13, i4, i4);
            resultPoint = resultPoint12;
        }
        return new DetectorResult(bitMatrix, new ResultPoint[]{resultPoint, resultPoint11, resultPoint10, resultPoint13});
    }

    private ResultPoint correctTopRightRectangular(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i, int i2) {
        float distance = ((float) distance(resultPoint, resultPoint2)) / ((float) i);
        float distance2 = (float) distance(resultPoint3, resultPoint4);
        ResultPoint resultPoint5 = new ResultPoint(resultPoint4.getX() + (((resultPoint4.getX() - resultPoint3.getX()) / distance2) * distance), resultPoint4.getY() + (distance * ((resultPoint4.getY() - resultPoint3.getY()) / distance2)));
        float distance3 = ((float) distance(resultPoint, resultPoint3)) / ((float) i2);
        float distance4 = (float) distance(resultPoint2, resultPoint4);
        ResultPoint resultPoint6 = new ResultPoint(resultPoint4.getX() + (((resultPoint4.getX() - resultPoint2.getX()) / distance4) * distance3), resultPoint4.getY() + (distance3 * ((resultPoint4.getY() - resultPoint2.getY()) / distance4)));
        if (isValid(resultPoint5)) {
            return (isValid(resultPoint6) && Math.abs(i - transitionsBetween(resultPoint3, resultPoint5).getTransitions()) + Math.abs(i2 - transitionsBetween(resultPoint2, resultPoint5).getTransitions()) > Math.abs(i - transitionsBetween(resultPoint3, resultPoint6).getTransitions()) + Math.abs(i2 - transitionsBetween(resultPoint2, resultPoint6).getTransitions())) ? resultPoint6 : resultPoint5;
        }
        if (isValid(resultPoint6)) {
            return resultPoint6;
        }
        return null;
    }

    private ResultPoint correctTopRight(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i) {
        float f = (float) i;
        float distance = ((float) distance(resultPoint, resultPoint2)) / f;
        float distance2 = (float) distance(resultPoint3, resultPoint4);
        ResultPoint resultPoint5 = new ResultPoint(resultPoint4.getX() + (((resultPoint4.getX() - resultPoint3.getX()) / distance2) * distance), resultPoint4.getY() + (distance * ((resultPoint4.getY() - resultPoint3.getY()) / distance2)));
        float distance3 = ((float) distance(resultPoint, resultPoint3)) / f;
        float distance4 = (float) distance(resultPoint2, resultPoint4);
        ResultPoint resultPoint6 = new ResultPoint(resultPoint4.getX() + (((resultPoint4.getX() - resultPoint2.getX()) / distance4) * distance3), resultPoint4.getY() + (distance3 * ((resultPoint4.getY() - resultPoint2.getY()) / distance4)));
        if (isValid(resultPoint5)) {
            return (isValid(resultPoint6) && Math.abs(transitionsBetween(resultPoint3, resultPoint5).getTransitions() - transitionsBetween(resultPoint2, resultPoint5).getTransitions()) > Math.abs(transitionsBetween(resultPoint3, resultPoint6).getTransitions() - transitionsBetween(resultPoint2, resultPoint6).getTransitions())) ? resultPoint6 : resultPoint5;
        }
        if (isValid(resultPoint6)) {
            return resultPoint6;
        }
        return null;
    }

    private boolean isValid(ResultPoint resultPoint) {
        return resultPoint.getX() >= 0.0f && resultPoint.getX() < ((float) this.image.getWidth()) && resultPoint.getY() > 0.0f && resultPoint.getY() < ((float) this.image.getHeight());
    }

    private static int distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2));
    }

    private static void increment(Map<ResultPoint, Integer> map, ResultPoint resultPoint) {
        Integer num = map.get(resultPoint);
        int i = 1;
        if (num != null) {
            i = 1 + num.intValue();
        }
        map.put(resultPoint, Integer.valueOf(i));
    }

    private static BitMatrix sampleGrid(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i, int i2) throws NotFoundException {
        int i3 = i;
        float f = ((float) i3) - 0.5f;
        int i4 = i2;
        float f2 = ((float) i4) - 0.5f;
        return GridSampler.getInstance().sampleGrid(bitMatrix, i3, i4, 0.5f, 0.5f, f, 0.5f, f, f2, 0.5f, f2, resultPoint.getX(), resultPoint.getY(), resultPoint4.getX(), resultPoint4.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    private ResultPointsAndTransitions transitionsBetween(ResultPoint resultPoint, ResultPoint resultPoint2) {
        int x = (int) resultPoint.getX();
        int y = (int) resultPoint.getY();
        int x2 = (int) resultPoint2.getX();
        int y2 = (int) resultPoint2.getY();
        int i = 0;
        boolean z = Math.abs(y2 - y) > Math.abs(x2 - x);
        if (z) {
            int i2 = y;
            y = x;
            x = i2;
            int i3 = y2;
            y2 = x2;
            x2 = i3;
        }
        int abs = Math.abs(x2 - x);
        int abs2 = Math.abs(y2 - y);
        int i4 = (-abs) / 2;
        int i5 = -1;
        int i6 = y < y2 ? 1 : -1;
        if (x < x2) {
            i5 = 1;
        }
        boolean z2 = this.image.get(z ? y : x, z ? x : y);
        while (x != x2) {
            boolean z3 = this.image.get(z ? y : x, z ? x : y);
            if (z3 != z2) {
                i++;
                z2 = z3;
            }
            i4 += abs2;
            if (i4 > 0) {
                if (y == y2) {
                    break;
                }
                y += i6;
                i4 -= abs;
            }
            ResultPoint resultPoint3 = resultPoint;
            ResultPoint resultPoint4 = resultPoint2;
            x += i5;
        }
        return new ResultPointsAndTransitions(resultPoint, resultPoint2, i, null);
    }
}
