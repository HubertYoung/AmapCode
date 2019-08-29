package com.ali.user.mobile.external.InSideService;

import android.os.Bundle;
import android.text.TextUtils;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.sso.SSOService;
import com.ali.user.mobile.login.sso.impl.SsoServiceImpl;
import com.ali.user.mobile.login.sso.info.SsoLoginInfo;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;

public class AccountSSOInfoService implements IInsideService<Boolean, Bundle> {
    public static final String SSO_ALIPAY_UID = "ssoAlipayUid";
    public static final String SSO_AVATAR = "avatar";
    public static final String SSO_LOGIN_ID = "loginId";
    public static final String SSO_TOKEN = "token";
    private static final String TAG = "AccountSSOInfoService";

    public void start(IInsideServiceCallback<Bundle> iInsideServiceCallback, Boolean bool) throws Exception {
    }

    public void start(Boolean bool) throws Exception {
    }

    public Bundle startForResult(Boolean bool) throws Exception {
        SsoLoginInfo ssoLoginInfo;
        SsoServiceImpl a = SsoServiceImpl.a(LauncherApplication.a().getApplicationContext());
        if (bool.booleanValue()) {
            ssoLoginInfo = getVerifiedSSOLoginInfo(a);
        } else {
            ssoLoginInfo = getSSOLoginInfo(a);
        }
        Bundle bundle = new Bundle();
        if (ssoLoginInfo != null) {
            bundle.putString("loginId", ssoLoginInfo.loginId);
            bundle.putString("avatar", ssoLoginInfo.headImg);
            bundle.putString("token", ssoLoginInfo.loginToken);
            bundle.putString(SSO_ALIPAY_UID, ssoLoginInfo.userId);
        }
        return bundle;
    }

    private SsoLoginInfo getSSOLoginInfo(SSOService sSOService) {
        AliUserLog.c(TAG, "getSSOLoginInfo");
        SsoLoginInfo a = sSOService.a();
        if (a == null || TextUtils.isEmpty(a.loginToken) || TextUtils.isEmpty(a.loginId)) {
            LoggerUtils.a("", "ssoInfoIsNull_20181228", "login", "originST");
            return null;
        }
        SsoLoginInfo a2 = sSOService.a(copySsoInfo(a));
        if (a2 != null && a2.isDirectLogin.booleanValue() && !TextUtils.isEmpty(a2.loginToken) && !TextUtils.isEmpty(a2.loginId)) {
            return a;
        }
        LoggerUtils.a("", "ssoInfoIsInvalid_20181228", "login", "originST");
        return null;
    }

    private SsoLoginInfo copySsoInfo(SsoLoginInfo ssoLoginInfo) {
        SsoLoginInfo ssoLoginInfo2 = new SsoLoginInfo();
        if (ssoLoginInfo != null) {
            ssoLoginInfo2.headImg = ssoLoginInfo.headImg;
            ssoLoginInfo2.isDirectLogin = ssoLoginInfo.isDirectLogin;
            ssoLoginInfo2.loginId = ssoLoginInfo.loginId;
            ssoLoginInfo2.loginToken = ssoLoginInfo.loginToken;
            ssoLoginInfo2.userId = ssoLoginInfo.userId;
        }
        return ssoLoginInfo2;
    }

    private SsoLoginInfo getVerifiedSSOLoginInfo(SSOService sSOService) {
        AliUserLog.c(TAG, "getVerifiedSSOLoginInfo");
        SsoLoginInfo a = sSOService.a();
        if (a == null || TextUtils.isEmpty(a.loginToken) || TextUtils.isEmpty(a.loginId)) {
            LoggerUtils.a("", "ssoInfoIsNull_20181228", "login", "verifiedST");
            return null;
        }
        SsoLoginInfo a2 = sSOService.a(a);
        if (a2 != null && a2.isDirectLogin.booleanValue() && !TextUtils.isEmpty(a2.loginToken) && !TextUtils.isEmpty(a2.loginId)) {
            return a2;
        }
        LoggerUtils.a("", "ssoInfoIsInvalid_20181228", "login", "verifiedST");
        return null;
    }
}
