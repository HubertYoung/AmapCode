package com.autonavi.minimap.ajx3.modules.falcon;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModuleImage extends AbstractModule {
    public abstract void compress(String str, String str2, int i, JsFunctionCallback jsFunctionCallback);

    public abstract void extractThumbnail(String str, String str2, int i, int i2, JsFunctionCallback jsFunctionCallback);

    public abstract String getType(String str);

    public AbstractModuleImage(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
