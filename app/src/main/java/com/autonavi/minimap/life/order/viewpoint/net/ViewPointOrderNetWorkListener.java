package com.autonavi.minimap.life.order.viewpoint.net;

import com.autonavi.common.Callback;

public class ViewPointOrderNetWorkListener implements Callback<dpp> {
    private dpq mListener;

    public ViewPointOrderNetWorkListener(dpq dpq) {
        this.mListener = dpq;
    }

    public void error(Throwable th, boolean z) {
        this.mListener.onError();
    }

    public void callback(dpp dpp) {
        if (dpp == null) {
            if (this.mListener != null) {
                this.mListener.onError();
            }
            return;
        }
        if ("VIEWPOINT_ORDER_SEARCH_RESULT".equals(dpp.d())) {
            if (this.mListener != null) {
                this.mListener.onOrderListNetDataFinished(dpp);
            }
        } else if ("VIEWPOINT_ORDER_SEARCH_BY_PHONE_RESULT".equals(dpp.d())) {
            if (this.mListener != null) {
                this.mListener.onOrderListByPhoneNetDataFinished(dpp);
            }
        } else if ("VIEWPOINT_ORDER_SEARCH_RESULT_NEW".equals(dpp.d()) && this.mListener != null) {
            this.mListener.onOrderListNetDataFinishedNew(dpp);
        }
    }
}
