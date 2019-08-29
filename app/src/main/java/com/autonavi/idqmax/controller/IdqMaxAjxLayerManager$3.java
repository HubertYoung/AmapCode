package com.autonavi.idqmax.controller;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus;
import com.autonavi.common.Callback;
import com.autonavi.minimap.ajx3.modules.ModuleAMap;
import com.autonavi.minimap.ajx3.views.AmapAjxView;

public class IdqMaxAjxLayerManager$3 implements Callback<AmapAjxView> {
    final /* synthetic */ bqf a;

    public IdqMaxAjxLayerManager$3(bqf bqf) {
        this.a = bqf;
    }

    public void callback(AmapAjxView amapAjxView) {
        if (amapAjxView == this.a.a) {
            ModuleAMap moduleAMap = (ModuleAMap) amapAjxView.getJsModule(ModuleAMap.MODULE_NAME);
            this.a.b = (ModuleIdqPlus) this.a.a.getJsModule(ModuleIdqPlus.MODULE_NAME);
            if (moduleAMap != null) {
                moduleAMap.setAMapSuspendView(this.a.g);
            }
            if (this.a.b != null) {
                if (this.a.c != null) {
                    this.a.b.setOnStateChangeListener(this.a.c);
                    this.a.c = null;
                }
                if (this.a.d != null) {
                    this.a.b.setOverlayEventListener(this.a.d);
                    this.a.d = null;
                }
                this.a.b.setOnAjxCallbackReadyListener(this.a.l);
            }
            this.a.a(this.a.e.c);
            this.a.e.c = null;
        }
    }

    public void error(Throwable th, boolean z) {
        ToastHelper.showToast("load error");
    }
}
