package org.aspectj.internal.lang.reflect;

import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.AjTypeSystem;
import org.aspectj.lang.reflect.DeclareSoft;
import org.aspectj.lang.reflect.PointcutExpression;

public class DeclareSoftImpl implements DeclareSoft {
    private AjType<?> a;
    private PointcutExpression b;
    private AjType<?> c;
    private String d;

    public DeclareSoftImpl(AjType<?> declaringType, String pcut, String exceptionTypeName) {
        this.a = declaringType;
        this.b = new PointcutExpressionImpl(pcut);
        try {
            this.c = AjTypeSystem.getAjType(Class.forName(exceptionTypeName, false, declaringType.getJavaClass().getClassLoader()));
        } catch (ClassNotFoundException e) {
            this.d = exceptionTypeName;
        }
    }

    public AjType getDeclaringType() {
        return this.a;
    }

    public AjType getSoftenedExceptionType() {
        if (this.d == null) {
            return this.c;
        }
        throw new ClassNotFoundException(this.d);
    }

    public PointcutExpression getPointcutExpression() {
        return this.b;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("declare soft : ");
        if (this.d != null) {
            sb.append(this.c.getName());
        } else {
            sb.append(this.d);
        }
        sb.append(" : ");
        sb.append(getPointcutExpression().asString());
        return sb.toString();
    }
}
