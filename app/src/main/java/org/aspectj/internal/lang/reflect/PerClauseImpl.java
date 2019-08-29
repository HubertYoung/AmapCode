package org.aspectj.internal.lang.reflect;

import org.aspectj.lang.reflect.PerClause;
import org.aspectj.lang.reflect.PerClauseKind;

public class PerClauseImpl implements PerClause {
    private final PerClauseKind a;

    protected PerClauseImpl(PerClauseKind kind) {
        this.a = kind;
    }

    public PerClauseKind getKind() {
        return this.a;
    }

    public String toString() {
        return "issingleton()";
    }
}
