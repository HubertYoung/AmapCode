package com.alipay.android.phone.beehive.androidannotations.api;

import com.alipay.mobile.beehive.rpc.CacheMode;
import com.alipay.mobile.beehive.rpc.LoadingMode;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface BeehiveRpc {
    boolean autoModifyLoadingOnCache() default true;

    String cacheKey() default "";

    CacheMode cacheMode() default CacheMode.NONE;

    int flowTipHolderViewId() default 0;

    String flowTipHolderViewName() default "";

    LoadingMode loadingMode() default LoadingMode.CANCELABLE_LOADING;

    String onCacheSuccess() default "";

    String onFail() default "";

    String onFinishEnd() default "";

    String onFinishStart() default "";

    String onNetworkException() default "";

    String onNotNetworkException() default "";

    String onSuccess() default "";

    String rpcResultProcessor() default "";

    boolean showNetError() default false;

    boolean showWarn() default false;
}
