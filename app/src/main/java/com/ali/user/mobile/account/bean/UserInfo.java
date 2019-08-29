package com.ali.user.mobile.account.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.ali.user.mobile.account.AuthUtil;
import com.ali.user.mobile.util.StringUtil;
import java.io.Serializable;

public class UserInfo implements Parcelable, Serializable, Cloneable {
    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        public final UserInfo[] newArray(int i) {
            return new UserInfo[i];
        }

        public final UserInfo createFromParcel(Parcel parcel) {
            return new UserInfo(parcel);
        }
    };
    public static final String GENDER_FEMALE = "f";
    public static final String GENDER_MALE = "m";
    private static final String sBooleanFalse = "false";
    private static final String sBooleanTrue = "true";
    private static final long serialVersionUID = 1;
    private String autoLogin;
    private String customerType;
    private String exterToken;
    private String gender;
    private String havanaId;
    private String isBindCard;
    private String isCertified;
    private String isNewUser;
    private String isShowWalletEditionSwitch = sBooleanFalse;
    private String isWirelessUser;
    private String loginEmail;
    private String loginMobile;
    private String loginTime;
    private String loginToken;
    private String logonId;
    private String memberGrade;
    private String mobileNumber;
    private String nick;
    private String noPayPwd;
    private String noQueryPwdUser;
    private String otherLoginId;
    private String realName;
    private String realNamed;
    private String sessionId;
    private String shippingAddressCount;
    private String studentCertify;
    private String taobaoNick;
    private String taobaoSid;
    private String userAvatar;
    private String userId;
    private String userName;
    private String userType;
    private String walletEdition;

    public int describeContents() {
        return 0;
    }

    public boolean isNoQueryPwdUser() {
        return sBooleanTrue.equalsIgnoreCase(this.noQueryPwdUser);
    }

    public String getNoQueryPwdUser() {
        return this.noQueryPwdUser;
    }

    public void setNoQueryPwdUser(String str) {
        this.noQueryPwdUser = str;
    }

    public String getLoginEmail() {
        return this.loginEmail;
    }

    public void setLoginEmail(String str) {
        this.loginEmail = str;
    }

    public String getLoginMobile() {
        return this.loginMobile;
    }

    public void setLoginMobile(String str) {
        this.loginMobile = str;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String str) {
        this.gender = str;
    }

    public String getTaobaoNick() {
        return this.taobaoNick;
    }

    public void setTaobaoNick(String str) {
        this.taobaoNick = str;
    }

    public String getStudentCertify() {
        return this.studentCertify;
    }

    public void setStudentCertify(String str) {
        this.studentCertify = str;
    }

    public String getShippingAddressCount() {
        return this.shippingAddressCount;
    }

    public void setShippingAddressCount(String str) {
        this.shippingAddressCount = str;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String str) {
        this.userType = str;
    }

    public String getHavanaId() {
        return this.havanaId;
    }

    public void setHavanaId(String str) {
        this.havanaId = str;
    }

    public String getMemberGrade() {
        return this.memberGrade;
    }

    public void setMemberGrade(String str) {
        this.memberGrade = str;
    }

    @Deprecated
    public String getMiniMode() {
        return this.walletEdition;
    }

    @Deprecated
    public void setMiniMode(String str) {
        this.walletEdition = str;
    }

    @Deprecated
    public boolean isShowMiniSwitch() {
        return sBooleanTrue.equals(this.isShowWalletEditionSwitch);
    }

    @Deprecated
    public void setShowMiniSwitch(boolean z) {
        this.isShowWalletEditionSwitch = String.valueOf(z);
    }

    public String getWalletEdition() {
        return this.walletEdition;
    }

    public void setWalletEdition(String str) {
        this.walletEdition = str;
    }

    public boolean isShowWalletEditionSwitch() {
        return sBooleanTrue.equals(this.isShowWalletEditionSwitch);
    }

    public void setShowWalletEditionSwitch(boolean z) {
        this.isShowWalletEditionSwitch = String.valueOf(z);
    }

    public String isShowWalletEditionSwitchStr() {
        return this.isShowWalletEditionSwitch;
    }

    public void setShowWalletEditionSwitchStr(String str) {
        this.isShowWalletEditionSwitch = str;
    }

    @Deprecated
    public String getNike() {
        return this.nick;
    }

    @Deprecated
    public void setNike(String str) {
        this.nick = str;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String str) {
        this.nick = str;
    }

    @Deprecated
    public String getRealName() {
        return getUserName();
    }

    @Deprecated
    public void setRealName(String str) {
        this.realName = str;
    }

    public String getShowName() {
        if (!"2".equals(this.customerType)) {
            return "Y".equalsIgnoreCase(this.isCertified) ? getUserName() : "";
        }
        if (!TextUtils.isEmpty(this.nick)) {
            return this.nick;
        }
        if ("REALNAMED".equalsIgnoreCase(this.realNamed) || "Y".equalsIgnoreCase(this.isCertified)) {
            return getUserName();
        }
        return "";
    }

    public boolean isNewUser() {
        return sBooleanTrue.equals(this.isNewUser);
    }

    public void setNewUser(boolean z) {
        this.isNewUser = String.valueOf(z);
    }

    public String getIsNewUserStr() {
        return this.isNewUser;
    }

    public void setIsNewUserStr(String str) {
        this.isNewUser = str;
    }

    public String getCustomerType() {
        return this.customerType;
    }

    public void setCustomerType(String str) {
        this.customerType = str;
    }

    public boolean isWirelessUser() {
        return sBooleanTrue.equals(this.isWirelessUser);
    }

    public void setWirelessUser(boolean z) {
        this.isWirelessUser = String.valueOf(z);
    }

    public String getIsWirelessUserStr() {
        return this.isWirelessUser;
    }

    public void setIsWirelessUserStr(String str) {
        this.isWirelessUser = str;
    }

    public String getLoginToken() {
        return this.loginToken;
    }

    public void setLoginToken(String str) {
        this.loginToken = str;
    }

    public String getTaobaoSid() {
        return this.taobaoSid;
    }

    public void setTaobaoSid(String str) {
        this.taobaoSid = str;
    }

    public String getExternToken() {
        return this.exterToken;
    }

    public void setExternToken(String str) {
        this.exterToken = str;
    }

    public String getIsCertified() {
        return this.isCertified;
    }

    public void setIsCertified(String str) {
        this.isCertified = str;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String getLogonId() {
        return this.logonId;
    }

    public String getSecuredLogonId() {
        return StringUtil.c(this.logonId);
    }

    public void setLogonId(String str) {
        this.logonId = str;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String str) {
        this.userName = str;
    }

    public String getUserAvatar() {
        return this.userAvatar;
    }

    public void setUserAvatar(String str) {
        this.userAvatar = str;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public boolean isAutoLogin() {
        return sBooleanTrue.equals(this.autoLogin);
    }

    public void setAutoLogin(boolean z) {
        if (!z) {
            AuthUtil.a("UserInfo", "setAutoLogin=false");
        }
        this.autoLogin = String.valueOf(z);
        AuthUtil.a(null, this.logonId, z);
    }

    public String getIsAutoLoginStr() {
        return this.autoLogin;
    }

    public void setIsAutoLoginStr(String str) {
        this.autoLogin = str;
    }

    public String getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(String str) {
        this.loginTime = str;
    }

    public String getNoPayPwd() {
        return this.noPayPwd;
    }

    public void setNoPayPwd(String str) {
        this.noPayPwd = str;
    }

    public String getMobileNumber() {
        return this.mobileNumber == null ? "" : this.mobileNumber;
    }

    public String getSecuredMobileNumber() {
        if (this.mobileNumber == null) {
            return "";
        }
        return StringUtil.c(this.mobileNumber);
    }

    public void setMobileNumber(String str) {
        this.mobileNumber = str;
    }

    public boolean isBindCard() {
        return sBooleanTrue.equals(this.isBindCard);
    }

    public void setBindCard(boolean z) {
        this.isBindCard = String.valueOf(z);
    }

    public String getIsBindCardStr() {
        return this.isBindCard;
    }

    public void setIsBindCardStr(String str) {
        this.isBindCard = str;
    }

    public String getRealNamed() {
        return this.realNamed;
    }

    public void setRealNamed(String str) {
        this.realNamed = str;
    }

    public boolean isCertifyStatusOK() {
        return "REALNAMED".equalsIgnoreCase(getRealNamed()) || "Y".equalsIgnoreCase(getIsCertified());
    }

    public String toString() {
        return this.logonId;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.logonId);
        parcel.writeString(this.userId);
        parcel.writeString(this.userName);
        parcel.writeString(this.userAvatar);
        parcel.writeString(this.sessionId);
        parcel.writeString(this.autoLogin);
        parcel.writeString(this.loginTime);
        parcel.writeString(this.noPayPwd);
        parcel.writeString(this.mobileNumber);
        parcel.writeString(this.isCertified);
        parcel.writeString(this.taobaoSid);
        parcel.writeString(this.exterToken);
        parcel.writeString(this.loginToken);
        parcel.writeString(this.isWirelessUser);
        parcel.writeString(this.isBindCard);
        parcel.writeString(this.realNamed);
        parcel.writeString(this.customerType);
        parcel.writeSerializable(this.isNewUser);
        parcel.writeString(this.havanaId);
        parcel.writeString(this.memberGrade);
        parcel.writeString(this.walletEdition);
        parcel.writeString(this.isShowWalletEditionSwitch);
        parcel.writeString(this.nick);
        parcel.writeString(this.realName);
        parcel.writeString(this.userType);
        parcel.writeString(this.taobaoNick);
        parcel.writeString(this.loginEmail);
        parcel.writeString(this.loginMobile);
        parcel.writeString(this.studentCertify);
        parcel.writeString(this.shippingAddressCount);
        parcel.writeString(this.gender);
        parcel.writeString(this.otherLoginId);
        parcel.writeString(this.noQueryPwdUser);
    }

    public UserInfo(Parcel parcel) {
        this.logonId = parcel.readString();
        this.userId = parcel.readString();
        this.userName = parcel.readString();
        this.userAvatar = parcel.readString();
        this.sessionId = parcel.readString();
        this.autoLogin = parcel.readString();
        this.loginTime = parcel.readString();
        this.noPayPwd = parcel.readString();
        this.mobileNumber = parcel.readString();
        this.isCertified = parcel.readString();
        this.taobaoSid = parcel.readString();
        this.exterToken = parcel.readString();
        this.loginToken = parcel.readString();
        this.isWirelessUser = parcel.readString();
        this.isBindCard = parcel.readString();
        this.realNamed = parcel.readString();
        this.customerType = parcel.readString();
        this.isNewUser = parcel.readString();
        this.havanaId = parcel.readString();
        this.memberGrade = parcel.readString();
        this.walletEdition = parcel.readString();
        this.isShowWalletEditionSwitch = parcel.readString();
        this.nick = parcel.readString();
        this.realName = parcel.readString();
        this.userType = parcel.readString();
        this.taobaoNick = parcel.readString();
        this.loginEmail = parcel.readString();
        this.loginMobile = parcel.readString();
        this.studentCertify = parcel.readString();
        this.shippingAddressCount = parcel.readString();
        this.gender = parcel.readString();
        this.otherLoginId = parcel.readString();
        this.noQueryPwdUser = parcel.readString();
    }

    public UserInfo() {
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (Exception unused) {
            return null;
        }
    }

    public String getOtherLoginId() {
        return this.otherLoginId;
    }

    public void setOtherLoginId(String str) {
        this.otherLoginId = str;
    }
}
