package org.androidannotations.annotations.sharedpreferences;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface SharedPref {

    public enum Scope {
        APPLICATION_DEFAULT,
        ACTIVITY,
        ACTIVITY_DEFAULT,
        UNIQUE
    }

    int mode() default 0;

    Scope value() default Scope.ACTIVITY;
}
