package com.alipay.android.phone.mobilecommon.multimedia.graphics.load;

public class Base64Optimization {
    public final int offset;
    public final int parts;
    public final int stride;

    public Base64Optimization(int offset2, int stride2, int parts2) {
        this.offset = offset2;
        this.stride = stride2;
        this.parts = parts2;
    }

    public static Base64Optimization createDefault() {
        return new Base64Optimization(150, 50, 3);
    }
}
