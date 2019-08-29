package com.alipay.android.phone.inside.cashier.service;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.cashier.utils.InsideEnvBuilder;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;

public class InsideEnvService extends AbstractInsideService<Bundle, String> {
    static final String BIZ_BAR_CODE_AUTH = "barCodeAuth";
    static final String BIZ_BUS_CODE_AUTH = "busCodeAuth";
    static final String BIZ_COMMON = "common";
    static final String KEY_BIZ = "biz";

    public String startForResult(Bundle bundle) throws Exception {
        String string = bundle.getString("biz", "");
        if (TextUtils.equals(string, BIZ_BUS_CODE_AUTH)) {
            return new InsideEnvBuilder().getBusCodeAuthEnv();
        }
        if (TextUtils.equals(string, BIZ_BAR_CODE_AUTH)) {
            return new InsideEnvBuilder().getQrcodeAuthEnv();
        }
        if (TextUtils.equals(string, "common")) {
            return new InsideEnvBuilder().getInsideEnv();
        }
        throw new Exception("biz param missed!!");
    }
}
