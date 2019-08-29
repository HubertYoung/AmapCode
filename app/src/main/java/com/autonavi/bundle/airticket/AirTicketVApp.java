package com.autonavi.bundle.airticket;

import com.autonavi.bundle.airticket.module.ModuleAirTicket;
import com.autonavi.minimap.ajx3.Ajx;

public class AirTicketVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleAirTicket.class);
    }
}
