package com.autonavi.minimap.ajx3.modules.log;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.context.IAjxContext;

public class AjxModuleLog extends AbstractModuleLog {
    public AjxModuleLog(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void fatal(String str, String str2, String str3) {
        AMapLog.fatal(str, str2, str3);
    }

    public void error(String str, String str2, String str3) {
        AMapLog.error(str, str2, str3);
    }

    public void warning(String str, String str2, String str3) {
        AMapLog.warning(str, str2, str3);
    }

    public void info(String str, String str2, String str3) {
        AMapLog.info(str, str2, str3);
    }

    public void debug(String str, String str2, String str3) {
        AMapLog.debug(str, str2, str3);
    }

    public void trace(String str, String str2, String str3) {
        AMapLog.trace(str, str2, str3);
    }

    public void performance(String str, String str2, String str3) {
        AMapLog.performance(str, str2, str3);
    }

    public void playback(long j, int i, int i2, String str) {
        AMapLog.playback(j, i, i2, str);
    }

    public void playbackFeedback(String str) {
        AMapLog.playbackFeedback(str);
    }
}
