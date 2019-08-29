package com.autonavi.minimap.route.foot;

import com.autonavi.bundle.footresult.ajx.ModuleFoot;
import com.autonavi.minimap.ajx3.Ajx;

public class FootResultVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleFoot.class);
    }
}
