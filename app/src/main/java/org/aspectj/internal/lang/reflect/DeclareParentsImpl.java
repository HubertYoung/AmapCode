package org.aspectj.internal.lang.reflect;

import java.lang.reflect.Type;
import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.DeclareParents;
import org.aspectj.lang.reflect.TypePattern;

public class DeclareParentsImpl implements DeclareParents {
    private AjType<?> a;
    private TypePattern b;
    private Type[] c;
    private String d;
    private String e;
    private boolean f;
    private boolean g = false;

    public DeclareParentsImpl(String targets, String parentsAsString, boolean isExtends, AjType<?> declaring) {
        this.b = new TypePatternImpl(targets);
        this.f = isExtends;
        this.a = declaring;
        this.d = parentsAsString;
        try {
            this.c = StringToType.commaSeparatedListToTypeArray(parentsAsString, declaring.getJavaClass());
        } catch (ClassNotFoundException cnfEx) {
            this.g = true;
            this.e = cnfEx.getMessage();
        }
    }

    public AjType getDeclaringType() {
        return this.a;
    }

    public TypePattern getTargetTypesPattern() {
        return this.b;
    }

    public boolean isExtends() {
        return this.f;
    }

    public boolean isImplements() {
        return !this.f;
    }

    public Type[] getParentTypes() {
        if (!this.g) {
            return this.c;
        }
        throw new ClassNotFoundException(this.e);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("declare parents : ");
        sb.append(getTargetTypesPattern().asString());
        sb.append(isExtends() ? " extends " : " implements ");
        sb.append(this.d);
        return sb.toString();
    }
}
