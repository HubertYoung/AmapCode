package com.ant.phone.xmedia.config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.alipay.android.phone.falcon.util.log.LogUtil;

/* compiled from: ConfigManager */
final class b extends BroadcastReceiver {
    final /* synthetic */ ConfigManager a;

    b(ConfigManager this$0) {
        this.a = this$0;
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent != null && "com.alipay.mobile.client.CONFIG_CHANGE".equalsIgnoreCase(intent.getAction())) {
            LogUtil.logInfo("ConfigManager", "ConfigChangeReceiver has changed");
            ConfigManager.a().a(true);
        }
    }
}
