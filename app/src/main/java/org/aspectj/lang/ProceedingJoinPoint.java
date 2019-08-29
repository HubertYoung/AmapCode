package org.aspectj.lang;

import org.aspectj.runtime.internal.AroundClosure;

public interface ProceedingJoinPoint extends JoinPoint {
    Object proceed();

    Object proceed(Object[] objArr);

    void set$AroundClosure(AroundClosure aroundClosure);
}
