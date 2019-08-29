package com.autonavi.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

public interface SQLiteMapper<T> {

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SQLiteEntry {
        String name() default "";

        int version() default 1;
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SQLiteProperty {
        boolean index() default false;

        String value() default "";
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SQLiteUpdate {
        int value();
    }

    T a(T t);

    List<T> a(String str);

    boolean b(Object obj);
}
