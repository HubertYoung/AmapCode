package com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode;

public final class APSpecificCropMode extends APMode {
    public final int height;
    public final int width;
    public final int x;
    public final int y;

    public APSpecificCropMode(int x2, int y2, int width2, int height2) {
        super(3);
        this.x = x2;
        this.y = y2;
        this.width = width2;
        this.height = height2;
    }
}
