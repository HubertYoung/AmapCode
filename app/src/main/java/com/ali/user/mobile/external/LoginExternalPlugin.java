package com.ali.user.mobile.external;

import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.external.InSideService.AccountChangeExternalService;
import com.ali.user.mobile.external.InSideService.AccountManagerService;
import com.ali.user.mobile.external.InSideService.AccountSSOInfoService;
import com.ali.user.mobile.external.InSideService.AlipayLoginStateService;
import com.ali.user.mobile.external.InSideService.AutoAuthExternalService;
import com.ali.user.mobile.external.InSideService.CheckLoginConsistencyService;
import com.ali.user.mobile.external.InSideService.GetUserIdService;
import com.ali.user.mobile.external.InSideService.LoginExternalService;
import com.ali.user.mobile.external.InSideService.LoginProcessAlipayParamsService;
import com.ali.user.mobile.external.InSideService.LoginUserInfoService;
import com.ali.user.mobile.external.InSideService.LogoutExternalService;
import com.ali.user.mobile.external.InSideService.OAuthAccountConsistencyService;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.plugin.IInsidePlugin;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.edge.face.EdgeRiskAnalyzer;
import java.util.HashMap;
import java.util.Map;

public class LoginExternalPlugin implements IInsidePlugin {
    private static final String ACCOUNT_MANAGER_SERVICE = "ACCOUNT_MANAGER_SERVICE";
    private static final String ACCOUNT_SSO_INFO_SERVICE = "ACCOUNT_SSO_INFO_SERVICE";
    private static final String ALIPAY_LOGIN_STATE_SERVICE = "ALIPAY_LOGIN_STATE_SERVICE";
    private static final String AUTO_AUTH_SERVICE_NAME = "AUTO_AUTH_EXTERNAL_SERVICE";
    private static final String CHANGE_ACCOUNT_SERVICE_NAME = "CHANGE_ACCOUNT_EXTERNAL_SERVICE";
    private static final String CHECK_LOGIN_CONSISTENCY_SERVICE = "CHECK_LOGIN_CONSISTENCY_SERVICE";
    private static final String GET_USER_ID_SERVICE_NAME = "GET_USER_ID_SERVICE";
    private static final String LOGIN_PROCESS_ALIPAY_PARAMS_SERVICE = "LOGIN_PROCESS_ALIPAY_PARAMS_SERVICE";
    private static final String LOGIN_SERVICE_NAME = "LOGIN_EXTERNAL_SERVICE";
    private static final String LOGIN_USERINFO_SERVICE = "LOGIN_USERINFO_SERVICE";
    private static final String LOGOUT_SERVICE_NAME = "LOGOUT_EXTERNAL_SERVICE";
    private static final String OAUTH_ACCOUNT_CONSISTENCY_SERVICE = "OAUTH_ACCOUNT_CONSISTENCY_SERVICE";
    private static final String TAG = "LoginExternalPlugin";
    private Map<String, IInsideService> mService = new HashMap();

    public IInsideService getService(String str) {
        return null;
    }

    public void onRegisted(Object obj) {
    }

    public void onUnRegisted(Object obj) {
    }

    public LoginExternalPlugin() {
        this.mService.put(LOGIN_SERVICE_NAME, new LoginExternalService());
        this.mService.put(AUTO_AUTH_SERVICE_NAME, new AutoAuthExternalService());
        this.mService.put(LOGOUT_SERVICE_NAME, new LogoutExternalService());
        this.mService.put(CHANGE_ACCOUNT_SERVICE_NAME, new AccountChangeExternalService());
        this.mService.put(GET_USER_ID_SERVICE_NAME, new GetUserIdService());
        this.mService.put(ALIPAY_LOGIN_STATE_SERVICE, new AlipayLoginStateService());
        this.mService.put(CHECK_LOGIN_CONSISTENCY_SERVICE, new CheckLoginConsistencyService());
        this.mService.put(ACCOUNT_MANAGER_SERVICE, new AccountManagerService());
        this.mService.put(LOGIN_USERINFO_SERVICE, new LoginUserInfoService());
        this.mService.put(LOGIN_PROCESS_ALIPAY_PARAMS_SERVICE, new LoginProcessAlipayParamsService());
        this.mService.put(ACCOUNT_SSO_INFO_SERVICE, new AccountSSOInfoService());
        this.mService.put(OAUTH_ACCOUNT_CONSISTENCY_SERVICE, new OAuthAccountConsistencyService());
        AliUserLog.c(TAG, "login plugin constructor");
        initEdge();
    }

    private void initEdge() {
        SecurityUtil.a((Runnable) new Runnable() {
            public void run() {
                try {
                    EdgeRiskAnalyzer.getInstance(LauncherApplication.a()).initialize();
                } catch (Throwable th) {
                    AliUserLog.a(LoginExternalPlugin.TAG, "init edge error", th);
                }
            }
        });
    }

    public Map<String, IInsideService> getServiceMap() {
        return this.mService;
    }
}
