package org.xidea.el.impl;

import org.xidea.el.Expression;
import org.xidea.el.ExpressionInfo;
import org.xidea.el.ExpressionToken;
import org.xidea.el.ReferenceExpression;

public class ExpressionImpl implements Expression, ExpressionInfo, ReferenceExpression {
    protected final ExpressionToken a;

    public String toString() {
        return this.a.toString();
    }
}
