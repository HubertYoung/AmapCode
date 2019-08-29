package com.autonavi.minimap.ajx3.modules;

import android.content.Context;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsContextRef;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public abstract class AbstractModule {
    private final IAjxContext context;

    public AbstractModule(IAjxContext iAjxContext) {
        if (iAjxContext == null) {
            throw new NullPointerException("context in AbstractModule'Constructure should not be null.");
        }
        this.context = iAjxContext;
    }

    public IAjxContext getContext() {
        return this.context;
    }

    public Context getNativeContext() {
        return this.context.getNativeContext();
    }

    public JsContextRef getJsContext() {
        return this.context.getJsContext();
    }

    public void onModuleDestroy() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" onModuleDestroy");
    }
}
