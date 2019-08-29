package com.ali.user.mobile.log;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import java.io.PrintWriter;
import java.io.StringWriter;

public class AliUserLogUtil {
    public static void a(String str, String str2) {
        LoggerFactory.f().b("LoginInside_".concat(String.valueOf(str)), str2);
    }

    public static void b(String str, String str2) {
        LoggerFactory.f().c("LoginInside_".concat(String.valueOf(str)), str2);
    }

    public static void a(String str, String str2, Throwable th) {
        TraceLogger f = LoggerFactory.f();
        String concat = "LoginInside_".concat(String.valueOf(str));
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("::");
        sb.append(a(th));
        f.c(concat, sb.toString());
    }

    public static void c(String str, String str2) {
        LoggerFactory.f().a("LoginInside_".concat(String.valueOf(str)), str2);
    }

    public static void d(String str, String str2) {
        LoggerFactory.f().c("LoginInside_".concat(String.valueOf(str)), str2);
    }

    public static void a(String str, Throwable th) {
        LoggerFactory.f().a("LoginInside_".concat(String.valueOf(str)), th);
    }

    private static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        try {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
            }
            printWriter.close();
            return stringWriter.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static void a(String str) {
        Behavior behavior = new Behavior();
        behavior.a = "UC-ZHAQ-56";
        behavior.c = "loginTrace-stackTrace";
        behavior.g = str;
        behavior.b = BehaviorType.EVENT;
        LoggerFactory.d().a(behavior);
    }
}
