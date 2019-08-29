package com.alipay.android.phone.mobilecommon.multimediabiz.biz.pb;

import com.squareup.wire.ProtoEnum;

public enum MMDPImageScale implements ProtoEnum {
    NOSCALE(0),
    SCALE(1),
    SCALEANDTRIM(2),
    TRIM(3);
    
    private final int value;

    private MMDPImageScale(int value2) {
        this.value = value2;
    }

    public final int getValue() {
        return this.value;
    }
}
