package com.alipay.mobile.liteprocess.advice;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.Config;
import com.alipay.mobile.liteprocess.Util;
import com.alipay.mobile.quinox.utils.LiteProcessInfo;
import com.alipay.mobile.quinox.utils.ReflectUtil;
import java.lang.reflect.Proxy;

public class PhoneCashierStartActivityAdvice extends StartActivityWithoutMicroAppAdvice {
    private static PhoneCashierStartActivityAdvice a;

    public static void register() {
        if (a == null) {
            synchronized (PhoneCashierStartActivityAdvice.class) {
                if (a == null) {
                    try {
                        a = new PhoneCashierStartActivityAdvice();
                        Object injector = Proxy.newProxyInstance(Util.getContext().getClassLoader(), new Class[]{Class.forName("com.alipay.android.app.helper.MspStartActivityInjector")}, a);
                        ReflectUtil.getMethod(Class.forName("com.alipay.android.app.helper.MspStartActivityInjectorUtils"), "setStartActivityInjector", new Class[]{Class.forName("com.alipay.android.app.helper.MspStartActivityInjector")}).invoke(null, new Object[]{injector});
                        LoggerFactory.getTraceLogger().debug(LiteProcessInfo.TAG, "register PhoneCashierStartActivityAdvice");
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
        return Config.needHookPhoneCashier();
    }
}
