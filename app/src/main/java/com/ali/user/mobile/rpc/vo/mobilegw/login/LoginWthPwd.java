package com.ali.user.mobile.rpc.vo.mobilegw.login;

import com.alipay.android.phone.inside.protobuf.wire.ProtoEnum;

public enum LoginWthPwd implements ProtoEnum {
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
    withlogintoken(11),
    afterreg(12),
    withauthtoken(13),
    withtbsso(14),
    withonekeytoken(15),
    withsndmsg(16),
    withopenauthtoken(17);
    
    private final int value;

    private LoginWthPwd(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
