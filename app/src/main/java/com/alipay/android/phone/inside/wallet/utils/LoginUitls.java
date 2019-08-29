package com.alipay.android.phone.inside.wallet.utils;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;

public class LoginUitls {
    public static String getLoginId(boolean z) throws Exception {
        Bundle bundle = (Bundle) ServiceExecutor.b("LOGIN_USERINFO_SERVICE", null);
        return (!z || bundle.getBoolean("isLogin")) ? bundle.getString("loginId") : "";
    }
}
