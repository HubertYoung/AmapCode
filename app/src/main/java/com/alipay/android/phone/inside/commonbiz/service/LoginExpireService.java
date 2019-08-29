package com.alipay.android.phone.inside.commonbiz.service;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.commonbiz.login.utils.LoginProvider;
import com.alipay.android.phone.inside.commonbiz.login.utils.LoginUtils;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class LoginExpireService extends AbstractInsideService<Bundle, Boolean> {
    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        Bundle bundle = (Bundle) obj;
        JSONObject a = LoginUtils.a();
        if (bundle != null && bundle.getBoolean("isNewOpenAuthFlow")) {
            a.put("openMcUid", bundle.getString("openMcUid"));
            a.put("authToken", bundle.getString("authToken"));
            a.put("alipayUserId", bundle.getString("alipayUserId"));
            a.put("isNewOpenAuthFlow", bundle.getBoolean("isNewOpenAuthFlow"));
            a.put("isOpenAuthLogin", true);
            LoggerFactory.f().a((String) "inside-LoginExpireService", "newOpenAuthFlow: ".concat(String.valueOf(a)));
        }
        LoginProvider loginProvider = new LoginProvider();
        getContext();
        String a2 = loginProvider.a(a, true);
        boolean equals = TextUtils.equals("success", a2);
        if (equals) {
            return Boolean.valueOf(equals);
        }
        if (!a.optBoolean("isNewOpenAuthFlow") || !TextUtils.equals("openAuthTokenInvalid", a2)) {
            throw new Exception("re login fail.");
        }
        throw new IllegalStateException("6601");
    }
}
