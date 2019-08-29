package com.alipay.android.phone.inside.commonbiz.report.model;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class TidInfoPbPB extends Message {
    public static final String DEFAULT_APPPACKAGENAME = "";
    public static final String DEFAULT_CLIENTKEY = "";
    public static final String DEFAULT_DEVICENAME = "";
    public static final String DEFAULT_IMEI = "";
    public static final String DEFAULT_IMSI = "";
    public static final String DEFAULT_PRODUCTID = "";
    public static final String DEFAULT_PRODUCTVERSION = "";
    public static final String DEFAULT_TID = "";
    public static final String DEFAULT_UTDID = "";
    public static final String DEFAULT_VIMEI = "";
    public static final String DEFAULT_VIMSI = "";
    public static final int TAG_APPPACKAGENAME = 10;
    public static final int TAG_CLIENTKEY = 7;
    public static final int TAG_DEVICENAME = 11;
    public static final int TAG_IMEI = 5;
    public static final int TAG_IMSI = 6;
    public static final int TAG_PRODUCTID = 8;
    public static final int TAG_PRODUCTVERSION = 9;
    public static final int TAG_TID = 1;
    public static final int TAG_UTDID = 2;
    public static final int TAG_VIMEI = 4;
    public static final int TAG_VIMSI = 3;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String appPackageName;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String clientKey;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String deviceName;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String imei;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String imsi;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String productId;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String productVersion;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String tid;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String utdid;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String vimei;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String vimsi;

    public TidInfoPbPB(TidInfoPbPB tidInfoPbPB) {
        super(tidInfoPbPB);
        if (tidInfoPbPB != null) {
            this.tid = tidInfoPbPB.tid;
            this.utdid = tidInfoPbPB.utdid;
            this.vimsi = tidInfoPbPB.vimsi;
            this.vimei = tidInfoPbPB.vimei;
            this.imei = tidInfoPbPB.imei;
            this.imsi = tidInfoPbPB.imsi;
            this.clientKey = tidInfoPbPB.clientKey;
            this.productId = tidInfoPbPB.productId;
            this.productVersion = tidInfoPbPB.productVersion;
            this.appPackageName = tidInfoPbPB.appPackageName;
            this.deviceName = tidInfoPbPB.deviceName;
        }
    }

    public TidInfoPbPB() {
    }

    public final TidInfoPbPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.tid = (String) obj;
                break;
            case 2:
                this.utdid = (String) obj;
                break;
            case 3:
                this.vimsi = (String) obj;
                break;
            case 4:
                this.vimei = (String) obj;
                break;
            case 5:
                this.imei = (String) obj;
                break;
            case 6:
                this.imsi = (String) obj;
                break;
            case 7:
                this.clientKey = (String) obj;
                break;
            case 8:
                this.productId = (String) obj;
                break;
            case 9:
                this.productVersion = (String) obj;
                break;
            case 10:
                this.appPackageName = (String) obj;
                break;
            case 11:
                this.deviceName = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TidInfoPbPB)) {
            return false;
        }
        TidInfoPbPB tidInfoPbPB = (TidInfoPbPB) obj;
        return equals((Object) this.tid, (Object) tidInfoPbPB.tid) && equals((Object) this.utdid, (Object) tidInfoPbPB.utdid) && equals((Object) this.vimsi, (Object) tidInfoPbPB.vimsi) && equals((Object) this.vimei, (Object) tidInfoPbPB.vimei) && equals((Object) this.imei, (Object) tidInfoPbPB.imei) && equals((Object) this.imsi, (Object) tidInfoPbPB.imsi) && equals((Object) this.clientKey, (Object) tidInfoPbPB.clientKey) && equals((Object) this.productId, (Object) tidInfoPbPB.productId) && equals((Object) this.productVersion, (Object) tidInfoPbPB.productVersion) && equals((Object) this.appPackageName, (Object) tidInfoPbPB.appPackageName) && equals((Object) this.deviceName, (Object) tidInfoPbPB.deviceName);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((((this.tid != null ? this.tid.hashCode() : 0) * 37) + (this.utdid != null ? this.utdid.hashCode() : 0)) * 37) + (this.vimsi != null ? this.vimsi.hashCode() : 0)) * 37) + (this.vimei != null ? this.vimei.hashCode() : 0)) * 37) + (this.imei != null ? this.imei.hashCode() : 0)) * 37) + (this.imsi != null ? this.imsi.hashCode() : 0)) * 37) + (this.clientKey != null ? this.clientKey.hashCode() : 0)) * 37) + (this.productId != null ? this.productId.hashCode() : 0)) * 37) + (this.productVersion != null ? this.productVersion.hashCode() : 0)) * 37) + (this.appPackageName != null ? this.appPackageName.hashCode() : 0)) * 37;
        if (this.deviceName != null) {
            i2 = this.deviceName.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
