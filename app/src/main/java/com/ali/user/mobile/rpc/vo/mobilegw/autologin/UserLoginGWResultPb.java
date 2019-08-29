package com.ali.user.mobile.rpc.vo.mobilegw.autologin;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.List;

public final class UserLoginGWResultPb extends Message {
    public static final int TAG_ACTIVESTATUS = 26;
    public static final int TAG_BARCODEPAYTOKEN = 29;
    public static final int TAG_CURRENTPRODUCTVERSION = 13;
    public static final int TAG_CUSTOMERTYPE = 25;
    public static final int TAG_DOWNLOADURL = 15;
    public static final int TAG_EXISTNEWVERSION = 14;
    public static final int TAG_EXTERN_TOKEN = 10;
    public static final int TAG_EXTRESATTRS = 28;
    public static final int TAG_HEADIMG = 27;
    public static final int TAG_ISBINDCARD = 23;
    public static final int TAG_ISCERTIFIED = 7;
    public static final int TAG_ISWIRELESSUSER = 22;
    public static final int TAG_LOGINCHECKCODEIMG = 17;
    public static final int TAG_LOGINCHECKCODEURL = 16;
    public static final int TAG_LOGINCONTEXT = 20;
    public static final int TAG_LOGINID = 3;
    public static final int TAG_LOGINSERVERTIME = 8;
    public static final int TAG_LOGINTOKEN = 11;
    public static final int TAG_MEMO = 2;
    public static final int TAG_MOBILENO = 6;
    public static final int TAG_OPERATORID = 21;
    public static final int TAG_OPERATORNAME = 24;
    public static final int TAG_RESULTSTATUS = 1;
    public static final int TAG_SESSIONID = 12;
    public static final int TAG_TAOBAOSID = 9;
    public static final int TAG_TBCHECKCODEID = 18;
    public static final int TAG_TBCHECKCODEURL = 19;
    public static final int TAG_USERID = 4;
    public static final int TAG_USERNAME = 5;
    @ProtoField(tag = 26, type = Datatype.STRING)
    public String activeStatus;
    @ProtoField(tag = 29, type = Datatype.STRING)
    public String barcodePayToken;
    @ProtoField(tag = 13, type = Datatype.STRING)
    public String currentProductVersion;
    @ProtoField(tag = 25, type = Datatype.STRING)
    public String customerType;
    @ProtoField(tag = 15, type = Datatype.STRING)
    public String downloadURL;
    @ProtoField(tag = 14, type = Datatype.STRING)
    public String existNewVersion;
    @ProtoField(label = Label.REPEATED, tag = 28)
    public List<ExternParamsWithout> extResAttrs;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String extern_token;
    @ProtoField(tag = 27, type = Datatype.STRING)
    public String headImg;
    @ProtoField(tag = 23, type = Datatype.BOOL)
    public Boolean isBindCard;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String isCertified;
    @ProtoField(tag = 22, type = Datatype.BOOL)
    public Boolean isWirelessUser;
    @ProtoField(tag = 17, type = Datatype.STRING)
    public String loginCheckCodeImg;
    @ProtoField(tag = 16, type = Datatype.STRING)
    public String loginCheckCodeUrl;
    @ProtoField(tag = 20, type = Datatype.STRING)
    public String loginContext;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String loginId;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String loginServerTime;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String loginToken;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String memo;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String mobileNo;
    @ProtoField(tag = 21, type = Datatype.STRING)
    public String operatorId;
    @ProtoField(tag = 24, type = Datatype.STRING)
    public String operatorName;
    @ProtoField(tag = 1, type = Datatype.INT32)
    public Integer resultStatus;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String sessionId;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String taobaoSid;
    @ProtoField(tag = 18, type = Datatype.STRING)
    public String tbCheckCodeId;
    @ProtoField(tag = 19, type = Datatype.STRING)
    public String tbCheckCodeUrl;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String userId;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String userName;

    public UserLoginGWResultPb(UserLoginGWResultPb userLoginGWResultPb) {
        super(userLoginGWResultPb);
        if (userLoginGWResultPb != null) {
            this.resultStatus = userLoginGWResultPb.resultStatus;
            this.memo = userLoginGWResultPb.memo;
            this.loginId = userLoginGWResultPb.loginId;
            this.userId = userLoginGWResultPb.userId;
            this.userName = userLoginGWResultPb.userName;
            this.mobileNo = userLoginGWResultPb.mobileNo;
            this.isCertified = userLoginGWResultPb.isCertified;
            this.loginServerTime = userLoginGWResultPb.loginServerTime;
            this.taobaoSid = userLoginGWResultPb.taobaoSid;
            this.extern_token = userLoginGWResultPb.extern_token;
            this.loginToken = userLoginGWResultPb.loginToken;
            this.sessionId = userLoginGWResultPb.sessionId;
            this.currentProductVersion = userLoginGWResultPb.currentProductVersion;
            this.existNewVersion = userLoginGWResultPb.existNewVersion;
            this.downloadURL = userLoginGWResultPb.downloadURL;
            this.loginCheckCodeUrl = userLoginGWResultPb.loginCheckCodeUrl;
            this.loginCheckCodeImg = userLoginGWResultPb.loginCheckCodeImg;
            this.tbCheckCodeId = userLoginGWResultPb.tbCheckCodeId;
            this.tbCheckCodeUrl = userLoginGWResultPb.tbCheckCodeUrl;
            this.loginContext = userLoginGWResultPb.loginContext;
            this.operatorId = userLoginGWResultPb.operatorId;
            this.isWirelessUser = userLoginGWResultPb.isWirelessUser;
            this.isBindCard = userLoginGWResultPb.isBindCard;
            this.operatorName = userLoginGWResultPb.operatorName;
            this.customerType = userLoginGWResultPb.customerType;
            this.activeStatus = userLoginGWResultPb.activeStatus;
            this.headImg = userLoginGWResultPb.headImg;
            this.extResAttrs = copyOf(userLoginGWResultPb.extResAttrs);
            this.barcodePayToken = userLoginGWResultPb.barcodePayToken;
        }
    }

    public UserLoginGWResultPb() {
    }

    public final UserLoginGWResultPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.resultStatus = (Integer) obj;
                break;
            case 2:
                this.memo = (String) obj;
                break;
            case 3:
                this.loginId = (String) obj;
                break;
            case 4:
                this.userId = (String) obj;
                break;
            case 5:
                this.userName = (String) obj;
                break;
            case 6:
                this.mobileNo = (String) obj;
                break;
            case 7:
                this.isCertified = (String) obj;
                break;
            case 8:
                this.loginServerTime = (String) obj;
                break;
            case 9:
                this.taobaoSid = (String) obj;
                break;
            case 10:
                this.extern_token = (String) obj;
                break;
            case 11:
                this.loginToken = (String) obj;
                break;
            case 12:
                this.sessionId = (String) obj;
                break;
            case 13:
                this.currentProductVersion = (String) obj;
                break;
            case 14:
                this.existNewVersion = (String) obj;
                break;
            case 15:
                this.downloadURL = (String) obj;
                break;
            case 16:
                this.loginCheckCodeUrl = (String) obj;
                break;
            case 17:
                this.loginCheckCodeImg = (String) obj;
                break;
            case 18:
                this.tbCheckCodeId = (String) obj;
                break;
            case 19:
                this.tbCheckCodeUrl = (String) obj;
                break;
            case 20:
                this.loginContext = (String) obj;
                break;
            case 21:
                this.operatorId = (String) obj;
                break;
            case 22:
                this.isWirelessUser = (Boolean) obj;
                break;
            case 23:
                this.isBindCard = (Boolean) obj;
                break;
            case 24:
                this.operatorName = (String) obj;
                break;
            case 25:
                this.customerType = (String) obj;
                break;
            case 26:
                this.activeStatus = (String) obj;
                break;
            case 27:
                this.headImg = (String) obj;
                break;
            case 28:
                this.extResAttrs = immutableCopyOf((List) obj);
                break;
            case 29:
                this.barcodePayToken = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UserLoginGWResultPb)) {
            return false;
        }
        UserLoginGWResultPb userLoginGWResultPb = (UserLoginGWResultPb) obj;
        return equals((Object) this.resultStatus, (Object) userLoginGWResultPb.resultStatus) && equals((Object) this.memo, (Object) userLoginGWResultPb.memo) && equals((Object) this.loginId, (Object) userLoginGWResultPb.loginId) && equals((Object) this.userId, (Object) userLoginGWResultPb.userId) && equals((Object) this.userName, (Object) userLoginGWResultPb.userName) && equals((Object) this.mobileNo, (Object) userLoginGWResultPb.mobileNo) && equals((Object) this.isCertified, (Object) userLoginGWResultPb.isCertified) && equals((Object) this.loginServerTime, (Object) userLoginGWResultPb.loginServerTime) && equals((Object) this.taobaoSid, (Object) userLoginGWResultPb.taobaoSid) && equals((Object) this.extern_token, (Object) userLoginGWResultPb.extern_token) && equals((Object) this.loginToken, (Object) userLoginGWResultPb.loginToken) && equals((Object) this.sessionId, (Object) userLoginGWResultPb.sessionId) && equals((Object) this.currentProductVersion, (Object) userLoginGWResultPb.currentProductVersion) && equals((Object) this.existNewVersion, (Object) userLoginGWResultPb.existNewVersion) && equals((Object) this.downloadURL, (Object) userLoginGWResultPb.downloadURL) && equals((Object) this.loginCheckCodeUrl, (Object) userLoginGWResultPb.loginCheckCodeUrl) && equals((Object) this.loginCheckCodeImg, (Object) userLoginGWResultPb.loginCheckCodeImg) && equals((Object) this.tbCheckCodeId, (Object) userLoginGWResultPb.tbCheckCodeId) && equals((Object) this.tbCheckCodeUrl, (Object) userLoginGWResultPb.tbCheckCodeUrl) && equals((Object) this.loginContext, (Object) userLoginGWResultPb.loginContext) && equals((Object) this.operatorId, (Object) userLoginGWResultPb.operatorId) && equals((Object) this.isWirelessUser, (Object) userLoginGWResultPb.isWirelessUser) && equals((Object) this.isBindCard, (Object) userLoginGWResultPb.isBindCard) && equals((Object) this.operatorName, (Object) userLoginGWResultPb.operatorName) && equals((Object) this.customerType, (Object) userLoginGWResultPb.customerType) && equals((Object) this.activeStatus, (Object) userLoginGWResultPb.activeStatus) && equals((Object) this.headImg, (Object) userLoginGWResultPb.headImg) && equals(this.extResAttrs, userLoginGWResultPb.extResAttrs) && equals((Object) this.barcodePayToken, (Object) userLoginGWResultPb.barcodePayToken);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((((((((((((((((((((((((((((((((((((((((this.resultStatus != null ? this.resultStatus.hashCode() : 0) * 37) + (this.memo != null ? this.memo.hashCode() : 0)) * 37) + (this.loginId != null ? this.loginId.hashCode() : 0)) * 37) + (this.userId != null ? this.userId.hashCode() : 0)) * 37) + (this.userName != null ? this.userName.hashCode() : 0)) * 37) + (this.mobileNo != null ? this.mobileNo.hashCode() : 0)) * 37) + (this.isCertified != null ? this.isCertified.hashCode() : 0)) * 37) + (this.loginServerTime != null ? this.loginServerTime.hashCode() : 0)) * 37) + (this.taobaoSid != null ? this.taobaoSid.hashCode() : 0)) * 37) + (this.extern_token != null ? this.extern_token.hashCode() : 0)) * 37) + (this.loginToken != null ? this.loginToken.hashCode() : 0)) * 37) + (this.sessionId != null ? this.sessionId.hashCode() : 0)) * 37) + (this.currentProductVersion != null ? this.currentProductVersion.hashCode() : 0)) * 37) + (this.existNewVersion != null ? this.existNewVersion.hashCode() : 0)) * 37) + (this.downloadURL != null ? this.downloadURL.hashCode() : 0)) * 37) + (this.loginCheckCodeUrl != null ? this.loginCheckCodeUrl.hashCode() : 0)) * 37) + (this.loginCheckCodeImg != null ? this.loginCheckCodeImg.hashCode() : 0)) * 37) + (this.tbCheckCodeId != null ? this.tbCheckCodeId.hashCode() : 0)) * 37) + (this.tbCheckCodeUrl != null ? this.tbCheckCodeUrl.hashCode() : 0)) * 37) + (this.loginContext != null ? this.loginContext.hashCode() : 0)) * 37) + (this.operatorId != null ? this.operatorId.hashCode() : 0)) * 37) + (this.isWirelessUser != null ? this.isWirelessUser.hashCode() : 0)) * 37) + (this.isBindCard != null ? this.isBindCard.hashCode() : 0)) * 37) + (this.operatorName != null ? this.operatorName.hashCode() : 0)) * 37) + (this.customerType != null ? this.customerType.hashCode() : 0)) * 37) + (this.activeStatus != null ? this.activeStatus.hashCode() : 0)) * 37) + (this.headImg != null ? this.headImg.hashCode() : 0)) * 37) + (this.extResAttrs != null ? this.extResAttrs.hashCode() : 1)) * 37;
        if (this.barcodePayToken != null) {
            i2 = this.barcodePayToken.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
