package com.ali.auth.third.core.rpc.protocol;

public class b {
    private static ThreadLocal<String> a = new ThreadLocal<>();

    public static String a() {
        return a.get();
    }

    public static void a(String str) {
        a.set(str);
    }
}
