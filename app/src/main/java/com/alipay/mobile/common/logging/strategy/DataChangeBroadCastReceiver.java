package com.alipay.mobile.common.logging.strategy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class DataChangeBroadCastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        LoggerFactory.getTraceLogger().info("DataChangeBroadCastReceiver", "onReceive");
        LogStrategyManager.getInstance().refreshHitState();
    }
}
