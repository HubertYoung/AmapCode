package com.alipay.multimedia.img;

public class ImageSize {
    public int height;
    public int width;

    public ImageSize(int width2, int height2) {
        this.width = width2;
        this.height = height2;
    }

    public ImageSize() {
    }

    public String toString() {
        return "ImageSize{width=" + this.width + ", height=" + this.height + '}';
    }
}
