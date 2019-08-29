package com.alipay.mobileappcommon.biz.rpc.clientswitch.model.pb;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class SwitchInfoReqPb extends Message {
    public static final SwitchBizType DEFAULT_BIZTYPE = SwitchBizType.ALL;
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
    public SwitchBizType bizType;
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

    public SwitchInfoReqPb() {
    }

    public SwitchInfoReqPb(SwitchInfoReqPb switchInfoReqPb) {
        super(switchInfoReqPb);
        if (switchInfoReqPb != null) {
            this.clientVersion = switchInfoReqPb.clientVersion;
            this.utdid = switchInfoReqPb.utdid;
            this.userId = switchInfoReqPb.userId;
            this.channelId = switchInfoReqPb.channelId;
            this.imei = switchInfoReqPb.imei;
            this.lastResponseTime = switchInfoReqPb.lastResponseTime;
            this.business = switchInfoReqPb.business;
            this.systemType = switchInfoReqPb.systemType;
            this.requestIp = switchInfoReqPb.requestIp;
            this.productId = switchInfoReqPb.productId;
            this.bizType = switchInfoReqPb.bizType;
            this.releaseVersion = switchInfoReqPb.releaseVersion;
            this.clientId = switchInfoReqPb.clientId;
            this.mobileBrand = switchInfoReqPb.mobileBrand;
            this.mobileModel = switchInfoReqPb.mobileModel;
            this.osVersion = switchInfoReqPb.osVersion;
            this.manufacturer = switchInfoReqPb.manufacturer;
            this.romVersion = switchInfoReqPb.romVersion;
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SwitchInfoReqPb)) {
            return false;
        }
        SwitchInfoReqPb switchInfoReqPb = (SwitchInfoReqPb) obj;
        return equals((Object) this.clientVersion, (Object) switchInfoReqPb.clientVersion) && equals((Object) this.utdid, (Object) switchInfoReqPb.utdid) && equals((Object) this.userId, (Object) switchInfoReqPb.userId) && equals((Object) this.channelId, (Object) switchInfoReqPb.channelId) && equals((Object) this.imei, (Object) switchInfoReqPb.imei) && equals((Object) this.lastResponseTime, (Object) switchInfoReqPb.lastResponseTime) && equals((Object) this.business, (Object) switchInfoReqPb.business) && equals((Object) this.systemType, (Object) switchInfoReqPb.systemType) && equals((Object) this.requestIp, (Object) switchInfoReqPb.requestIp) && equals((Object) this.productId, (Object) switchInfoReqPb.productId) && equals((Object) this.bizType, (Object) switchInfoReqPb.bizType) && equals((Object) this.releaseVersion, (Object) switchInfoReqPb.releaseVersion) && equals((Object) this.clientId, (Object) switchInfoReqPb.clientId) && equals((Object) this.mobileBrand, (Object) switchInfoReqPb.mobileBrand) && equals((Object) this.mobileModel, (Object) switchInfoReqPb.mobileModel) && equals((Object) this.osVersion, (Object) switchInfoReqPb.osVersion) && equals((Object) this.manufacturer, (Object) switchInfoReqPb.manufacturer) && equals((Object) this.romVersion, (Object) switchInfoReqPb.romVersion);
    }

    public final SwitchInfoReqPb fillTagValue(int i, Object obj) {
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
                this.bizType = (SwitchBizType) obj;
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

    public final int hashCode() {
        int i = 0;
        int i2 = this.hashCode;
        if (i2 != 0) {
            return i2;
        }
        int hashCode = ((this.manufacturer != null ? this.manufacturer.hashCode() : 0) + (((this.osVersion != null ? this.osVersion.hashCode() : 0) + (((this.mobileModel != null ? this.mobileModel.hashCode() : 0) + (((this.mobileBrand != null ? this.mobileBrand.hashCode() : 0) + (((this.clientId != null ? this.clientId.hashCode() : 0) + (((this.releaseVersion != null ? this.releaseVersion.hashCode() : 0) + (((this.bizType != null ? this.bizType.hashCode() : 0) + (((this.productId != null ? this.productId.hashCode() : 0) + (((this.requestIp != null ? this.requestIp.hashCode() : 0) + (((this.systemType != null ? this.systemType.hashCode() : 0) + (((this.business != null ? this.business.hashCode() : 0) + (((this.lastResponseTime != null ? this.lastResponseTime.hashCode() : 0) + (((this.imei != null ? this.imei.hashCode() : 0) + (((this.channelId != null ? this.channelId.hashCode() : 0) + (((this.userId != null ? this.userId.hashCode() : 0) + (((this.utdid != null ? this.utdid.hashCode() : 0) + ((this.clientVersion != null ? this.clientVersion.hashCode() : 0) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37;
        if (this.romVersion != null) {
            i = this.romVersion.hashCode();
        }
        int i3 = hashCode + i;
        this.hashCode = i3;
        return i3;
    }
}
