package org.xidea.el;

public class ExpressionSyntaxException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public ExpressionSyntaxException(String str, Throwable th) {
        super(str, th);
    }

    public ExpressionSyntaxException(String str) {
        super(str);
    }
}
