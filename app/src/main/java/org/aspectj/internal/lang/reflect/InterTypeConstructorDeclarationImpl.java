package org.aspectj.internal.lang.reflect;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.AjTypeSystem;
import org.aspectj.lang.reflect.InterTypeConstructorDeclaration;

public class InterTypeConstructorDeclarationImpl extends InterTypeDeclarationImpl implements InterTypeConstructorDeclaration {
    private Method a;

    public InterTypeConstructorDeclarationImpl(AjType<?> decType, String target, int mods, Method baseMethod) {
        super(decType, target, mods);
        this.a = baseMethod;
    }

    public AjType<?>[] getParameterTypes() {
        Class[] baseTypes = this.a.getParameterTypes();
        AjType[] ret = new AjType[(baseTypes.length - 1)];
        for (int i = 1; i < baseTypes.length; i++) {
            ret[i - 1] = AjTypeSystem.getAjType(baseTypes[i]);
        }
        return ret;
    }

    public Type[] getGenericParameterTypes() {
        Type[] baseTypes = this.a.getGenericParameterTypes();
        Type[] ret = new AjType[(baseTypes.length - 1)];
        for (int i = 1; i < baseTypes.length; i++) {
            if (baseTypes[i] instanceof Class) {
                ret[i - 1] = AjTypeSystem.getAjType((Class) baseTypes[i]);
            } else {
                ret[i - 1] = baseTypes[i];
            }
        }
        return ret;
    }

    public AjType<?>[] getExceptionTypes() {
        Class[] baseTypes = this.a.getExceptionTypes();
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
        sb.append(this.targetTypeName);
        sb.append(".new");
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
