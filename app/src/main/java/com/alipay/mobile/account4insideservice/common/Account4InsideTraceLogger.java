package com.alipay.mobile.account4insideservice.common;

import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class Account4InsideTraceLogger {
    public static void a(String str, String str2) {
        LoggerFactory.f().a("Account4Inside_".concat(String.valueOf(str)), str2);
    }

    public static void a(String str, String str2, Throwable th) {
        LoggerFactory.f().a("Account4Inside_".concat(String.valueOf(str)), str2, th);
    }
}
