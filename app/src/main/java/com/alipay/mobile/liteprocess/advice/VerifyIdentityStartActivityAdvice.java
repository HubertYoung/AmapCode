package com.alipay.mobile.liteprocess.advice;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.Config;
import com.alipay.mobile.liteprocess.Util;
import com.alipay.mobile.quinox.utils.LiteProcessInfo;
import com.alipay.mobile.quinox.utils.ReflectUtil;
import java.lang.reflect.Proxy;

public class VerifyIdentityStartActivityAdvice extends StartActivityWithoutMicroAppAdvice {
    private static VerifyIdentityStartActivityAdvice a;

    public static void register() {
        if (a == null) {
            synchronized (VerifyIdentityStartActivityAdvice.class) {
                if (a == null) {
                    try {
                        a = new VerifyIdentityStartActivityAdvice();
                        Object injector = Proxy.newProxyInstance(Util.getContext().getClassLoader(), new Class[]{Class.forName("com.alipay.mobile.verifyidentity.injector.StartActivityInjector")}, a);
                        ReflectUtil.getMethod(Class.forName("com.alipay.mobile.verifyidentity.injector.StartActivityInjectorUtils"), "setStartActivityInjector", new Class[]{Class.forName("com.alipay.mobile.verifyidentity.injector.StartActivityInjector")}).invoke(null, new Object[]{injector});
                        LoggerFactory.getTraceLogger().debug(LiteProcessInfo.TAG, "register VerifyIdentityStartActivityAdvice");
                    } catch (Throwable t) {
                        LoggerFactory.getTraceLogger().error((String) LiteProcessInfo.TAG, t);
                    }
                }
            }
            return;
        }
        return;
    }

    /* access modifiers changed from: protected */
    public final boolean a() {
        return Config.needHookVerifyIdentity();
    }
}
