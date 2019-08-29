package com.ali.auth.third.core.registry.a;

import com.ali.auth.third.core.model.Constants;
import com.ali.auth.third.core.registry.ServiceRegistration;
import com.ali.auth.third.core.registry.a;
import java.lang.reflect.Proxy;
import java.util.Map;

public class b implements a {
    /* access modifiers changed from: private */
    public a a;

    public b(a aVar) {
        this.a = aVar;
    }

    public ServiceRegistration a(Class<?>[] clsArr, Object obj, Map<String, String> map) {
        return this.a.a(clsArr, obj, map);
    }

    public Object a(ServiceRegistration serviceRegistration) {
        return this.a.a(serviceRegistration);
    }

    public <T> T a(Class<T> cls, Map<String, String> map) {
        T a2 = this.a.a(cls, map);
        if (a2 != null || map == null || !"true".equals(map.get(Constants.ISV_SCOPE_FLAG)) || !cls.isInterface()) {
            return a2;
        }
        return cls.cast(Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{cls}, new c(this, cls, map)));
    }

    public <T> T[] b(Class<T> cls, Map<String, String> map) {
        return this.a.b(cls, map);
    }
}
