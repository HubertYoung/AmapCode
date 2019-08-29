package com.alipay.android.phone.mobilecommon.multimediabiz.biz.common;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AccumulationExecutor<C extends Collection, T> {
    public static final int TYPE_EXECUTE_BY_COUNT = 1;
    public static final int TYPE_EXECUTE_BY_EXIT = 2;
    public static final int TYPE_EXECUTE_BY_TIME = 0;
    private static final AtomicInteger a = new AtomicInteger(0);
    private int b;
    private final long c;
    protected C cacheData = null;
    private long d = System.currentTimeMillis();
    private ExecuteHandler e;
    private OnExecuteListener f;
    private boolean g = false;

    private class ExecuteHandler extends Handler {
        public static final int MSG_CHECK = 0;
        public static final int MSG_EXECUTE = 1;
        public static final int MSG_EXIT = 2;
        public static final int MSG_QUIT = 3;

        public ExecuteHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AccumulationExecutor.this.a();
                    return;
                case 1:
                    AccumulationExecutor.this.b(msg.arg1);
                    return;
                case 2:
                    AccumulationExecutor.this.a(2);
                    return;
                case 3:
                    getLooper().quit();
                    return;
                default:
                    return;
            }
        }
    }

    public interface OnExecuteListener<T> {
        void onExecute(List<T> list, int i);
    }

    /* access modifiers changed from: protected */
    public abstract C createCacheData();

    public AccumulationExecutor(int thresholdCount, long thresholdTime, OnExecuteListener<T> listener, Looper looper) {
        this.b = thresholdCount;
        this.c = thresholdTime;
        this.f = listener;
        this.e = new ExecuteHandler<>(looper);
        this.cacheData = createCacheData();
    }

    public AccumulationExecutor(int thresholdCount, long thresholdTime, OnExecuteListener<T> listener) {
        this.b = thresholdCount;
        this.c = thresholdTime;
        this.f = listener;
        HandlerThread handlerThread = new HandlerThread("AccumulationExecutor-" + a.incrementAndGet(), 1);
        handlerThread.setDaemon(false);
        handlerThread.setPriority(1);
        handlerThread.start();
        this.e = new ExecuteHandler<>(handlerThread.getLooper());
        this.cacheData = createCacheData();
    }

    public synchronized void exit() {
        if (!this.g) {
            this.g = true;
            this.e.sendEmptyMessage(2);
        }
    }

    public synchronized void put(T data) {
        b();
        doPut(data);
        this.e.sendEmptyMessage(0);
    }

    /* access modifiers changed from: protected */
    public void doPut(T data) {
        this.cacheData.add(data);
    }

    public synchronized void remove(T data) {
        b();
        doRemove(data);
    }

    /* access modifiers changed from: protected */
    public void doRemove(T data) {
        this.cacheData.remove(data);
    }

    public synchronized List<T> clone() {
        b();
        return new CopyOnWriteArrayList(this.cacheData);
    }

    public synchronized void clear() {
        b();
        this.cacheData.clear();
    }

    /* access modifiers changed from: private */
    public void a(int type) {
        Message msg = Message.obtain();
        msg.what = 1;
        msg.arg1 = type;
        this.e.sendMessage(msg);
    }

    /* access modifiers changed from: private */
    public void a() {
        this.e.removeMessages(0);
        if (this.cacheData.size() >= this.b) {
            a(1);
            this.e.removeMessages(0);
        } else if (System.currentTimeMillis() - this.d >= this.c) {
            a(0);
        } else {
            long delayTime = this.c - (System.currentTimeMillis() - this.d);
            this.e.sendEmptyMessageDelayed(0, delayTime <= 0 ? this.c : Math.min(this.c, delayTime));
        }
    }

    /* access modifiers changed from: private */
    public void b(int executeType) {
        this.d = System.currentTimeMillis();
        List copy = new CopyOnWriteArrayList(this.cacheData);
        this.cacheData.removeAll(copy);
        if (this.f != null) {
            this.f.onExecute(copy, executeType);
        }
        if (2 == executeType) {
            this.e.sendEmptyMessage(3);
        }
    }

    private void b() {
        if (this.g) {
            throw new IllegalStateException("has already exited!!!!");
        }
    }
}
