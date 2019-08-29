package org.aspectj.lang.reflect;

import java.lang.reflect.Type;

public interface InterTypeFieldDeclaration extends InterTypeDeclaration {
    Type getGenericType();

    String getName();

    AjType<?> getType();
}
