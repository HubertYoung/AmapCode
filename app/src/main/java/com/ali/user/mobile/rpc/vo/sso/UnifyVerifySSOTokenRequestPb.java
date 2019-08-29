package com.ali.user.mobile.rpc.vo.sso;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class UnifyVerifySSOTokenRequestPb extends Message {
    public static final int TAG_VERIFYSSOTOKENREQUEST = 2;
    public static final int TAG_VERIFYTAOBAOSSOTOKENREQ = 1;
    @ProtoField(tag = 2)
    public VerifySsoTokenRequestPb verifySsoTokenRequest;

    public UnifyVerifySSOTokenRequestPb(UnifyVerifySSOTokenRequestPb unifyVerifySSOTokenRequestPb) {
        super(unifyVerifySSOTokenRequestPb);
        if (unifyVerifySSOTokenRequestPb != null) {
            this.verifySsoTokenRequest = unifyVerifySSOTokenRequestPb.verifySsoTokenRequest;
        }
    }

    public UnifyVerifySSOTokenRequestPb() {
    }

    public final UnifyVerifySSOTokenRequestPb fillTagValue(int i, Object obj) {
        if (i == 2) {
            this.verifySsoTokenRequest = (VerifySsoTokenRequestPb) obj;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UnifyVerifySSOTokenRequestPb)) {
            return false;
        }
        return equals((Object) this.verifySsoTokenRequest, (Object) ((UnifyVerifySSOTokenRequestPb) obj).verifySsoTokenRequest);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i == 0) {
            i = this.verifySsoTokenRequest != null ? this.verifySsoTokenRequest.hashCode() : 0;
            this.hashCode = i;
        }
        return i;
    }
}
