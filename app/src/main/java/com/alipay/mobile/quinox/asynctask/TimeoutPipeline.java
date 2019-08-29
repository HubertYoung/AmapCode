package com.alipay.mobile.quinox.asynctask;

import com.alipay.mobile.quinox.log.Log;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class TimeoutPipeline extends StandardPipeline {
    private AsyncTaskExecutor mAsyncTaskExecutor;
    protected long mTimeout;
    private TimeoutTask mTimeoutTask;
    /* access modifiers changed from: private */
    public TimerTask mTimeoutTimerTask;

    class TimeoutTask implements Runnable {
        PipelineRunnable mTargetTask;

        TimeoutTask() {
        }

        /* access modifiers changed from: 0000 */
        public void setTargetTask(PipelineRunnable pipelineRunnable) {
            this.mTargetTask = pipelineRunnable;
        }

        public void run() {
            synchronized (TimeoutPipeline.class) {
                if (TimeoutPipeline.this.mActive == this.mTargetTask) {
                    if (TimeoutPipeline.this.mActive != null) {
                        TimeoutPipeline.this.mActive.setPipeLine(null);
                    }
                    TimeoutPipeline.this.mTimeoutTimerTask = null;
                    StringBuilder sb = new StringBuilder("time out : [");
                    sb.append(this.mTargetTask.mThreadName);
                    sb.append("], force to call Pipeline.next()");
                    Log.w((String) "AsyTskExecutor", sb.toString());
                    TimeoutPipeline.this.next();
                }
            }
        }
    }

    public TimeoutPipeline(String str) {
        this(str, null);
    }

    public TimeoutPipeline(String str, Executor executor) {
        this(str, executor, TimeUnit.SECONDS.toMillis(1));
    }

    public TimeoutPipeline(String str, Executor executor, long j) {
        super(str, executor);
        this.mAsyncTaskExecutor = AsyncTaskExecutor.getInstance();
        this.mTimeoutTask = new TimeoutTask();
        this.mTimeout = j;
    }

    public void setTimeout(long j) {
        this.mTimeout = j;
    }

    /* access modifiers changed from: protected */
    public void execute(PipelineRunnable pipelineRunnable) {
        if (this.mTimeout > 0) {
            synchronized (TimeoutPipeline.class) {
                if (this.mTimeoutTimerTask != null) {
                    this.mTimeoutTimerTask.cancel();
                }
                this.mTimeoutTask.setTargetTask(pipelineRunnable);
                this.mTimeoutTimerTask = this.mAsyncTaskExecutor.scheduleTimer(this.mTimeoutTask, pipelineRunnable.mThreadName, this.mTimeout);
            }
        }
        super.execute(pipelineRunnable);
    }
}
