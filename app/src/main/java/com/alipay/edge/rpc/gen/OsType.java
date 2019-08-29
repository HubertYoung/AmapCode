package com.alipay.edge.rpc.gen;

import com.alipay.android.phone.inside.protobuf.wire.ProtoEnum;

public enum OsType implements ProtoEnum {
    ANDROID(0),
    IOS(1);
    
    private final int value;

    private OsType(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
