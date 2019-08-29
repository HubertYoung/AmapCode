package com.autonavi.common;

import com.autonavi.common.KeyValueStorage;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface KeyValueStorage<T extends KeyValueStorage<T>> {

    public interface AssetStorage<T extends AssetStorage<T>> extends KeyValueStorage<T> {
    }

    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DefaultValue {
        String jsonValue() default "";

        double value() default 0.0d;
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface OldKey {
        String value();
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface StorageKey {
        String name() default "";

        int version() default 1;
    }

    public interface WebStorage extends KeyValueStorage<WebStorage>, Iterable<String> {
        String get(String str);

        WebStorage remove(String str);

        WebStorage set(String str, String str2);

        int size();
    }

    public interface a<T> {
    }

    T beginTransaction();

    void commit();
}
