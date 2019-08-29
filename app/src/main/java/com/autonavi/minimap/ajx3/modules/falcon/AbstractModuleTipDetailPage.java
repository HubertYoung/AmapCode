package com.autonavi.minimap.ajx3.modules.falcon;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModuleTipDetailPage extends AbstractModule {
    public abstract void notifyNative(String str, JsFunctionCallback jsFunctionCallback);

    public AbstractModuleTipDetailPage(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
