package com.alipay.mobile.common.logging.util.crash;

public abstract class ThrowableListener {
    private static ThrowableListener a = null;

    public abstract void onThrowable(String str);

    public static void addThrowableListener(ThrowableListener listener) {
        try {
            a = listener;
        } catch (Throwable th) {
        }
    }

    public static void processThrowable(String trString) {
        try {
            if (a != null) {
                a.onThrowable(trString);
            }
        } catch (Throwable th) {
        }
    }
}
