package com.autonavi.minimap.ajx3.modules.falcon;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModuleCityInfo extends AbstractModule {
    public abstract void getAllCityInfo(JsFunctionCallback jsFunctionCallback);

    public AbstractModuleCityInfo(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
