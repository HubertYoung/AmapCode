package com.alipay.multimedia.adapter;

import android.os.Looper;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public abstract class AdapterFactory {
    private Executor mExecutor;
    private L mL;

    public interface Executor {
        <V> Future<V> execute(Callable<V> callable);

        void execute(Runnable runnable);

        <V> Future<V> io(Callable<V> callable);

        void io(Runnable runnable);

        <V> Future<V> net(Callable<V> callable);

        void net(Runnable runnable);

        Looper singleLooper(String str);

        void ui(Runnable runnable);
    }

    public interface L {
        void d(String str, String str2);

        void e(String str, String str2);

        void e(String str, String str2, Throwable th);

        void i(String str, String str2);

        void v(String str, String str2);

        void w(String str, String str2);
    }

    /* access modifiers changed from: protected */
    public abstract void initAdapter();

    public void setL(L log) {
        this.mL = log;
    }

    public L Log() {
        return this.mL;
    }

    public void setExecutor(Executor executor) {
        this.mExecutor = executor;
    }

    public Executor Executor() {
        return this.mExecutor;
    }

    public AdapterFactory() {
        initAdapter();
    }
}
