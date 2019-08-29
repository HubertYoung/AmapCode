package org.aspectj.runtime.reflect;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.reflect.Field;
import org.aspectj.lang.reflect.FieldSignature;

public class FieldSignatureImpl extends MemberSignatureImpl implements FieldSignature {
    private Field a;
    Class fieldType;

    FieldSignatureImpl(int modifiers, String name, Class declaringType, Class fieldType2) {
        super(modifiers, name, declaringType);
        this.fieldType = fieldType2;
    }

    FieldSignatureImpl(String stringRep) {
        super(stringRep);
    }

    public Class getFieldType() {
        if (this.fieldType == null) {
            this.fieldType = extractType(3);
        }
        return this.fieldType;
    }

    /* access modifiers changed from: protected */
    public String createToString(StringMaker sm) {
        StringBuffer buf = new StringBuffer();
        buf.append(sm.makeModifiersString(getModifiers()));
        if (sm.includeArgs) {
            buf.append(sm.makeTypeName(getFieldType()));
        }
        if (sm.includeArgs) {
            buf.append(Token.SEPARATOR);
        }
        buf.append(sm.makePrimaryTypeName(getDeclaringType(), getDeclaringTypeName()));
        buf.append(".");
        buf.append(getName());
        return buf.toString();
    }

    public Field getField() {
        if (this.a == null) {
            try {
                this.a = getDeclaringType().getDeclaredField(getName());
            } catch (Exception e) {
            }
        }
        return this.a;
    }
}
