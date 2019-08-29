package com.autonavi.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface Callback<ResultType> {

    public interface CachePolicyCallback {

        public enum CachePolicy {
            CacheOnly,
            NetworkOnly,
            Any
        }
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Loading {
        int id() default 0;

        String message() default "加载中...";
    }

    public interface PrepareCallback<RawType, ResultType> extends Callback<ResultType> {
        ResultType prepare(RawType rawtype);
    }

    public interface ResponseCodeCallback<ResultType> extends Callback<ResultType> {
        void onReseponseCode(int i);
    }

    public interface a {
        void cancel();

        boolean isCancelled();
    }

    public interface b {
        void onCancelled();
    }

    public interface c {
        String getLoadingMessage();
    }

    public interface d extends b {
        void onLoading(long j, long j2);

        void onStart();
    }

    void callback(ResultType resulttype);

    void error(Throwable th, boolean z);
}
