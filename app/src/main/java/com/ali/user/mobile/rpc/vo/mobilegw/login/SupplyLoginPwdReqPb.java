package com.ali.user.mobile.rpc.vo.mobilegw.login;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class SupplyLoginPwdReqPb extends Message {
    public static final String DEFAULT_APDID = "";
    public static final String DEFAULT_DEVKEYSET = "";
    public static final List<ExternParams> DEFAULT_EXTERNPARAMS = Collections.emptyList();
    public static final String DEFAULT_LOGINID = "";
    public static final String DEFAULT_PRODUCTID = "";
    public static final String DEFAULT_PRODUCTVERSION = "";
    public static final String DEFAULT_QUERYPASSWORD = "";
    public static final String DEFAULT_SCENE = "";
    public static final String DEFAULT_SDKVERSION = "";
    public static final String DEFAULT_TID = "";
    public static final String DEFAULT_UMIDTOKEN = "";
    public static final String DEFAULT_UTDID = "";
    public static final int TAG_APDID = 7;
    public static final int TAG_DEVKEYSET = 11;
    public static final int TAG_EXTERNPARAMS = 10;
    public static final int TAG_LOGINID = 1;
    public static final int TAG_PRODUCTID = 4;
    public static final int TAG_PRODUCTVERSION = 5;
    public static final int TAG_QUERYPASSWORD = 2;
    public static final int TAG_SCENE = 9;
    public static final int TAG_SDKVERSION = 3;
    public static final int TAG_TID = 12;
    public static final int TAG_UMIDTOKEN = 6;
    public static final int TAG_UTDID = 8;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String apdid;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String devKeySet;
    @ProtoField(label = Label.REPEATED, tag = 10)
    public List<ExternParams> externParams;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String loginId;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String productId;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String productVersion;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String queryPassword;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String scene;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String sdkVersion;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String tid;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String umidtoken;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String utdid;

    public SupplyLoginPwdReqPb(SupplyLoginPwdReqPb supplyLoginPwdReqPb) {
        super(supplyLoginPwdReqPb);
        if (supplyLoginPwdReqPb != null) {
            this.loginId = supplyLoginPwdReqPb.loginId;
            this.queryPassword = supplyLoginPwdReqPb.queryPassword;
            this.sdkVersion = supplyLoginPwdReqPb.sdkVersion;
            this.productId = supplyLoginPwdReqPb.productId;
            this.productVersion = supplyLoginPwdReqPb.productVersion;
            this.umidtoken = supplyLoginPwdReqPb.umidtoken;
            this.apdid = supplyLoginPwdReqPb.apdid;
            this.utdid = supplyLoginPwdReqPb.utdid;
            this.scene = supplyLoginPwdReqPb.scene;
            this.externParams = copyOf(supplyLoginPwdReqPb.externParams);
            this.devKeySet = supplyLoginPwdReqPb.devKeySet;
            this.tid = supplyLoginPwdReqPb.tid;
        }
    }

    public SupplyLoginPwdReqPb() {
    }

    public final SupplyLoginPwdReqPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.loginId = (String) obj;
                break;
            case 2:
                this.queryPassword = (String) obj;
                break;
            case 3:
                this.sdkVersion = (String) obj;
                break;
            case 4:
                this.productId = (String) obj;
                break;
            case 5:
                this.productVersion = (String) obj;
                break;
            case 6:
                this.umidtoken = (String) obj;
                break;
            case 7:
                this.apdid = (String) obj;
                break;
            case 8:
                this.utdid = (String) obj;
                break;
            case 9:
                this.scene = (String) obj;
                break;
            case 10:
                this.externParams = immutableCopyOf((List) obj);
                break;
            case 11:
                this.devKeySet = (String) obj;
                break;
            case 12:
                this.tid = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SupplyLoginPwdReqPb)) {
            return false;
        }
        SupplyLoginPwdReqPb supplyLoginPwdReqPb = (SupplyLoginPwdReqPb) obj;
        return equals((Object) this.loginId, (Object) supplyLoginPwdReqPb.loginId) && equals((Object) this.queryPassword, (Object) supplyLoginPwdReqPb.queryPassword) && equals((Object) this.sdkVersion, (Object) supplyLoginPwdReqPb.sdkVersion) && equals((Object) this.productId, (Object) supplyLoginPwdReqPb.productId) && equals((Object) this.productVersion, (Object) supplyLoginPwdReqPb.productVersion) && equals((Object) this.umidtoken, (Object) supplyLoginPwdReqPb.umidtoken) && equals((Object) this.apdid, (Object) supplyLoginPwdReqPb.apdid) && equals((Object) this.utdid, (Object) supplyLoginPwdReqPb.utdid) && equals((Object) this.scene, (Object) supplyLoginPwdReqPb.scene) && equals(this.externParams, supplyLoginPwdReqPb.externParams) && equals((Object) this.devKeySet, (Object) supplyLoginPwdReqPb.devKeySet) && equals((Object) this.tid, (Object) supplyLoginPwdReqPb.tid);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((((((this.loginId != null ? this.loginId.hashCode() : 0) * 37) + (this.queryPassword != null ? this.queryPassword.hashCode() : 0)) * 37) + (this.sdkVersion != null ? this.sdkVersion.hashCode() : 0)) * 37) + (this.productId != null ? this.productId.hashCode() : 0)) * 37) + (this.productVersion != null ? this.productVersion.hashCode() : 0)) * 37) + (this.umidtoken != null ? this.umidtoken.hashCode() : 0)) * 37) + (this.apdid != null ? this.apdid.hashCode() : 0)) * 37) + (this.utdid != null ? this.utdid.hashCode() : 0)) * 37) + (this.scene != null ? this.scene.hashCode() : 0)) * 37) + (this.externParams != null ? this.externParams.hashCode() : 1)) * 37) + (this.devKeySet != null ? this.devKeySet.hashCode() : 0)) * 37;
        if (this.tid != null) {
            i2 = this.tid.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
