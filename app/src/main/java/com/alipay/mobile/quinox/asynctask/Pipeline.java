package com.alipay.mobile.quinox.asynctask;

import java.util.concurrent.Executor;

public interface Pipeline {
    public static final String TAG = "AsyTskExecutor";

    void addIdleListener(Runnable runnable);

    void addTask(Runnable runnable, String str);

    void addTask(Runnable runnable, String str, int i);

    int next();

    void setExecutor(Executor executor);

    int start();

    int stop();
}
