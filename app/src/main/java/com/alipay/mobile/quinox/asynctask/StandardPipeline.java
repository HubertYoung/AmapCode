package com.alipay.mobile.quinox.asynctask;

import com.alipay.mobile.quinox.log.Log;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class StandardPipeline implements Pipeline {
    protected PipelineRunnable mActive;
    protected Executor mExecutor;
    protected Runnable mIdleListener;
    protected volatile boolean mIsStart;
    protected final String mName;
    protected final ArrayList<PipelineRunnable> mTasks;

    public StandardPipeline(String str) {
        this(str, null);
    }

    public StandardPipeline(String str, Executor executor) {
        this.mTasks = new ArrayList<>();
        this.mIsStart = false;
        if (str == null || str.length() == 0) {
            this.mName = "StandardPipeline";
        } else {
            this.mName = str;
        }
        this.mExecutor = executor;
    }

    public void setExecutor(Executor executor) {
        this.mExecutor = executor;
    }

    public void addIdleListener(Runnable runnable) {
        this.mIdleListener = runnable;
    }

    public void addTask(Runnable runnable, String str) {
        addTask(runnable, str, 0);
    }

    public void addTask(Runnable runnable, String str, int i) {
        addTask(PipelineRunnable.TASK_POOL.obtain(runnable, str, i));
    }

    /* access modifiers changed from: 0000 */
    public void addTask(PipelineRunnable pipelineRunnable) {
        if (this.mTasks == null) {
            throw new RuntimeException("The StandardPipeline has already stopped.");
        }
        pipelineRunnable.setPipeLine(this);
        synchronized (this.mTasks) {
            int i = 0;
            if (!this.mTasks.isEmpty()) {
                int size = this.mTasks.size() - 1;
                while (true) {
                    if (size < 0) {
                        break;
                    } else if (pipelineRunnable.getWeight() <= this.mTasks.get(size).getWeight()) {
                        size++;
                        break;
                    } else {
                        size--;
                    }
                }
                if (size >= 0) {
                    i = size;
                }
            }
            this.mTasks.add(i, pipelineRunnable);
        }
        if (this.mIsStart) {
            doStart();
        }
    }

    public int start() {
        if (this.mExecutor == null) {
            throw new RuntimeException("StandardPipeline start failed : The StandardPipeline's Executor is null.");
        }
        StringBuilder sb = new StringBuilder("Pipeline: [");
        sb.append(this.mName);
        sb.append("].start()");
        Log.i((String) "AsyTskExecutor", sb.toString());
        this.mIsStart = true;
        return doStart();
    }

    /* access modifiers changed from: protected */
    public int doStart() {
        if (this.mActive == null) {
            return next();
        }
        return 0;
    }

    public int next() {
        if (this.mTasks == null) {
            return 0;
        }
        int size = this.mTasks.size();
        synchronized (this.mTasks) {
            if (!this.mTasks.isEmpty()) {
                this.mActive = this.mTasks.remove(0);
            } else {
                this.mActive = null;
            }
        }
        if (this.mActive != null) {
            execute(this.mActive);
            return size;
        } else if (this.mIdleListener == null) {
            return size;
        } else {
            try {
                this.mIdleListener.run();
                return size;
            } catch (Throwable th) {
                Log.w((String) "AsyTskExecutor", th);
                return size;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void execute(PipelineRunnable pipelineRunnable) {
        if (this.mExecutor != null) {
            this.mExecutor.execute(pipelineRunnable);
            return;
        }
        throw new RuntimeException("The StandardPipeline's Executor is null.");
    }

    public int stop() {
        StringBuilder sb = new StringBuilder("Pipeline: [");
        sb.append(this.mName);
        sb.append("].stop()");
        Log.i((String) "AsyTskExecutor", sb.toString());
        this.mIsStart = false;
        if (this.mTasks == null) {
            return 0;
        }
        int size = this.mTasks.size();
        this.mTasks.clear();
        return size;
    }
}
