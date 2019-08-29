package com.autonavi.bundle.setting;

import com.autonavi.bundle.setting.ajx.ModuleLaboratory;
import com.autonavi.bundle.setting.ajx.ModuleSetting;
import com.autonavi.minimap.ajx3.Ajx;

public class SettingVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleLaboratory.class);
        Ajx.getInstance().registerModule(ModuleSetting.class);
    }
}
