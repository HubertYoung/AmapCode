package com.alipay.mobile.accountopenauth.biz.insideplugin;

import com.alipay.android.phone.inside.framework.plugin.IInsidePlugin;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.mobile.accountopenauth.biz.insideplugin.service.AccountOAuthService;
import com.alipay.mobile.accountopenauth.biz.insideplugin.service.FastOAuthService;
import com.alipay.mobile.accountopenauth.biz.insideplugin.service.MCAccountChangeService;
import com.alipay.mobile.accountopenauth.biz.insideplugin.service.OAuthActiveLoginService;
import com.alipay.mobile.accountopenauth.biz.insideplugin.service.ObtainMCOAuthInfoService;
import com.alipay.mobile.accountopenauth.common.OAuthTraceLogger;
import java.util.HashMap;
import java.util.Map;

public class AccountOAuthPlugin implements IInsidePlugin {
    private static final String ACCOUNT_OPEN_AUTH_SERVICE = "ACCOUNT_OPEN_AUTH_SERVICE";
    private static final String FAST_OPEN_AUTH_SERVICE = "FAST_OPEN_AUTH_SERVICE";
    private static final String MC_ACCOUNT_CHANGE_SERVICE = "MC_ACCOUNT_CHANGE_SERVICE";
    private static final String OBTAIN_MC_AUTHINFO_SERVICE = "OBTAIN_MC_AUTHINFO_SERVICE";
    private static final String OPEN_AUTH_LOGIN_SERVICE = "OPEN_AUTH_LOGIN_SERVICE";
    private static final String TAG = "AOpenAuthPlugin";
    private Map<String, IInsideService> mService = new HashMap();

    public IInsideService getService(String str) {
        return null;
    }

    public void onRegisted(Object obj) {
    }

    public void onUnRegisted(Object obj) {
    }

    public AccountOAuthPlugin() {
        this.mService.put(ACCOUNT_OPEN_AUTH_SERVICE, new AccountOAuthService());
        this.mService.put(FAST_OPEN_AUTH_SERVICE, new FastOAuthService());
        this.mService.put(OBTAIN_MC_AUTHINFO_SERVICE, new ObtainMCOAuthInfoService());
        this.mService.put(MC_ACCOUNT_CHANGE_SERVICE, new MCAccountChangeService());
        this.mService.put(OPEN_AUTH_LOGIN_SERVICE, new OAuthActiveLoginService());
        OAuthTraceLogger.a((String) TAG, (String) "openauth plugin constructor");
    }

    public Map<String, IInsideService> getServiceMap() {
        return this.mService;
    }
}
