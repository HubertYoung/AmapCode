package com.alipay.mobile.framework.service.common.threadpool;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskFactory implements ThreadFactory {
    private AtomicInteger a = new AtomicInteger(1);
    private String b;
    private int c;

    public TaskFactory(String threadNamePrefix, int threadPriority) {
        this.b = threadNamePrefix;
        this.c = threadPriority;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, this.b + this.a.getAndIncrement());
        thread.setPriority(this.c);
        return thread;
    }
}
