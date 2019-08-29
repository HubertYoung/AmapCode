package com.alipay.mobile.common.transportext.biz.util;

import android.content.Context;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;

public class AppContextHelper {
    public static Context getApplicationContext() {
        return ExtTransportEnv.getAppContext();
    }
}
