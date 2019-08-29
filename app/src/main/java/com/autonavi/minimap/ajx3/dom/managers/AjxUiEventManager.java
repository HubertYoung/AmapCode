package com.autonavi.minimap.ajx3.dom.managers;

import com.autonavi.minimap.ajx3.context.IAjxContext;

public abstract class AjxUiEventManager {
    public IAjxContext mAjxContext;

    public abstract void destroy();

    public AjxUiEventManager(IAjxContext iAjxContext) {
        this.mAjxContext = iAjxContext;
    }
}
