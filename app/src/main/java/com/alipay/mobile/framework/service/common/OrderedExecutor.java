package com.alipay.mobile.framework.service.common;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderedExecutor<K> {
    /* access modifiers changed from: private */
    public final Executor a;
    private final Map<K, Task> b = new HashMap();

    class Task implements Runnable {
        private final Lock a = new ReentrantLock();
        private final Queue<Runnable> b = new LinkedList();

        Task() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void add(Runnable runnable) {
            this.a.lock();
            try {
                boolean runTask = this.b.isEmpty();
                this.b.offer(runnable);
                if (runTask) {
                    OrderedExecutor.this.a.execute(this);
                }
            } finally {
                this.a.unlock();
            }
        }

        public void run() {
            this.a.lock();
            try {
                Runnable runnable = this.b.peek();
                if (runnable != null) {
                    try {
                        runnable.run();
                        this.a.lock();
                        try {
                            this.b.poll();
                            if (!this.b.isEmpty()) {
                                OrderedExecutor.this.a.execute(this);
                            }
                            return;
                        } catch (Throwable ex) {
                            LoggerFactory.getTraceLogger().error("OrderedExecutor", "task run finally", ex);
                            return;
                        } finally {
                            this.a.unlock();
                        }
                    } catch (Throwable ex2) {
                        try {
                            LoggerFactory.getTraceLogger().error("OrderedExecutor", "task run finally", ex2);
                        } finally {
                            this.a.unlock();
                        }
                    }
                } else {
                    return;
                }
                throw th;
            } finally {
                this.a.unlock();
            }
        }
    }

    public OrderedExecutor(Executor executor) {
        this.a = executor;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public synchronized void submit(K key, Runnable runnable) {
        Task task = this.b.get(key);
        if (task == null) {
            task = new Task();
            this.b.put(key, task);
        }
        task.add(runnable);
    }

    public Executor getExecutor() {
        return this.a;
    }
}
