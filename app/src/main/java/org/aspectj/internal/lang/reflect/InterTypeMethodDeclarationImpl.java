package org.aspectj.internal.lang.reflect;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.AjTypeSystem;
import org.aspectj.lang.reflect.InterTypeMethodDeclaration;

public class InterTypeMethodDeclarationImpl extends InterTypeDeclarationImpl implements InterTypeMethodDeclaration {
    private String a;
    private Method b;
    private int c;

    public InterTypeMethodDeclarationImpl(AjType<?> decType, String target, int mods, String name, Method itdInterMethod) {
        super(decType, target, mods);
        this.c = 1;
        this.a = name;
        this.b = itdInterMethod;
    }

    public InterTypeMethodDeclarationImpl(AjType<?> decType, AjType<?> targetType, Method base, int modifiers) {
        super(decType, targetType, modifiers);
        this.c = 1;
        this.c = 0;
        this.a = base.getName();
        this.b = base;
    }

    public String getName() {
        return this.a;
    }

    public AjType<?> getReturnType() {
        return AjTypeSystem.getAjType(this.b.getReturnType());
    }

    public Type getGenericReturnType() {
        Type gRet = this.b.getGenericReturnType();
        if (gRet instanceof Class) {
            return AjTypeSystem.getAjType((Class) gRet);
        }
        return gRet;
    }

    public AjType<?>[] getParameterTypes() {
        Class[] baseTypes = this.b.getParameterTypes();
        AjType[] ret = new AjType[(baseTypes.length - this.c)];
        for (int i = this.c; i < baseTypes.length; i++) {
            ret[i - this.c] = AjTypeSystem.getAjType(baseTypes[i]);
        }
        return ret;
    }

    public Type[] getGenericParameterTypes() {
        Type[] baseTypes = this.b.getGenericParameterTypes();
        Type[] ret = new AjType[(baseTypes.length - this.c)];
        for (int i = this.c; i < baseTypes.length; i++) {
            if (baseTypes[i] instanceof Class) {
                ret[i - this.c] = AjTypeSystem.getAjType((Class) baseTypes[i]);
            } else {
                ret[i - this.c] = baseTypes[i];
            }
        }
        return ret;
    }

    public TypeVariable<Method>[] getTypeParameters() {
        return this.b.getTypeParameters();
    }

    public AjType<?>[] getExceptionTypes() {
        Class[] baseTypes = this.b.getExceptionTypes();
        AjType[] ret = new AjType[baseTypes.length];
        for (int i = 0; i < baseTypes.length; i++) {
            ret[i] = AjTypeSystem.getAjType(baseTypes[i]);
        }
        return ret;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(Modifier.toString(getModifiers()));
        sb.append(Token.SEPARATOR);
        sb.append(getReturnType().toString());
        sb.append(Token.SEPARATOR);
        sb.append(this.targetTypeName);
        sb.append(".");
        sb.append(getName());
        sb.append("(");
        AjType[] pTypes = getParameterTypes();
        for (int i = 0; i < pTypes.length - 1; i++) {
            sb.append(pTypes[i].toString());
            sb.append(", ");
        }
        if (pTypes.length > 0) {
            sb.append(pTypes[pTypes.length - 1].toString());
        }
        sb.append(")");
        return sb.toString();
    }
}
