package com.amap.bundle;

import com.amap.bundle.openlayer.ajx.ModuleOpenLayer;
import com.autonavi.annotation.VirtualApp;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.ajx3.Ajx;

@VirtualApp(priority = 100)
public class MapBaseVAPP extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleOpenLayer.class);
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            abn.a(mapView);
        }
    }
}
