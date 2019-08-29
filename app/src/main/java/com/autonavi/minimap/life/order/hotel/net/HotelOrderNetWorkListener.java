package com.autonavi.minimap.life.order.hotel.net;

import com.autonavi.common.Callback;

public class HotelOrderNetWorkListener implements Callback<dpp> {
    public static final String HOTEL_ORDER_LIST_RESPONSER = "LIST";
    private dpq mListener;

    public HotelOrderNetWorkListener(dpq dpq) {
        this.mListener = dpq;
    }

    public void callback(dpp dpp) {
        if (dpp == null) {
            this.mListener.onError();
            return;
        }
        if (HOTEL_ORDER_LIST_RESPONSER.equals(dpp.k)) {
            this.mListener.onOrderListNetDataFinished(dpp);
        }
    }

    public void error(Throwable th, boolean z) {
        this.mListener.onError();
    }
}
