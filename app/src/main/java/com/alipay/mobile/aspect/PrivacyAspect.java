package com.alipay.mobile.aspect;

import org.aspectj.lang.annotation.Aspect;

@Aspect
public class PrivacyAspect {
    private static PrivacyAspect a = new PrivacyAspect();

    public static PrivacyAspect aspectOf() {
        return a;
    }

    public static boolean hasAspect() {
        return false;
    }
}
