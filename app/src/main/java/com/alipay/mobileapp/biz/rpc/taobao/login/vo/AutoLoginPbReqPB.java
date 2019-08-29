package com.alipay.mobileapp.biz.rpc.taobao.login.vo;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class AutoLoginPbReqPB extends Message {
    public static final Boolean DEFAULT_BINDTAOBAO = Boolean.FALSE;
    public static final String DEFAULT_BSSID = "";
    public static final String DEFAULT_CELLID = "";
    public static final String DEFAULT_CLIENTIP = "";
    public static final String DEFAULT_CLIENTVERS = "";
    public static final String DEFAULT_DOMAIN = "";
    public static final String DEFAULT_LOGONID = "";
    public static final String DEFAULT_MAC = "";
    public static final String DEFAULT_MODEL = "";
    public static final String DEFAULT_PRODUCTID = "";
    public static final String DEFAULT_SOURCETYPE = "";
    public static final String DEFAULT_TARGETURL = "";
    public static final String DEFAULT_UMID = "";
    public static final String DEFAULT_USERID = "";
    public static final int TAG_BINDTAOBAO = 13;
    public static final int TAG_BSSID = 9;
    public static final int TAG_CELLID = 8;
    public static final int TAG_CLIENTIP = 4;
    public static final int TAG_CLIENTVERS = 6;
    public static final int TAG_DOMAIN = 12;
    public static final int TAG_EXTPARAMS = 15;
    public static final int TAG_LOGONID = 2;
    public static final int TAG_MAC = 5;
    public static final int TAG_MODEL = 7;
    public static final int TAG_PRODUCTID = 14;
    public static final int TAG_SOURCETYPE = 11;
    public static final int TAG_TARGETURL = 10;
    public static final int TAG_UMID = 3;
    public static final int TAG_USERID = 1;
    @ProtoField(tag = 13, type = Datatype.BOOL)
    public Boolean bindTaobao;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String bssId;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String cellId;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String clientIp;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String clientVers;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String domain;
    @ProtoField(tag = 15)
    public MapStringString extParams;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String logonId;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String mac;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String model;
    @ProtoField(tag = 14, type = Datatype.STRING)
    public String productId;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String sourceType;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String targetUrl;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String umid;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String userId;

    public AutoLoginPbReqPB(AutoLoginPbReqPB autoLoginPbReqPB) {
        super(autoLoginPbReqPB);
        if (autoLoginPbReqPB != null) {
            this.userId = autoLoginPbReqPB.userId;
            this.logonId = autoLoginPbReqPB.logonId;
            this.umid = autoLoginPbReqPB.umid;
            this.clientIp = autoLoginPbReqPB.clientIp;
            this.mac = autoLoginPbReqPB.mac;
            this.clientVers = autoLoginPbReqPB.clientVers;
            this.model = autoLoginPbReqPB.model;
            this.cellId = autoLoginPbReqPB.cellId;
            this.bssId = autoLoginPbReqPB.bssId;
            this.targetUrl = autoLoginPbReqPB.targetUrl;
            this.sourceType = autoLoginPbReqPB.sourceType;
            this.domain = autoLoginPbReqPB.domain;
            this.bindTaobao = autoLoginPbReqPB.bindTaobao;
            this.productId = autoLoginPbReqPB.productId;
            this.extParams = autoLoginPbReqPB.extParams;
        }
    }

    public AutoLoginPbReqPB() {
    }

    public final AutoLoginPbReqPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.userId = (String) obj;
                break;
            case 2:
                this.logonId = (String) obj;
                break;
            case 3:
                this.umid = (String) obj;
                break;
            case 4:
                this.clientIp = (String) obj;
                break;
            case 5:
                this.mac = (String) obj;
                break;
            case 6:
                this.clientVers = (String) obj;
                break;
            case 7:
                this.model = (String) obj;
                break;
            case 8:
                this.cellId = (String) obj;
                break;
            case 9:
                this.bssId = (String) obj;
                break;
            case 10:
                this.targetUrl = (String) obj;
                break;
            case 11:
                this.sourceType = (String) obj;
                break;
            case 12:
                this.domain = (String) obj;
                break;
            case 13:
                this.bindTaobao = (Boolean) obj;
                break;
            case 14:
                this.productId = (String) obj;
                break;
            case 15:
                this.extParams = (MapStringString) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AutoLoginPbReqPB)) {
            return false;
        }
        AutoLoginPbReqPB autoLoginPbReqPB = (AutoLoginPbReqPB) obj;
        return equals((Object) this.userId, (Object) autoLoginPbReqPB.userId) && equals((Object) this.logonId, (Object) autoLoginPbReqPB.logonId) && equals((Object) this.umid, (Object) autoLoginPbReqPB.umid) && equals((Object) this.clientIp, (Object) autoLoginPbReqPB.clientIp) && equals((Object) this.mac, (Object) autoLoginPbReqPB.mac) && equals((Object) this.clientVers, (Object) autoLoginPbReqPB.clientVers) && equals((Object) this.model, (Object) autoLoginPbReqPB.model) && equals((Object) this.cellId, (Object) autoLoginPbReqPB.cellId) && equals((Object) this.bssId, (Object) autoLoginPbReqPB.bssId) && equals((Object) this.targetUrl, (Object) autoLoginPbReqPB.targetUrl) && equals((Object) this.sourceType, (Object) autoLoginPbReqPB.sourceType) && equals((Object) this.domain, (Object) autoLoginPbReqPB.domain) && equals((Object) this.bindTaobao, (Object) autoLoginPbReqPB.bindTaobao) && equals((Object) this.productId, (Object) autoLoginPbReqPB.productId) && equals((Object) this.extParams, (Object) autoLoginPbReqPB.extParams);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((((((((((((this.userId != null ? this.userId.hashCode() : 0) * 37) + (this.logonId != null ? this.logonId.hashCode() : 0)) * 37) + (this.umid != null ? this.umid.hashCode() : 0)) * 37) + (this.clientIp != null ? this.clientIp.hashCode() : 0)) * 37) + (this.mac != null ? this.mac.hashCode() : 0)) * 37) + (this.clientVers != null ? this.clientVers.hashCode() : 0)) * 37) + (this.model != null ? this.model.hashCode() : 0)) * 37) + (this.cellId != null ? this.cellId.hashCode() : 0)) * 37) + (this.bssId != null ? this.bssId.hashCode() : 0)) * 37) + (this.targetUrl != null ? this.targetUrl.hashCode() : 0)) * 37) + (this.sourceType != null ? this.sourceType.hashCode() : 0)) * 37) + (this.domain != null ? this.domain.hashCode() : 0)) * 37) + (this.bindTaobao != null ? this.bindTaobao.hashCode() : 0)) * 37) + (this.productId != null ? this.productId.hashCode() : 0)) * 37;
        if (this.extParams != null) {
            i2 = this.extParams.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
