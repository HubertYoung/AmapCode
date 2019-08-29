package com.autonavi.bundle.buscard;

import com.autonavi.bundle.buscard.module.ModuleBusCard;
import com.autonavi.minimap.ajx3.Ajx;

public class BusCardVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleBusCard.class);
    }
}
