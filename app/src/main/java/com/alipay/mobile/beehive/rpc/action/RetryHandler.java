package com.alipay.mobile.beehive.rpc.action;

import com.alipay.mobile.beehive.rpc.RpcUiProcessor;
import com.alipay.mobile.beehive.rpc.model.FollowAction;

public class RetryHandler {
    public static void run(RpcUiProcessor rr, FollowAction action) {
        if (rr.getRetryRunnable() != null) {
            rr.getRetryRunnable().run();
        }
    }
}
