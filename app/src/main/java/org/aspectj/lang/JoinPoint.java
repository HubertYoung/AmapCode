package org.aspectj.lang;

import org.aspectj.lang.reflect.SourceLocation;

public interface JoinPoint {
    public static final String ADVICE_EXECUTION = "adviceexecution";
    public static final String CONSTRUCTOR_CALL = "constructor-call";
    public static final String CONSTRUCTOR_EXECUTION = "constructor-execution";
    public static final String EXCEPTION_HANDLER = "exception-handler";
    public static final String FIELD_GET = "field-get";
    public static final String FIELD_SET = "field-set";
    public static final String INITIALIZATION = "initialization";
    public static final String METHOD_CALL = "method-call";
    public static final String METHOD_EXECUTION = "method-execution";
    public static final String PREINITIALIZATION = "preinitialization";
    public static final String STATICINITIALIZATION = "staticinitialization";
    public static final String SYNCHRONIZATION_LOCK = "lock";
    public static final String SYNCHRONIZATION_UNLOCK = "unlock";

    public interface EnclosingStaticPart extends StaticPart {
    }

    public interface StaticPart {
        int getId();

        String getKind();

        Signature getSignature();

        SourceLocation getSourceLocation();

        String toLongString();

        String toShortString();

        String toString();
    }

    Object[] getArgs();

    String getKind();

    Signature getSignature();

    SourceLocation getSourceLocation();

    StaticPart getStaticPart();

    Object getTarget();

    Object getThis();

    String toLongString();

    String toShortString();

    String toString();
}
