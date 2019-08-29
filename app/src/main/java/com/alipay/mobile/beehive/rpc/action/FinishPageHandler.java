package com.alipay.mobile.beehive.rpc.action;

import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.beehive.rpc.RpcUiProcessor;
import com.alipay.mobile.beehive.rpc.model.FollowAction;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class FinishPageHandler {
    public static void run(RpcUiProcessor rr, FollowAction action) {
        try {
            if (rr.getActivity() != null) {
                rr.getActivity().finish();
            }
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
        }
    }
}
