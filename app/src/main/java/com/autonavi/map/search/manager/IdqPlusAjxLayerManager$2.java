package com.autonavi.map.search.manager;

import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus;
import com.autonavi.common.Callback;
import com.autonavi.minimap.ajx3.modules.ModuleAMap;
import com.autonavi.minimap.ajx3.views.AmapAjxView;

public class IdqPlusAjxLayerManager$2 implements Callback<AmapAjxView> {
    final /* synthetic */ byx a;

    public void error(Throwable th, boolean z) {
    }

    public IdqPlusAjxLayerManager$2(byx byx) {
        this.a = byx;
    }

    public void callback(AmapAjxView amapAjxView) {
        if (amapAjxView == this.a.b) {
            ModuleAMap moduleAMap = (ModuleAMap) amapAjxView.getJsModule(ModuleAMap.MODULE_NAME);
            this.a.c = (ModuleIdqPlus) this.a.b.getJsModule(ModuleIdqPlus.MODULE_NAME);
            if (moduleAMap != null) {
                moduleAMap.setAMapSuspendView(new bxu());
            }
            if (this.a.c != null) {
                if (this.a.d != null) {
                    this.a.c.setOnStateChangeListener(this.a.d);
                    this.a.d = null;
                }
                if (this.a.e != null) {
                    this.a.c.setOverlayEventListener(this.a.e);
                    this.a.e = null;
                }
                this.a.c.setOnAjxCallbackReadyListener(this.a.j);
            }
            this.a.a(this.a.f.c);
            this.a.f.c = null;
            this.a.a = true;
        }
    }
}
