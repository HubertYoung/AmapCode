package com.autonavi.minimap.ajx3.modules.log;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModuleLog extends AbstractModule {
    public abstract void debug(String str, String str2, String str3);

    public abstract void error(String str, String str2, String str3);

    public abstract void fatal(String str, String str2, String str3);

    public abstract void info(String str, String str2, String str3);

    public abstract void performance(String str, String str2, String str3);

    public abstract void playback(long j, int i, int i2, String str);

    public abstract void playbackFeedback(String str);

    public abstract void trace(String str, String str2, String str3);

    public abstract void warning(String str, String str2, String str3);

    public AbstractModuleLog(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
