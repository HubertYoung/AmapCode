package com.alipay.android.phone.mobilesdk.permission.b;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: StandardPipeline */
public class i implements e {
    protected final ArrayList<f> a = new ArrayList<>();
    protected f b;
    protected volatile boolean c = false;
    protected Executor d;
    protected Runnable e;
    protected final String f;

    public i(String name, Executor executor) {
        if (name == null || name.length() == 0) {
            this.f = "StandardPipeline";
        } else {
            this.f = name;
        }
        this.d = executor;
    }

    public final void a(Runnable callback) {
        this.e = callback;
    }

    public final void a(Runnable task, String threadName) {
        b(task, threadName);
    }

    private void b(Runnable task, String threadName) {
        a(f.a.a(task, threadName));
    }

    /* access modifiers changed from: 0000 */
    public final void a(f task) {
        if (this.a == null) {
            throw new RuntimeException("The StandardPipeline has already stopped.");
        }
        task.a(this);
        synchronized (this.a) {
            int index = 0;
            if (!this.a.isEmpty()) {
                index = this.a.size() - 1;
                while (true) {
                    if (index < 0) {
                        break;
                    } else if (task.a() <= this.a.get(index).a()) {
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
            this.a.add(index, task);
        }
        if (this.c) {
            d();
        }
    }

    public final int b() {
        if (this.d == null) {
            throw new RuntimeException("StandardPipeline start failed : The StandardPipeline's Executor is null.");
        }
        LoggerFactory.getTraceLogger().info("AsyTskExecutor", "Pipeline: [" + this.f + "].start()");
        this.c = true;
        return d();
    }

    private int d() {
        if (this.b == null) {
            return a();
        }
        return 0;
    }

    public int a() {
        int size = 0;
        if (this.a != null) {
            size = this.a.size();
            synchronized (this.a) {
                if (!this.a.isEmpty()) {
                    this.b = this.a.remove(0);
                } else {
                    this.b = null;
                }
            }
            if (this.b != null) {
                b(this.b);
            } else if (this.e != null) {
                try {
                    this.e.run();
                } catch (Throwable e2) {
                    LoggerFactory.getTraceLogger().warn((String) "AsyTskExecutor", e2);
                }
            }
        }
        return size;
    }

    private void b(f runnable) {
        if (this.d != null) {
            this.d.execute(runnable);
            return;
        }
        throw new RuntimeException("The StandardPipeline's Executor is null.");
    }

    public final int c() {
        LoggerFactory.getTraceLogger().info("AsyTskExecutor", "Pipeline: [" + this.f + "].stop()");
        this.c = false;
        if (this.a == null) {
            return 0;
        }
        int size = this.a.size();
        this.a.clear();
        return size;
    }
}
