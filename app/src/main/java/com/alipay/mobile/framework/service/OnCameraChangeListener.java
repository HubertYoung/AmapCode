package com.alipay.mobile.framework.service;

import com.alipay.mobile.map.model.LatLonPoint;

public interface OnCameraChangeListener {
    void onCameraChange(LatLonPoint latLonPoint);

    void onCameraChangeFinish(LatLonPoint latLonPoint);
}
