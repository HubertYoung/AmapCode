package com.alipay.mobile.common.rpc.impl;

import com.alipay.mobile.common.rpc.RpcLifeManager;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class RpcLifeManagerImpl implements RpcLifeManager {
    private static RpcLifeManagerImpl instance;
    private static final List<Future<Response>> rpcFutureList = new ArrayList(4);

    public static final RpcLifeManagerImpl getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (RpcLifeManagerImpl.class) {
            try {
                if (instance != null) {
                    RpcLifeManagerImpl rpcLifeManagerImpl = instance;
                    return rpcLifeManagerImpl;
                }
                instance = new RpcLifeManagerImpl();
                return instance;
            }
        }
    }

    public synchronized void cancelAllRpc() {
        if (!rpcFutureList.isEmpty()) {
            for (Future future : rpcFutureList) {
                if (future != null && !future.isDone()) {
                    future.cancel(true);
                }
            }
            LogCatUtil.warn((String) "RpcLifeManager", "Cancel all rpc finish, rpc count: " + rpcFutureList.size());
            rpcFutureList.clear();
        }
    }

    public synchronized void addFuture(Future<Response> rpcFuture) {
        if (rpcFuture != null) {
            try {
                rpcFutureList.add(rpcFuture);
            } catch (Throwable th) {
            }
        }
    }

    public synchronized void removeFuture(Future<Response> future) {
        if (future != null) {
            if (!rpcFutureList.isEmpty()) {
                try {
                    rpcFutureList.remove(future);
                } catch (Throwable th) {
                }
            }
        }
    }
}
