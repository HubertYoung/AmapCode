package com.ali.user.mobile.rpc.vo.mobilegw.autologin;

import com.alipay.android.phone.inside.protobuf.wire.ProtoEnum;

public enum LoginWithout implements ProtoEnum {
    withpwd(1),
    without(2),
    withtoken(3),
    withinnertoken(4),
    withmobilepwd(5),
    withsso(6),
    withsndpwd(7),
    withchecktoken(8),
    withface(9),
    withmsg(10),
    withlogintoken(11);
    
    private final int value;

    private LoginWithout(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
