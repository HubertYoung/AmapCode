package com.autonavi.minimap.life.order.viewpoint;

import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.common.Callback;
import com.autonavi.minimap.life.common.net.opt.LifeNetRequestCallback;

public class ViewPointUIController$1 extends LifeNetRequestCallback<dqe> {
    final /* synthetic */ CompatDialog a;
    final /* synthetic */ dqi b;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public ViewPointUIController$1(dqi dqi, Callback callback, CompatDialog compatDialog) {
        // this.b = dqi;
        // this.a = compatDialog;
        super(callback);
    }

    public void finish() {
        if (this.a != null) {
            this.a.dismiss();
        }
    }

    public /* synthetic */ dog newInstance() {
        return new dqe();
    }
}
