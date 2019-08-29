package com.alipay.mobileapp.biz.rpc.mdap.vo.pb;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class UnifyCustomMdapResPb extends Message {
    public static final String DEFAULT_MEMO = "";
    public static final Long DEFAULT_RESULTSTATUS = Long.valueOf(0);
    public static final int TAG_MEMO = 2;
    public static final int TAG_RESULTSTATUS = 1;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String memo;
    @ProtoField(tag = 1, type = Datatype.INT64)
    public Long resultStatus;

    public UnifyCustomMdapResPb(UnifyCustomMdapResPb unifyCustomMdapResPb) {
        super(unifyCustomMdapResPb);
        if (unifyCustomMdapResPb != null) {
            this.resultStatus = unifyCustomMdapResPb.resultStatus;
            this.memo = unifyCustomMdapResPb.memo;
        }
    }

    public UnifyCustomMdapResPb() {
    }

    public final UnifyCustomMdapResPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.resultStatus = (Long) obj;
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
        if (!(obj instanceof UnifyCustomMdapResPb)) {
            return false;
        }
        UnifyCustomMdapResPb unifyCustomMdapResPb = (UnifyCustomMdapResPb) obj;
        return equals((Object) this.resultStatus, (Object) unifyCustomMdapResPb.resultStatus) && equals((Object) this.memo, (Object) unifyCustomMdapResPb.memo);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (this.resultStatus != null ? this.resultStatus.hashCode() : 0) * 37;
        if (this.memo != null) {
            i2 = this.memo.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
