package com.autonavi.minimap.life.order.hotel.presenter;

import android.app.Dialog;
import com.autonavi.common.Callback;
import com.autonavi.minimap.life.common.net.opt.LifeNetRequestCallback;

public class OrderHotelListPresenter$4 extends LifeNetRequestCallback<dqc> {
    final /* synthetic */ Dialog a;
    final /* synthetic */ dqg b;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public OrderHotelListPresenter$4(dqg dqg, Callback callback, Dialog dialog) {
        // this.b = dqg;
        // this.a = dialog;
        super(callback);
    }

    public void finish() {
        if (this.a != null) {
            this.a.dismiss();
        }
    }

    public /* synthetic */ dog newInstance() {
        return new dqc(8194);
    }
}
