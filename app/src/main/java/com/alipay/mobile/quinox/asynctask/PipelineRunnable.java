package com.alipay.mobile.quinox.asynctask;

import com.alipay.mobile.quinox.log.Log;
import com.alipay.mobile.quinox.utils.Pool.Poolable;
import java.util.concurrent.atomic.AtomicInteger;

class PipelineRunnable implements Poolable, Runnable {
    protected static final AtomicInteger COUNTER = new AtomicInteger(0);
    static final String TAG = "AsyTskExecutor";
    public static final PipelineRunnablePool TASK_POOL = new PipelineRunnablePool(8, 24);
    protected final String mID;
    protected Pipeline mPipeLine;
    protected Runnable mTask;
    protected String mThreadName;
    protected int mWeight = 0;

    PipelineRunnable() {
        StringBuilder sb = new StringBuilder("Transaction_");
        sb.append(COUNTER.getAndIncrement());
        this.mID = sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public void init(Runnable runnable, String str, int i) {
        this.mTask = runnable;
        this.mThreadName = str;
        this.mWeight = i;
    }

    public final String getId() {
        return this.mID;
    }

    public final int getWeight() {
        return this.mWeight;
    }

    public void setPipeLine(Pipeline pipeline) {
        this.mPipeLine = pipeline;
    }

    public void reset() {
        Log.i((String) "AsyTskExecutor", (String) "reset");
        init(null, null, 0);
        this.mPipeLine = null;
    }

    public void run() {
        String str;
        String name = Thread.currentThread().getName();
        if (this.mThreadName == null || this.mThreadName.length() <= 0) {
            str = name;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(this.mThreadName);
            str = sb.toString();
            Thread.currentThread().setName(str);
        }
        long currentTimeMillis = System.currentTimeMillis();
        Pipeline pipeline = this.mPipeLine;
        String str2 = null;
        if (pipeline != null && (pipeline instanceof StandardPipeline)) {
            str2 = ((StandardPipeline) pipeline).mName;
        }
        try {
            if (this.mTask != null) {
                this.mTask.run();
            } else {
                Log.e((String) "AsyTskExecutor", (String) "mTask is null");
            }
        } finally {
            long currentTimeMillis2 = System.currentTimeMillis();
            r8 = "AsyTskExecutor";
            r10 = "[";
            StringBuilder sb2 = new StringBuilder(r10);
            sb2.append(str);
            r1 = "] start at ";
            sb2.append(r1);
            sb2.append(System.currentTimeMillis());
            r1 = ", pipelineName=";
            sb2.append(r1);
            sb2.append(str2);
            r1 = ", cost=";
            sb2.append(r1);
            sb2.append(currentTimeMillis2 - currentTimeMillis);
            r1 = " ms, task=";
            sb2.append(r1);
            sb2.append(this.mTask);
            Log.d(r8, sb2.toString());
            if (this.mThreadName != null && this.mThreadName.length() > 0) {
                Thread.currentThread().setName(name);
            }
            if (this.mPipeLine != null) {
                this.mPipeLine.next();
            }
            TASK_POOL.free(this);
        }
    }
}
