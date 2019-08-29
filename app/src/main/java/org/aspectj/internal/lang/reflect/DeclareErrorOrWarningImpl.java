package org.aspectj.internal.lang.reflect;

import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.DeclareErrorOrWarning;
import org.aspectj.lang.reflect.PointcutExpression;

public class DeclareErrorOrWarningImpl implements DeclareErrorOrWarning {
    private PointcutExpression a;
    private String b;
    private boolean c;
    private AjType d;

    public DeclareErrorOrWarningImpl(String pointcut, String message, boolean isError, AjType decType) {
        this.a = new PointcutExpressionImpl(pointcut);
        this.b = message;
        this.c = isError;
        this.d = decType;
    }

    public AjType getDeclaringType() {
        return this.d;
    }

    public PointcutExpression getPointcutExpression() {
        return this.a;
    }

    public String getMessage() {
        return this.b;
    }

    public boolean isError() {
        return this.c;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("declare ");
        sb.append(isError() ? "error : " : "warning : ");
        sb.append(getPointcutExpression().asString());
        sb.append(" : ");
        sb.append("\"");
        sb.append(getMessage());
        sb.append("\"");
        return sb.toString();
    }
}
