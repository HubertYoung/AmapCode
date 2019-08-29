package com.ali.user.mobile.account.bean;

public class UserMode {
    boolean checkLogin = false;
    String userExtInfo = null;
    UserInfo userInfo = null;

    public boolean isCheckLogin() {
        return this.checkLogin;
    }

    public void setCheckLogin(boolean z) {
        this.checkLogin = z;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfo userInfo2) {
        this.userInfo = userInfo2;
    }

    public String getUserExtInfo() {
        return this.userExtInfo;
    }

    public void setUserExtInfo(String str) {
        this.userExtInfo = str;
    }
}
