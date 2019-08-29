package com.ali.user.mobile.rpc.vo.sso;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class VerifySsoTokenRequestPb extends Message {
    public static final String DEFAULT_ALIPAYSSOTOKEN = "";
    public static final String DEFAULT_DID = "";
    public static final List<KeyValueEntryPB> DEFAULT_EXTERNPARAM = Collections.emptyList();
    public static final String DEFAULT_LOGINID = "";
    public static final String DEFAULT_PRODUCTID = "";
    public static final String DEFAULT_PRODUCTVERSION = "";
    public static final String DEFAULT_SYSTEMTYPE = "";
    public static final int TAG_ALIPAYSSOTOKEN = 2;
    public static final int TAG_DID = 3;
    public static final int TAG_EXTERNPARAM = 7;
    public static final int TAG_LOGINID = 1;
    public static final int TAG_PRODUCTID = 4;
    public static final int TAG_PRODUCTVERSION = 5;
    public static final int TAG_SYSTEMTYPE = 6;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String alipaySsoToken;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String did;
    @ProtoField(label = Label.REPEATED, tag = 7)
    public List<KeyValueEntryPB> externParam;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String loginId;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String productId;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String productVersion;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String systemType;

    public VerifySsoTokenRequestPb(VerifySsoTokenRequestPb verifySsoTokenRequestPb) {
        super(verifySsoTokenRequestPb);
        if (verifySsoTokenRequestPb != null) {
            this.loginId = verifySsoTokenRequestPb.loginId;
            this.alipaySsoToken = verifySsoTokenRequestPb.alipaySsoToken;
            this.did = verifySsoTokenRequestPb.did;
            this.productId = verifySsoTokenRequestPb.productId;
            this.productVersion = verifySsoTokenRequestPb.productVersion;
            this.systemType = verifySsoTokenRequestPb.systemType;
            this.externParam = copyOf(verifySsoTokenRequestPb.externParam);
        }
    }

    public VerifySsoTokenRequestPb() {
    }

    public final VerifySsoTokenRequestPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.loginId = (String) obj;
                break;
            case 2:
                this.alipaySsoToken = (String) obj;
                break;
            case 3:
                this.did = (String) obj;
                break;
            case 4:
                this.productId = (String) obj;
                break;
            case 5:
                this.productVersion = (String) obj;
                break;
            case 6:
                this.systemType = (String) obj;
                break;
            case 7:
                this.externParam = immutableCopyOf((List) obj);
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VerifySsoTokenRequestPb)) {
            return false;
        }
        VerifySsoTokenRequestPb verifySsoTokenRequestPb = (VerifySsoTokenRequestPb) obj;
        return equals((Object) this.loginId, (Object) verifySsoTokenRequestPb.loginId) && equals((Object) this.alipaySsoToken, (Object) verifySsoTokenRequestPb.alipaySsoToken) && equals((Object) this.did, (Object) verifySsoTokenRequestPb.did) && equals((Object) this.productId, (Object) verifySsoTokenRequestPb.productId) && equals((Object) this.productVersion, (Object) verifySsoTokenRequestPb.productVersion) && equals((Object) this.systemType, (Object) verifySsoTokenRequestPb.systemType) && equals(this.externParam, verifySsoTokenRequestPb.externParam);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((this.loginId != null ? this.loginId.hashCode() : 0) * 37) + (this.alipaySsoToken != null ? this.alipaySsoToken.hashCode() : 0)) * 37) + (this.did != null ? this.did.hashCode() : 0)) * 37) + (this.productId != null ? this.productId.hashCode() : 0)) * 37) + (this.productVersion != null ? this.productVersion.hashCode() : 0)) * 37;
        if (this.systemType != null) {
            i2 = this.systemType.hashCode();
        }
        int hashCode2 = ((hashCode + i2) * 37) + (this.externParam != null ? this.externParam.hashCode() : 1);
        this.hashCode = hashCode2;
        return hashCode2;
    }
}
