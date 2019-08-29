package com.alipay.mobile.aspect;

import org.aspectj.lang.annotation.Aspect;

@Aspect
public class CommonAspect {
    private static CommonAspect a = new CommonAspect();

    public static CommonAspect aspectOf() {
        return a;
    }

    public static boolean hasAspect() {
        return false;
    }
}
