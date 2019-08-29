package com.autonavi.minimap.route.sharebike;

import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.sharebike.ajx.ModuleBicycle;
import com.autonavi.minimap.ajx3.Ajx;

public class ShareBikeVApp extends esh {
    private boolean a = false;

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleBicycle.class);
        if (hasPermission()) {
            this.a = true;
        }
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        ahm.a(new Runnable() {
            public final void run() {
                boolean parseBoolean = Boolean.parseBoolean(ehs.b("share_bike_riding_status_id"));
                boolean parseBoolean2 = Boolean.parseBoolean(ehs.b("share_bike_unlocking_status_id"));
                if (parseBoolean || parseBoolean2) {
                    eht.f();
                }
            }
        });
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a((anp) ebe.a());
        }
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        if (this.a) {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                iAccountService.b((anp) ebe.a());
            }
            egj.a().b();
            egb a2 = egb.a();
            ehx ehx = a2.b;
            ehx.f = null;
            ehx.d.removeCallbacksAndMessages(null);
            ehx.c.quit();
            ehz ehz = a2.a;
            ehz.b.removeCallbacksAndMessages(null);
            ehz.a.quit();
            ehz.c = null;
            egb.c = null;
        }
    }
}
