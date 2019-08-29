package com.alipay.android.phone.inside.framework.plugin;

import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;

public class PluginManager {
    static PluginManagerProxy a = new PluginManagerProxy();
    static boolean b = false;

    public static synchronized void a() {
        synchronized (PluginManager.class) {
            if (!b) {
                a.a();
                b = true;
            }
        }
    }

    public static IInsidePlugin a(String str) {
        a();
        IInsidePlugin a2 = a.a(str);
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("PluginManager::getInsidePlugin > pluginName:");
        sb.append(str);
        sb.append(", plugin:");
        sb.append(a2);
        f.b((String) "inside", sb.toString());
        return a2;
    }

    public static IInsideService b(String str) {
        a();
        IInsideService b2 = a.b(str);
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("PluginManager::getInsideService > serviceName:");
        sb.append(str);
        sb.append(", service:");
        sb.append(b2);
        f.b((String) "inside", sb.toString());
        return b2;
    }
}
