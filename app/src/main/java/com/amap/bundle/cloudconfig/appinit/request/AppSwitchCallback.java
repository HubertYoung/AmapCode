package com.amap.bundle.cloudconfig.appinit.request;

import de.greenrobot.event.EventBus;

public class AppSwitchCallback extends BaseAppInitAndSwitchCallback {
    public void callback(String str) {
        LogFormat("AppSwitchCallback callback: %s", str);
        if (lt.a().b(str, false)) {
            handleResponser();
        }
    }

    public void error(Throwable th, boolean z) {
        LogFormat("AppSwitchCallback error", th, new Object[0]);
    }

    private void handleResponser() {
        LogFormat("AppSwitchCallback. handleResponser start.", new Object[0]);
        a();
        handleTaxiSwitch();
        LogFormat("AppSwitchCallback. handleResponser end.", new Object[0]);
    }

    private void handleTaxiSwitch() {
        EventBus.getDefault().post(lt.a().d.k);
    }
}
