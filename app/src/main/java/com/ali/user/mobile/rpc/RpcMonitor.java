package com.ali.user.mobile.rpc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcMonitor {
    String appId() default "20000008";

    String behavior() default "event";

    String[] externalParams() default {"token"};

    String operationType();

    String param1() default "";

    String param3() default "";

    int resultType() default 1;

    String seedId();

    String userCaseId();
}
