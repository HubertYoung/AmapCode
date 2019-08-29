package com.alipay.mobile.common.task.pipeline;

import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.task.pipeline.Pool.Poolable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedRunnable implements Poolable, Runnable {
    static final String TAG = "AsyncTaskExecutor";
    public static final NamedRunnablePool TASK_POOL = new NamedRunnablePool(8, 16);
    IScheduleNext mScheduleNext;
    Runnable mTask;
    String mThreadName;
    int mWeight = 0;

    public final class NamedRunnablePool extends Pool<NamedRunnable> {
        private final AtomicInteger a = new AtomicInteger(1);

        public NamedRunnablePool(int initialCapacity, int max) {
            super(initialCapacity, max);
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        @Deprecated
        public final NamedRunnable obtain() {
            throw new RuntimeException("call obtain(Runnable, String) method instead.");
        }

        public final synchronized NamedRunnable obtain(Runnable task, String threadName) {
            try {
            }
            return obtain(task, threadName, 0);
        }

        public final synchronized NamedRunnable obtain(Runnable task, String threadName, int weight) {
            NamedRunnable namedRunnable;
            if (this.freeObjects.size() == 0) {
                com.alipay.mobile.common.task.Log.i("AsyncTaskExecutor", "NamedRunnablePool.obtain(): create a new NamedRunnable obj.");
                namedRunnable = newObject(task, threadName, weight);
            } else {
                com.alipay.mobile.common.task.Log.i("AsyncTaskExecutor", "NamedRunnablePool.obtain(): hit a cache NamedRunnable obj.");
                namedRunnable = (NamedRunnable) this.freeObjects.pop();
                namedRunnable.setTask(task);
                namedRunnable.setThreadName(threadName);
                namedRunnable.setWeight(weight);
            }
            return namedRunnable;
        }

        /* access modifiers changed from: protected */
        @Deprecated
        public final NamedRunnable newObject() {
            throw new RuntimeException("call newObject(Runnable, String) method instead.");
        }

        /* access modifiers changed from: 0000 */
        public final NamedRunnable newObject(Runnable task, String threadName, int weight) {
            String threadName2;
            if (TextUtils.isEmpty(threadName)) {
                threadName2 = "NamedRunable_" + this.a.getAndIncrement();
            } else {
                threadName2 = "NamedRunable_" + this.a.getAndIncrement() + "_" + threadName;
            }
            return new NamedRunnable(task, threadName2, weight);
        }

        public final synchronized void free(NamedRunnable object) {
            super.free(object);
        }

        public final synchronized void freeAll(List<NamedRunnable> objects) {
            super.freeAll(objects);
        }

        public final synchronized void clear() {
            super.clear();
        }
    }

    NamedRunnable(Runnable runnable, String threadName, int weight) {
        this.mTask = runnable;
        this.mThreadName = threadName;
        this.mWeight = weight;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public void setThreadName(String threadName) {
        this.mThreadName = threadName;
    }

    /* access modifiers changed from: 0000 */
    public void setTask(Runnable task) {
        this.mTask = task;
    }

    /* access modifiers changed from: 0000 */
    public NamedRunnable setScheduleNext(IScheduleNext scheduleNext) {
        this.mScheduleNext = scheduleNext;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void setWeight(int weight) {
        this.mWeight = weight;
    }

    public void run() {
        String threadName = null;
        if (!TextUtils.isEmpty(this.mThreadName)) {
            threadName = Thread.currentThread().getName();
            com.alipay.mobile.common.task.Log.i("AsyncTaskExecutor", "NamedRunable.run(set ThreadName to:" + this.mThreadName + ")");
            Thread.currentThread().setName(threadName + "_" + this.mThreadName);
        }
        long start = System.currentTimeMillis();
        LoggerFactory.getTraceLogger().verbose("AsyncTaskExecutor", "start at " + start);
        try {
            if (this.mTask != null) {
                this.mTask.run();
            }
        } finally {
            r7 = "AsyncTaskExecutor";
            r9 = "cost ";
            r9 = " ms";
            LoggerFactory.getTraceLogger().info(r7, (System.currentTimeMillis() - start) + r9);
            if (!TextUtils.isEmpty(this.mThreadName)) {
                r6 = "AsyncTaskExecutor";
                r8 = "NamedRunable.run(set ThreadName back to:";
                r8 = ")";
                com.alipay.mobile.common.task.Log.i(r6, threadName + r8);
                if (threadName != null) {
                    Thread.currentThread().setName(threadName);
                }
            }
            if (this.mScheduleNext != null) {
                r6 = "AsyncTaskExecutor";
                r7 = "NamedRunnable.run()->finish(finally:mScheduleNext.scheduleNext())";
                com.alipay.mobile.common.task.Log.v(r6, r7);
                this.mScheduleNext.scheduleNext();
            } else {
                r6 = "AsyncTaskExecutor";
                r7 = "NamedRunnable.run()->finish(finally:null == mScheduleNext)";
                com.alipay.mobile.common.task.Log.v(r6, r7);
            }
            TASK_POOL.free(this);
            r6 = "AsyncTaskExecutor";
            r8 = "NamedRunnable.run()->finish(TASK_POOL.free(this)): pool.size=";
            com.alipay.mobile.common.task.Log.d(r6, TASK_POOL.freeObjects.size());
        }
    }

    public void reset() {
        this.mTask = null;
        this.mThreadName = null;
        this.mScheduleNext = null;
        this.mWeight = 0;
    }
}
