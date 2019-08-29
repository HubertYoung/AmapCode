package org.aspectj.internal.lang.reflect;

import java.util.StringTokenizer;
import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.DeclarePrecedence;
import org.aspectj.lang.reflect.TypePattern;

public class DeclarePrecedenceImpl implements DeclarePrecedence {
    private AjType<?> a;
    private TypePattern[] b;
    private String c;

    public DeclarePrecedenceImpl(String precedenceList, AjType declaring) {
        this.a = declaring;
        this.c = precedenceList;
        String toTokenize = precedenceList;
        StringTokenizer strTok = new StringTokenizer(precedenceList.startsWith("(") ? toTokenize.substring(1, toTokenize.length() - 1) : toTokenize, ",");
        this.b = new TypePattern[strTok.countTokens()];
        for (int i = 0; i < this.b.length; i++) {
            this.b[i] = new TypePatternImpl(strTok.nextToken().trim());
        }
    }

    public AjType getDeclaringType() {
        return this.a;
    }

    public TypePattern[] getPrecedenceOrder() {
        return this.b;
    }

    public String toString() {
        return "declare precedence : " + this.c;
    }
}
