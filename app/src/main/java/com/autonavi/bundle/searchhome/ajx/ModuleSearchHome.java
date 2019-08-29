package com.autonavi.bundle.searchhome.ajx;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;

@AjxModule("search_home")
public class ModuleSearchHome extends AbstractModule {
    public static final String MODULE_NAME = "search_home";
    private a mModuleSearchHomeProxy;

    public interface a {
        void a(String str);

        void a(String str, boolean z, String str2);

        void a(boolean z);
    }

    public ModuleSearchHome(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("setVuiSuspendBtnVisible")
    public void setVuiSuspendBtnVisible(boolean z) {
        if (this.mModuleSearchHomeProxy != null) {
            this.mModuleSearchHomeProxy.a(z);
        }
    }

    @AjxMethod("goSearch")
    public void goSearch(String str) {
        if (this.mModuleSearchHomeProxy != null) {
            this.mModuleSearchHomeProxy.a(str);
        }
    }

    @AjxMethod("onSuggItemClick")
    public void onSuggItemClick(String str, int i, boolean z, String str2) {
        if (this.mModuleSearchHomeProxy != null) {
            this.mModuleSearchHomeProxy.a(str, z, str2);
        }
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        this.mModuleSearchHomeProxy = null;
    }

    public void setActionCallback(a aVar) {
        this.mModuleSearchHomeProxy = aVar;
    }
}
