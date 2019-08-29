package com.squareup.wire;

import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProtoField {
    boolean deprecated() default false;

    Label label() default Label.OPTIONAL;

    boolean redacted() default false;

    int tag();

    Datatype type() default Datatype.MESSAGE;
}
