package com.alipay.mobile.framework.service;

import com.alipay.mobile.map.model.LatLonPoint;

public interface OnLocateListener {
    void onLocateFail();

    void onLocateSuccess(LatLonPoint latLonPoint);
}
