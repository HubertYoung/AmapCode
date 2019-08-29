package org.xidea.el.impl;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.xidea.el.ExpressionFactory;
import org.xidea.el.Invocable;
import org.xidea.el.OperationStrategy;
import org.xidea.el.fn.ECMA262Impl;

public class ExpressionFactoryImpl implements ExpressionFactory {
    private static ValueStackImpl c = new ValueStackImpl(Collections.emptyMap());
    private static Map<String, Invocable> f = new HashMap();
    protected final OperationStrategy a = new OperationStrategyImpl();
    protected Map<String, Integer> b = new HashMap();
    private int d = 1;
    private boolean e = true;

    public ExpressionFactoryImpl() {
        ECMA262Impl.a(this);
    }

    private OperationStrategyImpl a() {
        if (this.a instanceof OperationStrategyImpl) {
            return (OperationStrategyImpl) this.a;
        }
        throw new UnsupportedOperationException();
    }

    public final void a(String str, Object obj) {
        a().b.put(str, obj);
    }

    public final void a(Class<? extends Object> cls, String str, Invocable invocable) {
        OperationStrategyImpl a2 = a();
        if (a2.a.get(cls) == null) {
            a2.a.put(cls, new HashMap());
        }
        for (Entry next : a2.a.entrySet()) {
            if (((Class) next.getKey()).isAssignableFrom(cls)) {
                ((Map) next.getValue()).put(str, invocable);
            }
        }
    }

    private static Invocable a(Method... methodArr) {
        for (Method accessible : methodArr) {
            try {
                accessible.setAccessible(true);
            } catch (Exception unused) {
            }
        }
        MethodInvocable methodInvocable = new MethodInvocable();
        methodInvocable.a = methodArr;
        return methodInvocable;
    }

    static Invocable a(Class<? extends Object> cls, String str) {
        Method[] methods;
        StringBuilder sb = new StringBuilder();
        sb.append(cls.getName());
        sb.append(DjangoUtils.EXTENSION_SEPARATOR);
        sb.append(-1);
        sb.append(str);
        String sb2 = sb.toString();
        Invocable invocable = f.get(sb2);
        if (invocable != null || f.containsKey(sb2)) {
            return invocable;
        }
        ArrayList arrayList = new ArrayList();
        for (Method method : cls.getMethods()) {
            if (method.getName().equals(str)) {
                arrayList.add(method);
            }
        }
        if (arrayList.size() <= 0) {
            return invocable;
        }
        Invocable a2 = a((Method[]) arrayList.toArray(new Method[arrayList.size()]));
        f.put(sb2, a2);
        return a2;
    }
}
