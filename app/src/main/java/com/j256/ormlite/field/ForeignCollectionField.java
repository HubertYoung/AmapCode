package com.j256.ormlite.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignCollectionField {
    public static final int MAX_EAGER_LEVEL = 1;

    String columnName() default "";

    boolean eager() default false;

    @Deprecated
    String foreignColumnName() default "";

    String foreignFieldName() default "";

    @Deprecated
    int maxEagerForeignCollectionLevel() default 1;

    int maxEagerLevel() default 1;

    boolean orderAscending() default true;

    String orderColumnName() default "";
}
