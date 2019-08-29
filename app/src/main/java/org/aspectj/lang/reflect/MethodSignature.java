package org.aspectj.lang.reflect;

import java.lang.reflect.Method;

public interface MethodSignature extends CodeSignature {
    Method getMethod();

    Class getReturnType();
}
