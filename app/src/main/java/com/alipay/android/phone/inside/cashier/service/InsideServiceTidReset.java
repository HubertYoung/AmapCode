package com.alipay.android.phone.inside.cashier.service;

import android.os.Bundle;
import com.alipay.android.app.helper.TidHelper;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class InsideServiceTidReset extends AbstractInsideService<Bundle, Bundle> {
    public Bundle startForResult(Bundle bundle) {
        LoggerFactory.f().b((String) "inside", (String) "InsideServiceTidReset::startForResult");
        try {
            TidHelper.resetTID(LauncherApplication.a());
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "cashier", (String) "ResetTid", th);
        }
        return null;
    }
}
