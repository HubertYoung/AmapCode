package org.aspectj.runtime.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.aspectj.lang.reflect.InitializerSignature;

class InitializerSignatureImpl extends CodeSignatureImpl implements InitializerSignature {
    private Constructor a;

    InitializerSignatureImpl(int modifiers, Class declaringType) {
        super(modifiers, Modifier.isStatic(modifiers) ? "<clinit>" : "<init>", declaringType, SignatureImpl.EMPTY_CLASS_ARRAY, SignatureImpl.EMPTY_STRING_ARRAY, SignatureImpl.EMPTY_CLASS_ARRAY);
    }

    InitializerSignatureImpl(String stringRep) {
        super(stringRep);
    }

    public String getName() {
        return Modifier.isStatic(getModifiers()) ? "<clinit>" : "<init>";
    }

    /* access modifiers changed from: protected */
    public String createToString(StringMaker sm) {
        StringBuffer buf = new StringBuffer();
        buf.append(sm.makeModifiersString(getModifiers()));
        buf.append(sm.makePrimaryTypeName(getDeclaringType(), getDeclaringTypeName()));
        buf.append(".");
        buf.append(getName());
        return buf.toString();
    }

    public Constructor getInitializer() {
        if (this.a == null) {
            try {
                this.a = getDeclaringType().getDeclaredConstructor(getParameterTypes());
            } catch (Exception e) {
            }
        }
        return this.a;
    }
}
