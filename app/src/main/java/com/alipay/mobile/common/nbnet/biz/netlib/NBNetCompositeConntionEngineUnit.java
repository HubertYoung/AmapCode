package com.alipay.mobile.common.nbnet.biz.netlib;

import android.os.Process;
import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.NBNetCommonUtil;
import com.alipay.mobile.common.nbnet.biz.util.NBNetConfigUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NBNetCompositeConntionEngineUnit {
    private String a;
    private List<ScheduledRunnable> b = new ArrayList();
    private ThreadPoolExecutor c = null;
    /* access modifiers changed from: private */
    public byte d = 0;
    /* access modifiers changed from: private */
    public NBNetConntionCallBack e;

    class ConnectionRunnale implements Runnable {
        public ScheduledRunnable a;
        private String c;
        private NBNetConnection d;
        private ScheduledRunnable e;
        private boolean f = false;

        public ConnectionRunnale(String hostName, ScheduledRunnable scheduledRunnable) {
            this.c = hostName;
            this.e = scheduledRunnable;
        }

        public void run() {
            try {
                if (this.f) {
                    NBNetLogCat.a((String) "NBNetCompositeConntionEngineUnit", (String) "ConnectionRunnale#run. closed");
                    NBNetCompositeConntionEngineUnit.this.a(this.e);
                } else if (NBNetCompositeConntionEngineUnit.this.d == 0) {
                    NBNetLogCat.a((String) "NBNetCompositeConntionEngineUnit", (String) "ConnectionRunnale#run. engineState stoped");
                    NBNetCompositeConntionEngineUnit.this.a(this.e);
                } else {
                    NBNetRoute nbNetRoute = NBNetRouteManager.a().a(this.c);
                    if (nbNetRoute == null) {
                        NBNetLogCat.d("NBNetCompositeConntionEngineUnit", "ConnectionRunnale. nbNetRoute is null");
                        NBNetCompositeConntionEngineUnit.this.a(this.e);
                        return;
                    }
                    NBNetContext nbNetContext = new BasicNBNetContext();
                    this.d = new NBNetConnection(nbNetRoute);
                    this.d.a(NBNetConfigUtil.c(), NBNetConfigUtil.b(), nbNetContext);
                    NBNetConnectionWrapper nbNetConnectionWrapper = new NBNetConnectionWrapper();
                    nbNetConnectionWrapper.b = 1;
                    nbNetConnectionWrapper.a = this.d;
                    nbNetConnectionWrapper.c = nbNetContext;
                    NBNetCompositeConntionEngineUnit.this.e.a(nbNetConnectionWrapper);
                    NBNetLogCat.a((String) "NBNetCompositeConntionEngineUnit", (String) "ConnectionRunnale#run callback finish.");
                    NBNetCompositeConntionEngineUnit.this.a(this.e);
                }
            } catch (Throwable e2) {
                try {
                    NBNetLogCat.b("NBNetCompositeConntionEngineUnit", "ConnectionRunnale exception", e2);
                } finally {
                    NBNetCompositeConntionEngineUnit.this.a(this.e);
                }
            }
        }

        public final synchronized void a() {
            this.f = true;
            if (this.d != null && !this.d.a()) {
                try {
                    this.d.close();
                } catch (Throwable e2) {
                    NBNetLogCat.d("NBNetCompositeConntionEngineUnit", "nbNetConnection close fail. " + e2.toString());
                }
            }
            return;
        }
    }

    class ScheduledRunnable implements Runnable {
        public ScheduledFuture a;
        public Future<?> b;
        public ConnectionRunnale c;
        public String d;
        public byte e = 0;
        private boolean g = false;

        ScheduledRunnable(String hostName) {
            this.d = hostName;
        }

        public void run() {
            Thread.currentThread().setPriority(5);
            Process.setThreadPriority(-2);
            if (this.g) {
                NBNetLogCat.a((String) "NBNetCompositeConntionEngineUnit", (String) "ScheduledRunnable#run. closed");
            } else if (NBNetCompositeConntionEngineUnit.this.d == 0) {
                NBNetLogCat.a((String) "NBNetCompositeConntionEngineUnit", (String) "ScheduledRunnable#run. engineState stoped");
            } else {
                this.e = 1;
                this.c = new ConnectionRunnale(this.d, this);
                this.b = NBNetCompositeConntionEngineUnit.this.c().submit(this.c);
                this.c.a = NBNetCompositeConntionEngineUnit.this.a(4, this.d);
            }
        }

        public final void a() {
            this.g = true;
            if (this.c != null) {
                this.c.a();
            }
            if (this.b != null && !this.b.isDone()) {
                try {
                    this.b.cancel(true);
                } catch (Throwable e2) {
                    NBNetLogCat.d("NBNetCompositeConntionEngineUnit", "connFuture cancel fail. " + e2.toString());
                }
            }
            if (this.a != null && !this.a.isDone()) {
                try {
                    this.a.cancel(true);
                } catch (Throwable e3) {
                    NBNetLogCat.d("NBNetCompositeConntionEngineUnit", "scheduledFuture cancel fail. " + e3.toString());
                }
            }
            NBNetCompositeConntionEngineUnit.this.a(this);
        }
    }

    public NBNetCompositeConntionEngineUnit(String hostName, NBNetConntionCallBack nbNetConntionCallBack) {
        if (TextUtils.isEmpty(hostName)) {
            throw new IllegalArgumentException("hostName can't be empty.");
        } else if (nbNetConntionCallBack == null) {
            throw new IllegalArgumentException("nbNetConntionCallBack can't be null.");
        } else {
            this.a = hostName;
            this.e = nbNetConntionCallBack;
        }
    }

    public final void a() {
        if (this.d != 1) {
            synchronized (this) {
                if (this.d != 1) {
                    this.d = 1;
                    a(0, this.a);
                }
            }
        }
    }

    public final void b() {
        if (this.d != 0) {
            synchronized (this) {
                if (this.d != 0) {
                    this.d = 0;
                    if (!this.b.isEmpty()) {
                        int size = this.b.size();
                        for (ScheduledRunnable a2 : (ScheduledRunnable[]) this.b.toArray(new ScheduledRunnable[this.b.size()])) {
                            a2.a();
                        }
                        this.b.clear();
                        NBNetLogCat.a((String) "NBNetCompositeConntionEngineUnit", "setStopState. scheduledRunnableList cleared " + size);
                    }
                    if (this.c != null && !this.c.isShutdown()) {
                        try {
                            List runnables = this.c.shutdownNow();
                            this.c = null;
                            NBNetLogCat.a((String) "NBNetCompositeConntionEngineUnit", "setStopState. threadPoolExecutor shutdownNow. runnables: " + runnables.size());
                        } catch (Throwable e2) {
                            NBNetLogCat.d("NBNetCompositeConntionEngineUnit", "setStopState. threadPoolExecutor shutdown fail. " + e2.toString());
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public ScheduledRunnable a(long delay, String hostName) {
        ScheduledRunnable scheduledRunnable;
        if (this.d == 0) {
            NBNetLogCat.d("NBNetCompositeConntionEngineUnit", "scheduleConnRunnable. engineState is toped");
            return null;
        }
        synchronized (this) {
            scheduledRunnable = new ScheduledRunnable(hostName);
            scheduledRunnable.a = NetworkAsyncTaskExecutor.schedule((Runnable) scheduledRunnable, delay, TimeUnit.SECONDS);
            NBNetLogCat.a((String) "NBNetCompositeConntionEngineUnit", (String) "add scheduledRunnable to list");
            this.b.add(scheduledRunnable);
        }
        return scheduledRunnable;
    }

    /* access modifiers changed from: private */
    public ThreadPoolExecutor c() {
        if (this.c != null && !this.c.isShutdown()) {
            return this.c;
        }
        synchronized (this) {
            if (this.c == null || this.c.isShutdown()) {
                this.c = new ThreadPoolExecutor(10, 10, 4, TimeUnit.SECONDS, new LinkedBlockingQueue());
                this.c.setThreadFactory(NBNetCommonUtil.a((String) "CompConnThread", this.c));
                this.c.allowCoreThreadTimeOut(true);
                ThreadPoolExecutor threadPoolExecutor = this.c;
                return threadPoolExecutor;
            }
            ThreadPoolExecutor threadPoolExecutor2 = this.c;
            return threadPoolExecutor2;
        }
    }

    /* access modifiers changed from: private */
    public void a(ScheduledRunnable scheduledRunnable) {
        synchronized (this) {
            try {
                this.b.remove(scheduledRunnable);
                NBNetLogCat.f("NBNetCompositeConntionEngineUnit", "removeScheduleRunnable hashCode:" + scheduledRunnable.hashCode());
            } catch (Throwable e2) {
                NBNetLogCat.a("NBNetCompositeConntionEngineUnit", " removeScheduleRunnable fail. ", e2);
            }
        }
    }
}
