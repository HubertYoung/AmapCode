package com.ali.user.mobile.external.InSideService;

import android.content.Intent;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.external.LogoutInsideActivity;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.CommonNotifyCaller;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;

public class LogoutExternalService implements IInsideService {
    private static final String TAG = "LogoutExternalService";

    public LogoutExternalService() {
        AliUserLog.c(TAG, "LogoutExternalService service constructor");
    }

    public void start(Object obj) {
        AliUserLog.c(TAG, "LogoutExternalService start 1");
    }

    public void start(IInsideServiceCallback iInsideServiceCallback, Object obj) {
        LoggerUtils.a("event", "inside_logout", "UC-INSIDE-LOGIN-LOGOUT-170401-3", "");
        AliUserLog.c(TAG, "LogoutExternalService start 2");
        AliuserLoginContext.b(iInsideServiceCallback);
        try {
            AntExtServiceManager.getLogoutService(LauncherApplication.a().getApplicationContext()).logout(new CommonNotifyCaller() {
                public void onError() {
                }

                public void onFinish() {
                    synchronized (LogoutInsideActivity.class) {
                        IInsideServiceCallback h = AliuserLoginContext.h();
                        if (h != null) {
                            h.onComplted("");
                            AliuserLoginContext.b(null);
                        }
                    }
                }
            });
        } catch (Throwable th) {
            AliUserLog.b(TAG, "logout exception", th);
            if (iInsideServiceCallback != null) {
                iInsideServiceCallback.onException(th);
                AliuserLoginContext.b(null);
            }
        }
    }

    public Object startForResult(Object obj) {
        AliUserLog.c(TAG, "LogoutExternalService start 3");
        return null;
    }

    private void goLogoutActivity() {
        Intent intent = new Intent(LauncherApplication.a().getApplicationContext(), LogoutInsideActivity.class);
        intent.setFlags(268435456);
        LauncherApplication.a().startActivity(intent);
    }
}
