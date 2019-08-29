package com.alipay.mobile.aspect.processor;

import java.util.List;
import org.aspectj.lang.JoinPoint;

public interface IAspectJProcessor {
    public static final int FLAG_CARE_RETURN = 2;
    public static final int FLAG_CARE_THROWABLE = 1;
    public static final int FLAG_INTERCEPT = 4;

    Object afterMethodWithReturn(JoinPoint joinPoint, Object obj, Object obj2);

    void beforeMethod(JoinPoint joinPoint, Object obj);

    int getFlags();

    String getMethodName();

    List<Class[]> getParameters();

    Class getTargetClass();

    Object whenIntercepted(JoinPoint joinPoint, Object obj);

    Object whenThrown(JoinPoint joinPoint, Throwable th, Object obj);
}
