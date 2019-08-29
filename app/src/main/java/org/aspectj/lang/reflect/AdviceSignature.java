package org.aspectj.lang.reflect;

import java.lang.reflect.Method;

public interface AdviceSignature extends CodeSignature {
    Method getAdvice();

    Class getReturnType();
}
