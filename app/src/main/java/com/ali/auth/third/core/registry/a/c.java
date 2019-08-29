package com.ali.auth.third.core.registry.a;

import com.ali.auth.third.core.message.Message;
import com.ali.auth.third.core.trace.SDKLogger;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

class c implements InvocationHandler {
    final /* synthetic */ Class a;
    final /* synthetic */ Map b;
    final /* synthetic */ b c;

    c(b bVar, Class cls, Map map) {
        this.c = bVar;
        this.a = cls;
        this.b = map;
    }

    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        Object a2 = this.c.a.a(this.a, this.b);
        if (a2 != null) {
            return method.invoke(a2, objArr);
        }
        Object[] objArr2 = new Object[2];
        objArr2[0] = this.a.getName();
        objArr2[1] = this.b != null ? this.b.toString() : "";
        SDKLogger.e("kernel", Message.create(17, objArr2).toString());
        return null;
    }
}
