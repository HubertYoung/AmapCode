package org.aspectj.runtime.reflect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.LockSignature;

class LockSignatureImpl extends SignatureImpl implements LockSignature {
    private Class a;

    LockSignatureImpl(Class c) {
        super(8, JoinPoint.SYNCHRONIZATION_LOCK, c);
        this.a = c;
    }

    LockSignatureImpl(String stringRep) {
        super(stringRep);
    }

    /* access modifiers changed from: protected */
    public String createToString(StringMaker sm) {
        if (this.a == null) {
            this.a = extractType(3);
        }
        return new StringBuffer("lock(").append(sm.makeTypeName(this.a)).append(")").toString();
    }

    public Class getParameterType() {
        if (this.a == null) {
            this.a = extractType(3);
        }
        return this.a;
    }
}
