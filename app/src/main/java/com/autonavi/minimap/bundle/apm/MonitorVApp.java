package com.autonavi.minimap.bundle.apm;

import android.app.Application;
import android.text.TextUtils;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.amap.app.AMapAppGlobal;
import com.ut.device.UTDevice;
import org.json.JSONObject;

public class MonitorVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        lo.a().a((String) "OnlineMonitor", (lp) new lp() {
            public final void onConfigCallBack(int i) {
            }

            public final void onConfigResultCallBack(int i, String str) {
                if (ahs.a(MonitorVApp.this.getApplicationContext()) && !TextUtils.isEmpty(str) && !cup.a()) {
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        Object opt = jSONObject.opt("android");
                        if (opt != null) {
                            jSONObject = (JSONObject) opt;
                        }
                        if (jSONObject.getBoolean("enableMonitor")) {
                            Application application = AMapAppGlobal.getApplication();
                            a aVar = new a();
                            aVar.c = application;
                            aVar.a = 3;
                            aVar.b = false;
                            aVar.d = "12278902";
                            aVar.e = a.a().a;
                            aVar.j = NetworkParam.getTaobaoID();
                            aVar.k = NetworkParam.getDiv();
                            aVar.i = NetworkParam.getDiu();
                            aVar.h = UTDevice.getUtdid(application);
                            aVar.f = application.getPackageName();
                            aVar.p = cut.a;
                            a.o = "channel";
                            aVar.l = jSONObject;
                            cup.a(aVar);
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        });
    }

    public void vAppEnterBackground() {
        super.vAppEnterBackground();
        cup.b();
    }

    public void vAppEnterForeground() {
        super.vAppEnterForeground();
        cup.c();
    }
}
