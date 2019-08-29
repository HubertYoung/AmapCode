package com.autonavi.minimap.ajx3.modules.falcon;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModuleAos extends AbstractModule {
    public abstract String getHost(String str);

    public AbstractModuleAos(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
