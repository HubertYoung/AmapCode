package org.aspectj.runtime.reflect;

import org.aspectj.lang.reflect.CatchClauseSignature;

class CatchClauseSignatureImpl extends SignatureImpl implements CatchClauseSignature {
    String parameterName;
    Class parameterType;

    CatchClauseSignatureImpl(Class declaringType, Class parameterType2, String parameterName2) {
        super(0, "catch", declaringType);
        this.parameterType = parameterType2;
        this.parameterName = parameterName2;
    }

    CatchClauseSignatureImpl(String stringRep) {
        super(stringRep);
    }

    public Class getParameterType() {
        if (this.parameterType == null) {
            this.parameterType = extractType(3);
        }
        return this.parameterType;
    }

    public String getParameterName() {
        if (this.parameterName == null) {
            this.parameterName = extractString(4);
        }
        return this.parameterName;
    }

    /* access modifiers changed from: protected */
    public String createToString(StringMaker sm) {
        return new StringBuffer("catch(").append(sm.makeTypeName(getParameterType())).append(")").toString();
    }
}
