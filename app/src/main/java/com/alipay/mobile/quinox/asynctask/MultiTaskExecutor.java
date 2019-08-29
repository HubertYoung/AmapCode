package com.alipay.mobile.quinox.asynctask;

import com.alipay.mobile.quinox.log.Log;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public class MultiTaskExecutor implements Executor {
    private static final String TAG = "MultiTaskExecutor";
    /* access modifiers changed from: private */
    public final CountDownLatch mCountDownLatch;
    private Executor mExecutor;

    class CountDownTask implements Runnable {
        private final Runnable target;

        CountDownTask(Runnable runnable) {
            if (runnable == null) {
                throw new IllegalArgumentException("null == command");
            }
            this.target = runnable;
        }

        public void run() {
            try {
                this.target.run();
            } catch (Throwable th) {
                MultiTaskExecutor.this.mCountDownLatch.countDown();
                throw th;
            }
            MultiTaskExecutor.this.mCountDownLatch.countDown();
        }
    }

    public MultiTaskExecutor(int i) {
        this(i, AsyncTaskExecutor.getInstance().getExecutor());
    }

    public MultiTaskExecutor(int i, Executor executor) {
        this.mExecutor = executor;
        this.mCountDownLatch = new CountDownLatch(i);
    }

    public void setExecutor(Executor executor) {
        this.mExecutor = executor;
    }

    public void execute(Runnable runnable) {
        if (this.mExecutor == null) {
            throw new IllegalStateException("Must set 'Executor' before call execute(Runnable)");
        }
        this.mExecutor.execute(new CountDownTask(runnable));
    }

    public void await() {
        try {
            this.mCountDownLatch.await();
        } catch (Throwable th) {
            Log.e((String) TAG, th);
        }
    }
}
