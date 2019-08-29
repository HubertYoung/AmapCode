package com.alipay.mobile.common.task.pipeline;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class StandardPipeline implements PipeLine {
    static final String TAG = "StandardPipeline";
    private Executor a;
    volatile NamedRunnable mActive;
    protected volatile boolean mIsStart;
    protected ArrayList<NamedRunnable> mTasks;
    final IScheduleNext next;

    interface IScheduleNext {
        public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

        void scheduleNext();
    }

    public StandardPipeline() {
        this(null);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public StandardPipeline(Executor executor) {
        this.mTasks = new ArrayList<>();
        this.next = new IScheduleNext() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void scheduleNext() {
                com.alipay.mobile.common.task.Log.v(StandardPipeline.TAG, "StandardPipeline.scheduleNext()");
                if (StandardPipeline.this.mIsStart) {
                    StandardPipeline.this.a();
                }
            }
        };
        this.mIsStart = false;
        this.a = executor;
    }

    public void setExecutor(Executor executor) {
        this.a = executor;
    }

    public void addTask(Runnable task, String threadName) {
        addTask(task, threadName, 0);
    }

    public void addTask(Runnable task, String threadName, int weight) {
        addTask(NamedRunnable.TASK_POOL.obtain(task, threadName, weight));
    }

    public void addTask(NamedRunnable task) {
        com.alipay.mobile.common.task.Log.v(TAG, "StandardPipeline.addTask()");
        if (this.mTasks == null) {
            throw new RuntimeException("The StandardPipeline has already stopped.");
        }
        task.setScheduleNext(this.next);
        synchronized (this.mTasks) {
            int index = 0;
            if (!this.mTasks.isEmpty()) {
                index = this.mTasks.size() - 1;
                while (true) {
                    if (index < 0) {
                        break;
                    } else if (task.mWeight <= this.mTasks.get(index).mWeight) {
                        index++;
                        break;
                    } else {
                        index--;
                    }
                }
                if (index < 0) {
                    index = 0;
                }
            }
            this.mTasks.add(index, task);
        }
        if (this.mIsStart) {
            doStart();
        }
    }

    public void start() {
        com.alipay.mobile.common.task.Log.v(TAG, "StandardPipeline.start()");
        if (this.a == null) {
            throw new RuntimeException("StandardPipeline start failed : The StandardPipeline's Execturo is null.");
        }
        this.mIsStart = true;
        doStart();
    }

    /* access modifiers changed from: protected */
    public void doStart() {
        if (this.mActive == null) {
            a();
        } else {
            com.alipay.mobile.common.task.Log.v(TAG, "StandardPipeline.start(a task is running, so don't call scheduleNext())");
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        NamedRunnable active;
        synchronized (this.mTasks) {
            if (!this.mTasks.isEmpty()) {
                this.mActive = this.mTasks.remove(0);
            } else {
                this.mActive = null;
                com.alipay.mobile.common.task.Log.v(TAG, "mTasks is empty.");
            }
            active = this.mActive;
        }
        if (active != null) {
            com.alipay.mobile.common.task.Log.d(TAG, "StandardPipeline.scheduleNext()");
            if (this.a != null) {
                this.a.execute(active);
                return;
            }
            throw new RuntimeException("The StandardPipeline's Executor is null.");
        }
        com.alipay.mobile.common.task.Log.d(TAG, "StandardPipeline.scheduleNext(mTasks is empty)");
    }

    public void stop() {
        this.mIsStart = false;
    }
}
