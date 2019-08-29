package com.alipay.mobile.aspect.processor;

import org.aspectj.lang.JoinPoint;

public abstract class AbsNormalAspectJProcessor implements IAspectJProcessor {
    public abstract void afterMethod(JoinPoint joinPoint, Object obj, Object obj2);

    public int getFlags() {
        return 0;
    }

    public Object whenThrown(JoinPoint joinPoint, Throwable tr, Object ext) {
        return null;
    }

    public Object whenIntercepted(JoinPoint joinPoint, Object ext) {
        return null;
    }

    public Object afterMethodWithReturn(JoinPoint joinPoint, Object result, Object ext) {
        afterMethod(joinPoint, result, ext);
        return null;
    }
}
