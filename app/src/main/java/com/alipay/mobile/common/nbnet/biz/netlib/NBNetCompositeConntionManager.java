package com.alipay.mobile.common.nbnet.biz.netlib;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.NBNetCommonUtil;
import com.alipay.mobile.common.nbnet.biz.util.NBNetConfigUtil;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NBNetCompositeConntionManager extends NBNetBasicConntionManager implements NBNetConntionCallBack {
    private static NBNetConntionManager a;
    private NBNetCompositeConntionEngine b;
    private ThreadPoolExecutor c = null;
    private ThreadPoolExecutor d = null;

    class DirectConnRunnable implements Runnable {
        private NBNetRoute b;
        private NBNetContext c;
        private NBNetConntionCallBack d;

        public DirectConnRunnable(NBNetRoute nbNetRoute, NBNetContext nbNetContext, NBNetConntionCallBack callBack) {
            this.b = nbNetRoute;
            this.c = nbNetContext;
            this.d = callBack;
        }

        public void run() {
            try {
                NBNetConnection newConnect = NBNetDefaultConntionEngine.a().a(this.b, this.c);
                NBNetConnectionWrapper nbNetConnectionWrapper = new NBNetConnectionWrapper();
                nbNetConnectionWrapper.b = 0;
                nbNetConnectionWrapper.a = newConnect;
                nbNetConnectionWrapper.c = this.c;
                this.d.a(nbNetConnectionWrapper);
                NBNetLogCat.a((String) "NBNetConntionManager", (String) "DirectConnRunnable#run callback finish.");
            } catch (Throwable e) {
                NBNetLogCat.b("NBNetConntionManager", "DirectConnRunnable#run", e);
            }
        }
    }

    public static final NBNetConntionManager a() {
        if (a != null) {
            return a;
        }
        synchronized (NBNetConntionManager.class) {
            if (a != null) {
                NBNetConntionManager nBNetConntionManager = a;
                return nBNetConntionManager;
            }
            a = new NBNetCompositeConntionManager();
            return a;
        }
    }

    public final NBNetConnectionEntity a(NBNetReqConn nbNetReqConn, NBNetContext nbNetContext) {
        long startWaitConnTime = System.currentTimeMillis();
        NBNetRoute nbNetRoute = b(nbNetReqConn);
        NBNetConnectionEntity oldConnectionEntity = a(nbNetReqConn, nbNetContext, nbNetRoute);
        if (oldConnectionEntity != null) {
            return oldConnectionEntity;
        }
        if (nbNetRoute.c() != null) {
            NBNetDefaultConntionManager.a();
            NBNetConnectionEntity nbNetConnectionEntity = NBNetDefaultConntionManager.a(nbNetRoute, nbNetContext);
            a(nbNetContext, startWaitConnTime, nbNetConnectionEntity.c().k(), "direct", false);
            return nbNetConnectionEntity;
        }
        NBNetConnectionTask nbNetConnectionTask = new NBNetConnectionTask(nbNetRoute);
        NBNetConnTaskRegister.a().a(nbNetConnectionTask);
        NBNetConnectionEntity directNetConnectionEntity = b(nbNetContext, startWaitConnTime, nbNetRoute, nbNetConnectionTask);
        if (directNetConnectionEntity != null) {
            return directNetConnectionEntity;
        }
        return a(nbNetContext, startWaitConnTime, nbNetRoute, nbNetConnectionTask);
    }

    @NonNull
    private NBNetConnectionEntity a(NBNetContext nbNetContext, long startWaitConnTime, NBNetRoute nbNetRoute, NBNetConnectionTask nbNetConnectionTask) {
        b().a(nbNetRoute, nbNetContext);
        NBNetConnectionWrapper nbNetConnectionWrapper = null;
        try {
            NBNetLogCat.a((String) "NBNetConntionManager", (String) "Start wait connection.");
            nbNetConnectionWrapper = (NBNetConnectionWrapper) nbNetConnectionTask.get((long) (NBNetConfigUtil.a() * 2), TimeUnit.MILLISECONDS);
            NBNetLogCat.a((String) "NBNetConntionManager", (String) "connection finish.");
            if (nbNetConnectionWrapper.d == null) {
                if (nbNetConnectionWrapper.c != null) {
                    nbNetConnectionWrapper.c.copyOverTo(nbNetContext);
                }
                String connMethod = "comp";
                if (nbNetConnectionWrapper.b == 0) {
                    connMethod = "direct";
                }
                a(nbNetContext, startWaitConnTime, nbNetConnectionWrapper.a.k(), connMethod, true);
                NBNetConnectionEntity nBNetConnectionEntity = new NBNetConnectionEntity(nbNetConnectionWrapper.a, nbNetContext);
                try {
                    Future directConnRunnableFuture1 = nbNetConnectionTask.b();
                    if (directConnRunnableFuture1 != null && !directConnRunnableFuture1.isDone()) {
                        directConnRunnableFuture1.cancel(true);
                    }
                } catch (Throwable e) {
                    NBNetLogCat.d("NBNetConntionManager", "directConnRunnableFuture cacel fail. " + e.toString());
                }
                NBNetConnTaskRegister.a().b(nbNetConnectionTask);
                a(nbNetRoute.a());
                return nBNetConnectionEntity;
            } else if (nbNetConnectionWrapper.d instanceof IOException) {
                throw ((IOException) nbNetConnectionWrapper.d);
            } else {
                throw nbNetConnectionWrapper.d;
            }
        } catch (Throwable th) {
            try {
                Future directConnRunnableFuture12 = nbNetConnectionTask.b();
                if (directConnRunnableFuture12 != null && !directConnRunnableFuture12.isDone()) {
                    directConnRunnableFuture12.cancel(true);
                }
            } catch (Throwable e2) {
                NBNetLogCat.d("NBNetConntionManager", "directConnRunnableFuture cacel fail. " + e2.toString());
            }
            NBNetConnTaskRegister.a().b(nbNetConnectionTask);
            a(nbNetRoute.a());
            throw th;
        }
    }

    @Nullable
    private NBNetConnectionEntity b(NBNetContext nbNetContext, long startWaitConnTime, NBNetRoute nbNetRoute, NBNetConnectionTask nbNetConnectionTask) {
        nbNetConnectionTask.a(d().submit(new DirectConnRunnable(nbNetRoute, nbNetContext, this)));
        try {
            NBNetConnectionWrapper nbNetConnectionWrapper = (NBNetConnectionWrapper) nbNetConnectionTask.get(3, TimeUnit.SECONDS);
            a(nbNetContext, startWaitConnTime, nbNetConnectionWrapper.a.k(), "direct", false);
            return new NBNetConnectionEntity(nbNetConnectionWrapper.a, nbNetContext);
        } catch (InterruptedException e) {
            NBNetLogCat.b("NBNetConntionManager", "directConnectionHandler interruptedException", e);
            InterruptedIOException interruptedIOException = new InterruptedIOException("Wait connection interrupted exception");
            interruptedIOException.initCause(e);
            throw interruptedIOException;
        } catch (Throwable e2) {
            NBNetLogCat.d("NBNetConntionManager", "directConnectionHandler timeout, will activate the compound.  errmsg: " + e2.toString());
            return null;
        }
    }

    public final void a(final NBNetConnectionWrapper nbNetConnectionWrapper) {
        NBNetLogCat.a((String) "NBNetConntionManager", (String) "callback start");
        c().execute(new Runnable() {
            public void run() {
                try {
                    NBNetCompositeConntionManager.this.b(nbNetConnectionWrapper);
                } catch (Throwable e) {
                    NBNetLogCat.b("NBNetConntionManager", "callback execute fail.", e);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void b(NBNetConnectionWrapper nbNetConnectionWrapper) {
        NBNetConnection newConnect = nbNetConnectionWrapper.a;
        String uriHost = nbNetConnectionWrapper.a.h().a();
        NBNetConnectionTask connTask = NBNetConnTaskRegister.a().a(uriHost);
        if (connTask != null) {
            try {
                newConnect.a(NBNetworkUtil.b());
                newConnect.a(false);
                connTask.set(nbNetConnectionWrapper);
                NBNetConnTaskRegister.a().b(connTask);
            } catch (Throwable e) {
                NBNetLogCat.b((String) "NBNetConntionManager", e);
                return;
            }
        } else {
            a(uriHost);
        }
        NBNetConnectionPool.a().a(newConnect);
        NBNetLogCat.a((String) "NBNetConntionManager", "requestConnection. new connection, connected time : " + newConnect.g());
    }

    private void a(String uriHost) {
        if (!TextUtils.isEmpty(uriHost) && NBNetConnTaskRegister.a().a(uriHost) == null) {
            b().a(uriHost);
            NBNetLogCat.a((String) "NBNetConntionManager", (String) "stopByHostName");
        }
    }

    private NBNetCompositeConntionEngine b() {
        if (this.b != null) {
            return this.b;
        }
        synchronized (this) {
            if (this.b != null) {
                NBNetCompositeConntionEngine nBNetCompositeConntionEngine = this.b;
                return nBNetCompositeConntionEngine;
            }
            this.b = (NBNetCompositeConntionEngine) NBNetCompositeConntionEngine.a();
            this.b.a((NBNetConntionCallBack) this);
            return this.b;
        }
    }

    private ThreadPoolExecutor c() {
        if (this.c != null) {
            return this.c;
        }
        synchronized (this) {
            if (this.c != null) {
                ThreadPoolExecutor threadPoolExecutor = this.c;
                return threadPoolExecutor;
            }
            this.c = new ThreadPoolExecutor(1, 1, 3, TimeUnit.SECONDS, new LinkedBlockingQueue());
            this.c.setThreadFactory(NBNetCommonUtil.a((String) "compConnCallback", this.c));
            this.c.allowCoreThreadTimeOut(true);
            ThreadPoolExecutor threadPoolExecutor2 = this.c;
            return threadPoolExecutor2;
        }
    }

    private ThreadPoolExecutor d() {
        if (this.d != null) {
            return this.d;
        }
        synchronized (this) {
            if (this.d != null) {
                ThreadPoolExecutor threadPoolExecutor = this.d;
                return threadPoolExecutor;
            }
            this.d = new ThreadPoolExecutor(20, 20, 3, TimeUnit.SECONDS, new LinkedBlockingQueue());
            this.d.setThreadFactory(NBNetCommonUtil.a((String) "directConn", this.c));
            this.d.allowCoreThreadTimeOut(true);
            ThreadPoolExecutor threadPoolExecutor2 = this.d;
            return threadPoolExecutor2;
        }
    }
}
