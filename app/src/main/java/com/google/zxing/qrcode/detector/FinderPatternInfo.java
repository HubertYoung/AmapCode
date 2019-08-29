package com.google.zxing.qrcode.detector;

public final class FinderPatternInfo {
    private final FinderPattern bottomLeft;
    private final FinderPattern topLeft;
    private final FinderPattern topRight;

    public FinderPatternInfo(FinderPattern[] finderPatternArr) {
        this.bottomLeft = finderPatternArr[0];
        this.topLeft = finderPatternArr[1];
        this.topRight = finderPatternArr[2];
    }

    public final FinderPattern getBottomLeft() {
        return this.bottomLeft;
    }

    public final FinderPattern getTopLeft() {
        return this.topLeft;
    }

    public final FinderPattern getTopRight() {
        return this.topRight;
    }
}
