package com.amap.bundle.splashscreen;

import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import org.json.JSONObject;

public class SplashscreenVApp extends esh {
    private a a = new a() {
        public final void a(JSONObject jSONObject) {
            ahm.a(new Runnable() {
                public final void run() {
                    if (FunctionSupportConfiger.getInst().splashScreenSource() == 0 && ((jo) ank.a(jo.class)) != null) {
                        lt.a();
                    }
                }
            });
        }
    };

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        lt.a().a(this.a);
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        lt.a().b(this.a);
    }
}
