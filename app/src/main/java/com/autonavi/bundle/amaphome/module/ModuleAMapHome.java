package com.autonavi.bundle.amaphome.module;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;

@AjxModule("amapHome")
public class ModuleAMapHome extends AbstractModule {
    static final String MODULE_NAME = "amapHome";

    public ModuleAMapHome(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("setGpsOverlayRegionCenter")
    public void setGpsOverlayRegionCenter(boolean z) {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            iMainMapService.a(z);
        }
    }
}
