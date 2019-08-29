package org.aspectj.internal.lang.reflect;

import org.aspectj.lang.reflect.PerClauseKind;
import org.aspectj.lang.reflect.PointcutBasedPerClause;
import org.aspectj.lang.reflect.PointcutExpression;

public class PointcutBasedPerClauseImpl extends PerClauseImpl implements PointcutBasedPerClause {
    private final PointcutExpression a;

    public PointcutBasedPerClauseImpl(PerClauseKind kind, String pointcutExpression) {
        super(kind);
        this.a = new PointcutExpressionImpl(pointcutExpression);
    }

    public PointcutExpression getPointcutExpression() {
        return this.a;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        switch (getKind()) {
            case PERCFLOW:
                sb.append("percflow(");
                break;
            case PERCFLOWBELOW:
                sb.append("percflowbelow(");
                break;
            case PERTARGET:
                sb.append("pertarget(");
                break;
            case PERTHIS:
                sb.append("perthis(");
                break;
        }
        sb.append(this.a.asString());
        sb.append(")");
        return sb.toString();
    }
}
