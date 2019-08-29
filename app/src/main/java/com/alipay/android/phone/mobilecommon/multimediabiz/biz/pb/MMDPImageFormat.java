package com.alipay.android.phone.mobilecommon.multimediabiz.biz.pb;

import com.squareup.wire.ProtoEnum;

public enum MMDPImageFormat implements ProtoEnum {
    NOFORMAT(0),
    PNG(1),
    JPG(2),
    WEBP(3),
    TIFF(4),
    BMP(5);
    
    private final int value;

    private MMDPImageFormat(int value2) {
        this.value = value2;
    }

    public final int getValue() {
        return this.value;
    }
}
