package org.aspectj.internal.lang.reflect;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.AjTypeSystem;
import org.aspectj.lang.reflect.InterTypeFieldDeclaration;

public class InterTypeFieldDeclarationImpl extends InterTypeDeclarationImpl implements InterTypeFieldDeclaration {
    private String a;
    private AjType<?> b;
    private Type c;

    public InterTypeFieldDeclarationImpl(AjType<?> decType, String target, int mods, String name, AjType<?> type, Type genericType) {
        super(decType, target, mods);
        this.a = name;
        this.b = type;
        this.c = genericType;
    }

    public InterTypeFieldDeclarationImpl(AjType<?> decType, AjType<?> targetType, Field base) {
        super(decType, targetType, base.getModifiers());
        this.a = base.getName();
        this.b = AjTypeSystem.getAjType(base.getType());
        Type gt = base.getGenericType();
        if (gt instanceof Class) {
            this.c = AjTypeSystem.getAjType((Class) gt);
        } else {
            this.c = gt;
        }
    }

    public String getName() {
        return this.a;
    }

    public AjType<?> getType() {
        return this.b;
    }

    public Type getGenericType() {
        return this.c;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(Modifier.toString(getModifiers()));
        sb.append(Token.SEPARATOR);
        sb.append(getType().toString());
        sb.append(Token.SEPARATOR);
        sb.append(this.targetTypeName);
        sb.append(".");
        sb.append(getName());
        return sb.toString();
    }
}
