package com.alipay.mobile.common.nbnet.api.download.proto;

import com.squareup.wire.ProtoEnum;

public enum MMDPResType implements ProtoEnum {
    FILE(0),
    IMAGE(1),
    VIDEO(2),
    AUDIO(3);
    
    private final int value;

    private MMDPResType(int value2) {
        this.value = value2;
    }

    public final int getValue() {
        return this.value;
    }
}
