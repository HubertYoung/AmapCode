package com.ali.user.mobile.account.bean;

import java.util.Map;

public class UserLoginResultBiz {
    private String barcodePayToken;
    private String currentProductVersion;
    private String downloadURL;
    private String existNewVersion = "0";
    private Map<String, String> extResAttrs;
    private String extern_token;
    private String isCertified;
    private String loginCheckCodeImg;
    private String loginCheckCodeUrl;
    private boolean loginFlag;
    private String loginId;
    private String loginServerTime;
    private String loginToken;
    private String memo;
    private String mobileNo;
    private int resultStatus;
    private String securityPolicyRes;
    private String sessionId;
    private String taobaoSid;
    private String tbCheckCodeId;
    private String tbCheckCodeUrl;
    private String userId;
    private String userName;

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

    public void setLoginFlag(boolean z) {
        this.loginFlag = z;
    }

    public boolean isLoginFlag() {
        return this.loginFlag;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setDownloadURL(String str) {
        this.downloadURL = str;
    }

    public String getDownloadURL() {
        return this.downloadURL;
    }

    public void setLoginServerTime(String str) {
        this.loginServerTime = str;
    }

    public String getLoginServerTime() {
        return this.loginServerTime;
    }

    public void setLoginToken(String str) {
        this.loginToken = str;
    }

    public String getLoginToken() {
        return this.loginToken;
    }

    public void setExistNewVersion(String str) {
        this.existNewVersion = str;
    }

    public String getExistNewVersion() {
        return this.existNewVersion;
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

    public void setCurrentProductVersion(String str) {
        this.currentProductVersion = str;
    }

    public String getCurrentProductVersion() {
        return this.currentProductVersion;
    }

    public String getSecurityPolicyRes() {
        return this.securityPolicyRes;
    }

    public void setSecurityPolicyRes(String str) {
        this.securityPolicyRes = str;
    }

    public Map<String, String> getExtResAttrs() {
        return this.extResAttrs;
    }

    public void setExtResAttrs(Map<String, String> map) {
        this.extResAttrs = map;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[resultStatus:");
        sb.append(this.resultStatus);
        sb.append(",memo:");
        sb.append(this.memo);
        sb.append(",loginId:");
        sb.append(this.loginId);
        sb.append(",userId:");
        sb.append(this.userId);
        sb.append(",userName:");
        sb.append(this.userName);
        sb.append("]");
        return sb.toString();
    }
}
