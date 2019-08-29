package com.alipay.deviceid.module.x;

import com.alipay.deviceid.module.rpc.json.a;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class ab implements aj, ak {
    public final Object a(Object obj) {
        ArrayList arrayList = new ArrayList();
        for (Object b : (Object[]) obj) {
            arrayList.add(ag.b(b));
        }
        return arrayList;
    }

    public final Object a(Object obj, Type type) {
        if (!obj.getClass().equals(a.class)) {
            return null;
        }
        a aVar = (a) obj;
        if (type instanceof GenericArrayType) {
            throw new IllegalArgumentException("Does not support generic array!");
        }
        Class<?> componentType = ((Class) type).getComponentType();
        int a = aVar.a();
        Object newInstance = Array.newInstance(componentType, a);
        for (int i = 0; i < a; i++) {
            Array.set(newInstance, i, af.a(aVar.a(i), (Type) componentType));
        }
        return newInstance;
    }

    public final boolean a(Class<?> cls) {
        return cls.isArray();
    }
}
