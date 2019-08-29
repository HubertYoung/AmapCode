package com.amap.bundle.planhome;

import com.amap.bundle.planhome.ajx.ModuleHome;
import com.amap.bundle.planhome.ajx.ModulePlanHome;
import com.autonavi.minimap.ajx3.Ajx;

public class PlanHomeVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleHome.class, ModulePlanHome.class);
        a.a.a = acr.a();
    }
}
