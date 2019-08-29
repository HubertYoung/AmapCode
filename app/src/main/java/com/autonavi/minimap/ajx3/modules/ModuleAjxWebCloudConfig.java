package com.autonavi.minimap.ajx3.modules;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager;

@AjxModule("ajxCloudConfig")
public class ModuleAjxWebCloudConfig extends AbstractModule {
    public static final String MODULE_NAME = "ajxCloudConfig";

    public ModuleAjxWebCloudConfig(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "getConfigItemsByModuleName")
    public String getConfigItemsByModuleName(String str) {
        return Ajx3UpgradeManager.getInstance().getConfigItemsByModuleName(str);
    }
}
