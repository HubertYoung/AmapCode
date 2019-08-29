package com.alipay.mobile.nebula.appcenter.listen;

public interface NebulaAppCallback {
    public static final int AppSourceBuildIn = 2;
    public static final int AppSourceRequest = 0;
    public static final int AppSourceSync = 1;

    void getCallback(NebulaAppCallbackInfo nebulaAppCallbackInfo);
}
