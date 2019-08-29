package com.ali.user.mobile.log;

import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class AliUserLog {
    public static void a(String str, String str2) {
        LoggerFactory.f().b("LoginInside_".concat(String.valueOf(str)), str2);
    }

    public static void b(String str, String str2) {
        LoggerFactory.f().c("LoginInside_".concat(String.valueOf(str)), str2);
    }

    public static void a(String str, Throwable th) {
        LoggerFactory.f().a("LoginInside_".concat(String.valueOf(str)), th);
    }

    public static void a(String str, String str2, Throwable th) {
        LoggerFactory.f().b("LoginInside_".concat(String.valueOf(str)), str2, th);
    }

    public static void c(String str, String str2) {
        LoggerFactory.f().a("LoginInside_".concat(String.valueOf(str)), str2);
    }

    public static void d(String str, String str2) {
        LoggerFactory.f().c("LoginInside_".concat(String.valueOf(str)), str2);
    }

    public static void b(String str, Throwable th) {
        LoggerFactory.f().a("LoginInside_".concat(String.valueOf(str)), th);
    }

    public static void b(String str, String str2, Throwable th) {
        LoggerFactory.f().a("LoginInside_".concat(String.valueOf(str)), str2, th);
    }
}
