package com.ali.user.mobile.rpc.vo.mobilegw.login;

import com.alipay.android.phone.inside.protobuf.wire.ProtoEnum;

public enum LoginType implements ProtoEnum {
    alipay(1),
    taobao(2);
    
    private final int value;

    private LoginType(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
