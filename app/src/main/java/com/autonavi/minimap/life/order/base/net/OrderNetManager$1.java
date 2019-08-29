package com.autonavi.minimap.life.order.base.net;

import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.common.Callback;
import com.autonavi.minimap.life.common.net.opt.LifeNetRequestCallback;

public class OrderNetManager$1 extends LifeNetRequestCallback<dpp> {
    final /* synthetic */ dpm a;
    final /* synthetic */ CompatDialog b;
    final /* synthetic */ dps c;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public OrderNetManager$1(dps dps, Callback callback, dpm dpm, CompatDialog compatDialog) {
        // this.c = dps;
        // this.a = dpm;
        // this.b = compatDialog;
        super(callback);
    }

    public void finish() {
        if (this.b != null) {
            this.b.dismiss();
        }
    }

    public /* bridge */ /* synthetic */ dog newInstance() {
        return this.a;
    }
}
