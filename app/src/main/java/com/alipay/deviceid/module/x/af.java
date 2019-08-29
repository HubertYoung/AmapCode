package com.alipay.deviceid.module.x;

import com.alipay.deviceid.module.rpc.json.a;
import com.alipay.deviceid.module.rpc.json.b;
import com.alipay.sdk.util.h;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class af {
    static List<aj> a;

    static {
        ArrayList arrayList = new ArrayList();
        a = arrayList;
        arrayList.add(new am());
        a.add(new ae());
        a.add(new ad());
        a.add(new ai());
        a.add(new al());
        a.add(new ac());
        a.add(new ab());
        a.add(new ah());
    }

    public static final <T> T a(Object obj, Type type) {
        for (aj next : a) {
            if (next.a(an.a(type))) {
                T a2 = next.a(obj, type);
                if (a2 != null) {
                    return a2;
                }
            }
        }
        return null;
    }

    public static final Object a(String str, Type type) {
        Object bVar;
        if (str == null || str.length() == 0) {
            return null;
        }
        String trim = str.trim();
        if (trim.startsWith("[") && trim.endsWith("]")) {
            bVar = new a(trim);
        } else if (!trim.startsWith("{") || !trim.endsWith(h.d)) {
            return a((Object) trim, type);
        } else {
            bVar = new b(trim);
        }
        return a(bVar, type);
    }
}
