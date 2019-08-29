package com.autonavi.bundle.uitemplate;

import com.autonavi.bundle.uitemplate.ajx.ModuleImmersive;
import com.autonavi.bundle.uitemplate.ajx.ModuleMapWidget;
import com.autonavi.bundle.uitemplate.ajx.ModuleSlideContainer;
import com.autonavi.minimap.ajx3.Ajx;

public class UITemplateVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleSlideContainer.class);
        Ajx.getInstance().registerModule(ModuleImmersive.class);
        Ajx.getInstance().registerModule(ModuleMapWidget.class);
    }
}
