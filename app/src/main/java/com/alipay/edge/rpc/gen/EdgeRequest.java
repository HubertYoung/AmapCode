package com.alipay.edge.rpc.gen;

import com.alipay.android.phone.inside.protobuf.okio.ByteString;
import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class EdgeRequest extends Message {
    public static final String DEFAULT_APDID = "";
    public static final String DEFAULT_APDIDTOKEN = "";
    public static final String DEFAULT_APPNAME = "";
    public static final String DEFAULT_APPVERSION = "";
    public static final String DEFAULT_BRAND = "";
    public static final Integer DEFAULT_CREDIBLETIMESTAMP = Integer.valueOf(0);
    public static final EdgeStatus DEFAULT_EDGESTATUS = EdgeStatus.DISABLED;
    public static final String DEFAULT_EXTDATA = "";
    public static final String DEFAULT_MODEL = "";
    public static final OsType DEFAULT_OSTYPE = OsType.ANDROID;
    public static final String DEFAULT_OSVERSION = "";
    public static final String DEFAULT_POLICYPACKVERSION = "";
    public static final String DEFAULT_SDKVERSION = "";
    public static final ByteString DEFAULT_SECUREDATA = ByteString.EMPTY;
    public static final int TAG_APDID = 9;
    public static final int TAG_APDIDTOKEN = 10;
    public static final int TAG_APPNAME = 1;
    public static final int TAG_APPVERSION = 2;
    public static final int TAG_BRAND = 6;
    public static final int TAG_CREDIBLETIMESTAMP = 11;
    public static final int TAG_EDGESTATUS = 8;
    public static final int TAG_EXTDATA = 14;
    public static final int TAG_MODEL = 7;
    public static final int TAG_OSTYPE = 4;
    public static final int TAG_OSVERSION = 5;
    public static final int TAG_POLICYPACKVERSION = 12;
    public static final int TAG_SDKVERSION = 3;
    public static final int TAG_SECUREDATA = 13;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String apdid;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String apdidToken;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String appName;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String appVersion;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String brand;
    @ProtoField(tag = 11, type = Datatype.INT32)
    public Integer credibleTimestamp;
    @ProtoField(tag = 8, type = Datatype.ENUM)
    public EdgeStatus edgeStatus;
    @ProtoField(tag = 14, type = Datatype.STRING)
    public String extData;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String model;
    @ProtoField(tag = 4, type = Datatype.ENUM)
    public OsType osType;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String osVersion;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String policyPackVersion;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String sdkVersion;
    @ProtoField(tag = 13, type = Datatype.BYTES)
    public ByteString secureData;

    public EdgeRequest(EdgeRequest edgeRequest) {
        super(edgeRequest);
        if (edgeRequest != null) {
            this.appName = edgeRequest.appName;
            this.appVersion = edgeRequest.appVersion;
            this.sdkVersion = edgeRequest.sdkVersion;
            this.osType = edgeRequest.osType;
            this.osVersion = edgeRequest.osVersion;
            this.brand = edgeRequest.brand;
            this.model = edgeRequest.model;
            this.edgeStatus = edgeRequest.edgeStatus;
            this.apdid = edgeRequest.apdid;
            this.apdidToken = edgeRequest.apdidToken;
            this.credibleTimestamp = edgeRequest.credibleTimestamp;
            this.policyPackVersion = edgeRequest.policyPackVersion;
            this.secureData = edgeRequest.secureData;
            this.extData = edgeRequest.extData;
        }
    }

    public EdgeRequest() {
    }

    public final EdgeRequest fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.appName = (String) obj;
                break;
            case 2:
                this.appVersion = (String) obj;
                break;
            case 3:
                this.sdkVersion = (String) obj;
                break;
            case 4:
                this.osType = (OsType) obj;
                break;
            case 5:
                this.osVersion = (String) obj;
                break;
            case 6:
                this.brand = (String) obj;
                break;
            case 7:
                this.model = (String) obj;
                break;
            case 8:
                this.edgeStatus = (EdgeStatus) obj;
                break;
            case 9:
                this.apdid = (String) obj;
                break;
            case 10:
                this.apdidToken = (String) obj;
                break;
            case 11:
                this.credibleTimestamp = (Integer) obj;
                break;
            case 12:
                this.policyPackVersion = (String) obj;
                break;
            case 13:
                this.secureData = (ByteString) obj;
                break;
            case 14:
                this.extData = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof EdgeRequest)) {
            return false;
        }
        EdgeRequest edgeRequest = (EdgeRequest) obj;
        return equals((Object) this.appName, (Object) edgeRequest.appName) && equals((Object) this.appVersion, (Object) edgeRequest.appVersion) && equals((Object) this.sdkVersion, (Object) edgeRequest.sdkVersion) && equals((Object) this.osType, (Object) edgeRequest.osType) && equals((Object) this.osVersion, (Object) edgeRequest.osVersion) && equals((Object) this.brand, (Object) edgeRequest.brand) && equals((Object) this.model, (Object) edgeRequest.model) && equals((Object) this.edgeStatus, (Object) edgeRequest.edgeStatus) && equals((Object) this.apdid, (Object) edgeRequest.apdid) && equals((Object) this.apdidToken, (Object) edgeRequest.apdidToken) && equals((Object) this.credibleTimestamp, (Object) edgeRequest.credibleTimestamp) && equals((Object) this.policyPackVersion, (Object) edgeRequest.policyPackVersion) && equals((Object) this.secureData, (Object) edgeRequest.secureData) && equals((Object) this.extData, (Object) edgeRequest.extData);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((((((((((this.appName != null ? this.appName.hashCode() : 0) * 37) + (this.appVersion != null ? this.appVersion.hashCode() : 0)) * 37) + (this.sdkVersion != null ? this.sdkVersion.hashCode() : 0)) * 37) + (this.osType != null ? this.osType.hashCode() : 0)) * 37) + (this.osVersion != null ? this.osVersion.hashCode() : 0)) * 37) + (this.brand != null ? this.brand.hashCode() : 0)) * 37) + (this.model != null ? this.model.hashCode() : 0)) * 37) + (this.edgeStatus != null ? this.edgeStatus.hashCode() : 0)) * 37) + (this.apdid != null ? this.apdid.hashCode() : 0)) * 37) + (this.apdidToken != null ? this.apdidToken.hashCode() : 0)) * 37) + (this.credibleTimestamp != null ? this.credibleTimestamp.hashCode() : 0)) * 37) + (this.policyPackVersion != null ? this.policyPackVersion.hashCode() : 0)) * 37) + (this.secureData != null ? this.secureData.hashCode() : 0)) * 37;
        if (this.extData != null) {
            i2 = this.extData.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
