package com.alipay.mobile.framework.pipeline;

public abstract class BlockablePipeline implements Pipeline {
    public abstract void doStart();

    public abstract String getName();

    public final void start() {
        BlockablePipelineInvoker.enqueuePipeline(this);
    }
}
