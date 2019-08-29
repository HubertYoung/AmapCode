package com.alipay.mobileappcommon.biz.rpc.pginfo.model;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class ClientPGInfoReqPB extends Message {
    public static final String DEFAULT_APPCODE = "";
    public static final String DEFAULT_CLIENTID = "";
    public static final List<PgDataPB> DEFAULT_EXTRADATA = Collections.emptyList();
    public static final String DEFAULT_LASTTIME = "";
    public static final String DEFAULT_MANUFACTURER = "";
    public static final String DEFAULT_MOBILEBRAND = "";
    public static final String DEFAULT_MOBILEMODEL = "";
    public static final String DEFAULT_NETTYPE = "";
    public static final String DEFAULT_OSVERSION = "";
    public static final String DEFAULT_PLATFORM = "";
    public static final String DEFAULT_PRODUCTID = "";
    public static final String DEFAULT_PRODUCTVERSION = "";
    public static final String DEFAULT_ROMVERSION = "";
    public static final String DEFAULT_UTDID = "";
    public static final int TAG_APPCODE = 14;
    public static final int TAG_CLIENTID = 3;
    public static final int TAG_EXTRADATA = 13;
    public static final int TAG_LASTTIME = 10;
    public static final int TAG_MANUFACTURER = 5;
    public static final int TAG_MOBILEBRAND = 7;
    public static final int TAG_MOBILEMODEL = 4;
    public static final int TAG_NETTYPE = 8;
    public static final int TAG_OSVERSION = 12;
    public static final int TAG_PLATFORM = 11;
    public static final int TAG_PRODUCTID = 1;
    public static final int TAG_PRODUCTVERSION = 2;
    public static final int TAG_ROMVERSION = 6;
    public static final int TAG_UTDID = 9;
    @ProtoField(tag = 14, type = Datatype.STRING)
    public String appCode;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String clientId;
    @ProtoField(label = Label.REPEATED, tag = 13)
    public List<PgDataPB> extraData;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String lastTime;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String manufacturer;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String mobileBrand;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String mobileModel;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String netType;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String osVersion;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String platform;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String productId;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String productVersion;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String romVersion;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String utdid;

    public ClientPGInfoReqPB() {
    }

    public ClientPGInfoReqPB(ClientPGInfoReqPB clientPGInfoReqPB) {
        super(clientPGInfoReqPB);
        if (clientPGInfoReqPB != null) {
            this.productId = clientPGInfoReqPB.productId;
            this.productVersion = clientPGInfoReqPB.productVersion;
            this.clientId = clientPGInfoReqPB.clientId;
            this.mobileModel = clientPGInfoReqPB.mobileModel;
            this.manufacturer = clientPGInfoReqPB.manufacturer;
            this.romVersion = clientPGInfoReqPB.romVersion;
            this.mobileBrand = clientPGInfoReqPB.mobileBrand;
            this.netType = clientPGInfoReqPB.netType;
            this.utdid = clientPGInfoReqPB.utdid;
            this.lastTime = clientPGInfoReqPB.lastTime;
            this.platform = clientPGInfoReqPB.platform;
            this.osVersion = clientPGInfoReqPB.osVersion;
            this.extraData = copyOf(clientPGInfoReqPB.extraData);
            this.appCode = clientPGInfoReqPB.appCode;
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ClientPGInfoReqPB)) {
            return false;
        }
        ClientPGInfoReqPB clientPGInfoReqPB = (ClientPGInfoReqPB) obj;
        return equals((Object) this.productId, (Object) clientPGInfoReqPB.productId) && equals((Object) this.productVersion, (Object) clientPGInfoReqPB.productVersion) && equals((Object) this.clientId, (Object) clientPGInfoReqPB.clientId) && equals((Object) this.mobileModel, (Object) clientPGInfoReqPB.mobileModel) && equals((Object) this.manufacturer, (Object) clientPGInfoReqPB.manufacturer) && equals((Object) this.romVersion, (Object) clientPGInfoReqPB.romVersion) && equals((Object) this.mobileBrand, (Object) clientPGInfoReqPB.mobileBrand) && equals((Object) this.netType, (Object) clientPGInfoReqPB.netType) && equals((Object) this.utdid, (Object) clientPGInfoReqPB.utdid) && equals((Object) this.lastTime, (Object) clientPGInfoReqPB.lastTime) && equals((Object) this.platform, (Object) clientPGInfoReqPB.platform) && equals((Object) this.osVersion, (Object) clientPGInfoReqPB.osVersion) && equals(this.extraData, clientPGInfoReqPB.extraData) && equals((Object) this.appCode, (Object) clientPGInfoReqPB.appCode);
    }

    public final ClientPGInfoReqPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.productId = (String) obj;
                break;
            case 2:
                this.productVersion = (String) obj;
                break;
            case 3:
                this.clientId = (String) obj;
                break;
            case 4:
                this.mobileModel = (String) obj;
                break;
            case 5:
                this.manufacturer = (String) obj;
                break;
            case 6:
                this.romVersion = (String) obj;
                break;
            case 7:
                this.mobileBrand = (String) obj;
                break;
            case 8:
                this.netType = (String) obj;
                break;
            case 9:
                this.utdid = (String) obj;
                break;
            case 10:
                this.lastTime = (String) obj;
                break;
            case 11:
                this.platform = (String) obj;
                break;
            case 12:
                this.osVersion = (String) obj;
                break;
            case 13:
                this.extraData = immutableCopyOf((List) obj);
                break;
            case 14:
                this.appCode = (String) obj;
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
        int hashCode = ((this.extraData != null ? this.extraData.hashCode() : 1) + (((this.osVersion != null ? this.osVersion.hashCode() : 0) + (((this.platform != null ? this.platform.hashCode() : 0) + (((this.lastTime != null ? this.lastTime.hashCode() : 0) + (((this.utdid != null ? this.utdid.hashCode() : 0) + (((this.netType != null ? this.netType.hashCode() : 0) + (((this.mobileBrand != null ? this.mobileBrand.hashCode() : 0) + (((this.romVersion != null ? this.romVersion.hashCode() : 0) + (((this.manufacturer != null ? this.manufacturer.hashCode() : 0) + (((this.mobileModel != null ? this.mobileModel.hashCode() : 0) + (((this.clientId != null ? this.clientId.hashCode() : 0) + (((this.productVersion != null ? this.productVersion.hashCode() : 0) + ((this.productId != null ? this.productId.hashCode() : 0) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37;
        if (this.appCode != null) {
            i = this.appCode.hashCode();
        }
        int i3 = hashCode + i;
        this.hashCode = i3;
        return i3;
    }
}
