package com.ali.user.mobile.rpc.vo.mobilegw.login;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class LoginSendMSGResPb extends Message {
    public static final String DEFAULT_MEMO = "";
    public static final String DEFAULT_RESULTCODE = "";
    public static final String DEFAULT_SECURITYID = "";
    public static final String DEFAULT_TOKEN = "";
    public static final int TAG_MEMO = 2;
    public static final int TAG_RESULTCODE = 1;
    public static final int TAG_SECURITYID = 3;
    public static final int TAG_TOKEN = 4;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String memo;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String resultCode;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String securityId;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String token;

    public LoginSendMSGResPb(LoginSendMSGResPb loginSendMSGResPb) {
        super(loginSendMSGResPb);
        if (loginSendMSGResPb != null) {
            this.resultCode = loginSendMSGResPb.resultCode;
            this.memo = loginSendMSGResPb.memo;
            this.securityId = loginSendMSGResPb.securityId;
            this.token = loginSendMSGResPb.token;
        }
    }

    public LoginSendMSGResPb() {
    }

    public final LoginSendMSGResPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.resultCode = (String) obj;
                break;
            case 2:
                this.memo = (String) obj;
                break;
            case 3:
                this.securityId = (String) obj;
                break;
            case 4:
                this.token = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LoginSendMSGResPb)) {
            return false;
        }
        LoginSendMSGResPb loginSendMSGResPb = (LoginSendMSGResPb) obj;
        return equals((Object) this.resultCode, (Object) loginSendMSGResPb.resultCode) && equals((Object) this.memo, (Object) loginSendMSGResPb.memo) && equals((Object) this.securityId, (Object) loginSendMSGResPb.securityId) && equals((Object) this.token, (Object) loginSendMSGResPb.token);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((this.resultCode != null ? this.resultCode.hashCode() : 0) * 37) + (this.memo != null ? this.memo.hashCode() : 0)) * 37) + (this.securityId != null ? this.securityId.hashCode() : 0)) * 37;
        if (this.token != null) {
            i2 = this.token.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
