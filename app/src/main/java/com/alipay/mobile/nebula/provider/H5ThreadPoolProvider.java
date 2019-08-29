package com.alipay.mobile.nebula.provider;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public interface H5ThreadPoolProvider {
    ThreadPoolExecutor getExecutor(String str);

    ScheduledThreadPoolExecutor getScheduledExecutor();

    void submitOrdered(String str, Runnable runnable);
}
