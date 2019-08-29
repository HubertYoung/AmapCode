package com.autonavi.minimap.ajx3.modules.falcon;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModuleUseridentifier extends AbstractModule {
    public abstract String getAdiu();

    public abstract String getCifa();

    public abstract String getPushToken();

    public abstract String getTid();

    public AbstractModuleUseridentifier(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
