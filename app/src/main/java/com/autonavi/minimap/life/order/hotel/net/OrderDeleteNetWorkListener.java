package com.autonavi.minimap.life.order.hotel.net;

import com.autonavi.common.Callback;

public class OrderDeleteNetWorkListener implements Callback<dqe> {
    dpq a;

    public void error(Throwable th, boolean z) {
    }

    public OrderDeleteNetWorkListener(dpq dpq) {
        this.a = dpq;
    }

    public void callback(dqe dqe) {
        this.a.onDeleteFinished(dqe);
    }
}
