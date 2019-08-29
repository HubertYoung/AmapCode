package com.alipay.mobile.common.nbnet.api.download.proto;

import com.squareup.wire.ProtoEnum;

public enum MMDPSourceType implements ProtoEnum {
    FILEID(0),
    TFS(1);
    
    private final int value;

    private MMDPSourceType(int value2) {
        this.value = value2;
    }

    public final int getValue() {
        return this.value;
    }
}
