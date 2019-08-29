package org.aspectj.lang.reflect;

import java.lang.reflect.Type;

public interface DeclareParents {
    AjType getDeclaringType();

    Type[] getParentTypes();

    TypePattern getTargetTypesPattern();

    boolean isExtends();

    boolean isImplements();
}
