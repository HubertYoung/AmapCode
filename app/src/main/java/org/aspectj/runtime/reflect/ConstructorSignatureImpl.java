package org.aspectj.runtime.reflect;

import java.lang.reflect.Constructor;
import org.aspectj.lang.reflect.ConstructorSignature;

class ConstructorSignatureImpl extends CodeSignatureImpl implements ConstructorSignature {
    private Constructor a;

    ConstructorSignatureImpl(int modifiers, Class declaringType, Class[] parameterTypes, String[] parameterNames, Class[] exceptionTypes) {
        super(modifiers, "<init>", declaringType, parameterTypes, parameterNames, exceptionTypes);
    }

    ConstructorSignatureImpl(String stringRep) {
        super(stringRep);
    }

    public String getName() {
        return "<init>";
    }

    /* access modifiers changed from: protected */
    public String createToString(StringMaker sm) {
        StringBuffer buf = new StringBuffer();
        buf.append(sm.makeModifiersString(getModifiers()));
        buf.append(sm.makePrimaryTypeName(getDeclaringType(), getDeclaringTypeName()));
        sm.addSignature(buf, getParameterTypes());
        sm.addThrows(buf, getExceptionTypes());
        return buf.toString();
    }

    public Constructor getConstructor() {
        if (this.a == null) {
            try {
                this.a = getDeclaringType().getDeclaredConstructor(getParameterTypes());
            } catch (Exception e) {
            }
        }
        return this.a;
    }
}
