package com.alipay.android.phone.inside.config.rpc.model;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class SwitchInfoReqPbPB extends Message {
    public static final SwitchBizTypePB DEFAULT_BIZTYPE = SwitchBizTypePB.ALL;
    public static final String DEFAULT_BUSINESS = "";
    public static final String DEFAULT_CHANNELID = "";
    public static final String DEFAULT_CLIENTID = "";
    public static final String DEFAULT_CLIENTVERSION = "";
    public static final String DEFAULT_IMEI = "";
    public static final String DEFAULT_LASTRESPONSETIME = "";
    public static final String DEFAULT_MANUFACTURER = "";
    public static final String DEFAULT_MOBILEBRAND = "";
    public static final String DEFAULT_MOBILEMODEL = "";
    public static final String DEFAULT_OSVERSION = "";
    public static final String DEFAULT_PRODUCTID = "";
    public static final String DEFAULT_RELEASEVERSION = "";
    public static final String DEFAULT_REQUESTIP = "";
    public static final String DEFAULT_ROMVERSION = "";
    public static final String DEFAULT_SYSTEMTYPE = "";
    public static final String DEFAULT_USERID = "";
    public static final String DEFAULT_UTDID = "";
    public static final int TAG_BIZTYPE = 11;
    public static final int TAG_BUSINESS = 7;
    public static final int TAG_CHANNELID = 4;
    public static final int TAG_CLIENTID = 13;
    public static final int TAG_CLIENTVERSION = 1;
    public static final int TAG_IMEI = 5;
    public static final int TAG_LASTRESPONSETIME = 6;
    public static final int TAG_MANUFACTURER = 17;
    public static final int TAG_MOBILEBRAND = 14;
    public static final int TAG_MOBILEMODEL = 15;
    public static final int TAG_OSVERSION = 16;
    public static final int TAG_PRODUCTID = 10;
    public static final int TAG_RELEASEVERSION = 12;
    public static final int TAG_REQUESTIP = 9;
    public static final int TAG_ROMVERSION = 18;
    public static final int TAG_SYSTEMTYPE = 8;
    public static final int TAG_USERID = 3;
    public static final int TAG_UTDID = 2;
    @ProtoField(tag = 11, type = Datatype.ENUM)
    public SwitchBizTypePB bizType;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String business;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String channelId;
    @ProtoField(tag = 13, type = Datatype.STRING)
    public String clientId;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String clientVersion;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String imei;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String lastResponseTime;
    @ProtoField(tag = 17, type = Datatype.STRING)
    public String manufacturer;
    @ProtoField(tag = 14, type = Datatype.STRING)
    public String mobileBrand;
    @ProtoField(tag = 15, type = Datatype.STRING)
    public String mobileModel;
    @ProtoField(tag = 16, type = Datatype.STRING)
    public String osVersion;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String productId;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String releaseVersion;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String requestIp;
    @ProtoField(tag = 18, type = Datatype.STRING)
    public String romVersion;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String systemType;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String userId;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String utdid;

    public SwitchInfoReqPbPB(SwitchInfoReqPbPB switchInfoReqPbPB) {
        super(switchInfoReqPbPB);
        if (switchInfoReqPbPB != null) {
            this.clientVersion = switchInfoReqPbPB.clientVersion;
            this.utdid = switchInfoReqPbPB.utdid;
            this.userId = switchInfoReqPbPB.userId;
            this.channelId = switchInfoReqPbPB.channelId;
            this.imei = switchInfoReqPbPB.imei;
            this.lastResponseTime = switchInfoReqPbPB.lastResponseTime;
            this.business = switchInfoReqPbPB.business;
            this.systemType = switchInfoReqPbPB.systemType;
            this.requestIp = switchInfoReqPbPB.requestIp;
            this.productId = switchInfoReqPbPB.productId;
            this.bizType = switchInfoReqPbPB.bizType;
            this.releaseVersion = switchInfoReqPbPB.releaseVersion;
            this.clientId = switchInfoReqPbPB.clientId;
            this.mobileBrand = switchInfoReqPbPB.mobileBrand;
            this.mobileModel = switchInfoReqPbPB.mobileModel;
            this.osVersion = switchInfoReqPbPB.osVersion;
            this.manufacturer = switchInfoReqPbPB.manufacturer;
            this.romVersion = switchInfoReqPbPB.romVersion;
        }
    }

    public SwitchInfoReqPbPB() {
    }

    public final SwitchInfoReqPbPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.clientVersion = (String) obj;
                break;
            case 2:
                this.utdid = (String) obj;
                break;
            case 3:
                this.userId = (String) obj;
                break;
            case 4:
                this.channelId = (String) obj;
                break;
            case 5:
                this.imei = (String) obj;
                break;
            case 6:
                this.lastResponseTime = (String) obj;
                break;
            case 7:
                this.business = (String) obj;
                break;
            case 8:
                this.systemType = (String) obj;
                break;
            case 9:
                this.requestIp = (String) obj;
                break;
            case 10:
                this.productId = (String) obj;
                break;
            case 11:
                this.bizType = (SwitchBizTypePB) obj;
                break;
            case 12:
                this.releaseVersion = (String) obj;
                break;
            case 13:
                this.clientId = (String) obj;
                break;
            case 14:
                this.mobileBrand = (String) obj;
                break;
            case 15:
                this.mobileModel = (String) obj;
                break;
            case 16:
                this.osVersion = (String) obj;
                break;
            case 17:
                this.manufacturer = (String) obj;
                break;
            case 18:
                this.romVersion = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SwitchInfoReqPbPB)) {
            return false;
        }
        SwitchInfoReqPbPB switchInfoReqPbPB = (SwitchInfoReqPbPB) obj;
        return equals((Object) this.clientVersion, (Object) switchInfoReqPbPB.clientVersion) && equals((Object) this.utdid, (Object) switchInfoReqPbPB.utdid) && equals((Object) this.userId, (Object) switchInfoReqPbPB.userId) && equals((Object) this.channelId, (Object) switchInfoReqPbPB.channelId) && equals((Object) this.imei, (Object) switchInfoReqPbPB.imei) && equals((Object) this.lastResponseTime, (Object) switchInfoReqPbPB.lastResponseTime) && equals((Object) this.business, (Object) switchInfoReqPbPB.business) && equals((Object) this.systemType, (Object) switchInfoReqPbPB.systemType) && equals((Object) this.requestIp, (Object) switchInfoReqPbPB.requestIp) && equals((Object) this.productId, (Object) switchInfoReqPbPB.productId) && equals((Object) this.bizType, (Object) switchInfoReqPbPB.bizType) && equals((Object) this.releaseVersion, (Object) switchInfoReqPbPB.releaseVersion) && equals((Object) this.clientId, (Object) switchInfoReqPbPB.clientId) && equals((Object) this.mobileBrand, (Object) switchInfoReqPbPB.mobileBrand) && equals((Object) this.mobileModel, (Object) switchInfoReqPbPB.mobileModel) && equals((Object) this.osVersion, (Object) switchInfoReqPbPB.osVersion) && equals((Object) this.manufacturer, (Object) switchInfoReqPbPB.manufacturer) && equals((Object) this.romVersion, (Object) switchInfoReqPbPB.romVersion);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((((((((((((((((((this.clientVersion != null ? this.clientVersion.hashCode() : 0) * 37) + (this.utdid != null ? this.utdid.hashCode() : 0)) * 37) + (this.userId != null ? this.userId.hashCode() : 0)) * 37) + (this.channelId != null ? this.channelId.hashCode() : 0)) * 37) + (this.imei != null ? this.imei.hashCode() : 0)) * 37) + (this.lastResponseTime != null ? this.lastResponseTime.hashCode() : 0)) * 37) + (this.business != null ? this.business.hashCode() : 0)) * 37) + (this.systemType != null ? this.systemType.hashCode() : 0)) * 37) + (this.requestIp != null ? this.requestIp.hashCode() : 0)) * 37) + (this.productId != null ? this.productId.hashCode() : 0)) * 37) + (this.bizType != null ? this.bizType.hashCode() : 0)) * 37) + (this.releaseVersion != null ? this.releaseVersion.hashCode() : 0)) * 37) + (this.clientId != null ? this.clientId.hashCode() : 0)) * 37) + (this.mobileBrand != null ? this.mobileBrand.hashCode() : 0)) * 37) + (this.mobileModel != null ? this.mobileModel.hashCode() : 0)) * 37) + (this.osVersion != null ? this.osVersion.hashCode() : 0)) * 37) + (this.manufacturer != null ? this.manufacturer.hashCode() : 0)) * 37;
        if (this.romVersion != null) {
            i2 = this.romVersion.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
