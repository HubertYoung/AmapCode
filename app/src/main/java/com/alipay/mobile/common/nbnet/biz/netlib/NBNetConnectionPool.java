package com.alipay.mobile.common.nbnet.biz.netlib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.NBNetCommonUtil;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class NBNetConnectionPool {
    private static NBNetConnectionPool d;
    private LinkedList<NBNetConnection> a = new LinkedList<>();
    /* access modifiers changed from: private */
    public final ExecutorService b = new ThreadPoolExecutor(0, 1, 4, TimeUnit.SECONDS, new LinkedBlockingQueue(), NBNetCommonUtil.c("NBNet_ConnectionPool"));
    /* access modifiers changed from: private */
    public ConnGCCallable c = new ConnGCCallable();
    private ConnGCListener e = new ConnGCListener();
    private ReadLock f = null;
    private WriteLock g = null;

    class ConnGCCallable implements Callable<Void> {
        ConnGCCallable() {
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public Void call() {
            NBNetConnectionPool.this.b();
            return null;
        }
    }

    class ConnGCListener extends BroadcastReceiver {
        ConnGCListener() {
        }

        public final void a() {
            try {
                NBNetEnvUtils.a().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND");
                intentFilter.addAction("com.alipay.mobile.framework.USERLEAVEHINT");
                LocalBroadcastManager.getInstance(NBNetEnvUtils.a()).registerReceiver(this, intentFilter);
                IntentFilter scrIntentFilter = new IntentFilter();
                scrIntentFilter.addAction("android.intent.action.SCREEN_OFF");
                scrIntentFilter.addAction("android.intent.action.SCREEN_ON");
                NBNetEnvUtils.a().registerReceiver(this, scrIntentFilter);
                NBNetLogCat.a((String) "NBNetConnectionPool", (String) "ConnGCListener#register finish.");
            } catch (Throwable e) {
                NBNetLogCat.a("NBNetConnectionPool", "ConnGCListener#register fail", e);
            }
        }

        public void onReceive(Context context, Intent intent) {
            NBNetLogCat.a((String) "Monitor", "onReceive:" + getClass().getSimpleName());
            NBNetConnectionPool.this.b.submit(NBNetConnectionPool.this.c);
        }
    }

    public static final NBNetConnectionPool a() {
        if (d != null) {
            return d;
        }
        synchronized (NBNetConnectionPool.class) {
            try {
                if (d != null) {
                    NBNetConnectionPool nBNetConnectionPool = d;
                    return nBNetConnectionPool;
                }
                d = new NBNetConnectionPool();
                return d;
            }
        }
    }

    private NBNetConnectionPool() {
        ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();
        this.f = rrwl.readLock();
        this.g = rrwl.writeLock();
        this.e.a();
    }

    public final void a(NBNetConnection nbNetConnection) {
        this.b.submit(this.c);
        if (nbNetConnection.a()) {
            this.g.lock();
            try {
                this.a.add(nbNetConnection);
            } finally {
                this.g.unlock();
            }
        }
    }

    public final NBNetConnection a(NBNetRoute pNBNetRoute) {
        NBNetConnection b2;
        if (this.a.isEmpty()) {
            return null;
        }
        this.f.lock();
        try {
            synchronized (pNBNetRoute.a()) {
                b2 = b(pNBNetRoute);
            }
            return b2;
        } finally {
            this.f.unlock();
        }
    }

    public final void b(NBNetConnection nbNetConnection) {
        if (!nbNetConnection.a()) {
            NBNetCommonUtil.a((Closeable) nbNetConnection);
        }
        NBNetPlatform.b(nbNetConnection.i());
        nbNetConnection.a(true);
        try {
            nbNetConnection.a(0);
        } catch (Throwable e2) {
            NBNetCommonUtil.a((Closeable) nbNetConnection);
            NBNetLogCat.d("NBNetConnectionPool", "recycle. updateReadTimeout exception: " + e2.toString());
        }
        this.b.submit(this.c);
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public void b() {
        List<NBNetConnection> expireNbNetConnections = new ArrayList<>(this.a.size());
        this.g.lock();
        try {
            int gcBeforeSize = this.a.size();
            ListIterator i = this.a.listIterator();
            while (i.hasNext()) {
                NBNetConnection nbNetConnection = (NBNetConnection) i.next();
                if (!nbNetConnection.a() || nbNetConnection.b()) {
                    i.remove();
                    expireNbNetConnections.add(nbNetConnection);
                }
            }
            this.g.unlock();
            for (NBNetConnection a2 : expireNbNetConnections) {
                NBNetCommonUtil.a((Closeable) a2);
            }
            expireNbNetConnections.clear();
            int gcAfterSize = this.a.size();
            if (gcAfterSize != gcBeforeSize) {
                NBNetLogCat.a((String) "NBNetConnectionPool", "ConnGCCallable. gcBeforeSize=" + gcBeforeSize + ", gcAfterSize=" + gcAfterSize);
            }
        } catch (Throwable th) {
            this.g.unlock();
            throw th;
        }
    }

    private NBNetConnection b(NBNetRoute pNBNetRoute) {
        boolean z = false;
        if (pNBNetRoute == null) {
            return null;
        }
        NBNetConnection foundNBNetConnection = null;
        ListIterator i = this.a.listIterator(this.a.size());
        while (i.hasPrevious()) {
            NBNetConnection nbNetConnection = i.previous();
            if (nbNetConnection.h().a(pNBNetRoute) && nbNetConnection.a() && !nbNetConnection.b() && nbNetConnection.c()) {
                foundNBNetConnection = nbNetConnection;
            }
        }
        if (foundNBNetConnection != null) {
            foundNBNetConnection.a(false);
            NBNetPlatform.a(foundNBNetConnection.i());
            NBNetRoute nbNetRoute = foundNBNetConnection.h();
            StringBuilder append = new StringBuilder("getConnection. hostName=").append(nbNetRoute.a()).append(", ip=").append(foundNBNetConnection.j()).append(", port=").append(nbNetRoute.b()).append(", ssl_model=");
            if (nbNetRoute.c() != null) {
                z = true;
            }
            NBNetLogCat.a((String) "NBNetConnectionPool", append.append(z).toString());
        }
        this.b.submit(this.c);
        return foundNBNetConnection;
    }
}
