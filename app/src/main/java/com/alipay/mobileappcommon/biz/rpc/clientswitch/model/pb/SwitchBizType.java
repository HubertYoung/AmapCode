package com.alipay.mobileappcommon.biz.rpc.clientswitch.model.pb;

import com.squareup.wire.ProtoEnum;

public enum SwitchBizType implements ProtoEnum {
    ALL(0),
    SWITCH(1),
    CONFIG(2);
    
    private final int a;

    private SwitchBizType(int i) {
        this.a = i;
    }

    public final int getValue() {
        return this.a;
    }
}
