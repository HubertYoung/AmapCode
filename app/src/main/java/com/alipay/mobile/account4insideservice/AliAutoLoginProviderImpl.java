package com.alipay.mobile.account4insideservice;

import com.alipay.android.phone.inside.framework.plugin.PluginManager;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.mobile.account4insideservice.common.Account4InsideTraceLogger;
import com.alipay.mobile.securitycommon.aliauth.IAliAuthProvider;

public class AliAutoLoginProviderImpl implements IAliAuthProvider {
    private static AliAutoLoginProviderImpl a;

    private AliAutoLoginProviderImpl() {
    }

    public static AliAutoLoginProviderImpl a() {
        if (a == null) {
            synchronized (AliAutoLoginProviderImpl.class) {
                if (a == null) {
                    a = new AliAutoLoginProviderImpl();
                }
            }
        }
        return a;
    }

    public String getLoginId() {
        Account4InsideTraceLogger.a("AliAutoLoginProviderImpl", "return null，only aliautologin rpc use，server can get it by session");
        return null;
    }

    public String getUserId() {
        try {
            IInsideService b = PluginManager.b("GET_USER_ID_SERVICE");
            if (b != null) {
                return (String) b.startForResult(null);
            }
            return null;
        } catch (Exception e) {
            LoggerFactory.f().c((String) "aliautologin", (Throwable) e);
            return null;
        }
    }
}
