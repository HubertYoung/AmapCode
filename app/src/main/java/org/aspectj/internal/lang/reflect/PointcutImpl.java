package org.aspectj.internal.lang.reflect;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.reflect.Method;
import java.util.StringTokenizer;
import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.AjTypeSystem;
import org.aspectj.lang.reflect.Pointcut;
import org.aspectj.lang.reflect.PointcutExpression;

public class PointcutImpl implements Pointcut {
    private final String a;
    private final PointcutExpression b;
    private final Method c;
    private final AjType d;
    private String[] e = new String[0];

    protected PointcutImpl(String name, String pc, Method method, AjType declaringType, String pNames) {
        this.a = name;
        this.b = new PointcutExpressionImpl(pc);
        this.c = method;
        this.d = declaringType;
        this.e = a(pNames);
    }

    public PointcutExpression getPointcutExpression() {
        return this.b;
    }

    public String getName() {
        return this.a;
    }

    public int getModifiers() {
        return this.c.getModifiers();
    }

    public AjType<?>[] getParameterTypes() {
        Class[] baseParamTypes = this.c.getParameterTypes();
        AjType[] ajParamTypes = new AjType[baseParamTypes.length];
        for (int i = 0; i < ajParamTypes.length; i++) {
            ajParamTypes[i] = AjTypeSystem.getAjType(baseParamTypes[i]);
        }
        return ajParamTypes;
    }

    public AjType getDeclaringType() {
        return this.d;
    }

    public String[] getParameterNames() {
        return this.e;
    }

    private static String[] a(String s) {
        StringTokenizer strTok = new StringTokenizer(s, ",");
        String[] ret = new String[strTok.countTokens()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = strTok.nextToken().trim();
        }
        return ret;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getName());
        sb.append("(");
        AjType[] ptypes = getParameterTypes();
        for (int i = 0; i < ptypes.length; i++) {
            sb.append(ptypes[i].getName());
            if (!(this.e == null || this.e[i] == null)) {
                sb.append(Token.SEPARATOR);
                sb.append(this.e[i]);
            }
            if (i + 1 < ptypes.length) {
                sb.append(",");
            }
        }
        sb.append(") : ");
        sb.append(getPointcutExpression().asString());
        return sb.toString();
    }
}
