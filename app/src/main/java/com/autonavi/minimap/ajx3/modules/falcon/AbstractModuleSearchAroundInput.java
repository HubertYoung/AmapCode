package com.autonavi.minimap.ajx3.modules.falcon;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModuleSearchAroundInput extends AbstractModule {
    public abstract void keywordSearch(String str, String str2);

    public abstract void onTipItemClick(String str, boolean z, String str2);

    public AbstractModuleSearchAroundInput(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
