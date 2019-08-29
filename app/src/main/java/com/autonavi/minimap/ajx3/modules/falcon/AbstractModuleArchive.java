package com.autonavi.minimap.ajx3.modules.falcon;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModuleArchive extends AbstractModule {
    public abstract void compress(String str, String[] strArr, String str2, JsFunctionCallback jsFunctionCallback);

    public abstract void decompress(String str, String str2, String str3, JsFunctionCallback jsFunctionCallback);

    public AbstractModuleArchive(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
