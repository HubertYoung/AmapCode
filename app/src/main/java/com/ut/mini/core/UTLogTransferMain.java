package com.ut.mini.core;

import com.alibaba.analytics.core.LogProcessor;
import com.alibaba.analytics.core.config.UTSampleConfBiz;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorEventDispather;
import com.alibaba.analytics.utils.Logger;
import java.util.Map;

public class UTLogTransferMain {
    private static UTLogTransferMain s_instance = new UTLogTransferMain();
    public SelfMonitorEventDispather mMonitor = new SelfMonitorEventDispather();

    private UTLogTransferMain() {
    }

    public static UTLogTransferMain getInstance() {
        return s_instance;
    }

    public void transferLog(Map<String, String> map) {
        if (map != null) {
            try {
                if (UTSampleConfBiz.getInstance().isSampleSuccess(map)) {
                    LogProcessor.process(map);
                } else {
                    Logger.i((String) "log discard", "aLogMap", map);
                }
            } catch (Throwable th) {
                Logger.e(null, th, new Object[0]);
            }
        }
    }
}
