package com.alipay.mobile.framework.service;

import com.alipay.mobile.common.lbs.LBSLocation;

public interface OnLBSLocationListener {
    void onLocationFailed(int i);

    void onLocationUpdate(LBSLocation lBSLocation);
}
