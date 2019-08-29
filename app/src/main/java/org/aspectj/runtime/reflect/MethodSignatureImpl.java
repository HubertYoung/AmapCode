package org.aspectj.runtime.reflect;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import org.aspectj.lang.reflect.MethodSignature;

class MethodSignatureImpl extends CodeSignatureImpl implements MethodSignature {
    private Method a;
    Class returnType;

    MethodSignatureImpl(int modifiers, String name, Class declaringType, Class[] parameterTypes, String[] parameterNames, Class[] exceptionTypes, Class returnType2) {
        super(modifiers, name, declaringType, parameterTypes, parameterNames, exceptionTypes);
        this.returnType = returnType2;
    }

    MethodSignatureImpl(String stringRep) {
        super(stringRep);
    }

    public Class getReturnType() {
        if (this.returnType == null) {
            this.returnType = extractType(6);
        }
        return this.returnType;
    }

    /* access modifiers changed from: protected */
    public String createToString(StringMaker sm) {
        StringBuffer buf = new StringBuffer();
        buf.append(sm.makeModifiersString(getModifiers()));
        if (sm.includeArgs) {
            buf.append(sm.makeTypeName(getReturnType()));
        }
        if (sm.includeArgs) {
            buf.append(Token.SEPARATOR);
        }
        buf.append(sm.makePrimaryTypeName(getDeclaringType(), getDeclaringTypeName()));
        buf.append(".");
        buf.append(getName());
        sm.addSignature(buf, getParameterTypes());
        sm.addThrows(buf, getExceptionTypes());
        return buf.toString();
    }

    public Method getMethod() {
        if (this.a == null) {
            Class dtype = getDeclaringType();
            try {
                this.a = dtype.getDeclaredMethod(getName(), getParameterTypes());
            } catch (NoSuchMethodException e) {
                Set searched = new HashSet();
                searched.add(dtype);
                this.a = a(dtype, getName(), getParameterTypes(), searched);
            }
        }
        return this.a;
    }

    private Method a(Class type, String name, Class[] params, Set searched) {
        if (type == null) {
            return null;
        }
        if (!searched.contains(type)) {
            searched.add(type);
            try {
                return type.getDeclaredMethod(name, params);
            } catch (NoSuchMethodException e) {
            }
        }
        Method m = a(type.getSuperclass(), name, params, searched);
        if (m != null) {
            return m;
        }
        Class[] superinterfaces = type.getInterfaces();
        if (superinterfaces != null) {
            for (Class a2 : superinterfaces) {
                Method m2 = a(a2, name, params, searched);
                if (m2 != null) {
                    return m2;
                }
            }
        }
        return null;
    }
}
