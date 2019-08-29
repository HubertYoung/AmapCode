package com.alipay.mobile.liteprocess.ipc;

import android.app.Service;
import android.content.Intent;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.Const;

public abstract class IpcService extends Service {
    public int onStartCommand(Intent intent, int flags, int startId) {
        LoggerFactory.getTraceLogger().info(Const.TAG, getClass().getSimpleName() + " onStartCommand received start id " + startId + ": " + intent);
        return 2;
    }

    public boolean onUnbind(Intent intent) {
        LoggerFactory.getTraceLogger().debug(Const.TAG, getClass().getSimpleName() + " onUnbind");
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent) {
        LoggerFactory.getTraceLogger().debug(Const.TAG, getClass().getSimpleName() + " onRebind");
        super.onRebind(intent);
    }

    public void onDestroy() {
        LoggerFactory.getTraceLogger().debug(Const.TAG, getClass().getSimpleName() + " onDestroy");
        super.onDestroy();
    }
}
