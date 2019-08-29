package com.autonavi.minimap.ajx3.modules.falcon;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModuleShare extends AbstractModule {
    public abstract void openShareUI(String str, JsFunctionCallback jsFunctionCallback);

    public AbstractModuleShare(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
