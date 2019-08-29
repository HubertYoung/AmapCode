package com.autonavi.minimap.life.order.hotel.presenter;

import android.app.Dialog;
import com.autonavi.common.Callback;
import com.autonavi.minimap.life.common.net.opt.LifeNetRequestCallback;

public class OrderHotelListPresenter$2 extends LifeNetRequestCallback<dqc> {
    final /* synthetic */ int a;
    final /* synthetic */ Dialog b;
    final /* synthetic */ dqg c;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public OrderHotelListPresenter$2(dqg dqg, Callback callback, int i, Dialog dialog) {
        // this.c = dqg;
        // this.a = i;
        // this.b = dialog;
        super(callback);
    }

    public void finish() {
        if (this.b != null) {
            this.b.dismiss();
        }
    }

    public /* synthetic */ dog newInstance() {
        return new dqc(4098, this.a);
    }
}
