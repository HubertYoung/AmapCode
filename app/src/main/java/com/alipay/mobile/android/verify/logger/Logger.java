package com.alipay.mobile.android.verify.logger;

public final class Logger {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private static Printer printer = new a();

    private Logger() {
    }

    public static void printer(Printer printer2) {
        printer = printer2;
    }

    public static void addLogAdapter(LogAdapter logAdapter) {
        printer.addAdapter(logAdapter);
    }

    public static void clearLogAdapters() {
        printer.clearLogAdapters();
    }

    public static Printer t(String str) {
        return printer.t(str);
    }

    public static void log(int i, String str, String str2, Throwable th) {
        printer.log(i, str, str2, th);
    }

    public static void d(String str, Object... objArr) {
        printer.d(str, objArr);
    }

    public static void d(Object obj) {
        printer.d(obj);
    }

    public static void e(String str, Object... objArr) {
        printer.e(null, str, objArr);
    }

    public static void e(Throwable th, String str, Object... objArr) {
        printer.e(th, str, objArr);
    }

    public static void i(String str, Object... objArr) {
        printer.i(str, objArr);
    }

    public static void v(String str, Object... objArr) {
        printer.v(str, objArr);
    }

    public static void w(String str, Object... objArr) {
        printer.w(str, objArr);
    }

    public static void wtf(String str, Object... objArr) {
        printer.wtf(str, objArr);
    }

    public static void json(String str) {
        printer.json(str);
    }

    public static void xml(String str) {
        printer.xml(str);
    }
}
