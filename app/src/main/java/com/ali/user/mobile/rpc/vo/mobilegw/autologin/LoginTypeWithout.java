package com.ali.user.mobile.rpc.vo.mobilegw.autologin;

import com.alipay.android.phone.inside.protobuf.wire.ProtoEnum;

public enum LoginTypeWithout implements ProtoEnum {
    alipay(1),
    taobao(2);
    
    private final int value;

    private LoginTypeWithout(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
