package com.autonavi.bundle.carownerservice;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.carownerservice.ajx.ModuleCarOwner;
import com.autonavi.minimap.ajx3.Ajx;

public class CarOwnerServiceVApp extends esh {
    private anp a = new anp() {
        public final void onUserInfoUpdate(ant ant) {
        }

        public final void onLoginStateChanged(boolean z, boolean z2) {
            if (z && !z2) {
                AMapAppGlobal.getApplication();
                bsm.e().d();
            }
        }
    };

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleCarOwner.class);
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(this.a);
        }
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.b(this.a);
        }
    }
}
