package org.aspectj.runtime.reflect;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.reflect.Method;
import java.util.StringTokenizer;
import org.aspectj.lang.reflect.AdviceSignature;

class AdviceSignatureImpl extends CodeSignatureImpl implements AdviceSignature {
    private Method a = null;
    Class returnType;

    AdviceSignatureImpl(int modifiers, String name, Class declaringType, Class[] parameterTypes, String[] parameterNames, Class[] exceptionTypes, Class returnType2) {
        super(modifiers, name, declaringType, parameterTypes, parameterNames, exceptionTypes);
        this.returnType = returnType2;
    }

    AdviceSignatureImpl(String stringRep) {
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
        if (sm.includeArgs) {
            buf.append(sm.makeTypeName(getReturnType()));
        }
        if (sm.includeArgs) {
            buf.append(Token.SEPARATOR);
        }
        buf.append(sm.makePrimaryTypeName(getDeclaringType(), getDeclaringTypeName()));
        buf.append(".");
        buf.append(a(getName()));
        sm.addSignature(buf, getParameterTypes());
        sm.addThrows(buf, getExceptionTypes());
        return buf.toString();
    }

    private static String a(String methodName) {
        if (methodName.indexOf(36) == -1) {
            return methodName;
        }
        StringTokenizer strTok = new StringTokenizer(methodName, "$");
        while (strTok.hasMoreTokens()) {
            String token = strTok.nextToken();
            if (token.startsWith("before") || token.startsWith("after")) {
                return token;
            }
            if (token.startsWith("around")) {
                return token;
            }
        }
        return methodName;
    }

    public Method getAdvice() {
        if (this.a == null) {
            try {
                this.a = getDeclaringType().getDeclaredMethod(getName(), getParameterTypes());
            } catch (Exception e) {
            }
        }
        return this.a;
    }
}
