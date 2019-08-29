package com.alipay.mobile.beehive.rpc.action;

import android.text.TextUtils;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.beehive.rpc.RpcUiProcessor;
import com.alipay.mobile.beehive.rpc.RpcUtil;
import com.alipay.mobile.beehive.rpc.model.FollowAction;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.Map;

public class ToastHandler {
    public static void run(DefaultActionProcessor p, RpcUiProcessor uiProcessor, FollowAction action) {
        Map extInfo = action.extInfo;
        if (extInfo != null) {
            String desc = extInfo.get("desc");
            if (TextUtils.isEmpty(desc)) {
                desc = RpcUtil.getString(uiProcessor, R.string.default_follow_action_desc);
            }
            if (!TextUtils.isEmpty(desc)) {
                LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "toast %s" + desc);
                uiProcessor.toast(desc, 0);
            }
        }
    }
}
