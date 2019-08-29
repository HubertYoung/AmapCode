package com.alipay.mobile.common.lbs;

public interface LBSLocationListener {
    void onLocationFailed(int i);

    void onLocationUpdate(LBSLocation lBSLocation);
}
