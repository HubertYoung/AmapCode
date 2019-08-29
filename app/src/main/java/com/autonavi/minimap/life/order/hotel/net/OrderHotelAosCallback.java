package com.autonavi.minimap.life.order.hotel.net;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Callback;
import java.util.ArrayList;

public class OrderHotelAosCallback implements Callback<dqc> {
    private static final String TAG = "OrderHotelAosCallback";
    private dqd mPresenter;

    public OrderHotelAosCallback(dqd dqd) {
        this.mPresenter = dqd;
    }

    public void callback(dqc dqc) {
        if (dqc == null) {
            AMapLog.e(TAG, "responser is null");
            return;
        }
        switch (dqc.i) {
            case 4097:
                if (dqc.c != 1) {
                    this.mPresenter.a(dqc.c);
                    break;
                } else {
                    new dqa();
                    onListUpdate(dqc, dqa.a(dqc.b()));
                    return;
                }
            case 4098:
                if (dqc.c == 1) {
                    new dqb();
                    onListUpdate(dqc, dqb.a(dqc.b()));
                    return;
                }
                this.mPresenter.a(dqc.c);
                return;
            case 8193:
            case 8194:
                this.mPresenter.a(dqc.c, dqc.i);
                return;
        }
    }

    public void error(Throwable th, boolean z) {
        this.mPresenter.a();
    }

    private void onListUpdate(dqc dqc, ArrayList<dpl> arrayList) {
        this.mPresenter.a(dqc.i, arrayList, dqc.k, dqc.j != null ? dqc.j.optInt("total") : 0);
    }
}
