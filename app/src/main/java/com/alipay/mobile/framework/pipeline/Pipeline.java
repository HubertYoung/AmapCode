package com.alipay.mobile.framework.pipeline;

import java.util.concurrent.Executor;

public interface Pipeline {
    void addIdleListener(Runnable runnable);

    void addTask(Runnable runnable, String str);

    void addTask(Runnable runnable, String str, int i);

    void next();

    void setExecutor(Executor executor);

    void start();

    void stop();
}
