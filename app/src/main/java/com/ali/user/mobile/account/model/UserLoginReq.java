package com.ali.user.mobile.account.model;

import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import java.util.HashMap;
import java.util.Map;

public class UserLoginReq {
    public String cellId;
    public String channels;
    public String clientDigest;
    public String clientId;
    public String deviceToken;
    public Map<String, String> externParams = new HashMap();
    public String location;
    public String loginCheckCode;
    public String loginId;
    public String loginPassword;
    public String loginType = BehavorReporter.PROVIDE_BY_ALIPAY;
    public String loginWthPwd = "withpwd";
    public String mac;
    public String mspClientKey;
    public String mspImei;
    public String mspImsi;
    public String mspTid;
    public String osVersion;
    public String productId;
    public String productVersion;
    public String screenHigh;
    public String screenWidth;
    public String secTS;
    public String sourceId;
    public String tbCheckCode;
    public String tbCheckCodeId;
    public String userAgent;
    public String vimei;
    public String vimsi;
    public String walletClientKey;
    public String walletTid;

    public void setLoginId(String str) {
        this.loginId = str;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginType(String str) {
        this.loginType = str;
    }

    public String getLoginType() {
        return this.loginType;
    }

    public void setLoginWthPwd(String str) {
        this.loginWthPwd = str;
    }

    public String getLoginWthPwd() {
        return this.loginWthPwd;
    }

    public void setLoginPassword(String str) {
        this.loginPassword = str;
    }

    public String getLoginPassword() {
        return this.loginPassword;
    }

    public void setLoginCheckCode(String str) {
        this.loginCheckCode = str;
    }

    public String getLoginCheckCode() {
        return this.loginCheckCode;
    }

    public void setTbCheckCodeId(String str) {
        this.tbCheckCodeId = str;
    }

    public String getTbCheckCodeId() {
        return this.tbCheckCodeId;
    }

    public void setTbCheckCode(String str) {
        this.tbCheckCode = str;
    }

    public String getTbCheckCode() {
        return this.tbCheckCode;
    }

    public void setProductId(String str) {
        this.productId = str;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductVersion(String str) {
        this.productVersion = str;
    }

    public String getProductVersion() {
        return this.productVersion;
    }

    public void setOsVersion(String str) {
        this.osVersion = str;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public void setUserAgent(String str) {
        this.userAgent = str;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public void setChannels(String str) {
        this.channels = str;
    }

    public String getChannels() {
        return this.channels;
    }

    public void setClientDigest(String str) {
        this.clientDigest = str;
    }

    public String getClientDigest() {
        return this.clientDigest;
    }

    public void setSecTS(String str) {
        this.secTS = str;
    }

    public String getSecTS() {
        return this.secTS;
    }

    public void setDeviceToken(String str) {
        this.deviceToken = str;
    }

    public String getDeviceToken() {
        return this.deviceToken;
    }

    public void setScreenWidth(String str) {
        this.screenWidth = str;
    }

    public String getScreenWidth() {
        return this.screenWidth;
    }

    public void setScreenHigh(String str) {
        this.screenHigh = str;
    }

    public String getScreenHigh() {
        return this.screenHigh;
    }

    public void setClientId(String str) {
        this.clientId = str;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setWalletTid(String str) {
        this.walletTid = str;
    }

    public String getWalletTid() {
        return this.walletTid;
    }

    public void setWalletClientKey(String str) {
        this.walletClientKey = str;
    }

    public String getWalletClientKey() {
        return this.walletClientKey;
    }

    public void setMspTid(String str) {
        this.mspTid = str;
    }

    public String getMspTid() {
        return this.mspTid;
    }

    public void setMspImsi(String str) {
        this.mspImsi = str;
    }

    public String getMspImsi() {
        return this.mspImsi;
    }

    public void setMspImei(String str) {
        this.mspImei = str;
    }

    public String getMspImei() {
        return this.mspImei;
    }

    public void setMspClientKey(String str) {
        this.mspClientKey = str;
    }

    public String getMspClientKey() {
        return this.mspClientKey;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(String str) {
        this.sourceId = str;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String str) {
        this.mac = str;
    }

    public String getCellId() {
        return this.cellId;
    }

    public void setCellId(String str) {
        this.cellId = str;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String str) {
        this.location = str;
    }

    public String getVimsi() {
        return this.vimsi;
    }

    public void setVimsi(String str) {
        this.vimsi = str;
    }

    public String getVimei() {
        return this.vimei;
    }

    public void setVimei(String str) {
        this.vimei = str;
    }

    public void setExternParams(Map<String, String> map) {
        this.externParams = map;
    }

    public Map<String, String> getExternParams() {
        return this.externParams;
    }
}
