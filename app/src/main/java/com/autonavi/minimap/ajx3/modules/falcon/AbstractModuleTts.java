package com.autonavi.minimap.ajx3.modules.falcon;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModuleTts extends AbstractModule {
    public abstract void destroy();

    public abstract boolean isPlaying();

    public abstract void play(String str, JsFunctionCallback jsFunctionCallback);

    public abstract void setConfig(String str);

    public abstract void stop();

    public abstract void stopAll();

    public AbstractModuleTts(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
