package com.alipay.mobile.common.nbnet.biz.log;

import com.alipay.mobile.common.transport.utils.NetBeanFactory;

public class FrameworkMonitorFactory {
    static Class<?> a;
    static FrameworkMonitor b;

    public static final FrameworkMonitor a() {
        try {
            if (a == null) {
                a = Class.forName("com.alipay.mobile.common.nbnet.integration.DefaultFrameworkMonitor");
            }
            return (FrameworkMonitor) NetBeanFactory.getBean(a);
        } catch (Throwable e) {
            NBNetLogCat.b("TraficMonitorFactory", "getDefault fail", e);
            return b();
        }
    }

    private static final FrameworkMonitor b() {
        if (b != null) {
            return b;
        }
        FrameworkMonitorAdapter frameworkMonitorAdapter = new FrameworkMonitorAdapter();
        b = frameworkMonitorAdapter;
        return frameworkMonitorAdapter;
    }
}
