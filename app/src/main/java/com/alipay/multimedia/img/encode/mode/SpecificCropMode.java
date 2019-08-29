package com.alipay.multimedia.img.encode.mode;

public final class SpecificCropMode extends Mode {
    public final int height;
    public final int width;
    public final int x;
    public final int y;

    public SpecificCropMode(int x2, int y2, int width2, int height2) {
        super(3);
        this.x = x2;
        this.y = y2;
        this.width = width2;
        this.height = height2;
    }

    public final String toString() {
        return "SpecificCropMode{x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + '}';
    }
}
