package com.alipay.android.phone.inside.log.api.ex;

public interface ExceptionLogger {
    void a(ExceptionEnum exceptionEnum, String str, String str2, Throwable th);

    void a(ExceptionEnum exceptionEnum, String str, String str2, Throwable th, String... strArr);

    void a(String str, String str2);

    void a(String str, String str2, Throwable th);

    void a(String str, String str2, Throwable th, String... strArr);

    void a(String str, String str2, String... strArr);
}
