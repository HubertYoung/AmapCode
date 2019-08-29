package com.alipay.mobile.beehive.api;

public interface UserSceneExecutor {

    public interface Interceptor {
        boolean handleThrottle();
    }

    void addThrottleInterceptor(Interceptor interceptor);

    boolean isThrottlePrevent();

    void preventThrottle(boolean z);

    void removeThrottleInterceptor(Interceptor interceptor);
}
