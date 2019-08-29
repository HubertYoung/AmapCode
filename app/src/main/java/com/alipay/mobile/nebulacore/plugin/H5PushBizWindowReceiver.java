package com.alipay.mobile.nebulacore.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.pushbiz.H5PushBizPlugin;
import com.alipay.mobile.nebulacore.pushbiz.H5PushBizUtil;

public class H5PushBizWindowReceiver extends BroadcastReceiver {
    private H5BridgeContext a;

    public H5PushBizWindowReceiver(H5BridgeContext h5BridgeContext) {
        this.a = h5BridgeContext;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null && H5PushBizUtil.getH5BridgeContext() != null) {
            H5Log.d("H5PushBizWindowReceiver", "intent " + intent.getExtras());
            if (H5PushBizPlugin.pushBizWindowAction.equals(intent.getAction())) {
                H5PushBizUtil.setH5BridgeContext(null);
                LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, H5PushBizPlugin.pushBizAppId, null);
                if (this.a != null) {
                    this.a.sendBridgeResult(H5Utils.toJSONObject(intent.getExtras()));
                }
                try {
                    LocalBroadcastManager.getInstance(H5Utils.getContext()).unregisterReceiver(this);
                } catch (Exception e) {
                    H5Log.e((String) "H5PushBizWindowReceiver", (Throwable) e);
                }
            }
        }
    }
}
