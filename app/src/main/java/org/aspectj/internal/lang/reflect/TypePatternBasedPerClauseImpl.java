package org.aspectj.internal.lang.reflect;

import org.aspectj.lang.reflect.PerClauseKind;
import org.aspectj.lang.reflect.TypePattern;
import org.aspectj.lang.reflect.TypePatternBasedPerClause;

public class TypePatternBasedPerClauseImpl extends PerClauseImpl implements TypePatternBasedPerClause {
    private TypePattern a;

    public TypePatternBasedPerClauseImpl(PerClauseKind kind, String pattern) {
        super(kind);
        this.a = new TypePatternImpl(pattern);
    }

    public TypePattern getTypePattern() {
        return this.a;
    }

    public String toString() {
        return "pertypewithin(" + this.a.asString() + ")";
    }
}
