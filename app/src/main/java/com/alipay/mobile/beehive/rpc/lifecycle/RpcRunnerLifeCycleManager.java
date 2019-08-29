package com.alipay.mobile.beehive.rpc.lifecycle;

import com.alipay.mobile.beehive.rpc.RpcRunnerContext;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RpcRunnerLifeCycleManager {
    private static RpcRunnerLifeCycleManager instance;
    private ConcurrentLinkedQueue<RpcRunnerLifeCycleCallback> callbacks = new ConcurrentLinkedQueue<>();

    public static synchronized RpcRunnerLifeCycleManager getInstance() {
        RpcRunnerLifeCycleManager rpcRunnerLifeCycleManager;
        synchronized (RpcRunnerLifeCycleManager.class) {
            try {
                if (instance == null) {
                    instance = new RpcRunnerLifeCycleManager();
                }
                rpcRunnerLifeCycleManager = instance;
            }
        }
        return rpcRunnerLifeCycleManager;
    }

    private RpcRunnerLifeCycleManager() {
    }

    public void onBeforeRpc(RpcRunnerContext context) {
        Iterator<RpcRunnerLifeCycleCallback> it = this.callbacks.iterator();
        while (it.hasNext()) {
            it.next().onBeforeRpc(context);
        }
    }

    public void onRpcResult(RpcRunnerContext context, Object responseBody) {
        Iterator<RpcRunnerLifeCycleCallback> it = this.callbacks.iterator();
        while (it.hasNext()) {
            it.next().onRpcResult(context, responseBody);
        }
    }

    public void onRpcException(RpcRunnerContext context, Exception ex) {
        Iterator<RpcRunnerLifeCycleCallback> it = this.callbacks.iterator();
        while (it.hasNext()) {
            it.next().onRpcException(context, ex);
        }
    }

    public void onAfterRpc(RpcRunnerContext context) {
        Iterator<RpcRunnerLifeCycleCallback> it = this.callbacks.iterator();
        while (it.hasNext()) {
            it.next().onAfterRpc(context);
        }
    }

    public synchronized void addLifeCycleCallback(RpcRunnerLifeCycleCallback cb) {
        if (cb != null) {
            boolean dup = false;
            Iterator<RpcRunnerLifeCycleCallback> it = this.callbacks.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next() == cb) {
                        dup = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!dup) {
                this.callbacks.add(cb);
            }
        }
    }

    public void removeLifeCycleCallback(RpcRunnerLifeCycleCallback cb) {
        if (cb != null) {
            Iterator i = this.callbacks.iterator();
            while (i.hasNext()) {
                if (i.next() == cb) {
                    i.remove();
                }
            }
        }
    }

    public ConcurrentLinkedQueue<RpcRunnerLifeCycleCallback> getLifeCycelCallbacks() {
        return this.callbacks;
    }
}
