package com.ali.user.mobile.accountbiz.extservice;

import android.os.Bundle;
import com.ali.user.mobile.account.bean.UserInfo;

public class LoginContext {
    private Boolean accountType = Boolean.FALSE;
    private boolean directAuth = false;
    private boolean isForceNotShowLogin = false;
    private Boolean isLoginSucess;
    private Boolean isSettingGesture;
    private Bundle params;
    private boolean resetCookie = false;
    private Boolean showActivity;
    private boolean skipAutoLogin = false;
    private boolean skipCheckIsLogin = false;
    private boolean skipGestureApp = false;
    private boolean skipSelectAccountApp = false;
    private UserInfo userInfo;

    public boolean isForceNotShowLoginApp() {
        return this.isForceNotShowLogin;
    }

    public void setForceNotShowLogin(boolean z) {
        this.isForceNotShowLogin = z;
    }

    public boolean isDirectAuth() {
        return this.directAuth;
    }

    public void setDirectAuth(boolean z) {
        this.directAuth = z;
    }

    public Boolean getAccountType() {
        return this.accountType;
    }

    public void setAccountType(Boolean bool) {
        this.accountType = bool;
    }

    public Boolean getIsSettingGesture() {
        return this.isSettingGesture;
    }

    public void setIsSettingGesture(Boolean bool) {
        this.isSettingGesture = bool;
    }

    public LoginContext() {
    }

    public LoginContext(Bundle bundle, UserInfo userInfo2, boolean z, boolean z2, boolean z3) {
        this.params = bundle;
        this.userInfo = userInfo2;
        this.skipSelectAccountApp = z;
        this.skipGestureApp = z2;
        this.skipAutoLogin = z3;
    }

    public Bundle getParams() {
        return this.params;
    }

    public void setParams(Bundle bundle) {
        this.params = bundle;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfo userInfo2) {
        this.userInfo = userInfo2;
    }

    public boolean isSkipSelectAccountApp() {
        return this.skipSelectAccountApp;
    }

    public void setSkipSelectAccountApp(boolean z) {
        this.skipSelectAccountApp = z;
    }

    public boolean isSkipGestureApp() {
        return this.skipGestureApp;
    }

    public void setSkipGestureApp(boolean z) {
        this.skipGestureApp = z;
    }

    public boolean isSkipAutoLogin() {
        return this.skipAutoLogin;
    }

    public void setSkipAutoLogin(boolean z) {
        this.skipAutoLogin = z;
    }

    public Boolean getShowActivity() {
        return this.showActivity;
    }

    public void setShowActivity(Boolean bool) {
        this.showActivity = bool;
    }

    public Boolean getIsLoginSucess() {
        return this.isLoginSucess;
    }

    public void setIsLoginSucess(Boolean bool) {
        this.isLoginSucess = bool;
    }

    public boolean isSkipCheckIsLogin() {
        return this.skipCheckIsLogin;
    }

    public void setSkipCheckIsLogin(boolean z) {
        this.skipCheckIsLogin = z;
    }

    public boolean isResetCookie() {
        return this.resetCookie;
    }

    public void setResetCookie(boolean z) {
        this.resetCookie = z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LoginContext [params=");
        sb.append(this.params);
        sb.append(", userInfo=");
        sb.append(this.userInfo);
        sb.append(", skipSelectAccountApp=");
        sb.append(this.skipSelectAccountApp);
        sb.append(", skipGestureApp=");
        sb.append(this.skipGestureApp);
        sb.append(", skipCheckIsLogin=");
        sb.append(this.skipCheckIsLogin);
        sb.append(", skipAutoLogin=");
        sb.append(this.skipAutoLogin);
        sb.append(", showActivity=");
        sb.append(this.showActivity);
        sb.append(", isLoginSucess=");
        sb.append(this.isLoginSucess);
        sb.append(", isSettingGesture=");
        sb.append(this.isSettingGesture);
        sb.append(", accountType=");
        sb.append(this.accountType);
        sb.append(", resetCookie=");
        sb.append(this.resetCookie);
        sb.append("]");
        return sb.toString();
    }
}
