package com.alipay.mobile.common.task.pipeline;

import com.alipay.android.hackbyte.ClassVerifier;
import java.util.concurrent.Executor;

public interface PipeLine {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    void addTask(Runnable runnable, String str);

    void addTask(Runnable runnable, String str, int i);

    void setExecutor(Executor executor);

    void start();

    void stop();
}
