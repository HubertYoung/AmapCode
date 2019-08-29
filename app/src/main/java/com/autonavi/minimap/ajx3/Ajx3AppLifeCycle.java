package com.autonavi.minimap.ajx3;

import com.autonavi.minimap.ajx3.ApplicationLifeCycle.APPLifeCycle;

public class Ajx3AppLifeCycle extends esh {
    private static final String TAG = "Ajx3AppLifeCycle";

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        if (AjxInit.sIsAjxEngineInited) {
            Ajx.getInstance().onAppLifeCycle(APPLifeCycle.APP_MAP_FIRST_RENDERED, "");
        }
    }

    public void vAppEnterForeground() {
        if (AjxInit.sIsAjxEngineInited) {
            Ajx.getInstance().onAppLifeCycle(APPLifeCycle.APP_ENTER_FOREGROUND, "");
        }
    }

    public void vAppEnterBackground() {
        if (AjxInit.sIsAjxEngineInited) {
            Ajx.getInstance().onAppLifeCycle(APPLifeCycle.APP_ENTER_BACKGROUND, "");
        }
    }
}
