package com.autonavi.minimap.ajx3;

import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

public class Ajx3Application extends esh {
    private boolean needStartService = true;

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        if (this.needStartService && AMapPageUtil.isHomePage()) {
            tryStartFireworksService();
        }
    }

    public void vAppEnterForeground() {
        super.vAppEnterForeground();
        if (this.needStartService && AMapPageUtil.isHomePage()) {
            tryStartFireworksService();
        }
    }

    private void tryStartFireworksService() {
        if (AjxInit.startFireworksService(getApplicationContext())) {
            this.needStartService = false;
        }
    }
}
