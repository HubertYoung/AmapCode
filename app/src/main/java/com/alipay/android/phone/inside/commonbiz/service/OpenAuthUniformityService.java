package com.alipay.android.phone.inside.commonbiz.service;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.commonbiz.login.utils.LoginProvider;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;

public class OpenAuthUniformityService extends AbstractInsideService<Bundle, Bundle> {
    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        String e = RunningConfig.e();
        if (OutsideConfig.p()) {
            String b = OutsideConfig.b();
            if (!TextUtils.isEmpty(e) && !TextUtils.equals(e, b)) {
                new LoginProvider().a();
            }
        }
        return new Bundle();
    }
}
