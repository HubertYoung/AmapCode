package com.autonavi.minimap.ajx3.modules.falcon;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModuleFaceRecognizer extends AbstractModule {
    public abstract String getMetaInfo();

    public abstract void verify(String str, String str2, JsFunctionCallback jsFunctionCallback);

    public AbstractModuleFaceRecognizer(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
