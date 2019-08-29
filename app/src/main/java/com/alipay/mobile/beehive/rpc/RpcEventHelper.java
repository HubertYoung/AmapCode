package com.alipay.mobile.beehive.rpc;

import com.alipay.mobile.beehive.eventbus.EventBusManager;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class RpcEventHelper {
    public static void post(String status, Object result, Exception ex, RpcTask task) {
        RpcEvent eventData = buildRpcEventData(status, result, ex, task);
        if (eventData != null) {
            EventBusManager.getInstance().post(eventData, RpcUtil.getRpcEventName(task));
        }
    }

    public static void postAtFront(String status, Object result, Exception ex, RpcTask task) {
        RpcEvent eventData = buildRpcEventData(status, result, ex, task);
        if (eventData != null) {
            EventBusManager.getInstance().postAtFront(eventData, RpcUtil.getRpcEventName(task));
        }
    }

    public static <T> RpcEvent buildRpcEventData(String status, T result, Exception exception, RpcTask<T> task) {
        try {
            RpcEvent data = new RpcEvent(task, result, exception);
            data.status = status;
            return data;
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) exception);
            return null;
        }
    }
}
