package com.alipay.android.phone.inside.log.api.trace;

public interface TraceLogger {
    void a(String str, String str2);

    void a(String str, String str2, Throwable th);

    void a(String str, Throwable th);

    void b(String str, String str2);

    void b(String str, String str2, Throwable th);

    void b(String str, Throwable th);

    void c(String str, String str2);

    void c(String str, Throwable th);

    void d(String str, String str2);

    void e(String str, String str2);
}
