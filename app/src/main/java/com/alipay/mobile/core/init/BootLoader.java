package com.alipay.mobile.core.init;

import com.alipay.mobile.framework.MicroApplicationContext;

public interface BootLoader {
    public static final String TAG = "BootLoader";

    MicroApplicationContext getContext();

    void load();

    void loadBundle(String str);

    void loadServices();

    void postLoad();

    void preload();
}
