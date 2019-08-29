package com.autonavi.minimap.ajx3.debug;

import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.util.LogHelper;

public class DevToolLog {
    public static void log(String str) {
        LogHelper.jniLogForce(Constants.TAG_DEVTOOLS.concat(String.valueOf(str)));
        LogManager.jsPrintLog(0, str, "devtool");
    }
}
