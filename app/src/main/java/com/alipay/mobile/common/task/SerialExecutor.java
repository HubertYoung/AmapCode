package com.alipay.mobile.common.task;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.task.pipeline.NamedRunnable;
import com.alipay.mobile.common.task.pipeline.StandardPipeline;
import java.util.concurrent.Executor;

class SerialExecutor extends StandardPipeline {
    static final String TAG = "SerialExecutor";

    public SerialExecutor(Executor executor) {
        super(executor);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void execute(NamedRunnable task) {
        Log.v(TAG, "SerialExecutor.execute()");
        addTask(task);
        start();
    }

    public void shutdown() {
        stop();
        this.mTasks.clear();
        this.mTasks = null;
    }
}
