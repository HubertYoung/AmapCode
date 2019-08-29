package org.aspectj.runtime.reflect;

import org.aspectj.lang.reflect.CodeSignature;

abstract class CodeSignatureImpl extends MemberSignatureImpl implements CodeSignature {
    Class[] exceptionTypes;
    String[] parameterNames;
    Class[] parameterTypes;

    CodeSignatureImpl(int modifiers, String name, Class declaringType, Class[] parameterTypes2, String[] parameterNames2, Class[] exceptionTypes2) {
        super(modifiers, name, declaringType);
        this.parameterTypes = parameterTypes2;
        this.parameterNames = parameterNames2;
        this.exceptionTypes = exceptionTypes2;
    }

    CodeSignatureImpl(String stringRep) {
        super(stringRep);
    }

    public Class[] getParameterTypes() {
        if (this.parameterTypes == null) {
            this.parameterTypes = extractTypes(3);
        }
        return this.parameterTypes;
    }

    public String[] getParameterNames() {
        if (this.parameterNames == null) {
            this.parameterNames = extractStrings(4);
        }
        return this.parameterNames;
    }

    public Class[] getExceptionTypes() {
        if (this.exceptionTypes == null) {
            this.exceptionTypes = extractTypes(5);
        }
        return this.exceptionTypes;
    }
}
