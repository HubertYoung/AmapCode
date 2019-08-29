package com.ali.user.mobile.external.InSideService;

import android.os.Bundle;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.app.LoginAppService;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;

public class AccountChangeExternalService implements IInsideService {
    private static final String TAG = "AccountChangeExternalService";

    public AccountChangeExternalService() {
        AliUserLog.c(TAG, "AccountChangeExternalService service constructor");
    }

    public void start(Object obj) {
        AliUserLog.c(TAG, "AccountChangeExternalService start 1");
    }

    public void start(IInsideServiceCallback iInsideServiceCallback, Object obj) {
        LoggerUtils.a("event", "inside_login_switch_account", "UC-INSIDE-LOGIN-SWITCH-170401-2", "");
        AliUserLog.c(TAG, "AccountChangeExternalService start 2");
        AliuserLoginContext.a(iInsideServiceCallback);
        Bundle bundle = new Bundle();
        bundle.putBoolean("source_accountSelectAccount", true);
        bundle.putString("resetCookie", "true");
        bundle.putString("LoginSource", "accountManager");
        LoginAppService.getInstance().startLoginPage(LauncherApplication.a(), bundle);
    }

    public Object startForResult(Object obj) {
        AliUserLog.c(TAG, "AccountChangeExternalService start 3");
        return null;
    }
}
