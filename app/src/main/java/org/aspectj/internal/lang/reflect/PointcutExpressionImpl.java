package org.aspectj.internal.lang.reflect;

import org.aspectj.lang.reflect.PointcutExpression;

public class PointcutExpressionImpl implements PointcutExpression {
    private String a;

    public PointcutExpressionImpl(String aPointcutExpression) {
        this.a = aPointcutExpression;
    }

    public String asString() {
        return this.a;
    }

    public String toString() {
        return asString();
    }
}
