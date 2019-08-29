package com.ali.user.mobile.log;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import java.util.HashMap;
import java.util.Map;

public class LoggerUtils {
    public static void a(String str) {
        LogAgent.f("UC-ZHAQ-56", "loginTrace", str, "", "");
    }

    public static void a(String str, HashMap<String, String> hashMap) {
        LogAgent.a((String) "AS-EXCEPTION-161206-01", str, (String) "", (String) "", (String) "", (Map<String, String>) hashMap);
    }

    public static void a(String str, String str2, String str3, String str4) {
        Behavior behavior = new Behavior();
        behavior.c = str2;
        behavior.a = str3;
        behavior.g = str4;
        LoggerFactory.d().a(behavior);
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("wirteBehaviorLog behaviorId=");
        sb.append(str);
        sb.append("-seedId=");
        sb.append(str2);
        sb.append("-userCase=");
        sb.append(str3);
        sb.append("-param1 = ");
        sb.append(str4);
        f.b((String) "LoggerUtils", sb.toString());
    }
}
