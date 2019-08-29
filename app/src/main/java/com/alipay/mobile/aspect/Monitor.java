package com.alipay.mobile.aspect;

import org.aspectj.lang.annotation.Aspect;

@Aspect
public class Monitor {
    private static Monitor a = new Monitor();

    public static Monitor aspectOf() {
        return a;
    }

    public static boolean hasAspect() {
        return false;
    }
}
