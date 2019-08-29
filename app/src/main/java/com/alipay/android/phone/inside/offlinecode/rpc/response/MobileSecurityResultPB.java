package com.alipay.android.phone.inside.offlinecode.rpc.response;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class MobileSecurityResultPB extends Message {
    public static final String DEFAULT_MESSAGE = "";
    public static final String DEFAULT_RESULTCODE = "";
    public static final Boolean DEFAULT_SUCCESS = Boolean.FALSE;
    public static final int TAG_MESSAGE = 3;
    public static final int TAG_RESULTCODE = 2;
    public static final int TAG_SUCCESS = 1;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String message;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String resultCode;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean success;

    public MobileSecurityResultPB(MobileSecurityResultPB mobileSecurityResultPB) {
        super(mobileSecurityResultPB);
        if (mobileSecurityResultPB != null) {
            this.success = mobileSecurityResultPB.success;
            this.resultCode = mobileSecurityResultPB.resultCode;
            this.message = mobileSecurityResultPB.message;
        }
    }

    public MobileSecurityResultPB() {
    }

    public final MobileSecurityResultPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.success = (Boolean) obj;
                break;
            case 2:
                this.resultCode = (String) obj;
                break;
            case 3:
                this.message = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MobileSecurityResultPB)) {
            return false;
        }
        MobileSecurityResultPB mobileSecurityResultPB = (MobileSecurityResultPB) obj;
        return equals((Object) this.success, (Object) mobileSecurityResultPB.success) && equals((Object) this.resultCode, (Object) mobileSecurityResultPB.resultCode) && equals((Object) this.message, (Object) mobileSecurityResultPB.message);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((this.success != null ? this.success.hashCode() : 0) * 37) + (this.resultCode != null ? this.resultCode.hashCode() : 0)) * 37;
        if (this.message != null) {
            i2 = this.message.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
