package org.xidea.el.impl;

import java.lang.reflect.Method;
import org.xidea.el.Invocable;
import org.xidea.el.fn.ECMA262Impl;

/* compiled from: ExpressionFactoryImpl */
class MethodInvocable implements Invocable {
    Method[] a;

    MethodInvocable() {
    }

    public final Object a(Object obj, Object... objArr) throws Exception {
        Method[] methodArr = this.a;
        int length = methodArr.length;
        int i = 0;
        while (i < length) {
            Method method = methodArr[i];
            Class[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == objArr.length) {
                int i2 = 0;
                while (i2 < parameterTypes.length) {
                    Class<? extends Object> e = ReflectUtil.e(parameterTypes[i2]);
                    Object b = ECMA262Impl.b(objArr[i2], e);
                    objArr[i2] = b;
                    if (b == null || e.isInstance(b)) {
                        i2++;
                    } else {
                        i++;
                    }
                }
            }
            return method.invoke(obj, objArr);
        }
        return null;
    }
}
