package com.alipay.edge.rpc.gen;

import com.alipay.android.phone.inside.protobuf.wire.ProtoEnum;

public enum EdgeStatus implements ProtoEnum {
    DISABLED(0),
    ENABLED(1);
    
    private final int value;

    private EdgeStatus(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
