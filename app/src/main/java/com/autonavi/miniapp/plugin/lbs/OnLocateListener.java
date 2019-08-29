package com.autonavi.miniapp.plugin.lbs;

public interface OnLocateListener {
    void onLocateFail();

    void onLocateSuccess(LatLonPoint latLonPoint);
}
