package org.aspectj.lang.reflect;

public interface DeclareSoft {
    AjType getDeclaringType();

    PointcutExpression getPointcutExpression();

    AjType getSoftenedExceptionType();
}
