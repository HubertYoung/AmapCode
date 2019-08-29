package com.alipay.mobile.beehive.rpc.action;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.beehive.rpc.RpcUiProcessor;
import com.alipay.mobile.beehive.rpc.model.FollowAction;
import com.alipay.mobile.beehive.util.DebugUtil;
import com.alipay.mobile.beehive.util.JumpUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.util.Map;

public class CertifyHandler {
    public static void run(final DefaultActionProcessor p, final RpcUiProcessor uiProcessor, final FollowAction action) {
        Map extInfo = action.extInfo;
        if (extInfo != null) {
            String schema = extInfo.get(ActionConstant.SCHEMA);
            if (!TextUtils.isEmpty(schema)) {
                IntentFilter filter = new IntentFilter("com.alipay.security.namecertified");
                LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).registerReceiver(new BroadcastReceiver() {
                    public final void onReceive(Context context, Intent intent) {
                        DebugUtil.log((String) RpcConstant.TAG, (String) "certify broad cast received=");
                        LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).unregisterReceiver(this);
                        Bundle b2 = intent.getExtras();
                        if (!b2.getBoolean("isProcessFinished")) {
                            p.runTriggerActions(uiProcessor, action, "cancel");
                            return;
                        }
                        if (b2.getBoolean("isCertified")) {
                            p.runTriggerActions(uiProcessor, action, ActionConstant.TRIGGER_TYPE_CERTIFY_SUCCESS);
                        } else {
                            p.runTriggerActions(uiProcessor, action, ActionConstant.TRIGGER_TYPE_CERTIFY_FAIL);
                        }
                        String isRealName = b2.getString("isRealNamed");
                        if (TextUtils.equals(isRealName, "REALNAME")) {
                            p.runTriggerActions(uiProcessor, action, ActionConstant.TRIGGER_TYPE_REALNAME_SUCCESS);
                        } else if (TextUtils.equals(isRealName, "NOT_REALNAMED") || TextUtils.equals(isRealName, "REALNAME_CANCELED")) {
                            p.runTriggerActions(uiProcessor, action, ActionConstant.TRIGGER_TYPE_REALNAME_FAIL);
                        }
                    }
                }, filter);
                JumpUtil.processSchema(schema);
            }
        }
    }
}
