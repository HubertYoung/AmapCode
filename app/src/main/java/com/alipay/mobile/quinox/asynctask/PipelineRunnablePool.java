package com.alipay.mobile.quinox.asynctask;

import com.alipay.mobile.quinox.utils.Pool;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

final class PipelineRunnablePool extends Pool<PipelineRunnable> {
    private final AtomicInteger mIndex = new AtomicInteger(1);

    public PipelineRunnablePool(int i, int i2) {
        super(i, i2);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public final PipelineRunnable newObject() {
        throw new RuntimeException("call newObject(Runnable, String) method instead.");
    }

    @Deprecated
    public final PipelineRunnable obtain() {
        throw new RuntimeException("call obtain(Runnable, String, int) method instead.");
    }

    public final synchronized PipelineRunnable obtain(Runnable runnable, String str) {
        try {
        }
        return obtain(runnable, str, 0, false);
    }

    public final synchronized PipelineRunnable obtain(Runnable runnable, String str, int i) {
        try {
        }
        return obtain(runnable, str, i, false);
    }

    public final synchronized PipelineRunnable obtain(Runnable runnable, String str, boolean z) {
        try {
        }
        return obtain(runnable, str, 0, z);
    }

    public final synchronized PipelineRunnable obtain(Runnable runnable, String str, int i, boolean z) {
        PipelineRunnable pipelineRunnable;
        if (z) {
            try {
                runnable = new PausableRunnable(runnable);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.freeObjects.size() == 0) {
            String valueOf = String.valueOf(this.mIndex.getAndIncrement());
            if (str == null || str.length() <= 0) {
                str = valueOf;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(valueOf);
                sb.append("_");
                sb.append(str);
                str = sb.toString();
            }
            pipelineRunnable = new PipelineRunnable();
        } else {
            pipelineRunnable = (PipelineRunnable) this.freeObjects.pop();
        }
        pipelineRunnable.init(runnable, str, i);
        return pipelineRunnable;
    }

    public final synchronized void free(PipelineRunnable pipelineRunnable) {
        super.free(pipelineRunnable);
    }

    public final synchronized void freeAll(List<PipelineRunnable> list) {
        super.freeAll(list);
    }

    public final synchronized void clear() {
        super.clear();
    }
}
