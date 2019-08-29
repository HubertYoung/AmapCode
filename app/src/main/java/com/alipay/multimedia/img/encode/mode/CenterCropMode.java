package com.alipay.multimedia.img.encode.mode;

public final class CenterCropMode extends Mode {
    public final int height;
    public final int width;

    public CenterCropMode(int width2, int height2) {
        super(2);
        this.width = width2;
        this.height = height2;
    }

    public final String toString() {
        return "CenterCropMode{width=" + this.width + ", height=" + this.height + '}';
    }
}
