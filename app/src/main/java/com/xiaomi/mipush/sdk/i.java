package com.xiaomi.mipush.sdk;

import com.xiaomi.xmpush.thrift.g;
import java.util.HashMap;

public class i {
    private static HashMap<d, a> a = new HashMap<>();

    static class a {
        public String a;
        public String b;

        public a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }
    }

    static {
        a(d.ASSEMBLE_PUSH_HUAWEI, new a("com.xiaomi.assemble.control.HmsPushManager", "newInstance"));
        a(d.ASSEMBLE_PUSH_FCM, new a("com.xiaomi.assemble.control.FCMPushManager", "newInstance"));
        a(d.ASSEMBLE_PUSH_COS, new a("com.xiaomi.assemble.control.COSPushManager", "newInstance"));
    }

    public static a a(d dVar) {
        return a.get(dVar);
    }

    private static void a(d dVar, a aVar) {
        if (aVar != null) {
            a.put(dVar, aVar);
        }
    }

    public static g b(d dVar) {
        return g.AggregatePushSwitch;
    }

    public static ao c(d dVar) {
        switch (j.a[dVar.ordinal()]) {
            case 1:
                return ao.UPLOAD_HUAWEI_TOKEN;
            case 2:
                return ao.UPLOAD_FCM_TOKEN;
            case 3:
                return ao.UPLOAD_COS_TOKEN;
            default:
                return null;
        }
    }
}
