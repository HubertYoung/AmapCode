package com.ali.user.mobile.account.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserLoginResult implements Serializable {
    private static final long serialVersionUID = -5105269619141195490L;
    public String barcodePayToken;
    public boolean bindCard;
    public String currentProductVersion;
    public String customerType;
    public String downloadURL;
    public String existNewVersion = "0";
    public Map<String, String> extResAttrs = new HashMap();
    public String extern_token;
    public String headImg;
    public String iconUrl;
    public String isCertified;
    public String loginCheckCodeImg;
    public String loginCheckCodeUrl;
    public String loginContext;
    public String loginId;
    public String loginServerTime;
    public String loginToken;
    public String memo;
    public String mobileNo;
    public int resultStatus;
    public String sessionId;
    public String taobaoSid;
    public String tbCheckCodeId;
    public String tbCheckCodeUrl;
    public String userId;
    public String userName;
    public boolean wirelessUser;

    public void setResultStatus(int i) {
        this.resultStatus = i;
    }

    public int getResultStatus() {
        return this.resultStatus;
    }

    public void setMemo(String str) {
        this.memo = str;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setLoginId(String str) {
        this.loginId = str;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserName(String str) {
        this.userName = str;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setMobileNo(String str) {
        this.mobileNo = str;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public void setIsCertified(String str) {
        this.isCertified = str;
    }

    public String getIsCertified() {
        return this.isCertified;
    }

    public void setIconUrl(String str) {
        this.iconUrl = str;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setLoginServerTime(String str) {
        this.loginServerTime = str;
    }

    public String getLoginServerTime() {
        return this.loginServerTime;
    }

    public void setTaobaoSid(String str) {
        this.taobaoSid = str;
    }

    public String getTaobaoSid() {
        return this.taobaoSid;
    }

    public void setBarcodePayToken(String str) {
        this.barcodePayToken = str;
    }

    public String getBarcodePayToken() {
        return this.barcodePayToken;
    }

    public void setExtern_token(String str) {
        this.extern_token = str;
    }

    public String getExtern_token() {
        return this.extern_token;
    }

    public void setLoginToken(String str) {
        this.loginToken = str;
    }

    public String getLoginToken() {
        return this.loginToken;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setCurrentProductVersion(String str) {
        this.currentProductVersion = str;
    }

    public String getCurrentProductVersion() {
        return this.currentProductVersion;
    }

    public void setExistNewVersion(String str) {
        this.existNewVersion = str;
    }

    public String getExistNewVersion() {
        return this.existNewVersion;
    }

    public void setDownloadURL(String str) {
        this.downloadURL = str;
    }

    public String getDownloadURL() {
        return this.downloadURL;
    }

    public void setLoginCheckCodeUrl(String str) {
        this.loginCheckCodeUrl = str;
    }

    public String getLoginCheckCodeUrl() {
        return this.loginCheckCodeUrl;
    }

    public void setLoginCheckCodeImg(String str) {
        this.loginCheckCodeImg = str;
    }

    public String getLoginCheckCodeImg() {
        return this.loginCheckCodeImg;
    }

    public void setTbCheckCodeId(String str) {
        this.tbCheckCodeId = str;
    }

    public String getTbCheckCodeId() {
        return this.tbCheckCodeId;
    }

    public void setTbCheckCodeUrl(String str) {
        this.tbCheckCodeUrl = str;
    }

    public String getTbCheckCodeUrl() {
        return this.tbCheckCodeUrl;
    }

    public void setLoginContext(String str) {
        this.loginContext = str;
    }

    public String getLoginContext() {
        return this.loginContext;
    }

    public boolean isWirelessUser() {
        return this.wirelessUser;
    }

    public void setWirelessUser(boolean z) {
        this.wirelessUser = z;
    }

    public boolean isBindCard() {
        return this.bindCard;
    }

    public void setBindCard(boolean z) {
        this.bindCard = z;
    }

    public String getHeadImg() {
        return this.headImg;
    }

    public void setHeadImg(String str) {
        this.headImg = str;
    }

    public Map<String, String> getExtResAttrs() {
        return this.extResAttrs;
    }

    public void setExtResAttrs(Map<String, String> map) {
        this.extResAttrs = map;
    }

    public String getCustomerType() {
        return this.customerType;
    }

    public void setCustomerType(String str) {
        this.customerType = str;
    }
}
