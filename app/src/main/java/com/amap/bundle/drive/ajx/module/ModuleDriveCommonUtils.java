package com.amap.bundle.drive.ajx.module;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@AjxModule("drive_utils")
@Keep
@KeepClassMembers
public class ModuleDriveCommonUtils extends AbstractModule {
    public static final String MODULE_NAME = "drive_utils";
    private static final String TAG = "ModuleDriveCommonUtils";
    private ModuleDriveCommonUtilsImpl mModuleDriveCommonUtilsImpl = new ModuleDriveCommonUtilsImpl();

    public ModuleDriveCommonUtils(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
