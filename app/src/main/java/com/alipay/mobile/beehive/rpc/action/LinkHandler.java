package com.alipay.mobile.beehive.rpc.action;

import android.text.TextUtils;
import com.alipay.mobile.beehive.rpc.RpcUiProcessor;
import com.alipay.mobile.beehive.rpc.model.FollowAction;
import com.alipay.mobile.beehive.util.JumpUtil;
import java.util.Map;

public class LinkHandler {
    public static void run(RpcUiProcessor rr, FollowAction action) {
        Map extInfo = action.extInfo;
        if (extInfo != null) {
            String schema = extInfo.get(ActionConstant.SCHEMA);
            if (!TextUtils.isEmpty(schema)) {
                JumpUtil.processSchema(schema);
            }
        }
    }
}
