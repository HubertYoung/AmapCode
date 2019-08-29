package com.ali.user.mobile.external.InSideService;

import android.os.Bundle;
import android.text.TextUtils;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.login.CommonNotifyCaller;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;

public class OAuthAccountConsistencyService implements IInsideService<Bundle, String> {
    private static final String TAG = "OAuthAccountConsistencyService";

    public OAuthAccountConsistencyService() {
        AliUserLog.c(TAG, "OAuthAccountConsistencyService  service constructor");
    }

    public void start(Bundle bundle) throws Exception {
        AliUserLog.c(TAG, "OAuthAccountConsistencyService start");
        startForResult(bundle);
    }

    public void start(IInsideServiceCallback<String> iInsideServiceCallback, Bundle bundle) throws Exception {
        AliUserLog.c(TAG, "OAuthAccountConsistencyService  callback");
        startForResult(bundle);
    }

    public String startForResult(Bundle bundle) {
        AliUserLog.c(TAG, "OAuthAccountConsistencyService  startForResult");
        String string = bundle != null ? bundle.getString("mcBindAlipayUid") : null;
        AliUserLog.c(TAG, "mcBindAlipayUid: ".concat(String.valueOf(string)));
        if (!AntExtServiceManager.getAuthService(LauncherApplication.a()).isLogin()) {
            AliUserLog.c(TAG, "check consistency ,inside has not login");
            return "alipayNotLogin";
        }
        String currentLoginUserId = AntExtServiceManager.getAccountService(LauncherApplication.a().getApplicationContext()).getCurrentLoginUserId();
        AliUserLog.c(TAG, "cUid: ".concat(String.valueOf(currentLoginUserId)));
        if (TextUtils.equals(string, currentLoginUserId)) {
            AliUserLog.c(TAG, "oauth账户一致");
            return "YES";
        }
        AliUserLog.c(TAG, "oauth账户不一致，登出");
        SecurityUtil.a();
        AntExtServiceManager.getLogoutService(LauncherApplication.a().getApplicationContext()).logout(new CommonNotifyCaller() {
            public void onFinish() {
                AliUserLog.c(OAuthAccountConsistencyService.TAG, "logout onFinish");
            }

            public void onError() {
                AliUserLog.c(OAuthAccountConsistencyService.TAG, "logout onError");
            }
        });
        return "NO";
    }
}
