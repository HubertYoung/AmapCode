package com.ut.mini.extend;

import com.alibaba.analytics.utils.Logger;
import com.ut.mini.internal.LogAdapter;

public class TLogExtend {
    public static void registerTLog() {
        if (UTExtendSwitch.bTlogExtend) {
            Logger.setLogger(new LogAdapter());
        }
    }
}
