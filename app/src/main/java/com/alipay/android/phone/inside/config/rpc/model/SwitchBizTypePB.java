package com.alipay.android.phone.inside.config.rpc.model;

import com.alipay.android.phone.inside.protobuf.wire.ProtoEnum;

public enum SwitchBizTypePB implements ProtoEnum {
    ALL(0),
    SWITCH(1),
    CONFIG(2);
    
    private final int value;

    private SwitchBizTypePB(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
