package com.alipay.android.phone.inside.commonbiz.service;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.commonbiz.login.uniformity.AccountUniformity;
import com.alipay.android.phone.inside.commonbiz.login.utils.LoginProvider;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import org.json.JSONObject;

public class AccountUniformityService extends AbstractInsideService<JSONObject, Bundle> {
    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        JSONObject jSONObject = (JSONObject) obj;
        boolean z = true;
        boolean optBoolean = jSONObject.optBoolean("reLogin", true);
        AccountUniformity accountUniformity = new AccountUniformity();
        LoginProvider loginProvider = new LoginProvider();
        boolean a = accountUniformity.a(jSONObject);
        if (!a) {
            accountUniformity.a();
            loginProvider.a();
            if (optBoolean) {
                getContext();
                z = TextUtils.equals("success", loginProvider.a(jSONObject, false));
            }
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("uniformity", a);
        bundle.putBoolean("reLoginResult", z);
        return bundle;
    }
}
