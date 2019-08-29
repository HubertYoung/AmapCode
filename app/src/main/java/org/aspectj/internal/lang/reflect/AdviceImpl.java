package org.aspectj.internal.lang.reflect;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.aspectj.lang.annotation.AdviceName;
import org.aspectj.lang.reflect.Advice;
import org.aspectj.lang.reflect.AdviceKind;
import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.AjTypeSystem;
import org.aspectj.lang.reflect.PointcutExpression;

public class AdviceImpl implements Advice {
    private final AdviceKind a;
    private final Method b;
    private PointcutExpression c;
    private boolean d = false;
    private Type[] e;
    private AjType[] f;
    private AjType[] g;

    protected AdviceImpl(Method method, String pointcut, AdviceKind type) {
        this.a = type;
        this.b = method;
        this.c = new PointcutExpressionImpl(pointcut);
    }

    protected AdviceImpl(Method method, String pointcut, AdviceKind type, String extraParamName) {
        this(method, pointcut, type);
    }

    public AjType getDeclaringType() {
        return AjTypeSystem.getAjType(this.b.getDeclaringClass());
    }

    public Type[] getGenericParameterTypes() {
        if (this.e == null) {
            Type[] genTypes = this.b.getGenericParameterTypes();
            int syntheticCount = 0;
            for (Type t : genTypes) {
                if ((t instanceof Class) && ((Class) t).getPackage().getName().equals("org.aspectj.runtime.internal")) {
                    syntheticCount++;
                }
            }
            this.e = new Type[(genTypes.length - syntheticCount)];
            for (int i = 0; i < this.e.length; i++) {
                if (genTypes[i] instanceof Class) {
                    this.e[i] = AjTypeSystem.getAjType((Class) genTypes[i]);
                } else {
                    this.e[i] = genTypes[i];
                }
            }
        }
        return this.e;
    }

    public AjType<?>[] getParameterTypes() {
        if (this.f == null) {
            Class[] ptypes = this.b.getParameterTypes();
            int syntheticCount = 0;
            for (Class cls : ptypes) {
                if (cls.getPackage().getName().equals("org.aspectj.runtime.internal")) {
                    syntheticCount++;
                }
            }
            this.f = new AjType[(ptypes.length - syntheticCount)];
            for (int i = 0; i < this.f.length; i++) {
                this.f[i] = AjTypeSystem.getAjType(ptypes[i]);
            }
        }
        return this.f;
    }

    public AjType<?>[] getExceptionTypes() {
        if (this.g == null) {
            Class[] exTypes = this.b.getExceptionTypes();
            this.g = new AjType[exTypes.length];
            for (int i = 0; i < exTypes.length; i++) {
                this.g[i] = AjTypeSystem.getAjType(exTypes[i]);
            }
        }
        return this.g;
    }

    public AdviceKind getKind() {
        return this.a;
    }

    public String getName() {
        String adviceName = this.b.getName();
        if (!adviceName.startsWith("ajc$")) {
            return adviceName;
        }
        AdviceName name = (AdviceName) this.b.getAnnotation(AdviceName.class);
        if (name != null) {
            return name.value();
        }
        return "";
    }

    public PointcutExpression getPointcutExpression() {
        return this.c;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (getName().length() > 0) {
            sb.append("@AdviceName(\"");
            sb.append(getName());
            sb.append("\") ");
        }
        if (getKind() == AdviceKind.AROUND) {
            sb.append(this.b.getGenericReturnType().toString());
            sb.append(Token.SEPARATOR);
        }
        switch (getKind()) {
            case AFTER:
                sb.append("after(");
                break;
            case AFTER_RETURNING:
                sb.append("after(");
                break;
            case AFTER_THROWING:
                sb.append("after(");
                break;
            case AROUND:
                sb.append("around(");
                break;
            case BEFORE:
                sb.append("before(");
                break;
        }
        AjType[] ptypes = getParameterTypes();
        int len = ptypes.length;
        if (this.d) {
            len--;
        }
        for (int i = 0; i < len; i++) {
            sb.append(ptypes[i].getName());
            if (i + 1 < len) {
                sb.append(",");
            }
        }
        sb.append(") ");
        switch (getKind()) {
            case AFTER_RETURNING:
                sb.append("returning");
                if (this.d) {
                    sb.append("(");
                    sb.append(ptypes[len - 1].getName());
                    sb.append(") ");
                    break;
                }
                break;
            case AFTER_THROWING:
                break;
        }
        sb.append("throwing");
        if (this.d) {
            sb.append("(");
            sb.append(ptypes[len - 1].getName());
            sb.append(") ");
        }
        AjType[] exTypes = getExceptionTypes();
        if (exTypes.length > 0) {
            sb.append("throws ");
            for (int i2 = 0; i2 < exTypes.length; i2++) {
                sb.append(exTypes[i2].getName());
                if (i2 + 1 < exTypes.length) {
                    sb.append(",");
                }
            }
            sb.append(Token.SEPARATOR);
        }
        sb.append(": ");
        sb.append(getPointcutExpression().asString());
        return sb.toString();
    }
}
