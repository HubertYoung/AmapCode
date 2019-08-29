package com.alipay.android.phone.androidannotations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface AlipayNeedPermissions {
    String denniedCall() default "";

    String permission() default "";

    String[] permissions() default {};

    String rationaleCall() default "";
}
