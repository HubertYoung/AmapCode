package com.alipay.mobile.android.verify.sdk;

import com.alipay.mobile.android.verify.sdk.interfaces.IService;

public class ServiceFactory {
    public static IService build() {
        return new c();
    }
}
