package com.alipay.mobile.common.logging.http;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.strategy.LogLengthConfig;

public class ConfigChangeBroadCastReceiver extends BroadcastReceiver {
    private static final String TAG = "ConfigChangeBroadCastReceiver";

    public void onReceive(Context context, Intent intent) {
        LoggerFactory.getTraceLogger().info(TAG, "onReceive resetUploadUrl");
        UploadUrlConfig.a().b();
        LogLengthConfig.a().c();
    }
}
