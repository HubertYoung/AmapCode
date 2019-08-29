package org.aspectj.lang.reflect;

public interface PointcutBasedPerClause extends PerClause {
    PointcutExpression getPointcutExpression();
}
