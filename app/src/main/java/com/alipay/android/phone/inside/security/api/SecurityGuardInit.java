package com.alipay.android.phone.inside.security.api;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;

public class SecurityGuardInit {
    static String a;

    public static void a(String str) {
        a = str;
    }

    public static String a() {
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("SecurityGuardInit::getAuthCode > ");
        sb.append(a);
        f.e("inside", sb.toString());
        if (a == null) {
            return "";
        }
        return a;
    }
}
