package com.alipay.android.nebulaapp;

import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.bundle.account.api.IAccountService;

public class MiniAppDataUtil {
    public static final String EXT_CLIENT_ID_KEY = "extdeviceid";

    public static String getAmapId() {
        return NetworkParam.getAdiu();
    }

    public static String getTaobaoId() {
        return NetworkParam.getTaobaoID();
    }

    public static String getAlipayId() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.u;
    }
}
