package com.alipay.multimedia.img.encode.mode;

public final class MinLenMode extends Mode {
    public final int len;

    public MinLenMode(int len2) {
        super(1);
        this.len = len2;
    }

    public final String toString() {
        return "MinLenMode{len=" + this.len + '}';
    }
}
