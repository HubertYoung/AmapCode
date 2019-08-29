package com.alipay.android.phone.inside.commonbiz.login.expire;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class LoginExpireProvider {
    static final Map<String, LoginExpireProvider> a = new HashMap();

    public static void a(String str) {
        LoginExpireProvider loginExpireProvider = a.get(str);
        if (loginExpireProvider != null) {
            synchronized (loginExpireProvider) {
                loginExpireProvider.notifyAll();
            }
            return;
        }
        LoggerFactory.e().a((String) "commonbiz", (String) "NotifyFinishUniformityUnMatch", "uuid=".concat(String.valueOf(str)));
    }
}
