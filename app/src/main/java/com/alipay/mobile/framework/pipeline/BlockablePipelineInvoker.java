package com.alipay.mobile.framework.pipeline;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BlockablePipelineInvoker {
    private static final List<BlockablePipeline> a = new ArrayList();
    private static AtomicBoolean b = new AtomicBoolean(false);

    public static void enqueuePipeline(BlockablePipeline pipeline) {
        if (pipeline != null) {
            synchronized (a) {
                a.add(pipeline);
            }
            a();
        }
    }

    public static void setBlockPipeline(boolean blockPipeline) {
        b.set(blockPipeline);
        if (!b.get()) {
            a();
        }
    }

    private static void a() {
        if (!b.get()) {
            synchronized (a) {
                Iterator iterator = a.iterator();
                while (iterator.hasNext()) {
                    BlockablePipeline pipeline = iterator.next();
                    Log.w("PipelineInvoker", "trigger pipeline " + pipeline.toString());
                    pipeline.doStart();
                    iterator.remove();
                }
            }
        }
    }
}
