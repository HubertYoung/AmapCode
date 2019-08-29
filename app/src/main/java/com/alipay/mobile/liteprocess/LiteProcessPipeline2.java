package com.alipay.mobile.liteprocess;

import com.alipay.mobile.common.logging.api.LoggerFactory;

public class LiteProcessPipeline2 implements Runnable {
    static boolean a = false;

    public void run() {
        if (Util.needSupportLiteProcess()) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessPipeline2 run at " + Util.getCurrentProcessName());
            if (Util.isMainProcess()) {
                a();
            }
        }
    }

    static void a() {
        if (!Config.NEED_LITE || a) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessPipeline2 cancel run");
            return;
        }
        LiteProcessServerManager.g().startLiteProcessAsync(Config.h);
        a = true;
        LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessPipeline2 run over");
    }
}
