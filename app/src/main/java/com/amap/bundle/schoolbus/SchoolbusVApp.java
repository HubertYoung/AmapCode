package com.amap.bundle.schoolbus;

import com.amap.bundle.schoolbus.module.ModuleSchoolbus;
import com.autonavi.annotation.VirtualApp;
import com.autonavi.minimap.ajx3.Ajx;

@VirtualApp(priority = 100)
public class SchoolbusVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleSchoolbus.class);
    }
}
