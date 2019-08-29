package com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode;

public final class APCenterCropMode extends APMode {
    public final int height;
    public final int width;

    public APCenterCropMode(int width2, int height2) {
        super(2);
        this.width = width2;
        this.height = height2;
    }
}
