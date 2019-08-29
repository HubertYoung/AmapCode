package com.alipay.mobile.accountopenauth.common;

import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class OAuthTraceLogger {
    public static void a(String str, Throwable th) {
        LoggerFactory.f().a("OAuth_".concat(String.valueOf(str)), th);
    }

    public static void a(String str, String str2) {
        LoggerFactory.f().a("OAuth_".concat(String.valueOf(str)), str2);
    }

    public static void b(String str, String str2) {
        LoggerFactory.f().c("OAuth_".concat(String.valueOf(str)), str2);
    }

    public static void b(String str, Throwable th) {
        LoggerFactory.f().a("OAuth_".concat(String.valueOf(str)), th);
    }

    public static void a(String str, String str2, Throwable th) {
        LoggerFactory.f().a("OAuth_".concat(String.valueOf(str)), str2, th);
    }
}
