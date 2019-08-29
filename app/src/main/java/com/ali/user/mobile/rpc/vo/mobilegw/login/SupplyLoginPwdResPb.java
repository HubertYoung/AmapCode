package com.ali.user.mobile.rpc.vo.mobilegw.login;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class SupplyLoginPwdResPb extends Message {
    public static final String DEFAULT_MEMO = "";
    public static final String DEFAULT_RESULTCODE = "";
    public static final int TAG_MEMO = 2;
    public static final int TAG_RESULTCODE = 1;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String memo;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String resultCode;

    public SupplyLoginPwdResPb(SupplyLoginPwdResPb supplyLoginPwdResPb) {
        super(supplyLoginPwdResPb);
        if (supplyLoginPwdResPb != null) {
            this.resultCode = supplyLoginPwdResPb.resultCode;
            this.memo = supplyLoginPwdResPb.memo;
        }
    }

    public SupplyLoginPwdResPb() {
    }

    public final SupplyLoginPwdResPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.resultCode = (String) obj;
                break;
            case 2:
                this.memo = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SupplyLoginPwdResPb)) {
            return false;
        }
        SupplyLoginPwdResPb supplyLoginPwdResPb = (SupplyLoginPwdResPb) obj;
        return equals((Object) this.resultCode, (Object) supplyLoginPwdResPb.resultCode) && equals((Object) this.memo, (Object) supplyLoginPwdResPb.memo);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (this.resultCode != null ? this.resultCode.hashCode() : 0) * 37;
        if (this.memo != null) {
            i2 = this.memo.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
