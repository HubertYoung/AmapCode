package com.alipay.android.phone.inside.commonbiz.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.commonbiz.login.utils.LoginUtils;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import org.json.JSONObject;

public class LoginExpireCheckService extends AbstractInsideService<Bundle, String> {
    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        JSONObject a = LoginUtils.a();
        if (LoginUtils.d(a)) {
            return "openAuthTokenLogin";
        }
        return LoginUtils.a(a) ? "thirdPartyAppLogin" : "normalLogin";
    }
}
