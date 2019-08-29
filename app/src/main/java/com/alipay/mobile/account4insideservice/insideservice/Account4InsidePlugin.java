package com.alipay.mobile.account4insideservice.insideservice;

import com.alipay.android.phone.inside.framework.plugin.IInsidePlugin;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class Account4InsidePlugin implements IInsidePlugin {
    private static final String ALI_AUTO_LOGIN_CHECK_CONDITION_SERVICE = "ALI_AUTO_LOGIN_CHECK_CONDITION_SERVICE";
    private static final String ALI_AUTO_LOGIN_CLEAR_TBSESSION_SERVICE = "ALI_AUTO_LOGIN_CLEAR_TBSESSION_SERVICE";
    private static final String ALI_AUTO_LOGIN_SERVICE = "ALI_AUTO_LOGIN_SERVICE";
    private static final String TAG = "Account4InsidePlugin";
    private Map<String, IInsideService> mServiceMap = new HashMap();

    public void onRegisted(Object obj) {
    }

    public void onUnRegisted(Object obj) {
    }

    public Account4InsidePlugin() {
        this.mServiceMap.put(ALI_AUTO_LOGIN_SERVICE, new AliAutoLoginService());
        this.mServiceMap.put(ALI_AUTO_LOGIN_CHECK_CONDITION_SERVICE, new AliAutoLoginCheckConditionService());
        this.mServiceMap.put(ALI_AUTO_LOGIN_CLEAR_TBSESSION_SERVICE, new AliAutoLoginClearTBSessionService());
        LoggerFactory.f().a((String) TAG, (String) "Account4InsidePlugin constructor");
    }

    public Map<String, IInsideService> getServiceMap() {
        return this.mServiceMap;
    }

    public IInsideService getService(String str) {
        return this.mServiceMap.get(str);
    }
}
