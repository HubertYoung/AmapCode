package com.ali.user.mobile.rpc.vo.mobilegw.login;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class LoginSendMSGReqPb extends Message {
    public static final String DEFAULT_APDID = "";
    public static final String DEFAULT_DEVKEYSET = "";
    public static final String DEFAULT_ENVJSON = "";
    public static final List<ExternParams> DEFAULT_EXTERNPARAMS = Collections.emptyList();
    public static final String DEFAULT_LOGINID = "";
    public static final String DEFAULT_PRODUCTID = "";
    public static final String DEFAULT_PRODUCTVERSION = "";
    public static final String DEFAULT_SDKVERSION = "";
    public static final String DEFAULT_SECURITYID = "";
    public static final String DEFAULT_TID = "";
    public static final String DEFAULT_TOKEN = "";
    public static final String DEFAULT_UMIDTOKEN = "";
    public static final String DEFAULT_UTDID = "";
    public static final int TAG_APDID = 8;
    public static final int TAG_DEVKEYSET = 12;
    public static final int TAG_ENVJSON = 2;
    public static final int TAG_EXTERNPARAMS = 10;
    public static final int TAG_LOGINID = 1;
    public static final int TAG_PRODUCTID = 5;
    public static final int TAG_PRODUCTVERSION = 6;
    public static final int TAG_SDKVERSION = 4;
    public static final int TAG_SECURITYID = 3;
    public static final int TAG_TID = 13;
    public static final int TAG_TOKEN = 11;
    public static final int TAG_UMIDTOKEN = 7;
    public static final int TAG_UTDID = 9;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String apdid;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String devKeySet;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String envJson;
    @ProtoField(label = Label.REPEATED, tag = 10)
    public List<ExternParams> externParams;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String loginId;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String productId;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String productVersion;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String sdkVersion;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String securityId;
    @ProtoField(tag = 13, type = Datatype.STRING)
    public String tid;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String token;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String umidtoken;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String utdid;

    public LoginSendMSGReqPb(LoginSendMSGReqPb loginSendMSGReqPb) {
        super(loginSendMSGReqPb);
        if (loginSendMSGReqPb != null) {
            this.loginId = loginSendMSGReqPb.loginId;
            this.envJson = loginSendMSGReqPb.envJson;
            this.securityId = loginSendMSGReqPb.securityId;
            this.sdkVersion = loginSendMSGReqPb.sdkVersion;
            this.productId = loginSendMSGReqPb.productId;
            this.productVersion = loginSendMSGReqPb.productVersion;
            this.umidtoken = loginSendMSGReqPb.umidtoken;
            this.apdid = loginSendMSGReqPb.apdid;
            this.utdid = loginSendMSGReqPb.utdid;
            this.externParams = copyOf(loginSendMSGReqPb.externParams);
            this.token = loginSendMSGReqPb.token;
            this.devKeySet = loginSendMSGReqPb.devKeySet;
            this.tid = loginSendMSGReqPb.tid;
        }
    }

    public LoginSendMSGReqPb() {
    }

    public final LoginSendMSGReqPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.loginId = (String) obj;
                break;
            case 2:
                this.envJson = (String) obj;
                break;
            case 3:
                this.securityId = (String) obj;
                break;
            case 4:
                this.sdkVersion = (String) obj;
                break;
            case 5:
                this.productId = (String) obj;
                break;
            case 6:
                this.productVersion = (String) obj;
                break;
            case 7:
                this.umidtoken = (String) obj;
                break;
            case 8:
                this.apdid = (String) obj;
                break;
            case 9:
                this.utdid = (String) obj;
                break;
            case 10:
                this.externParams = immutableCopyOf((List) obj);
                break;
            case 11:
                this.token = (String) obj;
                break;
            case 12:
                this.devKeySet = (String) obj;
                break;
            case 13:
                this.tid = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LoginSendMSGReqPb)) {
            return false;
        }
        LoginSendMSGReqPb loginSendMSGReqPb = (LoginSendMSGReqPb) obj;
        return equals((Object) this.loginId, (Object) loginSendMSGReqPb.loginId) && equals((Object) this.envJson, (Object) loginSendMSGReqPb.envJson) && equals((Object) this.securityId, (Object) loginSendMSGReqPb.securityId) && equals((Object) this.sdkVersion, (Object) loginSendMSGReqPb.sdkVersion) && equals((Object) this.productId, (Object) loginSendMSGReqPb.productId) && equals((Object) this.productVersion, (Object) loginSendMSGReqPb.productVersion) && equals((Object) this.umidtoken, (Object) loginSendMSGReqPb.umidtoken) && equals((Object) this.apdid, (Object) loginSendMSGReqPb.apdid) && equals((Object) this.utdid, (Object) loginSendMSGReqPb.utdid) && equals(this.externParams, loginSendMSGReqPb.externParams) && equals((Object) this.token, (Object) loginSendMSGReqPb.token) && equals((Object) this.devKeySet, (Object) loginSendMSGReqPb.devKeySet) && equals((Object) this.tid, (Object) loginSendMSGReqPb.tid);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((((((((this.loginId != null ? this.loginId.hashCode() : 0) * 37) + (this.envJson != null ? this.envJson.hashCode() : 0)) * 37) + (this.securityId != null ? this.securityId.hashCode() : 0)) * 37) + (this.sdkVersion != null ? this.sdkVersion.hashCode() : 0)) * 37) + (this.productId != null ? this.productId.hashCode() : 0)) * 37) + (this.productVersion != null ? this.productVersion.hashCode() : 0)) * 37) + (this.umidtoken != null ? this.umidtoken.hashCode() : 0)) * 37) + (this.apdid != null ? this.apdid.hashCode() : 0)) * 37) + (this.utdid != null ? this.utdid.hashCode() : 0)) * 37) + (this.externParams != null ? this.externParams.hashCode() : 1)) * 37) + (this.token != null ? this.token.hashCode() : 0)) * 37) + (this.devKeySet != null ? this.devKeySet.hashCode() : 0)) * 37;
        if (this.tid != null) {
            i2 = this.tid.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
