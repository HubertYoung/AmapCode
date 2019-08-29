package com.alipay.android.phone.inside.protobuf.wire;

import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
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
