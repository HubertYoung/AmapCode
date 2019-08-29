package org.aspectj.internal.lang.reflect;

import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.InterTypeDeclaration;

public class InterTypeDeclarationImpl implements InterTypeDeclaration {
    private AjType<?> a;
    private AjType<?> b;
    private int c;
    protected String targetTypeName;

    public InterTypeDeclarationImpl(AjType<?> decType, String target, int mods) {
        this.a = decType;
        this.targetTypeName = target;
        this.c = mods;
        try {
            this.b = (AjType) StringToType.stringToType(target, decType.getJavaClass());
        } catch (ClassNotFoundException e) {
        }
    }

    public InterTypeDeclarationImpl(AjType<?> decType, AjType<?> targetType, int mods) {
        this.a = decType;
        this.b = targetType;
        this.targetTypeName = targetType.getName();
        this.c = mods;
    }

    public AjType<?> getDeclaringType() {
        return this.a;
    }

    public AjType<?> getTargetType() {
        if (this.b != null) {
            return this.b;
        }
        throw new ClassNotFoundException(this.targetTypeName);
    }

    public int getModifiers() {
        return this.c;
    }
}
