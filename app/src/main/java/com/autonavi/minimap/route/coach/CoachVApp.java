package com.autonavi.minimap.route.coach;

import com.autonavi.bundle.coach.ajx.ModuleCoach;
import com.autonavi.minimap.ajx3.Ajx;

public class CoachVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleCoach.class);
    }
}
