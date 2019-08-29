package com.alipay.mobile.beehive.rpc.action;

import com.alipay.mobile.beehive.rpc.RpcUiProcessor;

public interface ActionProcessor {
    boolean handleFollowAction(RpcUiProcessor rpcUiProcessor, Object obj, String str);
}
