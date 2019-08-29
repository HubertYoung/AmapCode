package com.ali.user.mobile.rpc.vo.mobilegw.login;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class UnifyLoginReqPb extends Message {
    public static final String DEFAULT_ACCESSPOINT = "";
    public static final String DEFAULT_ALIPAYENVJSON = "";
    public static final String DEFAULT_APDID = "";
    public static final List<ExternParams> DEFAULT_APPDATA = Collections.emptyList();
    public static final String DEFAULT_APPID = "";
    public static final String DEFAULT_APPKEY = "";
    public static final String DEFAULT_CELLID = "";
    public static final String DEFAULT_CHANNEL = "";
    public static final String DEFAULT_CHECKCODE = "";
    public static final String DEFAULT_CHECKCODEID = "";
    public static final String DEFAULT_CLIENTPOSTION = "";
    public static final String DEFAULT_CLIENTTYPE = "";
    public static final String DEFAULT_DEVICEID = "";
    public static final List<ExternParams> DEFAULT_EXTERNPARAMS = Collections.emptyList();
    public static final String DEFAULT_IMEI = "";
    public static final String DEFAULT_IMSI = "";
    public static final String DEFAULT_ISPRISONBREAK = "";
    public static final String DEFAULT_LACID = "";
    public static final String DEFAULT_LOGINID = "";
    public static final String DEFAULT_LOGINPWD = "";
    public static final LoginType DEFAULT_LOGINTYPE = LoginType.alipay;
    public static final String DEFAULT_MOBILEBRAND = "";
    public static final String DEFAULT_MOBILEMODEL = "";
    public static final String DEFAULT_PRODUCTID = "";
    public static final String DEFAULT_PRODUCTVERSION = "";
    public static final String DEFAULT_SCENE = "";
    public static final Integer DEFAULT_SCREENHIGH = Integer.valueOf(0);
    public static final Integer DEFAULT_SCREENWIDTH = Integer.valueOf(0);
    public static final String DEFAULT_SDKVERSION = "";
    public static final String DEFAULT_SIGNDATA = "";
    public static final String DEFAULT_SSOTOKEN = "";
    public static final String DEFAULT_SYSTEMTYPE = "";
    public static final String DEFAULT_SYSTEMVERSION = "";
    public static final String DEFAULT_TAOBAOENVJSON = "";
    public static final String DEFAULT_TID = "";
    public static final String DEFAULT_TOKEN = "";
    public static final String DEFAULT_TTID = "";
    public static final String DEFAULT_UMIDTOKEN = "";
    public static final String DEFAULT_USERAGENT = "";
    public static final String DEFAULT_UTDID = "";
    public static final LoginWthPwd DEFAULT_VALIDATETPYE = LoginWthPwd.withpwd;
    public static final String DEFAULT_WIFIMAC = "";
    public static final String DEFAULT_WIFINODENAME = "";
    public static final int TAG_ACCESSPOINT = 28;
    public static final int TAG_ALIPAYENVJSON = 37;
    public static final int TAG_APDID = 12;
    public static final int TAG_APPDATA = 41;
    public static final int TAG_APPID = 2;
    public static final int TAG_APPKEY = 3;
    public static final int TAG_CELLID = 35;
    public static final int TAG_CHANNEL = 21;
    public static final int TAG_CHECKCODE = 11;
    public static final int TAG_CHECKCODEID = 10;
    public static final int TAG_CLIENTPOSTION = 29;
    public static final int TAG_CLIENTTYPE = 22;
    public static final int TAG_DEVICEID = 40;
    public static final int TAG_EXTERNPARAMS = 42;
    public static final int TAG_IMEI = 20;
    public static final int TAG_IMSI = 19;
    public static final int TAG_ISPRISONBREAK = 36;
    public static final int TAG_LACID = 34;
    public static final int TAG_LOGINID = 1;
    public static final int TAG_LOGINPWD = 7;
    public static final int TAG_LOGINTYPE = 4;
    public static final int TAG_MOBILEBRAND = 26;
    public static final int TAG_MOBILEMODEL = 27;
    public static final int TAG_PRODUCTID = 16;
    public static final int TAG_PRODUCTVERSION = 17;
    public static final int TAG_SCENE = 6;
    public static final int TAG_SCREENHIGH = 25;
    public static final int TAG_SCREENWIDTH = 24;
    public static final int TAG_SDKVERSION = 43;
    public static final int TAG_SIGNDATA = 9;
    public static final int TAG_SSOTOKEN = 8;
    public static final int TAG_SYSTEMTYPE = 30;
    public static final int TAG_SYSTEMVERSION = 31;
    public static final int TAG_TAOBAOENVJSON = 38;
    public static final int TAG_TID = 14;
    public static final int TAG_TOKEN = 39;
    public static final int TAG_TTID = 15;
    public static final int TAG_UMIDTOKEN = 18;
    public static final int TAG_USERAGENT = 23;
    public static final int TAG_UTDID = 13;
    public static final int TAG_VALIDATETPYE = 5;
    public static final int TAG_WIFIMAC = 32;
    public static final int TAG_WIFINODENAME = 33;
    @ProtoField(tag = 28, type = Datatype.STRING)
    public String accessPoint;
    @ProtoField(tag = 37, type = Datatype.STRING)
    public String alipayEnvJson;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String apdid;
    @ProtoField(label = Label.REPEATED, tag = 41)
    public List<ExternParams> appData;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String appId;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String appKey;
    @ProtoField(tag = 35, type = Datatype.STRING)
    public String cellId;
    @ProtoField(tag = 21, type = Datatype.STRING)
    public String channel;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String checkCode;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String checkCodeId;
    @ProtoField(tag = 29, type = Datatype.STRING)
    public String clientPostion;
    @ProtoField(tag = 22, type = Datatype.STRING)
    public String clientType;
    @ProtoField(tag = 40, type = Datatype.STRING)
    public String deviceId;
    @ProtoField(label = Label.REPEATED, tag = 42)
    public List<ExternParams> externParams;
    @ProtoField(tag = 20, type = Datatype.STRING)
    public String imei;
    @ProtoField(tag = 19, type = Datatype.STRING)
    public String imsi;
    @ProtoField(tag = 36, type = Datatype.STRING)
    public String isPrisonBreak;
    @ProtoField(tag = 34, type = Datatype.STRING)
    public String lacId;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String loginId;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String loginPwd;
    @ProtoField(tag = 4, type = Datatype.ENUM)
    public LoginType loginType;
    @ProtoField(tag = 26, type = Datatype.STRING)
    public String mobileBrand;
    @ProtoField(tag = 27, type = Datatype.STRING)
    public String mobileModel;
    @ProtoField(tag = 16, type = Datatype.STRING)
    public String productId;
    @ProtoField(tag = 17, type = Datatype.STRING)
    public String productVersion;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String scene;
    @ProtoField(tag = 25, type = Datatype.INT32)
    public Integer screenHigh;
    @ProtoField(tag = 24, type = Datatype.INT32)
    public Integer screenWidth;
    @ProtoField(tag = 43, type = Datatype.STRING)
    public String sdkVersion;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String signData;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String ssoToken;
    @ProtoField(tag = 30, type = Datatype.STRING)
    public String systemType;
    @ProtoField(tag = 31, type = Datatype.STRING)
    public String systemVersion;
    @ProtoField(tag = 38, type = Datatype.STRING)
    public String taobaoEnvJson;
    @ProtoField(tag = 14, type = Datatype.STRING)
    public String tid;
    @ProtoField(tag = 39, type = Datatype.STRING)
    public String token;
    @ProtoField(tag = 15, type = Datatype.STRING)
    public String ttid;
    @ProtoField(tag = 18, type = Datatype.STRING)
    public String umidToken;
    @ProtoField(tag = 23, type = Datatype.STRING)
    public String userAgent;
    @ProtoField(tag = 13, type = Datatype.STRING)
    public String utdid;
    @ProtoField(tag = 5, type = Datatype.ENUM)
    public LoginWthPwd validateTpye;
    @ProtoField(tag = 32, type = Datatype.STRING)
    public String wifiMac;
    @ProtoField(tag = 33, type = Datatype.STRING)
    public String wifiNodeName;

    public UnifyLoginReqPb(UnifyLoginReqPb unifyLoginReqPb) {
        super(unifyLoginReqPb);
        if (unifyLoginReqPb != null) {
            this.loginId = unifyLoginReqPb.loginId;
            this.appId = unifyLoginReqPb.appId;
            this.appKey = unifyLoginReqPb.appKey;
            this.loginType = unifyLoginReqPb.loginType;
            this.validateTpye = unifyLoginReqPb.validateTpye;
            this.scene = unifyLoginReqPb.scene;
            this.loginPwd = unifyLoginReqPb.loginPwd;
            this.ssoToken = unifyLoginReqPb.ssoToken;
            this.signData = unifyLoginReqPb.signData;
            this.checkCodeId = unifyLoginReqPb.checkCodeId;
            this.checkCode = unifyLoginReqPb.checkCode;
            this.apdid = unifyLoginReqPb.apdid;
            this.utdid = unifyLoginReqPb.utdid;
            this.tid = unifyLoginReqPb.tid;
            this.ttid = unifyLoginReqPb.ttid;
            this.productId = unifyLoginReqPb.productId;
            this.productVersion = unifyLoginReqPb.productVersion;
            this.umidToken = unifyLoginReqPb.umidToken;
            this.imsi = unifyLoginReqPb.imsi;
            this.imei = unifyLoginReqPb.imei;
            this.channel = unifyLoginReqPb.channel;
            this.clientType = unifyLoginReqPb.clientType;
            this.userAgent = unifyLoginReqPb.userAgent;
            this.screenWidth = unifyLoginReqPb.screenWidth;
            this.screenHigh = unifyLoginReqPb.screenHigh;
            this.mobileBrand = unifyLoginReqPb.mobileBrand;
            this.mobileModel = unifyLoginReqPb.mobileModel;
            this.accessPoint = unifyLoginReqPb.accessPoint;
            this.clientPostion = unifyLoginReqPb.clientPostion;
            this.systemType = unifyLoginReqPb.systemType;
            this.systemVersion = unifyLoginReqPb.systemVersion;
            this.wifiMac = unifyLoginReqPb.wifiMac;
            this.wifiNodeName = unifyLoginReqPb.wifiNodeName;
            this.lacId = unifyLoginReqPb.lacId;
            this.cellId = unifyLoginReqPb.cellId;
            this.isPrisonBreak = unifyLoginReqPb.isPrisonBreak;
            this.alipayEnvJson = unifyLoginReqPb.alipayEnvJson;
            this.taobaoEnvJson = unifyLoginReqPb.taobaoEnvJson;
            this.token = unifyLoginReqPb.token;
            this.deviceId = unifyLoginReqPb.deviceId;
            this.appData = copyOf(unifyLoginReqPb.appData);
            this.externParams = copyOf(unifyLoginReqPb.externParams);
            this.sdkVersion = unifyLoginReqPb.sdkVersion;
        }
    }

    public UnifyLoginReqPb() {
    }

    public final UnifyLoginReqPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.loginId = (String) obj;
                break;
            case 2:
                this.appId = (String) obj;
                break;
            case 3:
                this.appKey = (String) obj;
                break;
            case 4:
                this.loginType = (LoginType) obj;
                break;
            case 5:
                this.validateTpye = (LoginWthPwd) obj;
                break;
            case 6:
                this.scene = (String) obj;
                break;
            case 7:
                this.loginPwd = (String) obj;
                break;
            case 8:
                this.ssoToken = (String) obj;
                break;
            case 9:
                this.signData = (String) obj;
                break;
            case 10:
                this.checkCodeId = (String) obj;
                break;
            case 11:
                this.checkCode = (String) obj;
                break;
            case 12:
                this.apdid = (String) obj;
                break;
            case 13:
                this.utdid = (String) obj;
                break;
            case 14:
                this.tid = (String) obj;
                break;
            case 15:
                this.ttid = (String) obj;
                break;
            case 16:
                this.productId = (String) obj;
                break;
            case 17:
                this.productVersion = (String) obj;
                break;
            case 18:
                this.umidToken = (String) obj;
                break;
            case 19:
                this.imsi = (String) obj;
                break;
            case 20:
                this.imei = (String) obj;
                break;
            case 21:
                this.channel = (String) obj;
                break;
            case 22:
                this.clientType = (String) obj;
                break;
            case 23:
                this.userAgent = (String) obj;
                break;
            case 24:
                this.screenWidth = (Integer) obj;
                break;
            case 25:
                this.screenHigh = (Integer) obj;
                break;
            case 26:
                this.mobileBrand = (String) obj;
                break;
            case 27:
                this.mobileModel = (String) obj;
                break;
            case 28:
                this.accessPoint = (String) obj;
                break;
            case 29:
                this.clientPostion = (String) obj;
                break;
            case 30:
                this.systemType = (String) obj;
                break;
            case 31:
                this.systemVersion = (String) obj;
                break;
            case 32:
                this.wifiMac = (String) obj;
                break;
            case 33:
                this.wifiNodeName = (String) obj;
                break;
            case 34:
                this.lacId = (String) obj;
                break;
            case 35:
                this.cellId = (String) obj;
                break;
            case 36:
                this.isPrisonBreak = (String) obj;
                break;
            case 37:
                this.alipayEnvJson = (String) obj;
                break;
            case 38:
                this.taobaoEnvJson = (String) obj;
                break;
            case 39:
                this.token = (String) obj;
                break;
            case 40:
                this.deviceId = (String) obj;
                break;
            case 41:
                this.appData = immutableCopyOf((List) obj);
                break;
            case 42:
                this.externParams = immutableCopyOf((List) obj);
                break;
            case 43:
                this.sdkVersion = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UnifyLoginReqPb)) {
            return false;
        }
        UnifyLoginReqPb unifyLoginReqPb = (UnifyLoginReqPb) obj;
        return equals((Object) this.loginId, (Object) unifyLoginReqPb.loginId) && equals((Object) this.appId, (Object) unifyLoginReqPb.appId) && equals((Object) this.appKey, (Object) unifyLoginReqPb.appKey) && equals((Object) this.loginType, (Object) unifyLoginReqPb.loginType) && equals((Object) this.validateTpye, (Object) unifyLoginReqPb.validateTpye) && equals((Object) this.scene, (Object) unifyLoginReqPb.scene) && equals((Object) this.loginPwd, (Object) unifyLoginReqPb.loginPwd) && equals((Object) this.ssoToken, (Object) unifyLoginReqPb.ssoToken) && equals((Object) this.signData, (Object) unifyLoginReqPb.signData) && equals((Object) this.checkCodeId, (Object) unifyLoginReqPb.checkCodeId) && equals((Object) this.checkCode, (Object) unifyLoginReqPb.checkCode) && equals((Object) this.apdid, (Object) unifyLoginReqPb.apdid) && equals((Object) this.utdid, (Object) unifyLoginReqPb.utdid) && equals((Object) this.tid, (Object) unifyLoginReqPb.tid) && equals((Object) this.ttid, (Object) unifyLoginReqPb.ttid) && equals((Object) this.productId, (Object) unifyLoginReqPb.productId) && equals((Object) this.productVersion, (Object) unifyLoginReqPb.productVersion) && equals((Object) this.umidToken, (Object) unifyLoginReqPb.umidToken) && equals((Object) this.imsi, (Object) unifyLoginReqPb.imsi) && equals((Object) this.imei, (Object) unifyLoginReqPb.imei) && equals((Object) this.channel, (Object) unifyLoginReqPb.channel) && equals((Object) this.clientType, (Object) unifyLoginReqPb.clientType) && equals((Object) this.userAgent, (Object) unifyLoginReqPb.userAgent) && equals((Object) this.screenWidth, (Object) unifyLoginReqPb.screenWidth) && equals((Object) this.screenHigh, (Object) unifyLoginReqPb.screenHigh) && equals((Object) this.mobileBrand, (Object) unifyLoginReqPb.mobileBrand) && equals((Object) this.mobileModel, (Object) unifyLoginReqPb.mobileModel) && equals((Object) this.accessPoint, (Object) unifyLoginReqPb.accessPoint) && equals((Object) this.clientPostion, (Object) unifyLoginReqPb.clientPostion) && equals((Object) this.systemType, (Object) unifyLoginReqPb.systemType) && equals((Object) this.systemVersion, (Object) unifyLoginReqPb.systemVersion) && equals((Object) this.wifiMac, (Object) unifyLoginReqPb.wifiMac) && equals((Object) this.wifiNodeName, (Object) unifyLoginReqPb.wifiNodeName) && equals((Object) this.lacId, (Object) unifyLoginReqPb.lacId) && equals((Object) this.cellId, (Object) unifyLoginReqPb.cellId) && equals((Object) this.isPrisonBreak, (Object) unifyLoginReqPb.isPrisonBreak) && equals((Object) this.alipayEnvJson, (Object) unifyLoginReqPb.alipayEnvJson) && equals((Object) this.taobaoEnvJson, (Object) unifyLoginReqPb.taobaoEnvJson) && equals((Object) this.token, (Object) unifyLoginReqPb.token) && equals((Object) this.deviceId, (Object) unifyLoginReqPb.deviceId) && equals(this.appData, unifyLoginReqPb.appData) && equals(this.externParams, unifyLoginReqPb.externParams) && equals((Object) this.sdkVersion, (Object) unifyLoginReqPb.sdkVersion);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int i3 = 1;
        int hashCode = (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((this.loginId != null ? this.loginId.hashCode() : 0) * 37) + (this.appId != null ? this.appId.hashCode() : 0)) * 37) + (this.appKey != null ? this.appKey.hashCode() : 0)) * 37) + (this.loginType != null ? this.loginType.hashCode() : 0)) * 37) + (this.validateTpye != null ? this.validateTpye.hashCode() : 0)) * 37) + (this.scene != null ? this.scene.hashCode() : 0)) * 37) + (this.loginPwd != null ? this.loginPwd.hashCode() : 0)) * 37) + (this.ssoToken != null ? this.ssoToken.hashCode() : 0)) * 37) + (this.signData != null ? this.signData.hashCode() : 0)) * 37) + (this.checkCodeId != null ? this.checkCodeId.hashCode() : 0)) * 37) + (this.checkCode != null ? this.checkCode.hashCode() : 0)) * 37) + (this.apdid != null ? this.apdid.hashCode() : 0)) * 37) + (this.utdid != null ? this.utdid.hashCode() : 0)) * 37) + (this.tid != null ? this.tid.hashCode() : 0)) * 37) + (this.ttid != null ? this.ttid.hashCode() : 0)) * 37) + (this.productId != null ? this.productId.hashCode() : 0)) * 37) + (this.productVersion != null ? this.productVersion.hashCode() : 0)) * 37) + (this.umidToken != null ? this.umidToken.hashCode() : 0)) * 37) + (this.imsi != null ? this.imsi.hashCode() : 0)) * 37) + (this.imei != null ? this.imei.hashCode() : 0)) * 37) + (this.channel != null ? this.channel.hashCode() : 0)) * 37) + (this.clientType != null ? this.clientType.hashCode() : 0)) * 37) + (this.userAgent != null ? this.userAgent.hashCode() : 0)) * 37) + (this.screenWidth != null ? this.screenWidth.hashCode() : 0)) * 37) + (this.screenHigh != null ? this.screenHigh.hashCode() : 0)) * 37) + (this.mobileBrand != null ? this.mobileBrand.hashCode() : 0)) * 37) + (this.mobileModel != null ? this.mobileModel.hashCode() : 0)) * 37) + (this.accessPoint != null ? this.accessPoint.hashCode() : 0)) * 37) + (this.clientPostion != null ? this.clientPostion.hashCode() : 0)) * 37) + (this.systemType != null ? this.systemType.hashCode() : 0)) * 37) + (this.systemVersion != null ? this.systemVersion.hashCode() : 0)) * 37) + (this.wifiMac != null ? this.wifiMac.hashCode() : 0)) * 37) + (this.wifiNodeName != null ? this.wifiNodeName.hashCode() : 0)) * 37) + (this.lacId != null ? this.lacId.hashCode() : 0)) * 37) + (this.cellId != null ? this.cellId.hashCode() : 0)) * 37) + (this.isPrisonBreak != null ? this.isPrisonBreak.hashCode() : 0)) * 37) + (this.alipayEnvJson != null ? this.alipayEnvJson.hashCode() : 0)) * 37) + (this.taobaoEnvJson != null ? this.taobaoEnvJson.hashCode() : 0)) * 37) + (this.token != null ? this.token.hashCode() : 0)) * 37) + (this.deviceId != null ? this.deviceId.hashCode() : 0)) * 37) + (this.appData != null ? this.appData.hashCode() : 1)) * 37;
        if (this.externParams != null) {
            i3 = this.externParams.hashCode();
        }
        int i4 = (hashCode + i3) * 37;
        if (this.sdkVersion != null) {
            i2 = this.sdkVersion.hashCode();
        }
        int i5 = i4 + i2;
        this.hashCode = i5;
        return i5;
    }
}
