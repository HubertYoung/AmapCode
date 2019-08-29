package org.aspectj.runtime.reflect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.UnlockSignature;

class UnlockSignatureImpl extends SignatureImpl implements UnlockSignature {
    private Class a;

    UnlockSignatureImpl(Class c) {
        super(8, JoinPoint.SYNCHRONIZATION_UNLOCK, c);
        this.a = c;
    }

    UnlockSignatureImpl(String stringRep) {
        super(stringRep);
    }

    /* access modifiers changed from: protected */
    public String createToString(StringMaker sm) {
        if (this.a == null) {
            this.a = extractType(3);
        }
        return new StringBuffer("unlock(").append(sm.makeTypeName(this.a)).append(")").toString();
    }

    public Class getParameterType() {
        if (this.a == null) {
            this.a = extractType(3);
        }
        return this.a;
    }
}
