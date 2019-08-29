package com.alipay.android.phone.inside.framework.plugin;

import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PluginManagerProxy {
    private Map<String, IInsidePlugin> a = new HashMap();
    private Map<String, IInsideService> b = new HashMap();
    private List<PluginDesc> c;

    PluginManagerProxy() {
    }

    public final void a() {
        this.c = new PluginProvider().a();
        for (int i = 0; i < this.c.size(); i++) {
            PluginDesc pluginDesc = this.c.get(i);
            IInsidePlugin c2 = c(pluginDesc.b);
            if (c2 != null) {
                for (String next : c2.getServiceMap().keySet()) {
                    this.b.put(next, c2.getServiceMap().get(next));
                }
                this.a.put(pluginDesc.a, c2);
            }
        }
    }

    public final IInsidePlugin a(String str) {
        if (this.a.containsKey(str)) {
            return this.a.get(str);
        }
        return null;
    }

    public final IInsideService b(String str) {
        if (this.b.containsKey(str)) {
            return this.b.get(str);
        }
        return null;
    }

    private static IInsidePlugin c(String str) {
        try {
            return (IInsidePlugin) Class.forName(str).newInstance();
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return null;
        }
    }
}
