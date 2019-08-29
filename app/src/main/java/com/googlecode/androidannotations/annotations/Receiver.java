package com.googlecode.androidannotations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface Receiver {

    @Target({ElementType.PARAMETER})
    @Retention(RetentionPolicy.CLASS)
    public @interface Extra {
        String value() default "";
    }

    public enum RegisterAt {
        OnCreateOnDestroy,
        OnStartOnStop,
        OnResumeOnPause,
        OnAttachOnDetach
    }

    String[] actions();

    String[] dataSchemes() default {};

    boolean local() default false;

    RegisterAt registerAt() default RegisterAt.OnCreateOnDestroy;
}
