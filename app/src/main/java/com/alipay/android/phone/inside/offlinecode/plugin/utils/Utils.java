package com.alipay.android.phone.inside.offlinecode.plugin.utils;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;

public class Utils {
    static final String LOGIN_SERVICE_GET_USERINFO = "LOGIN_USERINFO_SERVICE";

    public static String getLoginId() throws Exception {
        Bundle bundle = (Bundle) ServiceExecutor.b(LOGIN_SERVICE_GET_USERINFO, null);
        boolean z = bundle.getBoolean("isLogin");
        String string = bundle.getString("loginId");
        return (!z || TextUtils.isEmpty(string)) ? "" : string;
    }
}
