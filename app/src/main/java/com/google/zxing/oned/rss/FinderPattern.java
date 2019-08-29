package com.google.zxing.oned.rss;

import com.google.zxing.ResultPoint;

public final class FinderPattern {
    private final ResultPoint[] resultPoints;
    private final int[] startEnd;
    private final int value;

    public FinderPattern(int i, int[] iArr, int i2, int i3, int i4) {
        this.value = i;
        this.startEnd = iArr;
        float f = (float) i4;
        this.resultPoints = new ResultPoint[]{new ResultPoint((float) i2, f), new ResultPoint((float) i3, f)};
    }

    public final int getValue() {
        return this.value;
    }

    public final int[] getStartEnd() {
        return this.startEnd;
    }

    public final ResultPoint[] getResultPoints() {
        return this.resultPoints;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof FinderPattern) && this.value == ((FinderPattern) obj).value) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.value;
    }
}
