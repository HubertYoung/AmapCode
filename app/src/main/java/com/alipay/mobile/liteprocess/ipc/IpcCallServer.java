package com.alipay.mobile.liteprocess.ipc;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.alipay.mobile.common.ipc.api.ServiceBeanManager;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.LiteProcessServerManager;
import com.alipay.mobile.liteprocess.Util;

public class IpcCallServer extends IpcService {
    public void onCreate() {
        super.onCreate();
        LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcCallServer onCreate");
        Util.setContext(getApplicationContext());
        if (Util.needSupportLiteProcess()) {
            if (!Util.isMainProcess()) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, "IpcCallServer must be in main process. " + Log.getStackTraceString(new Throwable()));
                return;
            }
            try {
                LiteProcessServerManager.g().registerCallServerBean();
                LiteIpcCallManager.a().a(getApplicationContext());
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, t);
            }
        }
    }

    public IBinder onBind(Intent intent) {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcCallServer onBind");
        return LiteIpcCallManager.a().b();
    }

    public static void registerServiceBean(String interfaceClassName, Object objImpl) {
        try {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "IpcCallServer registerServiceBean interfaceClassName " + interfaceClassName);
            ServiceBeanManager serviceBeanManager = LiteIpcCallManager.a().d();
            if (serviceBeanManager != null) {
                serviceBeanManager.register(interfaceClassName, objImpl);
            }
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(e));
        }
    }
}
