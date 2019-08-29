package com.alipay.mobile.scansdk.activity;

import com.alipay.mobile.mascanengine.MaScanResult;

public interface BaseScanRouter {
    boolean routeBarQrCode(MaScanResult maScanResult);
}
