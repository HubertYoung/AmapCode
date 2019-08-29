package com.alipay.android.phone.inside.commonbiz.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.commonbiz.login.uniformity.AccountUniformity;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import org.json.JSONObject;

public class CheckAccountUniformityService extends AbstractInsideService<JSONObject, Bundle> {
    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        boolean a = new AccountUniformity().a((JSONObject) obj);
        Bundle bundle = new Bundle();
        bundle.putBoolean("uniformity", a);
        return bundle;
    }
}
