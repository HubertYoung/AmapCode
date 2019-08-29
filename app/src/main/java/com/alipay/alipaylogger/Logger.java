package com.alipay.alipaylogger;

public interface Logger {
    int d(String str, String str2);

    int e(String str, String str2);

    int e(String str, String str2, Throwable th);

    int i(String str, String str2);

    int v(String str, String str2);

    int w(String str, String str2);
}
