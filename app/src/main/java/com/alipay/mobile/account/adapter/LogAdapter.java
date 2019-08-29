package com.alipay.mobile.account.adapter;

import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class LogAdapter {
    public static void a(String str, String str2) {
        LoggerFactory.f().b(str, str2);
    }

    public static void a(String str, String str2, Throwable th) {
        LoggerFactory.f().b(str, str2, th);
    }

    public static void a(String str, Throwable th) {
        LoggerFactory.f().a(str, th);
    }
}
