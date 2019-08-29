package com.autonavi.bundle.uitemplate.ajx;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxField;
import com.autonavi.minimap.ajx3.modules.AjxModule;

@AjxModule("immersive")
public class ModuleImmersive extends AbstractModule {
    public static final String MODULE_NAME = "immersive";
    @AjxField("isStatusBarSupport")
    public boolean isStatusBarSupport = euk.a();

    public ModuleImmersive(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
