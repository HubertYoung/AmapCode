package com.alipay.mobile.quinox.asynctask;

import java.util.concurrent.Executor;

public class TransactionPipeline extends StandardPipeline {
    public TransactionPipeline(String str, Executor executor) {
        super(str, executor);
    }

    @Deprecated
    public int next() {
        if (this.mTasks != null) {
            return this.mTasks.size();
        }
        return 0;
    }

    public int nextTransaction() {
        return super.next();
    }
}
