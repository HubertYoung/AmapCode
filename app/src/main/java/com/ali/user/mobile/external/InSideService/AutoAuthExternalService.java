package com.ali.user.mobile.external.InSideService;

import android.os.Bundle;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;

public class AutoAuthExternalService implements IInsideService<Bundle, Boolean> {
    private static final String ACTION = "action";
    private static final String IS_LOGIN_NAME = "is_login";
    private static final String RPC_AUTH = "rpc_auth";
    private static final String RPC_AUTH_FORCE_NOT_SHOW_LOGIN = "notShowLoginApp";
    private static final String TAG = "AutoAuthExternalService";

    public AutoAuthExternalService() {
        AliUserLog.c(TAG, "AutoAuthExternalService  service constructor");
    }

    public void start(Bundle bundle) {
        AliUserLog.c(TAG, "AutoAuthExternalService start just autologin,not care result");
        startForResult(bundle);
    }

    public void start(IInsideServiceCallback iInsideServiceCallback, Bundle bundle) {
        AliUserLog.c(TAG, "AutoAuthExternalService not support callback");
        startForResult(bundle);
    }

    public Boolean startForResult(Bundle bundle) {
        LoggerUtils.a("event", "inside_autologin", "UC-INSIDE-LOGIN-AUTO-170401-4", "");
        try {
            String string = bundle.getString("action");
            boolean z = bundle.getBoolean(RPC_AUTH_FORCE_NOT_SHOW_LOGIN, false);
            AliUserLog.c(TAG, "AutoAuthExternalService startForResult action = ".concat(String.valueOf(string)));
            if (RPC_AUTH.equalsIgnoreCase(string)) {
                return Boolean.valueOf(AntExtServiceManager.getAuthService(LauncherApplication.a().getApplicationContext()).rpcAuth(z));
            }
            if ("is_login".equalsIgnoreCase(string)) {
                return Boolean.valueOf(AntExtServiceManager.getAuthService(LauncherApplication.a().getApplicationContext()).isLogin());
            }
            throw new RuntimeException("params is not valid , need action");
        } catch (Exception e) {
            AliUserLog.a(TAG, "startForResult error", e);
            throw new RuntimeException("inside error");
        }
    }
}
