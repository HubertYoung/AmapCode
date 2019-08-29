package com.alipay.mobile.common.nbnet.biz.netlib;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class NBNetConnectionTask extends FutureTask<NBNetConnectionWrapper> {
    private NBNetRoute a;
    private Future<?> b;

    class VoidCallable implements Callable<NBNetConnectionWrapper> {
        VoidCallable() {
        }

        public /* bridge */ /* synthetic */ Object call() {
            return null;
        }
    }

    public NBNetConnectionTask() {
        super(new VoidCallable());
    }

    public NBNetConnectionTask(NBNetRoute nbNetRoute) {
        this();
        this.a = nbNetRoute;
    }

    /* renamed from: a */
    public final void set(NBNetConnectionWrapper nbNetConnection) {
        super.set(nbNetConnection);
    }

    public void setException(Throwable t) {
        super.setException(t);
    }

    public final NBNetRoute a() {
        return this.a;
    }

    public final void a(Future<?> directConnRunnableFuture) {
        this.b = directConnRunnableFuture;
    }

    public final Future<?> b() {
        return this.b;
    }
}
