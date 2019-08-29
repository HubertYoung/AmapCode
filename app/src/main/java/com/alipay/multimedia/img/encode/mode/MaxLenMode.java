package com.alipay.multimedia.img.encode.mode;

public final class MaxLenMode extends Mode {
    public final int len;

    public MaxLenMode(int len2) {
        super(0);
        this.len = len2;
    }

    public final String toString() {
        return "MaxLenMode{len=" + this.len + '}';
    }
}
