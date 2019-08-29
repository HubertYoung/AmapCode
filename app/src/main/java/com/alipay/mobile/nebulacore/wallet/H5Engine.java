package com.alipay.mobile.nebulacore.wallet;

import com.alipay.mobile.framework.app.IApplicationEngine;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.nebula.util.H5Log;

public class H5Engine implements IApplicationEngine {
    public static final String TAG = "H5Engine";

    public MicroApplication createApplication() {
        H5Log.d(TAG, "createApplication");
        return new H5Application();
    }
}
