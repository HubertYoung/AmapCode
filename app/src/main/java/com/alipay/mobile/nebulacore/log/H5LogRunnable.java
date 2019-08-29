package com.alipay.mobile.nebulacore.log;

import java.util.Map;

public abstract class H5LogRunnable implements Runnable {
    Map<String, Object> a;

    public H5LogRunnable(Map<String, Object> performance) {
        this.a = performance;
    }
}
