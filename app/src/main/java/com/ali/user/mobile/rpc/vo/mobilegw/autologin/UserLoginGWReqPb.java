package com.ali.user.mobile.rpc.vo.mobilegw.autologin;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.List;

public final class UserLoginGWReqPb extends Message {
    public static final int TAG_CELLID = 27;
    public static final int TAG_CHANNELS = 12;
    public static final int TAG_CLIENTDIGEST = 13;
    public static final int TAG_CLIENTID = 18;
    public static final int TAG_DEVICETOKEN = 15;
    public static final int TAG_EXTERNPARAMS = 32;
    public static final int TAG_LOCATION = 28;
    public static final int TAG_LOGINCHECKCODE = 5;
    public static final int TAG_LOGINID = 1;
    public static final int TAG_LOGINPASSWORD = 4;
    public static final int TAG_LOGINTYPE = 2;
    public static final int TAG_LOGINWTHPWD = 3;
    public static final int TAG_MAC = 26;
    public static final int TAG_MSPCLIENTKEY = 24;
    public static final int TAG_MSPIMEI = 23;
    public static final int TAG_MSPIMSI = 22;
    public static final int TAG_MSPTID = 21;
    public static final int TAG_OPERATORTYPE = 31;
    public static final int TAG_OSVERSION = 10;
    public static final int TAG_PRODUCTID = 8;
    public static final int TAG_PRODUCTVERSION = 9;
    public static final int TAG_SCREENHIGH = 17;
    public static final int TAG_SCREENWIDTH = 16;
    public static final int TAG_SECTS = 14;
    public static final int TAG_SOURCEID = 25;
    public static final int TAG_TBCHECKCODE = 7;
    public static final int TAG_TBCHECKCODEID = 6;
    public static final int TAG_USERAGENT = 11;
    public static final int TAG_VIMEI = 30;
    public static final int TAG_VIMSI = 29;
    public static final int TAG_WALLETCLIENTKEY = 20;
    public static final int TAG_WALLETTID = 19;
    @ProtoField(tag = 27, type = Datatype.STRING)
    public String cellId;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String channels;
    @ProtoField(tag = 13, type = Datatype.STRING)
    public String clientDigest;
    @ProtoField(tag = 18, type = Datatype.STRING)
    public String clientId;
    @ProtoField(tag = 15, type = Datatype.STRING)
    public String deviceToken;
    @ProtoField(label = Label.REPEATED, tag = 32)
    public List<ExternParamsWithout> externParams;
    @ProtoField(tag = 28, type = Datatype.STRING)
    public String location;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String loginCheckCode;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String loginId;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String loginPassword;
    @ProtoField(tag = 2, type = Datatype.ENUM)
    public LoginTypeWithout loginType;
    @ProtoField(tag = 3, type = Datatype.ENUM)
    public LoginWithout loginWthPwd;
    @ProtoField(tag = 26, type = Datatype.STRING)
    public String mac;
    @ProtoField(tag = 24, type = Datatype.STRING)
    public String mspClientKey;
    @ProtoField(tag = 23, type = Datatype.STRING)
    public String mspImei;
    @ProtoField(tag = 22, type = Datatype.STRING)
    public String mspImsi;
    @ProtoField(tag = 21, type = Datatype.STRING)
    public String mspTid;
    @ProtoField(tag = 31, type = Datatype.STRING)
    public String operatorType;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String osVersion;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String productId;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String productVersion;
    @ProtoField(tag = 17, type = Datatype.INT32)
    public Integer screenHigh;
    @ProtoField(tag = 16, type = Datatype.INT32)
    public Integer screenWidth;
    @ProtoField(tag = 14, type = Datatype.STRING)
    public String secTS;
    @ProtoField(tag = 25, type = Datatype.STRING)
    public String sourceId;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String tbCheckCode;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String tbCheckCodeId;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String userAgent;
    @ProtoField(tag = 30, type = Datatype.STRING)
    public String vimei;
    @ProtoField(tag = 29, type = Datatype.STRING)
    public String vimsi;
    @ProtoField(tag = 20, type = Datatype.STRING)
    public String walletClientKey;
    @ProtoField(tag = 19, type = Datatype.STRING)
    public String walletTid;

    public UserLoginGWReqPb(UserLoginGWReqPb userLoginGWReqPb) {
        super(userLoginGWReqPb);
        if (userLoginGWReqPb != null) {
            this.loginId = userLoginGWReqPb.loginId;
            this.loginType = userLoginGWReqPb.loginType;
            this.loginWthPwd = userLoginGWReqPb.loginWthPwd;
            this.loginPassword = userLoginGWReqPb.loginPassword;
            this.loginCheckCode = userLoginGWReqPb.loginCheckCode;
            this.tbCheckCodeId = userLoginGWReqPb.tbCheckCodeId;
            this.tbCheckCode = userLoginGWReqPb.tbCheckCode;
            this.productId = userLoginGWReqPb.productId;
            this.productVersion = userLoginGWReqPb.productVersion;
            this.osVersion = userLoginGWReqPb.osVersion;
            this.userAgent = userLoginGWReqPb.userAgent;
            this.channels = userLoginGWReqPb.channels;
            this.clientDigest = userLoginGWReqPb.clientDigest;
            this.secTS = userLoginGWReqPb.secTS;
            this.deviceToken = userLoginGWReqPb.deviceToken;
            this.screenWidth = userLoginGWReqPb.screenWidth;
            this.screenHigh = userLoginGWReqPb.screenHigh;
            this.clientId = userLoginGWReqPb.clientId;
            this.walletTid = userLoginGWReqPb.walletTid;
            this.walletClientKey = userLoginGWReqPb.walletClientKey;
            this.mspTid = userLoginGWReqPb.mspTid;
            this.mspImsi = userLoginGWReqPb.mspImsi;
            this.mspImei = userLoginGWReqPb.mspImei;
            this.mspClientKey = userLoginGWReqPb.mspClientKey;
            this.sourceId = userLoginGWReqPb.sourceId;
            this.mac = userLoginGWReqPb.mac;
            this.cellId = userLoginGWReqPb.cellId;
            this.location = userLoginGWReqPb.location;
            this.vimsi = userLoginGWReqPb.vimsi;
            this.vimei = userLoginGWReqPb.vimei;
            this.operatorType = userLoginGWReqPb.operatorType;
            this.externParams = copyOf(userLoginGWReqPb.externParams);
        }
    }

    public UserLoginGWReqPb() {
    }

    public final UserLoginGWReqPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.loginId = (String) obj;
                break;
            case 2:
                this.loginType = (LoginTypeWithout) obj;
                break;
            case 3:
                this.loginWthPwd = (LoginWithout) obj;
                break;
            case 4:
                this.loginPassword = (String) obj;
                break;
            case 5:
                this.loginCheckCode = (String) obj;
                break;
            case 6:
                this.tbCheckCodeId = (String) obj;
                break;
            case 7:
                this.tbCheckCode = (String) obj;
                break;
            case 8:
                this.productId = (String) obj;
                break;
            case 9:
                this.productVersion = (String) obj;
                break;
            case 10:
                this.osVersion = (String) obj;
                break;
            case 11:
                this.userAgent = (String) obj;
                break;
            case 12:
                this.channels = (String) obj;
                break;
            case 13:
                this.clientDigest = (String) obj;
                break;
            case 14:
                this.secTS = (String) obj;
                break;
            case 15:
                this.deviceToken = (String) obj;
                break;
            case 16:
                this.screenWidth = (Integer) obj;
                break;
            case 17:
                this.screenHigh = (Integer) obj;
                break;
            case 18:
                this.clientId = (String) obj;
                break;
            case 19:
                this.walletTid = (String) obj;
                break;
            case 20:
                this.walletClientKey = (String) obj;
                break;
            case 21:
                this.mspTid = (String) obj;
                break;
            case 22:
                this.mspImsi = (String) obj;
                break;
            case 23:
                this.mspImei = (String) obj;
                break;
            case 24:
                this.mspClientKey = (String) obj;
                break;
            case 25:
                this.sourceId = (String) obj;
                break;
            case 26:
                this.mac = (String) obj;
                break;
            case 27:
                this.cellId = (String) obj;
                break;
            case 28:
                this.location = (String) obj;
                break;
            case 29:
                this.vimsi = (String) obj;
                break;
            case 30:
                this.vimei = (String) obj;
                break;
            case 31:
                this.operatorType = (String) obj;
                break;
            case 32:
                this.externParams = immutableCopyOf((List) obj);
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UserLoginGWReqPb)) {
            return false;
        }
        UserLoginGWReqPb userLoginGWReqPb = (UserLoginGWReqPb) obj;
        return equals((Object) this.loginId, (Object) userLoginGWReqPb.loginId) && equals((Object) this.loginType, (Object) userLoginGWReqPb.loginType) && equals((Object) this.loginWthPwd, (Object) userLoginGWReqPb.loginWthPwd) && equals((Object) this.loginPassword, (Object) userLoginGWReqPb.loginPassword) && equals((Object) this.loginCheckCode, (Object) userLoginGWReqPb.loginCheckCode) && equals((Object) this.tbCheckCodeId, (Object) userLoginGWReqPb.tbCheckCodeId) && equals((Object) this.tbCheckCode, (Object) userLoginGWReqPb.tbCheckCode) && equals((Object) this.productId, (Object) userLoginGWReqPb.productId) && equals((Object) this.productVersion, (Object) userLoginGWReqPb.productVersion) && equals((Object) this.osVersion, (Object) userLoginGWReqPb.osVersion) && equals((Object) this.userAgent, (Object) userLoginGWReqPb.userAgent) && equals((Object) this.channels, (Object) userLoginGWReqPb.channels) && equals((Object) this.clientDigest, (Object) userLoginGWReqPb.clientDigest) && equals((Object) this.secTS, (Object) userLoginGWReqPb.secTS) && equals((Object) this.deviceToken, (Object) userLoginGWReqPb.deviceToken) && equals((Object) this.screenWidth, (Object) userLoginGWReqPb.screenWidth) && equals((Object) this.screenHigh, (Object) userLoginGWReqPb.screenHigh) && equals((Object) this.clientId, (Object) userLoginGWReqPb.clientId) && equals((Object) this.walletTid, (Object) userLoginGWReqPb.walletTid) && equals((Object) this.walletClientKey, (Object) userLoginGWReqPb.walletClientKey) && equals((Object) this.mspTid, (Object) userLoginGWReqPb.mspTid) && equals((Object) this.mspImsi, (Object) userLoginGWReqPb.mspImsi) && equals((Object) this.mspImei, (Object) userLoginGWReqPb.mspImei) && equals((Object) this.mspClientKey, (Object) userLoginGWReqPb.mspClientKey) && equals((Object) this.sourceId, (Object) userLoginGWReqPb.sourceId) && equals((Object) this.mac, (Object) userLoginGWReqPb.mac) && equals((Object) this.cellId, (Object) userLoginGWReqPb.cellId) && equals((Object) this.location, (Object) userLoginGWReqPb.location) && equals((Object) this.vimsi, (Object) userLoginGWReqPb.vimsi) && equals((Object) this.vimei, (Object) userLoginGWReqPb.vimei) && equals((Object) this.operatorType, (Object) userLoginGWReqPb.operatorType) && equals(this.externParams, userLoginGWReqPb.externParams);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((this.loginId != null ? this.loginId.hashCode() : 0) * 37) + (this.loginType != null ? this.loginType.hashCode() : 0)) * 37) + (this.loginWthPwd != null ? this.loginWthPwd.hashCode() : 0)) * 37) + (this.loginPassword != null ? this.loginPassword.hashCode() : 0)) * 37) + (this.loginCheckCode != null ? this.loginCheckCode.hashCode() : 0)) * 37) + (this.tbCheckCodeId != null ? this.tbCheckCodeId.hashCode() : 0)) * 37) + (this.tbCheckCode != null ? this.tbCheckCode.hashCode() : 0)) * 37) + (this.productId != null ? this.productId.hashCode() : 0)) * 37) + (this.productVersion != null ? this.productVersion.hashCode() : 0)) * 37) + (this.osVersion != null ? this.osVersion.hashCode() : 0)) * 37) + (this.userAgent != null ? this.userAgent.hashCode() : 0)) * 37) + (this.channels != null ? this.channels.hashCode() : 0)) * 37) + (this.clientDigest != null ? this.clientDigest.hashCode() : 0)) * 37) + (this.secTS != null ? this.secTS.hashCode() : 0)) * 37) + (this.deviceToken != null ? this.deviceToken.hashCode() : 0)) * 37) + (this.screenWidth != null ? this.screenWidth.hashCode() : 0)) * 37) + (this.screenHigh != null ? this.screenHigh.hashCode() : 0)) * 37) + (this.clientId != null ? this.clientId.hashCode() : 0)) * 37) + (this.walletTid != null ? this.walletTid.hashCode() : 0)) * 37) + (this.walletClientKey != null ? this.walletClientKey.hashCode() : 0)) * 37) + (this.mspTid != null ? this.mspTid.hashCode() : 0)) * 37) + (this.mspImsi != null ? this.mspImsi.hashCode() : 0)) * 37) + (this.mspImei != null ? this.mspImei.hashCode() : 0)) * 37) + (this.mspClientKey != null ? this.mspClientKey.hashCode() : 0)) * 37) + (this.sourceId != null ? this.sourceId.hashCode() : 0)) * 37) + (this.mac != null ? this.mac.hashCode() : 0)) * 37) + (this.cellId != null ? this.cellId.hashCode() : 0)) * 37) + (this.location != null ? this.location.hashCode() : 0)) * 37) + (this.vimsi != null ? this.vimsi.hashCode() : 0)) * 37) + (this.vimei != null ? this.vimei.hashCode() : 0)) * 37;
        if (this.operatorType != null) {
            i2 = this.operatorType.hashCode();
        }
        int hashCode2 = ((hashCode + i2) * 37) + (this.externParams != null ? this.externParams.hashCode() : 1);
        this.hashCode = hashCode2;
        return hashCode2;
    }
}
