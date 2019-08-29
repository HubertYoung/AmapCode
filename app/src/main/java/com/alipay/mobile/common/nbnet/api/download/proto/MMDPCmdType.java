package com.alipay.mobile.common.nbnet.api.download.proto;

import com.squareup.wire.ProtoEnum;

public enum MMDPCmdType implements ProtoEnum {
    FILE_DOWNLOAD(0),
    IMAGE_ZOOM(1),
    IMAGE_MARK(2);
    
    private final int value;

    private MMDPCmdType(int value2) {
        this.value = value2;
    }

    public final int getValue() {
        return this.value;
    }
}
