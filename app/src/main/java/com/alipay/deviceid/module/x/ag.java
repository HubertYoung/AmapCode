package com.alipay.deviceid.module.x;

import com.alipay.deviceid.module.rpc.json.a;
import com.alipay.deviceid.module.rpc.json.b;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class ag {
    private static List<ak> a;

    static {
        ArrayList arrayList = new ArrayList();
        a = arrayList;
        arrayList.add(new am());
        a.add(new ae());
        a.add(new ad());
        a.add(new ai());
        a.add(new ac());
        a.add(new ab());
        a.add(new ah());
    }

    public static String a(Object obj) {
        if (obj == null) {
            return null;
        }
        Object b = b(obj);
        if (an.a(b.getClass())) {
            return b.c(b.toString());
        }
        if (Collection.class.isAssignableFrom(b.getClass())) {
            return new a((Collection) (List) b).toString();
        }
        if (Map.class.isAssignableFrom(b.getClass())) {
            return new b((Map) b).toString();
        }
        StringBuilder sb = new StringBuilder("Unsupported Class : ");
        sb.append(b.getClass());
        throw new IllegalArgumentException(sb.toString());
    }

    public static Object b(Object obj) {
        if (obj == null) {
            return null;
        }
        for (ak next : a) {
            if (next.a(obj.getClass())) {
                Object a2 = next.a(obj);
                if (a2 != null) {
                    return a2;
                }
            }
        }
        StringBuilder sb = new StringBuilder("Unsupported Class : ");
        sb.append(obj.getClass());
        throw new IllegalArgumentException(sb.toString());
    }
}
