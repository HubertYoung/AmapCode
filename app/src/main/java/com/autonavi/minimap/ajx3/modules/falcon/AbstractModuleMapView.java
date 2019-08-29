package com.autonavi.minimap.ajx3.modules.falcon;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModuleMapView extends AbstractModule {
    public abstract void setZoomCenterType(String str);

    public abstract void snapshot(JsFunctionCallback jsFunctionCallback);

    public AbstractModuleMapView(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
