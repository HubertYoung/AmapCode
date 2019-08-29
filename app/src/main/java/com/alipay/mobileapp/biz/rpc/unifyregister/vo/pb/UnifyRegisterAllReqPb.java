package com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class UnifyRegisterAllReqPb extends Message {
    public static final String DEFAULT_ACCESSPOINT = "";
    public static final String DEFAULT_APDID = "";
    public static final String DEFAULT_APPID = "";
    public static final String DEFAULT_APPKEY = "";
    public static final String DEFAULT_CELLID = "";
    public static final String DEFAULT_CHANNEL = "";
    public static final String DEFAULT_CLIENTPOSTION = "";
    public static final String DEFAULT_CLIENTTYPE = "";
    public static final String DEFAULT_COUNTRYCODE = "";
    public static final String DEFAULT_DEVKEYSET = "";
    public static final List<ExternKVParam> DEFAULT_EXTERNPARAMS = Collections.emptyList();
    public static final String DEFAULT_IMEI = "";
    public static final String DEFAULT_IMSI = "";
    public static final String DEFAULT_ISPRISONBREAK = "";
    public static final String DEFAULT_LACID = "";
    public static final String DEFAULT_LONGONID = "";
    public static final String DEFAULT_MOBILEBRAND = "";
    public static final String DEFAULT_MOBILEMODEL = "";
    public static final String DEFAULT_PRODUCTID = "";
    public static final String DEFAULT_PRODUCTVERSION = "";
    public static final String DEFAULT_QUERYPASSWORD = "";
    public static final String DEFAULT_RDSINFO = "";
    public static final String DEFAULT_REGISTSCENECODE = "";
    public static final String DEFAULT_SCREENHIGH = "";
    public static final String DEFAULT_SCREENWIDTH = "";
    public static final String DEFAULT_SDKVERSION = "";
    public static final String DEFAULT_SECURITYID = "";
    public static final String DEFAULT_SMSCODE = "";
    public static final String DEFAULT_SYSTEMTYPE = "";
    public static final String DEFAULT_SYSTEMVERSION = "";
    public static final String DEFAULT_TID = "";
    public static final String DEFAULT_TOKEN = "";
    public static final String DEFAULT_TTID = "";
    public static final String DEFAULT_UMIDTOKEN = "";
    public static final String DEFAULT_USERAGENT = "";
    public static final String DEFAULT_UTDID = "";
    public static final String DEFAULT_WIFIMAC = "";
    public static final String DEFAULT_WIFINODENAME = "";
    public static final int TAG_ACCESSPOINT = 22;
    public static final int TAG_APDID = 4;
    public static final int TAG_APPID = 5;
    public static final int TAG_APPKEY = 3;
    public static final int TAG_CELLID = 29;
    public static final int TAG_CHANNEL = 15;
    public static final int TAG_CLIENTPOSTION = 23;
    public static final int TAG_CLIENTTYPE = 16;
    public static final int TAG_COUNTRYCODE = 32;
    public static final int TAG_DEVKEYSET = 30;
    public static final int TAG_EXTERNPARAMS = 38;
    public static final int TAG_IMEI = 14;
    public static final int TAG_IMSI = 13;
    public static final int TAG_ISPRISONBREAK = 31;
    public static final int TAG_LACID = 28;
    public static final int TAG_LONGONID = 1;
    public static final int TAG_MOBILEBRAND = 20;
    public static final int TAG_MOBILEMODEL = 21;
    public static final int TAG_PRODUCTID = 10;
    public static final int TAG_PRODUCTVERSION = 11;
    public static final int TAG_QUERYPASSWORD = 34;
    public static final int TAG_RDSINFO = 33;
    public static final int TAG_REGISTSCENECODE = 37;
    public static final int TAG_SCREENHIGH = 19;
    public static final int TAG_SCREENWIDTH = 18;
    public static final int TAG_SDKVERSION = 6;
    public static final int TAG_SECURITYID = 35;
    public static final int TAG_SMSCODE = 36;
    public static final int TAG_SYSTEMTYPE = 24;
    public static final int TAG_SYSTEMVERSION = 25;
    public static final int TAG_TID = 8;
    public static final int TAG_TOKEN = 2;
    public static final int TAG_TTID = 9;
    public static final int TAG_UMIDTOKEN = 12;
    public static final int TAG_USERAGENT = 17;
    public static final int TAG_UTDID = 7;
    public static final int TAG_WIFIMAC = 26;
    public static final int TAG_WIFINODENAME = 27;
    @ProtoField(tag = 14, type = Datatype.STRING)
    public String IMEI;
    @ProtoField(tag = 13, type = Datatype.STRING)
    public String IMSI;
    @ProtoField(tag = 22, type = Datatype.STRING)
    public String accessPoint;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String apdId;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String appId;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String appKey;
    @ProtoField(tag = 29, type = Datatype.STRING)
    public String cellId;
    @ProtoField(tag = 15, type = Datatype.STRING)
    public String channel;
    @ProtoField(tag = 23, type = Datatype.STRING)
    public String clientPostion;
    @ProtoField(tag = 16, type = Datatype.STRING)
    public String clientType;
    @ProtoField(tag = 32, type = Datatype.STRING)
    public String countryCode;
    @ProtoField(tag = 30, type = Datatype.STRING)
    public String devKeySet;
    @ProtoField(label = Label.REPEATED, tag = 38)
    public List<ExternKVParam> externParams;
    @ProtoField(tag = 31, type = Datatype.STRING)
    public String isPrisonBreak;
    @ProtoField(tag = 28, type = Datatype.STRING)
    public String lacId;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String longonId;
    @ProtoField(tag = 20, type = Datatype.STRING)
    public String mobileBrand;
    @ProtoField(tag = 21, type = Datatype.STRING)
    public String mobileModel;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String productId;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String productVersion;
    @ProtoField(tag = 34, type = Datatype.STRING)
    public String queryPassword;
    @ProtoField(tag = 33, type = Datatype.STRING)
    public String rdsInfo;
    @ProtoField(tag = 37, type = Datatype.STRING)
    public String registSceneCode;
    @ProtoField(tag = 19, type = Datatype.STRING)
    public String screenHigh;
    @ProtoField(tag = 18, type = Datatype.STRING)
    public String screenWidth;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String sdkVersion;
    @ProtoField(tag = 35, type = Datatype.STRING)
    public String securityId;
    @ProtoField(tag = 36, type = Datatype.STRING)
    public String smsCode;
    @ProtoField(tag = 24, type = Datatype.STRING)
    public String systemType;
    @ProtoField(tag = 25, type = Datatype.STRING)
    public String systemVersion;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String tid;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String token;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String ttid;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String umidToken;
    @ProtoField(tag = 17, type = Datatype.STRING)
    public String userAgent;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String utdid;
    @ProtoField(tag = 26, type = Datatype.STRING)
    public String wifiMac;
    @ProtoField(tag = 27, type = Datatype.STRING)
    public String wifiNodeName;

    public UnifyRegisterAllReqPb(UnifyRegisterAllReqPb unifyRegisterAllReqPb) {
        super(unifyRegisterAllReqPb);
        if (unifyRegisterAllReqPb != null) {
            this.longonId = unifyRegisterAllReqPb.longonId;
            this.token = unifyRegisterAllReqPb.token;
            this.appKey = unifyRegisterAllReqPb.appKey;
            this.apdId = unifyRegisterAllReqPb.apdId;
            this.appId = unifyRegisterAllReqPb.appId;
            this.sdkVersion = unifyRegisterAllReqPb.sdkVersion;
            this.utdid = unifyRegisterAllReqPb.utdid;
            this.tid = unifyRegisterAllReqPb.tid;
            this.ttid = unifyRegisterAllReqPb.ttid;
            this.productId = unifyRegisterAllReqPb.productId;
            this.productVersion = unifyRegisterAllReqPb.productVersion;
            this.umidToken = unifyRegisterAllReqPb.umidToken;
            this.IMSI = unifyRegisterAllReqPb.IMSI;
            this.IMEI = unifyRegisterAllReqPb.IMEI;
            this.channel = unifyRegisterAllReqPb.channel;
            this.clientType = unifyRegisterAllReqPb.clientType;
            this.userAgent = unifyRegisterAllReqPb.userAgent;
            this.screenWidth = unifyRegisterAllReqPb.screenWidth;
            this.screenHigh = unifyRegisterAllReqPb.screenHigh;
            this.mobileBrand = unifyRegisterAllReqPb.mobileBrand;
            this.mobileModel = unifyRegisterAllReqPb.mobileModel;
            this.accessPoint = unifyRegisterAllReqPb.accessPoint;
            this.clientPostion = unifyRegisterAllReqPb.clientPostion;
            this.systemType = unifyRegisterAllReqPb.systemType;
            this.systemVersion = unifyRegisterAllReqPb.systemVersion;
            this.wifiMac = unifyRegisterAllReqPb.wifiMac;
            this.wifiNodeName = unifyRegisterAllReqPb.wifiNodeName;
            this.lacId = unifyRegisterAllReqPb.lacId;
            this.cellId = unifyRegisterAllReqPb.cellId;
            this.devKeySet = unifyRegisterAllReqPb.devKeySet;
            this.isPrisonBreak = unifyRegisterAllReqPb.isPrisonBreak;
            this.countryCode = unifyRegisterAllReqPb.countryCode;
            this.rdsInfo = unifyRegisterAllReqPb.rdsInfo;
            this.queryPassword = unifyRegisterAllReqPb.queryPassword;
            this.securityId = unifyRegisterAllReqPb.securityId;
            this.smsCode = unifyRegisterAllReqPb.smsCode;
            this.registSceneCode = unifyRegisterAllReqPb.registSceneCode;
            this.externParams = copyOf(unifyRegisterAllReqPb.externParams);
        }
    }

    public UnifyRegisterAllReqPb() {
    }

    public final UnifyRegisterAllReqPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.longonId = (String) obj;
                break;
            case 2:
                this.token = (String) obj;
                break;
            case 3:
                this.appKey = (String) obj;
                break;
            case 4:
                this.apdId = (String) obj;
                break;
            case 5:
                this.appId = (String) obj;
                break;
            case 6:
                this.sdkVersion = (String) obj;
                break;
            case 7:
                this.utdid = (String) obj;
                break;
            case 8:
                this.tid = (String) obj;
                break;
            case 9:
                this.ttid = (String) obj;
                break;
            case 10:
                this.productId = (String) obj;
                break;
            case 11:
                this.productVersion = (String) obj;
                break;
            case 12:
                this.umidToken = (String) obj;
                break;
            case 13:
                this.IMSI = (String) obj;
                break;
            case 14:
                this.IMEI = (String) obj;
                break;
            case 15:
                this.channel = (String) obj;
                break;
            case 16:
                this.clientType = (String) obj;
                break;
            case 17:
                this.userAgent = (String) obj;
                break;
            case 18:
                this.screenWidth = (String) obj;
                break;
            case 19:
                this.screenHigh = (String) obj;
                break;
            case 20:
                this.mobileBrand = (String) obj;
                break;
            case 21:
                this.mobileModel = (String) obj;
                break;
            case 22:
                this.accessPoint = (String) obj;
                break;
            case 23:
                this.clientPostion = (String) obj;
                break;
            case 24:
                this.systemType = (String) obj;
                break;
            case 25:
                this.systemVersion = (String) obj;
                break;
            case 26:
                this.wifiMac = (String) obj;
                break;
            case 27:
                this.wifiNodeName = (String) obj;
                break;
            case 28:
                this.lacId = (String) obj;
                break;
            case 29:
                this.cellId = (String) obj;
                break;
            case 30:
                this.devKeySet = (String) obj;
                break;
            case 31:
                this.isPrisonBreak = (String) obj;
                break;
            case 32:
                this.countryCode = (String) obj;
                break;
            case 33:
                this.rdsInfo = (String) obj;
                break;
            case 34:
                this.queryPassword = (String) obj;
                break;
            case 35:
                this.securityId = (String) obj;
                break;
            case 36:
                this.smsCode = (String) obj;
                break;
            case 37:
                this.registSceneCode = (String) obj;
                break;
            case 38:
                this.externParams = immutableCopyOf((List) obj);
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UnifyRegisterAllReqPb)) {
            return false;
        }
        UnifyRegisterAllReqPb unifyRegisterAllReqPb = (UnifyRegisterAllReqPb) obj;
        return equals((Object) this.longonId, (Object) unifyRegisterAllReqPb.longonId) && equals((Object) this.token, (Object) unifyRegisterAllReqPb.token) && equals((Object) this.appKey, (Object) unifyRegisterAllReqPb.appKey) && equals((Object) this.apdId, (Object) unifyRegisterAllReqPb.apdId) && equals((Object) this.appId, (Object) unifyRegisterAllReqPb.appId) && equals((Object) this.sdkVersion, (Object) unifyRegisterAllReqPb.sdkVersion) && equals((Object) this.utdid, (Object) unifyRegisterAllReqPb.utdid) && equals((Object) this.tid, (Object) unifyRegisterAllReqPb.tid) && equals((Object) this.ttid, (Object) unifyRegisterAllReqPb.ttid) && equals((Object) this.productId, (Object) unifyRegisterAllReqPb.productId) && equals((Object) this.productVersion, (Object) unifyRegisterAllReqPb.productVersion) && equals((Object) this.umidToken, (Object) unifyRegisterAllReqPb.umidToken) && equals((Object) this.IMSI, (Object) unifyRegisterAllReqPb.IMSI) && equals((Object) this.IMEI, (Object) unifyRegisterAllReqPb.IMEI) && equals((Object) this.channel, (Object) unifyRegisterAllReqPb.channel) && equals((Object) this.clientType, (Object) unifyRegisterAllReqPb.clientType) && equals((Object) this.userAgent, (Object) unifyRegisterAllReqPb.userAgent) && equals((Object) this.screenWidth, (Object) unifyRegisterAllReqPb.screenWidth) && equals((Object) this.screenHigh, (Object) unifyRegisterAllReqPb.screenHigh) && equals((Object) this.mobileBrand, (Object) unifyRegisterAllReqPb.mobileBrand) && equals((Object) this.mobileModel, (Object) unifyRegisterAllReqPb.mobileModel) && equals((Object) this.accessPoint, (Object) unifyRegisterAllReqPb.accessPoint) && equals((Object) this.clientPostion, (Object) unifyRegisterAllReqPb.clientPostion) && equals((Object) this.systemType, (Object) unifyRegisterAllReqPb.systemType) && equals((Object) this.systemVersion, (Object) unifyRegisterAllReqPb.systemVersion) && equals((Object) this.wifiMac, (Object) unifyRegisterAllReqPb.wifiMac) && equals((Object) this.wifiNodeName, (Object) unifyRegisterAllReqPb.wifiNodeName) && equals((Object) this.lacId, (Object) unifyRegisterAllReqPb.lacId) && equals((Object) this.cellId, (Object) unifyRegisterAllReqPb.cellId) && equals((Object) this.devKeySet, (Object) unifyRegisterAllReqPb.devKeySet) && equals((Object) this.isPrisonBreak, (Object) unifyRegisterAllReqPb.isPrisonBreak) && equals((Object) this.countryCode, (Object) unifyRegisterAllReqPb.countryCode) && equals((Object) this.rdsInfo, (Object) unifyRegisterAllReqPb.rdsInfo) && equals((Object) this.queryPassword, (Object) unifyRegisterAllReqPb.queryPassword) && equals((Object) this.securityId, (Object) unifyRegisterAllReqPb.securityId) && equals((Object) this.smsCode, (Object) unifyRegisterAllReqPb.smsCode) && equals((Object) this.registSceneCode, (Object) unifyRegisterAllReqPb.registSceneCode) && equals(this.externParams, unifyRegisterAllReqPb.externParams);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((this.longonId != null ? this.longonId.hashCode() : 0) * 37) + (this.token != null ? this.token.hashCode() : 0)) * 37) + (this.appKey != null ? this.appKey.hashCode() : 0)) * 37) + (this.apdId != null ? this.apdId.hashCode() : 0)) * 37) + (this.appId != null ? this.appId.hashCode() : 0)) * 37) + (this.sdkVersion != null ? this.sdkVersion.hashCode() : 0)) * 37) + (this.utdid != null ? this.utdid.hashCode() : 0)) * 37) + (this.tid != null ? this.tid.hashCode() : 0)) * 37) + (this.ttid != null ? this.ttid.hashCode() : 0)) * 37) + (this.productId != null ? this.productId.hashCode() : 0)) * 37) + (this.productVersion != null ? this.productVersion.hashCode() : 0)) * 37) + (this.umidToken != null ? this.umidToken.hashCode() : 0)) * 37) + (this.IMSI != null ? this.IMSI.hashCode() : 0)) * 37) + (this.IMEI != null ? this.IMEI.hashCode() : 0)) * 37) + (this.channel != null ? this.channel.hashCode() : 0)) * 37) + (this.clientType != null ? this.clientType.hashCode() : 0)) * 37) + (this.userAgent != null ? this.userAgent.hashCode() : 0)) * 37) + (this.screenWidth != null ? this.screenWidth.hashCode() : 0)) * 37) + (this.screenHigh != null ? this.screenHigh.hashCode() : 0)) * 37) + (this.mobileBrand != null ? this.mobileBrand.hashCode() : 0)) * 37) + (this.mobileModel != null ? this.mobileModel.hashCode() : 0)) * 37) + (this.accessPoint != null ? this.accessPoint.hashCode() : 0)) * 37) + (this.clientPostion != null ? this.clientPostion.hashCode() : 0)) * 37) + (this.systemType != null ? this.systemType.hashCode() : 0)) * 37) + (this.systemVersion != null ? this.systemVersion.hashCode() : 0)) * 37) + (this.wifiMac != null ? this.wifiMac.hashCode() : 0)) * 37) + (this.wifiNodeName != null ? this.wifiNodeName.hashCode() : 0)) * 37) + (this.lacId != null ? this.lacId.hashCode() : 0)) * 37) + (this.cellId != null ? this.cellId.hashCode() : 0)) * 37) + (this.devKeySet != null ? this.devKeySet.hashCode() : 0)) * 37) + (this.isPrisonBreak != null ? this.isPrisonBreak.hashCode() : 0)) * 37) + (this.countryCode != null ? this.countryCode.hashCode() : 0)) * 37) + (this.rdsInfo != null ? this.rdsInfo.hashCode() : 0)) * 37) + (this.queryPassword != null ? this.queryPassword.hashCode() : 0)) * 37) + (this.securityId != null ? this.securityId.hashCode() : 0)) * 37) + (this.smsCode != null ? this.smsCode.hashCode() : 0)) * 37;
        if (this.registSceneCode != null) {
            i2 = this.registSceneCode.hashCode();
        }
        int hashCode2 = ((hashCode + i2) * 37) + (this.externParams != null ? this.externParams.hashCode() : 1);
        this.hashCode = hashCode2;
        return hashCode2;
    }
}
