package com.autonavi.bundle.amaphome;

import com.autonavi.bundle.amaphome.components.centralcard.ModuleCentralCard;
import com.autonavi.bundle.amaphome.module.ModuleAMapHome;
import com.autonavi.minimap.ajx3.Ajx;

public class AmapHomeVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleCentralCard.class, ModuleAMapHome.class);
    }
}
