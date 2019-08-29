package org.eclipse.mat.snapshot.model;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.Serializable;

public final class Field extends FieldDescriptor implements Serializable {
    private static final long serialVersionUID = 2;
    protected Object value;

    public Field(String str, int i, Object obj) {
        super(str, i);
        this.value = obj;
    }

    public final Object getValue() {
        return this.value;
    }

    public final void setValue(Object obj) {
        this.value = obj;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.type);
        sb.append(Token.SEPARATOR);
        sb.append(this.name);
        sb.append(": \t");
        sb.append(this.value);
        return sb.toString();
    }
}
