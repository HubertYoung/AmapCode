package org.aspectj.internal.lang.reflect;

import java.lang.annotation.Annotation;
import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.DeclareAnnotation;
import org.aspectj.lang.reflect.DeclareAnnotation.Kind;
import org.aspectj.lang.reflect.SignaturePattern;
import org.aspectj.lang.reflect.TypePattern;

public class DeclareAnnotationImpl implements DeclareAnnotation {
    private Annotation a;
    private String b;
    private AjType<?> c;
    private Kind d;
    private TypePattern e;
    private SignaturePattern f;

    public DeclareAnnotationImpl(AjType<?> declaring, String kindString, String pattern, Annotation ann, String annText) {
        this.c = declaring;
        if (kindString.equals("at_type")) {
            this.d = Kind.Type;
        } else if (kindString.equals("at_field")) {
            this.d = Kind.Field;
        } else if (kindString.equals("at_method")) {
            this.d = Kind.Method;
        } else if (kindString.equals("at_constructor")) {
            this.d = Kind.Constructor;
        } else {
            throw new IllegalStateException("Unknown declare annotation kind: " + kindString);
        }
        if (this.d == Kind.Type) {
            this.e = new TypePatternImpl(pattern);
        } else {
            this.f = new SignaturePatternImpl(pattern);
        }
        this.a = ann;
        this.b = annText;
    }

    public AjType<?> getDeclaringType() {
        return this.c;
    }

    public Kind getKind() {
        return this.d;
    }

    public SignaturePattern getSignaturePattern() {
        return this.f;
    }

    public TypePattern getTypePattern() {
        return this.e;
    }

    public Annotation getAnnotation() {
        return this.a;
    }

    public String getAnnotationAsText() {
        return this.b;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("declare @");
        switch (getKind()) {
            case Type:
                sb.append("type : ");
                sb.append(getTypePattern().asString());
                break;
            case Method:
                sb.append("method : ");
                sb.append(getSignaturePattern().asString());
                break;
            case Field:
                sb.append("field : ");
                sb.append(getSignaturePattern().asString());
                break;
            case Constructor:
                sb.append("constructor : ");
                sb.append(getSignaturePattern().asString());
                break;
        }
        sb.append(" : ");
        sb.append(getAnnotationAsText());
        return sb.toString();
    }
}
