package com.alipay.mobileappcommon.biz.rpc.pginfo.model;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class ClientPGReportReqPB extends Message {
    public static final String DEFAULT_CLIENTID = "";
    public static final String DEFAULT_MANUFACTURER = "";
    public static final String DEFAULT_MOBILEBRAND = "";
    public static final String DEFAULT_MOBILEMODEL = "";
    public static final String DEFAULT_NETTYPE = "";
    public static final String DEFAULT_OSVERSION = "";
    public static final List<PgDataPB> DEFAULT_PGDATA = Collections.emptyList();
    public static final String DEFAULT_PLATFORM = "";
    public static final String DEFAULT_PRODUCTID = "";
    public static final String DEFAULT_PRODUCTVERSION = "";
    public static final String DEFAULT_ROMVERSION = "";
    public static final String DEFAULT_UTDID = "";
    public static final int TAG_CLIENTID = 3;
    public static final int TAG_MANUFACTURER = 5;
    public static final int TAG_MOBILEBRAND = 4;
    public static final int TAG_MOBILEMODEL = 7;
    public static final int TAG_NETTYPE = 8;
    public static final int TAG_OSVERSION = 11;
    public static final int TAG_PGDATA = 12;
    public static final int TAG_PLATFORM = 10;
    public static final int TAG_PRODUCTID = 1;
    public static final int TAG_PRODUCTVERSION = 2;
    public static final int TAG_ROMVERSION = 6;
    public static final int TAG_UTDID = 9;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String clientId;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String manufacturer;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String mobileBrand;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String mobileModel;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String netType;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String osVersion;
    @ProtoField(label = Label.REPEATED, tag = 12)
    public List<PgDataPB> pgData;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String platform;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String productId;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String productVersion;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String romVersion;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String utdid;

    public ClientPGReportReqPB() {
    }

    public ClientPGReportReqPB(ClientPGReportReqPB clientPGReportReqPB) {
        super(clientPGReportReqPB);
        if (clientPGReportReqPB != null) {
            this.productId = clientPGReportReqPB.productId;
            this.productVersion = clientPGReportReqPB.productVersion;
            this.clientId = clientPGReportReqPB.clientId;
            this.mobileBrand = clientPGReportReqPB.mobileBrand;
            this.manufacturer = clientPGReportReqPB.manufacturer;
            this.romVersion = clientPGReportReqPB.romVersion;
            this.mobileModel = clientPGReportReqPB.mobileModel;
            this.netType = clientPGReportReqPB.netType;
            this.utdid = clientPGReportReqPB.utdid;
            this.platform = clientPGReportReqPB.platform;
            this.osVersion = clientPGReportReqPB.osVersion;
            this.pgData = copyOf(clientPGReportReqPB.pgData);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ClientPGReportReqPB)) {
            return false;
        }
        ClientPGReportReqPB clientPGReportReqPB = (ClientPGReportReqPB) obj;
        return equals((Object) this.productId, (Object) clientPGReportReqPB.productId) && equals((Object) this.productVersion, (Object) clientPGReportReqPB.productVersion) && equals((Object) this.clientId, (Object) clientPGReportReqPB.clientId) && equals((Object) this.mobileBrand, (Object) clientPGReportReqPB.mobileBrand) && equals((Object) this.manufacturer, (Object) clientPGReportReqPB.manufacturer) && equals((Object) this.romVersion, (Object) clientPGReportReqPB.romVersion) && equals((Object) this.mobileModel, (Object) clientPGReportReqPB.mobileModel) && equals((Object) this.netType, (Object) clientPGReportReqPB.netType) && equals((Object) this.utdid, (Object) clientPGReportReqPB.utdid) && equals((Object) this.platform, (Object) clientPGReportReqPB.platform) && equals((Object) this.osVersion, (Object) clientPGReportReqPB.osVersion) && equals(this.pgData, clientPGReportReqPB.pgData);
    }

    public final ClientPGReportReqPB fillTagValue(int i, Object obj) {
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
                this.mobileBrand = (String) obj;
                break;
            case 5:
                this.manufacturer = (String) obj;
                break;
            case 6:
                this.romVersion = (String) obj;
                break;
            case 7:
                this.mobileModel = (String) obj;
                break;
            case 8:
                this.netType = (String) obj;
                break;
            case 9:
                this.utdid = (String) obj;
                break;
            case 10:
                this.platform = (String) obj;
                break;
            case 11:
                this.osVersion = (String) obj;
                break;
            case 12:
                this.pgData = immutableCopyOf((List) obj);
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
        int hashCode = ((this.platform != null ? this.platform.hashCode() : 0) + (((this.utdid != null ? this.utdid.hashCode() : 0) + (((this.netType != null ? this.netType.hashCode() : 0) + (((this.mobileModel != null ? this.mobileModel.hashCode() : 0) + (((this.romVersion != null ? this.romVersion.hashCode() : 0) + (((this.manufacturer != null ? this.manufacturer.hashCode() : 0) + (((this.mobileBrand != null ? this.mobileBrand.hashCode() : 0) + (((this.clientId != null ? this.clientId.hashCode() : 0) + (((this.productVersion != null ? this.productVersion.hashCode() : 0) + ((this.productId != null ? this.productId.hashCode() : 0) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37)) * 37;
        if (this.osVersion != null) {
            i = this.osVersion.hashCode();
        }
        int hashCode2 = (this.pgData != null ? this.pgData.hashCode() : 1) + ((hashCode + i) * 37);
        this.hashCode = hashCode2;
        return hashCode2;
    }
}
