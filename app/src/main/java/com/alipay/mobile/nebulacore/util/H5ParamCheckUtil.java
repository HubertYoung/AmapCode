package com.alipay.mobile.nebulacore.util;

import android.os.Bundle;
import com.alipay.mobile.nebula.provider.H5StartParamCheck;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;

public class H5ParamCheckUtil {
    public static final String TAG = "H5ParamCheckUtil";

    public static void checkParams(Bundle bundle) {
        if (!H5Utils.getBoolean(bundle, (String) "isTinyApp", false)) {
            H5StartParamCheck h5StartParamCheck = (H5StartParamCheck) Nebula.getProviderManager().getProvider(H5StartParamCheck.class.getName());
            if (h5StartParamCheck != null) {
                h5StartParamCheck.checkParams(bundle);
            }
        }
    }
}
