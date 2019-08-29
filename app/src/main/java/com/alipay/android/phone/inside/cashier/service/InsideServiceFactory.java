package com.alipay.android.phone.inside.cashier.service;

import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.framework.service.IInsideService;

public class InsideServiceFactory {
    public static IInsideService getInsideServicePay() {
        if (StaticConfig.j()) {
            return new InsideServicePayForAlipay();
        }
        return new InsideServicePayForSdk();
    }

    public static IInsideService getInsideServiceUpCodeConfig() {
        if (StaticConfig.j()) {
            return new InsideServiceUpCodeConfigForAlipay();
        }
        return new InsideServiceUpCodeConfigForSdk();
    }
}
