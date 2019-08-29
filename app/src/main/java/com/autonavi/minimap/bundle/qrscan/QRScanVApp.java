package com.autonavi.minimap.bundle.qrscan;

import com.autonavi.annotation.VirtualApp;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.bundle.qrscan.ajx.ModuleQRScan;

@VirtualApp(priority = 100)
public class QRScanVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        Ajx.getInstance().registerModule(ModuleQRScan.class);
    }
}
