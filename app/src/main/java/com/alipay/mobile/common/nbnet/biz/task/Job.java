package com.alipay.mobile.common.nbnet.biz.task;

import java.util.concurrent.Callable;

public abstract class Job<T> implements Callable {
    public abstract T a();

    public T call() {
        return a();
    }
}
