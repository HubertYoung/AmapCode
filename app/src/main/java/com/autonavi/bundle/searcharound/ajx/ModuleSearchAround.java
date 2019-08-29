package com.autonavi.bundle.searcharound.ajx;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("search_around")
@KeepPublicClassMembers
@KeepName
public class ModuleSearchAround extends AbstractModule {
    public static final String MODULE_NAME = "search_around";
    private a mProxy;

    public interface a {
        void a(String str);
    }

    public ModuleSearchAround(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setModuleProxy(a aVar) {
        this.mProxy = aVar;
    }

    @AjxMethod("openInput")
    public void openInput(String str) {
        if (this.mProxy != null) {
            this.mProxy.a(str);
        }
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        this.mProxy = null;
    }
}
