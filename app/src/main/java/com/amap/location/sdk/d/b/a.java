package com.amap.location.sdk.d.b;

import com.amap.location.common.f.i;

/* compiled from: AosBridge */
public class a {
    public static String a() {
        Object obj;
        try {
            obj = i.a("com.amap.bundle.network.request.param.NetworkParam", "getSession");
        } catch (Exception e) {
            com.amap.location.common.d.a.a((Throwable) e);
            obj = null;
        }
        if (obj == null) {
            return null;
        }
        return String.valueOf(obj);
    }

    public static String b() {
        Object obj;
        try {
            obj = i.a("com.amap.bundle.network.request.param.NetworkParam", "getSpm");
        } catch (Exception e) {
            com.amap.location.common.d.a.a((Throwable) e);
            obj = null;
        }
        if (obj == null) {
            return null;
        }
        return String.valueOf(obj);
    }
}
